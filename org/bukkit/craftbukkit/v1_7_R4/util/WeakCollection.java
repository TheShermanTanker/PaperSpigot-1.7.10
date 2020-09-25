/*     */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.commons.lang.Validate;
/*     */ 
/*     */ public final class WeakCollection<T>
/*     */   implements Collection<T> {
/*  12 */   static final Object NO_VALUE = new Object();
/*     */ 
/*     */ 
/*     */   
/*  16 */   private final Collection<WeakReference<T>> collection = new ArrayList<WeakReference<T>>();
/*     */ 
/*     */   
/*     */   public boolean add(T value) {
/*  20 */     Validate.notNull(value, "Cannot add null value");
/*  21 */     return this.collection.add(new WeakReference<T>(value));
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends T> collection) {
/*  25 */     Collection<WeakReference<T>> values = this.collection;
/*  26 */     boolean ret = false;
/*  27 */     for (T value : collection) {
/*  28 */       Validate.notNull(value, "Cannot add null value");
/*  29 */       ret |= values.add(new WeakReference<T>(value));
/*     */     } 
/*  31 */     return ret;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  35 */     this.collection.clear();
/*     */   }
/*     */   
/*     */   public boolean contains(Object object) {
/*  39 */     if (object == null) {
/*  40 */       return false;
/*     */     }
/*  42 */     for (T compare : this) {
/*  43 */       if (object.equals(compare)) {
/*  44 */         return true;
/*     */       }
/*     */     } 
/*  47 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> collection) {
/*  51 */     return toCollection().containsAll(collection);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  55 */     return !iterator().hasNext();
/*     */   }
/*     */   
/*     */   public Iterator<T> iterator() {
/*  59 */     return new Iterator<T>() {
/*  60 */         Iterator<WeakReference<T>> it = WeakCollection.this.collection.iterator();
/*  61 */         Object value = WeakCollection.NO_VALUE;
/*     */         
/*     */         public boolean hasNext() {
/*  64 */           Object value = this.value;
/*  65 */           if (value != null && value != WeakCollection.NO_VALUE) {
/*  66 */             return true;
/*     */           }
/*     */           
/*  69 */           Iterator<WeakReference<T>> it = this.it;
/*  70 */           value = null;
/*     */           
/*  72 */           while (it.hasNext()) {
/*  73 */             WeakReference<T> ref = it.next();
/*  74 */             value = ref.get();
/*  75 */             if (value == null) {
/*  76 */               it.remove(); continue;
/*     */             } 
/*  78 */             this.value = value;
/*  79 */             return true;
/*     */           } 
/*     */           
/*  82 */           return false;
/*     */         }
/*     */         
/*     */         public T next() throws NoSuchElementException {
/*  86 */           if (!hasNext()) {
/*  87 */             throw new NoSuchElementException("No more elements");
/*     */           }
/*     */ 
/*     */           
/*  91 */           T value = (T)this.value;
/*  92 */           this.value = WeakCollection.NO_VALUE;
/*  93 */           return value;
/*     */         }
/*     */         
/*     */         public void remove() throws IllegalStateException {
/*  97 */           if (this.value != WeakCollection.NO_VALUE) {
/*  98 */             throw new IllegalStateException("No last element");
/*     */           }
/*     */           
/* 101 */           this.value = null;
/* 102 */           this.it.remove();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean remove(Object object) {
/* 108 */     if (object == null) {
/* 109 */       return false;
/*     */     }
/*     */     
/* 112 */     Iterator<T> it = iterator();
/* 113 */     while (it.hasNext()) {
/* 114 */       if (object.equals(it.next())) {
/* 115 */         it.remove();
/* 116 */         return true;
/*     */       } 
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> collection) {
/* 123 */     Iterator<T> it = iterator();
/* 124 */     boolean ret = false;
/* 125 */     while (it.hasNext()) {
/* 126 */       if (collection.contains(it.next())) {
/* 127 */         ret = true;
/* 128 */         it.remove();
/*     */       } 
/*     */     } 
/* 131 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection<?> collection) {
/* 135 */     Iterator<T> it = iterator();
/* 136 */     boolean ret = false;
/* 137 */     while (it.hasNext()) {
/* 138 */       if (!collection.contains(it.next())) {
/* 139 */         ret = true;
/* 140 */         it.remove();
/*     */       } 
/*     */     } 
/* 143 */     return ret;
/*     */   }
/*     */   
/*     */   public int size() {
/* 147 */     int s = 0;
/* 148 */     for (T value : this) {
/* 149 */       s++;
/*     */     }
/* 151 */     return s;
/*     */   }
/*     */   
/*     */   public Object[] toArray() {
/* 155 */     return toArray(new Object[0]);
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] array) {
/* 159 */     return toCollection().toArray(array);
/*     */   }
/*     */   
/*     */   private Collection<T> toCollection() {
/* 163 */     ArrayList<T> collection = new ArrayList<T>();
/* 164 */     for (T value : this) {
/* 165 */       collection.add(value);
/*     */     }
/* 167 */     return collection;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\WeakCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */