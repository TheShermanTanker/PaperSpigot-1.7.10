/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.util.Arrays;
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
/*     */ @GwtCompatible
/*     */ public final class Objects
/*     */ {
/*     */   public static boolean equal(@Nullable Object a, @Nullable Object b) {
/*  51 */     return (a == b || (a != null && a.equals(b)));
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
/*     */   public static int hashCode(@Nullable Object... objects) {
/*  70 */     return Arrays.hashCode(objects);
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
/*     */   public static ToStringHelper toStringHelper(Object self) {
/* 106 */     return new ToStringHelper(simpleName(self.getClass()));
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
/*     */   public static ToStringHelper toStringHelper(Class<?> clazz) {
/* 120 */     return new ToStringHelper(simpleName(clazz));
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
/*     */   public static ToStringHelper toStringHelper(String className) {
/* 132 */     return new ToStringHelper(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String simpleName(Class<?> clazz) {
/* 140 */     String name = clazz.getName();
/*     */ 
/*     */ 
/*     */     
/* 144 */     name = name.replaceAll("\\$[0-9]+", "\\$");
/*     */ 
/*     */     
/* 147 */     int start = name.lastIndexOf('$');
/*     */ 
/*     */ 
/*     */     
/* 151 */     if (start == -1) {
/* 152 */       start = name.lastIndexOf('.');
/*     */     }
/* 154 */     return name.substring(start + 1);
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
/*     */   public static <T> T firstNonNull(@Nullable T first, @Nullable T second) {
/* 174 */     return (first != null) ? first : Preconditions.<T>checkNotNull(second);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class ToStringHelper
/*     */   {
/*     */     private final StringBuilder builder;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean needsSeparator = false;
/*     */ 
/*     */ 
/*     */     
/*     */     private ToStringHelper(String className) {
/* 191 */       Preconditions.checkNotNull(className);
/* 192 */       this.builder = (new StringBuilder(32)).append(className).append('{');
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper add(String name, @Nullable Object value) {
/* 201 */       Preconditions.checkNotNull(name);
/* 202 */       maybeAppendSeparator().append(name).append('=').append(value);
/* 203 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper addValue(@Nullable Object value) {
/* 213 */       maybeAppendSeparator().append(value);
/* 214 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*     */       try {
/* 223 */         return this.builder.append('}').toString();
/*     */       }
/*     */       finally {
/*     */         
/* 227 */         this.builder.setLength(this.builder.length() - 1);
/*     */       } 
/*     */     }
/*     */     
/*     */     private StringBuilder maybeAppendSeparator() {
/* 232 */       if (this.needsSeparator) {
/* 233 */         return this.builder.append(", ");
/*     */       }
/* 235 */       this.needsSeparator = true;
/* 236 */       return this.builder;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Objects.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */