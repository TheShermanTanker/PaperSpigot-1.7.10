/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.FutureRowCount;
/*    */ import com.avaje.ebean.Query;
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
/*    */ public class QueryFutureRowCount<T>
/*    */   extends BaseFuture<Integer>
/*    */   implements FutureRowCount<T>
/*    */ {
/*    */   private final Query<T> query;
/*    */   
/*    */   public QueryFutureRowCount(Query<T> query, FutureTask<Integer> futureTask) {
/* 35 */     super(futureTask);
/* 36 */     this.query = query;
/*    */   }
/*    */   
/*    */   public Query<T> getQuery() {
/* 40 */     return this.query;
/*    */   }
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 44 */     this.query.cancel();
/* 45 */     return super.cancel(mayInterruptIfRunning);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\QueryFutureRowCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */