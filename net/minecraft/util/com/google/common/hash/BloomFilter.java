/*     */ package net.minecraft.util.com.google.common.hash;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.util.com.google.common.base.Objects;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.base.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class BloomFilter<T>
/*     */   implements Predicate<T>, Serializable
/*     */ {
/*     */   private final BloomFilterStrategies.BitArray bits;
/*     */   private final int numHashFunctions;
/*     */   private final Funnel<T> funnel;
/*     */   private final Strategy strategy;
/*     */   
/*     */   private BloomFilter(BloomFilterStrategies.BitArray bits, int numHashFunctions, Funnel<T> funnel, Strategy strategy) {
/* 101 */     Preconditions.checkArgument((numHashFunctions > 0), "numHashFunctions (%s) must be > 0", new Object[] { Integer.valueOf(numHashFunctions) });
/*     */     
/* 103 */     Preconditions.checkArgument((numHashFunctions <= 255), "numHashFunctions (%s) must be <= 255", new Object[] { Integer.valueOf(numHashFunctions) });
/*     */     
/* 105 */     this.bits = (BloomFilterStrategies.BitArray)Preconditions.checkNotNull(bits);
/* 106 */     this.numHashFunctions = numHashFunctions;
/* 107 */     this.funnel = (Funnel<T>)Preconditions.checkNotNull(funnel);
/* 108 */     this.strategy = (Strategy)Preconditions.checkNotNull(strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BloomFilter<T> copy() {
/* 118 */     return new BloomFilter(this.bits.copy(), this.numHashFunctions, this.funnel, this.strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mightContain(T object) {
/* 126 */     return this.strategy.mightContain(object, this.funnel, this.numHashFunctions, this.bits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean apply(T input) {
/* 136 */     return mightContain(input);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean put(T object) {
/* 152 */     return this.strategy.put(object, this.funnel, this.numHashFunctions, this.bits);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public double expectedFpp() {
/* 168 */     return Math.pow(this.bits.bitCount() / bitSize(), this.numHashFunctions);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   long bitSize() {
/* 175 */     return this.bits.bitSize();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCompatible(BloomFilter<T> that) {
/* 194 */     Preconditions.checkNotNull(that);
/* 195 */     return (this != that && this.numHashFunctions == that.numHashFunctions && bitSize() == that.bitSize() && this.strategy.equals(that.strategy) && this.funnel.equals(that.funnel));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(BloomFilter<T> that) {
/* 213 */     Preconditions.checkNotNull(that);
/* 214 */     Preconditions.checkArgument((this != that), "Cannot combine a BloomFilter with itself.");
/* 215 */     Preconditions.checkArgument((this.numHashFunctions == that.numHashFunctions), "BloomFilters must have the same number of hash functions (%s != %s)", new Object[] { Integer.valueOf(this.numHashFunctions), Integer.valueOf(that.numHashFunctions) });
/*     */ 
/*     */     
/* 218 */     Preconditions.checkArgument((bitSize() == that.bitSize()), "BloomFilters must have the same size underlying bit arrays (%s != %s)", new Object[] { Long.valueOf(bitSize()), Long.valueOf(that.bitSize()) });
/*     */ 
/*     */     
/* 221 */     Preconditions.checkArgument(this.strategy.equals(that.strategy), "BloomFilters must have equal strategies (%s != %s)", new Object[] { this.strategy, that.strategy });
/*     */ 
/*     */     
/* 224 */     Preconditions.checkArgument(this.funnel.equals(that.funnel), "BloomFilters must have equal funnels (%s != %s)", new Object[] { this.funnel, that.funnel });
/*     */ 
/*     */     
/* 227 */     this.bits.putAll(that.bits);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 232 */     if (object == this) {
/* 233 */       return true;
/*     */     }
/* 235 */     if (object instanceof BloomFilter) {
/* 236 */       BloomFilter<?> that = (BloomFilter)object;
/* 237 */       return (this.numHashFunctions == that.numHashFunctions && this.funnel.equals(that.funnel) && this.bits.equals(that.bits) && this.strategy.equals(that.strategy));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 247 */     return Objects.hashCode(new Object[] { Integer.valueOf(this.numHashFunctions), this.funnel, this.strategy, this.bits });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> BloomFilter<T> create(Funnel<T> funnel, int expectedInsertions, double fpp) {
/* 273 */     Preconditions.checkNotNull(funnel);
/* 274 */     Preconditions.checkArgument((expectedInsertions >= 0), "Expected insertions (%s) must be >= 0", new Object[] { Integer.valueOf(expectedInsertions) });
/*     */     
/* 276 */     Preconditions.checkArgument((fpp > 0.0D), "False positive probability (%s) must be > 0.0", new Object[] { Double.valueOf(fpp) });
/* 277 */     Preconditions.checkArgument((fpp < 1.0D), "False positive probability (%s) must be < 1.0", new Object[] { Double.valueOf(fpp) });
/* 278 */     if (expectedInsertions == 0) {
/* 279 */       expectedInsertions = 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     long numBits = optimalNumOfBits(expectedInsertions, fpp);
/* 288 */     int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
/*     */     try {
/* 290 */       return new BloomFilter<T>(new BloomFilterStrategies.BitArray(numBits), numHashFunctions, funnel, BloomFilterStrategies.MURMUR128_MITZ_32);
/*     */     }
/* 292 */     catch (IllegalArgumentException e) {
/* 293 */       throw new IllegalArgumentException("Could not create BloomFilter of " + numBits + " bits", e);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> BloomFilter<T> create(Funnel<T> funnel, int expectedInsertions) {
/* 314 */     return create(funnel, expectedInsertions, 0.03D);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static int optimalNumOfHashFunctions(long n, long m) {
/* 342 */     return Math.max(1, (int)Math.round((m / n) * Math.log(2.0D)));
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
/*     */   @VisibleForTesting
/*     */   static long optimalNumOfBits(long n, double p) {
/* 356 */     if (p == 0.0D) {
/* 357 */       p = Double.MIN_VALUE;
/*     */     }
/* 359 */     return (long)(-n * Math.log(p) / Math.log(2.0D) * Math.log(2.0D));
/*     */   }
/*     */   
/*     */   private Object writeReplace() {
/* 363 */     return new SerialForm<T>(this);
/*     */   }
/*     */   
/*     */   private static class SerialForm<T>
/*     */     implements Serializable
/*     */   {
/*     */     final long[] data;
/*     */     final int numHashFunctions;
/*     */     
/*     */     SerialForm(BloomFilter<T> bf) {
/* 373 */       this.data = bf.bits.data;
/* 374 */       this.numHashFunctions = bf.numHashFunctions;
/* 375 */       this.funnel = bf.funnel;
/* 376 */       this.strategy = bf.strategy;
/*     */     } final Funnel<T> funnel; final BloomFilter.Strategy strategy; private static final long serialVersionUID = 1L;
/*     */     Object readResolve() {
/* 379 */       return new BloomFilter(new BloomFilterStrategies.BitArray(this.data), this.numHashFunctions, this.funnel, this.strategy);
/*     */     }
/*     */   }
/*     */   
/*     */   static interface Strategy extends Serializable {
/*     */     <T> boolean put(T param1T, Funnel<? super T> param1Funnel, int param1Int, BloomFilterStrategies.BitArray param1BitArray);
/*     */     
/*     */     <T> boolean mightContain(T param1T, Funnel<? super T> param1Funnel, int param1Int, BloomFilterStrategies.BitArray param1BitArray);
/*     */     
/*     */     int ordinal();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\hash\BloomFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */