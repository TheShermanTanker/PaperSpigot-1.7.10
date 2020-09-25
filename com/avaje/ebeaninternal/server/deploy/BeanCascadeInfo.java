/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import javax.persistence.CascadeType;
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
/*     */ public class BeanCascadeInfo
/*     */ {
/*     */   boolean delete;
/*     */   boolean save;
/*     */   boolean validate;
/*     */   
/*     */   public void setAttribute(String attr) {
/*  51 */     if (attr == null) {
/*     */       return;
/*     */     }
/*  54 */     attr = attr.toLowerCase();
/*  55 */     this.delete = (attr.indexOf("delete") > -1);
/*  56 */     if (!this.delete)
/*     */     {
/*  58 */       this.delete = (attr.indexOf("remove") > -1);
/*     */     }
/*  60 */     this.save = (attr.indexOf("save") > -1);
/*  61 */     if (!this.save)
/*     */     {
/*  63 */       this.save = (attr.indexOf("persist") > -1);
/*     */     }
/*  65 */     if (attr.indexOf("validate") > -1) {
/*  66 */       this.validate = true;
/*     */     }
/*     */     
/*  69 */     if (attr.indexOf("all") > -1) {
/*  70 */       this.delete = true;
/*  71 */       this.save = true;
/*  72 */       this.validate = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setTypes(CascadeType[] types) {
/*  77 */     for (int i = 0; i < types.length; i++) {
/*  78 */       setType(types[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setType(CascadeType type) {
/*  83 */     if (type.equals(CascadeType.ALL)) {
/*  84 */       this.save = true;
/*  85 */       this.delete = true;
/*     */     } 
/*  87 */     if (type.equals(CascadeType.REMOVE)) {
/*  88 */       this.delete = true;
/*     */     }
/*  90 */     if (type.equals(CascadeType.PERSIST)) {
/*  91 */       this.save = true;
/*     */     }
/*  93 */     if (type.equals(CascadeType.MERGE)) {
/*  94 */       this.save = true;
/*     */     }
/*  96 */     if (this.save || this.delete) {
/*  97 */       this.validate = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDelete() {
/* 105 */     return this.delete;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDelete(boolean isDelete) {
/* 111 */     this.delete = isDelete;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSave() {
/* 117 */     return this.save;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSave(boolean isUpdate) {
/* 124 */     this.save = isUpdate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidate() {
/* 131 */     return this.validate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValidate(boolean isValidate) {
/* 138 */     this.validate = isValidate;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanCascadeInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */