/*    */ package net.minecraft.util.io.netty.handler.codec.http;
/*    */ 
/*    */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*    */ 
/*    */ 
/*    */ public class DefaultHttpResponse
/*    */   extends DefaultHttpMessage
/*    */   implements HttpResponse
/*    */ {
/*    */   private HttpResponseStatus status;
/*    */   
/*    */   public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status) {
/* 34 */     super(version);
/* 35 */     if (status == null) {
/* 36 */       throw new NullPointerException("status");
/*    */     }
/* 38 */     this.status = status;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpResponseStatus getStatus() {
/* 43 */     return this.status;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpResponse setStatus(HttpResponseStatus status) {
/* 48 */     if (status == null) {
/* 49 */       throw new NullPointerException("status");
/*    */     }
/* 51 */     this.status = status;
/* 52 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpResponse setProtocolVersion(HttpVersion version) {
/* 57 */     super.setProtocolVersion(version);
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     StringBuilder buf = new StringBuilder();
/* 64 */     buf.append(getClass().getSimpleName());
/* 65 */     buf.append("(decodeResult: ");
/* 66 */     buf.append(getDecoderResult());
/* 67 */     buf.append(')');
/* 68 */     buf.append(StringUtil.NEWLINE);
/* 69 */     buf.append(getProtocolVersion().text());
/* 70 */     buf.append(' ');
/* 71 */     buf.append(getStatus().toString());
/* 72 */     buf.append(StringUtil.NEWLINE);
/* 73 */     appendHeaders(buf);
/*    */ 
/*    */     
/* 76 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/* 77 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\DefaultHttpResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */