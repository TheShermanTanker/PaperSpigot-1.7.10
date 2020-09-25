/*     */ package net.minecraft.util.io.netty.channel.local;
/*     */ 
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.AlreadyConnectedException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.ConnectionPendingException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
/*     */ import net.minecraft.util.io.netty.channel.AbstractChannel;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelMetadata;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.DefaultChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.EventLoop;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*     */ import net.minecraft.util.io.netty.util.concurrent.SingleThreadEventExecutor;
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
/*     */ public class LocalChannel
/*     */   extends AbstractChannel
/*     */ {
/*  45 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   private static final int MAX_READER_STACK_DEPTH = 8;
/*     */   
/*  48 */   private static final ThreadLocal<Integer> READER_STACK_DEPTH = new ThreadLocal<Integer>()
/*     */     {
/*     */       protected Integer initialValue() {
/*  51 */         return Integer.valueOf(0);
/*     */       }
/*     */     };
/*     */   
/*  55 */   private final ChannelConfig config = (ChannelConfig)new DefaultChannelConfig((Channel)this);
/*  56 */   private final Queue<Object> inboundBuffer = new ArrayDeque();
/*  57 */   private final Runnable readTask = new Runnable()
/*     */     {
/*     */       public void run() {
/*  60 */         ChannelPipeline pipeline = LocalChannel.this.pipeline();
/*     */         while (true) {
/*  62 */           Object m = LocalChannel.this.inboundBuffer.poll();
/*  63 */           if (m == null) {
/*     */             break;
/*     */           }
/*  66 */           pipeline.fireChannelRead(m);
/*     */         } 
/*  68 */         pipeline.fireChannelReadComplete();
/*     */       }
/*     */     };
/*     */   
/*  72 */   private final Runnable shutdownHook = new Runnable()
/*     */     {
/*     */       public void run() {
/*  75 */         LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidPromise());
/*     */       }
/*     */     };
/*     */   
/*     */   private volatile int state;
/*     */   private volatile LocalChannel peer;
/*     */   private volatile LocalAddress localAddress;
/*     */   private volatile LocalAddress remoteAddress;
/*     */   private volatile ChannelPromise connectPromise;
/*     */   private volatile boolean readInProgress;
/*     */   
/*     */   public LocalChannel() {
/*  87 */     super(null);
/*     */   }
/*     */   
/*     */   LocalChannel(LocalServerChannel parent, LocalChannel peer) {
/*  91 */     super((Channel)parent);
/*  92 */     this.peer = peer;
/*  93 */     this.localAddress = parent.localAddress();
/*  94 */     this.remoteAddress = peer.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  99 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig config() {
/* 104 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalServerChannel parent() {
/* 109 */     return (LocalServerChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress localAddress() {
/* 114 */     return (LocalAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress remoteAddress() {
/* 119 */     return (LocalAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 124 */     return (this.state < 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 129 */     return (this.state == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/* 134 */     return new LocalUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/* 139 */     return loop instanceof net.minecraft.util.io.netty.channel.SingleThreadEventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 144 */     return this.localAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 149 */     return this.remoteAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister() throws Exception {
/* 154 */     if (this.peer != null) {
/* 155 */       this.state = 2;
/*     */       
/* 157 */       this.peer.remoteAddress = parent().localAddress();
/* 158 */       this.peer.state = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 164 */       this.peer.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 167 */               LocalChannel.this.peer.pipeline().fireChannelActive();
/* 168 */               LocalChannel.this.peer.connectPromise.setSuccess();
/*     */             }
/*     */           });
/*     */     } 
/* 172 */     ((SingleThreadEventExecutor)eventLoop()).addShutdownHook(this.shutdownHook);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 177 */     this.localAddress = LocalChannelRegistry.register((Channel)this, this.localAddress, localAddress);
/*     */ 
/*     */     
/* 180 */     this.state = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 185 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 190 */     if (this.state <= 2) {
/*     */       
/* 192 */       if (this.localAddress != null) {
/* 193 */         if (parent() == null) {
/* 194 */           LocalChannelRegistry.unregister(this.localAddress);
/*     */         }
/* 196 */         this.localAddress = null;
/*     */       } 
/* 198 */       this.state = 3;
/*     */     } 
/*     */     
/* 201 */     final LocalChannel peer = this.peer;
/* 202 */     if (peer != null && peer.isActive()) {
/*     */ 
/*     */       
/* 205 */       EventLoop eventLoop = peer.eventLoop();
/* 206 */       if (eventLoop.inEventLoop()) {
/* 207 */         peer.unsafe().close(unsafe().voidPromise());
/*     */       } else {
/* 209 */         peer.eventLoop().execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 212 */                 peer.unsafe().close(LocalChannel.this.unsafe().voidPromise());
/*     */               }
/*     */             });
/*     */       } 
/* 216 */       this.peer = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 222 */     if (isOpen()) {
/* 223 */       unsafe().close(unsafe().voidPromise());
/*     */     }
/* 225 */     ((SingleThreadEventExecutor)eventLoop()).removeShutdownHook(this.shutdownHook);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 230 */     if (this.readInProgress) {
/*     */       return;
/*     */     }
/*     */     
/* 234 */     ChannelPipeline pipeline = pipeline();
/* 235 */     Queue<Object> inboundBuffer = this.inboundBuffer;
/* 236 */     if (inboundBuffer.isEmpty()) {
/* 237 */       this.readInProgress = true;
/*     */       
/*     */       return;
/*     */     } 
/* 241 */     Integer stackDepth = READER_STACK_DEPTH.get();
/* 242 */     if (stackDepth.intValue() < 8) {
/* 243 */       READER_STACK_DEPTH.set(Integer.valueOf(stackDepth.intValue() + 1));
/*     */       try {
/*     */         while (true) {
/* 246 */           Object received = inboundBuffer.poll();
/* 247 */           if (received == null) {
/*     */             break;
/*     */           }
/* 250 */           pipeline.fireChannelRead(received);
/*     */         } 
/* 252 */         pipeline.fireChannelReadComplete();
/*     */       } finally {
/* 254 */         READER_STACK_DEPTH.set(stackDepth);
/*     */       } 
/*     */     } else {
/* 257 */       eventLoop().execute(this.readTask);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 263 */     if (this.state < 2) {
/* 264 */       throw new NotYetConnectedException();
/*     */     }
/* 266 */     if (this.state > 2) {
/* 267 */       throw new ClosedChannelException();
/*     */     }
/*     */     
/* 270 */     final LocalChannel peer = this.peer;
/* 271 */     final ChannelPipeline peerPipeline = peer.pipeline();
/* 272 */     EventLoop peerLoop = peer.eventLoop();
/*     */     
/* 274 */     if (peerLoop == eventLoop()) {
/*     */       while (true) {
/* 276 */         Object msg = in.current();
/* 277 */         if (msg == null) {
/*     */           break;
/*     */         }
/* 280 */         peer.inboundBuffer.add(msg);
/* 281 */         ReferenceCountUtil.retain(msg);
/* 282 */         in.remove();
/*     */       } 
/* 284 */       finishPeerRead(peer, peerPipeline);
/*     */     } else {
/*     */       
/* 287 */       final Object[] msgsCopy = new Object[in.size()];
/* 288 */       for (int i = 0; i < msgsCopy.length; i++) {
/* 289 */         msgsCopy[i] = ReferenceCountUtil.retain(in.current());
/* 290 */         in.remove();
/*     */       } 
/*     */       
/* 293 */       peerLoop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 296 */               for (int i = 0; i < msgsCopy.length; i++) {
/* 297 */                 peer.inboundBuffer.add(msgsCopy[i]);
/*     */               }
/* 299 */               LocalChannel.finishPeerRead(peer, peerPipeline);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void finishPeerRead(LocalChannel peer, ChannelPipeline peerPipeline) {
/* 306 */     if (peer.readInProgress) {
/* 307 */       peer.readInProgress = false;
/*     */       while (true) {
/* 309 */         Object received = peer.inboundBuffer.poll();
/* 310 */         if (received == null) {
/*     */           break;
/*     */         }
/* 313 */         peerPipeline.fireChannelRead(received);
/*     */       } 
/* 315 */       peerPipeline.fireChannelReadComplete();
/*     */     } 
/*     */   }
/*     */   private class LocalUnsafe extends AbstractChannel.AbstractUnsafe { private LocalUnsafe() {
/* 319 */       super(LocalChannel.this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 324 */       if (!ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/* 328 */       if (LocalChannel.this.state == 2) {
/* 329 */         Exception cause = new AlreadyConnectedException();
/* 330 */         promise.setFailure(cause);
/* 331 */         LocalChannel.this.pipeline().fireExceptionCaught(cause);
/*     */         
/*     */         return;
/*     */       } 
/* 335 */       if (LocalChannel.this.connectPromise != null) {
/* 336 */         throw new ConnectionPendingException();
/*     */       }
/*     */       
/* 339 */       LocalChannel.this.connectPromise = promise;
/*     */       
/* 341 */       if (LocalChannel.this.state != 1)
/*     */       {
/* 343 */         if (localAddress == null) {
/* 344 */           localAddress = new LocalAddress((Channel)LocalChannel.this);
/*     */         }
/*     */       }
/*     */       
/* 348 */       if (localAddress != null) {
/*     */         try {
/* 350 */           LocalChannel.this.doBind(localAddress);
/* 351 */         } catch (Throwable t) {
/* 352 */           promise.setFailure(t);
/* 353 */           LocalChannel.this.pipeline().fireExceptionCaught(t);
/* 354 */           close(voidPromise());
/*     */           
/*     */           return;
/*     */         } 
/*     */       }
/* 359 */       Channel boundChannel = LocalChannelRegistry.get(remoteAddress);
/* 360 */       if (!(boundChannel instanceof LocalServerChannel)) {
/* 361 */         ChannelException channelException = new ChannelException("connection refused");
/* 362 */         promise.setFailure((Throwable)channelException);
/* 363 */         close(voidPromise());
/*     */         
/*     */         return;
/*     */       } 
/* 367 */       LocalServerChannel serverChannel = (LocalServerChannel)boundChannel;
/* 368 */       LocalChannel.this.peer = serverChannel.serve(LocalChannel.this);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\local\LocalChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */