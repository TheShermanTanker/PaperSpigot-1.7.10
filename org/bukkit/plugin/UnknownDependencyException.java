/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnknownDependencyException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 5721389371901775895L;
/*    */   
/*    */   public UnknownDependencyException(Throwable throwable) {
/* 17 */     super(throwable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UnknownDependencyException(String message) {
/* 26 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UnknownDependencyException(Throwable throwable, String message) {
/* 37 */     super(message, throwable);
/*    */   }
/*    */   
/*    */   public UnknownDependencyException() {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\UnknownDependencyException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */