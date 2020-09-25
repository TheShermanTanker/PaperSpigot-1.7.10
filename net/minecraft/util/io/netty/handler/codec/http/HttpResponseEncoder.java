/*    */ package net.minecraft.util.io.netty.handler.codec.http;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
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
/*    */ public class HttpResponseEncoder
/*    */   extends HttpObjectEncoder<HttpResponse>
/*    */ {
/* 28 */   private static final byte[] CRLF = new byte[] { 13, 10 };
/*    */ 
/*    */   
/*    */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 32 */     return (super.acceptOutboundMessage(msg) && !(msg instanceof HttpRequest));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf buf, HttpResponse response) throws Exception {
/* 37 */     encodeAscii(response.getProtocolVersion().toString(), buf);
/* 38 */     buf.writeByte(32);
/* 39 */     encodeAscii(String.valueOf(response.getStatus().code()), buf);
/* 40 */     buf.writeByte(32);
/* 41 */     encodeAscii(String.valueOf(response.getStatus().reasonPhrase()), buf);
/* 42 */     buf.writeBytes(CRLF);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpResponseEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */