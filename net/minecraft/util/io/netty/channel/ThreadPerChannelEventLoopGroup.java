/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.util.concurrent.AbstractEventExecutorGroup;
/*     */ import net.minecraft.util.io.netty.util.concurrent.DefaultPromise;
/*     */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.FutureListener;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Promise;
/*     */ import net.minecraft.util.io.netty.util.internal.EmptyArrays;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*     */ import net.minecraft.util.io.netty.util.internal.ReadOnlyIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadPerChannelEventLoopGroup
/*     */   extends AbstractEventExecutorGroup
/*     */   implements EventLoopGroup
/*     */ {
/*     */   private final Object[] childArgs;
/*     */   private final int maxChannels;
/*     */   final ThreadFactory threadFactory;
/*  48 */   final Set<ThreadPerChannelEventLoop> activeChildren = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
/*     */   
/*  50 */   final Queue<ThreadPerChannelEventLoop> idleChildren = new ConcurrentLinkedQueue<ThreadPerChannelEventLoop>();
/*     */   
/*     */   private final ChannelException tooManyChannels;
/*     */   private volatile boolean shuttingDown;
/*  54 */   private final Promise<?> terminationFuture = (Promise<?>)new DefaultPromise((EventExecutor)GlobalEventExecutor.INSTANCE);
/*  55 */   private final FutureListener<Object> childTerminationListener = new FutureListener<Object>()
/*     */     {
/*     */       public void operationComplete(Future<Object> future) throws Exception
/*     */       {
/*  59 */         if (ThreadPerChannelEventLoopGroup.this.isTerminated()) {
/*  60 */           ThreadPerChannelEventLoopGroup.this.terminationFuture.trySuccess(null);
/*     */         }
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ThreadPerChannelEventLoopGroup() {
/*  69 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ThreadPerChannelEventLoopGroup(int maxChannels) {
/*  82 */     this(maxChannels, Executors.defaultThreadFactory(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ThreadPerChannelEventLoopGroup(int maxChannels, ThreadFactory threadFactory, Object... args) {
/*  98 */     if (maxChannels < 0) {
/*  99 */       throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", new Object[] { Integer.valueOf(maxChannels) }));
/*     */     }
/*     */     
/* 102 */     if (threadFactory == null) {
/* 103 */       throw new NullPointerException("threadFactory");
/*     */     }
/*     */     
/* 106 */     if (args == null) {
/* 107 */       this.childArgs = EmptyArrays.EMPTY_OBJECTS;
/*     */     } else {
/* 109 */       this.childArgs = (Object[])args.clone();
/*     */     } 
/*     */     
/* 112 */     this.maxChannels = maxChannels;
/* 113 */     this.threadFactory = threadFactory;
/*     */     
/* 115 */     this.tooManyChannels = new ChannelException("too many channels (max: " + maxChannels + ')');
/* 116 */     this.tooManyChannels.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ThreadPerChannelEventLoop newChild(Object... args) throws Exception {
/* 124 */     return new ThreadPerChannelEventLoop(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<EventExecutor> iterator() {
/* 129 */     return (Iterator<EventExecutor>)new ReadOnlyIterator(this.activeChildren.iterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoop next() {
/* 134 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 139 */     this.shuttingDown = true;
/*     */     
/* 141 */     for (EventLoop l : this.activeChildren) {
/* 142 */       l.shutdownGracefully(quietPeriod, timeout, unit);
/*     */     }
/* 144 */     for (EventLoop l : this.idleChildren) {
/* 145 */       l.shutdownGracefully(quietPeriod, timeout, unit);
/*     */     }
/*     */ 
/*     */     
/* 149 */     if (isTerminated()) {
/* 150 */       this.terminationFuture.trySuccess(null);
/*     */     }
/*     */     
/* 153 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 158 */     return (Future<?>)this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/* 164 */     this.shuttingDown = true;
/*     */     
/* 166 */     for (EventLoop l : this.activeChildren) {
/* 167 */       l.shutdown();
/*     */     }
/* 169 */     for (EventLoop l : this.idleChildren) {
/* 170 */       l.shutdown();
/*     */     }
/*     */ 
/*     */     
/* 174 */     if (isTerminated()) {
/* 175 */       this.terminationFuture.trySuccess(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 181 */     for (EventLoop l : this.activeChildren) {
/* 182 */       if (!l.isShuttingDown()) {
/* 183 */         return false;
/*     */       }
/*     */     } 
/* 186 */     for (EventLoop l : this.idleChildren) {
/* 187 */       if (!l.isShuttingDown()) {
/* 188 */         return false;
/*     */       }
/*     */     } 
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 196 */     for (EventLoop l : this.activeChildren) {
/* 197 */       if (!l.isShutdown()) {
/* 198 */         return false;
/*     */       }
/*     */     } 
/* 201 */     for (EventLoop l : this.idleChildren) {
/* 202 */       if (!l.isShutdown()) {
/* 203 */         return false;
/*     */       }
/*     */     } 
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 211 */     for (EventLoop l : this.activeChildren) {
/* 212 */       if (!l.isTerminated()) {
/* 213 */         return false;
/*     */       }
/*     */     } 
/* 216 */     for (EventLoop l : this.idleChildren) {
/* 217 */       if (!l.isTerminated()) {
/* 218 */         return false;
/*     */       }
/*     */     } 
/* 221 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 227 */     long deadline = System.nanoTime() + unit.toNanos(timeout);
/* 228 */     label26: for (EventLoop l : this.activeChildren) {
/*     */       while (true) {
/* 230 */         long timeLeft = deadline - System.nanoTime();
/* 231 */         if (timeLeft <= 0L) {
/* 232 */           return isTerminated();
/*     */         }
/* 234 */         if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
/*     */           continue label26;
/*     */         }
/*     */       } 
/*     */     } 
/* 239 */     label27: for (EventLoop l : this.idleChildren) {
/*     */       while (true) {
/* 241 */         long timeLeft = deadline - System.nanoTime();
/* 242 */         if (timeLeft <= 0L) {
/* 243 */           return isTerminated();
/*     */         }
/* 245 */         if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
/*     */           continue label27;
/*     */         }
/*     */       } 
/*     */     } 
/* 250 */     return isTerminated();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(Channel channel) {
/* 255 */     if (channel == null) {
/* 256 */       throw new NullPointerException("channel");
/*     */     }
/*     */     try {
/* 259 */       return nextChild().register(channel);
/* 260 */     } catch (Throwable t) {
/* 261 */       return channel.newFailedFuture(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 267 */     if (channel == null) {
/* 268 */       throw new NullPointerException("channel");
/*     */     }
/*     */     try {
/* 271 */       return nextChild().register(channel, promise);
/* 272 */     } catch (Throwable t) {
/* 273 */       promise.setFailure(t);
/* 274 */       return promise;
/*     */     } 
/*     */   }
/*     */   
/*     */   private EventLoop nextChild() throws Exception {
/* 279 */     if (this.shuttingDown) {
/* 280 */       throw new RejectedExecutionException("shutting down");
/*     */     }
/*     */     
/* 283 */     ThreadPerChannelEventLoop loop = this.idleChildren.poll();
/* 284 */     if (loop == null) {
/* 285 */       if (this.maxChannels > 0 && this.activeChildren.size() >= this.maxChannels) {
/* 286 */         throw this.tooManyChannels;
/*     */       }
/* 288 */       loop = newChild(this.childArgs);
/* 289 */       loop.terminationFuture().addListener((GenericFutureListener)this.childTerminationListener);
/*     */     } 
/* 291 */     this.activeChildren.add(loop);
/* 292 */     return loop;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\ThreadPerChannelEventLoopGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */