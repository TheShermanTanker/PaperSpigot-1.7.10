/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Preconditions
/*     */ {
/*     */   public static void checkArgument(boolean expression) {
/*  71 */     if (!expression) {
/*  72 */       throw new IllegalArgumentException();
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
/*     */   public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
/*  87 */     if (!expression) {
/*  88 */       throw new IllegalArgumentException(String.valueOf(errorMessage));
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
/*     */   public static void checkArgument(boolean expression, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
/* 114 */     if (!expression) {
/* 115 */       throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
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
/*     */   public static void checkState(boolean expression) {
/* 128 */     if (!expression) {
/* 129 */       throw new IllegalStateException();
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
/*     */   public static void checkState(boolean expression, @Nullable Object errorMessage) {
/* 144 */     if (!expression) {
/* 145 */       throw new IllegalStateException(String.valueOf(errorMessage));
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
/*     */   public static void checkState(boolean expression, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
/* 171 */     if (!expression) {
/* 172 */       throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
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
/*     */   public static <T> T checkNotNull(T reference) {
/* 186 */     if (reference == null) {
/* 187 */       throw new NullPointerException();
/*     */     }
/* 189 */     return reference;
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
/*     */   public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
/* 203 */     if (reference == null) {
/* 204 */       throw new NullPointerException(String.valueOf(errorMessage));
/*     */     }
/* 206 */     return reference;
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
/*     */   public static <T> T checkNotNull(T reference, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
/* 229 */     if (reference == null)
/*     */     {
/* 231 */       throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
/*     */     }
/*     */     
/* 234 */     return reference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int checkElementIndex(int index, int size) {
/* 280 */     return checkElementIndex(index, size, "index");
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
/*     */   public static int checkElementIndex(int index, int size, @Nullable String desc) {
/* 300 */     if (index < 0 || index >= size) {
/* 301 */       throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
/*     */     }
/* 303 */     return index;
/*     */   }
/*     */   
/*     */   private static String badElementIndex(int index, int size, String desc) {
/* 307 */     if (index < 0)
/* 308 */       return format("%s (%s) must not be negative", new Object[] { desc, Integer.valueOf(index) }); 
/* 309 */     if (size < 0) {
/* 310 */       throw new IllegalArgumentException("negative size: " + size);
/*     */     }
/* 312 */     return format("%s (%s) must be less than size (%s)", new Object[] { desc, Integer.valueOf(index), Integer.valueOf(size) });
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
/*     */   public static int checkPositionIndex(int index, int size) {
/* 330 */     return checkPositionIndex(index, size, "index");
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
/*     */   public static int checkPositionIndex(int index, int size, @Nullable String desc) {
/* 350 */     if (index < 0 || index > size) {
/* 351 */       throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
/*     */     }
/* 353 */     return index;
/*     */   }
/*     */   
/*     */   private static String badPositionIndex(int index, int size, String desc) {
/* 357 */     if (index < 0)
/* 358 */       return format("%s (%s) must not be negative", new Object[] { desc, Integer.valueOf(index) }); 
/* 359 */     if (size < 0) {
/* 360 */       throw new IllegalArgumentException("negative size: " + size);
/*     */     }
/* 362 */     return format("%s (%s) must not be greater than size (%s)", new Object[] { desc, Integer.valueOf(index), Integer.valueOf(size) });
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
/*     */   public static void checkPositionIndexes(int start, int end, int size) {
/* 383 */     if (start < 0 || end < start || end > size) {
/* 384 */       throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
/*     */     }
/*     */   }
/*     */   
/*     */   private static String badPositionIndexes(int start, int end, int size) {
/* 389 */     if (start < 0 || start > size) {
/* 390 */       return badPositionIndex(start, size, "start index");
/*     */     }
/* 392 */     if (end < 0 || end > size) {
/* 393 */       return badPositionIndex(end, size, "end index");
/*     */     }
/*     */     
/* 396 */     return format("end index (%s) must not be less than start index (%s)", new Object[] { Integer.valueOf(end), Integer.valueOf(start) });
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
/*     */   @VisibleForTesting
/*     */   static String format(String template, @Nullable Object... args) {
/* 414 */     template = String.valueOf(template);
/*     */ 
/*     */     
/* 417 */     StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
/*     */     
/* 419 */     int templateStart = 0;
/* 420 */     int i = 0;
/* 421 */     while (i < args.length) {
/* 422 */       int placeholderStart = template.indexOf("%s", templateStart);
/* 423 */       if (placeholderStart == -1) {
/*     */         break;
/*     */       }
/* 426 */       builder.append(template.substring(templateStart, placeholderStart));
/* 427 */       builder.append(args[i++]);
/* 428 */       templateStart = placeholderStart + 2;
/*     */     } 
/* 430 */     builder.append(template.substring(templateStart));
/*     */ 
/*     */     
/* 433 */     if (i < args.length) {
/* 434 */       builder.append(" [");
/* 435 */       builder.append(args[i++]);
/* 436 */       while (i < args.length) {
/* 437 */         builder.append(", ");
/* 438 */         builder.append(args[i++]);
/*     */       } 
/* 440 */       builder.append(']');
/*     */     } 
/*     */     
/* 443 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Preconditions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */