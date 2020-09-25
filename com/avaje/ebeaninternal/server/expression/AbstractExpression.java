/*    */ package com.avaje.ebeaninternal.server.expression;
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
/*    */ 
/*    */ public abstract class AbstractExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 4072786211853856174L;
/*    */   protected final String propName;
/*    */   protected final FilterExprPath pathPrefix;
/*    */   
/*    */   protected AbstractExpression(FilterExprPath pathPrefix, String propName) {
/* 43 */     this.pathPrefix = pathPrefix;
/* 44 */     this.propName = propName;
/*    */   }
/*    */   
/*    */   public String getPropertyName() {
/* 48 */     if (this.pathPrefix == null) {
/* 49 */       return this.propName;
/*    */     }
/* 51 */     String path = this.pathPrefix.getPath();
/* 52 */     if (path == null || path.length() == 0) {
/* 53 */       return this.propName;
/*    */     }
/* 55 */     return path + "." + this.propName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/* 62 */     String propertyName = getPropertyName();
/* 63 */     if (propertyName != null) {
/* 64 */       ElPropertyDeploy elProp = desc.getElPropertyDeploy(propertyName);
/* 65 */       if (elProp != null && elProp.containsMany()) {
/* 66 */         manyWhereJoin.add(elProp);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ElPropertyValue getElProp(SpiExpressionRequest request) {
/* 73 */     String propertyName = getPropertyName();
/* 74 */     return request.getBeanDescriptor().getElGetValue(propertyName);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\AbstractExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */