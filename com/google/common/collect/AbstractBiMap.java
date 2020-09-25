/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ abstract class AbstractBiMap<K, V>
/*     */   extends ForwardingMap<K, V>
/*     */   implements BiMap<K, V>, Serializable
/*     */ {
/*     */   private transient Map<K, V> delegate;
/*     */   private transient AbstractBiMap<V, K> inverse;
/*     */   private transient Set<K> keySet;
/*     */   private transient Set<V> valueSet;
/*     */   private transient Set<Map.Entry<K, V>> entrySet;
/*     */   @GwtIncompatible("Not needed in emulated source.")
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   AbstractBiMap(Map<K, V> forward, Map<V, K> backward) {
/*  56 */     setDelegates(forward, backward);
/*     */   }
/*     */ 
/*     */   
/*     */   private AbstractBiMap(Map<K, V> backward, AbstractBiMap<V, K> forward) {
/*  61 */     this.delegate = backward;
/*  62 */     this.inverse = forward;
/*     */   }
/*     */   
/*     */   protected Map<K, V> delegate() {
/*  66 */     return this.delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setDelegates(Map<K, V> forward, Map<V, K> backward) {
/*  74 */     Preconditions.checkState((this.delegate == null));
/*  75 */     Preconditions.checkState((this.inverse == null));
/*  76 */     Preconditions.checkArgument(forward.isEmpty());
/*  77 */     Preconditions.checkArgument(backward.isEmpty());
/*  78 */     Preconditions.checkArgument((forward != backward));
/*  79 */     this.delegate = forward;
/*  80 */     this.inverse = new Inverse<V, K>(backward, this);
/*     */   }
/*     */   
/*     */   void setInverse(AbstractBiMap<V, K> inverse) {
/*  84 */     this.inverse = inverse;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/*  90 */     return this.inverse.containsKey(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(K key, V value) {
/*  96 */     return putInBothMaps(key, value, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public V forcePut(K key, V value) {
/* 101 */     return putInBothMaps(key, value, true);
/*     */   }
/*     */   
/*     */   private V putInBothMaps(@Nullable K key, @Nullable V value, boolean force) {
/* 105 */     boolean containedKey = containsKey(key);
/* 106 */     if (containedKey && Objects.equal(value, get(key))) {
/* 107 */       return value;
/*     */     }
/* 109 */     if (force) {
/* 110 */       inverse().remove(value);
/*     */     } else {
/* 112 */       Preconditions.checkArgument(!containsValue(value), "value already present: %s", new Object[] { value });
/*     */     } 
/* 114 */     V oldValue = this.delegate.put(key, value);
/* 115 */     updateInverseMap(key, containedKey, oldValue, value);
/* 116 */     return oldValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateInverseMap(K key, boolean containedKey, V oldValue, V newValue) {
/* 121 */     if (containedKey) {
/* 122 */       removeFromInverseMap(oldValue);
/*     */     }
/* 124 */     this.inverse.delegate.put((K)newValue, (V)key);
/*     */   }
/*     */   
/*     */   public V remove(Object key) {
/* 128 */     return containsKey(key) ? removeFromBothMaps(key) : null;
/*     */   }
/*     */   
/*     */   private V removeFromBothMaps(Object key) {
/* 132 */     V oldValue = this.delegate.remove(key);
/* 133 */     removeFromInverseMap(oldValue);
/* 134 */     return oldValue;
/*     */   }
/*     */   
/*     */   private void removeFromInverseMap(V oldValue) {
/* 138 */     this.inverse.delegate.remove(oldValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> map) {
/* 144 */     for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 145 */       put(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/* 150 */     this.delegate.clear();
/* 151 */     this.inverse.delegate.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiMap<V, K> inverse() {
/* 158 */     return this.inverse;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<K> keySet() {
/* 164 */     Set<K> result = this.keySet;
/* 165 */     return (result == null) ? (this.keySet = new KeySet()) : result;
/*     */   }
/*     */   
/*     */   private class KeySet extends ForwardingSet<K> {
/*     */     protected Set<K> delegate() {
/* 170 */       return AbstractBiMap.this.delegate.keySet();
/*     */     }
/*     */     private KeySet() {}
/*     */     public void clear() {
/* 174 */       AbstractBiMap.this.clear();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key) {
/* 178 */       if (!contains(key)) {
/* 179 */         return false;
/*     */       }
/* 181 */       AbstractBiMap.this.removeFromBothMaps(key);
/* 182 */       return true;
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> keysToRemove) {
/* 186 */       return standardRemoveAll(keysToRemove);
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> keysToRetain) {
/* 190 */       return standardRetainAll(keysToRetain);
/*     */     }
/*     */     
/*     */     public Iterator<K> iterator() {
/* 194 */       final Iterator<Map.Entry<K, V>> iterator = AbstractBiMap.this.delegate.entrySet().iterator();
/* 195 */       return new Iterator<K>()
/*     */         {
/*     */           Map.Entry<K, V> entry;
/*     */           
/*     */           public boolean hasNext() {
/* 200 */             return iterator.hasNext();
/*     */           }
/*     */           
/*     */           public K next() {
/* 204 */             this.entry = iterator.next();
/* 205 */             return this.entry.getKey();
/*     */           }
/*     */           
/*     */           public void remove() {
/* 209 */             Preconditions.checkState((this.entry != null));
/* 210 */             V value = this.entry.getValue();
/* 211 */             iterator.remove();
/* 212 */             AbstractBiMap.this.removeFromInverseMap(value);
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<V> values() {
/* 225 */     Set<V> result = this.valueSet;
/* 226 */     return (result == null) ? (this.valueSet = new ValueSet()) : result;
/*     */   }
/*     */   
/*     */   private class ValueSet extends ForwardingSet<V> {
/* 230 */     final Set<V> valuesDelegate = AbstractBiMap.this.inverse.keySet();
/*     */     
/*     */     protected Set<V> delegate() {
/* 233 */       return this.valuesDelegate;
/*     */     }
/*     */     
/*     */     public Iterator<V> iterator() {
/* 237 */       final Iterator<V> iterator = AbstractBiMap.this.delegate.values().iterator();
/* 238 */       return new Iterator<V>() {
/*     */           V valueToRemove;
/*     */           
/*     */           public boolean hasNext() {
/* 242 */             return iterator.hasNext();
/*     */           }
/*     */           
/*     */           public V next() {
/* 246 */             return this.valueToRemove = (V)iterator.next();
/*     */           }
/*     */           
/*     */           public void remove() {
/* 250 */             iterator.remove();
/* 251 */             AbstractBiMap.this.removeFromInverseMap(this.valueToRemove);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/* 257 */       return standardToArray();
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/* 261 */       return (T[])standardToArray((Object[])array);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 265 */       return standardToString();
/*     */     }
/*     */     
/*     */     private ValueSet() {}
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 272 */     Set<Map.Entry<K, V>> result = this.entrySet;
/* 273 */     return (result == null) ? (this.entrySet = new EntrySet()) : result;
/*     */   }
/*     */   
/*     */   private class EntrySet extends ForwardingSet<Map.Entry<K, V>> {
/* 277 */     final Set<Map.Entry<K, V>> esDelegate = AbstractBiMap.this.delegate.entrySet();
/*     */     
/*     */     protected Set<Map.Entry<K, V>> delegate() {
/* 280 */       return this.esDelegate;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 284 */       AbstractBiMap.this.clear();
/*     */     }
/*     */     
/*     */     public boolean remove(Object object) {
/* 288 */       if (!this.esDelegate.contains(object)) {
/* 289 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 293 */       Map.Entry<?, ?> entry = (Map.Entry<?, ?>)object;
/* 294 */       AbstractBiMap.this.inverse.delegate.remove(entry.getValue());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 300 */       this.esDelegate.remove(entry);
/* 301 */       return true;
/*     */     }
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 305 */       final Iterator<Map.Entry<K, V>> iterator = this.esDelegate.iterator();
/* 306 */       return new Iterator<Map.Entry<K, V>>() {
/*     */           Map.Entry<K, V> entry;
/*     */           
/*     */           public boolean hasNext() {
/* 310 */             return iterator.hasNext();
/*     */           }
/*     */           
/*     */           public Map.Entry<K, V> next() {
/* 314 */             this.entry = iterator.next();
/* 315 */             final Map.Entry<K, V> finalEntry = this.entry;
/*     */             
/* 317 */             return new ForwardingMapEntry<K, V>() {
/*     */                 protected Map.Entry<K, V> delegate() {
/* 319 */                   return finalEntry;
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public V setValue(V value) {
/* 324 */                   Preconditions.checkState(AbstractBiMap.EntrySet.this.contains(this), "entry no longer in map");
/*     */                   
/* 326 */                   if (Objects.equal(value, getValue())) {
/* 327 */                     return value;
/*     */                   }
/* 329 */                   Preconditions.checkArgument(!AbstractBiMap.this.containsValue(value), "value already present: %s", new Object[] { value });
/*     */                   
/* 331 */                   V oldValue = (V)finalEntry.setValue(value);
/* 332 */                   Preconditions.checkState(Objects.equal(value, AbstractBiMap.this.get(getKey())), "entry no longer in map");
/*     */                   
/* 334 */                   AbstractBiMap.this.updateInverseMap(getKey(), true, oldValue, value);
/* 335 */                   return oldValue;
/*     */                 }
/*     */               };
/*     */           }
/*     */           
/*     */           public void remove() {
/* 341 */             Preconditions.checkState((this.entry != null));
/* 342 */             V value = this.entry.getValue();
/* 343 */             iterator.remove();
/* 344 */             AbstractBiMap.this.removeFromInverseMap(value);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 352 */       return standardToArray();
/*     */     }
/*     */     public <T> T[] toArray(T[] array) {
/* 355 */       return (T[])standardToArray((Object[])array);
/*     */     }
/*     */     public boolean contains(Object o) {
/* 358 */       return Maps.containsEntryImpl(delegate(), o);
/*     */     }
/*     */     public boolean containsAll(Collection<?> c) {
/* 361 */       return standardContainsAll(c);
/*     */     }
/*     */     public boolean removeAll(Collection<?> c) {
/* 364 */       return standardRemoveAll(c);
/*     */     }
/*     */     public boolean retainAll(Collection<?> c) {
/* 367 */       return standardRetainAll(c);
/*     */     }
/*     */     
/*     */     private EntrySet() {} }
/*     */   
/*     */   private static class Inverse<K, V> extends AbstractBiMap<K, V> {
/*     */     private Inverse(Map<K, V> backward, AbstractBiMap<V, K> forward) {
/* 374 */       super(backward, forward);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @GwtIncompatible("Not needed in emulated source.")
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @GwtIncompatible("java.io.ObjectOuputStream")
/*     */     private void writeObject(ObjectOutputStream stream) throws IOException {
/* 391 */       stream.defaultWriteObject();
/* 392 */       stream.writeObject(inverse());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @GwtIncompatible("java.io.ObjectInputStream")
/*     */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 399 */       stream.defaultReadObject();
/* 400 */       setInverse((AbstractBiMap<V, K>)stream.readObject());
/*     */     }
/*     */     
/*     */     @GwtIncompatible("Not needed in the emulated source.")
/*     */     Object readResolve() {
/* 405 */       return inverse().inverse();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractBiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */