/*      */ package net.minecraft.util.io.netty.channel;
/*      */ 
/*      */ import java.net.SocketAddress;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.Future;
/*      */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*      */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*      */ import net.minecraft.util.io.netty.util.concurrent.EventExecutorGroup;
/*      */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*      */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*      */ import net.minecraft.util.io.netty.util.internal.StringUtil;
/*      */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*      */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class DefaultChannelPipeline
/*      */   implements ChannelPipeline
/*      */ {
/*   46 */   static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);
/*      */ 
/*      */   
/*   49 */   private static final WeakHashMap<Class<?>, String>[] nameCaches = (WeakHashMap<Class<?>, String>[])new WeakHashMap[Runtime.getRuntime().availableProcessors()];
/*      */   final AbstractChannel channel;
/*      */   
/*      */   static {
/*   53 */     for (int i = 0; i < nameCaches.length; i++) {
/*   54 */       nameCaches[i] = new WeakHashMap<Class<?>, String>();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final DefaultChannelHandlerContext head;
/*      */   
/*      */   final DefaultChannelHandlerContext tail;
/*      */   
/*   63 */   private final Map<String, DefaultChannelHandlerContext> name2ctx = new HashMap<String, DefaultChannelHandlerContext>(4);
/*      */ 
/*      */   
/*   66 */   final Map<EventExecutorGroup, EventExecutor> childExecutors = new IdentityHashMap<EventExecutorGroup, EventExecutor>();
/*      */ 
/*      */   
/*      */   public DefaultChannelPipeline(AbstractChannel channel) {
/*   70 */     if (channel == null) {
/*   71 */       throw new NullPointerException("channel");
/*      */     }
/*   73 */     this.channel = channel;
/*      */     
/*   75 */     TailHandler tailHandler = new TailHandler();
/*   76 */     this.tail = new DefaultChannelHandlerContext(this, null, generateName(tailHandler), tailHandler);
/*      */     
/*   78 */     HeadHandler headHandler = new HeadHandler(channel.unsafe());
/*   79 */     this.head = new DefaultChannelHandlerContext(this, null, generateName(headHandler), headHandler);
/*      */     
/*   81 */     this.head.next = this.tail;
/*   82 */     this.tail.prev = this.head;
/*      */   }
/*      */ 
/*      */   
/*      */   public Channel channel() {
/*   87 */     return this.channel;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addFirst(String name, ChannelHandler handler) {
/*   92 */     return addFirst(null, name, handler);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addFirst(EventExecutorGroup group, String name, ChannelHandler handler) {
/*   97 */     synchronized (this) {
/*   98 */       checkDuplicateName(name);
/*   99 */       DefaultChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
/*  100 */       addFirst0(name, newCtx);
/*      */     } 
/*      */     
/*  103 */     return this;
/*      */   }
/*      */   
/*      */   private void addFirst0(String name, DefaultChannelHandlerContext newCtx) {
/*  107 */     checkMultiplicity(newCtx);
/*      */     
/*  109 */     DefaultChannelHandlerContext nextCtx = this.head.next;
/*  110 */     newCtx.prev = this.head;
/*  111 */     newCtx.next = nextCtx;
/*  112 */     this.head.next = newCtx;
/*  113 */     nextCtx.prev = newCtx;
/*      */     
/*  115 */     this.name2ctx.put(name, newCtx);
/*      */     
/*  117 */     callHandlerAdded(newCtx);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addLast(String name, ChannelHandler handler) {
/*  122 */     return addLast(null, name, handler);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addLast(EventExecutorGroup group, String name, ChannelHandler handler) {
/*  127 */     synchronized (this) {
/*  128 */       checkDuplicateName(name);
/*      */       
/*  130 */       DefaultChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
/*  131 */       addLast0(name, newCtx);
/*      */     } 
/*      */     
/*  134 */     return this;
/*      */   }
/*      */   
/*      */   private void addLast0(String name, DefaultChannelHandlerContext newCtx) {
/*  138 */     checkMultiplicity(newCtx);
/*      */     
/*  140 */     DefaultChannelHandlerContext prev = this.tail.prev;
/*  141 */     newCtx.prev = prev;
/*  142 */     newCtx.next = this.tail;
/*  143 */     prev.next = newCtx;
/*  144 */     this.tail.prev = newCtx;
/*      */     
/*  146 */     this.name2ctx.put(name, newCtx);
/*      */     
/*  148 */     callHandlerAdded(newCtx);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addBefore(String baseName, String name, ChannelHandler handler) {
/*  153 */     return addBefore(null, baseName, name, handler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelPipeline addBefore(EventExecutorGroup group, String baseName, String name, ChannelHandler handler) {
/*  159 */     synchronized (this) {
/*  160 */       DefaultChannelHandlerContext ctx = getContextOrDie(baseName);
/*  161 */       checkDuplicateName(name);
/*  162 */       DefaultChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
/*  163 */       addBefore0(name, ctx, newCtx);
/*      */     } 
/*  165 */     return this;
/*      */   }
/*      */   
/*      */   private void addBefore0(String name, DefaultChannelHandlerContext ctx, DefaultChannelHandlerContext newCtx) {
/*  169 */     checkMultiplicity(newCtx);
/*      */     
/*  171 */     newCtx.prev = ctx.prev;
/*  172 */     newCtx.next = ctx;
/*  173 */     ctx.prev.next = newCtx;
/*  174 */     ctx.prev = newCtx;
/*      */     
/*  176 */     this.name2ctx.put(name, newCtx);
/*      */     
/*  178 */     callHandlerAdded(newCtx);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addAfter(String baseName, String name, ChannelHandler handler) {
/*  183 */     return addAfter(null, baseName, name, handler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelPipeline addAfter(EventExecutorGroup group, String baseName, String name, ChannelHandler handler) {
/*  189 */     synchronized (this) {
/*  190 */       DefaultChannelHandlerContext ctx = getContextOrDie(baseName);
/*  191 */       checkDuplicateName(name);
/*  192 */       DefaultChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
/*      */       
/*  194 */       addAfter0(name, ctx, newCtx);
/*      */     } 
/*      */     
/*  197 */     return this;
/*      */   }
/*      */   
/*      */   private void addAfter0(String name, DefaultChannelHandlerContext ctx, DefaultChannelHandlerContext newCtx) {
/*  201 */     checkDuplicateName(name);
/*  202 */     checkMultiplicity(newCtx);
/*      */     
/*  204 */     newCtx.prev = ctx;
/*  205 */     newCtx.next = ctx.next;
/*  206 */     ctx.next.prev = newCtx;
/*  207 */     ctx.next = newCtx;
/*      */     
/*  209 */     this.name2ctx.put(name, newCtx);
/*      */     
/*  211 */     callHandlerAdded(newCtx);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addFirst(ChannelHandler... handlers) {
/*  216 */     return addFirst((EventExecutorGroup)null, handlers);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addFirst(EventExecutorGroup executor, ChannelHandler... handlers) {
/*  221 */     if (handlers == null) {
/*  222 */       throw new NullPointerException("handlers");
/*      */     }
/*  224 */     if (handlers.length == 0 || handlers[0] == null) {
/*  225 */       return this;
/*      */     }
/*      */     
/*      */     int size;
/*  229 */     for (size = 1; size < handlers.length && 
/*  230 */       handlers[size] != null; size++);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  235 */     for (int i = size - 1; i >= 0; i--) {
/*  236 */       ChannelHandler h = handlers[i];
/*  237 */       addFirst(executor, generateName(h), h);
/*      */     } 
/*      */     
/*  240 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addLast(ChannelHandler... handlers) {
/*  245 */     return addLast((EventExecutorGroup)null, handlers);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addLast(EventExecutorGroup executor, ChannelHandler... handlers) {
/*  250 */     if (handlers == null) {
/*  251 */       throw new NullPointerException("handlers");
/*      */     }
/*      */     
/*  254 */     for (ChannelHandler h : handlers) {
/*  255 */       if (h == null) {
/*      */         break;
/*      */       }
/*  258 */       addLast(executor, generateName(h), h);
/*      */     } 
/*      */     
/*  261 */     return this;
/*      */   }
/*      */   private String generateName(ChannelHandler handler) {
/*      */     String name;
/*  265 */     WeakHashMap<Class<?>, String> cache = nameCaches[(int)(Thread.currentThread().getId() % nameCaches.length)];
/*  266 */     Class<?> handlerType = handler.getClass();
/*      */     
/*  268 */     synchronized (cache) {
/*  269 */       name = cache.get(handlerType);
/*  270 */       if (name == null) {
/*  271 */         name = StringUtil.simpleClassName(handlerType) + "#0";
/*  272 */         cache.put(handlerType, name);
/*      */       } 
/*      */     } 
/*      */     
/*  276 */     synchronized (this) {
/*      */ 
/*      */       
/*  279 */       if (this.name2ctx.containsKey(name)) {
/*  280 */         String baseName = name.substring(0, name.length() - 1);
/*  281 */         for (int i = 1;; i++) {
/*  282 */           String newName = baseName + i;
/*  283 */           if (!this.name2ctx.containsKey(newName)) {
/*  284 */             name = newName;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  291 */     return name;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline remove(ChannelHandler handler) {
/*  296 */     remove(getContextOrDie(handler));
/*  297 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler remove(String name) {
/*  302 */     return remove(getContextOrDie(name)).handler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends ChannelHandler> T remove(Class<T> handlerType) {
/*  308 */     return (T)remove(getContextOrDie(handlerType)).handler();
/*      */   } private DefaultChannelHandlerContext remove(final DefaultChannelHandlerContext ctx) {
/*      */     Future future;
/*      */     DefaultChannelHandlerContext context;
/*  312 */     assert ctx != this.head && ctx != this.tail;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  317 */     synchronized (this) {
/*  318 */       if (!ctx.channel().isRegistered() || ctx.executor().inEventLoop()) {
/*  319 */         remove0(ctx);
/*  320 */         return ctx;
/*      */       } 
/*  322 */       future = ctx.executor().submit(new Runnable()
/*      */           {
/*      */             public void run() {
/*  325 */               synchronized (DefaultChannelPipeline.this) {
/*  326 */                 DefaultChannelPipeline.this.remove0(ctx);
/*      */               } 
/*      */             }
/*      */           });
/*  330 */       context = ctx;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  337 */     waitForFuture((Future<?>)future);
/*      */     
/*  339 */     return context;
/*      */   }
/*      */   
/*      */   void remove0(DefaultChannelHandlerContext ctx) {
/*  343 */     DefaultChannelHandlerContext prev = ctx.prev;
/*  344 */     DefaultChannelHandlerContext next = ctx.next;
/*  345 */     prev.next = next;
/*  346 */     next.prev = prev;
/*  347 */     this.name2ctx.remove(ctx.name());
/*  348 */     callHandlerRemoved(ctx);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler removeFirst() {
/*  353 */     if (this.head.next == this.tail) {
/*  354 */       throw new NoSuchElementException();
/*      */     }
/*  356 */     return remove(this.head.next).handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler removeLast() {
/*  361 */     if (this.head.next == this.tail) {
/*  362 */       throw new NoSuchElementException();
/*      */     }
/*  364 */     return remove(this.tail.prev).handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline replace(ChannelHandler oldHandler, String newName, ChannelHandler newHandler) {
/*  369 */     replace(getContextOrDie(oldHandler), newName, newHandler);
/*  370 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler replace(String oldName, String newName, ChannelHandler newHandler) {
/*  375 */     return replace(getContextOrDie(oldName), newName, newHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends ChannelHandler> T replace(Class<T> oldHandlerType, String newName, ChannelHandler newHandler) {
/*  382 */     return (T)replace(getContextOrDie(oldHandlerType), newName, newHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private ChannelHandler replace(final DefaultChannelHandlerContext ctx, final String newName, ChannelHandler newHandler) {
/*      */     Future future;
/*  389 */     assert ctx != this.head && ctx != this.tail;
/*      */ 
/*      */     
/*  392 */     synchronized (this) {
/*  393 */       boolean sameName = ctx.name().equals(newName);
/*  394 */       if (!sameName) {
/*  395 */         checkDuplicateName(newName);
/*      */       }
/*      */       
/*  398 */       final DefaultChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, (EventExecutorGroup)ctx.executor, newName, newHandler);
/*      */ 
/*      */       
/*  401 */       if (!newCtx.channel().isRegistered() || newCtx.executor().inEventLoop()) {
/*  402 */         replace0(ctx, newName, newCtx);
/*  403 */         return ctx.handler();
/*      */       } 
/*  405 */       future = newCtx.executor().submit(new Runnable()
/*      */           {
/*      */             public void run() {
/*  408 */               synchronized (DefaultChannelPipeline.this) {
/*  409 */                 DefaultChannelPipeline.this.replace0(ctx, newName, newCtx);
/*      */               } 
/*      */             }
/*      */           });
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  419 */     waitForFuture((Future<?>)future);
/*      */     
/*  421 */     return ctx.handler();
/*      */   }
/*      */ 
/*      */   
/*      */   private void replace0(DefaultChannelHandlerContext oldCtx, String newName, DefaultChannelHandlerContext newCtx) {
/*  426 */     checkMultiplicity(newCtx);
/*      */     
/*  428 */     DefaultChannelHandlerContext prev = oldCtx.prev;
/*  429 */     DefaultChannelHandlerContext next = oldCtx.next;
/*  430 */     newCtx.prev = prev;
/*  431 */     newCtx.next = next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  437 */     prev.next = newCtx;
/*  438 */     next.prev = newCtx;
/*      */     
/*  440 */     if (!oldCtx.name().equals(newName)) {
/*  441 */       this.name2ctx.remove(oldCtx.name());
/*      */     }
/*  443 */     this.name2ctx.put(newName, newCtx);
/*      */ 
/*      */     
/*  446 */     oldCtx.prev = newCtx;
/*  447 */     oldCtx.next = newCtx;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  452 */     callHandlerAdded(newCtx);
/*  453 */     callHandlerRemoved(oldCtx);
/*      */   }
/*      */   
/*      */   private static void checkMultiplicity(ChannelHandlerContext ctx) {
/*  457 */     ChannelHandler handler = ctx.handler();
/*  458 */     if (handler instanceof ChannelHandlerAdapter) {
/*  459 */       ChannelHandlerAdapter h = (ChannelHandlerAdapter)handler;
/*  460 */       if (!h.isSharable() && h.added) {
/*  461 */         throw new ChannelPipelineException(h.getClass().getName() + " is not a @Sharable handler, so can't be added or removed multiple times.");
/*      */       }
/*      */ 
/*      */       
/*  465 */       h.added = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void callHandlerAdded(final ChannelHandlerContext ctx) {
/*  470 */     if (ctx.channel().isRegistered() && !ctx.executor().inEventLoop()) {
/*  471 */       ctx.executor().execute(new Runnable()
/*      */           {
/*      */             public void run() {
/*  474 */               DefaultChannelPipeline.this.callHandlerAdded0(ctx);
/*      */             }
/*      */           });
/*      */       return;
/*      */     } 
/*  479 */     callHandlerAdded0(ctx);
/*      */   }
/*      */   
/*      */   private void callHandlerAdded0(ChannelHandlerContext ctx) {
/*      */     try {
/*  484 */       ctx.handler().handlerAdded(ctx);
/*  485 */     } catch (Throwable t) {
/*  486 */       boolean removed = false;
/*      */       try {
/*  488 */         remove((DefaultChannelHandlerContext)ctx);
/*  489 */         removed = true;
/*  490 */       } catch (Throwable t2) {
/*  491 */         if (logger.isWarnEnabled()) {
/*  492 */           logger.warn("Failed to remove a handler: " + ctx.name(), t2);
/*      */         }
/*      */       } 
/*      */       
/*  496 */       if (removed) {
/*  497 */         fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerAdded() has thrown an exception; removed.", t));
/*      */       }
/*      */       else {
/*      */         
/*  501 */         fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerAdded() has thrown an exception; also failed to remove.", t));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void callHandlerRemoved(final DefaultChannelHandlerContext ctx) {
/*  509 */     if (ctx.channel().isRegistered() && !ctx.executor().inEventLoop()) {
/*  510 */       ctx.executor().execute(new Runnable()
/*      */           {
/*      */             public void run() {
/*  513 */               DefaultChannelPipeline.this.callHandlerRemoved0(ctx);
/*      */             }
/*      */           });
/*      */       return;
/*      */     } 
/*  518 */     callHandlerRemoved0(ctx);
/*      */   }
/*      */ 
/*      */   
/*      */   private void callHandlerRemoved0(DefaultChannelHandlerContext ctx) {
/*      */     try {
/*  524 */       ctx.handler().handlerRemoved(ctx);
/*  525 */       ctx.setRemoved();
/*  526 */     } catch (Throwable t) {
/*  527 */       fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerRemoved() has thrown an exception.", t));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void waitForFuture(Future<?> future) {
/*      */     try {
/*  549 */       future.get();
/*  550 */     } catch (ExecutionException ex) {
/*      */       
/*  552 */       PlatformDependent.throwException(ex.getCause());
/*  553 */     } catch (InterruptedException ex) {
/*      */       
/*  555 */       Thread.currentThread().interrupt();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler first() {
/*  561 */     ChannelHandlerContext first = firstContext();
/*  562 */     if (first == null) {
/*  563 */       return null;
/*      */     }
/*  565 */     return first.handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext firstContext() {
/*  570 */     DefaultChannelHandlerContext first = this.head.next;
/*  571 */     if (first == this.head) {
/*  572 */       return null;
/*      */     }
/*  574 */     return this.head.next;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler last() {
/*  579 */     DefaultChannelHandlerContext last = this.tail.prev;
/*  580 */     if (last == this.head) {
/*  581 */       return null;
/*      */     }
/*  583 */     return last.handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext lastContext() {
/*  588 */     DefaultChannelHandlerContext last = this.tail.prev;
/*  589 */     if (last == this.head) {
/*  590 */       return null;
/*      */     }
/*  592 */     return last;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler get(String name) {
/*  597 */     ChannelHandlerContext ctx = context(name);
/*  598 */     if (ctx == null) {
/*  599 */       return null;
/*      */     }
/*  601 */     return ctx.handler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends ChannelHandler> T get(Class<T> handlerType) {
/*  608 */     ChannelHandlerContext ctx = context(handlerType);
/*  609 */     if (ctx == null) {
/*  610 */       return null;
/*      */     }
/*  612 */     return (T)ctx.handler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext context(String name) {
/*  618 */     if (name == null) {
/*  619 */       throw new NullPointerException("name");
/*      */     }
/*      */     
/*  622 */     synchronized (this) {
/*  623 */       return this.name2ctx.get(name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext context(ChannelHandler handler) {
/*  629 */     if (handler == null) {
/*  630 */       throw new NullPointerException("handler");
/*      */     }
/*      */     
/*  633 */     DefaultChannelHandlerContext ctx = this.head.next;
/*      */     
/*      */     while (true) {
/*  636 */       if (ctx == null) {
/*  637 */         return null;
/*      */       }
/*      */       
/*  640 */       if (ctx.handler() == handler) {
/*  641 */         return ctx;
/*      */       }
/*      */       
/*  644 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext context(Class<? extends ChannelHandler> handlerType) {
/*  650 */     if (handlerType == null) {
/*  651 */       throw new NullPointerException("handlerType");
/*      */     }
/*      */     
/*  654 */     DefaultChannelHandlerContext ctx = this.head.next;
/*      */     while (true) {
/*  656 */       if (ctx == null) {
/*  657 */         return null;
/*      */       }
/*  659 */       if (handlerType.isAssignableFrom(ctx.handler().getClass())) {
/*  660 */         return ctx;
/*      */       }
/*  662 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> names() {
/*  668 */     List<String> list = new ArrayList<String>();
/*  669 */     DefaultChannelHandlerContext ctx = this.head.next;
/*      */     while (true) {
/*  671 */       if (ctx == null) {
/*  672 */         return list;
/*      */       }
/*  674 */       list.add(ctx.name());
/*  675 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<String, ChannelHandler> toMap() {
/*  681 */     Map<String, ChannelHandler> map = new LinkedHashMap<String, ChannelHandler>();
/*  682 */     DefaultChannelHandlerContext ctx = this.head.next;
/*      */     while (true) {
/*  684 */       if (ctx == this.tail) {
/*  685 */         return map;
/*      */       }
/*  687 */       map.put(ctx.name(), ctx.handler());
/*  688 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator<Map.Entry<String, ChannelHandler>> iterator() {
/*  694 */     return toMap().entrySet().iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  702 */     StringBuilder buf = new StringBuilder();
/*  703 */     buf.append(getClass().getSimpleName());
/*  704 */     buf.append('{');
/*  705 */     DefaultChannelHandlerContext ctx = this.head.next;
/*      */     
/*  707 */     while (ctx != this.tail) {
/*      */ 
/*      */ 
/*      */       
/*  711 */       buf.append('(');
/*  712 */       buf.append(ctx.name());
/*  713 */       buf.append(" = ");
/*  714 */       buf.append(ctx.handler().getClass().getName());
/*  715 */       buf.append(')');
/*      */       
/*  717 */       ctx = ctx.next;
/*  718 */       if (ctx == this.tail) {
/*      */         break;
/*      */       }
/*      */       
/*  722 */       buf.append(", ");
/*      */     } 
/*  724 */     buf.append('}');
/*  725 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelRegistered() {
/*  730 */     this.head.fireChannelRegistered();
/*  731 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelUnregistered() {
/*  736 */     this.head.fireChannelUnregistered();
/*      */ 
/*      */     
/*  739 */     if (!this.channel.isOpen()) {
/*  740 */       teardownAll();
/*      */     }
/*  742 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void teardownAll() {
/*  751 */     this.tail.prev.teardown();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelActive() {
/*  756 */     this.head.fireChannelActive();
/*      */     
/*  758 */     if (this.channel.config().isAutoRead()) {
/*  759 */       this.channel.read();
/*      */     }
/*      */     
/*  762 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelInactive() {
/*  767 */     this.head.fireChannelInactive();
/*  768 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireExceptionCaught(Throwable cause) {
/*  773 */     this.head.fireExceptionCaught(cause);
/*  774 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireUserEventTriggered(Object event) {
/*  779 */     this.head.fireUserEventTriggered(event);
/*  780 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelRead(Object msg) {
/*  785 */     this.head.fireChannelRead(msg);
/*  786 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelReadComplete() {
/*  791 */     this.head.fireChannelReadComplete();
/*  792 */     if (this.channel.config().isAutoRead()) {
/*  793 */       read();
/*      */     }
/*  795 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelWritabilityChanged() {
/*  800 */     this.head.fireChannelWritabilityChanged();
/*  801 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture bind(SocketAddress localAddress) {
/*  806 */     return this.tail.bind(localAddress);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress) {
/*  811 */     return this.tail.connect(remoteAddress);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/*  816 */     return this.tail.connect(remoteAddress, localAddress);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture disconnect() {
/*  821 */     return this.tail.disconnect();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture close() {
/*  826 */     return this.tail.close();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture deregister() {
/*  831 */     return this.tail.deregister();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline flush() {
/*  836 */     this.tail.flush();
/*  837 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
/*  842 */     return this.tail.bind(localAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/*  847 */     return this.tail.connect(remoteAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*  852 */     return this.tail.connect(remoteAddress, localAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture disconnect(ChannelPromise promise) {
/*  857 */     return this.tail.disconnect(promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture close(ChannelPromise promise) {
/*  862 */     return this.tail.close(promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture deregister(ChannelPromise promise) {
/*  867 */     return this.tail.deregister(promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline read() {
/*  872 */     this.tail.read();
/*  873 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture write(Object msg) {
/*  878 */     return this.tail.write(msg);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture write(Object msg, ChannelPromise promise) {
/*  883 */     return this.tail.write(msg, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/*  888 */     return this.tail.writeAndFlush(msg, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture writeAndFlush(Object msg) {
/*  893 */     return this.tail.writeAndFlush(msg);
/*      */   }
/*      */   
/*      */   private void checkDuplicateName(String name) {
/*  897 */     if (this.name2ctx.containsKey(name)) {
/*  898 */       throw new IllegalArgumentException("Duplicate handler name: " + name);
/*      */     }
/*      */   }
/*      */   
/*      */   private DefaultChannelHandlerContext getContextOrDie(String name) {
/*  903 */     DefaultChannelHandlerContext ctx = (DefaultChannelHandlerContext)context(name);
/*  904 */     if (ctx == null) {
/*  905 */       throw new NoSuchElementException(name);
/*      */     }
/*  907 */     return ctx;
/*      */   }
/*      */ 
/*      */   
/*      */   private DefaultChannelHandlerContext getContextOrDie(ChannelHandler handler) {
/*  912 */     DefaultChannelHandlerContext ctx = (DefaultChannelHandlerContext)context(handler);
/*  913 */     if (ctx == null) {
/*  914 */       throw new NoSuchElementException(handler.getClass().getName());
/*      */     }
/*  916 */     return ctx;
/*      */   }
/*      */ 
/*      */   
/*      */   private DefaultChannelHandlerContext getContextOrDie(Class<? extends ChannelHandler> handlerType) {
/*  921 */     DefaultChannelHandlerContext ctx = (DefaultChannelHandlerContext)context(handlerType);
/*  922 */     if (ctx == null) {
/*  923 */       throw new NoSuchElementException(handlerType.getName());
/*      */     }
/*  925 */     return ctx;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class TailHandler
/*      */     implements ChannelInboundHandler
/*      */   {
/*      */     public void channelRegistered(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void channelActive(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void channelInactive(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void handlerAdded(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {}
/*      */ 
/*      */     
/*      */     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/*  958 */       DefaultChannelPipeline.logger.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", cause);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*      */       try {
/*  966 */         DefaultChannelPipeline.logger.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", msg);
/*      */       }
/*      */       finally {
/*      */         
/*  970 */         ReferenceCountUtil.release(msg);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {}
/*      */   }
/*      */   
/*      */   static final class HeadHandler
/*      */     implements ChannelOutboundHandler
/*      */   {
/*      */     protected final Channel.Unsafe unsafe;
/*      */     
/*      */     protected HeadHandler(Channel.Unsafe unsafe) {
/*  983 */       this.unsafe = unsafe;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handlerAdded(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 1000 */       this.unsafe.bind(localAddress, promise);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 1008 */       this.unsafe.connect(remoteAddress, localAddress, promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 1013 */       this.unsafe.disconnect(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 1018 */       this.unsafe.close(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 1023 */       this.unsafe.deregister(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void read(ChannelHandlerContext ctx) {
/* 1028 */       this.unsafe.beginRead();
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 1033 */       this.unsafe.write(msg, promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void flush(ChannelHandlerContext ctx) throws Exception {
/* 1038 */       this.unsafe.flush();
/*      */     }
/*      */ 
/*      */     
/*      */     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 1043 */       ctx.fireExceptionCaught(cause);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\DefaultChannelPipeline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */