/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.util.Collection;
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
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class ObjectArrays
/*     */ {
/*     */   @GwtIncompatible("Array.newInstance(Class, int)")
/*     */   public static <T> T[] newArray(Class<T> type, int length) {
/*  44 */     return Platform.newArray(type, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T[] newArray(T[] reference, int length) {
/*  55 */     return Platform.newArray(reference, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("Array.newInstance(Class, int)")
/*     */   public static <T> T[] concat(T[] first, T[] second, Class<T> type) {
/*  67 */     T[] result = newArray(type, first.length + second.length);
/*  68 */     Platform.unsafeArrayCopy((Object[])first, 0, (Object[])result, 0, first.length);
/*  69 */     Platform.unsafeArrayCopy((Object[])second, 0, (Object[])result, first.length, second.length);
/*  70 */     return result;
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
/*     */   public static <T> T[] concat(@Nullable T element, T[] array) {
/*  83 */     T[] result = newArray(array, array.length + 1);
/*  84 */     result[0] = element;
/*  85 */     Platform.unsafeArrayCopy((Object[])array, 0, (Object[])result, 1, array.length);
/*  86 */     return result;
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
/*     */   public static <T> T[] concat(T[] array, @Nullable T element) {
/*  99 */     T[] result = arraysCopyOf(array, array.length + 1);
/* 100 */     result[array.length] = element;
/* 101 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   static <T> T[] arraysCopyOf(T[] original, int newLength) {
/* 106 */     T[] copy = newArray(original, newLength);
/* 107 */     Platform.unsafeArrayCopy((Object[])original, 0, (Object[])copy, 0, Math.min(original.length, newLength));
/*     */     
/* 109 */     return copy;
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
/*     */   static <T> T[] toArrayImpl(Collection<?> c, T[] array) {
/* 137 */     int size = c.size();
/* 138 */     if (array.length < size) {
/* 139 */       array = newArray(array, size);
/*     */     }
/* 141 */     fillArray(c, (Object[])array);
/* 142 */     if (array.length > size) {
/* 143 */       array[size] = null;
/*     */     }
/* 145 */     return array;
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
/*     */   static Object[] toArrayImpl(Collection<?> c) {
/* 163 */     return fillArray(c, new Object[c.size()]);
/*     */   }
/*     */   
/*     */   private static Object[] fillArray(Iterable<?> elements, Object[] array) {
/* 167 */     int i = 0;
/* 168 */     for (Object element : elements) {
/* 169 */       array[i++] = element;
/*     */     }
/* 171 */     return array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void swap(Object[] array, int i, int j) {
/* 178 */     Object temp = array[i];
/* 179 */     array[i] = array[j];
/* 180 */     array[j] = temp;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ObjectArrays.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */