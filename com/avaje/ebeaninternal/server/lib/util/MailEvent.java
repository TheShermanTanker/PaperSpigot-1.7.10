/*    */ package com.avaje.ebeaninternal.server.lib.util;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MailEvent
/*    */ {
/*    */   Throwable error;
/*    */   MailMessage message;
/*    */   
/*    */   public MailEvent(MailMessage message, Throwable error) {
/* 41 */     this.message = message;
/* 42 */     this.error = error;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MailMessage getMailMessage() {
/* 49 */     return this.message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean wasSuccessful() {
/* 56 */     return (this.error == null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable getError() {
/* 63 */     return this.error;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\li\\util\MailEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */