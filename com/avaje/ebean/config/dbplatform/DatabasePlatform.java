/*     */ package com.avaje.ebean.config.dbplatform;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.sql.DataSource;
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
/*     */ public class DatabasePlatform
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(DatabasePlatform.class.getName());
/*     */ 
/*     */   
/*  39 */   protected String openQuote = "\"";
/*     */ 
/*     */   
/*  42 */   protected String closeQuote = "\"";
/*     */ 
/*     */   
/*  45 */   protected SqlLimiter sqlLimiter = new LimitOffsetSqlLimiter();
/*     */ 
/*     */   
/*  48 */   protected DbTypeMap dbTypeMap = new DbTypeMap();
/*     */ 
/*     */   
/*  51 */   protected DbDdlSyntax dbDdlSyntax = new DbDdlSyntax();
/*     */ 
/*     */   
/*  54 */   protected DbIdentity dbIdentity = new DbIdentity();
/*     */ 
/*     */   
/*  57 */   protected int booleanDbType = 16;
/*     */ 
/*     */   
/*  60 */   protected int blobDbType = 2004;
/*     */ 
/*     */   
/*  63 */   protected int clobDbType = 2005;
/*     */ 
/*     */   
/*     */   protected boolean treatEmptyStringsAsNull;
/*     */ 
/*     */   
/*  69 */   protected String name = "generic";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final char BACK_TICK = '`';
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DbEncrypt dbEncrypt;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean idInExpandedForm;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean selectCountWithAlias;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  96 */     return this.name;
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
/*     */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) {
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbEncrypt getDbEncrypt() {
/* 122 */     return this.dbEncrypt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbEncrypt(DbEncrypt dbEncrypt) {
/* 129 */     this.dbEncrypt = dbEncrypt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbTypeMap getDbTypeMap() {
/* 138 */     return this.dbTypeMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbDdlSyntax getDbDdlSyntax() {
/* 147 */     return this.dbDdlSyntax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCloseQuote() {
/* 156 */     return this.closeQuote;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOpenQuote() {
/* 165 */     return this.openQuote;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBooleanDbType() {
/* 174 */     return this.booleanDbType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlobDbType() {
/* 185 */     return this.blobDbType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getClobDbType() {
/* 195 */     return this.clobDbType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTreatEmptyStringsAsNull() {
/* 204 */     return this.treatEmptyStringsAsNull;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIdInExpandedForm() {
/* 213 */     return this.idInExpandedForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbIdentity getDbIdentity() {
/* 222 */     return this.dbIdentity;
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
/*     */   public SqlLimiter getSqlLimiter() {
/* 235 */     return this.sqlLimiter;
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
/*     */   public String convertQuotedIdentifiers(String dbName) {
/* 251 */     if (dbName != null && dbName.length() > 0 && 
/* 252 */       dbName.charAt(0) == '`') {
/* 253 */       if (dbName.charAt(dbName.length() - 1) == '`') {
/*     */         
/* 255 */         String quotedName = getOpenQuote();
/* 256 */         quotedName = quotedName + dbName.substring(1, dbName.length() - 1);
/* 257 */         quotedName = quotedName + getCloseQuote();
/*     */         
/* 259 */         return quotedName;
/*     */       } 
/*     */       
/* 262 */       logger.log(Level.SEVERE, "Missing backquote on [" + dbName + "]");
/*     */     } 
/*     */ 
/*     */     
/* 266 */     return dbName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelectCountWithAlias() {
/* 273 */     return this.selectCountWithAlias;
/*     */   }
/*     */   
/*     */   public String completeSql(String sql, SpiQuery<?> query) {
/* 277 */     if (Boolean.TRUE.equals(query.isForUpdate())) {
/* 278 */       sql = withForUpdate(sql);
/*     */     }
/*     */     
/* 281 */     return sql;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String withForUpdate(String sql) {
/* 286 */     if (logger.isLoggable(Level.INFO)) {
/* 287 */       logger.log(Level.INFO, "it seems your database does not support the 'for update' clause");
/*     */     }
/*     */     
/* 290 */     return sql;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\DatabasePlatform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */