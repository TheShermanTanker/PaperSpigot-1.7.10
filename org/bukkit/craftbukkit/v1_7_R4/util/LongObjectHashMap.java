/*     */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongObjectHashMap<V>
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 2841537710170573815L;
/*     */   private static final long EMPTY_KEY = -9223372036854775808L;
/*     */   private static final int BUCKET_SIZE = 4096;
/*     */   private transient long[][] keys;
/*     */   private transient V[][] values;
/*     */   private transient int modCount;
/*     */   private transient int size;
/*     */   
/*     */   public LongObjectHashMap() {
/*  33 */     initialize();
/*     */   }
/*     */   
/*     */   public LongObjectHashMap(Map<? extends Long, ? extends V> map) {
/*  37 */     this();
/*  38 */     putAll(map);
/*     */   }
/*     */   
/*     */   public int size() {
/*  42 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  46 */     return (this.size == 0);
/*     */   }
/*     */   
/*     */   public boolean containsKey(long key) {
/*  50 */     return (get(key) != null);
/*     */   }
/*     */   
/*     */   public boolean containsValue(V value) {
/*  54 */     for (V val : values()) {
/*  55 */       if (val == value || val.equals(value)) {
/*  56 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  60 */     return false;
/*     */   }
/*     */   
/*     */   public V get(long key) {
/*  64 */     int index = (int)(keyIndex(key) & 0xFFFL);
/*  65 */     long[] inner = this.keys[index];
/*  66 */     if (inner == null) return null;
/*     */     
/*  68 */     for (int i = 0; i < inner.length; i++) {
/*  69 */       long innerKey = inner[i];
/*  70 */       if (innerKey == Long.MIN_VALUE)
/*  71 */         return null; 
/*  72 */       if (innerKey == key) {
/*  73 */         return this.values[index][i];
/*     */       }
/*     */     } 
/*     */     
/*  77 */     return null;
/*     */   }
/*     */   
/*     */   public V put(long key, V value) {
/*  81 */     int index = (int)(keyIndex(key) & 0xFFFL);
/*  82 */     long[] innerKeys = this.keys[index];
/*  83 */     V[] innerValues = this.values[index];
/*  84 */     this.modCount++;
/*     */     
/*  86 */     if (innerKeys == null) {
/*     */       
/*  88 */       this.keys[index] = innerKeys = new long[8];
/*  89 */       Arrays.fill(innerKeys, Long.MIN_VALUE);
/*  90 */       this.values[index] = innerValues = (V[])new Object[8];
/*  91 */       innerKeys[0] = key;
/*  92 */       innerValues[0] = value;
/*  93 */       this.size++;
/*     */     } else {
/*     */       int i;
/*  96 */       for (i = 0; i < innerKeys.length; i++) {
/*     */         
/*  98 */         if (innerKeys[i] == Long.MIN_VALUE) {
/*  99 */           this.size++;
/* 100 */           innerKeys[i] = key;
/* 101 */           innerValues[i] = value;
/* 102 */           return null;
/*     */         } 
/*     */ 
/*     */         
/* 106 */         if (innerKeys[i] == key) {
/* 107 */           V oldValue = innerValues[i];
/* 108 */           innerKeys[i] = key;
/* 109 */           innerValues[i] = value;
/* 110 */           return oldValue;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 115 */       this.keys[index] = innerKeys = Java15Compat.Arrays_copyOf(innerKeys, i << 1);
/* 116 */       Arrays.fill(innerKeys, i, innerKeys.length, Long.MIN_VALUE);
/* 117 */       this.values[index] = innerValues = Java15Compat.Arrays_copyOf((Object[])innerValues, i << 1);
/* 118 */       innerKeys[i] = key;
/* 119 */       innerValues[i] = value;
/* 120 */       this.size++;
/*     */     } 
/*     */     
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   public V remove(long key) {
/* 127 */     int index = (int)(keyIndex(key) & 0xFFFL);
/* 128 */     long[] inner = this.keys[index];
/* 129 */     if (inner == null) {
/* 130 */       return null;
/*     */     }
/*     */     
/* 133 */     for (int i = 0; i < inner.length; i++) {
/*     */       
/* 135 */       if (inner[i] == Long.MIN_VALUE) {
/*     */         break;
/*     */       }
/*     */       
/* 139 */       if (inner[i] == key) {
/* 140 */         V value = this.values[index][i];
/*     */         
/* 142 */         for (; ++i < inner.length && 
/* 143 */           inner[i] != Long.MIN_VALUE; i++) {
/*     */ 
/*     */ 
/*     */           
/* 147 */           inner[i - 1] = inner[i];
/* 148 */           this.values[index][i - 1] = this.values[index][i];
/*     */         } 
/*     */         
/* 151 */         inner[i - 1] = Long.MIN_VALUE;
/* 152 */         this.values[index][i - 1] = null;
/* 153 */         this.size--;
/* 154 */         this.modCount++;
/* 155 */         return value;
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     return null;
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends V> map) {
/* 163 */     for (Map.Entry<? extends Long, ? extends V> entry : map.entrySet()) {
/* 164 */       put(((Long)entry.getKey()).longValue(), (V)entry.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/* 169 */     if (this.size == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 173 */     this.modCount++;
/* 174 */     this.size = 0;
/* 175 */     Arrays.fill((Object[])this.keys, (Object)null);
/* 176 */     Arrays.fill((Object[])this.values, (Object)null);
/*     */   }
/*     */   
/*     */   public Set<Long> keySet() {
/* 180 */     return new KeySet();
/*     */   }
/*     */   
/*     */   public Collection<V> values() {
/* 184 */     return new ValueCollection();
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
/*     */   @Deprecated
/*     */   public Set<Map.Entry<Long, V>> entrySet() {
/* 197 */     HashSet<Map.Entry<Long, V>> set = new HashSet<Map.Entry<Long, V>>();
/* 198 */     for (Iterator<Long> i$ = keySet().iterator(); i$.hasNext(); ) { long key = ((Long)i$.next()).longValue();
/* 199 */       set.add(new Entry(key, get(key))); }
/*     */ 
/*     */     
/* 202 */     return set;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 206 */     LongObjectHashMap<V> clone = (LongObjectHashMap)super.clone();
/*     */     
/* 208 */     clone.clear();
/*     */     
/* 210 */     clone.initialize();
/*     */ 
/*     */     
/* 213 */     for (Iterator<Long> i$ = keySet().iterator(); i$.hasNext(); ) { long key = ((Long)i$.next()).longValue();
/* 214 */       V value = get(key);
/* 215 */       clone.put(key, value); }
/*     */ 
/*     */     
/* 218 */     return clone;
/*     */   }
/*     */   
/*     */   private void initialize() {
/* 222 */     this.keys = new long[4096][];
/* 223 */     this.values = (V[][])new Object[4096][];
/*     */   }
/*     */   
/*     */   private long keyIndex(long key) {
/* 227 */     key ^= key >>> 33L;
/* 228 */     key *= -49064778989728563L;
/* 229 */     key ^= key >>> 33L;
/* 230 */     key *= -4265267296055464877L;
/* 231 */     key ^= key >>> 33L;
/* 232 */     return key;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream outputStream) throws IOException {
/* 236 */     outputStream.defaultWriteObject();
/*     */     
/* 238 */     for (Iterator<Long> i$ = keySet().iterator(); i$.hasNext(); ) { long key = ((Long)i$.next()).longValue();
/* 239 */       V value = get(key);
/* 240 */       outputStream.writeLong(key);
/* 241 */       outputStream.writeObject(value); }
/*     */ 
/*     */     
/* 244 */     outputStream.writeLong(Long.MIN_VALUE);
/* 245 */     outputStream.writeObject(null);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {
/* 249 */     inputStream.defaultReadObject();
/* 250 */     initialize();
/*     */     
/*     */     while (true) {
/* 253 */       long key = inputStream.readLong();
/* 254 */       V value = (V)inputStream.readObject();
/* 255 */       if (key == Long.MIN_VALUE && value == null) {
/*     */         break;
/*     */       }
/*     */       
/* 259 */       put(key, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   private class ValueIterator
/*     */     implements Iterator<V> {
/*     */     private int count;
/*     */     private int index;
/*     */     private int innerIndex;
/*     */     private int expectedModCount;
/* 269 */     private long lastReturned = Long.MIN_VALUE;
/*     */     
/* 271 */     long prevKey = Long.MIN_VALUE;
/*     */     V prevValue;
/*     */     
/*     */     ValueIterator() {
/* 275 */       this.expectedModCount = LongObjectHashMap.this.modCount;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 279 */       return (this.count < LongObjectHashMap.this.size);
/*     */     }
/*     */     
/*     */     public void remove() {
/* 283 */       if (LongObjectHashMap.this.modCount != this.expectedModCount) {
/* 284 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 287 */       if (this.lastReturned == Long.MIN_VALUE) {
/* 288 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 291 */       this.count--;
/* 292 */       LongObjectHashMap.this.remove(this.lastReturned);
/* 293 */       this.lastReturned = Long.MIN_VALUE;
/* 294 */       this.expectedModCount = LongObjectHashMap.this.modCount;
/*     */     }
/*     */     
/*     */     public V next() {
/* 298 */       if (LongObjectHashMap.this.modCount != this.expectedModCount) {
/* 299 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 302 */       if (!hasNext()) {
/* 303 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 306 */       long[][] keys = LongObjectHashMap.this.keys;
/* 307 */       this.count++;
/*     */       
/* 309 */       if (this.prevKey != Long.MIN_VALUE) {
/* 310 */         this.innerIndex++;
/*     */       }
/*     */       
/* 313 */       for (; this.index < keys.length; this.index++) {
/* 314 */         if (keys[this.index] != null) {
/* 315 */           if (this.innerIndex < (keys[this.index]).length) {
/* 316 */             long key = keys[this.index][this.innerIndex];
/* 317 */             V value = (V)LongObjectHashMap.this.values[this.index][this.innerIndex];
/* 318 */             if (key != Long.MIN_VALUE) {
/*     */ 
/*     */ 
/*     */               
/* 322 */               this.lastReturned = key;
/* 323 */               this.prevKey = key;
/* 324 */               this.prevValue = value;
/* 325 */               return this.prevValue;
/*     */             } 
/* 327 */           }  this.innerIndex = 0;
/*     */         } 
/*     */       } 
/*     */       
/* 331 */       throw new NoSuchElementException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class KeyIterator
/*     */     implements Iterator<Long>
/*     */   {
/* 339 */     final LongObjectHashMap<V>.ValueIterator iterator = new LongObjectHashMap.ValueIterator();
/*     */ 
/*     */     
/*     */     public void remove() {
/* 343 */       this.iterator.remove();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 347 */       return this.iterator.hasNext();
/*     */     }
/*     */     
/*     */     public Long next() {
/* 351 */       this.iterator.next();
/* 352 */       return Long.valueOf(this.iterator.prevKey);
/*     */     }
/*     */   }
/*     */   
/*     */   private class KeySet extends AbstractSet<Long> { private KeySet() {}
/*     */     
/*     */     public void clear() {
/* 359 */       LongObjectHashMap.this.clear();
/*     */     }
/*     */     
/*     */     public int size() {
/* 363 */       return LongObjectHashMap.this.size();
/*     */     }
/*     */     
/*     */     public boolean contains(Object key) {
/* 367 */       return (key instanceof Long && LongObjectHashMap.this.containsKey(((Long)key).longValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key) {
/* 372 */       return (LongObjectHashMap.this.remove(((Long)key).longValue()) != null);
/*     */     }
/*     */     
/*     */     public Iterator<Long> iterator() {
/* 376 */       return new LongObjectHashMap.KeyIterator();
/*     */     } }
/*     */   
/*     */   private class ValueCollection extends AbstractCollection<V> {
/*     */     private ValueCollection() {}
/*     */     
/*     */     public void clear() {
/* 383 */       LongObjectHashMap.this.clear();
/*     */     }
/*     */     
/*     */     public int size() {
/* 387 */       return LongObjectHashMap.this.size();
/*     */     }
/*     */     
/*     */     public boolean contains(Object value) {
/* 391 */       return LongObjectHashMap.this.containsValue(value);
/*     */     }
/*     */     
/*     */     public Iterator<V> iterator() {
/* 395 */       return new LongObjectHashMap.ValueIterator();
/*     */     }
/*     */   }
/*     */   
/*     */   private class Entry
/*     */     implements Map.Entry<Long, V> {
/*     */     private final Long key;
/*     */     private V value;
/*     */     
/*     */     Entry(long k, V v) {
/* 405 */       this.key = Long.valueOf(k);
/* 406 */       this.value = v;
/*     */     }
/*     */     
/*     */     public Long getKey() {
/* 410 */       return this.key;
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 414 */       return this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V v) {
/* 418 */       V old = this.value;
/* 419 */       this.value = v;
/* 420 */       LongObjectHashMap.this.put(this.key.longValue(), v);
/* 421 */       return old;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\LongObjectHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */