/*    */ package org.bukkit.util;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ 
/*    */ public class Java15Compat
/*    */ {
/*    */   public static <T> T[] Arrays_copyOfRange(T[] original, int start, int end) {
/*  8 */     if (original.length >= start && 0 <= start) {
/*  9 */       if (start <= end) {
/* 10 */         int length = end - start;
/* 11 */         int copyLength = Math.min(length, original.length - start);
/* 12 */         T[] copy = (T[])Array.newInstance(original.getClass().getComponentType(), length);
/*    */         
/* 14 */         System.arraycopy(original, start, copy, 0, copyLength);
/* 15 */         return copy;
/*    */       } 
/* 17 */       throw new IllegalArgumentException();
/*    */     } 
/* 19 */     throw new ArrayIndexOutOfBoundsException();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\Java15Compat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */