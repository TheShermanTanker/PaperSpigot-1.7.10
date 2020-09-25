/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.Expression;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ 
/*    */ 
/*    */ final class NotExpression
/*    */   implements SpiExpression, LuceneAwareExpression
/*    */ {
/*    */   private static final long serialVersionUID = 5648926732402355781L;
/*    */   private static final String NOT = "not (";
/*    */   private final SpiExpression exp;
/*    */   
/*    */   NotExpression(Expression exp) {
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
/* 31 */     request.append("not (");
/* 32 */     this.exp.addSql(request);
/* 33 */     request.append(") ");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 40 */     int hc = NotExpression.class.getName().hashCode();
/* 41 */     hc = hc * 31 + this.exp.queryAutoFetchHash();
/* 42 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 46 */     int hc = NotExpression.class.getName().hashCode();
/* 47 */     hc = hc * 31 + this.exp.queryPlanHash(request);
/* 48 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 52 */     return this.exp.queryBindHash();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\NotExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */