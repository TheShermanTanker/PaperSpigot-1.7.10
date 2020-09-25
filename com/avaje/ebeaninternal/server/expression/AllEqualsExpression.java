/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*     */ import com.avaje.ebeaninternal.api.SpiExpression;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AllEqualsExpression
/*     */   implements SpiExpression
/*     */ {
/*     */   private static final long serialVersionUID = -8691773558205937025L;
/*     */   private final Map<String, Object> propMap;
/*     */   private final FilterExprPath pathPrefix;
/*     */   
/*     */   AllEqualsExpression(FilterExprPath pathPrefix, Map<String, Object> propMap) {
/*  24 */     this.pathPrefix = pathPrefix;
/*  25 */     this.propMap = propMap;
/*     */   }
/*     */   
/*     */   protected String name(String propName) {
/*  29 */     if (this.pathPrefix == null) {
/*  30 */       return propName;
/*     */     }
/*  32 */     String path = this.pathPrefix.getPath();
/*  33 */     if (path == null || path.length() == 0) {
/*  34 */       return propName;
/*     */     }
/*  36 */     return path + "." + propName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/*  42 */     if (this.propMap != null) {
/*  43 */       Iterator<String> it = this.propMap.keySet().iterator();
/*  44 */       while (it.hasNext()) {
/*  45 */         String propertyName = it.next();
/*  46 */         ElPropertyDeploy elProp = desc.getElPropertyDeploy(name(propertyName));
/*  47 */         if (elProp != null && elProp.containsMany()) {
/*  48 */           manyWhereJoin.add(elProp);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/*  56 */     if (this.propMap.isEmpty()) {
/*     */       return;
/*     */     }
/*  59 */     Iterator<Object> it = this.propMap.values().iterator();
/*  60 */     while (it.hasNext()) {
/*  61 */       Object value = it.next();
/*  62 */       if (value != null) {
/*  63 */         request.addBindValue(value);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/*  72 */     if (this.propMap.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     request.append("(");
/*     */     
/*  78 */     Set<Map.Entry<String, Object>> entries = this.propMap.entrySet();
/*  79 */     Iterator<Map.Entry<String, Object>> it = entries.iterator();
/*     */     
/*  81 */     int count = 0;
/*  82 */     while (it.hasNext()) {
/*  83 */       Map.Entry<String, Object> entry = it.next();
/*  84 */       Object value = entry.getValue();
/*  85 */       String propName = entry.getKey();
/*     */       
/*  87 */       if (count > 0) {
/*  88 */         request.append("and ");
/*     */       }
/*     */       
/*  91 */       request.append(name(propName));
/*  92 */       if (value == null) {
/*  93 */         request.append(" is null ");
/*     */       } else {
/*  95 */         request.append(" = ? ");
/*     */       } 
/*  97 */       count++;
/*     */     } 
/*  99 */     request.append(")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 110 */     int hc = AllEqualsExpression.class.getName().hashCode();
/* 111 */     Set<Map.Entry<String, Object>> entries = this.propMap.entrySet();
/* 112 */     Iterator<Map.Entry<String, Object>> it = entries.iterator();
/*     */     
/* 114 */     while (it.hasNext()) {
/* 115 */       Map.Entry<String, Object> entry = it.next();
/* 116 */       Object value = entry.getValue();
/* 117 */       String propName = entry.getKey();
/*     */       
/* 119 */       hc = hc * 31 + propName.hashCode();
/* 120 */       hc = hc * 31 + ((value == null) ? 0 : 1);
/*     */     } 
/*     */     
/* 123 */     return hc;
/*     */   }
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 127 */     return queryAutoFetchHash();
/*     */   }
/*     */   
/*     */   public int queryBindHash() {
/* 131 */     return queryAutoFetchHash();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\AllEqualsExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */