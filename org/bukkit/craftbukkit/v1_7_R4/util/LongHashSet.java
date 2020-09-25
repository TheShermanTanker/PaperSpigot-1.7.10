/*     */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*     */ 
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class LongHashSet
/*     */ {
/*     */   private static final int INITIAL_SIZE = 3;
/*     */   private static final double LOAD_FACTOR = 0.75D;
/*     */   private static final long FREE = 0L;
/*     */   private static final long REMOVED = -9223372036854775808L;
/*     */   private int freeEntries;
/*     */   private int elements;
/*     */   private long[] values;
/*     */   private int modCount;
/*     */   
/*     */   public LongHashSet() {
/*  36 */     this(3);
/*     */   }
/*     */   
/*     */   public LongHashSet(int size) {
/*  40 */     this.values = new long[(size == 0) ? 1 : size];
/*  41 */     this.elements = 0;
/*  42 */     this.freeEntries = this.values.length;
/*  43 */     this.modCount = 0;
/*     */   }
/*     */   
/*     */   public Iterator iterator() {
/*  47 */     return new Itr();
/*     */   }
/*     */   
/*     */   public int size() {
/*  51 */     return this.elements;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  55 */     return (this.elements == 0);
/*     */   }
/*     */   
/*     */   public boolean contains(int msw, int lsw) {
/*  59 */     return contains(LongHash.toLong(msw, lsw));
/*     */   }
/*     */   
/*     */   public boolean contains(long value) {
/*  63 */     int hash = hash(value);
/*  64 */     int index = (hash & Integer.MAX_VALUE) % this.values.length;
/*  65 */     int offset = 1;
/*     */ 
/*     */     
/*  68 */     while (this.values[index] != 0L && (hash(this.values[index]) != hash || this.values[index] != value)) {
/*  69 */       index = (index + offset & Integer.MAX_VALUE) % this.values.length;
/*  70 */       offset = offset * 2 + 1;
/*     */       
/*  72 */       if (offset == -1) {
/*  73 */         offset = 2;
/*     */       }
/*     */     } 
/*     */     
/*  77 */     return (this.values[index] != 0L);
/*     */   }
/*     */   
/*     */   public boolean add(int msw, int lsw) {
/*  81 */     return add(LongHash.toLong(msw, lsw));
/*     */   }
/*     */   
/*     */   public boolean add(long value) {
/*  85 */     int hash = hash(value);
/*  86 */     int index = (hash & Integer.MAX_VALUE) % this.values.length;
/*  87 */     int offset = 1;
/*  88 */     int deletedix = -1;
/*     */ 
/*     */     
/*  91 */     while (this.values[index] != 0L && (hash(this.values[index]) != hash || this.values[index] != value)) {
/*     */ 
/*     */       
/*  94 */       if (this.values[index] == Long.MIN_VALUE) {
/*  95 */         deletedix = index;
/*     */       }
/*     */       
/*  98 */       index = (index + offset & Integer.MAX_VALUE) % this.values.length;
/*  99 */       offset = offset * 2 + 1;
/*     */       
/* 101 */       if (offset == -1) {
/* 102 */         offset = 2;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     if (this.values[index] == 0L) {
/* 107 */       if (deletedix != -1) {
/* 108 */         index = deletedix;
/*     */       } else {
/* 110 */         this.freeEntries--;
/*     */       } 
/*     */       
/* 113 */       this.modCount++;
/* 114 */       this.elements++;
/* 115 */       this.values[index] = value;
/*     */       
/* 117 */       if (1.0D - this.freeEntries / this.values.length > 0.75D) {
/* 118 */         rehash();
/*     */       }
/*     */       
/* 121 */       return true;
/*     */     } 
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(int msw, int lsw) {
/* 128 */     remove(LongHash.toLong(msw, lsw));
/*     */   }
/*     */   
/*     */   public boolean remove(long value) {
/* 132 */     int hash = hash(value);
/* 133 */     int index = (hash & Integer.MAX_VALUE) % this.values.length;
/* 134 */     int offset = 1;
/*     */ 
/*     */     
/* 137 */     while (this.values[index] != 0L && (hash(this.values[index]) != hash || this.values[index] != value)) {
/* 138 */       index = (index + offset & Integer.MAX_VALUE) % this.values.length;
/* 139 */       offset = offset * 2 + 1;
/*     */       
/* 141 */       if (offset == -1) {
/* 142 */         offset = 2;
/*     */       }
/*     */     } 
/*     */     
/* 146 */     if (this.values[index] != 0L) {
/* 147 */       this.values[index] = Long.MIN_VALUE;
/* 148 */       this.modCount++;
/* 149 */       this.elements--;
/* 150 */       return true;
/*     */     } 
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 157 */     this.elements = 0;
/* 158 */     for (int ix = 0; ix < this.values.length; ix++) {
/* 159 */       this.values[ix] = 0L;
/*     */     }
/*     */     
/* 162 */     this.freeEntries = this.values.length;
/* 163 */     this.modCount++;
/*     */   }
/*     */   
/*     */   public long[] toArray() {
/* 167 */     long[] result = new long[this.elements];
/* 168 */     long[] values = Java15Compat.Arrays_copyOf(this.values, this.values.length);
/* 169 */     int pos = 0;
/*     */     
/* 171 */     for (long value : values) {
/* 172 */       if (value != 0L && value != Long.MIN_VALUE) {
/* 173 */         result[pos++] = value;
/*     */       }
/*     */     } 
/*     */     
/* 177 */     return result;
/*     */   }
/*     */   
/*     */   public long popFirst() {
/* 181 */     for (long value : this.values) {
/* 182 */       if (value != 0L && value != Long.MIN_VALUE) {
/* 183 */         remove(value);
/* 184 */         return value;
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     return 0L;
/*     */   }
/*     */   
/*     */   public long[] popAll() {
/* 192 */     long[] ret = toArray();
/* 193 */     clear();
/* 194 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private int hash(long value) {
/* 199 */     value ^= value >>> 33L;
/* 200 */     value *= -49064778989728563L;
/* 201 */     value ^= value >>> 33L;
/* 202 */     value *= -4265267296055464877L;
/* 203 */     value ^= value >>> 33L;
/* 204 */     return (int)value;
/*     */   }
/*     */   
/*     */   private void rehash() {
/* 208 */     int gargagecells = this.values.length - this.elements + this.freeEntries;
/* 209 */     if (gargagecells / this.values.length > 0.05D) {
/* 210 */       rehash(this.values.length);
/*     */     } else {
/* 212 */       rehash(this.values.length * 2 + 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void rehash(int newCapacity) {
/* 217 */     long[] newValues = new long[newCapacity];
/*     */     
/* 219 */     for (long value : this.values) {
/* 220 */       if (value != 0L && value != Long.MIN_VALUE) {
/*     */ 
/*     */ 
/*     */         
/* 224 */         int hash = hash(value);
/* 225 */         int index = (hash & Integer.MAX_VALUE) % newCapacity;
/* 226 */         int offset = 1;
/*     */ 
/*     */         
/* 229 */         while (newValues[index] != 0L) {
/* 230 */           index = (index + offset & Integer.MAX_VALUE) % newCapacity;
/* 231 */           offset = offset * 2 + 1;
/*     */           
/* 233 */           if (offset == -1) {
/* 234 */             offset = 2;
/*     */           }
/*     */         } 
/*     */         
/* 238 */         newValues[index] = value;
/*     */       } 
/*     */     } 
/* 241 */     this.values = newValues;
/* 242 */     this.freeEntries = this.values.length - this.elements;
/*     */   }
/*     */   
/*     */   private class Itr implements Iterator {
/*     */     private int index;
/* 247 */     private int lastReturned = -1;
/*     */     private int expectedModCount;
/*     */     
/*     */     public Itr() {
/* 251 */       for (this.index = 0; this.index < LongHashSet.this.values.length && (LongHashSet.this.values[this.index] == 0L || LongHashSet.this.values[this.index] == Long.MIN_VALUE); this.index++);
/*     */ 
/*     */       
/* 254 */       this.expectedModCount = LongHashSet.this.modCount;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 258 */       return (this.index != LongHashSet.this.values.length);
/*     */     }
/*     */     
/*     */     public Long next() {
/* 262 */       if (LongHashSet.this.modCount != this.expectedModCount) {
/* 263 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 266 */       int length = LongHashSet.this.values.length;
/* 267 */       if (this.index >= length) {
/* 268 */         this.lastReturned = -2;
/* 269 */         throw new NoSuchElementException();
/*     */       } 
/*     */       
/* 272 */       this.lastReturned = this.index;
/* 273 */       this.index++; for (; this.index < length && (LongHashSet.this.values[this.index] == 0L || LongHashSet.this.values[this.index] == Long.MIN_VALUE); this.index++);
/*     */ 
/*     */ 
/*     */       
/* 277 */       if (LongHashSet.this.values[this.lastReturned] == 0L) {
/* 278 */         return Long.valueOf(0L);
/*     */       }
/* 280 */       return Long.valueOf(LongHashSet.this.values[this.lastReturned]);
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 285 */       if (LongHashSet.this.modCount != this.expectedModCount) {
/* 286 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 289 */       if (this.lastReturned == -1 || this.lastReturned == -2) {
/* 290 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 293 */       if (LongHashSet.this.values[this.lastReturned] != 0L && LongHashSet.this.values[this.lastReturned] != Long.MIN_VALUE) {
/* 294 */         LongHashSet.this.values[this.lastReturned] = Long.MIN_VALUE;
/* 295 */         LongHashSet.this.elements--;
/* 296 */         LongHashSet.this.modCount++;
/* 297 */         this.expectedModCount = LongHashSet.this.modCount;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\LongHashSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */