/*    */ package net.minecraft.util.io.netty.handler.codec.http;
/*    */ 
/*    */ import java.util.Map;
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
/*    */ public abstract class DefaultHttpMessage
/*    */   extends DefaultHttpObject
/*    */   implements HttpMessage
/*    */ {
/*    */   private HttpVersion version;
/* 28 */   private final HttpHeaders headers = new DefaultHttpHeaders();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected DefaultHttpMessage(HttpVersion version) {
/* 34 */     if (version == null) {
/* 35 */       throw new NullPointerException("version");
/*    */     }
/* 37 */     this.version = version;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpHeaders headers() {
/* 42 */     return this.headers;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpVersion getProtocolVersion() {
/* 47 */     return this.version;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder buf = new StringBuilder();
/* 53 */     buf.append(getClass().getSimpleName());
/* 54 */     buf.append("(version: ");
/* 55 */     buf.append(getProtocolVersion().text());
/* 56 */     buf.append(", keepAlive: ");
/* 57 */     buf.append(HttpHeaders.isKeepAlive(this));
/* 58 */     buf.append(')');
/* 59 */     buf.append(StringUtil.NEWLINE);
/* 60 */     appendHeaders(buf);
/*    */ 
/*    */     
/* 63 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/* 64 */     return buf.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpMessage setProtocolVersion(HttpVersion version) {
/* 69 */     if (version == null) {
/* 70 */       throw new NullPointerException("version");
/*    */     }
/* 72 */     this.version = version;
/* 73 */     return this;
/*    */   }
/*    */   
/*    */   void appendHeaders(StringBuilder buf) {
/* 77 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)headers()) {
/* 78 */       buf.append(e.getKey());
/* 79 */       buf.append(": ");
/* 80 */       buf.append(e.getValue());
/* 81 */       buf.append(StringUtil.NEWLINE);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\DefaultHttpMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */