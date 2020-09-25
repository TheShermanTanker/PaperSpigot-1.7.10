/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class BeanDeltaList
/*    */ {
/*    */   private final BeanDescriptor<?> beanDescriptor;
/* 33 */   private final List<BeanDelta> deltaBeans = new ArrayList<BeanDelta>();
/*    */   
/*    */   public BeanDeltaList(BeanDescriptor<?> beanDescriptor) {
/* 36 */     this.beanDescriptor = beanDescriptor;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     return this.deltaBeans.toString();
/*    */   }
/*    */   
/*    */   public BeanDescriptor<?> getBeanDescriptor() {
/* 44 */     return this.beanDescriptor;
/*    */   }
/*    */   
/*    */   public void add(BeanDelta b) {
/* 48 */     this.deltaBeans.add(b);
/*    */   }
/*    */   
/*    */   public List<BeanDelta> getDeltaBeans() {
/* 52 */     return this.deltaBeans;
/*    */   }
/*    */   
/*    */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/* 56 */     for (int i = 0; i < this.deltaBeans.size(); i++)
/* 57 */       ((BeanDelta)this.deltaBeans.get(i)).writeBinaryMessage(msgList); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\BeanDeltaList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */