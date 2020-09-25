/*     */ package net.minecraft.util.io.netty.channel.nio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.channel.AbstractChannel;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.ConnectTimeoutException;
/*     */ import net.minecraft.util.io.netty.channel.EventLoop;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public abstract class AbstractNioChannel
/*     */   extends AbstractChannel
/*     */ {
/*  43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractNioChannel.class);
/*     */ 
/*     */   
/*     */   private final SelectableChannel ch;
/*     */ 
/*     */   
/*     */   protected final int readInterestOp;
/*     */ 
/*     */   
/*     */   private volatile SelectionKey selectionKey;
/*     */ 
/*     */   
/*     */   private volatile boolean inputShutdown;
/*     */ 
/*     */   
/*     */   private ChannelPromise connectPromise;
/*     */ 
/*     */   
/*     */   private ScheduledFuture<?> connectTimeoutFuture;
/*     */   
/*     */   private SocketAddress requestedRemoteAddress;
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
/*  67 */     super(parent);
/*  68 */     this.ch = ch;
/*  69 */     this.readInterestOp = readInterestOp;
/*     */     try {
/*  71 */       ch.configureBlocking(false);
/*  72 */     } catch (IOException e) {
/*     */       try {
/*  74 */         ch.close();
/*  75 */       } catch (IOException e2) {
/*  76 */         if (logger.isWarnEnabled()) {
/*  77 */           logger.warn("Failed to close a partially initialized socket.", e2);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  82 */       throw new ChannelException("Failed to enter non-blocking mode.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  88 */     return this.ch.isOpen();
/*     */   }
/*     */ 
/*     */   
/*     */   public NioUnsafe unsafe() {
/*  93 */     return (NioUnsafe)super.unsafe();
/*     */   }
/*     */   
/*     */   protected SelectableChannel javaChannel() {
/*  97 */     return this.ch;
/*     */   }
/*     */ 
/*     */   
/*     */   public NioEventLoop eventLoop() {
/* 102 */     return (NioEventLoop)super.eventLoop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SelectionKey selectionKey() {
/* 109 */     assert this.selectionKey != null;
/* 110 */     return this.selectionKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInputShutdown() {
/* 117 */     return this.inputShutdown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setInputShutdown() {
/* 124 */     this.inputShutdown = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract class AbstractNioUnsafe
/*     */     extends AbstractChannel.AbstractUnsafe
/*     */     implements NioUnsafe
/*     */   {
/*     */     protected AbstractNioUnsafe() {
/* 149 */       super(AbstractNioChannel.this);
/*     */     }
/*     */     
/*     */     public SelectableChannel ch() {
/* 153 */       return AbstractNioChannel.this.javaChannel();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 159 */       if (!ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 164 */         if (AbstractNioChannel.this.connectPromise != null) {
/* 165 */           throw new IllegalStateException("connection attempt already made");
/*     */         }
/*     */         
/* 168 */         boolean wasActive = AbstractNioChannel.this.isActive();
/* 169 */         if (AbstractNioChannel.this.doConnect(remoteAddress, localAddress)) {
/* 170 */           promise.setSuccess();
/* 171 */           if (!wasActive && AbstractNioChannel.this.isActive()) {
/* 172 */             AbstractNioChannel.this.pipeline().fireChannelActive();
/*     */           }
/*     */         } else {
/* 175 */           AbstractNioChannel.this.connectPromise = promise;
/* 176 */           AbstractNioChannel.this.requestedRemoteAddress = remoteAddress;
/*     */ 
/*     */           
/* 179 */           int connectTimeoutMillis = AbstractNioChannel.this.config().getConnectTimeoutMillis();
/* 180 */           if (connectTimeoutMillis > 0) {
/* 181 */             AbstractNioChannel.this.connectTimeoutFuture = (ScheduledFuture<?>)AbstractNioChannel.this.eventLoop().schedule(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 184 */                     ChannelPromise connectPromise = AbstractNioChannel.this.connectPromise;
/* 185 */                     ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
/*     */                     
/* 187 */                     if (connectPromise != null && connectPromise.tryFailure((Throwable)cause)) {
/* 188 */                       AbstractNioChannel.AbstractNioUnsafe.this.close(AbstractNioChannel.AbstractNioUnsafe.this.voidPromise());
/*     */                     }
/*     */                   }
/*     */                 },  connectTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */           }
/*     */           
/* 194 */           promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 197 */                   if (future.isCancelled()) {
/* 198 */                     if (AbstractNioChannel.this.connectTimeoutFuture != null) {
/* 199 */                       AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
/*     */                     }
/* 201 */                     AbstractNioChannel.this.connectPromise = null;
/* 202 */                     AbstractNioChannel.AbstractNioUnsafe.this.close(AbstractNioChannel.AbstractNioUnsafe.this.voidPromise());
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/* 207 */       } catch (Throwable t) {
/* 208 */         if (t instanceof ConnectException) {
/* 209 */           Throwable newT = new ConnectException(t.getMessage() + ": " + remoteAddress);
/* 210 */           newT.setStackTrace(t.getStackTrace());
/* 211 */           t = newT;
/*     */         } 
/* 213 */         closeIfClosed();
/* 214 */         promise.tryFailure(t);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void finishConnect() {
/* 223 */       assert AbstractNioChannel.this.eventLoop().inEventLoop();
/* 224 */       assert AbstractNioChannel.this.connectPromise != null;
/*     */       
/*     */       try {
/* 227 */         boolean wasActive = AbstractNioChannel.this.isActive();
/* 228 */         AbstractNioChannel.this.doFinishConnect();
/* 229 */         AbstractNioChannel.this.connectPromise.setSuccess();
/* 230 */         if (!wasActive && AbstractNioChannel.this.isActive()) {
/* 231 */           AbstractNioChannel.this.pipeline().fireChannelActive();
/*     */         }
/* 233 */       } catch (Throwable t) {
/* 234 */         if (t instanceof ConnectException) {
/* 235 */           Throwable newT = new ConnectException(t.getMessage() + ": " + AbstractNioChannel.this.requestedRemoteAddress);
/* 236 */           newT.setStackTrace(t.getStackTrace());
/* 237 */           t = newT;
/*     */         } 
/*     */         
/* 240 */         AbstractNioChannel.this.connectPromise.setFailure(t);
/* 241 */         closeIfClosed();
/*     */       }
/*     */       finally {
/*     */         
/* 245 */         if (AbstractNioChannel.this.connectTimeoutFuture != null) {
/* 246 */           AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
/*     */         }
/* 248 */         AbstractNioChannel.this.connectPromise = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void flush0() {
/* 257 */       if (isFlushPending()) {
/*     */         return;
/*     */       }
/* 260 */       super.flush0();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forceFlush() {
/* 266 */       super.flush0();
/*     */     }
/*     */     
/*     */     private boolean isFlushPending() {
/* 270 */       SelectionKey selectionKey = AbstractNioChannel.this.selectionKey();
/* 271 */       return (selectionKey.isValid() && (selectionKey.interestOps() & 0x4) != 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/* 277 */     return loop instanceof NioEventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister() throws Exception {
/* 282 */     boolean selected = false;
/*     */     while (true) {
/*     */       try {
/* 285 */         this.selectionKey = javaChannel().register((eventLoop()).selector, 0, this);
/*     */         return;
/* 287 */       } catch (CancelledKeyException e) {
/* 288 */         if (!selected) {
/*     */ 
/*     */           
/* 291 */           eventLoop().selectNow();
/* 292 */           selected = true; continue;
/*     */         }  break;
/*     */       } 
/*     */     } 
/* 296 */     throw e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 304 */     eventLoop().cancel(selectionKey());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 309 */     if (this.inputShutdown) {
/*     */       return;
/*     */     }
/*     */     
/* 313 */     SelectionKey selectionKey = this.selectionKey;
/* 314 */     if (!selectionKey.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 318 */     int interestOps = selectionKey.interestOps();
/* 319 */     if ((interestOps & this.readInterestOp) == 0)
/* 320 */       selectionKey.interestOps(interestOps | this.readInterestOp); 
/*     */   }
/*     */   
/*     */   protected abstract boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2) throws Exception;
/*     */   
/*     */   protected abstract void doFinishConnect() throws Exception;
/*     */   
/*     */   public static interface NioUnsafe extends Channel.Unsafe {
/*     */     SelectableChannel ch();
/*     */     
/*     */     void finishConnect();
/*     */     
/*     */     void read();
/*     */     
/*     */     void forceFlush();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\nio\AbstractNioChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */