/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.querydefn.SimpleTextParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DRawSqlParser
/*     */ {
/*     */   public static final String $_AND_HAVING = "${andHaving}";
/*     */   public static final String $_HAVING = "${having}";
/*     */   public static final String $_AND_WHERE = "${andWhere}";
/*     */   public static final String $_WHERE = "${where}";
/*     */   private static final String ORDER_BY = "order by";
/*     */   private final SimpleTextParser textParser;
/*     */   private String sql;
/*     */   private int placeHolderWhere;
/*     */   private int placeHolderAndWhere;
/*     */   private int placeHolderHaving;
/*     */   private int placeHolderAndHaving;
/*     */   private boolean hasPlaceHolders;
/*  32 */   private int selectPos = -1;
/*  33 */   private int distinctPos = -1;
/*  34 */   private int fromPos = -1;
/*  35 */   private int wherePos = -1;
/*  36 */   private int groupByPos = -1;
/*  37 */   private int havingPos = -1;
/*  38 */   private int orderByPos = -1;
/*     */   
/*     */   private boolean whereExprAnd;
/*  41 */   private int whereExprPos = -1;
/*     */   private boolean havingExprAnd;
/*  43 */   private int havingExprPos = -1;
/*     */   
/*     */   public static RawSql.Sql parse(String sql) {
/*  46 */     return (new DRawSqlParser(sql)).parse();
/*     */   }
/*     */   
/*     */   private DRawSqlParser(String sqlString) {
/*  50 */     sqlString = sqlString.trim();
/*  51 */     this.sql = sqlString;
/*  52 */     this.hasPlaceHolders = findAndRemovePlaceHolders();
/*  53 */     this.textParser = new SimpleTextParser(sqlString);
/*     */   }
/*     */ 
/*     */   
/*     */   private RawSql.Sql parse() {
/*  58 */     if (!hasPlaceHolders())
/*     */     {
/*     */       
/*  61 */       parseSqlFindKeywords(true);
/*     */     }
/*     */     
/*  64 */     this.whereExprPos = findWhereExprPosition();
/*  65 */     this.havingExprPos = findHavingExprPosition();
/*     */     
/*  67 */     String preFrom = removeWhitespace(findPreFromSql());
/*  68 */     String preWhere = removeWhitespace(findPreWhereSql());
/*  69 */     String preHaving = removeWhitespace(findPreHavingSql());
/*  70 */     String orderBySql = findOrderBySql();
/*     */     
/*  72 */     preFrom = trimSelectKeyword(preFrom);
/*     */     
/*  74 */     return new RawSql.Sql(this.sql.hashCode(), preFrom, preWhere, this.whereExprAnd, preHaving, this.havingExprAnd, orderBySql, (this.distinctPos > -1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean findAndRemovePlaceHolders() {
/*  81 */     this.placeHolderWhere = removePlaceHolder("${where}");
/*  82 */     this.placeHolderAndWhere = removePlaceHolder("${andWhere}");
/*  83 */     this.placeHolderHaving = removePlaceHolder("${having}");
/*  84 */     this.placeHolderAndHaving = removePlaceHolder("${andHaving}");
/*  85 */     return hasPlaceHolders();
/*     */   }
/*     */   
/*     */   private int removePlaceHolder(String placeHolder) {
/*  89 */     int pos = this.sql.indexOf(placeHolder);
/*  90 */     if (pos > -1) {
/*  91 */       int after = pos + placeHolder.length() + 1;
/*  92 */       if (after > this.sql.length()) {
/*  93 */         this.sql = this.sql.substring(0, pos);
/*     */       } else {
/*  95 */         this.sql = this.sql.substring(0, pos) + this.sql.substring(after);
/*     */       } 
/*     */     } 
/*  98 */     return pos;
/*     */   }
/*     */   
/*     */   private boolean hasPlaceHolders() {
/* 102 */     if (this.placeHolderWhere > -1) {
/* 103 */       return true;
/*     */     }
/* 105 */     if (this.placeHolderAndWhere > -1) {
/* 106 */       return true;
/*     */     }
/* 108 */     if (this.placeHolderHaving > -1) {
/* 109 */       return true;
/*     */     }
/* 111 */     if (this.placeHolderAndHaving > -1) {
/* 112 */       return true;
/*     */     }
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String trimSelectKeyword(String preWhereExprSql) {
/* 122 */     if (this.selectPos < 0) {
/* 123 */       throw new IllegalStateException("select keyword not found?");
/*     */     }
/*     */     
/* 126 */     preWhereExprSql = preWhereExprSql.trim();
/* 127 */     String select = preWhereExprSql.substring(0, 7);
/* 128 */     if (!select.equalsIgnoreCase("select ")) {
/* 129 */       throw new RuntimeException("Expecting [" + preWhereExprSql + "] to start with \"select\"");
/*     */     }
/* 131 */     preWhereExprSql = preWhereExprSql.substring(7).trim();
/* 132 */     if (this.distinctPos > -1) {
/*     */       
/* 134 */       String distinct = preWhereExprSql.substring(0, 9);
/* 135 */       if (!distinct.equalsIgnoreCase("distinct ")) {
/* 136 */         throw new RuntimeException("Expecting [" + preWhereExprSql + "] to start with \"select distinct\"");
/*     */       }
/* 138 */       preWhereExprSql = preWhereExprSql.substring(9);
/*     */     } 
/*     */     
/* 141 */     return preWhereExprSql;
/*     */   }
/*     */   
/*     */   private String findOrderBySql() {
/* 145 */     if (this.orderByPos > -1) {
/* 146 */       int pos = this.orderByPos + "order by".length();
/* 147 */       return this.sql.substring(pos).trim();
/*     */     } 
/* 149 */     return null;
/*     */   }
/*     */   
/*     */   private String findPreHavingSql() {
/* 153 */     if (this.havingExprPos > this.whereExprPos)
/*     */     {
/* 155 */       return this.sql.substring(this.whereExprPos, this.havingExprPos - 1);
/*     */     }
/* 157 */     if (this.whereExprPos > -1) {
/* 158 */       if (this.orderByPos == -1) {
/* 159 */         return this.sql.substring(this.whereExprPos);
/*     */       }
/* 161 */       if (this.whereExprPos == this.orderByPos) {
/* 162 */         return "";
/*     */       }
/*     */       
/* 165 */       return this.sql.substring(this.whereExprPos, this.orderByPos - 1);
/*     */     } 
/*     */     
/* 168 */     return null;
/*     */   }
/*     */   
/*     */   private String findPreFromSql() {
/* 172 */     return this.sql.substring(0, this.fromPos - 1);
/*     */   }
/*     */   
/*     */   private String findPreWhereSql() {
/* 176 */     if (this.whereExprPos > -1) {
/* 177 */       return this.sql.substring(this.fromPos, this.whereExprPos - 1);
/*     */     }
/* 179 */     return this.sql.substring(this.fromPos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseSqlFindKeywords(boolean allKeywords) {
/* 185 */     this.selectPos = this.textParser.findWordLower("select");
/* 186 */     if (this.selectPos == -1) {
/* 187 */       String msg = "Error parsing sql, can not find SELECT keyword in:";
/* 188 */       throw new RuntimeException(msg + this.sql);
/*     */     } 
/*     */     
/* 191 */     String possibleDistinct = this.textParser.nextWord();
/* 192 */     if ("distinct".equals(possibleDistinct)) {
/* 193 */       this.distinctPos = this.textParser.getPos() - 8;
/*     */     }
/*     */     
/* 196 */     this.fromPos = this.textParser.findWordLower("from");
/* 197 */     if (this.fromPos == -1) {
/* 198 */       String msg = "Error parsing sql, can not find FROM keyword in:";
/* 199 */       throw new RuntimeException(msg + this.sql);
/*     */     } 
/*     */     
/* 202 */     if (!allKeywords) {
/*     */       return;
/*     */     }
/*     */     
/* 206 */     this.wherePos = this.textParser.findWordLower("where");
/* 207 */     if (this.wherePos == -1) {
/* 208 */       this.groupByPos = this.textParser.findWordLower("group", this.fromPos + 5);
/*     */     } else {
/* 210 */       this.groupByPos = this.textParser.findWordLower("group");
/*     */     } 
/* 212 */     if (this.groupByPos > -1) {
/* 213 */       this.havingPos = this.textParser.findWordLower("having");
/*     */     }
/*     */     
/* 216 */     int startOrderBy = this.havingPos;
/* 217 */     if (startOrderBy == -1) {
/* 218 */       startOrderBy = this.groupByPos;
/*     */     }
/* 220 */     if (startOrderBy == -1) {
/* 221 */       startOrderBy = this.wherePos;
/*     */     }
/* 223 */     if (startOrderBy == -1) {
/* 224 */       startOrderBy = this.fromPos;
/*     */     }
/*     */     
/* 227 */     this.orderByPos = this.textParser.findWordLower("order", startOrderBy);
/*     */   }
/*     */   
/*     */   private int findWhereExprPosition() {
/* 231 */     if (this.hasPlaceHolders) {
/* 232 */       if (this.placeHolderWhere > -1) {
/* 233 */         return this.placeHolderWhere;
/*     */       }
/* 235 */       this.whereExprAnd = true;
/* 236 */       return this.placeHolderAndWhere;
/*     */     } 
/*     */     
/* 239 */     this.whereExprAnd = (this.wherePos > 0);
/* 240 */     if (this.groupByPos > 0) {
/* 241 */       return this.groupByPos;
/*     */     }
/* 243 */     if (this.havingPos > 0) {
/* 244 */       return this.havingPos;
/*     */     }
/* 246 */     if (this.orderByPos > 0) {
/* 247 */       return this.orderByPos;
/*     */     }
/* 249 */     return -1;
/*     */   }
/*     */   
/*     */   private int findHavingExprPosition() {
/* 253 */     if (this.hasPlaceHolders) {
/* 254 */       if (this.placeHolderHaving > -1) {
/* 255 */         return this.placeHolderHaving;
/*     */       }
/* 257 */       this.havingExprAnd = true;
/* 258 */       return this.placeHolderAndHaving;
/*     */     } 
/*     */     
/* 261 */     this.havingExprAnd = (this.havingPos > 0);
/* 262 */     if (this.orderByPos > 0) {
/* 263 */       return this.orderByPos;
/*     */     }
/* 265 */     return -1;
/*     */   }
/*     */   
/*     */   private String removeWhitespace(String sql) {
/* 269 */     if (sql == null) {
/* 270 */       return "";
/*     */     }
/*     */     
/* 273 */     boolean removeWhitespace = false;
/*     */     
/* 275 */     int length = sql.length();
/* 276 */     StringBuilder sb = new StringBuilder();
/* 277 */     for (int i = 0; i < length; i++) {
/* 278 */       char c = sql.charAt(i);
/* 279 */       if (removeWhitespace) {
/* 280 */         if (!Character.isWhitespace(c)) {
/* 281 */           sb.append(c);
/* 282 */           removeWhitespace = false;
/*     */         }
/*     */       
/* 285 */       } else if (c == '\r' || c == '\n') {
/* 286 */         sb.append('\n');
/* 287 */         removeWhitespace = true;
/*     */       } else {
/* 289 */         sb.append(c);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 294 */     String s = sb.toString();
/* 295 */     return s.trim();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\DRawSqlParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */