/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ import com.avaje.ebean.Expression;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ 
/*    */ 
/*    */ final class LdNotExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 5648926732402355782L;
/*    */   private static final String NOT = "!";
/*    */   private final SpiExpression exp;
/*    */   
/*    */   LdNotExpression(Expression exp) {
/* 19 */     this.exp = (SpiExpression)exp;
/*    */   }
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/* 23 */     this.exp.containsMany(desc, manyWhereJoin);
/*    */   }
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 27 */     this.exp.addBindValues(request);
/*    */   }
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 31 */     request.append("(");
/* 32 */     request.append("!");
/* 33 */     this.exp.addSql(request);
/* 34 */     request.append(")");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 41 */     int hc = LdNotExpression.class.getName().hashCode();
/* 42 */     hc = hc * 31 + this.exp.queryAutoFetchHash();
/* 43 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 47 */     int hc = LdNotExpression.class.getName().hashCode();
/* 48 */     hc = hc * 31 + this.exp.queryPlanHash(request);
/* 49 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 53 */     return this.exp.queryBindHash();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdNotExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */