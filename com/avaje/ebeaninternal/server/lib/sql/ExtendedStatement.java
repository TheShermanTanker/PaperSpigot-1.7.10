/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import com.avaje.ebeaninternal.jdbc.PreparedStatementDelegator;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ExtendedStatement
/*     */   extends PreparedStatementDelegator
/*     */ {
/*     */   protected final PooledConnection pooledConnection;
/*     */   protected final PreparedStatement pstmt;
/*     */   
/*     */   public ExtendedStatement(PooledConnection pooledConnection, PreparedStatement pstmt) {
/*  53 */     super(pstmt);
/*     */     
/*  55 */     this.pooledConnection = pooledConnection;
/*  56 */     this.pstmt = pstmt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void close() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/*     */     try {
/*  69 */       return this.pstmt.getConnection();
/*  70 */     } catch (SQLException e) {
/*  71 */       this.pooledConnection.addError(e);
/*  72 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch(String sql) throws SQLException {
/*     */     try {
/*  81 */       this.pooledConnection.setLastStatement(sql);
/*  82 */       this.pstmt.addBatch(sql);
/*  83 */     } catch (SQLException e) {
/*  84 */       this.pooledConnection.addError(e);
/*  85 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String sql) throws SQLException {
/*     */     try {
/*  94 */       this.pooledConnection.setLastStatement(sql);
/*  95 */       return this.pstmt.execute(sql);
/*  96 */     } catch (SQLException e) {
/*  97 */       this.pooledConnection.addError(e);
/*  98 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery(String sql) throws SQLException {
/*     */     try {
/* 107 */       this.pooledConnection.setLastStatement(sql);
/* 108 */       return this.pstmt.executeQuery(sql);
/* 109 */     } catch (SQLException e) {
/* 110 */       this.pooledConnection.addError(e);
/* 111 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String sql) throws SQLException {
/*     */     try {
/* 120 */       this.pooledConnection.setLastStatement(sql);
/* 121 */       return this.pstmt.executeUpdate(sql);
/* 122 */     } catch (SQLException e) {
/* 123 */       this.pooledConnection.addError(e);
/* 124 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] executeBatch() throws SQLException {
/* 132 */     return this.pstmt.executeBatch();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() throws SQLException {
/* 139 */     this.pstmt.cancel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearBatch() throws SQLException {
/* 146 */     this.pstmt.clearBatch();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearWarnings() throws SQLException {
/* 153 */     this.pstmt.clearWarnings();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFetchDirection() throws SQLException {
/* 160 */     return this.pstmt.getFetchDirection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFetchSize() throws SQLException {
/* 167 */     return this.pstmt.getFetchSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxFieldSize() throws SQLException {
/* 174 */     return this.pstmt.getMaxFieldSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRows() throws SQLException {
/* 181 */     return this.pstmt.getMaxRows();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMoreResults() throws SQLException {
/* 188 */     return this.pstmt.getMoreResults();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQueryTimeout() throws SQLException {
/* 195 */     return this.pstmt.getQueryTimeout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getResultSet() throws SQLException {
/* 202 */     return this.pstmt.getResultSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetConcurrency() throws SQLException {
/* 209 */     return this.pstmt.getResultSetConcurrency();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetType() throws SQLException {
/* 216 */     return this.pstmt.getResultSetType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpdateCount() throws SQLException {
/* 223 */     return this.pstmt.getUpdateCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLWarning getWarnings() throws SQLException {
/* 230 */     return this.pstmt.getWarnings();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorName(String name) throws SQLException {
/* 237 */     this.pstmt.setCursorName(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEscapeProcessing(boolean enable) throws SQLException {
/* 244 */     this.pstmt.setEscapeProcessing(enable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchDirection(int direction) throws SQLException {
/* 251 */     this.pstmt.setFetchDirection(direction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchSize(int rows) throws SQLException {
/* 258 */     this.pstmt.setFetchSize(rows);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxFieldSize(int max) throws SQLException {
/* 265 */     this.pstmt.setMaxFieldSize(max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxRows(int max) throws SQLException {
/* 272 */     this.pstmt.setMaxRows(max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQueryTimeout(int seconds) throws SQLException {
/* 279 */     this.pstmt.setQueryTimeout(seconds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMoreResults(int i) throws SQLException {
/* 286 */     return this.pstmt.getMoreResults(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getGeneratedKeys() throws SQLException {
/* 293 */     return this.pstmt.getGeneratedKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String s, int i) throws SQLException {
/* 300 */     return this.pstmt.executeUpdate(s, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String s, int[] i) throws SQLException {
/* 307 */     return this.pstmt.executeUpdate(s, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String s, String[] i) throws SQLException {
/* 314 */     return this.pstmt.executeUpdate(s, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String s, int i) throws SQLException {
/* 321 */     return this.pstmt.execute(s, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String s, int[] i) throws SQLException {
/* 328 */     return this.pstmt.execute(s, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String s, String[] i) throws SQLException {
/* 335 */     return this.pstmt.execute(s, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetHoldability() throws SQLException {
/* 342 */     return this.pstmt.getResultSetHoldability();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\ExtendedStatement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */