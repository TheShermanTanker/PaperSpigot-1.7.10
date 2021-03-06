/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ public class SplitName
/*    */ {
/*    */   public static String add(String prefix, String name) {
/*  6 */     if (prefix != null) {
/*  7 */       return prefix + "." + name;
/*    */     }
/*  9 */     return name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int count(char c, String name) {
/* 18 */     int count = 0;
/* 19 */     for (int i = 0; i < name.length(); i++) {
/* 20 */       if (c == name.charAt(i)) {
/* 21 */         count++;
/*    */       }
/*    */     } 
/* 24 */     return count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String parent(String name) {
/* 31 */     if (name == null) {
/* 32 */       return null;
/*    */     }
/* 34 */     String[] s = split(name, true);
/* 35 */     return s[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] split(String name) {
/* 40 */     return split(name, true);
/*    */   }
/*    */   
/*    */   public static String[] splitBegin(String name) {
/* 44 */     return split(name, false);
/*    */   }
/*    */ 
/*    */   
/*    */   private static String[] split(String name, boolean last) {
/* 49 */     int pos = last ? name.lastIndexOf('.') : name.indexOf('.');
/* 50 */     if (pos == -1) {
/* 51 */       if (last) {
/* 52 */         return new String[] { null, name };
/*    */       }
/* 54 */       return new String[] { name, null };
/*    */     } 
/*    */     
/* 57 */     String s0 = name.substring(0, pos);
/* 58 */     String s1 = name.substring(pos + 1);
/* 59 */     return new String[] { s0, s1 };
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\SplitName.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */