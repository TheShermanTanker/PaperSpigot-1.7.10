/*     */ package net.minecraft.util.com.google.common.primitives;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
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
/*     */ 
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Floats
/*     */ {
/*     */   public static final int BYTES = 4;
/*     */   
/*     */   public static int hashCode(float value) {
/*  74 */     return Float.valueOf(value).hashCode();
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
/*     */   public static int compare(float a, float b) {
/*  93 */     return Float.compare(a, b);
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
/* 104 */     return ((Float.NEGATIVE_INFINITY < value)) & ((value < Float.POSITIVE_INFINITY));
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
/* 118 */     for (float value : array) {
/* 119 */       if (value == target) {
/* 120 */         return true;
/*     */       }
/*     */     } 
/* 123 */     return false;
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
/* 137 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(float[] array, float target, int start, int end) {
/* 143 */     for (int i = start; i < end; i++) {
/* 144 */       if (array[i] == target) {
/* 145 */         return i;
/*     */       }
/*     */     } 
/* 148 */     return -1;
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
/* 166 */     Preconditions.checkNotNull(array, "array");
/* 167 */     Preconditions.checkNotNull(target, "target");
/* 168 */     if (target.length == 0) {
/* 169 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 173 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 174 */       int j = 0; while (true) { if (j < target.length) {
/* 175 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 179 */         return i; }
/*     */     
/* 181 */     }  return -1;
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
/* 195 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(float[] array, float target, int start, int end) {
/* 201 */     for (int i = end - 1; i >= start; i--) {
/* 202 */       if (array[i] == target) {
/* 203 */         return i;
/*     */       }
/*     */     } 
/* 206 */     return -1;
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
/* 219 */     Preconditions.checkArgument((array.length > 0));
/* 220 */     float min = array[0];
/* 221 */     for (int i = 1; i < array.length; i++) {
/* 222 */       min = Math.min(min, array[i]);
/*     */     }
/* 224 */     return min;
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
/* 237 */     Preconditions.checkArgument((array.length > 0));
/* 238 */     float max = array[0];
/* 239 */     for (int i = 1; i < array.length; i++) {
/* 240 */       max = Math.max(max, array[i]);
/*     */     }
/* 242 */     return max;
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
/* 255 */     int length = 0;
/* 256 */     for (float[] array : arrays) {
/* 257 */       length += array.length;
/*     */     }
/* 259 */     float[] result = new float[length];
/* 260 */     int pos = 0;
/* 261 */     for (float[] array : arrays) {
/* 262 */       System.arraycopy(array, 0, result, pos, array.length);
/* 263 */       pos += array.length;
/*     */     } 
/* 265 */     return result;
/*     */   }
/*     */   
/*     */   private static final class FloatConverter
/*     */     extends Converter<String, Float> implements Serializable {
/* 270 */     static final FloatConverter INSTANCE = new FloatConverter();
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected Float doForward(String value) {
/* 274 */       return Float.valueOf(value);
/*     */     }
/*     */ 
/*     */     
/*     */     protected String doBackward(Float value) {
/* 279 */       return value.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 284 */       return "Floats.stringConverter()";
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 288 */       return INSTANCE;
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
/*     */   public static Converter<String, Float> stringConverter() {
/* 301 */     return FloatConverter.INSTANCE;
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
/* 322 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 323 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 324 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[] copyOf(float[] original, int length) {
/* 331 */     float[] copy = new float[length];
/* 332 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 333 */     return copy;
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
/* 351 */     Preconditions.checkNotNull(separator);
/* 352 */     if (array.length == 0) {
/* 353 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 357 */     StringBuilder builder = new StringBuilder(array.length * 12);
/* 358 */     builder.append(array[0]);
/* 359 */     for (int i = 1; i < array.length; i++) {
/* 360 */       builder.append(separator).append(array[i]);
/*     */     }
/* 362 */     return builder.toString();
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
/* 382 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<float[]> {
/* 386 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(float[] left, float[] right) {
/* 390 */       int minLength = Math.min(left.length, right.length);
/* 391 */       for (int i = 0; i < minLength; i++) {
/* 392 */         int result = Floats.compare(left[i], right[i]);
/* 393 */         if (result != 0) {
/* 394 */           return result;
/*     */         }
/*     */       } 
/* 397 */       return left.length - right.length;
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
/*     */   public static float[] toArray(Collection<? extends Number> collection) {
/* 417 */     if (collection instanceof FloatArrayAsList) {
/* 418 */       return ((FloatArrayAsList)collection).toFloatArray();
/*     */     }
/*     */     
/* 421 */     Object[] boxedArray = collection.toArray();
/* 422 */     int len = boxedArray.length;
/* 423 */     float[] array = new float[len];
/* 424 */     for (int i = 0; i < len; i++)
/*     */     {
/* 426 */       array[i] = ((Number)Preconditions.checkNotNull(boxedArray[i])).floatValue();
/*     */     }
/* 428 */     return array;
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
/* 449 */     if (backingArray.length == 0) {
/* 450 */       return Collections.emptyList();
/*     */     }
/* 452 */     return new FloatArrayAsList(backingArray);
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
/* 463 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     FloatArrayAsList(float[] array, int start, int end) {
/* 467 */       this.array = array;
/* 468 */       this.start = start;
/* 469 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 473 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 477 */       return false;
/*     */     }
/*     */     
/*     */     public Float get(int index) {
/* 481 */       Preconditions.checkElementIndex(index, size());
/* 482 */       return Float.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 487 */       return (target instanceof Float && Floats.indexOf(this.array, ((Float)target).floatValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 493 */       if (target instanceof Float) {
/* 494 */         int i = Floats.indexOf(this.array, ((Float)target).floatValue(), this.start, this.end);
/* 495 */         if (i >= 0) {
/* 496 */           return i - this.start;
/*     */         }
/*     */       } 
/* 499 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 504 */       if (target instanceof Float) {
/* 505 */         int i = Floats.lastIndexOf(this.array, ((Float)target).floatValue(), this.start, this.end);
/* 506 */         if (i >= 0) {
/* 507 */           return i - this.start;
/*     */         }
/*     */       } 
/* 510 */       return -1;
/*     */     }
/*     */     
/*     */     public Float set(int index, Float element) {
/* 514 */       Preconditions.checkElementIndex(index, size());
/* 515 */       float oldValue = this.array[this.start + index];
/*     */       
/* 517 */       this.array[this.start + index] = ((Float)Preconditions.checkNotNull(element)).floatValue();
/* 518 */       return Float.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Float> subList(int fromIndex, int toIndex) {
/* 522 */       int size = size();
/* 523 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 524 */       if (fromIndex == toIndex) {
/* 525 */         return Collections.emptyList();
/*     */       }
/* 527 */       return new FloatArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 531 */       if (object == this) {
/* 532 */         return true;
/*     */       }
/* 534 */       if (object instanceof FloatArrayAsList) {
/* 535 */         FloatArrayAsList that = (FloatArrayAsList)object;
/* 536 */         int size = size();
/* 537 */         if (that.size() != size) {
/* 538 */           return false;
/*     */         }
/* 540 */         for (int i = 0; i < size; i++) {
/* 541 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 542 */             return false;
/*     */           }
/*     */         } 
/* 545 */         return true;
/*     */       } 
/* 547 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 551 */       int result = 1;
/* 552 */       for (int i = this.start; i < this.end; i++) {
/* 553 */         result = 31 * result + Floats.hashCode(this.array[i]);
/*     */       }
/* 555 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 559 */       StringBuilder builder = new StringBuilder(size() * 12);
/* 560 */       builder.append('[').append(this.array[this.start]);
/* 561 */       for (int i = this.start + 1; i < this.end; i++) {
/* 562 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 564 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     float[] toFloatArray() {
/* 569 */       int size = size();
/* 570 */       float[] result = new float[size];
/* 571 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 572 */       return result;
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
/*     */   
/*     */   @Nullable
/*     */   @GwtIncompatible("regular expressions")
/*     */   @Beta
/*     */   public static Float tryParse(String string) {
/* 601 */     if (Doubles.FLOATING_POINT_PATTERN.matcher(string).matches()) {
/*     */       
/*     */       try {
/*     */         
/* 605 */         return Float.valueOf(Float.parseFloat(string));
/* 606 */       } catch (NumberFormatException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 611 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\primitives\Floats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */