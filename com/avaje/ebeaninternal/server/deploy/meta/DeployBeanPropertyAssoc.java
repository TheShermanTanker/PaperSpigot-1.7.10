/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanCascadeInfo;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanTable;
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
/*     */ public abstract class DeployBeanPropertyAssoc<T>
/*     */   extends DeployBeanProperty
/*     */ {
/*     */   Class<T> targetType;
/*  38 */   BeanCascadeInfo cascadeInfo = new BeanCascadeInfo();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BeanTable beanTable;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   DeployTableJoin tableJoin = new DeployTableJoin();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isOuterJoin = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String extraWhere;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String mappedBy;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployBeanPropertyAssoc(DeployBeanDescriptor<?> desc, Class<T> targetType) {
/*  69 */     super(desc, targetType, null, null);
/*  70 */     this.targetType = targetType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScalar() {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<T> getTargetType() {
/*  89 */     return this.targetType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOuterJoin() {
/*  96 */     return this.isOuterJoin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOuterJoin(boolean isOuterJoin) {
/* 103 */     this.isOuterJoin = isOuterJoin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtraWhere() {
/* 111 */     return this.extraWhere;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtraWhere(String extraWhere) {
/* 119 */     this.extraWhere = extraWhere;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployTableJoin getTableJoin() {
/* 126 */     return this.tableJoin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanTable getBeanTable() {
/* 137 */     return this.beanTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBeanTable(BeanTable beanTable) {
/* 144 */     this.beanTable = beanTable;
/* 145 */     getTableJoin().setTable(beanTable.getBaseTable());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCascadeInfo getCascadeInfo() {
/* 152 */     return this.cascadeInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMappedBy() {
/* 164 */     return this.mappedBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMappedBy(String mappedBy) {
/* 171 */     if (!"".equals(mappedBy))
/* 172 */       this.mappedBy = mappedBy; 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertyAssoc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */