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
/*     */ @GwtCompatible
/*     */ public final class Booleans
/*     */ {
/*     */   public static int hashCode(boolean value) {
/*  55 */     return value ? 1231 : 1237;
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
/*     */   public static int compare(boolean a, boolean b) {
/*  69 */     return (a == b) ? 0 : (a ? 1 : -1);
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
/*     */   public static boolean contains(boolean[] array, boolean target) {
/*  87 */     for (boolean value : array) {
/*  88 */       if (value == target) {
/*  89 */         return true;
/*     */       }
/*     */     } 
/*  92 */     return false;
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
/*     */   public static int indexOf(boolean[] array, boolean target) {
/* 109 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(boolean[] array, boolean target, int start, int end) {
/* 115 */     for (int i = start; i < end; i++) {
/* 116 */       if (array[i] == target) {
/* 117 */         return i;
/*     */       }
/*     */     } 
/* 120 */     return -1;
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
/*     */   public static int indexOf(boolean[] array, boolean[] target) {
/* 135 */     Preconditions.checkNotNull(array, "array");
/* 136 */     Preconditions.checkNotNull(target, "target");
/* 137 */     if (target.length == 0) {
/* 138 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 142 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 143 */       int j = 0; while (true) { if (j < target.length) {
/* 144 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 148 */         return i; }
/*     */     
/* 150 */     }  return -1;
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
/*     */   public static int lastIndexOf(boolean[] array, boolean target) {
/* 163 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(boolean[] array, boolean target, int start, int end) {
/* 169 */     for (int i = end - 1; i >= start; i--) {
/* 170 */       if (array[i] == target) {
/* 171 */         return i;
/*     */       }
/*     */     } 
/* 174 */     return -1;
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
/*     */   public static boolean[] concat(boolean[]... arrays) {
/* 187 */     int length = 0;
/* 188 */     for (boolean[] array : arrays) {
/* 189 */       length += array.length;
/*     */     }
/* 191 */     boolean[] result = new boolean[length];
/* 192 */     int pos = 0;
/* 193 */     for (boolean[] array : arrays) {
/* 194 */       System.arraycopy(array, 0, result, pos, array.length);
/* 195 */       pos += array.length;
/*     */     } 
/* 197 */     return result;
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
/*     */   public static boolean[] ensureCapacity(boolean[] array, int minLength, int padding) {
/* 218 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 219 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 220 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean[] copyOf(boolean[] original, int length) {
/* 227 */     boolean[] copy = new boolean[length];
/* 228 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 229 */     return copy;
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
/*     */   public static String join(String separator, boolean... array) {
/* 242 */     Preconditions.checkNotNull(separator);
/* 243 */     if (array.length == 0) {
/* 244 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 248 */     StringBuilder builder = new StringBuilder(array.length * 7);
/* 249 */     builder.append(array[0]);
/* 250 */     for (int i = 1; i < array.length; i++) {
/* 251 */       builder.append(separator).append(array[i]);
/*     */     }
/* 253 */     return builder.toString();
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
/*     */   public static Comparator<boolean[]> lexicographicalComparator() {
/* 273 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<boolean[]> {
/* 277 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(boolean[] left, boolean[] right) {
/* 281 */       int minLength = Math.min(left.length, right.length);
/* 282 */       for (int i = 0; i < minLength; i++) {
/* 283 */         int result = Booleans.compare(left[i], right[i]);
/* 284 */         if (result != 0) {
/* 285 */           return result;
/*     */         }
/*     */       } 
/* 288 */       return left.length - right.length;
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
/*     */   public static boolean[] toArray(Collection<Boolean> collection) {
/* 310 */     if (collection instanceof BooleanArrayAsList) {
/* 311 */       return ((BooleanArrayAsList)collection).toBooleanArray();
/*     */     }
/*     */     
/* 314 */     Object[] boxedArray = collection.toArray();
/* 315 */     int len = boxedArray.length;
/* 316 */     boolean[] array = new boolean[len];
/* 317 */     for (int i = 0; i < len; i++)
/*     */     {
/* 319 */       array[i] = ((Boolean)Preconditions.checkNotNull(boxedArray[i])).booleanValue();
/*     */     }
/* 321 */     return array;
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
/*     */   public static List<Boolean> asList(boolean... backingArray) {
/* 339 */     if (backingArray.length == 0) {
/* 340 */       return Collections.emptyList();
/*     */     }
/* 342 */     return new BooleanArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class BooleanArrayAsList extends AbstractList<Boolean> implements RandomAccess, Serializable {
/*     */     final boolean[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     BooleanArrayAsList(boolean[] array) {
/* 353 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     BooleanArrayAsList(boolean[] array, int start, int end) {
/* 357 */       this.array = array;
/* 358 */       this.start = start;
/* 359 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 363 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 367 */       return false;
/*     */     }
/*     */     
/*     */     public Boolean get(int index) {
/* 371 */       Preconditions.checkElementIndex(index, size());
/* 372 */       return Boolean.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 377 */       return (target instanceof Boolean && Booleans.indexOf(this.array, ((Boolean)target).booleanValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 383 */       if (target instanceof Boolean) {
/* 384 */         int i = Booleans.indexOf(this.array, ((Boolean)target).booleanValue(), this.start, this.end);
/* 385 */         if (i >= 0) {
/* 386 */           return i - this.start;
/*     */         }
/*     */       } 
/* 389 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 394 */       if (target instanceof Boolean) {
/* 395 */         int i = Booleans.lastIndexOf(this.array, ((Boolean)target).booleanValue(), this.start, this.end);
/* 396 */         if (i >= 0) {
/* 397 */           return i - this.start;
/*     */         }
/*     */       } 
/* 400 */       return -1;
/*     */     }
/*     */     
/*     */     public Boolean set(int index, Boolean element) {
/* 404 */       Preconditions.checkElementIndex(index, size());
/* 405 */       boolean oldValue = this.array[this.start + index];
/* 406 */       this.array[this.start + index] = ((Boolean)Preconditions.checkNotNull(element)).booleanValue();
/* 407 */       return Boolean.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Boolean> subList(int fromIndex, int toIndex) {
/* 411 */       int size = size();
/* 412 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 413 */       if (fromIndex == toIndex) {
/* 414 */         return Collections.emptyList();
/*     */       }
/* 416 */       return new BooleanArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 420 */       if (object == this) {
/* 421 */         return true;
/*     */       }
/* 423 */       if (object instanceof BooleanArrayAsList) {
/* 424 */         BooleanArrayAsList that = (BooleanArrayAsList)object;
/* 425 */         int size = size();
/* 426 */         if (that.size() != size) {
/* 427 */           return false;
/*     */         }
/* 429 */         for (int i = 0; i < size; i++) {
/* 430 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 431 */             return false;
/*     */           }
/*     */         } 
/* 434 */         return true;
/*     */       } 
/* 436 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 440 */       int result = 1;
/* 441 */       for (int i = this.start; i < this.end; i++) {
/* 442 */         result = 31 * result + Booleans.hashCode(this.array[i]);
/*     */       }
/* 444 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 448 */       StringBuilder builder = new StringBuilder(size() * 7);
/* 449 */       builder.append(this.array[this.start] ? "[true" : "[false");
/* 450 */       for (int i = this.start + 1; i < this.end; i++) {
/* 451 */         builder.append(this.array[i] ? ", true" : ", false");
/*     */       }
/* 453 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     boolean[] toBooleanArray() {
/* 458 */       int size = size();
/* 459 */       boolean[] result = new boolean[size];
/* 460 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 461 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\Booleans.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */