/*    */ package net.minecraft.util.io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.HttpMessage;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponse;
/*    */ import net.minecraft.util.io.netty.util.CharsetUtil;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RtspResponseEncoder
/*    */   extends RtspObjectEncoder<HttpResponse>
/*    */ {
/* 31 */   private static final byte[] CRLF = new byte[] { 13, 10 };
/*    */ 
/*    */   
/*    */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 35 */     return msg instanceof net.minecraft.util.io.netty.handler.codec.http.FullHttpResponse;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf buf, HttpResponse response) throws Exception {
/* 41 */     encodeAscii(response.getProtocolVersion().toString(), buf);
/* 42 */     buf.writeByte(32);
/* 43 */     buf.writeBytes(String.valueOf(response.getStatus().code()).getBytes(CharsetUtil.US_ASCII));
/* 44 */     buf.writeByte(32);
/* 45 */     encodeAscii(String.valueOf(response.getStatus().reasonPhrase()), buf);
/* 46 */     buf.writeBytes(CRLF);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\rtsp\RtspResponseEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */