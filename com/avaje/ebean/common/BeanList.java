/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.bean.SerializeControl;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanList<E>
/*     */   extends AbstractBeanCollection<E>
/*     */   implements List<E>, BeanCollectionAdd
/*     */ {
/*     */   private List<E> list;
/*     */   
/*     */   public BeanList(List<E> list) {
/*  50 */     this.list = list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanList() {
/*  57 */     this(new ArrayList<E>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanList(BeanCollectionLoader loader, Object ownerBean, String propertyName) {
/*  64 */     super(loader, ownerBean, propertyName);
/*     */   }
/*     */   
/*     */   Object readResolve() throws ObjectStreamException {
/*  68 */     if (SerializeControl.isVanillaCollections()) {
/*  69 */       return this.list;
/*     */     }
/*  71 */     return this;
/*     */   }
/*     */   
/*     */   Object writeReplace() throws ObjectStreamException {
/*  75 */     if (SerializeControl.isVanillaCollections()) {
/*  76 */       return this.list;
/*     */     }
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBean(Object bean) {
/*  83 */     this.list.add((E)bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void internalAdd(Object bean) {
/*  88 */     this.list.add((E)bean);
/*     */   }
/*     */   
/*     */   public boolean checkEmptyLazyLoad() {
/*  92 */     if (this.list == null) {
/*  93 */       this.list = new ArrayList<E>();
/*  94 */       return true;
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initClear() {
/* 101 */     synchronized (this) {
/* 102 */       if (this.list == null) {
/* 103 */         if (this.modifyListening) {
/* 104 */           lazyLoadCollection(true);
/*     */         } else {
/* 106 */           this.list = new ArrayList<E>();
/*     */         } 
/*     */       }
/* 109 */       touched();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init() {
/* 114 */     synchronized (this) {
/* 115 */       if (this.list == null) {
/* 116 */         lazyLoadCollection(false);
/*     */       }
/* 118 */       touched();
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
/*     */   public void setActualList(List<?> list) {
/* 130 */     this.list = (List)list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<E> getActualList() {
/* 137 */     return this.list;
/*     */   }
/*     */   
/*     */   public Collection<E> getActualDetails() {
/* 141 */     return this.list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getActualCollection() {
/* 148 */     return this.list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPopulated() {
/* 155 */     return (this.list != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReference() {
/* 163 */     return (this.list == null);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 167 */     StringBuffer sb = new StringBuffer();
/* 168 */     sb.append("BeanList ");
/* 169 */     if (isReadOnly()) {
/* 170 */       sb.append("readOnly ");
/*     */     }
/* 172 */     if (this.list == null) {
/* 173 */       sb.append("deferred ");
/*     */     } else {
/*     */       
/* 176 */       sb.append("size[").append(this.list.size()).append("] ");
/* 177 */       sb.append("hasMoreRows[").append(this.hasMoreRows).append("] ");
/* 178 */       sb.append("list").append(this.list).append("");
/*     */     } 
/* 180 */     return sb.toString();
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
/*     */   public boolean equals(Object obj) {
/* 192 */     init();
/* 193 */     return this.list.equals(obj);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 197 */     init();
/* 198 */     return this.list.hashCode();
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
/*     */   public void add(int index, E element) {
/* 213 */     checkReadOnly();
/* 214 */     init();
/* 215 */     if (this.modifyAddListening) {
/* 216 */       modifyAddition(element);
/*     */     }
/* 218 */     this.list.add(index, element);
/*     */   }
/*     */   
/*     */   public boolean add(E o) {
/* 222 */     checkReadOnly();
/* 223 */     init();
/* 224 */     if (this.modifyAddListening) {
/* 225 */       if (this.list.add(o)) {
/* 226 */         modifyAddition(o);
/* 227 */         return true;
/*     */       } 
/* 229 */       return false;
/*     */     } 
/*     */     
/* 232 */     return this.list.add(o);
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends E> c) {
/* 236 */     checkReadOnly();
/* 237 */     init();
/* 238 */     if (this.modifyAddListening)
/*     */     {
/* 240 */       getModifyHolder().modifyAdditionAll(c);
/*     */     }
/* 242 */     return this.list.addAll(c);
/*     */   }
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends E> c) {
/* 246 */     checkReadOnly();
/* 247 */     init();
/* 248 */     if (this.modifyAddListening)
/*     */     {
/* 250 */       getModifyHolder().modifyAdditionAll(c);
/*     */     }
/* 252 */     return this.list.addAll(index, c);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 256 */     checkReadOnly();
/*     */ 
/*     */     
/* 259 */     initClear();
/* 260 */     if (this.modifyRemoveListening) {
/* 261 */       for (int i = 0; i < this.list.size(); i++) {
/* 262 */         getModifyHolder().modifyRemoval(this.list.get(i));
/*     */       }
/*     */     }
/* 265 */     this.list.clear();
/*     */   }
/*     */   
/*     */   public boolean contains(Object o) {
/* 269 */     init();
/* 270 */     return this.list.contains(o);
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 274 */     init();
/* 275 */     return this.list.containsAll(c);
/*     */   }
/*     */   
/*     */   public E get(int index) {
/* 279 */     init();
/* 280 */     return this.list.get(index);
/*     */   }
/*     */   
/*     */   public int indexOf(Object o) {
/* 284 */     init();
/* 285 */     return this.list.indexOf(o);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 289 */     init();
/* 290 */     return this.list.isEmpty();
/*     */   }
/*     */   
/*     */   public Iterator<E> iterator() {
/* 294 */     init();
/* 295 */     if (isReadOnly()) {
/* 296 */       return new ReadOnlyListIterator<E>(this.list.listIterator());
/*     */     }
/* 298 */     if (this.modifyListening) {
/* 299 */       Iterator<E> it = this.list.iterator();
/* 300 */       return new ModifyIterator<E>(this, it);
/*     */     } 
/* 302 */     return this.list.iterator();
/*     */   }
/*     */   
/*     */   public int lastIndexOf(Object o) {
/* 306 */     init();
/* 307 */     return this.list.lastIndexOf(o);
/*     */   }
/*     */   
/*     */   public ListIterator<E> listIterator() {
/* 311 */     init();
/* 312 */     if (isReadOnly()) {
/* 313 */       return new ReadOnlyListIterator<E>(this.list.listIterator());
/*     */     }
/* 315 */     if (this.modifyListening) {
/* 316 */       ListIterator<E> it = this.list.listIterator();
/* 317 */       return new ModifyListIterator<E>(this, it);
/*     */     } 
/* 319 */     return this.list.listIterator();
/*     */   }
/*     */   
/*     */   public ListIterator<E> listIterator(int index) {
/* 323 */     init();
/* 324 */     if (isReadOnly()) {
/* 325 */       return new ReadOnlyListIterator<E>(this.list.listIterator(index));
/*     */     }
/* 327 */     if (this.modifyListening) {
/* 328 */       ListIterator<E> it = this.list.listIterator(index);
/* 329 */       return new ModifyListIterator<E>(this, it);
/*     */     } 
/* 331 */     return this.list.listIterator(index);
/*     */   }
/*     */   
/*     */   public E remove(int index) {
/* 335 */     checkReadOnly();
/* 336 */     init();
/* 337 */     if (this.modifyRemoveListening) {
/* 338 */       E o = this.list.remove(index);
/* 339 */       modifyRemoval(o);
/* 340 */       return o;
/*     */     } 
/* 342 */     return this.list.remove(index);
/*     */   }
/*     */   
/*     */   public boolean remove(Object o) {
/* 346 */     checkReadOnly();
/* 347 */     init();
/* 348 */     if (this.modifyRemoveListening) {
/* 349 */       boolean isRemove = this.list.remove(o);
/* 350 */       if (isRemove) {
/* 351 */         modifyRemoval(o);
/*     */       }
/* 353 */       return isRemove;
/*     */     } 
/* 355 */     return this.list.remove(o);
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 359 */     checkReadOnly();
/* 360 */     init();
/* 361 */     if (this.modifyRemoveListening) {
/* 362 */       boolean changed = false;
/* 363 */       Iterator<?> it = c.iterator();
/* 364 */       while (it.hasNext()) {
/* 365 */         Object o = it.next();
/* 366 */         if (this.list.remove(o)) {
/* 367 */           modifyRemoval(o);
/* 368 */           changed = true;
/*     */         } 
/*     */       } 
/* 371 */       return changed;
/*     */     } 
/* 373 */     return this.list.removeAll(c);
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 377 */     checkReadOnly();
/* 378 */     init();
/* 379 */     if (this.modifyRemoveListening) {
/* 380 */       boolean changed = false;
/* 381 */       Iterator<E> it = this.list.iterator();
/* 382 */       while (it.hasNext()) {
/* 383 */         Object o = it.next();
/* 384 */         if (!c.contains(o)) {
/* 385 */           it.remove();
/* 386 */           modifyRemoval(o);
/* 387 */           changed = true;
/*     */         } 
/*     */       } 
/* 390 */       return changed;
/*     */     } 
/* 392 */     return this.list.retainAll(c);
/*     */   }
/*     */   
/*     */   public E set(int index, E element) {
/* 396 */     checkReadOnly();
/* 397 */     init();
/* 398 */     if (this.modifyListening) {
/* 399 */       E o = this.list.set(index, element);
/* 400 */       modifyAddition(element);
/* 401 */       modifyRemoval(o);
/* 402 */       return o;
/*     */     } 
/* 404 */     return this.list.set(index, element);
/*     */   }
/*     */   
/*     */   public int size() {
/* 408 */     init();
/* 409 */     return this.list.size();
/*     */   }
/*     */   
/*     */   public List<E> subList(int fromIndex, int toIndex) {
/* 413 */     init();
/* 414 */     if (isReadOnly()) {
/* 415 */       return Collections.unmodifiableList(this.list.subList(fromIndex, toIndex));
/*     */     }
/* 417 */     if (this.modifyListening) {
/* 418 */       return new ModifyList<E>(this, this.list.subList(fromIndex, toIndex));
/*     */     }
/* 420 */     return this.list.subList(fromIndex, toIndex);
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/* 424 */     init();
/* 425 */     return this.list.toArray();
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 429 */     init();
/* 430 */     return this.list.toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ReadOnlyListIterator<E>
/*     */     implements ListIterator<E>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 3097271091406323699L;
/*     */     private final ListIterator<E> i;
/*     */     
/*     */     ReadOnlyListIterator(ListIterator<E> i) {
/* 441 */       this.i = i;
/*     */     }
/*     */     public void add(E o) {
/* 444 */       throw new IllegalStateException("This collection is in ReadOnly mode");
/*     */     }
/*     */     public void remove() {
/* 447 */       throw new IllegalStateException("This collection is in ReadOnly mode");
/*     */     }
/*     */     public void set(E o) {
/* 450 */       throw new IllegalStateException("This collection is in ReadOnly mode");
/*     */     }
/*     */     public boolean hasNext() {
/* 453 */       return this.i.hasNext();
/*     */     }
/*     */     public boolean hasPrevious() {
/* 456 */       return this.i.hasPrevious();
/*     */     }
/*     */     public E next() {
/* 459 */       return this.i.next();
/*     */     }
/*     */     public int nextIndex() {
/* 462 */       return this.i.nextIndex();
/*     */     }
/*     */     public E previous() {
/* 465 */       return this.i.previous();
/*     */     }
/*     */     public int previousIndex() {
/* 468 */       return this.i.previousIndex();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\common\BeanList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */