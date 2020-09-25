/*     */ package net.minecraft.util.com.google.common.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Field;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @Beta
/*     */ public final class Enums
/*     */ {
/*     */   @GwtIncompatible("reflection")
/*     */   public static Field getField(Enum<?> enumValue) {
/*  52 */     Class<?> clazz = enumValue.getDeclaringClass();
/*     */     try {
/*  54 */       return clazz.getDeclaredField(enumValue.name());
/*  55 */     } catch (NoSuchFieldException impossible) {
/*  56 */       throw new AssertionError(impossible);
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
/*     */   @Deprecated
/*     */   public static <T extends Enum<T>> Function<String, T> valueOfFunction(Class<T> enumClass) {
/*  74 */     return new ValueOfFunction<T>(enumClass);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ValueOfFunction<T extends Enum<T>>
/*     */     implements Function<String, T>, Serializable
/*     */   {
/*     */     private final Class<T> enumClass;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     private ValueOfFunction(Class<T> enumClass) {
/*  87 */       this.enumClass = Preconditions.<Class<T>>checkNotNull(enumClass);
/*     */     }
/*     */ 
/*     */     
/*     */     public T apply(String value) {
/*     */       try {
/*  93 */         return Enum.valueOf(this.enumClass, value);
/*  94 */       } catch (IllegalArgumentException e) {
/*  95 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 100 */       return (obj instanceof ValueOfFunction && this.enumClass.equals(((ValueOfFunction)obj).enumClass));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 104 */       return this.enumClass.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 108 */       return "Enums.valueOf(" + this.enumClass + ")";
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
/*     */   public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> enumClass, String value) {
/* 123 */     Preconditions.checkNotNull(enumClass);
/* 124 */     Preconditions.checkNotNull(value);
/*     */     try {
/* 126 */       return Optional.of(Enum.valueOf(enumClass, value));
/* 127 */     } catch (IllegalArgumentException iae) {
/* 128 */       return Optional.absent();
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
/*     */   public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> enumClass) {
/* 141 */     return new StringConverter<T>(enumClass);
/*     */   }
/*     */   
/*     */   private static final class StringConverter<T extends Enum<T>>
/*     */     extends Converter<String, T> implements Serializable {
/*     */     private final Class<T> enumClass;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     StringConverter(Class<T> enumClass) {
/* 150 */       this.enumClass = Preconditions.<Class<T>>checkNotNull(enumClass);
/*     */     }
/*     */ 
/*     */     
/*     */     protected T doForward(String value) {
/* 155 */       return Enum.valueOf(this.enumClass, value);
/*     */     }
/*     */ 
/*     */     
/*     */     protected String doBackward(T enumValue) {
/* 160 */       return enumValue.name();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object object) {
/* 165 */       if (object instanceof StringConverter) {
/* 166 */         StringConverter<?> that = (StringConverter)object;
/* 167 */         return this.enumClass.equals(that.enumClass);
/*     */       } 
/* 169 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 174 */       return this.enumClass.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 179 */       return "Enums.stringConverter(" + this.enumClass.getName() + ".class)";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\base\Enums.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */