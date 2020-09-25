/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*    */ import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IdInExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final List<?> idList;
/*    */   
/*    */   public IdInExpression(List<?> idList) {
/* 23 */     this.idList = idList;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 33 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 34 */     BeanDescriptor<?> descriptor = r.getBeanDescriptor();
/* 35 */     IdBinder idBinder = descriptor.getIdBinder();
/*    */     
/* 37 */     for (int i = 0; i < this.idList.size(); i++) {
/* 38 */       idBinder.addIdInBindValue(request, this.idList.get(i));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSqlNoAlias(SpiExpressionRequest request) {
/* 47 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 48 */     BeanDescriptor<?> descriptor = r.getBeanDescriptor();
/* 49 */     IdBinder idBinder = descriptor.getIdBinder();
/*    */     
/* 51 */     request.append(descriptor.getIdBinder().getBindIdInSql(null));
/* 52 */     String inClause = idBinder.getIdInValueExpr(this.idList.size());
/* 53 */     request.append(inClause);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 58 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 59 */     BeanDescriptor<?> descriptor = r.getBeanDescriptor();
/* 60 */     IdBinder idBinder = descriptor.getIdBinder();
/*    */     
/* 62 */     request.append(descriptor.getIdBinderInLHSSql());
/* 63 */     String inClause = idBinder.getIdInValueExpr(this.idList.size());
/* 64 */     request.append(inClause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 73 */     int hc = IdInExpression.class.getName().hashCode();
/* 74 */     hc = hc * 31 + this.idList.size();
/* 75 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 79 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 83 */     return this.idList.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\IdInExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */