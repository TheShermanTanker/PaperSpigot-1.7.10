/*     */ package com.google.common.cache;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Ascii;
/*     */ import com.google.common.base.Equivalence;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.base.Ticker;
/*     */ import com.google.common.collect.ForwardingConcurrentMap;
/*     */ import com.google.common.util.concurrent.ExecutionError;
/*     */ import com.google.common.util.concurrent.UncheckedExecutionException;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.CheckReturnValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class CacheBuilder<K, V>
/*     */ {
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 16;
/*     */   private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
/*     */   private static final int DEFAULT_EXPIRATION_NANOS = 0;
/*     */   
/* 134 */   static final Supplier<? extends AbstractCache.StatsCounter> DEFAULT_STATS_COUNTER = Suppliers.ofInstance(new AbstractCache.StatsCounter()
/*     */       {
/*     */         public void recordHit() {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void recordLoadSuccess(long loadTime) {}
/*     */ 
/*     */         
/*     */         public void recordLoadException(long loadTime) {}
/*     */ 
/*     */         
/*     */         public void recordConcurrentMiss() {}
/*     */ 
/*     */         
/*     */         public void recordEviction() {}
/*     */ 
/*     */         
/*     */         public CacheStats snapshot() {
/* 153 */           return CacheBuilder.EMPTY_STATS;
/*     */         }
/*     */       });
/* 156 */   static final CacheStats EMPTY_STATS = new CacheStats(0L, 0L, 0L, 0L, 0L, 0L);
/*     */   
/* 158 */   static final Supplier<AbstractCache.SimpleStatsCounter> CACHE_STATS_COUNTER = new Supplier<AbstractCache.SimpleStatsCounter>()
/*     */     {
/*     */       public AbstractCache.SimpleStatsCounter get()
/*     */       {
/* 162 */         return new AbstractCache.SimpleStatsCounter();
/*     */       }
/*     */     };
/*     */   static final int UNSET_INT = -1;
/*     */   
/* 167 */   enum NullListener implements RemovalListener<Object, Object> { INSTANCE;
/*     */ 
/*     */ 
/*     */     
/*     */     public void onRemoval(RemovalNotification<Object, Object> notification) {} }
/*     */ 
/*     */ 
/*     */   
/* 175 */   int initialCapacity = -1;
/* 176 */   int concurrencyLevel = -1;
/* 177 */   int maximumSize = -1;
/*     */   
/*     */   CustomConcurrentHashMap.Strength keyStrength;
/*     */   
/*     */   CustomConcurrentHashMap.Strength valueStrength;
/* 182 */   long expireAfterWriteNanos = -1L;
/* 183 */   long expireAfterAccessNanos = -1L;
/*     */ 
/*     */   
/*     */   RemovalCause nullRemovalCause;
/*     */ 
/*     */   
/*     */   Equivalence<Object> keyEquivalence;
/*     */ 
/*     */   
/*     */   Equivalence<Object> valueEquivalence;
/*     */ 
/*     */   
/*     */   RemovalListener<? super K, ? super V> removalListener;
/*     */ 
/*     */   
/*     */   Ticker ticker;
/*     */ 
/*     */   
/*     */   public static CacheBuilder<Object, Object> newBuilder() {
/* 202 */     return new CacheBuilder<Object, Object>();
/*     */   }
/*     */   
/*     */   private boolean useNullCache() {
/* 206 */     return (this.nullRemovalCause == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CacheBuilder<K, V> keyEquivalence(Equivalence<Object> equivalence) {
/* 216 */     Preconditions.checkState((this.keyEquivalence == null), "key equivalence was already set to %s", new Object[] { this.keyEquivalence });
/* 217 */     this.keyEquivalence = (Equivalence<Object>)Preconditions.checkNotNull(equivalence);
/* 218 */     return this;
/*     */   }
/*     */   
/*     */   Equivalence<Object> getKeyEquivalence() {
/* 222 */     return (Equivalence<Object>)Objects.firstNonNull(this.keyEquivalence, getKeyStrength().defaultEquivalence());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CacheBuilder<K, V> valueEquivalence(Equivalence<Object> equivalence) {
/* 233 */     Preconditions.checkState((this.valueEquivalence == null), "value equivalence was already set to %s", new Object[] { this.valueEquivalence });
/*     */     
/* 235 */     this.valueEquivalence = (Equivalence<Object>)Preconditions.checkNotNull(equivalence);
/* 236 */     return this;
/*     */   }
/*     */   
/*     */   Equivalence<Object> getValueEquivalence() {
/* 240 */     return (Equivalence<Object>)Objects.firstNonNull(this.valueEquivalence, getValueStrength().defaultEquivalence());
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
/*     */   public CacheBuilder<K, V> initialCapacity(int initialCapacity) {
/* 254 */     Preconditions.checkState((this.initialCapacity == -1), "initial capacity was already set to %s", new Object[] { Integer.valueOf(this.initialCapacity) });
/*     */     
/* 256 */     Preconditions.checkArgument((initialCapacity >= 0));
/* 257 */     this.initialCapacity = initialCapacity;
/* 258 */     return this;
/*     */   }
/*     */   
/*     */   int getInitialCapacity() {
/* 262 */     return (this.initialCapacity == -1) ? 16 : this.initialCapacity;
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
/*     */   public CacheBuilder<K, V> concurrencyLevel(int concurrencyLevel) {
/* 284 */     Preconditions.checkState((this.concurrencyLevel == -1), "concurrency level was already set to %s", new Object[] { Integer.valueOf(this.concurrencyLevel) });
/*     */     
/* 286 */     Preconditions.checkArgument((concurrencyLevel > 0));
/* 287 */     this.concurrencyLevel = concurrencyLevel;
/* 288 */     return this;
/*     */   }
/*     */   
/*     */   int getConcurrencyLevel() {
/* 292 */     return (this.concurrencyLevel == -1) ? 4 : this.concurrencyLevel;
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
/*     */   public CacheBuilder<K, V> maximumSize(int size) {
/* 311 */     Preconditions.checkState((this.maximumSize == -1), "maximum size was already set to %s", new Object[] { Integer.valueOf(this.maximumSize) });
/*     */     
/* 313 */     Preconditions.checkArgument((size >= 0), "maximum size must not be negative");
/* 314 */     this.maximumSize = size;
/* 315 */     if (this.maximumSize == 0)
/*     */     {
/* 317 */       this.nullRemovalCause = RemovalCause.SIZE;
/*     */     }
/* 319 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CacheBuilder<K, V> strongKeys() {
/* 328 */     return setKeyStrength(CustomConcurrentHashMap.Strength.STRONG);
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
/*     */   public CacheBuilder<K, V> weakKeys() {
/* 345 */     return setKeyStrength(CustomConcurrentHashMap.Strength.WEAK);
/*     */   }
/*     */   
/*     */   CacheBuilder<K, V> setKeyStrength(CustomConcurrentHashMap.Strength strength) {
/* 349 */     Preconditions.checkState((this.keyStrength == null), "Key strength was already set to %s", new Object[] { this.keyStrength });
/* 350 */     this.keyStrength = (CustomConcurrentHashMap.Strength)Preconditions.checkNotNull(strength);
/* 351 */     return this;
/*     */   }
/*     */   
/*     */   CustomConcurrentHashMap.Strength getKeyStrength() {
/* 355 */     return (CustomConcurrentHashMap.Strength)Objects.firstNonNull(this.keyStrength, CustomConcurrentHashMap.Strength.STRONG);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CacheBuilder<K, V> strongValues() {
/* 364 */     return setValueStrength(CustomConcurrentHashMap.Strength.STRONG);
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
/*     */   public CacheBuilder<K, V> weakValues() {
/* 384 */     return setValueStrength(CustomConcurrentHashMap.Strength.WEAK);
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
/*     */   public CacheBuilder<K, V> softValues() {
/* 407 */     return setValueStrength(CustomConcurrentHashMap.Strength.SOFT);
/*     */   }
/*     */   
/*     */   CacheBuilder<K, V> setValueStrength(CustomConcurrentHashMap.Strength strength) {
/* 411 */     Preconditions.checkState((this.valueStrength == null), "Value strength was already set to %s", new Object[] { this.valueStrength });
/* 412 */     this.valueStrength = (CustomConcurrentHashMap.Strength)Preconditions.checkNotNull(strength);
/* 413 */     return this;
/*     */   }
/*     */   
/*     */   CustomConcurrentHashMap.Strength getValueStrength() {
/* 417 */     return (CustomConcurrentHashMap.Strength)Objects.firstNonNull(this.valueStrength, CustomConcurrentHashMap.Strength.STRONG);
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
/*     */   public CacheBuilder<K, V> expireAfterWrite(long duration, TimeUnit unit) {
/* 439 */     checkExpiration(duration, unit);
/* 440 */     this.expireAfterWriteNanos = unit.toNanos(duration);
/* 441 */     if (duration == 0L && this.nullRemovalCause == null)
/*     */     {
/* 443 */       this.nullRemovalCause = RemovalCause.EXPIRED;
/*     */     }
/* 445 */     return this;
/*     */   }
/*     */   
/*     */   private void checkExpiration(long duration, TimeUnit unit) {
/* 449 */     Preconditions.checkState((this.expireAfterWriteNanos == -1L), "expireAfterWrite was already set to %s ns", new Object[] { Long.valueOf(this.expireAfterWriteNanos) });
/*     */     
/* 451 */     Preconditions.checkState((this.expireAfterAccessNanos == -1L), "expireAfterAccess was already set to %s ns", new Object[] { Long.valueOf(this.expireAfterAccessNanos) });
/*     */     
/* 453 */     Preconditions.checkArgument((duration >= 0L), "duration cannot be negative: %s %s", new Object[] { Long.valueOf(duration), unit });
/*     */   }
/*     */   
/*     */   long getExpireAfterWriteNanos() {
/* 457 */     return (this.expireAfterWriteNanos == -1L) ? 0L : this.expireAfterWriteNanos;
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
/*     */   public CacheBuilder<K, V> expireAfterAccess(long duration, TimeUnit unit) {
/* 481 */     checkExpiration(duration, unit);
/* 482 */     this.expireAfterAccessNanos = unit.toNanos(duration);
/* 483 */     if (duration == 0L && this.nullRemovalCause == null)
/*     */     {
/* 485 */       this.nullRemovalCause = RemovalCause.EXPIRED;
/*     */     }
/* 487 */     return this;
/*     */   }
/*     */   
/*     */   long getExpireAfterAccessNanos() {
/* 491 */     return (this.expireAfterAccessNanos == -1L) ? 0L : this.expireAfterAccessNanos;
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
/*     */   public CacheBuilder<K, V> ticker(Ticker ticker) {
/* 505 */     Preconditions.checkState((this.ticker == null));
/* 506 */     this.ticker = (Ticker)Preconditions.checkNotNull(ticker);
/* 507 */     return this;
/*     */   }
/*     */   
/*     */   Ticker getTicker() {
/* 511 */     return (Ticker)Objects.firstNonNull(this.ticker, Ticker.systemTicker());
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
/*     */ 
/*     */   
/*     */   @CheckReturnValue
/*     */   public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> listener) {
/* 541 */     Preconditions.checkState((this.removalListener == null));
/*     */ 
/*     */ 
/*     */     
/* 545 */     CacheBuilder<K1, V1> me = this;
/* 546 */     me.removalListener = (RemovalListener<? super K, ? super V>)Preconditions.checkNotNull(listener);
/* 547 */     return me;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   <K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener() {
/* 553 */     return (RemovalListener<K1, V1>)Objects.firstNonNull(this.removalListener, NullListener.INSTANCE);
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
/*     */   public <K1 extends K, V1 extends V> Cache<K1, V1> build(CacheLoader<? super K1, V1> loader) {
/* 569 */     return useNullCache() ? new ComputingCache<K1, V1>(this, (Supplier)CACHE_STATS_COUNTER, loader) : new NullCache<K1, V1>(this, (Supplier)CACHE_STATS_COUNTER, loader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 580 */     Objects.ToStringHelper s = Objects.toStringHelper(this);
/* 581 */     if (this.initialCapacity != -1) {
/* 582 */       s.add("initialCapacity", Integer.valueOf(this.initialCapacity));
/*     */     }
/* 584 */     if (this.concurrencyLevel != -1) {
/* 585 */       s.add("concurrencyLevel", Integer.valueOf(this.concurrencyLevel));
/*     */     }
/* 587 */     if (this.maximumSize != -1) {
/* 588 */       s.add("maximumSize", Integer.valueOf(this.maximumSize));
/*     */     }
/* 590 */     if (this.expireAfterWriteNanos != -1L) {
/* 591 */       s.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
/*     */     }
/* 593 */     if (this.expireAfterAccessNanos != -1L) {
/* 594 */       s.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
/*     */     }
/* 596 */     if (this.keyStrength != null) {
/* 597 */       s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
/*     */     }
/* 599 */     if (this.valueStrength != null) {
/* 600 */       s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
/*     */     }
/* 602 */     if (this.keyEquivalence != null) {
/* 603 */       s.addValue("keyEquivalence");
/*     */     }
/* 605 */     if (this.valueEquivalence != null) {
/* 606 */       s.addValue("valueEquivalence");
/*     */     }
/* 608 */     if (this.removalListener != null) {
/* 609 */       s.addValue("removalListener");
/*     */     }
/* 611 */     return s.toString();
/*     */   }
/*     */   
/*     */   static class NullConcurrentMap<K, V>
/*     */     extends AbstractMap<K, V>
/*     */     implements ConcurrentMap<K, V>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */     private final RemovalListener<K, V> removalListener;
/*     */     private final RemovalCause removalCause;
/*     */     
/*     */     NullConcurrentMap(CacheBuilder<? super K, ? super V> builder) {
/* 623 */       this.removalListener = builder.getRemovalListener();
/* 624 */       this.removalCause = builder.nullRemovalCause;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsKey(@Nullable Object key) {
/* 631 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(@Nullable Object value) {
/* 636 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(@Nullable Object key) {
/* 641 */       return null;
/*     */     }
/*     */     
/*     */     void notifyRemoval(K key, V value) {
/* 645 */       RemovalNotification<K, V> notification = new RemovalNotification<K, V>(key, value, this.removalCause);
/*     */       
/* 647 */       this.removalListener.onRemoval(notification);
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(K key, V value) {
/* 652 */       Preconditions.checkNotNull(key);
/* 653 */       Preconditions.checkNotNull(value);
/* 654 */       notifyRemoval(key, value);
/* 655 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 660 */       return put(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(@Nullable Object key) {
/* 665 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(@Nullable Object key, @Nullable Object value) {
/* 670 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public V replace(K key, V value) {
/* 675 */       Preconditions.checkNotNull(key);
/* 676 */       Preconditions.checkNotNull(value);
/* 677 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(K key, @Nullable V oldValue, V newValue) {
/* 682 */       Preconditions.checkNotNull(key);
/* 683 */       Preconditions.checkNotNull(newValue);
/* 684 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<K, V>> entrySet() {
/* 689 */       return Collections.emptySet();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class NullComputingConcurrentMap<K, V>
/*     */     extends NullConcurrentMap<K, V>
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     final CacheLoader<? super K, ? extends V> loader;
/*     */     
/*     */     NullComputingConcurrentMap(CacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, ? extends V> loader) {
/* 702 */       super(builder);
/* 703 */       this.loader = (CacheLoader<? super K, ? extends V>)Preconditions.checkNotNull(loader);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public V get(Object k) {
/* 709 */       K key = (K)k;
/* 710 */       V value = compute(key);
/* 711 */       Preconditions.checkNotNull(value, this.loader + " returned null for key " + key + ".");
/* 712 */       notifyRemoval(key, value);
/* 713 */       return value;
/*     */     }
/*     */     
/*     */     private V compute(K key) {
/* 717 */       Preconditions.checkNotNull(key);
/*     */       try {
/* 719 */         return this.loader.load(key);
/* 720 */       } catch (Exception e) {
/* 721 */         throw new UncheckedExecutionException(e);
/* 722 */       } catch (Error e) {
/* 723 */         throw new ExecutionError(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class NullCache<K, V>
/*     */     extends AbstractCache<K, V>
/*     */   {
/*     */     final CacheBuilder.NullConcurrentMap<K, V> map;
/*     */     final CacheLoader<? super K, V> loader;
/*     */     final AbstractCache.StatsCounter statsCounter;
/*     */     ConcurrentMap<K, V> asMap;
/*     */     
/*     */     NullCache(CacheBuilder<? super K, ? super V> builder, Supplier<? extends AbstractCache.StatsCounter> statsCounterSupplier, CacheLoader<? super K, V> loader) {
/* 738 */       this.map = new CacheBuilder.NullConcurrentMap<K, V>(builder);
/* 739 */       this.statsCounter = (AbstractCache.StatsCounter)statsCounterSupplier.get();
/* 740 */       this.loader = (CacheLoader<? super K, V>)Preconditions.checkNotNull(loader);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(K key) throws ExecutionException {
/* 745 */       V value = compute(key);
/* 746 */       this.map.notifyRemoval(key, value);
/* 747 */       return value;
/*     */     }
/*     */     
/*     */     private V compute(K key) throws ExecutionException {
/* 751 */       Preconditions.checkNotNull(key);
/* 752 */       long start = System.nanoTime();
/* 753 */       V value = null;
/*     */       try {
/* 755 */         value = this.loader.load(key);
/* 756 */       } catch (RuntimeException e) {
/* 757 */         throw new UncheckedExecutionException(e);
/* 758 */       } catch (Exception e) {
/* 759 */         throw new ExecutionException(e);
/* 760 */       } catch (Error e) {
/* 761 */         throw new ExecutionError(e);
/*     */       } finally {
/* 763 */         long elapsed = System.nanoTime() - start;
/* 764 */         if (value == null) {
/* 765 */           this.statsCounter.recordLoadException(elapsed);
/*     */         } else {
/* 767 */           this.statsCounter.recordLoadSuccess(elapsed);
/*     */         } 
/* 769 */         this.statsCounter.recordEviction();
/*     */       } 
/* 771 */       if (value == null) {
/* 772 */         throw new NullPointerException();
/*     */       }
/* 774 */       return value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long size() {
/* 780 */       return 0L;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void invalidate(Object key) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void invalidateAll() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public CacheStats stats() {
/* 794 */       return this.statsCounter.snapshot();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ConcurrentMap<K, V> asMap() {
/* 801 */       ConcurrentMap<K, V> am = this.asMap;
/* 802 */       return (am != null) ? am : (this.asMap = (ConcurrentMap<K, V>)new CacheBuilder.CacheAsMap<K, V>(this.map));
/*     */     }
/*     */   }
/*     */   
/*     */   static final class CacheAsMap<K, V> extends ForwardingConcurrentMap<K, V> {
/*     */     private final ConcurrentMap<K, V> delegate;
/*     */     
/*     */     CacheAsMap(ConcurrentMap<K, V> delegate) {
/* 810 */       this.delegate = delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     protected ConcurrentMap<K, V> delegate() {
/* 815 */       return this.delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(K key, V value) {
/* 820 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> map) {
/* 825 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 830 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V replace(K key, V value) {
/* 835 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 840 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\cache\CacheBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */