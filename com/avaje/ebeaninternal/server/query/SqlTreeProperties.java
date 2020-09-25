/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashSet;
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
/*     */ public class SqlTreeProperties
/*     */ {
/*     */   Set<String> includedProps;
/*     */   boolean readOnly;
/*     */   boolean includeId = true;
/*  35 */   TableJoin[] tableJoins = new TableJoin[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   List<BeanProperty> propsList = new ArrayList<BeanProperty>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   LinkedHashSet<String> propNames = new LinkedHashSet<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsProperty(String propName) {
/*  52 */     return this.propNames.contains(propName);
/*     */   }
/*     */   
/*     */   public void add(BeanProperty[] props) {
/*  56 */     for (BeanProperty beanProperty : props) {
/*  57 */       this.propsList.add(beanProperty);
/*     */     }
/*     */   }
/*     */   
/*     */   public void add(BeanProperty prop) {
/*  62 */     this.propsList.add(prop);
/*  63 */     this.propNames.add(prop.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanProperty[] getProps() {
/*  68 */     return this.propsList.<BeanProperty>toArray(new BeanProperty[this.propsList.size()]);
/*     */   }
/*     */   
/*     */   public boolean isIncludeId() {
/*  72 */     return this.includeId;
/*     */   }
/*     */   
/*     */   public void setIncludeId(boolean includeId) {
/*  76 */     this.includeId = includeId;
/*     */   }
/*     */   
/*     */   public boolean isPartialObject() {
/*  80 */     return (this.includedProps != null);
/*     */   }
/*     */   
/*     */   public Set<String> getIncludedProperties() {
/*  84 */     return this.includedProps;
/*     */   }
/*     */   
/*     */   public void setIncludedProperties(Set<String> includedProps) {
/*  88 */     this.includedProps = includedProps;
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/*  92 */     return this.readOnly;
/*     */   }
/*     */   
/*     */   public void setReadOnly(boolean readOnly) {
/*  96 */     this.readOnly = readOnly;
/*     */   }
/*     */   
/*     */   public TableJoin[] getTableJoins() {
/* 100 */     return this.tableJoins;
/*     */   }
/*     */   
/*     */   public void setTableJoins(TableJoin[] tableJoins) {
/* 104 */     this.tableJoins = tableJoins;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\SqlTreeProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */