/*     */ package net.minecraft.util.com.google.common.hash;
/*     */ 
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.math.LongMath;
/*     */ import net.minecraft.util.com.google.common.primitives.Ints;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ enum BloomFilterStrategies
/*     */   implements BloomFilter.Strategy
/*     */ {
/*  42 */   MURMUR128_MITZ_32
/*     */   {
/*     */     public <T> boolean put(T object, Funnel<? super T> funnel, int numHashFunctions, BitArray bits) {
/*  45 */       long hash64 = Hashing.murmur3_128().<T>hashObject(object, funnel).asLong();
/*  46 */       int hash1 = (int)hash64;
/*  47 */       int hash2 = (int)(hash64 >>> 32L);
/*  48 */       boolean bitsChanged = false;
/*  49 */       for (int i = 1; i <= numHashFunctions; i++) {
/*  50 */         int nextHash = hash1 + i * hash2;
/*  51 */         if (nextHash < 0) {
/*  52 */           nextHash ^= 0xFFFFFFFF;
/*     */         }
/*  54 */         bitsChanged |= bits.set(nextHash % bits.bitSize());
/*     */       } 
/*  56 */       return bitsChanged;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> boolean mightContain(T object, Funnel<? super T> funnel, int numHashFunctions, BitArray bits) {
/*  61 */       long hash64 = Hashing.murmur3_128().<T>hashObject(object, funnel).asLong();
/*  62 */       int hash1 = (int)hash64;
/*  63 */       int hash2 = (int)(hash64 >>> 32L);
/*  64 */       for (int i = 1; i <= numHashFunctions; i++) {
/*  65 */         int nextHash = hash1 + i * hash2;
/*  66 */         if (nextHash < 0) {
/*  67 */           nextHash ^= 0xFFFFFFFF;
/*     */         }
/*  69 */         if (!bits.get(nextHash % bits.bitSize())) {
/*  70 */           return false;
/*     */         }
/*     */       } 
/*  73 */       return true;
/*     */     }
/*     */   };
/*     */   
/*     */   static class BitArray
/*     */   {
/*     */     final long[] data;
/*     */     int bitCount;
/*     */     
/*     */     BitArray(long bits) {
/*  83 */       this(new long[Ints.checkedCast(LongMath.divide(bits, 64L, RoundingMode.CEILING))]);
/*     */     }
/*     */ 
/*     */     
/*     */     BitArray(long[] data) {
/*  88 */       Preconditions.checkArgument((data.length > 0), "data length is zero!");
/*  89 */       this.data = data;
/*  90 */       int bitCount = 0;
/*  91 */       for (long value : data) {
/*  92 */         bitCount += Long.bitCount(value);
/*     */       }
/*  94 */       this.bitCount = bitCount;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean set(int index) {
/*  99 */       if (!get(index)) {
/* 100 */         this.data[index >> 6] = this.data[index >> 6] | 1L << index;
/* 101 */         this.bitCount++;
/* 102 */         return true;
/*     */       } 
/* 104 */       return false;
/*     */     }
/*     */     
/*     */     boolean get(int index) {
/* 108 */       return ((this.data[index >> 6] & 1L << index) != 0L);
/*     */     }
/*     */ 
/*     */     
/*     */     int bitSize() {
/* 113 */       return this.data.length * 64;
/*     */     }
/*     */ 
/*     */     
/*     */     int bitCount() {
/* 118 */       return this.bitCount;
/*     */     }
/*     */     
/*     */     BitArray copy() {
/* 122 */       return new BitArray((long[])this.data.clone());
/*     */     }
/*     */ 
/*     */     
/*     */     void putAll(BitArray array) {
/* 127 */       Preconditions.checkArgument((this.data.length == array.data.length), "BitArrays must be of equal length (%s != %s)", new Object[] { Integer.valueOf(this.data.length), Integer.valueOf(array.data.length) });
/*     */       
/* 129 */       this.bitCount = 0;
/* 130 */       for (int i = 0; i < this.data.length; i++) {
/* 131 */         this.data[i] = this.data[i] | array.data[i];
/* 132 */         this.bitCount += Long.bitCount(this.data[i]);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 137 */       if (o instanceof BitArray) {
/* 138 */         BitArray bitArray = (BitArray)o;
/* 139 */         return Arrays.equals(this.data, bitArray.data);
/*     */       } 
/* 141 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 145 */       return Arrays.hashCode(this.data);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\hash\BloomFilterStrategies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */