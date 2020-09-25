/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import com.avaje.ebeaninternal.server.query.CQuery;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class InQueryExpression
/*    */   extends AbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = 666990277309851644L;
/*    */   private final SpiQuery<?> subQuery;
/*    */   private transient CQuery<?> compiledSubQuery;
/*    */   
/*    */   public InQueryExpression(FilterExprPath pathPrefix, String propertyName, SpiQuery<?> subQuery) {
/* 25 */     super(pathPrefix, propertyName);
/* 26 */     this.subQuery = subQuery;
/*    */   }
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 30 */     int hc = InQueryExpression.class.getName().hashCode();
/* 31 */     hc = hc * 31 + this.propName.hashCode();
/* 32 */     hc = hc * 31 + this.subQuery.queryAutofetchHash();
/* 33 */     return hc;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 40 */     this.compiledSubQuery = compileSubQuery(request);
/*    */     
/* 42 */     int hc = InQueryExpression.class.getName().hashCode();
/* 43 */     hc = hc * 31 + this.propName.hashCode();
/* 44 */     hc = hc * 31 + this.subQuery.queryPlanHash(request);
/* 45 */     return hc;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private CQuery<?> compileSubQuery(BeanQueryRequest<?> queryRequest) {
/* 53 */     SpiEbeanServer ebeanServer = (SpiEbeanServer)queryRequest.getEbeanServer();
/* 54 */     return ebeanServer.compileQuery((Query)this.subQuery, queryRequest.getTransaction());
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 58 */     return this.subQuery.queryBindHash();
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 63 */     String subSelect = this.compiledSubQuery.getGeneratedSql();
/* 64 */     subSelect = subSelect.replace('\n', ' ');
/*    */     
/* 66 */     String propertyName = getPropertyName();
/* 67 */     request.append(" (");
/* 68 */     request.append(propertyName);
/* 69 */     request.append(") in (");
/* 70 */     request.append(subSelect);
/* 71 */     request.append(") ");
/*    */   }
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 76 */     List<Object> bindParams = this.compiledSubQuery.getPredicates().getWhereExprBindValues();
/*    */     
/* 78 */     if (bindParams == null) {
/*    */       return;
/*    */     }
/*    */     
/* 82 */     for (int i = 0; i < bindParams.size(); i++)
/* 83 */       request.addBindValue(bindParams.get(i)); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\InQueryExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */