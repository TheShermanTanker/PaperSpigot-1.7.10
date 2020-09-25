/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.ConcurrentModificationException;
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
/*     */ 
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
/*     */ abstract class AbstractMapBasedMultiset<E>
/*     */   extends AbstractMultiset<E>
/*     */   implements Serializable
/*     */ {
/*     */   private transient Map<E, Count> backingMap;
/*     */   private transient long size;
/*     */   @GwtIncompatible("not needed in emulated source.")
/*     */   private static final long serialVersionUID = -2250766705698539974L;
/*     */   
/*     */   protected AbstractMapBasedMultiset(Map<E, Count> backingMap) {
/*  63 */     this.backingMap = (Map<E, Count>)Preconditions.checkNotNull(backingMap);
/*  64 */     this.size = super.size();
/*     */   }
/*     */   
/*     */   Map<E, Count> backingMap() {
/*  68 */     return this.backingMap;
/*     */   }
/*     */ 
/*     */   
/*     */   void setBackingMap(Map<E, Count> backingMap) {
/*  73 */     this.backingMap = backingMap;
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
/*     */   public Set<Multiset.Entry<E>> entrySet() {
/*  87 */     return super.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   Iterator<Multiset.Entry<E>> entryIterator() {
/*  92 */     final Iterator<Map.Entry<E, Count>> backingEntries = this.backingMap.entrySet().iterator();
/*     */     
/*  94 */     return (Iterator)new Iterator<Multiset.Entry<Multiset.Entry<E>>>()
/*     */       {
/*     */         Map.Entry<E, Count> toRemove;
/*     */         
/*     */         public boolean hasNext() {
/*  99 */           return backingEntries.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public Multiset.Entry<E> next() {
/* 104 */           final Map.Entry<E, Count> mapEntry = backingEntries.next();
/* 105 */           this.toRemove = mapEntry;
/* 106 */           return new Multisets.AbstractEntry<E>()
/*     */             {
/*     */               public E getElement() {
/* 109 */                 return (E)mapEntry.getKey();
/*     */               }
/*     */               
/*     */               public int getCount() {
/* 113 */                 int count = ((Count)mapEntry.getValue()).get();
/* 114 */                 if (count == 0) {
/* 115 */                   Count frequency = (Count)AbstractMapBasedMultiset.this.backingMap.get(getElement());
/* 116 */                   if (frequency != null) {
/* 117 */                     count = frequency.get();
/*     */                   }
/*     */                 } 
/* 120 */                 return count;
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 127 */           Preconditions.checkState((this.toRemove != null), "no calls to next() since the last call to remove()");
/*     */           
/* 129 */           AbstractMapBasedMultiset.this.size -= ((Count)this.toRemove.getValue()).getAndSet(0);
/* 130 */           backingEntries.remove();
/* 131 */           this.toRemove = null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 138 */     for (Count frequency : this.backingMap.values()) {
/* 139 */       frequency.set(0);
/*     */     }
/* 141 */     this.backingMap.clear();
/* 142 */     this.size = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   int distinctElements() {
/* 147 */     return this.backingMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 153 */     return Ints.saturatedCast(this.size);
/*     */   }
/*     */   
/*     */   public Iterator<E> iterator() {
/* 157 */     return new MapBasedMultisetIterator();
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
/*     */   private class MapBasedMultisetIterator
/*     */     implements Iterator<E>
/*     */   {
/* 172 */     final Iterator<Map.Entry<E, Count>> entryIterator = AbstractMapBasedMultiset.this.backingMap.entrySet().iterator();
/*     */     
/*     */     Map.Entry<E, Count> currentEntry;
/*     */     
/*     */     public boolean hasNext() {
/* 177 */       return (this.occurrencesLeft > 0 || this.entryIterator.hasNext());
/*     */     }
/*     */     int occurrencesLeft; boolean canRemove;
/*     */     
/*     */     public E next() {
/* 182 */       if (this.occurrencesLeft == 0) {
/* 183 */         this.currentEntry = this.entryIterator.next();
/* 184 */         this.occurrencesLeft = ((Count)this.currentEntry.getValue()).get();
/*     */       } 
/* 186 */       this.occurrencesLeft--;
/* 187 */       this.canRemove = true;
/* 188 */       return this.currentEntry.getKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 193 */       Preconditions.checkState(this.canRemove, "no calls to next() since the last call to remove()");
/*     */       
/* 195 */       int frequency = ((Count)this.currentEntry.getValue()).get();
/* 196 */       if (frequency <= 0) {
/* 197 */         throw new ConcurrentModificationException();
/*     */       }
/* 199 */       if (((Count)this.currentEntry.getValue()).addAndGet(-1) == 0) {
/* 200 */         this.entryIterator.remove();
/*     */       }
/* 202 */       AbstractMapBasedMultiset.this.size--;
/* 203 */       this.canRemove = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public int count(@Nullable Object element) {
/*     */     try {
/* 209 */       Count frequency = this.backingMap.get(element);
/* 210 */       return (frequency == null) ? 0 : frequency.get();
/* 211 */     } catch (NullPointerException e) {
/* 212 */       return 0;
/* 213 */     } catch (ClassCastException e) {
/* 214 */       return 0;
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
/*     */   public int add(@Nullable E element, int occurrences) {
/*     */     int oldCount;
/* 228 */     if (occurrences == 0) {
/* 229 */       return count(element);
/*     */     }
/* 231 */     Preconditions.checkArgument((occurrences > 0), "occurrences cannot be negative: %s", new Object[] { Integer.valueOf(occurrences) });
/*     */     
/* 233 */     Count frequency = this.backingMap.get(element);
/*     */     
/* 235 */     if (frequency == null) {
/* 236 */       oldCount = 0;
/* 237 */       this.backingMap.put(element, new Count(occurrences));
/*     */     } else {
/* 239 */       oldCount = frequency.get();
/* 240 */       long newCount = oldCount + occurrences;
/* 241 */       Preconditions.checkArgument((newCount <= 2147483647L), "too many occurrences: %s", new Object[] { Long.valueOf(newCount) });
/*     */       
/* 243 */       frequency.getAndAdd(occurrences);
/*     */     } 
/* 245 */     this.size += occurrences;
/* 246 */     return oldCount;
/*     */   }
/*     */   public int remove(@Nullable Object element, int occurrences) {
/*     */     int numberRemoved;
/* 250 */     if (occurrences == 0) {
/* 251 */       return count(element);
/*     */     }
/* 253 */     Preconditions.checkArgument((occurrences > 0), "occurrences cannot be negative: %s", new Object[] { Integer.valueOf(occurrences) });
/*     */     
/* 255 */     Count frequency = this.backingMap.get(element);
/* 256 */     if (frequency == null) {
/* 257 */       return 0;
/*     */     }
/*     */     
/* 260 */     int oldCount = frequency.get();
/*     */ 
/*     */     
/* 263 */     if (oldCount > occurrences) {
/* 264 */       numberRemoved = occurrences;
/*     */     } else {
/* 266 */       numberRemoved = oldCount;
/* 267 */       this.backingMap.remove(element);
/*     */     } 
/*     */     
/* 270 */     frequency.addAndGet(-numberRemoved);
/* 271 */     this.size -= numberRemoved;
/* 272 */     return oldCount;
/*     */   }
/*     */   
/*     */   public int setCount(E element, int count) {
/*     */     int oldCount;
/* 277 */     Multisets.checkNonnegative(count, "count");
/*     */ 
/*     */ 
/*     */     
/* 281 */     if (count == 0) {
/* 282 */       Count existingCounter = this.backingMap.remove(element);
/* 283 */       oldCount = getAndSet(existingCounter, count);
/*     */     } else {
/* 285 */       Count existingCounter = this.backingMap.get(element);
/* 286 */       oldCount = getAndSet(existingCounter, count);
/*     */       
/* 288 */       if (existingCounter == null) {
/* 289 */         this.backingMap.put(element, new Count(count));
/*     */       }
/*     */     } 
/*     */     
/* 293 */     this.size += (count - oldCount);
/* 294 */     return oldCount;
/*     */   }
/*     */   
/*     */   private static int getAndSet(Count i, int count) {
/* 298 */     if (i == null) {
/* 299 */       return 0;
/*     */     }
/*     */     
/* 302 */     return i.getAndSet(count);
/*     */   }
/*     */ 
/*     */   
/*     */   private int removeAllOccurrences(@Nullable Object element, Map<E, Count> map) {
/* 307 */     Count frequency = map.remove(element);
/* 308 */     if (frequency == null) {
/* 309 */       return 0;
/*     */     }
/* 311 */     int numberRemoved = frequency.getAndSet(0);
/* 312 */     this.size -= numberRemoved;
/* 313 */     return numberRemoved;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Set<E> createElementSet() {
/* 319 */     return new MapBasedElementSet(this.backingMap);
/*     */   }
/*     */ 
/*     */   
/*     */   class MapBasedElementSet
/*     */     extends ForwardingSet<E>
/*     */   {
/*     */     private final Map<E, Count> map;
/*     */     
/*     */     private final Set<E> delegate;
/*     */ 
/*     */     
/*     */     MapBasedElementSet(Map<E, Count> map) {
/* 332 */       this.map = map;
/* 333 */       this.delegate = map.keySet();
/*     */     }
/*     */     
/*     */     protected Set<E> delegate() {
/* 337 */       return this.delegate;
/*     */     }
/*     */     
/*     */     public Iterator<E> iterator() {
/* 341 */       final Iterator<Map.Entry<E, Count>> entries = this.map.entrySet().iterator();
/*     */       
/* 343 */       return new Iterator<E>()
/*     */         {
/*     */           Map.Entry<E, Count> toRemove;
/*     */           
/*     */           public boolean hasNext() {
/* 348 */             return entries.hasNext();
/*     */           }
/*     */ 
/*     */           
/*     */           public E next() {
/* 353 */             this.toRemove = entries.next();
/* 354 */             return this.toRemove.getKey();
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 359 */             Preconditions.checkState((this.toRemove != null), "no calls to next() since the last call to remove()");
/*     */             
/* 361 */             AbstractMapBasedMultiset.this.size -= ((Count)this.toRemove.getValue()).getAndSet(0);
/* 362 */             entries.remove();
/* 363 */             this.toRemove = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean remove(Object element) {
/* 369 */       return (AbstractMapBasedMultiset.this.removeAllOccurrences(element, this.map) != 0);
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> elementsToRemove) {
/* 373 */       return Iterators.removeAll(iterator(), elementsToRemove);
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> elementsToRetain) {
/* 377 */       return Iterators.retainAll(iterator(), elementsToRetain);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 381 */       if (this.map == AbstractMapBasedMultiset.this.backingMap) {
/* 382 */         AbstractMapBasedMultiset.this.clear();
/*     */       } else {
/* 384 */         Iterator<E> i = iterator();
/* 385 */         while (i.hasNext()) {
/* 386 */           i.next();
/* 387 */           i.remove();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map<E, Count> getMap() {
/* 393 */       return this.map;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectStreamException")
/*     */   private void readObjectNoData() throws ObjectStreamException {
/* 401 */     throw new InvalidObjectException("Stream data required");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractMapBasedMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */