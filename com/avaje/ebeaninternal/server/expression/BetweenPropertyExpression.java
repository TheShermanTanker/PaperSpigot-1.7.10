/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
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
/*    */ class BetweenPropertyExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 2078918165221454910L;
/*    */   private static final String BETWEEN = " between ";
/*    */   private final FilterExprPath pathPrefix;
/*    */   private final String lowProperty;
/*    */   private final String highProperty;
/*    */   private final Object value;
/*    */   
/*    */   BetweenPropertyExpression(FilterExprPath pathPrefix, String lowProperty, String highProperty, Object value) {
/* 46 */     this.pathPrefix = pathPrefix;
/* 47 */     this.lowProperty = lowProperty;
/* 48 */     this.highProperty = highProperty;
/* 49 */     this.value = value;
/*    */   }
/*    */   
/*    */   protected String name(String propName) {
/* 53 */     if (this.pathPrefix == null) {
/* 54 */       return propName;
/*    */     }
/* 56 */     String path = this.pathPrefix.getPath();
/* 57 */     if (path == null || path.length() == 0) {
/* 58 */       return propName;
/*    */     }
/* 60 */     return path + "." + propName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/* 67 */     ElPropertyDeploy elProp = desc.getElPropertyDeploy(name(this.lowProperty));
/* 68 */     if (elProp != null && elProp.containsMany()) {
/* 69 */       manyWhereJoin.add(elProp);
/*    */     }
/*    */     
/* 72 */     elProp = desc.getElPropertyDeploy(name(this.highProperty));
/* 73 */     if (elProp != null && elProp.containsMany()) {
/* 74 */       manyWhereJoin.add(elProp);
/*    */     }
/*    */   }
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 79 */     request.addBindValue(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 84 */     request.append(" ? ").append(" between ").append(name(this.lowProperty)).append(" and ").append(name(this.highProperty));
/*    */   }
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 88 */     int hc = BetweenPropertyExpression.class.getName().hashCode();
/* 89 */     hc = hc * 31 + this.lowProperty.hashCode();
/* 90 */     hc = hc * 31 + this.highProperty.hashCode();
/* 91 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 95 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 99 */     return this.value.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\BetweenPropertyExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */