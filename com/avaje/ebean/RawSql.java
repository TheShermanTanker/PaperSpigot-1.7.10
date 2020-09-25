/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import com.avaje.ebeaninternal.util.CamelCaseHelper;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class RawSql
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Sql sql;
/*     */   private final ColumnMapping columnMapping;
/*     */   
/*     */   protected RawSql(Sql sql, ColumnMapping columnMapping) {
/* 164 */     this.sql = sql;
/* 165 */     this.columnMapping = columnMapping;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sql getSql() {
/* 172 */     return this.sql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColumnMapping getColumnMapping() {
/* 179 */     return this.columnMapping;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryHash() {
/* 186 */     return 31 * this.sql.queryHash() + this.columnMapping.queryHash();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Sql
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     private final boolean parsed;
/*     */ 
/*     */     
/*     */     private final String unparsedSql;
/*     */ 
/*     */     
/*     */     private final String preFrom;
/*     */ 
/*     */     
/*     */     private final String preWhere;
/*     */     
/*     */     private final boolean andWhereExpr;
/*     */     
/*     */     private final String preHaving;
/*     */     
/*     */     private final boolean andHavingExpr;
/*     */     
/*     */     private final String orderBy;
/*     */     
/*     */     private final boolean distinct;
/*     */     
/*     */     private final int queryHashCode;
/*     */ 
/*     */     
/*     */     protected Sql(String unparsedSql) {
/* 222 */       this.queryHashCode = unparsedSql.hashCode();
/* 223 */       this.parsed = false;
/* 224 */       this.unparsedSql = unparsedSql;
/* 225 */       this.preFrom = null;
/* 226 */       this.preHaving = null;
/* 227 */       this.preWhere = null;
/* 228 */       this.andHavingExpr = false;
/* 229 */       this.andWhereExpr = false;
/* 230 */       this.orderBy = null;
/* 231 */       this.distinct = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Sql(int queryHashCode, String preFrom, String preWhere, boolean andWhereExpr, String preHaving, boolean andHavingExpr, String orderBy, boolean distinct) {
/* 240 */       this.queryHashCode = queryHashCode;
/* 241 */       this.parsed = true;
/* 242 */       this.unparsedSql = null;
/* 243 */       this.preFrom = preFrom;
/* 244 */       this.preHaving = preHaving;
/* 245 */       this.preWhere = preWhere;
/* 246 */       this.andHavingExpr = andHavingExpr;
/* 247 */       this.andWhereExpr = andWhereExpr;
/* 248 */       this.orderBy = orderBy;
/* 249 */       this.distinct = distinct;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int queryHash() {
/* 256 */       return this.queryHashCode;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 260 */       if (!this.parsed) {
/* 261 */         return "unparsed[" + this.unparsedSql + "]";
/*     */       }
/* 263 */       return "select[" + this.preFrom + "] preWhere[" + this.preWhere + "] preHaving[" + this.preHaving + "] orderBy[" + this.orderBy + "]";
/*     */     }
/*     */     
/*     */     public boolean isDistinct() {
/* 267 */       return this.distinct;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isParsed() {
/* 278 */       return this.parsed;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getUnparsedSql() {
/* 285 */       return this.unparsedSql;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPreFrom() {
/* 292 */       return this.preFrom;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPreWhere() {
/* 299 */       return this.preWhere;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAndWhereExpr() {
/* 307 */       return this.andWhereExpr;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPreHaving() {
/* 314 */       return this.preHaving;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAndHavingExpr() {
/* 322 */       return this.andHavingExpr;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getOrderBy() {
/* 329 */       return this.orderBy;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class ColumnMapping
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     private final LinkedHashMap<String, Column> dbColumnMap;
/*     */ 
/*     */     
/*     */     private final Map<String, String> propertyMap;
/*     */     
/*     */     private final Map<String, Column> propertyColumnMap;
/*     */     
/*     */     private final boolean parsed;
/*     */     
/*     */     private final boolean immutable;
/*     */     
/*     */     private final int queryHashCode;
/*     */ 
/*     */     
/*     */     protected ColumnMapping(List<Column> columns) {
/* 356 */       this.queryHashCode = 0;
/* 357 */       this.immutable = false;
/* 358 */       this.parsed = true;
/* 359 */       this.propertyMap = null;
/* 360 */       this.propertyColumnMap = null;
/* 361 */       this.dbColumnMap = new LinkedHashMap<String, Column>();
/* 362 */       for (int i = 0; i < columns.size(); i++) {
/* 363 */         Column c = columns.get(i);
/* 364 */         this.dbColumnMap.put(c.getDbColumn(), c);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ColumnMapping() {
/* 372 */       this.queryHashCode = 0;
/* 373 */       this.immutable = false;
/* 374 */       this.parsed = false;
/* 375 */       this.propertyMap = null;
/* 376 */       this.propertyColumnMap = null;
/* 377 */       this.dbColumnMap = new LinkedHashMap<String, Column>();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ColumnMapping(boolean parsed, LinkedHashMap<String, Column> dbColumnMap) {
/* 384 */       this.immutable = true;
/* 385 */       this.parsed = parsed;
/* 386 */       this.dbColumnMap = dbColumnMap;
/*     */       
/* 388 */       int hc = ColumnMapping.class.getName().hashCode();
/*     */       
/* 390 */       HashMap<String, Column> pcMap = new HashMap<String, Column>();
/* 391 */       HashMap<String, String> pMap = new HashMap<String, String>();
/*     */       
/* 393 */       for (Column c : dbColumnMap.values()) {
/* 394 */         pMap.put(c.getPropertyName(), c.getDbColumn());
/* 395 */         pcMap.put(c.getPropertyName(), c);
/*     */         
/* 397 */         hc = ((31 * hc) + c.getPropertyName() == null) ? 0 : c.getPropertyName().hashCode();
/* 398 */         hc = ((31 * hc) + c.getDbColumn() == null) ? 0 : c.getDbColumn().hashCode();
/*     */       } 
/* 400 */       this.propertyMap = Collections.unmodifiableMap(pMap);
/* 401 */       this.propertyColumnMap = Collections.unmodifiableMap(pcMap);
/* 402 */       this.queryHashCode = hc;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ColumnMapping createImmutableCopy() {
/* 413 */       for (Column c : this.dbColumnMap.values()) {
/* 414 */         c.checkMapping();
/*     */       }
/*     */       
/* 417 */       return new ColumnMapping(this.parsed, this.dbColumnMap);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void columnMapping(String dbColumn, String propertyName) {
/* 422 */       if (this.immutable) {
/* 423 */         throw new IllegalStateException("Should never happen");
/*     */       }
/* 425 */       if (!this.parsed) {
/* 426 */         int pos = this.dbColumnMap.size();
/* 427 */         this.dbColumnMap.put(dbColumn, new Column(pos, dbColumn, null, propertyName));
/*     */       } else {
/* 429 */         Column column = this.dbColumnMap.get(dbColumn);
/* 430 */         if (column == null) {
/* 431 */           String msg = "DB Column [" + dbColumn + "] not found in mapping. Expecting one of [" + this.dbColumnMap.keySet() + "]";
/* 432 */           throw new IllegalArgumentException(msg);
/*     */         } 
/* 434 */         column.setPropertyName(propertyName);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int queryHash() {
/* 442 */       if (this.queryHashCode == 0) {
/* 443 */         throw new RuntimeException("Bug: queryHashCode == 0");
/*     */       }
/* 445 */       return this.queryHashCode;
/*     */     }
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
/*     */     public boolean isParsed() {
/* 458 */       return this.parsed;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 465 */       return this.dbColumnMap.size();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Map<String, Column> mapping() {
/* 472 */       return this.dbColumnMap;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, String> getMapping() {
/* 479 */       return this.propertyMap;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getIndexPosition(String property) {
/* 486 */       Column c = this.propertyColumnMap.get(property);
/* 487 */       return (c == null) ? -1 : c.getIndexPos();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Iterator<Column> getColumns() {
/* 494 */       return this.dbColumnMap.values().iterator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static class Column
/*     */       implements Serializable
/*     */     {
/*     */       private static final long serialVersionUID = 1L;
/*     */       
/*     */       private final int indexPos;
/*     */       
/*     */       private final String dbColumn;
/*     */       
/*     */       private final String dbAlias;
/*     */       
/*     */       private String propertyName;
/*     */ 
/*     */       
/*     */       public Column(int indexPos, String dbColumn, String dbAlias) {
/* 514 */         this(indexPos, dbColumn, dbAlias, derivePropertyName(dbAlias, dbColumn));
/*     */       }
/*     */       
/*     */       private Column(int indexPos, String dbColumn, String dbAlias, String propertyName) {
/* 518 */         this.indexPos = indexPos;
/* 519 */         this.dbColumn = dbColumn;
/* 520 */         this.dbAlias = dbAlias;
/* 521 */         if (propertyName == null && dbAlias != null) {
/* 522 */           this.propertyName = dbAlias;
/*     */         } else {
/* 524 */           this.propertyName = propertyName;
/*     */         } 
/*     */       }
/*     */       
/*     */       private static String derivePropertyName(String dbAlias, String dbColumn) {
/* 529 */         if (dbAlias != null) {
/* 530 */           return dbAlias;
/*     */         }
/* 532 */         int dotPos = dbColumn.indexOf('.');
/* 533 */         if (dotPos > -1) {
/* 534 */           dbColumn = dbColumn.substring(dotPos + 1);
/*     */         }
/* 536 */         return CamelCaseHelper.toCamelFromUnderscore(dbColumn);
/*     */       }
/*     */       
/*     */       private void checkMapping() {
/* 540 */         if (this.propertyName == null) {
/* 541 */           String msg = "No propertyName defined (Column mapping) for dbColumn [" + this.dbColumn + "]";
/* 542 */           throw new IllegalStateException(msg);
/*     */         } 
/*     */       }
/*     */       
/*     */       public String toString() {
/* 547 */         return this.dbColumn + "->" + this.propertyName;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public int getIndexPos() {
/* 554 */         return this.indexPos;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public String getDbColumn() {
/* 561 */         return this.dbColumn;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public String getDbAlias() {
/* 568 */         return this.dbAlias;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public String getPropertyName() {
/* 575 */         return this.propertyName;
/*     */       }
/*     */       
/*     */       private void setPropertyName(String propertyName) {
/* 579 */         this.propertyName = propertyName;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\RawSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */