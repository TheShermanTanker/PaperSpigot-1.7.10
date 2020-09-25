/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
/*     */ import java.net.SocketAddress;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CombinedChannelDuplexHandler<I extends ChannelInboundHandler, O extends ChannelOutboundHandler>
/*     */   extends ChannelDuplexHandler
/*     */ {
/*     */   private I inboundHandler;
/*     */   private O outboundHandler;
/*     */   
/*     */   protected CombinedChannelDuplexHandler() {}
/*     */   
/*     */   public CombinedChannelDuplexHandler(I inboundHandler, O outboundHandler) {
/*  41 */     init(inboundHandler, outboundHandler);
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
/*     */   protected final void init(I inboundHandler, O outboundHandler) {
/*  53 */     validate(inboundHandler, outboundHandler);
/*  54 */     this.inboundHandler = inboundHandler;
/*  55 */     this.outboundHandler = outboundHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   private void validate(I inboundHandler, O outboundHandler) {
/*  60 */     if (this.inboundHandler != null) {
/*  61 */       throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with non-default constructor.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  66 */     if (inboundHandler == null) {
/*  67 */       throw new NullPointerException("inboundHandler");
/*     */     }
/*  69 */     if (outboundHandler == null) {
/*  70 */       throw new NullPointerException("outboundHandler");
/*     */     }
/*  72 */     if (inboundHandler instanceof ChannelOutboundHandler) {
/*  73 */       throw new IllegalArgumentException("inboundHandler must not implement " + ChannelOutboundHandler.class.getSimpleName() + " to get combined.");
/*     */     }
/*     */ 
/*     */     
/*  77 */     if (outboundHandler instanceof ChannelInboundHandler) {
/*  78 */       throw new IllegalArgumentException("outboundHandler must not implement " + ChannelInboundHandler.class.getSimpleName() + " to get combined.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final I inboundHandler() {
/*  85 */     return this.inboundHandler;
/*     */   }
/*     */   
/*     */   protected final O outboundHandler() {
/*  89 */     return this.outboundHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  94 */     if (this.inboundHandler == null) {
/*  95 */       throw new IllegalStateException("init() must be invoked before being added to a " + ChannelPipeline.class.getSimpleName() + " if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with the default constructor.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 101 */       this.inboundHandler.handlerAdded(ctx);
/*     */     } finally {
/* 103 */       this.outboundHandler.handlerAdded(ctx);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/*     */     try {
/* 110 */       this.inboundHandler.handlerRemoved(ctx);
/*     */     } finally {
/* 112 */       this.outboundHandler.handlerRemoved(ctx);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 118 */     this.inboundHandler.channelRegistered(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
/* 123 */     this.inboundHandler.channelUnregistered(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 128 */     this.inboundHandler.channelActive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 133 */     this.inboundHandler.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 138 */     this.inboundHandler.exceptionCaught(ctx, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/* 143 */     this.inboundHandler.userEventTriggered(ctx, evt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 148 */     this.inboundHandler.channelRead(ctx, msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 153 */     this.inboundHandler.channelReadComplete(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 160 */     this.outboundHandler.bind(ctx, localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 168 */     this.outboundHandler.connect(ctx, remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 173 */     this.outboundHandler.disconnect(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 178 */     this.outboundHandler.close(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 183 */     this.outboundHandler.deregister(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 188 */     this.outboundHandler.read(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 193 */     this.outboundHandler.write(ctx, msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 198 */     this.outboundHandler.flush(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
/* 203 */     this.inboundHandler.channelWritabilityChanged(ctx);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\CombinedChannelDuplexHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */