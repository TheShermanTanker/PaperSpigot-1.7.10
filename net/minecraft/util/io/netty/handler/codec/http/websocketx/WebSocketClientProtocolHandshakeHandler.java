/*    */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*    */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpResponse;
/*    */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*    */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class WebSocketClientProtocolHandshakeHandler
/*    */   extends ChannelInboundHandlerAdapter
/*    */ {
/*    */   private final WebSocketClientHandshaker handshaker;
/*    */   
/*    */   public WebSocketClientProtocolHandshakeHandler(WebSocketClientHandshaker handshaker) {
/* 28 */     this.handshaker = handshaker;
/*    */   }
/*    */ 
/*    */   
/*    */   public void channelActive(final ChannelHandlerContext ctx) throws Exception {
/* 33 */     super.channelActive(ctx);
/* 34 */     this.handshaker.handshake(ctx.channel()).addListener((GenericFutureListener)new ChannelFutureListener()
/*    */         {
/*    */           public void operationComplete(ChannelFuture future) throws Exception {
/* 37 */             if (!future.isSuccess()) {
/* 38 */               ctx.fireExceptionCaught(future.cause());
/*    */             } else {
/* 40 */               ctx.fireUserEventTriggered(WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_ISSUED);
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 49 */     if (!(msg instanceof FullHttpResponse)) {
/* 50 */       ctx.fireChannelRead(msg);
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     if (!this.handshaker.isHandshakeComplete()) {
/* 55 */       this.handshaker.finishHandshake(ctx.channel(), (FullHttpResponse)msg);
/* 56 */       ctx.fireUserEventTriggered(WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE);
/*    */       
/* 58 */       ctx.pipeline().remove((ChannelHandler)this);
/*    */       return;
/*    */     } 
/* 61 */     throw new IllegalStateException("WebSocketClientHandshaker should have been non finished yet");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketClientProtocolHandshakeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */