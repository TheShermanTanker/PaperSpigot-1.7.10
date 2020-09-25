/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ public class ImmutableListMultimap<K, V>
/*     */   extends ImmutableMultimap<K, V>
/*     */   implements ListMultimap<K, V>
/*     */ {
/*     */   @GwtIncompatible("Not needed in emulated source")
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of() {
/*  61 */     return EmptyImmutableListMultimap.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1) {
/*  68 */     Builder<K, V> builder = builder();
/*     */     
/*  70 */     builder.put(k1, v1);
/*  71 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2) {
/*  78 */     Builder<K, V> builder = builder();
/*     */     
/*  80 */     builder.put(k1, v1);
/*  81 */     builder.put(k2, v2);
/*  82 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
/*  90 */     Builder<K, V> builder = builder();
/*     */     
/*  92 */     builder.put(k1, v1);
/*  93 */     builder.put(k2, v2);
/*  94 */     builder.put(k3, v3);
/*  95 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
/* 103 */     Builder<K, V> builder = builder();
/*     */     
/* 105 */     builder.put(k1, v1);
/* 106 */     builder.put(k2, v2);
/* 107 */     builder.put(k3, v3);
/* 108 */     builder.put(k4, v4);
/* 109 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
/* 117 */     Builder<K, V> builder = builder();
/*     */     
/* 119 */     builder.put(k1, v1);
/* 120 */     builder.put(k2, v2);
/* 121 */     builder.put(k3, v3);
/* 122 */     builder.put(k4, v4);
/* 123 */     builder.put(k5, v5);
/* 124 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Builder<K, V> builder() {
/* 134 */     return new Builder<K, V>();
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
/*     */   public static final class Builder<K, V>
/*     */     extends ImmutableMultimap.Builder<K, V>
/*     */   {
/*     */     public Builder<K, V> put(K key, V value) {
/* 164 */       super.put(key, value);
/* 165 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<K, V> putAll(K key, Iterable<? extends V> values) {
/* 169 */       super.putAll(key, values);
/* 170 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<K, V> putAll(K key, V... values) {
/* 174 */       super.putAll(key, values);
/* 175 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
/* 180 */       super.putAll(multimap);
/* 181 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Beta
/*     */     public Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
/* 191 */       super.orderKeysBy(keyComparator);
/* 192 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Beta
/*     */     public Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
/* 202 */       super.orderValuesBy(valueComparator);
/* 203 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableListMultimap<K, V> build() {
/* 210 */       return (ImmutableListMultimap<K, V>)super.build();
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
/*     */   public static <K, V> ImmutableListMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
/* 228 */     if (multimap.isEmpty()) {
/* 229 */       return of();
/*     */     }
/*     */ 
/*     */     
/* 233 */     if (multimap instanceof ImmutableListMultimap) {
/*     */       
/* 235 */       ImmutableListMultimap<K, V> kvMultimap = (ImmutableListMultimap)multimap;
/*     */       
/* 237 */       if (!kvMultimap.isPartialView()) {
/* 238 */         return kvMultimap;
/*     */       }
/*     */     } 
/*     */     
/* 242 */     ImmutableMap.Builder<K, ImmutableList<V>> builder = ImmutableMap.builder();
/* 243 */     int size = 0;
/*     */ 
/*     */     
/* 246 */     for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : (Iterable<Map.Entry<? extends K, ? extends Collection<? extends V>>>)multimap.asMap().entrySet()) {
/* 247 */       ImmutableList<V> list = ImmutableList.copyOf(entry.getValue());
/* 248 */       if (!list.isEmpty()) {
/* 249 */         builder.put(entry.getKey(), list);
/* 250 */         size += list.size();
/*     */       } 
/*     */     } 
/*     */     
/* 254 */     return new ImmutableListMultimap<K, V>(builder.build(), size);
/*     */   }
/*     */   
/*     */   ImmutableListMultimap(ImmutableMap<K, ImmutableList<V>> map, int size) {
/* 258 */     super((ImmutableMap)map, size);
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
/*     */   public ImmutableList<V> get(@Nullable K key) {
/* 271 */     ImmutableList<V> list = (ImmutableList<V>)this.map.get(key);
/* 272 */     return (list == null) ? ImmutableList.<V>of() : list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableList<V> removeAll(Object key) {
/* 281 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableList<V> replaceValues(K key, Iterable<? extends V> values) {
/* 291 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 300 */     stream.defaultWriteObject();
/* 301 */     Serialization.writeMultimap(this, stream);
/*     */   }
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*     */     ImmutableMap<Object, ImmutableList<Object>> tmpMap;
/* 307 */     stream.defaultReadObject();
/* 308 */     int keyCount = stream.readInt();
/* 309 */     if (keyCount < 0) {
/* 310 */       throw new InvalidObjectException("Invalid key count " + keyCount);
/*     */     }
/* 312 */     ImmutableMap.Builder<Object, ImmutableList<Object>> builder = ImmutableMap.builder();
/*     */     
/* 314 */     int tmpSize = 0;
/*     */     
/* 316 */     for (int i = 0; i < keyCount; i++) {
/* 317 */       Object key = stream.readObject();
/* 318 */       int valueCount = stream.readInt();
/* 319 */       if (valueCount <= 0) {
/* 320 */         throw new InvalidObjectException("Invalid value count " + valueCount);
/*     */       }
/*     */       
/* 323 */       Object[] array = new Object[valueCount];
/* 324 */       for (int j = 0; j < valueCount; j++) {
/* 325 */         array[j] = stream.readObject();
/*     */       }
/* 327 */       builder.put(key, ImmutableList.copyOf(array));
/* 328 */       tmpSize += valueCount;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 333 */       tmpMap = builder.build();
/* 334 */     } catch (IllegalArgumentException e) {
/* 335 */       throw (InvalidObjectException)(new InvalidObjectException(e.getMessage())).initCause(e);
/*     */     } 
/*     */ 
/*     */     
/* 339 */     ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, tmpMap);
/* 340 */     ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, tmpSize);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableListMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */