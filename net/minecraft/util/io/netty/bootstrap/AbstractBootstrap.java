/*     */ package net.minecraft.util.io.netty.bootstrap;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOption;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.EventLoopGroup;
/*     */ import net.minecraft.util.io.netty.util.AttributeKey;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel>
/*     */   implements Cloneable
/*     */ {
/*     */   private volatile EventLoopGroup group;
/*     */   private volatile ChannelFactory<? extends C> channelFactory;
/*     */   private volatile SocketAddress localAddress;
/*  48 */   private final Map<ChannelOption<?>, Object> options = new LinkedHashMap<ChannelOption<?>, Object>();
/*  49 */   private final Map<AttributeKey<?>, Object> attrs = new LinkedHashMap<AttributeKey<?>, Object>();
/*     */ 
/*     */   
/*     */   private volatile ChannelHandler handler;
/*     */ 
/*     */ 
/*     */   
/*     */   AbstractBootstrap(AbstractBootstrap<B, C> bootstrap) {
/*  57 */     this.group = bootstrap.group;
/*  58 */     this.channelFactory = bootstrap.channelFactory;
/*  59 */     this.handler = bootstrap.handler;
/*  60 */     this.localAddress = bootstrap.localAddress;
/*  61 */     synchronized (bootstrap.options) {
/*  62 */       this.options.putAll(bootstrap.options);
/*     */     } 
/*  64 */     synchronized (bootstrap.attrs) {
/*  65 */       this.attrs.putAll(bootstrap.attrs);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B group(EventLoopGroup group) {
/*  75 */     if (group == null) {
/*  76 */       throw new NullPointerException("group");
/*     */     }
/*  78 */     if (this.group != null) {
/*  79 */       throw new IllegalStateException("group set already");
/*     */     }
/*  81 */     this.group = group;
/*  82 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B channel(Class<? extends C> channelClass) {
/*  91 */     if (channelClass == null) {
/*  92 */       throw new NullPointerException("channelClass");
/*     */     }
/*  94 */     return channelFactory(new BootstrapChannelFactory<C>(channelClass));
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
/*     */   public B channelFactory(ChannelFactory<? extends C> channelFactory) {
/* 106 */     if (channelFactory == null) {
/* 107 */       throw new NullPointerException("channelFactory");
/*     */     }
/* 109 */     if (this.channelFactory != null) {
/* 110 */       throw new IllegalStateException("channelFactory set already");
/*     */     }
/*     */     
/* 113 */     this.channelFactory = channelFactory;
/* 114 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(SocketAddress localAddress) {
/* 123 */     this.localAddress = localAddress;
/* 124 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(int inetPort) {
/* 131 */     return localAddress(new InetSocketAddress(inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(String inetHost, int inetPort) {
/* 138 */     return localAddress(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(InetAddress inetHost, int inetPort) {
/* 145 */     return localAddress(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> B option(ChannelOption<T> option, T value) {
/* 154 */     if (option == null) {
/* 155 */       throw new NullPointerException("option");
/*     */     }
/* 157 */     if (value == null) {
/* 158 */       synchronized (this.options) {
/* 159 */         this.options.remove(option);
/*     */       } 
/*     */     } else {
/* 162 */       synchronized (this.options) {
/* 163 */         this.options.put(option, value);
/*     */       } 
/*     */     } 
/* 166 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> B attr(AttributeKey<T> key, T value) {
/* 174 */     if (key == null) {
/* 175 */       throw new NullPointerException("key");
/*     */     }
/* 177 */     if (value == null) {
/* 178 */       synchronized (this.attrs) {
/* 179 */         this.attrs.remove(key);
/*     */       } 
/*     */     } else {
/* 182 */       synchronized (this.attrs) {
/* 183 */         this.attrs.put(key, value);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 188 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B validate() {
/* 198 */     if (this.group == null) {
/* 199 */       throw new IllegalStateException("group not set");
/*     */     }
/* 201 */     if (this.channelFactory == null) {
/* 202 */       throw new IllegalStateException("factory not set");
/*     */     }
/* 204 */     return (B)this;
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
/*     */   public ChannelFuture register() {
/* 220 */     validate();
/* 221 */     return initAndRegister();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind() {
/* 228 */     validate();
/* 229 */     SocketAddress localAddress = this.localAddress;
/* 230 */     if (localAddress == null) {
/* 231 */       throw new IllegalStateException("localAddress not set");
/*     */     }
/* 233 */     return doBind(localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(int inetPort) {
/* 240 */     return bind(new InetSocketAddress(inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(String inetHost, int inetPort) {
/* 247 */     return bind(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(InetAddress inetHost, int inetPort) {
/* 254 */     return bind(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress localAddress) {
/* 261 */     validate();
/* 262 */     if (localAddress == null) {
/* 263 */       throw new NullPointerException("localAddress");
/*     */     }
/* 265 */     return doBind(localAddress);
/*     */   }
/*     */   
/*     */   private ChannelFuture doBind(final SocketAddress localAddress) {
/* 269 */     ChannelFuture regPromise = initAndRegister();
/* 270 */     final Channel channel = regPromise.channel();
/* 271 */     final ChannelPromise promise = channel.newPromise();
/* 272 */     if (regPromise.isDone()) {
/* 273 */       doBind0(regPromise, channel, localAddress, promise);
/*     */     } else {
/* 275 */       regPromise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) throws Exception {
/* 278 */               AbstractBootstrap.doBind0(future, channel, localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 283 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   final ChannelFuture initAndRegister() {
/* 287 */     Channel channel = (Channel)channelFactory().newChannel();
/*     */     try {
/* 289 */       init(channel);
/* 290 */     } catch (Throwable t) {
/* 291 */       channel.unsafe().closeForcibly();
/* 292 */       return channel.newFailedFuture(t);
/*     */     } 
/*     */     
/* 295 */     ChannelPromise regPromise = channel.newPromise();
/* 296 */     group().register(channel, regPromise);
/* 297 */     if (regPromise.cause() != null) {
/* 298 */       if (channel.isRegistered()) {
/* 299 */         channel.close();
/*     */       } else {
/* 301 */         channel.unsafe().closeForcibly();
/*     */       } 
/*     */     }
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
/* 314 */     return (ChannelFuture)regPromise;
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
/*     */   private static void doBind0(final ChannelFuture regFuture, final Channel channel, final SocketAddress localAddress, final ChannelPromise promise) {
/* 326 */     channel.eventLoop().execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 329 */             if (regFuture.isSuccess()) {
/* 330 */               channel.bind(localAddress, promise).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */             } else {
/* 332 */               promise.setFailure(regFuture.cause());
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B handler(ChannelHandler handler) {
/* 343 */     if (handler == null) {
/* 344 */       throw new NullPointerException("handler");
/*     */     }
/* 346 */     this.handler = handler;
/* 347 */     return (B)this;
/*     */   }
/*     */   
/*     */   final SocketAddress localAddress() {
/* 351 */     return this.localAddress;
/*     */   }
/*     */   
/*     */   final ChannelFactory<? extends C> channelFactory() {
/* 355 */     return this.channelFactory;
/*     */   }
/*     */   
/*     */   final ChannelHandler handler() {
/* 359 */     return this.handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final EventLoopGroup group() {
/* 366 */     return this.group;
/*     */   }
/*     */   
/*     */   final Map<ChannelOption<?>, Object> options() {
/* 370 */     return this.options;
/*     */   }
/*     */   
/*     */   final Map<AttributeKey<?>, Object> attrs() {
/* 374 */     return this.attrs;
/*     */   }
/*     */   AbstractBootstrap() {}
/*     */   
/*     */   public String toString() {
/* 379 */     StringBuilder buf = new StringBuilder();
/* 380 */     buf.append(getClass().getSimpleName());
/* 381 */     buf.append('(');
/* 382 */     if (this.group != null) {
/* 383 */       buf.append("group: ");
/* 384 */       buf.append(this.group.getClass().getSimpleName());
/* 385 */       buf.append(", ");
/*     */     } 
/* 387 */     if (this.channelFactory != null) {
/* 388 */       buf.append("channelFactory: ");
/* 389 */       buf.append(this.channelFactory);
/* 390 */       buf.append(", ");
/*     */     } 
/* 392 */     if (this.localAddress != null) {
/* 393 */       buf.append("localAddress: ");
/* 394 */       buf.append(this.localAddress);
/* 395 */       buf.append(", ");
/*     */     } 
/* 397 */     synchronized (this.options) {
/* 398 */       if (!this.options.isEmpty()) {
/* 399 */         buf.append("options: ");
/* 400 */         buf.append(this.options);
/* 401 */         buf.append(", ");
/*     */       } 
/*     */     } 
/* 404 */     synchronized (this.attrs) {
/* 405 */       if (!this.attrs.isEmpty()) {
/* 406 */         buf.append("attrs: ");
/* 407 */         buf.append(this.attrs);
/* 408 */         buf.append(", ");
/*     */       } 
/*     */     } 
/* 411 */     if (this.handler != null) {
/* 412 */       buf.append("handler: ");
/* 413 */       buf.append(this.handler);
/* 414 */       buf.append(", ");
/*     */     } 
/* 416 */     if (buf.charAt(buf.length() - 1) == '(') {
/* 417 */       buf.append(')');
/*     */     } else {
/* 419 */       buf.setCharAt(buf.length() - 2, ')');
/* 420 */       buf.setLength(buf.length() - 1);
/*     */     } 
/* 422 */     return buf.toString();
/*     */   }
/*     */   public abstract B clone();
/*     */   
/*     */   abstract void init(Channel paramChannel) throws Exception;
/*     */   
/*     */   private static final class BootstrapChannelFactory<T extends Channel> implements ChannelFactory<T> { BootstrapChannelFactory(Class<? extends T> clazz) {
/* 429 */       this.clazz = clazz;
/*     */     }
/*     */     private final Class<? extends T> clazz;
/*     */     
/*     */     public T newChannel() {
/*     */       try {
/* 435 */         return this.clazz.newInstance();
/* 436 */       } catch (Throwable t) {
/* 437 */         throw new ChannelException("Unable to create Channel from class " + this.clazz, t);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 443 */       return this.clazz.getSimpleName() + ".class";
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\bootstrap\AbstractBootstrap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */