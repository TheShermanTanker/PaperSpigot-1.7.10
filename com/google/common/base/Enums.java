/*    */ package com.google.common.base;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.Serializable;
/*    */ import javax.annotation.Nullable;
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
/*    */ @GwtCompatible
/*    */ @Beta
/*    */ public final class Enums
/*    */ {
/*    */   public static <T extends Enum<T>> Function<String, T> valueOfFunction(Class<T> enumClass) {
/* 50 */     return new ValueOfFunction<T>(enumClass);
/*    */   }
/*    */ 
/*    */   
/*    */   private static final class ValueOfFunction<T extends Enum<T>>
/*    */     implements Function<String, T>, Serializable
/*    */   {
/*    */     private final Class<T> enumClass;
/*    */     
/*    */     private static final long serialVersionUID = 0L;
/*    */ 
/*    */     
/*    */     private ValueOfFunction(Class<T> enumClass) {
/* 63 */       this.enumClass = Preconditions.<Class<T>>checkNotNull(enumClass);
/*    */     }
/*    */ 
/*    */     
/*    */     public T apply(String value) {
/*    */       try {
/* 69 */         return Enum.valueOf(this.enumClass, value);
/* 70 */       } catch (IllegalArgumentException e) {
/* 71 */         return null;
/*    */       } 
/*    */     }
/*    */     
/*    */     public boolean equals(@Nullable Object obj) {
/* 76 */       return (obj instanceof ValueOfFunction && this.enumClass.equals(((ValueOfFunction)obj).enumClass));
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 81 */       return this.enumClass.hashCode();
/*    */     }
/*    */     
/*    */     public String toString() {
/* 85 */       return "Enums.valueOf(" + this.enumClass + ")";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Enums.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */