/*     */ package com.avaje.ebean.config;
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
/*     */ public final class TableName
/*     */ {
/*     */   private String catalog;
/*     */   private String schema;
/*     */   private String name;
/*     */   
/*     */   public TableName(String catalog, String schema, String name) {
/*  47 */     this.catalog = (catalog != null) ? catalog.trim() : null;
/*  48 */     this.schema = (schema != null) ? schema.trim() : null;
/*  49 */     this.name = (name != null) ? name.trim() : null;
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
/*     */   public TableName(String qualifiedTableName) {
/*  65 */     String[] split = qualifiedTableName.split("\\.");
/*  66 */     int len = split.length;
/*  67 */     if (split.length > 3) {
/*  68 */       String m = "Error splitting " + qualifiedTableName + ". Expecting at most 2 '.' characters";
/*  69 */       throw new RuntimeException(m);
/*     */     } 
/*  71 */     if (len == 3) {
/*  72 */       this.catalog = split[0];
/*     */     }
/*  74 */     if (len >= 2) {
/*  75 */       this.schema = split[len - 2];
/*     */     }
/*  77 */     this.name = split[len - 1];
/*     */   }
/*     */   
/*     */   public String toString() {
/*  81 */     return getQualifiedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCatalog() {
/*  90 */     return this.catalog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSchema() {
/*  99 */     return this.schema;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 108 */     return this.name;
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
/*     */   public String getQualifiedName() {
/* 121 */     StringBuilder buffer = new StringBuilder();
/*     */ 
/*     */     
/* 124 */     if (this.catalog != null) {
/* 125 */       buffer.append(this.catalog);
/*     */     }
/*     */ 
/*     */     
/* 129 */     if (this.schema != null) {
/* 130 */       if (buffer.length() > 0) {
/* 131 */         buffer.append(".");
/*     */       }
/* 133 */       buffer.append(this.schema);
/*     */     } 
/*     */     
/* 136 */     if (buffer.length() > 0) {
/* 137 */       buffer.append(".");
/*     */     }
/* 139 */     buffer.append(this.name);
/*     */     
/* 141 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 150 */     return (this.name != null && this.name.length() > 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\TableName.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */