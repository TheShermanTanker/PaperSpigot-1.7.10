/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class IdExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = -3065936341718489842L;
/*    */   private final Object value;
/*    */   
/*    */   IdExpression(Object value) {
/* 20 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 34 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 35 */     Object[] bindIdValues = r.getBeanDescriptor().getBindIdValues(this.value);
/* 36 */     for (int i = 0; i < bindIdValues.length; i++) {
/* 37 */       request.addBindValue(bindIdValues[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 43 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 44 */     String idSql = r.getBeanDescriptor().getIdBinderIdSql();
/*    */     
/* 46 */     request.append(idSql).append(" ");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 55 */     return IdExpression.class.getName().hashCode();
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 59 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 63 */     return this.value.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\IdExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */