/*     */ package net.minecraft.util.com.google.common.primitives;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Doubles
/*     */ {
/*     */   public static final int BYTES = 8;
/*     */   
/*     */   public static int hashCode(double value) {
/*  74 */     return Double.valueOf(value).hashCode();
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
/*     */   public static int compare(double a, double b) {
/*  97 */     return Double.compare(a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFinite(double value) {
/* 108 */     return ((Double.NEGATIVE_INFINITY < value)) & ((value < Double.POSITIVE_INFINITY));
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
/*     */   public static boolean contains(double[] array, double target) {
/* 122 */     for (double value : array) {
/* 123 */       if (value == target) {
/* 124 */         return true;
/*     */       }
/*     */     } 
/* 127 */     return false;
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
/*     */   public static int indexOf(double[] array, double target) {
/* 141 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(double[] array, double target, int start, int end) {
/* 147 */     for (int i = start; i < end; i++) {
/* 148 */       if (array[i] == target) {
/* 149 */         return i;
/*     */       }
/*     */     } 
/* 152 */     return -1;
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
/*     */   public static int indexOf(double[] array, double[] target) {
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
/*     */   
/*     */   public static int lastIndexOf(double[] array, double target) {
/* 199 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(double[] array, double target, int start, int end) {
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
/*     */   
/*     */   public static double min(double... array) {
/* 223 */     Preconditions.checkArgument((array.length > 0));
/* 224 */     double min = array[0];
/* 225 */     for (int i = 1; i < array.length; i++) {
/* 226 */       min = Math.min(min, array[i]);
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
/*     */   
/*     */   public static double max(double... array) {
/* 241 */     Preconditions.checkArgument((array.length > 0));
/* 242 */     double max = array[0];
/* 243 */     for (int i = 1; i < array.length; i++) {
/* 244 */       max = Math.max(max, array[i]);
/*     */     }
/* 246 */     return max;
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
/*     */   public static double[] concat(double[]... arrays) {
/* 259 */     int length = 0;
/* 260 */     for (double[] array : arrays) {
/* 261 */       length += array.length;
/*     */     }
/* 263 */     double[] result = new double[length];
/* 264 */     int pos = 0;
/* 265 */     for (double[] array : arrays) {
/* 266 */       System.arraycopy(array, 0, result, pos, array.length);
/* 267 */       pos += array.length;
/*     */     } 
/* 269 */     return result;
/*     */   }
/*     */   
/*     */   private static final class DoubleConverter
/*     */     extends Converter<String, Double> implements Serializable {
/* 274 */     static final DoubleConverter INSTANCE = new DoubleConverter();
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected Double doForward(String value) {
/* 278 */       return Double.valueOf(value);
/*     */     }
/*     */ 
/*     */     
/*     */     protected String doBackward(Double value) {
/* 283 */       return value.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 288 */       return "Doubles.stringConverter()";
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 292 */       return INSTANCE;
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
/*     */   public static Converter<String, Double> stringConverter() {
/* 305 */     return DoubleConverter.INSTANCE;
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
/*     */   public static double[] ensureCapacity(double[] array, int minLength, int padding) {
/* 326 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 327 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 328 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] copyOf(double[] original, int length) {
/* 335 */     double[] copy = new double[length];
/* 336 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 337 */     return copy;
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
/*     */   public static String join(String separator, double... array) {
/* 355 */     Preconditions.checkNotNull(separator);
/* 356 */     if (array.length == 0) {
/* 357 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 361 */     StringBuilder builder = new StringBuilder(array.length * 12);
/* 362 */     builder.append(array[0]);
/* 363 */     for (int i = 1; i < array.length; i++) {
/* 364 */       builder.append(separator).append(array[i]);
/*     */     }
/* 366 */     return builder.toString();
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
/*     */   public static Comparator<double[]> lexicographicalComparator() {
/* 386 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<double[]> {
/* 390 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(double[] left, double[] right) {
/* 394 */       int minLength = Math.min(left.length, right.length);
/* 395 */       for (int i = 0; i < minLength; i++) {
/* 396 */         int result = Doubles.compare(left[i], right[i]);
/* 397 */         if (result != 0) {
/* 398 */           return result;
/*     */         }
/*     */       } 
/* 401 */       return left.length - right.length;
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
/*     */   public static double[] toArray(Collection<? extends Number> collection) {
/* 421 */     if (collection instanceof DoubleArrayAsList) {
/* 422 */       return ((DoubleArrayAsList)collection).toDoubleArray();
/*     */     }
/*     */     
/* 425 */     Object[] boxedArray = collection.toArray();
/* 426 */     int len = boxedArray.length;
/* 427 */     double[] array = new double[len];
/* 428 */     for (int i = 0; i < len; i++)
/*     */     {
/* 430 */       array[i] = ((Number)Preconditions.checkNotNull(boxedArray[i])).doubleValue();
/*     */     }
/* 432 */     return array;
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
/*     */   public static List<Double> asList(double... backingArray) {
/* 453 */     if (backingArray.length == 0) {
/* 454 */       return Collections.emptyList();
/*     */     }
/* 456 */     return new DoubleArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class DoubleArrayAsList extends AbstractList<Double> implements RandomAccess, Serializable {
/*     */     final double[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     DoubleArrayAsList(double[] array) {
/* 467 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     DoubleArrayAsList(double[] array, int start, int end) {
/* 471 */       this.array = array;
/* 472 */       this.start = start;
/* 473 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 477 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 481 */       return false;
/*     */     }
/*     */     
/*     */     public Double get(int index) {
/* 485 */       Preconditions.checkElementIndex(index, size());
/* 486 */       return Double.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 491 */       return (target instanceof Double && Doubles.indexOf(this.array, ((Double)target).doubleValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 497 */       if (target instanceof Double) {
/* 498 */         int i = Doubles.indexOf(this.array, ((Double)target).doubleValue(), this.start, this.end);
/* 499 */         if (i >= 0) {
/* 500 */           return i - this.start;
/*     */         }
/*     */       } 
/* 503 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 508 */       if (target instanceof Double) {
/* 509 */         int i = Doubles.lastIndexOf(this.array, ((Double)target).doubleValue(), this.start, this.end);
/* 510 */         if (i >= 0) {
/* 511 */           return i - this.start;
/*     */         }
/*     */       } 
/* 514 */       return -1;
/*     */     }
/*     */     
/*     */     public Double set(int index, Double element) {
/* 518 */       Preconditions.checkElementIndex(index, size());
/* 519 */       double oldValue = this.array[this.start + index];
/*     */       
/* 521 */       this.array[this.start + index] = ((Double)Preconditions.checkNotNull(element)).doubleValue();
/* 522 */       return Double.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Double> subList(int fromIndex, int toIndex) {
/* 526 */       int size = size();
/* 527 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 528 */       if (fromIndex == toIndex) {
/* 529 */         return Collections.emptyList();
/*     */       }
/* 531 */       return new DoubleArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 535 */       if (object == this) {
/* 536 */         return true;
/*     */       }
/* 538 */       if (object instanceof DoubleArrayAsList) {
/* 539 */         DoubleArrayAsList that = (DoubleArrayAsList)object;
/* 540 */         int size = size();
/* 541 */         if (that.size() != size) {
/* 542 */           return false;
/*     */         }
/* 544 */         for (int i = 0; i < size; i++) {
/* 545 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 546 */             return false;
/*     */           }
/*     */         } 
/* 549 */         return true;
/*     */       } 
/* 551 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 555 */       int result = 1;
/* 556 */       for (int i = this.start; i < this.end; i++) {
/* 557 */         result = 31 * result + Doubles.hashCode(this.array[i]);
/*     */       }
/* 559 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 563 */       StringBuilder builder = new StringBuilder(size() * 12);
/* 564 */       builder.append('[').append(this.array[this.start]);
/* 565 */       for (int i = this.start + 1; i < this.end; i++) {
/* 566 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 568 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     double[] toDoubleArray() {
/* 573 */       int size = size();
/* 574 */       double[] result = new double[size];
/* 575 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 576 */       return result;
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
/*     */   @GwtIncompatible("regular expressions")
/* 589 */   static final Pattern FLOATING_POINT_PATTERN = fpPattern();
/*     */   
/*     */   @GwtIncompatible("regular expressions")
/*     */   private static Pattern fpPattern() {
/* 593 */     String decimal = "(?:\\d++(?:\\.\\d*+)?|\\.\\d++)";
/* 594 */     String completeDec = decimal + "(?:[eE][+-]?\\d++)?[fFdD]?";
/* 595 */     String hex = "(?:\\p{XDigit}++(?:\\.\\p{XDigit}*+)?|\\.\\p{XDigit}++)";
/* 596 */     String completeHex = "0[xX]" + hex + "[pP][+-]?\\d++[fFdD]?";
/* 597 */     String fpPattern = "[+-]?(?:NaN|Infinity|" + completeDec + "|" + completeHex + ")";
/* 598 */     return Pattern.compile(fpPattern);
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
/*     */   @Nullable
/*     */   @GwtIncompatible("regular expressions")
/*     */   @Beta
/*     */   public static Double tryParse(String string) {
/* 624 */     if (FLOATING_POINT_PATTERN.matcher(string).matches()) {
/*     */       
/*     */       try {
/*     */         
/* 628 */         return Double.valueOf(Double.parseDouble(string));
/* 629 */       } catch (NumberFormatException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 634 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\primitives\Doubles.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */