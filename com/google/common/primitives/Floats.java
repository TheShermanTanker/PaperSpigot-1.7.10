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
/*     */ 
/*     */ @GwtCompatible
/*     */ public final class Floats
/*     */ {
/*     */   public static final int BYTES = 4;
/*     */   
/*     */   public static int hashCode(float value) {
/*  65 */     return Float.valueOf(value).hashCode();
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
/*     */   public static int compare(float a, float b) {
/*  79 */     return Float.compare(a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFinite(float value) {
/*  90 */     return ((Float.NEGATIVE_INFINITY < value)) & ((value < Float.POSITIVE_INFINITY));
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
/*     */   public static boolean contains(float[] array, float target) {
/* 104 */     for (float value : array) {
/* 105 */       if (value == target) {
/* 106 */         return true;
/*     */       }
/*     */     } 
/* 109 */     return false;
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
/*     */   public static int indexOf(float[] array, float target) {
/* 123 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(float[] array, float target, int start, int end) {
/* 129 */     for (int i = start; i < end; i++) {
/* 130 */       if (array[i] == target) {
/* 131 */         return i;
/*     */       }
/*     */     } 
/* 134 */     return -1;
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
/*     */   public static int indexOf(float[] array, float[] target) {
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
/*     */   
/*     */   public static int lastIndexOf(float[] array, float target) {
/* 181 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(float[] array, float target, int start, int end) {
/* 187 */     for (int i = end - 1; i >= start; i--) {
/* 188 */       if (array[i] == target) {
/* 189 */         return i;
/*     */       }
/*     */     } 
/* 192 */     return -1;
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
/*     */   public static float min(float... array) {
/* 205 */     Preconditions.checkArgument((array.length > 0));
/* 206 */     float min = array[0];
/* 207 */     for (int i = 1; i < array.length; i++) {
/* 208 */       min = Math.min(min, array[i]);
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
/*     */   
/*     */   public static float max(float... array) {
/* 223 */     Preconditions.checkArgument((array.length > 0));
/* 224 */     float max = array[0];
/* 225 */     for (int i = 1; i < array.length; i++) {
/* 226 */       max = Math.max(max, array[i]);
/*     */     }
/* 228 */     return max;
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
/*     */   public static float[] concat(float[]... arrays) {
/* 241 */     int length = 0;
/* 242 */     for (float[] array : arrays) {
/* 243 */       length += array.length;
/*     */     }
/* 245 */     float[] result = new float[length];
/* 246 */     int pos = 0;
/* 247 */     for (float[] array : arrays) {
/* 248 */       System.arraycopy(array, 0, result, pos, array.length);
/* 249 */       pos += array.length;
/*     */     } 
/* 251 */     return result;
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
/*     */   public static float[] ensureCapacity(float[] array, int minLength, int padding) {
/* 272 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 273 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 274 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[] copyOf(float[] original, int length) {
/* 281 */     float[] copy = new float[length];
/* 282 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 283 */     return copy;
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
/*     */   public static String join(String separator, float... array) {
/* 301 */     Preconditions.checkNotNull(separator);
/* 302 */     if (array.length == 0) {
/* 303 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 307 */     StringBuilder builder = new StringBuilder(array.length * 12);
/* 308 */     builder.append(array[0]);
/* 309 */     for (int i = 1; i < array.length; i++) {
/* 310 */       builder.append(separator).append(array[i]);
/*     */     }
/* 312 */     return builder.toString();
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
/*     */   public static Comparator<float[]> lexicographicalComparator() {
/* 332 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<float[]> {
/* 336 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(float[] left, float[] right) {
/* 340 */       int minLength = Math.min(left.length, right.length);
/* 341 */       for (int i = 0; i < minLength; i++) {
/* 342 */         int result = Floats.compare(left[i], right[i]);
/* 343 */         if (result != 0) {
/* 344 */           return result;
/*     */         }
/*     */       } 
/* 347 */       return left.length - right.length;
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
/*     */   public static float[] toArray(Collection<Float> collection) {
/* 366 */     if (collection instanceof FloatArrayAsList) {
/* 367 */       return ((FloatArrayAsList)collection).toFloatArray();
/*     */     }
/*     */     
/* 370 */     Object[] boxedArray = collection.toArray();
/* 371 */     int len = boxedArray.length;
/* 372 */     float[] array = new float[len];
/* 373 */     for (int i = 0; i < len; i++)
/*     */     {
/* 375 */       array[i] = ((Float)Preconditions.checkNotNull(boxedArray[i])).floatValue();
/*     */     }
/* 377 */     return array;
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
/*     */   public static List<Float> asList(float... backingArray) {
/* 398 */     if (backingArray.length == 0) {
/* 399 */       return Collections.emptyList();
/*     */     }
/* 401 */     return new FloatArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class FloatArrayAsList extends AbstractList<Float> implements RandomAccess, Serializable {
/*     */     final float[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     FloatArrayAsList(float[] array) {
/* 412 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     FloatArrayAsList(float[] array, int start, int end) {
/* 416 */       this.array = array;
/* 417 */       this.start = start;
/* 418 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 422 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 426 */       return false;
/*     */     }
/*     */     
/*     */     public Float get(int index) {
/* 430 */       Preconditions.checkElementIndex(index, size());
/* 431 */       return Float.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 436 */       return (target instanceof Float && Floats.indexOf(this.array, ((Float)target).floatValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 442 */       if (target instanceof Float) {
/* 443 */         int i = Floats.indexOf(this.array, ((Float)target).floatValue(), this.start, this.end);
/* 444 */         if (i >= 0) {
/* 445 */           return i - this.start;
/*     */         }
/*     */       } 
/* 448 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 453 */       if (target instanceof Float) {
/* 454 */         int i = Floats.lastIndexOf(this.array, ((Float)target).floatValue(), this.start, this.end);
/* 455 */         if (i >= 0) {
/* 456 */           return i - this.start;
/*     */         }
/*     */       } 
/* 459 */       return -1;
/*     */     }
/*     */     
/*     */     public Float set(int index, Float element) {
/* 463 */       Preconditions.checkElementIndex(index, size());
/* 464 */       float oldValue = this.array[this.start + index];
/* 465 */       this.array[this.start + index] = ((Float)Preconditions.checkNotNull(element)).floatValue();
/* 466 */       return Float.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Float> subList(int fromIndex, int toIndex) {
/* 470 */       int size = size();
/* 471 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 472 */       if (fromIndex == toIndex) {
/* 473 */         return Collections.emptyList();
/*     */       }
/* 475 */       return new FloatArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 479 */       if (object == this) {
/* 480 */         return true;
/*     */       }
/* 482 */       if (object instanceof FloatArrayAsList) {
/* 483 */         FloatArrayAsList that = (FloatArrayAsList)object;
/* 484 */         int size = size();
/* 485 */         if (that.size() != size) {
/* 486 */           return false;
/*     */         }
/* 488 */         for (int i = 0; i < size; i++) {
/* 489 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 490 */             return false;
/*     */           }
/*     */         } 
/* 493 */         return true;
/*     */       } 
/* 495 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 499 */       int result = 1;
/* 500 */       for (int i = this.start; i < this.end; i++) {
/* 501 */         result = 31 * result + Floats.hashCode(this.array[i]);
/*     */       }
/* 503 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 507 */       StringBuilder builder = new StringBuilder(size() * 12);
/* 508 */       builder.append('[').append(this.array[this.start]);
/* 509 */       for (int i = this.start + 1; i < this.end; i++) {
/* 510 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 512 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     float[] toFloatArray() {
/* 517 */       int size = size();
/* 518 */       float[] result = new float[size];
/* 519 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 520 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\Floats.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */