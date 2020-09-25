/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InternString
/*    */ {
/* 14 */   private static HashMap<String, String> map = new HashMap<String, String>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String intern(String s) {
/* 22 */     if (s == null) {
/* 23 */       return null;
/*    */     }
/*    */     
/* 26 */     synchronized (map) {
/* 27 */       String v = map.get(s);
/* 28 */       if (v != null) {
/* 29 */         return v;
/*    */       }
/* 31 */       map.put(s, s);
/* 32 */       return s;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\InternString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */