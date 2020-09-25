/*     */ package com.avaje.ebean.config.dbplatform;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DbDdlSyntax
/*     */ {
/*     */   private boolean renderIndexForFkey = true;
/*     */   private boolean inlinePrimaryKeyConstraint = false;
/*     */   private boolean addOneToOneUniqueContraint = false;
/*  15 */   private int maxConstraintNameLength = 32;
/*     */   
/*  17 */   private int columnNameWidth = 25;
/*     */   
/*     */   private String dropTableCascade;
/*     */   
/*     */   private String dropIfExists;
/*  22 */   private String newLine = "\r\n";
/*     */   
/*  24 */   private String identity = "auto_increment";
/*     */   
/*  26 */   private String pkPrefix = "pk_";
/*     */ 
/*     */   
/*     */   private String disableReferentialIntegrity;
/*     */ 
/*     */   
/*     */   private String enableReferentialIntegrity;
/*     */   
/*     */   private String foreignKeySuffix;
/*     */ 
/*     */   
/*     */   public String getPrimaryKeyName(String tableName) {
/*  38 */     String pk = this.pkPrefix + tableName;
/*  39 */     if (pk.length() > this.maxConstraintNameLength)
/*     */     {
/*  41 */       pk = pk.substring(0, this.maxConstraintNameLength);
/*     */     }
/*  43 */     return pk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIdentity() {
/*  50 */     return this.identity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIdentity(String identity) {
/*  57 */     this.identity = identity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnNameWidth() {
/*  64 */     return this.columnNameWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumnNameWidth(int columnNameWidth) {
/*  71 */     this.columnNameWidth = columnNameWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewLine() {
/*  78 */     return this.newLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNewLine(String newLine) {
/*  85 */     this.newLine = newLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPkPrefix() {
/*  92 */     return this.pkPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPkPrefix(String pkPrefix) {
/*  99 */     this.pkPrefix = pkPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisableReferentialIntegrity() {
/* 106 */     return this.disableReferentialIntegrity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisableReferentialIntegrity(String disableReferentialIntegrity) {
/* 113 */     this.disableReferentialIntegrity = disableReferentialIntegrity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEnableReferentialIntegrity() {
/* 120 */     return this.enableReferentialIntegrity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnableReferentialIntegrity(String enableReferentialIntegrity) {
/* 127 */     this.enableReferentialIntegrity = enableReferentialIntegrity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRenderIndexForFkey() {
/* 134 */     return this.renderIndexForFkey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderIndexForFkey(boolean renderIndexForFkey) {
/* 141 */     this.renderIndexForFkey = renderIndexForFkey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDropIfExists() {
/* 149 */     return this.dropIfExists;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDropIfExists(String dropIfExists) {
/* 157 */     this.dropIfExists = dropIfExists;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDropTableCascade() {
/* 164 */     return this.dropTableCascade;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDropTableCascade(String dropTableCascade) {
/* 171 */     this.dropTableCascade = dropTableCascade;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getForeignKeySuffix() {
/* 178 */     return this.foreignKeySuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForeignKeySuffix(String foreignKeySuffix) {
/* 185 */     this.foreignKeySuffix = foreignKeySuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxConstraintNameLength() {
/* 192 */     return this.maxConstraintNameLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxConstraintNameLength(int maxFkeyLength) {
/* 199 */     this.maxConstraintNameLength = maxFkeyLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAddOneToOneUniqueContraint() {
/* 208 */     return this.addOneToOneUniqueContraint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAddOneToOneUniqueContraint(boolean addOneToOneUniqueContraint) {
/* 215 */     this.addOneToOneUniqueContraint = addOneToOneUniqueContraint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInlinePrimaryKeyConstraint() {
/* 222 */     return this.inlinePrimaryKeyConstraint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInlinePrimaryKeyConstraint(boolean inlinePrimaryKeyConstraint) {
/* 229 */     this.inlinePrimaryKeyConstraint = inlinePrimaryKeyConstraint;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIndexName(String table, String propName, int ixCount) {
/* 234 */     StringBuilder buffer = new StringBuilder();
/* 235 */     buffer.append("ix_");
/* 236 */     buffer.append(table);
/* 237 */     buffer.append("_");
/* 238 */     buffer.append(propName);
/*     */     
/* 240 */     addSuffix(buffer, ixCount);
/*     */     
/* 242 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getForeignKeyName(String table, String propName, int fkCount) {
/* 247 */     StringBuilder buffer = new StringBuilder();
/* 248 */     buffer.append("fk_");
/* 249 */     buffer.append(table);
/* 250 */     buffer.append("_");
/* 251 */     buffer.append(propName);
/*     */     
/* 253 */     addSuffix(buffer, fkCount);
/*     */     
/* 255 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addSuffix(StringBuilder buffer, int count) {
/* 265 */     String suffixNr = Integer.toString(count);
/* 266 */     int suffixLen = suffixNr.length() + 1;
/*     */     
/* 268 */     if (buffer.length() + suffixLen > this.maxConstraintNameLength) {
/* 269 */       buffer.setLength(this.maxConstraintNameLength - suffixLen);
/*     */     }
/* 271 */     buffer.append("_");
/* 272 */     buffer.append(suffixNr);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\DbDdlSyntax.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */