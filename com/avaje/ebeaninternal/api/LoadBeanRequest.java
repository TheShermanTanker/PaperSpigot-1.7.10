/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebean.bean.EntityBeanIntercept;
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
/*    */ public class LoadBeanRequest
/*    */   extends LoadRequest
/*    */ {
/*    */   private final List<EntityBeanIntercept> batch;
/*    */   private final LoadBeanContext loadContext;
/*    */   private final String lazyLoadProperty;
/*    */   private final boolean loadCache;
/*    */   
/*    */   public LoadBeanRequest(LoadBeanContext loadContext, List<EntityBeanIntercept> batch, Transaction transaction, int batchSize, boolean lazy, String lazyLoadProperty, boolean loadCache) {
/* 43 */     super(transaction, batchSize, lazy);
/* 44 */     this.loadContext = loadContext;
/* 45 */     this.batch = batch;
/* 46 */     this.lazyLoadProperty = lazyLoadProperty;
/* 47 */     this.loadCache = loadCache;
/*    */   }
/*    */   
/*    */   public boolean isLoadCache() {
/* 51 */     return this.loadCache;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 55 */     String fullPath = this.loadContext.getFullPath();
/* 56 */     String s = "path:" + fullPath + " batch:" + this.batchSize + " actual:" + this.batch.size();
/*    */     
/* 58 */     return s;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<EntityBeanIntercept> getBatch() {
/* 65 */     return this.batch;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LoadBeanContext getLoadContext() {
/* 72 */     return this.loadContext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLazyLoadProperty() {
/* 79 */     return this.lazyLoadProperty;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\LoadBeanRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */