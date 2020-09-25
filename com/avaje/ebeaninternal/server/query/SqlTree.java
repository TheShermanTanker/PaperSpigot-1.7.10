/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class SqlTree
/*     */ {
/*     */   private SqlTreeNode rootNode;
/*     */   private BeanPropertyAssocMany<?> manyProperty;
/*     */   private String manyPropertyName;
/*     */   private ElPropertyValue manyPropEl;
/*     */   private Set<String> includes;
/*     */   private String summary;
/*     */   private String selectSql;
/*     */   private String fromSql;
/*     */   private BeanProperty[] encryptedProps;
/*     */   private String inheritanceWhereSql;
/*     */   
/*     */   public List<String> buildSelectExpressionChain() {
/*  74 */     ArrayList<String> list = new ArrayList<String>();
/*  75 */     this.rootNode.buildSelectExpressionChain(list);
/*  76 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getIncludes() {
/*  83 */     return this.includes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIncludes(Set<String> includes) {
/*  90 */     this.includes = includes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setManyProperty(BeanPropertyAssocMany<?> manyProperty, String manyPropertyName, ElPropertyValue manyPropEl) {
/*  97 */     this.manyProperty = manyProperty;
/*  98 */     this.manyPropertyName = manyPropertyName;
/*  99 */     this.manyPropEl = manyPropEl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelectSql() {
/* 106 */     return this.selectSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectSql(String selectSql) {
/* 113 */     this.selectSql = selectSql;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFromSql() {
/* 118 */     return this.fromSql;
/*     */   }
/*     */   
/*     */   public void setFromSql(String fromSql) {
/* 122 */     this.fromSql = fromSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInheritanceWhereSql() {
/* 129 */     return this.inheritanceWhereSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInheritanceWhereSql(String whereSql) {
/* 136 */     this.inheritanceWhereSql = whereSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSummary(String summary) {
/* 143 */     this.summary = summary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSummary() {
/* 150 */     return this.summary;
/*     */   }
/*     */   
/*     */   public SqlTreeNode getRootNode() {
/* 154 */     return this.rootNode;
/*     */   }
/*     */   
/*     */   public void setRootNode(SqlTreeNode rootNode) {
/* 158 */     this.rootNode = rootNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyAssocMany<?> getManyProperty() {
/* 166 */     return this.manyProperty;
/*     */   }
/*     */   
/*     */   public String getManyPropertyName() {
/* 170 */     return this.manyPropertyName;
/*     */   }
/*     */   
/*     */   public ElPropertyValue getManyPropertyEl() {
/* 174 */     return this.manyPropEl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isManyIncluded() {
/* 181 */     return (this.manyProperty != null);
/*     */   }
/*     */   
/*     */   public BeanProperty[] getEncryptedProps() {
/* 185 */     return this.encryptedProps;
/*     */   }
/*     */   
/*     */   public void setEncryptedProps(BeanProperty[] encryptedProps) {
/* 189 */     this.encryptedProps = encryptedProps;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\SqlTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */