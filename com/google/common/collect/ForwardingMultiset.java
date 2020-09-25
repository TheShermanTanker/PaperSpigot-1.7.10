/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Objects;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ @GwtCompatible
/*     */ public abstract class ForwardingMultiset<E>
/*     */   extends ForwardingCollection<E>
/*     */   implements Multiset<E>
/*     */ {
/*     */   public int count(Object element) {
/*  62 */     return delegate().count(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public int add(E element, int occurrences) {
/*  67 */     return delegate().add(element, occurrences);
/*     */   }
/*     */ 
/*     */   
/*     */   public int remove(Object element, int occurrences) {
/*  72 */     return delegate().remove(element, occurrences);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<E> elementSet() {
/*  77 */     return delegate().elementSet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Multiset.Entry<E>> entrySet() {
/*  82 */     return delegate().entrySet();
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/*  86 */     return (object == this || delegate().equals(object));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  90 */     return delegate().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public int setCount(E element, int count) {
/*  95 */     return delegate().setCount(element, count);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setCount(E element, int oldCount, int newCount) {
/* 100 */     return delegate().setCount(element, oldCount, newCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected boolean standardContains(@Nullable Object object) {
/* 111 */     return (count(object) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected void standardClear() {
/* 122 */     Iterator<Multiset.Entry<E>> entryIterator = entrySet().iterator();
/* 123 */     while (entryIterator.hasNext()) {
/* 124 */       entryIterator.next();
/* 125 */       entryIterator.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected int standardCount(@Nullable Object object) {
/* 137 */     for (Multiset.Entry<?> entry : entrySet()) {
/* 138 */       if (Objects.equal(entry.getElement(), object)) {
/* 139 */         return entry.getCount();
/*     */       }
/*     */     } 
/* 142 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected boolean standardAdd(E element) {
/* 153 */     add(element, 1);
/* 154 */     return true;
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
/*     */   @Beta
/*     */   protected boolean standardAddAll(Collection<? extends E> elementsToAdd) {
/* 167 */     return Multisets.addAllImpl(this, elementsToAdd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected boolean standardRemove(Object element) {
/* 179 */     return (remove(element, 1) > 0);
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
/*     */   @Beta
/*     */   protected boolean standardRemoveAll(Collection<?> elementsToRemove) {
/* 192 */     return Multisets.removeAllImpl(this, elementsToRemove);
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
/*     */   @Beta
/*     */   protected boolean standardRetainAll(Collection<?> elementsToRetain) {
/* 205 */     return Multisets.retainAllImpl(this, elementsToRetain);
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
/*     */   @Beta
/*     */   protected int standardSetCount(E element, int count) {
/* 218 */     return Multisets.setCountImpl(this, element, count);
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
/*     */   @Beta
/*     */   protected boolean standardSetCount(E element, int oldCount, int newCount) {
/* 231 */     return Multisets.setCountImpl(this, element, oldCount, newCount);
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
/*     */   @Deprecated
/*     */   @Beta
/*     */   protected Set<E> standardElementSet() {
/* 247 */     return new StandardElementSet();
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
/*     */   @Beta
/*     */   protected class StandardElementSet
/*     */     extends Multisets.ElementSet<E>
/*     */   {
/*     */     Multiset<E> multiset() {
/* 269 */       return ForwardingMultiset.this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected Iterator<E> standardIterator() {
/* 281 */     return Multisets.iteratorImpl(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected int standardSize() {
/* 292 */     return Multisets.sizeImpl(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected boolean standardEquals(@Nullable Object object) {
/* 304 */     return Multisets.equalsImpl(this, object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected int standardHashCode() {
/* 315 */     return entrySet().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   protected String standardToString() {
/* 326 */     return entrySet().toString();
/*     */   }
/*     */   
/*     */   protected abstract Multiset<E> delegate();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ForwardingMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */