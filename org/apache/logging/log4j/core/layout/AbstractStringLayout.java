/*    */ package org.apache.logging.log4j.core.layout;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import org.apache.logging.log4j.core.LogEvent;
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
/*    */ public abstract class AbstractStringLayout
/*    */   extends AbstractLayout<String>
/*    */ {
/*    */   private final Charset charset;
/*    */   
/*    */   protected AbstractStringLayout(Charset charset) {
/* 34 */     this.charset = charset;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] toByteArray(LogEvent event) {
/* 45 */     return ((String)toSerializable(event)).getBytes(this.charset);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getContentType() {
/* 53 */     return "text/plain";
/*    */   }
/*    */   
/*    */   protected Charset getCharset() {
/* 57 */     return this.charset;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\AbstractStringLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */