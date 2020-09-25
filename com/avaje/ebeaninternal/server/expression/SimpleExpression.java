/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ 
/*     */ public class SimpleExpression extends AbstractExpression implements LuceneAwareExpression {
/*     */   private static final long serialVersionUID = -382881395755603790L;
/*     */   private final Op type;
/*     */   private final Object value;
/*     */   
/*     */   enum Op {
/*  12 */     EQ(" = ? ", " = "), NOT_EQ(" <> ? ", " <> "), LT(" < ? ", " < "), LT_EQ(" <= ? ", " <= "), GT(" > ? ", " > "), GT_EQ(" >= ? ", " >= ");
/*     */     
/*     */     String shortDesc;
/*     */     String exp;
/*     */     
/*     */     Op(String exp, String shortDesc) {
/*  18 */       this.exp = exp;
/*  19 */       this.shortDesc = shortDesc;
/*     */     }
/*     */     
/*     */     public String bind() {
/*  23 */       return this.exp;
/*     */     }
/*     */     
/*     */     public String shortDesc() {
/*  27 */       return this.shortDesc;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleExpression(FilterExprPath pathPrefix, String propertyName, Op type, Object value) {
/*  36 */     super(pathPrefix, propertyName);
/*  37 */     this.type = type;
/*  38 */     this.value = value;
/*     */   }
/*     */   
/*     */   public boolean isOpEquals() {
/*  42 */     return Op.EQ.equals(this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/*  47 */     ElPropertyValue prop = getElProp(request);
/*  48 */     if (prop != null) {
/*  49 */       if (prop.isAssocId()) {
/*  50 */         Object[] ids = prop.getAssocOneIdValues(this.value);
/*  51 */         if (ids != null) {
/*  52 */           for (int i = 0; i < ids.length; i++) {
/*  53 */             request.addBindValue(ids[i]);
/*     */           }
/*     */         }
/*     */         return;
/*     */       } 
/*  58 */       if (prop.isDbEncrypted()) {
/*     */         
/*  60 */         String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
/*  61 */         request.addBindValue(encryptKey);
/*  62 */       } else if (prop.isLocalEncrypted()) {
/*     */       
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  69 */     request.addBindValue(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/*  74 */     String propertyName = getPropertyName();
/*     */     
/*  76 */     ElPropertyValue prop = getElProp(request);
/*  77 */     if (prop != null) {
/*  78 */       if (prop.isAssocId()) {
/*  79 */         request.append(prop.getAssocOneIdExpr(propertyName, this.type.bind()));
/*     */         return;
/*     */       } 
/*  82 */       if (prop.isDbEncrypted()) {
/*  83 */         String dsql = prop.getBeanProperty().getDecryptSql();
/*  84 */         request.append(dsql).append(this.type.bind());
/*     */         return;
/*     */       } 
/*     */     } 
/*  88 */     request.append(propertyName).append(this.type.bind());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/*  95 */     int hc = SimpleExpression.class.getName().hashCode();
/*  96 */     hc = hc * 31 + this.propName.hashCode();
/*  97 */     hc = hc * 31 + this.type.name().hashCode();
/*  98 */     return hc;
/*     */   }
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 102 */     return queryAutoFetchHash();
/*     */   }
/*     */   
/*     */   public int queryBindHash() {
/* 106 */     return this.value.hashCode();
/*     */   }
/*     */   
/*     */   public Object getValue() {
/* 110 */     return this.value;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\SimpleExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */