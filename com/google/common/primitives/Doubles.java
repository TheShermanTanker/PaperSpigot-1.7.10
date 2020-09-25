/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
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
/*     */ @GwtCompatible
/*     */ public final class Doubles
/*     */ {
/*     */   public static final int BYTES = 8;
/*     */   
/*     */   public static int hashCode(double value) {
/*  64 */     return Double.valueOf(value).hashCode();
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
/*     */   public static int compare(double a, double b) {
/*  82 */     return Double.compare(a, b);
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
/*  93 */     return ((Double.NEGATIVE_INFINITY < value)) & ((value < Double.POSITIVE_INFINITY));
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
/* 107 */     for (double value : array) {
/* 108 */       if (value == target) {
/* 109 */         return true;
/*     */       }
/*     */     } 
/* 112 */     return false;
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
/* 126 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(double[] array, double target, int start, int end) {
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static int indexOf(double[] array, double[] target) {
/* 155 */     Preconditions.checkNotNull(array, "array");
/* 156 */     Preconditions.checkNotNull(target, "target");
/* 157 */     if (target.length == 0) {
/* 158 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 162 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 163 */       int j = 0; while (true) { if (j < target.length) {
/* 164 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 168 */         return i; }
/*     */     
/* 170 */     }  return -1;
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
/* 184 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(double[] array, double target, int start, int end) {
/* 190 */     for (int i = end - 1; i >= start; i--) {
/* 191 */       if (array[i] == target) {
/* 192 */         return i;
/*     */       }
/*     */     } 
/* 195 */     return -1;
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
/* 208 */     Preconditions.checkArgument((array.length > 0));
/* 209 */     double min = array[0];
/* 210 */     for (int i = 1; i < array.length; i++) {
/* 211 */       min = Math.min(min, array[i]);
/*     */     }
/* 213 */     return min;
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
/* 226 */     Preconditions.checkArgument((array.length > 0));
/* 227 */     double max = array[0];
/* 228 */     for (int i = 1; i < array.length; i++) {
/* 229 */       max = Math.max(max, array[i]);
/*     */     }
/* 231 */     return max;
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
/* 244 */     int length = 0;
/* 245 */     for (double[] array : arrays) {
/* 246 */       length += array.length;
/*     */     }
/* 248 */     double[] result = new double[length];
/* 249 */     int pos = 0;
/* 250 */     for (double[] array : arrays) {
/* 251 */       System.arraycopy(array, 0, result, pos, array.length);
/* 252 */       pos += array.length;
/*     */     } 
/* 254 */     return result;
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
/* 275 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 276 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 277 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] copyOf(double[] original, int length) {
/* 284 */     double[] copy = new double[length];
/* 285 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 286 */     return copy;
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
/* 304 */     Preconditions.checkNotNull(separator);
/* 305 */     if (array.length == 0) {
/* 306 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 310 */     StringBuilder builder = new StringBuilder(array.length * 12);
/* 311 */     builder.append(array[0]);
/* 312 */     for (int i = 1; i < array.length; i++) {
/* 313 */       builder.append(separator).append(array[i]);
/*     */     }
/* 315 */     return builder.toString();
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
/* 335 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<double[]> {
/* 339 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(double[] left, double[] right) {
/* 343 */       int minLength = Math.min(left.length, right.length);
/* 344 */       for (int i = 0; i < minLength; i++) {
/* 345 */         int result = Doubles.compare(left[i], right[i]);
/* 346 */         if (result != 0) {
/* 347 */           return result;
/*     */         }
/*     */       } 
/* 350 */       return left.length - right.length;
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
/*     */   public static double[] toArray(Collection<Double> collection) {
/* 369 */     if (collection instanceof DoubleArrayAsList) {
/* 370 */       return ((DoubleArrayAsList)collection).toDoubleArray();
/*     */     }
/*     */     
/* 373 */     Object[] boxedArray = collection.toArray();
/* 374 */     int len = boxedArray.length;
/* 375 */     double[] array = new double[len];
/* 376 */     for (int i = 0; i < len; i++)
/*     */     {
/* 378 */       array[i] = ((Double)Preconditions.checkNotNull(boxedArray[i])).doubleValue();
/*     */     }
/* 380 */     return array;
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
/* 401 */     if (backingArray.length == 0) {
/* 402 */       return Collections.emptyList();
/*     */     }
/* 404 */     return new DoubleArrayAsList(backingArray);
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
/* 415 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     DoubleArrayAsList(double[] array, int start, int end) {
/* 419 */       this.array = array;
/* 420 */       this.start = start;
/* 421 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 425 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 429 */       return false;
/*     */     }
/*     */     
/*     */     public Double get(int index) {
/* 433 */       Preconditions.checkElementIndex(index, size());
/* 434 */       return Double.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 439 */       return (target instanceof Double && Doubles.indexOf(this.array, ((Double)target).doubleValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 445 */       if (target instanceof Double) {
/* 446 */         int i = Doubles.indexOf(this.array, ((Double)target).doubleValue(), this.start, this.end);
/* 447 */         if (i >= 0) {
/* 448 */           return i - this.start;
/*     */         }
/*     */       } 
/* 451 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 456 */       if (target instanceof Double) {
/* 457 */         int i = Doubles.lastIndexOf(this.array, ((Double)target).doubleValue(), this.start, this.end);
/* 458 */         if (i >= 0) {
/* 459 */           return i - this.start;
/*     */         }
/*     */       } 
/* 462 */       return -1;
/*     */     }
/*     */     
/*     */     public Double set(int index, Double element) {
/* 466 */       Preconditions.checkElementIndex(index, size());
/* 467 */       double oldValue = this.array[this.start + index];
/* 468 */       this.array[this.start + index] = ((Double)Preconditions.checkNotNull(element)).doubleValue();
/* 469 */       return Double.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Double> subList(int fromIndex, int toIndex) {
/* 473 */       int size = size();
/* 474 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 475 */       if (fromIndex == toIndex) {
/* 476 */         return Collections.emptyList();
/*     */       }
/* 478 */       return new DoubleArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 482 */       if (object == this) {
/* 483 */         return true;
/*     */       }
/* 485 */       if (object instanceof DoubleArrayAsList) {
/* 486 */         DoubleArrayAsList that = (DoubleArrayAsList)object;
/* 487 */         int size = size();
/* 488 */         if (that.size() != size) {
/* 489 */           return false;
/*     */         }
/* 491 */         for (int i = 0; i < size; i++) {
/* 492 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 493 */             return false;
/*     */           }
/*     */         } 
/* 496 */         return true;
/*     */       } 
/* 498 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 502 */       int result = 1;
/* 503 */       for (int i = this.start; i < this.end; i++) {
/* 504 */         result = 31 * result + Doubles.hashCode(this.array[i]);
/*     */       }
/* 506 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 510 */       StringBuilder builder = new StringBuilder(size() * 12);
/* 511 */       builder.append('[').append(this.array[this.start]);
/* 512 */       for (int i = this.start + 1; i < this.end; i++) {
/* 513 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 515 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     double[] toDoubleArray() {
/* 520 */       int size = size();
/* 521 */       double[] result = new double[size];
/* 522 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 523 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\Doubles.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */