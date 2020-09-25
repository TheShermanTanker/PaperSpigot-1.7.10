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
/*     */ class LongHashMapEntry
/*     */ {
/*     */   final long a;
/*     */   Object b;
/*     */   LongHashMapEntry c;
/*     */   final int d;
/*     */   
/*     */   LongHashMapEntry(int paramInt, long paramLong, Object paramObject, LongHashMapEntry paramLongHashMapEntry) {
/* 182 */     this.b = paramObject;
/* 183 */     this.c = paramLongHashMapEntry;
/* 184 */     this.a = paramLong;
/* 185 */     this.d = paramInt;
/*     */   }
/*     */   
/*     */   public final long a() {
/* 189 */     return this.a;
/*     */   }
/*     */   
/*     */   public final Object b() {
/* 193 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object paramObject) {
/* 198 */     if (!(paramObject instanceof LongHashMapEntry)) return false; 
/* 199 */     LongHashMapEntry longHashMapEntry = (LongHashMapEntry)paramObject;
/* 200 */     Long long_1 = Long.valueOf(a());
/* 201 */     Long long_2 = Long.valueOf(longHashMapEntry.a());
/* 202 */     if (long_1 == long_2 || (long_1 != null && long_1.equals(long_2))) {
/* 203 */       Object object1 = b();
/* 204 */       Object object2 = longHashMapEntry.b();
/* 205 */       if (object1 == object2 || (object1 != null && object1.equals(object2))) return true; 
/*     */     } 
/* 207 */     return false;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/* 211 */     return LongHashMap.f(this.a);
/*     */   }
/*     */   
/*     */   public final String toString() {
/* 215 */     return a() + "=" + b();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\LongHashMapEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */