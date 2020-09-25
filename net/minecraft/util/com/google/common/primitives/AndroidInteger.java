/*    */ package net.minecraft.util.com.google.common.primitives;
/*    */ 
/*    */ import javax.annotation.CheckForNull;
/*    */ import net.minecraft.util.com.google.common.base.Preconditions;
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
/*    */ final class AndroidInteger
/*    */ {
/*    */   @CheckForNull
/*    */   static Integer tryParse(String string) {
/* 34 */     return tryParse(string, 10);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @CheckForNull
/*    */   static Integer tryParse(String string, int radix) {
/* 42 */     Preconditions.checkNotNull(string);
/* 43 */     Preconditions.checkArgument((radix >= 2), "Invalid radix %s, min radix is %s", new Object[] { Integer.valueOf(radix), Integer.valueOf(2) });
/*    */     
/* 45 */     Preconditions.checkArgument((radix <= 36), "Invalid radix %s, max radix is %s", new Object[] { Integer.valueOf(radix), Integer.valueOf(36) });
/*    */     
/* 47 */     int length = string.length(), i = 0;
/* 48 */     if (length == 0) {
/* 49 */       return null;
/*    */     }
/* 51 */     boolean negative = (string.charAt(i) == '-');
/* 52 */     if (negative && ++i == length) {
/* 53 */       return null;
/*    */     }
/* 55 */     return tryParse(string, i, radix, negative);
/*    */   }
/*    */ 
/*    */   
/*    */   @CheckForNull
/*    */   private static Integer tryParse(String string, int offset, int radix, boolean negative) {
/* 61 */     int max = Integer.MIN_VALUE / radix;
/* 62 */     int result = 0, length = string.length();
/* 63 */     while (offset < length) {
/* 64 */       int digit = Character.digit(string.charAt(offset++), radix);
/* 65 */       if (digit == -1) {
/* 66 */         return null;
/*    */       }
/* 68 */       if (max > result) {
/* 69 */         return null;
/*    */       }
/* 71 */       int next = result * radix - digit;
/* 72 */       if (next > result) {
/* 73 */         return null;
/*    */       }
/* 75 */       result = next;
/*    */     } 
/* 77 */     if (!negative) {
/* 78 */       result = -result;
/* 79 */       if (result < 0) {
/* 80 */         return null;
/*    */       }
/*    */     } 
/*    */     
/* 84 */     if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
/* 85 */       return null;
/*    */     }
/* 87 */     return Integer.valueOf(result);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\primitives\AndroidInteger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */