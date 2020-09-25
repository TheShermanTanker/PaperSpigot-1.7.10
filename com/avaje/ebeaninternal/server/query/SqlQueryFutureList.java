/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.SqlFutureList;
/*    */ import com.avaje.ebean.SqlQuery;
/*    */ import com.avaje.ebean.SqlRow;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.FutureTask;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SqlQueryFutureList
/*    */   extends BaseFuture<List<SqlRow>>
/*    */   implements SqlFutureList
/*    */ {
/*    */   private final SqlQuery query;
/*    */   
/*    */   public SqlQueryFutureList(SqlQuery query, FutureTask<List<SqlRow>> futureTask) {
/* 20 */     super(futureTask);
/* 21 */     this.query = query;
/*    */   }
/*    */   
/*    */   public SqlQuery getQuery() {
/* 25 */     return this.query;
/*    */   }
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 29 */     this.query.cancel();
/* 30 */     return super.cancel(mayInterruptIfRunning);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\SqlQueryFutureList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */