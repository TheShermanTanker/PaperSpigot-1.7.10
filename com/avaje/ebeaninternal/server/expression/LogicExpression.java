/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.Expression;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ 
/*    */ abstract class LogicExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 616860781960645251L;
/*    */   static final String AND = " and ";
/*    */   static final String OR = " or ";
/*    */   private final SpiExpression expOne;
/*    */   private final SpiExpression expTwo;
/*    */   private final String joinType;
/*    */   
/*    */   static class And
/*    */     extends LogicExpression {
/*    */     private static final long serialVersionUID = -3832889676798526444L;
/*    */     
/*    */     And(Expression expOne, Expression expTwo) {
/* 25 */       super(" and ", expOne, expTwo);
/*    */     }
/*    */   }
/*    */   
/*    */   static class Or
/*    */     extends LogicExpression {
/*    */     private static final long serialVersionUID = -6871993143194094819L;
/*    */     
/*    */     Or(Expression expOne, Expression expTwo) {
/* 34 */       super(" or ", expOne, expTwo);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   LogicExpression(String joinType, Expression expOne, Expression expTwo) {
/* 45 */     this.joinType = joinType;
/* 46 */     this.expOne = (SpiExpression)expOne;
/* 47 */     this.expTwo = (SpiExpression)expTwo;
/*    */   }
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/* 51 */     this.expOne.containsMany(desc, manyWhereJoin);
/* 52 */     this.expTwo.containsMany(desc, manyWhereJoin);
/*    */   }
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 56 */     this.expOne.addBindValues(request);
/* 57 */     this.expTwo.addBindValues(request);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 62 */     request.append("(");
/* 63 */     this.expOne.addSql(request);
/* 64 */     request.append(this.joinType);
/* 65 */     this.expTwo.addSql(request);
/* 66 */     request.append(") ");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 73 */     int hc = LogicExpression.class.getName().hashCode() + this.joinType.hashCode();
/* 74 */     hc = hc * 31 + this.expOne.queryAutoFetchHash();
/* 75 */     hc = hc * 31 + this.expTwo.queryAutoFetchHash();
/* 76 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 80 */     int hc = LogicExpression.class.getName().hashCode() + this.joinType.hashCode();
/* 81 */     hc = hc * 31 + this.expOne.queryPlanHash(request);
/* 82 */     hc = hc * 31 + this.expTwo.queryPlanHash(request);
/* 83 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 87 */     int hc = this.expOne.queryBindHash();
/* 88 */     hc = hc * 31 + this.expTwo.queryBindHash();
/* 89 */     return hc;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\LogicExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */