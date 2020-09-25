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
/*     */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Shorts
/*     */ {
/*     */   public static final int BYTES = 2;
/*     */   public static final short MAX_POWER_OF_TWO = 16384;
/*     */   
/*     */   public static int hashCode(short value) {
/*  74 */     return value;
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
/*  87 */     short result = (short)(int)value;
/*  88 */     if (result != value)
/*     */     {
/*  90 */       throw new IllegalArgumentException("Out of range: " + value);
/*     */     }
/*  92 */     return result;
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
/* 104 */     if (value > 32767L) {
/* 105 */       return Short.MAX_VALUE;
/*     */     }
/* 107 */     if (value < -32768L) {
/* 108 */       return Short.MIN_VALUE;
/*     */     }
/* 110 */     return (short)(int)value;
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
/*     */   public static int compare(short a, short b) {
/* 127 */     return a - b;
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
/* 140 */     for (short value : array) {
/* 141 */       if (value == target) {
/* 142 */         return true;
/*     */       }
/*     */     } 
/* 145 */     return false;
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
/* 158 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(short[] array, short target, int start, int end) {
/* 164 */     for (int i = start; i < end; i++) {
/* 165 */       if (array[i] == target) {
/* 166 */         return i;
/*     */       }
/*     */     } 
/* 169 */     return -1;
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
/* 184 */     Preconditions.checkNotNull(array, "array");
/* 185 */     Preconditions.checkNotNull(target, "target");
/* 186 */     if (target.length == 0) {
/* 187 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 191 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 192 */       int j = 0; while (true) { if (j < target.length) {
/* 193 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 197 */         return i; }
/*     */     
/* 199 */     }  return -1;
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
/* 212 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(short[] array, short target, int start, int end) {
/* 218 */     for (int i = end - 1; i >= start; i--) {
/* 219 */       if (array[i] == target) {
/* 220 */         return i;
/*     */       }
/*     */     } 
/* 223 */     return -1;
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
/* 235 */     Preconditions.checkArgument((array.length > 0));
/* 236 */     short min = array[0];
/* 237 */     for (int i = 1; i < array.length; i++) {
/* 238 */       if (array[i] < min) {
/* 239 */         min = array[i];
/*     */       }
/*     */     } 
/* 242 */     return min;
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
/* 254 */     Preconditions.checkArgument((array.length > 0));
/* 255 */     short max = array[0];
/* 256 */     for (int i = 1; i < array.length; i++) {
/* 257 */       if (array[i] > max) {
/* 258 */         max = array[i];
/*     */       }
/*     */     } 
/* 261 */     return max;
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
/* 274 */     int length = 0;
/* 275 */     for (short[] array : arrays) {
/* 276 */       length += array.length;
/*     */     }
/* 278 */     short[] result = new short[length];
/* 279 */     int pos = 0;
/* 280 */     for (short[] array : arrays) {
/* 281 */       System.arraycopy(array, 0, result, pos, array.length);
/* 282 */       pos += array.length;
/*     */     } 
/* 284 */     return result;
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
/* 301 */     return new byte[] { (byte)(value >> 8), (byte)value };
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
/* 320 */     Preconditions.checkArgument((bytes.length >= 2), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(2) });
/*     */     
/* 322 */     return fromBytes(bytes[0], bytes[1]);
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
/* 334 */     return (short)(b1 << 8 | b2 & 0xFF);
/*     */   }
/*     */   
/*     */   private static final class ShortConverter
/*     */     extends Converter<String, Short> implements Serializable {
/* 339 */     static final ShortConverter INSTANCE = new ShortConverter();
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected Short doForward(String value) {
/* 343 */       return Short.decode(value);
/*     */     }
/*     */ 
/*     */     
/*     */     protected String doBackward(Short value) {
/* 348 */       return value.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 353 */       return "Shorts.stringConverter()";
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 357 */       return INSTANCE;
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
/*     */   public static Converter<String, Short> stringConverter() {
/* 370 */     return ShortConverter.INSTANCE;
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
/* 391 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 392 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 393 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static short[] copyOf(short[] original, int length) {
/* 400 */     short[] copy = new short[length];
/* 401 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 402 */     return copy;
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
/* 415 */     Preconditions.checkNotNull(separator);
/* 416 */     if (array.length == 0) {
/* 417 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 421 */     StringBuilder builder = new StringBuilder(array.length * 6);
/* 422 */     builder.append(array[0]);
/* 423 */     for (int i = 1; i < array.length; i++) {
/* 424 */       builder.append(separator).append(array[i]);
/*     */     }
/* 426 */     return builder.toString();
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
/* 446 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<short[]> {
/* 450 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(short[] left, short[] right) {
/* 454 */       int minLength = Math.min(left.length, right.length);
/* 455 */       for (int i = 0; i < minLength; i++) {
/* 456 */         int result = Shorts.compare(left[i], right[i]);
/* 457 */         if (result != 0) {
/* 458 */           return result;
/*     */         }
/*     */       } 
/* 461 */       return left.length - right.length;
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
/*     */   public static short[] toArray(Collection<? extends Number> collection) {
/* 481 */     if (collection instanceof ShortArrayAsList) {
/* 482 */       return ((ShortArrayAsList)collection).toShortArray();
/*     */     }
/*     */     
/* 485 */     Object[] boxedArray = collection.toArray();
/* 486 */     int len = boxedArray.length;
/* 487 */     short[] array = new short[len];
/* 488 */     for (int i = 0; i < len; i++)
/*     */     {
/* 490 */       array[i] = ((Number)Preconditions.checkNotNull(boxedArray[i])).shortValue();
/*     */     }
/* 492 */     return array;
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
/* 510 */     if (backingArray.length == 0) {
/* 511 */       return Collections.emptyList();
/*     */     }
/* 513 */     return new ShortArrayAsList(backingArray);
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
/* 524 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     ShortArrayAsList(short[] array, int start, int end) {
/* 528 */       this.array = array;
/* 529 */       this.start = start;
/* 530 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 534 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 538 */       return false;
/*     */     }
/*     */     
/*     */     public Short get(int index) {
/* 542 */       Preconditions.checkElementIndex(index, size());
/* 543 */       return Short.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 548 */       return (target instanceof Short && Shorts.indexOf(this.array, ((Short)target).shortValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 554 */       if (target instanceof Short) {
/* 555 */         int i = Shorts.indexOf(this.array, ((Short)target).shortValue(), this.start, this.end);
/* 556 */         if (i >= 0) {
/* 557 */           return i - this.start;
/*     */         }
/*     */       } 
/* 560 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 565 */       if (target instanceof Short) {
/* 566 */         int i = Shorts.lastIndexOf(this.array, ((Short)target).shortValue(), this.start, this.end);
/* 567 */         if (i >= 0) {
/* 568 */           return i - this.start;
/*     */         }
/*     */       } 
/* 571 */       return -1;
/*     */     }
/*     */     
/*     */     public Short set(int index, Short element) {
/* 575 */       Preconditions.checkElementIndex(index, size());
/* 576 */       short oldValue = this.array[this.start + index];
/*     */       
/* 578 */       this.array[this.start + index] = ((Short)Preconditions.checkNotNull(element)).shortValue();
/* 579 */       return Short.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Short> subList(int fromIndex, int toIndex) {
/* 583 */       int size = size();
/* 584 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 585 */       if (fromIndex == toIndex) {
/* 586 */         return Collections.emptyList();
/*     */       }
/* 588 */       return new ShortArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 592 */       if (object == this) {
/* 593 */         return true;
/*     */       }
/* 595 */       if (object instanceof ShortArrayAsList) {
/* 596 */         ShortArrayAsList that = (ShortArrayAsList)object;
/* 597 */         int size = size();
/* 598 */         if (that.size() != size) {
/* 599 */           return false;
/*     */         }
/* 601 */         for (int i = 0; i < size; i++) {
/* 602 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 603 */             return false;
/*     */           }
/*     */         } 
/* 606 */         return true;
/*     */       } 
/* 608 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 612 */       int result = 1;
/* 613 */       for (int i = this.start; i < this.end; i++) {
/* 614 */         result = 31 * result + Shorts.hashCode(this.array[i]);
/*     */       }
/* 616 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 620 */       StringBuilder builder = new StringBuilder(size() * 6);
/* 621 */       builder.append('[').append(this.array[this.start]);
/* 622 */       for (int i = this.start + 1; i < this.end; i++) {
/* 623 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 625 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     short[] toShortArray() {
/* 630 */       int size = size();
/* 631 */       short[] result = new short[size];
/* 632 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 633 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\primitives\Shorts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */