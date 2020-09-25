/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.Beta;
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.GwtIncompatible;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Joiner;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Supplier;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
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
/*      */ @GwtCompatible(emulated = true)
/*      */ public final class Multimaps
/*      */ {
/*      */   public static <K, V> Multimap<K, V> newMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> factory) {
/*  106 */     return new CustomMultimap<K, V>(map, factory);
/*      */   }
/*      */   
/*      */   private static class CustomMultimap<K, V>
/*      */     extends AbstractMultimap<K, V> {
/*      */     transient Supplier<? extends Collection<V>> factory;
/*      */     
/*      */     CustomMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> factory) {
/*  114 */       super(map);
/*  115 */       this.factory = (Supplier<? extends Collection<V>>)Preconditions.checkNotNull(factory);
/*      */     } @GwtIncompatible("java serialization not supported")
/*      */     private static final long serialVersionUID = 0L;
/*      */     protected Collection<V> createCollection() {
/*  119 */       return (Collection<V>)this.factory.get();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.io.ObjectOutputStream")
/*      */     private void writeObject(ObjectOutputStream stream) throws IOException {
/*  128 */       stream.defaultWriteObject();
/*  129 */       stream.writeObject(this.factory);
/*  130 */       stream.writeObject(backingMap());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.io.ObjectInputStream")
/*      */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*  137 */       stream.defaultReadObject();
/*  138 */       this.factory = (Supplier<? extends Collection<V>>)stream.readObject();
/*  139 */       Map<K, Collection<V>> map = (Map<K, Collection<V>>)stream.readObject();
/*  140 */       setMap(map);
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
/*      */   public static <K, V> ListMultimap<K, V> newListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> factory) {
/*  186 */     return new CustomListMultimap<K, V>(map, factory);
/*      */   }
/*      */   
/*      */   private static class CustomListMultimap<K, V> extends AbstractListMultimap<K, V> {
/*      */     transient Supplier<? extends List<V>> factory;
/*      */     @GwtIncompatible("java serialization not supported")
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     CustomListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> factory) {
/*  195 */       super(map);
/*  196 */       this.factory = (Supplier<? extends List<V>>)Preconditions.checkNotNull(factory);
/*      */     }
/*      */     
/*      */     protected List<V> createCollection() {
/*  200 */       return (List<V>)this.factory.get();
/*      */     }
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.io.ObjectOutputStream")
/*      */     private void writeObject(ObjectOutputStream stream) throws IOException {
/*  206 */       stream.defaultWriteObject();
/*  207 */       stream.writeObject(this.factory);
/*  208 */       stream.writeObject(backingMap());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.io.ObjectInputStream")
/*      */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*  215 */       stream.defaultReadObject();
/*  216 */       this.factory = (Supplier<? extends List<V>>)stream.readObject();
/*  217 */       Map<K, Collection<V>> map = (Map<K, Collection<V>>)stream.readObject();
/*  218 */       setMap(map);
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
/*      */   public static <K, V> SetMultimap<K, V> newSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> factory) {
/*  264 */     return new CustomSetMultimap<K, V>(map, factory);
/*      */   }
/*      */   
/*      */   private static class CustomSetMultimap<K, V> extends AbstractSetMultimap<K, V> {
/*      */     transient Supplier<? extends Set<V>> factory;
/*      */     @GwtIncompatible("not needed in emulated source")
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     CustomSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> factory) {
/*  273 */       super(map);
/*  274 */       this.factory = (Supplier<? extends Set<V>>)Preconditions.checkNotNull(factory);
/*      */     }
/*      */     
/*      */     protected Set<V> createCollection() {
/*  278 */       return (Set<V>)this.factory.get();
/*      */     }
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.io.ObjectOutputStream")
/*      */     private void writeObject(ObjectOutputStream stream) throws IOException {
/*  284 */       stream.defaultWriteObject();
/*  285 */       stream.writeObject(this.factory);
/*  286 */       stream.writeObject(backingMap());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.io.ObjectInputStream")
/*      */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*  293 */       stream.defaultReadObject();
/*  294 */       this.factory = (Supplier<? extends Set<V>>)stream.readObject();
/*  295 */       Map<K, Collection<V>> map = (Map<K, Collection<V>>)stream.readObject();
/*  296 */       setMap(map);
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
/*      */   public static <K, V> SortedSetMultimap<K, V> newSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> factory) {
/*  342 */     return new CustomSortedSetMultimap<K, V>(map, factory);
/*      */   }
/*      */   
/*      */   private static class CustomSortedSetMultimap<K, V> extends AbstractSortedSetMultimap<K, V> {
/*      */     transient Supplier<? extends SortedSet<V>> factory;
/*      */     transient Comparator<? super V> valueComparator;
/*      */     @GwtIncompatible("not needed in emulated source")
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     CustomSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> factory) {
/*  352 */       super(map);
/*  353 */       this.factory = (Supplier<? extends SortedSet<V>>)Preconditions.checkNotNull(factory);
/*  354 */       this.valueComparator = ((SortedSet<V>)factory.get()).comparator();
/*      */     }
/*      */     
/*      */     protected SortedSet<V> createCollection() {
/*  358 */       return (SortedSet<V>)this.factory.get();
/*      */     }
/*      */     
/*      */     public Comparator<? super V> valueComparator() {
/*  362 */       return this.valueComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.io.ObjectOutputStream")
/*      */     private void writeObject(ObjectOutputStream stream) throws IOException {
/*  368 */       stream.defaultWriteObject();
/*  369 */       stream.writeObject(this.factory);
/*  370 */       stream.writeObject(backingMap());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.io.ObjectInputStream")
/*      */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/*  377 */       stream.defaultReadObject();
/*  378 */       this.factory = (Supplier<? extends SortedSet<V>>)stream.readObject();
/*  379 */       this.valueComparator = ((SortedSet<V>)this.factory.get()).comparator();
/*  380 */       Map<K, Collection<V>> map = (Map<K, Collection<V>>)stream.readObject();
/*  381 */       setMap(map);
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
/*      */   public static <K, V, M extends Multimap<K, V>> M invertFrom(Multimap<? extends V, ? extends K> source, M dest) {
/*  398 */     Preconditions.checkNotNull(dest);
/*  399 */     for (Map.Entry<? extends V, ? extends K> entry : source.entries()) {
/*  400 */       dest.put(entry.getValue(), entry.getKey());
/*      */     }
/*  402 */     return dest;
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
/*      */   public static <K, V> Multimap<K, V> synchronizedMultimap(Multimap<K, V> multimap) {
/*  440 */     return Synchronized.multimap(multimap, null);
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
/*      */   public static <K, V> Multimap<K, V> unmodifiableMultimap(Multimap<K, V> delegate) {
/*  462 */     if (delegate instanceof UnmodifiableMultimap || delegate instanceof ImmutableMultimap)
/*      */     {
/*  464 */       return delegate;
/*      */     }
/*  466 */     return new UnmodifiableMultimap<K, V>(delegate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <K, V> Multimap<K, V> unmodifiableMultimap(ImmutableMultimap<K, V> delegate) {
/*  477 */     return (Multimap<K, V>)Preconditions.checkNotNull(delegate);
/*      */   }
/*      */   
/*      */   private static class UnmodifiableMultimap<K, V> extends ForwardingMultimap<K, V> implements Serializable {
/*      */     final Multimap<K, V> delegate;
/*      */     transient Collection<Map.Entry<K, V>> entries;
/*      */     transient Multiset<K> keys;
/*      */     transient Set<K> keySet;
/*      */     transient Collection<V> values;
/*      */     transient Map<K, Collection<V>> map;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     UnmodifiableMultimap(Multimap<K, V> delegate) {
/*  490 */       this.delegate = (Multimap<K, V>)Preconditions.checkNotNull(delegate);
/*      */     }
/*      */     
/*      */     protected Multimap<K, V> delegate() {
/*  494 */       return this.delegate;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  498 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Map<K, Collection<V>> asMap() {
/*  502 */       Map<K, Collection<V>> result = this.map;
/*  503 */       if (result == null) {
/*  504 */         final Map<K, Collection<V>> unmodifiableMap = Collections.unmodifiableMap(this.delegate.asMap());
/*      */         
/*  506 */         this.map = result = (Map)new ForwardingMap<K, Collection<Collection<Collection<Collection<V>>>>>() {
/*      */             protected Map<K, Collection<V>> delegate() {
/*  508 */               return unmodifiableMap;
/*      */             }
/*      */             Set<Map.Entry<K, Collection<V>>> entrySet;
/*      */             Collection<Collection<V>> asMapValues;
/*      */             
/*      */             public Set<Map.Entry<K, Collection<V>>> entrySet() {
/*  514 */               Set<Map.Entry<K, Collection<V>>> result = this.entrySet;
/*  515 */               return (result == null) ? (this.entrySet = Multimaps.unmodifiableAsMapEntries(unmodifiableMap.entrySet())) : result;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public Collection<V> get(Object key) {
/*  522 */               Collection<V> collection = (Collection<V>)unmodifiableMap.get(key);
/*  523 */               return (collection == null) ? null : Multimaps.unmodifiableValueCollection(collection);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public Collection<Collection<V>> values() {
/*  530 */               Collection<Collection<V>> result = this.asMapValues;
/*  531 */               return (result == null) ? (this.asMapValues = new Multimaps.UnmodifiableAsMapValues<V>(unmodifiableMap.values())) : result;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean containsValue(Object o) {
/*  538 */               return values().contains(o);
/*      */             }
/*      */           };
/*      */       } 
/*  542 */       return result;
/*      */     }
/*      */     
/*      */     public Collection<Map.Entry<K, V>> entries() {
/*  546 */       Collection<Map.Entry<K, V>> result = this.entries;
/*  547 */       if (result == null) {
/*  548 */         this.entries = result = Multimaps.unmodifiableEntries(this.delegate.entries());
/*      */       }
/*  550 */       return result;
/*      */     }
/*      */     
/*      */     public Collection<V> get(K key) {
/*  554 */       return Multimaps.unmodifiableValueCollection(this.delegate.get(key));
/*      */     }
/*      */     
/*      */     public Multiset<K> keys() {
/*  558 */       Multiset<K> result = this.keys;
/*  559 */       if (result == null) {
/*  560 */         this.keys = result = Multisets.unmodifiableMultiset(this.delegate.keys());
/*      */       }
/*  562 */       return result;
/*      */     }
/*      */     
/*      */     public Set<K> keySet() {
/*  566 */       Set<K> result = this.keySet;
/*  567 */       if (result == null) {
/*  568 */         this.keySet = result = Collections.unmodifiableSet(this.delegate.keySet());
/*      */       }
/*  570 */       return result;
/*      */     }
/*      */     
/*      */     public boolean put(K key, V value) {
/*  574 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean putAll(K key, Iterable<? extends V> values) {
/*  578 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
/*  583 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean remove(Object key, Object value) {
/*  587 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Collection<V> removeAll(Object key) {
/*  591 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
/*  596 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Collection<V> values() {
/*  600 */       Collection<V> result = this.values;
/*  601 */       if (result == null) {
/*  602 */         this.values = result = Collections.unmodifiableCollection(this.delegate.values());
/*      */       }
/*  604 */       return result;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class UnmodifiableAsMapValues<V>
/*      */     extends ForwardingCollection<Collection<V>>
/*      */   {
/*      */     final Collection<Collection<V>> delegate;
/*      */     
/*      */     UnmodifiableAsMapValues(Collection<Collection<V>> delegate) {
/*  614 */       this.delegate = Collections.unmodifiableCollection(delegate);
/*      */     }
/*      */     protected Collection<Collection<V>> delegate() {
/*  617 */       return this.delegate;
/*      */     }
/*      */     public Iterator<Collection<V>> iterator() {
/*  620 */       final Iterator<Collection<V>> iterator = this.delegate.iterator();
/*  621 */       return new Iterator<Collection<V>>()
/*      */         {
/*      */           public boolean hasNext() {
/*  624 */             return iterator.hasNext();
/*      */           }
/*      */           
/*      */           public Collection<V> next() {
/*  628 */             return Multimaps.unmodifiableValueCollection(iterator.next());
/*      */           }
/*      */           
/*      */           public void remove() {
/*  632 */             throw new UnsupportedOperationException();
/*      */           }
/*      */         };
/*      */     }
/*      */     public Object[] toArray() {
/*  637 */       return standardToArray();
/*      */     }
/*      */     public <T> T[] toArray(T[] array) {
/*  640 */       return (T[])standardToArray((Object[])array);
/*      */     }
/*      */     public boolean contains(Object o) {
/*  643 */       return standardContains(o);
/*      */     }
/*      */     public boolean containsAll(Collection<?> c) {
/*  646 */       return standardContainsAll(c);
/*      */     } }
/*      */   
/*      */   private static class UnmodifiableListMultimap<K, V> extends UnmodifiableMultimap<K, V> implements ListMultimap<K, V> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     UnmodifiableListMultimap(ListMultimap<K, V> delegate) {
/*  653 */       super(delegate);
/*      */     }
/*      */     public ListMultimap<K, V> delegate() {
/*  656 */       return (ListMultimap<K, V>)super.delegate();
/*      */     }
/*      */     public List<V> get(K key) {
/*  659 */       return Collections.unmodifiableList(delegate().get(key));
/*      */     }
/*      */     public List<V> removeAll(Object key) {
/*  662 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public List<V> replaceValues(K key, Iterable<? extends V> values) {
/*  666 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   private static class UnmodifiableSetMultimap<K, V> extends UnmodifiableMultimap<K, V> implements SetMultimap<K, V> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     UnmodifiableSetMultimap(SetMultimap<K, V> delegate) {
/*  674 */       super(delegate);
/*      */     }
/*      */     public SetMultimap<K, V> delegate() {
/*  677 */       return (SetMultimap<K, V>)super.delegate();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<V> get(K key) {
/*  684 */       return Collections.unmodifiableSet(delegate().get(key));
/*      */     }
/*      */     public Set<Map.Entry<K, V>> entries() {
/*  687 */       return Maps.unmodifiableEntrySet(delegate().entries());
/*      */     }
/*      */     public Set<V> removeAll(Object key) {
/*  690 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Set<V> replaceValues(K key, Iterable<? extends V> values) {
/*  694 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   private static class UnmodifiableSortedSetMultimap<K, V> extends UnmodifiableSetMultimap<K, V> implements SortedSetMultimap<K, V> {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     UnmodifiableSortedSetMultimap(SortedSetMultimap<K, V> delegate) {
/*  702 */       super(delegate);
/*      */     }
/*      */     public SortedSetMultimap<K, V> delegate() {
/*  705 */       return (SortedSetMultimap<K, V>)super.delegate();
/*      */     }
/*      */     public SortedSet<V> get(K key) {
/*  708 */       return Collections.unmodifiableSortedSet(delegate().get(key));
/*      */     }
/*      */     public SortedSet<V> removeAll(Object key) {
/*  711 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) {
/*  715 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Comparator<? super V> valueComparator() {
/*  719 */       return delegate().valueComparator();
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
/*      */   public static <K, V> SetMultimap<K, V> synchronizedSetMultimap(SetMultimap<K, V> multimap) {
/*  738 */     return Synchronized.setMultimap(multimap, null);
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
/*      */   public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(SetMultimap<K, V> delegate) {
/*  761 */     if (delegate instanceof UnmodifiableSetMultimap || delegate instanceof ImmutableSetMultimap)
/*      */     {
/*  763 */       return delegate;
/*      */     }
/*  765 */     return new UnmodifiableSetMultimap<K, V>(delegate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(ImmutableSetMultimap<K, V> delegate) {
/*  776 */     return (SetMultimap<K, V>)Preconditions.checkNotNull(delegate);
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
/*      */   public static <K, V> SortedSetMultimap<K, V> synchronizedSortedSetMultimap(SortedSetMultimap<K, V> multimap) {
/*  793 */     return Synchronized.sortedSetMultimap(multimap, null);
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
/*      */   public static <K, V> SortedSetMultimap<K, V> unmodifiableSortedSetMultimap(SortedSetMultimap<K, V> delegate) {
/*  816 */     if (delegate instanceof UnmodifiableSortedSetMultimap) {
/*  817 */       return delegate;
/*      */     }
/*  819 */     return new UnmodifiableSortedSetMultimap<K, V>(delegate);
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
/*      */   public static <K, V> ListMultimap<K, V> synchronizedListMultimap(ListMultimap<K, V> multimap) {
/*  833 */     return Synchronized.listMultimap(multimap, null);
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
/*      */   public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ListMultimap<K, V> delegate) {
/*  856 */     if (delegate instanceof UnmodifiableListMultimap || delegate instanceof ImmutableListMultimap)
/*      */     {
/*  858 */       return delegate;
/*      */     }
/*  860 */     return new UnmodifiableListMultimap<K, V>(delegate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ImmutableListMultimap<K, V> delegate) {
/*  871 */     return (ListMultimap<K, V>)Preconditions.checkNotNull(delegate);
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
/*      */   private static <V> Collection<V> unmodifiableValueCollection(Collection<V> collection) {
/*  884 */     if (collection instanceof SortedSet)
/*  885 */       return Collections.unmodifiableSortedSet((SortedSet<V>)collection); 
/*  886 */     if (collection instanceof Set)
/*  887 */       return Collections.unmodifiableSet((Set<? extends V>)collection); 
/*  888 */     if (collection instanceof List) {
/*  889 */       return Collections.unmodifiableList((List<? extends V>)collection);
/*      */     }
/*  891 */     return Collections.unmodifiableCollection(collection);
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
/*      */   private static <K, V> Map.Entry<K, Collection<V>> unmodifiableAsMapEntry(final Map.Entry<K, Collection<V>> entry) {
/*  907 */     Preconditions.checkNotNull(entry);
/*  908 */     return (Map.Entry)new AbstractMapEntry<K, Collection<Collection<V>>>() {
/*      */         public K getKey() {
/*  910 */           return (K)entry.getKey();
/*      */         }
/*      */         
/*      */         public Collection<V> getValue() {
/*  914 */           return Multimaps.unmodifiableValueCollection((Collection)entry.getValue());
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
/*      */ 
/*      */   
/*      */   private static <K, V> Collection<Map.Entry<K, V>> unmodifiableEntries(Collection<Map.Entry<K, V>> entries) {
/*  930 */     if (entries instanceof Set) {
/*  931 */       return Maps.unmodifiableEntrySet((Set<Map.Entry<K, V>>)entries);
/*      */     }
/*  933 */     return new Maps.UnmodifiableEntries<K, V>(Collections.unmodifiableCollection(entries));
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
/*      */   private static <K, V> Set<Map.Entry<K, Collection<V>>> unmodifiableAsMapEntries(Set<Map.Entry<K, Collection<V>>> asMapEntries) {
/*  949 */     return new UnmodifiableAsMapEntries<K, V>(Collections.unmodifiableSet(asMapEntries));
/*      */   }
/*      */   
/*      */   static class UnmodifiableAsMapEntries<K, V>
/*      */     extends ForwardingSet<Map.Entry<K, Collection<V>>>
/*      */   {
/*      */     private final Set<Map.Entry<K, Collection<V>>> delegate;
/*      */     
/*      */     UnmodifiableAsMapEntries(Set<Map.Entry<K, Collection<V>>> delegate) {
/*  958 */       this.delegate = delegate;
/*      */     }
/*      */     
/*      */     protected Set<Map.Entry<K, Collection<V>>> delegate() {
/*  962 */       return this.delegate;
/*      */     }
/*      */     
/*      */     public Iterator<Map.Entry<K, Collection<V>>> iterator() {
/*  966 */       final Iterator<Map.Entry<K, Collection<V>>> iterator = this.delegate.iterator();
/*  967 */       return new ForwardingIterator<Map.Entry<K, Collection<V>>>() {
/*      */           protected Iterator<Map.Entry<K, Collection<V>>> delegate() {
/*  969 */             return iterator;
/*      */           }
/*      */           public Map.Entry<K, Collection<V>> next() {
/*  972 */             return Multimaps.unmodifiableAsMapEntry(iterator.next());
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/*  978 */       return standardToArray();
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] array) {
/*  982 */       return (T[])standardToArray((Object[])array);
/*      */     }
/*      */     
/*      */     public boolean contains(Object o) {
/*  986 */       return Maps.containsEntryImpl(delegate(), o);
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> c) {
/*  990 */       return standardContainsAll(c);
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  994 */       return standardEquals(object);
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
/*      */   public static <K, V> SetMultimap<K, V> forMap(Map<K, V> map) {
/* 1016 */     return new MapMultimap<K, V>(map);
/*      */   }
/*      */   
/*      */   private static class MapMultimap<K, V>
/*      */     implements SetMultimap<K, V>, Serializable
/*      */   {
/*      */     final Map<K, V> map;
/*      */     transient Map<K, Collection<V>> asMap;
/*      */     
/*      */     MapMultimap(Map<K, V> map) {
/* 1026 */       this.map = (Map<K, V>)Preconditions.checkNotNull(map);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1031 */       return this.map.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1036 */       return this.map.isEmpty();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object key) {
/* 1041 */       return this.map.containsKey(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(Object value) {
/* 1046 */       return this.map.containsValue(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsEntry(Object key, Object value) {
/* 1051 */       return this.map.entrySet().contains(Maps.immutableEntry(key, value));
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<V> get(final K key) {
/* 1056 */       return new AbstractSet<V>() {
/*      */           public Iterator<V> iterator() {
/* 1058 */             return new Iterator<V>()
/*      */               {
/*      */                 int i;
/*      */                 
/*      */                 public boolean hasNext() {
/* 1063 */                   return (this.i == 0 && Multimaps.MapMultimap.this.map.containsKey(key));
/*      */                 }
/*      */ 
/*      */                 
/*      */                 public V next() {
/* 1068 */                   if (!hasNext()) {
/* 1069 */                     throw new NoSuchElementException();
/*      */                   }
/* 1071 */                   this.i++;
/* 1072 */                   return (V)Multimaps.MapMultimap.this.map.get(key);
/*      */                 }
/*      */ 
/*      */                 
/*      */                 public void remove() {
/* 1077 */                   Preconditions.checkState((this.i == 1));
/* 1078 */                   this.i = -1;
/* 1079 */                   Multimaps.MapMultimap.this.map.remove(key);
/*      */                 }
/*      */               };
/*      */           }
/*      */           
/*      */           public int size() {
/* 1085 */             return Multimaps.MapMultimap.this.map.containsKey(key) ? 1 : 0;
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean put(K key, V value) {
/* 1092 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean putAll(K key, Iterable<? extends V> values) {
/* 1097 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
/* 1102 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<V> replaceValues(K key, Iterable<? extends V> values) {
/* 1107 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object key, Object value) {
/* 1112 */       return this.map.entrySet().remove(Maps.immutableEntry(key, value));
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<V> removeAll(Object key) {
/* 1117 */       Set<V> values = new HashSet<V>(2);
/* 1118 */       if (!this.map.containsKey(key)) {
/* 1119 */         return values;
/*      */       }
/* 1121 */       values.add(this.map.remove(key));
/* 1122 */       return values;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1127 */       this.map.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 1132 */       return this.map.keySet();
/*      */     }
/*      */ 
/*      */     
/*      */     public Multiset<K> keys() {
/* 1137 */       return Multisets.forSet(this.map.keySet());
/*      */     }
/*      */ 
/*      */     
/*      */     public Collection<V> values() {
/* 1142 */       return this.map.values();
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entries() {
/* 1147 */       return this.map.entrySet();
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<K, Collection<V>> asMap() {
/* 1152 */       Map<K, Collection<V>> result = this.asMap;
/* 1153 */       if (result == null) {
/* 1154 */         this.asMap = result = new AsMap();
/*      */       }
/* 1156 */       return result;
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/* 1160 */       if (object == this) {
/* 1161 */         return true;
/*      */       }
/* 1163 */       if (object instanceof Multimap) {
/* 1164 */         Multimap<?, ?> that = (Multimap<?, ?>)object;
/* 1165 */         return (size() == that.size() && asMap().equals(that.asMap()));
/*      */       } 
/* 1167 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1171 */       return this.map.hashCode();
/*      */     }
/*      */     
/* 1174 */     private static final Joiner.MapJoiner JOINER = Joiner.on("], ").withKeyValueSeparator("=[").useForNull("null");
/*      */     private static final long serialVersionUID = 7845222491160860175L;
/*      */     
/*      */     public String toString() {
/* 1178 */       if (this.map.isEmpty()) {
/* 1179 */         return "{}";
/*      */       }
/* 1181 */       StringBuilder builder = Collections2.newStringBuilderForCollection(this.map.size()).append('{');
/*      */       
/* 1183 */       JOINER.appendTo(builder, this.map);
/* 1184 */       return builder.append("]}").toString();
/*      */     }
/*      */     
/*      */     class AsMapEntries
/*      */       extends AbstractSet<Map.Entry<K, Collection<V>>> {
/*      */       public int size() {
/* 1190 */         return Multimaps.MapMultimap.this.map.size();
/*      */       }
/*      */       
/*      */       public Iterator<Map.Entry<K, Collection<V>>> iterator() {
/* 1194 */         return new Iterator<Map.Entry<K, Collection<V>>>() {
/* 1195 */             final Iterator<K> keys = Multimaps.MapMultimap.this.map.keySet().iterator();
/*      */ 
/*      */             
/*      */             public boolean hasNext() {
/* 1199 */               return this.keys.hasNext();
/*      */             }
/*      */             
/*      */             public Map.Entry<K, Collection<V>> next() {
/* 1203 */               final K key = this.keys.next();
/* 1204 */               return (Map.Entry)new AbstractMapEntry<K, Collection<Collection<V>>>() {
/*      */                   public K getKey() {
/* 1206 */                     return (K)key;
/*      */                   }
/*      */                   public Collection<V> getValue() {
/* 1209 */                     return Multimaps.MapMultimap.this.get(key);
/*      */                   }
/*      */                 };
/*      */             }
/*      */             
/*      */             public void remove() {
/* 1215 */               this.keys.remove();
/*      */             }
/*      */           };
/*      */       }
/*      */       
/*      */       public boolean contains(Object o) {
/* 1221 */         if (!(o instanceof Map.Entry)) {
/* 1222 */           return false;
/*      */         }
/* 1224 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 1225 */         if (!(entry.getValue() instanceof Set)) {
/* 1226 */           return false;
/*      */         }
/* 1228 */         Set<?> set = (Set)entry.getValue();
/* 1229 */         return (set.size() == 1 && Multimaps.MapMultimap.this.containsEntry(entry.getKey(), set.iterator().next()));
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean remove(Object o) {
/* 1234 */         if (!(o instanceof Map.Entry)) {
/* 1235 */           return false;
/*      */         }
/* 1237 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 1238 */         if (!(entry.getValue() instanceof Set)) {
/* 1239 */           return false;
/*      */         }
/* 1241 */         Set<?> set = (Set)entry.getValue();
/* 1242 */         return (set.size() == 1 && Multimaps.MapMultimap.this.map.entrySet().remove(Maps.immutableEntry(entry.getKey(), set.iterator().next())));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     class AsMap
/*      */       extends Maps.ImprovedAbstractMap<K, Collection<V>>
/*      */     {
/*      */       protected Set<Map.Entry<K, Collection<V>>> createEntrySet() {
/* 1251 */         return new Multimaps.MapMultimap.AsMapEntries();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean containsKey(Object key) {
/* 1257 */         return Multimaps.MapMultimap.this.map.containsKey(key);
/*      */       }
/*      */ 
/*      */       
/*      */       public Collection<V> get(Object key) {
/* 1262 */         Collection<V> collection = Multimaps.MapMultimap.this.get(key);
/* 1263 */         return collection.isEmpty() ? null : collection;
/*      */       }
/*      */       
/*      */       public Collection<V> remove(Object key) {
/* 1267 */         Collection<V> collection = Multimaps.MapMultimap.this.removeAll(key);
/* 1268 */         return collection.isEmpty() ? null : collection;
/*      */       }
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
/*      */   @Beta
/*      */   public static <K, V1, V2> Multimap<K, V2> transformValues(Multimap<K, V1> fromMultimap, final Function<? super V1, V2> function) {
/* 1321 */     Preconditions.checkNotNull(function);
/* 1322 */     Maps.EntryTransformer<K, V1, V2> transformer = new Maps.EntryTransformer<K, V1, V2>()
/*      */       {
/*      */         public V2 transformEntry(K key, V1 value)
/*      */         {
/* 1326 */           return (V2)function.apply(value);
/*      */         }
/*      */       };
/* 1329 */     return transformEntries(fromMultimap, transformer);
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
/*      */   @Beta
/*      */   public static <K, V1, V2> Multimap<K, V2> transformEntries(Multimap<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1391 */     return new TransformedEntriesMultimap<K, V1, V2>(fromMap, transformer);
/*      */   }
/*      */   
/*      */   private static class TransformedEntriesMultimap<K, V1, V2> implements Multimap<K, V2> { final Multimap<K, V1> fromMultimap;
/*      */     final Maps.EntryTransformer<? super K, ? super V1, V2> transformer;
/*      */     private transient Map<K, Collection<V2>> asMap;
/*      */     private transient Collection<Map.Entry<K, V2>> entries;
/*      */     private transient Collection<V2> values;
/*      */     
/*      */     TransformedEntriesMultimap(Multimap<K, V1> fromMultimap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1401 */       this.fromMultimap = (Multimap<K, V1>)Preconditions.checkNotNull(fromMultimap);
/* 1402 */       this.transformer = (Maps.EntryTransformer<? super K, ? super V1, V2>)Preconditions.checkNotNull(transformer);
/*      */     }
/*      */     
/*      */     Collection<V2> transform(final K key, Collection<V1> values) {
/* 1406 */       return Collections2.transform(values, new Function<V1, V2>() {
/*      */             public V2 apply(V1 value) {
/* 1408 */               return Multimaps.TransformedEntriesMultimap.this.transformer.transformEntry(key, value);
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map<K, Collection<V2>> asMap() {
/* 1416 */       if (this.asMap == null) {
/* 1417 */         Map<K, Collection<V2>> aM = Maps.transformEntries(this.fromMultimap.asMap(), (Maps.EntryTransformer)new Maps.EntryTransformer<K, Collection<Collection<V1>>, Collection<Collection<V2>>>()
/*      */             {
/*      */               
/*      */               public Collection<V2> transformEntry(K key, Collection<V1> value)
/*      */               {
/* 1422 */                 return Multimaps.TransformedEntriesMultimap.this.transform(key, value);
/*      */               }
/*      */             });
/* 1425 */         this.asMap = aM;
/* 1426 */         return aM;
/*      */       } 
/* 1428 */       return this.asMap;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1432 */       this.fromMultimap.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsEntry(Object key, Object value) {
/* 1437 */       Collection<V2> values = get((K)key);
/* 1438 */       return values.contains(value);
/*      */     }
/*      */     
/*      */     public boolean containsKey(Object key) {
/* 1442 */       return this.fromMultimap.containsKey(key);
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object value) {
/* 1446 */       return values().contains(value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<Map.Entry<K, V2>> entries() {
/* 1452 */       if (this.entries == null) {
/* 1453 */         Collection<Map.Entry<K, V2>> es = new TransformedEntries(this.transformer);
/* 1454 */         this.entries = es;
/* 1455 */         return es;
/*      */       } 
/* 1457 */       return this.entries;
/*      */     }
/*      */ 
/*      */     
/*      */     private class TransformedEntries
/*      */       extends Collections2.TransformedCollection<Map.Entry<K, V1>, Map.Entry<K, V2>>
/*      */     {
/*      */       TransformedEntries(Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1465 */         super(Multimaps.TransformedEntriesMultimap.this.fromMultimap.entries(), new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>(Multimaps.TransformedEntriesMultimap.this, transformer)
/*      */             {
/*      */               public Map.Entry<K, V2> apply(final Map.Entry<K, V1> entry) {
/* 1468 */                 return new AbstractMapEntry<K, V2>()
/*      */                   {
/*      */                     public K getKey() {
/* 1471 */                       return (K)entry.getKey();
/*      */                     }
/*      */                     
/*      */                     public V2 getValue() {
/* 1475 */                       return (V2)transformer.transformEntry(entry.getKey(), entry.getValue());
/*      */                     }
/*      */                   };
/*      */               }
/*      */             });
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean contains(Object o) {
/* 1484 */         if (o instanceof Map.Entry) {
/* 1485 */           Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 1486 */           return Multimaps.TransformedEntriesMultimap.this.containsEntry(entry.getKey(), entry.getValue());
/*      */         } 
/* 1488 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean remove(Object o) {
/* 1493 */         if (o instanceof Map.Entry) {
/* 1494 */           Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 1495 */           Collection<V2> values = Multimaps.TransformedEntriesMultimap.this.get(entry.getKey());
/* 1496 */           return values.remove(entry.getValue());
/*      */         } 
/* 1498 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public Collection<V2> get(K key) {
/* 1504 */       return transform(key, this.fromMultimap.get(key));
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1508 */       return this.fromMultimap.isEmpty();
/*      */     }
/*      */     
/*      */     public Set<K> keySet() {
/* 1512 */       return this.fromMultimap.keySet();
/*      */     }
/*      */     
/*      */     public Multiset<K> keys() {
/* 1516 */       return this.fromMultimap.keys();
/*      */     }
/*      */     
/*      */     public boolean put(K key, V2 value) {
/* 1520 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean putAll(K key, Iterable<? extends V2> values) {
/* 1524 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean putAll(Multimap<? extends K, ? extends V2> multimap) {
/* 1529 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object key, Object value) {
/* 1534 */       return get((K)key).remove(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public Collection<V2> removeAll(Object key) {
/* 1539 */       return transform((K)key, this.fromMultimap.removeAll(key));
/*      */     }
/*      */ 
/*      */     
/*      */     public Collection<V2> replaceValues(K key, Iterable<? extends V2> values) {
/* 1544 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int size() {
/* 1548 */       return this.fromMultimap.size();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<V2> values() {
/* 1554 */       if (this.values == null) {
/* 1555 */         Collection<V2> vs = Collections2.transform(this.fromMultimap.entries(), new Function<Map.Entry<K, V1>, V2>()
/*      */             {
/*      */               public V2 apply(Map.Entry<K, V1> entry)
/*      */               {
/* 1559 */                 return (V2)Multimaps.TransformedEntriesMultimap.this.transformer.transformEntry(entry.getKey(), entry.getValue());
/*      */               }
/*      */             });
/*      */         
/* 1563 */         this.values = vs;
/* 1564 */         return vs;
/*      */       } 
/* 1566 */       return this.values;
/*      */     }
/*      */     
/*      */     public boolean equals(Object obj) {
/* 1570 */       if (obj instanceof Multimap) {
/* 1571 */         Multimap<?, ?> other = (Multimap<?, ?>)obj;
/* 1572 */         return asMap().equals(other.asMap());
/*      */       } 
/* 1574 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1578 */       return asMap().hashCode();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1582 */       return asMap().toString();
/*      */     } }
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
/*      */   @Beta
/*      */   public static <K, V1, V2> ListMultimap<K, V2> transformValues(ListMultimap<K, V1> fromMultimap, final Function<? super V1, V2> function) {
/* 1631 */     Preconditions.checkNotNull(function);
/* 1632 */     Maps.EntryTransformer<K, V1, V2> transformer = new Maps.EntryTransformer<K, V1, V2>()
/*      */       {
/*      */         public V2 transformEntry(K key, V1 value)
/*      */         {
/* 1636 */           return (V2)function.apply(value);
/*      */         }
/*      */       };
/* 1639 */     return transformEntries(fromMultimap, transformer);
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
/*      */   @Beta
/*      */   public static <K, V1, V2> ListMultimap<K, V2> transformEntries(ListMultimap<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1698 */     return new TransformedEntriesListMultimap<K, V1, V2>(fromMap, transformer);
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class TransformedEntriesListMultimap<K, V1, V2>
/*      */     extends TransformedEntriesMultimap<K, V1, V2>
/*      */     implements ListMultimap<K, V2>
/*      */   {
/*      */     TransformedEntriesListMultimap(ListMultimap<K, V1> fromMultimap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
/* 1707 */       super(fromMultimap, transformer);
/*      */     }
/*      */     
/*      */     List<V2> transform(final K key, Collection<V1> values) {
/* 1711 */       return Lists.transform((List<V1>)values, new Function<V1, V2>() {
/*      */             public V2 apply(V1 value) {
/* 1713 */               return Multimaps.TransformedEntriesListMultimap.this.transformer.transformEntry(key, value);
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     public List<V2> get(K key) {
/* 1719 */       return transform(key, this.fromMultimap.get(key));
/*      */     }
/*      */ 
/*      */     
/*      */     public List<V2> removeAll(Object key) {
/* 1724 */       return transform((K)key, this.fromMultimap.removeAll(key));
/*      */     }
/*      */ 
/*      */     
/*      */     public List<V2> replaceValues(K key, Iterable<? extends V2> values) {
/* 1729 */       throw new UnsupportedOperationException();
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
/*      */   public static <K, V> ImmutableListMultimap<K, V> index(Iterable<V> values, Function<? super V, K> keyFunction) {
/* 1777 */     return index(values.iterator(), keyFunction);
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
/*      */   @Deprecated
/*      */   @Beta
/*      */   public static <K, V, I extends Iterable<V> & Iterator<V>> ImmutableListMultimap<K, V> index(I values, Function<? super V, K> keyFunction) {
/* 1794 */     Iterable<V> valuesIterable = (Iterable<V>)Preconditions.checkNotNull(values);
/* 1795 */     return index(valuesIterable, keyFunction);
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
/*      */   public static <K, V> ImmutableListMultimap<K, V> index(Iterator<V> values, Function<? super V, K> keyFunction) {
/* 1843 */     Preconditions.checkNotNull(keyFunction);
/* 1844 */     ImmutableListMultimap.Builder<K, V> builder = ImmutableListMultimap.builder();
/*      */     
/* 1846 */     while (values.hasNext()) {
/* 1847 */       V value = values.next();
/* 1848 */       Preconditions.checkNotNull(value, values);
/* 1849 */       builder.put((K)keyFunction.apply(value), value);
/*      */     } 
/* 1851 */     return builder.build();
/*      */   }
/*      */   
/*      */   static abstract class Keys<K, V> extends AbstractMultiset<K> {
/*      */     private Set<Multiset.Entry<K>> entrySet;
/*      */     
/*      */     abstract Multimap<K, V> multimap();
/*      */     
/*      */     public Set<Multiset.Entry<K>> entrySet() {
/* 1860 */       return (this.entrySet == null) ? (this.entrySet = createEntrySet()) : this.entrySet;
/*      */     }
/*      */     
/*      */     Iterator<Multiset.Entry<K>> entryIterator() {
/* 1864 */       final Iterator<Map.Entry<K, Collection<V>>> backingIterator = multimap().asMap().entrySet().iterator();
/*      */       
/* 1866 */       return new Iterator<Multiset.Entry<K>>() {
/*      */           public boolean hasNext() {
/* 1868 */             return backingIterator.hasNext();
/*      */           }
/*      */           
/*      */           public Multiset.Entry<K> next() {
/* 1872 */             final Map.Entry<K, Collection<V>> backingEntry = backingIterator.next();
/*      */             
/* 1874 */             return new Multisets.AbstractEntry<K>() {
/*      */                 public K getElement() {
/* 1876 */                   return (K)backingEntry.getKey();
/*      */                 }
/*      */                 
/*      */                 public int getCount() {
/* 1880 */                   return ((Collection)backingEntry.getValue()).size();
/*      */                 }
/*      */               };
/*      */           }
/*      */           
/*      */           public void remove() {
/* 1886 */             backingIterator.remove();
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     int distinctElements() {
/* 1892 */       return multimap().asMap().size();
/*      */     }
/*      */     
/*      */     Set<Multiset.Entry<K>> createEntrySet() {
/* 1896 */       return new KeysEntrySet();
/*      */     }
/*      */     
/*      */     class KeysEntrySet extends Multisets.EntrySet<K> {
/*      */       Multiset<K> multiset() {
/* 1901 */         return Multimaps.Keys.this;
/*      */       }
/*      */       
/*      */       public Iterator<Multiset.Entry<K>> iterator() {
/* 1905 */         return Multimaps.Keys.this.entryIterator();
/*      */       }
/*      */       
/*      */       public int size() {
/* 1909 */         return Multimaps.Keys.this.distinctElements();
/*      */       }
/*      */       
/*      */       public boolean isEmpty() {
/* 1913 */         return Multimaps.Keys.this.multimap().isEmpty();
/*      */       }
/*      */       
/*      */       public boolean contains(@Nullable Object o) {
/* 1917 */         if (o instanceof Multiset.Entry) {
/* 1918 */           Multiset.Entry<?> entry = (Multiset.Entry)o;
/* 1919 */           Collection<V> collection = (Collection<V>)Multimaps.Keys.this.multimap().asMap().get(entry.getElement());
/* 1920 */           return (collection != null && collection.size() == entry.getCount());
/*      */         } 
/* 1922 */         return false;
/*      */       }
/*      */       
/*      */       public boolean remove(@Nullable Object o) {
/* 1926 */         if (o instanceof Multiset.Entry) {
/* 1927 */           Multiset.Entry<?> entry = (Multiset.Entry)o;
/* 1928 */           Collection<V> collection = (Collection<V>)Multimaps.Keys.this.multimap().asMap().get(entry.getElement());
/* 1929 */           if (collection != null && collection.size() == entry.getCount()) {
/* 1930 */             collection.clear();
/* 1931 */             return true;
/*      */           } 
/*      */         } 
/* 1934 */         return false;
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean contains(@Nullable Object element) {
/* 1939 */       return multimap().containsKey(element);
/*      */     }
/*      */     
/*      */     public Iterator<K> iterator() {
/* 1943 */       return Iterators.transform(multimap().entries().iterator(), new Function<Map.Entry<K, V>, K>()
/*      */           {
/*      */             public K apply(Map.Entry<K, V> entry) {
/* 1946 */               return entry.getKey();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     public int count(@Nullable Object element) {
/*      */       try {
/* 1953 */         if (multimap().containsKey(element)) {
/* 1954 */           Collection<V> values = (Collection<V>)multimap().asMap().get(element);
/* 1955 */           return (values == null) ? 0 : values.size();
/*      */         } 
/* 1957 */         return 0;
/* 1958 */       } catch (ClassCastException e) {
/* 1959 */         return 0;
/* 1960 */       } catch (NullPointerException e) {
/* 1961 */         return 0;
/*      */       } 
/*      */     }
/*      */     public int remove(@Nullable Object element, int occurrences) {
/*      */       Collection<V> values;
/* 1966 */       Preconditions.checkArgument((occurrences >= 0));
/* 1967 */       if (occurrences == 0) {
/* 1968 */         return count(element);
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 1973 */         values = (Collection<V>)multimap().asMap().get(element);
/* 1974 */       } catch (ClassCastException e) {
/* 1975 */         return 0;
/* 1976 */       } catch (NullPointerException e) {
/* 1977 */         return 0;
/*      */       } 
/*      */       
/* 1980 */       if (values == null) {
/* 1981 */         return 0;
/*      */       }
/*      */       
/* 1984 */       int oldCount = values.size();
/* 1985 */       if (occurrences >= oldCount) {
/* 1986 */         values.clear();
/*      */       } else {
/* 1988 */         Iterator<V> iterator = values.iterator();
/* 1989 */         for (int i = 0; i < occurrences; i++) {
/* 1990 */           iterator.next();
/* 1991 */           iterator.remove();
/*      */         } 
/*      */       } 
/* 1994 */       return oldCount;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1998 */       multimap().clear();
/*      */     }
/*      */     
/*      */     public Set<K> elementSet() {
/* 2002 */       return multimap().keySet();
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Values<K, V> extends AbstractCollection<V> {
/*      */     abstract Multimap<K, V> multimap();
/*      */     
/*      */     public Iterator<V> iterator() {
/* 2010 */       final Iterator<Map.Entry<K, V>> backingIterator = multimap().entries().iterator();
/*      */       
/* 2012 */       return new Iterator<V>() {
/*      */           public boolean hasNext() {
/* 2014 */             return backingIterator.hasNext();
/*      */           }
/*      */           
/*      */           public V next() {
/* 2018 */             return (V)((Map.Entry)backingIterator.next()).getValue();
/*      */           }
/*      */           
/*      */           public void remove() {
/* 2022 */             backingIterator.remove();
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public int size() {
/* 2028 */       return multimap().size();
/*      */     }
/*      */     
/*      */     public boolean contains(@Nullable Object o) {
/* 2032 */       return multimap().containsValue(o);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 2036 */       multimap().clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class Entries<K, V>
/*      */     extends AbstractCollection<Map.Entry<K, V>>
/*      */   {
/*      */     abstract Multimap<K, V> multimap();
/*      */ 
/*      */     
/*      */     public int size() {
/* 2048 */       return multimap().size();
/*      */     }
/*      */     
/*      */     public boolean contains(@Nullable Object o) {
/* 2052 */       if (o instanceof Map.Entry) {
/* 2053 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 2054 */         return multimap().containsEntry(entry.getKey(), entry.getValue());
/*      */       } 
/* 2056 */       return false;
/*      */     }
/*      */     
/*      */     public boolean remove(@Nullable Object o) {
/* 2060 */       if (o instanceof Map.Entry) {
/* 2061 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 2062 */         return multimap().remove(entry.getKey(), entry.getValue());
/*      */       } 
/* 2064 */       return false;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 2068 */       multimap().clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class EntrySet<K, V>
/*      */     extends Entries<K, V>
/*      */     implements Set<Map.Entry<K, V>>
/*      */   {
/*      */     public int hashCode() {
/* 2078 */       return Sets.hashCodeImpl(this);
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object obj) {
/* 2082 */       return Sets.equalsImpl(this, obj);
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
/*      */   static abstract class AsMap<K, V>
/*      */     extends Maps.ImprovedAbstractMap<K, Collection<V>>
/*      */   {
/*      */     protected Set<Map.Entry<K, Collection<V>>> createEntrySet() {
/* 2098 */       return new EntrySet();
/*      */     }
/*      */     
/*      */     void removeValuesForKey(Object key) {
/* 2102 */       multimap().removeAll(key);
/*      */     }
/*      */     
/*      */     class EntrySet extends Maps.EntrySet<K, Collection<V>> {
/*      */       Map<K, Collection<V>> map() {
/* 2107 */         return Multimaps.AsMap.this;
/*      */       }
/*      */       
/*      */       public Iterator<Map.Entry<K, Collection<V>>> iterator() {
/* 2111 */         return Multimaps.AsMap.this.entryIterator();
/*      */       }
/*      */       
/*      */       public boolean remove(Object o) {
/* 2115 */         if (!contains(o)) {
/* 2116 */           return false;
/*      */         }
/* 2118 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
/* 2119 */         Multimaps.AsMap.this.removeValuesForKey(entry.getKey());
/* 2120 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public Collection<V> get(Object key) {
/* 2126 */       return containsKey(key) ? multimap().get((K)key) : null;
/*      */     }
/*      */     
/*      */     public Collection<V> remove(Object key) {
/* 2130 */       return containsKey(key) ? multimap().removeAll(key) : null;
/*      */     }
/*      */     
/*      */     public Set<K> keySet() {
/* 2134 */       return multimap().keySet();
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 2138 */       return multimap().isEmpty();
/*      */     }
/*      */     
/*      */     public boolean containsKey(Object key) {
/* 2142 */       return multimap().containsKey(key);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 2146 */       multimap().clear();
/*      */     }
/*      */     
/*      */     abstract Multimap<K, V> multimap();
/*      */     
/*      */     public abstract int size();
/*      */     
/*      */     abstract Iterator<Map.Entry<K, Collection<V>>> entryIterator();
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Multimaps.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */