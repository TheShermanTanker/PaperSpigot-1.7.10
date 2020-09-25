/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public final class ConcurrentHashMultiset<E>
/*     */   extends AbstractMultiset<E>
/*     */   implements Serializable
/*     */ {
/*     */   private final transient ConcurrentMap<E, AtomicInteger> countMap;
/*     */   private transient EntrySet entrySet;
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private static class FieldSettersHolder
/*     */   {
/*  70 */     static final Serialization.FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");
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
/*     */   public static <E> ConcurrentHashMultiset<E> create() {
/*  82 */     return new ConcurrentHashMultiset<E>(new ConcurrentHashMap<E, AtomicInteger>());
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
/*     */   public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> elements) {
/*  96 */     ConcurrentHashMultiset<E> multiset = create();
/*  97 */     Iterables.addAll(multiset, elements);
/*  98 */     return multiset;
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
/*     */   @Beta
/*     */   public static <E> ConcurrentHashMultiset<E> create(GenericMapMaker<? super E, ? super Number> mapMaker) {
/* 124 */     return new ConcurrentHashMultiset<E>(mapMaker.makeMap());
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
/*     */   @VisibleForTesting
/*     */   ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> countMap) {
/* 139 */     Preconditions.checkArgument(countMap.isEmpty());
/* 140 */     this.countMap = countMap;
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
/*     */   public int count(@Nullable Object element) {
/* 152 */     AtomicInteger existingCounter = safeGet(element);
/* 153 */     return (existingCounter == null) ? 0 : existingCounter.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AtomicInteger safeGet(Object element) {
/*     */     try {
/* 163 */       return this.countMap.get(element);
/* 164 */     } catch (NullPointerException e) {
/* 165 */       return null;
/* 166 */     } catch (ClassCastException e) {
/* 167 */       return null;
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
/*     */   public int size() {
/* 179 */     long sum = 0L;
/* 180 */     for (AtomicInteger value : this.countMap.values()) {
/* 181 */       sum += value.get();
/*     */     }
/* 183 */     return Ints.saturatedCast(sum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 192 */     return snapshot().toArray();
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] array) {
/* 196 */     return snapshot().toArray(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<E> snapshot() {
/* 204 */     List<E> list = Lists.newArrayListWithExpectedSize(size());
/* 205 */     for (Multiset.Entry<E> entry : entrySet()) {
/* 206 */       E element = entry.getElement();
/* 207 */       for (int i = entry.getCount(); i > 0; i--) {
/* 208 */         list.add(element);
/*     */       }
/*     */     } 
/* 211 */     return list;
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
/*     */   public int add(E element, int occurrences) {
/*     */     AtomicInteger existingCounter, newCounter;
/* 227 */     if (occurrences == 0) {
/* 228 */       return count(element);
/*     */     }
/* 230 */     Preconditions.checkArgument((occurrences > 0), "Invalid occurrences: %s", new Object[] { Integer.valueOf(occurrences) });
/*     */     
/*     */     do {
/* 233 */       existingCounter = safeGet(element);
/* 234 */       if (existingCounter == null) {
/* 235 */         existingCounter = this.countMap.putIfAbsent(element, new AtomicInteger(occurrences));
/* 236 */         if (existingCounter == null) {
/* 237 */           return 0;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*     */       while (true) {
/* 243 */         int oldValue = existingCounter.get();
/* 244 */         if (oldValue != 0) {
/* 245 */           Preconditions.checkArgument((occurrences <= Integer.MAX_VALUE - oldValue), "Overflow adding %s occurrences to a count of %s", new Object[] { Integer.valueOf(occurrences), Integer.valueOf(oldValue) });
/*     */ 
/*     */           
/* 248 */           int newValue = oldValue + occurrences;
/* 249 */           if (existingCounter.compareAndSet(oldValue, newValue))
/*     */           {
/* 251 */             return oldValue;
/*     */           }
/*     */           continue;
/*     */         } 
/*     */         break;
/*     */       } 
/* 257 */       newCounter = new AtomicInteger(occurrences);
/* 258 */     } while (this.countMap.putIfAbsent(element, newCounter) != null && !this.countMap.replace(element, existingCounter, newCounter));
/*     */     
/* 260 */     return 0;
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
/*     */   public int remove(@Nullable Object element, int occurrences) {
/* 281 */     if (occurrences == 0) {
/* 282 */       return count(element);
/*     */     }
/* 284 */     Preconditions.checkArgument((occurrences > 0), "Invalid occurrences: %s", new Object[] { Integer.valueOf(occurrences) });
/*     */     
/* 286 */     AtomicInteger existingCounter = safeGet(element);
/* 287 */     if (existingCounter == null) {
/* 288 */       return 0;
/*     */     }
/*     */     while (true) {
/* 291 */       int oldValue = existingCounter.get();
/* 292 */       if (oldValue != 0) {
/* 293 */         int newValue = Math.max(0, oldValue - occurrences);
/* 294 */         if (existingCounter.compareAndSet(oldValue, newValue)) {
/* 295 */           if (newValue == 0)
/*     */           {
/*     */             
/* 298 */             this.countMap.remove(element, existingCounter);
/*     */           }
/* 300 */           return oldValue;
/*     */         }  continue;
/*     */       }  break;
/* 303 */     }  return 0;
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
/*     */   public boolean removeExactly(@Nullable Object element, int occurrences) {
/* 321 */     if (occurrences == 0) {
/* 322 */       return true;
/*     */     }
/* 324 */     Preconditions.checkArgument((occurrences > 0), "Invalid occurrences: %s", new Object[] { Integer.valueOf(occurrences) });
/*     */     
/* 326 */     AtomicInteger existingCounter = safeGet(element);
/* 327 */     if (existingCounter == null) {
/* 328 */       return false;
/*     */     }
/*     */     while (true) {
/* 331 */       int oldValue = existingCounter.get();
/* 332 */       if (oldValue < occurrences) {
/* 333 */         return false;
/*     */       }
/* 335 */       int newValue = oldValue - occurrences;
/* 336 */       if (existingCounter.compareAndSet(oldValue, newValue)) {
/* 337 */         if (newValue == 0)
/*     */         {
/*     */           
/* 340 */           this.countMap.remove(element, existingCounter);
/*     */         }
/* 342 */         return true;
/*     */       } 
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
/*     */   public int setCount(E element, int count) {
/* 355 */     Multisets.checkNonnegative(count, "count");
/*     */     label26: while (true) {
/* 357 */       AtomicInteger existingCounter = safeGet(element);
/* 358 */       if (existingCounter == null) {
/* 359 */         if (count == 0) {
/* 360 */           return 0;
/*     */         }
/* 362 */         existingCounter = this.countMap.putIfAbsent(element, new AtomicInteger(count));
/* 363 */         if (existingCounter == null) {
/* 364 */           return 0;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 371 */         int oldValue = existingCounter.get();
/* 372 */         if (oldValue == 0) {
/* 373 */           if (count == 0) {
/* 374 */             return 0;
/*     */           }
/* 376 */           AtomicInteger newCounter = new AtomicInteger(count);
/* 377 */           if (this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter))
/*     */           {
/* 379 */             return 0;
/*     */           }
/*     */           
/*     */           continue label26;
/*     */         } 
/* 384 */         if (existingCounter.compareAndSet(oldValue, count)) {
/* 385 */           if (count == 0)
/*     */           {
/*     */             
/* 388 */             this.countMap.remove(element, existingCounter);
/*     */           }
/* 390 */           return oldValue;
/*     */         } 
/*     */       } 
/*     */       break;
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
/*     */   public boolean setCount(E element, int expectedOldCount, int newCount) {
/* 410 */     Multisets.checkNonnegative(expectedOldCount, "oldCount");
/* 411 */     Multisets.checkNonnegative(newCount, "newCount");
/*     */     
/* 413 */     AtomicInteger existingCounter = safeGet(element);
/* 414 */     if (existingCounter == null) {
/* 415 */       if (expectedOldCount != 0)
/* 416 */         return false; 
/* 417 */       if (newCount == 0) {
/* 418 */         return true;
/*     */       }
/*     */       
/* 421 */       return (this.countMap.putIfAbsent(element, new AtomicInteger(newCount)) == null);
/*     */     } 
/*     */     
/* 424 */     int oldValue = existingCounter.get();
/* 425 */     if (oldValue == expectedOldCount) {
/* 426 */       if (oldValue == 0) {
/* 427 */         if (newCount == 0) {
/*     */           
/* 429 */           this.countMap.remove(element, existingCounter);
/* 430 */           return true;
/*     */         } 
/* 432 */         AtomicInteger newCounter = new AtomicInteger(newCount);
/* 433 */         return (this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter));
/*     */       } 
/*     */ 
/*     */       
/* 437 */       if (existingCounter.compareAndSet(oldValue, newCount)) {
/* 438 */         if (newCount == 0)
/*     */         {
/*     */           
/* 441 */           this.countMap.remove(element, existingCounter);
/*     */         }
/* 443 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 447 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Set<E> createElementSet() {
/* 453 */     final Set<E> delegate = this.countMap.keySet();
/* 454 */     return new ForwardingSet<E>() {
/*     */         protected Set<E> delegate() {
/* 456 */           return delegate;
/*     */         }
/*     */         public boolean remove(Object object) {
/*     */           try {
/* 460 */             return delegate.remove(object);
/* 461 */           } catch (NullPointerException e) {
/* 462 */             return false;
/* 463 */           } catch (ClassCastException e) {
/* 464 */             return false;
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Multiset.Entry<E>> entrySet() {
/* 473 */     EntrySet result = this.entrySet;
/* 474 */     if (result == null) {
/* 475 */       this.entrySet = result = new EntrySet();
/*     */     }
/* 477 */     return result;
/*     */   }
/*     */   
/*     */   int distinctElements() {
/* 481 */     return this.countMap.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 485 */     return this.countMap.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Iterator<Multiset.Entry<E>> entryIterator() {
/* 491 */     final Iterator<Multiset.Entry<E>> readOnlyIterator = new AbstractIterator<Multiset.Entry<E>>()
/*     */       {
/* 493 */         private Iterator<Map.Entry<E, AtomicInteger>> mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();
/*     */         
/*     */         protected Multiset.Entry<E> computeNext() {
/*     */           while (true) {
/* 497 */             if (!this.mapEntries.hasNext()) {
/* 498 */               return endOfData();
/*     */             }
/* 500 */             Map.Entry<E, AtomicInteger> mapEntry = this.mapEntries.next();
/* 501 */             int count = ((AtomicInteger)mapEntry.getValue()).get();
/* 502 */             if (count != 0) {
/* 503 */               return Multisets.immutableEntry(mapEntry.getKey(), count);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 509 */     return new ForwardingIterator<Multiset.Entry<E>>() {
/*     */         private Multiset.Entry<E> last;
/*     */         
/*     */         protected Iterator<Multiset.Entry<E>> delegate() {
/* 513 */           return readOnlyIterator;
/*     */         }
/*     */         
/*     */         public Multiset.Entry<E> next() {
/* 517 */           this.last = super.next();
/* 518 */           return this.last;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 522 */           Preconditions.checkState((this.last != null));
/* 523 */           ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
/* 524 */           this.last = null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void clear() {
/* 530 */     this.countMap.clear();
/*     */   }
/*     */   private class EntrySet extends AbstractMultiset.EntrySet { private EntrySet() {}
/*     */     
/*     */     ConcurrentHashMultiset<E> multiset() {
/* 535 */       return ConcurrentHashMultiset.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 544 */       return snapshot().toArray();
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/* 548 */       return snapshot().toArray(array);
/*     */     }
/*     */     
/*     */     private List<Multiset.Entry<E>> snapshot() {
/* 552 */       List<Multiset.Entry<E>> list = Lists.newArrayListWithExpectedSize(size());
/*     */       
/* 554 */       Iterators.addAll(list, iterator());
/* 555 */       return list;
/*     */     }
/*     */     
/*     */     public boolean remove(Object object) {
/* 559 */       if (object instanceof Multiset.Entry) {
/* 560 */         Multiset.Entry<?> entry = (Multiset.Entry)object;
/* 561 */         Object element = entry.getElement();
/* 562 */         int entryCount = entry.getCount();
/* 563 */         if (entryCount != 0) {
/*     */ 
/*     */           
/* 566 */           Multiset<Object> multiset = (Multiset)multiset();
/* 567 */           return multiset.setCount(element, entryCount, 0);
/*     */         } 
/*     */       } 
/* 570 */       return false;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 578 */     stream.defaultWriteObject();
/* 579 */     stream.writeObject(this.countMap);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 583 */     stream.defaultReadObject();
/*     */     
/* 585 */     ConcurrentMap<E, Integer> deserializedCountMap = (ConcurrentMap<E, Integer>)stream.readObject();
/*     */     
/* 587 */     FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, deserializedCountMap);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ConcurrentHashMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */