/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
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
/*    */ public class CallableQueryRowCount<T>
/*    */   extends CallableQuery<T>
/*    */   implements Callable<Integer>
/*    */ {
/*    */   public CallableQueryRowCount(SpiEbeanServer server, Query<T> query, Transaction t) {
/* 37 */     super(server, query, t);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer call() throws Exception {
/* 44 */     return Integer.valueOf(this.server.findRowCountWithCopy(this.query, this.t));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CallableQueryRowCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */