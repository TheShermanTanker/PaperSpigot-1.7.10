/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ public final class Charsets
/*    */ {
/* 31 */   public static final Charset UTF_8 = Charset.forName("UTF-8");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Charset getSupportedCharset(String charsetName) {
/* 42 */     return getSupportedCharset(charsetName, Charset.defaultCharset());
/*    */   }
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
/*    */   public static Charset getSupportedCharset(String charsetName, Charset defaultCharset) {
/* 56 */     Charset charset = null;
/* 57 */     if (charsetName != null && 
/* 58 */       Charset.isSupported(charsetName)) {
/* 59 */       charset = Charset.forName(charsetName);
/*    */     }
/*    */     
/* 62 */     if (charset == null) {
/* 63 */       charset = defaultCharset;
/* 64 */       if (charsetName != null) {
/* 65 */         StatusLogger.getLogger().error("Charset " + charsetName + " is not supported for layout, using " + charset.displayName());
/*    */       }
/*    */     } 
/*    */     
/* 69 */     return charset;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Charsets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */