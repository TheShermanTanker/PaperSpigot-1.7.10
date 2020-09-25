/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransactionEventBeans
/*    */ {
/* 36 */   ArrayList<PersistRequestBean<?>> requests = new ArrayList<PersistRequestBean<?>>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<PersistRequestBean<?>> getRequests() {
/* 42 */     return this.requests;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(PersistRequestBean<?> request) {
/* 50 */     this.requests.add(request);
/*    */   }
/*    */   
/*    */   public void notifyCache() {
/* 54 */     for (int i = 0; i < this.requests.size(); i++)
/* 55 */       ((PersistRequestBean)this.requests.get(i)).notifyCache(); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\TransactionEventBeans.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */