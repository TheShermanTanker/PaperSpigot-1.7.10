/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.QueryIterator;
/*    */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ class CQueryIteratorWithBuffer<T>
/*    */   implements QueryIterator<T>
/*    */ {
/*    */   private final CQuery<T> cquery;
/*    */   private final int bufferSize;
/*    */   private final OrmQueryRequest<T> request;
/*    */   private final ArrayList<T> buffer;
/*    */   private boolean moreToLoad = true;
/*    */   
/*    */   CQueryIteratorWithBuffer(CQuery<T> cquery, OrmQueryRequest<T> request, int bufferSize) {
/* 48 */     this.cquery = cquery;
/* 49 */     this.request = request;
/* 50 */     this.bufferSize = bufferSize;
/* 51 */     this.buffer = new ArrayList<T>(bufferSize);
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/*    */     try {
/* 56 */       if (this.buffer.isEmpty() && this.moreToLoad) {
/*    */         
/* 58 */         int i = -1;
/* 59 */         while (this.moreToLoad && ++i < this.bufferSize) {
/* 60 */           if (this.cquery.hasNextBean(true)) {
/* 61 */             this.buffer.add(this.cquery.getLoadedBean()); continue;
/*    */           } 
/* 63 */           this.moreToLoad = false;
/*    */         } 
/*    */ 
/*    */         
/* 67 */         this.request.executeSecondaryQueries(this.bufferSize);
/*    */       } 
/* 69 */       return !this.buffer.isEmpty();
/*    */     }
/* 71 */     catch (SQLException e) {
/* 72 */       throw this.cquery.createPersistenceException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public T next() {
/* 77 */     return this.buffer.remove(0);
/*    */   }
/*    */   
/*    */   public void close() {
/* 81 */     this.cquery.updateExecutionStatistics();
/* 82 */     this.cquery.close();
/* 83 */     this.request.endTransIfRequired();
/*    */   }
/*    */   
/*    */   public void remove() {
/* 87 */     throw new PersistenceException("Remove not allowed");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CQueryIteratorWithBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */