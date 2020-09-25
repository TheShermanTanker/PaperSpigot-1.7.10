/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.sql.Connection;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DatabaseMetaData
/*      */   implements DatabaseMetaData
/*      */ {
/*      */   private static String mysqlKeywordsThatArentSQL92;
/*      */   protected static final int MAX_IDENTIFIER_LENGTH = 64;
/*      */   private static final int DEFERRABILITY = 13;
/*      */   private static final int DELETE_RULE = 10;
/*      */   private static final int FK_NAME = 11;
/*      */   private static final int FKCOLUMN_NAME = 7;
/*      */   private static final int FKTABLE_CAT = 4;
/*      */   private static final int FKTABLE_NAME = 6;
/*      */   private static final int FKTABLE_SCHEM = 5;
/*      */   private static final int KEY_SEQ = 8;
/*      */   private static final int PK_NAME = 12;
/*      */   private static final int PKCOLUMN_NAME = 3;
/*      */   private static final int PKTABLE_CAT = 0;
/*      */   private static final int PKTABLE_NAME = 2;
/*      */   private static final int PKTABLE_SCHEM = 1;
/*      */   private static final String SUPPORTS_FK = "SUPPORTS_FK";
/*      */   
/*      */   protected abstract class IteratorWithCleanup
/*      */   {
/*      */     abstract void close() throws SQLException;
/*      */     
/*      */     abstract boolean hasNext() throws SQLException;
/*      */     
/*      */     abstract Object next() throws SQLException;
/*      */   }
/*      */   
/*      */   class LocalAndReferencedColumns
/*      */   {
/*      */     String constraintName;
/*      */     List localColumnsList;
/*      */     String referencedCatalog;
/*      */     List referencedColumnsList;
/*      */     String referencedTable;
/*      */     
/*      */     LocalAndReferencedColumns(List localColumns, List refColumns, String constName, String refCatalog, String refTable) {
/*   87 */       this.localColumnsList = localColumns;
/*   88 */       this.referencedColumnsList = refColumns;
/*   89 */       this.constraintName = constName;
/*   90 */       this.referencedTable = refTable;
/*   91 */       this.referencedCatalog = refCatalog;
/*      */     }
/*      */   }
/*      */   
/*      */   protected class ResultSetIterator
/*      */     extends IteratorWithCleanup {
/*      */     int colIndex;
/*      */     ResultSet resultSet;
/*      */     
/*      */     ResultSetIterator(ResultSet rs, int index) {
/*  101 */       this.resultSet = rs;
/*  102 */       this.colIndex = index;
/*      */     }
/*      */     
/*      */     void close() throws SQLException {
/*  106 */       this.resultSet.close();
/*      */     }
/*      */     
/*      */     boolean hasNext() throws SQLException {
/*  110 */       return this.resultSet.next();
/*      */     }
/*      */     
/*      */     Object next() throws SQLException {
/*  114 */       return this.resultSet.getObject(this.colIndex);
/*      */     }
/*      */   }
/*      */   
/*      */   protected class SingleStringIterator
/*      */     extends IteratorWithCleanup {
/*      */     boolean onFirst = true;
/*      */     String value;
/*      */     
/*      */     SingleStringIterator(String s) {
/*  124 */       this.value = s;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void close() throws SQLException {}
/*      */ 
/*      */     
/*      */     boolean hasNext() throws SQLException {
/*  133 */       return this.onFirst;
/*      */     }
/*      */     
/*      */     Object next() throws SQLException {
/*  137 */       this.onFirst = false;
/*  138 */       return this.value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class TypeDescriptor
/*      */   {
/*      */     int bufferLength;
/*      */ 
/*      */     
/*      */     int charOctetLength;
/*      */     
/*      */     Integer columnSize;
/*      */     
/*      */     short dataType;
/*      */     
/*      */     Integer decimalDigits;
/*      */     
/*      */     String isNullable;
/*      */     
/*      */     int nullability;
/*      */     
/*  161 */     int numPrecRadix = 10;
/*      */     
/*      */     String typeName;
/*      */ 
/*      */     
/*      */     TypeDescriptor(String typeInfo, String nullabilityInfo) throws SQLException {
/*  167 */       if (typeInfo == null) {
/*  168 */         throw SQLError.createSQLException("NULL typeinfo not supported.", "S1009", DatabaseMetaData.this.getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */       
/*  172 */       String mysqlType = "";
/*  173 */       String fullMysqlType = null;
/*      */       
/*  175 */       if (typeInfo.indexOf("(") != -1) {
/*  176 */         mysqlType = typeInfo.substring(0, typeInfo.indexOf("("));
/*      */       } else {
/*  178 */         mysqlType = typeInfo;
/*      */       } 
/*      */       
/*  181 */       int indexOfUnsignedInMysqlType = StringUtils.indexOfIgnoreCase(mysqlType, "unsigned");
/*      */ 
/*      */       
/*  184 */       if (indexOfUnsignedInMysqlType != -1) {
/*  185 */         mysqlType = mysqlType.substring(0, indexOfUnsignedInMysqlType - 1);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  192 */       boolean isUnsigned = false;
/*      */       
/*  194 */       if (StringUtils.indexOfIgnoreCase(typeInfo, "unsigned") != -1) {
/*  195 */         fullMysqlType = mysqlType + " unsigned";
/*  196 */         isUnsigned = true;
/*      */       } else {
/*  198 */         fullMysqlType = mysqlType;
/*      */       } 
/*      */       
/*  201 */       if (DatabaseMetaData.this.conn.getCapitalizeTypeNames()) {
/*  202 */         fullMysqlType = fullMysqlType.toUpperCase(Locale.ENGLISH);
/*      */       }
/*      */       
/*  205 */       this.dataType = (short)MysqlDefs.mysqlToJavaType(mysqlType);
/*      */       
/*  207 */       this.typeName = fullMysqlType;
/*      */ 
/*      */ 
/*      */       
/*  211 */       if (StringUtils.startsWithIgnoreCase(typeInfo, "enum")) {
/*  212 */         String temp = typeInfo.substring(typeInfo.indexOf("("), typeInfo.lastIndexOf(")"));
/*      */         
/*  214 */         StringTokenizer tokenizer = new StringTokenizer(temp, ",");
/*      */         
/*  216 */         int maxLength = 0;
/*      */         
/*  218 */         while (tokenizer.hasMoreTokens()) {
/*  219 */           maxLength = Math.max(maxLength, tokenizer.nextToken().length() - 2);
/*      */         }
/*      */ 
/*      */         
/*  223 */         this.columnSize = Constants.integerValueOf(maxLength);
/*  224 */         this.decimalDigits = null;
/*  225 */       } else if (StringUtils.startsWithIgnoreCase(typeInfo, "set")) {
/*  226 */         String temp = typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.lastIndexOf(")"));
/*      */         
/*  228 */         StringTokenizer tokenizer = new StringTokenizer(temp, ",");
/*      */         
/*  230 */         int maxLength = 0;
/*      */         
/*  232 */         int numElements = tokenizer.countTokens();
/*      */         
/*  234 */         if (numElements > 0) {
/*  235 */           maxLength += numElements - 1;
/*      */         }
/*      */         
/*  238 */         while (tokenizer.hasMoreTokens()) {
/*  239 */           String setMember = tokenizer.nextToken().trim();
/*      */           
/*  241 */           if (setMember.startsWith("'") && setMember.endsWith("'")) {
/*      */             
/*  243 */             maxLength += setMember.length() - 2; continue;
/*      */           } 
/*  245 */           maxLength += setMember.length();
/*      */         } 
/*      */ 
/*      */         
/*  249 */         this.columnSize = Constants.integerValueOf(maxLength);
/*  250 */         this.decimalDigits = null;
/*  251 */       } else if (typeInfo.indexOf(",") != -1) {
/*      */         
/*  253 */         this.columnSize = Integer.valueOf(typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.indexOf(",")).trim());
/*      */         
/*  255 */         this.decimalDigits = Integer.valueOf(typeInfo.substring(typeInfo.indexOf(",") + 1, typeInfo.indexOf(")")).trim());
/*      */       }
/*      */       else {
/*      */         
/*  259 */         this.columnSize = null;
/*  260 */         this.decimalDigits = null;
/*      */ 
/*      */         
/*  263 */         if ((StringUtils.indexOfIgnoreCase(typeInfo, "char") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "text") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "blob") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "binary") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "bit") != -1) && typeInfo.indexOf("(") != -1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  270 */           int endParenIndex = typeInfo.indexOf(")");
/*      */           
/*  272 */           if (endParenIndex == -1) {
/*  273 */             endParenIndex = typeInfo.length();
/*      */           }
/*      */           
/*  276 */           this.columnSize = Integer.valueOf(typeInfo.substring(typeInfo.indexOf("(") + 1, endParenIndex).trim());
/*      */ 
/*      */ 
/*      */           
/*  280 */           if (DatabaseMetaData.this.conn.getTinyInt1isBit() && this.columnSize.intValue() == 1 && StringUtils.startsWithIgnoreCase(typeInfo, 0, "tinyint"))
/*      */           {
/*      */ 
/*      */             
/*  284 */             if (DatabaseMetaData.this.conn.getTransformedBitIsBoolean()) {
/*  285 */               this.dataType = 16;
/*  286 */               this.typeName = "BOOLEAN";
/*      */             } else {
/*  288 */               this.dataType = -7;
/*  289 */               this.typeName = "BIT";
/*      */             } 
/*      */           }
/*  292 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "tinyint")) {
/*      */           
/*  294 */           if (DatabaseMetaData.this.conn.getTinyInt1isBit() && typeInfo.indexOf("(1)") != -1) {
/*  295 */             if (DatabaseMetaData.this.conn.getTransformedBitIsBoolean()) {
/*  296 */               this.dataType = 16;
/*  297 */               this.typeName = "BOOLEAN";
/*      */             } else {
/*  299 */               this.dataType = -7;
/*  300 */               this.typeName = "BIT";
/*      */             } 
/*      */           } else {
/*  303 */             this.columnSize = Constants.integerValueOf(3);
/*  304 */             this.decimalDigits = Constants.integerValueOf(0);
/*      */           } 
/*  306 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "smallint")) {
/*      */           
/*  308 */           this.columnSize = Constants.integerValueOf(5);
/*  309 */           this.decimalDigits = Constants.integerValueOf(0);
/*  310 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "mediumint")) {
/*      */           
/*  312 */           this.columnSize = Constants.integerValueOf(isUnsigned ? 8 : 7);
/*  313 */           this.decimalDigits = Constants.integerValueOf(0);
/*  314 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "int")) {
/*      */           
/*  316 */           this.columnSize = Constants.integerValueOf(10);
/*  317 */           this.decimalDigits = Constants.integerValueOf(0);
/*  318 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "integer")) {
/*      */           
/*  320 */           this.columnSize = Constants.integerValueOf(10);
/*  321 */           this.decimalDigits = Constants.integerValueOf(0);
/*  322 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "bigint")) {
/*      */           
/*  324 */           this.columnSize = Constants.integerValueOf(isUnsigned ? 20 : 19);
/*  325 */           this.decimalDigits = Constants.integerValueOf(0);
/*  326 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "int24")) {
/*      */           
/*  328 */           this.columnSize = Constants.integerValueOf(19);
/*  329 */           this.decimalDigits = Constants.integerValueOf(0);
/*  330 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "real")) {
/*      */           
/*  332 */           this.columnSize = Constants.integerValueOf(12);
/*  333 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "float")) {
/*      */           
/*  335 */           this.columnSize = Constants.integerValueOf(12);
/*  336 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "decimal")) {
/*      */           
/*  338 */           this.columnSize = Constants.integerValueOf(12);
/*  339 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "numeric")) {
/*      */           
/*  341 */           this.columnSize = Constants.integerValueOf(12);
/*  342 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "double")) {
/*      */           
/*  344 */           this.columnSize = Constants.integerValueOf(22);
/*  345 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "char")) {
/*      */           
/*  347 */           this.columnSize = Constants.integerValueOf(1);
/*  348 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "varchar")) {
/*      */           
/*  350 */           this.columnSize = Constants.integerValueOf(255);
/*  351 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "timestamp")) {
/*      */           
/*  353 */           this.columnSize = Constants.integerValueOf(19);
/*  354 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "datetime")) {
/*      */           
/*  356 */           this.columnSize = Constants.integerValueOf(19);
/*  357 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "date")) {
/*      */           
/*  359 */           this.columnSize = Constants.integerValueOf(10);
/*  360 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "time")) {
/*      */           
/*  362 */           this.columnSize = Constants.integerValueOf(8);
/*      */         }
/*  364 */         else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "tinyblob")) {
/*      */           
/*  366 */           this.columnSize = Constants.integerValueOf(255);
/*  367 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "blob")) {
/*      */           
/*  369 */           this.columnSize = Constants.integerValueOf(65535);
/*  370 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "mediumblob")) {
/*      */           
/*  372 */           this.columnSize = Constants.integerValueOf(16777215);
/*  373 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "longblob")) {
/*      */           
/*  375 */           this.columnSize = Constants.integerValueOf(2147483647);
/*  376 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "tinytext")) {
/*      */           
/*  378 */           this.columnSize = Constants.integerValueOf(255);
/*  379 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "text")) {
/*      */           
/*  381 */           this.columnSize = Constants.integerValueOf(65535);
/*  382 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "mediumtext")) {
/*      */           
/*  384 */           this.columnSize = Constants.integerValueOf(16777215);
/*  385 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "longtext")) {
/*      */           
/*  387 */           this.columnSize = Constants.integerValueOf(2147483647);
/*  388 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "enum")) {
/*      */           
/*  390 */           this.columnSize = Constants.integerValueOf(255);
/*  391 */         } else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "set")) {
/*      */           
/*  393 */           this.columnSize = Constants.integerValueOf(255);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  399 */       this.bufferLength = MysqlIO.getMaxBuf();
/*      */ 
/*      */       
/*  402 */       this.numPrecRadix = 10;
/*      */ 
/*      */       
/*  405 */       if (nullabilityInfo != null) {
/*  406 */         if (nullabilityInfo.equals("YES")) {
/*  407 */           this.nullability = 1;
/*  408 */           this.isNullable = "YES";
/*      */         }
/*      */         else {
/*      */           
/*  412 */           this.nullability = 0;
/*  413 */           this.isNullable = "NO";
/*      */         } 
/*      */       } else {
/*  416 */         this.nullability = 0;
/*  417 */         this.isNullable = "NO";
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  459 */   private static final byte[] TABLE_AS_BYTES = "TABLE".getBytes();
/*      */   
/*  461 */   private static final byte[] SYSTEM_TABLE_AS_BYTES = "SYSTEM TABLE".getBytes();
/*      */   
/*      */   private static final int UPDATE_RULE = 9;
/*      */   
/*  465 */   private static final byte[] VIEW_AS_BYTES = "VIEW".getBytes();
/*      */   
/*      */   private static final Constructor JDBC_4_DBMD_SHOW_CTOR;
/*      */   private static final Constructor JDBC_4_DBMD_IS_CTOR;
/*      */   protected MySQLConnection conn;
/*      */   
/*      */   static {
/*  472 */     if (Util.isJdbc4()) {
/*      */       try {
/*  474 */         JDBC_4_DBMD_SHOW_CTOR = Class.forName("com.mysql.jdbc.JDBC4DatabaseMetaData").getConstructor(new Class[] { MySQLConnection.class, String.class });
/*      */ 
/*      */ 
/*      */         
/*  478 */         JDBC_4_DBMD_IS_CTOR = Class.forName("com.mysql.jdbc.JDBC4DatabaseMetaDataUsingInfoSchema").getConstructor(new Class[] { MySQLConnection.class, String.class });
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  483 */       catch (SecurityException e) {
/*  484 */         throw new RuntimeException(e);
/*  485 */       } catch (NoSuchMethodException e) {
/*  486 */         throw new RuntimeException(e);
/*  487 */       } catch (ClassNotFoundException e) {
/*  488 */         throw new RuntimeException(e);
/*      */       } 
/*      */     } else {
/*  491 */       JDBC_4_DBMD_IS_CTOR = null;
/*  492 */       JDBC_4_DBMD_SHOW_CTOR = null;
/*      */     } 
/*      */ 
/*      */     
/*  496 */     String[] allMySQLKeywords = { "ACCESSIBLE", "ADD", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ASENSITIVE", "BEFORE", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOTH", "BY", "CALL", "CASCADE", "CASE", "CHANGE", "CHAR", "CHARACTER", "CHECK", "COLLATE", "COLUMN", "CONDITION", "CONNECTION", "CONSTRAINT", "CONTINUE", "CONVERT", "CREATE", "CROSS", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATABASE", "DATABASES", "DAY_HOUR", "DAY_MICROSECOND", "DAY_MINUTE", "DAY_SECOND", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELAYED", "DELETE", "DESC", "DESCRIBE", "DETERMINISTIC", "DISTINCT", "DISTINCTROW", "DIV", "DOUBLE", "DROP", "DUAL", "EACH", "ELSE", "ELSEIF", "ENCLOSED", "ESCAPED", "EXISTS", "EXIT", "EXPLAIN", "FALSE", "FETCH", "FLOAT", "FLOAT4", "FLOAT8", "FOR", "FORCE", "FOREIGN", "FROM", "FULLTEXT", "GRANT", "GROUP", "HAVING", "HIGH_PRIORITY", "HOUR_MICROSECOND", "HOUR_MINUTE", "HOUR_SECOND", "IF", "IGNORE", "IN", "INDEX", "INFILE", "INNER", "INOUT", "INSENSITIVE", "INSERT", "INT", "INT1", "INT2", "INT3", "INT4", "INT8", "INTEGER", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN", "KEY", "KEYS", "KILL", "LEADING", "LEAVE", "LEFT", "LIKE", "LIMIT", "LINEAR", "LINES", "LOAD", "LOCALTIME", "LOCALTIMESTAMP", "LOCK", "LONG", "LONGBLOB", "LONGTEXT", "LOOP", "LOW_PRIORITY", "MATCH", "MEDIUMBLOB", "MEDIUMINT", "MEDIUMTEXT", "MIDDLEINT", "MINUTE_MICROSECOND", "MINUTE_SECOND", "MOD", "MODIFIES", "NATURAL", "NOT", "NO_WRITE_TO_BINLOG", "NULL", "NUMERIC", "ON", "OPTIMIZE", "OPTION", "OPTIONALLY", "OR", "ORDER", "OUT", "OUTER", "OUTFILE", "PRECISION", "PRIMARY", "PROCEDURE", "PURGE", "RANGE", "READ", "READS", "READ_ONLY", "READ_WRITE", "REAL", "REFERENCES", "REGEXP", "RELEASE", "RENAME", "REPEAT", "REPLACE", "REQUIRE", "RESTRICT", "RETURN", "REVOKE", "RIGHT", "RLIKE", "SCHEMA", "SCHEMAS", "SECOND_MICROSECOND", "SELECT", "SENSITIVE", "SEPARATOR", "SET", "SHOW", "SMALLINT", "SPATIAL", "SPECIFIC", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQL_BIG_RESULT", "SQL_CALC_FOUND_ROWS", "SQL_SMALL_RESULT", "SSL", "STARTING", "STRAIGHT_JOIN", "TABLE", "TERMINATED", "THEN", "TINYBLOB", "TINYINT", "TINYTEXT", "TO", "TRAILING", "TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNLOCK", "UNSIGNED", "UPDATE", "USAGE", "USE", "USING", "UTC_DATE", "UTC_TIME", "UTC_TIMESTAMP", "VALUES", "VARBINARY", "VARCHAR", "VARCHARACTER", "VARYING", "WHEN", "WHERE", "WHILE", "WITH", "WRITE", "X509", "XOR", "YEAR_MONTH", "ZEROFILL" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  539 */     String[] sql92Keywords = { "ABSOLUTE", "EXEC", "OVERLAPS", "ACTION", "EXECUTE", "PAD", "ADA", "EXISTS", "PARTIAL", "ADD", "EXTERNAL", "PASCAL", "ALL", "EXTRACT", "POSITION", "ALLOCATE", "FALSE", "PRECISION", "ALTER", "FETCH", "PREPARE", "AND", "FIRST", "PRESERVE", "ANY", "FLOAT", "PRIMARY", "ARE", "FOR", "PRIOR", "AS", "FOREIGN", "PRIVILEGES", "ASC", "FORTRAN", "PROCEDURE", "ASSERTION", "FOUND", "PUBLIC", "AT", "FROM", "READ", "AUTHORIZATION", "FULL", "REAL", "AVG", "GET", "REFERENCES", "BEGIN", "GLOBAL", "RELATIVE", "BETWEEN", "GO", "RESTRICT", "BIT", "GOTO", "REVOKE", "BIT_LENGTH", "GRANT", "RIGHT", "BOTH", "GROUP", "ROLLBACK", "BY", "HAVING", "ROWS", "CASCADE", "HOUR", "SCHEMA", "CASCADED", "IDENTITY", "SCROLL", "CASE", "IMMEDIATE", "SECOND", "CAST", "IN", "SECTION", "CATALOG", "INCLUDE", "SELECT", "CHAR", "INDEX", "SESSION", "CHAR_LENGTH", "INDICATOR", "SESSION_USER", "CHARACTER", "INITIALLY", "SET", "CHARACTER_LENGTH", "INNER", "SIZE", "CHECK", "INPUT", "SMALLINT", "CLOSE", "INSENSITIVE", "SOME", "COALESCE", "INSERT", "SPACE", "COLLATE", "INT", "SQL", "COLLATION", "INTEGER", "SQLCA", "COLUMN", "INTERSECT", "SQLCODE", "COMMIT", "INTERVAL", "SQLERROR", "CONNECT", "INTO", "SQLSTATE", "CONNECTION", "IS", "SQLWARNING", "CONSTRAINT", "ISOLATION", "SUBSTRING", "CONSTRAINTS", "JOIN", "SUM", "CONTINUE", "KEY", "SYSTEM_USER", "CONVERT", "LANGUAGE", "TABLE", "CORRESPONDING", "LAST", "TEMPORARY", "COUNT", "LEADING", "THEN", "CREATE", "LEFT", "TIME", "CROSS", "LEVEL", "TIMESTAMP", "CURRENT", "LIKE", "TIMEZONE_HOUR", "CURRENT_DATE", "LOCAL", "TIMEZONE_MINUTE", "CURRENT_TIME", "LOWER", "TO", "CURRENT_TIMESTAMP", "MATCH", "TRAILING", "CURRENT_USER", "MAX", "TRANSACTION", "CURSOR", "MIN", "TRANSLATE", "DATE", "MINUTE", "TRANSLATION", "DAY", "MODULE", "TRIM", "DEALLOCATE", "MONTH", "TRUE", "DEC", "NAMES", "UNION", "DECIMAL", "NATIONAL", "UNIQUE", "DECLARE", "NATURAL", "UNKNOWN", "DEFAULT", "NCHAR", "UPDATE", "DEFERRABLE", "NEXT", "UPPER", "DEFERRED", "NO", "USAGE", "DELETE", "NONE", "USER", "DESC", "NOT", "USING", "DESCRIBE", "NULL", "VALUE", "DESCRIPTOR", "NULLIF", "VALUES", "DIAGNOSTICS", "NUMERIC", "VARCHAR", "DISCONNECT", "OCTET_LENGTH", "VARYING", "DISTINCT", "OF", "VIEW", "DOMAIN", "ON", "WHEN", "DOUBLE", "ONLY", "WHENEVER", "DROP", "OPEN", "WHERE", "ELSE", "OPTION", "WITH", "END", "OR", "WORK", "END-EXEC", "ORDER", "WRITE", "ESCAPE", "OUTER", "YEAR", "EXCEPT", "OUTPUT", "ZONE", "EXCEPTION" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  581 */     TreeMap<Object, Object> mySQLKeywordMap = new TreeMap<Object, Object>();
/*      */     
/*  583 */     for (int i = 0; i < allMySQLKeywords.length; i++) {
/*  584 */       mySQLKeywordMap.put(allMySQLKeywords[i], null);
/*      */     }
/*      */     
/*  587 */     HashMap<Object, Object> sql92KeywordMap = new HashMap<Object, Object>(sql92Keywords.length);
/*      */     
/*  589 */     for (int j = 0; j < sql92Keywords.length; j++) {
/*  590 */       sql92KeywordMap.put(sql92Keywords[j], null);
/*      */     }
/*      */     
/*  593 */     Iterator<E> it = sql92KeywordMap.keySet().iterator();
/*      */     
/*  595 */     while (it.hasNext()) {
/*  596 */       mySQLKeywordMap.remove(it.next());
/*      */     }
/*      */     
/*  599 */     StringBuffer keywordBuf = new StringBuffer();
/*      */     
/*  601 */     it = mySQLKeywordMap.keySet().iterator();
/*      */     
/*  603 */     if (it.hasNext()) {
/*  604 */       keywordBuf.append(it.next().toString());
/*      */     }
/*      */     
/*  607 */     while (it.hasNext()) {
/*  608 */       keywordBuf.append(",");
/*  609 */       keywordBuf.append(it.next().toString());
/*      */     } 
/*      */     
/*  612 */     mysqlKeywordsThatArentSQL92 = keywordBuf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  619 */   protected String database = null;
/*      */ 
/*      */   
/*  622 */   protected String quotedId = null;
/*      */ 
/*      */   
/*      */   private ExceptionInterceptor exceptionInterceptor;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static DatabaseMetaData getInstance(MySQLConnection connToSet, String databaseToSet, boolean checkForInfoSchema) throws SQLException {
/*  630 */     if (!Util.isJdbc4()) {
/*  631 */       if (checkForInfoSchema && connToSet != null && connToSet.getUseInformationSchema() && connToSet.versionMeetsMinimum(5, 0, 7))
/*      */       {
/*      */         
/*  634 */         return new DatabaseMetaDataUsingInfoSchema(connToSet, databaseToSet);
/*      */       }
/*      */ 
/*      */       
/*  638 */       return new DatabaseMetaData(connToSet, databaseToSet);
/*      */     } 
/*      */     
/*  641 */     if (checkForInfoSchema && connToSet != null && connToSet.getUseInformationSchema() && connToSet.versionMeetsMinimum(5, 0, 7))
/*      */     {
/*      */ 
/*      */       
/*  645 */       return (DatabaseMetaData)Util.handleNewInstance(JDBC_4_DBMD_IS_CTOR, new Object[] { connToSet, databaseToSet }, connToSet.getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  650 */     return (DatabaseMetaData)Util.handleNewInstance(JDBC_4_DBMD_SHOW_CTOR, new Object[] { connToSet, databaseToSet }, connToSet.getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DatabaseMetaData(MySQLConnection connToSet, String databaseToSet) {
/*  663 */     this.conn = connToSet;
/*  664 */     this.database = databaseToSet;
/*  665 */     this.exceptionInterceptor = this.conn.getExceptionInterceptor();
/*      */     
/*      */     try {
/*  668 */       this.quotedId = this.conn.supportsQuotedIdentifiers() ? getIdentifierQuoteString() : "";
/*      */     }
/*  670 */     catch (SQLException sqlEx) {
/*      */ 
/*      */ 
/*      */       
/*  674 */       AssertionFailedException.shouldNotHappen(sqlEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean allProceduresAreCallable() throws SQLException {
/*  687 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean allTablesAreSelectable() throws SQLException {
/*  698 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private ResultSet buildResultSet(Field[] fields, ArrayList rows) throws SQLException {
/*  703 */     return buildResultSet(fields, rows, this.conn);
/*      */   }
/*      */ 
/*      */   
/*      */   static ResultSet buildResultSet(Field[] fields, ArrayList rows, MySQLConnection c) throws SQLException {
/*  708 */     int fieldsLength = fields.length;
/*      */     
/*  710 */     for (int i = 0; i < fieldsLength; i++) {
/*  711 */       int jdbcType = fields[i].getSQLType();
/*      */       
/*  713 */       switch (jdbcType) {
/*      */         case -1:
/*      */         case 1:
/*      */         case 12:
/*  717 */           fields[i].setCharacterSet(c.getCharacterSetMetadata());
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  723 */       fields[i].setConnection(c);
/*  724 */       fields[i].setUseOldNameMetadata(true);
/*      */     } 
/*      */     
/*  727 */     return ResultSetImpl.getInstance(c.getCatalog(), fields, new RowDataStatic(rows), c, null, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void convertToJdbcFunctionList(String catalog, ResultSet proceduresRs, boolean needsClientFiltering, String db, Map<String, ByteArrayRow> procedureRowsOrderedByName, int nameIndex, Field[] fields) throws SQLException {
/*  735 */     while (proceduresRs.next()) {
/*  736 */       boolean shouldAdd = true;
/*      */       
/*  738 */       if (needsClientFiltering) {
/*  739 */         shouldAdd = false;
/*      */         
/*  741 */         String procDb = proceduresRs.getString(1);
/*      */         
/*  743 */         if (db == null && procDb == null) {
/*  744 */           shouldAdd = true;
/*  745 */         } else if (db != null && db.equals(procDb)) {
/*  746 */           shouldAdd = true;
/*      */         } 
/*      */       } 
/*      */       
/*  750 */       if (shouldAdd) {
/*  751 */         String functionName = proceduresRs.getString(nameIndex);
/*      */         
/*  753 */         byte[][] rowData = (byte[][])null;
/*      */         
/*  755 */         if (fields != null && fields.length == 9) {
/*      */           
/*  757 */           rowData = new byte[9][];
/*  758 */           rowData[0] = (catalog == null) ? null : s2b(catalog);
/*  759 */           rowData[1] = null;
/*  760 */           rowData[2] = s2b(functionName);
/*  761 */           rowData[3] = null;
/*  762 */           rowData[4] = null;
/*  763 */           rowData[5] = null;
/*  764 */           rowData[6] = s2b(proceduresRs.getString("comment"));
/*  765 */           rowData[7] = s2b(Integer.toString(2));
/*  766 */           rowData[8] = s2b(functionName);
/*      */         } else {
/*      */           
/*  769 */           rowData = new byte[6][];
/*      */           
/*  771 */           rowData[0] = (catalog == null) ? null : s2b(catalog);
/*  772 */           rowData[1] = null;
/*  773 */           rowData[2] = s2b(functionName);
/*  774 */           rowData[3] = s2b(proceduresRs.getString("comment"));
/*  775 */           rowData[4] = s2b(Integer.toString(getJDBC4FunctionNoTableConstant()));
/*  776 */           rowData[5] = s2b(functionName);
/*      */         } 
/*      */         
/*  779 */         procedureRowsOrderedByName.put(functionName, new ByteArrayRow(rowData, getExceptionInterceptor()));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected int getJDBC4FunctionNoTableConstant() {
/*  785 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void convertToJdbcProcedureList(boolean fromSelect, String catalog, ResultSet proceduresRs, boolean needsClientFiltering, String db, Map<String, ByteArrayRow> procedureRowsOrderedByName, int nameIndex) throws SQLException {
/*  791 */     while (proceduresRs.next()) {
/*  792 */       boolean shouldAdd = true;
/*      */       
/*  794 */       if (needsClientFiltering) {
/*  795 */         shouldAdd = false;
/*      */         
/*  797 */         String procDb = proceduresRs.getString(1);
/*      */         
/*  799 */         if (db == null && procDb == null) {
/*  800 */           shouldAdd = true;
/*  801 */         } else if (db != null && db.equals(procDb)) {
/*  802 */           shouldAdd = true;
/*      */         } 
/*      */       } 
/*      */       
/*  806 */       if (shouldAdd) {
/*  807 */         String procedureName = proceduresRs.getString(nameIndex);
/*  808 */         byte[][] rowData = new byte[9][];
/*  809 */         rowData[0] = (catalog == null) ? null : s2b(catalog);
/*  810 */         rowData[1] = null;
/*  811 */         rowData[2] = s2b(procedureName);
/*  812 */         rowData[3] = null;
/*  813 */         rowData[4] = null;
/*  814 */         rowData[5] = null;
/*  815 */         rowData[6] = null;
/*      */         
/*  817 */         boolean isFunction = fromSelect ? "FUNCTION".equalsIgnoreCase(proceduresRs.getString("type")) : false;
/*      */ 
/*      */         
/*  820 */         rowData[7] = s2b(isFunction ? Integer.toString(2) : Integer.toString(0));
/*      */ 
/*      */ 
/*      */         
/*  824 */         rowData[8] = s2b(procedureName);
/*      */         
/*  826 */         procedureRowsOrderedByName.put(procedureName, new ByteArrayRow(rowData, getExceptionInterceptor()));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ResultSetRow convertTypeDescriptorToProcedureRow(byte[] procNameAsBytes, byte[] procCatAsBytes, String paramName, boolean isOutParam, boolean isInParam, boolean isReturnParam, TypeDescriptor typeDesc, boolean forGetFunctionColumns, int ordinal) throws SQLException {
/*  837 */     byte[][] row = forGetFunctionColumns ? new byte[17][] : new byte[14][];
/*  838 */     row[0] = procCatAsBytes;
/*  839 */     row[1] = null;
/*  840 */     row[2] = procNameAsBytes;
/*  841 */     row[3] = s2b(paramName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  848 */     if (isInParam && isOutParam) {
/*  849 */       row[4] = s2b(String.valueOf(2));
/*  850 */     } else if (isInParam) {
/*  851 */       row[4] = s2b(String.valueOf(1));
/*  852 */     } else if (isOutParam) {
/*  853 */       row[4] = s2b(String.valueOf(4));
/*  854 */     } else if (isReturnParam) {
/*  855 */       row[4] = s2b(String.valueOf(5));
/*      */     } else {
/*  857 */       row[4] = s2b(String.valueOf(0));
/*      */     } 
/*  859 */     row[5] = s2b(Short.toString(typeDesc.dataType));
/*  860 */     row[6] = s2b(typeDesc.typeName);
/*  861 */     row[7] = (typeDesc.columnSize == null) ? null : s2b(typeDesc.columnSize.toString());
/*  862 */     row[8] = row[7];
/*  863 */     row[9] = (typeDesc.decimalDigits == null) ? null : s2b(typeDesc.decimalDigits.toString());
/*  864 */     row[10] = s2b(Integer.toString(typeDesc.numPrecRadix));
/*      */     
/*  866 */     switch (typeDesc.nullability) {
/*      */       case 0:
/*  868 */         row[11] = s2b(String.valueOf(0));
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  873 */         row[11] = s2b(String.valueOf(1));
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/*  878 */         row[11] = s2b(String.valueOf(2));
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/*  883 */         throw SQLError.createSQLException("Internal error while parsing callable statement metadata (unknown nullability value fount)", "S1000", getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  888 */     row[12] = null;
/*      */     
/*  890 */     if (forGetFunctionColumns) {
/*      */       
/*  892 */       row[13] = null;
/*      */ 
/*      */       
/*  895 */       row[14] = s2b(String.valueOf(ordinal));
/*      */ 
/*      */       
/*  898 */       row[15] = Constants.EMPTY_BYTE_ARRAY;
/*      */       
/*  900 */       row[16] = s2b(paramName);
/*      */     } 
/*      */     
/*  903 */     return new ByteArrayRow(row, getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ExceptionInterceptor getExceptionInterceptor() {
/*  909 */     return this.exceptionInterceptor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
/*  921 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
/*  932 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean deletesAreDetected(int type) throws SQLException {
/*  947 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
/*  960 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List extractForeignKeyForTable(ArrayList<ByteArrayRow> rows, ResultSet rs, String catalog) throws SQLException {
/*  978 */     byte[][] row = new byte[3][];
/*  979 */     row[0] = rs.getBytes(1);
/*  980 */     row[1] = s2b("SUPPORTS_FK");
/*      */     
/*  982 */     String createTableString = rs.getString(2);
/*  983 */     StringTokenizer lineTokenizer = new StringTokenizer(createTableString, "\n");
/*      */     
/*  985 */     StringBuffer commentBuf = new StringBuffer("comment; ");
/*  986 */     boolean firstTime = true;
/*      */     
/*  988 */     String quoteChar = getIdentifierQuoteString();
/*      */     
/*  990 */     if (quoteChar == null) {
/*  991 */       quoteChar = "`";
/*      */     }
/*      */     
/*  994 */     while (lineTokenizer.hasMoreTokens()) {
/*  995 */       String line = lineTokenizer.nextToken().trim();
/*      */       
/*  997 */       String constraintName = null;
/*      */       
/*  999 */       if (StringUtils.startsWithIgnoreCase(line, "CONSTRAINT")) {
/* 1000 */         boolean usingBackTicks = true;
/* 1001 */         int beginPos = line.indexOf(quoteChar);
/*      */         
/* 1003 */         if (beginPos == -1) {
/* 1004 */           beginPos = line.indexOf("\"");
/* 1005 */           usingBackTicks = false;
/*      */         } 
/*      */         
/* 1008 */         if (beginPos != -1) {
/* 1009 */           int endPos = -1;
/*      */           
/* 1011 */           if (usingBackTicks) {
/* 1012 */             endPos = line.indexOf(quoteChar, beginPos + 1);
/*      */           } else {
/* 1014 */             endPos = line.indexOf("\"", beginPos + 1);
/*      */           } 
/*      */           
/* 1017 */           if (endPos != -1) {
/* 1018 */             constraintName = line.substring(beginPos + 1, endPos);
/* 1019 */             line = line.substring(endPos + 1, line.length()).trim();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1025 */       if (line.startsWith("FOREIGN KEY")) {
/* 1026 */         if (line.endsWith(",")) {
/* 1027 */           line = line.substring(0, line.length() - 1);
/*      */         }
/*      */         
/* 1030 */         char quote = this.quotedId.charAt(0);
/*      */         
/* 1032 */         int indexOfFK = line.indexOf("FOREIGN KEY");
/*      */         
/* 1034 */         String localColumnName = null;
/* 1035 */         String referencedCatalogName = this.quotedId + catalog + this.quotedId;
/* 1036 */         String referencedTableName = null;
/* 1037 */         String referencedColumnName = null;
/*      */ 
/*      */         
/* 1040 */         if (indexOfFK != -1) {
/* 1041 */           int afterFk = indexOfFK + "FOREIGN KEY".length();
/*      */           
/* 1043 */           int indexOfRef = StringUtils.indexOfIgnoreCaseRespectQuotes(afterFk, line, "REFERENCES", quote, true);
/*      */           
/* 1045 */           if (indexOfRef != -1) {
/*      */             
/* 1047 */             int indexOfParenOpen = line.indexOf('(', afterFk);
/* 1048 */             int indexOfParenClose = StringUtils.indexOfIgnoreCaseRespectQuotes(indexOfParenOpen, line, ")", quote, true);
/*      */             
/* 1050 */             if (indexOfParenOpen == -1 || indexOfParenClose == -1);
/*      */ 
/*      */ 
/*      */             
/* 1054 */             localColumnName = line.substring(indexOfParenOpen + 1, indexOfParenClose);
/*      */             
/* 1056 */             int afterRef = indexOfRef + "REFERENCES".length();
/*      */             
/* 1058 */             int referencedColumnBegin = StringUtils.indexOfIgnoreCaseRespectQuotes(afterRef, line, "(", quote, true);
/*      */             
/* 1060 */             if (referencedColumnBegin != -1) {
/* 1061 */               referencedTableName = line.substring(afterRef, referencedColumnBegin);
/*      */               
/* 1063 */               int referencedColumnEnd = StringUtils.indexOfIgnoreCaseRespectQuotes(referencedColumnBegin + 1, line, ")", quote, true);
/*      */               
/* 1065 */               if (referencedColumnEnd != -1) {
/* 1066 */                 referencedColumnName = line.substring(referencedColumnBegin + 1, referencedColumnEnd);
/*      */               }
/*      */               
/* 1069 */               int indexOfCatalogSep = StringUtils.indexOfIgnoreCaseRespectQuotes(0, referencedTableName, ".", quote, true);
/*      */               
/* 1071 */               if (indexOfCatalogSep != -1) {
/* 1072 */                 referencedCatalogName = referencedTableName.substring(0, indexOfCatalogSep);
/* 1073 */                 referencedTableName = referencedTableName.substring(indexOfCatalogSep + 1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1080 */         if (!firstTime) {
/* 1081 */           commentBuf.append("; ");
/*      */         } else {
/* 1083 */           firstTime = false;
/*      */         } 
/*      */         
/* 1086 */         if (constraintName != null) {
/* 1087 */           commentBuf.append(constraintName);
/*      */         } else {
/* 1089 */           commentBuf.append("not_available");
/*      */         } 
/*      */         
/* 1092 */         commentBuf.append("(");
/* 1093 */         commentBuf.append(localColumnName);
/* 1094 */         commentBuf.append(") REFER ");
/* 1095 */         commentBuf.append(referencedCatalogName);
/* 1096 */         commentBuf.append("/");
/* 1097 */         commentBuf.append(referencedTableName);
/* 1098 */         commentBuf.append("(");
/* 1099 */         commentBuf.append(referencedColumnName);
/* 1100 */         commentBuf.append(")");
/*      */         
/* 1102 */         int lastParenIndex = line.lastIndexOf(")");
/*      */         
/* 1104 */         if (lastParenIndex != line.length() - 1) {
/* 1105 */           String cascadeOptions = line.substring(lastParenIndex + 1);
/*      */           
/* 1107 */           commentBuf.append(" ");
/* 1108 */           commentBuf.append(cascadeOptions);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1113 */     row[2] = s2b(commentBuf.toString());
/* 1114 */     rows.add(new ByteArrayRow(row, getExceptionInterceptor()));
/*      */     
/* 1116 */     return rows;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet extractForeignKeyFromCreateTable(String catalog, String tableName) throws SQLException {
/* 1137 */     ArrayList<String> tableList = new ArrayList();
/* 1138 */     ResultSet rs = null;
/* 1139 */     Statement stmt = null;
/*      */     
/* 1141 */     if (tableName != null) {
/* 1142 */       tableList.add(tableName);
/*      */     } else {
/*      */       try {
/* 1145 */         rs = getTables(catalog, "", "%", new String[] { "TABLE" });
/*      */         
/* 1147 */         while (rs.next()) {
/* 1148 */           tableList.add(rs.getString("TABLE_NAME"));
/*      */         }
/*      */       } finally {
/* 1151 */         if (rs != null) {
/* 1152 */           rs.close();
/*      */         }
/*      */         
/* 1155 */         rs = null;
/*      */       } 
/*      */     } 
/*      */     
/* 1159 */     ArrayList rows = new ArrayList();
/* 1160 */     Field[] fields = new Field[3];
/* 1161 */     fields[0] = new Field("", "Name", 1, 2147483647);
/* 1162 */     fields[1] = new Field("", "Type", 1, 255);
/* 1163 */     fields[2] = new Field("", "Comment", 1, 2147483647);
/*      */     
/* 1165 */     int numTables = tableList.size();
/* 1166 */     stmt = this.conn.getMetadataSafeStatement();
/*      */     
/* 1168 */     String quoteChar = getIdentifierQuoteString();
/*      */     
/* 1170 */     if (quoteChar == null) {
/* 1171 */       quoteChar = "`";
/*      */     }
/*      */     
/*      */     try {
/* 1175 */       for (int i = 0; i < numTables; i++) {
/* 1176 */         String tableToExtract = tableList.get(i);
/* 1177 */         if (tableToExtract.indexOf(quoteChar) > 0) {
/* 1178 */           tableToExtract = StringUtils.escapeQuote(tableToExtract, quoteChar);
/*      */         }
/*      */         
/* 1181 */         String query = "SHOW CREATE TABLE " + quoteChar + catalog + quoteChar + "." + quoteChar + tableToExtract + quoteChar;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1187 */           rs = stmt.executeQuery(query);
/* 1188 */         } catch (SQLException sqlEx) {
/*      */           
/* 1190 */           String sqlState = sqlEx.getSQLState();
/*      */           
/* 1192 */           if (!"42S02".equals(sqlState) && sqlEx.getErrorCode() != 1146)
/*      */           {
/* 1194 */             throw sqlEx;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1200 */         while (rs.next()) {
/* 1201 */           extractForeignKeyForTable(rows, rs, catalog);
/*      */         }
/*      */       } 
/*      */     } finally {
/* 1205 */       if (rs != null) {
/* 1206 */         rs.close();
/*      */       }
/*      */       
/* 1209 */       rs = null;
/*      */       
/* 1211 */       if (stmt != null) {
/* 1212 */         stmt.close();
/*      */       }
/*      */       
/* 1215 */       stmt = null;
/*      */     } 
/*      */     
/* 1218 */     return buildResultSet(fields, rows);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getAttributes(String arg0, String arg1, String arg2, String arg3) throws SQLException {
/* 1226 */     Field[] fields = new Field[21];
/* 1227 */     fields[0] = new Field("", "TYPE_CAT", 1, 32);
/* 1228 */     fields[1] = new Field("", "TYPE_SCHEM", 1, 32);
/* 1229 */     fields[2] = new Field("", "TYPE_NAME", 1, 32);
/* 1230 */     fields[3] = new Field("", "ATTR_NAME", 1, 32);
/* 1231 */     fields[4] = new Field("", "DATA_TYPE", 5, 32);
/* 1232 */     fields[5] = new Field("", "ATTR_TYPE_NAME", 1, 32);
/* 1233 */     fields[6] = new Field("", "ATTR_SIZE", 4, 32);
/* 1234 */     fields[7] = new Field("", "DECIMAL_DIGITS", 4, 32);
/* 1235 */     fields[8] = new Field("", "NUM_PREC_RADIX", 4, 32);
/* 1236 */     fields[9] = new Field("", "NULLABLE ", 4, 32);
/* 1237 */     fields[10] = new Field("", "REMARKS", 1, 32);
/* 1238 */     fields[11] = new Field("", "ATTR_DEF", 1, 32);
/* 1239 */     fields[12] = new Field("", "SQL_DATA_TYPE", 4, 32);
/* 1240 */     fields[13] = new Field("", "SQL_DATETIME_SUB", 4, 32);
/* 1241 */     fields[14] = new Field("", "CHAR_OCTET_LENGTH", 4, 32);
/* 1242 */     fields[15] = new Field("", "ORDINAL_POSITION", 4, 32);
/* 1243 */     fields[16] = new Field("", "IS_NULLABLE", 1, 32);
/* 1244 */     fields[17] = new Field("", "SCOPE_CATALOG", 1, 32);
/* 1245 */     fields[18] = new Field("", "SCOPE_SCHEMA", 1, 32);
/* 1246 */     fields[19] = new Field("", "SCOPE_TABLE", 1, 32);
/* 1247 */     fields[20] = new Field("", "SOURCE_DATA_TYPE", 5, 32);
/*      */     
/* 1249 */     return buildResultSet(fields, new ArrayList());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getBestRowIdentifier(String catalog, String schema, final String table, int scope, boolean nullable) throws SQLException {
/* 1300 */     if (table == null) {
/* 1301 */       throw SQLError.createSQLException("Table not specified.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 1305 */     Field[] fields = new Field[8];
/* 1306 */     fields[0] = new Field("", "SCOPE", 5, 5);
/* 1307 */     fields[1] = new Field("", "COLUMN_NAME", 1, 32);
/* 1308 */     fields[2] = new Field("", "DATA_TYPE", 4, 32);
/* 1309 */     fields[3] = new Field("", "TYPE_NAME", 1, 32);
/* 1310 */     fields[4] = new Field("", "COLUMN_SIZE", 4, 10);
/* 1311 */     fields[5] = new Field("", "BUFFER_LENGTH", 4, 10);
/* 1312 */     fields[6] = new Field("", "DECIMAL_DIGITS", 5, 10);
/* 1313 */     fields[7] = new Field("", "PSEUDO_COLUMN", 5, 5);
/*      */     
/* 1315 */     final ArrayList rows = new ArrayList();
/* 1316 */     final Statement stmt = this.conn.getMetadataSafeStatement();
/*      */ 
/*      */     
/*      */     try {
/* 1320 */       (new IterateBlock(getCatalogIterator(catalog)) {
/*      */           void forEach(Object catalogStr) throws SQLException {
/* 1322 */             ResultSet results = null;
/*      */             
/*      */             try {
/* 1325 */               StringBuffer queryBuf = new StringBuffer("SHOW COLUMNS FROM ");
/*      */               
/* 1327 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/* 1328 */               queryBuf.append(table);
/* 1329 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/* 1330 */               queryBuf.append(" FROM ");
/* 1331 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/* 1332 */               queryBuf.append(catalogStr.toString());
/* 1333 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/*      */               
/* 1335 */               results = stmt.executeQuery(queryBuf.toString());
/*      */               
/* 1337 */               while (results.next()) {
/* 1338 */                 String keyType = results.getString("Key");
/*      */                 
/* 1340 */                 if (keyType != null && 
/* 1341 */                   StringUtils.startsWithIgnoreCase(keyType, "PRI"))
/*      */                 {
/* 1343 */                   byte[][] rowVal = new byte[8][];
/* 1344 */                   rowVal[0] = Integer.toString(2).getBytes();
/*      */ 
/*      */ 
/*      */                   
/* 1348 */                   rowVal[1] = results.getBytes("Field");
/*      */                   
/* 1350 */                   String type = results.getString("Type");
/* 1351 */                   int size = MysqlIO.getMaxBuf();
/* 1352 */                   int decimals = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1357 */                   if (type.indexOf("enum") != -1) {
/* 1358 */                     String temp = type.substring(type.indexOf("("), type.indexOf(")"));
/*      */ 
/*      */                     
/* 1361 */                     StringTokenizer tokenizer = new StringTokenizer(temp, ",");
/*      */                     
/* 1363 */                     int maxLength = 0;
/*      */                     
/* 1365 */                     while (tokenizer.hasMoreTokens()) {
/* 1366 */                       maxLength = Math.max(maxLength, tokenizer.nextToken().length() - 2);
/*      */                     }
/*      */ 
/*      */ 
/*      */                     
/* 1371 */                     size = maxLength;
/* 1372 */                     decimals = 0;
/* 1373 */                     type = "enum";
/* 1374 */                   } else if (type.indexOf("(") != -1) {
/* 1375 */                     if (type.indexOf(",") != -1) {
/* 1376 */                       size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.indexOf(",")));
/*      */ 
/*      */ 
/*      */                       
/* 1380 */                       decimals = Integer.parseInt(type.substring(type.indexOf(",") + 1, type.indexOf(")")));
/*      */                     
/*      */                     }
/*      */                     else {
/*      */                       
/* 1385 */                       size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.indexOf(")")));
/*      */                     } 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1391 */                     type = type.substring(0, type.indexOf("("));
/*      */                   } 
/*      */ 
/*      */                   
/* 1395 */                   rowVal[2] = DatabaseMetaData.this.s2b(String.valueOf(MysqlDefs.mysqlToJavaType(type)));
/*      */                   
/* 1397 */                   rowVal[3] = DatabaseMetaData.this.s2b(type);
/* 1398 */                   rowVal[4] = Integer.toString(size + decimals).getBytes();
/*      */                   
/* 1400 */                   rowVal[5] = Integer.toString(size + decimals).getBytes();
/*      */                   
/* 1402 */                   rowVal[6] = Integer.toString(decimals).getBytes();
/*      */                   
/* 1404 */                   rowVal[7] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1409 */                   rows.add(new ByteArrayRow(rowVal, DatabaseMetaData.this.getExceptionInterceptor()));
/*      */                 }
/*      */               
/*      */               } 
/* 1413 */             } catch (SQLException sqlEx) {
/* 1414 */               if (!"42S02".equals(sqlEx.getSQLState())) {
/* 1415 */                 throw sqlEx;
/*      */               }
/*      */             } finally {
/* 1418 */               if (results != null) {
/*      */                 try {
/* 1420 */                   results.close();
/* 1421 */                 } catch (Exception ex) {}
/*      */ 
/*      */ 
/*      */                 
/* 1425 */                 results = null;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         }).doForAll();
/*      */     } finally {
/* 1431 */       if (stmt != null) {
/* 1432 */         stmt.close();
/*      */       }
/*      */     } 
/*      */     
/* 1436 */     ResultSet results = buildResultSet(fields, rows);
/*      */     
/* 1438 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getCallStmtParameterTypes(String catalog, String procName, String parameterNamePattern, List resultRows) throws SQLException {
/* 1476 */     getCallStmtParameterTypes(catalog, procName, parameterNamePattern, resultRows, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getCallStmtParameterTypes(String catalog, String procName, String parameterNamePattern, List<ResultSetRow> resultRows, boolean forGetFunctionColumns) throws SQLException {
/* 1483 */     Statement paramRetrievalStmt = null;
/* 1484 */     ResultSet paramRetrievalRs = null;
/*      */     
/* 1486 */     if (parameterNamePattern == null) {
/* 1487 */       if (this.conn.getNullNamePatternMatchesAll()) {
/* 1488 */         parameterNamePattern = "%";
/*      */       } else {
/* 1490 */         throw SQLError.createSQLException("Parameter/Column name pattern can not be NULL or empty.", "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1496 */     String quoteChar = getIdentifierQuoteString();
/*      */     
/* 1498 */     String parameterDef = null;
/*      */     
/* 1500 */     byte[] procNameAsBytes = null;
/* 1501 */     byte[] procCatAsBytes = null;
/*      */     
/* 1503 */     boolean isProcedureInAnsiMode = false;
/* 1504 */     String storageDefnDelims = null;
/* 1505 */     String storageDefnClosures = null;
/*      */     
/*      */     try {
/* 1508 */       paramRetrievalStmt = this.conn.getMetadataSafeStatement();
/*      */       
/* 1510 */       if (this.conn.lowerCaseTableNames() && catalog != null && catalog.length() != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1516 */         String oldCatalog = this.conn.getCatalog();
/* 1517 */         ResultSet rs = null;
/*      */         
/*      */         try {
/* 1520 */           this.conn.setCatalog(catalog.replaceAll(quoteChar, ""));
/* 1521 */           rs = paramRetrievalStmt.executeQuery("SELECT DATABASE()");
/* 1522 */           rs.next();
/*      */           
/* 1524 */           catalog = rs.getString(1);
/*      */         }
/*      */         finally {
/*      */           
/* 1528 */           this.conn.setCatalog(oldCatalog);
/*      */           
/* 1530 */           if (rs != null) {
/* 1531 */             rs.close();
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1536 */       if (paramRetrievalStmt.getMaxRows() != 0) {
/* 1537 */         paramRetrievalStmt.setMaxRows(0);
/*      */       }
/*      */       
/* 1540 */       int dotIndex = -1;
/*      */       
/* 1542 */       if (!" ".equals(quoteChar)) {
/* 1543 */         dotIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(0, procName, ".", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
/*      */       }
/*      */       else {
/*      */         
/* 1547 */         dotIndex = procName.indexOf(".");
/*      */       } 
/*      */       
/* 1550 */       String dbName = null;
/*      */       
/* 1552 */       if (dotIndex != -1 && dotIndex + 1 < procName.length()) {
/* 1553 */         dbName = procName.substring(0, dotIndex);
/* 1554 */         procName = procName.substring(dotIndex + 1);
/*      */       } else {
/* 1556 */         dbName = catalog;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1562 */       String tmpProcName = procName;
/* 1563 */       tmpProcName = tmpProcName.replaceAll(quoteChar, "");
/*      */       try {
/* 1565 */         procNameAsBytes = tmpProcName.getBytes("UTF-8");
/* 1566 */       } catch (UnsupportedEncodingException ueEx) {
/* 1567 */         procNameAsBytes = s2b(tmpProcName);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1572 */       tmpProcName = dbName;
/* 1573 */       tmpProcName = tmpProcName.replaceAll(quoteChar, "");
/*      */       try {
/* 1575 */         procCatAsBytes = tmpProcName.getBytes("UTF-8");
/* 1576 */       } catch (UnsupportedEncodingException ueEx) {
/* 1577 */         procCatAsBytes = s2b(tmpProcName);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1582 */       StringBuffer procNameBuf = new StringBuffer();
/*      */       
/* 1584 */       if (dbName != null) {
/* 1585 */         if (!" ".equals(quoteChar) && !dbName.startsWith(quoteChar)) {
/* 1586 */           procNameBuf.append(quoteChar);
/*      */         }
/*      */         
/* 1589 */         procNameBuf.append(dbName);
/*      */         
/* 1591 */         if (!" ".equals(quoteChar) && !dbName.startsWith(quoteChar)) {
/* 1592 */           procNameBuf.append(quoteChar);
/*      */         }
/*      */         
/* 1595 */         procNameBuf.append(".");
/*      */       } 
/*      */       
/* 1598 */       boolean procNameIsNotQuoted = !procName.startsWith(quoteChar);
/*      */       
/* 1600 */       if (!" ".equals(quoteChar) && procNameIsNotQuoted) {
/* 1601 */         procNameBuf.append(quoteChar);
/*      */       }
/*      */       
/* 1604 */       procNameBuf.append(procName);
/*      */       
/* 1606 */       if (!" ".equals(quoteChar) && procNameIsNotQuoted) {
/* 1607 */         procNameBuf.append(quoteChar);
/*      */       }
/*      */       
/* 1610 */       boolean parsingFunction = false;
/*      */       
/*      */       try {
/* 1613 */         paramRetrievalRs = paramRetrievalStmt.executeQuery("SHOW CREATE PROCEDURE " + procNameBuf.toString());
/*      */ 
/*      */         
/* 1616 */         parsingFunction = false;
/* 1617 */       } catch (SQLException sqlEx) {
/* 1618 */         paramRetrievalRs = paramRetrievalStmt.executeQuery("SHOW CREATE FUNCTION " + procNameBuf.toString());
/*      */ 
/*      */         
/* 1621 */         parsingFunction = true;
/*      */       } 
/*      */       
/* 1624 */       if (paramRetrievalRs.next()) {
/* 1625 */         String procedureDef = parsingFunction ? paramRetrievalRs.getString("Create Function") : paramRetrievalRs.getString("Create Procedure");
/*      */ 
/*      */ 
/*      */         
/* 1629 */         if (procedureDef == null || procedureDef.length() == 0) {
/* 1630 */           throw SQLError.createSQLException("User does not have access to metadata required to determine stored procedure parameter types. If rights can not be granted, configure connection with \"noAccessToProcedureBodies=true\" to have driver generate parameters that represent INOUT strings irregardless of actual parameter types.", "S1000", getExceptionInterceptor());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1637 */           String sqlMode = paramRetrievalRs.getString("sql_mode");
/*      */           
/* 1639 */           if (StringUtils.indexOfIgnoreCase(sqlMode, "ANSI") != -1) {
/* 1640 */             isProcedureInAnsiMode = true;
/*      */           }
/* 1642 */         } catch (SQLException sqlEx) {}
/*      */ 
/*      */ 
/*      */         
/* 1646 */         String identifierMarkers = isProcedureInAnsiMode ? "`\"" : "`";
/* 1647 */         String identifierAndStringMarkers = "'" + identifierMarkers;
/* 1648 */         storageDefnDelims = "(" + identifierMarkers;
/* 1649 */         storageDefnClosures = ")" + identifierMarkers;
/*      */ 
/*      */         
/* 1652 */         procedureDef = StringUtils.stripComments(procedureDef, identifierAndStringMarkers, identifierAndStringMarkers, true, false, true, true);
/*      */ 
/*      */         
/* 1655 */         int openParenIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(0, procedureDef, "(", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
/*      */ 
/*      */ 
/*      */         
/* 1659 */         int endOfParamDeclarationIndex = 0;
/*      */         
/* 1661 */         endOfParamDeclarationIndex = endPositionOfParameterDeclaration(openParenIndex, procedureDef, quoteChar);
/*      */ 
/*      */         
/* 1664 */         if (parsingFunction) {
/*      */ 
/*      */ 
/*      */           
/* 1668 */           int returnsIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(0, procedureDef, " RETURNS ", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1673 */           int endReturnsDef = findEndOfReturnsClause(procedureDef, quoteChar, returnsIndex);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1678 */           int declarationStart = returnsIndex + "RETURNS ".length();
/*      */           
/* 1680 */           while (declarationStart < procedureDef.length() && 
/* 1681 */             Character.isWhitespace(procedureDef.charAt(declarationStart))) {
/* 1682 */             declarationStart++;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1688 */           String returnsDefn = procedureDef.substring(declarationStart, endReturnsDef).trim();
/* 1689 */           TypeDescriptor returnDescriptor = new TypeDescriptor(returnsDefn, null);
/*      */ 
/*      */           
/* 1692 */           resultRows.add(convertTypeDescriptorToProcedureRow(procNameAsBytes, procCatAsBytes, "", false, false, true, returnDescriptor, forGetFunctionColumns, 0));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1697 */         if (openParenIndex == -1 || endOfParamDeclarationIndex == -1)
/*      */         {
/*      */           
/* 1700 */           throw SQLError.createSQLException("Internal error when parsing callable statement metadata", "S1000", getExceptionInterceptor());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1706 */         parameterDef = procedureDef.substring(openParenIndex + 1, endOfParamDeclarationIndex);
/*      */       } 
/*      */     } finally {
/*      */       
/* 1710 */       SQLException sqlExRethrow = null;
/*      */       
/* 1712 */       if (paramRetrievalRs != null) {
/*      */         try {
/* 1714 */           paramRetrievalRs.close();
/* 1715 */         } catch (SQLException sqlEx) {
/* 1716 */           sqlExRethrow = sqlEx;
/*      */         } 
/*      */         
/* 1719 */         paramRetrievalRs = null;
/*      */       } 
/*      */       
/* 1722 */       if (paramRetrievalStmt != null) {
/*      */         try {
/* 1724 */           paramRetrievalStmt.close();
/* 1725 */         } catch (SQLException sqlEx) {
/* 1726 */           sqlExRethrow = sqlEx;
/*      */         } 
/*      */         
/* 1729 */         paramRetrievalStmt = null;
/*      */       } 
/*      */       
/* 1732 */       if (sqlExRethrow != null) {
/* 1733 */         throw sqlExRethrow;
/*      */       }
/*      */     } 
/*      */     
/* 1737 */     if (parameterDef != null) {
/* 1738 */       int ordinal = 1;
/*      */       
/* 1740 */       List<String> parseList = StringUtils.split(parameterDef, ",", storageDefnDelims, storageDefnClosures, true);
/*      */ 
/*      */       
/* 1743 */       int parseListLen = parseList.size();
/*      */       
/* 1745 */       for (int i = 0; i < parseListLen; i++) {
/* 1746 */         String declaration = parseList.get(i);
/*      */         
/* 1748 */         if (declaration.trim().length() == 0) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1754 */         declaration = declaration.replaceAll("[\\t\\n\\x0B\\f\\r]", " ");
/* 1755 */         StringTokenizer declarationTok = new StringTokenizer(declaration, " \t");
/*      */ 
/*      */         
/* 1758 */         String paramName = null;
/* 1759 */         boolean isOutParam = false;
/* 1760 */         boolean isInParam = false;
/*      */         
/* 1762 */         if (declarationTok.hasMoreTokens()) {
/* 1763 */           String possibleParamName = declarationTok.nextToken();
/*      */           
/* 1765 */           if (possibleParamName.equalsIgnoreCase("OUT")) {
/* 1766 */             isOutParam = true;
/*      */             
/* 1768 */             if (declarationTok.hasMoreTokens()) {
/* 1769 */               paramName = declarationTok.nextToken();
/*      */             } else {
/* 1771 */               throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter name)", "S1000", getExceptionInterceptor());
/*      */             }
/*      */           
/*      */           }
/* 1775 */           else if (possibleParamName.equalsIgnoreCase("INOUT")) {
/* 1776 */             isOutParam = true;
/* 1777 */             isInParam = true;
/*      */             
/* 1779 */             if (declarationTok.hasMoreTokens()) {
/* 1780 */               paramName = declarationTok.nextToken();
/*      */             } else {
/* 1782 */               throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter name)", "S1000", getExceptionInterceptor());
/*      */             }
/*      */           
/*      */           }
/* 1786 */           else if (possibleParamName.equalsIgnoreCase("IN")) {
/* 1787 */             isOutParam = false;
/* 1788 */             isInParam = true;
/*      */             
/* 1790 */             if (declarationTok.hasMoreTokens()) {
/* 1791 */               paramName = declarationTok.nextToken();
/*      */             } else {
/* 1793 */               throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter name)", "S1000", getExceptionInterceptor());
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 1798 */             isOutParam = false;
/* 1799 */             isInParam = true;
/*      */             
/* 1801 */             paramName = possibleParamName;
/*      */           } 
/*      */           
/* 1804 */           TypeDescriptor typeDesc = null;
/*      */           
/* 1806 */           if (declarationTok.hasMoreTokens()) {
/* 1807 */             StringBuffer typeInfoBuf = new StringBuffer(declarationTok.nextToken());
/*      */ 
/*      */             
/* 1810 */             while (declarationTok.hasMoreTokens()) {
/* 1811 */               typeInfoBuf.append(" ");
/* 1812 */               typeInfoBuf.append(declarationTok.nextToken());
/*      */             } 
/*      */             
/* 1815 */             String typeInfo = typeInfoBuf.toString();
/*      */             
/* 1817 */             typeDesc = new TypeDescriptor(typeInfo, null);
/*      */           } else {
/* 1819 */             throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter type)", "S1000", getExceptionInterceptor());
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1824 */           if ((paramName.startsWith("`") && paramName.endsWith("`")) || (isProcedureInAnsiMode && paramName.startsWith("\"") && paramName.endsWith("\"")))
/*      */           {
/* 1826 */             paramName = paramName.substring(1, paramName.length() - 1);
/*      */           }
/*      */           
/* 1829 */           int wildCompareRes = StringUtils.wildCompare(paramName, parameterNamePattern);
/*      */ 
/*      */           
/* 1832 */           if (wildCompareRes != -1) {
/* 1833 */             ResultSetRow row = convertTypeDescriptorToProcedureRow(procNameAsBytes, procCatAsBytes, paramName, isOutParam, isInParam, false, typeDesc, forGetFunctionColumns, ordinal++);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1838 */             resultRows.add(row);
/*      */           } 
/*      */         } else {
/* 1841 */           throw SQLError.createSQLException("Internal error when parsing callable statement metadata (unknown output from 'SHOW CREATE PROCEDURE')", "S1000", getExceptionInterceptor());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int endPositionOfParameterDeclaration(int beginIndex, String procedureDef, String quoteChar) throws SQLException {
/* 1870 */     int currentPos = beginIndex + 1;
/* 1871 */     int parenDepth = 1;
/*      */     
/* 1873 */     while (parenDepth > 0 && currentPos < procedureDef.length()) {
/* 1874 */       int closedParenIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(currentPos, procedureDef, ")", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
/*      */ 
/*      */ 
/*      */       
/* 1878 */       if (closedParenIndex != -1) {
/* 1879 */         int nextOpenParenIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(currentPos, procedureDef, "(", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1884 */         if (nextOpenParenIndex != -1 && nextOpenParenIndex < closedParenIndex) {
/*      */           
/* 1886 */           parenDepth++;
/* 1887 */           currentPos = closedParenIndex + 1;
/*      */           
/*      */           continue;
/*      */         } 
/* 1891 */         parenDepth--;
/* 1892 */         currentPos = closedParenIndex;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1897 */       throw SQLError.createSQLException("Internal error when parsing callable statement metadata", "S1000", getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1904 */     return currentPos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int findEndOfReturnsClause(String procedureDefn, String quoteChar, int positionOfReturnKeyword) throws SQLException {
/* 1929 */     String[] tokens = { "LANGUAGE", "NOT", "DETERMINISTIC", "CONTAINS", "NO", "READ", "MODIFIES", "SQL", "COMMENT", "BEGIN", "RETURN" };
/*      */ 
/*      */ 
/*      */     
/* 1933 */     int startLookingAt = positionOfReturnKeyword + "RETURNS".length() + 1;
/*      */     
/* 1935 */     int endOfReturn = -1;
/*      */     int i;
/* 1937 */     for (i = 0; i < tokens.length; i++) {
/* 1938 */       int nextEndOfReturn = StringUtils.indexOfIgnoreCaseRespectQuotes(startLookingAt, procedureDefn, tokens[i], quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
/*      */ 
/*      */ 
/*      */       
/* 1942 */       if (nextEndOfReturn != -1 && (
/* 1943 */         endOfReturn == -1 || nextEndOfReturn < endOfReturn)) {
/* 1944 */         endOfReturn = nextEndOfReturn;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1949 */     if (endOfReturn != -1) {
/* 1950 */       return endOfReturn;
/*      */     }
/*      */ 
/*      */     
/* 1954 */     endOfReturn = StringUtils.indexOfIgnoreCaseRespectQuotes(startLookingAt, procedureDefn, ":", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
/*      */ 
/*      */ 
/*      */     
/* 1958 */     if (endOfReturn != -1)
/*      */     {
/* 1960 */       for (i = endOfReturn; i > 0; i--) {
/* 1961 */         if (Character.isWhitespace(procedureDefn.charAt(i))) {
/* 1962 */           return i;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1969 */     throw SQLError.createSQLException("Internal error when parsing callable statement metadata", "S1000", getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getCascadeDeleteOption(String cascadeOptions) {
/* 1983 */     int onDeletePos = cascadeOptions.indexOf("ON DELETE");
/*      */     
/* 1985 */     if (onDeletePos != -1) {
/* 1986 */       String deleteOptions = cascadeOptions.substring(onDeletePos, cascadeOptions.length());
/*      */ 
/*      */       
/* 1989 */       if (deleteOptions.startsWith("ON DELETE CASCADE"))
/* 1990 */         return 0; 
/* 1991 */       if (deleteOptions.startsWith("ON DELETE SET NULL"))
/* 1992 */         return 2; 
/* 1993 */       if (deleteOptions.startsWith("ON DELETE RESTRICT"))
/* 1994 */         return 1; 
/* 1995 */       if (deleteOptions.startsWith("ON DELETE NO ACTION")) {
/* 1996 */         return 3;
/*      */       }
/*      */     } 
/*      */     
/* 2000 */     return 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getCascadeUpdateOption(String cascadeOptions) {
/* 2012 */     int onUpdatePos = cascadeOptions.indexOf("ON UPDATE");
/*      */     
/* 2014 */     if (onUpdatePos != -1) {
/* 2015 */       String updateOptions = cascadeOptions.substring(onUpdatePos, cascadeOptions.length());
/*      */ 
/*      */       
/* 2018 */       if (updateOptions.startsWith("ON UPDATE CASCADE"))
/* 2019 */         return 0; 
/* 2020 */       if (updateOptions.startsWith("ON UPDATE SET NULL"))
/* 2021 */         return 2; 
/* 2022 */       if (updateOptions.startsWith("ON UPDATE RESTRICT"))
/* 2023 */         return 1; 
/* 2024 */       if (updateOptions.startsWith("ON UPDATE NO ACTION")) {
/* 2025 */         return 3;
/*      */       }
/*      */     } 
/*      */     
/* 2029 */     return 3;
/*      */   }
/*      */ 
/*      */   
/*      */   protected IteratorWithCleanup getCatalogIterator(String catalogSpec) throws SQLException {
/*      */     IteratorWithCleanup allCatalogsIter;
/* 2035 */     if (catalogSpec != null) {
/* 2036 */       if (!catalogSpec.equals("")) {
/* 2037 */         allCatalogsIter = new SingleStringIterator(catalogSpec);
/*      */       } else {
/*      */         
/* 2040 */         allCatalogsIter = new SingleStringIterator(this.database);
/*      */       } 
/* 2042 */     } else if (this.conn.getNullCatalogMeansCurrent()) {
/* 2043 */       allCatalogsIter = new SingleStringIterator(this.database);
/*      */     } else {
/* 2045 */       allCatalogsIter = new ResultSetIterator(getCatalogs(), 1);
/*      */     } 
/*      */     
/* 2048 */     return allCatalogsIter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getCatalogs() throws SQLException {
/* 2067 */     ResultSet results = null;
/* 2068 */     Statement stmt = null;
/*      */     
/*      */     try {
/* 2071 */       stmt = this.conn.createStatement();
/* 2072 */       stmt.setEscapeProcessing(false);
/* 2073 */       results = stmt.executeQuery("SHOW DATABASES");
/*      */       
/* 2075 */       ResultSetMetaData resultsMD = results.getMetaData();
/* 2076 */       Field[] fields = new Field[1];
/* 2077 */       fields[0] = new Field("", "TABLE_CAT", 12, resultsMD.getColumnDisplaySize(1));
/*      */ 
/*      */       
/* 2080 */       ArrayList<ByteArrayRow> tuples = new ArrayList();
/*      */       
/* 2082 */       while (results.next()) {
/* 2083 */         byte[][] rowVal = new byte[1][];
/* 2084 */         rowVal[0] = results.getBytes(1);
/* 2085 */         tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */       } 
/*      */       
/* 2088 */       return buildResultSet(fields, tuples);
/*      */     } finally {
/* 2090 */       if (results != null) {
/*      */         try {
/* 2092 */           results.close();
/* 2093 */         } catch (SQLException sqlEx) {
/* 2094 */           AssertionFailedException.shouldNotHappen(sqlEx);
/*      */         } 
/*      */         
/* 2097 */         results = null;
/*      */       } 
/*      */       
/* 2100 */       if (stmt != null) {
/*      */         try {
/* 2102 */           stmt.close();
/* 2103 */         } catch (SQLException sqlEx) {
/* 2104 */           AssertionFailedException.shouldNotHappen(sqlEx);
/*      */         } 
/*      */         
/* 2107 */         stmt = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCatalogSeparator() throws SQLException {
/* 2120 */     return ".";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCatalogTerm() throws SQLException {
/* 2137 */     return "database";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
/* 2178 */     Field[] fields = new Field[8];
/* 2179 */     fields[0] = new Field("", "TABLE_CAT", 1, 64);
/* 2180 */     fields[1] = new Field("", "TABLE_SCHEM", 1, 1);
/* 2181 */     fields[2] = new Field("", "TABLE_NAME", 1, 64);
/* 2182 */     fields[3] = new Field("", "COLUMN_NAME", 1, 64);
/* 2183 */     fields[4] = new Field("", "GRANTOR", 1, 77);
/* 2184 */     fields[5] = new Field("", "GRANTEE", 1, 77);
/* 2185 */     fields[6] = new Field("", "PRIVILEGE", 1, 64);
/* 2186 */     fields[7] = new Field("", "IS_GRANTABLE", 1, 3);
/*      */     
/* 2188 */     StringBuffer grantQuery = new StringBuffer("SELECT c.host, c.db, t.grantor, c.user, c.table_name, c.column_name, c.column_priv from mysql.columns_priv c, mysql.tables_priv t where c.host = t.host and c.db = t.db and c.table_name = t.table_name ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2195 */     if (catalog != null && catalog.length() != 0) {
/* 2196 */       grantQuery.append(" AND c.db='");
/* 2197 */       grantQuery.append(catalog);
/* 2198 */       grantQuery.append("' ");
/*      */     } 
/*      */ 
/*      */     
/* 2202 */     grantQuery.append(" AND c.table_name ='");
/* 2203 */     grantQuery.append(table);
/* 2204 */     grantQuery.append("' AND c.column_name like '");
/* 2205 */     grantQuery.append(columnNamePattern);
/* 2206 */     grantQuery.append("'");
/*      */     
/* 2208 */     Statement stmt = null;
/* 2209 */     ResultSet results = null;
/* 2210 */     ArrayList<ByteArrayRow> grantRows = new ArrayList();
/*      */     
/*      */     try {
/* 2213 */       stmt = this.conn.createStatement();
/* 2214 */       stmt.setEscapeProcessing(false);
/* 2215 */       results = stmt.executeQuery(grantQuery.toString());
/*      */       
/* 2217 */       while (results.next()) {
/* 2218 */         String host = results.getString(1);
/* 2219 */         String db = results.getString(2);
/* 2220 */         String grantor = results.getString(3);
/* 2221 */         String user = results.getString(4);
/*      */         
/* 2223 */         if (user == null || user.length() == 0) {
/* 2224 */           user = "%";
/*      */         }
/*      */         
/* 2227 */         StringBuffer fullUser = new StringBuffer(user);
/*      */         
/* 2229 */         if (host != null && this.conn.getUseHostsInPrivileges()) {
/* 2230 */           fullUser.append("@");
/* 2231 */           fullUser.append(host);
/*      */         } 
/*      */         
/* 2234 */         String columnName = results.getString(6);
/* 2235 */         String allPrivileges = results.getString(7);
/*      */         
/* 2237 */         if (allPrivileges != null) {
/* 2238 */           allPrivileges = allPrivileges.toUpperCase(Locale.ENGLISH);
/*      */           
/* 2240 */           StringTokenizer st = new StringTokenizer(allPrivileges, ",");
/*      */           
/* 2242 */           while (st.hasMoreTokens()) {
/* 2243 */             String privilege = st.nextToken().trim();
/* 2244 */             byte[][] tuple = new byte[8][];
/* 2245 */             tuple[0] = s2b(db);
/* 2246 */             tuple[1] = null;
/* 2247 */             tuple[2] = s2b(table);
/* 2248 */             tuple[3] = s2b(columnName);
/*      */             
/* 2250 */             if (grantor != null) {
/* 2251 */               tuple[4] = s2b(grantor);
/*      */             } else {
/* 2253 */               tuple[4] = null;
/*      */             } 
/*      */             
/* 2256 */             tuple[5] = s2b(fullUser.toString());
/* 2257 */             tuple[6] = s2b(privilege);
/* 2258 */             tuple[7] = null;
/* 2259 */             grantRows.add(new ByteArrayRow(tuple, getExceptionInterceptor()));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 2264 */       if (results != null) {
/*      */         try {
/* 2266 */           results.close();
/* 2267 */         } catch (Exception ex) {}
/*      */ 
/*      */ 
/*      */         
/* 2271 */         results = null;
/*      */       } 
/*      */       
/* 2274 */       if (stmt != null) {
/*      */         try {
/* 2276 */           stmt.close();
/* 2277 */         } catch (Exception ex) {}
/*      */ 
/*      */ 
/*      */         
/* 2281 */         stmt = null;
/*      */       } 
/*      */     } 
/*      */     
/* 2285 */     return buildResultSet(fields, grantRows);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getColumns(String catalog, final String schemaPattern, final String tableNamePattern, String columnNamePattern) throws SQLException {
/* 2349 */     if (columnNamePattern == null) {
/* 2350 */       if (this.conn.getNullNamePatternMatchesAll()) {
/* 2351 */         columnNamePattern = "%";
/*      */       } else {
/* 2353 */         throw SQLError.createSQLException("Column name pattern can not be NULL or empty.", "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2359 */     final String colPattern = columnNamePattern;
/*      */     
/* 2361 */     Field[] fields = createColumnsFields();
/*      */     
/* 2363 */     final ArrayList rows = new ArrayList();
/* 2364 */     final Statement stmt = this.conn.getMetadataSafeStatement();
/*      */ 
/*      */     
/*      */     try {
/* 2368 */       (new IterateBlock(getCatalogIterator(catalog)) { void forEach(Object catalogStr) throws SQLException { // Byte code:
/*      */             //   0: new java/util/ArrayList
/*      */             //   3: dup
/*      */             //   4: invokespecial <init> : ()V
/*      */             //   7: astore_2
/*      */             //   8: aload_0
/*      */             //   9: getfield val$tableNamePattern : Ljava/lang/String;
/*      */             //   12: ifnonnull -> 111
/*      */             //   15: aconst_null
/*      */             //   16: astore_3
/*      */             //   17: aload_0
/*      */             //   18: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   21: aload_1
/*      */             //   22: checkcast java/lang/String
/*      */             //   25: aload_0
/*      */             //   26: getfield val$schemaPattern : Ljava/lang/String;
/*      */             //   29: ldc '%'
/*      */             //   31: iconst_0
/*      */             //   32: anewarray java/lang/String
/*      */             //   35: invokevirtual getTables : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Ljava/sql/ResultSet;
/*      */             //   38: astore_3
/*      */             //   39: aload_3
/*      */             //   40: invokeinterface next : ()Z
/*      */             //   45: ifeq -> 68
/*      */             //   48: aload_3
/*      */             //   49: ldc 'TABLE_NAME'
/*      */             //   51: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */             //   56: astore #4
/*      */             //   58: aload_2
/*      */             //   59: aload #4
/*      */             //   61: invokevirtual add : (Ljava/lang/Object;)Z
/*      */             //   64: pop
/*      */             //   65: goto -> 39
/*      */             //   68: jsr -> 82
/*      */             //   71: goto -> 108
/*      */             //   74: astore #5
/*      */             //   76: jsr -> 82
/*      */             //   79: aload #5
/*      */             //   81: athrow
/*      */             //   82: astore #6
/*      */             //   84: aload_3
/*      */             //   85: ifnull -> 106
/*      */             //   88: aload_3
/*      */             //   89: invokeinterface close : ()V
/*      */             //   94: goto -> 104
/*      */             //   97: astore #7
/*      */             //   99: aload #7
/*      */             //   101: invokestatic shouldNotHappen : (Ljava/lang/Exception;)V
/*      */             //   104: aconst_null
/*      */             //   105: astore_3
/*      */             //   106: ret #6
/*      */             //   108: goto -> 206
/*      */             //   111: aconst_null
/*      */             //   112: astore_3
/*      */             //   113: aload_0
/*      */             //   114: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   117: aload_1
/*      */             //   118: checkcast java/lang/String
/*      */             //   121: aload_0
/*      */             //   122: getfield val$schemaPattern : Ljava/lang/String;
/*      */             //   125: aload_0
/*      */             //   126: getfield val$tableNamePattern : Ljava/lang/String;
/*      */             //   129: iconst_0
/*      */             //   130: anewarray java/lang/String
/*      */             //   133: invokevirtual getTables : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Ljava/sql/ResultSet;
/*      */             //   136: astore_3
/*      */             //   137: aload_3
/*      */             //   138: invokeinterface next : ()Z
/*      */             //   143: ifeq -> 166
/*      */             //   146: aload_3
/*      */             //   147: ldc 'TABLE_NAME'
/*      */             //   149: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */             //   154: astore #4
/*      */             //   156: aload_2
/*      */             //   157: aload #4
/*      */             //   159: invokevirtual add : (Ljava/lang/Object;)Z
/*      */             //   162: pop
/*      */             //   163: goto -> 137
/*      */             //   166: jsr -> 180
/*      */             //   169: goto -> 206
/*      */             //   172: astore #8
/*      */             //   174: jsr -> 180
/*      */             //   177: aload #8
/*      */             //   179: athrow
/*      */             //   180: astore #9
/*      */             //   182: aload_3
/*      */             //   183: ifnull -> 204
/*      */             //   186: aload_3
/*      */             //   187: invokeinterface close : ()V
/*      */             //   192: goto -> 202
/*      */             //   195: astore #10
/*      */             //   197: aload #10
/*      */             //   199: invokestatic shouldNotHappen : (Ljava/lang/Exception;)V
/*      */             //   202: aconst_null
/*      */             //   203: astore_3
/*      */             //   204: ret #9
/*      */             //   206: aload_2
/*      */             //   207: invokevirtual iterator : ()Ljava/util/Iterator;
/*      */             //   210: astore_3
/*      */             //   211: aload_3
/*      */             //   212: invokeinterface hasNext : ()Z
/*      */             //   217: ifeq -> 1300
/*      */             //   220: aload_3
/*      */             //   221: invokeinterface next : ()Ljava/lang/Object;
/*      */             //   226: checkcast java/lang/String
/*      */             //   229: astore #4
/*      */             //   231: aconst_null
/*      */             //   232: astore #5
/*      */             //   234: new java/lang/StringBuffer
/*      */             //   237: dup
/*      */             //   238: ldc 'SHOW '
/*      */             //   240: invokespecial <init> : (Ljava/lang/String;)V
/*      */             //   243: astore #6
/*      */             //   245: aload_0
/*      */             //   246: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   249: getfield conn : Lcom/mysql/jdbc/MySQLConnection;
/*      */             //   252: iconst_4
/*      */             //   253: iconst_1
/*      */             //   254: iconst_0
/*      */             //   255: invokeinterface versionMeetsMinimum : (III)Z
/*      */             //   260: ifeq -> 271
/*      */             //   263: aload #6
/*      */             //   265: ldc 'FULL '
/*      */             //   267: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   270: pop
/*      */             //   271: aload #6
/*      */             //   273: ldc 'COLUMNS FROM '
/*      */             //   275: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   278: pop
/*      */             //   279: aload #6
/*      */             //   281: aload_0
/*      */             //   282: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   285: getfield quotedId : Ljava/lang/String;
/*      */             //   288: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   291: pop
/*      */             //   292: aload #6
/*      */             //   294: aload #4
/*      */             //   296: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   299: pop
/*      */             //   300: aload #6
/*      */             //   302: aload_0
/*      */             //   303: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   306: getfield quotedId : Ljava/lang/String;
/*      */             //   309: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   312: pop
/*      */             //   313: aload #6
/*      */             //   315: ldc ' FROM '
/*      */             //   317: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   320: pop
/*      */             //   321: aload #6
/*      */             //   323: aload_0
/*      */             //   324: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   327: getfield quotedId : Ljava/lang/String;
/*      */             //   330: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   333: pop
/*      */             //   334: aload #6
/*      */             //   336: aload_1
/*      */             //   337: checkcast java/lang/String
/*      */             //   340: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   343: pop
/*      */             //   344: aload #6
/*      */             //   346: aload_0
/*      */             //   347: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   350: getfield quotedId : Ljava/lang/String;
/*      */             //   353: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   356: pop
/*      */             //   357: aload #6
/*      */             //   359: ldc ' LIKE ''
/*      */             //   361: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   364: pop
/*      */             //   365: aload #6
/*      */             //   367: aload_0
/*      */             //   368: getfield val$colPattern : Ljava/lang/String;
/*      */             //   371: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   374: pop
/*      */             //   375: aload #6
/*      */             //   377: ldc '''
/*      */             //   379: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   382: pop
/*      */             //   383: iconst_0
/*      */             //   384: istore #7
/*      */             //   386: aconst_null
/*      */             //   387: astore #8
/*      */             //   389: aload_0
/*      */             //   390: getfield val$colPattern : Ljava/lang/String;
/*      */             //   393: ldc '%'
/*      */             //   395: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */             //   398: ifne -> 597
/*      */             //   401: iconst_1
/*      */             //   402: istore #7
/*      */             //   404: new java/lang/StringBuffer
/*      */             //   407: dup
/*      */             //   408: ldc 'SHOW '
/*      */             //   410: invokespecial <init> : (Ljava/lang/String;)V
/*      */             //   413: astore #9
/*      */             //   415: aload_0
/*      */             //   416: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   419: getfield conn : Lcom/mysql/jdbc/MySQLConnection;
/*      */             //   422: iconst_4
/*      */             //   423: iconst_1
/*      */             //   424: iconst_0
/*      */             //   425: invokeinterface versionMeetsMinimum : (III)Z
/*      */             //   430: ifeq -> 441
/*      */             //   433: aload #9
/*      */             //   435: ldc 'FULL '
/*      */             //   437: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   440: pop
/*      */             //   441: aload #9
/*      */             //   443: ldc 'COLUMNS FROM '
/*      */             //   445: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   448: pop
/*      */             //   449: aload #9
/*      */             //   451: aload_0
/*      */             //   452: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   455: getfield quotedId : Ljava/lang/String;
/*      */             //   458: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   461: pop
/*      */             //   462: aload #9
/*      */             //   464: aload #4
/*      */             //   466: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   469: pop
/*      */             //   470: aload #9
/*      */             //   472: aload_0
/*      */             //   473: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   476: getfield quotedId : Ljava/lang/String;
/*      */             //   479: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   482: pop
/*      */             //   483: aload #9
/*      */             //   485: ldc ' FROM '
/*      */             //   487: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   490: pop
/*      */             //   491: aload #9
/*      */             //   493: aload_0
/*      */             //   494: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   497: getfield quotedId : Ljava/lang/String;
/*      */             //   500: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   503: pop
/*      */             //   504: aload #9
/*      */             //   506: aload_1
/*      */             //   507: checkcast java/lang/String
/*      */             //   510: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   513: pop
/*      */             //   514: aload #9
/*      */             //   516: aload_0
/*      */             //   517: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   520: getfield quotedId : Ljava/lang/String;
/*      */             //   523: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */             //   526: pop
/*      */             //   527: aload_0
/*      */             //   528: getfield val$stmt : Ljava/sql/Statement;
/*      */             //   531: aload #9
/*      */             //   533: invokevirtual toString : ()Ljava/lang/String;
/*      */             //   536: invokeinterface executeQuery : (Ljava/lang/String;)Ljava/sql/ResultSet;
/*      */             //   541: astore #5
/*      */             //   543: new java/util/HashMap
/*      */             //   546: dup
/*      */             //   547: invokespecial <init> : ()V
/*      */             //   550: astore #8
/*      */             //   552: iconst_1
/*      */             //   553: istore #10
/*      */             //   555: aload #5
/*      */             //   557: invokeinterface next : ()Z
/*      */             //   562: ifeq -> 597
/*      */             //   565: aload #5
/*      */             //   567: ldc 'Field'
/*      */             //   569: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */             //   574: astore #11
/*      */             //   576: aload #8
/*      */             //   578: aload #11
/*      */             //   580: iload #10
/*      */             //   582: iinc #10, 1
/*      */             //   585: invokestatic integerValueOf : (I)Ljava/lang/Integer;
/*      */             //   588: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */             //   593: pop
/*      */             //   594: goto -> 555
/*      */             //   597: aload_0
/*      */             //   598: getfield val$stmt : Ljava/sql/Statement;
/*      */             //   601: aload #6
/*      */             //   603: invokevirtual toString : ()Ljava/lang/String;
/*      */             //   606: invokeinterface executeQuery : (Ljava/lang/String;)Ljava/sql/ResultSet;
/*      */             //   611: astore #5
/*      */             //   613: iconst_1
/*      */             //   614: istore #9
/*      */             //   616: aload #5
/*      */             //   618: invokeinterface next : ()Z
/*      */             //   623: ifeq -> 1259
/*      */             //   626: bipush #23
/*      */             //   628: anewarray [B
/*      */             //   631: astore #10
/*      */             //   633: aload #10
/*      */             //   635: iconst_0
/*      */             //   636: aload_0
/*      */             //   637: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   640: aload_1
/*      */             //   641: checkcast java/lang/String
/*      */             //   644: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   647: aastore
/*      */             //   648: aload #10
/*      */             //   650: iconst_1
/*      */             //   651: aconst_null
/*      */             //   652: aastore
/*      */             //   653: aload #10
/*      */             //   655: iconst_2
/*      */             //   656: aload_0
/*      */             //   657: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   660: aload #4
/*      */             //   662: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   665: aastore
/*      */             //   666: aload #10
/*      */             //   668: iconst_3
/*      */             //   669: aload #5
/*      */             //   671: ldc 'Field'
/*      */             //   673: invokeinterface getBytes : (Ljava/lang/String;)[B
/*      */             //   678: aastore
/*      */             //   679: new com/mysql/jdbc/DatabaseMetaData$TypeDescriptor
/*      */             //   682: dup
/*      */             //   683: aload_0
/*      */             //   684: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   687: aload #5
/*      */             //   689: ldc 'Type'
/*      */             //   691: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */             //   696: aload #5
/*      */             //   698: ldc 'Null'
/*      */             //   700: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */             //   705: invokespecial <init> : (Lcom/mysql/jdbc/DatabaseMetaData;Ljava/lang/String;Ljava/lang/String;)V
/*      */             //   708: astore #11
/*      */             //   710: aload #10
/*      */             //   712: iconst_4
/*      */             //   713: aload #11
/*      */             //   715: getfield dataType : S
/*      */             //   718: invokestatic toString : (S)Ljava/lang/String;
/*      */             //   721: invokevirtual getBytes : ()[B
/*      */             //   724: aastore
/*      */             //   725: aload #10
/*      */             //   727: iconst_5
/*      */             //   728: aload_0
/*      */             //   729: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   732: aload #11
/*      */             //   734: getfield typeName : Ljava/lang/String;
/*      */             //   737: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   740: aastore
/*      */             //   741: aload #10
/*      */             //   743: bipush #6
/*      */             //   745: aload #11
/*      */             //   747: getfield columnSize : Ljava/lang/Integer;
/*      */             //   750: ifnonnull -> 757
/*      */             //   753: aconst_null
/*      */             //   754: goto -> 772
/*      */             //   757: aload_0
/*      */             //   758: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   761: aload #11
/*      */             //   763: getfield columnSize : Ljava/lang/Integer;
/*      */             //   766: invokevirtual toString : ()Ljava/lang/String;
/*      */             //   769: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   772: aastore
/*      */             //   773: aload #10
/*      */             //   775: bipush #7
/*      */             //   777: aload_0
/*      */             //   778: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   781: aload #11
/*      */             //   783: getfield bufferLength : I
/*      */             //   786: invokestatic toString : (I)Ljava/lang/String;
/*      */             //   789: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   792: aastore
/*      */             //   793: aload #10
/*      */             //   795: bipush #8
/*      */             //   797: aload #11
/*      */             //   799: getfield decimalDigits : Ljava/lang/Integer;
/*      */             //   802: ifnonnull -> 809
/*      */             //   805: aconst_null
/*      */             //   806: goto -> 824
/*      */             //   809: aload_0
/*      */             //   810: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   813: aload #11
/*      */             //   815: getfield decimalDigits : Ljava/lang/Integer;
/*      */             //   818: invokevirtual toString : ()Ljava/lang/String;
/*      */             //   821: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   824: aastore
/*      */             //   825: aload #10
/*      */             //   827: bipush #9
/*      */             //   829: aload_0
/*      */             //   830: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   833: aload #11
/*      */             //   835: getfield numPrecRadix : I
/*      */             //   838: invokestatic toString : (I)Ljava/lang/String;
/*      */             //   841: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   844: aastore
/*      */             //   845: aload #10
/*      */             //   847: bipush #10
/*      */             //   849: aload_0
/*      */             //   850: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   853: aload #11
/*      */             //   855: getfield nullability : I
/*      */             //   858: invokestatic toString : (I)Ljava/lang/String;
/*      */             //   861: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   864: aastore
/*      */             //   865: aload_0
/*      */             //   866: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   869: getfield conn : Lcom/mysql/jdbc/MySQLConnection;
/*      */             //   872: iconst_4
/*      */             //   873: iconst_1
/*      */             //   874: iconst_0
/*      */             //   875: invokeinterface versionMeetsMinimum : (III)Z
/*      */             //   880: ifeq -> 900
/*      */             //   883: aload #10
/*      */             //   885: bipush #11
/*      */             //   887: aload #5
/*      */             //   889: ldc 'Comment'
/*      */             //   891: invokeinterface getBytes : (Ljava/lang/String;)[B
/*      */             //   896: aastore
/*      */             //   897: goto -> 914
/*      */             //   900: aload #10
/*      */             //   902: bipush #11
/*      */             //   904: aload #5
/*      */             //   906: ldc 'Extra'
/*      */             //   908: invokeinterface getBytes : (Ljava/lang/String;)[B
/*      */             //   913: aastore
/*      */             //   914: goto -> 927
/*      */             //   917: astore #12
/*      */             //   919: aload #10
/*      */             //   921: bipush #11
/*      */             //   923: iconst_0
/*      */             //   924: newarray byte
/*      */             //   926: aastore
/*      */             //   927: aload #10
/*      */             //   929: bipush #12
/*      */             //   931: aload #5
/*      */             //   933: ldc 'Default'
/*      */             //   935: invokeinterface getBytes : (Ljava/lang/String;)[B
/*      */             //   940: aastore
/*      */             //   941: aload #10
/*      */             //   943: bipush #13
/*      */             //   945: iconst_1
/*      */             //   946: newarray byte
/*      */             //   948: dup
/*      */             //   949: iconst_0
/*      */             //   950: bipush #48
/*      */             //   952: bastore
/*      */             //   953: aastore
/*      */             //   954: aload #10
/*      */             //   956: bipush #14
/*      */             //   958: iconst_1
/*      */             //   959: newarray byte
/*      */             //   961: dup
/*      */             //   962: iconst_0
/*      */             //   963: bipush #48
/*      */             //   965: bastore
/*      */             //   966: aastore
/*      */             //   967: aload #11
/*      */             //   969: getfield typeName : Ljava/lang/String;
/*      */             //   972: ldc 'CHAR'
/*      */             //   974: invokestatic indexOfIgnoreCase : (Ljava/lang/String;Ljava/lang/String;)I
/*      */             //   977: iconst_m1
/*      */             //   978: if_icmpne -> 1023
/*      */             //   981: aload #11
/*      */             //   983: getfield typeName : Ljava/lang/String;
/*      */             //   986: ldc 'BLOB'
/*      */             //   988: invokestatic indexOfIgnoreCase : (Ljava/lang/String;Ljava/lang/String;)I
/*      */             //   991: iconst_m1
/*      */             //   992: if_icmpne -> 1023
/*      */             //   995: aload #11
/*      */             //   997: getfield typeName : Ljava/lang/String;
/*      */             //   1000: ldc 'TEXT'
/*      */             //   1002: invokestatic indexOfIgnoreCase : (Ljava/lang/String;Ljava/lang/String;)I
/*      */             //   1005: iconst_m1
/*      */             //   1006: if_icmpne -> 1023
/*      */             //   1009: aload #11
/*      */             //   1011: getfield typeName : Ljava/lang/String;
/*      */             //   1014: ldc 'BINARY'
/*      */             //   1016: invokestatic indexOfIgnoreCase : (Ljava/lang/String;Ljava/lang/String;)I
/*      */             //   1019: iconst_m1
/*      */             //   1020: if_icmpeq -> 1036
/*      */             //   1023: aload #10
/*      */             //   1025: bipush #15
/*      */             //   1027: aload #10
/*      */             //   1029: bipush #6
/*      */             //   1031: aaload
/*      */             //   1032: aastore
/*      */             //   1033: goto -> 1042
/*      */             //   1036: aload #10
/*      */             //   1038: bipush #15
/*      */             //   1040: aconst_null
/*      */             //   1041: aastore
/*      */             //   1042: iload #7
/*      */             //   1044: ifne -> 1066
/*      */             //   1047: aload #10
/*      */             //   1049: bipush #16
/*      */             //   1051: iload #9
/*      */             //   1053: iinc #9, 1
/*      */             //   1056: invokestatic toString : (I)Ljava/lang/String;
/*      */             //   1059: invokevirtual getBytes : ()[B
/*      */             //   1062: aastore
/*      */             //   1063: goto -> 1127
/*      */             //   1066: aload #5
/*      */             //   1068: ldc 'Field'
/*      */             //   1070: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */             //   1075: astore #12
/*      */             //   1077: aload #8
/*      */             //   1079: aload #12
/*      */             //   1081: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */             //   1086: checkcast java/lang/Integer
/*      */             //   1089: astore #13
/*      */             //   1091: aload #13
/*      */             //   1093: ifnull -> 1112
/*      */             //   1096: aload #10
/*      */             //   1098: bipush #16
/*      */             //   1100: aload #13
/*      */             //   1102: invokevirtual toString : ()Ljava/lang/String;
/*      */             //   1105: invokevirtual getBytes : ()[B
/*      */             //   1108: aastore
/*      */             //   1109: goto -> 1127
/*      */             //   1112: ldc 'Can not find column in full column list to determine true ordinal position.'
/*      */             //   1114: ldc 'S1000'
/*      */             //   1116: aload_0
/*      */             //   1117: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   1120: invokevirtual getExceptionInterceptor : ()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */             //   1123: invokestatic createSQLException : (Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */             //   1126: athrow
/*      */             //   1127: aload #10
/*      */             //   1129: bipush #17
/*      */             //   1131: aload_0
/*      */             //   1132: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   1135: aload #11
/*      */             //   1137: getfield isNullable : Ljava/lang/String;
/*      */             //   1140: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   1143: aastore
/*      */             //   1144: aload #10
/*      */             //   1146: bipush #18
/*      */             //   1148: aconst_null
/*      */             //   1149: aastore
/*      */             //   1150: aload #10
/*      */             //   1152: bipush #19
/*      */             //   1154: aconst_null
/*      */             //   1155: aastore
/*      */             //   1156: aload #10
/*      */             //   1158: bipush #20
/*      */             //   1160: aconst_null
/*      */             //   1161: aastore
/*      */             //   1162: aload #10
/*      */             //   1164: bipush #21
/*      */             //   1166: aconst_null
/*      */             //   1167: aastore
/*      */             //   1168: aload #10
/*      */             //   1170: bipush #22
/*      */             //   1172: aload_0
/*      */             //   1173: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   1176: ldc_w ''
/*      */             //   1179: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   1182: aastore
/*      */             //   1183: aload #5
/*      */             //   1185: ldc 'Extra'
/*      */             //   1187: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */             //   1192: astore #12
/*      */             //   1194: aload #12
/*      */             //   1196: ifnull -> 1232
/*      */             //   1199: aload #10
/*      */             //   1201: bipush #22
/*      */             //   1203: aload_0
/*      */             //   1204: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   1207: aload #12
/*      */             //   1209: ldc_w 'auto_increment'
/*      */             //   1212: invokestatic indexOfIgnoreCase : (Ljava/lang/String;Ljava/lang/String;)I
/*      */             //   1215: iconst_m1
/*      */             //   1216: if_icmpeq -> 1225
/*      */             //   1219: ldc_w 'YES'
/*      */             //   1222: goto -> 1228
/*      */             //   1225: ldc_w 'NO'
/*      */             //   1228: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */             //   1231: aastore
/*      */             //   1232: aload_0
/*      */             //   1233: getfield val$rows : Ljava/util/ArrayList;
/*      */             //   1236: new com/mysql/jdbc/ByteArrayRow
/*      */             //   1239: dup
/*      */             //   1240: aload #10
/*      */             //   1242: aload_0
/*      */             //   1243: getfield this$0 : Lcom/mysql/jdbc/DatabaseMetaData;
/*      */             //   1246: invokevirtual getExceptionInterceptor : ()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */             //   1249: invokespecial <init> : ([[BLcom/mysql/jdbc/ExceptionInterceptor;)V
/*      */             //   1252: invokevirtual add : (Ljava/lang/Object;)Z
/*      */             //   1255: pop
/*      */             //   1256: goto -> 616
/*      */             //   1259: jsr -> 1273
/*      */             //   1262: goto -> 1297
/*      */             //   1265: astore #14
/*      */             //   1267: jsr -> 1273
/*      */             //   1270: aload #14
/*      */             //   1272: athrow
/*      */             //   1273: astore #15
/*      */             //   1275: aload #5
/*      */             //   1277: ifnull -> 1295
/*      */             //   1280: aload #5
/*      */             //   1282: invokeinterface close : ()V
/*      */             //   1287: goto -> 1292
/*      */             //   1290: astore #16
/*      */             //   1292: aconst_null
/*      */             //   1293: astore #5
/*      */             //   1295: ret #15
/*      */             //   1297: goto -> 211
/*      */             //   1300: return
/*      */             // Line number table:
/*      */             //   Java source line number -> byte code offset
/*      */             //   #2371	-> 0
/*      */             //   #2373	-> 8
/*      */             //   #2375	-> 15
/*      */             //   #2378	-> 17
/*      */             //   #2381	-> 39
/*      */             //   #2382	-> 48
/*      */             //   #2384	-> 58
/*      */             //   #2385	-> 65
/*      */             //   #2386	-> 68
/*      */             //   #2397	-> 71
/*      */             //   #2387	-> 74
/*      */             //   #2389	-> 88
/*      */             //   #2393	-> 94
/*      */             //   #2390	-> 97
/*      */             //   #2391	-> 99
/*      */             //   #2395	-> 104
/*      */             //   #2398	-> 108
/*      */             //   #2399	-> 111
/*      */             //   #2402	-> 113
/*      */             //   #2405	-> 137
/*      */             //   #2406	-> 146
/*      */             //   #2408	-> 156
/*      */             //   #2409	-> 163
/*      */             //   #2410	-> 166
/*      */             //   #2421	-> 169
/*      */             //   #2411	-> 172
/*      */             //   #2413	-> 186
/*      */             //   #2417	-> 192
/*      */             //   #2414	-> 195
/*      */             //   #2415	-> 197
/*      */             //   #2419	-> 202
/*      */             //   #2424	-> 206
/*      */             //   #2426	-> 211
/*      */             //   #2427	-> 220
/*      */             //   #2429	-> 231
/*      */             //   #2432	-> 234
/*      */             //   #2434	-> 245
/*      */             //   #2435	-> 263
/*      */             //   #2438	-> 271
/*      */             //   #2439	-> 279
/*      */             //   #2440	-> 292
/*      */             //   #2441	-> 300
/*      */             //   #2442	-> 313
/*      */             //   #2443	-> 321
/*      */             //   #2444	-> 334
/*      */             //   #2445	-> 344
/*      */             //   #2446	-> 357
/*      */             //   #2447	-> 365
/*      */             //   #2448	-> 375
/*      */             //   #2455	-> 383
/*      */             //   #2456	-> 386
/*      */             //   #2458	-> 389
/*      */             //   #2459	-> 401
/*      */             //   #2461	-> 404
/*      */             //   #2464	-> 415
/*      */             //   #2465	-> 433
/*      */             //   #2468	-> 441
/*      */             //   #2469	-> 449
/*      */             //   #2470	-> 462
/*      */             //   #2471	-> 470
/*      */             //   #2472	-> 483
/*      */             //   #2473	-> 491
/*      */             //   #2474	-> 504
/*      */             //   #2476	-> 514
/*      */             //   #2478	-> 527
/*      */             //   #2481	-> 543
/*      */             //   #2483	-> 552
/*      */             //   #2485	-> 555
/*      */             //   #2486	-> 565
/*      */             //   #2489	-> 576
/*      */             //   #2491	-> 594
/*      */             //   #2494	-> 597
/*      */             //   #2496	-> 613
/*      */             //   #2498	-> 616
/*      */             //   #2499	-> 626
/*      */             //   #2500	-> 633
/*      */             //   #2501	-> 648
/*      */             //   #2504	-> 653
/*      */             //   #2505	-> 666
/*      */             //   #2507	-> 679
/*      */             //   #2511	-> 710
/*      */             //   #2515	-> 725
/*      */             //   #2517	-> 741
/*      */             //   #2518	-> 773
/*      */             //   #2519	-> 793
/*      */             //   #2520	-> 825
/*      */             //   #2522	-> 845
/*      */             //   #2533	-> 865
/*      */             //   #2534	-> 883
/*      */             //   #2537	-> 900
/*      */             //   #2541	-> 914
/*      */             //   #2539	-> 917
/*      */             //   #2540	-> 919
/*      */             //   #2544	-> 927
/*      */             //   #2546	-> 941
/*      */             //   #2547	-> 954
/*      */             //   #2549	-> 967
/*      */             //   #2553	-> 1023
/*      */             //   #2555	-> 1036
/*      */             //   #2559	-> 1042
/*      */             //   #2560	-> 1047
/*      */             //   #2563	-> 1066
/*      */             //   #2565	-> 1077
/*      */             //   #2568	-> 1091
/*      */             //   #2569	-> 1096
/*      */             //   #2572	-> 1112
/*      */             //   #2578	-> 1127
/*      */             //   #2581	-> 1144
/*      */             //   #2582	-> 1150
/*      */             //   #2583	-> 1156
/*      */             //   #2584	-> 1162
/*      */             //   #2586	-> 1168
/*      */             //   #2588	-> 1183
/*      */             //   #2590	-> 1194
/*      */             //   #2591	-> 1199
/*      */             //   #2597	-> 1232
/*      */             //   #2598	-> 1256
/*      */             //   #2599	-> 1259
/*      */             //   #2609	-> 1262
/*      */             //   #2600	-> 1265
/*      */             //   #2602	-> 1280
/*      */             //   #2605	-> 1287
/*      */             //   #2603	-> 1290
/*      */             //   #2607	-> 1292
/*      */             //   #2610	-> 1297
/*      */             //   #2611	-> 1300
/*      */             // Local variable table:
/*      */             //   start	length	slot	name	descriptor
/*      */             //   58	7	4	tableNameFromList	Ljava/lang/String;
/*      */             //   99	5	7	sqlEx	Ljava/lang/Exception;
/*      */             //   17	91	3	tables	Ljava/sql/ResultSet;
/*      */             //   156	7	4	tableNameFromList	Ljava/lang/String;
/*      */             //   197	5	10	sqlEx	Ljava/sql/SQLException;
/*      */             //   113	93	3	tables	Ljava/sql/ResultSet;
/*      */             //   576	18	11	fullOrdColName	Ljava/lang/String;
/*      */             //   415	182	9	fullColumnQueryBuf	Ljava/lang/StringBuffer;
/*      */             //   555	42	10	fullOrdinalPos	I
/*      */             //   919	8	12	E	Ljava/lang/Exception;
/*      */             //   1077	50	12	origColName	Ljava/lang/String;
/*      */             //   1091	36	13	realOrdinal	Ljava/lang/Integer;
/*      */             //   633	623	10	rowVal	[[B
/*      */             //   710	546	11	typeDesc	Lcom/mysql/jdbc/DatabaseMetaData$TypeDescriptor;
/*      */             //   1194	62	12	extra	Ljava/lang/String;
/*      */             //   245	1014	6	queryBuf	Ljava/lang/StringBuffer;
/*      */             //   386	873	7	fixUpOrdinalsRequired	Z
/*      */             //   389	870	8	ordinalFixUpMap	Ljava/util/Map;
/*      */             //   616	643	9	ordPos	I
/*      */             //   1292	0	16	ex	Ljava/lang/Exception;
/*      */             //   231	1066	4	tableName	Ljava/lang/String;
/*      */             //   234	1063	5	results	Ljava/sql/ResultSet;
/*      */             //   0	1301	0	this	Lcom/mysql/jdbc/DatabaseMetaData$2;
/*      */             //   0	1301	1	catalogStr	Ljava/lang/Object;
/*      */             //   8	1293	2	tableNameList	Ljava/util/ArrayList;
/*      */             //   211	1090	3	tableNames	Ljava/util/Iterator;
/*      */             // Exception table:
/*      */             //   from	to	target	type
/*      */             //   17	71	74	finally
/*      */             //   74	79	74	finally
/*      */             //   88	94	97	java/lang/Exception
/*      */             //   113	169	172	finally
/*      */             //   172	177	172	finally
/*      */             //   186	192	195	java/sql/SQLException
/*      */             //   234	1262	1265	finally
/*      */             //   865	914	917	java/lang/Exception
/*      */             //   1265	1270	1265	finally
/* 2368 */             //   1280	1287	1290	java/lang/Exception } }).doForAll();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2614 */       if (stmt != null) {
/* 2615 */         stmt.close();
/*      */       }
/*      */     } 
/*      */     
/* 2619 */     ResultSet results = buildResultSet(fields, rows);
/*      */     
/* 2621 */     return results;
/*      */   }
/*      */   
/*      */   protected Field[] createColumnsFields() {
/* 2625 */     Field[] fields = new Field[23];
/* 2626 */     fields[0] = new Field("", "TABLE_CAT", 1, 255);
/* 2627 */     fields[1] = new Field("", "TABLE_SCHEM", 1, 0);
/* 2628 */     fields[2] = new Field("", "TABLE_NAME", 1, 255);
/* 2629 */     fields[3] = new Field("", "COLUMN_NAME", 1, 32);
/* 2630 */     fields[4] = new Field("", "DATA_TYPE", 4, 5);
/* 2631 */     fields[5] = new Field("", "TYPE_NAME", 1, 16);
/* 2632 */     fields[6] = new Field("", "COLUMN_SIZE", 4, Integer.toString(2147483647).length());
/*      */     
/* 2634 */     fields[7] = new Field("", "BUFFER_LENGTH", 4, 10);
/* 2635 */     fields[8] = new Field("", "DECIMAL_DIGITS", 4, 10);
/* 2636 */     fields[9] = new Field("", "NUM_PREC_RADIX", 4, 10);
/* 2637 */     fields[10] = new Field("", "NULLABLE", 4, 10);
/* 2638 */     fields[11] = new Field("", "REMARKS", 1, 0);
/* 2639 */     fields[12] = new Field("", "COLUMN_DEF", 1, 0);
/* 2640 */     fields[13] = new Field("", "SQL_DATA_TYPE", 4, 10);
/* 2641 */     fields[14] = new Field("", "SQL_DATETIME_SUB", 4, 10);
/* 2642 */     fields[15] = new Field("", "CHAR_OCTET_LENGTH", 4, Integer.toString(2147483647).length());
/*      */     
/* 2644 */     fields[16] = new Field("", "ORDINAL_POSITION", 4, 10);
/* 2645 */     fields[17] = new Field("", "IS_NULLABLE", 1, 3);
/* 2646 */     fields[18] = new Field("", "SCOPE_CATALOG", 1, 255);
/* 2647 */     fields[19] = new Field("", "SCOPE_SCHEMA", 1, 255);
/* 2648 */     fields[20] = new Field("", "SCOPE_TABLE", 1, 255);
/* 2649 */     fields[21] = new Field("", "SOURCE_DATA_TYPE", 5, 10);
/* 2650 */     fields[22] = new Field("", "IS_AUTOINCREMENT", 1, 3);
/* 2651 */     return fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Connection getConnection() throws SQLException {
/* 2662 */     return this.conn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getCrossReference(final String primaryCatalog, final String primarySchema, final String primaryTable, final String foreignCatalog, final String foreignSchema, final String foreignTable) throws SQLException {
/* 2736 */     if (primaryTable == null) {
/* 2737 */       throw SQLError.createSQLException("Table not specified.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 2741 */     Field[] fields = createFkMetadataFields();
/*      */     
/* 2743 */     final ArrayList tuples = new ArrayList();
/*      */     
/* 2745 */     if (this.conn.versionMeetsMinimum(3, 23, 0)) {
/*      */       
/* 2747 */       final Statement stmt = this.conn.getMetadataSafeStatement();
/*      */ 
/*      */       
/*      */       try {
/* 2751 */         (new IterateBlock(getCatalogIterator(foreignCatalog))
/*      */           {
/*      */             void forEach(Object catalogStr) throws SQLException {
/* 2754 */               ResultSet fkresults = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               try {
/* 2761 */                 if (DatabaseMetaData.this.conn.versionMeetsMinimum(3, 23, 50)) {
/* 2762 */                   fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr.toString(), null);
/*      */                 } else {
/*      */                   
/* 2765 */                   StringBuffer queryBuf = new StringBuffer("SHOW TABLE STATUS FROM ");
/*      */                   
/* 2767 */                   queryBuf.append(DatabaseMetaData.this.quotedId);
/* 2768 */                   queryBuf.append(catalogStr.toString());
/* 2769 */                   queryBuf.append(DatabaseMetaData.this.quotedId);
/*      */                   
/* 2771 */                   fkresults = stmt.executeQuery(queryBuf.toString());
/*      */                 } 
/*      */ 
/*      */                 
/* 2775 */                 String foreignTableWithCase = DatabaseMetaData.this.getTableNameWithCase(foreignTable);
/* 2776 */                 String primaryTableWithCase = DatabaseMetaData.this.getTableNameWithCase(primaryTable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2784 */                 while (fkresults.next()) {
/* 2785 */                   String tableType = fkresults.getString("Type");
/*      */                   
/* 2787 */                   if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK"))) {
/*      */ 
/*      */ 
/*      */                     
/* 2791 */                     String comment = fkresults.getString("Comment").trim();
/*      */ 
/*      */                     
/* 2794 */                     if (comment != null) {
/* 2795 */                       StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
/*      */ 
/*      */                       
/* 2798 */                       if (commentTokens.hasMoreTokens()) {
/* 2799 */                         String str = commentTokens.nextToken();
/*      */                       }
/*      */ 
/*      */ 
/*      */                       
/* 2804 */                       while (commentTokens.hasMoreTokens()) {
/* 2805 */                         String keys = commentTokens.nextToken();
/*      */                         
/* 2807 */                         DatabaseMetaData.LocalAndReferencedColumns parsedInfo = DatabaseMetaData.this.parseTableStatusIntoLocalAndReferencedColumns(keys);
/*      */                         
/* 2809 */                         int keySeq = 0;
/*      */                         
/* 2811 */                         Iterator<E> referencingColumns = parsedInfo.localColumnsList.iterator();
/*      */                         
/* 2813 */                         Iterator<E> referencedColumns = parsedInfo.referencedColumnsList.iterator();
/*      */ 
/*      */                         
/* 2816 */                         while (referencingColumns.hasNext()) {
/* 2817 */                           String referencingColumn = DatabaseMetaData.this.removeQuotedId(referencingColumns.next().toString());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2823 */                           byte[][] tuple = new byte[14][];
/* 2824 */                           tuple[4] = (foreignCatalog == null) ? null : DatabaseMetaData.this.s2b(foreignCatalog);
/*      */                           
/* 2826 */                           tuple[5] = (foreignSchema == null) ? null : DatabaseMetaData.this.s2b(foreignSchema);
/*      */                           
/* 2828 */                           String dummy = fkresults.getString("Name");
/*      */ 
/*      */                           
/* 2831 */                           if (dummy.compareTo(foreignTableWithCase) != 0) {
/*      */                             continue;
/*      */                           }
/*      */ 
/*      */                           
/* 2836 */                           tuple[6] = DatabaseMetaData.this.s2b(dummy);
/*      */                           
/* 2838 */                           tuple[7] = DatabaseMetaData.this.s2b(referencingColumn);
/* 2839 */                           tuple[0] = (primaryCatalog == null) ? null : DatabaseMetaData.this.s2b(primaryCatalog);
/*      */                           
/* 2841 */                           tuple[1] = (primarySchema == null) ? null : DatabaseMetaData.this.s2b(primarySchema);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2847 */                           if (parsedInfo.referencedTable.compareTo(primaryTableWithCase) != 0) {
/*      */                             continue;
/*      */                           }
/*      */ 
/*      */                           
/* 2852 */                           tuple[2] = DatabaseMetaData.this.s2b(parsedInfo.referencedTable);
/* 2853 */                           tuple[3] = DatabaseMetaData.this.s2b(DatabaseMetaData.this.removeQuotedId(referencedColumns.next().toString()));
/*      */                           
/* 2855 */                           tuple[8] = Integer.toString(keySeq).getBytes();
/*      */ 
/*      */                           
/* 2858 */                           int[] actions = DatabaseMetaData.this.getForeignKeyActions(keys);
/*      */                           
/* 2860 */                           tuple[9] = Integer.toString(actions[1]).getBytes();
/*      */                           
/* 2862 */                           tuple[10] = Integer.toString(actions[0]).getBytes();
/*      */                           
/* 2864 */                           tuple[11] = null;
/* 2865 */                           tuple[12] = null;
/* 2866 */                           tuple[13] = Integer.toString(7).getBytes();
/*      */ 
/*      */ 
/*      */                           
/* 2870 */                           tuples.add(new ByteArrayRow(tuple, DatabaseMetaData.this.getExceptionInterceptor()));
/* 2871 */                           keySeq++;
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } finally {
/*      */                 
/* 2879 */                 if (fkresults != null) {
/*      */                   try {
/* 2881 */                     fkresults.close();
/* 2882 */                   } catch (Exception sqlEx) {
/* 2883 */                     AssertionFailedException.shouldNotHappen(sqlEx);
/*      */                   } 
/*      */ 
/*      */                   
/* 2887 */                   fkresults = null;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }).doForAll();
/*      */       } finally {
/* 2893 */         if (stmt != null) {
/* 2894 */           stmt.close();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2899 */     ResultSet results = buildResultSet(fields, tuples);
/*      */     
/* 2901 */     return results;
/*      */   }
/*      */   
/*      */   protected Field[] createFkMetadataFields() {
/* 2905 */     Field[] fields = new Field[14];
/* 2906 */     fields[0] = new Field("", "PKTABLE_CAT", 1, 255);
/* 2907 */     fields[1] = new Field("", "PKTABLE_SCHEM", 1, 0);
/* 2908 */     fields[2] = new Field("", "PKTABLE_NAME", 1, 255);
/* 2909 */     fields[3] = new Field("", "PKCOLUMN_NAME", 1, 32);
/* 2910 */     fields[4] = new Field("", "FKTABLE_CAT", 1, 255);
/* 2911 */     fields[5] = new Field("", "FKTABLE_SCHEM", 1, 0);
/* 2912 */     fields[6] = new Field("", "FKTABLE_NAME", 1, 255);
/* 2913 */     fields[7] = new Field("", "FKCOLUMN_NAME", 1, 32);
/* 2914 */     fields[8] = new Field("", "KEY_SEQ", 5, 2);
/* 2915 */     fields[9] = new Field("", "UPDATE_RULE", 5, 2);
/* 2916 */     fields[10] = new Field("", "DELETE_RULE", 5, 2);
/* 2917 */     fields[11] = new Field("", "FK_NAME", 1, 0);
/* 2918 */     fields[12] = new Field("", "PK_NAME", 1, 0);
/* 2919 */     fields[13] = new Field("", "DEFERRABILITY", 5, 2);
/* 2920 */     return fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDatabaseMajorVersion() throws SQLException {
/* 2927 */     return this.conn.getServerMajorVersion();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDatabaseMinorVersion() throws SQLException {
/* 2934 */     return this.conn.getServerMinorVersion();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDatabaseProductName() throws SQLException {
/* 2945 */     return "MySQL";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDatabaseProductVersion() throws SQLException {
/* 2956 */     return this.conn.getServerVersion();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultTransactionIsolation() throws SQLException {
/* 2969 */     if (this.conn.supportsIsolationLevel()) {
/* 2970 */       return 2;
/*      */     }
/*      */     
/* 2973 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDriverMajorVersion() {
/* 2982 */     return NonRegisteringDriver.getMajorVersionInternal();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDriverMinorVersion() {
/* 2991 */     return NonRegisteringDriver.getMinorVersionInternal();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDriverName() throws SQLException {
/* 3002 */     return "MySQL-AB JDBC Driver";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDriverVersion() throws SQLException {
/* 3013 */     return "mysql-connector-java-5.1.14 ( Revision: ${bzr.revision-id} )";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getExportedKeys(String catalog, String schema, final String table) throws SQLException {
/* 3077 */     if (table == null) {
/* 3078 */       throw SQLError.createSQLException("Table not specified.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 3082 */     Field[] fields = createFkMetadataFields();
/*      */     
/* 3084 */     final ArrayList rows = new ArrayList();
/*      */     
/* 3086 */     if (this.conn.versionMeetsMinimum(3, 23, 0)) {
/*      */       
/* 3088 */       final Statement stmt = this.conn.getMetadataSafeStatement();
/*      */ 
/*      */       
/*      */       try {
/* 3092 */         (new IterateBlock(getCatalogIterator(catalog)) {
/*      */             void forEach(Object catalogStr) throws SQLException {
/* 3094 */               ResultSet fkresults = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               try {
/* 3101 */                 if (DatabaseMetaData.this.conn.versionMeetsMinimum(3, 23, 50)) {
/*      */ 
/*      */                   
/* 3104 */                   fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr.toString(), null);
/*      */                 } else {
/*      */                   
/* 3107 */                   StringBuffer queryBuf = new StringBuffer("SHOW TABLE STATUS FROM ");
/*      */                   
/* 3109 */                   queryBuf.append(DatabaseMetaData.this.quotedId);
/* 3110 */                   queryBuf.append(catalogStr.toString());
/* 3111 */                   queryBuf.append(DatabaseMetaData.this.quotedId);
/*      */                   
/* 3113 */                   fkresults = stmt.executeQuery(queryBuf.toString());
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/* 3118 */                 String tableNameWithCase = DatabaseMetaData.this.getTableNameWithCase(table);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 3124 */                 while (fkresults.next()) {
/* 3125 */                   String tableType = fkresults.getString("Type");
/*      */                   
/* 3127 */                   if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK")))
/*      */                   {
/*      */ 
/*      */                     
/* 3131 */                     String comment = fkresults.getString("Comment").trim();
/*      */ 
/*      */                     
/* 3134 */                     if (comment != null) {
/* 3135 */                       StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
/*      */ 
/*      */                       
/* 3138 */                       if (commentTokens.hasMoreTokens()) {
/* 3139 */                         commentTokens.nextToken();
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3144 */                         while (commentTokens.hasMoreTokens()) {
/* 3145 */                           String keys = commentTokens.nextToken();
/*      */                           
/* 3147 */                           DatabaseMetaData.this.getExportKeyResults(catalogStr.toString(), tableNameWithCase, keys, rows, fkresults.getString("Name"));
/*      */                         }
/*      */                       
/*      */                       }
/*      */                     
/*      */                     }
/*      */                   
/*      */                   }
/*      */                 
/*      */                 }
/*      */               
/*      */               }
/*      */               finally {
/*      */                 
/* 3161 */                 if (fkresults != null) {
/*      */                   try {
/* 3163 */                     fkresults.close();
/* 3164 */                   } catch (SQLException sqlEx) {
/* 3165 */                     AssertionFailedException.shouldNotHappen(sqlEx);
/*      */                   } 
/*      */ 
/*      */                   
/* 3169 */                   fkresults = null;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }).doForAll();
/*      */       } finally {
/* 3175 */         if (stmt != null) {
/* 3176 */           stmt.close();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3181 */     ResultSet results = buildResultSet(fields, rows);
/*      */     
/* 3183 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getExportKeyResults(String catalog, String exportingTable, String keysComment, List tuples, String fkTableName) throws SQLException {
/* 3207 */     getResultsImpl(catalog, exportingTable, keysComment, tuples, fkTableName, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getExtraNameCharacters() throws SQLException {
/* 3220 */     return "#@";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] getForeignKeyActions(String commentString) {
/* 3233 */     int[] actions = { 3, 3 };
/*      */ 
/*      */ 
/*      */     
/* 3237 */     int lastParenIndex = commentString.lastIndexOf(")");
/*      */     
/* 3239 */     if (lastParenIndex != commentString.length() - 1) {
/* 3240 */       String cascadeOptions = commentString.substring(lastParenIndex + 1).trim().toUpperCase(Locale.ENGLISH);
/*      */ 
/*      */       
/* 3243 */       actions[0] = getCascadeDeleteOption(cascadeOptions);
/* 3244 */       actions[1] = getCascadeUpdateOption(cascadeOptions);
/*      */     } 
/*      */     
/* 3247 */     return actions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getIdentifierQuoteString() throws SQLException {
/* 3260 */     if (this.conn.supportsQuotedIdentifiers()) {
/* 3261 */       if (!this.conn.useAnsiQuotedIdentifiers()) {
/* 3262 */         return "`";
/*      */       }
/*      */       
/* 3265 */       return "\"";
/*      */     } 
/*      */     
/* 3268 */     return " ";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getImportedKeys(String catalog, String schema, final String table) throws SQLException {
/* 3332 */     if (table == null) {
/* 3333 */       throw SQLError.createSQLException("Table not specified.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 3337 */     Field[] fields = createFkMetadataFields();
/*      */     
/* 3339 */     final ArrayList rows = new ArrayList();
/*      */     
/* 3341 */     if (this.conn.versionMeetsMinimum(3, 23, 0)) {
/*      */       
/* 3343 */       final Statement stmt = this.conn.getMetadataSafeStatement();
/*      */ 
/*      */       
/*      */       try {
/* 3347 */         (new IterateBlock(getCatalogIterator(catalog)) {
/*      */             void forEach(Object catalogStr) throws SQLException {
/* 3349 */               ResultSet fkresults = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               try {
/* 3356 */                 if (DatabaseMetaData.this.conn.versionMeetsMinimum(3, 23, 50)) {
/*      */ 
/*      */                   
/* 3359 */                   fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr.toString(), table);
/*      */                 } else {
/*      */                   
/* 3362 */                   StringBuffer queryBuf = new StringBuffer("SHOW TABLE STATUS ");
/*      */                   
/* 3364 */                   queryBuf.append(" FROM ");
/* 3365 */                   queryBuf.append(DatabaseMetaData.this.quotedId);
/* 3366 */                   queryBuf.append(catalogStr.toString());
/* 3367 */                   queryBuf.append(DatabaseMetaData.this.quotedId);
/* 3368 */                   queryBuf.append(" LIKE '");
/* 3369 */                   queryBuf.append(table);
/* 3370 */                   queryBuf.append("'");
/*      */                   
/* 3372 */                   fkresults = stmt.executeQuery(queryBuf.toString());
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 3380 */                 while (fkresults.next()) {
/* 3381 */                   String tableType = fkresults.getString("Type");
/*      */                   
/* 3383 */                   if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK"))) {
/*      */ 
/*      */ 
/*      */                     
/* 3387 */                     String comment = fkresults.getString("Comment").trim();
/*      */ 
/*      */                     
/* 3390 */                     if (comment != null) {
/* 3391 */                       StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
/*      */ 
/*      */                       
/* 3394 */                       if (commentTokens.hasMoreTokens()) {
/* 3395 */                         commentTokens.nextToken();
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3400 */                         while (commentTokens.hasMoreTokens()) {
/* 3401 */                           String keys = commentTokens.nextToken();
/*      */                           
/* 3403 */                           DatabaseMetaData.this.getImportKeyResults(catalogStr.toString(), table, keys, rows);
/*      */                         }
/*      */                       
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } finally {
/*      */                 
/* 3412 */                 if (fkresults != null) {
/*      */                   try {
/* 3414 */                     fkresults.close();
/* 3415 */                   } catch (SQLException sqlEx) {
/* 3416 */                     AssertionFailedException.shouldNotHappen(sqlEx);
/*      */                   } 
/*      */ 
/*      */                   
/* 3420 */                   fkresults = null;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }).doForAll();
/*      */       } finally {
/* 3426 */         if (stmt != null) {
/* 3427 */           stmt.close();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3432 */     ResultSet results = buildResultSet(fields, rows);
/*      */     
/* 3434 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getImportKeyResults(String catalog, String importingTable, String keysComment, List tuples) throws SQLException {
/* 3456 */     getResultsImpl(catalog, importingTable, keysComment, tuples, null, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getIndexInfo(String catalog, String schema, final String table, final boolean unique, boolean approximate) throws SQLException {
/* 3527 */     Field[] fields = createIndexInfoFields();
/*      */     
/* 3529 */     final ArrayList rows = new ArrayList();
/* 3530 */     final Statement stmt = this.conn.getMetadataSafeStatement();
/*      */ 
/*      */     
/*      */     try {
/* 3534 */       (new IterateBlock(getCatalogIterator(catalog))
/*      */         {
/*      */           void forEach(Object catalogStr) throws SQLException {
/* 3537 */             ResultSet results = null;
/*      */             
/*      */             try {
/* 3540 */               StringBuffer queryBuf = new StringBuffer("SHOW INDEX FROM ");
/*      */               
/* 3542 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/* 3543 */               queryBuf.append(table);
/* 3544 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/* 3545 */               queryBuf.append(" FROM ");
/* 3546 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/* 3547 */               queryBuf.append(catalogStr.toString());
/* 3548 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/*      */               
/*      */               try {
/* 3551 */                 results = stmt.executeQuery(queryBuf.toString());
/* 3552 */               } catch (SQLException sqlEx) {
/* 3553 */                 int errorCode = sqlEx.getErrorCode();
/*      */ 
/*      */ 
/*      */                 
/* 3557 */                 if (!"42S02".equals(sqlEx.getSQLState()))
/*      */                 {
/*      */                   
/* 3560 */                   if (errorCode != 1146) {
/* 3561 */                     throw sqlEx;
/*      */                   }
/*      */                 }
/*      */               } 
/*      */               
/* 3566 */               while (results != null && results.next()) {
/* 3567 */                 byte[][] row = new byte[14][];
/* 3568 */                 row[0] = (catalogStr.toString() == null) ? new byte[0] : DatabaseMetaData.this.s2b(catalogStr.toString());
/*      */ 
/*      */                 
/* 3571 */                 row[1] = null;
/* 3572 */                 row[2] = results.getBytes("Table");
/*      */                 
/* 3574 */                 boolean indexIsUnique = (results.getInt("Non_unique") == 0);
/*      */ 
/*      */                 
/* 3577 */                 row[3] = !indexIsUnique ? DatabaseMetaData.this.s2b("true") : DatabaseMetaData.this.s2b("false");
/*      */                 
/* 3579 */                 row[4] = new byte[0];
/* 3580 */                 row[5] = results.getBytes("Key_name");
/* 3581 */                 row[6] = Integer.toString(3).getBytes();
/*      */ 
/*      */                 
/* 3584 */                 row[7] = results.getBytes("Seq_in_index");
/* 3585 */                 row[8] = results.getBytes("Column_name");
/* 3586 */                 row[9] = results.getBytes("Collation");
/*      */ 
/*      */ 
/*      */                 
/* 3590 */                 long cardinality = results.getLong("Cardinality");
/*      */                 
/* 3592 */                 if (cardinality > 2147483647L) {
/* 3593 */                   cardinality = 2147483647L;
/*      */                 }
/*      */                 
/* 3596 */                 row[10] = DatabaseMetaData.this.s2b(String.valueOf(cardinality));
/* 3597 */                 row[11] = DatabaseMetaData.this.s2b("0");
/* 3598 */                 row[12] = null;
/*      */                 
/* 3600 */                 if (unique) {
/* 3601 */                   if (indexIsUnique) {
/* 3602 */                     rows.add(new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
/*      */                   }
/*      */                   continue;
/*      */                 } 
/* 3606 */                 rows.add(new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
/*      */               } 
/*      */             } finally {
/*      */               
/* 3610 */               if (results != null) {
/*      */                 try {
/* 3612 */                   results.close();
/* 3613 */                 } catch (Exception ex) {}
/*      */ 
/*      */ 
/*      */                 
/* 3617 */                 results = null;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         }).doForAll();
/*      */       
/* 3623 */       ResultSet indexInfo = buildResultSet(fields, rows);
/*      */       
/* 3625 */       return indexInfo;
/*      */     } finally {
/* 3627 */       if (stmt != null) {
/* 3628 */         stmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Field[] createIndexInfoFields() {
/* 3634 */     Field[] fields = new Field[13];
/* 3635 */     fields[0] = new Field("", "TABLE_CAT", 1, 255);
/* 3636 */     fields[1] = new Field("", "TABLE_SCHEM", 1, 0);
/* 3637 */     fields[2] = new Field("", "TABLE_NAME", 1, 255);
/* 3638 */     fields[3] = new Field("", "NON_UNIQUE", 16, 4);
/* 3639 */     fields[4] = new Field("", "INDEX_QUALIFIER", 1, 1);
/* 3640 */     fields[5] = new Field("", "INDEX_NAME", 1, 32);
/* 3641 */     fields[6] = new Field("", "TYPE", 5, 32);
/* 3642 */     fields[7] = new Field("", "ORDINAL_POSITION", 5, 5);
/* 3643 */     fields[8] = new Field("", "COLUMN_NAME", 1, 32);
/* 3644 */     fields[9] = new Field("", "ASC_OR_DESC", 1, 1);
/* 3645 */     fields[10] = new Field("", "CARDINALITY", 4, 20);
/* 3646 */     fields[11] = new Field("", "PAGES", 4, 10);
/* 3647 */     fields[12] = new Field("", "FILTER_CONDITION", 1, 32);
/* 3648 */     return fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getJDBCMajorVersion() throws SQLException {
/* 3655 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getJDBCMinorVersion() throws SQLException {
/* 3662 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxBinaryLiteralLength() throws SQLException {
/* 3673 */     return 16777208;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxCatalogNameLength() throws SQLException {
/* 3684 */     return 32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxCharLiteralLength() throws SQLException {
/* 3695 */     return 16777208;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnNameLength() throws SQLException {
/* 3706 */     return 64;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnsInGroupBy() throws SQLException {
/* 3717 */     return 64;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnsInIndex() throws SQLException {
/* 3728 */     return 16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnsInOrderBy() throws SQLException {
/* 3739 */     return 64;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnsInSelect() throws SQLException {
/* 3750 */     return 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnsInTable() throws SQLException {
/* 3761 */     return 512;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxConnections() throws SQLException {
/* 3772 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxCursorNameLength() throws SQLException {
/* 3783 */     return 64;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxIndexLength() throws SQLException {
/* 3794 */     return 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxProcedureNameLength() throws SQLException {
/* 3805 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxRowSize() throws SQLException {
/* 3816 */     return 2147483639;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSchemaNameLength() throws SQLException {
/* 3827 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxStatementLength() throws SQLException {
/* 3838 */     return MysqlIO.getMaxBuf() - 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxStatements() throws SQLException {
/* 3849 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxTableNameLength() throws SQLException {
/* 3860 */     return 64;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxTablesInSelect() throws SQLException {
/* 3871 */     return 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxUserNameLength() throws SQLException {
/* 3882 */     return 16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNumericFunctions() throws SQLException {
/* 3893 */     return "ABS,ACOS,ASIN,ATAN,ATAN2,BIT_COUNT,CEILING,COS,COT,DEGREES,EXP,FLOOR,LOG,LOG10,MAX,MIN,MOD,PI,POW,POWER,RADIANS,RAND,ROUND,SIN,SQRT,TAN,TRUNCATE";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getPrimaryKeys(String catalog, String schema, final String table) throws SQLException {
/* 3925 */     Field[] fields = new Field[6];
/* 3926 */     fields[0] = new Field("", "TABLE_CAT", 1, 255);
/* 3927 */     fields[1] = new Field("", "TABLE_SCHEM", 1, 0);
/* 3928 */     fields[2] = new Field("", "TABLE_NAME", 1, 255);
/* 3929 */     fields[3] = new Field("", "COLUMN_NAME", 1, 32);
/* 3930 */     fields[4] = new Field("", "KEY_SEQ", 5, 5);
/* 3931 */     fields[5] = new Field("", "PK_NAME", 1, 32);
/*      */     
/* 3933 */     if (table == null) {
/* 3934 */       throw SQLError.createSQLException("Table not specified.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 3938 */     final ArrayList rows = new ArrayList();
/* 3939 */     final Statement stmt = this.conn.getMetadataSafeStatement();
/*      */ 
/*      */     
/*      */     try {
/* 3943 */       (new IterateBlock(getCatalogIterator(catalog)) {
/*      */           void forEach(Object catalogStr) throws SQLException {
/* 3945 */             ResultSet rs = null;
/*      */ 
/*      */             
/*      */             try {
/* 3949 */               StringBuffer queryBuf = new StringBuffer("SHOW KEYS FROM ");
/*      */               
/* 3951 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/* 3952 */               queryBuf.append(table);
/* 3953 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/* 3954 */               queryBuf.append(" FROM ");
/* 3955 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/* 3956 */               queryBuf.append(catalogStr.toString());
/* 3957 */               queryBuf.append(DatabaseMetaData.this.quotedId);
/*      */               
/* 3959 */               rs = stmt.executeQuery(queryBuf.toString());
/*      */               
/* 3961 */               TreeMap<Object, Object> sortMap = new TreeMap<Object, Object>();
/*      */               
/* 3963 */               while (rs.next()) {
/* 3964 */                 String keyType = rs.getString("Key_name");
/*      */                 
/* 3966 */                 if (keyType != null && (
/* 3967 */                   keyType.equalsIgnoreCase("PRIMARY") || keyType.equalsIgnoreCase("PRI"))) {
/*      */                   
/* 3969 */                   byte[][] tuple = new byte[6][];
/* 3970 */                   tuple[0] = (catalogStr.toString() == null) ? new byte[0] : DatabaseMetaData.this.s2b(catalogStr.toString());
/*      */                   
/* 3972 */                   tuple[1] = null;
/* 3973 */                   tuple[2] = DatabaseMetaData.this.s2b(table);
/*      */                   
/* 3975 */                   String columnName = rs.getString("Column_name");
/*      */                   
/* 3977 */                   tuple[3] = DatabaseMetaData.this.s2b(columnName);
/* 3978 */                   tuple[4] = DatabaseMetaData.this.s2b(rs.getString("Seq_in_index"));
/* 3979 */                   tuple[5] = DatabaseMetaData.this.s2b(keyType);
/* 3980 */                   sortMap.put(columnName, tuple);
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/* 3986 */               Iterator<byte[][]> sortedIterator = sortMap.values().iterator();
/*      */               
/* 3988 */               while (sortedIterator.hasNext()) {
/* 3989 */                 rows.add(new ByteArrayRow(sortedIterator.next(), DatabaseMetaData.this.getExceptionInterceptor()));
/*      */               }
/*      */             } finally {
/*      */               
/* 3993 */               if (rs != null) {
/*      */                 try {
/* 3995 */                   rs.close();
/* 3996 */                 } catch (Exception ex) {}
/*      */ 
/*      */ 
/*      */                 
/* 4000 */                 rs = null;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         }).doForAll();
/*      */     } finally {
/* 4006 */       if (stmt != null) {
/* 4007 */         stmt.close();
/*      */       }
/*      */     } 
/*      */     
/* 4011 */     ResultSet results = buildResultSet(fields, rows);
/*      */     
/* 4013 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
/* 4085 */     Field[] fields = createProcedureColumnsFields();
/*      */     
/* 4087 */     return getProcedureOrFunctionColumns(fields, catalog, schemaPattern, procedureNamePattern, columnNamePattern, true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Field[] createProcedureColumnsFields() {
/* 4094 */     Field[] fields = new Field[13];
/*      */     
/* 4096 */     fields[0] = new Field("", "PROCEDURE_CAT", 1, 512);
/* 4097 */     fields[1] = new Field("", "PROCEDURE_SCHEM", 1, 512);
/* 4098 */     fields[2] = new Field("", "PROCEDURE_NAME", 1, 512);
/* 4099 */     fields[3] = new Field("", "COLUMN_NAME", 1, 512);
/* 4100 */     fields[4] = new Field("", "COLUMN_TYPE", 1, 64);
/* 4101 */     fields[5] = new Field("", "DATA_TYPE", 5, 6);
/* 4102 */     fields[6] = new Field("", "TYPE_NAME", 1, 64);
/* 4103 */     fields[7] = new Field("", "PRECISION", 4, 12);
/* 4104 */     fields[8] = new Field("", "LENGTH", 4, 12);
/* 4105 */     fields[9] = new Field("", "SCALE", 5, 12);
/* 4106 */     fields[10] = new Field("", "RADIX", 5, 6);
/* 4107 */     fields[11] = new Field("", "NULLABLE", 5, 6);
/* 4108 */     fields[12] = new Field("", "REMARKS", 1, 512);
/* 4109 */     return fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResultSet getProcedureOrFunctionColumns(Field[] fields, String catalog, String schemaPattern, String procedureOrFunctionNamePattern, String columnNamePattern, boolean returnProcedures, boolean returnFunctions) throws SQLException {
/* 4118 */     List<String> proceduresToExtractList = new ArrayList();
/*      */     
/* 4120 */     ResultSet procedureNameRs = null;
/*      */     
/* 4122 */     if (supportsStoredProcedures()) {
/*      */       
/*      */       try {
/*      */         
/* 4126 */         String tmpProcedureOrFunctionNamePattern = null;
/*      */         
/* 4128 */         if (procedureOrFunctionNamePattern != null && procedureOrFunctionNamePattern != "%") {
/* 4129 */           tmpProcedureOrFunctionNamePattern = StringUtils.sanitizeProcOrFuncName(procedureOrFunctionNamePattern);
/*      */         }
/*      */ 
/*      */         
/* 4133 */         if (tmpProcedureOrFunctionNamePattern == null) {
/* 4134 */           tmpProcedureOrFunctionNamePattern = procedureOrFunctionNamePattern;
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 4139 */           String tmpCatalog = catalog;
/* 4140 */           List<String> parseList = StringUtils.splitDBdotName(tmpProcedureOrFunctionNamePattern, tmpCatalog, this.quotedId, this.conn.isNoBackslashEscapesSet());
/*      */ 
/*      */ 
/*      */           
/* 4144 */           if (parseList.size() == 2) {
/* 4145 */             tmpCatalog = parseList.get(0);
/* 4146 */             tmpProcedureOrFunctionNamePattern = parseList.get(1);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 4152 */         procedureNameRs = getProceduresAndOrFunctions(createFieldMetadataForGetProcedures(), catalog, schemaPattern, tmpProcedureOrFunctionNamePattern, returnProcedures, returnFunctions);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4164 */         String tmpstrPNameRs = null;
/* 4165 */         String tmpstrCatNameRs = null;
/*      */         
/* 4167 */         boolean hasResults = false;
/* 4168 */         while (procedureNameRs.next()) {
/* 4169 */           tmpstrCatNameRs = procedureNameRs.getString(1);
/* 4170 */           tmpstrPNameRs = procedureNameRs.getString(3);
/*      */           
/* 4172 */           if ((!tmpstrCatNameRs.startsWith(this.quotedId) || !tmpstrCatNameRs.endsWith(this.quotedId)) && (!tmpstrCatNameRs.startsWith("\"") || !tmpstrCatNameRs.endsWith("\"")))
/*      */           {
/* 4174 */             tmpstrCatNameRs = this.quotedId + tmpstrCatNameRs + this.quotedId;
/*      */           }
/* 4176 */           if ((!tmpstrPNameRs.startsWith(this.quotedId) || !tmpstrPNameRs.endsWith(this.quotedId)) && (!tmpstrPNameRs.startsWith("\"") || !tmpstrPNameRs.endsWith("\"")))
/*      */           {
/* 4178 */             tmpstrPNameRs = this.quotedId + tmpstrPNameRs + this.quotedId;
/*      */           }
/*      */           
/* 4181 */           if (proceduresToExtractList.indexOf(tmpstrCatNameRs + "." + tmpstrPNameRs) < 0) {
/* 4182 */             proceduresToExtractList.add(tmpstrCatNameRs + "." + tmpstrPNameRs);
/*      */           }
/* 4184 */           hasResults = true;
/*      */         } 
/*      */ 
/*      */         
/* 4188 */         if (hasResults)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4195 */           Collections.sort(proceduresToExtractList);
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/* 4204 */         SQLException rethrowSqlEx = null;
/*      */         
/* 4206 */         if (procedureNameRs != null) {
/*      */           try {
/* 4208 */             procedureNameRs.close();
/* 4209 */           } catch (SQLException sqlEx) {
/* 4210 */             rethrowSqlEx = sqlEx;
/*      */           } 
/*      */         }
/*      */         
/* 4214 */         if (rethrowSqlEx != null) {
/* 4215 */           throw rethrowSqlEx;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 4220 */     ArrayList resultRows = new ArrayList();
/* 4221 */     int idx = 0;
/* 4222 */     String procNameToCall = "";
/*      */     
/* 4224 */     for (Iterator<String> iter = proceduresToExtractList.iterator(); iter.hasNext(); ) {
/* 4225 */       String procName = iter.next();
/*      */ 
/*      */       
/* 4228 */       if (!" ".equals(this.quotedId)) {
/* 4229 */         idx = StringUtils.indexOfIgnoreCaseRespectQuotes(0, procName, ".", this.quotedId.charAt(0), !this.conn.isNoBackslashEscapesSet());
/*      */       }
/*      */       else {
/*      */         
/* 4233 */         idx = procName.indexOf(".");
/*      */       } 
/*      */       
/* 4236 */       if (idx > 0) {
/* 4237 */         catalog = procName.substring(0, idx);
/* 4238 */         if (this.quotedId != " " && catalog.startsWith(this.quotedId) && catalog.endsWith(this.quotedId)) {
/* 4239 */           catalog = procName.substring(1, catalog.length() - 1);
/*      */         }
/* 4241 */         procNameToCall = procName;
/*      */       } else {
/*      */         
/* 4244 */         procNameToCall = procName;
/*      */       } 
/* 4246 */       getCallStmtParameterTypes(catalog, procNameToCall, columnNamePattern, resultRows, (fields.length == 17));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4251 */     return buildResultSet(fields, resultRows);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
/* 4297 */     Field[] fields = createFieldMetadataForGetProcedures();
/*      */     
/* 4299 */     return getProceduresAndOrFunctions(fields, catalog, schemaPattern, procedureNamePattern, true, true);
/*      */   }
/*      */ 
/*      */   
/*      */   private Field[] createFieldMetadataForGetProcedures() {
/* 4304 */     Field[] fields = new Field[9];
/* 4305 */     fields[0] = new Field("", "PROCEDURE_CAT", 1, 255);
/* 4306 */     fields[1] = new Field("", "PROCEDURE_SCHEM", 1, 255);
/* 4307 */     fields[2] = new Field("", "PROCEDURE_NAME", 1, 255);
/* 4308 */     fields[3] = new Field("", "reserved1", 1, 0);
/* 4309 */     fields[4] = new Field("", "reserved2", 1, 0);
/* 4310 */     fields[5] = new Field("", "reserved3", 1, 0);
/* 4311 */     fields[6] = new Field("", "REMARKS", 1, 255);
/* 4312 */     fields[7] = new Field("", "PROCEDURE_TYPE", 5, 6);
/* 4313 */     fields[8] = new Field("", "SPECIFIC_NAME", 1, 255);
/*      */     
/* 4315 */     return fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResultSet getProceduresAndOrFunctions(final Field[] fields, String catalog, String schemaPattern, String procedureNamePattern, final boolean returnProcedures, final boolean returnFunctions) throws SQLException {
/* 4325 */     if (procedureNamePattern == null || procedureNamePattern.length() == 0)
/*      */     {
/* 4327 */       if (this.conn.getNullNamePatternMatchesAll()) {
/* 4328 */         procedureNamePattern = "%";
/*      */       } else {
/* 4330 */         throw SQLError.createSQLException("Procedure name pattern can not be NULL or empty.", "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4336 */     final ArrayList procedureRows = new ArrayList();
/*      */     
/* 4338 */     if (supportsStoredProcedures()) {
/* 4339 */       final String procNamePattern = procedureNamePattern;
/*      */       
/* 4341 */       final Map<Object, Object> procedureRowsOrderedByName = new TreeMap<Object, Object>();
/*      */       
/* 4343 */       (new IterateBlock(getCatalogIterator(catalog)) {
/*      */           void forEach(Object catalogStr) throws SQLException {
/* 4345 */             String db = catalogStr.toString();
/*      */             
/* 4347 */             boolean fromSelect = false;
/* 4348 */             ResultSet proceduresRs = null;
/* 4349 */             boolean needsClientFiltering = true;
/* 4350 */             PreparedStatement proceduresStmt = DatabaseMetaData.this.conn.clientPrepareStatement("SELECT name, type, comment FROM mysql.proc WHERE name like ? and db <=> ? ORDER BY name");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/* 4359 */               boolean hasTypeColumn = false;
/*      */               
/* 4361 */               if (db != null) {
/* 4362 */                 if (DatabaseMetaData.this.conn.lowerCaseTableNames()) {
/* 4363 */                   db = db.toLowerCase();
/*      */                 }
/* 4365 */                 proceduresStmt.setString(2, db);
/*      */               } else {
/* 4367 */                 proceduresStmt.setNull(2, 12);
/*      */               } 
/*      */               
/* 4370 */               int nameIndex = 1;
/*      */               
/* 4372 */               if (proceduresStmt.getMaxRows() != 0) {
/* 4373 */                 proceduresStmt.setMaxRows(0);
/*      */               }
/*      */               
/* 4376 */               proceduresStmt.setString(1, procNamePattern);
/*      */               
/*      */               try {
/* 4379 */                 proceduresRs = proceduresStmt.executeQuery();
/* 4380 */                 fromSelect = true;
/* 4381 */                 needsClientFiltering = false;
/* 4382 */                 hasTypeColumn = true;
/* 4383 */               } catch (SQLException sqlEx) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 4390 */                 proceduresStmt.close();
/*      */                 
/* 4392 */                 fromSelect = false;
/*      */                 
/* 4394 */                 if (DatabaseMetaData.this.conn.versionMeetsMinimum(5, 0, 1)) {
/* 4395 */                   nameIndex = 2;
/*      */                 } else {
/* 4397 */                   nameIndex = 1;
/*      */                 } 
/*      */                 
/* 4400 */                 proceduresStmt = DatabaseMetaData.this.conn.clientPrepareStatement("SHOW PROCEDURE STATUS LIKE ?");
/*      */ 
/*      */                 
/* 4403 */                 if (proceduresStmt.getMaxRows() != 0) {
/* 4404 */                   proceduresStmt.setMaxRows(0);
/*      */                 }
/*      */                 
/* 4407 */                 proceduresStmt.setString(1, procNamePattern);
/*      */                 
/* 4409 */                 proceduresRs = proceduresStmt.executeQuery();
/*      */               } 
/*      */               
/* 4412 */               if (returnProcedures) {
/* 4413 */                 DatabaseMetaData.this.convertToJdbcProcedureList(fromSelect, db, proceduresRs, needsClientFiltering, db, procedureRowsOrderedByName, nameIndex);
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 4418 */               if (!hasTypeColumn) {
/*      */                 
/* 4420 */                 if (proceduresStmt != null) {
/* 4421 */                   proceduresStmt.close();
/*      */                 }
/*      */                 
/* 4424 */                 proceduresStmt = DatabaseMetaData.this.conn.clientPrepareStatement("SHOW FUNCTION STATUS LIKE ?");
/*      */ 
/*      */                 
/* 4427 */                 if (proceduresStmt.getMaxRows() != 0) {
/* 4428 */                   proceduresStmt.setMaxRows(0);
/*      */                 }
/*      */                 
/* 4431 */                 proceduresStmt.setString(1, procNamePattern);
/*      */                 
/* 4433 */                 proceduresRs = proceduresStmt.executeQuery();
/*      */               } 
/*      */ 
/*      */               
/* 4437 */               if (returnFunctions) {
/* 4438 */                 DatabaseMetaData.this.convertToJdbcFunctionList(db, proceduresRs, needsClientFiltering, db, procedureRowsOrderedByName, nameIndex, fields);
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 4446 */               Iterator proceduresIter = procedureRowsOrderedByName.values().iterator();
/*      */ 
/*      */               
/* 4449 */               while (proceduresIter.hasNext()) {
/* 4450 */                 procedureRows.add(proceduresIter.next());
/*      */               }
/*      */             } finally {
/* 4453 */               SQLException rethrowSqlEx = null;
/*      */               
/* 4455 */               if (proceduresRs != null) {
/*      */                 try {
/* 4457 */                   proceduresRs.close();
/* 4458 */                 } catch (SQLException sqlEx) {
/* 4459 */                   rethrowSqlEx = sqlEx;
/*      */                 } 
/*      */               }
/*      */               
/* 4463 */               if (proceduresStmt != null) {
/*      */                 try {
/* 4465 */                   proceduresStmt.close();
/* 4466 */                 } catch (SQLException sqlEx) {
/* 4467 */                   rethrowSqlEx = sqlEx;
/*      */                 } 
/*      */               }
/*      */               
/* 4471 */               if (rethrowSqlEx != null) {
/* 4472 */                 throw rethrowSqlEx;
/*      */               }
/*      */             } 
/*      */           }
/*      */         }).doForAll();
/*      */     } 
/*      */     
/* 4479 */     return buildResultSet(fields, procedureRows);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProcedureTerm() throws SQLException {
/* 4491 */     return "PROCEDURE";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getResultSetHoldability() throws SQLException {
/* 4498 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getResultsImpl(String catalog, String table, String keysComment, List<ByteArrayRow> tuples, String fkTableName, boolean isExport) throws SQLException {
/* 4505 */     LocalAndReferencedColumns parsedInfo = parseTableStatusIntoLocalAndReferencedColumns(keysComment);
/*      */     
/* 4507 */     if (isExport && !parsedInfo.referencedTable.equals(table)) {
/*      */       return;
/*      */     }
/*      */     
/* 4511 */     if (parsedInfo.localColumnsList.size() != parsedInfo.referencedColumnsList.size())
/*      */     {
/* 4513 */       throw SQLError.createSQLException("Error parsing foreign keys definition,number of local and referenced columns is not the same.", "S1000", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4519 */     Iterator<E> localColumnNames = parsedInfo.localColumnsList.iterator();
/* 4520 */     Iterator<E> referColumnNames = parsedInfo.referencedColumnsList.iterator();
/*      */     
/* 4522 */     int keySeqIndex = 1;
/*      */     
/* 4524 */     while (localColumnNames.hasNext()) {
/* 4525 */       byte[][] tuple = new byte[14][];
/* 4526 */       String lColumnName = removeQuotedId(localColumnNames.next().toString());
/*      */       
/* 4528 */       String rColumnName = removeQuotedId(referColumnNames.next().toString());
/*      */       
/* 4530 */       tuple[4] = (catalog == null) ? new byte[0] : s2b(catalog);
/*      */       
/* 4532 */       tuple[5] = null;
/* 4533 */       tuple[6] = s2b(isExport ? fkTableName : table);
/* 4534 */       tuple[7] = s2b(lColumnName);
/* 4535 */       tuple[0] = s2b(parsedInfo.referencedCatalog);
/* 4536 */       tuple[1] = null;
/* 4537 */       tuple[2] = s2b(isExport ? table : parsedInfo.referencedTable);
/*      */       
/* 4539 */       tuple[3] = s2b(rColumnName);
/* 4540 */       tuple[8] = s2b(Integer.toString(keySeqIndex++));
/*      */       
/* 4542 */       int[] actions = getForeignKeyActions(keysComment);
/*      */       
/* 4544 */       tuple[9] = s2b(Integer.toString(actions[1]));
/* 4545 */       tuple[10] = s2b(Integer.toString(actions[0]));
/* 4546 */       tuple[11] = s2b(parsedInfo.constraintName);
/* 4547 */       tuple[12] = null;
/* 4548 */       tuple[13] = s2b(Integer.toString(7));
/*      */       
/* 4550 */       tuples.add(new ByteArrayRow(tuple, getExceptionInterceptor()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getSchemas() throws SQLException {
/* 4570 */     Field[] fields = new Field[2];
/* 4571 */     fields[0] = new Field("", "TABLE_SCHEM", 1, 0);
/* 4572 */     fields[1] = new Field("", "TABLE_CATALOG", 1, 0);
/*      */     
/* 4574 */     ArrayList tuples = new ArrayList();
/* 4575 */     ResultSet results = buildResultSet(fields, tuples);
/*      */     
/* 4577 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSchemaTerm() throws SQLException {
/* 4588 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSearchStringEscape() throws SQLException {
/* 4606 */     return "\\";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSQLKeywords() throws SQLException {
/* 4618 */     return mysqlKeywordsThatArentSQL92;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSQLStateType() throws SQLException {
/* 4625 */     if (this.conn.versionMeetsMinimum(4, 1, 0)) {
/* 4626 */       return 2;
/*      */     }
/*      */     
/* 4629 */     if (this.conn.getUseSqlStateCodes()) {
/* 4630 */       return 2;
/*      */     }
/*      */     
/* 4633 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStringFunctions() throws SQLException {
/* 4644 */     return "ASCII,BIN,BIT_LENGTH,CHAR,CHARACTER_LENGTH,CHAR_LENGTH,CONCAT,CONCAT_WS,CONV,ELT,EXPORT_SET,FIELD,FIND_IN_SET,HEX,INSERT,INSTR,LCASE,LEFT,LENGTH,LOAD_FILE,LOCATE,LOCATE,LOWER,LPAD,LTRIM,MAKE_SET,MATCH,MID,OCT,OCTET_LENGTH,ORD,POSITION,QUOTE,REPEAT,REPLACE,REVERSE,RIGHT,RPAD,RTRIM,SOUNDEX,SPACE,STRCMP,SUBSTRING,SUBSTRING,SUBSTRING,SUBSTRING,SUBSTRING_INDEX,TRIM,UCASE,UPPER";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getSuperTables(String arg0, String arg1, String arg2) throws SQLException {
/* 4658 */     Field[] fields = new Field[4];
/* 4659 */     fields[0] = new Field("", "TABLE_CAT", 1, 32);
/* 4660 */     fields[1] = new Field("", "TABLE_SCHEM", 1, 32);
/* 4661 */     fields[2] = new Field("", "TABLE_NAME", 1, 32);
/* 4662 */     fields[3] = new Field("", "SUPERTABLE_NAME", 1, 32);
/*      */     
/* 4664 */     return buildResultSet(fields, new ArrayList());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getSuperTypes(String arg0, String arg1, String arg2) throws SQLException {
/* 4672 */     Field[] fields = new Field[6];
/* 4673 */     fields[0] = new Field("", "TYPE_CAT", 1, 32);
/* 4674 */     fields[1] = new Field("", "TYPE_SCHEM", 1, 32);
/* 4675 */     fields[2] = new Field("", "TYPE_NAME", 1, 32);
/* 4676 */     fields[3] = new Field("", "SUPERTYPE_CAT", 1, 32);
/* 4677 */     fields[4] = new Field("", "SUPERTYPE_SCHEM", 1, 32);
/* 4678 */     fields[5] = new Field("", "SUPERTYPE_NAME", 1, 32);
/*      */     
/* 4680 */     return buildResultSet(fields, new ArrayList());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSystemFunctions() throws SQLException {
/* 4691 */     return "DATABASE,USER,SYSTEM_USER,SESSION_USER,PASSWORD,ENCRYPT,LAST_INSERT_ID,VERSION";
/*      */   }
/*      */   
/*      */   private String getTableNameWithCase(String table) {
/* 4695 */     String tableNameWithCase = this.conn.lowerCaseTableNames() ? table.toLowerCase() : table;
/*      */ 
/*      */     
/* 4698 */     return tableNameWithCase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
/*      */     // Byte code:
/*      */     //   0: aload_3
/*      */     //   1: ifnonnull -> 37
/*      */     //   4: aload_0
/*      */     //   5: getfield conn : Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   8: invokeinterface getNullNamePatternMatchesAll : ()Z
/*      */     //   13: ifeq -> 23
/*      */     //   16: ldc_w '%'
/*      */     //   19: astore_3
/*      */     //   20: goto -> 37
/*      */     //   23: ldc_w 'Table name pattern can not be NULL or empty.'
/*      */     //   26: ldc_w 'S1009'
/*      */     //   29: aload_0
/*      */     //   30: invokevirtual getExceptionInterceptor : ()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */     //   33: invokestatic createSQLException : (Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */     //   36: athrow
/*      */     //   37: bipush #7
/*      */     //   39: anewarray com/mysql/jdbc/Field
/*      */     //   42: astore #4
/*      */     //   44: aload #4
/*      */     //   46: iconst_0
/*      */     //   47: new com/mysql/jdbc/Field
/*      */     //   50: dup
/*      */     //   51: ldc ''
/*      */     //   53: ldc_w 'TABLE_CAT'
/*      */     //   56: iconst_1
/*      */     //   57: bipush #64
/*      */     //   59: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;II)V
/*      */     //   62: aastore
/*      */     //   63: aload #4
/*      */     //   65: iconst_1
/*      */     //   66: new com/mysql/jdbc/Field
/*      */     //   69: dup
/*      */     //   70: ldc ''
/*      */     //   72: ldc_w 'TABLE_SCHEM'
/*      */     //   75: iconst_1
/*      */     //   76: iconst_1
/*      */     //   77: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;II)V
/*      */     //   80: aastore
/*      */     //   81: aload #4
/*      */     //   83: iconst_2
/*      */     //   84: new com/mysql/jdbc/Field
/*      */     //   87: dup
/*      */     //   88: ldc ''
/*      */     //   90: ldc_w 'TABLE_NAME'
/*      */     //   93: iconst_1
/*      */     //   94: bipush #64
/*      */     //   96: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;II)V
/*      */     //   99: aastore
/*      */     //   100: aload #4
/*      */     //   102: iconst_3
/*      */     //   103: new com/mysql/jdbc/Field
/*      */     //   106: dup
/*      */     //   107: ldc ''
/*      */     //   109: ldc_w 'GRANTOR'
/*      */     //   112: iconst_1
/*      */     //   113: bipush #77
/*      */     //   115: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;II)V
/*      */     //   118: aastore
/*      */     //   119: aload #4
/*      */     //   121: iconst_4
/*      */     //   122: new com/mysql/jdbc/Field
/*      */     //   125: dup
/*      */     //   126: ldc ''
/*      */     //   128: ldc_w 'GRANTEE'
/*      */     //   131: iconst_1
/*      */     //   132: bipush #77
/*      */     //   134: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;II)V
/*      */     //   137: aastore
/*      */     //   138: aload #4
/*      */     //   140: iconst_5
/*      */     //   141: new com/mysql/jdbc/Field
/*      */     //   144: dup
/*      */     //   145: ldc ''
/*      */     //   147: ldc_w 'PRIVILEGE'
/*      */     //   150: iconst_1
/*      */     //   151: bipush #64
/*      */     //   153: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;II)V
/*      */     //   156: aastore
/*      */     //   157: aload #4
/*      */     //   159: bipush #6
/*      */     //   161: new com/mysql/jdbc/Field
/*      */     //   164: dup
/*      */     //   165: ldc ''
/*      */     //   167: ldc_w 'IS_GRANTABLE'
/*      */     //   170: iconst_1
/*      */     //   171: iconst_3
/*      */     //   172: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;II)V
/*      */     //   175: aastore
/*      */     //   176: new java/lang/StringBuffer
/*      */     //   179: dup
/*      */     //   180: ldc_w 'SELECT host,db,table_name,grantor,user,table_priv from mysql.tables_priv '
/*      */     //   183: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   186: astore #5
/*      */     //   188: aload #5
/*      */     //   190: ldc_w ' WHERE '
/*      */     //   193: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   196: pop
/*      */     //   197: aload_1
/*      */     //   198: ifnull -> 233
/*      */     //   201: aload_1
/*      */     //   202: invokevirtual length : ()I
/*      */     //   205: ifeq -> 233
/*      */     //   208: aload #5
/*      */     //   210: ldc_w ' db=''
/*      */     //   213: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   216: pop
/*      */     //   217: aload #5
/*      */     //   219: aload_1
/*      */     //   220: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   223: pop
/*      */     //   224: aload #5
/*      */     //   226: ldc_w '' AND '
/*      */     //   229: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   232: pop
/*      */     //   233: aload #5
/*      */     //   235: ldc_w 'table_name like ''
/*      */     //   238: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   241: pop
/*      */     //   242: aload #5
/*      */     //   244: aload_3
/*      */     //   245: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   248: pop
/*      */     //   249: aload #5
/*      */     //   251: ldc_w '''
/*      */     //   254: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   257: pop
/*      */     //   258: aconst_null
/*      */     //   259: astore #6
/*      */     //   261: new java/util/ArrayList
/*      */     //   264: dup
/*      */     //   265: invokespecial <init> : ()V
/*      */     //   268: astore #7
/*      */     //   270: aconst_null
/*      */     //   271: astore #8
/*      */     //   273: aload_0
/*      */     //   274: getfield conn : Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   277: invokeinterface createStatement : ()Ljava/sql/Statement;
/*      */     //   282: astore #8
/*      */     //   284: aload #8
/*      */     //   286: iconst_0
/*      */     //   287: invokeinterface setEscapeProcessing : (Z)V
/*      */     //   292: aload #8
/*      */     //   294: aload #5
/*      */     //   296: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   299: invokeinterface executeQuery : (Ljava/lang/String;)Ljava/sql/ResultSet;
/*      */     //   304: astore #6
/*      */     //   306: aload #6
/*      */     //   308: invokeinterface next : ()Z
/*      */     //   313: ifeq -> 660
/*      */     //   316: aload #6
/*      */     //   318: iconst_1
/*      */     //   319: invokeinterface getString : (I)Ljava/lang/String;
/*      */     //   324: astore #9
/*      */     //   326: aload #6
/*      */     //   328: iconst_2
/*      */     //   329: invokeinterface getString : (I)Ljava/lang/String;
/*      */     //   334: astore #10
/*      */     //   336: aload #6
/*      */     //   338: iconst_3
/*      */     //   339: invokeinterface getString : (I)Ljava/lang/String;
/*      */     //   344: astore #11
/*      */     //   346: aload #6
/*      */     //   348: iconst_4
/*      */     //   349: invokeinterface getString : (I)Ljava/lang/String;
/*      */     //   354: astore #12
/*      */     //   356: aload #6
/*      */     //   358: iconst_5
/*      */     //   359: invokeinterface getString : (I)Ljava/lang/String;
/*      */     //   364: astore #13
/*      */     //   366: aload #13
/*      */     //   368: ifnull -> 379
/*      */     //   371: aload #13
/*      */     //   373: invokevirtual length : ()I
/*      */     //   376: ifne -> 384
/*      */     //   379: ldc_w '%'
/*      */     //   382: astore #13
/*      */     //   384: new java/lang/StringBuffer
/*      */     //   387: dup
/*      */     //   388: aload #13
/*      */     //   390: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   393: astore #14
/*      */     //   395: aload #9
/*      */     //   397: ifnull -> 429
/*      */     //   400: aload_0
/*      */     //   401: getfield conn : Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   404: invokeinterface getUseHostsInPrivileges : ()Z
/*      */     //   409: ifeq -> 429
/*      */     //   412: aload #14
/*      */     //   414: ldc_w '@'
/*      */     //   417: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   420: pop
/*      */     //   421: aload #14
/*      */     //   423: aload #9
/*      */     //   425: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   428: pop
/*      */     //   429: aload #6
/*      */     //   431: bipush #6
/*      */     //   433: invokeinterface getString : (I)Ljava/lang/String;
/*      */     //   438: astore #15
/*      */     //   440: aload #15
/*      */     //   442: ifnull -> 657
/*      */     //   445: aload #15
/*      */     //   447: getstatic java/util/Locale.ENGLISH : Ljava/util/Locale;
/*      */     //   450: invokevirtual toUpperCase : (Ljava/util/Locale;)Ljava/lang/String;
/*      */     //   453: astore #15
/*      */     //   455: new java/util/StringTokenizer
/*      */     //   458: dup
/*      */     //   459: aload #15
/*      */     //   461: ldc_w ','
/*      */     //   464: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   467: astore #16
/*      */     //   469: aload #16
/*      */     //   471: invokevirtual hasMoreTokens : ()Z
/*      */     //   474: ifeq -> 657
/*      */     //   477: aload #16
/*      */     //   479: invokevirtual nextToken : ()Ljava/lang/String;
/*      */     //   482: invokevirtual trim : ()Ljava/lang/String;
/*      */     //   485: astore #17
/*      */     //   487: aconst_null
/*      */     //   488: astore #18
/*      */     //   490: aload_0
/*      */     //   491: aload_1
/*      */     //   492: aload_2
/*      */     //   493: aload #11
/*      */     //   495: ldc_w '%'
/*      */     //   498: invokevirtual getColumns : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/sql/ResultSet;
/*      */     //   501: astore #18
/*      */     //   503: aload #18
/*      */     //   505: invokeinterface next : ()Z
/*      */     //   510: ifeq -> 619
/*      */     //   513: bipush #8
/*      */     //   515: anewarray [B
/*      */     //   518: astore #19
/*      */     //   520: aload #19
/*      */     //   522: iconst_0
/*      */     //   523: aload_0
/*      */     //   524: aload #10
/*      */     //   526: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */     //   529: aastore
/*      */     //   530: aload #19
/*      */     //   532: iconst_1
/*      */     //   533: aconst_null
/*      */     //   534: aastore
/*      */     //   535: aload #19
/*      */     //   537: iconst_2
/*      */     //   538: aload_0
/*      */     //   539: aload #11
/*      */     //   541: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */     //   544: aastore
/*      */     //   545: aload #12
/*      */     //   547: ifnull -> 563
/*      */     //   550: aload #19
/*      */     //   552: iconst_3
/*      */     //   553: aload_0
/*      */     //   554: aload #12
/*      */     //   556: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */     //   559: aastore
/*      */     //   560: goto -> 568
/*      */     //   563: aload #19
/*      */     //   565: iconst_3
/*      */     //   566: aconst_null
/*      */     //   567: aastore
/*      */     //   568: aload #19
/*      */     //   570: iconst_4
/*      */     //   571: aload_0
/*      */     //   572: aload #14
/*      */     //   574: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   577: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */     //   580: aastore
/*      */     //   581: aload #19
/*      */     //   583: iconst_5
/*      */     //   584: aload_0
/*      */     //   585: aload #17
/*      */     //   587: invokevirtual s2b : (Ljava/lang/String;)[B
/*      */     //   590: aastore
/*      */     //   591: aload #19
/*      */     //   593: bipush #6
/*      */     //   595: aconst_null
/*      */     //   596: aastore
/*      */     //   597: aload #7
/*      */     //   599: new com/mysql/jdbc/ByteArrayRow
/*      */     //   602: dup
/*      */     //   603: aload #19
/*      */     //   605: aload_0
/*      */     //   606: invokevirtual getExceptionInterceptor : ()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */     //   609: invokespecial <init> : ([[BLcom/mysql/jdbc/ExceptionInterceptor;)V
/*      */     //   612: invokevirtual add : (Ljava/lang/Object;)Z
/*      */     //   615: pop
/*      */     //   616: goto -> 503
/*      */     //   619: jsr -> 633
/*      */     //   622: goto -> 654
/*      */     //   625: astore #20
/*      */     //   627: jsr -> 633
/*      */     //   630: aload #20
/*      */     //   632: athrow
/*      */     //   633: astore #21
/*      */     //   635: aload #18
/*      */     //   637: ifnull -> 652
/*      */     //   640: aload #18
/*      */     //   642: invokeinterface close : ()V
/*      */     //   647: goto -> 652
/*      */     //   650: astore #22
/*      */     //   652: ret #21
/*      */     //   654: goto -> 469
/*      */     //   657: goto -> 306
/*      */     //   660: jsr -> 674
/*      */     //   663: goto -> 718
/*      */     //   666: astore #23
/*      */     //   668: jsr -> 674
/*      */     //   671: aload #23
/*      */     //   673: athrow
/*      */     //   674: astore #24
/*      */     //   676: aload #6
/*      */     //   678: ifnull -> 696
/*      */     //   681: aload #6
/*      */     //   683: invokeinterface close : ()V
/*      */     //   688: goto -> 693
/*      */     //   691: astore #25
/*      */     //   693: aconst_null
/*      */     //   694: astore #6
/*      */     //   696: aload #8
/*      */     //   698: ifnull -> 716
/*      */     //   701: aload #8
/*      */     //   703: invokeinterface close : ()V
/*      */     //   708: goto -> 713
/*      */     //   711: astore #25
/*      */     //   713: aconst_null
/*      */     //   714: astore #8
/*      */     //   716: ret #24
/*      */     //   718: aload_0
/*      */     //   719: aload #4
/*      */     //   721: aload #7
/*      */     //   723: invokespecial buildResultSet : ([Lcom/mysql/jdbc/Field;Ljava/util/ArrayList;)Ljava/sql/ResultSet;
/*      */     //   726: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #4738	-> 0
/*      */     //   #4739	-> 4
/*      */     //   #4740	-> 16
/*      */     //   #4742	-> 23
/*      */     //   #4748	-> 37
/*      */     //   #4749	-> 44
/*      */     //   #4750	-> 63
/*      */     //   #4751	-> 81
/*      */     //   #4752	-> 100
/*      */     //   #4753	-> 119
/*      */     //   #4754	-> 138
/*      */     //   #4755	-> 157
/*      */     //   #4757	-> 176
/*      */     //   #4759	-> 188
/*      */     //   #4761	-> 197
/*      */     //   #4762	-> 208
/*      */     //   #4763	-> 217
/*      */     //   #4764	-> 224
/*      */     //   #4767	-> 233
/*      */     //   #4768	-> 242
/*      */     //   #4769	-> 249
/*      */     //   #4771	-> 258
/*      */     //   #4772	-> 261
/*      */     //   #4773	-> 270
/*      */     //   #4776	-> 273
/*      */     //   #4777	-> 284
/*      */     //   #4779	-> 292
/*      */     //   #4781	-> 306
/*      */     //   #4782	-> 316
/*      */     //   #4783	-> 326
/*      */     //   #4784	-> 336
/*      */     //   #4785	-> 346
/*      */     //   #4786	-> 356
/*      */     //   #4788	-> 366
/*      */     //   #4789	-> 379
/*      */     //   #4792	-> 384
/*      */     //   #4794	-> 395
/*      */     //   #4795	-> 412
/*      */     //   #4796	-> 421
/*      */     //   #4799	-> 429
/*      */     //   #4801	-> 440
/*      */     //   #4802	-> 445
/*      */     //   #4804	-> 455
/*      */     //   #4806	-> 469
/*      */     //   #4807	-> 477
/*      */     //   #4810	-> 487
/*      */     //   #4813	-> 490
/*      */     //   #4816	-> 503
/*      */     //   #4817	-> 513
/*      */     //   #4818	-> 520
/*      */     //   #4819	-> 530
/*      */     //   #4820	-> 535
/*      */     //   #4822	-> 545
/*      */     //   #4823	-> 550
/*      */     //   #4825	-> 563
/*      */     //   #4828	-> 568
/*      */     //   #4829	-> 581
/*      */     //   #4830	-> 591
/*      */     //   #4831	-> 597
/*      */     //   #4832	-> 616
/*      */     //   #4833	-> 619
/*      */     //   #4841	-> 622
/*      */     //   #4834	-> 625
/*      */     //   #4836	-> 640
/*      */     //   #4839	-> 647
/*      */     //   #4837	-> 650
/*      */     //   #4839	-> 652
/*      */     //   #4842	-> 654
/*      */     //   #4844	-> 657
/*      */     //   #4845	-> 660
/*      */     //   #4865	-> 663
/*      */     //   #4846	-> 666
/*      */     //   #4848	-> 681
/*      */     //   #4851	-> 688
/*      */     //   #4849	-> 691
/*      */     //   #4853	-> 693
/*      */     //   #4856	-> 696
/*      */     //   #4858	-> 701
/*      */     //   #4861	-> 708
/*      */     //   #4859	-> 711
/*      */     //   #4863	-> 713
/*      */     //   #4867	-> 718
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   520	96	19	tuple	[[B
/*      */     //   652	0	22	ex	Ljava/lang/Exception;
/*      */     //   487	167	17	privilege	Ljava/lang/String;
/*      */     //   490	164	18	columnResults	Ljava/sql/ResultSet;
/*      */     //   469	188	16	st	Ljava/util/StringTokenizer;
/*      */     //   326	331	9	host	Ljava/lang/String;
/*      */     //   336	321	10	db	Ljava/lang/String;
/*      */     //   346	311	11	table	Ljava/lang/String;
/*      */     //   356	301	12	grantor	Ljava/lang/String;
/*      */     //   366	291	13	user	Ljava/lang/String;
/*      */     //   395	262	14	fullUser	Ljava/lang/StringBuffer;
/*      */     //   440	217	15	allPrivileges	Ljava/lang/String;
/*      */     //   693	0	25	ex	Ljava/lang/Exception;
/*      */     //   713	0	25	ex	Ljava/lang/Exception;
/*      */     //   0	727	0	this	Lcom/mysql/jdbc/DatabaseMetaData;
/*      */     //   0	727	1	catalog	Ljava/lang/String;
/*      */     //   0	727	2	schemaPattern	Ljava/lang/String;
/*      */     //   0	727	3	tableNamePattern	Ljava/lang/String;
/*      */     //   44	683	4	fields	[Lcom/mysql/jdbc/Field;
/*      */     //   188	539	5	grantQuery	Ljava/lang/StringBuffer;
/*      */     //   261	466	6	results	Ljava/sql/ResultSet;
/*      */     //   270	457	7	grantRows	Ljava/util/ArrayList;
/*      */     //   273	454	8	stmt	Ljava/sql/Statement;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   273	663	666	finally
/*      */     //   490	622	625	finally
/*      */     //   625	630	625	finally
/*      */     //   640	647	650	java/lang/Exception
/*      */     //   666	671	666	finally
/*      */     //   681	688	691	java/lang/Exception
/*      */     //   701	708	711	java/lang/Exception
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, final String[] types) throws SQLException {
/*      */     final String tableNamePat;
/* 4909 */     if (tableNamePattern == null) {
/* 4910 */       if (this.conn.getNullNamePatternMatchesAll()) {
/* 4911 */         tableNamePattern = "%";
/*      */       } else {
/* 4913 */         throw SQLError.createSQLException("Table name pattern can not be NULL or empty.", "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4919 */     Field[] fields = new Field[5];
/* 4920 */     fields[0] = new Field("", "TABLE_CAT", 12, 255);
/* 4921 */     fields[1] = new Field("", "TABLE_SCHEM", 12, 0);
/* 4922 */     fields[2] = new Field("", "TABLE_NAME", 12, 255);
/* 4923 */     fields[3] = new Field("", "TABLE_TYPE", 12, 5);
/* 4924 */     fields[4] = new Field("", "REMARKS", 12, 0);
/*      */     
/* 4926 */     final ArrayList tuples = new ArrayList();
/*      */     
/* 4928 */     final Statement stmt = this.conn.getMetadataSafeStatement();
/*      */ 
/*      */     
/* 4931 */     List<String> parseList = StringUtils.splitDBdotName(tableNamePattern, "", this.quotedId, this.conn.isNoBackslashEscapesSet());
/*      */ 
/*      */     
/* 4934 */     if (parseList.size() == 2) {
/* 4935 */       tableNamePat = parseList.get(1);
/*      */     } else {
/* 4937 */       tableNamePat = tableNamePattern;
/*      */     } 
/*      */ 
/*      */     
/* 4941 */     final boolean operatingOnInformationSchema = "information_schema".equalsIgnoreCase(catalog);
/*      */ 
/*      */     
/*      */     try {
/* 4945 */       (new IterateBlock(getCatalogIterator(catalog)) {
/*      */           void forEach(Object catalogStr) throws SQLException {
/* 4947 */             ResultSet results = null;
/*      */ 
/*      */             
/*      */             try {
/* 4951 */               if (!DatabaseMetaData.this.conn.versionMeetsMinimum(5, 0, 2)) {
/*      */                 try {
/* 4953 */                   results = stmt.executeQuery("SHOW TABLES FROM " + DatabaseMetaData.this.quotedId + catalogStr.toString() + DatabaseMetaData.this.quotedId + " LIKE '" + tableNamePat + "'");
/*      */ 
/*      */ 
/*      */                 
/*      */                 }
/* 4958 */                 catch (SQLException sqlEx) {
/* 4959 */                   if ("08S01".equals(sqlEx.getSQLState())) {
/* 4960 */                     throw sqlEx;
/*      */                   }
/*      */                   
/*      */                   return;
/*      */                 } 
/*      */               } else {
/*      */                 try {
/* 4967 */                   results = stmt.executeQuery("SHOW FULL TABLES FROM " + DatabaseMetaData.this.quotedId + catalogStr.toString() + DatabaseMetaData.this.quotedId + " LIKE '" + tableNamePat + "'");
/*      */ 
/*      */ 
/*      */                 
/*      */                 }
/* 4972 */                 catch (SQLException sqlEx) {
/* 4973 */                   if ("08S01".equals(sqlEx.getSQLState())) {
/* 4974 */                     throw sqlEx;
/*      */                   }
/*      */                   
/*      */                   return;
/*      */                 } 
/*      */               } 
/*      */               
/* 4981 */               boolean shouldReportTables = false;
/* 4982 */               boolean shouldReportViews = false;
/* 4983 */               boolean shouldReportSystemTables = false;
/*      */               
/* 4985 */               if (types == null || types.length == 0) {
/* 4986 */                 shouldReportTables = true;
/* 4987 */                 shouldReportViews = true;
/* 4988 */                 shouldReportSystemTables = true;
/*      */               } else {
/* 4990 */                 for (int i = 0; i < types.length; i++) {
/* 4991 */                   if ("TABLE".equalsIgnoreCase(types[i])) {
/* 4992 */                     shouldReportTables = true;
/*      */                   }
/*      */                   
/* 4995 */                   if ("VIEW".equalsIgnoreCase(types[i])) {
/* 4996 */                     shouldReportViews = true;
/*      */                   }
/*      */                   
/* 4999 */                   if ("SYSTEM TABLE".equalsIgnoreCase(types[i])) {
/* 5000 */                     shouldReportSystemTables = true;
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */               
/* 5005 */               int typeColumnIndex = 0;
/* 5006 */               boolean hasTableTypes = false;
/*      */               
/* 5008 */               if (DatabaseMetaData.this.conn.versionMeetsMinimum(5, 0, 2)) {
/*      */                 
/*      */                 try {
/*      */ 
/*      */                   
/* 5013 */                   typeColumnIndex = results.findColumn("table_type");
/*      */                   
/* 5015 */                   hasTableTypes = true;
/* 5016 */                 } catch (SQLException sqlEx) {
/*      */ 
/*      */                   
/*      */                   try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 5027 */                     typeColumnIndex = results.findColumn("Type");
/*      */                     
/* 5029 */                     hasTableTypes = true;
/* 5030 */                   } catch (SQLException sqlEx2) {
/* 5031 */                     hasTableTypes = false;
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */               
/* 5036 */               TreeMap<Object, Object> tablesOrderedByName = null;
/* 5037 */               TreeMap<Object, Object> viewsOrderedByName = null;
/*      */               
/* 5039 */               while (results.next()) {
/* 5040 */                 byte[][] row = new byte[5][];
/* 5041 */                 row[0] = (catalogStr.toString() == null) ? null : DatabaseMetaData.this.s2b(catalogStr.toString());
/*      */                 
/* 5043 */                 row[1] = null;
/* 5044 */                 row[2] = results.getBytes(1);
/* 5045 */                 row[4] = new byte[0];
/*      */                 
/* 5047 */                 if (hasTableTypes) {
/* 5048 */                   String tableType = results.getString(typeColumnIndex);
/*      */ 
/*      */                   
/* 5051 */                   if (("table".equalsIgnoreCase(tableType) || "base table".equalsIgnoreCase(tableType)) && shouldReportTables) {
/*      */ 
/*      */                     
/* 5054 */                     boolean reportTable = false;
/*      */                     
/* 5056 */                     if (!operatingOnInformationSchema && shouldReportTables) {
/* 5057 */                       row[3] = DatabaseMetaData.TABLE_AS_BYTES;
/* 5058 */                       reportTable = true;
/* 5059 */                     } else if (operatingOnInformationSchema && shouldReportSystemTables) {
/* 5060 */                       row[3] = DatabaseMetaData.SYSTEM_TABLE_AS_BYTES;
/* 5061 */                       reportTable = true;
/*      */                     } 
/*      */                     
/* 5064 */                     if (reportTable) {
/* 5065 */                       if (tablesOrderedByName == null) {
/* 5066 */                         tablesOrderedByName = new TreeMap<Object, Object>();
/*      */                       }
/*      */                       
/* 5069 */                       tablesOrderedByName.put(results.getString(1), row);
/*      */                     }  continue;
/*      */                   } 
/* 5072 */                   if ("system view".equalsIgnoreCase(tableType) && shouldReportSystemTables) {
/* 5073 */                     row[3] = DatabaseMetaData.SYSTEM_TABLE_AS_BYTES;
/*      */                     
/* 5075 */                     if (tablesOrderedByName == null) {
/* 5076 */                       tablesOrderedByName = new TreeMap<Object, Object>();
/*      */                     }
/*      */                     
/* 5079 */                     tablesOrderedByName.put(results.getString(1), row); continue;
/*      */                   } 
/* 5081 */                   if ("view".equalsIgnoreCase(tableType) && shouldReportViews) {
/*      */                     
/* 5083 */                     row[3] = DatabaseMetaData.VIEW_AS_BYTES;
/*      */                     
/* 5085 */                     if (viewsOrderedByName == null) {
/* 5086 */                       viewsOrderedByName = new TreeMap<Object, Object>();
/*      */                     }
/*      */                     
/* 5089 */                     viewsOrderedByName.put(results.getString(1), row); continue;
/*      */                   } 
/* 5091 */                   if (!hasTableTypes) {
/*      */                     
/* 5093 */                     row[3] = DatabaseMetaData.TABLE_AS_BYTES;
/*      */                     
/* 5095 */                     if (tablesOrderedByName == null) {
/* 5096 */                       tablesOrderedByName = new TreeMap<Object, Object>();
/*      */                     }
/*      */                     
/* 5099 */                     tablesOrderedByName.put(results.getString(1), row);
/*      */                   } 
/*      */                   continue;
/*      */                 } 
/* 5103 */                 if (shouldReportTables) {
/*      */                   
/* 5105 */                   row[3] = DatabaseMetaData.TABLE_AS_BYTES;
/*      */                   
/* 5107 */                   if (tablesOrderedByName == null) {
/* 5108 */                     tablesOrderedByName = new TreeMap<Object, Object>();
/*      */                   }
/*      */                   
/* 5111 */                   tablesOrderedByName.put(results.getString(1), row);
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 5120 */               if (tablesOrderedByName != null) {
/* 5121 */                 Iterator<byte[][]> tablesIter = tablesOrderedByName.values().iterator();
/*      */ 
/*      */                 
/* 5124 */                 while (tablesIter.hasNext()) {
/* 5125 */                   tuples.add(new ByteArrayRow(tablesIter.next(), DatabaseMetaData.this.getExceptionInterceptor()));
/*      */                 }
/*      */               } 
/*      */               
/* 5129 */               if (viewsOrderedByName != null) {
/* 5130 */                 Iterator<byte[][]> viewsIter = viewsOrderedByName.values().iterator();
/*      */ 
/*      */                 
/* 5133 */                 while (viewsIter.hasNext()) {
/* 5134 */                   tuples.add(new ByteArrayRow(viewsIter.next(), DatabaseMetaData.this.getExceptionInterceptor()));
/*      */                 }
/*      */               } 
/*      */             } finally {
/*      */               
/* 5139 */               if (results != null) {
/*      */                 try {
/* 5141 */                   results.close();
/* 5142 */                 } catch (Exception ex) {}
/*      */ 
/*      */ 
/*      */                 
/* 5146 */                 results = null;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         }).doForAll();
/*      */     } finally {
/*      */       
/* 5153 */       if (stmt != null) {
/* 5154 */         stmt.close();
/*      */       }
/*      */     } 
/*      */     
/* 5158 */     ResultSet tables = buildResultSet(fields, tuples);
/*      */     
/* 5160 */     return tables;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getTableTypes() throws SQLException {
/* 5181 */     ArrayList<ByteArrayRow> tuples = new ArrayList();
/* 5182 */     Field[] fields = new Field[1];
/* 5183 */     fields[0] = new Field("", "TABLE_TYPE", 12, 5);
/*      */     
/* 5185 */     byte[][] tableTypeRow = new byte[1][];
/* 5186 */     tableTypeRow[0] = TABLE_AS_BYTES;
/* 5187 */     tuples.add(new ByteArrayRow(tableTypeRow, getExceptionInterceptor()));
/*      */     
/* 5189 */     if (this.conn.versionMeetsMinimum(5, 0, 1)) {
/* 5190 */       byte[][] viewTypeRow = new byte[1][];
/* 5191 */       viewTypeRow[0] = VIEW_AS_BYTES;
/* 5192 */       tuples.add(new ByteArrayRow(viewTypeRow, getExceptionInterceptor()));
/*      */     } 
/*      */     
/* 5195 */     byte[][] tempTypeRow = new byte[1][];
/* 5196 */     tempTypeRow[0] = s2b("LOCAL TEMPORARY");
/* 5197 */     tuples.add(new ByteArrayRow(tempTypeRow, getExceptionInterceptor()));
/*      */     
/* 5199 */     return buildResultSet(fields, tuples);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTimeDateFunctions() throws SQLException {
/* 5210 */     return "DAYOFWEEK,WEEKDAY,DAYOFMONTH,DAYOFYEAR,MONTH,DAYNAME,MONTHNAME,QUARTER,WEEK,YEAR,HOUR,MINUTE,SECOND,PERIOD_ADD,PERIOD_DIFF,TO_DAYS,FROM_DAYS,DATE_FORMAT,TIME_FORMAT,CURDATE,CURRENT_DATE,CURTIME,CURRENT_TIME,NOW,SYSDATE,CURRENT_TIMESTAMP,UNIX_TIMESTAMP,FROM_UNIXTIME,SEC_TO_TIME,TIME_TO_SEC";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getTypeInfo() throws SQLException {
/* 5319 */     Field[] fields = new Field[18];
/* 5320 */     fields[0] = new Field("", "TYPE_NAME", 1, 32);
/* 5321 */     fields[1] = new Field("", "DATA_TYPE", 4, 5);
/* 5322 */     fields[2] = new Field("", "PRECISION", 4, 10);
/* 5323 */     fields[3] = new Field("", "LITERAL_PREFIX", 1, 4);
/* 5324 */     fields[4] = new Field("", "LITERAL_SUFFIX", 1, 4);
/* 5325 */     fields[5] = new Field("", "CREATE_PARAMS", 1, 32);
/* 5326 */     fields[6] = new Field("", "NULLABLE", 5, 5);
/* 5327 */     fields[7] = new Field("", "CASE_SENSITIVE", 16, 3);
/* 5328 */     fields[8] = new Field("", "SEARCHABLE", 5, 3);
/* 5329 */     fields[9] = new Field("", "UNSIGNED_ATTRIBUTE", 16, 3);
/* 5330 */     fields[10] = new Field("", "FIXED_PREC_SCALE", 16, 3);
/* 5331 */     fields[11] = new Field("", "AUTO_INCREMENT", 16, 3);
/* 5332 */     fields[12] = new Field("", "LOCAL_TYPE_NAME", 1, 32);
/* 5333 */     fields[13] = new Field("", "MINIMUM_SCALE", 5, 5);
/* 5334 */     fields[14] = new Field("", "MAXIMUM_SCALE", 5, 5);
/* 5335 */     fields[15] = new Field("", "SQL_DATA_TYPE", 4, 10);
/* 5336 */     fields[16] = new Field("", "SQL_DATETIME_SUB", 4, 10);
/* 5337 */     fields[17] = new Field("", "NUM_PREC_RADIX", 4, 10);
/*      */     
/* 5339 */     byte[][] rowVal = (byte[][])null;
/* 5340 */     ArrayList<ByteArrayRow> tuples = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5349 */     rowVal = new byte[18][];
/* 5350 */     rowVal[0] = s2b("BIT");
/* 5351 */     rowVal[1] = Integer.toString(-7).getBytes();
/*      */ 
/*      */     
/* 5354 */     rowVal[2] = s2b("1");
/* 5355 */     rowVal[3] = s2b("");
/* 5356 */     rowVal[4] = s2b("");
/* 5357 */     rowVal[5] = s2b("");
/* 5358 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5362 */     rowVal[7] = s2b("true");
/* 5363 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5367 */     rowVal[9] = s2b("false");
/* 5368 */     rowVal[10] = s2b("false");
/* 5369 */     rowVal[11] = s2b("false");
/* 5370 */     rowVal[12] = s2b("BIT");
/* 5371 */     rowVal[13] = s2b("0");
/* 5372 */     rowVal[14] = s2b("0");
/* 5373 */     rowVal[15] = s2b("0");
/* 5374 */     rowVal[16] = s2b("0");
/* 5375 */     rowVal[17] = s2b("10");
/* 5376 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5381 */     rowVal = new byte[18][];
/* 5382 */     rowVal[0] = s2b("BOOL");
/* 5383 */     rowVal[1] = Integer.toString(-7).getBytes();
/*      */ 
/*      */     
/* 5386 */     rowVal[2] = s2b("1");
/* 5387 */     rowVal[3] = s2b("");
/* 5388 */     rowVal[4] = s2b("");
/* 5389 */     rowVal[5] = s2b("");
/* 5390 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5394 */     rowVal[7] = s2b("true");
/* 5395 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5399 */     rowVal[9] = s2b("false");
/* 5400 */     rowVal[10] = s2b("false");
/* 5401 */     rowVal[11] = s2b("false");
/* 5402 */     rowVal[12] = s2b("BOOL");
/* 5403 */     rowVal[13] = s2b("0");
/* 5404 */     rowVal[14] = s2b("0");
/* 5405 */     rowVal[15] = s2b("0");
/* 5406 */     rowVal[16] = s2b("0");
/* 5407 */     rowVal[17] = s2b("10");
/* 5408 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5413 */     rowVal = new byte[18][];
/* 5414 */     rowVal[0] = s2b("TINYINT");
/* 5415 */     rowVal[1] = Integer.toString(-6).getBytes();
/*      */ 
/*      */     
/* 5418 */     rowVal[2] = s2b("3");
/* 5419 */     rowVal[3] = s2b("");
/* 5420 */     rowVal[4] = s2b("");
/* 5421 */     rowVal[5] = s2b("[(M)] [UNSIGNED] [ZEROFILL]");
/* 5422 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5426 */     rowVal[7] = s2b("false");
/* 5427 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5431 */     rowVal[9] = s2b("true");
/* 5432 */     rowVal[10] = s2b("false");
/* 5433 */     rowVal[11] = s2b("true");
/* 5434 */     rowVal[12] = s2b("TINYINT");
/* 5435 */     rowVal[13] = s2b("0");
/* 5436 */     rowVal[14] = s2b("0");
/* 5437 */     rowVal[15] = s2b("0");
/* 5438 */     rowVal[16] = s2b("0");
/* 5439 */     rowVal[17] = s2b("10");
/* 5440 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */     
/* 5442 */     rowVal = new byte[18][];
/* 5443 */     rowVal[0] = s2b("TINYINT UNSIGNED");
/* 5444 */     rowVal[1] = Integer.toString(-6).getBytes();
/*      */ 
/*      */     
/* 5447 */     rowVal[2] = s2b("3");
/* 5448 */     rowVal[3] = s2b("");
/* 5449 */     rowVal[4] = s2b("");
/* 5450 */     rowVal[5] = s2b("[(M)] [UNSIGNED] [ZEROFILL]");
/* 5451 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5455 */     rowVal[7] = s2b("false");
/* 5456 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5460 */     rowVal[9] = s2b("true");
/* 5461 */     rowVal[10] = s2b("false");
/* 5462 */     rowVal[11] = s2b("true");
/* 5463 */     rowVal[12] = s2b("TINYINT UNSIGNED");
/* 5464 */     rowVal[13] = s2b("0");
/* 5465 */     rowVal[14] = s2b("0");
/* 5466 */     rowVal[15] = s2b("0");
/* 5467 */     rowVal[16] = s2b("0");
/* 5468 */     rowVal[17] = s2b("10");
/* 5469 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5474 */     rowVal = new byte[18][];
/* 5475 */     rowVal[0] = s2b("BIGINT");
/* 5476 */     rowVal[1] = Integer.toString(-5).getBytes();
/*      */ 
/*      */     
/* 5479 */     rowVal[2] = s2b("19");
/* 5480 */     rowVal[3] = s2b("");
/* 5481 */     rowVal[4] = s2b("");
/* 5482 */     rowVal[5] = s2b("[(M)] [UNSIGNED] [ZEROFILL]");
/* 5483 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5487 */     rowVal[7] = s2b("false");
/* 5488 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5492 */     rowVal[9] = s2b("true");
/* 5493 */     rowVal[10] = s2b("false");
/* 5494 */     rowVal[11] = s2b("true");
/* 5495 */     rowVal[12] = s2b("BIGINT");
/* 5496 */     rowVal[13] = s2b("0");
/* 5497 */     rowVal[14] = s2b("0");
/* 5498 */     rowVal[15] = s2b("0");
/* 5499 */     rowVal[16] = s2b("0");
/* 5500 */     rowVal[17] = s2b("10");
/* 5501 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */     
/* 5503 */     rowVal = new byte[18][];
/* 5504 */     rowVal[0] = s2b("BIGINT UNSIGNED");
/* 5505 */     rowVal[1] = Integer.toString(-5).getBytes();
/*      */ 
/*      */     
/* 5508 */     rowVal[2] = s2b("20");
/* 5509 */     rowVal[3] = s2b("");
/* 5510 */     rowVal[4] = s2b("");
/* 5511 */     rowVal[5] = s2b("[(M)] [ZEROFILL]");
/* 5512 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5516 */     rowVal[7] = s2b("false");
/* 5517 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5521 */     rowVal[9] = s2b("true");
/* 5522 */     rowVal[10] = s2b("false");
/* 5523 */     rowVal[11] = s2b("true");
/* 5524 */     rowVal[12] = s2b("BIGINT UNSIGNED");
/* 5525 */     rowVal[13] = s2b("0");
/* 5526 */     rowVal[14] = s2b("0");
/* 5527 */     rowVal[15] = s2b("0");
/* 5528 */     rowVal[16] = s2b("0");
/* 5529 */     rowVal[17] = s2b("10");
/* 5530 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5535 */     rowVal = new byte[18][];
/* 5536 */     rowVal[0] = s2b("LONG VARBINARY");
/* 5537 */     rowVal[1] = Integer.toString(-4).getBytes();
/*      */ 
/*      */     
/* 5540 */     rowVal[2] = s2b("16777215");
/* 5541 */     rowVal[3] = s2b("'");
/* 5542 */     rowVal[4] = s2b("'");
/* 5543 */     rowVal[5] = s2b("");
/* 5544 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5548 */     rowVal[7] = s2b("true");
/* 5549 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5553 */     rowVal[9] = s2b("false");
/* 5554 */     rowVal[10] = s2b("false");
/* 5555 */     rowVal[11] = s2b("false");
/* 5556 */     rowVal[12] = s2b("LONG VARBINARY");
/* 5557 */     rowVal[13] = s2b("0");
/* 5558 */     rowVal[14] = s2b("0");
/* 5559 */     rowVal[15] = s2b("0");
/* 5560 */     rowVal[16] = s2b("0");
/* 5561 */     rowVal[17] = s2b("10");
/* 5562 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5567 */     rowVal = new byte[18][];
/* 5568 */     rowVal[0] = s2b("MEDIUMBLOB");
/* 5569 */     rowVal[1] = Integer.toString(-4).getBytes();
/*      */ 
/*      */     
/* 5572 */     rowVal[2] = s2b("16777215");
/* 5573 */     rowVal[3] = s2b("'");
/* 5574 */     rowVal[4] = s2b("'");
/* 5575 */     rowVal[5] = s2b("");
/* 5576 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5580 */     rowVal[7] = s2b("true");
/* 5581 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5585 */     rowVal[9] = s2b("false");
/* 5586 */     rowVal[10] = s2b("false");
/* 5587 */     rowVal[11] = s2b("false");
/* 5588 */     rowVal[12] = s2b("MEDIUMBLOB");
/* 5589 */     rowVal[13] = s2b("0");
/* 5590 */     rowVal[14] = s2b("0");
/* 5591 */     rowVal[15] = s2b("0");
/* 5592 */     rowVal[16] = s2b("0");
/* 5593 */     rowVal[17] = s2b("10");
/* 5594 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5599 */     rowVal = new byte[18][];
/* 5600 */     rowVal[0] = s2b("LONGBLOB");
/* 5601 */     rowVal[1] = Integer.toString(-4).getBytes();
/*      */ 
/*      */     
/* 5604 */     rowVal[2] = Integer.toString(2147483647).getBytes();
/*      */ 
/*      */     
/* 5607 */     rowVal[3] = s2b("'");
/* 5608 */     rowVal[4] = s2b("'");
/* 5609 */     rowVal[5] = s2b("");
/* 5610 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5614 */     rowVal[7] = s2b("true");
/* 5615 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5619 */     rowVal[9] = s2b("false");
/* 5620 */     rowVal[10] = s2b("false");
/* 5621 */     rowVal[11] = s2b("false");
/* 5622 */     rowVal[12] = s2b("LONGBLOB");
/* 5623 */     rowVal[13] = s2b("0");
/* 5624 */     rowVal[14] = s2b("0");
/* 5625 */     rowVal[15] = s2b("0");
/* 5626 */     rowVal[16] = s2b("0");
/* 5627 */     rowVal[17] = s2b("10");
/* 5628 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5633 */     rowVal = new byte[18][];
/* 5634 */     rowVal[0] = s2b("BLOB");
/* 5635 */     rowVal[1] = Integer.toString(-4).getBytes();
/*      */ 
/*      */     
/* 5638 */     rowVal[2] = s2b("65535");
/* 5639 */     rowVal[3] = s2b("'");
/* 5640 */     rowVal[4] = s2b("'");
/* 5641 */     rowVal[5] = s2b("");
/* 5642 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5646 */     rowVal[7] = s2b("true");
/* 5647 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5651 */     rowVal[9] = s2b("false");
/* 5652 */     rowVal[10] = s2b("false");
/* 5653 */     rowVal[11] = s2b("false");
/* 5654 */     rowVal[12] = s2b("BLOB");
/* 5655 */     rowVal[13] = s2b("0");
/* 5656 */     rowVal[14] = s2b("0");
/* 5657 */     rowVal[15] = s2b("0");
/* 5658 */     rowVal[16] = s2b("0");
/* 5659 */     rowVal[17] = s2b("10");
/* 5660 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5665 */     rowVal = new byte[18][];
/* 5666 */     rowVal[0] = s2b("TINYBLOB");
/* 5667 */     rowVal[1] = Integer.toString(-4).getBytes();
/*      */ 
/*      */     
/* 5670 */     rowVal[2] = s2b("255");
/* 5671 */     rowVal[3] = s2b("'");
/* 5672 */     rowVal[4] = s2b("'");
/* 5673 */     rowVal[5] = s2b("");
/* 5674 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5678 */     rowVal[7] = s2b("true");
/* 5679 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5683 */     rowVal[9] = s2b("false");
/* 5684 */     rowVal[10] = s2b("false");
/* 5685 */     rowVal[11] = s2b("false");
/* 5686 */     rowVal[12] = s2b("TINYBLOB");
/* 5687 */     rowVal[13] = s2b("0");
/* 5688 */     rowVal[14] = s2b("0");
/* 5689 */     rowVal[15] = s2b("0");
/* 5690 */     rowVal[16] = s2b("0");
/* 5691 */     rowVal[17] = s2b("10");
/* 5692 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5698 */     rowVal = new byte[18][];
/* 5699 */     rowVal[0] = s2b("VARBINARY");
/* 5700 */     rowVal[1] = Integer.toString(-3).getBytes();
/*      */ 
/*      */     
/* 5703 */     rowVal[2] = s2b("255");
/* 5704 */     rowVal[3] = s2b("'");
/* 5705 */     rowVal[4] = s2b("'");
/* 5706 */     rowVal[5] = s2b("(M)");
/* 5707 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5711 */     rowVal[7] = s2b("true");
/* 5712 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5716 */     rowVal[9] = s2b("false");
/* 5717 */     rowVal[10] = s2b("false");
/* 5718 */     rowVal[11] = s2b("false");
/* 5719 */     rowVal[12] = s2b("VARBINARY");
/* 5720 */     rowVal[13] = s2b("0");
/* 5721 */     rowVal[14] = s2b("0");
/* 5722 */     rowVal[15] = s2b("0");
/* 5723 */     rowVal[16] = s2b("0");
/* 5724 */     rowVal[17] = s2b("10");
/* 5725 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5731 */     rowVal = new byte[18][];
/* 5732 */     rowVal[0] = s2b("BINARY");
/* 5733 */     rowVal[1] = Integer.toString(-2).getBytes();
/*      */ 
/*      */     
/* 5736 */     rowVal[2] = s2b("255");
/* 5737 */     rowVal[3] = s2b("'");
/* 5738 */     rowVal[4] = s2b("'");
/* 5739 */     rowVal[5] = s2b("(M)");
/* 5740 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5744 */     rowVal[7] = s2b("true");
/* 5745 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5749 */     rowVal[9] = s2b("false");
/* 5750 */     rowVal[10] = s2b("false");
/* 5751 */     rowVal[11] = s2b("false");
/* 5752 */     rowVal[12] = s2b("BINARY");
/* 5753 */     rowVal[13] = s2b("0");
/* 5754 */     rowVal[14] = s2b("0");
/* 5755 */     rowVal[15] = s2b("0");
/* 5756 */     rowVal[16] = s2b("0");
/* 5757 */     rowVal[17] = s2b("10");
/* 5758 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5763 */     rowVal = new byte[18][];
/* 5764 */     rowVal[0] = s2b("LONG VARCHAR");
/* 5765 */     rowVal[1] = Integer.toString(-1).getBytes();
/*      */ 
/*      */     
/* 5768 */     rowVal[2] = s2b("16777215");
/* 5769 */     rowVal[3] = s2b("'");
/* 5770 */     rowVal[4] = s2b("'");
/* 5771 */     rowVal[5] = s2b("");
/* 5772 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5776 */     rowVal[7] = s2b("false");
/* 5777 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5781 */     rowVal[9] = s2b("false");
/* 5782 */     rowVal[10] = s2b("false");
/* 5783 */     rowVal[11] = s2b("false");
/* 5784 */     rowVal[12] = s2b("LONG VARCHAR");
/* 5785 */     rowVal[13] = s2b("0");
/* 5786 */     rowVal[14] = s2b("0");
/* 5787 */     rowVal[15] = s2b("0");
/* 5788 */     rowVal[16] = s2b("0");
/* 5789 */     rowVal[17] = s2b("10");
/* 5790 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5795 */     rowVal = new byte[18][];
/* 5796 */     rowVal[0] = s2b("MEDIUMTEXT");
/* 5797 */     rowVal[1] = Integer.toString(-1).getBytes();
/*      */ 
/*      */     
/* 5800 */     rowVal[2] = s2b("16777215");
/* 5801 */     rowVal[3] = s2b("'");
/* 5802 */     rowVal[4] = s2b("'");
/* 5803 */     rowVal[5] = s2b("");
/* 5804 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5808 */     rowVal[7] = s2b("false");
/* 5809 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5813 */     rowVal[9] = s2b("false");
/* 5814 */     rowVal[10] = s2b("false");
/* 5815 */     rowVal[11] = s2b("false");
/* 5816 */     rowVal[12] = s2b("MEDIUMTEXT");
/* 5817 */     rowVal[13] = s2b("0");
/* 5818 */     rowVal[14] = s2b("0");
/* 5819 */     rowVal[15] = s2b("0");
/* 5820 */     rowVal[16] = s2b("0");
/* 5821 */     rowVal[17] = s2b("10");
/* 5822 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5827 */     rowVal = new byte[18][];
/* 5828 */     rowVal[0] = s2b("LONGTEXT");
/* 5829 */     rowVal[1] = Integer.toString(-1).getBytes();
/*      */ 
/*      */     
/* 5832 */     rowVal[2] = Integer.toString(2147483647).getBytes();
/*      */ 
/*      */     
/* 5835 */     rowVal[3] = s2b("'");
/* 5836 */     rowVal[4] = s2b("'");
/* 5837 */     rowVal[5] = s2b("");
/* 5838 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5842 */     rowVal[7] = s2b("false");
/* 5843 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5847 */     rowVal[9] = s2b("false");
/* 5848 */     rowVal[10] = s2b("false");
/* 5849 */     rowVal[11] = s2b("false");
/* 5850 */     rowVal[12] = s2b("LONGTEXT");
/* 5851 */     rowVal[13] = s2b("0");
/* 5852 */     rowVal[14] = s2b("0");
/* 5853 */     rowVal[15] = s2b("0");
/* 5854 */     rowVal[16] = s2b("0");
/* 5855 */     rowVal[17] = s2b("10");
/* 5856 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5861 */     rowVal = new byte[18][];
/* 5862 */     rowVal[0] = s2b("TEXT");
/* 5863 */     rowVal[1] = Integer.toString(-1).getBytes();
/*      */ 
/*      */     
/* 5866 */     rowVal[2] = s2b("65535");
/* 5867 */     rowVal[3] = s2b("'");
/* 5868 */     rowVal[4] = s2b("'");
/* 5869 */     rowVal[5] = s2b("");
/* 5870 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5874 */     rowVal[7] = s2b("false");
/* 5875 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5879 */     rowVal[9] = s2b("false");
/* 5880 */     rowVal[10] = s2b("false");
/* 5881 */     rowVal[11] = s2b("false");
/* 5882 */     rowVal[12] = s2b("TEXT");
/* 5883 */     rowVal[13] = s2b("0");
/* 5884 */     rowVal[14] = s2b("0");
/* 5885 */     rowVal[15] = s2b("0");
/* 5886 */     rowVal[16] = s2b("0");
/* 5887 */     rowVal[17] = s2b("10");
/* 5888 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5893 */     rowVal = new byte[18][];
/* 5894 */     rowVal[0] = s2b("TINYTEXT");
/* 5895 */     rowVal[1] = Integer.toString(-1).getBytes();
/*      */ 
/*      */     
/* 5898 */     rowVal[2] = s2b("255");
/* 5899 */     rowVal[3] = s2b("'");
/* 5900 */     rowVal[4] = s2b("'");
/* 5901 */     rowVal[5] = s2b("");
/* 5902 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5906 */     rowVal[7] = s2b("false");
/* 5907 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5911 */     rowVal[9] = s2b("false");
/* 5912 */     rowVal[10] = s2b("false");
/* 5913 */     rowVal[11] = s2b("false");
/* 5914 */     rowVal[12] = s2b("TINYTEXT");
/* 5915 */     rowVal[13] = s2b("0");
/* 5916 */     rowVal[14] = s2b("0");
/* 5917 */     rowVal[15] = s2b("0");
/* 5918 */     rowVal[16] = s2b("0");
/* 5919 */     rowVal[17] = s2b("10");
/* 5920 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5925 */     rowVal = new byte[18][];
/* 5926 */     rowVal[0] = s2b("CHAR");
/* 5927 */     rowVal[1] = Integer.toString(1).getBytes();
/*      */ 
/*      */     
/* 5930 */     rowVal[2] = s2b("255");
/* 5931 */     rowVal[3] = s2b("'");
/* 5932 */     rowVal[4] = s2b("'");
/* 5933 */     rowVal[5] = s2b("(M)");
/* 5934 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5938 */     rowVal[7] = s2b("false");
/* 5939 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5943 */     rowVal[9] = s2b("false");
/* 5944 */     rowVal[10] = s2b("false");
/* 5945 */     rowVal[11] = s2b("false");
/* 5946 */     rowVal[12] = s2b("CHAR");
/* 5947 */     rowVal[13] = s2b("0");
/* 5948 */     rowVal[14] = s2b("0");
/* 5949 */     rowVal[15] = s2b("0");
/* 5950 */     rowVal[16] = s2b("0");
/* 5951 */     rowVal[17] = s2b("10");
/* 5952 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */     
/* 5956 */     int decimalPrecision = 254;
/*      */     
/* 5958 */     if (this.conn.versionMeetsMinimum(5, 0, 3)) {
/* 5959 */       if (this.conn.versionMeetsMinimum(5, 0, 6)) {
/* 5960 */         decimalPrecision = 65;
/*      */       } else {
/* 5962 */         decimalPrecision = 64;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5970 */     rowVal = new byte[18][];
/* 5971 */     rowVal[0] = s2b("NUMERIC");
/* 5972 */     rowVal[1] = Integer.toString(2).getBytes();
/*      */ 
/*      */     
/* 5975 */     rowVal[2] = s2b(String.valueOf(decimalPrecision));
/* 5976 */     rowVal[3] = s2b("");
/* 5977 */     rowVal[4] = s2b("");
/* 5978 */     rowVal[5] = s2b("[(M[,D])] [ZEROFILL]");
/* 5979 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5983 */     rowVal[7] = s2b("false");
/* 5984 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 5988 */     rowVal[9] = s2b("false");
/* 5989 */     rowVal[10] = s2b("false");
/* 5990 */     rowVal[11] = s2b("true");
/* 5991 */     rowVal[12] = s2b("NUMERIC");
/* 5992 */     rowVal[13] = s2b("-308");
/* 5993 */     rowVal[14] = s2b("308");
/* 5994 */     rowVal[15] = s2b("0");
/* 5995 */     rowVal[16] = s2b("0");
/* 5996 */     rowVal[17] = s2b("10");
/* 5997 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6002 */     rowVal = new byte[18][];
/* 6003 */     rowVal[0] = s2b("DECIMAL");
/* 6004 */     rowVal[1] = Integer.toString(3).getBytes();
/*      */ 
/*      */     
/* 6007 */     rowVal[2] = s2b(String.valueOf(decimalPrecision));
/* 6008 */     rowVal[3] = s2b("");
/* 6009 */     rowVal[4] = s2b("");
/* 6010 */     rowVal[5] = s2b("[(M[,D])] [ZEROFILL]");
/* 6011 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6015 */     rowVal[7] = s2b("false");
/* 6016 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6020 */     rowVal[9] = s2b("false");
/* 6021 */     rowVal[10] = s2b("false");
/* 6022 */     rowVal[11] = s2b("true");
/* 6023 */     rowVal[12] = s2b("DECIMAL");
/* 6024 */     rowVal[13] = s2b("-308");
/* 6025 */     rowVal[14] = s2b("308");
/* 6026 */     rowVal[15] = s2b("0");
/* 6027 */     rowVal[16] = s2b("0");
/* 6028 */     rowVal[17] = s2b("10");
/* 6029 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6034 */     rowVal = new byte[18][];
/* 6035 */     rowVal[0] = s2b("INTEGER");
/* 6036 */     rowVal[1] = Integer.toString(4).getBytes();
/*      */ 
/*      */     
/* 6039 */     rowVal[2] = s2b("10");
/* 6040 */     rowVal[3] = s2b("");
/* 6041 */     rowVal[4] = s2b("");
/* 6042 */     rowVal[5] = s2b("[(M)] [UNSIGNED] [ZEROFILL]");
/* 6043 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6047 */     rowVal[7] = s2b("false");
/* 6048 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6052 */     rowVal[9] = s2b("true");
/* 6053 */     rowVal[10] = s2b("false");
/* 6054 */     rowVal[11] = s2b("true");
/* 6055 */     rowVal[12] = s2b("INTEGER");
/* 6056 */     rowVal[13] = s2b("0");
/* 6057 */     rowVal[14] = s2b("0");
/* 6058 */     rowVal[15] = s2b("0");
/* 6059 */     rowVal[16] = s2b("0");
/* 6060 */     rowVal[17] = s2b("10");
/* 6061 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */     
/* 6063 */     rowVal = new byte[18][];
/* 6064 */     rowVal[0] = s2b("INTEGER UNSIGNED");
/* 6065 */     rowVal[1] = Integer.toString(4).getBytes();
/*      */ 
/*      */     
/* 6068 */     rowVal[2] = s2b("10");
/* 6069 */     rowVal[3] = s2b("");
/* 6070 */     rowVal[4] = s2b("");
/* 6071 */     rowVal[5] = s2b("[(M)] [ZEROFILL]");
/* 6072 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6076 */     rowVal[7] = s2b("false");
/* 6077 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6081 */     rowVal[9] = s2b("true");
/* 6082 */     rowVal[10] = s2b("false");
/* 6083 */     rowVal[11] = s2b("true");
/* 6084 */     rowVal[12] = s2b("INTEGER UNSIGNED");
/* 6085 */     rowVal[13] = s2b("0");
/* 6086 */     rowVal[14] = s2b("0");
/* 6087 */     rowVal[15] = s2b("0");
/* 6088 */     rowVal[16] = s2b("0");
/* 6089 */     rowVal[17] = s2b("10");
/* 6090 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6095 */     rowVal = new byte[18][];
/* 6096 */     rowVal[0] = s2b("INT");
/* 6097 */     rowVal[1] = Integer.toString(4).getBytes();
/*      */ 
/*      */     
/* 6100 */     rowVal[2] = s2b("10");
/* 6101 */     rowVal[3] = s2b("");
/* 6102 */     rowVal[4] = s2b("");
/* 6103 */     rowVal[5] = s2b("[(M)] [UNSIGNED] [ZEROFILL]");
/* 6104 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6108 */     rowVal[7] = s2b("false");
/* 6109 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6113 */     rowVal[9] = s2b("true");
/* 6114 */     rowVal[10] = s2b("false");
/* 6115 */     rowVal[11] = s2b("true");
/* 6116 */     rowVal[12] = s2b("INT");
/* 6117 */     rowVal[13] = s2b("0");
/* 6118 */     rowVal[14] = s2b("0");
/* 6119 */     rowVal[15] = s2b("0");
/* 6120 */     rowVal[16] = s2b("0");
/* 6121 */     rowVal[17] = s2b("10");
/* 6122 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */     
/* 6124 */     rowVal = new byte[18][];
/* 6125 */     rowVal[0] = s2b("INT UNSIGNED");
/* 6126 */     rowVal[1] = Integer.toString(4).getBytes();
/*      */ 
/*      */     
/* 6129 */     rowVal[2] = s2b("10");
/* 6130 */     rowVal[3] = s2b("");
/* 6131 */     rowVal[4] = s2b("");
/* 6132 */     rowVal[5] = s2b("[(M)] [ZEROFILL]");
/* 6133 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6137 */     rowVal[7] = s2b("false");
/* 6138 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6142 */     rowVal[9] = s2b("true");
/* 6143 */     rowVal[10] = s2b("false");
/* 6144 */     rowVal[11] = s2b("true");
/* 6145 */     rowVal[12] = s2b("INT UNSIGNED");
/* 6146 */     rowVal[13] = s2b("0");
/* 6147 */     rowVal[14] = s2b("0");
/* 6148 */     rowVal[15] = s2b("0");
/* 6149 */     rowVal[16] = s2b("0");
/* 6150 */     rowVal[17] = s2b("10");
/* 6151 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6156 */     rowVal = new byte[18][];
/* 6157 */     rowVal[0] = s2b("MEDIUMINT");
/* 6158 */     rowVal[1] = Integer.toString(4).getBytes();
/*      */ 
/*      */     
/* 6161 */     rowVal[2] = s2b("7");
/* 6162 */     rowVal[3] = s2b("");
/* 6163 */     rowVal[4] = s2b("");
/* 6164 */     rowVal[5] = s2b("[(M)] [UNSIGNED] [ZEROFILL]");
/* 6165 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6169 */     rowVal[7] = s2b("false");
/* 6170 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6174 */     rowVal[9] = s2b("true");
/* 6175 */     rowVal[10] = s2b("false");
/* 6176 */     rowVal[11] = s2b("true");
/* 6177 */     rowVal[12] = s2b("MEDIUMINT");
/* 6178 */     rowVal[13] = s2b("0");
/* 6179 */     rowVal[14] = s2b("0");
/* 6180 */     rowVal[15] = s2b("0");
/* 6181 */     rowVal[16] = s2b("0");
/* 6182 */     rowVal[17] = s2b("10");
/* 6183 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */     
/* 6185 */     rowVal = new byte[18][];
/* 6186 */     rowVal[0] = s2b("MEDIUMINT UNSIGNED");
/* 6187 */     rowVal[1] = Integer.toString(4).getBytes();
/*      */ 
/*      */     
/* 6190 */     rowVal[2] = s2b("8");
/* 6191 */     rowVal[3] = s2b("");
/* 6192 */     rowVal[4] = s2b("");
/* 6193 */     rowVal[5] = s2b("[(M)] [ZEROFILL]");
/* 6194 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6198 */     rowVal[7] = s2b("false");
/* 6199 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6203 */     rowVal[9] = s2b("true");
/* 6204 */     rowVal[10] = s2b("false");
/* 6205 */     rowVal[11] = s2b("true");
/* 6206 */     rowVal[12] = s2b("MEDIUMINT UNSIGNED");
/* 6207 */     rowVal[13] = s2b("0");
/* 6208 */     rowVal[14] = s2b("0");
/* 6209 */     rowVal[15] = s2b("0");
/* 6210 */     rowVal[16] = s2b("0");
/* 6211 */     rowVal[17] = s2b("10");
/* 6212 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6217 */     rowVal = new byte[18][];
/* 6218 */     rowVal[0] = s2b("SMALLINT");
/* 6219 */     rowVal[1] = Integer.toString(5).getBytes();
/*      */ 
/*      */     
/* 6222 */     rowVal[2] = s2b("5");
/* 6223 */     rowVal[3] = s2b("");
/* 6224 */     rowVal[4] = s2b("");
/* 6225 */     rowVal[5] = s2b("[(M)] [UNSIGNED] [ZEROFILL]");
/* 6226 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6230 */     rowVal[7] = s2b("false");
/* 6231 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6235 */     rowVal[9] = s2b("true");
/* 6236 */     rowVal[10] = s2b("false");
/* 6237 */     rowVal[11] = s2b("true");
/* 6238 */     rowVal[12] = s2b("SMALLINT");
/* 6239 */     rowVal[13] = s2b("0");
/* 6240 */     rowVal[14] = s2b("0");
/* 6241 */     rowVal[15] = s2b("0");
/* 6242 */     rowVal[16] = s2b("0");
/* 6243 */     rowVal[17] = s2b("10");
/* 6244 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */     
/* 6246 */     rowVal = new byte[18][];
/* 6247 */     rowVal[0] = s2b("SMALLINT UNSIGNED");
/* 6248 */     rowVal[1] = Integer.toString(5).getBytes();
/*      */ 
/*      */     
/* 6251 */     rowVal[2] = s2b("5");
/* 6252 */     rowVal[3] = s2b("");
/* 6253 */     rowVal[4] = s2b("");
/* 6254 */     rowVal[5] = s2b("[(M)] [ZEROFILL]");
/* 6255 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6259 */     rowVal[7] = s2b("false");
/* 6260 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6264 */     rowVal[9] = s2b("true");
/* 6265 */     rowVal[10] = s2b("false");
/* 6266 */     rowVal[11] = s2b("true");
/* 6267 */     rowVal[12] = s2b("SMALLINT UNSIGNED");
/* 6268 */     rowVal[13] = s2b("0");
/* 6269 */     rowVal[14] = s2b("0");
/* 6270 */     rowVal[15] = s2b("0");
/* 6271 */     rowVal[16] = s2b("0");
/* 6272 */     rowVal[17] = s2b("10");
/* 6273 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6279 */     rowVal = new byte[18][];
/* 6280 */     rowVal[0] = s2b("FLOAT");
/* 6281 */     rowVal[1] = Integer.toString(7).getBytes();
/*      */ 
/*      */     
/* 6284 */     rowVal[2] = s2b("10");
/* 6285 */     rowVal[3] = s2b("");
/* 6286 */     rowVal[4] = s2b("");
/* 6287 */     rowVal[5] = s2b("[(M,D)] [ZEROFILL]");
/* 6288 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6292 */     rowVal[7] = s2b("false");
/* 6293 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6297 */     rowVal[9] = s2b("false");
/* 6298 */     rowVal[10] = s2b("false");
/* 6299 */     rowVal[11] = s2b("true");
/* 6300 */     rowVal[12] = s2b("FLOAT");
/* 6301 */     rowVal[13] = s2b("-38");
/* 6302 */     rowVal[14] = s2b("38");
/* 6303 */     rowVal[15] = s2b("0");
/* 6304 */     rowVal[16] = s2b("0");
/* 6305 */     rowVal[17] = s2b("10");
/* 6306 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6311 */     rowVal = new byte[18][];
/* 6312 */     rowVal[0] = s2b("DOUBLE");
/* 6313 */     rowVal[1] = Integer.toString(8).getBytes();
/*      */ 
/*      */     
/* 6316 */     rowVal[2] = s2b("17");
/* 6317 */     rowVal[3] = s2b("");
/* 6318 */     rowVal[4] = s2b("");
/* 6319 */     rowVal[5] = s2b("[(M,D)] [ZEROFILL]");
/* 6320 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6324 */     rowVal[7] = s2b("false");
/* 6325 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6329 */     rowVal[9] = s2b("false");
/* 6330 */     rowVal[10] = s2b("false");
/* 6331 */     rowVal[11] = s2b("true");
/* 6332 */     rowVal[12] = s2b("DOUBLE");
/* 6333 */     rowVal[13] = s2b("-308");
/* 6334 */     rowVal[14] = s2b("308");
/* 6335 */     rowVal[15] = s2b("0");
/* 6336 */     rowVal[16] = s2b("0");
/* 6337 */     rowVal[17] = s2b("10");
/* 6338 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6343 */     rowVal = new byte[18][];
/* 6344 */     rowVal[0] = s2b("DOUBLE PRECISION");
/* 6345 */     rowVal[1] = Integer.toString(8).getBytes();
/*      */ 
/*      */     
/* 6348 */     rowVal[2] = s2b("17");
/* 6349 */     rowVal[3] = s2b("");
/* 6350 */     rowVal[4] = s2b("");
/* 6351 */     rowVal[5] = s2b("[(M,D)] [ZEROFILL]");
/* 6352 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6356 */     rowVal[7] = s2b("false");
/* 6357 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6361 */     rowVal[9] = s2b("false");
/* 6362 */     rowVal[10] = s2b("false");
/* 6363 */     rowVal[11] = s2b("true");
/* 6364 */     rowVal[12] = s2b("DOUBLE PRECISION");
/* 6365 */     rowVal[13] = s2b("-308");
/* 6366 */     rowVal[14] = s2b("308");
/* 6367 */     rowVal[15] = s2b("0");
/* 6368 */     rowVal[16] = s2b("0");
/* 6369 */     rowVal[17] = s2b("10");
/* 6370 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6375 */     rowVal = new byte[18][];
/* 6376 */     rowVal[0] = s2b("REAL");
/* 6377 */     rowVal[1] = Integer.toString(8).getBytes();
/*      */ 
/*      */     
/* 6380 */     rowVal[2] = s2b("17");
/* 6381 */     rowVal[3] = s2b("");
/* 6382 */     rowVal[4] = s2b("");
/* 6383 */     rowVal[5] = s2b("[(M,D)] [ZEROFILL]");
/* 6384 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6388 */     rowVal[7] = s2b("false");
/* 6389 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6393 */     rowVal[9] = s2b("false");
/* 6394 */     rowVal[10] = s2b("false");
/* 6395 */     rowVal[11] = s2b("true");
/* 6396 */     rowVal[12] = s2b("REAL");
/* 6397 */     rowVal[13] = s2b("-308");
/* 6398 */     rowVal[14] = s2b("308");
/* 6399 */     rowVal[15] = s2b("0");
/* 6400 */     rowVal[16] = s2b("0");
/* 6401 */     rowVal[17] = s2b("10");
/* 6402 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6407 */     rowVal = new byte[18][];
/* 6408 */     rowVal[0] = s2b("VARCHAR");
/* 6409 */     rowVal[1] = Integer.toString(12).getBytes();
/*      */ 
/*      */     
/* 6412 */     rowVal[2] = s2b("255");
/* 6413 */     rowVal[3] = s2b("'");
/* 6414 */     rowVal[4] = s2b("'");
/* 6415 */     rowVal[5] = s2b("(M)");
/* 6416 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6420 */     rowVal[7] = s2b("false");
/* 6421 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6425 */     rowVal[9] = s2b("false");
/* 6426 */     rowVal[10] = s2b("false");
/* 6427 */     rowVal[11] = s2b("false");
/* 6428 */     rowVal[12] = s2b("VARCHAR");
/* 6429 */     rowVal[13] = s2b("0");
/* 6430 */     rowVal[14] = s2b("0");
/* 6431 */     rowVal[15] = s2b("0");
/* 6432 */     rowVal[16] = s2b("0");
/* 6433 */     rowVal[17] = s2b("10");
/* 6434 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6439 */     rowVal = new byte[18][];
/* 6440 */     rowVal[0] = s2b("ENUM");
/* 6441 */     rowVal[1] = Integer.toString(12).getBytes();
/*      */ 
/*      */     
/* 6444 */     rowVal[2] = s2b("65535");
/* 6445 */     rowVal[3] = s2b("'");
/* 6446 */     rowVal[4] = s2b("'");
/* 6447 */     rowVal[5] = s2b("");
/* 6448 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6452 */     rowVal[7] = s2b("false");
/* 6453 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6457 */     rowVal[9] = s2b("false");
/* 6458 */     rowVal[10] = s2b("false");
/* 6459 */     rowVal[11] = s2b("false");
/* 6460 */     rowVal[12] = s2b("ENUM");
/* 6461 */     rowVal[13] = s2b("0");
/* 6462 */     rowVal[14] = s2b("0");
/* 6463 */     rowVal[15] = s2b("0");
/* 6464 */     rowVal[16] = s2b("0");
/* 6465 */     rowVal[17] = s2b("10");
/* 6466 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6471 */     rowVal = new byte[18][];
/* 6472 */     rowVal[0] = s2b("SET");
/* 6473 */     rowVal[1] = Integer.toString(12).getBytes();
/*      */ 
/*      */     
/* 6476 */     rowVal[2] = s2b("64");
/* 6477 */     rowVal[3] = s2b("'");
/* 6478 */     rowVal[4] = s2b("'");
/* 6479 */     rowVal[5] = s2b("");
/* 6480 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6484 */     rowVal[7] = s2b("false");
/* 6485 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6489 */     rowVal[9] = s2b("false");
/* 6490 */     rowVal[10] = s2b("false");
/* 6491 */     rowVal[11] = s2b("false");
/* 6492 */     rowVal[12] = s2b("SET");
/* 6493 */     rowVal[13] = s2b("0");
/* 6494 */     rowVal[14] = s2b("0");
/* 6495 */     rowVal[15] = s2b("0");
/* 6496 */     rowVal[16] = s2b("0");
/* 6497 */     rowVal[17] = s2b("10");
/* 6498 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6503 */     rowVal = new byte[18][];
/* 6504 */     rowVal[0] = s2b("DATE");
/* 6505 */     rowVal[1] = Integer.toString(91).getBytes();
/*      */ 
/*      */     
/* 6508 */     rowVal[2] = s2b("0");
/* 6509 */     rowVal[3] = s2b("'");
/* 6510 */     rowVal[4] = s2b("'");
/* 6511 */     rowVal[5] = s2b("");
/* 6512 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6516 */     rowVal[7] = s2b("false");
/* 6517 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6521 */     rowVal[9] = s2b("false");
/* 6522 */     rowVal[10] = s2b("false");
/* 6523 */     rowVal[11] = s2b("false");
/* 6524 */     rowVal[12] = s2b("DATE");
/* 6525 */     rowVal[13] = s2b("0");
/* 6526 */     rowVal[14] = s2b("0");
/* 6527 */     rowVal[15] = s2b("0");
/* 6528 */     rowVal[16] = s2b("0");
/* 6529 */     rowVal[17] = s2b("10");
/* 6530 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6535 */     rowVal = new byte[18][];
/* 6536 */     rowVal[0] = s2b("TIME");
/* 6537 */     rowVal[1] = Integer.toString(92).getBytes();
/*      */ 
/*      */     
/* 6540 */     rowVal[2] = s2b("0");
/* 6541 */     rowVal[3] = s2b("'");
/* 6542 */     rowVal[4] = s2b("'");
/* 6543 */     rowVal[5] = s2b("");
/* 6544 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6548 */     rowVal[7] = s2b("false");
/* 6549 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6553 */     rowVal[9] = s2b("false");
/* 6554 */     rowVal[10] = s2b("false");
/* 6555 */     rowVal[11] = s2b("false");
/* 6556 */     rowVal[12] = s2b("TIME");
/* 6557 */     rowVal[13] = s2b("0");
/* 6558 */     rowVal[14] = s2b("0");
/* 6559 */     rowVal[15] = s2b("0");
/* 6560 */     rowVal[16] = s2b("0");
/* 6561 */     rowVal[17] = s2b("10");
/* 6562 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6567 */     rowVal = new byte[18][];
/* 6568 */     rowVal[0] = s2b("DATETIME");
/* 6569 */     rowVal[1] = Integer.toString(93).getBytes();
/*      */ 
/*      */     
/* 6572 */     rowVal[2] = s2b("0");
/* 6573 */     rowVal[3] = s2b("'");
/* 6574 */     rowVal[4] = s2b("'");
/* 6575 */     rowVal[5] = s2b("");
/* 6576 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6580 */     rowVal[7] = s2b("false");
/* 6581 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6585 */     rowVal[9] = s2b("false");
/* 6586 */     rowVal[10] = s2b("false");
/* 6587 */     rowVal[11] = s2b("false");
/* 6588 */     rowVal[12] = s2b("DATETIME");
/* 6589 */     rowVal[13] = s2b("0");
/* 6590 */     rowVal[14] = s2b("0");
/* 6591 */     rowVal[15] = s2b("0");
/* 6592 */     rowVal[16] = s2b("0");
/* 6593 */     rowVal[17] = s2b("10");
/* 6594 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6599 */     rowVal = new byte[18][];
/* 6600 */     rowVal[0] = s2b("TIMESTAMP");
/* 6601 */     rowVal[1] = Integer.toString(93).getBytes();
/*      */ 
/*      */     
/* 6604 */     rowVal[2] = s2b("0");
/* 6605 */     rowVal[3] = s2b("'");
/* 6606 */     rowVal[4] = s2b("'");
/* 6607 */     rowVal[5] = s2b("[(M)]");
/* 6608 */     rowVal[6] = Integer.toString(1).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6612 */     rowVal[7] = s2b("false");
/* 6613 */     rowVal[8] = Integer.toString(3).getBytes();
/*      */ 
/*      */ 
/*      */     
/* 6617 */     rowVal[9] = s2b("false");
/* 6618 */     rowVal[10] = s2b("false");
/* 6619 */     rowVal[11] = s2b("false");
/* 6620 */     rowVal[12] = s2b("TIMESTAMP");
/* 6621 */     rowVal[13] = s2b("0");
/* 6622 */     rowVal[14] = s2b("0");
/* 6623 */     rowVal[15] = s2b("0");
/* 6624 */     rowVal[16] = s2b("0");
/* 6625 */     rowVal[17] = s2b("10");
/* 6626 */     tuples.add(new ByteArrayRow(rowVal, getExceptionInterceptor()));
/*      */     
/* 6628 */     return buildResultSet(fields, tuples);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
/* 6674 */     Field[] fields = new Field[6];
/* 6675 */     fields[0] = new Field("", "TYPE_CAT", 12, 32);
/* 6676 */     fields[1] = new Field("", "TYPE_SCHEM", 12, 32);
/* 6677 */     fields[2] = new Field("", "TYPE_NAME", 12, 32);
/* 6678 */     fields[3] = new Field("", "CLASS_NAME", 12, 32);
/* 6679 */     fields[4] = new Field("", "DATA_TYPE", 12, 32);
/* 6680 */     fields[5] = new Field("", "REMARKS", 12, 32);
/*      */     
/* 6682 */     ArrayList tuples = new ArrayList();
/*      */     
/* 6684 */     return buildResultSet(fields, tuples);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getURL() throws SQLException {
/* 6695 */     return this.conn.getURL();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserName() throws SQLException {
/* 6706 */     if (this.conn.getUseHostsInPrivileges()) {
/* 6707 */       Statement stmt = null;
/* 6708 */       ResultSet rs = null;
/*      */       
/*      */       try {
/* 6711 */         stmt = this.conn.createStatement();
/* 6712 */         stmt.setEscapeProcessing(false);
/*      */         
/* 6714 */         rs = stmt.executeQuery("SELECT USER()");
/* 6715 */         rs.next();
/*      */         
/* 6717 */         return rs.getString(1);
/*      */       } finally {
/* 6719 */         if (rs != null) {
/*      */           try {
/* 6721 */             rs.close();
/* 6722 */           } catch (Exception ex) {
/* 6723 */             AssertionFailedException.shouldNotHappen(ex);
/*      */           } 
/*      */           
/* 6726 */           rs = null;
/*      */         } 
/*      */         
/* 6729 */         if (stmt != null) {
/*      */           try {
/* 6731 */             stmt.close();
/* 6732 */           } catch (Exception ex) {
/* 6733 */             AssertionFailedException.shouldNotHappen(ex);
/*      */           } 
/*      */           
/* 6736 */           stmt = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 6741 */     return this.conn.getUser();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
/* 6780 */     Field[] fields = new Field[8];
/* 6781 */     fields[0] = new Field("", "SCOPE", 5, 5);
/* 6782 */     fields[1] = new Field("", "COLUMN_NAME", 1, 32);
/* 6783 */     fields[2] = new Field("", "DATA_TYPE", 4, 5);
/* 6784 */     fields[3] = new Field("", "TYPE_NAME", 1, 16);
/* 6785 */     fields[4] = new Field("", "COLUMN_SIZE", 4, 16);
/* 6786 */     fields[5] = new Field("", "BUFFER_LENGTH", 4, 16);
/* 6787 */     fields[6] = new Field("", "DECIMAL_DIGITS", 5, 16);
/* 6788 */     fields[7] = new Field("", "PSEUDO_COLUMN", 5, 5);
/*      */     
/* 6790 */     return buildResultSet(fields, new ArrayList());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean insertsAreDetected(int type) throws SQLException {
/* 6806 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCatalogAtStart() throws SQLException {
/* 6818 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() throws SQLException {
/* 6829 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean locatorsUpdateCopy() throws SQLException {
/* 6836 */     return !this.conn.getEmulateLocators();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nullPlusNonNullIsNull() throws SQLException {
/* 6848 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nullsAreSortedAtEnd() throws SQLException {
/* 6859 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nullsAreSortedAtStart() throws SQLException {
/* 6870 */     return (this.conn.versionMeetsMinimum(4, 0, 2) && !this.conn.versionMeetsMinimum(4, 0, 11));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nullsAreSortedHigh() throws SQLException {
/* 6882 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nullsAreSortedLow() throws SQLException {
/* 6893 */     return !nullsAreSortedHigh();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean othersDeletesAreVisible(int type) throws SQLException {
/* 6906 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean othersInsertsAreVisible(int type) throws SQLException {
/* 6919 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean othersUpdatesAreVisible(int type) throws SQLException {
/* 6932 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean ownDeletesAreVisible(int type) throws SQLException {
/* 6945 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean ownInsertsAreVisible(int type) throws SQLException {
/* 6958 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean ownUpdatesAreVisible(int type) throws SQLException {
/* 6971 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LocalAndReferencedColumns parseTableStatusIntoLocalAndReferencedColumns(String keysComment) throws SQLException {
/* 6992 */     String columnsDelimitter = ",";
/*      */     
/* 6994 */     char quoteChar = (this.quotedId.length() == 0) ? Character.MIN_VALUE : this.quotedId.charAt(0);
/*      */ 
/*      */     
/* 6997 */     int indexOfOpenParenLocalColumns = StringUtils.indexOfIgnoreCaseRespectQuotes(0, keysComment, "(", quoteChar, true);
/*      */ 
/*      */ 
/*      */     
/* 7001 */     if (indexOfOpenParenLocalColumns == -1) {
/* 7002 */       throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find start of local columns list.", "S1000", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7007 */     String constraintName = removeQuotedId(keysComment.substring(0, indexOfOpenParenLocalColumns).trim());
/*      */     
/* 7009 */     keysComment = keysComment.substring(indexOfOpenParenLocalColumns, keysComment.length());
/*      */ 
/*      */     
/* 7012 */     String keysCommentTrimmed = keysComment.trim();
/*      */     
/* 7014 */     int indexOfCloseParenLocalColumns = StringUtils.indexOfIgnoreCaseRespectQuotes(0, keysCommentTrimmed, ")", quoteChar, true);
/*      */ 
/*      */ 
/*      */     
/* 7018 */     if (indexOfCloseParenLocalColumns == -1) {
/* 7019 */       throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find end of local columns list.", "S1000", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7024 */     String localColumnNamesString = keysCommentTrimmed.substring(1, indexOfCloseParenLocalColumns);
/*      */ 
/*      */     
/* 7027 */     int indexOfRefer = StringUtils.indexOfIgnoreCaseRespectQuotes(0, keysCommentTrimmed, "REFER ", this.quotedId.charAt(0), true);
/*      */ 
/*      */     
/* 7030 */     if (indexOfRefer == -1) {
/* 7031 */       throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find start of referenced tables list.", "S1000", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7036 */     int indexOfOpenParenReferCol = StringUtils.indexOfIgnoreCaseRespectQuotes(indexOfRefer, keysCommentTrimmed, "(", quoteChar, false);
/*      */ 
/*      */ 
/*      */     
/* 7040 */     if (indexOfOpenParenReferCol == -1) {
/* 7041 */       throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find start of referenced columns list.", "S1000", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7046 */     String referCatalogTableString = keysCommentTrimmed.substring(indexOfRefer + "REFER ".length(), indexOfOpenParenReferCol);
/*      */ 
/*      */     
/* 7049 */     int indexOfSlash = StringUtils.indexOfIgnoreCaseRespectQuotes(0, referCatalogTableString, "/", this.quotedId.charAt(0), false);
/*      */ 
/*      */     
/* 7052 */     if (indexOfSlash == -1) {
/* 7053 */       throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find name of referenced catalog.", "S1000", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7058 */     String referCatalog = removeQuotedId(referCatalogTableString.substring(0, indexOfSlash));
/*      */     
/* 7060 */     String referTable = removeQuotedId(referCatalogTableString.substring(indexOfSlash + 1).trim());
/*      */ 
/*      */     
/* 7063 */     int indexOfCloseParenRefer = StringUtils.indexOfIgnoreCaseRespectQuotes(indexOfOpenParenReferCol, keysCommentTrimmed, ")", quoteChar, true);
/*      */ 
/*      */ 
/*      */     
/* 7067 */     if (indexOfCloseParenRefer == -1) {
/* 7068 */       throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find end of referenced columns list.", "S1000", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7073 */     String referColumnNamesString = keysCommentTrimmed.substring(indexOfOpenParenReferCol + 1, indexOfCloseParenRefer);
/*      */ 
/*      */     
/* 7076 */     List<String> referColumnsList = StringUtils.split(referColumnNamesString, columnsDelimitter, this.quotedId, this.quotedId, false);
/*      */     
/* 7078 */     List<String> localColumnsList = StringUtils.split(localColumnNamesString, columnsDelimitter, this.quotedId, this.quotedId, false);
/*      */ 
/*      */     
/* 7081 */     return new LocalAndReferencedColumns(localColumnsList, referColumnsList, constraintName, referCatalog, referTable);
/*      */   }
/*      */ 
/*      */   
/*      */   private String removeQuotedId(String s) {
/* 7086 */     if (s == null) {
/* 7087 */       return null;
/*      */     }
/*      */     
/* 7090 */     if (this.quotedId.equals("")) {
/* 7091 */       return s;
/*      */     }
/*      */     
/* 7094 */     s = s.trim();
/*      */     
/* 7096 */     int frontOffset = 0;
/* 7097 */     int backOffset = s.length();
/* 7098 */     int quoteLength = this.quotedId.length();
/*      */     
/* 7100 */     if (s.startsWith(this.quotedId)) {
/* 7101 */       frontOffset = quoteLength;
/*      */     }
/*      */     
/* 7104 */     if (s.endsWith(this.quotedId)) {
/* 7105 */       backOffset -= quoteLength;
/*      */     }
/*      */     
/* 7108 */     return s.substring(frontOffset, backOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] s2b(String s) throws SQLException {
/* 7120 */     if (s == null) {
/* 7121 */       return null;
/*      */     }
/*      */     
/* 7124 */     return StringUtils.getBytes(s, this.conn.getCharacterSetMetadata(), this.conn.getServerCharacterEncoding(), this.conn.parserKnowsUnicode(), this.conn, getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesLowerCaseIdentifiers() throws SQLException {
/* 7138 */     return this.conn.storesLowerCaseTableName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
/* 7150 */     return this.conn.storesLowerCaseTableName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesMixedCaseIdentifiers() throws SQLException {
/* 7162 */     return !this.conn.storesLowerCaseTableName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
/* 7173 */     return !this.conn.storesLowerCaseTableName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesUpperCaseIdentifiers() throws SQLException {
/* 7185 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
/* 7197 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsAlterTableWithAddColumn() throws SQLException {
/* 7208 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsAlterTableWithDropColumn() throws SQLException {
/* 7219 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsANSI92EntryLevelSQL() throws SQLException {
/* 7231 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsANSI92FullSQL() throws SQLException {
/* 7242 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsANSI92IntermediateSQL() throws SQLException {
/* 7253 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsBatchUpdates() throws SQLException {
/* 7265 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCatalogsInDataManipulation() throws SQLException {
/* 7277 */     return this.conn.versionMeetsMinimum(3, 22, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
/* 7289 */     return this.conn.versionMeetsMinimum(3, 22, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
/* 7301 */     return this.conn.versionMeetsMinimum(3, 22, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCatalogsInProcedureCalls() throws SQLException {
/* 7313 */     return this.conn.versionMeetsMinimum(3, 22, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCatalogsInTableDefinitions() throws SQLException {
/* 7325 */     return this.conn.versionMeetsMinimum(3, 22, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsColumnAliasing() throws SQLException {
/* 7341 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsConvert() throws SQLException {
/* 7352 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsConvert(int fromType, int toType) throws SQLException {
/* 7369 */     switch (fromType) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/*      */       case -1:
/*      */       case 1:
/*      */       case 12:
/* 7380 */         switch (toType) {
/*      */           case -6:
/*      */           case -5:
/*      */           case -4:
/*      */           case -3:
/*      */           case -2:
/*      */           case -1:
/*      */           case 1:
/*      */           case 2:
/*      */           case 3:
/*      */           case 4:
/*      */           case 5:
/*      */           case 6:
/*      */           case 7:
/*      */           case 8:
/*      */           case 12:
/*      */           case 91:
/*      */           case 92:
/*      */           case 93:
/*      */           case 1111:
/* 7400 */             return true;
/*      */         } 
/*      */         
/* 7403 */         return false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case -7:
/* 7410 */         return false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case -6:
/*      */       case -5:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/* 7426 */         switch (toType) {
/*      */           case -6:
/*      */           case -5:
/*      */           case -4:
/*      */           case -3:
/*      */           case -2:
/*      */           case -1:
/*      */           case 1:
/*      */           case 2:
/*      */           case 3:
/*      */           case 4:
/*      */           case 5:
/*      */           case 6:
/*      */           case 7:
/*      */           case 8:
/*      */           case 12:
/* 7442 */             return true;
/*      */         } 
/*      */         
/* 7445 */         return false;
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/* 7450 */         return false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1111:
/* 7458 */         switch (toType) {
/*      */           case -4:
/*      */           case -3:
/*      */           case -2:
/*      */           case -1:
/*      */           case 1:
/*      */           case 12:
/* 7465 */             return true;
/*      */         } 
/*      */         
/* 7468 */         return false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 91:
/* 7474 */         switch (toType) {
/*      */           case -4:
/*      */           case -3:
/*      */           case -2:
/*      */           case -1:
/*      */           case 1:
/*      */           case 12:
/* 7481 */             return true;
/*      */         } 
/*      */         
/* 7484 */         return false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 92:
/* 7490 */         switch (toType) {
/*      */           case -4:
/*      */           case -3:
/*      */           case -2:
/*      */           case -1:
/*      */           case 1:
/*      */           case 12:
/* 7497 */             return true;
/*      */         } 
/*      */         
/* 7500 */         return false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 93:
/* 7509 */         switch (toType) {
/*      */           case -4:
/*      */           case -3:
/*      */           case -2:
/*      */           case -1:
/*      */           case 1:
/*      */           case 12:
/*      */           case 91:
/*      */           case 92:
/* 7518 */             return true;
/*      */         } 
/*      */         
/* 7521 */         return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 7526 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCoreSQLGrammar() throws SQLException {
/* 7538 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCorrelatedSubqueries() throws SQLException {
/* 7550 */     return this.conn.versionMeetsMinimum(4, 1, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
/* 7563 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
/* 7575 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsDifferentTableCorrelationNames() throws SQLException {
/* 7588 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsExpressionsInOrderBy() throws SQLException {
/* 7599 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsExtendedSQLGrammar() throws SQLException {
/* 7610 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsFullOuterJoins() throws SQLException {
/* 7621 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsGetGeneratedKeys() {
/* 7630 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsGroupBy() throws SQLException {
/* 7641 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsGroupByBeyondSelect() throws SQLException {
/* 7653 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsGroupByUnrelated() throws SQLException {
/* 7664 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsIntegrityEnhancementFacility() throws SQLException {
/* 7675 */     if (!this.conn.getOverrideSupportsIntegrityEnhancementFacility()) {
/* 7676 */       return false;
/*      */     }
/*      */     
/* 7679 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsLikeEscapeClause() throws SQLException {
/* 7691 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsLimitedOuterJoins() throws SQLException {
/* 7703 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsMinimumSQLGrammar() throws SQLException {
/* 7715 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsMixedCaseIdentifiers() throws SQLException {
/* 7726 */     return !this.conn.lowerCaseTableNames();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
/* 7738 */     return !this.conn.lowerCaseTableNames();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsMultipleOpenResults() throws SQLException {
/* 7745 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsMultipleResultSets() throws SQLException {
/* 7756 */     return this.conn.versionMeetsMinimum(4, 1, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsMultipleTransactions() throws SQLException {
/* 7768 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsNamedParameters() throws SQLException {
/* 7775 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsNonNullableColumns() throws SQLException {
/* 7787 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
/* 7799 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
/* 7811 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
/* 7823 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
/* 7835 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsOrderByUnrelated() throws SQLException {
/* 7846 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsOuterJoins() throws SQLException {
/* 7857 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsPositionedDelete() throws SQLException {
/* 7868 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsPositionedUpdate() throws SQLException {
/* 7879 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
/* 7897 */     switch (type) {
/*      */       case 1004:
/* 7899 */         if (concurrency == 1007 || concurrency == 1008)
/*      */         {
/* 7901 */           return true;
/*      */         }
/* 7903 */         throw SQLError.createSQLException("Illegal arguments to supportsResultSetConcurrency()", "S1009", getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */       
/*      */       case 1003:
/* 7908 */         if (concurrency == 1007 || concurrency == 1008)
/*      */         {
/* 7910 */           return true;
/*      */         }
/* 7912 */         throw SQLError.createSQLException("Illegal arguments to supportsResultSetConcurrency()", "S1009", getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */       
/*      */       case 1005:
/* 7917 */         return false;
/*      */     } 
/* 7919 */     throw SQLError.createSQLException("Illegal arguments to supportsResultSetConcurrency()", "S1009", getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsResultSetHoldability(int holdability) throws SQLException {
/* 7931 */     return (holdability == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsResultSetType(int type) throws SQLException {
/* 7945 */     return (type == 1004);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSavepoints() throws SQLException {
/* 7953 */     return (this.conn.versionMeetsMinimum(4, 0, 14) || this.conn.versionMeetsMinimum(4, 1, 1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSchemasInDataManipulation() throws SQLException {
/* 7965 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSchemasInIndexDefinitions() throws SQLException {
/* 7976 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
/* 7987 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSchemasInProcedureCalls() throws SQLException {
/* 7998 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSchemasInTableDefinitions() throws SQLException {
/* 8009 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSelectForUpdate() throws SQLException {
/* 8020 */     return this.conn.versionMeetsMinimum(4, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsStatementPooling() throws SQLException {
/* 8027 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsStoredProcedures() throws SQLException {
/* 8039 */     return this.conn.versionMeetsMinimum(5, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSubqueriesInComparisons() throws SQLException {
/* 8051 */     return this.conn.versionMeetsMinimum(4, 1, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSubqueriesInExists() throws SQLException {
/* 8063 */     return this.conn.versionMeetsMinimum(4, 1, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSubqueriesInIns() throws SQLException {
/* 8075 */     return this.conn.versionMeetsMinimum(4, 1, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSubqueriesInQuantifieds() throws SQLException {
/* 8087 */     return this.conn.versionMeetsMinimum(4, 1, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsTableCorrelationNames() throws SQLException {
/* 8099 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
/* 8114 */     if (this.conn.supportsIsolationLevel()) {
/* 8115 */       switch (level) {
/*      */         case 1:
/*      */         case 2:
/*      */         case 4:
/*      */         case 8:
/* 8120 */           return true;
/*      */       } 
/*      */       
/* 8123 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 8127 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsTransactions() throws SQLException {
/* 8139 */     return this.conn.supportsTransactions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsUnion() throws SQLException {
/* 8150 */     return this.conn.versionMeetsMinimum(4, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsUnionAll() throws SQLException {
/* 8161 */     return this.conn.versionMeetsMinimum(4, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean updatesAreDetected(int type) throws SQLException {
/* 8175 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean usesLocalFilePerTable() throws SQLException {
/* 8186 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean usesLocalFiles() throws SQLException {
/* 8197 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
/* 8213 */     Field[] fields = createFunctionColumnsFields();
/*      */     
/* 8215 */     return getProcedureOrFunctionColumns(fields, catalog, schemaPattern, functionNamePattern, columnNamePattern, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Field[] createFunctionColumnsFields() {
/* 8222 */     Field[] fields = { new Field("", "FUNCTION_CAT", 12, 512), new Field("", "FUNCTION_SCHEM", 12, 512), new Field("", "FUNCTION_NAME", 12, 512), new Field("", "COLUMN_NAME", 12, 512), new Field("", "COLUMN_TYPE", 12, 64), new Field("", "DATA_TYPE", 5, 6), new Field("", "TYPE_NAME", 12, 64), new Field("", "PRECISION", 4, 12), new Field("", "LENGTH", 4, 12), new Field("", "SCALE", 5, 12), new Field("", "RADIX", 5, 6), new Field("", "NULLABLE", 5, 6), new Field("", "REMARKS", 12, 512), new Field("", "CHAR_OCTET_LENGTH", 4, 32), new Field("", "ORDINAL_POSITION", 4, 32), new Field("", "IS_NULLABLE", 12, 12), new Field("", "SPECIFIC_NAME", 12, 64) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 8240 */     return fields;
/*      */   }
/*      */   
/*      */   public boolean providesQueryObjectGenerator() throws SQLException {
/* 8244 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
/* 8249 */     Field[] fields = { new Field("", "TABLE_SCHEM", 12, 255), new Field("", "TABLE_CATALOG", 12, 255) };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 8254 */     return buildResultSet(fields, new ArrayList());
/*      */   }
/*      */   
/*      */   public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
/* 8258 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PreparedStatement prepareMetaDataSafeStatement(String sql) throws SQLException {
/* 8271 */     PreparedStatement pStmt = this.conn.clientPrepareStatement(sql);
/*      */     
/* 8273 */     if (pStmt.getMaxRows() != 0) {
/* 8274 */       pStmt.setMaxRows(0);
/*      */     }
/*      */     
/* 8277 */     ((Statement)pStmt).setHoldResultsOpenOverClose(true);
/*      */     
/* 8279 */     return pStmt;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\DatabaseMetaData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */