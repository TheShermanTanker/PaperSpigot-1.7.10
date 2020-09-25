/*     */ package com.google.common.collect;@Beta
/*     */ @GwtCompatible(emulated = true)
/*     */ public abstract class GenericMapMaker<K0, V0> { @GwtIncompatible("To be supported")
/*     */   MapMaker.RemovalListener<K0, V0> removalListener;
/*     */   @GwtIncompatible("To be supported")
/*     */   abstract GenericMapMaker<K0, V0> keyEquivalence(Equivalence<Object> paramEquivalence);
/*     */   @GwtIncompatible("To be supported")
/*     */   abstract GenericMapMaker<K0, V0> valueEquivalence(Equivalence<Object> paramEquivalence);
/*     */   public abstract GenericMapMaker<K0, V0> initialCapacity(int paramInt);
/*     */   
/*     */   @Deprecated
/*     */   @Beta
/*     */   public abstract GenericMapMaker<K0, V0> maximumSize(int paramInt);
/*     */   
/*     */   abstract GenericMapMaker<K0, V0> strongKeys();
/*     */   
/*     */   public abstract GenericMapMaker<K0, V0> concurrencyLevel(int paramInt);
/*     */   
/*     */   @GwtIncompatible("java.lang.ref.WeakReference")
/*     */   public abstract GenericMapMaker<K0, V0> weakKeys();
/*     */   
/*     */   abstract GenericMapMaker<K0, V0> strongValues();
/*     */   
/*     */   @Deprecated
/*     */   @GwtIncompatible("java.lang.ref.SoftReference")
/*     */   public abstract GenericMapMaker<K0, V0> softKeys();
/*     */   
/*     */   @GwtIncompatible("java.lang.ref.WeakReference")
/*     */   public abstract GenericMapMaker<K0, V0> weakValues();
/*     */   
/*     */   @GwtIncompatible("java.lang.ref.SoftReference")
/*     */   public abstract GenericMapMaker<K0, V0> softValues();
/*     */   
/*     */   @Deprecated
/*     */   public abstract GenericMapMaker<K0, V0> expiration(long paramLong, TimeUnit paramTimeUnit);
/*     */   
/*     */   @Deprecated
/*     */   public abstract GenericMapMaker<K0, V0> expireAfterWrite(long paramLong, TimeUnit paramTimeUnit);
/*     */   
/*     */   @Deprecated
/*     */   @GwtIncompatible("To be supported")
/*     */   public abstract GenericMapMaker<K0, V0> expireAfterAccess(long paramLong, TimeUnit paramTimeUnit);
/*     */   
/*     */   @GwtIncompatible("To be supported")
/*  45 */   enum NullListener implements MapMaker.RemovalListener<Object, Object> { INSTANCE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onRemoval(MapMaker.RemovalNotification<Object, Object> notification) {} }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("To be supported")
/*     */   <K extends K0, V extends V0> MapMaker.RemovalListener<K, V> getRemovalListener() {
/* 156 */     return (MapMaker.RemovalListener<K, V>)Objects.firstNonNull(this.removalListener, NullListener.INSTANCE);
/*     */   }
/*     */   
/*     */   public abstract <K extends K0, V extends V0> ConcurrentMap<K, V> makeMap();
/*     */   
/*     */   @GwtIncompatible("CustomConcurrentHashMap")
/*     */   abstract <K, V> CustomConcurrentHashMap<K, V> makeCustomMap();
/*     */   
/*     */   @Deprecated
/*     */   public abstract <K extends K0, V extends V0> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> paramFunction); }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\GenericMapMaker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */