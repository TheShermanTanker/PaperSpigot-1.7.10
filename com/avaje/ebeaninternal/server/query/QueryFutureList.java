/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.FutureList;
/*    */ import com.avaje.ebean.Query;
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
/*    */ 
/*    */ public class QueryFutureList<T>
/*    */   extends BaseFuture<List<T>>
/*    */   implements FutureList<T>
/*    */ {
/*    */   private final Query<T> query;
/*    */   
/*    */   public QueryFutureList(Query<T> query, FutureTask<List<T>> futureTask) {
/* 37 */     super(futureTask);
/* 38 */     this.query = query;
/*    */   }
/*    */   
/*    */   public Query<T> getQuery() {
/* 42 */     return this.query;
/*    */   }
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 46 */     this.query.cancel();
/* 47 */     return super.cancel(mayInterruptIfRunning);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\QueryFutureList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */