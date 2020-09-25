/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*    */ 
/*    */ class CaseInsensitiveEqualExpression
/*    */   extends AbstractExpression
/*    */   implements LuceneAwareExpression {
/*    */   private static final long serialVersionUID = -6406036750998971064L;
/*    */   private final String value;
/*    */   
/*    */   CaseInsensitiveEqualExpression(FilterExprPath pathPrefix, String propertyName, String value) {
/* 14 */     super(pathPrefix, propertyName);
/* 15 */     this.value = value.toLowerCase();
/*    */   }
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 20 */     ElPropertyValue prop = getElProp(request);
/* 21 */     if (prop != null && prop.isDbEncrypted()) {
/*    */       
/* 23 */       String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
/* 24 */       request.addBindValue(encryptKey);
/*    */     } 
/*    */     
/* 27 */     request.addBindValue(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 32 */     String propertyName = getPropertyName();
/* 33 */     String pname = propertyName;
/*    */     
/* 35 */     ElPropertyValue prop = getElProp(request);
/* 36 */     if (prop != null && prop.isDbEncrypted()) {
/* 37 */       pname = prop.getBeanProperty().getDecryptProperty(propertyName);
/*    */     }
/*    */     
/* 40 */     request.append("lower(").append(pname).append(") =? ");
/*    */   }
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 44 */     int hc = CaseInsensitiveEqualExpression.class.getName().hashCode();
/* 45 */     hc = hc * 31 + this.propName.hashCode();
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\CaseInsensitiveEqualExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */