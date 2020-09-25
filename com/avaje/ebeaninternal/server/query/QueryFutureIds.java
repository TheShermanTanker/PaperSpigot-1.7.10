/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.FutureIds;
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.FutureTask;
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
/*    */ public class QueryFutureIds<T>
/*    */   extends BaseFuture<List<Object>>
/*    */   implements FutureIds<T>
/*    */ {
/*    */   private final SpiQuery<T> query;
/*    */   
/*    */   public QueryFutureIds(SpiQuery<T> query, FutureTask<List<Object>> futureTask) {
/* 37 */     super(futureTask);
/* 38 */     this.query = query;
/*    */   }
/*    */   
/*    */   public Query<T> getQuery() {
/* 42 */     return (Query<T>)this.query;
/*    */   }
/*    */   
/*    */   public List<Object> getPartialIds() {
/* 46 */     return this.query.getIdList();
/*    */   }
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 50 */     this.query.cancel();
/* 51 */     return super.cancel(mayInterruptIfRunning);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\QueryFutureIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */