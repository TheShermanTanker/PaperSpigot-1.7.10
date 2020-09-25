/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.rmi.server.UID;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Savepoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MysqlSavepoint
/*     */   implements Savepoint
/*     */ {
/*     */   private String savepointName;
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */   
/*     */   private static String getUniqueId() {
/*  42 */     String uidStr = (new UID()).toString();
/*     */     
/*  44 */     int uidLength = uidStr.length();
/*     */     
/*  46 */     StringBuffer safeString = new StringBuffer(uidLength);
/*     */     
/*  48 */     for (int i = 0; i < uidLength; i++) {
/*  49 */       char c = uidStr.charAt(i);
/*     */       
/*  51 */       if (Character.isLetter(c) || Character.isDigit(c)) {
/*  52 */         safeString.append(c);
/*     */       } else {
/*  54 */         safeString.append('_');
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     return safeString.toString();
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
/*     */   MysqlSavepoint(ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*  74 */     this(getUniqueId(), exceptionInterceptor);
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
/*     */   MysqlSavepoint(String name, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*  87 */     if (name == null || name.length() == 0) {
/*  88 */       throw SQLError.createSQLException("Savepoint name can not be NULL or empty", "S1009", exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/*  92 */     this.savepointName = name;
/*     */     
/*  94 */     this.exceptionInterceptor = exceptionInterceptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSavepointId() throws SQLException {
/* 101 */     throw SQLError.createSQLException("Only named savepoints are supported.", "S1C00", this.exceptionInterceptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSavepointName() throws SQLException {
/* 109 */     return this.savepointName;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\MysqlSavepoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */