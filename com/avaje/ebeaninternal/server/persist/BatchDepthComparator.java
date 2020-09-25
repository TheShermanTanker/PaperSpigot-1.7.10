/*    */ package com.avaje.ebeaninternal.server.persist;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
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
/*    */ public class BatchDepthComparator
/*    */   implements Comparator<BatchedBeanHolder>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 264611821665757991L;
/*    */   
/*    */   public int compare(BatchedBeanHolder b1, BatchedBeanHolder b2) {
/* 40 */     if (b1.getOrder() < b2.getOrder()) {
/* 41 */       return -1;
/*    */     }
/* 43 */     if (b1.getOrder() == b2.getOrder()) {
/* 44 */       return 0;
/*    */     }
/* 46 */     return 1;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BatchDepthComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */