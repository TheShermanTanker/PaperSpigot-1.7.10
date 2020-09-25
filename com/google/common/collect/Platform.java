/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.annotations.GwtIncompatible;
/*    */ import java.lang.reflect.Array;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(emulated = true)
/*    */ class Platform
/*    */ {
/*    */   static <T> T[] clone(T[] array) {
/* 36 */     return (T[])array.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void unsafeArrayCopy(Object[] src, int srcPos, Object[] dest, int destPos, int length) {
/* 53 */     System.arraycopy(src, srcPos, dest, destPos, length);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @GwtIncompatible("Array.newInstance(Class, int)")
/*    */   static <T> T[] newArray(Class<T> type, int length) {
/* 65 */     return (T[])Array.newInstance(type, length);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <T> T[] newArray(T[] reference, int length) {
/* 76 */     Class<?> type = reference.getClass().getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     T[] result = (T[])Array.newInstance(type, length);
/* 82 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static MapMaker tryWeakKeys(MapMaker mapMaker) {
/* 92 */     return mapMaker.weakKeys();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Platform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */