/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ class InExpression
/*    */   extends AbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = 3150665801693551260L;
/*    */   private final Object[] values;
/*    */   
/*    */   InExpression(FilterExprPath pathPrefix, String propertyName, Collection<?> coll) {
/* 16 */     super(pathPrefix, propertyName);
/* 17 */     this.values = coll.toArray(new Object[coll.size()]);
/*    */   }
/*    */   
/*    */   InExpression(FilterExprPath pathPrefix, String propertyName, Object[] array) {
/* 21 */     super(pathPrefix, propertyName);
/* 22 */     this.values = array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 27 */     ElPropertyValue prop = getElProp(request);
/* 28 */     if (prop != null && !prop.isAssocId()) {
/* 29 */       prop = null;
/*    */     }
/*    */     
/* 32 */     for (int i = 0; i < this.values.length; i++) {
/* 33 */       if (prop == null) {
/* 34 */         request.addBindValue(this.values[i]);
/*    */       }
/*    */       else {
/*    */         
/* 38 */         Object[] ids = prop.getAssocOneIdValues(this.values[i]);
/* 39 */         if (ids != null) {
/* 40 */           for (int j = 0; j < ids.length; j++) {
/* 41 */             request.addBindValue(ids[j]);
/*    */           }
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 50 */     if (this.values.length == 0) {
/*    */       
/* 52 */       request.append("1=0");
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     String propertyName = getPropertyName();
/*    */     
/* 58 */     ElPropertyValue prop = getElProp(request);
/* 59 */     if (prop != null && !prop.isAssocId()) {
/* 60 */       prop = null;
/*    */     }
/*    */     
/* 63 */     if (prop != null) {
/* 64 */       request.append(prop.getAssocIdInExpr(propertyName));
/* 65 */       String inClause = prop.getAssocIdInValueExpr(this.values.length);
/* 66 */       request.append(inClause);
/*    */     } else {
/*    */       
/* 69 */       request.append(propertyName);
/* 70 */       request.append(" in (?");
/* 71 */       for (int i = 1; i < this.values.length; i++) {
/* 72 */         request.append(", ").append("?");
/*    */       }
/*    */       
/* 75 */       request.append(" ) ");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 83 */     int hc = InExpression.class.getName().hashCode() + 31 * this.values.length;
/* 84 */     hc = hc * 31 + this.propName.hashCode();
/* 85 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 89 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 93 */     int hc = 0;
/* 94 */     for (int i = 1; i < this.values.length; i++) {
/* 95 */       hc = 31 * hc + this.values[i].hashCode();
/*    */     }
/* 97 */     return hc;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\InExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */