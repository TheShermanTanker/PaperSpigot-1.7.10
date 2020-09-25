/*    */ package com.avaje.ebeaninternal.server.ldap;
/*    */ 
/*    */ import com.avaje.ebean.QueryIterator;
/*    */ import com.avaje.ebean.QueryResultVisitor;
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ public class LdapOrmQueryRequest<T>
/*    */   implements SpiOrmQueryRequest<T>
/*    */ {
/*    */   private final SpiQuery<T> query;
/*    */   private final BeanDescriptor<T> desc;
/*    */   private final LdapOrmQueryEngine queryEngine;
/*    */   
/*    */   public LdapOrmQueryRequest(SpiQuery<T> query, BeanDescriptor<T> desc, LdapOrmQueryEngine queryEngine) {
/* 40 */     this.query = query;
/* 41 */     this.desc = desc;
/* 42 */     this.queryEngine = queryEngine;
/*    */   }
/*    */   
/*    */   public BeanDescriptor<T> getBeanDescriptor() {
/* 46 */     return this.desc;
/*    */   }
/*    */   
/*    */   public SpiQuery<T> getQuery() {
/* 50 */     return this.query;
/*    */   }
/*    */   
/*    */   public Object findId() {
/* 54 */     return this.queryEngine.findId(this);
/*    */   }
/*    */   
/*    */   public List<Object> findIds() {
/* 58 */     throw new RuntimeException("Not Implemented yet");
/*    */   }
/*    */   
/*    */   public List<T> findList() {
/* 62 */     return this.queryEngine.findList(this);
/*    */   }
/*    */   
/*    */   public void findVisit(QueryResultVisitor<T> visitor) {
/* 66 */     throw new RuntimeException("Not Implemented yet");
/*    */   }
/*    */   
/*    */   public QueryIterator<T> findIterate() {
/* 70 */     throw new RuntimeException("Not Implemented yet");
/*    */   }
/*    */   
/*    */   public Map<?, ?> findMap() {
/* 74 */     throw new RuntimeException("Not Implemented yet");
/*    */   }
/*    */   
/*    */   public int findRowCount() {
/* 78 */     throw new RuntimeException("Not Implemented yet");
/*    */   }
/*    */   
/*    */   public Set<?> findSet() {
/* 82 */     throw new RuntimeException("Not Implemented yet");
/*    */   }
/*    */   
/*    */   public T getFromPersistenceContextOrCache() {
/* 86 */     return null;
/*    */   }
/*    */   
/*    */   public BeanCollection<T> getFromQueryCache() {
/* 90 */     return null;
/*    */   }
/*    */   
/*    */   public void initTransIfRequired() {}
/*    */   
/*    */   public void rollbackTransIfRequired() {}
/*    */   
/*    */   public void endTransIfRequired() {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\LdapOrmQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */