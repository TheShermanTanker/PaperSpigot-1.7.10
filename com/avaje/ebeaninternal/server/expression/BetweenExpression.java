/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class BetweenExpression
/*    */   extends AbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = 2078918165221454910L;
/*    */   private static final String BETWEEN = " between ";
/*    */   private final Object valueHigh;
/*    */   private final Object valueLow;
/*    */   
/*    */   BetweenExpression(FilterExprPath pathPrefix, String propertyName, Object valLo, Object valHigh) {
/* 18 */     super(pathPrefix, propertyName);
/* 19 */     this.valueLow = valLo;
/* 20 */     this.valueHigh = valHigh;
/*    */   }
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 24 */     request.addBindValue(this.valueLow);
/* 25 */     request.addBindValue(this.valueHigh);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 30 */     request.append(getPropertyName()).append(" between ").append(" ? and ? ");
/*    */   }
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 34 */     int hc = BetweenExpression.class.getName().hashCode();
/* 35 */     hc = hc * 31 + this.propName.hashCode();
/* 36 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 40 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 44 */     int hc = this.valueLow.hashCode();
/* 45 */     hc = hc * 31 + this.valueHigh.hashCode();
/* 46 */     return hc;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\BetweenExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */