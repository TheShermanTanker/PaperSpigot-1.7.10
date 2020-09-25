/*    */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageDecoder;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class WebSocketProtocolHandler
/*    */   extends MessageToMessageDecoder<WebSocketFrame>
/*    */ {
/*    */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
/* 27 */     if (frame instanceof PingWebSocketFrame) {
/* 28 */       frame.content().retain();
/* 29 */       ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content()));
/*    */       return;
/*    */     } 
/* 32 */     if (frame instanceof PongWebSocketFrame) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 37 */     out.add(frame.retain());
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 42 */     ctx.close();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */