/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthorNagException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final String message;
/*    */   
/*    */   public AuthorNagException(String message) {
/* 13 */     this.message = message;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 18 */     return this.message;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\AuthorNagException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */