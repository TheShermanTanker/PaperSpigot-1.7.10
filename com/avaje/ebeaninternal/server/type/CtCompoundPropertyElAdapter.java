/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.StringFormatter;
/*     */ import com.avaje.ebean.text.StringParser;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
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
/*     */ public class CtCompoundPropertyElAdapter
/*     */   implements ElPropertyValue
/*     */ {
/*     */   private final CtCompoundProperty prop;
/*     */   private int deployOrder;
/*     */   
/*     */   public CtCompoundPropertyElAdapter(CtCompoundProperty prop) {
/*  43 */     this.prop = prop;
/*     */   }
/*     */   
/*     */   public void setDeployOrder(int deployOrder) {
/*  47 */     this.deployOrder = deployOrder;
/*     */   }
/*     */   
/*     */   public Object elConvertType(Object value) {
/*  51 */     return value;
/*     */   }
/*     */   
/*     */   public Object elGetReference(Object bean) {
/*  55 */     return bean;
/*     */   }
/*     */   
/*     */   public Object elGetValue(Object bean) {
/*  59 */     return this.prop.getValue(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void elSetReference(Object bean) {}
/*     */ 
/*     */   
/*     */   public void elSetValue(Object bean, Object value, boolean populate, boolean reference) {
/*  67 */     this.prop.setValue(bean, value);
/*     */   }
/*     */   
/*     */   public int getDeployOrder() {
/*  71 */     return this.deployOrder;
/*     */   }
/*     */   
/*     */   public String getAssocOneIdExpr(String prefix, String operator) {
/*  75 */     throw new RuntimeException("Not Supported or Expected");
/*     */   }
/*     */   
/*     */   public Object[] getAssocOneIdValues(Object bean) {
/*  79 */     throw new RuntimeException("Not Supported or Expected");
/*     */   }
/*     */   
/*     */   public String getAssocIdInExpr(String prefix) {
/*  83 */     throw new RuntimeException("Not Supported or Expected");
/*     */   }
/*     */   
/*     */   public String getAssocIdInValueExpr(int size) {
/*  87 */     throw new RuntimeException("Not Supported or Expected");
/*     */   }
/*     */   
/*     */   public BeanProperty getBeanProperty() {
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   public StringFormatter getStringFormatter() {
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public StringParser getStringParser() {
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isDbEncrypted() {
/* 103 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isLocalEncrypted() {
/* 107 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isAssocId() {
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isAssocProperty() {
/* 115 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   public int getJdbcType() {
/* 123 */     return 0;
/*     */   }
/*     */   
/*     */   public Object parseDateTime(long systemTimeMillis) {
/* 127 */     throw new RuntimeException("Not Supported or Expected");
/*     */   }
/*     */   
/*     */   public boolean containsMany() {
/* 131 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsManySince(String sinceProperty) {
/* 135 */     return containsMany();
/*     */   }
/*     */   
/*     */   public String getDbColumn() {
/* 139 */     return null;
/*     */   }
/*     */   
/*     */   public String getElPlaceholder(boolean encrypted) {
/* 143 */     return null;
/*     */   }
/*     */   
/*     */   public String getElPrefix() {
/* 147 */     return null;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 151 */     return this.prop.getPropertyName();
/*     */   }
/*     */   
/*     */   public String getElName() {
/* 155 */     return this.prop.getPropertyName();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\CtCompoundPropertyElAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */