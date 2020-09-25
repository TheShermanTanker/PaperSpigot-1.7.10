/*    */ package org.spigotmc;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValidateUtils
/*    */ {
/*    */   public static String limit(String str, int limit) {
/*  8 */     if (str.length() > limit)
/*    */     {
/* 10 */       return str.substring(0, limit);
/*    */     }
/* 12 */     return str;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\ValidateUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */