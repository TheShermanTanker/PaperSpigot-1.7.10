/*     */ package com.google.common.primitives;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.ByteOrder;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Comparator;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UnsignedBytes
/*     */ {
/*     */   public static final byte MAX_POWER_OF_TWO = -128;
/*     */   
/*     */   public static int toInt(byte value) {
/*  62 */     return value & 0xFF;
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
/*     */   public static byte checkedCast(long value) {
/*  76 */     Preconditions.checkArgument((value >> 8L == 0L), "out of range: %s", new Object[] { Long.valueOf(value) });
/*  77 */     return (byte)(int)value;
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
/*     */   public static byte saturatedCast(long value) {
/*  89 */     if (value > 255L) {
/*  90 */       return -1;
/*     */     }
/*  92 */     if (value < 0L) {
/*  93 */       return 0;
/*     */     }
/*  95 */     return (byte)(int)value;
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
/*     */   public static int compare(byte a, byte b) {
/* 110 */     return toInt(a) - toInt(b);
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
/*     */   public static byte min(byte... array) {
/* 122 */     Preconditions.checkArgument((array.length > 0));
/* 123 */     int min = toInt(array[0]);
/* 124 */     for (int i = 1; i < array.length; i++) {
/* 125 */       int next = toInt(array[i]);
/* 126 */       if (next < min) {
/* 127 */         min = next;
/*     */       }
/*     */     } 
/* 130 */     return (byte)min;
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
/*     */   public static byte max(byte... array) {
/* 142 */     Preconditions.checkArgument((array.length > 0));
/* 143 */     int max = toInt(array[0]);
/* 144 */     for (int i = 1; i < array.length; i++) {
/* 145 */       int next = toInt(array[i]);
/* 146 */       if (next > max) {
/* 147 */         max = next;
/*     */       }
/*     */     } 
/* 150 */     return (byte)max;
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
/*     */   public static String join(String separator, byte... array) {
/* 163 */     Preconditions.checkNotNull(separator);
/* 164 */     if (array.length == 0) {
/* 165 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 169 */     StringBuilder builder = new StringBuilder(array.length * 5);
/* 170 */     builder.append(toInt(array[0]));
/* 171 */     for (int i = 1; i < array.length; i++) {
/* 172 */       builder.append(separator).append(toInt(array[i]));
/*     */     }
/* 174 */     return builder.toString();
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
/*     */   public static Comparator<byte[]> lexicographicalComparator() {
/* 194 */     return LexicographicalComparatorHolder.BEST_COMPARATOR;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static Comparator<byte[]> lexicographicalComparatorJavaImpl() {
/* 199 */     return LexicographicalComparatorHolder.PureJavaComparator.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static class LexicographicalComparatorHolder
/*     */   {
/* 211 */     static final String UNSAFE_COMPARATOR_NAME = LexicographicalComparatorHolder.class.getName() + "$UnsafeComparator";
/*     */ 
/*     */     
/* 214 */     static final Comparator<byte[]> BEST_COMPARATOR = getBestComparator();
/*     */     
/*     */     @VisibleForTesting
/*     */     enum UnsafeComparator implements Comparator<byte[]> {
/* 218 */       INSTANCE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       static final int BYTE_ARRAY_BASE_OFFSET;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 248 */       static final Unsafe theUnsafe = AccessController.<Unsafe>doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run()
/*     */             {
/*     */               try {
/* 253 */                 Field f = Unsafe.class.getDeclaredField("theUnsafe");
/* 254 */                 f.setAccessible(true);
/* 255 */                 return f.get(null);
/* 256 */               } catch (NoSuchFieldException e) {
/*     */ 
/*     */                 
/* 259 */                 throw new Error();
/* 260 */               } catch (IllegalAccessException e) {
/* 261 */                 throw new Error();
/*     */               } 
/*     */             }
/*     */           });
/*     */       static {
/* 266 */         BYTE_ARRAY_BASE_OFFSET = theUnsafe.arrayBaseOffset(byte[].class);
/*     */ 
/*     */         
/* 269 */         if (theUnsafe.arrayIndexScale(byte[].class) != 1)
/* 270 */           throw new AssertionError(); 
/*     */       }
/*     */       static final boolean littleEndian = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN);
/*     */       
/*     */       public int compare(byte[] left, byte[] right) {
/* 275 */         int minLength = Math.min(left.length, right.length);
/* 276 */         int minWords = minLength / 8;
/*     */ 
/*     */ 
/*     */         
/*     */         int i;
/*     */ 
/*     */         
/* 283 */         for (i = 0; i < minWords * 8; i += 8) {
/* 284 */           long lw = theUnsafe.getLong(left, BYTE_ARRAY_BASE_OFFSET + i);
/* 285 */           long rw = theUnsafe.getLong(right, BYTE_ARRAY_BASE_OFFSET + i);
/* 286 */           long diff = lw ^ rw;
/*     */           
/* 288 */           if (diff != 0L) {
/* 289 */             if (!littleEndian) {
/* 290 */               return UnsignedLongs.compare(lw, rw);
/*     */             }
/*     */ 
/*     */             
/* 294 */             int n = 0;
/*     */             
/* 296 */             int x = (int)diff;
/* 297 */             if (x == 0) {
/* 298 */               x = (int)(diff >>> 32L);
/* 299 */               n = 32;
/*     */             } 
/*     */             
/* 302 */             int y = x << 16;
/* 303 */             if (y == 0) {
/* 304 */               n += 16;
/*     */             } else {
/* 306 */               x = y;
/*     */             } 
/*     */             
/* 309 */             y = x << 8;
/* 310 */             if (y == 0) {
/* 311 */               n += 8;
/*     */             }
/* 313 */             return (int)((lw >>> n & 0xFFL) - (rw >>> n & 0xFFL));
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 318 */         for (i = minWords * 8; i < minLength; i++) {
/* 319 */           int result = UnsignedBytes.compare(left[i], right[i]);
/* 320 */           if (result != 0) {
/* 321 */             return result;
/*     */           }
/*     */         } 
/* 324 */         return left.length - right.length;
/*     */       }
/*     */     }
/*     */     
/*     */     enum PureJavaComparator implements Comparator<byte[]> {
/* 329 */       INSTANCE;
/*     */       
/*     */       public int compare(byte[] left, byte[] right) {
/* 332 */         int minLength = Math.min(left.length, right.length);
/* 333 */         for (int i = 0; i < minLength; i++) {
/* 334 */           int result = UnsignedBytes.compare(left[i], right[i]);
/* 335 */           if (result != 0) {
/* 336 */             return result;
/*     */           }
/*     */         } 
/* 339 */         return left.length - right.length;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Comparator<byte[]> getBestComparator() {
/*     */       try {
/* 349 */         Class<?> theClass = Class.forName(UNSAFE_COMPARATOR_NAME);
/*     */ 
/*     */ 
/*     */         
/* 353 */         Comparator<byte[]> comparator = (Comparator<byte[]>)theClass.getEnumConstants()[0];
/*     */         
/* 355 */         return comparator;
/* 356 */       } catch (Throwable t) {
/* 357 */         return UnsignedBytes.lexicographicalComparatorJavaImpl();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   enum PureJavaComparator implements Comparator<byte[]> {
/*     */     INSTANCE;
/*     */     
/*     */     public int compare(byte[] left, byte[] right) {
/*     */       int minLength = Math.min(left.length, right.length);
/*     */       for (int i = 0; i < minLength; i++) {
/*     */         int result = UnsignedBytes.compare(left[i], right[i]);
/*     */         if (result != 0)
/*     */           return result; 
/*     */       } 
/*     */       return left.length - right.length;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\primitives\UnsignedBytes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */