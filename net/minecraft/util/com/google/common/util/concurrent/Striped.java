/*     */ package net.minecraft.util.com.google.common.util.concurrent;
/*     */ 
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.base.Objects;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.base.Supplier;
/*     */ import net.minecraft.util.com.google.common.collect.Iterables;
/*     */ import net.minecraft.util.com.google.common.collect.MapMaker;
/*     */ import net.minecraft.util.com.google.common.math.IntMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class Striped<L>
/*     */ {
/*     */   private Striped() {}
/*     */   
/*     */   public Iterable<L> bulkGet(Iterable<?> keys) {
/* 134 */     Object[] array = Iterables.toArray(keys, Object.class);
/* 135 */     int[] stripes = new int[array.length]; int i;
/* 136 */     for (i = 0; i < array.length; i++) {
/* 137 */       stripes[i] = indexFor(array[i]);
/*     */     }
/* 139 */     Arrays.sort(stripes);
/* 140 */     for (i = 0; i < array.length; i++) {
/* 141 */       array[i] = getAt(stripes[i]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     List<L> asList = Arrays.asList((L[])array);
/* 162 */     return Collections.unmodifiableList(asList);
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
/*     */   public static Striped<Lock> lock(int stripes) {
/* 175 */     return new CompactStriped<Lock>(stripes, new Supplier<Lock>() {
/*     */           public Lock get() {
/* 177 */             return new Striped.PaddedLock();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Striped<Lock> lazyWeakLock(int stripes) {
/* 190 */     return new LazyStriped<Lock>(stripes, new Supplier<Lock>() {
/*     */           public Lock get() {
/* 192 */             return new ReentrantLock(false);
/*     */           }
/*     */         });
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
/*     */   public static Striped<Semaphore> semaphore(int stripes, final int permits) {
/* 206 */     return new CompactStriped<Semaphore>(stripes, new Supplier<Semaphore>() {
/*     */           public Semaphore get() {
/* 208 */             return new Striped.PaddedSemaphore(permits);
/*     */           }
/*     */         });
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
/*     */   public static Striped<Semaphore> lazyWeakSemaphore(int stripes, final int permits) {
/* 222 */     return new LazyStriped<Semaphore>(stripes, new Supplier<Semaphore>() {
/*     */           public Semaphore get() {
/* 224 */             return new Semaphore(permits, false);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Striped<ReadWriteLock> readWriteLock(int stripes) {
/* 237 */     return new CompactStriped<ReadWriteLock>(stripes, READ_WRITE_LOCK_SUPPLIER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Striped<ReadWriteLock> lazyWeakReadWriteLock(int stripes) {
/* 248 */     return new LazyStriped<ReadWriteLock>(stripes, READ_WRITE_LOCK_SUPPLIER);
/*     */   }
/*     */ 
/*     */   
/* 252 */   private static final Supplier<ReadWriteLock> READ_WRITE_LOCK_SUPPLIER = new Supplier<ReadWriteLock>()
/*     */     {
/*     */       public ReadWriteLock get() {
/* 255 */         return new ReentrantReadWriteLock();
/*     */       }
/*     */     };
/*     */   
/*     */   private static final int ALL_SET = -1;
/*     */   
/*     */   private static abstract class PowerOfTwoStriped<L> extends Striped<L> { final int mask;
/*     */     
/*     */     PowerOfTwoStriped(int stripes) {
/* 264 */       Preconditions.checkArgument((stripes > 0), "Stripes must be positive");
/* 265 */       this.mask = (stripes > 1073741824) ? -1 : (Striped.ceilToPowerOfTwo(stripes) - 1);
/*     */     }
/*     */     
/*     */     final int indexFor(Object key) {
/* 269 */       int hash = Striped.smear(key.hashCode());
/* 270 */       return hash & this.mask;
/*     */     }
/*     */     
/*     */     public final L get(Object key) {
/* 274 */       return getAt(indexFor(key));
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CompactStriped<L>
/*     */     extends PowerOfTwoStriped<L>
/*     */   {
/*     */     private final Object[] array;
/*     */ 
/*     */     
/*     */     private CompactStriped(int stripes, Supplier<L> supplier) {
/* 287 */       super(stripes);
/* 288 */       Preconditions.checkArgument((stripes <= 1073741824), "Stripes must be <= 2^30)");
/*     */       
/* 290 */       this.array = new Object[this.mask + 1];
/* 291 */       for (int i = 0; i < this.array.length; i++) {
/* 292 */         this.array[i] = supplier.get();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public L getAt(int index) {
/* 298 */       return (L)this.array[index];
/*     */     }
/*     */     
/*     */     public int size() {
/* 302 */       return this.array.length;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LazyStriped<L>
/*     */     extends PowerOfTwoStriped<L>
/*     */   {
/*     */     final ConcurrentMap<Integer, L> locks;
/*     */     
/*     */     final Supplier<L> supplier;
/*     */     
/*     */     final int size;
/*     */     
/*     */     LazyStriped(int stripes, Supplier<L> supplier) {
/* 317 */       super(stripes);
/* 318 */       this.size = (this.mask == -1) ? Integer.MAX_VALUE : (this.mask + 1);
/* 319 */       this.supplier = supplier;
/* 320 */       this.locks = (new MapMaker()).weakValues().makeMap();
/*     */     }
/*     */     
/*     */     public L getAt(int index) {
/* 324 */       if (this.size != Integer.MAX_VALUE) {
/* 325 */         Preconditions.checkElementIndex(index, size());
/*     */       }
/* 327 */       L existing = this.locks.get(Integer.valueOf(index));
/* 328 */       if (existing != null) {
/* 329 */         return existing;
/*     */       }
/* 331 */       L created = (L)this.supplier.get();
/* 332 */       existing = this.locks.putIfAbsent(Integer.valueOf(index), created);
/* 333 */       return (L)Objects.firstNonNull(existing, created);
/*     */     }
/*     */     
/*     */     public int size() {
/* 337 */       return this.size;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int ceilToPowerOfTwo(int x) {
/* 347 */     return 1 << IntMath.log2(x, RoundingMode.CEILING);
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
/*     */   private static int smear(int hashCode) {
/* 360 */     hashCode ^= hashCode >>> 20 ^ hashCode >>> 12;
/* 361 */     return hashCode ^ hashCode >>> 7 ^ hashCode >>> 4;
/*     */   }
/*     */   public abstract L get(Object paramObject);
/*     */   
/*     */   public abstract L getAt(int paramInt);
/*     */   
/*     */   abstract int indexFor(Object paramObject);
/*     */   
/*     */   public abstract int size();
/*     */   
/*     */   private static class PaddedLock extends ReentrantLock { long q1;
/*     */     
/*     */     PaddedLock() {
/* 374 */       super(false);
/*     */     }
/*     */     long q2;
/*     */     long q3; }
/*     */   
/*     */   private static class PaddedSemaphore extends Semaphore { long q1;
/*     */     long q2;
/*     */     long q3;
/*     */     
/*     */     PaddedSemaphore(int permits) {
/* 384 */       super(permits, false);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\Striped.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */