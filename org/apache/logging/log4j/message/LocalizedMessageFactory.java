/*    */ package org.apache.logging.log4j.message;
/*    */ 
/*    */ import java.util.ResourceBundle;
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
/*    */ public class LocalizedMessageFactory
/*    */   extends AbstractMessageFactory
/*    */ {
/*    */   private final ResourceBundle bundle;
/*    */   private final String bundleId;
/*    */   
/*    */   public LocalizedMessageFactory(ResourceBundle bundle) {
/* 33 */     this.bundle = bundle;
/* 34 */     this.bundleId = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public LocalizedMessageFactory(String bundleId) {
/* 39 */     this.bundle = null;
/* 40 */     this.bundleId = bundleId;
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
/*    */   
/*    */   public Message newMessage(String message, Object... params) {
/* 55 */     if (this.bundle == null) {
/* 56 */       return new LocalizedMessage(this.bundleId, message, params);
/*    */     }
/* 58 */     return new LocalizedMessage(this.bundle, message, params);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\LocalizedMessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */