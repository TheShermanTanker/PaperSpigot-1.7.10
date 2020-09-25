/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Chars
/*     */ {
/*     */   public static final int BYTES = 2;
/*     */   
/*     */   public static int hashCode(char value) {
/*  64 */     return value;
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
/*  76 */     char result = (char)(int)value;
/*  77 */     Preconditions.checkArgument((result == value), "Out of range: %s", new Object[] { Long.valueOf(value) });
/*  78 */     return result;
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
/*  90 */     if (value > 65535L) {
/*  91 */       return Character.MAX_VALUE;
/*     */     }
/*  93 */     if (value < 0L) {
/*  94 */       return Character.MIN_VALUE;
/*     */     }
/*  96 */     return (char)(int)value;
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
/*     */   public static int compare(char a, char b) {
/* 109 */     return a - b;
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
/* 122 */     for (char value : array) {
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
/*     */   public static int indexOf(char[] array, char target) {
/* 140 */     return indexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOf(char[] array, char target, int start, int end) {
/* 146 */     for (int i = start; i < end; i++) {
/* 147 */       if (array[i] == target) {
/* 148 */         return i;
/*     */       }
/*     */     } 
/* 151 */     return -1;
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
/*     */   public static int lastIndexOf(char[] array, char target) {
/* 194 */     return lastIndexOf(array, target, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lastIndexOf(char[] array, char target, int start, int end) {
/* 200 */     for (int i = end - 1; i >= start; i--) {
/* 201 */       if (array[i] == target) {
/* 202 */         return i;
/*     */       }
/*     */     } 
/* 205 */     return -1;
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
/* 217 */     Preconditions.checkArgument((array.length > 0));
/* 218 */     char min = array[0];
/* 219 */     for (int i = 1; i < array.length; i++) {
/* 220 */       if (array[i] < min) {
/* 221 */         min = array[i];
/*     */       }
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
/*     */   public static char max(char... array) {
/* 236 */     Preconditions.checkArgument((array.length > 0));
/* 237 */     char max = array[0];
/* 238 */     for (int i = 1; i < array.length; i++) {
/* 239 */       if (array[i] > max) {
/* 240 */         max = array[i];
/*     */       }
/*     */     } 
/* 243 */     return max;
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
/* 256 */     int length = 0;
/* 257 */     for (char[] array : arrays) {
/* 258 */       length += array.length;
/*     */     }
/* 260 */     char[] result = new char[length];
/* 261 */     int pos = 0;
/* 262 */     for (char[] array : arrays) {
/* 263 */       System.arraycopy(array, 0, result, pos, array.length);
/* 264 */       pos += array.length;
/*     */     } 
/* 266 */     return result;
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
/* 282 */     return new byte[] { (byte)(value >> 8), (byte)value };
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
/* 301 */     Preconditions.checkArgument((bytes.length >= 2), "array too small: %s < %s", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(2) });
/*     */     
/* 303 */     return fromBytes(bytes[0], bytes[1]);
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
/* 315 */     return (char)(b1 << 8 | b2 & 0xFF);
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
/* 336 */     Preconditions.checkArgument((minLength >= 0), "Invalid minLength: %s", new Object[] { Integer.valueOf(minLength) });
/* 337 */     Preconditions.checkArgument((padding >= 0), "Invalid padding: %s", new Object[] { Integer.valueOf(padding) });
/* 338 */     return (array.length < minLength) ? copyOf(array, minLength + padding) : array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static char[] copyOf(char[] original, int length) {
/* 345 */     char[] copy = new char[length];
/* 346 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, length));
/* 347 */     return copy;
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
/* 360 */     Preconditions.checkNotNull(separator);
/* 361 */     int len = array.length;
/* 362 */     if (len == 0) {
/* 363 */       return "";
/*     */     }
/*     */     
/* 366 */     StringBuilder builder = new StringBuilder(len + separator.length() * (len - 1));
/*     */     
/* 368 */     builder.append(array[0]);
/* 369 */     for (int i = 1; i < len; i++) {
/* 370 */       builder.append(separator).append(array[i]);
/*     */     }
/* 372 */     return builder.toString();
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
/* 392 */     return LexicographicalComparator.INSTANCE;
/*     */   }
/*     */   
/*     */   private enum LexicographicalComparator implements Comparator<char[]> {
/* 396 */     INSTANCE;
/*     */ 
/*     */     
/*     */     public int compare(char[] left, char[] right) {
/* 400 */       int minLength = Math.min(left.length, right.length);
/* 401 */       for (int i = 0; i < minLength; i++) {
/* 402 */         int result = Chars.compare(left[i], right[i]);
/* 403 */         if (result != 0) {
/* 404 */           return result;
/*     */         }
/*     */       } 
/* 407 */       return left.length - right.length;
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
/* 426 */     if (collection instanceof CharArrayAsList) {
/* 427 */       return ((CharArrayAsList)collection).toCharArray();
/*     */     }
/*     */     
/* 430 */     Object[] boxedArray = collection.toArray();
/* 431 */     int len = boxedArray.length;
/* 432 */     char[] array = new char[len];
/* 433 */     for (int i = 0; i < len; i++)
/*     */     {
/* 435 */       array[i] = ((Character)Preconditions.checkNotNull(boxedArray[i])).charValue();
/*     */     }
/* 437 */     return array;
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
/* 455 */     if (backingArray.length == 0) {
/* 456 */       return Collections.emptyList();
/*     */     }
/* 458 */     return new CharArrayAsList(backingArray);
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
/* 469 */       this(array, 0, array.length);
/*     */     }
/*     */     
/*     */     CharArrayAsList(char[] array, int start, int end) {
/* 473 */       this.array = array;
/* 474 */       this.start = start;
/* 475 */       this.end = end;
/*     */     }
/*     */     
/*     */     public int size() {
/* 479 */       return this.end - this.start;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 483 */       return false;
/*     */     }
/*     */     
/*     */     public Character get(int index) {
/* 487 */       Preconditions.checkElementIndex(index, size());
/* 488 */       return Character.valueOf(this.array[this.start + index]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object target) {
/* 493 */       return (target instanceof Character && Chars.indexOf(this.array, ((Character)target).charValue(), this.start, this.end) != -1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Object target) {
/* 499 */       if (target instanceof Character) {
/* 500 */         int i = Chars.indexOf(this.array, ((Character)target).charValue(), this.start, this.end);
/* 501 */         if (i >= 0) {
/* 502 */           return i - this.start;
/*     */         }
/*     */       } 
/* 505 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object target) {
/* 510 */       if (target instanceof Character) {
/* 511 */         int i = Chars.lastIndexOf(this.array, ((Character)target).charValue(), this.start, this.end);
/* 512 */         if (i >= 0) {
/* 513 */           return i - this.start;
/*     */         }
/*     */       } 
/* 516 */       return -1;
/*     */     }
/*     */     
/*     */     public Character set(int index, Character element) {
/* 520 */       Preconditions.checkElementIndex(index, size());
/* 521 */       char oldValue = this.array[this.start + index];
/* 522 */       this.array[this.start + index] = ((Character)Preconditions.checkNotNull(element)).charValue();
/* 523 */       return Character.valueOf(oldValue);
/*     */     }
/*     */     
/*     */     public List<Character> subList(int fromIndex, int toIndex) {
/* 527 */       int size = size();
/* 528 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
/* 529 */       if (fromIndex == toIndex) {
/* 530 */         return Collections.emptyList();
/*     */       }
/* 532 */       return new CharArrayAsList(this.array, this.start + fromIndex, this.start + toIndex);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 536 */       if (object == this) {
/* 537 */         return true;
/*     */       }
/* 539 */       if (object instanceof CharArrayAsList) {
/* 540 */         CharArrayAsList that = (CharArrayAsList)object;
/* 541 */         int size = size();
/* 542 */         if (that.size() != size) {
/* 543 */           return false;
/*     */         }
/* 545 */         for (int i = 0; i < size; i++) {
/* 546 */           if (this.array[this.start + i] != that.array[that.start + i]) {
/* 547 */             return false;
/*     */           }
/*     */         } 
/* 550 */         return true;
/*     */       } 
/* 552 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 556 */       int result = 1;
/* 557 */       for (int i = this.start; i < this.end; i++) {
/* 558 */         result = 31 * result + Chars.hashCode(this.array[i]);
/*     */       }
/* 560 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 564 */       StringBuilder builder = new StringBuilder(size() * 3);
/* 565 */       builder.append('[').append(this.array[this.start]);
/* 566 */       for (int i = this.start + 1; i < this.end; i++) {
/* 567 */         builder.append(", ").append(this.array[i]);
/*     */       }
/* 569 */       return builder.append(']').toString();
/*     */     }
/*     */ 
/*     */     
/*     */     char[] toCharArray() {
/* 574 */       int size = size();
/* 575 */       char[] result = new char[size];
/* 576 */       System.arraycopy(this.array, this.start, result, 0, size);
/* 577 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\Chars.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */