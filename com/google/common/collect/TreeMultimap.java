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
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
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
/*     */ 
/*     */ 
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ public class TreeMultimap<K, V>
/*     */   extends AbstractSortedSetMultimap<K, V>
/*     */ {
/*     */   private transient Comparator<? super K> keyComparator;
/*     */   private transient Comparator<? super V> valueComparator;
/*     */   @GwtIncompatible("not needed in emulated source")
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create() {
/*  81 */     return new TreeMultimap<K, V>(Ordering.natural(), Ordering.natural());
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
/*     */   public static <K, V> TreeMultimap<K, V> create(Comparator<? super K> keyComparator, Comparator<? super V> valueComparator) {
/*  95 */     return new TreeMultimap<K, V>((Comparator<? super K>)Preconditions.checkNotNull(keyComparator), (Comparator<? super V>)Preconditions.checkNotNull(valueComparator));
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
/*     */   public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
/* 107 */     return new TreeMultimap<K, V>(Ordering.natural(), Ordering.natural(), multimap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   TreeMultimap(Comparator<? super K> keyComparator, Comparator<? super V> valueComparator) {
/* 113 */     super(new TreeMap<K, Collection<V>>(keyComparator));
/* 114 */     this.keyComparator = keyComparator;
/* 115 */     this.valueComparator = valueComparator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TreeMultimap(Comparator<? super K> keyComparator, Comparator<? super V> valueComparator, Multimap<? extends K, ? extends V> multimap) {
/* 121 */     this(keyComparator, valueComparator);
/* 122 */     putAll(multimap);
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
/*     */   SortedSet<V> createCollection() {
/* 134 */     return new TreeSet<V>(this.valueComparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparator<? super K> keyComparator() {
/* 141 */     return this.keyComparator;
/*     */   }
/*     */ 
/*     */   
/*     */   public Comparator<? super V> valueComparator() {
/* 146 */     return this.valueComparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedSet<K> keySet() {
/* 157 */     return (SortedSet<K>)super.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedMap<K, Collection<V>> asMap() {
/* 168 */     return (SortedMap<K, Collection<V>>)super.asMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 178 */     stream.defaultWriteObject();
/* 179 */     stream.writeObject(keyComparator());
/* 180 */     stream.writeObject(valueComparator());
/* 181 */     Serialization.writeMultimap(this, stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 188 */     stream.defaultReadObject();
/* 189 */     this.keyComparator = (Comparator<? super K>)Preconditions.checkNotNull(stream.readObject());
/* 190 */     this.valueComparator = (Comparator<? super V>)Preconditions.checkNotNull(stream.readObject());
/* 191 */     setMap(new TreeMap<K, Collection<V>>(this.keyComparator));
/* 192 */     Serialization.populateMultimap(this, stream);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\TreeMultimap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */