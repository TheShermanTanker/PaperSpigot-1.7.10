/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ class LdIdExpression
/*    */   extends LdAbstractExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = -3065936341718489843L;
/*    */   private final Object value;
/*    */   
/*    */   LdIdExpression(Object value) {
/* 21 */     super(null);
/* 22 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 33 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 34 */     BeanProperty[] propertiesId = r.getBeanDescriptor().propertiesId();
/* 35 */     if (propertiesId.length > 1) {
/* 36 */       throw new RuntimeException("Only single Id property is supported for LDAP");
/*    */     }
/* 38 */     request.addBindValue(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 43 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 44 */     BeanProperty[] propertiesId = r.getBeanDescriptor().propertiesId();
/* 45 */     if (propertiesId.length > 1) {
/* 46 */       throw new RuntimeException("Only single Id property is supported for LDAP");
/*    */     }
/*    */     
/* 49 */     String ldapProp = propertiesId[0].getDbColumn();
/* 50 */     request.append(ldapProp).append("=").append(nextParam(request));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 59 */     return LdIdExpression.class.getName().hashCode();
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 63 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 67 */     return this.value.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdIdExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */