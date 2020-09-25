/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class HashBiMap<K, V>
/*     */   extends AbstractBiMap<K, V>
/*     */ {
/*     */   @GwtIncompatible("Not needed in emulated source")
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <K, V> HashBiMap<K, V> create() {
/*  46 */     return new HashBiMap<K, V>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> HashBiMap<K, V> create(int expectedSize) {
/*  57 */     return new HashBiMap<K, V>(expectedSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
/*  67 */     HashBiMap<K, V> bimap = create(map.size());
/*  68 */     bimap.putAll(map);
/*  69 */     return bimap;
/*     */   }
/*     */   
/*     */   private HashBiMap() {
/*  73 */     super(new HashMap<K, V>(), new HashMap<V, K>());
/*     */   }
/*     */   
/*     */   private HashBiMap(int expectedSize) {
/*  77 */     super(Maps.newHashMapWithExpectedSize(expectedSize), Maps.newHashMapWithExpectedSize(expectedSize));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(@Nullable K key, @Nullable V value) {
/*  85 */     return super.put(key, value);
/*     */   }
/*     */   
/*     */   public V forcePut(@Nullable K key, @Nullable V value) {
/*  89 */     return super.forcePut(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/*  98 */     stream.defaultWriteObject();
/*  99 */     Serialization.writeMap(this, stream);
/*     */   }
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 105 */     stream.defaultReadObject();
/* 106 */     int size = Serialization.readCount(stream);
/* 107 */     setDelegates(Maps.newHashMapWithExpectedSize(size), Maps.newHashMapWithExpectedSize(size));
/*     */     
/* 109 */     Serialization.populateMap(this, stream, size);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\HashBiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */