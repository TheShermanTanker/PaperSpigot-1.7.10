/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ 
/*    */ class LdLikeExpression
/*    */   extends LdAbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = 4091359751840929076L;
/*    */   private final String value;
/*    */   
/*    */   public LdLikeExpression(String propertyName, String value) {
/* 13 */     super(propertyName);
/* 14 */     this.value = value;
/*    */   }
/*    */   
/*    */   public String getPropertyName() {
/* 18 */     return this.propertyName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {}
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/*    */     String escapedValue;
/* 28 */     if (this.value == null) {
/* 29 */       escapedValue = "*";
/*    */     } else {
/* 31 */       escapedValue = LdEscape.forLike(this.value);
/*    */     } 
/*    */     
/* 34 */     String parsed = request.parseDeploy(this.propertyName);
/*    */     
/* 36 */     request.append("(").append(parsed).append("=").append(escapedValue).append(")");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 44 */     int hc = LdLikeExpression.class.getName().hashCode();
/* 45 */     hc = hc * 31 + this.propertyName.hashCode();
/* 46 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 50 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 54 */     return this.value.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdLikeExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */