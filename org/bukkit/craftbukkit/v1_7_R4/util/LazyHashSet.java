/*    */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class LazyHashSet<E>
/*    */   implements Set<E> {
/*  9 */   Set<E> reference = null;
/*    */   
/*    */   public int size() {
/* 12 */     return getReference().size();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 16 */     return getReference().isEmpty();
/*    */   }
/*    */   
/*    */   public boolean contains(Object o) {
/* 20 */     return getReference().contains(o);
/*    */   }
/*    */   
/*    */   public Iterator<E> iterator() {
/* 24 */     return getReference().iterator();
/*    */   }
/*    */   
/*    */   public Object[] toArray() {
/* 28 */     return getReference().toArray();
/*    */   }
/*    */   
/*    */   public <T> T[] toArray(T[] a) {
/* 32 */     return getReference().toArray(a);
/*    */   }
/*    */   
/*    */   public boolean add(E o) {
/* 36 */     return getReference().add(o);
/*    */   }
/*    */   
/*    */   public boolean remove(Object o) {
/* 40 */     return getReference().remove(o);
/*    */   }
/*    */   
/*    */   public boolean containsAll(Collection<?> c) {
/* 44 */     return getReference().containsAll(c);
/*    */   }
/*    */   
/*    */   public boolean addAll(Collection<? extends E> c) {
/* 48 */     return getReference().addAll(c);
/*    */   }
/*    */   
/*    */   public boolean retainAll(Collection<?> c) {
/* 52 */     return getReference().retainAll(c);
/*    */   }
/*    */   
/*    */   public boolean removeAll(Collection<?> c) {
/* 56 */     return getReference().removeAll(c);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 60 */     getReference().clear();
/*    */   }
/*    */   
/*    */   public Set<E> getReference() {
/* 64 */     Set<E> reference = this.reference;
/* 65 */     if (reference != null) {
/* 66 */       return reference;
/*    */     }
/* 68 */     return this.reference = makeReference();
/*    */   }
/*    */   
/*    */   abstract Set<E> makeReference();
/*    */   
/*    */   public boolean isLazy() {
/* 74 */     return (this.reference == null);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 79 */     return 157 * getReference().hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 84 */     if (obj == this) {
/* 85 */       return true;
/*    */     }
/* 87 */     if (obj == null || getClass() != obj.getClass()) {
/* 88 */       return false;
/*    */     }
/* 90 */     LazyHashSet<?> that = (LazyHashSet)obj;
/* 91 */     return ((isLazy() && that.isLazy()) || getReference().equals(that.getReference()));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 96 */     return getReference().toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\LazyHashSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */