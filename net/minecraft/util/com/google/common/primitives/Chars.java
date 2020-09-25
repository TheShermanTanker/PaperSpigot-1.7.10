/*     */ package net.minecraft.util.com.google.common.primitives;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Chars
/*     */ {
/*     */   public static final int BYTES = 2;
/*     */   
/*     */   public static int hashCode(char value) {
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
/*     */   public static char checkedCast(long value) {
/*  80 */     char result = (char)(int)value;
/*  81 */     if (result != value)
/*     */     {
/*  83 */       throw new IllegalArgumentException("Out of range: " + value);
/*     */     }
/*  85 */     return result;
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
/*     */   public static char saturatedCast(long value) {
/*  97 */     if (value > 65535L) {
/*  98 */       return Character.MAX_VALUE;
/*     */     }
/* 100 */     if (value < 0L) {
/* 101 */       return Character.MIN_VALUE;
/*     */     }
/* 103 */     return (char)(int)value;
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
/*     */   public static int compare(char a, char b) {
/* 120 */     return a - b;
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
/*     */   public static boolean contains(char[] array, char target) {
/* 133 */     for (char value : array) {
/* 134 */       if (value == target) {
/* 135 */         return true;
/*     */       }
/*     */     } 
/* 138 */     return false;
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
/*     */   public static int indexOf(char[] array, char target) {
/* 151 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(char[] array, char target, int start, int end) {
/* 157 */     for (int i = start; i < end; i++) {
/* 158 */       if (array[i] == target) {
/* 159 */         return i;
/*     */       }
/*     */     } 
/* 162 */     return -1;
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
/*     */   public static int indexOf(char[] array, char[] target) {
/* 177 */     Preconditions.checkNotNull(array, "array");
/* 178 */     Preconditions.checkNotNull(target, "target");
/* 179 */     if (target.length == 0) {
/* 180 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 184 */     for (int i = 0; i < array.length - target.length + 1; i++) {
/* 185 */       int j = 0; while (true) { if (j < target.length) {
/* 186 */           if (array[i + j] != target[j])
/*     */             break;  j++;
/*     */           continue;
/*     */         } 
/* 190 */         return i; }
/*     */     
/* 192 */     }  return -1;
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
/*     */   public static int lastIndexOf(char[] array, char target) {
/* 205 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(char[] array, char target, int start, int end) {
/* 211 */     for (int i = end - 1; i >= start; i--) {
/* 212 */       if (array[i] == target) {
/* 213 */         return i;
/*     */       }
/*     */     } 
/* 216 */     return -1;
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
/*     */   public static char min(char... array) {
/* 228 */     Preconditions.checkArgument((array.length > 0));
/* 229 */     char min = array[0];
/* 230 */     for (int i = 1; i < array.length; i++) {
/* 231 */       if (array[i] < min) {
/* 232 */         min = array[i];
/*     */       }
/*     */     } 
/* 235 */     return min;
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
/*     */   public static char max(char... array) {
/* 247 */     Preconditions.checkArgument((array.length > 0));
/* 248 */     char max = array[0];
/* 249 */     for (int i = 1; i < array.length; i++) {
/* 250 */       if (array[i] > max) {
/* 251 */         max = array[i];
/*     */       }
/*     */     } 
/* 254 */     return max;
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
/*     */   public static char[] concat(char[]... arrays) {
/* 267 */     int length = 0;
/* 268 */     for (char[] array : arrays) {
/* 269 */       length += array.length;
/*     */     }
/* 271 */     char[] result = new char[length];
/* 272 */     int pos = 0;
/* 273 */     for (char[] array : arrays) {
/* 274 */       System.arraycopy(array, 0, result, pos, array.length);
/* 275 */       pos += array.length;
/*     */     } 
/* 277 */     return result;
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
/*     */   public static byte[] toByteArray(char value) {
/* 293 */     return new byte[] { (byte)(value >> 8), (byte)value };
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
/*     */   public static char fromByteArray(byte[] bytes) {
/* 312 */     Preconditions.checkArgument((bytes.length >= 2), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(2) });
/*     */     
/* 314 */     return fromBytes(bytes[0], bytes[1]);
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
/*     */   public static char fromBytes(byte b1, byte b2) {
/* 326 */     return (char)(b1 << 8 | b2 & 0xFF);
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
/*     */   public static char[] ensureCapacity(char[] array, int minLength, int padding) {
/* 347 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 348 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 349 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static char[] copyOf(char[] original, int length) {
/* 356 */     char[] copy = new char[length];
/* 357 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 358 */     return copy;
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
/*     */   public static String join(String separator, char... array) {
/* 371 */     Preconditions.checkNotNull(separator);
/* 372 */     int len = array.length;
/* 373 */     if (len == 0) {
/* 374 */       return "";
/*     */     }
/*     */     
/* 377 */     StringBuilder builder = new StringBuilder(len + separator.length() * (len - 1));
/*     */     
/* 379 */     builder.append(array[0]);
/* 380 */     for (int i = 1; i < len; i++) {
/* 381 */       builder.append(separator).append(array[i]);
/*     */     }
/* 383 */     return builder.toString();
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
/*     */   public static Comparator<char[]> lexicographicalComparator() {
/* 403 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<char[]> {
/* 407 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(char[] left, char[] right) {
/* 411 */       int minLength = Math.min(left.length, right.length);
/* 412 */       for (int i = 0; i < minLength; i++) {
/* 413 */         int result = Chars.compare(left[i], right[i]);
/* 414 */         if (result != 0) {
/* 415 */           return result;
/*     */         }
/*     */       } 
/* 418 */       return left.length - right.length;
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
/*     */   public static char[] toArray(Collection<Character> collection) {
/* 437 */     if (collection instanceof CharArrayAsList) {
/* 438 */       return ((CharArrayAsList)collection).toCharArray();
/*     */     }
/*     */     
/* 441 */     Object[] boxedArray = collection.toArray();
/* 442 */     int len = boxedArray.length;
/* 443 */     char[] array = new char[len];
/* 444 */     for (int i = 0; i < len; i++)
/*     */     {
/* 446 */       array[i] = ((Character)Preconditions.checkNotNull(boxedArray[i])).charValue();
/*     */     }
/* 448 */     return array;
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
/*     */   public static List<Character> asList(char... backingArray) {
/* 466 */     if (backingArray.length == 0) {
/* 467 */       return Collections.emptyList();
/*     */     }
/* 469 */     return new CharArrayAsList(backingArray);
/*     */   }
/*     */   
/*     */   @GwtCompatible
/*     */   private static class CharArrayAsList extends AbstractList<Character> implements RandomAccess, Serializable {
/*     */     final char[] array;
/*     */     final int start;
/*     */     final int end;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     CharArrayAsList(char[] array) {
/* 480 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     CharArrayAsList(char[] array, int start, int end) {
/* 484 */       this.array = array;
/* 485 */       this.start = start;
/* 486 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 490 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 494 */       return false;
/*     */     }
/*     */     
/*     */     public Character get(int index) {
/* 498 */       Preconditions.checkElementIndex(index, size());
/* 499 */       return Character.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 504 */       return (target instanceof Character && Chars.indexOf(this.array, ((Character)target).charValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 510 */       if (target instanceof Character) {
/* 511 */         int i = Chars.indexOf(this.array, ((Character)target).charValue(), this.start, this.end);
/* 512 */         if (i >= 0) {
/* 513 */           return i - this.start;
/*     */         }
/*     */       } 
/* 516 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 521 */       if (target instanceof Character) {
/* 522 */         int i = Chars.lastIndexOf(this.array, ((Character)target).charValue(), this.start, this.end);
/* 523 */         if (i >= 0) {
/* 524 */           return i - this.start;
/*     */         }
/*     */       } 
/* 527 */       return -1;
/*     */     }
/*     */     
/*     */     public Character set(int index, Character element) {
/* 531 */       Preconditions.checkElementIndex(index, size());
/* 532 */       char oldValue = this.array[this.start + index];
/*     */       
/* 534 */       this.array[this.start + index] = ((Character)Preconditions.checkNotNull(element)).charValue();
/* 535 */       return Character.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Character> subList(int fromIndex, int toIndex) {
/* 539 */       int size = size();
/* 540 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 541 */       if (fromIndex == toIndex) {
/* 542 */         return Collections.emptyList();
/*     */       }
/* 544 */       return new CharArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 548 */       if (object == this) {
/* 549 */         return true;
/*     */       }
/* 551 */       if (object instanceof CharArrayAsList) {
/* 552 */         CharArrayAsList that = (CharArrayAsList)object;
/* 553 */         int size = size();
/* 554 */         if (that.size() != size) {
/* 555 */           return false;
/*     */         }
/* 557 */         for (int i = 0; i < size; i++) {
/* 558 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 559 */             return false;
/*     */           }
/*     */         } 
/* 562 */         return true;
/*     */       } 
/* 564 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 568 */       int result = 1;
/* 569 */       for (int i = this.start; i < this.end; i++) {
/* 570 */         result = 31 * result + Chars.hashCode(this.array[i]);
/*     */       }
/* 572 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 576 */       StringBuilder builder = new StringBuilder(size() * 3);
/* 577 */       builder.append('[').append(this.array[this.start]);
/* 578 */       for (int i = this.start + 1; i < this.end; i++) {
/* 579 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 581 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     char[] toCharArray() {
/* 586 */       int size = size();
/* 587 */       char[] result = new char[size];
/* 588 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 589 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\primitives\Chars.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */