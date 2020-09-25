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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Ints
/*     */ {
/*     */   public static final int BYTES = 4;
/*     */   public static final int MAX_POWER_OF_TWO = 1073741824;
/*     */   
/*     */   public static int hashCode(int value) {
/*  68 */     return value;
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
/*     */   public static int checkedCast(long value) {
/*  80 */     int result = (int)value;
/*  81 */     Preconditions.checkArgument((result == value), "Out of range: %s", new Object[] { Long.valueOf(value) });
/*  82 */     return result;
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
/*     */   public static int saturatedCast(long value) {
/*  94 */     if (value > 2147483647L) {
/*  95 */       return Integer.MAX_VALUE;
/*     */     }
/*  97 */     if (value < -2147483648L) {
/*  98 */       return Integer.MIN_VALUE;
/*     */     }
/* 100 */     return (int)value;
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
/*     */   public static int compare(int a, int b) {
/* 113 */     return (a < b) ? -1 : ((a > b) ? 1 : 0);
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
/*     */   public static boolean contains(int[] array, int target) {
/* 126 */     for (int value : array) {
/* 127 */       if (value == target) {
/* 128 */         return true;
/*     */       }
/*     */     } 
/* 131 */     return false;
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
/*     */   public static int indexOf(int[] array, int target) {
/* 144 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(int[] array, int target, int start, int end) {
/* 150 */     for (int i = start; i < end; i++) {
/* 151 */       if (array[i] == target) {
/* 152 */         return i;
/*     */       }
/*     */     } 
/* 155 */     return -1;
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
/*     */   public static int indexOf(int[] array, int[] target) {
/* 170 */     Preconditions.checkNotNull(array, "array");
/* 171 */     Preconditions.checkNotNull(target, "target");
/* 172 */     if (target.length == 0) {
/* 173 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 177 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 178 */       int j = 0; while (true) { if (j < target.length) {
/* 179 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 183 */         return i; }
/*     */     
/* 185 */     }  return -1;
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
/*     */   public static int lastIndexOf(int[] array, int target) {
/* 198 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(int[] array, int target, int start, int end) {
/* 204 */     for (int i = end - 1; i >= start; i--) {
/* 205 */       if (array[i] == target) {
/* 206 */         return i;
/*     */       }
/*     */     } 
/* 209 */     return -1;
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
/*     */   public static int min(int... array) {
/* 221 */     Preconditions.checkArgument((array.length > 0));
/* 222 */     int min = array[0];
/* 223 */     for (int i = 1; i < array.length; i++) {
/* 224 */       if (array[i] < min) {
/* 225 */         min = array[i];
/*     */       }
/*     */     } 
/* 228 */     return min;
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
/*     */   public static int max(int... array) {
/* 240 */     Preconditions.checkArgument((array.length > 0));
/* 241 */     int max = array[0];
/* 242 */     for (int i = 1; i < array.length; i++) {
/* 243 */       if (array[i] > max) {
/* 244 */         max = array[i];
/*     */       }
/*     */     } 
/* 247 */     return max;
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
/*     */   public static int[] concat(int[]... arrays) {
/* 260 */     int length = 0;
/* 261 */     for (int[] array : arrays) {
/* 262 */       length += array.length;
/*     */     }
/* 264 */     int[] result = new int[length];
/* 265 */     int pos = 0;
/* 266 */     for (int[] array : arrays) {
/* 267 */       System.arraycopy(array, 0, result, pos, array.length);
/* 268 */       pos += array.length;
/*     */     } 
/* 270 */     return result;
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
/*     */   public static byte[] toByteArray(int value) {
/* 286 */     return new byte[] { (byte)(value >> 24), (byte)(value >> 16), (byte)(value >> 8), (byte)value };
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
/*     */   @GwtIncompatible("doesn't work")
/*     */   public static int fromByteArray(byte[] bytes) {
/* 307 */     Preconditions.checkArgument((bytes.length >= 4), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(4) });
/*     */     
/* 309 */     return fromBytes(bytes[0], bytes[1], bytes[2], bytes[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("doesn't work")
/*     */   public static int fromBytes(byte b1, byte b2, byte b3, byte b4) {
/* 321 */     return b1 << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8 | b4 & 0xFF;
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
/*     */   public static int[] ensureCapacity(int[] array, int minLength, int padding) {
/* 342 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 343 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 344 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] copyOf(int[] original, int length) {
/* 351 */     int[] copy = new int[length];
/* 352 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 353 */     return copy;
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
/*     */   public static String join(String separator, int... array) {
/* 366 */     Preconditions.checkNotNull(separator);
/* 367 */     if (array.length == 0) {
/* 368 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 372 */     StringBuilder builder = new StringBuilder(array.length * 5);
/* 373 */     builder.append(array[0]);
/* 374 */     for (int i = 1; i < array.length; i++) {
/* 375 */       builder.append(separator).append(array[i]);
/*     */     }
/* 377 */     return builder.toString();
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
/*     */   public static Comparator<int[]> lexicographicalComparator() {
/* 396 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<int[]> {
/* 400 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(int[] left, int[] right) {
/* 404 */       int minLength = Math.min(left.length, right.length);
/* 405 */       for (int i = 0; i < minLength; i++) {
/* 406 */         int result = Ints.compare(left[i], right[i]);
/* 407 */         if (result != 0) {
/* 408 */           return result;
/*     */         }
/*     */       } 
/* 411 */       return left.length - right.length;
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
/*     */   public static int[] toArray(Collection<Integer> collection) {
/* 430 */     if (collection instanceof IntArrayAsList) {
/* 431 */       return ((IntArrayAsList)collection).toIntArray();
/*     */     }
/*     */     
/* 434 */     Object[] boxedArray = collection.toArray();
/* 435 */     int len = boxedArray.length;
/* 436 */     int[] array = new int[len];
/* 437 */     for (int i = 0; i < len; i++)
/*     */     {
/* 439 */       array[i] = ((Integer)Preconditions.checkNotNull(boxedArray[i])).intValue();
/*     */     }
/* 441 */     return array;
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
/*     */   public static List<Integer> asList(int... backingArray) {
/* 459 */     if (backingArray.length == 0) {
/* 460 */       return Collections.emptyList();
/*     */     }
/* 462 */     return new IntArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class IntArrayAsList extends AbstractList<Integer> implements RandomAccess, Serializable {
/*     */     final int[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     IntArrayAsList(int[] array) {
/* 473 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     IntArrayAsList(int[] array, int start, int end) {
/* 477 */       this.array = array;
/* 478 */       this.start = start;
/* 479 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 483 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 487 */       return false;
/*     */     }
/*     */     
/*     */     public Integer get(int index) {
/* 491 */       Preconditions.checkElementIndex(index, size());
/* 492 */       return Integer.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 497 */       return (target instanceof Integer && Ints.indexOf(this.array, ((Integer)target).intValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 503 */       if (target instanceof Integer) {
/* 504 */         int i = Ints.indexOf(this.array, ((Integer)target).intValue(), this.start, this.end);
/* 505 */         if (i >= 0) {
/* 506 */           return i - this.start;
/*     */         }
/*     */       } 
/* 509 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 514 */       if (target instanceof Integer) {
/* 515 */         int i = Ints.lastIndexOf(this.array, ((Integer)target).intValue(), this.start, this.end);
/* 516 */         if (i >= 0) {
/* 517 */           return i - this.start;
/*     */         }
/*     */       } 
/* 520 */       return -1;
/*     */     }
/*     */     
/*     */     public Integer set(int index, Integer element) {
/* 524 */       Preconditions.checkElementIndex(index, size());
/* 525 */       int oldValue = this.array[this.start + index];
/* 526 */       this.array[this.start + index] = ((Integer)Preconditions.checkNotNull(element)).intValue();
/* 527 */       return Integer.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Integer> subList(int fromIndex, int toIndex) {
/* 531 */       int size = size();
/* 532 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 533 */       if (fromIndex == toIndex) {
/* 534 */         return Collections.emptyList();
/*     */       }
/* 536 */       return new IntArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 540 */       if (object == this) {
/* 541 */         return true;
/*     */       }
/* 543 */       if (object instanceof IntArrayAsList) {
/* 544 */         IntArrayAsList that = (IntArrayAsList)object;
/* 545 */         int size = size();
/* 546 */         if (that.size() != size) {
/* 547 */           return false;
/*     */         }
/* 549 */         for (int i = 0; i < size; i++) {
/* 550 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 551 */             return false;
/*     */           }
/*     */         } 
/* 554 */         return true;
/*     */       } 
/* 556 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 560 */       int result = 1;
/* 561 */       for (int i = this.start; i < this.end; i++) {
/* 562 */         result = 31 * result + Ints.hashCode(this.array[i]);
/*     */       }
/* 564 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 568 */       StringBuilder builder = new StringBuilder(size() * 5);
/* 569 */       builder.append('[').append(this.array[this.start]);
/* 570 */       for (int i = this.start + 1; i < this.end; i++) {
/* 571 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 573 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     int[] toIntArray() {
/* 578 */       int size = size();
/* 579 */       int[] result = new int[size];
/* 580 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 581 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\Ints.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */