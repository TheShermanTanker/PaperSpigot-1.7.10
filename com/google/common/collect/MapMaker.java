/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.Beta;
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.GwtIncompatible;
/*      */ import com.google.common.base.Ascii;
/*      */ import com.google.common.base.Equivalence;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Ticker;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.Collections;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @GwtCompatible(emulated = true)
/*      */ public final class MapMaker
/*      */   extends GenericMapMaker<Object, Object>
/*      */ {
/*      */   private static final int DEFAULT_INITIAL_CAPACITY = 16;
/*      */   private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
/*      */   private static final int DEFAULT_EXPIRATION_NANOS = 0;
/*      */   static final int UNSET_INT = -1;
/*      */   boolean useCustomMap;
/*  126 */   int initialCapacity = -1;
/*  127 */   int concurrencyLevel = -1;
/*  128 */   int maximumSize = -1;
/*      */   
/*      */   CustomConcurrentHashMap.Strength keyStrength;
/*      */   
/*      */   CustomConcurrentHashMap.Strength valueStrength;
/*  133 */   long expireAfterWriteNanos = -1L;
/*  134 */   long expireAfterAccessNanos = -1L;
/*      */ 
/*      */   
/*      */   RemovalCause nullRemovalCause;
/*      */ 
/*      */   
/*      */   Equivalence<Object> keyEquivalence;
/*      */ 
/*      */   
/*      */   Equivalence<Object> valueEquivalence;
/*      */ 
/*      */   
/*      */   Ticker ticker;
/*      */ 
/*      */   
/*      */   private boolean useNullMap() {
/*  150 */     return (this.nullRemovalCause == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("To be supported")
/*      */   MapMaker keyEquivalence(Equivalence<Object> equivalence) {
/*  163 */     Preconditions.checkState((this.keyEquivalence == null), "key equivalence was already set to %s", new Object[] { this.keyEquivalence });
/*  164 */     this.keyEquivalence = (Equivalence<Object>)Preconditions.checkNotNull(equivalence);
/*  165 */     this.useCustomMap = true;
/*  166 */     return this;
/*      */   }
/*      */   
/*      */   Equivalence<Object> getKeyEquivalence() {
/*  170 */     return (Equivalence<Object>)Objects.firstNonNull(this.keyEquivalence, getKeyStrength().defaultEquivalence());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("To be supported")
/*      */   MapMaker valueEquivalence(Equivalence<Object> equivalence) {
/*  183 */     Preconditions.checkState((this.valueEquivalence == null), "value equivalence was already set to %s", new Object[] { this.valueEquivalence });
/*      */     
/*  185 */     this.valueEquivalence = (Equivalence<Object>)Preconditions.checkNotNull(equivalence);
/*  186 */     this.useCustomMap = true;
/*  187 */     return this;
/*      */   }
/*      */   
/*      */   Equivalence<Object> getValueEquivalence() {
/*  191 */     return (Equivalence<Object>)Objects.firstNonNull(this.valueEquivalence, getValueStrength().defaultEquivalence());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapMaker initialCapacity(int initialCapacity) {
/*  206 */     Preconditions.checkState((this.initialCapacity == -1), "initial capacity was already set to %s", new Object[] { Integer.valueOf(this.initialCapacity) });
/*      */     
/*  208 */     Preconditions.checkArgument((initialCapacity >= 0));
/*  209 */     this.initialCapacity = initialCapacity;
/*  210 */     return this;
/*      */   }
/*      */   
/*      */   int getInitialCapacity() {
/*  214 */     return (this.initialCapacity == -1) ? 16 : this.initialCapacity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Beta
/*      */   public MapMaker maximumSize(int size) {
/*  241 */     Preconditions.checkState((this.maximumSize == -1), "maximum size was already set to %s", new Object[] { Integer.valueOf(this.maximumSize) });
/*      */     
/*  243 */     Preconditions.checkArgument((size >= 0), "maximum size must not be negative");
/*  244 */     this.maximumSize = size;
/*  245 */     this.useCustomMap = true;
/*  246 */     if (this.maximumSize == 0)
/*      */     {
/*  248 */       this.nullRemovalCause = RemovalCause.SIZE;
/*      */     }
/*  250 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapMaker concurrencyLevel(int concurrencyLevel) {
/*  274 */     Preconditions.checkState((this.concurrencyLevel == -1), "concurrency level was already set to %s", new Object[] { Integer.valueOf(this.concurrencyLevel) });
/*      */     
/*  276 */     Preconditions.checkArgument((concurrencyLevel > 0));
/*  277 */     this.concurrencyLevel = concurrencyLevel;
/*  278 */     return this;
/*      */   }
/*      */   
/*      */   int getConcurrencyLevel() {
/*  282 */     return (this.concurrencyLevel == -1) ? 4 : this.concurrencyLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MapMaker strongKeys() {
/*  292 */     return setKeyStrength(CustomConcurrentHashMap.Strength.STRONG);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.lang.ref.WeakReference")
/*      */   public MapMaker weakKeys() {
/*  309 */     return setKeyStrength(CustomConcurrentHashMap.Strength.WEAK);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @GwtIncompatible("java.lang.ref.SoftReference")
/*      */   public MapMaker softKeys() {
/*  332 */     return setKeyStrength(CustomConcurrentHashMap.Strength.SOFT);
/*      */   }
/*      */   
/*      */   MapMaker setKeyStrength(CustomConcurrentHashMap.Strength strength) {
/*  336 */     Preconditions.checkState((this.keyStrength == null), "Key strength was already set to %s", new Object[] { this.keyStrength });
/*  337 */     this.keyStrength = (CustomConcurrentHashMap.Strength)Preconditions.checkNotNull(strength);
/*  338 */     if (strength != CustomConcurrentHashMap.Strength.STRONG)
/*      */     {
/*  340 */       this.useCustomMap = true;
/*      */     }
/*  342 */     return this;
/*      */   }
/*      */   
/*      */   CustomConcurrentHashMap.Strength getKeyStrength() {
/*  346 */     return (CustomConcurrentHashMap.Strength)Objects.firstNonNull(this.keyStrength, CustomConcurrentHashMap.Strength.STRONG);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MapMaker strongValues() {
/*  356 */     return setValueStrength(CustomConcurrentHashMap.Strength.STRONG);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.lang.ref.WeakReference")
/*      */   public MapMaker weakValues() {
/*  379 */     return setValueStrength(CustomConcurrentHashMap.Strength.WEAK);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.lang.ref.SoftReference")
/*      */   public MapMaker softValues() {
/*  405 */     return setValueStrength(CustomConcurrentHashMap.Strength.SOFT);
/*      */   }
/*      */   
/*      */   MapMaker setValueStrength(CustomConcurrentHashMap.Strength strength) {
/*  409 */     Preconditions.checkState((this.valueStrength == null), "Value strength was already set to %s", new Object[] { this.valueStrength });
/*  410 */     this.valueStrength = (CustomConcurrentHashMap.Strength)Preconditions.checkNotNull(strength);
/*  411 */     if (strength != CustomConcurrentHashMap.Strength.STRONG)
/*      */     {
/*  413 */       this.useCustomMap = true;
/*      */     }
/*  415 */     return this;
/*      */   }
/*      */   
/*      */   CustomConcurrentHashMap.Strength getValueStrength() {
/*  419 */     return (CustomConcurrentHashMap.Strength)Objects.firstNonNull(this.valueStrength, CustomConcurrentHashMap.Strength.STRONG);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public MapMaker expiration(long duration, TimeUnit unit) {
/*  435 */     return expireAfterWrite(duration, unit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public MapMaker expireAfterWrite(long duration, TimeUnit unit) {
/*  466 */     checkExpiration(duration, unit);
/*  467 */     this.expireAfterWriteNanos = unit.toNanos(duration);
/*  468 */     if (duration == 0L && this.nullRemovalCause == null)
/*      */     {
/*  470 */       this.nullRemovalCause = RemovalCause.EXPIRED;
/*      */     }
/*  472 */     this.useCustomMap = true;
/*  473 */     return this;
/*      */   }
/*      */   
/*      */   private void checkExpiration(long duration, TimeUnit unit) {
/*  477 */     Preconditions.checkState((this.expireAfterWriteNanos == -1L), "expireAfterWrite was already set to %s ns", new Object[] { Long.valueOf(this.expireAfterWriteNanos) });
/*      */     
/*  479 */     Preconditions.checkState((this.expireAfterAccessNanos == -1L), "expireAfterAccess was already set to %s ns", new Object[] { Long.valueOf(this.expireAfterAccessNanos) });
/*      */     
/*  481 */     Preconditions.checkArgument((duration >= 0L), "duration cannot be negative: %s %s", new Object[] { Long.valueOf(duration), unit });
/*      */   }
/*      */   
/*      */   long getExpireAfterWriteNanos() {
/*  485 */     return (this.expireAfterWriteNanos == -1L) ? 0L : this.expireAfterWriteNanos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @GwtIncompatible("To be supported")
/*      */   public MapMaker expireAfterAccess(long duration, TimeUnit unit) {
/*  517 */     checkExpiration(duration, unit);
/*  518 */     this.expireAfterAccessNanos = unit.toNanos(duration);
/*  519 */     if (duration == 0L && this.nullRemovalCause == null)
/*      */     {
/*  521 */       this.nullRemovalCause = RemovalCause.EXPIRED;
/*      */     }
/*  523 */     this.useCustomMap = true;
/*  524 */     return this;
/*      */   }
/*      */   
/*      */   long getExpireAfterAccessNanos() {
/*  528 */     return (this.expireAfterAccessNanos == -1L) ? 0L : this.expireAfterAccessNanos;
/*      */   }
/*      */ 
/*      */   
/*      */   Ticker getTicker() {
/*  533 */     return (Ticker)Objects.firstNonNull(this.ticker, Ticker.systemTicker());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("To be supported")
/*      */   <K, V> GenericMapMaker<K, V> removalListener(RemovalListener<K, V> listener) {
/*  562 */     Preconditions.checkState((this.removalListener == null));
/*      */ 
/*      */ 
/*      */     
/*  566 */     GenericMapMaker<K, V> me = this;
/*  567 */     me.removalListener = (RemovalListener<K, V>)Preconditions.checkNotNull(listener);
/*  568 */     this.useCustomMap = true;
/*  569 */     return me;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class MapMakerRemovalListener<K, V>
/*      */     implements RemovalListener<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     private final MapEvictionListener<K, V> listener;
/*      */ 
/*      */     
/*      */     public MapMakerRemovalListener(MapEvictionListener<K, V> listener) {
/*  583 */       this.listener = (MapEvictionListener<K, V>)Preconditions.checkNotNull(listener);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onRemoval(MapMaker.RemovalNotification<K, V> notification) {
/*  588 */       if (notification.wasEvicted()) {
/*  589 */         this.listener.onEviction(notification.getKey(), notification.getValue());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Beta
/*      */   @GwtIncompatible("To be supported")
/*      */   public <K, V> GenericMapMaker<K, V> evictionListener(MapEvictionListener<K, V> listener) {
/*  635 */     Preconditions.checkState((this.removalListener == null));
/*      */ 
/*      */ 
/*      */     
/*  639 */     GenericMapMaker<K, V> me = this;
/*  640 */     me.removalListener = new MapMakerRemovalListener<K, V>(listener);
/*  641 */     this.useCustomMap = true;
/*  642 */     return me;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <K, V> ConcurrentMap<K, V> makeMap() {
/*  659 */     if (!this.useCustomMap) {
/*  660 */       return new ConcurrentHashMap<K, V>(getInitialCapacity(), 0.75F, getConcurrencyLevel());
/*      */     }
/*  662 */     return (this.nullRemovalCause == null) ? new CustomConcurrentHashMap<K, V>(this) : new NullConcurrentMap<K, V>(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("CustomConcurrentHashMap")
/*      */   <K, V> CustomConcurrentHashMap<K, V> makeCustomMap() {
/*  674 */     return new CustomConcurrentHashMap<K, V>(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public <K, V> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> computingFunction) {
/*  739 */     return useNullMap() ? new ComputingConcurrentHashMap.ComputingMapAdapter<K, V>(this, computingFunction) : new NullComputingConcurrentMap<K, V>(this, computingFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  750 */     Objects.ToStringHelper s = Objects.toStringHelper(this);
/*  751 */     if (this.initialCapacity != -1) {
/*  752 */       s.add("initialCapacity", Integer.valueOf(this.initialCapacity));
/*      */     }
/*  754 */     if (this.concurrencyLevel != -1) {
/*  755 */       s.add("concurrencyLevel", Integer.valueOf(this.concurrencyLevel));
/*      */     }
/*  757 */     if (this.maximumSize != -1) {
/*  758 */       s.add("maximumSize", Integer.valueOf(this.maximumSize));
/*      */     }
/*  760 */     if (this.expireAfterWriteNanos != -1L) {
/*  761 */       s.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
/*      */     }
/*  763 */     if (this.expireAfterAccessNanos != -1L) {
/*  764 */       s.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
/*      */     }
/*  766 */     if (this.keyStrength != null) {
/*  767 */       s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
/*      */     }
/*  769 */     if (this.valueStrength != null) {
/*  770 */       s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
/*      */     }
/*  772 */     if (this.keyEquivalence != null) {
/*  773 */       s.addValue("keyEquivalence");
/*      */     }
/*  775 */     if (this.valueEquivalence != null) {
/*  776 */       s.addValue("valueEquivalence");
/*      */     }
/*  778 */     if (this.removalListener != null) {
/*  779 */       s.addValue("removalListener");
/*      */     }
/*  781 */     return s.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static interface RemovalListener<K, V>
/*      */   {
/*      */     void onRemoval(MapMaker.RemovalNotification<K, V> param1RemovalNotification);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class RemovalNotification<K, V>
/*      */     extends ImmutableEntry<K, V>
/*      */   {
/*      */     private static final long serialVersionUID = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final MapMaker.RemovalCause cause;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     RemovalNotification(@Nullable K key, @Nullable V value, MapMaker.RemovalCause cause) {
/*  819 */       super(key, value);
/*  820 */       this.cause = cause;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MapMaker.RemovalCause getCause() {
/*  827 */       return this.cause;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean wasEvicted() {
/*  835 */       return this.cause.wasEvicted();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   enum RemovalCause
/*      */   {
/*  847 */     EXPLICIT
/*      */     {
/*      */       boolean wasEvicted() {
/*  850 */         return false;
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  860 */     REPLACED
/*      */     {
/*      */       boolean wasEvicted() {
/*  863 */         return false;
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  872 */     COLLECTED
/*      */     {
/*      */       boolean wasEvicted() {
/*  875 */         return true;
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  883 */     EXPIRED
/*      */     {
/*      */       boolean wasEvicted() {
/*  886 */         return true;
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  894 */     SIZE
/*      */     {
/*      */       boolean wasEvicted() {
/*  897 */         return true;
/*      */       }
/*      */     };
/*      */ 
/*      */     
/*      */     abstract boolean wasEvicted();
/*      */   }
/*      */ 
/*      */   
/*      */   static class NullConcurrentMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */     implements ConcurrentMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     private final MapMaker.RemovalListener<K, V> removalListener;
/*      */     
/*      */     private final MapMaker.RemovalCause removalCause;
/*      */     
/*      */     NullConcurrentMap(MapMaker mapMaker) {
/*  917 */       this.removalListener = mapMaker.getRemovalListener();
/*  918 */       this.removalCause = mapMaker.nullRemovalCause;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(@Nullable Object key) {
/*  925 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(@Nullable Object value) {
/*  930 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get(@Nullable Object key) {
/*  935 */       return null;
/*      */     }
/*      */     
/*      */     void notifyRemoval(K key, V value) {
/*  939 */       MapMaker.RemovalNotification<K, V> notification = new MapMaker.RemovalNotification<K, V>(key, value, this.removalCause);
/*      */       
/*  941 */       this.removalListener.onRemoval(notification);
/*      */     }
/*      */ 
/*      */     
/*      */     public V put(K key, V value) {
/*  946 */       Preconditions.checkNotNull(key);
/*  947 */       Preconditions.checkNotNull(value);
/*  948 */       notifyRemoval(key, value);
/*  949 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K key, V value) {
/*  954 */       return put(key, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(@Nullable Object key) {
/*  959 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(@Nullable Object key, @Nullable Object value) {
/*  964 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public V replace(K key, V value) {
/*  969 */       Preconditions.checkNotNull(key);
/*  970 */       Preconditions.checkNotNull(value);
/*  971 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean replace(K key, @Nullable V oldValue, V newValue) {
/*  976 */       Preconditions.checkNotNull(key);
/*  977 */       Preconditions.checkNotNull(newValue);
/*  978 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/*  983 */       return Collections.emptySet();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class NullComputingConcurrentMap<K, V>
/*      */     extends NullConcurrentMap<K, V>
/*      */   {
/*      */     private static final long serialVersionUID = 0L;
/*      */     final Function<? super K, ? extends V> computingFunction;
/*      */     
/*      */     NullComputingConcurrentMap(MapMaker mapMaker, Function<? super K, ? extends V> computingFunction) {
/*  995 */       super(mapMaker);
/*  996 */       this.computingFunction = (Function<? super K, ? extends V>)Preconditions.checkNotNull(computingFunction);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(Object k) {
/* 1002 */       K key = (K)k;
/* 1003 */       V value = compute(key);
/* 1004 */       Preconditions.checkNotNull(value, this.computingFunction + " returned null for key " + key + ".");
/* 1005 */       notifyRemoval(key, value);
/* 1006 */       return value;
/*      */     }
/*      */     
/*      */     private V compute(K key) {
/* 1010 */       Preconditions.checkNotNull(key);
/*      */       try {
/* 1012 */         return (V)this.computingFunction.apply(key);
/* 1013 */       } catch (ComputationException e) {
/* 1014 */         throw e;
/* 1015 */       } catch (Throwable t) {
/* 1016 */         throw new ComputationException(t);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\MapMaker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */