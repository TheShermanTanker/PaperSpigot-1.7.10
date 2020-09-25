/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.ExampleExpression;
/*     */ import com.avaje.ebean.LikeType;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*     */ import com.avaje.ebeaninternal.api.SpiExpression;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultExampleExpression
/*     */   implements SpiExpression, ExampleExpression
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Object entity;
/*     */   private boolean caseInsensitive;
/*     */   private LikeType likeType;
/*     */   private boolean includeZeros;
/*     */   private ArrayList<SpiExpression> list;
/*     */   private final FilterExprPath pathPrefix;
/*     */   
/*     */   public DefaultExampleExpression(FilterExprPath pathPrefix, Object entity, boolean caseInsensitive, LikeType likeType) {
/*  81 */     this.pathPrefix = pathPrefix;
/*  82 */     this.entity = entity;
/*  83 */     this.caseInsensitive = caseInsensitive;
/*  84 */     this.likeType = likeType;
/*     */   }
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins whereManyJoins) {
/*  88 */     if (this.list != null) {
/*  89 */       for (int i = 0; i < this.list.size(); i++) {
/*  90 */         ((SpiExpression)this.list.get(i)).containsMany(desc, whereManyJoins);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public ExampleExpression includeZeros() {
/*  96 */     this.includeZeros = true;
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   public ExampleExpression caseInsensitive() {
/* 101 */     this.caseInsensitive = true;
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public ExampleExpression useStartsWith() {
/* 106 */     this.likeType = LikeType.STARTS_WITH;
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public ExampleExpression useContains() {
/* 111 */     this.likeType = LikeType.CONTAINS;
/* 112 */     return this;
/*     */   }
/*     */   
/*     */   public ExampleExpression useEndsWith() {
/* 116 */     this.likeType = LikeType.ENDS_WITH;
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   public ExampleExpression useEqualTo() {
/* 121 */     this.likeType = LikeType.EQUAL_TO;
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPropertyName() {
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/* 137 */     for (int i = 0; i < this.list.size(); i++) {
/* 138 */       SpiExpression item = this.list.get(i);
/* 139 */       item.addBindValues(request);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/* 148 */     if (!this.list.isEmpty()) {
/* 149 */       request.append("(");
/*     */       
/* 151 */       for (int i = 0; i < this.list.size(); i++) {
/* 152 */         SpiExpression item = this.list.get(i);
/* 153 */         if (i > 0) {
/* 154 */           request.append(" and ");
/*     */         }
/* 156 */         item.addSql(request);
/*     */       } 
/*     */       
/* 159 */       request.append(") ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 169 */     return DefaultExampleExpression.class.getName().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 179 */     this.list = buildExpressions(request);
/*     */     
/* 181 */     int hc = DefaultExampleExpression.class.getName().hashCode();
/*     */     
/* 183 */     for (int i = 0; i < this.list.size(); i++) {
/* 184 */       hc = hc * 31 + ((SpiExpression)this.list.get(i)).queryPlanHash(request);
/*     */     }
/*     */     
/* 187 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryBindHash() {
/* 194 */     int hc = DefaultExampleExpression.class.getName().hashCode();
/* 195 */     for (int i = 0; i < this.list.size(); i++) {
/* 196 */       hc = hc * 31 + ((SpiExpression)this.list.get(i)).queryBindHash();
/*     */     }
/*     */     
/* 199 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<SpiExpression> buildExpressions(BeanQueryRequest<?> request) {
/* 207 */     ArrayList<SpiExpression> list = new ArrayList<SpiExpression>();
/*     */     
/* 209 */     OrmQueryRequest<?> r = (OrmQueryRequest)request;
/* 210 */     BeanDescriptor<?> beanDescriptor = r.getBeanDescriptor();
/*     */     
/* 212 */     Iterator<BeanProperty> propIter = beanDescriptor.propertiesAll();
/*     */     
/* 214 */     while (propIter.hasNext()) {
/* 215 */       BeanProperty beanProperty = propIter.next();
/* 216 */       String propName = beanProperty.getName();
/* 217 */       Object value = beanProperty.getValue(this.entity);
/*     */       
/* 219 */       if (beanProperty.isScalar() && value != null) {
/* 220 */         if (value instanceof String) {
/* 221 */           list.add(new LikeExpression(this.pathPrefix, propName, (String)value, this.caseInsensitive, this.likeType)); continue;
/*     */         } 
/* 223 */         if (!this.includeZeros && isZero(value)) {
/*     */           continue;
/*     */         }
/*     */         
/* 227 */         list.add(new SimpleExpression(this.pathPrefix, propName, SimpleExpression.Op.EQ, value));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 233 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isZero(Object value) {
/* 240 */     if (value instanceof Number) {
/* 241 */       Number num = (Number)value;
/* 242 */       double doubleValue = num.doubleValue();
/* 243 */       if (doubleValue == 0.0D) {
/* 244 */         return true;
/*     */       }
/*     */     } 
/* 247 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\DefaultExampleExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */