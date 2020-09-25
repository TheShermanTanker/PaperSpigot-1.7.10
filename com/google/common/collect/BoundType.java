/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
/*    */ import com.google.common.annotations.GwtCompatible;
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
/*    */ @GwtCompatible
/*    */ public enum BoundType
/*    */ {
/* 33 */   OPEN,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   CLOSED;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static BoundType forBoolean(boolean inclusive) {
/* 44 */     return inclusive ? CLOSED : OPEN;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BoundType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */