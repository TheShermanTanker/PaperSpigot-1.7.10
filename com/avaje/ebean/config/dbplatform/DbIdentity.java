/*     */ package com.avaje.ebean.config.dbplatform;
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
/*     */ public class DbIdentity
/*     */ {
/*     */   private boolean supportsSequence;
/*     */   private boolean supportsIdentity;
/*     */   private boolean supportsGetGeneratedKeys;
/*     */   private String selectLastInsertedIdTemplate;
/*  19 */   private IdType idType = IdType.IDENTITY;
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
/*     */   public boolean isSupportsGetGeneratedKeys() {
/*  31 */     return this.supportsGetGeneratedKeys;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSupportsGetGeneratedKeys(boolean supportsGetGeneratedKeys) {
/*  38 */     this.supportsGetGeneratedKeys = supportsGetGeneratedKeys;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelectLastInsertedId(String table) {
/*  49 */     if (this.selectLastInsertedIdTemplate == null) {
/*  50 */       return null;
/*     */     }
/*  52 */     return this.selectLastInsertedIdTemplate.replace("{table}", table);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectLastInsertedIdTemplate(String selectLastInsertedIdTemplate) {
/*  67 */     this.selectLastInsertedIdTemplate = selectLastInsertedIdTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportsSequence() {
/*  74 */     return this.supportsSequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSupportsSequence(boolean supportsSequence) {
/*  83 */     this.supportsSequence = supportsSequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportsIdentity() {
/*  91 */     return this.supportsIdentity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSupportsIdentity(boolean supportsIdentity) {
/*  98 */     this.supportsIdentity = supportsIdentity;
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
/*     */   public IdType getIdType() {
/* 110 */     return this.idType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIdType(IdType idType) {
/* 117 */     this.idType = idType;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\DbIdentity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */