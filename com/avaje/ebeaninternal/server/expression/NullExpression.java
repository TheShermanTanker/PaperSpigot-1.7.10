/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NullExpression
/*    */   extends AbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = 4246991057451128269L;
/*    */   private final boolean notNull;
/*    */   
/*    */   NullExpression(FilterExprPath pathPrefix, String propertyName, boolean notNull) {
/* 18 */     super(pathPrefix, propertyName);
/* 19 */     this.notNull = notNull;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {}
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 28 */     String propertyName = getPropertyName();
/*    */     
/* 30 */     String nullExpr = this.notNull ? " is not null " : " is null ";
/*    */     
/* 32 */     ElPropertyValue prop = getElProp(request);
/* 33 */     if (prop != null && prop.isAssocId()) {
/* 34 */       request.append(prop.getAssocOneIdExpr(propertyName, nullExpr));
/*    */       
/*    */       return;
/*    */     } 
/* 38 */     request.append(propertyName).append(nullExpr);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 45 */     int hc = NullExpression.class.getName().hashCode();
/* 46 */     hc = hc * 31 + (this.notNull ? 1 : 0);
/* 47 */     hc = hc * 31 + this.propName.hashCode();
/* 48 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 52 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 56 */     return this.notNull ? 1 : 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\NullExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */