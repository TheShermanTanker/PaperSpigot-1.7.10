/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebean.bean.BeanCollection;
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
/*    */ 
/*    */ 
/*    */ public class LoadManyRequest
/*    */   extends LoadRequest
/*    */ {
/*    */   private final List<BeanCollection<?>> batch;
/*    */   private final LoadManyContext loadContext;
/*    */   private final boolean onlyIds;
/*    */   private final boolean loadCache;
/*    */   
/*    */   public LoadManyRequest(LoadManyContext loadContext, List<BeanCollection<?>> batch, Transaction transaction, int batchSize, boolean lazy, boolean onlyIds, boolean loadCache) {
/* 45 */     super(transaction, batchSize, lazy);
/* 46 */     this.loadContext = loadContext;
/* 47 */     this.batch = batch;
/* 48 */     this.onlyIds = onlyIds;
/* 49 */     this.loadCache = loadCache;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 53 */     String fullPath = this.loadContext.getFullPath();
/* 54 */     String s = "path:" + fullPath + " batch:" + this.batchSize + " actual:" + this.batch.size();
/*    */     
/* 56 */     return s;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<BeanCollection<?>> getBatch() {
/* 63 */     return this.batch;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LoadManyContext getLoadContext() {
/* 70 */     return this.loadContext;
/*    */   }
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
/*    */   public boolean isOnlyIds() {
/* 83 */     return this.onlyIds;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isLoadCache() {
/* 90 */     return this.loadCache;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\LoadManyRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */