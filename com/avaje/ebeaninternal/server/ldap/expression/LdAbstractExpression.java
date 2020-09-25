/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
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
/*    */ public abstract class LdAbstractExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 4072786211853856174L;
/*    */   protected final String propertyName;
/*    */   
/*    */   protected LdAbstractExpression(String propertyName) {
/* 41 */     this.propertyName = propertyName;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String nextParam(SpiExpressionRequest request) {
/* 46 */     int pos = request.nextParameter();
/* 47 */     return "{" + (pos - 1) + "}";
/*    */   }
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/* 52 */     if (this.propertyName != null) {
/* 53 */       ElPropertyDeploy elProp = desc.getElPropertyDeploy(this.propertyName);
/* 54 */       if (elProp != null && elProp.containsMany()) {
/* 55 */         manyWhereJoin.add(elProp);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ElPropertyValue getElProp(SpiExpressionRequest request) {
/* 62 */     return request.getBeanDescriptor().getElGetValue(this.propertyName);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdAbstractExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */