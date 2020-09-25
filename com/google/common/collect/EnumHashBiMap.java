/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EnumMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class EnumHashBiMap<K extends Enum<K>, V>
/*     */   extends AbstractBiMap<K, V>
/*     */ {
/*     */   private transient Class<K> keyType;
/*     */   @GwtIncompatible("only needed in emulated source.")
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Class<K> keyType) {
/*  52 */     return new EnumHashBiMap<K, V>(keyType);
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
/*     */   public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Map<K, ? extends V> map) {
/*  67 */     EnumHashBiMap<K, V> bimap = create(EnumBiMap.inferKeyType(map));
/*  68 */     bimap.putAll(map);
/*  69 */     return bimap;
/*     */   }
/*     */   
/*     */   private EnumHashBiMap(Class<K> keyType) {
/*  73 */     super(WellBehavedMap.wrap(new EnumMap<K, V>(keyType)), Maps.newHashMapWithExpectedSize(((Enum[])keyType.getEnumConstants()).length));
/*     */ 
/*     */ 
/*     */     
/*  77 */     this.keyType = keyType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(K key, @Nullable V value) {
/*  83 */     return super.put(key, value);
/*     */   }
/*     */   
/*     */   public V forcePut(K key, @Nullable V value) {
/*  87 */     return super.forcePut(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<K> keyType() {
/*  92 */     return this.keyType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 101 */     stream.defaultWriteObject();
/* 102 */     stream.writeObject(this.keyType);
/* 103 */     Serialization.writeMap(this, stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 110 */     stream.defaultReadObject();
/* 111 */     this.keyType = (Class<K>)stream.readObject();
/* 112 */     setDelegates(WellBehavedMap.wrap(new EnumMap<K, V>(this.keyType)), new HashMap<V, K>(((Enum[])this.keyType.getEnumConstants()).length * 3 / 2));
/*     */     
/* 114 */     Serialization.populateMap(this, stream);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\EnumHashBiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */