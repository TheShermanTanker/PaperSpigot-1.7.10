/*    */ package com.google.common.cache;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
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
/*    */ @Beta
/*    */ public enum RemovalCause
/*    */ {
/* 38 */   EXPLICIT
/*    */   {
/*    */     boolean wasEvicted() {
/* 41 */       return false;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   REPLACED
/*    */   {
/*    */     boolean wasEvicted() {
/* 54 */       return false;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   COLLECTED
/*    */   {
/*    */     boolean wasEvicted() {
/* 66 */       return true;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 74 */   EXPIRED
/*    */   {
/*    */     boolean wasEvicted() {
/* 77 */       return true;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 85 */   SIZE
/*    */   {
/*    */     boolean wasEvicted() {
/* 88 */       return true;
/*    */     }
/*    */   };
/*    */   
/*    */   abstract boolean wasEvicted();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\cache\RemovalCause.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */