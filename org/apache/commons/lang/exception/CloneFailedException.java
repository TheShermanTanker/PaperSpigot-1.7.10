/*    */ package org.apache.commons.lang.exception;
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
/*    */ public class CloneFailedException
/*    */   extends NestableRuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 20091223L;
/*    */   
/*    */   public CloneFailedException(String message) {
/* 40 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CloneFailedException(Throwable cause) {
/* 50 */     super(cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CloneFailedException(String message, Throwable cause) {
/* 61 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang\exception\CloneFailedException.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */