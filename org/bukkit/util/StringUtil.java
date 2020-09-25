/*    */ package org.bukkit.util;
/*    */ 
/*    */ import org.apache.commons.lang.Validate;
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
/*    */ public class StringUtil
/*    */ {
/*    */   public static <T extends java.util.Collection<? super String>> T copyPartialMatches(String token, Iterable<String> originals, T collection) throws UnsupportedOperationException, IllegalArgumentException {
/* 25 */     Validate.notNull(token, "Search token cannot be null");
/* 26 */     Validate.notNull(collection, "Collection cannot be null");
/* 27 */     Validate.notNull(originals, "Originals cannot be null");
/*    */     
/* 29 */     for (String string : originals) {
/* 30 */       if (startsWithIgnoreCase(string, token)) {
/* 31 */         collection.add(string);
/*    */       }
/*    */     } 
/*    */     
/* 35 */     return collection;
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
/*    */   
/*    */   public static boolean startsWithIgnoreCase(String string, String prefix) throws IllegalArgumentException, NullPointerException {
/* 51 */     Validate.notNull(string, "Cannot check a null string for a match");
/* 52 */     if (string.length() < prefix.length()) {
/* 53 */       return false;
/*    */     }
/* 55 */     return string.regionMatches(true, 0, prefix, 0, prefix.length());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\StringUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */