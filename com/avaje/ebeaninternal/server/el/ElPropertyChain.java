/*     */ package com.avaje.ebeaninternal.server.el;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.text.StringFormatter;
/*     */ import com.avaje.ebean.text.StringParser;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
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
/*     */ public class ElPropertyChain
/*     */   implements ElPropertyValue
/*     */ {
/*     */   private final String prefix;
/*     */   private final String placeHolder;
/*     */   private final String placeHolderEncrypted;
/*     */   private final String name;
/*     */   private final String expression;
/*     */   private final boolean containsMany;
/*     */   private final ElPropertyValue[] chain;
/*     */   private final boolean assocId;
/*     */   private final int last;
/*     */   private final BeanProperty lastBeanProperty;
/*     */   private final ScalarType<?> scalarType;
/*     */   private final ElPropertyValue lastElPropertyValue;
/*     */   
/*     */   public ElPropertyChain(boolean containsMany, boolean embedded, String expression, ElPropertyValue[] chain) {
/*  66 */     this.containsMany = containsMany;
/*  67 */     this.chain = chain;
/*  68 */     this.expression = expression;
/*  69 */     int dotPos = expression.lastIndexOf('.');
/*  70 */     if (dotPos > -1) {
/*  71 */       this.name = expression.substring(dotPos + 1);
/*  72 */       if (embedded) {
/*  73 */         int embPos = expression.lastIndexOf('.', dotPos - 1);
/*  74 */         this.prefix = (embPos == -1) ? null : expression.substring(0, embPos);
/*     */       } else {
/*     */         
/*  77 */         this.prefix = expression.substring(0, dotPos);
/*     */       } 
/*     */     } else {
/*  80 */       this.prefix = null;
/*  81 */       this.name = expression;
/*     */     } 
/*     */     
/*  84 */     this.assocId = chain[chain.length - 1].isAssocId();
/*     */     
/*  86 */     this.last = chain.length - 1;
/*  87 */     this.lastBeanProperty = chain[chain.length - 1].getBeanProperty();
/*  88 */     if (this.lastBeanProperty != null) {
/*  89 */       this.scalarType = this.lastBeanProperty.getScalarType();
/*     */     } else {
/*     */       
/*  92 */       this.scalarType = null;
/*     */     } 
/*  94 */     this.lastElPropertyValue = chain[chain.length - 1];
/*  95 */     this.placeHolder = getElPlaceHolder(this.prefix, this.lastElPropertyValue, false);
/*  96 */     this.placeHolderEncrypted = getElPlaceHolder(this.prefix, this.lastElPropertyValue, true);
/*     */   }
/*     */   
/*     */   private String getElPlaceHolder(String prefix, ElPropertyValue lastElPropertyValue, boolean encrypted) {
/* 100 */     if (prefix == null) {
/* 101 */       return lastElPropertyValue.getElPlaceholder(encrypted);
/*     */     }
/*     */     
/* 104 */     String el = lastElPropertyValue.getElPlaceholder(encrypted);
/*     */     
/* 106 */     if (!el.contains("${}"))
/*     */     {
/* 108 */       return StringHelper.replaceString(el, "${", "${" + prefix + ".");
/*     */     }
/* 110 */     return StringHelper.replaceString(el, "${}", "${" + prefix + "}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeployOnly() {
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsManySince(String sinceProperty) {
/* 126 */     if (sinceProperty == null) {
/* 127 */       return this.containsMany;
/*     */     }
/* 129 */     if (!this.expression.startsWith(sinceProperty)) {
/* 130 */       return this.containsMany;
/*     */     }
/*     */     
/* 133 */     int i = 1 + SplitName.count('.', sinceProperty);
/*     */     
/* 135 */     for (; i < this.chain.length; i++) {
/* 136 */       if (this.chain[i].getBeanProperty().containsMany()) {
/* 137 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsMany() {
/* 145 */     return this.containsMany;
/*     */   }
/*     */   
/*     */   public String getElPrefix() {
/* 149 */     return this.prefix;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 153 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getElName() {
/* 157 */     return this.expression;
/*     */   }
/*     */   
/*     */   public String getElPlaceholder(boolean encrypted) {
/* 161 */     return encrypted ? this.placeHolderEncrypted : this.placeHolder;
/*     */   }
/*     */   
/*     */   public boolean isDbEncrypted() {
/* 165 */     return this.lastElPropertyValue.isDbEncrypted();
/*     */   }
/*     */   
/*     */   public boolean isLocalEncrypted() {
/* 169 */     return this.lastElPropertyValue.isLocalEncrypted();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getAssocOneIdValues(Object bean) {
/* 175 */     return this.lastElPropertyValue.getAssocOneIdValues(bean);
/*     */   }
/*     */   
/*     */   public String getAssocOneIdExpr(String prefix, String operator) {
/* 179 */     return this.lastElPropertyValue.getAssocOneIdExpr(this.expression, operator);
/*     */   }
/*     */   
/*     */   public String getAssocIdInExpr(String prefix) {
/* 183 */     return this.lastElPropertyValue.getAssocIdInExpr(prefix);
/*     */   }
/*     */   
/*     */   public String getAssocIdInValueExpr(int size) {
/* 187 */     return this.lastElPropertyValue.getAssocIdInValueExpr(size);
/*     */   }
/*     */   
/*     */   public int getDeployOrder() {
/* 191 */     int i = this.lastBeanProperty.getDeployOrder();
/* 192 */     int max = this.chain.length - 1;
/* 193 */     for (int j = 0; j < max; j++) {
/* 194 */       int xtra = (max - j) * 1000 * this.chain[j].getDeployOrder();
/* 195 */       i += xtra;
/*     */     } 
/* 197 */     return i;
/*     */   }
/*     */   
/*     */   public boolean isAssocId() {
/* 201 */     return this.assocId;
/*     */   }
/*     */   
/*     */   public boolean isAssocProperty() {
/* 205 */     for (int i = 0; i < this.chain.length; i++) {
/* 206 */       if (this.chain[i].isAssocProperty()) {
/* 207 */         return true;
/*     */       }
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */   
/*     */   public String getDbColumn() {
/* 214 */     return this.lastElPropertyValue.getDbColumn();
/*     */   }
/*     */   
/*     */   public BeanProperty getBeanProperty() {
/* 218 */     return this.lastBeanProperty;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDateTimeCapable() {
/* 223 */     return (this.scalarType != null && this.scalarType.isDateTimeCapable());
/*     */   }
/*     */   
/*     */   public int getJdbcType() {
/* 227 */     return (this.scalarType == null) ? 0 : this.scalarType.getJdbcType();
/*     */   }
/*     */   
/*     */   public Object parseDateTime(long systemTimeMillis) {
/* 231 */     return this.scalarType.parseDateTime(systemTimeMillis);
/*     */   }
/*     */   
/*     */   public StringParser getStringParser() {
/* 235 */     return (StringParser)this.scalarType;
/*     */   }
/*     */   
/*     */   public StringFormatter getStringFormatter() {
/* 239 */     return (StringFormatter)this.scalarType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object elConvertType(Object value) {
/* 244 */     return this.lastElPropertyValue.elConvertType(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object elGetValue(Object bean) {
/* 249 */     for (int i = 0; i < this.chain.length; i++) {
/* 250 */       bean = this.chain[i].elGetValue(bean);
/* 251 */       if (bean == null) {
/* 252 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 256 */     return bean;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object elGetReference(Object bean) {
/* 261 */     Object prevBean = bean;
/* 262 */     for (int i = 0; i < this.last; i++)
/*     */     {
/* 264 */       prevBean = this.chain[i].elGetReference(prevBean);
/*     */     }
/*     */     
/* 267 */     bean = this.chain[this.last].elGetValue(prevBean);
/*     */     
/* 269 */     return bean;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void elSetLoaded(Object bean) {
/* 275 */     for (int i = 0; i < this.last; i++) {
/* 276 */       bean = this.chain[i].elGetValue(bean);
/* 277 */       if (bean == null) {
/*     */         break;
/*     */       }
/*     */     } 
/* 281 */     if (bean != null) {
/* 282 */       ((EntityBean)bean)._ebean_getIntercept().setLoaded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void elSetReference(Object bean) {
/* 288 */     for (int i = 0; i < this.last; i++) {
/* 289 */       bean = this.chain[i].elGetValue(bean);
/* 290 */       if (bean == null) {
/*     */         break;
/*     */       }
/*     */     } 
/* 294 */     if (bean != null) {
/* 295 */       ((EntityBean)bean)._ebean_getIntercept().setReference();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void elSetValue(Object bean, Object value, boolean populate, boolean reference) {
/* 301 */     Object prevBean = bean;
/* 302 */     if (populate) {
/* 303 */       for (int i = 0; i < this.last; i++)
/*     */       {
/* 305 */         prevBean = this.chain[i].elGetReference(prevBean);
/*     */       }
/*     */     } else {
/* 308 */       for (int i = 0; i < this.last; i++) {
/*     */         
/* 310 */         prevBean = this.chain[i].elGetValue(prevBean);
/* 311 */         if (prevBean == null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 316 */     if (prevBean != null)
/* 317 */       if (this.lastBeanProperty != null) {
/*     */         
/* 319 */         this.lastBeanProperty.setValueIntercept(prevBean, value);
/* 320 */         if (reference) {
/* 321 */           ((EntityBean)prevBean)._ebean_getIntercept().setReference();
/*     */         }
/*     */       } else {
/*     */         
/* 325 */         this.lastElPropertyValue.elSetValue(prevBean, value, populate, reference);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\el\ElPropertyChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */