/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.EbeanServer;
/*    */ import com.avaje.ebean.SqlQuery;
/*    */ import com.avaje.ebean.SqlRow;
/*    */ import com.avaje.ebean.Transaction;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.Callable;
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
/*    */ public class CallableSqlQueryList
/*    */   implements Callable<List<SqlRow>>
/*    */ {
/*    */   private final SqlQuery query;
/*    */   private final EbeanServer server;
/*    */   private final Transaction t;
/*    */   
/*    */   public CallableSqlQueryList(EbeanServer server, SqlQuery query, Transaction t) {
/* 42 */     this.server = server;
/* 43 */     this.query = query;
/* 44 */     this.t = t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<SqlRow> call() throws Exception {
/* 51 */     return this.server.findList(this.query, this.t);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CallableSqlQueryList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */