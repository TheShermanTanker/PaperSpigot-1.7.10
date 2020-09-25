/*     */ package net.minecraft.util.com.google.common.primitives;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
/*     */ import javax.annotation.CheckForNull;
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
/*     */ 
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Ints
/*     */ {
/*     */   public static final int BYTES = 4;
/*     */   public static final int MAX_POWER_OF_TWO = 1073741824;
/*     */   
/*     */   public static int hashCode(int value) {
/*  76 */     return value;
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
/*  88 */     int result = (int)value;
/*  89 */     if (result != value)
/*     */     {
/*  91 */       throw new IllegalArgumentException("Out of range: " + value);
/*     */     }
/*  93 */     return result;
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
/* 105 */     if (value > 2147483647L) {
/* 106 */       return Integer.MAX_VALUE;
/*     */     }
/* 108 */     if (value < -2147483648L) {
/* 109 */       return Integer.MIN_VALUE;
/*     */     }
/* 111 */     return (int)value;
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
/*     */   public static int compare(int a, int b) {
/* 128 */     return (a < b) ? -1 : ((a > b) ? 1 : 0);
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
/* 141 */     for (int value : array) {
/* 142 */       if (value == target) {
/* 143 */         return true;
/*     */       }
/*     */     } 
/* 146 */     return false;
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
/* 159 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(int[] array, int target, int start, int end) {
/* 165 */     for (int i = start; i < end; i++) {
/* 166 */       if (array[i] == target) {
/* 167 */         return i;
/*     */       }
/*     */     } 
/* 170 */     return -1;
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
/* 185 */     Preconditions.checkNotNull(array, "array");
/* 186 */     Preconditions.checkNotNull(target, "target");
/* 187 */     if (target.length == 0) {
/* 188 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 192 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 193 */       int j = 0; while (true) { if (j < target.length) {
/* 194 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 198 */         return i; }
/*     */     
/* 200 */     }  return -1;
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
/* 213 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(int[] array, int target, int start, int end) {
/* 219 */     for (int i = end - 1; i >= start; i--) {
/* 220 */       if (array[i] == target) {
/* 221 */         return i;
/*     */       }
/*     */     } 
/* 224 */     return -1;
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
/* 236 */     Preconditions.checkArgument((array.length > 0));
/* 237 */     int min = array[0];
/* 238 */     for (int i = 1; i < array.length; i++) {
/* 239 */       if (array[i] < min) {
/* 240 */         min = array[i];
/*     */       }
/*     */     } 
/* 243 */     return min;
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
/* 255 */     Preconditions.checkArgument((array.length > 0));
/* 256 */     int max = array[0];
/* 257 */     for (int i = 1; i < array.length; i++) {
/* 258 */       if (array[i] > max) {
/* 259 */         max = array[i];
/*     */       }
/*     */     } 
/* 262 */     return max;
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
/* 275 */     int length = 0;
/* 276 */     for (int[] array : arrays) {
/* 277 */       length += array.length;
/*     */     }
/* 279 */     int[] result = new int[length];
/* 280 */     int pos = 0;
/* 281 */     for (int[] array : arrays) {
/* 282 */       System.arraycopy(array, 0, result, pos, array.length);
/* 283 */       pos += array.length;
/*     */     } 
/* 285 */     return result;
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
/* 301 */     return new byte[] { (byte)(value >> 24), (byte)(value >> 16), (byte)(value >> 8), (byte)value };
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
/* 322 */     Preconditions.checkArgument((bytes.length >= 4), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(4) });
/*     */     
/* 324 */     return fromBytes(bytes[0], bytes[1], bytes[2], bytes[3]);
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
/* 336 */     return b1 << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8 | b4 & 0xFF;
/*     */   }
/*     */   
/*     */   private static final class IntConverter
/*     */     extends Converter<String, Integer> implements Serializable {
/* 341 */     static final IntConverter INSTANCE = new IntConverter();
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected Integer doForward(String value) {
/* 345 */       return Integer.decode(value);
/*     */     }
/*     */ 
/*     */     
/*     */     protected String doBackward(Integer value) {
/* 350 */       return value.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 355 */       return "Ints.stringConverter()";
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 359 */       return INSTANCE;
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
/*     */   public static Converter<String, Integer> stringConverter() {
/* 372 */     return IntConverter.INSTANCE;
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
/* 393 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 394 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 395 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] copyOf(int[] original, int length) {
/* 402 */     int[] copy = new int[length];
/* 403 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 404 */     return copy;
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
/* 417 */     Preconditions.checkNotNull(separator);
/* 418 */     if (array.length == 0) {
/* 419 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 423 */     StringBuilder builder = new StringBuilder(array.length * 5);
/* 424 */     builder.append(array[0]);
/* 425 */     for (int i = 1; i < array.length; i++) {
/* 426 */       builder.append(separator).append(array[i]);
/*     */     }
/* 428 */     return builder.toString();
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
/* 447 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<int[]> {
/* 451 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(int[] left, int[] right) {
/* 455 */       int minLength = Math.min(left.length, right.length);
/* 456 */       for (int i = 0; i < minLength; i++) {
/* 457 */         int result = Ints.compare(left[i], right[i]);
/* 458 */         if (result != 0) {
/* 459 */           return result;
/*     */         }
/*     */       } 
/* 462 */       return left.length - right.length;
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
/*     */   public static int[] toArray(Collection<? extends Number> collection) {
/* 482 */     if (collection instanceof IntArrayAsList) {
/* 483 */       return ((IntArrayAsList)collection).toIntArray();
/*     */     }
/*     */     
/* 486 */     Object[] boxedArray = collection.toArray();
/* 487 */     int len = boxedArray.length;
/* 488 */     int[] array = new int[len];
/* 489 */     for (int i = 0; i < len; i++)
/*     */     {
/* 491 */       array[i] = ((Number)Preconditions.checkNotNull(boxedArray[i])).intValue();
/*     */     }
/* 493 */     return array;
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
/* 511 */     if (backingArray.length == 0) {
/* 512 */       return Collections.emptyList();
/*     */     }
/* 514 */     return new IntArrayAsList(backingArray);
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
/* 525 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     IntArrayAsList(int[] array, int start, int end) {
/* 529 */       this.array = array;
/* 530 */       this.start = start;
/* 531 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 535 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 539 */       return false;
/*     */     }
/*     */     
/*     */     public Integer get(int index) {
/* 543 */       Preconditions.checkElementIndex(index, size());
/* 544 */       return Integer.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 549 */       return (target instanceof Integer && Ints.indexOf(this.array, ((Integer)target).intValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 555 */       if (target instanceof Integer) {
/* 556 */         int i = Ints.indexOf(this.array, ((Integer)target).intValue(), this.start, this.end);
/* 557 */         if (i >= 0) {
/* 558 */           return i - this.start;
/*     */         }
/*     */       } 
/* 561 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 566 */       if (target instanceof Integer) {
/* 567 */         int i = Ints.lastIndexOf(this.array, ((Integer)target).intValue(), this.start, this.end);
/* 568 */         if (i >= 0) {
/* 569 */           return i - this.start;
/*     */         }
/*     */       } 
/* 572 */       return -1;
/*     */     }
/*     */     
/*     */     public Integer set(int index, Integer element) {
/* 576 */       Preconditions.checkElementIndex(index, size());
/* 577 */       int oldValue = this.array[this.start + index];
/*     */       
/* 579 */       this.array[this.start + index] = ((Integer)Preconditions.checkNotNull(element)).intValue();
/* 580 */       return Integer.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Integer> subList(int fromIndex, int toIndex) {
/* 584 */       int size = size();
/* 585 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 586 */       if (fromIndex == toIndex) {
/* 587 */         return Collections.emptyList();
/*     */       }
/* 589 */       return new IntArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 593 */       if (object == this) {
/* 594 */         return true;
/*     */       }
/* 596 */       if (object instanceof IntArrayAsList) {
/* 597 */         IntArrayAsList that = (IntArrayAsList)object;
/* 598 */         int size = size();
/* 599 */         if (that.size() != size) {
/* 600 */           return false;
/*     */         }
/* 602 */         for (int i = 0; i < size; i++) {
/* 603 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 604 */             return false;
/*     */           }
/*     */         } 
/* 607 */         return true;
/*     */       } 
/* 609 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 613 */       int result = 1;
/* 614 */       for (int i = this.start; i < this.end; i++) {
/* 615 */         result = 31 * result + Ints.hashCode(this.array[i]);
/*     */       }
/* 617 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 621 */       StringBuilder builder = new StringBuilder(size() * 5);
/* 622 */       builder.append('[').append(this.array[this.start]);
/* 623 */       for (int i = this.start + 1; i < this.end; i++) {
/* 624 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 626 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     int[] toIntArray() {
/* 631 */       int size = size();
/* 632 */       int[] result = new int[size];
/* 633 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 634 */       return result;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CheckForNull
/*     */   @Beta
/*     */   @GwtIncompatible("TODO")
/*     */   public static Integer tryParse(String string) {
/* 662 */     return AndroidInteger.tryParse(string, 10);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\primitives\Ints.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */