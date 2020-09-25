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
/*    */ 
/*    */ public class DefaultHttpRequest
/*    */   extends DefaultHttpMessage
/*    */   implements HttpRequest
/*    */ {
/*    */   private HttpMethod method;
/*    */   private String uri;
/*    */   
/*    */   public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri) {
/* 36 */     super(httpVersion);
/* 37 */     if (method == null) {
/* 38 */       throw new NullPointerException("getMethod");
/*    */     }
/* 40 */     if (uri == null) {
/* 41 */       throw new NullPointerException("getUri");
/*    */     }
/* 43 */     this.method = method;
/* 44 */     this.uri = uri;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpMethod getMethod() {
/* 49 */     return this.method;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUri() {
/* 54 */     return this.uri;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpRequest setMethod(HttpMethod method) {
/* 59 */     if (method == null) {
/* 60 */       throw new NullPointerException("method");
/*    */     }
/* 62 */     this.method = method;
/* 63 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpRequest setUri(String uri) {
/* 68 */     if (uri == null) {
/* 69 */       throw new NullPointerException("method");
/*    */     }
/* 71 */     this.uri = uri;
/* 72 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpRequest setProtocolVersion(HttpVersion version) {
/* 77 */     super.setProtocolVersion(version);
/* 78 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 83 */     StringBuilder buf = new StringBuilder();
/* 84 */     buf.append(getClass().getSimpleName());
/* 85 */     buf.append(", decodeResult: ");
/* 86 */     buf.append(getDecoderResult());
/* 87 */     buf.append(')');
/* 88 */     buf.append(StringUtil.NEWLINE);
/* 89 */     buf.append(getMethod().toString());
/* 90 */     buf.append(' ');
/* 91 */     buf.append(getUri());
/* 92 */     buf.append(' ');
/* 93 */     buf.append(getProtocolVersion().text());
/* 94 */     buf.append(StringUtil.NEWLINE);
/* 95 */     appendHeaders(buf);
/*    */ 
/*    */     
/* 98 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/* 99 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\DefaultHttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */