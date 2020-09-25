/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class LinkedHashMultiset<E>
/*     */   extends AbstractMapBasedMultiset<E>
/*     */ {
/*     */   @GwtIncompatible("not needed in emulated source")
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   public static <E> LinkedHashMultiset<E> create() {
/*  48 */     return new LinkedHashMultiset<E>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> LinkedHashMultiset<E> create(int distinctElements) {
/*  59 */     return new LinkedHashMultiset<E>(distinctElements);
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
/*     */   public static <E> LinkedHashMultiset<E> create(Iterable<? extends E> elements) {
/*  72 */     LinkedHashMultiset<E> multiset = create(Multisets.inferDistinctElements(elements));
/*     */     
/*  74 */     Iterables.addAll(multiset, elements);
/*  75 */     return multiset;
/*     */   }
/*     */   
/*     */   private LinkedHashMultiset() {
/*  79 */     super(new LinkedHashMap<E, Count>());
/*     */   }
/*     */ 
/*     */   
/*     */   private LinkedHashMultiset(int distinctElements) {
/*  84 */     super(new LinkedHashMap<E, Count>(Maps.capacity(distinctElements)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectOutputStream")
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/*  93 */     stream.defaultWriteObject();
/*  94 */     Serialization.writeMultiset(this, stream);
/*     */   }
/*     */ 
/*     */   
/*     */   @GwtIncompatible("java.io.ObjectInputStream")
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 100 */     stream.defaultReadObject();
/* 101 */     int distinctElements = Serialization.readCount(stream);
/* 102 */     setBackingMap(new LinkedHashMap<E, Count>(Maps.capacity(distinctElements)));
/*     */     
/* 104 */     Serialization.populateMultiset(this, stream, distinctElements);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\LinkedHashMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */