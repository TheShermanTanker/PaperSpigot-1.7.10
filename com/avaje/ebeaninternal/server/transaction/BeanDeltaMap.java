/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class BeanDeltaMap
/*    */ {
/* 31 */   private Map<String, BeanDeltaList> deltaMap = new HashMap<String, BeanDeltaList>();
/*    */ 
/*    */   
/*    */   public BeanDeltaMap() {}
/*    */   
/*    */   public BeanDeltaMap(List<BeanDelta> deltaBeans) {
/* 37 */     if (deltaBeans != null) {
/* 38 */       for (int i = 0; i < deltaBeans.size(); i++) {
/* 39 */         BeanDelta deltaBean = deltaBeans.get(i);
/* 40 */         addBeanDelta(deltaBean);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     return this.deltaMap.values().toString();
/*    */   }
/*    */   
/*    */   public void addBeanDelta(BeanDelta beanDelta) {
/* 50 */     BeanDescriptor<?> d = beanDelta.getBeanDescriptor();
/* 51 */     BeanDeltaList list = getDeltaBeanList(d);
/* 52 */     list.add(beanDelta);
/*    */   }
/*    */   
/*    */   public Collection<BeanDeltaList> deltaLists() {
/* 56 */     return this.deltaMap.values();
/*    */   }
/*    */   
/*    */   private BeanDeltaList getDeltaBeanList(BeanDescriptor<?> d) {
/* 60 */     BeanDeltaList deltaList = this.deltaMap.get(d.getFullName());
/* 61 */     if (deltaList == null) {
/* 62 */       deltaList = new BeanDeltaList(d);
/* 63 */       this.deltaMap.put(d.getFullName(), deltaList);
/*    */     } 
/* 65 */     return deltaList;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\BeanDeltaMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */