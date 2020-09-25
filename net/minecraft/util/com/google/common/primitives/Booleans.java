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
/*     */ @GwtCompatible
/*     */ public final class Booleans
/*     */ {
/*     */   public static int hashCode(boolean value) {
/*  60 */     return value ? 1231 : 1237;
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
/*     */   public static int compare(boolean a, boolean b) {
/*  78 */     return (a == b) ? 0 : (a ? 1 : -1);
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
/*  96 */     for (boolean value : array) {
/*  97 */       if (value == target) {
/*  98 */         return true;
/*     */       }
/*     */     } 
/* 101 */     return false;
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
/* 118 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(boolean[] array, boolean target, int start, int end) {
/* 124 */     for (int i = start; i < end; i++) {
/* 125 */       if (array[i] == target) {
/* 126 */         return i;
/*     */       }
/*     */     } 
/* 129 */     return -1;
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
/* 144 */     Preconditions.checkNotNull(array, "array");
/* 145 */     Preconditions.checkNotNull(target, "target");
/* 146 */     if (target.length == 0) {
/* 147 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 151 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 152 */       int j = 0; while (true) { if (j < target.length) {
/* 153 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 157 */         return i; }
/*     */     
/* 159 */     }  return -1;
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
/* 172 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(boolean[] array, boolean target, int start, int end) {
/* 178 */     for (int i = end - 1; i >= start; i--) {
/* 179 */       if (array[i] == target) {
/* 180 */         return i;
/*     */       }
/*     */     } 
/* 183 */     return -1;
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
/* 196 */     int length = 0;
/* 197 */     for (boolean[] array : arrays) {
/* 198 */       length += array.length;
/*     */     }
/* 200 */     boolean[] result = new boolean[length];
/* 201 */     int pos = 0;
/* 202 */     for (boolean[] array : arrays) {
/* 203 */       System.arraycopy(array, 0, result, pos, array.length);
/* 204 */       pos += array.length;
/*     */     } 
/* 206 */     return result;
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
/* 227 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 228 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 229 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean[] copyOf(boolean[] original, int length) {
/* 236 */     boolean[] copy = new boolean[length];
/* 237 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 238 */     return copy;
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
/* 251 */     Preconditions.checkNotNull(separator);
/* 252 */     if (array.length == 0) {
/* 253 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 257 */     StringBuilder builder = new StringBuilder(array.length * 7);
/* 258 */     builder.append(array[0]);
/* 259 */     for (int i = 1; i < array.length; i++) {
/* 260 */       builder.append(separator).append(array[i]);
/*     */     }
/* 262 */     return builder.toString();
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
/* 282 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<boolean[]> {
/* 286 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(boolean[] left, boolean[] right) {
/* 290 */       int minLength = Math.min(left.length, right.length);
/* 291 */       for (int i = 0; i < minLength; i++) {
/* 292 */         int result = Booleans.compare(left[i], right[i]);
/* 293 */         if (result != 0) {
/* 294 */           return result;
/*     */         }
/*     */       } 
/* 297 */       return left.length - right.length;
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
/* 319 */     if (collection instanceof BooleanArrayAsList) {
/* 320 */       return ((BooleanArrayAsList)collection).toBooleanArray();
/*     */     }
/*     */     
/* 323 */     Object[] boxedArray = collection.toArray();
/* 324 */     int len = boxedArray.length;
/* 325 */     boolean[] array = new boolean[len];
/* 326 */     for (int i = 0; i < len; i++)
/*     */     {
/* 328 */       array[i] = ((Boolean)Preconditions.checkNotNull(boxedArray[i])).booleanValue();
/*     */     }
/* 330 */     return array;
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
/* 348 */     if (backingArray.length == 0) {
/* 349 */       return Collections.emptyList();
/*     */     }
/* 351 */     return new BooleanArrayAsList(backingArray);
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
/* 362 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     BooleanArrayAsList(boolean[] array, int start, int end) {
/* 366 */       this.array = array;
/* 367 */       this.start = start;
/* 368 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 372 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 376 */       return false;
/*     */     }
/*     */     
/*     */     public Boolean get(int index) {
/* 380 */       Preconditions.checkElementIndex(index, size());
/* 381 */       return Boolean.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 386 */       return (target instanceof Boolean && Booleans.indexOf(this.array, ((Boolean)target).booleanValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 392 */       if (target instanceof Boolean) {
/* 393 */         int i = Booleans.indexOf(this.array, ((Boolean)target).booleanValue(), this.start, this.end);
/* 394 */         if (i >= 0) {
/* 395 */           return i - this.start;
/*     */         }
/*     */       } 
/* 398 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 403 */       if (target instanceof Boolean) {
/* 404 */         int i = Booleans.lastIndexOf(this.array, ((Boolean)target).booleanValue(), this.start, this.end);
/* 405 */         if (i >= 0) {
/* 406 */           return i - this.start;
/*     */         }
/*     */       } 
/* 409 */       return -1;
/*     */     }
/*     */     
/*     */     public Boolean set(int index, Boolean element) {
/* 413 */       Preconditions.checkElementIndex(index, size());
/* 414 */       boolean oldValue = this.array[this.start + index];
/*     */       
/* 416 */       this.array[this.start + index] = ((Boolean)Preconditions.checkNotNull(element)).booleanValue();
/* 417 */       return Boolean.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Boolean> subList(int fromIndex, int toIndex) {
/* 421 */       int size = size();
/* 422 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 423 */       if (fromIndex == toIndex) {
/* 424 */         return Collections.emptyList();
/*     */       }
/* 426 */       return new BooleanArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 430 */       if (object == this) {
/* 431 */         return true;
/*     */       }
/* 433 */       if (object instanceof BooleanArrayAsList) {
/* 434 */         BooleanArrayAsList that = (BooleanArrayAsList)object;
/* 435 */         int size = size();
/* 436 */         if (that.size() != size) {
/* 437 */           return false;
/*     */         }
/* 439 */         for (int i = 0; i < size; i++) {
/* 440 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 441 */             return false;
/*     */           }
/*     */         } 
/* 444 */         return true;
/*     */       } 
/* 446 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 450 */       int result = 1;
/* 451 */       for (int i = this.start; i < this.end; i++) {
/* 452 */         result = 31 * result + Booleans.hashCode(this.array[i]);
/*     */       }
/* 454 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 458 */       StringBuilder builder = new StringBuilder(size() * 7);
/* 459 */       builder.append(this.array[this.start] ? "[true" : "[false");
/* 460 */       for (int i = this.start + 1; i < this.end; i++) {
/* 461 */         builder.append(this.array[i] ? ", true" : ", false");
/*     */       }
/* 463 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     boolean[] toBooleanArray() {
/* 468 */       int size = size();
/* 469 */       boolean[] result = new boolean[size];
/* 470 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 471 */       return result;
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
/*     */   public static int countTrue(boolean... values) {
/* 484 */     int count = 0;
/* 485 */     for (boolean value : values) {
/* 486 */       if (value) {
/* 487 */         count++;
/*     */       }
/*     */     } 
/* 490 */     return count;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\primitives\Booleans.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */