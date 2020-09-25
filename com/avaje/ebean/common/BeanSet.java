/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.bean.SerializeControl;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
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
/*     */ public final class BeanSet<E>
/*     */   extends AbstractBeanCollection<E>
/*     */   implements Set<E>, BeanCollectionAdd
/*     */ {
/*     */   private Set<E> set;
/*     */   
/*     */   public BeanSet(Set<E> set) {
/*  47 */     this.set = set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanSet() {
/*  54 */     this(new LinkedHashSet<E>());
/*     */   }
/*     */   
/*     */   public BeanSet(BeanCollectionLoader loader, Object ownerBean, String propertyName) {
/*  58 */     super(loader, ownerBean, propertyName);
/*     */   }
/*     */ 
/*     */   
/*     */   Object readResolve() throws ObjectStreamException {
/*  63 */     if (SerializeControl.isVanillaCollections()) {
/*  64 */       return this.set;
/*     */     }
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   Object writeReplace() throws ObjectStreamException {
/*  70 */     if (SerializeControl.isVanillaCollections()) {
/*  71 */       return this.set;
/*     */     }
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBean(Object bean) {
/*  78 */     this.set.add((E)bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void internalAdd(Object bean) {
/*  83 */     this.set.add((E)bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPopulated() {
/*  90 */     return (this.set != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReference() {
/*  98 */     return (this.set == null);
/*     */   }
/*     */   
/*     */   public boolean checkEmptyLazyLoad() {
/* 102 */     if (this.set == null) {
/* 103 */       this.set = new LinkedHashSet<E>();
/* 104 */       return true;
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initClear() {
/* 111 */     synchronized (this) {
/* 112 */       if (this.set == null) {
/* 113 */         if (this.modifyListening) {
/* 114 */           lazyLoadCollection(true);
/*     */         } else {
/* 116 */           this.set = new LinkedHashSet<E>();
/*     */         } 
/*     */       }
/* 119 */       touched();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init() {
/* 124 */     synchronized (this) {
/* 125 */       if (this.set == null) {
/* 126 */         lazyLoadCollection(true);
/*     */       }
/* 128 */       touched();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActualSet(Set<?> set) {
/* 137 */     this.set = (Set)set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<E> getActualSet() {
/* 144 */     return this.set;
/*     */   }
/*     */   
/*     */   public Collection<E> getActualDetails() {
/* 148 */     return this.set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getActualCollection() {
/* 155 */     return this.set;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 160 */     StringBuffer sb = new StringBuffer();
/* 161 */     sb.append("BeanSet ");
/* 162 */     if (isReadOnly()) {
/* 163 */       sb.append("readOnly ");
/*     */     }
/* 165 */     if (this.set == null) {
/* 166 */       sb.append("deferred ");
/*     */     } else {
/*     */       
/* 169 */       sb.append("size[").append(this.set.size()).append("]");
/* 170 */       sb.append(" hasMoreRows[").append(this.hasMoreRows).append("]");
/* 171 */       sb.append(" set").append(this.set);
/*     */     } 
/* 173 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 180 */     init();
/* 181 */     return this.set.equals(obj);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 185 */     init();
/* 186 */     return this.set.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(E o) {
/* 195 */     checkReadOnly();
/* 196 */     init();
/* 197 */     if (this.modifyAddListening) {
/* 198 */       if (this.set.add(o)) {
/* 199 */         modifyAddition(o);
/* 200 */         return true;
/*     */       } 
/* 202 */       return false;
/*     */     } 
/*     */     
/* 205 */     return this.set.add(o);
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends E> c) {
/* 209 */     checkReadOnly();
/* 210 */     init();
/* 211 */     if (this.modifyAddListening) {
/* 212 */       boolean changed = false;
/* 213 */       Iterator<? extends E> it = c.iterator();
/* 214 */       while (it.hasNext()) {
/* 215 */         E o = it.next();
/* 216 */         if (this.set.add(o)) {
/* 217 */           modifyAddition(o);
/* 218 */           changed = true;
/*     */         } 
/*     */       } 
/* 221 */       return changed;
/*     */     } 
/* 223 */     return this.set.addAll(c);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 227 */     checkReadOnly();
/* 228 */     initClear();
/* 229 */     if (this.modifyRemoveListening) {
/* 230 */       Iterator<E> it = this.set.iterator();
/* 231 */       while (it.hasNext()) {
/* 232 */         E e = it.next();
/* 233 */         modifyRemoval(e);
/*     */       } 
/*     */     } 
/* 236 */     this.set.clear();
/*     */   }
/*     */   
/*     */   public boolean contains(Object o) {
/* 240 */     init();
/* 241 */     return this.set.contains(o);
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 245 */     init();
/* 246 */     return this.set.containsAll(c);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 250 */     init();
/* 251 */     return this.set.isEmpty();
/*     */   }
/*     */   
/*     */   public Iterator<E> iterator() {
/* 255 */     init();
/* 256 */     if (isReadOnly()) {
/* 257 */       return new ReadOnlyIterator<E>(this.set.iterator());
/*     */     }
/* 259 */     if (this.modifyListening) {
/* 260 */       return new ModifyIterator<E>(this, this.set.iterator());
/*     */     }
/* 262 */     return this.set.iterator();
/*     */   }
/*     */   
/*     */   public boolean remove(Object o) {
/* 266 */     checkReadOnly();
/* 267 */     init();
/* 268 */     if (this.modifyRemoveListening) {
/* 269 */       if (this.set.remove(o)) {
/* 270 */         modifyRemoval(o);
/* 271 */         return true;
/*     */       } 
/* 273 */       return false;
/*     */     } 
/* 275 */     return this.set.remove(o);
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 279 */     checkReadOnly();
/* 280 */     init();
/* 281 */     if (this.modifyRemoveListening) {
/* 282 */       boolean changed = false;
/* 283 */       Iterator<?> it = c.iterator();
/* 284 */       while (it.hasNext()) {
/* 285 */         Object o = it.next();
/* 286 */         if (this.set.remove(o)) {
/* 287 */           modifyRemoval(o);
/* 288 */           changed = true;
/*     */         } 
/*     */       } 
/* 291 */       return changed;
/*     */     } 
/* 293 */     return this.set.removeAll(c);
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 297 */     checkReadOnly();
/* 298 */     init();
/* 299 */     if (this.modifyRemoveListening) {
/* 300 */       boolean changed = false;
/* 301 */       Iterator<?> it = this.set.iterator();
/* 302 */       while (it.hasNext()) {
/* 303 */         Object o = it.next();
/* 304 */         if (!c.contains(o)) {
/* 305 */           it.remove();
/* 306 */           modifyRemoval(o);
/* 307 */           changed = true;
/*     */         } 
/*     */       } 
/* 310 */       return changed;
/*     */     } 
/* 312 */     return this.set.retainAll(c);
/*     */   }
/*     */   
/*     */   public int size() {
/* 316 */     init();
/* 317 */     return this.set.size();
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/* 321 */     init();
/* 322 */     return this.set.toArray();
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 326 */     init();
/* 327 */     return this.set.toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ReadOnlyIterator<E>
/*     */     implements Iterator<E>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 2577697326745352605L;
/*     */     private final Iterator<E> it;
/*     */     
/*     */     ReadOnlyIterator(Iterator<E> it) {
/* 338 */       this.it = it;
/*     */     }
/*     */     public boolean hasNext() {
/* 341 */       return this.it.hasNext();
/*     */     }
/*     */     
/*     */     public E next() {
/* 345 */       return this.it.next();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 349 */       throw new IllegalStateException("This collection is in ReadOnly mode");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\common\BeanSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */