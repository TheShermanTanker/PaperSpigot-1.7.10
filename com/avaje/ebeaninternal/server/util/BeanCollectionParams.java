/*    */ package com.avaje.ebeaninternal.server.util;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
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
/*    */ public class BeanCollectionParams
/*    */ {
/*    */   private final SpiQuery.Type manyType;
/*    */   
/*    */   public BeanCollectionParams(SpiQuery.Type manyType) {
/* 35 */     this.manyType = manyType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpiQuery.Type getManyType() {
/* 42 */     return this.manyType;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\serve\\util\BeanCollectionParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */