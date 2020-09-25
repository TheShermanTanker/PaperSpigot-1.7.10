/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
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
/*    */ public enum BoundType
/*    */ {
/* 31 */   OPEN
/*    */   {
/*    */     BoundType flip() {
/* 34 */       return CLOSED;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */   
/* 40 */   CLOSED
/*    */   {
/*    */     BoundType flip() {
/* 43 */       return OPEN;
/*    */     }
/*    */   };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static BoundType forBoolean(boolean inclusive) {
/* 51 */     return inclusive ? CLOSED : OPEN;
/*    */   }
/*    */   
/*    */   abstract BoundType flip();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\BoundType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */