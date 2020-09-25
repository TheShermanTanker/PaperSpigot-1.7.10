/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
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
/*     */ public final class Bytes
/*     */ {
/*     */   public static int hashCode(byte value) {
/*  58 */     return value;
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
/*     */   public static boolean contains(byte[] array, byte target) {
/*  71 */     for (byte value : array) {
/*  72 */       if (value == target) {
/*  73 */         return true;
/*     */       }
/*     */     } 
/*  76 */     return false;
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
/*     */   public static int indexOf(byte[] array, byte target) {
/*  89 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(byte[] array, byte target, int start, int end) {
/*  95 */     for (int i = start; i < end; i++) {
/*  96 */       if (array[i] == target) {
/*  97 */         return i;
/*     */       }
/*     */     } 
/* 100 */     return -1;
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
/*     */   public static int indexOf(byte[] array, byte[] target) {
/* 115 */     Preconditions.checkNotNull(array, "array");
/* 116 */     Preconditions.checkNotNull(target, "target");
/* 117 */     if (target.length == 0) {
/* 118 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 122 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 123 */       int j = 0; while (true) { if (j < target.length) {
/* 124 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 128 */         return i; }
/*     */     
/* 130 */     }  return -1;
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
/*     */   public static int lastIndexOf(byte[] array, byte target) {
/* 143 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(byte[] array, byte target, int start, int end) {
/* 149 */     for (int i = end - 1; i >= start; i--) {
/* 150 */       if (array[i] == target) {
/* 151 */         return i;
/*     */       }
/*     */     } 
/* 154 */     return -1;
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
/*     */   public static byte[] concat(byte[]... arrays) {
/* 167 */     int length = 0;
/* 168 */     for (byte[] array : arrays) {
/* 169 */       length += array.length;
/*     */     }
/* 171 */     byte[] result = new byte[length];
/* 172 */     int pos = 0;
/* 173 */     for (byte[] array : arrays) {
/* 174 */       System.arraycopy(array, 0, result, pos, array.length);
/* 175 */       pos += array.length;
/*     */     } 
/* 177 */     return result;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] ensureCapacity(byte[] array, int minLength, int padding) {
/* 198 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 199 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 200 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] copyOf(byte[] original, int length) {
/* 207 */     byte[] copy = new byte[length];
/* 208 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 209 */     return copy;
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
/*     */   public static byte[] toArray(Collection<Byte> collection) {
/* 227 */     if (collection instanceof ByteArrayAsList) {
/* 228 */       return ((ByteArrayAsList)collection).toByteArray();
/*     */     }
/*     */     
/* 231 */     Object[] boxedArray = collection.toArray();
/* 232 */     int len = boxedArray.length;
/* 233 */     byte[] array = new byte[len];
/* 234 */     for (int i = 0; i < len; i++)
/*     */     {
/* 236 */       array[i] = ((Byte)Preconditions.checkNotNull(boxedArray[i])).byteValue();
/*     */     }
/* 238 */     return array;
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
/*     */   public static List<Byte> asList(byte... backingArray) {
/* 256 */     if (backingArray.length == 0) {
/* 257 */       return Collections.emptyList();
/*     */     }
/* 259 */     return new ByteArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class ByteArrayAsList extends AbstractList<Byte> implements RandomAccess, Serializable {
/*     */     final byte[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ByteArrayAsList(byte[] array) {
/* 270 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     ByteArrayAsList(byte[] array, int start, int end) {
/* 274 */       this.array = array;
/* 275 */       this.start = start;
/* 276 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 280 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 284 */       return false;
/*     */     }
/*     */     
/*     */     public Byte get(int index) {
/* 288 */       Preconditions.checkElementIndex(index, size());
/* 289 */       return Byte.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 294 */       return (target instanceof Byte && Bytes.indexOf(this.array, ((Byte)target).byteValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 300 */       if (target instanceof Byte) {
/* 301 */         int i = Bytes.indexOf(this.array, ((Byte)target).byteValue(), this.start, this.end);
/* 302 */         if (i >= 0) {
/* 303 */           return i - this.start;
/*     */         }
/*     */       } 
/* 306 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 311 */       if (target instanceof Byte) {
/* 312 */         int i = Bytes.lastIndexOf(this.array, ((Byte)target).byteValue(), this.start, this.end);
/* 313 */         if (i >= 0) {
/* 314 */           return i - this.start;
/*     */         }
/*     */       } 
/* 317 */       return -1;
/*     */     }
/*     */     
/*     */     public Byte set(int index, Byte element) {
/* 321 */       Preconditions.checkElementIndex(index, size());
/* 322 */       byte oldValue = this.array[this.start + index];
/* 323 */       this.array[this.start + index] = ((Byte)Preconditions.checkNotNull(element)).byteValue();
/* 324 */       return Byte.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Byte> subList(int fromIndex, int toIndex) {
/* 328 */       int size = size();
/* 329 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 330 */       if (fromIndex == toIndex) {
/* 331 */         return Collections.emptyList();
/*     */       }
/* 333 */       return new ByteArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 337 */       if (object == this) {
/* 338 */         return true;
/*     */       }
/* 340 */       if (object instanceof ByteArrayAsList) {
/* 341 */         ByteArrayAsList that = (ByteArrayAsList)object;
/* 342 */         int size = size();
/* 343 */         if (that.size() != size) {
/* 344 */           return false;
/*     */         }
/* 346 */         for (int i = 0; i < size; i++) {
/* 347 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 348 */             return false;
/*     */           }
/*     */         } 
/* 351 */         return true;
/*     */       } 
/* 353 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 357 */       int result = 1;
/* 358 */       for (int i = this.start; i < this.end; i++) {
/* 359 */         result = 31 * result + Bytes.hashCode(this.array[i]);
/*     */       }
/* 361 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 365 */       StringBuilder builder = new StringBuilder(size() * 5);
/* 366 */       builder.append('[').append(this.array[this.start]);
/* 367 */       for (int i = this.start + 1; i < this.end; i++) {
/* 368 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 370 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     byte[] toByteArray() {
/* 375 */       int size = size();
/* 376 */       byte[] result = new byte[size];
/* 377 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 378 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\Bytes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */