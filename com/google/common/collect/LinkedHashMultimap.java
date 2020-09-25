/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class LinkedHashMultimap<K, V>
/*     */   extends AbstractSetMultimap<K, V>
/*     */ {
/*     */   private static final int DEFAULT_VALUES_PER_KEY = 8;
/*     */   @VisibleForTesting
/*  75 */   transient int expectedValuesPerKey = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   transient Collection<Map.Entry<K, V>> linkedEntries;
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java serialization not supported")
/*     */   private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> LinkedHashMultimap<K, V> create() {
/*  90 */     return new LinkedHashMultimap<K, V>();
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
/*     */   public static <K, V> LinkedHashMultimap<K, V> create(int expectedKeys, int expectedValuesPerKey) {
/* 104 */     return new LinkedHashMultimap<K, V>(expectedKeys, expectedValuesPerKey);
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
/*     */   public static <K, V> LinkedHashMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
/* 118 */     return new LinkedHashMultimap<K, V>(multimap);
/*     */   }
/*     */   
/*     */   private LinkedHashMultimap() {
/* 122 */     super(new LinkedHashMap<K, Collection<V>>());
/* 123 */     this.linkedEntries = Sets.newLinkedHashSet();
/*     */   }
/*     */   
/*     */   private LinkedHashMultimap(int expectedKeys, int expectedValuesPerKey) {
/* 127 */     super(new LinkedHashMap<K, Collection<V>>(expectedKeys));
/* 128 */     Preconditions.checkArgument((expectedValuesPerKey >= 0));
/* 129 */     this.expectedValuesPerKey = expectedValuesPerKey;
/* 130 */     this.linkedEntries = new LinkedHashSet<Map.Entry<K, V>>((int)Math.min(1073741824L, expectedKeys * expectedValuesPerKey));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private LinkedHashMultimap(Multimap<? extends K, ? extends V> multimap) {
/* 136 */     super(new LinkedHashMap<K, Collection<V>>(Maps.capacity(multimap.keySet().size())));
/*     */     
/* 138 */     this.linkedEntries = new LinkedHashSet<Map.Entry<K, V>>(Maps.capacity(multimap.size()));
/*     */     
/* 140 */     putAll(multimap);
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
/*     */   Set<V> createCollection() {
/* 153 */     return new LinkedHashSet<V>(Maps.capacity(this.expectedValuesPerKey));
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
/*     */   Collection<V> createCollection(@Nullable K key) {
/* 167 */     return new SetDecorator(key, createCollection());
/*     */   }
/*     */   
/*     */   private class SetDecorator extends ForwardingSet<V> {
/*     */     final Set<V> delegate;
/*     */     final K key;
/*     */     
/*     */     SetDecorator(K key, Set<V> delegate) {
/* 175 */       this.delegate = delegate;
/* 176 */       this.key = key;
/*     */     }
/*     */     
/*     */     protected Set<V> delegate() {
/* 180 */       return this.delegate;
/*     */     }
/*     */     
/*     */     <E> Map.Entry<K, E> createEntry(@Nullable E value) {
/* 184 */       return Maps.immutableEntry(this.key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     <E> Collection<Map.Entry<K, E>> createEntries(Collection<E> values) {
/* 189 */       Collection<Map.Entry<K, E>> entries = Lists.newArrayListWithExpectedSize(values.size());
/*     */       
/* 191 */       for (E value : values) {
/* 192 */         entries.add(createEntry(value));
/*     */       }
/* 194 */       return entries;
/*     */     }
/*     */     
/*     */     public boolean add(@Nullable V value) {
/* 198 */       boolean changed = this.delegate.add(value);
/* 199 */       if (changed) {
/* 200 */         LinkedHashMultimap.this.linkedEntries.add(createEntry(value));
/*     */       }
/* 202 */       return changed;
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends V> values) {
/* 206 */       boolean changed = this.delegate.addAll(values);
/* 207 */       if (changed) {
/* 208 */         LinkedHashMultimap.this.linkedEntries.addAll(createEntries(delegate()));
/*     */       }
/* 210 */       return changed;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 214 */       for (V value : this.delegate) {
/* 215 */         LinkedHashMultimap.this.linkedEntries.remove(createEntry(value));
/*     */       }
/* 217 */       this.delegate.clear();
/*     */     }
/*     */     
/*     */     public Iterator<V> iterator() {
/* 221 */       final Iterator<V> delegateIterator = this.delegate.iterator();
/* 222 */       return new Iterator<V>()
/*     */         {
/*     */           V value;
/*     */           
/*     */           public boolean hasNext() {
/* 227 */             return delegateIterator.hasNext();
/*     */           }
/*     */           
/*     */           public V next() {
/* 231 */             this.value = delegateIterator.next();
/* 232 */             return this.value;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 236 */             delegateIterator.remove();
/* 237 */             LinkedHashMultimap.this.linkedEntries.remove(LinkedHashMultimap.SetDecorator.this.createEntry(this.value));
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean remove(@Nullable Object value) {
/* 243 */       boolean changed = this.delegate.remove(value);
/* 244 */       if (changed)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 249 */         LinkedHashMultimap.this.linkedEntries.remove(createEntry(value));
/*     */       }
/* 251 */       return changed;
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> values) {
/* 255 */       boolean changed = this.delegate.removeAll(values);
/* 256 */       if (changed) {
/* 257 */         LinkedHashMultimap.this.linkedEntries.removeAll(createEntries(values));
/*     */       }
/* 259 */       return changed;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> values) {
/* 267 */       boolean changed = false;
/* 268 */       Iterator<V> iterator = this.delegate.iterator();
/* 269 */       while (iterator.hasNext()) {
/* 270 */         V value = iterator.next();
/* 271 */         if (!values.contains(value)) {
/* 272 */           iterator.remove();
/* 273 */           LinkedHashMultimap.this.linkedEntries.remove(Maps.immutableEntry(this.key, value));
/* 274 */           changed = true;
/*     */         } 
/*     */       } 
/* 277 */       return changed;
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
/*     */   Iterator<Map.Entry<K, V>> createEntryIterator() {
/* 290 */     final Iterator<Map.Entry<K, V>> delegateIterator = this.linkedEntries.iterator();
/*     */     
/* 292 */     return new Iterator<Map.Entry<K, V>>()
/*     */       {
/*     */         Map.Entry<K, V> entry;
/*     */         
/*     */         public boolean hasNext() {
/* 297 */           return delegateIterator.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public Map.Entry<K, V> next() {
/* 302 */           this.entry = delegateIterator.next();
/* 303 */           return this.entry;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void remove() {
/* 309 */           delegateIterator.remove();
/* 310 */           LinkedHashMultimap.this.remove(this.entry.getKey(), this.entry.getValue());
/*     */         }
/*     */       };
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
/*     */   public Set<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
/* 325 */     return super.replaceValues(key, values);
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
/*     */   public Set<Map.Entry<K, V>> entries() {
/* 341 */     return super.entries();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<V> values() {
/* 352 */     return super.values();
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
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 365 */     stream.defaultWriteObject();
/* 366 */     stream.writeInt(this.expectedValuesPerKey);
/* 367 */     Serialization.writeMultimap(this, stream);
/* 368 */     for (Map.Entry<K, V> entry : this.linkedEntries) {
/* 369 */       stream.writeObject(entry.getKey());
/* 370 */       stream.writeObject(entry.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 377 */     stream.defaultReadObject();
/* 378 */     this.expectedValuesPerKey = stream.readInt();
/* 379 */     int distinctKeys = Serialization.readCount(stream);
/* 380 */     setMap(new LinkedHashMap<K, Collection<V>>(Maps.capacity(distinctKeys)));
/* 381 */     this.linkedEntries = new LinkedHashSet<Map.Entry<K, V>>(distinctKeys * this.expectedValuesPerKey);
/*     */     
/* 383 */     Serialization.populateMultimap(this, stream, distinctKeys);
/* 384 */     this.linkedEntries.clear();
/* 385 */     for (int i = 0; i < size(); i++) {
/*     */       
/* 387 */       K key = (K)stream.readObject();
/*     */       
/* 389 */       V value = (V)stream.readObject();
/* 390 */       this.linkedEntries.add(Maps.immutableEntry(key, value));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\LinkedHashMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */