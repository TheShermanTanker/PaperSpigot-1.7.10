/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Longs
/*     */ {
/*     */   public static final int BYTES = 8;
/*     */   public static final long MAX_POWER_OF_TWO = 4611686018427387904L;
/*     */   
/*     */   public static int hashCode(long value) {
/*  73 */     return (int)(value ^ value >>> 32L);
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
/*     */   public static int compare(long a, long b) {
/*  86 */     return (a < b) ? -1 : ((a > b) ? 1 : 0);
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
/*     */   public static boolean contains(long[] array, long target) {
/*  99 */     for (long value : array) {
/* 100 */       if (value == target) {
/* 101 */         return true;
/*     */       }
/*     */     } 
/* 104 */     return false;
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
/*     */   public static int indexOf(long[] array, long target) {
/* 117 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(long[] array, long target, int start, int end) {
/* 123 */     for (int i = start; i < end; i++) {
/* 124 */       if (array[i] == target) {
/* 125 */         return i;
/*     */       }
/*     */     } 
/* 128 */     return -1;
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
/*     */   public static int indexOf(long[] array, long[] target) {
/* 143 */     Preconditions.checkNotNull(array, "array");
/* 144 */     Preconditions.checkNotNull(target, "target");
/* 145 */     if (target.length == 0) {
/* 146 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 150 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 151 */       int j = 0; while (true) { if (j < target.length) {
/* 152 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 156 */         return i; }
/*     */     
/* 158 */     }  return -1;
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
/*     */   public static int lastIndexOf(long[] array, long target) {
/* 171 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(long[] array, long target, int start, int end) {
/* 177 */     for (int i = end - 1; i >= start; i--) {
/* 178 */       if (array[i] == target) {
/* 179 */         return i;
/*     */       }
/*     */     } 
/* 182 */     return -1;
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
/*     */   public static long min(long... array) {
/* 194 */     Preconditions.checkArgument((array.length > 0));
/* 195 */     long min = array[0];
/* 196 */     for (int i = 1; i < array.length; i++) {
/* 197 */       if (array[i] < min) {
/* 198 */         min = array[i];
/*     */       }
/*     */     } 
/* 201 */     return min;
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
/*     */   public static long max(long... array) {
/* 213 */     Preconditions.checkArgument((array.length > 0));
/* 214 */     long max = array[0];
/* 215 */     for (int i = 1; i < array.length; i++) {
/* 216 */       if (array[i] > max) {
/* 217 */         max = array[i];
/*     */       }
/*     */     } 
/* 220 */     return max;
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
/*     */   public static long[] concat(long[]... arrays) {
/* 233 */     int length = 0;
/* 234 */     for (long[] array : arrays) {
/* 235 */       length += array.length;
/*     */     }
/* 237 */     long[] result = new long[length];
/* 238 */     int pos = 0;
/* 239 */     for (long[] array : arrays) {
/* 240 */       System.arraycopy(array, 0, result, pos, array.length);
/* 241 */       pos += array.length;
/*     */     } 
/* 243 */     return result;
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
/*     */   @GwtIncompatible("doesn't work")
/*     */   public static byte[] toByteArray(long value) {
/* 259 */     return new byte[] { (byte)(int)(value >> 56L), (byte)(int)(value >> 48L), (byte)(int)(value >> 40L), (byte)(int)(value >> 32L), (byte)(int)(value >> 24L), (byte)(int)(value >> 16L), (byte)(int)(value >> 8L), (byte)(int)value };
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("doesn't work")
/*     */   public static long fromByteArray(byte[] bytes) {
/* 285 */     Preconditions.checkArgument((bytes.length >= 8), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(8) });
/*     */     
/* 287 */     return fromBytes(bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]);
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
/*     */   @GwtIncompatible("doesn't work")
/*     */   public static long fromBytes(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
/* 301 */     return (b1 & 0xFFL) << 56L | (b2 & 0xFFL) << 48L | (b3 & 0xFFL) << 40L | (b4 & 0xFFL) << 32L | (b5 & 0xFFL) << 24L | (b6 & 0xFFL) << 16L | (b7 & 0xFFL) << 8L | b8 & 0xFFL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long[] ensureCapacity(long[] array, int minLength, int padding) {
/* 329 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 330 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 331 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long[] copyOf(long[] original, int length) {
/* 338 */     long[] copy = new long[length];
/* 339 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 340 */     return copy;
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
/*     */   public static String join(String separator, long... array) {
/* 353 */     Preconditions.checkNotNull(separator);
/* 354 */     if (array.length == 0) {
/* 355 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 359 */     StringBuilder builder = new StringBuilder(array.length * 10);
/* 360 */     builder.append(array[0]);
/* 361 */     for (int i = 1; i < array.length; i++) {
/* 362 */       builder.append(separator).append(array[i]);
/*     */     }
/* 364 */     return builder.toString();
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
/*     */   public static Comparator<long[]> lexicographicalComparator() {
/* 384 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<long[]> {
/* 388 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(long[] left, long[] right) {
/* 392 */       int minLength = Math.min(left.length, right.length);
/* 393 */       for (int i = 0; i < minLength; i++) {
/* 394 */         int result = Longs.compare(left[i], right[i]);
/* 395 */         if (result != 0) {
/* 396 */           return result;
/*     */         }
/*     */       } 
/* 399 */       return left.length - right.length;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long[] toArray(Collection<Long> collection) {
/* 418 */     if (collection instanceof LongArrayAsList) {
/* 419 */       return ((LongArrayAsList)collection).toLongArray();
/*     */     }
/*     */     
/* 422 */     Object[] boxedArray = collection.toArray();
/* 423 */     int len = boxedArray.length;
/* 424 */     long[] array = new long[len];
/* 425 */     for (int i = 0; i < len; i++)
/*     */     {
/* 427 */       array[i] = ((Long)Preconditions.checkNotNull(boxedArray[i])).longValue();
/*     */     }
/* 429 */     return array;
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
/*     */   public static List<Long> asList(long... backingArray) {
/* 447 */     if (backingArray.length == 0) {
/* 448 */       return Collections.emptyList();
/*     */     }
/* 450 */     return new LongArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class LongArrayAsList extends AbstractList<Long> implements RandomAccess, Serializable {
/*     */     final long[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     LongArrayAsList(long[] array) {
/* 461 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     LongArrayAsList(long[] array, int start, int end) {
/* 465 */       this.array = array;
/* 466 */       this.start = start;
/* 467 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 471 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 475 */       return false;
/*     */     }
/*     */     
/*     */     public Long get(int index) {
/* 479 */       Preconditions.checkElementIndex(index, size());
/* 480 */       return Long.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 485 */       return (target instanceof Long && Longs.indexOf(this.array, ((Long)target).longValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 491 */       if (target instanceof Long) {
/* 492 */         int i = Longs.indexOf(this.array, ((Long)target).longValue(), this.start, this.end);
/* 493 */         if (i >= 0) {
/* 494 */           return i - this.start;
/*     */         }
/*     */       } 
/* 497 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 502 */       if (target instanceof Long) {
/* 503 */         int i = Longs.lastIndexOf(this.array, ((Long)target).longValue(), this.start, this.end);
/* 504 */         if (i >= 0) {
/* 505 */           return i - this.start;
/*     */         }
/*     */       } 
/* 508 */       return -1;
/*     */     }
/*     */     
/*     */     public Long set(int index, Long element) {
/* 512 */       Preconditions.checkElementIndex(index, size());
/* 513 */       long oldValue = this.array[this.start + index];
/* 514 */       this.array[this.start + index] = ((Long)Preconditions.checkNotNull(element)).longValue();
/* 515 */       return Long.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Long> subList(int fromIndex, int toIndex) {
/* 519 */       int size = size();
/* 520 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 521 */       if (fromIndex == toIndex) {
/* 522 */         return Collections.emptyList();
/*     */       }
/* 524 */       return new LongArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 528 */       if (object == this) {
/* 529 */         return true;
/*     */       }
/* 531 */       if (object instanceof LongArrayAsList) {
/* 532 */         LongArrayAsList that = (LongArrayAsList)object;
/* 533 */         int size = size();
/* 534 */         if (that.size() != size) {
/* 535 */           return false;
/*     */         }
/* 537 */         for (int i = 0; i < size; i++) {
/* 538 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 539 */             return false;
/*     */           }
/*     */         } 
/* 542 */         return true;
/*     */       } 
/* 544 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 548 */       int result = 1;
/* 549 */       for (int i = this.start; i < this.end; i++) {
/* 550 */         result = 31 * result + Longs.hashCode(this.array[i]);
/*     */       }
/* 552 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 556 */       StringBuilder builder = new StringBuilder(size() * 10);
/* 557 */       builder.append('[').append(this.array[this.start]);
/* 558 */       for (int i = this.start + 1; i < this.end; i++) {
/* 559 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 561 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     long[] toLongArray() {
/* 566 */       int size = size();
/* 567 */       long[] result = new long[size];
/* 568 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 569 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\Longs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */