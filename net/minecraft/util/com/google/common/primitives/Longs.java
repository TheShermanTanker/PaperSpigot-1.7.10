/*     */ package net.minecraft.util.com.google.common.primitives;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.base.Converter;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Longs
/*     */ {
/*     */   public static final int BYTES = 8;
/*     */   public static final long MAX_POWER_OF_TWO = 4611686018427387904L;
/*     */   
/*     */   public static int hashCode(long value) {
/*  78 */     return (int)(value ^ value >>> 32L);
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
/*     */   public static int compare(long a, long b) {
/*  95 */     return (a < b) ? -1 : ((a > b) ? 1 : 0);
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
/* 108 */     for (long value : array) {
/* 109 */       if (value == target) {
/* 110 */         return true;
/*     */       }
/*     */     } 
/* 113 */     return false;
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
/* 126 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(long[] array, long target, int start, int end) {
/* 132 */     for (int i = start; i < end; i++) {
/* 133 */       if (array[i] == target) {
/* 134 */         return i;
/*     */       }
/*     */     } 
/* 137 */     return -1;
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
/* 152 */     Preconditions.checkNotNull(array, "array");
/* 153 */     Preconditions.checkNotNull(target, "target");
/* 154 */     if (target.length == 0) {
/* 155 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 159 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 160 */       int j = 0; while (true) { if (j < target.length) {
/* 161 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 165 */         return i; }
/*     */     
/* 167 */     }  return -1;
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
/* 180 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(long[] array, long target, int start, int end) {
/* 186 */     for (int i = end - 1; i >= start; i--) {
/* 187 */       if (array[i] == target) {
/* 188 */         return i;
/*     */       }
/*     */     } 
/* 191 */     return -1;
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
/* 203 */     Preconditions.checkArgument((array.length > 0));
/* 204 */     long min = array[0];
/* 205 */     for (int i = 1; i < array.length; i++) {
/* 206 */       if (array[i] < min) {
/* 207 */         min = array[i];
/*     */       }
/*     */     } 
/* 210 */     return min;
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
/* 222 */     Preconditions.checkArgument((array.length > 0));
/* 223 */     long max = array[0];
/* 224 */     for (int i = 1; i < array.length; i++) {
/* 225 */       if (array[i] > max) {
/* 226 */         max = array[i];
/*     */       }
/*     */     } 
/* 229 */     return max;
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
/* 242 */     int length = 0;
/* 243 */     for (long[] array : arrays) {
/* 244 */       length += array.length;
/*     */     }
/* 246 */     long[] result = new long[length];
/* 247 */     int pos = 0;
/* 248 */     for (long[] array : arrays) {
/* 249 */       System.arraycopy(array, 0, result, pos, array.length);
/* 250 */       pos += array.length;
/*     */     } 
/* 252 */     return result;
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
/*     */   public static byte[] toByteArray(long value) {
/* 269 */     byte[] result = new byte[8];
/* 270 */     for (int i = 7; i >= 0; i--) {
/* 271 */       result[i] = (byte)(int)(value & 0xFFL);
/* 272 */       value >>= 8L;
/*     */     } 
/* 274 */     return result;
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
/*     */   public static long fromByteArray(byte[] bytes) {
/* 291 */     Preconditions.checkArgument((bytes.length >= 8), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(8) });
/*     */     
/* 293 */     return fromBytes(bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]);
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
/*     */   public static long fromBytes(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
/* 306 */     return (b1 & 0xFFL) << 56L | (b2 & 0xFFL) << 48L | (b3 & 0xFFL) << 40L | (b4 & 0xFFL) << 32L | (b5 & 0xFFL) << 24L | (b6 & 0xFFL) << 16L | (b7 & 0xFFL) << 8L | b8 & 0xFFL;
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
/*     */   
/*     */   @Beta
/*     */   public static Long tryParse(String string) {
/* 336 */     if (((String)Preconditions.checkNotNull(string)).isEmpty()) {
/* 337 */       return null;
/*     */     }
/* 339 */     boolean negative = (string.charAt(0) == '-');
/* 340 */     int index = negative ? 1 : 0;
/* 341 */     if (index == string.length()) {
/* 342 */       return null;
/*     */     }
/* 344 */     int digit = string.charAt(index++) - 48;
/* 345 */     if (digit < 0 || digit > 9) {
/* 346 */       return null;
/*     */     }
/* 348 */     long accum = -digit;
/* 349 */     while (index < string.length()) {
/* 350 */       digit = string.charAt(index++) - 48;
/* 351 */       if (digit < 0 || digit > 9 || accum < -922337203685477580L) {
/* 352 */         return null;
/*     */       }
/* 354 */       accum *= 10L;
/* 355 */       if (accum < Long.MIN_VALUE + digit) {
/* 356 */         return null;
/*     */       }
/* 358 */       accum -= digit;
/*     */     } 
/*     */     
/* 361 */     if (negative)
/* 362 */       return Long.valueOf(accum); 
/* 363 */     if (accum == Long.MIN_VALUE) {
/* 364 */       return null;
/*     */     }
/* 366 */     return Long.valueOf(-accum);
/*     */   }
/*     */   
/*     */   private static final class LongConverter
/*     */     extends Converter<String, Long> implements Serializable {
/* 371 */     static final LongConverter INSTANCE = new LongConverter();
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected Long doForward(String value) {
/* 375 */       return Long.decode(value);
/*     */     }
/*     */ 
/*     */     
/*     */     protected String doBackward(Long value) {
/* 380 */       return value.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 385 */       return "Longs.stringConverter()";
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 389 */       return INSTANCE;
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
/*     */   @Beta
/*     */   public static Converter<String, Long> stringConverter() {
/* 402 */     return LongConverter.INSTANCE;
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
/*     */   public static long[] ensureCapacity(long[] array, int minLength, int padding) {
/* 423 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 424 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 425 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long[] copyOf(long[] original, int length) {
/* 432 */     long[] copy = new long[length];
/* 433 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 434 */     return copy;
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
/* 447 */     Preconditions.checkNotNull(separator);
/* 448 */     if (array.length == 0) {
/* 449 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 453 */     StringBuilder builder = new StringBuilder(array.length * 10);
/* 454 */     builder.append(array[0]);
/* 455 */     for (int i = 1; i < array.length; i++) {
/* 456 */       builder.append(separator).append(array[i]);
/*     */     }
/* 458 */     return builder.toString();
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
/* 478 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<long[]> {
/* 482 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(long[] left, long[] right) {
/* 486 */       int minLength = Math.min(left.length, right.length);
/* 487 */       for (int i = 0; i < minLength; i++) {
/* 488 */         int result = Longs.compare(left[i], right[i]);
/* 489 */         if (result != 0) {
/* 490 */           return result;
/*     */         }
/*     */       } 
/* 493 */       return left.length - right.length;
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
/*     */   
/*     */   public static long[] toArray(Collection<? extends Number> collection) {
/* 513 */     if (collection instanceof LongArrayAsList) {
/* 514 */       return ((LongArrayAsList)collection).toLongArray();
/*     */     }
/*     */     
/* 517 */     Object[] boxedArray = collection.toArray();
/* 518 */     int len = boxedArray.length;
/* 519 */     long[] array = new long[len];
/* 520 */     for (int i = 0; i < len; i++)
/*     */     {
/* 522 */       array[i] = ((Number)Preconditions.checkNotNull(boxedArray[i])).longValue();
/*     */     }
/* 524 */     return array;
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
/* 542 */     if (backingArray.length == 0) {
/* 543 */       return Collections.emptyList();
/*     */     }
/* 545 */     return new LongArrayAsList(backingArray);
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
/* 556 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     LongArrayAsList(long[] array, int start, int end) {
/* 560 */       this.array = array;
/* 561 */       this.start = start;
/* 562 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 566 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 570 */       return false;
/*     */     }
/*     */     
/*     */     public Long get(int index) {
/* 574 */       Preconditions.checkElementIndex(index, size());
/* 575 */       return Long.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 580 */       return (target instanceof Long && Longs.indexOf(this.array, ((Long)target).longValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 586 */       if (target instanceof Long) {
/* 587 */         int i = Longs.indexOf(this.array, ((Long)target).longValue(), this.start, this.end);
/* 588 */         if (i >= 0) {
/* 589 */           return i - this.start;
/*     */         }
/*     */       } 
/* 592 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 597 */       if (target instanceof Long) {
/* 598 */         int i = Longs.lastIndexOf(this.array, ((Long)target).longValue(), this.start, this.end);
/* 599 */         if (i >= 0) {
/* 600 */           return i - this.start;
/*     */         }
/*     */       } 
/* 603 */       return -1;
/*     */     }
/*     */     
/*     */     public Long set(int index, Long element) {
/* 607 */       Preconditions.checkElementIndex(index, size());
/* 608 */       long oldValue = this.array[this.start + index];
/*     */       
/* 610 */       this.array[this.start + index] = ((Long)Preconditions.checkNotNull(element)).longValue();
/* 611 */       return Long.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Long> subList(int fromIndex, int toIndex) {
/* 615 */       int size = size();
/* 616 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 617 */       if (fromIndex == toIndex) {
/* 618 */         return Collections.emptyList();
/*     */       }
/* 620 */       return new LongArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 624 */       if (object == this) {
/* 625 */         return true;
/*     */       }
/* 627 */       if (object instanceof LongArrayAsList) {
/* 628 */         LongArrayAsList that = (LongArrayAsList)object;
/* 629 */         int size = size();
/* 630 */         if (that.size() != size) {
/* 631 */           return false;
/*     */         }
/* 633 */         for (int i = 0; i < size; i++) {
/* 634 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 635 */             return false;
/*     */           }
/*     */         } 
/* 638 */         return true;
/*     */       } 
/* 640 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 644 */       int result = 1;
/* 645 */       for (int i = this.start; i < this.end; i++) {
/* 646 */         result = 31 * result + Longs.hashCode(this.array[i]);
/*     */       }
/* 648 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 652 */       StringBuilder builder = new StringBuilder(size() * 10);
/* 653 */       builder.append('[').append(this.array[this.start]);
/* 654 */       for (int i = this.start + 1; i < this.end; i++) {
/* 655 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 657 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     long[] toLongArray() {
/* 662 */       int size = size();
/* 663 */       long[] result = new long[size];
/* 664 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 665 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\primitives\Longs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */