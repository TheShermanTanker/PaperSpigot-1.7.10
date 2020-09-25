/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.Inheritance;
/*     */ import javax.persistence.Table;
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
/*     */ public abstract class AbstractNamingConvention
/*     */   implements NamingConvention
/*     */ {
/*  38 */   private static final Logger logger = Logger.getLogger(AbstractNamingConvention.class.getName());
/*     */ 
/*     */   
/*     */   public static final String DEFAULT_SEQ_FORMAT = "{table}_seq";
/*     */ 
/*     */   
/*     */   public static final String TABLE_PKCOLUMN_SEQ_FORMAT = "{table}_{column}_seq";
/*     */ 
/*     */   
/*     */   private String catalog;
/*     */ 
/*     */   
/*     */   private String schema;
/*     */ 
/*     */   
/*     */   private String sequenceFormat;
/*     */ 
/*     */   
/*     */   protected DatabasePlatform databasePlatform;
/*     */ 
/*     */   
/*     */   protected int maxConstraintNameLength;
/*     */ 
/*     */   
/*  62 */   protected int rhsPrefixLength = 3;
/*     */ 
/*     */   
/*     */   protected boolean useForeignKeyPrefix = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractNamingConvention(String sequenceFormat, boolean useForeignKeyPrefix) {
/*  70 */     this.sequenceFormat = sequenceFormat;
/*  71 */     this.useForeignKeyPrefix = useForeignKeyPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractNamingConvention(String sequenceFormat) {
/*  81 */     this.sequenceFormat = sequenceFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractNamingConvention() {
/*  89 */     this("{table}_seq");
/*     */   }
/*     */   
/*     */   public void setDatabasePlatform(DatabasePlatform databasePlatform) {
/*  93 */     this.databasePlatform = databasePlatform;
/*  94 */     this.maxConstraintNameLength = databasePlatform.getDbDdlSyntax().getMaxConstraintNameLength();
/*     */     
/*  96 */     logger.finer("Using maxConstraintNameLength of " + this.maxConstraintNameLength);
/*     */   }
/*     */   
/*     */   public String getSequenceName(String tableName, String pkColumn) {
/* 100 */     String s = this.sequenceFormat.replace("{table}", tableName);
/* 101 */     if (pkColumn == null) {
/* 102 */       pkColumn = "";
/*     */     }
/* 104 */     return s.replace("{column}", pkColumn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCatalog() {
/* 111 */     return this.catalog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCatalog(String catalog) {
/* 118 */     this.catalog = catalog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSchema() {
/* 125 */     return this.schema;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSchema(String schema) {
/* 132 */     this.schema = schema;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSequenceFormat() {
/* 139 */     return this.sequenceFormat;
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
/*     */   public void setSequenceFormat(String sequenceFormat) {
/* 154 */     this.sequenceFormat = sequenceFormat;
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
/*     */   public boolean isUseForeignKeyPrefix() {
/* 173 */     return this.useForeignKeyPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseForeignKeyPrefix(boolean useForeignKeyPrefix) {
/* 181 */     this.useForeignKeyPrefix = useForeignKeyPrefix;
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
/*     */   protected abstract TableName getTableNameByConvention(Class<?> paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableName getTableName(Class<?> beanClass) {
/* 202 */     TableName tableName = getTableNameFromAnnotation(beanClass);
/* 203 */     if (tableName == null) {
/*     */       
/* 205 */       Class<?> supCls = beanClass.getSuperclass();
/* 206 */       Inheritance inheritance = supCls.<Inheritance>getAnnotation(Inheritance.class);
/* 207 */       if (inheritance != null)
/*     */       {
/*     */         
/* 210 */         return getTableName(supCls);
/*     */       }
/*     */       
/* 213 */       tableName = getTableNameByConvention(beanClass);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 218 */     String catalog = tableName.getCatalog();
/* 219 */     if (isEmpty(catalog)) {
/* 220 */       catalog = getCatalog();
/*     */     }
/* 222 */     String schema = tableName.getSchema();
/* 223 */     if (isEmpty(schema)) {
/* 224 */       schema = getSchema();
/*     */     }
/* 226 */     return new TableName(catalog, schema, tableName.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public TableName getM2MJoinTableName(TableName lhsTable, TableName rhsTable) {
/* 231 */     StringBuilder buffer = new StringBuilder();
/* 232 */     buffer.append(lhsTable.getName());
/* 233 */     buffer.append("_");
/*     */     
/* 235 */     String rhsTableName = rhsTable.getName();
/* 236 */     if (rhsTableName.indexOf('_') < this.rhsPrefixLength)
/*     */     {
/* 238 */       rhsTableName = rhsTableName.substring(rhsTableName.indexOf('_') + 1);
/*     */     }
/* 240 */     buffer.append(rhsTableName);
/*     */     
/* 242 */     int maxConstraintNameLength = this.databasePlatform.getDbDdlSyntax().getMaxConstraintNameLength();
/*     */ 
/*     */     
/* 245 */     if (buffer.length() > maxConstraintNameLength) {
/* 246 */       buffer.setLength(maxConstraintNameLength);
/*     */     }
/*     */     
/* 249 */     return new TableName(lhsTable.getCatalog(), lhsTable.getSchema(), buffer.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableName getTableNameFromAnnotation(Class<?> beanClass) {
/* 257 */     Table t = findTableAnnotation(beanClass);
/*     */ 
/*     */     
/* 260 */     if (t != null && !isEmpty(t.name()))
/*     */     {
/*     */       
/* 263 */       return new TableName(quoteIdentifiers(t.catalog()), quoteIdentifiers(t.schema()), quoteIdentifiers(t.name()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 268 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Table findTableAnnotation(Class<?> cls) {
/* 275 */     if (cls.equals(Object.class)) {
/* 276 */       return null;
/*     */     }
/* 278 */     Table table = cls.<Table>getAnnotation(Table.class);
/* 279 */     if (table != null) {
/* 280 */       return table;
/*     */     }
/* 282 */     return findTableAnnotation(cls.getSuperclass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String quoteIdentifiers(String s) {
/* 290 */     return this.databasePlatform.convertQuotedIdentifiers(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEmpty(String s) {
/* 297 */     if (s == null || s.trim().length() == 0) {
/* 298 */       return true;
/*     */     }
/* 300 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\AbstractNamingConvention.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */