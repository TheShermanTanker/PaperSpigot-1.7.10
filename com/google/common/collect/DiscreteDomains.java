/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ @Beta
/*     */ public final class DiscreteDomains
/*     */ {
/*     */   public static DiscreteDomain<Integer> integers() {
/*  40 */     return IntegerDomain.INSTANCE;
/*     */   }
/*     */   
/*     */   private static final class IntegerDomain
/*     */     extends DiscreteDomain<Integer> implements Serializable {
/*  45 */     private static final IntegerDomain INSTANCE = new IntegerDomain();
/*     */     
/*     */     public Integer next(Integer value) {
/*  48 */       int i = value.intValue();
/*  49 */       return (i == Integer.MAX_VALUE) ? null : Integer.valueOf(i + 1);
/*     */     }
/*     */     private static final long serialVersionUID = 0L;
/*     */     public Integer previous(Integer value) {
/*  53 */       int i = value.intValue();
/*  54 */       return (i == Integer.MIN_VALUE) ? null : Integer.valueOf(i - 1);
/*     */     }
/*     */     
/*     */     public long distance(Integer start, Integer end) {
/*  58 */       return end.intValue() - start.intValue();
/*     */     }
/*     */     
/*     */     public Integer minValue() {
/*  62 */       return Integer.valueOf(-2147483648);
/*     */     }
/*     */     
/*     */     public Integer maxValue() {
/*  66 */       return Integer.valueOf(2147483647);
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  70 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DiscreteDomain<Long> longs() {
/*  80 */     return LongDomain.INSTANCE;
/*     */   }
/*     */   
/*     */   private static final class LongDomain
/*     */     extends DiscreteDomain<Long> implements Serializable {
/*  85 */     private static final LongDomain INSTANCE = new LongDomain(); private static final long serialVersionUID = 0L;
/*     */     
/*     */     public Long next(Long value) {
/*  88 */       long l = value.longValue();
/*  89 */       return (l == Long.MAX_VALUE) ? null : Long.valueOf(l + 1L);
/*     */     }
/*     */     
/*     */     public Long previous(Long value) {
/*  93 */       long l = value.longValue();
/*  94 */       return (l == Long.MIN_VALUE) ? null : Long.valueOf(l - 1L);
/*     */     }
/*     */     
/*     */     public long distance(Long start, Long end) {
/*  98 */       long result = end.longValue() - start.longValue();
/*  99 */       if (end.longValue() > start.longValue() && result < 0L) {
/* 100 */         return Long.MAX_VALUE;
/*     */       }
/* 102 */       if (end.longValue() < start.longValue() && result > 0L) {
/* 103 */         return Long.MIN_VALUE;
/*     */       }
/* 105 */       return result;
/*     */     }
/*     */     
/*     */     public Long minValue() {
/* 109 */       return Long.valueOf(Long.MIN_VALUE);
/*     */     }
/*     */     
/*     */     public Long maxValue() {
/* 113 */       return Long.valueOf(Long.MAX_VALUE);
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 117 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static DiscreteDomain<BigInteger> bigIntegers() {
/* 128 */     return BigIntegerDomain.INSTANCE;
/*     */   }
/*     */   
/*     */   private static final class BigIntegerDomain
/*     */     extends DiscreteDomain<BigInteger> implements Serializable {
/* 133 */     private static final BigIntegerDomain INSTANCE = new BigIntegerDomain();
/*     */     
/* 135 */     private static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
/*     */     
/* 137 */     private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public BigInteger next(BigInteger value) {
/* 141 */       return value.add(BigInteger.ONE);
/*     */     }
/*     */     
/*     */     public BigInteger previous(BigInteger value) {
/* 145 */       return value.subtract(BigInteger.ONE);
/*     */     }
/*     */     
/*     */     public long distance(BigInteger start, BigInteger end) {
/* 149 */       return start.subtract(end).max(MIN_LONG).min(MAX_LONG).longValue();
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 153 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\DiscreteDomains.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */