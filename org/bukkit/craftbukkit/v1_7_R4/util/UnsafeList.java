/*     */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.RandomAccess;
/*     */ 
/*     */ 
/*     */ public class UnsafeList<E>
/*     */   extends AbstractList<E>
/*     */   implements List<E>, RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8683452581112892191L;
/*     */   private transient Object[] data;
/*     */   private int size;
/*     */   private int initialCapacity;
/*  23 */   private Iterator[] iterPool = new Iterator[1];
/*     */   
/*     */   private int maxPool;
/*     */   private int poolCounter;
/*     */   
/*     */   public UnsafeList(int capacity, int maxIterPool) {
/*  29 */     if (capacity < 0) capacity = 32; 
/*  30 */     int rounded = Integer.highestOneBit(capacity - 1) << 1;
/*  31 */     this.data = new Object[rounded];
/*  32 */     this.initialCapacity = rounded;
/*  33 */     this.maxPool = maxIterPool;
/*  34 */     this.iterPool[0] = new Itr();
/*     */   }
/*     */   
/*     */   public UnsafeList(int capacity) {
/*  38 */     this(capacity, 5);
/*     */   }
/*     */   
/*     */   public UnsafeList() {
/*  42 */     this(32);
/*     */   }
/*     */   
/*     */   public E get(int index) {
/*  46 */     rangeCheck(index);
/*     */     
/*  48 */     return (E)this.data[index];
/*     */   }
/*     */   
/*     */   public E unsafeGet(int index) {
/*  52 */     return (E)this.data[index];
/*     */   }
/*     */   
/*     */   public E set(int index, E element) {
/*  56 */     rangeCheck(index);
/*     */     
/*  58 */     E old = (E)this.data[index];
/*  59 */     this.data[index] = element;
/*  60 */     return old;
/*     */   }
/*     */   
/*     */   public boolean add(E element) {
/*  64 */     growIfNeeded();
/*  65 */     this.data[this.size++] = element;
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   public void add(int index, E element) {
/*  70 */     growIfNeeded();
/*  71 */     System.arraycopy(this.data, index, this.data, index + 1, this.size - index);
/*  72 */     this.data[index] = element;
/*  73 */     this.size++;
/*     */   }
/*     */   
/*     */   public E remove(int index) {
/*  77 */     rangeCheck(index);
/*     */     
/*  79 */     E old = (E)this.data[index];
/*  80 */     int movedCount = this.size - index - 1;
/*  81 */     if (movedCount > 0) {
/*  82 */       System.arraycopy(this.data, index + 1, this.data, index, movedCount);
/*     */     }
/*  84 */     this.data[--this.size] = null;
/*     */     
/*  86 */     return old;
/*     */   }
/*     */   
/*     */   public boolean remove(Object o) {
/*  90 */     int index = indexOf(o);
/*  91 */     if (index >= 0) {
/*  92 */       remove(index);
/*  93 */       return true;
/*     */     } 
/*     */     
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public int indexOf(Object o) {
/* 100 */     for (int i = 0; i < this.size; i++) {
/* 101 */       if (o == this.data[i] || o.equals(this.data[i])) {
/* 102 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean contains(Object o) {
/* 110 */     return (indexOf(o) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 115 */     this.size = 0;
/*     */ 
/*     */     
/* 118 */     if (this.data.length > this.initialCapacity << 3) {
/* 119 */       this.data = new Object[this.initialCapacity];
/*     */     } else {
/* 121 */       for (int i = 0; i < this.data.length; i++) {
/* 122 */         this.data[i] = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void trimToSize() {
/* 129 */     int old = this.data.length;
/* 130 */     int rounded = Integer.highestOneBit(this.size - 1) << 1;
/* 131 */     if (rounded < old) {
/* 132 */       this.data = Java15Compat.Arrays_copyOf(this.data, rounded);
/*     */     }
/*     */   }
/*     */   
/*     */   public int size() {
/* 137 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 141 */     return (this.size == 0);
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 145 */     UnsafeList<E> copy = (UnsafeList<E>)super.clone();
/* 146 */     copy.data = Java15Compat.Arrays_copyOf(this.data, this.size);
/* 147 */     copy.size = this.size;
/* 148 */     copy.initialCapacity = this.initialCapacity;
/* 149 */     copy.iterPool = new Iterator[1];
/* 150 */     copy.iterPool[0] = new Itr();
/* 151 */     copy.maxPool = this.maxPool;
/* 152 */     copy.poolCounter = 0;
/* 153 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/* 158 */     for (Iterator iter : this.iterPool) {
/* 159 */       if (!((Itr)iter).valid) {
/* 160 */         Itr iterator = (Itr)iter;
/* 161 */         iterator.reset();
/* 162 */         return iterator;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 167 */     if (this.iterPool.length < this.maxPool) {
/* 168 */       Iterator[] newPool = new Iterator[this.iterPool.length + 1];
/* 169 */       System.arraycopy(this.iterPool, 0, newPool, 0, this.iterPool.length);
/* 170 */       this.iterPool = newPool;
/*     */       
/* 172 */       this.iterPool[this.iterPool.length - 1] = new Itr();
/* 173 */       return this.iterPool[this.iterPool.length - 1];
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 178 */     this.poolCounter = ++this.poolCounter % this.iterPool.length;
/* 179 */     this.iterPool[this.poolCounter] = new Itr();
/* 180 */     return this.iterPool[this.poolCounter];
/*     */   }
/*     */   
/*     */   private void rangeCheck(int index) {
/* 184 */     if (index >= this.size || index < 0) {
/* 185 */       throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
/*     */     }
/*     */   }
/*     */   
/*     */   private void growIfNeeded() {
/* 190 */     if (this.size == this.data.length) {
/* 191 */       Object[] newData = new Object[this.data.length << 1];
/* 192 */       System.arraycopy(this.data, 0, newData, 0, this.size);
/* 193 */       this.data = newData;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream os) throws IOException {
/* 198 */     os.defaultWriteObject();
/*     */     
/* 200 */     os.writeInt(this.size);
/* 201 */     os.writeInt(this.initialCapacity);
/* 202 */     for (int i = 0; i < this.size; i++) {
/* 203 */       os.writeObject(this.data[i]);
/*     */     }
/* 205 */     os.writeInt(this.maxPool);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
/* 209 */     is.defaultReadObject();
/*     */     
/* 211 */     this.size = is.readInt();
/* 212 */     this.initialCapacity = is.readInt();
/* 213 */     this.data = new Object[Integer.highestOneBit(this.size - 1) << 1];
/* 214 */     for (int i = 0; i < this.size; i++) {
/* 215 */       this.data[i] = is.readObject();
/*     */     }
/* 217 */     this.maxPool = is.readInt();
/* 218 */     this.iterPool = new Iterator[1];
/* 219 */     this.iterPool[0] = new Itr();
/*     */   }
/*     */   
/*     */   public class Itr implements Iterator<E> {
/*     */     int index;
/* 224 */     int lastRet = -1;
/* 225 */     int expectedModCount = UnsafeList.this.modCount;
/*     */     public boolean valid = true;
/*     */     
/*     */     public void reset() {
/* 229 */       this.index = 0;
/* 230 */       this.lastRet = -1;
/* 231 */       this.expectedModCount = UnsafeList.this.modCount;
/* 232 */       this.valid = true;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 236 */       this.valid = (this.index != UnsafeList.this.size);
/* 237 */       return this.valid;
/*     */     }
/*     */     
/*     */     public E next() {
/* 241 */       if (UnsafeList.this.modCount != this.expectedModCount) {
/* 242 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 245 */       int i = this.index;
/* 246 */       if (i >= UnsafeList.this.size) {
/* 247 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 250 */       if (i >= UnsafeList.this.data.length) {
/* 251 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 254 */       this.index = i + 1;
/* 255 */       return (E)UnsafeList.this.data[this.lastRet = i];
/*     */     }
/*     */     
/*     */     public void remove() {
/* 259 */       if (this.lastRet < 0) {
/* 260 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 263 */       if (UnsafeList.this.modCount != this.expectedModCount) {
/* 264 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/*     */       try {
/* 268 */         UnsafeList.this.remove(this.lastRet);
/* 269 */         this.index = this.lastRet;
/* 270 */         this.lastRet = -1;
/* 271 */         this.expectedModCount = UnsafeList.this.modCount;
/* 272 */       } catch (IndexOutOfBoundsException ex) {
/* 273 */         throw new ConcurrentModificationException();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\UnsafeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */