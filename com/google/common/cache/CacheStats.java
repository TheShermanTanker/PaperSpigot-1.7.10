/*     */ package com.google.common.cache;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public final class CacheStats
/*     */ {
/*     */   private final long hitCount;
/*     */   private final long missCount;
/*     */   private final long loadSuccessCount;
/*     */   private final long loadExceptionCount;
/*     */   private final long totalLoadTime;
/*     */   private final long evictionCount;
/*     */   
/*     */   public CacheStats(long hitCount, long missCount, long loadSuccessCount, long loadExceptionCount, long totalLoadTime, long evictionCount) {
/*  70 */     Preconditions.checkArgument((hitCount >= 0L));
/*  71 */     Preconditions.checkArgument((missCount >= 0L));
/*  72 */     Preconditions.checkArgument((loadSuccessCount >= 0L));
/*  73 */     Preconditions.checkArgument((loadExceptionCount >= 0L));
/*  74 */     Preconditions.checkArgument((totalLoadTime >= 0L));
/*  75 */     Preconditions.checkArgument((evictionCount >= 0L));
/*     */     
/*  77 */     this.hitCount = hitCount;
/*  78 */     this.missCount = missCount;
/*  79 */     this.loadSuccessCount = loadSuccessCount;
/*  80 */     this.loadExceptionCount = loadExceptionCount;
/*  81 */     this.totalLoadTime = totalLoadTime;
/*  82 */     this.evictionCount = evictionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long requestCount() {
/*  90 */     return this.hitCount + this.missCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long hitCount() {
/*  97 */     return this.hitCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double hitRate() {
/* 106 */     long requestCount = requestCount();
/* 107 */     return (requestCount == 0L) ? 1.0D : (this.hitCount / requestCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long missCount() {
/* 117 */     return this.missCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double missRate() {
/* 130 */     long requestCount = requestCount();
/* 131 */     return (requestCount == 0L) ? 0.0D : (this.missCount / requestCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long loadCount() {
/* 140 */     return this.loadSuccessCount + this.loadExceptionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long loadSuccessCount() {
/* 151 */     return this.loadSuccessCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long loadExceptionCount() {
/* 162 */     return this.loadExceptionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double loadExceptionRate() {
/* 171 */     long totalLoadCount = this.loadSuccessCount + this.loadExceptionCount;
/* 172 */     return (totalLoadCount == 0L) ? 0.0D : (this.loadExceptionCount / totalLoadCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long totalLoadTime() {
/* 183 */     return this.totalLoadTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double averageLoadPenalty() {
/* 191 */     long totalLoadCount = this.loadSuccessCount + this.loadExceptionCount;
/* 192 */     return (totalLoadCount == 0L) ? 0.0D : (this.totalLoadTime / totalLoadCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long evictionCount() {
/* 202 */     return this.evictionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CacheStats minus(CacheStats other) {
/* 211 */     return new CacheStats(Math.max(0L, this.hitCount - other.hitCount), Math.max(0L, this.missCount - other.missCount), Math.max(0L, this.loadSuccessCount - other.loadSuccessCount), Math.max(0L, this.loadExceptionCount - other.loadExceptionCount), Math.max(0L, this.totalLoadTime - other.totalLoadTime), Math.max(0L, this.evictionCount - other.evictionCount));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 222 */     return Objects.hashCode(new Object[] { Long.valueOf(this.hitCount), Long.valueOf(this.missCount), Long.valueOf(this.loadSuccessCount), Long.valueOf(this.loadExceptionCount), Long.valueOf(this.totalLoadTime), Long.valueOf(this.evictionCount) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 228 */     if (object instanceof CacheStats) {
/* 229 */       CacheStats other = (CacheStats)object;
/* 230 */       return (this.hitCount == other.hitCount && this.missCount == other.missCount && this.loadSuccessCount == other.loadSuccessCount && this.loadExceptionCount == other.loadExceptionCount && this.totalLoadTime == other.totalLoadTime && this.evictionCount == other.evictionCount);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 242 */     return Objects.toStringHelper(this).add("hitCount", Long.valueOf(this.hitCount)).add("missCount", Long.valueOf(this.missCount)).add("loadSuccessCount", Long.valueOf(this.loadSuccessCount)).add("loadExceptionCount", Long.valueOf(this.loadExceptionCount)).add("totalLoadTime", Long.valueOf(this.totalLoadTime)).add("evictionCount", Long.valueOf(this.evictionCount)).toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\cache\CacheStats.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */