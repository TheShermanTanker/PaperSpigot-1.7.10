/*     */ package net.minecraft.util.com.google.common.base;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  55 */     return (a == b || (a != null && a.equals(b)));
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
/*     */   public static int hashCode(@Nullable Object... objects) {
/*  76 */     return Arrays.hashCode(objects);
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
/*     */   public static ToStringHelper toStringHelper(Object self) {
/* 119 */     return new ToStringHelper(simpleName(self.getClass()));
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
/* 133 */     return new ToStringHelper(simpleName(clazz));
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
/* 145 */     return new ToStringHelper(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String simpleName(Class<?> clazz) {
/* 153 */     String name = clazz.getName();
/*     */ 
/*     */ 
/*     */     
/* 157 */     name = name.replaceAll("\\$[0-9]+", "\\$");
/*     */ 
/*     */     
/* 160 */     int start = name.lastIndexOf('$');
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (start == -1) {
/* 165 */       start = name.lastIndexOf('.');
/*     */     }
/* 167 */     return name.substring(start + 1);
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
/*     */   public static <T> T firstNonNull(@Nullable T first, @Nullable T second) {
/* 188 */     return (first != null) ? first : Preconditions.<T>checkNotNull(second);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class ToStringHelper
/*     */   {
/*     */     private final String className;
/*     */ 
/*     */     
/* 199 */     private ValueHolder holderHead = new ValueHolder();
/* 200 */     private ValueHolder holderTail = this.holderHead;
/*     */ 
/*     */     
/*     */     private boolean omitNullValues = false;
/*     */ 
/*     */     
/*     */     private ToStringHelper(String className) {
/* 207 */       this.className = Preconditions.<String>checkNotNull(className);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper omitNullValues() {
/* 218 */       this.omitNullValues = true;
/* 219 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper add(String name, @Nullable Object value) {
/* 229 */       return addHolder(name, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper add(String name, boolean value) {
/* 239 */       return addHolder(name, String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper add(String name, char value) {
/* 249 */       return addHolder(name, String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper add(String name, double value) {
/* 259 */       return addHolder(name, String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper add(String name, float value) {
/* 269 */       return addHolder(name, String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper add(String name, int value) {
/* 279 */       return addHolder(name, String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper add(String name, long value) {
/* 289 */       return addHolder(name, String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper addValue(@Nullable Object value) {
/* 299 */       return addHolder(value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper addValue(boolean value) {
/* 311 */       return addHolder(String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper addValue(char value) {
/* 323 */       return addHolder(String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper addValue(double value) {
/* 335 */       return addHolder(String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper addValue(float value) {
/* 347 */       return addHolder(String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper addValue(int value) {
/* 359 */       return addHolder(String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper addValue(long value) {
/* 371 */       return addHolder(String.valueOf(value));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 386 */       boolean omitNullValuesSnapshot = this.omitNullValues;
/* 387 */       String nextSeparator = "";
/* 388 */       StringBuilder builder = (new StringBuilder(32)).append(this.className).append('{');
/*     */       
/* 390 */       for (ValueHolder valueHolder = this.holderHead.next; valueHolder != null; 
/* 391 */         valueHolder = valueHolder.next) {
/* 392 */         if (!omitNullValuesSnapshot || valueHolder.value != null) {
/* 393 */           builder.append(nextSeparator);
/* 394 */           nextSeparator = ", ";
/*     */           
/* 396 */           if (valueHolder.name != null) {
/* 397 */             builder.append(valueHolder.name).append('=');
/*     */           }
/* 399 */           builder.append(valueHolder.value);
/*     */         } 
/*     */       } 
/* 402 */       return builder.append('}').toString();
/*     */     }
/*     */     
/*     */     private ValueHolder addHolder() {
/* 406 */       ValueHolder valueHolder = new ValueHolder();
/* 407 */       this.holderTail = this.holderTail.next = valueHolder;
/* 408 */       return valueHolder;
/*     */     }
/*     */     
/*     */     private ToStringHelper addHolder(@Nullable Object value) {
/* 412 */       ValueHolder valueHolder = addHolder();
/* 413 */       valueHolder.value = value;
/* 414 */       return this;
/*     */     }
/*     */     
/*     */     private ToStringHelper addHolder(String name, @Nullable Object value) {
/* 418 */       ValueHolder valueHolder = addHolder();
/* 419 */       valueHolder.value = value;
/* 420 */       valueHolder.name = Preconditions.<String>checkNotNull(name);
/* 421 */       return this;
/*     */     }
/*     */     
/*     */     private static final class ValueHolder {
/*     */       String name;
/*     */       Object value;
/*     */       ValueHolder next;
/*     */       
/*     */       private ValueHolder() {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\base\Objects.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */