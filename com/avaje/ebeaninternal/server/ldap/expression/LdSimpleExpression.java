/*     */ package com.avaje.ebeaninternal.server.ldap.expression;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ 
/*     */ class LdSimpleExpression extends LdAbstractExpression {
/*     */   private static final long serialVersionUID = 4091359751840929075L;
/*     */   private final Op type;
/*     */   private final Object value;
/*     */   
/*     */   enum Op {
/*  13 */     EQ {
/*     */       public String toString() {
/*  15 */         return "=";
/*     */       }
/*     */     },
/*  18 */     NOT_EQ {
/*     */       public String toString() {
/*  20 */         return "<>";
/*     */       }
/*     */     },
/*  23 */     LT {
/*     */       public String toString() {
/*  25 */         return "<";
/*     */       }
/*     */     },
/*  28 */     LT_EQ {
/*     */       public String toString() {
/*  30 */         return "<=";
/*     */       }
/*     */     },
/*  33 */     GT {
/*     */       public String toString() {
/*  35 */         return ">";
/*     */       }
/*     */     },
/*  38 */     GT_EQ {
/*     */       public String toString() {
/*  40 */         return ">=";
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LdSimpleExpression(String propertyName, Op type, Object value) {
/*  50 */     super(propertyName);
/*  51 */     this.type = type;
/*  52 */     this.value = value;
/*     */   }
/*     */   
/*     */   public String getPropertyName() {
/*  56 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/*  61 */     ElPropertyValue prop = getElProp(request);
/*  62 */     if (prop != null) {
/*  63 */       if (prop.isAssocId()) {
/*  64 */         Object[] ids = prop.getAssocOneIdValues(this.value);
/*  65 */         if (ids != null) {
/*  66 */           for (int i = 0; i < ids.length; i++) {
/*  67 */             request.addBindValue(ids[i]);
/*     */           }
/*     */         }
/*     */         return;
/*     */       } 
/*  72 */       ScalarType<?> scalarType = prop.getBeanProperty().getScalarType();
/*  73 */       Object v = scalarType.toJdbcType(this.value);
/*  74 */       request.addBindValue(v);
/*     */     } else {
/*  76 */       request.addBindValue(this.value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/*  81 */     ElPropertyValue prop = getElProp(request);
/*  82 */     if (prop != null && 
/*  83 */       prop.isAssocId()) {
/*  84 */       String rawExpr = prop.getAssocOneIdExpr(this.propertyName, this.type.toString());
/*  85 */       String str1 = request.parseDeploy(rawExpr);
/*  86 */       request.append(str1);
/*     */       
/*     */       return;
/*     */     } 
/*  90 */     String parsed = request.parseDeploy(this.propertyName);
/*     */     
/*  92 */     request.append("(").append(parsed).append("").append(this.type.toString()).append(nextParam(request)).append(")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/*  99 */     int hc = LdSimpleExpression.class.getName().hashCode();
/* 100 */     hc = hc * 31 + this.propertyName.hashCode();
/* 101 */     hc = hc * 31 + this.type.name().hashCode();
/* 102 */     return hc;
/*     */   }
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 106 */     return queryAutoFetchHash();
/*     */   }
/*     */   
/*     */   public int queryBindHash() {
/* 110 */     return this.value.hashCode();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdSimpleExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */