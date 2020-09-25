/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class EnumBiMap<K extends Enum<K>, V extends Enum<V>>
/*     */   extends AbstractBiMap<K, V>
/*     */ {
/*     */   private transient Class<K> keyType;
/*     */   private transient Class<V> valueType;
/*     */   @GwtIncompatible("not needed in emulated source.")
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Class<K> keyType, Class<V> valueType) {
/*  53 */     return new EnumBiMap<K, V>(keyType, valueType);
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
/*     */   public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Map<K, V> map) {
/*  68 */     EnumBiMap<K, V> bimap = create(inferKeyType(map), inferValueType(map));
/*  69 */     bimap.putAll(map);
/*  70 */     return bimap;
/*     */   }
/*     */   
/*     */   private EnumBiMap(Class<K> keyType, Class<V> valueType) {
/*  74 */     super(WellBehavedMap.wrap(new EnumMap<K, V>(keyType)), WellBehavedMap.wrap((Map)new EnumMap<V, Object>(valueType)));
/*     */     
/*  76 */     this.keyType = keyType;
/*  77 */     this.valueType = valueType;
/*     */   }
/*     */   
/*     */   static <K extends Enum<K>> Class<K> inferKeyType(Map<K, ?> map) {
/*  81 */     if (map instanceof EnumBiMap) {
/*  82 */       return ((EnumBiMap)map).keyType();
/*     */     }
/*  84 */     if (map instanceof EnumHashBiMap) {
/*  85 */       return ((EnumHashBiMap)map).keyType();
/*     */     }
/*  87 */     Preconditions.checkArgument(!map.isEmpty());
/*  88 */     return ((Enum<K>)map.keySet().iterator().next()).getDeclaringClass();
/*     */   }
/*     */   
/*     */   private static <V extends Enum<V>> Class<V> inferValueType(Map<?, V> map) {
/*  92 */     if (map instanceof EnumBiMap) {
/*  93 */       return ((EnumBiMap)map).valueType;
/*     */     }
/*  95 */     Preconditions.checkArgument(!map.isEmpty());
/*  96 */     return ((Enum<V>)map.values().iterator().next()).getDeclaringClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<K> keyType() {
/* 101 */     return this.keyType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<V> valueType() {
/* 106 */     return this.valueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 115 */     stream.defaultWriteObject();
/* 116 */     stream.writeObject(this.keyType);
/* 117 */     stream.writeObject(this.valueType);
/* 118 */     Serialization.writeMap(this, stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 125 */     stream.defaultReadObject();
/* 126 */     this.keyType = (Class<K>)stream.readObject();
/* 127 */     this.valueType = (Class<V>)stream.readObject();
/* 128 */     setDelegates(WellBehavedMap.wrap(new EnumMap<K, V>(this.keyType)), WellBehavedMap.wrap((Map)new EnumMap<V, Object>(this.valueType)));
/*     */ 
/*     */     
/* 131 */     Serialization.populateMap(this, stream);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\EnumBiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */