/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ 
/*    */ 
/*    */ class LdRawExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 7973903141340334607L;
/*    */   private final String rawExpr;
/*    */   private final Object[] values;
/*    */   
/*    */   LdRawExpression(String rawExpr, Object[] values) {
/* 18 */     this.rawExpr = rawExpr;
/* 19 */     this.values = values;
/*    */   }
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {}
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 27 */     if (this.values != null) {
/* 28 */       for (int i = 0; i < this.values.length; i++) {
/* 29 */         request.addBindValue(this.values[i]);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 35 */     request.append(this.rawExpr);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 42 */     int hc = LdRawExpression.class.getName().hashCode();
/* 43 */     hc = hc * 31 + this.rawExpr.hashCode();
/* 44 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 48 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 52 */     return this.rawExpr.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdRawExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */