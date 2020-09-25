/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.Page;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LimitOffsetList<T>
/*     */   implements List<T>
/*     */ {
/*     */   private final LimitOffsetPagingQuery<T> owner;
/*     */   private List<T> localCopy;
/*     */   
/*     */   public LimitOffsetList(LimitOffsetPagingQuery<T> owner) {
/*  37 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   private void ensureLocalCopy() {
/*  41 */     if (this.localCopy == null) {
/*  42 */       Page<T> page; this.localCopy = new ArrayList<T>();
/*     */       
/*  44 */       int pgIndex = 0;
/*     */       do {
/*  46 */         page = this.owner.getPage(pgIndex++);
/*  47 */         List<T> list = page.getList();
/*  48 */         this.localCopy.addAll(list);
/*  49 */       } while (page.hasNext());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasNext(int position) {
/*  57 */     return this.owner.hasNext(position);
/*     */   }
/*     */   
/*     */   public void clear() {
/*  61 */     this.localCopy = new ArrayList<T>();
/*     */   }
/*     */   
/*     */   public T get(int index) {
/*  65 */     if (this.localCopy != null) {
/*  66 */       return this.localCopy.get(index);
/*     */     }
/*  68 */     return this.owner.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  73 */     if (this.localCopy != null) {
/*  74 */       return this.localCopy.isEmpty();
/*     */     }
/*  76 */     return (this.owner.getTotalRowCount() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  81 */     if (this.localCopy != null) {
/*  82 */       return this.localCopy.size();
/*     */     }
/*  84 */     return this.owner.getTotalRowCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/*  89 */     if (this.localCopy != null) {
/*  90 */       return this.localCopy.iterator();
/*     */     }
/*  92 */     return new ListItr(this, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListIterator<T> listIterator() {
/*  97 */     if (this.localCopy != null) {
/*  98 */       return this.localCopy.listIterator();
/*     */     }
/* 100 */     return new ListItr(this, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListIterator<T> listIterator(int index) {
/* 105 */     if (this.localCopy != null) {
/* 106 */       return this.localCopy.listIterator(index);
/*     */     }
/* 108 */     return new ListItr(this, index);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<T> subList(int fromIndex, int toIndex) {
/* 113 */     if (this.localCopy != null) {
/* 114 */       return this.localCopy.subList(fromIndex, toIndex);
/*     */     }
/*     */     
/* 117 */     throw new RuntimeException("Not implemented at this point");
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(Object o) {
/* 122 */     ensureLocalCopy();
/* 123 */     return this.localCopy.lastIndexOf(o);
/*     */   }
/*     */   
/*     */   public void add(int index, T element) {
/* 127 */     ensureLocalCopy();
/* 128 */     this.localCopy.add(index, element);
/*     */   }
/*     */   
/*     */   public boolean add(T o) {
/* 132 */     ensureLocalCopy();
/* 133 */     return this.localCopy.add(o);
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends T> c) {
/* 137 */     ensureLocalCopy();
/* 138 */     return this.localCopy.addAll(c);
/*     */   }
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends T> c) {
/* 142 */     ensureLocalCopy();
/* 143 */     return this.localCopy.addAll(index, c);
/*     */   }
/*     */   
/*     */   public boolean contains(Object o) {
/* 147 */     ensureLocalCopy();
/* 148 */     return this.localCopy.contains(o);
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 152 */     ensureLocalCopy();
/* 153 */     return this.localCopy.containsAll(c);
/*     */   }
/*     */   
/*     */   public int indexOf(Object o) {
/* 157 */     ensureLocalCopy();
/* 158 */     return this.localCopy.indexOf(o);
/*     */   }
/*     */   
/*     */   public T remove(int index) {
/* 162 */     ensureLocalCopy();
/* 163 */     return this.localCopy.remove(index);
/*     */   }
/*     */   
/*     */   public boolean remove(Object o) {
/* 167 */     ensureLocalCopy();
/* 168 */     return this.localCopy.remove(o);
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 172 */     ensureLocalCopy();
/* 173 */     return this.localCopy.removeAll(c);
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 177 */     ensureLocalCopy();
/* 178 */     return this.localCopy.retainAll(c);
/*     */   }
/*     */   
/*     */   public T set(int index, T element) {
/* 182 */     ensureLocalCopy();
/* 183 */     return this.localCopy.set(index, element);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 188 */     ensureLocalCopy();
/* 189 */     return this.localCopy.toArray();
/*     */   }
/*     */   
/*     */   public <K> K[] toArray(K[] a) {
/* 193 */     ensureLocalCopy();
/* 194 */     return this.localCopy.toArray(a);
/*     */   }
/*     */   
/*     */   private class ListItr
/*     */     implements ListIterator<T> {
/*     */     private LimitOffsetList<T> ownerList;
/*     */     private int position;
/*     */     
/*     */     ListItr(LimitOffsetList<T> ownerList, int position) {
/* 203 */       this.ownerList = ownerList;
/* 204 */       this.position = position;
/*     */     }
/*     */     
/*     */     public void add(T o) {
/* 208 */       this.ownerList.add(this.position++, o);
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 212 */       return this.ownerList.hasNext(this.position);
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 216 */       return (this.position > 0);
/*     */     }
/*     */     
/*     */     public T next() {
/* 220 */       return this.ownerList.get(this.position++);
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 224 */       return this.position;
/*     */     }
/*     */     
/*     */     public T previous() {
/* 228 */       return LimitOffsetList.this.get(--this.position);
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 232 */       return this.position - 1;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 236 */       throw new RuntimeException("Not supported yet");
/*     */     }
/*     */     
/*     */     public void set(T o) {
/* 240 */       throw new RuntimeException("Not supported yet");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\LimitOffsetList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */