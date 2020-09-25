/*    */ package com.avaje.ebeaninternal.util;
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
/*    */ public class CamelCaseHelper
/*    */ {
/*    */   public static String toCamelFromUnderscore(String underscore) {
/* 15 */     StringBuffer result = new StringBuffer();
/* 16 */     String[] vals = underscore.split("_");
/*    */     
/* 18 */     for (int i = 0; i < vals.length; i++) {
/* 19 */       String lower = vals[i].toLowerCase();
/* 20 */       if (i > 0) {
/* 21 */         char c = Character.toUpperCase(lower.charAt(0));
/* 22 */         result.append(c);
/* 23 */         result.append(lower.substring(1));
/*    */       } else {
/* 25 */         result.append(lower);
/*    */       } 
/*    */     } 
/*    */     
/* 29 */     return result.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninterna\\util\CamelCaseHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */