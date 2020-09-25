/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Equivalence;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Throwables;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.annotation.concurrent.GuardedBy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ComputingConcurrentHashMap<K, V>
/*     */   extends CustomConcurrentHashMap<K, V>
/*     */ {
/*     */   final Function<? super K, ? extends V> computingFunction;
/*     */   private static final long serialVersionUID = 4L;
/*     */   
/*     */   ComputingConcurrentHashMap(MapMaker builder, Function<? super K, ? extends V> computingFunction) {
/*  53 */     super(builder);
/*  54 */     this.computingFunction = (Function<? super K, ? extends V>)Preconditions.checkNotNull(computingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   CustomConcurrentHashMap.Segment<K, V> createSegment(int initialCapacity, int maxSegmentSize) {
/*  59 */     return new ComputingSegment<K, V>(this, initialCapacity, maxSegmentSize);
/*     */   }
/*     */ 
/*     */   
/*     */   ComputingSegment<K, V> segmentFor(int hash) {
/*  64 */     return (ComputingSegment<K, V>)super.segmentFor(hash);
/*     */   }
/*     */   
/*     */   V getOrCompute(K key) throws ExecutionException {
/*  68 */     int hash = hash(Preconditions.checkNotNull(key));
/*  69 */     return segmentFor(hash).getOrCompute(key, hash, this.computingFunction);
/*     */   }
/*     */   
/*     */   static final class ComputingSegment<K, V>
/*     */     extends CustomConcurrentHashMap.Segment<K, V> {
/*     */     ComputingSegment(CustomConcurrentHashMap<K, V> map, int initialCapacity, int maxSegmentSize) {
/*  75 */       super(map, initialCapacity, maxSegmentSize);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     V getOrCompute(K key, int hash, Function<? super K, ? extends V> computingFunction) throws ExecutionException {
/*     */       try {
/*     */         while (true) {
/*  83 */           CustomConcurrentHashMap.ReferenceEntry<K, V> e = getEntry(key, hash);
/*  84 */           if (e != null) {
/*  85 */             V v = getLiveValue(e);
/*  86 */             if (v != null) {
/*  87 */               recordRead(e);
/*  88 */               return v;
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*  94 */           if (e == null || !e.getValueReference().isComputingReference()) {
/*  95 */             boolean createNewEntry = true;
/*  96 */             ComputingConcurrentHashMap.ComputingValueReference<K, V> computingValueReference = null;
/*  97 */             lock();
/*     */             try {
/*  99 */               preWriteCleanup();
/*     */               
/* 101 */               int newCount = this.count - 1;
/* 102 */               AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 103 */               int index = hash & table.length() - 1;
/* 104 */               CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*     */               
/* 106 */               for (e = first; e != null; e = e.getNext()) {
/* 107 */                 K entryKey = e.getKey();
/* 108 */                 if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*     */                   
/* 110 */                   CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 111 */                   if (valueReference.isComputingReference()) {
/* 112 */                     createNewEntry = false; break;
/*     */                   } 
/* 114 */                   V v = (V)e.getValueReference().get();
/* 115 */                   if (v == null) {
/* 116 */                     enqueueNotification(entryKey, hash, v, MapMaker.RemovalCause.COLLECTED);
/* 117 */                   } else if (this.map.expires() && this.map.isExpired(e)) {
/*     */ 
/*     */                     
/* 120 */                     enqueueNotification(entryKey, hash, v, MapMaker.RemovalCause.EXPIRED);
/*     */                   } else {
/* 122 */                     recordLockedRead(e);
/* 123 */                     return v;
/*     */                   } 
/*     */ 
/*     */                   
/* 127 */                   this.evictionQueue.remove(e);
/* 128 */                   this.expirationQueue.remove(e);
/* 129 */                   this.count = newCount;
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */               
/* 135 */               if (createNewEntry) {
/* 136 */                 computingValueReference = new ComputingConcurrentHashMap.ComputingValueReference<K, V>(computingFunction);
/*     */                 
/* 138 */                 if (e == null) {
/* 139 */                   e = newEntry(key, hash, first);
/* 140 */                   e.setValueReference(computingValueReference);
/* 141 */                   table.set(index, e);
/*     */                 } else {
/* 143 */                   e.setValueReference(computingValueReference);
/*     */                 } 
/*     */               } 
/*     */             } finally {
/* 147 */               unlock();
/* 148 */               postWriteCleanup();
/*     */             } 
/*     */             
/* 151 */             if (createNewEntry)
/*     */             {
/* 153 */               return compute(key, hash, e, computingValueReference);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 158 */           Preconditions.checkState(!Thread.holdsLock(e), "Recursive computation");
/*     */           
/* 160 */           V value = (V)e.getValueReference().waitForValue();
/* 161 */           if (value != null) {
/* 162 */             recordRead(e);
/* 163 */             return value;
/*     */           }
/*     */         
/*     */         } 
/*     */       } finally {
/*     */         
/* 169 */         postReadCleanup();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     V compute(K key, int hash, CustomConcurrentHashMap.ReferenceEntry<K, V> e, ComputingConcurrentHashMap.ComputingValueReference<K, V> computingValueReference) throws ExecutionException {
/* 176 */       V value = null;
/* 177 */       long start = System.nanoTime();
/* 178 */       long end = 0L;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 183 */         synchronized (e) {
/* 184 */           value = computingValueReference.compute(key, hash);
/* 185 */           end = System.nanoTime();
/*     */         } 
/* 187 */         if (value != null) {
/*     */           
/* 189 */           V oldValue = put(key, hash, value, true);
/* 190 */           if (oldValue != null)
/*     */           {
/* 192 */             enqueueNotification(key, hash, value, MapMaker.RemovalCause.REPLACED);
/*     */           }
/*     */         } 
/* 195 */         return value;
/*     */       } finally {
/* 197 */         if (end == 0L) {
/* 198 */           end = System.nanoTime();
/*     */         }
/* 200 */         if (value == null) {
/* 201 */           clearValue(key, hash, computingValueReference);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ComputationExceptionReference<K, V>
/*     */     implements CustomConcurrentHashMap.ValueReference<K, V>
/*     */   {
/*     */     final Throwable t;
/*     */     
/*     */     ComputationExceptionReference(Throwable t) {
/* 214 */       this.t = t;
/*     */     }
/*     */ 
/*     */     
/*     */     public V get() {
/* 219 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry() {
/* 224 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 229 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isComputingReference() {
/* 234 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public V waitForValue() throws ExecutionException {
/* 239 */       throw new ExecutionException(this.t);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear(CustomConcurrentHashMap.ValueReference<K, V> newValue) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ComputedReference<K, V>
/*     */     implements CustomConcurrentHashMap.ValueReference<K, V>
/*     */   {
/*     */     final V value;
/*     */     
/*     */     ComputedReference(@Nullable V value) {
/* 253 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public V get() {
/* 258 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry() {
/* 263 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 268 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isComputingReference() {
/* 273 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public V waitForValue() {
/* 278 */       return get();
/*     */     }
/*     */     
/*     */     public void clear(CustomConcurrentHashMap.ValueReference<K, V> newValue) {}
/*     */   }
/*     */   
/*     */   private static final class ComputingValueReference<K, V>
/*     */     implements CustomConcurrentHashMap.ValueReference<K, V> {
/*     */     final Function<? super K, ? extends V> computingFunction;
/*     */     @GuardedBy("ComputingValueReference.this")
/* 288 */     volatile CustomConcurrentHashMap.ValueReference<K, V> computedReference = CustomConcurrentHashMap.unset();
/*     */ 
/*     */     
/*     */     public ComputingValueReference(Function<? super K, ? extends V> computingFunction) {
/* 292 */       this.computingFunction = computingFunction;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public V get() {
/* 299 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry() {
/* 304 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 309 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isComputingReference() {
/* 314 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public V waitForValue() throws ExecutionException {
/* 322 */       if (this.computedReference == CustomConcurrentHashMap.UNSET) {
/* 323 */         boolean interrupted = false;
/*     */         try {
/* 325 */           synchronized (this) {
/* 326 */             while (this.computedReference == CustomConcurrentHashMap.UNSET) {
/*     */               try {
/* 328 */                 wait();
/* 329 */               } catch (InterruptedException ie) {
/* 330 */                 interrupted = true;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } finally {
/* 335 */           if (interrupted) {
/* 336 */             Thread.currentThread().interrupt();
/*     */           }
/*     */         } 
/*     */       } 
/* 340 */       return this.computedReference.waitForValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear(CustomConcurrentHashMap.ValueReference<K, V> newValue) {
/* 347 */       setValueReference(newValue);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     V compute(K key, int hash) throws ExecutionException {
/*     */       V value;
/*     */       try {
/* 355 */         value = (V)this.computingFunction.apply(key);
/* 356 */       } catch (Throwable t) {
/* 357 */         setValueReference(new ComputingConcurrentHashMap.ComputationExceptionReference<K, V>(t));
/* 358 */         throw new ExecutionException(t);
/*     */       } 
/*     */       
/* 361 */       setValueReference(new ComputingConcurrentHashMap.ComputedReference<K, V>(value));
/* 362 */       return value;
/*     */     }
/*     */     
/*     */     void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 366 */       synchronized (this) {
/* 367 */         if (this.computedReference == CustomConcurrentHashMap.UNSET) {
/* 368 */           this.computedReference = valueReference;
/* 369 */           notifyAll();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ComputingMapAdapter<K, V>
/*     */     extends ComputingConcurrentHashMap<K, V>
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     ComputingMapAdapter(MapMaker mapMaker, Function<? super K, ? extends V> computingFunction) {
/* 385 */       super(mapMaker, computingFunction);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public V get(Object key) {
/*     */       V value;
/*     */       try {
/* 393 */         value = getOrCompute((K)key);
/* 394 */       } catch (ExecutionException e) {
/* 395 */         Throwable cause = e.getCause();
/* 396 */         Throwables.propagateIfInstanceOf(cause, ComputationException.class);
/* 397 */         throw new ComputationException(cause);
/*     */       } 
/*     */       
/* 400 */       if (value == null) {
/* 401 */         throw new NullPointerException(this.computingFunction + " returned null for key " + key + ".");
/*     */       }
/* 403 */       return value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/* 413 */     return new ComputingSerializationProxy<K, V>(this.keyStrength, this.valueStrength, this.keyEquivalence, this.valueEquivalence, this.expireAfterWriteNanos, this.expireAfterAccessNanos, this.maximumSize, this.concurrencyLevel, this.removalListener, this, this.computingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ComputingSerializationProxy<K, V>
/*     */     extends CustomConcurrentHashMap.AbstractSerializationProxy<K, V>
/*     */   {
/*     */     final Function<? super K, ? extends V> computingFunction;
/*     */     
/*     */     private static final long serialVersionUID = 4L;
/*     */ 
/*     */     
/*     */     ComputingSerializationProxy(CustomConcurrentHashMap.Strength keyStrength, CustomConcurrentHashMap.Strength valueStrength, Equivalence<Object> keyEquivalence, Equivalence<Object> valueEquivalence, long expireAfterWriteNanos, long expireAfterAccessNanos, int maximumSize, int concurrencyLevel, MapMaker.RemovalListener<? super K, ? super V> removalListener, ConcurrentMap<K, V> delegate, Function<? super K, ? extends V> computingFunction) {
/* 427 */       super(keyStrength, valueStrength, keyEquivalence, valueEquivalence, expireAfterWriteNanos, expireAfterAccessNanos, maximumSize, concurrencyLevel, removalListener, delegate);
/*     */       
/* 429 */       this.computingFunction = computingFunction;
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream out) throws IOException {
/* 433 */       out.defaultWriteObject();
/* 434 */       writeMapTo(out);
/*     */     }
/*     */ 
/*     */     
/*     */     private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 439 */       in.defaultReadObject();
/* 440 */       MapMaker mapMaker = readMapMaker(in);
/* 441 */       this.delegate = mapMaker.makeComputingMap(this.computingFunction);
/* 442 */       readEntries(in);
/*     */     }
/*     */     
/*     */     Object readResolve() {
/* 446 */       return this.delegate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ComputingConcurrentHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */