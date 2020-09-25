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
/*     */ public class DbType
/*     */ {
/*     */   private final String name;
/*     */   private final int defaultLength;
/*     */   private final int defaultScale;
/*     */   private final boolean canHaveLength;
/*     */   
/*     */   public DbType(String name) {
/*  35 */     this(name, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbType(String name, int defaultLength) {
/*  42 */     this(name, defaultLength, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbType(String name, int defaultPrecision, int defaultScale) {
/*  49 */     this.name = name;
/*  50 */     this.defaultLength = defaultPrecision;
/*  51 */     this.defaultScale = defaultScale;
/*  52 */     this.canHaveLength = true;
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
/*     */   public DbType(String name, boolean canHaveLength) {
/*  64 */     this.name = name;
/*  65 */     this.defaultLength = 0;
/*  66 */     this.defaultScale = 0;
/*  67 */     this.canHaveLength = canHaveLength;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String renderType(int deployLength, int deployScale) {
/*  86 */     StringBuilder sb = new StringBuilder();
/*  87 */     sb.append(this.name);
/*     */     
/*  89 */     if (this.canHaveLength) {
/*     */       
/*  91 */       int len = (deployLength != 0) ? deployLength : this.defaultLength;
/*     */       
/*  93 */       if (len > 0) {
/*  94 */         sb.append("(");
/*  95 */         sb.append(len);
/*  96 */         int scale = (deployScale != 0) ? deployScale : this.defaultScale;
/*  97 */         if (scale > 0) {
/*  98 */           sb.append(",");
/*  99 */           sb.append(scale);
/*     */         } 
/* 101 */         sb.append(")");
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\DbType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */