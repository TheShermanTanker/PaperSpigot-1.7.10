/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class TreeMultiset<E>
/*     */   extends AbstractMapBasedMultiset<E>
/*     */   implements SortedIterable<E>
/*     */ {
/*     */   private final Comparator<? super E> comparator;
/*     */   @GwtIncompatible("not needed in emulated source")
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <E extends Comparable> TreeMultiset<E> create() {
/*  73 */     return new TreeMultiset<E>();
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
/*     */   public static <E> TreeMultiset<E> create(@Nullable Comparator<? super E> comparator) {
/*  92 */     return (comparator == null) ? new TreeMultiset<E>() : new TreeMultiset<E>(comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/* 103 */     return super.iterator();
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
/*     */   public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> elements) {
/* 119 */     TreeMultiset<E> multiset = create();
/* 120 */     Iterables.addAll(multiset, elements);
/* 121 */     return multiset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TreeMultiset() {
/* 128 */     this(Ordering.natural());
/*     */   }
/*     */   
/*     */   private TreeMultiset(@Nullable Comparator<? super E> comparator) {
/* 132 */     super(new TreeMap<E, Count>((Comparator<? super E>)Preconditions.checkNotNull(comparator)));
/* 133 */     this.comparator = comparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparator<? super E> comparator() {
/* 143 */     return this.comparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedSet<E> elementSet() {
/* 153 */     return (SortedSet<E>)super.elementSet();
/*     */   }
/*     */   
/*     */   public int count(@Nullable Object element) {
/*     */     try {
/* 158 */       return super.count(element);
/* 159 */     } catch (NullPointerException e) {
/* 160 */       return 0;
/* 161 */     } catch (ClassCastException e) {
/* 162 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int add(E element, int occurrences) {
/* 168 */     if (element == null) {
/* 169 */       this.comparator.compare(element, element);
/*     */     }
/* 171 */     return super.add(element, occurrences);
/*     */   }
/*     */   
/*     */   Set<E> createElementSet() {
/* 175 */     return new SortedMapBasedElementSet((SortedMap<E, Count>)backingMap());
/*     */   }
/*     */   
/*     */   private class SortedMapBasedElementSet
/*     */     extends AbstractMapBasedMultiset<E>.MapBasedElementSet
/*     */     implements SortedSet<E>, SortedIterable<E>
/*     */   {
/*     */     SortedMapBasedElementSet(SortedMap<E, Count> map) {
/* 183 */       super(map);
/*     */     }
/*     */     
/*     */     SortedMap<E, Count> sortedMap() {
/* 187 */       return (SortedMap<E, Count>)getMap();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Comparator<? super E> comparator() {
/* 197 */       return sortedMap().comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public E first() {
/* 202 */       return sortedMap().firstKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public E last() {
/* 207 */       return sortedMap().lastKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public SortedSet<E> headSet(E toElement) {
/* 212 */       return new SortedMapBasedElementSet(sortedMap().headMap(toElement));
/*     */     }
/*     */ 
/*     */     
/*     */     public SortedSet<E> subSet(E fromElement, E toElement) {
/* 217 */       return new SortedMapBasedElementSet(sortedMap().subMap(fromElement, toElement));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SortedSet<E> tailSet(E fromElement) {
/* 223 */       return new SortedMapBasedElementSet(sortedMap().tailMap(fromElement));
/*     */     }
/*     */     
/*     */     public boolean remove(Object element) {
/*     */       try {
/* 228 */         return super.remove(element);
/* 229 */       } catch (NullPointerException e) {
/* 230 */         return false;
/* 231 */       } catch (ClassCastException e) {
/* 232 */         return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 250 */     stream.defaultWriteObject();
/* 251 */     stream.writeObject(elementSet().comparator());
/* 252 */     Serialization.writeMultiset(this, stream);
/*     */   }
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 258 */     stream.defaultReadObject();
/*     */     
/* 260 */     Comparator<? super E> comparator = (Comparator<? super E>)stream.readObject();
/*     */     
/* 262 */     setBackingMap(new TreeMap<E, Count>(comparator));
/* 263 */     Serialization.populateMultiset(this, stream);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\TreeMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */