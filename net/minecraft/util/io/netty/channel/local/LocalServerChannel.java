/*     */ package net.minecraft.util.io.netty.channel.local;
/*     */ 
/*     */ import java.net.SocketAddress;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
/*     */ import net.minecraft.util.io.netty.channel.AbstractServerChannel;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
/*     */ import net.minecraft.util.io.netty.channel.DefaultChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.EventLoop;
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
/*     */ public class LocalServerChannel
/*     */   extends AbstractServerChannel
/*     */ {
/*  36 */   private final ChannelConfig config = (ChannelConfig)new DefaultChannelConfig((Channel)this);
/*  37 */   private final Queue<Object> inboundBuffer = new ArrayDeque();
/*  38 */   private final Runnable shutdownHook = new Runnable()
/*     */     {
/*     */       public void run() {
/*  41 */         LocalServerChannel.this.unsafe().close(LocalServerChannel.this.unsafe().voidPromise());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private volatile int state;
/*     */   private volatile LocalAddress localAddress;
/*     */   private volatile boolean acceptInProgress;
/*     */   
/*     */   public ChannelConfig config() {
/*  51 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress localAddress() {
/*  56 */     return (LocalAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress remoteAddress() {
/*  61 */     return (LocalAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  66 */     return (this.state < 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  71 */     return (this.state == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/*  76 */     return loop instanceof net.minecraft.util.io.netty.channel.SingleThreadEventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*  81 */     return this.localAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister() throws Exception {
/*  86 */     ((SingleThreadEventExecutor)eventLoop()).addShutdownHook(this.shutdownHook);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  91 */     this.localAddress = LocalChannelRegistry.register((Channel)this, this.localAddress, localAddress);
/*  92 */     this.state = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/*  97 */     if (this.state <= 1) {
/*     */       
/*  99 */       LocalChannelRegistry.unregister(this.localAddress);
/* 100 */       this.localAddress = null;
/* 101 */       this.state = 2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 107 */     ((SingleThreadEventExecutor)eventLoop()).removeShutdownHook(this.shutdownHook);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 112 */     if (this.acceptInProgress) {
/*     */       return;
/*     */     }
/*     */     
/* 116 */     Queue<Object> inboundBuffer = this.inboundBuffer;
/* 117 */     if (inboundBuffer.isEmpty()) {
/* 118 */       this.acceptInProgress = true;
/*     */       
/*     */       return;
/*     */     } 
/* 122 */     ChannelPipeline pipeline = pipeline();
/*     */     while (true) {
/* 124 */       Object m = inboundBuffer.poll();
/* 125 */       if (m == null) {
/*     */         break;
/*     */       }
/* 128 */       pipeline.fireChannelRead(m);
/*     */     } 
/* 130 */     pipeline.fireChannelReadComplete();
/*     */   }
/*     */   
/*     */   LocalChannel serve(LocalChannel peer) {
/* 134 */     final LocalChannel child = new LocalChannel(this, peer);
/* 135 */     if (eventLoop().inEventLoop()) {
/* 136 */       serve0(child);
/*     */     } else {
/* 138 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 141 */               LocalServerChannel.this.serve0(child);
/*     */             }
/*     */           });
/*     */     } 
/* 145 */     return child;
/*     */   }
/*     */   
/*     */   private void serve0(LocalChannel child) {
/* 149 */     this.inboundBuffer.add(child);
/* 150 */     if (this.acceptInProgress) {
/* 151 */       this.acceptInProgress = false;
/* 152 */       ChannelPipeline pipeline = pipeline();
/*     */       while (true) {
/* 154 */         Object m = this.inboundBuffer.poll();
/* 155 */         if (m == null) {
/*     */           break;
/*     */         }
/* 158 */         pipeline.fireChannelRead(m);
/*     */       } 
/* 160 */       pipeline.fireChannelReadComplete();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\local\LocalServerChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */