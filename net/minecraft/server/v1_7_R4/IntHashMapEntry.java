/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IntHashMapEntry
/*     */ {
/*     */   final int a;
/*     */   Object b;
/*     */   IntHashMapEntry c;
/*     */   final int d;
/*     */   
/*     */   IntHashMapEntry(int paramInt1, int paramInt2, Object paramObject, IntHashMapEntry paramIntHashMapEntry) {
/* 183 */     this.b = paramObject;
/* 184 */     this.c = paramIntHashMapEntry;
/* 185 */     this.a = paramInt2;
/* 186 */     this.d = paramInt1;
/*     */   }
/*     */   
/*     */   public final int a() {
/* 190 */     return this.a;
/*     */   }
/*     */   
/*     */   public final Object b() {
/* 194 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object paramObject) {
/* 199 */     if (!(paramObject instanceof IntHashMapEntry)) return false; 
/* 200 */     IntHashMapEntry intHashMapEntry = (IntHashMapEntry)paramObject;
/* 201 */     Integer integer1 = Integer.valueOf(a());
/* 202 */     Integer integer2 = Integer.valueOf(intHashMapEntry.a());
/* 203 */     if (integer1 == integer2 || (integer1 != null && integer1.equals(integer2))) {
/* 204 */       Object object1 = b();
/* 205 */       Object object2 = intHashMapEntry.b();
/* 206 */       if (object1 == object2 || (object1 != null && object1.equals(object2))) return true; 
/*     */     } 
/* 208 */     return false;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/* 212 */     return IntHashMap.f(this.a);
/*     */   }
/*     */   
/*     */   public final String toString() {
/* 216 */     return a() + "=" + b();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IntHashMapEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */