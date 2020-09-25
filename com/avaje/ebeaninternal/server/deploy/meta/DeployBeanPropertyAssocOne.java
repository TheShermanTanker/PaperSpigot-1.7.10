/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
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
/*     */ public class DeployBeanPropertyAssocOne<T>
/*     */   extends DeployBeanPropertyAssoc<T>
/*     */ {
/*     */   boolean oneToOne;
/*     */   boolean oneToOneExported;
/*     */   boolean importedPrimaryKey;
/*     */   DeployBeanEmbedded deployEmbedded;
/*     */   
/*     */   public DeployBeanPropertyAssocOne(DeployBeanDescriptor<?> desc, Class<T> targetType) {
/*  42 */     super(desc, targetType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployBeanEmbedded getDeployEmbedded() {
/*  51 */     if (this.deployEmbedded == null) {
/*  52 */       this.deployEmbedded = new DeployBeanEmbedded();
/*     */     }
/*  54 */     return this.deployEmbedded;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDbColumn() {
/*  59 */     DeployTableJoinColumn[] columns = this.tableJoin.columns();
/*  60 */     if (columns.length == 1) {
/*  61 */       return columns[0].getLocalDbColumn();
/*     */     }
/*  63 */     return super.getDbColumn();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getElPlaceHolder(BeanDescriptor.EntityType et) {
/*  68 */     return super.getElPlaceHolder(et);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOneToOne() {
/*  75 */     return this.oneToOne;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOneToOne(boolean oneToOne) {
/*  82 */     this.oneToOne = oneToOne;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOneToOneExported() {
/*  89 */     return this.oneToOneExported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOneToOneExported(boolean oneToOneExported) {
/*  97 */     this.oneToOneExported = oneToOneExported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImportedPrimaryKey() {
/* 104 */     return this.importedPrimaryKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImportedPrimaryKey(boolean importedPrimaryKey) {
/* 111 */     this.importedPrimaryKey = importedPrimaryKey;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertyAssocOne.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */