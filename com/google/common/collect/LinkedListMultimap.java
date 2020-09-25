/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.GwtIncompatible;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSequentialList;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
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
/*      */ @GwtCompatible(serializable = true, emulated = true)
/*      */ public class LinkedListMultimap<K, V>
/*      */   implements ListMultimap<K, V>, Serializable
/*      */ {
/*      */   private transient Node<K, V> head;
/*      */   private transient Node<K, V> tail;
/*      */   private transient Multiset<K> keyCount;
/*      */   private transient Map<K, Node<K, V>> keyToKeyHead;
/*      */   private transient Map<K, Node<K, V>> keyToKeyTail;
/*      */   private transient Set<K> keySet;
/*      */   private transient Multiset<K> keys;
/*      */   private transient List<V> valuesList;
/*      */   private transient List<Map.Entry<K, V>> entries;
/*      */   private transient Map<K, Collection<V>> map;
/*      */   @GwtIncompatible("java serialization not supported")
/*      */   private static final long serialVersionUID = 0L;
/*      */   
/*      */   private static final class Node<K, V>
/*      */   {
/*      */     final K key;
/*      */     V value;
/*      */     Node<K, V> next;
/*      */     Node<K, V> previous;
/*      */     Node<K, V> nextSibling;
/*      */     Node<K, V> previousSibling;
/*      */     
/*      */     Node(@Nullable K key, @Nullable V value) {
/*  120 */       this.key = key;
/*  121 */       this.value = value;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  125 */       return (new StringBuilder()).append(this.key).append("=").append(this.value).toString();
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
/*      */   public static <K, V> LinkedListMultimap<K, V> create() {
/*  140 */     return new LinkedListMultimap<K, V>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> LinkedListMultimap<K, V> create(int expectedKeys) {
/*  151 */     return new LinkedListMultimap<K, V>(expectedKeys);
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
/*      */   public static <K, V> LinkedListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
/*  163 */     return new LinkedListMultimap<K, V>(multimap);
/*      */   }
/*      */   
/*      */   LinkedListMultimap() {
/*  167 */     this.keyCount = LinkedHashMultiset.create();
/*  168 */     this.keyToKeyHead = Maps.newHashMap();
/*  169 */     this.keyToKeyTail = Maps.newHashMap();
/*      */   }
/*      */   
/*      */   private LinkedListMultimap(int expectedKeys) {
/*  173 */     this.keyCount = LinkedHashMultiset.create(expectedKeys);
/*  174 */     this.keyToKeyHead = Maps.newHashMapWithExpectedSize(expectedKeys);
/*  175 */     this.keyToKeyTail = Maps.newHashMapWithExpectedSize(expectedKeys);
/*      */   }
/*      */   
/*      */   private LinkedListMultimap(Multimap<? extends K, ? extends V> multimap) {
/*  179 */     this(multimap.keySet().size());
/*  180 */     putAll(multimap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node<K, V> addNode(@Nullable K key, @Nullable V value, @Nullable Node<K, V> nextSibling) {
/*  191 */     Node<K, V> node = new Node<K, V>(key, value);
/*  192 */     if (this.head == null) {
/*  193 */       this.head = this.tail = node;
/*  194 */       this.keyToKeyHead.put(key, node);
/*  195 */       this.keyToKeyTail.put(key, node);
/*  196 */     } else if (nextSibling == null) {
/*  197 */       this.tail.next = node;
/*  198 */       node.previous = this.tail;
/*  199 */       Node<K, V> keyTail = this.keyToKeyTail.get(key);
/*  200 */       if (keyTail == null) {
/*  201 */         this.keyToKeyHead.put(key, node);
/*      */       } else {
/*  203 */         keyTail.nextSibling = node;
/*  204 */         node.previousSibling = keyTail;
/*      */       } 
/*  206 */       this.keyToKeyTail.put(key, node);
/*  207 */       this.tail = node;
/*      */     } else {
/*  209 */       node.previous = nextSibling.previous;
/*  210 */       node.previousSibling = nextSibling.previousSibling;
/*  211 */       node.next = nextSibling;
/*  212 */       node.nextSibling = nextSibling;
/*  213 */       if (nextSibling.previousSibling == null) {
/*  214 */         this.keyToKeyHead.put(key, node);
/*      */       } else {
/*  216 */         nextSibling.previousSibling.nextSibling = node;
/*      */       } 
/*  218 */       if (nextSibling.previous == null) {
/*  219 */         this.head = node;
/*      */       } else {
/*  221 */         nextSibling.previous.next = node;
/*      */       } 
/*  223 */       nextSibling.previous = node;
/*  224 */       nextSibling.previousSibling = node;
/*      */     } 
/*  226 */     this.keyCount.add(key);
/*  227 */     return node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeNode(Node<K, V> node) {
/*  236 */     if (node.previous != null) {
/*  237 */       node.previous.next = node.next;
/*      */     } else {
/*  239 */       this.head = node.next;
/*      */     } 
/*  241 */     if (node.next != null) {
/*  242 */       node.next.previous = node.previous;
/*      */     } else {
/*  244 */       this.tail = node.previous;
/*      */     } 
/*  246 */     if (node.previousSibling != null) {
/*  247 */       node.previousSibling.nextSibling = node.nextSibling;
/*  248 */     } else if (node.nextSibling != null) {
/*  249 */       this.keyToKeyHead.put(node.key, node.nextSibling);
/*      */     } else {
/*  251 */       this.keyToKeyHead.remove(node.key);
/*      */     } 
/*  253 */     if (node.nextSibling != null) {
/*  254 */       node.nextSibling.previousSibling = node.previousSibling;
/*  255 */     } else if (node.previousSibling != null) {
/*  256 */       this.keyToKeyTail.put(node.key, node.previousSibling);
/*      */     } else {
/*  258 */       this.keyToKeyTail.remove(node.key);
/*      */     } 
/*  260 */     this.keyCount.remove(node.key);
/*      */   }
/*      */ 
/*      */   
/*      */   private void removeAllNodes(@Nullable Object key) {
/*  265 */     for (Iterator<V> i = new ValueForKeyIterator(key); i.hasNext(); ) {
/*  266 */       i.next();
/*  267 */       i.remove();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkElement(@Nullable Object node) {
/*  273 */     if (node == null)
/*  274 */       throw new NoSuchElementException(); 
/*      */   }
/*      */   
/*      */   private class NodeIterator
/*      */     implements ListIterator<Node<K, V>>
/*      */   {
/*      */     int nextIndex;
/*      */     LinkedListMultimap.Node<K, V> next;
/*      */     LinkedListMultimap.Node<K, V> current;
/*      */     LinkedListMultimap.Node<K, V> previous;
/*      */     
/*      */     NodeIterator() {
/*  286 */       this.next = LinkedListMultimap.this.head;
/*      */     }
/*      */     NodeIterator(int index) {
/*  289 */       int size = LinkedListMultimap.this.size();
/*  290 */       Preconditions.checkPositionIndex(index, size);
/*  291 */       if (index >= size / 2) {
/*  292 */         this.previous = LinkedListMultimap.this.tail;
/*  293 */         this.nextIndex = size;
/*  294 */         while (index++ < size) {
/*  295 */           previous();
/*      */         }
/*      */       } else {
/*  298 */         this.next = LinkedListMultimap.this.head;
/*  299 */         while (index-- > 0) {
/*  300 */           next();
/*      */         }
/*      */       } 
/*  303 */       this.current = null;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  307 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public LinkedListMultimap.Node<K, V> next() {
/*  311 */       LinkedListMultimap.checkElement(this.next);
/*  312 */       this.previous = this.current = this.next;
/*  313 */       this.next = this.next.next;
/*  314 */       this.nextIndex++;
/*  315 */       return this.current;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  319 */       Preconditions.checkState((this.current != null));
/*  320 */       if (this.current != this.next) {
/*  321 */         this.previous = this.current.previous;
/*  322 */         this.nextIndex--;
/*      */       } else {
/*  324 */         this.next = this.current.next;
/*      */       } 
/*  326 */       LinkedListMultimap.this.removeNode(this.current);
/*  327 */       this.current = null;
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  331 */       return (this.previous != null);
/*      */     }
/*      */     
/*      */     public LinkedListMultimap.Node<K, V> previous() {
/*  335 */       LinkedListMultimap.checkElement(this.previous);
/*  336 */       this.next = this.current = this.previous;
/*  337 */       this.previous = this.previous.previous;
/*  338 */       this.nextIndex--;
/*  339 */       return this.current;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  343 */       return this.nextIndex;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  347 */       return this.nextIndex - 1;
/*      */     }
/*      */     
/*      */     public void set(LinkedListMultimap.Node<K, V> e) {
/*  351 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(LinkedListMultimap.Node<K, V> e) {
/*  355 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     void setValue(V value) {
/*  358 */       Preconditions.checkState((this.current != null));
/*  359 */       this.current.value = value;
/*      */     }
/*      */   }
/*      */   
/*      */   private class DistinctKeyIterator
/*      */     implements Iterator<K> {
/*  365 */     final Set<K> seenKeys = Sets.newHashSetWithExpectedSize(LinkedListMultimap.this.keySet().size());
/*  366 */     LinkedListMultimap.Node<K, V> next = LinkedListMultimap.this.head;
/*      */     
/*      */     LinkedListMultimap.Node<K, V> current;
/*      */     
/*      */     public boolean hasNext() {
/*  371 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public K next() {
/*  375 */       LinkedListMultimap.checkElement(this.next);
/*  376 */       this.current = this.next;
/*  377 */       this.seenKeys.add(this.current.key);
/*      */       do {
/*  379 */         this.next = this.next.next;
/*  380 */       } while (this.next != null && !this.seenKeys.add(this.next.key));
/*  381 */       return this.current.key;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  385 */       Preconditions.checkState((this.current != null));
/*  386 */       LinkedListMultimap.this.removeAllNodes(this.current.key);
/*  387 */       this.current = null;
/*      */     }
/*      */     
/*      */     private DistinctKeyIterator() {}
/*      */   }
/*      */   
/*      */   private class ValueForKeyIterator implements ListIterator<V> {
/*      */     final Object key;
/*      */     int nextIndex;
/*      */     LinkedListMultimap.Node<K, V> next;
/*      */     LinkedListMultimap.Node<K, V> current;
/*      */     LinkedListMultimap.Node<K, V> previous;
/*      */     
/*      */     ValueForKeyIterator(Object key) {
/*  401 */       this.key = key;
/*  402 */       this.next = (LinkedListMultimap.Node<K, V>)LinkedListMultimap.this.keyToKeyHead.get(key);
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
/*      */     
/*      */     public ValueForKeyIterator(Object key, int index) {
/*  415 */       int size = LinkedListMultimap.this.keyCount.count(key);
/*  416 */       Preconditions.checkPositionIndex(index, size);
/*  417 */       if (index >= size / 2) {
/*  418 */         this.previous = (LinkedListMultimap.Node<K, V>)LinkedListMultimap.this.keyToKeyTail.get(key);
/*  419 */         this.nextIndex = size;
/*  420 */         while (index++ < size) {
/*  421 */           previous();
/*      */         }
/*      */       } else {
/*  424 */         this.next = (LinkedListMultimap.Node<K, V>)LinkedListMultimap.this.keyToKeyHead.get(key);
/*  425 */         while (index-- > 0) {
/*  426 */           next();
/*      */         }
/*      */       } 
/*  429 */       this.key = key;
/*  430 */       this.current = null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  435 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public V next() {
/*  440 */       LinkedListMultimap.checkElement(this.next);
/*  441 */       this.previous = this.current = this.next;
/*  442 */       this.next = this.next.nextSibling;
/*  443 */       this.nextIndex++;
/*  444 */       return this.current.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  449 */       return (this.previous != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public V previous() {
/*  454 */       LinkedListMultimap.checkElement(this.previous);
/*  455 */       this.next = this.current = this.previous;
/*  456 */       this.previous = this.previous.previousSibling;
/*  457 */       this.nextIndex--;
/*  458 */       return this.current.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  463 */       return this.nextIndex;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  468 */       return this.nextIndex - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  473 */       Preconditions.checkState((this.current != null));
/*  474 */       if (this.current != this.next) {
/*  475 */         this.previous = this.current.previousSibling;
/*  476 */         this.nextIndex--;
/*      */       } else {
/*  478 */         this.next = this.current.nextSibling;
/*      */       } 
/*  480 */       LinkedListMultimap.this.removeNode(this.current);
/*  481 */       this.current = null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(V value) {
/*  486 */       Preconditions.checkState((this.current != null));
/*  487 */       this.current.value = value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void add(V value) {
/*  493 */       this.previous = LinkedListMultimap.this.addNode((K)this.key, value, this.next);
/*  494 */       this.nextIndex++;
/*  495 */       this.current = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  503 */     return this.keyCount.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  508 */     return (this.head == null);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(@Nullable Object key) {
/*  513 */     return this.keyToKeyHead.containsKey(key);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(@Nullable Object value) {
/*  518 */     for (Iterator<Node<K, V>> i = new NodeIterator(); i.hasNext();) {
/*  519 */       if (Objects.equal(((Node)i.next()).value, value)) {
/*  520 */         return true;
/*      */       }
/*      */     } 
/*  523 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
/*  528 */     for (Iterator<V> i = new ValueForKeyIterator(key); i.hasNext();) {
/*  529 */       if (Objects.equal(i.next(), value)) {
/*  530 */         return true;
/*      */       }
/*      */     } 
/*  533 */     return false;
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
/*      */   public boolean put(@Nullable K key, @Nullable V value) {
/*  547 */     addNode(key, value, null);
/*  548 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(@Nullable Object key, @Nullable Object value) {
/*  553 */     Iterator<V> values = new ValueForKeyIterator(key);
/*  554 */     while (values.hasNext()) {
/*  555 */       if (Objects.equal(values.next(), value)) {
/*  556 */         values.remove();
/*  557 */         return true;
/*      */       } 
/*      */     } 
/*  560 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean putAll(@Nullable K key, Iterable<? extends V> values) {
/*  567 */     boolean changed = false;
/*  568 */     for (V value : values) {
/*  569 */       changed |= put(key, value);
/*      */     }
/*  571 */     return changed;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
/*  576 */     boolean changed = false;
/*  577 */     for (Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
/*  578 */       changed |= put(entry.getKey(), entry.getValue());
/*      */     }
/*  580 */     return changed;
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
/*      */   public List<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
/*  595 */     List<V> oldValues = getCopy(key);
/*  596 */     ListIterator<V> keyValues = new ValueForKeyIterator(key);
/*  597 */     Iterator<? extends V> newValues = values.iterator();
/*      */ 
/*      */     
/*  600 */     while (keyValues.hasNext() && newValues.hasNext()) {
/*  601 */       keyValues.next();
/*  602 */       keyValues.set(newValues.next());
/*      */     } 
/*      */ 
/*      */     
/*  606 */     while (keyValues.hasNext()) {
/*  607 */       keyValues.next();
/*  608 */       keyValues.remove();
/*      */     } 
/*      */ 
/*      */     
/*  612 */     while (newValues.hasNext()) {
/*  613 */       keyValues.add(newValues.next());
/*      */     }
/*      */     
/*  616 */     return oldValues;
/*      */   }
/*      */   
/*      */   private List<V> getCopy(@Nullable Object key) {
/*  620 */     return Collections.unmodifiableList(Lists.newArrayList(new ValueForKeyIterator(key)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<V> removeAll(@Nullable Object key) {
/*  631 */     List<V> oldValues = getCopy(key);
/*  632 */     removeAllNodes(key);
/*  633 */     return oldValues;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  638 */     this.head = null;
/*  639 */     this.tail = null;
/*  640 */     this.keyCount.clear();
/*  641 */     this.keyToKeyHead.clear();
/*  642 */     this.keyToKeyTail.clear();
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
/*      */   public List<V> get(@Nullable final K key) {
/*  658 */     return new AbstractSequentialList<V>() {
/*      */         public int size() {
/*  660 */           return LinkedListMultimap.this.keyCount.count(key);
/*      */         }
/*      */         public ListIterator<V> listIterator(int index) {
/*  663 */           return new LinkedListMultimap.ValueForKeyIterator(key, index);
/*      */         }
/*      */         public boolean removeAll(Collection<?> c) {
/*  666 */           return Iterators.removeAll(iterator(), c);
/*      */         }
/*      */         public boolean retainAll(Collection<?> c) {
/*  669 */           return Iterators.retainAll(iterator(), c);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<K> keySet() {
/*  678 */     Set<K> result = this.keySet;
/*  679 */     if (result == null) {
/*  680 */       this.keySet = result = new AbstractSet<K>() {
/*      */           public int size() {
/*  682 */             return LinkedListMultimap.this.keyCount.elementSet().size();
/*      */           }
/*      */           public Iterator<K> iterator() {
/*  685 */             return new LinkedListMultimap.DistinctKeyIterator();
/*      */           }
/*      */           public boolean contains(Object key) {
/*  688 */             return LinkedListMultimap.this.keyCount.contains(key);
/*      */           }
/*      */           public boolean removeAll(Collection<?> c) {
/*  691 */             Preconditions.checkNotNull(c);
/*  692 */             return super.removeAll(c);
/*      */           }
/*      */         };
/*      */     }
/*  696 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Multiset<K> keys() {
/*  703 */     Multiset<K> result = this.keys;
/*  704 */     if (result == null) {
/*  705 */       this.keys = result = new MultisetView();
/*      */     }
/*  707 */     return result;
/*      */   }
/*      */   
/*      */   private class MultisetView extends AbstractCollection<K> implements Multiset<K> {
/*      */     private MultisetView() {}
/*      */     
/*      */     public int size() {
/*  714 */       return LinkedListMultimap.this.keyCount.size();
/*      */     }
/*      */     
/*      */     public Iterator<K> iterator() {
/*  718 */       final Iterator<LinkedListMultimap.Node<K, V>> nodes = new LinkedListMultimap.NodeIterator();
/*  719 */       return new Iterator<K>()
/*      */         {
/*      */           public boolean hasNext() {
/*  722 */             return nodes.hasNext();
/*      */           }
/*      */           
/*      */           public K next() {
/*  726 */             return ((LinkedListMultimap.Node)nodes.next()).key;
/*      */           }
/*      */           
/*      */           public void remove() {
/*  730 */             nodes.remove();
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */     
/*      */     public int count(@Nullable Object key) {
/*  737 */       return LinkedListMultimap.this.keyCount.count(key);
/*      */     }
/*      */ 
/*      */     
/*      */     public int add(@Nullable K key, int occurrences) {
/*  742 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int remove(@Nullable Object key, int occurrences) {
/*  747 */       Preconditions.checkArgument((occurrences >= 0));
/*  748 */       int oldCount = count(key);
/*  749 */       Iterator<V> values = new LinkedListMultimap.ValueForKeyIterator(key);
/*  750 */       while (occurrences-- > 0 && values.hasNext()) {
/*  751 */         values.next();
/*  752 */         values.remove();
/*      */       } 
/*  754 */       return oldCount;
/*      */     }
/*      */ 
/*      */     
/*      */     public int setCount(K element, int count) {
/*  759 */       return Multisets.setCountImpl(this, element, count);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setCount(K element, int oldCount, int newCount) {
/*  764 */       return Multisets.setCountImpl(this, element, oldCount, newCount);
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  768 */       return Iterators.removeAll(iterator(), c);
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  772 */       return Iterators.retainAll(iterator(), c);
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<K> elementSet() {
/*  777 */       return LinkedListMultimap.this.keySet();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Multiset.Entry<K>> entrySet() {
/*  783 */       return new AbstractSet<Multiset.Entry<K>>() {
/*      */           public int size() {
/*  785 */             return LinkedListMultimap.this.keyCount.elementSet().size();
/*      */           }
/*      */           
/*      */           public Iterator<Multiset.Entry<K>> iterator() {
/*  789 */             final Iterator<K> keyIterator = new LinkedListMultimap.DistinctKeyIterator();
/*  790 */             return new Iterator<Multiset.Entry<K>>()
/*      */               {
/*      */                 public boolean hasNext() {
/*  793 */                   return keyIterator.hasNext();
/*      */                 }
/*      */                 
/*      */                 public Multiset.Entry<K> next() {
/*  797 */                   final K key = keyIterator.next();
/*  798 */                   return new Multisets.AbstractEntry<K>()
/*      */                     {
/*      */                       public K getElement() {
/*  801 */                         return (K)key;
/*      */                       }
/*      */                       
/*      */                       public int getCount() {
/*  805 */                         return LinkedListMultimap.this.keyCount.count(key);
/*      */                       }
/*      */                     };
/*      */                 }
/*      */                 
/*      */                 public void remove() {
/*  811 */                   keyIterator.remove();
/*      */                 }
/*      */               };
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/*  819 */       return LinkedListMultimap.this.keyCount.equals(object);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  823 */       return LinkedListMultimap.this.keyCount.hashCode();
/*      */     }
/*      */     
/*      */     public String toString() {
/*  827 */       return LinkedListMultimap.this.keyCount.toString();
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
/*      */   public List<V> values() {
/*  844 */     List<V> result = this.valuesList;
/*  845 */     if (result == null) {
/*  846 */       this.valuesList = result = new AbstractSequentialList<V>() {
/*      */           public int size() {
/*  848 */             return LinkedListMultimap.this.keyCount.size();
/*      */           }
/*      */           
/*      */           public ListIterator<V> listIterator(int index) {
/*  852 */             final LinkedListMultimap<K, V>.NodeIterator nodes = new LinkedListMultimap.NodeIterator(index);
/*  853 */             return new ListIterator<V>()
/*      */               {
/*      */                 public boolean hasNext() {
/*  856 */                   return nodes.hasNext();
/*      */                 }
/*      */                 
/*      */                 public V next() {
/*  860 */                   return (nodes.next()).value;
/*      */                 }
/*      */                 
/*      */                 public boolean hasPrevious() {
/*  864 */                   return nodes.hasPrevious();
/*      */                 }
/*      */                 
/*      */                 public V previous() {
/*  868 */                   return (nodes.previous()).value;
/*      */                 }
/*      */                 
/*      */                 public int nextIndex() {
/*  872 */                   return nodes.nextIndex();
/*      */                 }
/*      */                 
/*      */                 public int previousIndex() {
/*  876 */                   return nodes.previousIndex();
/*      */                 }
/*      */                 
/*      */                 public void remove() {
/*  880 */                   nodes.remove();
/*      */                 }
/*      */                 
/*      */                 public void set(V e) {
/*  884 */                   nodes.setValue(e);
/*      */                 }
/*      */                 
/*      */                 public void add(V e) {
/*  888 */                   throw new UnsupportedOperationException();
/*      */                 }
/*      */               };
/*      */           }
/*      */         };
/*      */     }
/*  894 */     return result;
/*      */   }
/*      */   
/*      */   private static <K, V> Map.Entry<K, V> createEntry(final Node<K, V> node) {
/*  898 */     return new AbstractMapEntry<K, V>() {
/*      */         public K getKey() {
/*  900 */           return node.key;
/*      */         }
/*      */         public V getValue() {
/*  903 */           return node.value;
/*      */         }
/*      */         public V setValue(V value) {
/*  906 */           V oldValue = node.value;
/*  907 */           node.value = value;
/*  908 */           return oldValue;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Map.Entry<K, V>> entries() {
/*  935 */     List<Map.Entry<K, V>> result = this.entries;
/*  936 */     if (result == null) {
/*  937 */       this.entries = result = new AbstractSequentialList<Map.Entry<K, V>>() {
/*      */           public int size() {
/*  939 */             return LinkedListMultimap.this.keyCount.size();
/*      */           }
/*      */           
/*      */           public ListIterator<Map.Entry<K, V>> listIterator(int index) {
/*  943 */             final ListIterator<LinkedListMultimap.Node<K, V>> nodes = new LinkedListMultimap.NodeIterator(index);
/*  944 */             return new ListIterator<Map.Entry<K, V>>()
/*      */               {
/*      */                 public boolean hasNext() {
/*  947 */                   return nodes.hasNext();
/*      */                 }
/*      */ 
/*      */                 
/*      */                 public Map.Entry<K, V> next() {
/*  952 */                   return LinkedListMultimap.createEntry(nodes.next());
/*      */                 }
/*      */ 
/*      */                 
/*      */                 public void remove() {
/*  957 */                   nodes.remove();
/*      */                 }
/*      */ 
/*      */                 
/*      */                 public boolean hasPrevious() {
/*  962 */                   return nodes.hasPrevious();
/*      */                 }
/*      */ 
/*      */                 
/*      */                 public Map.Entry<K, V> previous() {
/*  967 */                   return LinkedListMultimap.createEntry(nodes.previous());
/*      */                 }
/*      */ 
/*      */                 
/*      */                 public int nextIndex() {
/*  972 */                   return nodes.nextIndex();
/*      */                 }
/*      */ 
/*      */                 
/*      */                 public int previousIndex() {
/*  977 */                   return nodes.previousIndex();
/*      */                 }
/*      */ 
/*      */                 
/*      */                 public void set(Map.Entry<K, V> e) {
/*  982 */                   throw new UnsupportedOperationException();
/*      */                 }
/*      */ 
/*      */                 
/*      */                 public void add(Map.Entry<K, V> e) {
/*  987 */                   throw new UnsupportedOperationException();
/*      */                 }
/*      */               };
/*      */           }
/*      */         };
/*      */     }
/*  993 */     return result;
/*      */   }
/*      */   
/*      */   private class AsMapEntries extends AbstractSet<Map.Entry<K, Collection<V>>> {
/*      */     public int size() {
/*  998 */       return LinkedListMultimap.this.keyCount.elementSet().size();
/*      */     }
/*      */     private AsMapEntries() {}
/*      */     public Iterator<Map.Entry<K, Collection<V>>> iterator() {
/* 1002 */       final Iterator<K> keyIterator = new LinkedListMultimap.DistinctKeyIterator();
/* 1003 */       return new Iterator<Map.Entry<K, Collection<V>>>()
/*      */         {
/*      */           public boolean hasNext() {
/* 1006 */             return keyIterator.hasNext();
/*      */           }
/*      */ 
/*      */           
/*      */           public Map.Entry<K, Collection<V>> next() {
/* 1011 */             final K key = keyIterator.next();
/* 1012 */             return (Map.Entry)new AbstractMapEntry<K, Collection<Collection<V>>>() {
/*      */                 public K getKey() {
/* 1014 */                   return (K)key;
/*      */                 }
/*      */                 
/*      */                 public Collection<V> getValue() {
/* 1018 */                   return LinkedListMultimap.this.get(key);
/*      */                 }
/*      */               };
/*      */           }
/*      */ 
/*      */           
/*      */           public void remove() {
/* 1025 */             keyIterator.remove();
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<K, Collection<V>> asMap() {
/* 1037 */     Map<K, Collection<V>> result = this.map;
/* 1038 */     if (result == null) {
/* 1039 */       this.map = result = (Map)new AbstractMap<K, Collection<Collection<Collection<Collection<V>>>>>() {
/*      */           Set<Map.Entry<K, Collection<V>>> entrySet;
/*      */           
/*      */           public Set<Map.Entry<K, Collection<V>>> entrySet() {
/* 1043 */             Set<Map.Entry<K, Collection<V>>> result = this.entrySet;
/* 1044 */             if (result == null) {
/* 1045 */               this.entrySet = result = new LinkedListMultimap.AsMapEntries();
/*      */             }
/* 1047 */             return result;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean containsKey(@Nullable Object key) {
/* 1053 */             return LinkedListMultimap.this.containsKey(key);
/*      */           }
/*      */ 
/*      */           
/*      */           public Collection<V> get(@Nullable Object key) {
/* 1058 */             Collection<V> collection = LinkedListMultimap.this.get(key);
/* 1059 */             return collection.isEmpty() ? null : collection;
/*      */           }
/*      */           
/*      */           public Collection<V> remove(@Nullable Object key) {
/* 1063 */             Collection<V> collection = LinkedListMultimap.this.removeAll(key);
/* 1064 */             return collection.isEmpty() ? null : collection;
/*      */           }
/*      */         };
/*      */     }
/*      */     
/* 1069 */     return result;
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
/*      */   public boolean equals(@Nullable Object other) {
/* 1082 */     if (other == this) {
/* 1083 */       return true;
/*      */     }
/* 1085 */     if (other instanceof Multimap) {
/* 1086 */       Multimap<?, ?> that = (Multimap<?, ?>)other;
/* 1087 */       return asMap().equals(that.asMap());
/*      */     } 
/* 1089 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1099 */     return asMap().hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1109 */     return asMap().toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.io.ObjectOutputStream")
/*      */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 1119 */     stream.defaultWriteObject();
/* 1120 */     stream.writeInt(size());
/* 1121 */     for (Map.Entry<K, V> entry : entries()) {
/* 1122 */       stream.writeObject(entry.getKey());
/* 1123 */       stream.writeObject(entry.getValue());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.io.ObjectInputStream")
/*      */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 1130 */     stream.defaultReadObject();
/* 1131 */     this.keyCount = LinkedHashMultiset.create();
/* 1132 */     this.keyToKeyHead = Maps.newHashMap();
/* 1133 */     this.keyToKeyTail = Maps.newHashMap();
/* 1134 */     int size = stream.readInt();
/* 1135 */     for (int i = 0; i < size; i++) {
/*      */       
/* 1137 */       K key = (K)stream.readObject();
/*      */       
/* 1139 */       V value = (V)stream.readObject();
/* 1140 */       put(key, value);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\LinkedListMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */