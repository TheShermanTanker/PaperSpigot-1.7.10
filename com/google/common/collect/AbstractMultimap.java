/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.base.Preconditions;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @GwtCompatible
/*      */ abstract class AbstractMultimap<K, V>
/*      */   implements Multimap<K, V>, Serializable
/*      */ {
/*      */   private transient Map<K, Collection<V>> map;
/*      */   private transient int totalSize;
/*      */   private transient Set<K> keySet;
/*      */   private transient Multiset<K> multiset;
/*      */   private transient Collection<V> valuesCollection;
/*      */   private transient Collection<Map.Entry<K, V>> entries;
/*      */   private transient Map<K, Collection<V>> asMap;
/*      */   private static final long serialVersionUID = 2447537837011683357L;
/*      */   
/*      */   protected AbstractMultimap(Map<K, Collection<V>> map) {
/*  119 */     Preconditions.checkArgument(map.isEmpty());
/*  120 */     this.map = map;
/*      */   }
/*      */ 
/*      */   
/*      */   final void setMap(Map<K, Collection<V>> map) {
/*  125 */     this.map = map;
/*  126 */     this.totalSize = 0;
/*  127 */     for (Collection<V> values : map.values()) {
/*  128 */       Preconditions.checkArgument(!values.isEmpty());
/*  129 */       this.totalSize += values.size();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Collection<V> createCollection(@Nullable K key) {
/*  156 */     return createCollection();
/*      */   }
/*      */   
/*      */   Map<K, Collection<V>> backingMap() {
/*  160 */     return this.map;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  167 */     return this.totalSize;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  172 */     return (this.totalSize == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(@Nullable Object key) {
/*  177 */     return this.map.containsKey(key);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(@Nullable Object value) {
/*  182 */     for (Collection<V> collection : this.map.values()) {
/*  183 */       if (collection.contains(value)) {
/*  184 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  188 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
/*  193 */     Collection<V> collection = this.map.get(key);
/*  194 */     return (collection != null && collection.contains(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean put(@Nullable K key, @Nullable V value) {
/*  201 */     Collection<V> collection = getOrCreateCollection(key);
/*      */     
/*  203 */     if (collection.add(value)) {
/*  204 */       this.totalSize++;
/*  205 */       return true;
/*      */     } 
/*  207 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private Collection<V> getOrCreateCollection(@Nullable K key) {
/*  212 */     Collection<V> collection = this.map.get(key);
/*  213 */     if (collection == null) {
/*  214 */       collection = createCollection(key);
/*  215 */       this.map.put(key, collection);
/*      */     } 
/*  217 */     return collection;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(@Nullable Object key, @Nullable Object value) {
/*  222 */     Collection<V> collection = this.map.get(key);
/*  223 */     if (collection == null) {
/*  224 */       return false;
/*      */     }
/*      */     
/*  227 */     boolean changed = collection.remove(value);
/*  228 */     if (changed) {
/*  229 */       this.totalSize--;
/*  230 */       if (collection.isEmpty()) {
/*  231 */         this.map.remove(key);
/*      */       }
/*      */     } 
/*  234 */     return changed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean putAll(@Nullable K key, Iterable<? extends V> values) {
/*  241 */     if (!values.iterator().hasNext()) {
/*  242 */       return false;
/*      */     }
/*  244 */     Collection<V> collection = getOrCreateCollection(key);
/*  245 */     int oldSize = collection.size();
/*      */     
/*  247 */     boolean changed = false;
/*  248 */     if (values instanceof Collection) {
/*  249 */       Collection<? extends V> c = Collections2.cast(values);
/*  250 */       changed = collection.addAll(c);
/*      */     } else {
/*  252 */       for (V value : values) {
/*  253 */         changed |= collection.add(value);
/*      */       }
/*      */     } 
/*      */     
/*  257 */     this.totalSize += collection.size() - oldSize;
/*  258 */     return changed;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
/*  263 */     boolean changed = false;
/*  264 */     for (Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
/*  265 */       changed |= put(entry.getKey(), entry.getValue());
/*      */     }
/*  267 */     return changed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
/*  278 */     Iterator<? extends V> iterator = values.iterator();
/*  279 */     if (!iterator.hasNext()) {
/*  280 */       return removeAll(key);
/*      */     }
/*      */     
/*  283 */     Collection<V> collection = getOrCreateCollection(key);
/*  284 */     Collection<V> oldValues = createCollection();
/*  285 */     oldValues.addAll(collection);
/*      */     
/*  287 */     this.totalSize -= collection.size();
/*  288 */     collection.clear();
/*      */     
/*  290 */     while (iterator.hasNext()) {
/*  291 */       if (collection.add(iterator.next())) {
/*  292 */         this.totalSize++;
/*      */       }
/*      */     } 
/*      */     
/*  296 */     return unmodifiableCollectionSubclass(oldValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> removeAll(@Nullable Object key) {
/*  306 */     Collection<V> collection = this.map.remove(key);
/*  307 */     Collection<V> output = createCollection();
/*      */     
/*  309 */     if (collection != null) {
/*  310 */       output.addAll(collection);
/*  311 */       this.totalSize -= collection.size();
/*  312 */       collection.clear();
/*      */     } 
/*      */     
/*  315 */     return unmodifiableCollectionSubclass(output);
/*      */   }
/*      */ 
/*      */   
/*      */   private Collection<V> unmodifiableCollectionSubclass(Collection<V> collection) {
/*  320 */     if (collection instanceof SortedSet)
/*  321 */       return Collections.unmodifiableSortedSet((SortedSet<V>)collection); 
/*  322 */     if (collection instanceof Set)
/*  323 */       return Collections.unmodifiableSet((Set<? extends V>)collection); 
/*  324 */     if (collection instanceof List) {
/*  325 */       return Collections.unmodifiableList((List<? extends V>)collection);
/*      */     }
/*  327 */     return Collections.unmodifiableCollection(collection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  334 */     for (Collection<V> collection : this.map.values()) {
/*  335 */       collection.clear();
/*      */     }
/*  337 */     this.map.clear();
/*  338 */     this.totalSize = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> get(@Nullable K key) {
/*  350 */     Collection<V> collection = this.map.get(key);
/*  351 */     if (collection == null) {
/*  352 */       collection = createCollection(key);
/*      */     }
/*  354 */     return wrapCollection(key, collection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<V> wrapCollection(@Nullable K key, Collection<V> collection) {
/*  364 */     if (collection instanceof SortedSet)
/*  365 */       return new WrappedSortedSet(key, (SortedSet<V>)collection, null); 
/*  366 */     if (collection instanceof Set)
/*  367 */       return new WrappedSet(key, (Set<V>)collection); 
/*  368 */     if (collection instanceof List) {
/*  369 */       return wrapList(key, (List<V>)collection, null);
/*      */     }
/*  371 */     return new WrappedCollection(key, collection, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private List<V> wrapList(@Nullable K key, List<V> list, @Nullable WrappedCollection ancestor) {
/*  377 */     return (list instanceof RandomAccess) ? new RandomAccessWrappedList(key, list, ancestor) : new WrappedList(key, list, ancestor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class WrappedCollection
/*      */     extends AbstractCollection<V>
/*      */   {
/*      */     final K key;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Collection<V> delegate;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final WrappedCollection ancestor;
/*      */ 
/*      */ 
/*      */     
/*      */     final Collection<V> ancestorDelegate;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     WrappedCollection(K key, @Nullable Collection<V> delegate, WrappedCollection ancestor) {
/*  407 */       this.key = key;
/*  408 */       this.delegate = delegate;
/*  409 */       this.ancestor = ancestor;
/*  410 */       this.ancestorDelegate = (ancestor == null) ? null : ancestor.getDelegate();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void refreshIfEmpty() {
/*  422 */       if (this.ancestor != null) {
/*  423 */         this.ancestor.refreshIfEmpty();
/*  424 */         if (this.ancestor.getDelegate() != this.ancestorDelegate) {
/*  425 */           throw new ConcurrentModificationException();
/*      */         }
/*  427 */       } else if (this.delegate.isEmpty()) {
/*  428 */         Collection<V> newDelegate = (Collection<V>)AbstractMultimap.this.map.get(this.key);
/*  429 */         if (newDelegate != null) {
/*  430 */           this.delegate = newDelegate;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void removeIfEmpty() {
/*  440 */       if (this.ancestor != null) {
/*  441 */         this.ancestor.removeIfEmpty();
/*  442 */       } else if (this.delegate.isEmpty()) {
/*  443 */         AbstractMultimap.this.map.remove(this.key);
/*      */       } 
/*      */     }
/*      */     
/*      */     K getKey() {
/*  448 */       return this.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void addToMap() {
/*  459 */       if (this.ancestor != null) {
/*  460 */         this.ancestor.addToMap();
/*      */       } else {
/*  462 */         AbstractMultimap.this.map.put(this.key, this.delegate);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  467 */       refreshIfEmpty();
/*  468 */       return this.delegate.size();
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  472 */       if (object == this) {
/*  473 */         return true;
/*      */       }
/*  475 */       refreshIfEmpty();
/*  476 */       return this.delegate.equals(object);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  480 */       refreshIfEmpty();
/*  481 */       return this.delegate.hashCode();
/*      */     }
/*      */     
/*      */     public String toString() {
/*  485 */       refreshIfEmpty();
/*  486 */       return this.delegate.toString();
/*      */     }
/*      */     
/*      */     Collection<V> getDelegate() {
/*  490 */       return this.delegate;
/*      */     }
/*      */     
/*      */     public Iterator<V> iterator() {
/*  494 */       refreshIfEmpty();
/*  495 */       return new WrappedIterator();
/*      */     }
/*      */     
/*      */     class WrappedIterator
/*      */       implements Iterator<V> {
/*      */       final Iterator<V> delegateIterator;
/*  501 */       final Collection<V> originalDelegate = AbstractMultimap.WrappedCollection.this.delegate;
/*      */       
/*      */       WrappedIterator() {
/*  504 */         this.delegateIterator = AbstractMultimap.this.iteratorOrListIterator(AbstractMultimap.WrappedCollection.this.delegate);
/*      */       }
/*      */       
/*      */       WrappedIterator(Iterator<V> delegateIterator) {
/*  508 */         this.delegateIterator = delegateIterator;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void validateIterator() {
/*  516 */         AbstractMultimap.WrappedCollection.this.refreshIfEmpty();
/*  517 */         if (AbstractMultimap.WrappedCollection.this.delegate != this.originalDelegate) {
/*  518 */           throw new ConcurrentModificationException();
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean hasNext() {
/*  524 */         validateIterator();
/*  525 */         return this.delegateIterator.hasNext();
/*      */       }
/*      */ 
/*      */       
/*      */       public V next() {
/*  530 */         validateIterator();
/*  531 */         return this.delegateIterator.next();
/*      */       }
/*      */ 
/*      */       
/*      */       public void remove() {
/*  536 */         this.delegateIterator.remove();
/*  537 */         AbstractMultimap.this.totalSize--;
/*  538 */         AbstractMultimap.WrappedCollection.this.removeIfEmpty();
/*      */       }
/*      */       
/*      */       Iterator<V> getDelegateIterator() {
/*  542 */         validateIterator();
/*  543 */         return this.delegateIterator;
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean add(V value) {
/*  548 */       refreshIfEmpty();
/*  549 */       boolean wasEmpty = this.delegate.isEmpty();
/*  550 */       boolean changed = this.delegate.add(value);
/*  551 */       if (changed) {
/*  552 */         AbstractMultimap.this.totalSize++;
/*  553 */         if (wasEmpty) {
/*  554 */           addToMap();
/*      */         }
/*      */       } 
/*  557 */       return changed;
/*      */     }
/*      */     
/*      */     WrappedCollection getAncestor() {
/*  561 */       return this.ancestor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends V> collection) {
/*  567 */       if (collection.isEmpty()) {
/*  568 */         return false;
/*      */       }
/*  570 */       int oldSize = size();
/*  571 */       boolean changed = this.delegate.addAll(collection);
/*  572 */       if (changed) {
/*  573 */         int newSize = this.delegate.size();
/*  574 */         AbstractMultimap.this.totalSize += newSize - oldSize;
/*  575 */         if (oldSize == 0) {
/*  576 */           addToMap();
/*      */         }
/*      */       } 
/*  579 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean contains(Object o) {
/*  583 */       refreshIfEmpty();
/*  584 */       return this.delegate.contains(o);
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> c) {
/*  588 */       refreshIfEmpty();
/*  589 */       return this.delegate.containsAll(c);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  593 */       int oldSize = size();
/*  594 */       if (oldSize == 0) {
/*      */         return;
/*      */       }
/*  597 */       this.delegate.clear();
/*  598 */       AbstractMultimap.this.totalSize -= oldSize;
/*  599 */       removeIfEmpty();
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  603 */       refreshIfEmpty();
/*  604 */       boolean changed = this.delegate.remove(o);
/*  605 */       if (changed) {
/*  606 */         AbstractMultimap.this.totalSize--;
/*  607 */         removeIfEmpty();
/*      */       } 
/*  609 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  613 */       if (c.isEmpty()) {
/*  614 */         return false;
/*      */       }
/*  616 */       int oldSize = size();
/*  617 */       boolean changed = this.delegate.removeAll(c);
/*  618 */       if (changed) {
/*  619 */         int newSize = this.delegate.size();
/*  620 */         AbstractMultimap.this.totalSize += newSize - oldSize;
/*  621 */         removeIfEmpty();
/*      */       } 
/*  623 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  627 */       Preconditions.checkNotNull(c);
/*  628 */       int oldSize = size();
/*  629 */       boolean changed = this.delegate.retainAll(c);
/*  630 */       if (changed) {
/*  631 */         int newSize = this.delegate.size();
/*  632 */         AbstractMultimap.this.totalSize += newSize - oldSize;
/*  633 */         removeIfEmpty();
/*      */       } 
/*  635 */       return changed;
/*      */     }
/*      */   }
/*      */   
/*      */   private Iterator<V> iteratorOrListIterator(Collection<V> collection) {
/*  640 */     return (collection instanceof List) ? ((List<V>)collection).listIterator() : collection.iterator();
/*      */   }
/*      */   
/*      */   private class WrappedSet
/*      */     extends WrappedCollection
/*      */     implements Set<V>
/*      */   {
/*      */     WrappedSet(K key, Set<V> delegate) {
/*  648 */       super(key, delegate, null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class WrappedSortedSet
/*      */     extends WrappedCollection
/*      */     implements SortedSet<V>
/*      */   {
/*      */     WrappedSortedSet(K key, @Nullable SortedSet<V> delegate, AbstractMultimap<K, V>.WrappedCollection ancestor) {
/*  659 */       super(key, delegate, ancestor);
/*      */     }
/*      */     
/*      */     SortedSet<V> getSortedSetDelegate() {
/*  663 */       return (SortedSet<V>)getDelegate();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super V> comparator() {
/*  668 */       return getSortedSetDelegate().comparator();
/*      */     }
/*      */ 
/*      */     
/*      */     public V first() {
/*  673 */       refreshIfEmpty();
/*  674 */       return getSortedSetDelegate().first();
/*      */     }
/*      */ 
/*      */     
/*      */     public V last() {
/*  679 */       refreshIfEmpty();
/*  680 */       return getSortedSetDelegate().last();
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedSet<V> headSet(V toElement) {
/*  685 */       refreshIfEmpty();
/*  686 */       return new WrappedSortedSet(getKey(), getSortedSetDelegate().headSet(toElement), (getAncestor() == null) ? this : getAncestor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SortedSet<V> subSet(V fromElement, V toElement) {
/*  693 */       refreshIfEmpty();
/*  694 */       return new WrappedSortedSet(getKey(), getSortedSetDelegate().subSet(fromElement, toElement), (getAncestor() == null) ? this : getAncestor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SortedSet<V> tailSet(V fromElement) {
/*  701 */       refreshIfEmpty();
/*  702 */       return new WrappedSortedSet(getKey(), getSortedSetDelegate().tailSet(fromElement), (getAncestor() == null) ? this : getAncestor());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class WrappedList
/*      */     extends WrappedCollection
/*      */     implements List<V>
/*      */   {
/*      */     WrappedList(K key, @Nullable List<V> delegate, AbstractMultimap<K, V>.WrappedCollection ancestor) {
/*  712 */       super(key, delegate, ancestor);
/*      */     }
/*      */     
/*      */     List<V> getListDelegate() {
/*  716 */       return (List<V>)getDelegate();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends V> c) {
/*  721 */       if (c.isEmpty()) {
/*  722 */         return false;
/*      */       }
/*  724 */       int oldSize = size();
/*  725 */       boolean changed = getListDelegate().addAll(index, c);
/*  726 */       if (changed) {
/*  727 */         int newSize = getDelegate().size();
/*  728 */         AbstractMultimap.this.totalSize += newSize - oldSize;
/*  729 */         if (oldSize == 0) {
/*  730 */           addToMap();
/*      */         }
/*      */       } 
/*  733 */       return changed;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get(int index) {
/*  738 */       refreshIfEmpty();
/*  739 */       return getListDelegate().get(index);
/*      */     }
/*      */ 
/*      */     
/*      */     public V set(int index, V element) {
/*  744 */       refreshIfEmpty();
/*  745 */       return getListDelegate().set(index, element);
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int index, V element) {
/*  750 */       refreshIfEmpty();
/*  751 */       boolean wasEmpty = getDelegate().isEmpty();
/*  752 */       getListDelegate().add(index, element);
/*  753 */       AbstractMultimap.this.totalSize++;
/*  754 */       if (wasEmpty) {
/*  755 */         addToMap();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(int index) {
/*  761 */       refreshIfEmpty();
/*  762 */       V value = getListDelegate().remove(index);
/*  763 */       AbstractMultimap.this.totalSize--;
/*  764 */       removeIfEmpty();
/*  765 */       return value;
/*      */     }
/*      */ 
/*      */     
/*      */     public int indexOf(Object o) {
/*  770 */       refreshIfEmpty();
/*  771 */       return getListDelegate().indexOf(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastIndexOf(Object o) {
/*  776 */       refreshIfEmpty();
/*  777 */       return getListDelegate().lastIndexOf(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public ListIterator<V> listIterator() {
/*  782 */       refreshIfEmpty();
/*  783 */       return new WrappedListIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ListIterator<V> listIterator(int index) {
/*  788 */       refreshIfEmpty();
/*  789 */       return new WrappedListIterator(index);
/*      */     }
/*      */ 
/*      */     
/*      */     public List<V> subList(int fromIndex, int toIndex) {
/*  794 */       refreshIfEmpty();
/*  795 */       return AbstractMultimap.this.wrapList(getKey(), getListDelegate().subList(fromIndex, toIndex), (getAncestor() == null) ? this : getAncestor());
/*      */     }
/*      */ 
/*      */     
/*      */     private class WrappedListIterator
/*      */       extends AbstractMultimap<K, V>.WrappedCollection.WrappedIterator
/*      */       implements ListIterator<V>
/*      */     {
/*      */       WrappedListIterator() {}
/*      */       
/*      */       public WrappedListIterator(int index) {
/*  806 */         super(AbstractMultimap.WrappedList.this.getListDelegate().listIterator(index));
/*      */       }
/*      */       
/*      */       private ListIterator<V> getDelegateListIterator() {
/*  810 */         return (ListIterator<V>)getDelegateIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean hasPrevious() {
/*  815 */         return getDelegateListIterator().hasPrevious();
/*      */       }
/*      */ 
/*      */       
/*      */       public V previous() {
/*  820 */         return getDelegateListIterator().previous();
/*      */       }
/*      */ 
/*      */       
/*      */       public int nextIndex() {
/*  825 */         return getDelegateListIterator().nextIndex();
/*      */       }
/*      */ 
/*      */       
/*      */       public int previousIndex() {
/*  830 */         return getDelegateListIterator().previousIndex();
/*      */       }
/*      */ 
/*      */       
/*      */       public void set(V value) {
/*  835 */         getDelegateListIterator().set(value);
/*      */       }
/*      */ 
/*      */       
/*      */       public void add(V value) {
/*  840 */         boolean wasEmpty = AbstractMultimap.WrappedList.this.isEmpty();
/*  841 */         getDelegateListIterator().add(value);
/*  842 */         AbstractMultimap.this.totalSize++;
/*  843 */         if (wasEmpty) {
/*  844 */           AbstractMultimap.WrappedList.this.addToMap();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class RandomAccessWrappedList
/*      */     extends WrappedList
/*      */     implements RandomAccess
/*      */   {
/*      */     RandomAccessWrappedList(K key, @Nullable List<V> delegate, AbstractMultimap<K, V>.WrappedCollection ancestor) {
/*  858 */       super(key, delegate, ancestor);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<K> keySet() {
/*  866 */     Set<K> result = this.keySet;
/*  867 */     return (result == null) ? (this.keySet = createKeySet()) : result;
/*      */   }
/*      */   
/*      */   private Set<K> createKeySet() {
/*  871 */     return (this.map instanceof SortedMap) ? new SortedKeySet((SortedMap<K, Collection<V>>)this.map) : new KeySet(this.map);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class KeySet
/*      */     extends Maps.KeySet<K, Collection<V>>
/*      */   {
/*      */     final Map<K, Collection<V>> subMap;
/*      */ 
/*      */ 
/*      */     
/*      */     KeySet(Map<K, Collection<V>> subMap) {
/*  884 */       this.subMap = subMap;
/*      */     }
/*      */ 
/*      */     
/*      */     Map<K, Collection<V>> map() {
/*  889 */       return this.subMap;
/*      */     }
/*      */     
/*      */     public Iterator<K> iterator() {
/*  893 */       return new Iterator<K>() {
/*  894 */           final Iterator<Map.Entry<K, Collection<V>>> entryIterator = AbstractMultimap.KeySet.this.subMap.entrySet().iterator();
/*      */           
/*      */           Map.Entry<K, Collection<V>> entry;
/*      */ 
/*      */           
/*      */           public boolean hasNext() {
/*  900 */             return this.entryIterator.hasNext();
/*      */           }
/*      */           
/*      */           public K next() {
/*  904 */             this.entry = this.entryIterator.next();
/*  905 */             return this.entry.getKey();
/*      */           }
/*      */           
/*      */           public void remove() {
/*  909 */             Preconditions.checkState((this.entry != null));
/*  910 */             Collection<V> collection = this.entry.getValue();
/*  911 */             this.entryIterator.remove();
/*  912 */             AbstractMultimap.this.totalSize -= collection.size();
/*  913 */             collection.clear();
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object key) {
/*  921 */       int count = 0;
/*  922 */       Collection<V> collection = this.subMap.remove(key);
/*  923 */       if (collection != null) {
/*  924 */         count = collection.size();
/*  925 */         collection.clear();
/*  926 */         AbstractMultimap.this.totalSize -= count;
/*      */       } 
/*  928 */       return (count > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/*  933 */       Iterators.clear(iterator());
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> c) {
/*  937 */       return this.subMap.keySet().containsAll(c);
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  941 */       return (this == object || this.subMap.keySet().equals(object));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  945 */       return this.subMap.keySet().hashCode();
/*      */     }
/*      */   }
/*      */   
/*      */   private class SortedKeySet
/*      */     extends KeySet implements SortedSet<K> {
/*      */     SortedKeySet(SortedMap<K, Collection<V>> subMap) {
/*  952 */       super(subMap);
/*      */     }
/*      */     
/*      */     SortedMap<K, Collection<V>> sortedMap() {
/*  956 */       return (SortedMap<K, Collection<V>>)this.subMap;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/*  961 */       return sortedMap().comparator();
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/*  966 */       return sortedMap().firstKey();
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedSet<K> headSet(K toElement) {
/*  971 */       return new SortedKeySet(sortedMap().headMap(toElement));
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/*  976 */       return sortedMap().lastKey();
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedSet<K> subSet(K fromElement, K toElement) {
/*  981 */       return new SortedKeySet(sortedMap().subMap(fromElement, toElement));
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedSet<K> tailSet(K fromElement) {
/*  986 */       return new SortedKeySet(sortedMap().tailMap(fromElement));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Multiset<K> keys() {
/*  994 */     Multiset<K> result = this.multiset;
/*  995 */     if (result == null) {
/*  996 */       return this.multiset = new Multimaps.Keys<K, V>() {
/*      */           Multimap<K, V> multimap() {
/*  998 */             return AbstractMultimap.this;
/*      */           }
/*      */         };
/*      */     }
/* 1002 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int removeValuesForKey(Object key) {
/*      */     Collection<V> collection;
/*      */     try {
/* 1012 */       collection = this.map.remove(key);
/* 1013 */     } catch (NullPointerException e) {
/* 1014 */       return 0;
/* 1015 */     } catch (ClassCastException e) {
/* 1016 */       return 0;
/*      */     } 
/*      */     
/* 1019 */     int count = 0;
/* 1020 */     if (collection != null) {
/* 1021 */       count = collection.size();
/* 1022 */       collection.clear();
/* 1023 */       this.totalSize -= count;
/*      */     } 
/* 1025 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> values() {
/* 1037 */     Collection<V> result = this.valuesCollection;
/* 1038 */     if (result == null) {
/* 1039 */       return this.valuesCollection = new Multimaps.Values<K, V>() {
/*      */           Multimap<K, V> multimap() {
/* 1041 */             return AbstractMultimap.this;
/*      */           }
/*      */         };
/*      */     }
/* 1045 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<Map.Entry<K, V>> entries() {
/* 1068 */     Collection<Map.Entry<K, V>> result = this.entries;
/* 1069 */     return (result == null) ? (this.entries = createEntries()) : result;
/*      */   }
/*      */   
/*      */   Collection<Map.Entry<K, V>> createEntries() {
/* 1073 */     if (this instanceof SetMultimap) {
/* 1074 */       return new Multimaps.EntrySet<K, V>() {
/*      */           Multimap<K, V> multimap() {
/* 1076 */             return AbstractMultimap.this;
/*      */           }
/*      */           
/*      */           public Iterator<Map.Entry<K, V>> iterator() {
/* 1080 */             return AbstractMultimap.this.createEntryIterator();
/*      */           }
/*      */         };
/*      */     }
/* 1084 */     return new Multimaps.Entries<K, V>() {
/*      */         Multimap<K, V> multimap() {
/* 1086 */           return AbstractMultimap.this;
/*      */         }
/*      */         
/*      */         public Iterator<Map.Entry<K, V>> iterator() {
/* 1090 */           return AbstractMultimap.this.createEntryIterator();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Iterator<Map.Entry<K, V>> createEntryIterator() {
/* 1104 */     return new EntryIterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     implements Iterator<Map.Entry<K, V>>
/*      */   {
/* 1115 */     final Iterator<Map.Entry<K, Collection<V>>> keyIterator = AbstractMultimap.this.map.entrySet().iterator(); K key; EntryIterator() {
/* 1116 */       if (this.keyIterator.hasNext()) {
/* 1117 */         findValueIteratorAndKey();
/*      */       } else {
/* 1119 */         this.valueIterator = Iterators.emptyModifiableIterator();
/*      */       } 
/*      */     }
/*      */     Collection<V> collection; Iterator<V> valueIterator;
/*      */     void findValueIteratorAndKey() {
/* 1124 */       Map.Entry<K, Collection<V>> entry = this.keyIterator.next();
/* 1125 */       this.key = entry.getKey();
/* 1126 */       this.collection = entry.getValue();
/* 1127 */       this.valueIterator = this.collection.iterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1132 */       return (this.keyIterator.hasNext() || this.valueIterator.hasNext());
/*      */     }
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> next() {
/* 1137 */       if (!this.valueIterator.hasNext()) {
/* 1138 */         findValueIteratorAndKey();
/*      */       }
/* 1140 */       return Maps.immutableEntry(this.key, this.valueIterator.next());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1145 */       this.valueIterator.remove();
/* 1146 */       if (this.collection.isEmpty()) {
/* 1147 */         this.keyIterator.remove();
/*      */       }
/* 1149 */       AbstractMultimap.this.totalSize--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<K, Collection<V>> asMap() {
/* 1157 */     Map<K, Collection<V>> result = this.asMap;
/* 1158 */     return (result == null) ? (this.asMap = createAsMap()) : result;
/*      */   }
/*      */   
/*      */   private Map<K, Collection<V>> createAsMap() {
/* 1162 */     return (this.map instanceof SortedMap) ? new SortedAsMap((SortedMap<K, Collection<V>>)this.map) : new AsMap(this.map);
/*      */   }
/*      */ 
/*      */   
/*      */   private class AsMap
/*      */     extends AbstractMap<K, Collection<V>>
/*      */   {
/*      */     final transient Map<K, Collection<V>> submap;
/*      */     
/*      */     transient Set<Map.Entry<K, Collection<V>>> entrySet;
/*      */     
/*      */     AsMap(Map<K, Collection<V>> submap) {
/* 1174 */       this.submap = submap;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, Collection<V>>> entrySet() {
/* 1180 */       Set<Map.Entry<K, Collection<V>>> result = this.entrySet;
/* 1181 */       return (result == null) ? (this.entrySet = new AsMapEntries()) : result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object key) {
/* 1187 */       return Maps.safeContainsKey(this.submap, key);
/*      */     }
/*      */     
/*      */     public Collection<V> get(Object key) {
/* 1191 */       Collection<V> collection = Maps.<Collection<V>>safeGet(this.submap, key);
/* 1192 */       if (collection == null) {
/* 1193 */         return null;
/*      */       }
/*      */       
/* 1196 */       K k = (K)key;
/* 1197 */       return AbstractMultimap.this.wrapCollection(k, collection);
/*      */     }
/*      */     
/*      */     public Set<K> keySet() {
/* 1201 */       return AbstractMultimap.this.keySet();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1206 */       return this.submap.size();
/*      */     }
/*      */     
/*      */     public Collection<V> remove(Object key) {
/* 1210 */       Collection<V> collection = this.submap.remove(key);
/* 1211 */       if (collection == null) {
/* 1212 */         return null;
/*      */       }
/*      */       
/* 1215 */       Collection<V> output = AbstractMultimap.this.createCollection();
/* 1216 */       output.addAll(collection);
/* 1217 */       AbstractMultimap.this.totalSize -= collection.size();
/* 1218 */       collection.clear();
/* 1219 */       return output;
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/* 1223 */       return (this == object || this.submap.equals(object));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1227 */       return this.submap.hashCode();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1231 */       return this.submap.toString();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1236 */       if (this.submap == AbstractMultimap.this.map) {
/* 1237 */         AbstractMultimap.this.clear();
/*      */       } else {
/*      */         
/* 1240 */         Iterators.clear(new AsMapIterator());
/*      */       } 
/*      */     }
/*      */     
/*      */     class AsMapEntries
/*      */       extends Maps.EntrySet<K, Collection<V>> {
/*      */       Map<K, Collection<V>> map() {
/* 1247 */         return AbstractMultimap.AsMap.this;
/*      */       }
/*      */       
/*      */       public Iterator<Map.Entry<K, Collection<V>>> iterator() {
/* 1251 */         return new AbstractMultimap.AsMap.AsMapIterator();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean contains(Object o) {
/* 1257 */         return Collections2.safeContains(AbstractMultimap.AsMap.this.submap.entrySet(), o);
/*      */       }
/*      */       
/*      */       public boolean remove(Object o) {
/* 1261 */         if (!contains(o)) {
/* 1262 */           return false;
/*      */         }
/* 1264 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 1265 */         AbstractMultimap.this.removeValuesForKey(entry.getKey());
/* 1266 */         return true;
/*      */       }
/*      */     }
/*      */     
/*      */     class AsMapIterator
/*      */       implements Iterator<Map.Entry<K, Collection<V>>> {
/* 1272 */       final Iterator<Map.Entry<K, Collection<V>>> delegateIterator = AbstractMultimap.AsMap.this.submap.entrySet().iterator();
/*      */       
/*      */       Collection<V> collection;
/*      */ 
/*      */       
/*      */       public boolean hasNext() {
/* 1278 */         return this.delegateIterator.hasNext();
/*      */       }
/*      */ 
/*      */       
/*      */       public Map.Entry<K, Collection<V>> next() {
/* 1283 */         Map.Entry<K, Collection<V>> entry = this.delegateIterator.next();
/* 1284 */         K key = entry.getKey();
/* 1285 */         this.collection = entry.getValue();
/* 1286 */         return Maps.immutableEntry(key, AbstractMultimap.this.wrapCollection(key, this.collection));
/*      */       }
/*      */ 
/*      */       
/*      */       public void remove() {
/* 1291 */         this.delegateIterator.remove();
/* 1292 */         AbstractMultimap.this.totalSize -= this.collection.size();
/* 1293 */         this.collection.clear();
/*      */       } }
/*      */   }
/*      */   
/*      */   private class SortedAsMap extends AsMap implements SortedMap<K, Collection<V>> {
/*      */     SortedSet<K> sortedKeySet;
/*      */     
/*      */     SortedAsMap(SortedMap<K, Collection<V>> submap) {
/* 1301 */       super(submap);
/*      */     }
/*      */     
/*      */     SortedMap<K, Collection<V>> sortedMap() {
/* 1305 */       return (SortedMap<K, Collection<V>>)this.submap;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1310 */       return sortedMap().comparator();
/*      */     }
/*      */ 
/*      */     
/*      */     public K firstKey() {
/* 1315 */       return sortedMap().firstKey();
/*      */     }
/*      */ 
/*      */     
/*      */     public K lastKey() {
/* 1320 */       return sortedMap().lastKey();
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, Collection<V>> headMap(K toKey) {
/* 1325 */       return new SortedAsMap(sortedMap().headMap(toKey));
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, Collection<V>> subMap(K fromKey, K toKey) {
/* 1330 */       return new SortedAsMap(sortedMap().subMap(fromKey, toKey));
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, Collection<V>> tailMap(K fromKey) {
/* 1335 */       return new SortedAsMap(sortedMap().tailMap(fromKey));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SortedSet<K> keySet() {
/* 1343 */       SortedSet<K> result = this.sortedKeySet;
/* 1344 */       return (result == null) ? (this.sortedKeySet = new AbstractMultimap.SortedKeySet(sortedMap())) : result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(@Nullable Object object) {
/* 1352 */     if (object == this) {
/* 1353 */       return true;
/*      */     }
/* 1355 */     if (object instanceof Multimap) {
/* 1356 */       Multimap<?, ?> that = (Multimap<?, ?>)object;
/* 1357 */       return this.map.equals(that.asMap());
/*      */     } 
/* 1359 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1371 */     return this.map.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1382 */     return this.map.toString();
/*      */   }
/*      */   
/*      */   abstract Collection<V> createCollection();
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */