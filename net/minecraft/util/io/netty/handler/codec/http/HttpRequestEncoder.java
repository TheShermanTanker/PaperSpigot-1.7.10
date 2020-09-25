/*    */ package net.minecraft.util.io.netty.handler.codec.http;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
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
/*    */ public class HttpRequestEncoder
/*    */   extends HttpObjectEncoder<HttpRequest>
/*    */ {
/*    */   private static final char SLASH = '/';
/* 29 */   private static final byte[] CRLF = new byte[] { 13, 10 };
/*    */ 
/*    */   
/*    */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 33 */     return (super.acceptOutboundMessage(msg) && !(msg instanceof HttpResponse));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf buf, HttpRequest request) throws Exception {
/* 38 */     encodeAscii(request.getMethod().toString(), buf);
/* 39 */     buf.writeByte(32);
/*    */ 
/*    */ 
/*    */     
/* 43 */     String uri = request.getUri();
/*    */     
/* 45 */     if (uri.length() == 0) {
/* 46 */       uri = uri + '/';
/*    */     } else {
/* 48 */       int start = uri.indexOf("://");
/* 49 */       if (start != -1 && uri.charAt(0) != '/') {
/* 50 */         int startIndex = start + 3;
/* 51 */         if (uri.lastIndexOf('/') <= startIndex) {
/* 52 */           uri = uri + '/';
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     buf.writeBytes(uri.getBytes(CharsetUtil.UTF_8));
/*    */     
/* 59 */     buf.writeByte(32);
/* 60 */     encodeAscii(request.getProtocolVersion().toString(), buf);
/* 61 */     buf.writeBytes(CRLF);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpRequestEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */