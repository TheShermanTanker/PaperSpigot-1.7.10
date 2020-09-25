/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.QueryIterator;
/*    */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*    */ import java.sql.SQLException;
/*    */ import javax.persistence.PersistenceException;
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
/*    */ class CQueryIteratorSimple<T>
/*    */   implements QueryIterator<T>
/*    */ {
/*    */   private final CQuery<T> cquery;
/*    */   private final OrmQueryRequest<T> request;
/*    */   
/*    */   CQueryIteratorSimple(CQuery<T> cquery, OrmQueryRequest<T> request) {
/* 43 */     this.cquery = cquery;
/* 44 */     this.request = request;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/*    */     try {
/* 49 */       return this.cquery.hasNextBean(true);
/* 50 */     } catch (SQLException e) {
/* 51 */       throw this.cquery.createPersistenceException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public T next() {
/* 56 */     return this.cquery.getLoadedBean();
/*    */   }
/*    */   
/*    */   public void close() {
/* 60 */     this.cquery.updateExecutionStatistics();
/* 61 */     this.cquery.close();
/* 62 */     this.request.endTransIfRequired();
/*    */   }
/*    */   
/*    */   public void remove() {
/* 66 */     throw new PersistenceException("Remove not allowed");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CQueryIteratorSimple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */