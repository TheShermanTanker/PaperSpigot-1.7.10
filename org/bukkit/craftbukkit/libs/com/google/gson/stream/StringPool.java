/*    */ package org.bukkit.craftbukkit.libs.com.google.gson.stream;
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
/*    */ final class StringPool
/*    */ {
/* 26 */   private final String[] pool = new String[512];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String get(char[] array, int start, int length) {
/* 33 */     int hashCode = 0;
/* 34 */     for (int i = start; i < start + length; i++) {
/* 35 */       hashCode = hashCode * 31 + array[i];
/*    */     }
/*    */ 
/*    */     
/* 39 */     hashCode ^= hashCode >>> 20 ^ hashCode >>> 12;
/* 40 */     hashCode ^= hashCode >>> 7 ^ hashCode >>> 4;
/* 41 */     int index = hashCode & this.pool.length - 1;
/*    */     
/* 43 */     String pooled = this.pool[index];
/* 44 */     if (pooled == null || pooled.length() != length) {
/* 45 */       String result = new String(array, start, length);
/* 46 */       this.pool[index] = result;
/* 47 */       return result;
/*    */     } 
/*    */     
/* 50 */     for (int j = 0; j < length; j++) {
/* 51 */       if (pooled.charAt(j) != array[start + j]) {
/* 52 */         String result = new String(array, start, length);
/* 53 */         this.pool[index] = result;
/* 54 */         return result;
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     return pooled;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\stream\StringPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */