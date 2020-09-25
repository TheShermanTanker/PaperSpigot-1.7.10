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
/*     */ public final class Shorts
/*     */ {
/*     */   public static final int BYTES = 2;
/*     */   public static final short MAX_POWER_OF_TWO = 16384;
/*     */   
/*     */   public static int hashCode(short value) {
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
/*     */   
/*     */   public static short checkedCast(long value) {
/*  81 */     short result = (short)(int)value;
/*  82 */     Preconditions.checkArgument((result == value), "Out of range: %s", new Object[] { Long.valueOf(value) });
/*  83 */     return result;
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
/*     */   public static short saturatedCast(long value) {
/*  95 */     if (value > 32767L) {
/*  96 */       return Short.MAX_VALUE;
/*     */     }
/*  98 */     if (value < -32768L) {
/*  99 */       return Short.MIN_VALUE;
/*     */     }
/* 101 */     return (short)(int)value;
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
/*     */   public static int compare(short a, short b) {
/* 114 */     return a - b;
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
/*     */   public static boolean contains(short[] array, short target) {
/* 127 */     for (short value : array) {
/* 128 */       if (value == target) {
/* 129 */         return true;
/*     */       }
/*     */     } 
/* 132 */     return false;
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
/*     */   public static int indexOf(short[] array, short target) {
/* 145 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(short[] array, short target, int start, int end) {
/* 151 */     for (int i = start; i < end; i++) {
/* 152 */       if (array[i] == target) {
/* 153 */         return i;
/*     */       }
/*     */     } 
/* 156 */     return -1;
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
/*     */   public static int indexOf(short[] array, short[] target) {
/* 171 */     Preconditions.checkNotNull(array, "array");
/* 172 */     Preconditions.checkNotNull(target, "target");
/* 173 */     if (target.length == 0) {
/* 174 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 178 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 179 */       int j = 0; while (true) { if (j < target.length) {
/* 180 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 184 */         return i; }
/*     */     
/* 186 */     }  return -1;
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
/*     */   public static int lastIndexOf(short[] array, short target) {
/* 199 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(short[] array, short target, int start, int end) {
/* 205 */     for (int i = end - 1; i >= start; i--) {
/* 206 */       if (array[i] == target) {
/* 207 */         return i;
/*     */       }
/*     */     } 
/* 210 */     return -1;
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
/*     */   public static short min(short... array) {
/* 222 */     Preconditions.checkArgument((array.length > 0));
/* 223 */     short min = array[0];
/* 224 */     for (int i = 1; i < array.length; i++) {
/* 225 */       if (array[i] < min) {
/* 226 */         min = array[i];
/*     */       }
/*     */     } 
/* 229 */     return min;
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
/*     */   public static short max(short... array) {
/* 241 */     Preconditions.checkArgument((array.length > 0));
/* 242 */     short max = array[0];
/* 243 */     for (int i = 1; i < array.length; i++) {
/* 244 */       if (array[i] > max) {
/* 245 */         max = array[i];
/*     */       }
/*     */     } 
/* 248 */     return max;
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
/*     */   public static short[] concat(short[]... arrays) {
/* 261 */     int length = 0;
/* 262 */     for (short[] array : arrays) {
/* 263 */       length += array.length;
/*     */     }
/* 265 */     short[] result = new short[length];
/* 266 */     int pos = 0;
/* 267 */     for (short[] array : arrays) {
/* 268 */       System.arraycopy(array, 0, result, pos, array.length);
/* 269 */       pos += array.length;
/*     */     } 
/* 271 */     return result;
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
/*     */   @GwtIncompatible("doesn't work")
/*     */   public static byte[] toByteArray(short value) {
/* 288 */     return new byte[] { (byte)(value >> 8), (byte)value };
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
/*     */   @GwtIncompatible("doesn't work")
/*     */   public static short fromByteArray(byte[] bytes) {
/* 307 */     Preconditions.checkArgument((bytes.length >= 2), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(2) });
/*     */     
/* 309 */     return fromBytes(bytes[0], bytes[1]);
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
/*     */   public static short fromBytes(byte b1, byte b2) {
/* 321 */     return (short)(b1 << 8 | b2 & 0xFF);
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
/*     */   public static short[] ensureCapacity(short[] array, int minLength, int padding) {
/* 342 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 343 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 344 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static short[] copyOf(short[] original, int length) {
/* 351 */     short[] copy = new short[length];
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
/*     */   public static String join(String separator, short... array) {
/* 366 */     Preconditions.checkNotNull(separator);
/* 367 */     if (array.length == 0) {
/* 368 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 372 */     StringBuilder builder = new StringBuilder(array.length * 6);
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
/*     */   
/*     */   public static Comparator<short[]> lexicographicalComparator() {
/* 397 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<short[]> {
/* 401 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(short[] left, short[] right) {
/* 405 */       int minLength = Math.min(left.length, right.length);
/* 406 */       for (int i = 0; i < minLength; i++) {
/* 407 */         int result = Shorts.compare(left[i], right[i]);
/* 408 */         if (result != 0) {
/* 409 */           return result;
/*     */         }
/*     */       } 
/* 412 */       return left.length - right.length;
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
/*     */   public static short[] toArray(Collection<Short> collection) {
/* 431 */     if (collection instanceof ShortArrayAsList) {
/* 432 */       return ((ShortArrayAsList)collection).toShortArray();
/*     */     }
/*     */     
/* 435 */     Object[] boxedArray = collection.toArray();
/* 436 */     int len = boxedArray.length;
/* 437 */     short[] array = new short[len];
/* 438 */     for (int i = 0; i < len; i++)
/*     */     {
/* 440 */       array[i] = ((Short)Preconditions.checkNotNull(boxedArray[i])).shortValue();
/*     */     }
/* 442 */     return array;
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
/*     */   public static List<Short> asList(short... backingArray) {
/* 460 */     if (backingArray.length == 0) {
/* 461 */       return Collections.emptyList();
/*     */     }
/* 463 */     return new ShortArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class ShortArrayAsList extends AbstractList<Short> implements RandomAccess, Serializable {
/*     */     final short[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ShortArrayAsList(short[] array) {
/* 474 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     ShortArrayAsList(short[] array, int start, int end) {
/* 478 */       this.array = array;
/* 479 */       this.start = start;
/* 480 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 484 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 488 */       return false;
/*     */     }
/*     */     
/*     */     public Short get(int index) {
/* 492 */       Preconditions.checkElementIndex(index, size());
/* 493 */       return Short.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 498 */       return (target instanceof Short && Shorts.indexOf(this.array, ((Short)target).shortValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 504 */       if (target instanceof Short) {
/* 505 */         int i = Shorts.indexOf(this.array, ((Short)target).shortValue(), this.start, this.end);
/* 506 */         if (i >= 0) {
/* 507 */           return i - this.start;
/*     */         }
/*     */       } 
/* 510 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 515 */       if (target instanceof Short) {
/* 516 */         int i = Shorts.lastIndexOf(this.array, ((Short)target).shortValue(), this.start, this.end);
/* 517 */         if (i >= 0) {
/* 518 */           return i - this.start;
/*     */         }
/*     */       } 
/* 521 */       return -1;
/*     */     }
/*     */     
/*     */     public Short set(int index, Short element) {
/* 525 */       Preconditions.checkElementIndex(index, size());
/* 526 */       short oldValue = this.array[this.start + index];
/* 527 */       this.array[this.start + index] = ((Short)Preconditions.checkNotNull(element)).shortValue();
/* 528 */       return Short.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Short> subList(int fromIndex, int toIndex) {
/* 532 */       int size = size();
/* 533 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 534 */       if (fromIndex == toIndex) {
/* 535 */         return Collections.emptyList();
/*     */       }
/* 537 */       return new ShortArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 541 */       if (object == this) {
/* 542 */         return true;
/*     */       }
/* 544 */       if (object instanceof ShortArrayAsList) {
/* 545 */         ShortArrayAsList that = (ShortArrayAsList)object;
/* 546 */         int size = size();
/* 547 */         if (that.size() != size) {
/* 548 */           return false;
/*     */         }
/* 550 */         for (int i = 0; i < size; i++) {
/* 551 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 552 */             return false;
/*     */           }
/*     */         } 
/* 555 */         return true;
/*     */       } 
/* 557 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 561 */       int result = 1;
/* 562 */       for (int i = this.start; i < this.end; i++) {
/* 563 */         result = 31 * result + Shorts.hashCode(this.array[i]);
/*     */       }
/* 565 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 569 */       StringBuilder builder = new StringBuilder(size() * 6);
/* 570 */       builder.append('[').append(this.array[this.start]);
/* 571 */       for (int i = this.start + 1; i < this.end; i++) {
/* 572 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 574 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     short[] toShortArray() {
/* 579 */       int size = size();
/* 580 */       short[] result = new short[size];
/* 581 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 582 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\Shorts.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */