/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.text.StringFormatter;
/*     */ import com.avaje.ebean.text.StringParser;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanFkeyProperty
/*     */   implements ElPropertyValue
/*     */ {
/*     */   private final String placeHolder;
/*     */   private final String prefix;
/*     */   private final String name;
/*     */   private final String dbColumn;
/*     */   private int deployOrder;
/*     */   
/*     */   public BeanFkeyProperty(String prefix, String name, String dbColumn, int deployOrder) {
/*  20 */     this.prefix = prefix;
/*  21 */     this.name = name;
/*  22 */     this.dbColumn = dbColumn;
/*  23 */     this.deployOrder = deployOrder;
/*  24 */     this.placeHolder = calcPlaceHolder(prefix, dbColumn);
/*     */   }
/*     */   
/*     */   public int getDeployOrder() {
/*  28 */     return this.deployOrder;
/*     */   }
/*     */   
/*     */   private String calcPlaceHolder(String prefix, String dbColumn) {
/*  32 */     if (prefix != null) {
/*  33 */       return "${" + prefix + "}" + dbColumn;
/*     */     }
/*  35 */     return "${}" + dbColumn;
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanFkeyProperty create(String expression) {
/*  40 */     int len = expression.length() - this.name.length() - 1;
/*  41 */     String prefix = expression.substring(0, len);
/*     */     
/*  43 */     return new BeanFkeyProperty(prefix, this.name, this.dbColumn, this.deployOrder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDbEncrypted() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalEncrypted() {
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeployOnly() {
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsMany() {
/*  71 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsManySince(String sinceProperty) {
/*  75 */     return containsMany();
/*     */   }
/*     */   
/*     */   public String getDbColumn() {
/*  79 */     return this.dbColumn;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  83 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getElName() {
/*  87 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getAssocOneIdValues(Object value) {
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocOneIdExpr(String prefix, String operator) {
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocIdInExpr(String prefix) {
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocIdInValueExpr(int size) {
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAssocId() {
/* 122 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isAssocProperty() {
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   public String getElPlaceholder(boolean encrypted) {
/* 130 */     return this.placeHolder;
/*     */   }
/*     */   
/*     */   public String getElPrefix() {
/* 134 */     return this.prefix;
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/* 138 */     return false;
/*     */   }
/*     */   
/*     */   public int getJdbcType() {
/* 142 */     return 0;
/*     */   }
/*     */   
/*     */   public Object parseDateTime(long systemTimeMillis) {
/* 146 */     throw new RuntimeException("ElPropertyDeploy only - not implemented");
/*     */   }
/*     */   
/*     */   public StringFormatter getStringFormatter() {
/* 150 */     throw new RuntimeException("ElPropertyDeploy only - not implemented");
/*     */   }
/*     */   
/*     */   public StringParser getStringParser() {
/* 154 */     throw new RuntimeException("ElPropertyDeploy only - not implemented");
/*     */   }
/*     */   
/*     */   public void elSetReference(Object bean) {
/* 158 */     throw new RuntimeException("ElPropertyDeploy only - not implemented");
/*     */   }
/*     */   
/*     */   public Object elConvertType(Object value) {
/* 162 */     throw new RuntimeException("ElPropertyDeploy only - not implemented");
/*     */   }
/*     */   
/*     */   public void elSetValue(Object bean, Object value, boolean populate, boolean reference) {
/* 166 */     throw new RuntimeException("ElPropertyDeploy only - not implemented");
/*     */   }
/*     */   
/*     */   public Object elGetValue(Object bean) {
/* 170 */     throw new RuntimeException("ElPropertyDeploy only - not implemented");
/*     */   }
/*     */   
/*     */   public Object elGetReference(Object bean) {
/* 174 */     throw new RuntimeException("ElPropertyDeploy only - not implemented");
/*     */   }
/*     */   
/*     */   public BeanProperty getBeanProperty() {
/* 178 */     throw new RuntimeException("ElPropertyDeploy only - not implemented");
/*     */   }
/*     */   
/*     */   public String getDeployProperty() {
/* 182 */     throw new RuntimeException("ElPropertyDeploy only - not implemented");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanFkeyProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */