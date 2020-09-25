/*    */ package com.google.common.collect;
/*    */ 
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
/*    */ @GwtCompatible
/*    */ final class Count
/*    */   implements Serializable
/*    */ {
/*    */   private int value;
/*    */   
/*    */   Count() {
/* 33 */     this(0);
/*    */   }
/*    */   
/*    */   Count(int value) {
/* 37 */     this.value = value;
/*    */   }
/*    */   
/*    */   public int get() {
/* 41 */     return this.value;
/*    */   }
/*    */   
/*    */   public int getAndAdd(int delta) {
/* 45 */     int result = this.value;
/* 46 */     this.value = result + delta;
/* 47 */     return result;
/*    */   }
/*    */   
/*    */   public int addAndGet(int delta) {
/* 51 */     return this.value += delta;
/*    */   }
/*    */   
/*    */   public void set(int newValue) {
/* 55 */     this.value = newValue;
/*    */   }
/*    */   
/*    */   public int getAndSet(int newValue) {
/* 59 */     int result = this.value;
/* 60 */     this.value = newValue;
/* 61 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object obj) {
/* 71 */     return (obj instanceof Count && ((Count)obj).value == this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 76 */     return Integer.toString(this.value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Count.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */