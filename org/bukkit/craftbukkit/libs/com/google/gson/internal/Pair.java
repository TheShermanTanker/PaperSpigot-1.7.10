/*    */ package org.bukkit.craftbukkit.libs.com.google.gson.internal;
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
/*    */ public final class Pair<FIRST, SECOND>
/*    */ {
/*    */   public final FIRST first;
/*    */   public final SECOND second;
/*    */   
/*    */   public Pair(FIRST first, SECOND second) {
/* 33 */     this.first = first;
/* 34 */     this.second = second;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 39 */     return 17 * ((this.first != null) ? this.first.hashCode() : 0) + 17 * ((this.second != null) ? this.second.hashCode() : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 45 */     if (!(o instanceof Pair)) {
/* 46 */       return false;
/*    */     }
/*    */     
/* 49 */     Pair<?, ?> that = (Pair<?, ?>)o;
/* 50 */     return (equal(this.first, that.first) && equal(this.second, that.second));
/*    */   }
/*    */   
/*    */   private static boolean equal(Object a, Object b) {
/* 54 */     return (a == b || (a != null && a.equals(b)));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return String.format("{%s,%s}", new Object[] { this.first, this.second });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\internal\Pair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */