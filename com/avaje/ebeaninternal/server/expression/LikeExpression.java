/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.LikeType;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*    */ 
/*    */ 
/*    */ class LikeExpression
/*    */   extends AbstractExpression
/*    */   implements LuceneAwareExpression
/*    */ {
/*    */   private static final long serialVersionUID = -5398151809111172380L;
/*    */   private final String val;
/*    */   private final boolean caseInsensitive;
/*    */   private final LikeType type;
/*    */   
/*    */   LikeExpression(FilterExprPath pathPrefix, String propertyName, String value, boolean caseInsensitive, LikeType type) {
/* 19 */     super(pathPrefix, propertyName);
/* 20 */     this.caseInsensitive = caseInsensitive;
/* 21 */     this.type = type;
/* 22 */     this.val = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 27 */     ElPropertyValue prop = getElProp(request);
/* 28 */     if (prop != null && prop.isDbEncrypted()) {
/*    */       
/* 30 */       String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
/* 31 */       request.addBindValue(encryptKey);
/*    */     } 
/*    */     
/* 34 */     String bindValue = getValue(this.val, this.caseInsensitive, this.type);
/* 35 */     request.addBindValue(bindValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 40 */     String propertyName = getPropertyName();
/* 41 */     String pname = propertyName;
/*    */     
/* 43 */     ElPropertyValue prop = getElProp(request);
/* 44 */     if (prop != null && prop.isDbEncrypted()) {
/* 45 */       pname = prop.getBeanProperty().getDecryptProperty(propertyName);
/*    */     }
/* 47 */     if (this.caseInsensitive) {
/* 48 */       request.append("lower(").append(pname).append(")");
/*    */     } else {
/* 50 */       request.append(pname);
/*    */     } 
/* 52 */     if (this.type.equals(LikeType.EQUAL_TO)) {
/* 53 */       request.append(" = ? ");
/*    */     } else {
/* 55 */       request.append(" like ? ");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 63 */     int hc = LikeExpression.class.getName().hashCode();
/* 64 */     hc = hc * 31 + (this.caseInsensitive ? 0 : 1);
/* 65 */     hc = hc * 31 + this.propName.hashCode();
/* 66 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 70 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 74 */     return this.val.hashCode();
/*    */   }
/*    */   
/*    */   private static String getValue(String value, boolean caseInsensitive, LikeType type) {
/* 78 */     if (caseInsensitive) {
/* 79 */       value = value.toLowerCase();
/*    */     }
/* 81 */     switch (type) {
/*    */       case RAW:
/* 83 */         return value;
/*    */       case STARTS_WITH:
/* 85 */         return value + "%";
/*    */       case ENDS_WITH:
/* 87 */         return "%" + value;
/*    */       case CONTAINS:
/* 89 */         return "%" + value + "%";
/*    */       case EQUAL_TO:
/* 91 */         return value;
/*    */     } 
/*    */     
/* 94 */     throw new RuntimeException("LikeType " + type + " missed?");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\LikeExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */