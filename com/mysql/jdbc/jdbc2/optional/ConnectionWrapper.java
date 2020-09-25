/*      */ package com.mysql.jdbc.jdbc2.optional;
/*      */ 
/*      */ import com.mysql.jdbc.Connection;
/*      */ import com.mysql.jdbc.ExceptionInterceptor;
/*      */ import com.mysql.jdbc.Extension;
/*      */ import com.mysql.jdbc.MySQLConnection;
/*      */ import com.mysql.jdbc.SQLError;
/*      */ import com.mysql.jdbc.Util;
/*      */ import com.mysql.jdbc.log.Log;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Savepoint;
/*      */ import java.sql.Statement;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.TimeZone;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConnectionWrapper
/*      */   extends WrapperBase
/*      */   implements Connection
/*      */ {
/*   68 */   protected Connection mc = null;
/*      */   
/*   70 */   private String invalidHandleStr = "Logical handle no longer valid";
/*      */   
/*      */   private boolean closed;
/*      */   
/*      */   private boolean isForXa;
/*      */   
/*      */   private static final Constructor JDBC_4_CONNECTION_WRAPPER_CTOR;
/*      */   
/*      */   static {
/*   79 */     if (Util.isJdbc4()) {
/*      */       try {
/*   81 */         JDBC_4_CONNECTION_WRAPPER_CTOR = Class.forName("com.mysql.jdbc.jdbc2.optional.JDBC4ConnectionWrapper").getConstructor(new Class[] { MysqlPooledConnection.class, Connection.class, boolean.class });
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*   86 */       catch (SecurityException e) {
/*   87 */         throw new RuntimeException(e);
/*   88 */       } catch (NoSuchMethodException e) {
/*   89 */         throw new RuntimeException(e);
/*   90 */       } catch (ClassNotFoundException e) {
/*   91 */         throw new RuntimeException(e);
/*      */       } 
/*      */     } else {
/*   94 */       JDBC_4_CONNECTION_WRAPPER_CTOR = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static ConnectionWrapper getInstance(MysqlPooledConnection mysqlPooledConnection, Connection mysqlConnection, boolean forXa) throws SQLException {
/*  101 */     if (!Util.isJdbc4()) {
/*  102 */       return new ConnectionWrapper(mysqlPooledConnection, mysqlConnection, forXa);
/*      */     }
/*      */ 
/*      */     
/*  106 */     return (ConnectionWrapper)Util.handleNewInstance(JDBC_4_CONNECTION_WRAPPER_CTOR, new Object[] { mysqlPooledConnection, mysqlConnection, Boolean.valueOf(forXa) }, mysqlPooledConnection.getExceptionInterceptor());
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
/*      */   public ConnectionWrapper(MysqlPooledConnection mysqlPooledConnection, Connection mysqlConnection, boolean forXa) throws SQLException {
/*  125 */     super(mysqlPooledConnection);
/*      */     
/*  127 */     this.mc = mysqlConnection;
/*  128 */     this.closed = false;
/*  129 */     this.isForXa = forXa;
/*      */     
/*  131 */     if (this.isForXa) {
/*  132 */       setInGlobalTx(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoCommit(boolean autoCommit) throws SQLException {
/*  143 */     checkClosed();
/*      */     
/*  145 */     if (autoCommit && isInGlobalTx()) {
/*  146 */       throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  153 */       this.mc.setAutoCommit(autoCommit);
/*  154 */     } catch (SQLException sqlException) {
/*  155 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoCommit() throws SQLException {
/*  166 */     checkClosed();
/*      */     
/*      */     try {
/*  169 */       return this.mc.getAutoCommit();
/*  170 */     } catch (SQLException sqlException) {
/*  171 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  174 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCatalog(String catalog) throws SQLException {
/*  184 */     checkClosed();
/*      */     
/*      */     try {
/*  187 */       this.mc.setCatalog(catalog);
/*  188 */     } catch (SQLException sqlException) {
/*  189 */       checkAndFireConnectionError(sqlException);
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
/*      */   public String getCatalog() throws SQLException {
/*  203 */     checkClosed();
/*      */     
/*      */     try {
/*  206 */       return this.mc.getCatalog();
/*  207 */     } catch (SQLException sqlException) {
/*  208 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  211 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isClosed() throws SQLException {
/*  221 */     return (this.closed || this.mc.isClosed());
/*      */   }
/*      */   
/*      */   public boolean isMasterConnection() {
/*  225 */     return this.mc.isMasterConnection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHoldability(int arg0) throws SQLException {
/*  232 */     checkClosed();
/*      */     
/*      */     try {
/*  235 */       this.mc.setHoldability(arg0);
/*  236 */     } catch (SQLException sqlException) {
/*  237 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHoldability() throws SQLException {
/*  245 */     checkClosed();
/*      */     
/*      */     try {
/*  248 */       return this.mc.getHoldability();
/*  249 */     } catch (SQLException sqlException) {
/*  250 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  253 */       return 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getIdleFor() {
/*  263 */     return this.mc.getIdleFor();
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
/*      */   public DatabaseMetaData getMetaData() throws SQLException {
/*  276 */     checkClosed();
/*      */     
/*      */     try {
/*  279 */       return this.mc.getMetaData();
/*  280 */     } catch (SQLException sqlException) {
/*  281 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  284 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadOnly(boolean readOnly) throws SQLException {
/*  294 */     checkClosed();
/*      */     
/*      */     try {
/*  297 */       this.mc.setReadOnly(readOnly);
/*  298 */     } catch (SQLException sqlException) {
/*  299 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() throws SQLException {
/*  310 */     checkClosed();
/*      */     
/*      */     try {
/*  313 */       return this.mc.isReadOnly();
/*  314 */     } catch (SQLException sqlException) {
/*  315 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  318 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Savepoint setSavepoint() throws SQLException {
/*  325 */     checkClosed();
/*      */     
/*  327 */     if (isInGlobalTx()) {
/*  328 */       throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  335 */       return this.mc.setSavepoint();
/*  336 */     } catch (SQLException sqlException) {
/*  337 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  340 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Savepoint setSavepoint(String arg0) throws SQLException {
/*  347 */     checkClosed();
/*      */     
/*  349 */     if (isInGlobalTx()) {
/*  350 */       throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  357 */       return this.mc.setSavepoint(arg0);
/*  358 */     } catch (SQLException sqlException) {
/*  359 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  362 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransactionIsolation(int level) throws SQLException {
/*  372 */     checkClosed();
/*      */     
/*      */     try {
/*  375 */       this.mc.setTransactionIsolation(level);
/*  376 */     } catch (SQLException sqlException) {
/*  377 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTransactionIsolation() throws SQLException {
/*  388 */     checkClosed();
/*      */     
/*      */     try {
/*  391 */       return this.mc.getTransactionIsolation();
/*  392 */     } catch (SQLException sqlException) {
/*  393 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  396 */       return 4;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTypeMap(Map map) throws SQLException {
/*  407 */     checkClosed();
/*      */     
/*      */     try {
/*  410 */       this.mc.setTypeMap(map);
/*  411 */     } catch (SQLException sqlException) {
/*  412 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map getTypeMap() throws SQLException {
/*  423 */     checkClosed();
/*      */     
/*      */     try {
/*  426 */       return this.mc.getTypeMap();
/*  427 */     } catch (SQLException sqlException) {
/*  428 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  431 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SQLWarning getWarnings() throws SQLException {
/*  441 */     checkClosed();
/*      */     
/*      */     try {
/*  444 */       return this.mc.getWarnings();
/*  445 */     } catch (SQLException sqlException) {
/*  446 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  449 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearWarnings() throws SQLException {
/*  460 */     checkClosed();
/*      */     
/*      */     try {
/*  463 */       this.mc.clearWarnings();
/*  464 */     } catch (SQLException sqlException) {
/*  465 */       checkAndFireConnectionError(sqlException);
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
/*      */   public void close() throws SQLException {
/*  480 */     close(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void commit() throws SQLException {
/*  491 */     checkClosed();
/*      */     
/*  493 */     if (isInGlobalTx()) {
/*  494 */       throw SQLError.createSQLException("Can't call commit() on an XAConnection associated with a global transaction", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  502 */       this.mc.commit();
/*  503 */     } catch (SQLException sqlException) {
/*  504 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement() throws SQLException {
/*  515 */     checkClosed();
/*      */     
/*      */     try {
/*  518 */       return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement());
/*      */     }
/*  520 */     catch (SQLException sqlException) {
/*  521 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  524 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
/*  535 */     checkClosed();
/*      */     
/*      */     try {
/*  538 */       return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement(resultSetType, resultSetConcurrency));
/*      */     }
/*  540 */     catch (SQLException sqlException) {
/*  541 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  544 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
/*  552 */     checkClosed();
/*      */     
/*      */     try {
/*  555 */       return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement(arg0, arg1, arg2));
/*      */     }
/*  557 */     catch (SQLException sqlException) {
/*  558 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  561 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nativeSQL(String sql) throws SQLException {
/*  571 */     checkClosed();
/*      */     
/*      */     try {
/*  574 */       return this.mc.nativeSQL(sql);
/*  575 */     } catch (SQLException sqlException) {
/*  576 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  579 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String sql) throws SQLException {
/*  590 */     checkClosed();
/*      */     
/*      */     try {
/*  593 */       return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(sql));
/*      */     }
/*  595 */     catch (SQLException sqlException) {
/*  596 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  599 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*  610 */     checkClosed();
/*      */     
/*      */     try {
/*  613 */       return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(sql, resultSetType, resultSetConcurrency));
/*      */     }
/*  615 */     catch (SQLException sqlException) {
/*  616 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  619 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
/*  627 */     checkClosed();
/*      */     
/*      */     try {
/*  630 */       return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(arg0, arg1, arg2, arg3));
/*      */     }
/*  632 */     catch (SQLException sqlException) {
/*  633 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  636 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepare(String sql) throws SQLException {
/*  641 */     checkClosed();
/*      */     
/*      */     try {
/*  644 */       return new PreparedStatementWrapper(this, this.pooledConnection, this.mc.clientPrepareStatement(sql));
/*      */     }
/*  646 */     catch (SQLException sqlException) {
/*  647 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  650 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepare(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*  655 */     checkClosed();
/*      */     
/*      */     try {
/*  658 */       return new PreparedStatementWrapper(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency));
/*      */     
/*      */     }
/*  661 */     catch (SQLException sqlException) {
/*  662 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  665 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql) throws SQLException {
/*  676 */     checkClosed();
/*      */     
/*      */     try {
/*  679 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(sql));
/*      */     }
/*  681 */     catch (SQLException sqlException) {
/*  682 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  685 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*  696 */     checkClosed();
/*      */     
/*      */     try {
/*  699 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(sql, resultSetType, resultSetConcurrency));
/*      */     
/*      */     }
/*  702 */     catch (SQLException sqlException) {
/*  703 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  706 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
/*  714 */     checkClosed();
/*      */     
/*      */     try {
/*  717 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1, arg2, arg3));
/*      */     }
/*  719 */     catch (SQLException sqlException) {
/*  720 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  723 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
/*  731 */     checkClosed();
/*      */     
/*      */     try {
/*  734 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
/*      */     }
/*  736 */     catch (SQLException sqlException) {
/*  737 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  740 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
/*  748 */     checkClosed();
/*      */     
/*      */     try {
/*  751 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
/*      */     }
/*  753 */     catch (SQLException sqlException) {
/*  754 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  757 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
/*  765 */     checkClosed();
/*      */     
/*      */     try {
/*  768 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
/*      */     }
/*  770 */     catch (SQLException sqlException) {
/*  771 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  774 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void releaseSavepoint(Savepoint arg0) throws SQLException {
/*  781 */     checkClosed();
/*      */     
/*      */     try {
/*  784 */       this.mc.releaseSavepoint(arg0);
/*  785 */     } catch (SQLException sqlException) {
/*  786 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rollback() throws SQLException {
/*  797 */     checkClosed();
/*      */     
/*  799 */     if (isInGlobalTx()) {
/*  800 */       throw SQLError.createSQLException("Can't call rollback() on an XAConnection associated with a global transaction", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  808 */       this.mc.rollback();
/*  809 */     } catch (SQLException sqlException) {
/*  810 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rollback(Savepoint arg0) throws SQLException {
/*  818 */     checkClosed();
/*      */     
/*  820 */     if (isInGlobalTx()) {
/*  821 */       throw SQLError.createSQLException("Can't call rollback() on an XAConnection associated with a global transaction", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  829 */       this.mc.rollback(arg0);
/*  830 */     } catch (SQLException sqlException) {
/*  831 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isSameResource(Connection c) {
/*  836 */     if (c instanceof ConnectionWrapper)
/*  837 */       return this.mc.isSameResource(((ConnectionWrapper)c).mc); 
/*  838 */     if (c instanceof Connection) {
/*  839 */       return this.mc.isSameResource(c);
/*      */     }
/*      */     
/*  842 */     return false;
/*      */   }
/*      */   
/*      */   protected void close(boolean fireClosedEvent) throws SQLException {
/*  846 */     synchronized (this.pooledConnection) {
/*  847 */       if (this.closed) {
/*      */         return;
/*      */       }
/*      */       
/*  851 */       if (!isInGlobalTx() && this.mc.getRollbackOnPooledClose() && !getAutoCommit())
/*      */       {
/*  853 */         rollback();
/*      */       }
/*      */       
/*  856 */       if (fireClosedEvent) {
/*  857 */         this.pooledConnection.callConnectionEventListeners(2, null);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  866 */       this.closed = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkClosed() throws SQLException {
/*  871 */     if (this.closed) {
/*  872 */       throw SQLError.createSQLException(this.invalidHandleStr, this.exceptionInterceptor);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isInGlobalTx() {
/*  877 */     return this.mc.isInGlobalTx();
/*      */   }
/*      */   
/*      */   public void setInGlobalTx(boolean flag) {
/*  881 */     this.mc.setInGlobalTx(flag);
/*      */   }
/*      */   
/*      */   public void ping() throws SQLException {
/*  885 */     if (this.mc != null) {
/*  886 */       this.mc.ping();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void changeUser(String userName, String newPassword) throws SQLException {
/*  892 */     checkClosed();
/*      */     
/*      */     try {
/*  895 */       this.mc.changeUser(userName, newPassword);
/*  896 */     } catch (SQLException sqlException) {
/*  897 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void clearHasTriedMaster() {
/*  902 */     this.mc.clearHasTriedMaster();
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql) throws SQLException {
/*  907 */     checkClosed();
/*      */     
/*      */     try {
/*  910 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql));
/*      */     }
/*  912 */     catch (SQLException sqlException) {
/*  913 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  916 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/*      */     try {
/*  922 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyIndex));
/*      */     }
/*  924 */     catch (SQLException sqlException) {
/*  925 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  928 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*      */     try {
/*  934 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency));
/*      */     
/*      */     }
/*  937 */     catch (SQLException sqlException) {
/*  938 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  941 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/*      */     try {
/*  948 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
/*      */     
/*      */     }
/*  951 */     catch (SQLException sqlException) {
/*  952 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  955 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/*      */     try {
/*  961 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyIndexes));
/*      */     }
/*  963 */     catch (SQLException sqlException) {
/*  964 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  967 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/*      */     try {
/*  973 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyColNames));
/*      */     }
/*  975 */     catch (SQLException sqlException) {
/*  976 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  979 */       return null;
/*      */     } 
/*      */   }
/*      */   public int getActiveStatementCount() {
/*  983 */     return this.mc.getActiveStatementCount();
/*      */   }
/*      */   
/*      */   public Log getLog() throws SQLException {
/*  987 */     return this.mc.getLog();
/*      */   }
/*      */   
/*      */   public String getServerCharacterEncoding() {
/*  991 */     return this.mc.getServerCharacterEncoding();
/*      */   }
/*      */   
/*      */   public TimeZone getServerTimezoneTZ() {
/*  995 */     return this.mc.getServerTimezoneTZ();
/*      */   }
/*      */   
/*      */   public String getStatementComment() {
/*  999 */     return this.mc.getStatementComment();
/*      */   }
/*      */   
/*      */   public boolean hasTriedMaster() {
/* 1003 */     return this.mc.hasTriedMaster();
/*      */   }
/*      */   
/*      */   public boolean isAbonormallyLongQuery(long millisOrNanos) {
/* 1007 */     return this.mc.isAbonormallyLongQuery(millisOrNanos);
/*      */   }
/*      */   
/*      */   public boolean isNoBackslashEscapesSet() {
/* 1011 */     return this.mc.isNoBackslashEscapesSet();
/*      */   }
/*      */   
/*      */   public boolean lowerCaseTableNames() {
/* 1015 */     return this.mc.lowerCaseTableNames();
/*      */   }
/*      */   
/*      */   public boolean parserKnowsUnicode() {
/* 1019 */     return this.mc.parserKnowsUnicode();
/*      */   }
/*      */   
/*      */   public void reportQueryTime(long millisOrNanos) {
/* 1023 */     this.mc.reportQueryTime(millisOrNanos);
/*      */   }
/*      */   
/*      */   public void resetServerState() throws SQLException {
/* 1027 */     checkClosed();
/*      */     
/*      */     try {
/* 1030 */       this.mc.resetServerState();
/* 1031 */     } catch (SQLException sqlException) {
/* 1032 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql) throws SQLException {
/* 1038 */     checkClosed();
/*      */     
/*      */     try {
/* 1041 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql));
/*      */     }
/* 1043 */     catch (SQLException sqlException) {
/* 1044 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1047 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/*      */     try {
/* 1053 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyIndex));
/*      */     }
/* 1055 */     catch (SQLException sqlException) {
/* 1056 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1059 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*      */     try {
/* 1065 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, resultSetType, resultSetConcurrency));
/*      */     
/*      */     }
/* 1068 */     catch (SQLException sqlException) {
/* 1069 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1072 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/*      */     try {
/* 1079 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
/*      */     
/*      */     }
/* 1082 */     catch (SQLException sqlException) {
/* 1083 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1086 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/*      */     try {
/* 1092 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyIndexes));
/*      */     }
/* 1094 */     catch (SQLException sqlException) {
/* 1095 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1098 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/*      */     try {
/* 1104 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyColNames));
/*      */     }
/* 1106 */     catch (SQLException sqlException) {
/* 1107 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1110 */       return null;
/*      */     } 
/*      */   }
/*      */   public void setFailedOver(boolean flag) {
/* 1114 */     this.mc.setFailedOver(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPreferSlaveDuringFailover(boolean flag) {
/* 1119 */     this.mc.setPreferSlaveDuringFailover(flag);
/*      */   }
/*      */   
/*      */   public void setStatementComment(String comment) {
/* 1123 */     this.mc.setStatementComment(comment);
/*      */   }
/*      */ 
/*      */   
/*      */   public void shutdownServer() throws SQLException {
/* 1128 */     checkClosed();
/*      */     
/*      */     try {
/* 1131 */       this.mc.shutdownServer();
/* 1132 */     } catch (SQLException sqlException) {
/* 1133 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean supportsIsolationLevel() {
/* 1139 */     return this.mc.supportsIsolationLevel();
/*      */   }
/*      */   
/*      */   public boolean supportsQuotedIdentifiers() {
/* 1143 */     return this.mc.supportsQuotedIdentifiers();
/*      */   }
/*      */   
/*      */   public boolean supportsTransactions() {
/* 1147 */     return this.mc.supportsTransactions();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean versionMeetsMinimum(int major, int minor, int subminor) throws SQLException {
/* 1152 */     checkClosed();
/*      */     
/*      */     try {
/* 1155 */       return this.mc.versionMeetsMinimum(major, minor, subminor);
/* 1156 */     } catch (SQLException sqlException) {
/* 1157 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1160 */       return false;
/*      */     } 
/*      */   }
/*      */   public String exposeAsXml() throws SQLException {
/* 1164 */     checkClosed();
/*      */     
/*      */     try {
/* 1167 */       return this.mc.exposeAsXml();
/* 1168 */     } catch (SQLException sqlException) {
/* 1169 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1172 */       return null;
/*      */     } 
/*      */   }
/*      */   public boolean getAllowLoadLocalInfile() {
/* 1176 */     return this.mc.getAllowLoadLocalInfile();
/*      */   }
/*      */   
/*      */   public boolean getAllowMultiQueries() {
/* 1180 */     return this.mc.getAllowMultiQueries();
/*      */   }
/*      */   
/*      */   public boolean getAllowNanAndInf() {
/* 1184 */     return this.mc.getAllowNanAndInf();
/*      */   }
/*      */   
/*      */   public boolean getAllowUrlInLocalInfile() {
/* 1188 */     return this.mc.getAllowUrlInLocalInfile();
/*      */   }
/*      */   
/*      */   public boolean getAlwaysSendSetIsolation() {
/* 1192 */     return this.mc.getAlwaysSendSetIsolation();
/*      */   }
/*      */   
/*      */   public boolean getAutoClosePStmtStreams() {
/* 1196 */     return this.mc.getAutoClosePStmtStreams();
/*      */   }
/*      */   
/*      */   public boolean getAutoDeserialize() {
/* 1200 */     return this.mc.getAutoDeserialize();
/*      */   }
/*      */   
/*      */   public boolean getAutoGenerateTestcaseScript() {
/* 1204 */     return this.mc.getAutoGenerateTestcaseScript();
/*      */   }
/*      */   
/*      */   public boolean getAutoReconnectForPools() {
/* 1208 */     return this.mc.getAutoReconnectForPools();
/*      */   }
/*      */   
/*      */   public boolean getAutoSlowLog() {
/* 1212 */     return this.mc.getAutoSlowLog();
/*      */   }
/*      */   
/*      */   public int getBlobSendChunkSize() {
/* 1216 */     return this.mc.getBlobSendChunkSize();
/*      */   }
/*      */   
/*      */   public boolean getBlobsAreStrings() {
/* 1220 */     return this.mc.getBlobsAreStrings();
/*      */   }
/*      */   
/*      */   public boolean getCacheCallableStatements() {
/* 1224 */     return this.mc.getCacheCallableStatements();
/*      */   }
/*      */   
/*      */   public boolean getCacheCallableStmts() {
/* 1228 */     return this.mc.getCacheCallableStmts();
/*      */   }
/*      */   
/*      */   public boolean getCachePrepStmts() {
/* 1232 */     return this.mc.getCachePrepStmts();
/*      */   }
/*      */   
/*      */   public boolean getCachePreparedStatements() {
/* 1236 */     return this.mc.getCachePreparedStatements();
/*      */   }
/*      */   
/*      */   public boolean getCacheResultSetMetadata() {
/* 1240 */     return this.mc.getCacheResultSetMetadata();
/*      */   }
/*      */   
/*      */   public boolean getCacheServerConfiguration() {
/* 1244 */     return this.mc.getCacheServerConfiguration();
/*      */   }
/*      */   
/*      */   public int getCallableStatementCacheSize() {
/* 1248 */     return this.mc.getCallableStatementCacheSize();
/*      */   }
/*      */   
/*      */   public int getCallableStmtCacheSize() {
/* 1252 */     return this.mc.getCallableStmtCacheSize();
/*      */   }
/*      */   
/*      */   public boolean getCapitalizeTypeNames() {
/* 1256 */     return this.mc.getCapitalizeTypeNames();
/*      */   }
/*      */   
/*      */   public String getCharacterSetResults() {
/* 1260 */     return this.mc.getCharacterSetResults();
/*      */   }
/*      */   
/*      */   public String getClientCertificateKeyStorePassword() {
/* 1264 */     return this.mc.getClientCertificateKeyStorePassword();
/*      */   }
/*      */   
/*      */   public String getClientCertificateKeyStoreType() {
/* 1268 */     return this.mc.getClientCertificateKeyStoreType();
/*      */   }
/*      */   
/*      */   public String getClientCertificateKeyStoreUrl() {
/* 1272 */     return this.mc.getClientCertificateKeyStoreUrl();
/*      */   }
/*      */   
/*      */   public String getClientInfoProvider() {
/* 1276 */     return this.mc.getClientInfoProvider();
/*      */   }
/*      */   
/*      */   public String getClobCharacterEncoding() {
/* 1280 */     return this.mc.getClobCharacterEncoding();
/*      */   }
/*      */   
/*      */   public boolean getClobberStreamingResults() {
/* 1284 */     return this.mc.getClobberStreamingResults();
/*      */   }
/*      */   
/*      */   public int getConnectTimeout() {
/* 1288 */     return this.mc.getConnectTimeout();
/*      */   }
/*      */   
/*      */   public String getConnectionCollation() {
/* 1292 */     return this.mc.getConnectionCollation();
/*      */   }
/*      */   
/*      */   public String getConnectionLifecycleInterceptors() {
/* 1296 */     return this.mc.getConnectionLifecycleInterceptors();
/*      */   }
/*      */   
/*      */   public boolean getContinueBatchOnError() {
/* 1300 */     return this.mc.getContinueBatchOnError();
/*      */   }
/*      */   
/*      */   public boolean getCreateDatabaseIfNotExist() {
/* 1304 */     return this.mc.getCreateDatabaseIfNotExist();
/*      */   }
/*      */   
/*      */   public int getDefaultFetchSize() {
/* 1308 */     return this.mc.getDefaultFetchSize();
/*      */   }
/*      */   
/*      */   public boolean getDontTrackOpenResources() {
/* 1312 */     return this.mc.getDontTrackOpenResources();
/*      */   }
/*      */   
/*      */   public boolean getDumpMetadataOnColumnNotFound() {
/* 1316 */     return this.mc.getDumpMetadataOnColumnNotFound();
/*      */   }
/*      */   
/*      */   public boolean getDumpQueriesOnException() {
/* 1320 */     return this.mc.getDumpQueriesOnException();
/*      */   }
/*      */   
/*      */   public boolean getDynamicCalendars() {
/* 1324 */     return this.mc.getDynamicCalendars();
/*      */   }
/*      */   
/*      */   public boolean getElideSetAutoCommits() {
/* 1328 */     return this.mc.getElideSetAutoCommits();
/*      */   }
/*      */   
/*      */   public boolean getEmptyStringsConvertToZero() {
/* 1332 */     return this.mc.getEmptyStringsConvertToZero();
/*      */   }
/*      */   
/*      */   public boolean getEmulateLocators() {
/* 1336 */     return this.mc.getEmulateLocators();
/*      */   }
/*      */   
/*      */   public boolean getEmulateUnsupportedPstmts() {
/* 1340 */     return this.mc.getEmulateUnsupportedPstmts();
/*      */   }
/*      */   
/*      */   public boolean getEnablePacketDebug() {
/* 1344 */     return this.mc.getEnablePacketDebug();
/*      */   }
/*      */   
/*      */   public boolean getEnableQueryTimeouts() {
/* 1348 */     return this.mc.getEnableQueryTimeouts();
/*      */   }
/*      */   
/*      */   public String getEncoding() {
/* 1352 */     return this.mc.getEncoding();
/*      */   }
/*      */   
/*      */   public boolean getExplainSlowQueries() {
/* 1356 */     return this.mc.getExplainSlowQueries();
/*      */   }
/*      */   
/*      */   public boolean getFailOverReadOnly() {
/* 1360 */     return this.mc.getFailOverReadOnly();
/*      */   }
/*      */   
/*      */   public boolean getFunctionsNeverReturnBlobs() {
/* 1364 */     return this.mc.getFunctionsNeverReturnBlobs();
/*      */   }
/*      */   
/*      */   public boolean getGatherPerfMetrics() {
/* 1368 */     return this.mc.getGatherPerfMetrics();
/*      */   }
/*      */   
/*      */   public boolean getGatherPerformanceMetrics() {
/* 1372 */     return this.mc.getGatherPerformanceMetrics();
/*      */   }
/*      */   
/*      */   public boolean getGenerateSimpleParameterMetadata() {
/* 1376 */     return this.mc.getGenerateSimpleParameterMetadata();
/*      */   }
/*      */   
/*      */   public boolean getHoldResultsOpenOverStatementClose() {
/* 1380 */     return this.mc.getHoldResultsOpenOverStatementClose();
/*      */   }
/*      */   
/*      */   public boolean getIgnoreNonTxTables() {
/* 1384 */     return this.mc.getIgnoreNonTxTables();
/*      */   }
/*      */   
/*      */   public boolean getIncludeInnodbStatusInDeadlockExceptions() {
/* 1388 */     return this.mc.getIncludeInnodbStatusInDeadlockExceptions();
/*      */   }
/*      */   
/*      */   public int getInitialTimeout() {
/* 1392 */     return this.mc.getInitialTimeout();
/*      */   }
/*      */   
/*      */   public boolean getInteractiveClient() {
/* 1396 */     return this.mc.getInteractiveClient();
/*      */   }
/*      */   
/*      */   public boolean getIsInteractiveClient() {
/* 1400 */     return this.mc.getIsInteractiveClient();
/*      */   }
/*      */   
/*      */   public boolean getJdbcCompliantTruncation() {
/* 1404 */     return this.mc.getJdbcCompliantTruncation();
/*      */   }
/*      */   
/*      */   public boolean getJdbcCompliantTruncationForReads() {
/* 1408 */     return this.mc.getJdbcCompliantTruncationForReads();
/*      */   }
/*      */   
/*      */   public String getLargeRowSizeThreshold() {
/* 1412 */     return this.mc.getLargeRowSizeThreshold();
/*      */   }
/*      */   
/*      */   public String getLoadBalanceStrategy() {
/* 1416 */     return this.mc.getLoadBalanceStrategy();
/*      */   }
/*      */   
/*      */   public String getLocalSocketAddress() {
/* 1420 */     return this.mc.getLocalSocketAddress();
/*      */   }
/*      */   
/*      */   public int getLocatorFetchBufferSize() {
/* 1424 */     return this.mc.getLocatorFetchBufferSize();
/*      */   }
/*      */   
/*      */   public boolean getLogSlowQueries() {
/* 1428 */     return this.mc.getLogSlowQueries();
/*      */   }
/*      */   
/*      */   public boolean getLogXaCommands() {
/* 1432 */     return this.mc.getLogXaCommands();
/*      */   }
/*      */   
/*      */   public String getLogger() {
/* 1436 */     return this.mc.getLogger();
/*      */   }
/*      */   
/*      */   public String getLoggerClassName() {
/* 1440 */     return this.mc.getLoggerClassName();
/*      */   }
/*      */   
/*      */   public boolean getMaintainTimeStats() {
/* 1444 */     return this.mc.getMaintainTimeStats();
/*      */   }
/*      */   
/*      */   public int getMaxQuerySizeToLog() {
/* 1448 */     return this.mc.getMaxQuerySizeToLog();
/*      */   }
/*      */   
/*      */   public int getMaxReconnects() {
/* 1452 */     return this.mc.getMaxReconnects();
/*      */   }
/*      */   
/*      */   public int getMaxRows() {
/* 1456 */     return this.mc.getMaxRows();
/*      */   }
/*      */   
/*      */   public int getMetadataCacheSize() {
/* 1460 */     return this.mc.getMetadataCacheSize();
/*      */   }
/*      */   
/*      */   public int getNetTimeoutForStreamingResults() {
/* 1464 */     return this.mc.getNetTimeoutForStreamingResults();
/*      */   }
/*      */   
/*      */   public boolean getNoAccessToProcedureBodies() {
/* 1468 */     return this.mc.getNoAccessToProcedureBodies();
/*      */   }
/*      */   
/*      */   public boolean getNoDatetimeStringSync() {
/* 1472 */     return this.mc.getNoDatetimeStringSync();
/*      */   }
/*      */   
/*      */   public boolean getNoTimezoneConversionForTimeType() {
/* 1476 */     return this.mc.getNoTimezoneConversionForTimeType();
/*      */   }
/*      */   
/*      */   public boolean getNullCatalogMeansCurrent() {
/* 1480 */     return this.mc.getNullCatalogMeansCurrent();
/*      */   }
/*      */   
/*      */   public boolean getNullNamePatternMatchesAll() {
/* 1484 */     return this.mc.getNullNamePatternMatchesAll();
/*      */   }
/*      */   
/*      */   public boolean getOverrideSupportsIntegrityEnhancementFacility() {
/* 1488 */     return this.mc.getOverrideSupportsIntegrityEnhancementFacility();
/*      */   }
/*      */   
/*      */   public int getPacketDebugBufferSize() {
/* 1492 */     return this.mc.getPacketDebugBufferSize();
/*      */   }
/*      */   
/*      */   public boolean getPadCharsWithSpace() {
/* 1496 */     return this.mc.getPadCharsWithSpace();
/*      */   }
/*      */   
/*      */   public boolean getParanoid() {
/* 1500 */     return this.mc.getParanoid();
/*      */   }
/*      */   
/*      */   public boolean getPedantic() {
/* 1504 */     return this.mc.getPedantic();
/*      */   }
/*      */   
/*      */   public boolean getPinGlobalTxToPhysicalConnection() {
/* 1508 */     return this.mc.getPinGlobalTxToPhysicalConnection();
/*      */   }
/*      */   
/*      */   public boolean getPopulateInsertRowWithDefaultValues() {
/* 1512 */     return this.mc.getPopulateInsertRowWithDefaultValues();
/*      */   }
/*      */   
/*      */   public int getPrepStmtCacheSize() {
/* 1516 */     return this.mc.getPrepStmtCacheSize();
/*      */   }
/*      */   
/*      */   public int getPrepStmtCacheSqlLimit() {
/* 1520 */     return this.mc.getPrepStmtCacheSqlLimit();
/*      */   }
/*      */   
/*      */   public int getPreparedStatementCacheSize() {
/* 1524 */     return this.mc.getPreparedStatementCacheSize();
/*      */   }
/*      */   
/*      */   public int getPreparedStatementCacheSqlLimit() {
/* 1528 */     return this.mc.getPreparedStatementCacheSqlLimit();
/*      */   }
/*      */   
/*      */   public boolean getProcessEscapeCodesForPrepStmts() {
/* 1532 */     return this.mc.getProcessEscapeCodesForPrepStmts();
/*      */   }
/*      */   
/*      */   public boolean getProfileSQL() {
/* 1536 */     return this.mc.getProfileSQL();
/*      */   }
/*      */   
/*      */   public boolean getProfileSql() {
/* 1540 */     return this.mc.getProfileSql();
/*      */   }
/*      */   
/*      */   public String getPropertiesTransform() {
/* 1544 */     return this.mc.getPropertiesTransform();
/*      */   }
/*      */   
/*      */   public int getQueriesBeforeRetryMaster() {
/* 1548 */     return this.mc.getQueriesBeforeRetryMaster();
/*      */   }
/*      */   
/*      */   public boolean getReconnectAtTxEnd() {
/* 1552 */     return this.mc.getReconnectAtTxEnd();
/*      */   }
/*      */   
/*      */   public boolean getRelaxAutoCommit() {
/* 1556 */     return this.mc.getRelaxAutoCommit();
/*      */   }
/*      */   
/*      */   public int getReportMetricsIntervalMillis() {
/* 1560 */     return this.mc.getReportMetricsIntervalMillis();
/*      */   }
/*      */   
/*      */   public boolean getRequireSSL() {
/* 1564 */     return this.mc.getRequireSSL();
/*      */   }
/*      */   
/*      */   public String getResourceId() {
/* 1568 */     return this.mc.getResourceId();
/*      */   }
/*      */   
/*      */   public int getResultSetSizeThreshold() {
/* 1572 */     return this.mc.getResultSetSizeThreshold();
/*      */   }
/*      */   
/*      */   public boolean getRewriteBatchedStatements() {
/* 1576 */     return this.mc.getRewriteBatchedStatements();
/*      */   }
/*      */   
/*      */   public boolean getRollbackOnPooledClose() {
/* 1580 */     return this.mc.getRollbackOnPooledClose();
/*      */   }
/*      */   
/*      */   public boolean getRoundRobinLoadBalance() {
/* 1584 */     return this.mc.getRoundRobinLoadBalance();
/*      */   }
/*      */   
/*      */   public boolean getRunningCTS13() {
/* 1588 */     return this.mc.getRunningCTS13();
/*      */   }
/*      */   
/*      */   public int getSecondsBeforeRetryMaster() {
/* 1592 */     return this.mc.getSecondsBeforeRetryMaster();
/*      */   }
/*      */   
/*      */   public String getServerTimezone() {
/* 1596 */     return this.mc.getServerTimezone();
/*      */   }
/*      */   
/*      */   public String getSessionVariables() {
/* 1600 */     return this.mc.getSessionVariables();
/*      */   }
/*      */   
/*      */   public int getSlowQueryThresholdMillis() {
/* 1604 */     return this.mc.getSlowQueryThresholdMillis();
/*      */   }
/*      */   
/*      */   public long getSlowQueryThresholdNanos() {
/* 1608 */     return this.mc.getSlowQueryThresholdNanos();
/*      */   }
/*      */   
/*      */   public String getSocketFactory() {
/* 1612 */     return this.mc.getSocketFactory();
/*      */   }
/*      */   
/*      */   public String getSocketFactoryClassName() {
/* 1616 */     return this.mc.getSocketFactoryClassName();
/*      */   }
/*      */   
/*      */   public int getSocketTimeout() {
/* 1620 */     return this.mc.getSocketTimeout();
/*      */   }
/*      */   
/*      */   public String getStatementInterceptors() {
/* 1624 */     return this.mc.getStatementInterceptors();
/*      */   }
/*      */   
/*      */   public boolean getStrictFloatingPoint() {
/* 1628 */     return this.mc.getStrictFloatingPoint();
/*      */   }
/*      */   
/*      */   public boolean getStrictUpdates() {
/* 1632 */     return this.mc.getStrictUpdates();
/*      */   }
/*      */   
/*      */   public boolean getTcpKeepAlive() {
/* 1636 */     return this.mc.getTcpKeepAlive();
/*      */   }
/*      */   
/*      */   public boolean getTcpNoDelay() {
/* 1640 */     return this.mc.getTcpNoDelay();
/*      */   }
/*      */   
/*      */   public int getTcpRcvBuf() {
/* 1644 */     return this.mc.getTcpRcvBuf();
/*      */   }
/*      */   
/*      */   public int getTcpSndBuf() {
/* 1648 */     return this.mc.getTcpSndBuf();
/*      */   }
/*      */   
/*      */   public int getTcpTrafficClass() {
/* 1652 */     return this.mc.getTcpTrafficClass();
/*      */   }
/*      */   
/*      */   public boolean getTinyInt1isBit() {
/* 1656 */     return this.mc.getTinyInt1isBit();
/*      */   }
/*      */   
/*      */   public boolean getTraceProtocol() {
/* 1660 */     return this.mc.getTraceProtocol();
/*      */   }
/*      */   
/*      */   public boolean getTransformedBitIsBoolean() {
/* 1664 */     return this.mc.getTransformedBitIsBoolean();
/*      */   }
/*      */   
/*      */   public boolean getTreatUtilDateAsTimestamp() {
/* 1668 */     return this.mc.getTreatUtilDateAsTimestamp();
/*      */   }
/*      */   
/*      */   public String getTrustCertificateKeyStorePassword() {
/* 1672 */     return this.mc.getTrustCertificateKeyStorePassword();
/*      */   }
/*      */   
/*      */   public String getTrustCertificateKeyStoreType() {
/* 1676 */     return this.mc.getTrustCertificateKeyStoreType();
/*      */   }
/*      */   
/*      */   public String getTrustCertificateKeyStoreUrl() {
/* 1680 */     return this.mc.getTrustCertificateKeyStoreUrl();
/*      */   }
/*      */   
/*      */   public boolean getUltraDevHack() {
/* 1684 */     return this.mc.getUltraDevHack();
/*      */   }
/*      */   
/*      */   public boolean getUseBlobToStoreUTF8OutsideBMP() {
/* 1688 */     return this.mc.getUseBlobToStoreUTF8OutsideBMP();
/*      */   }
/*      */   
/*      */   public boolean getUseCompression() {
/* 1692 */     return this.mc.getUseCompression();
/*      */   }
/*      */   
/*      */   public String getUseConfigs() {
/* 1696 */     return this.mc.getUseConfigs();
/*      */   }
/*      */   
/*      */   public boolean getUseCursorFetch() {
/* 1700 */     return this.mc.getUseCursorFetch();
/*      */   }
/*      */   
/*      */   public boolean getUseDirectRowUnpack() {
/* 1704 */     return this.mc.getUseDirectRowUnpack();
/*      */   }
/*      */   
/*      */   public boolean getUseDynamicCharsetInfo() {
/* 1708 */     return this.mc.getUseDynamicCharsetInfo();
/*      */   }
/*      */   
/*      */   public boolean getUseFastDateParsing() {
/* 1712 */     return this.mc.getUseFastDateParsing();
/*      */   }
/*      */   
/*      */   public boolean getUseFastIntParsing() {
/* 1716 */     return this.mc.getUseFastIntParsing();
/*      */   }
/*      */   
/*      */   public boolean getUseGmtMillisForDatetimes() {
/* 1720 */     return this.mc.getUseGmtMillisForDatetimes();
/*      */   }
/*      */   
/*      */   public boolean getUseHostsInPrivileges() {
/* 1724 */     return this.mc.getUseHostsInPrivileges();
/*      */   }
/*      */   
/*      */   public boolean getUseInformationSchema() {
/* 1728 */     return this.mc.getUseInformationSchema();
/*      */   }
/*      */   
/*      */   public boolean getUseJDBCCompliantTimezoneShift() {
/* 1732 */     return this.mc.getUseJDBCCompliantTimezoneShift();
/*      */   }
/*      */   
/*      */   public boolean getUseJvmCharsetConverters() {
/* 1736 */     return this.mc.getUseJvmCharsetConverters();
/*      */   }
/*      */   
/*      */   public boolean getUseLocalSessionState() {
/* 1740 */     return this.mc.getUseLocalSessionState();
/*      */   }
/*      */   
/*      */   public boolean getUseNanosForElapsedTime() {
/* 1744 */     return this.mc.getUseNanosForElapsedTime();
/*      */   }
/*      */   
/*      */   public boolean getUseOldAliasMetadataBehavior() {
/* 1748 */     return this.mc.getUseOldAliasMetadataBehavior();
/*      */   }
/*      */   
/*      */   public boolean getUseOldUTF8Behavior() {
/* 1752 */     return this.mc.getUseOldUTF8Behavior();
/*      */   }
/*      */   
/*      */   public boolean getUseOnlyServerErrorMessages() {
/* 1756 */     return this.mc.getUseOnlyServerErrorMessages();
/*      */   }
/*      */   
/*      */   public boolean getUseReadAheadInput() {
/* 1760 */     return this.mc.getUseReadAheadInput();
/*      */   }
/*      */   
/*      */   public boolean getUseSSL() {
/* 1764 */     return this.mc.getUseSSL();
/*      */   }
/*      */   
/*      */   public boolean getUseSSPSCompatibleTimezoneShift() {
/* 1768 */     return this.mc.getUseSSPSCompatibleTimezoneShift();
/*      */   }
/*      */   
/*      */   public boolean getUseServerPrepStmts() {
/* 1772 */     return this.mc.getUseServerPrepStmts();
/*      */   }
/*      */   
/*      */   public boolean getUseServerPreparedStmts() {
/* 1776 */     return this.mc.getUseServerPreparedStmts();
/*      */   }
/*      */   
/*      */   public boolean getUseSqlStateCodes() {
/* 1780 */     return this.mc.getUseSqlStateCodes();
/*      */   }
/*      */   
/*      */   public boolean getUseStreamLengthsInPrepStmts() {
/* 1784 */     return this.mc.getUseStreamLengthsInPrepStmts();
/*      */   }
/*      */   
/*      */   public boolean getUseTimezone() {
/* 1788 */     return this.mc.getUseTimezone();
/*      */   }
/*      */   
/*      */   public boolean getUseUltraDevWorkAround() {
/* 1792 */     return this.mc.getUseUltraDevWorkAround();
/*      */   }
/*      */   
/*      */   public boolean getUseUnbufferedInput() {
/* 1796 */     return this.mc.getUseUnbufferedInput();
/*      */   }
/*      */   
/*      */   public boolean getUseUnicode() {
/* 1800 */     return this.mc.getUseUnicode();
/*      */   }
/*      */   
/*      */   public boolean getUseUsageAdvisor() {
/* 1804 */     return this.mc.getUseUsageAdvisor();
/*      */   }
/*      */   
/*      */   public String getUtf8OutsideBmpExcludedColumnNamePattern() {
/* 1808 */     return this.mc.getUtf8OutsideBmpExcludedColumnNamePattern();
/*      */   }
/*      */   
/*      */   public String getUtf8OutsideBmpIncludedColumnNamePattern() {
/* 1812 */     return this.mc.getUtf8OutsideBmpIncludedColumnNamePattern();
/*      */   }
/*      */   
/*      */   public boolean getYearIsDateType() {
/* 1816 */     return this.mc.getYearIsDateType();
/*      */   }
/*      */   
/*      */   public String getZeroDateTimeBehavior() {
/* 1820 */     return this.mc.getZeroDateTimeBehavior();
/*      */   }
/*      */   
/*      */   public void setAllowLoadLocalInfile(boolean property) {
/* 1824 */     this.mc.setAllowLoadLocalInfile(property);
/*      */   }
/*      */   
/*      */   public void setAllowMultiQueries(boolean property) {
/* 1828 */     this.mc.setAllowMultiQueries(property);
/*      */   }
/*      */   
/*      */   public void setAllowNanAndInf(boolean flag) {
/* 1832 */     this.mc.setAllowNanAndInf(flag);
/*      */   }
/*      */   
/*      */   public void setAllowUrlInLocalInfile(boolean flag) {
/* 1836 */     this.mc.setAllowUrlInLocalInfile(flag);
/*      */   }
/*      */   
/*      */   public void setAlwaysSendSetIsolation(boolean flag) {
/* 1840 */     this.mc.setAlwaysSendSetIsolation(flag);
/*      */   }
/*      */   
/*      */   public void setAutoClosePStmtStreams(boolean flag) {
/* 1844 */     this.mc.setAutoClosePStmtStreams(flag);
/*      */   }
/*      */   
/*      */   public void setAutoDeserialize(boolean flag) {
/* 1848 */     this.mc.setAutoDeserialize(flag);
/*      */   }
/*      */   
/*      */   public void setAutoGenerateTestcaseScript(boolean flag) {
/* 1852 */     this.mc.setAutoGenerateTestcaseScript(flag);
/*      */   }
/*      */   
/*      */   public void setAutoReconnect(boolean flag) {
/* 1856 */     this.mc.setAutoReconnect(flag);
/*      */   }
/*      */   
/*      */   public void setAutoReconnectForConnectionPools(boolean property) {
/* 1860 */     this.mc.setAutoReconnectForConnectionPools(property);
/*      */   }
/*      */   
/*      */   public void setAutoReconnectForPools(boolean flag) {
/* 1864 */     this.mc.setAutoReconnectForPools(flag);
/*      */   }
/*      */   
/*      */   public void setAutoSlowLog(boolean flag) {
/* 1868 */     this.mc.setAutoSlowLog(flag);
/*      */   }
/*      */   
/*      */   public void setBlobSendChunkSize(String value) throws SQLException {
/* 1872 */     this.mc.setBlobSendChunkSize(value);
/*      */   }
/*      */   
/*      */   public void setBlobsAreStrings(boolean flag) {
/* 1876 */     this.mc.setBlobsAreStrings(flag);
/*      */   }
/*      */   
/*      */   public void setCacheCallableStatements(boolean flag) {
/* 1880 */     this.mc.setCacheCallableStatements(flag);
/*      */   }
/*      */   
/*      */   public void setCacheCallableStmts(boolean flag) {
/* 1884 */     this.mc.setCacheCallableStmts(flag);
/*      */   }
/*      */   
/*      */   public void setCachePrepStmts(boolean flag) {
/* 1888 */     this.mc.setCachePrepStmts(flag);
/*      */   }
/*      */   
/*      */   public void setCachePreparedStatements(boolean flag) {
/* 1892 */     this.mc.setCachePreparedStatements(flag);
/*      */   }
/*      */   
/*      */   public void setCacheResultSetMetadata(boolean property) {
/* 1896 */     this.mc.setCacheResultSetMetadata(property);
/*      */   }
/*      */   
/*      */   public void setCacheServerConfiguration(boolean flag) {
/* 1900 */     this.mc.setCacheServerConfiguration(flag);
/*      */   }
/*      */   
/*      */   public void setCallableStatementCacheSize(int size) {
/* 1904 */     this.mc.setCallableStatementCacheSize(size);
/*      */   }
/*      */   
/*      */   public void setCallableStmtCacheSize(int cacheSize) {
/* 1908 */     this.mc.setCallableStmtCacheSize(cacheSize);
/*      */   }
/*      */   
/*      */   public void setCapitalizeDBMDTypes(boolean property) {
/* 1912 */     this.mc.setCapitalizeDBMDTypes(property);
/*      */   }
/*      */   
/*      */   public void setCapitalizeTypeNames(boolean flag) {
/* 1916 */     this.mc.setCapitalizeTypeNames(flag);
/*      */   }
/*      */   
/*      */   public void setCharacterEncoding(String encoding) {
/* 1920 */     this.mc.setCharacterEncoding(encoding);
/*      */   }
/*      */   
/*      */   public void setCharacterSetResults(String characterSet) {
/* 1924 */     this.mc.setCharacterSetResults(characterSet);
/*      */   }
/*      */   
/*      */   public void setClientCertificateKeyStorePassword(String value) {
/* 1928 */     this.mc.setClientCertificateKeyStorePassword(value);
/*      */   }
/*      */   
/*      */   public void setClientCertificateKeyStoreType(String value) {
/* 1932 */     this.mc.setClientCertificateKeyStoreType(value);
/*      */   }
/*      */   
/*      */   public void setClientCertificateKeyStoreUrl(String value) {
/* 1936 */     this.mc.setClientCertificateKeyStoreUrl(value);
/*      */   }
/*      */   
/*      */   public void setClientInfoProvider(String classname) {
/* 1940 */     this.mc.setClientInfoProvider(classname);
/*      */   }
/*      */   
/*      */   public void setClobCharacterEncoding(String encoding) {
/* 1944 */     this.mc.setClobCharacterEncoding(encoding);
/*      */   }
/*      */   
/*      */   public void setClobberStreamingResults(boolean flag) {
/* 1948 */     this.mc.setClobberStreamingResults(flag);
/*      */   }
/*      */   
/*      */   public void setConnectTimeout(int timeoutMs) {
/* 1952 */     this.mc.setConnectTimeout(timeoutMs);
/*      */   }
/*      */   
/*      */   public void setConnectionCollation(String collation) {
/* 1956 */     this.mc.setConnectionCollation(collation);
/*      */   }
/*      */   
/*      */   public void setConnectionLifecycleInterceptors(String interceptors) {
/* 1960 */     this.mc.setConnectionLifecycleInterceptors(interceptors);
/*      */   }
/*      */   
/*      */   public void setContinueBatchOnError(boolean property) {
/* 1964 */     this.mc.setContinueBatchOnError(property);
/*      */   }
/*      */   
/*      */   public void setCreateDatabaseIfNotExist(boolean flag) {
/* 1968 */     this.mc.setCreateDatabaseIfNotExist(flag);
/*      */   }
/*      */   
/*      */   public void setDefaultFetchSize(int n) {
/* 1972 */     this.mc.setDefaultFetchSize(n);
/*      */   }
/*      */   
/*      */   public void setDetectServerPreparedStmts(boolean property) {
/* 1976 */     this.mc.setDetectServerPreparedStmts(property);
/*      */   }
/*      */   
/*      */   public void setDontTrackOpenResources(boolean flag) {
/* 1980 */     this.mc.setDontTrackOpenResources(flag);
/*      */   }
/*      */   
/*      */   public void setDumpMetadataOnColumnNotFound(boolean flag) {
/* 1984 */     this.mc.setDumpMetadataOnColumnNotFound(flag);
/*      */   }
/*      */   
/*      */   public void setDumpQueriesOnException(boolean flag) {
/* 1988 */     this.mc.setDumpQueriesOnException(flag);
/*      */   }
/*      */   
/*      */   public void setDynamicCalendars(boolean flag) {
/* 1992 */     this.mc.setDynamicCalendars(flag);
/*      */   }
/*      */   
/*      */   public void setElideSetAutoCommits(boolean flag) {
/* 1996 */     this.mc.setElideSetAutoCommits(flag);
/*      */   }
/*      */   
/*      */   public void setEmptyStringsConvertToZero(boolean flag) {
/* 2000 */     this.mc.setEmptyStringsConvertToZero(flag);
/*      */   }
/*      */   
/*      */   public void setEmulateLocators(boolean property) {
/* 2004 */     this.mc.setEmulateLocators(property);
/*      */   }
/*      */   
/*      */   public void setEmulateUnsupportedPstmts(boolean flag) {
/* 2008 */     this.mc.setEmulateUnsupportedPstmts(flag);
/*      */   }
/*      */   
/*      */   public void setEnablePacketDebug(boolean flag) {
/* 2012 */     this.mc.setEnablePacketDebug(flag);
/*      */   }
/*      */   
/*      */   public void setEnableQueryTimeouts(boolean flag) {
/* 2016 */     this.mc.setEnableQueryTimeouts(flag);
/*      */   }
/*      */   
/*      */   public void setEncoding(String property) {
/* 2020 */     this.mc.setEncoding(property);
/*      */   }
/*      */   
/*      */   public void setExplainSlowQueries(boolean flag) {
/* 2024 */     this.mc.setExplainSlowQueries(flag);
/*      */   }
/*      */   
/*      */   public void setFailOverReadOnly(boolean flag) {
/* 2028 */     this.mc.setFailOverReadOnly(flag);
/*      */   }
/*      */   
/*      */   public void setFunctionsNeverReturnBlobs(boolean flag) {
/* 2032 */     this.mc.setFunctionsNeverReturnBlobs(flag);
/*      */   }
/*      */   
/*      */   public void setGatherPerfMetrics(boolean flag) {
/* 2036 */     this.mc.setGatherPerfMetrics(flag);
/*      */   }
/*      */   
/*      */   public void setGatherPerformanceMetrics(boolean flag) {
/* 2040 */     this.mc.setGatherPerformanceMetrics(flag);
/*      */   }
/*      */   
/*      */   public void setGenerateSimpleParameterMetadata(boolean flag) {
/* 2044 */     this.mc.setGenerateSimpleParameterMetadata(flag);
/*      */   }
/*      */   
/*      */   public void setHoldResultsOpenOverStatementClose(boolean flag) {
/* 2048 */     this.mc.setHoldResultsOpenOverStatementClose(flag);
/*      */   }
/*      */   
/*      */   public void setIgnoreNonTxTables(boolean property) {
/* 2052 */     this.mc.setIgnoreNonTxTables(property);
/*      */   }
/*      */   
/*      */   public void setIncludeInnodbStatusInDeadlockExceptions(boolean flag) {
/* 2056 */     this.mc.setIncludeInnodbStatusInDeadlockExceptions(flag);
/*      */   }
/*      */   
/*      */   public void setInitialTimeout(int property) {
/* 2060 */     this.mc.setInitialTimeout(property);
/*      */   }
/*      */   
/*      */   public void setInteractiveClient(boolean property) {
/* 2064 */     this.mc.setInteractiveClient(property);
/*      */   }
/*      */   
/*      */   public void setIsInteractiveClient(boolean property) {
/* 2068 */     this.mc.setIsInteractiveClient(property);
/*      */   }
/*      */   
/*      */   public void setJdbcCompliantTruncation(boolean flag) {
/* 2072 */     this.mc.setJdbcCompliantTruncation(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setJdbcCompliantTruncationForReads(boolean jdbcCompliantTruncationForReads) {
/* 2077 */     this.mc.setJdbcCompliantTruncationForReads(jdbcCompliantTruncationForReads);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLargeRowSizeThreshold(String value) {
/* 2082 */     this.mc.setLargeRowSizeThreshold(value);
/*      */   }
/*      */   
/*      */   public void setLoadBalanceStrategy(String strategy) {
/* 2086 */     this.mc.setLoadBalanceStrategy(strategy);
/*      */   }
/*      */   
/*      */   public void setLocalSocketAddress(String address) {
/* 2090 */     this.mc.setLocalSocketAddress(address);
/*      */   }
/*      */   
/*      */   public void setLocatorFetchBufferSize(String value) throws SQLException {
/* 2094 */     this.mc.setLocatorFetchBufferSize(value);
/*      */   }
/*      */   
/*      */   public void setLogSlowQueries(boolean flag) {
/* 2098 */     this.mc.setLogSlowQueries(flag);
/*      */   }
/*      */   
/*      */   public void setLogXaCommands(boolean flag) {
/* 2102 */     this.mc.setLogXaCommands(flag);
/*      */   }
/*      */   
/*      */   public void setLogger(String property) {
/* 2106 */     this.mc.setLogger(property);
/*      */   }
/*      */   
/*      */   public void setLoggerClassName(String className) {
/* 2110 */     this.mc.setLoggerClassName(className);
/*      */   }
/*      */   
/*      */   public void setMaintainTimeStats(boolean flag) {
/* 2114 */     this.mc.setMaintainTimeStats(flag);
/*      */   }
/*      */   
/*      */   public void setMaxQuerySizeToLog(int sizeInBytes) {
/* 2118 */     this.mc.setMaxQuerySizeToLog(sizeInBytes);
/*      */   }
/*      */   
/*      */   public void setMaxReconnects(int property) {
/* 2122 */     this.mc.setMaxReconnects(property);
/*      */   }
/*      */   
/*      */   public void setMaxRows(int property) {
/* 2126 */     this.mc.setMaxRows(property);
/*      */   }
/*      */   
/*      */   public void setMetadataCacheSize(int value) {
/* 2130 */     this.mc.setMetadataCacheSize(value);
/*      */   }
/*      */   
/*      */   public void setNetTimeoutForStreamingResults(int value) {
/* 2134 */     this.mc.setNetTimeoutForStreamingResults(value);
/*      */   }
/*      */   
/*      */   public void setNoAccessToProcedureBodies(boolean flag) {
/* 2138 */     this.mc.setNoAccessToProcedureBodies(flag);
/*      */   }
/*      */   
/*      */   public void setNoDatetimeStringSync(boolean flag) {
/* 2142 */     this.mc.setNoDatetimeStringSync(flag);
/*      */   }
/*      */   
/*      */   public void setNoTimezoneConversionForTimeType(boolean flag) {
/* 2146 */     this.mc.setNoTimezoneConversionForTimeType(flag);
/*      */   }
/*      */   
/*      */   public void setNullCatalogMeansCurrent(boolean value) {
/* 2150 */     this.mc.setNullCatalogMeansCurrent(value);
/*      */   }
/*      */   
/*      */   public void setNullNamePatternMatchesAll(boolean value) {
/* 2154 */     this.mc.setNullNamePatternMatchesAll(value);
/*      */   }
/*      */   
/*      */   public void setOverrideSupportsIntegrityEnhancementFacility(boolean flag) {
/* 2158 */     this.mc.setOverrideSupportsIntegrityEnhancementFacility(flag);
/*      */   }
/*      */   
/*      */   public void setPacketDebugBufferSize(int size) {
/* 2162 */     this.mc.setPacketDebugBufferSize(size);
/*      */   }
/*      */   
/*      */   public void setPadCharsWithSpace(boolean flag) {
/* 2166 */     this.mc.setPadCharsWithSpace(flag);
/*      */   }
/*      */   
/*      */   public void setParanoid(boolean property) {
/* 2170 */     this.mc.setParanoid(property);
/*      */   }
/*      */   
/*      */   public void setPedantic(boolean property) {
/* 2174 */     this.mc.setPedantic(property);
/*      */   }
/*      */   
/*      */   public void setPinGlobalTxToPhysicalConnection(boolean flag) {
/* 2178 */     this.mc.setPinGlobalTxToPhysicalConnection(flag);
/*      */   }
/*      */   
/*      */   public void setPopulateInsertRowWithDefaultValues(boolean flag) {
/* 2182 */     this.mc.setPopulateInsertRowWithDefaultValues(flag);
/*      */   }
/*      */   
/*      */   public void setPrepStmtCacheSize(int cacheSize) {
/* 2186 */     this.mc.setPrepStmtCacheSize(cacheSize);
/*      */   }
/*      */   
/*      */   public void setPrepStmtCacheSqlLimit(int sqlLimit) {
/* 2190 */     this.mc.setPrepStmtCacheSqlLimit(sqlLimit);
/*      */   }
/*      */   
/*      */   public void setPreparedStatementCacheSize(int cacheSize) {
/* 2194 */     this.mc.setPreparedStatementCacheSize(cacheSize);
/*      */   }
/*      */   
/*      */   public void setPreparedStatementCacheSqlLimit(int cacheSqlLimit) {
/* 2198 */     this.mc.setPreparedStatementCacheSqlLimit(cacheSqlLimit);
/*      */   }
/*      */   
/*      */   public void setProcessEscapeCodesForPrepStmts(boolean flag) {
/* 2202 */     this.mc.setProcessEscapeCodesForPrepStmts(flag);
/*      */   }
/*      */   
/*      */   public void setProfileSQL(boolean flag) {
/* 2206 */     this.mc.setProfileSQL(flag);
/*      */   }
/*      */   
/*      */   public void setProfileSql(boolean property) {
/* 2210 */     this.mc.setProfileSql(property);
/*      */   }
/*      */   
/*      */   public void setPropertiesTransform(String value) {
/* 2214 */     this.mc.setPropertiesTransform(value);
/*      */   }
/*      */   
/*      */   public void setQueriesBeforeRetryMaster(int property) {
/* 2218 */     this.mc.setQueriesBeforeRetryMaster(property);
/*      */   }
/*      */   
/*      */   public void setReconnectAtTxEnd(boolean property) {
/* 2222 */     this.mc.setReconnectAtTxEnd(property);
/*      */   }
/*      */   
/*      */   public void setRelaxAutoCommit(boolean property) {
/* 2226 */     this.mc.setRelaxAutoCommit(property);
/*      */   }
/*      */   
/*      */   public void setReportMetricsIntervalMillis(int millis) {
/* 2230 */     this.mc.setReportMetricsIntervalMillis(millis);
/*      */   }
/*      */   
/*      */   public void setRequireSSL(boolean property) {
/* 2234 */     this.mc.setRequireSSL(property);
/*      */   }
/*      */   
/*      */   public void setResourceId(String resourceId) {
/* 2238 */     this.mc.setResourceId(resourceId);
/*      */   }
/*      */   
/*      */   public void setResultSetSizeThreshold(int threshold) {
/* 2242 */     this.mc.setResultSetSizeThreshold(threshold);
/*      */   }
/*      */   
/*      */   public void setRetainStatementAfterResultSetClose(boolean flag) {
/* 2246 */     this.mc.setRetainStatementAfterResultSetClose(flag);
/*      */   }
/*      */   
/*      */   public void setRewriteBatchedStatements(boolean flag) {
/* 2250 */     this.mc.setRewriteBatchedStatements(flag);
/*      */   }
/*      */   
/*      */   public void setRollbackOnPooledClose(boolean flag) {
/* 2254 */     this.mc.setRollbackOnPooledClose(flag);
/*      */   }
/*      */   
/*      */   public void setRoundRobinLoadBalance(boolean flag) {
/* 2258 */     this.mc.setRoundRobinLoadBalance(flag);
/*      */   }
/*      */   
/*      */   public void setRunningCTS13(boolean flag) {
/* 2262 */     this.mc.setRunningCTS13(flag);
/*      */   }
/*      */   
/*      */   public void setSecondsBeforeRetryMaster(int property) {
/* 2266 */     this.mc.setSecondsBeforeRetryMaster(property);
/*      */   }
/*      */   
/*      */   public void setServerTimezone(String property) {
/* 2270 */     this.mc.setServerTimezone(property);
/*      */   }
/*      */   
/*      */   public void setSessionVariables(String variables) {
/* 2274 */     this.mc.setSessionVariables(variables);
/*      */   }
/*      */   
/*      */   public void setSlowQueryThresholdMillis(int millis) {
/* 2278 */     this.mc.setSlowQueryThresholdMillis(millis);
/*      */   }
/*      */   
/*      */   public void setSlowQueryThresholdNanos(long nanos) {
/* 2282 */     this.mc.setSlowQueryThresholdNanos(nanos);
/*      */   }
/*      */   
/*      */   public void setSocketFactory(String name) {
/* 2286 */     this.mc.setSocketFactory(name);
/*      */   }
/*      */   
/*      */   public void setSocketFactoryClassName(String property) {
/* 2290 */     this.mc.setSocketFactoryClassName(property);
/*      */   }
/*      */   
/*      */   public void setSocketTimeout(int property) {
/* 2294 */     this.mc.setSocketTimeout(property);
/*      */   }
/*      */   
/*      */   public void setStatementInterceptors(String value) {
/* 2298 */     this.mc.setStatementInterceptors(value);
/*      */   }
/*      */   
/*      */   public void setStrictFloatingPoint(boolean property) {
/* 2302 */     this.mc.setStrictFloatingPoint(property);
/*      */   }
/*      */   
/*      */   public void setStrictUpdates(boolean property) {
/* 2306 */     this.mc.setStrictUpdates(property);
/*      */   }
/*      */   
/*      */   public void setTcpKeepAlive(boolean flag) {
/* 2310 */     this.mc.setTcpKeepAlive(flag);
/*      */   }
/*      */   
/*      */   public void setTcpNoDelay(boolean flag) {
/* 2314 */     this.mc.setTcpNoDelay(flag);
/*      */   }
/*      */   
/*      */   public void setTcpRcvBuf(int bufSize) {
/* 2318 */     this.mc.setTcpRcvBuf(bufSize);
/*      */   }
/*      */   
/*      */   public void setTcpSndBuf(int bufSize) {
/* 2322 */     this.mc.setTcpSndBuf(bufSize);
/*      */   }
/*      */   
/*      */   public void setTcpTrafficClass(int classFlags) {
/* 2326 */     this.mc.setTcpTrafficClass(classFlags);
/*      */   }
/*      */   
/*      */   public void setTinyInt1isBit(boolean flag) {
/* 2330 */     this.mc.setTinyInt1isBit(flag);
/*      */   }
/*      */   
/*      */   public void setTraceProtocol(boolean flag) {
/* 2334 */     this.mc.setTraceProtocol(flag);
/*      */   }
/*      */   
/*      */   public void setTransformedBitIsBoolean(boolean flag) {
/* 2338 */     this.mc.setTransformedBitIsBoolean(flag);
/*      */   }
/*      */   
/*      */   public void setTreatUtilDateAsTimestamp(boolean flag) {
/* 2342 */     this.mc.setTreatUtilDateAsTimestamp(flag);
/*      */   }
/*      */   
/*      */   public void setTrustCertificateKeyStorePassword(String value) {
/* 2346 */     this.mc.setTrustCertificateKeyStorePassword(value);
/*      */   }
/*      */   
/*      */   public void setTrustCertificateKeyStoreType(String value) {
/* 2350 */     this.mc.setTrustCertificateKeyStoreType(value);
/*      */   }
/*      */   
/*      */   public void setTrustCertificateKeyStoreUrl(String value) {
/* 2354 */     this.mc.setTrustCertificateKeyStoreUrl(value);
/*      */   }
/*      */   
/*      */   public void setUltraDevHack(boolean flag) {
/* 2358 */     this.mc.setUltraDevHack(flag);
/*      */   }
/*      */   
/*      */   public void setUseBlobToStoreUTF8OutsideBMP(boolean flag) {
/* 2362 */     this.mc.setUseBlobToStoreUTF8OutsideBMP(flag);
/*      */   }
/*      */   
/*      */   public void setUseCompression(boolean property) {
/* 2366 */     this.mc.setUseCompression(property);
/*      */   }
/*      */   
/*      */   public void setUseConfigs(String configs) {
/* 2370 */     this.mc.setUseConfigs(configs);
/*      */   }
/*      */   
/*      */   public void setUseCursorFetch(boolean flag) {
/* 2374 */     this.mc.setUseCursorFetch(flag);
/*      */   }
/*      */   
/*      */   public void setUseDirectRowUnpack(boolean flag) {
/* 2378 */     this.mc.setUseDirectRowUnpack(flag);
/*      */   }
/*      */   
/*      */   public void setUseDynamicCharsetInfo(boolean flag) {
/* 2382 */     this.mc.setUseDynamicCharsetInfo(flag);
/*      */   }
/*      */   
/*      */   public void setUseFastDateParsing(boolean flag) {
/* 2386 */     this.mc.setUseFastDateParsing(flag);
/*      */   }
/*      */   
/*      */   public void setUseFastIntParsing(boolean flag) {
/* 2390 */     this.mc.setUseFastIntParsing(flag);
/*      */   }
/*      */   
/*      */   public void setUseGmtMillisForDatetimes(boolean flag) {
/* 2394 */     this.mc.setUseGmtMillisForDatetimes(flag);
/*      */   }
/*      */   
/*      */   public void setUseHostsInPrivileges(boolean property) {
/* 2398 */     this.mc.setUseHostsInPrivileges(property);
/*      */   }
/*      */   
/*      */   public void setUseInformationSchema(boolean flag) {
/* 2402 */     this.mc.setUseInformationSchema(flag);
/*      */   }
/*      */   
/*      */   public void setUseJDBCCompliantTimezoneShift(boolean flag) {
/* 2406 */     this.mc.setUseJDBCCompliantTimezoneShift(flag);
/*      */   }
/*      */   
/*      */   public void setUseJvmCharsetConverters(boolean flag) {
/* 2410 */     this.mc.setUseJvmCharsetConverters(flag);
/*      */   }
/*      */   
/*      */   public void setUseLocalSessionState(boolean flag) {
/* 2414 */     this.mc.setUseLocalSessionState(flag);
/*      */   }
/*      */   
/*      */   public void setUseNanosForElapsedTime(boolean flag) {
/* 2418 */     this.mc.setUseNanosForElapsedTime(flag);
/*      */   }
/*      */   
/*      */   public void setUseOldAliasMetadataBehavior(boolean flag) {
/* 2422 */     this.mc.setUseOldAliasMetadataBehavior(flag);
/*      */   }
/*      */   
/*      */   public void setUseOldUTF8Behavior(boolean flag) {
/* 2426 */     this.mc.setUseOldUTF8Behavior(flag);
/*      */   }
/*      */   
/*      */   public void setUseOnlyServerErrorMessages(boolean flag) {
/* 2430 */     this.mc.setUseOnlyServerErrorMessages(flag);
/*      */   }
/*      */   
/*      */   public void setUseReadAheadInput(boolean flag) {
/* 2434 */     this.mc.setUseReadAheadInput(flag);
/*      */   }
/*      */   
/*      */   public void setUseSSL(boolean property) {
/* 2438 */     this.mc.setUseSSL(property);
/*      */   }
/*      */   
/*      */   public void setUseSSPSCompatibleTimezoneShift(boolean flag) {
/* 2442 */     this.mc.setUseSSPSCompatibleTimezoneShift(flag);
/*      */   }
/*      */   
/*      */   public void setUseServerPrepStmts(boolean flag) {
/* 2446 */     this.mc.setUseServerPrepStmts(flag);
/*      */   }
/*      */   
/*      */   public void setUseServerPreparedStmts(boolean flag) {
/* 2450 */     this.mc.setUseServerPreparedStmts(flag);
/*      */   }
/*      */   
/*      */   public void setUseSqlStateCodes(boolean flag) {
/* 2454 */     this.mc.setUseSqlStateCodes(flag);
/*      */   }
/*      */   
/*      */   public void setUseStreamLengthsInPrepStmts(boolean property) {
/* 2458 */     this.mc.setUseStreamLengthsInPrepStmts(property);
/*      */   }
/*      */   
/*      */   public void setUseTimezone(boolean property) {
/* 2462 */     this.mc.setUseTimezone(property);
/*      */   }
/*      */   
/*      */   public void setUseUltraDevWorkAround(boolean property) {
/* 2466 */     this.mc.setUseUltraDevWorkAround(property);
/*      */   }
/*      */   
/*      */   public void setUseUnbufferedInput(boolean flag) {
/* 2470 */     this.mc.setUseUnbufferedInput(flag);
/*      */   }
/*      */   
/*      */   public void setUseUnicode(boolean flag) {
/* 2474 */     this.mc.setUseUnicode(flag);
/*      */   }
/*      */   
/*      */   public void setUseUsageAdvisor(boolean useUsageAdvisorFlag) {
/* 2478 */     this.mc.setUseUsageAdvisor(useUsageAdvisorFlag);
/*      */   }
/*      */   
/*      */   public void setUtf8OutsideBmpExcludedColumnNamePattern(String regexPattern) {
/* 2482 */     this.mc.setUtf8OutsideBmpExcludedColumnNamePattern(regexPattern);
/*      */   }
/*      */   
/*      */   public void setUtf8OutsideBmpIncludedColumnNamePattern(String regexPattern) {
/* 2486 */     this.mc.setUtf8OutsideBmpIncludedColumnNamePattern(regexPattern);
/*      */   }
/*      */   
/*      */   public void setYearIsDateType(boolean flag) {
/* 2490 */     this.mc.setYearIsDateType(flag);
/*      */   }
/*      */   
/*      */   public void setZeroDateTimeBehavior(String behavior) {
/* 2494 */     this.mc.setZeroDateTimeBehavior(behavior);
/*      */   }
/*      */   
/*      */   public boolean useUnbufferedInput() {
/* 2498 */     return this.mc.useUnbufferedInput();
/*      */   }
/*      */   
/*      */   public void initializeExtension(Extension ex) throws SQLException {
/* 2502 */     this.mc.initializeExtension(ex);
/*      */   }
/*      */   
/*      */   public String getProfilerEventHandler() {
/* 2506 */     return this.mc.getProfilerEventHandler();
/*      */   }
/*      */   
/*      */   public void setProfilerEventHandler(String handler) {
/* 2510 */     this.mc.setProfilerEventHandler(handler);
/*      */   }
/*      */   
/*      */   public boolean getVerifyServerCertificate() {
/* 2514 */     return this.mc.getVerifyServerCertificate();
/*      */   }
/*      */   
/*      */   public void setVerifyServerCertificate(boolean flag) {
/* 2518 */     this.mc.setVerifyServerCertificate(flag);
/*      */   }
/*      */   
/*      */   public boolean getUseLegacyDatetimeCode() {
/* 2522 */     return this.mc.getUseLegacyDatetimeCode();
/*      */   }
/*      */   
/*      */   public void setUseLegacyDatetimeCode(boolean flag) {
/* 2526 */     this.mc.setUseLegacyDatetimeCode(flag);
/*      */   }
/*      */   
/*      */   public int getSelfDestructOnPingMaxOperations() {
/* 2530 */     return this.mc.getSelfDestructOnPingMaxOperations();
/*      */   }
/*      */   
/*      */   public int getSelfDestructOnPingSecondsLifetime() {
/* 2534 */     return this.mc.getSelfDestructOnPingSecondsLifetime();
/*      */   }
/*      */   
/*      */   public void setSelfDestructOnPingMaxOperations(int maxOperations) {
/* 2538 */     this.mc.setSelfDestructOnPingMaxOperations(maxOperations);
/*      */   }
/*      */   
/*      */   public void setSelfDestructOnPingSecondsLifetime(int seconds) {
/* 2542 */     this.mc.setSelfDestructOnPingSecondsLifetime(seconds);
/*      */   }
/*      */   
/*      */   public boolean getUseColumnNamesInFindColumn() {
/* 2546 */     return this.mc.getUseColumnNamesInFindColumn();
/*      */   }
/*      */   
/*      */   public void setUseColumnNamesInFindColumn(boolean flag) {
/* 2550 */     this.mc.setUseColumnNamesInFindColumn(flag);
/*      */   }
/*      */   
/*      */   public boolean getUseLocalTransactionState() {
/* 2554 */     return this.mc.getUseLocalTransactionState();
/*      */   }
/*      */   
/*      */   public void setUseLocalTransactionState(boolean flag) {
/* 2558 */     this.mc.setUseLocalTransactionState(flag);
/*      */   }
/*      */   
/*      */   public boolean getCompensateOnDuplicateKeyUpdateCounts() {
/* 2562 */     return this.mc.getCompensateOnDuplicateKeyUpdateCounts();
/*      */   }
/*      */   
/*      */   public void setCompensateOnDuplicateKeyUpdateCounts(boolean flag) {
/* 2566 */     this.mc.setCompensateOnDuplicateKeyUpdateCounts(flag);
/*      */   }
/*      */   
/*      */   public boolean getUseAffectedRows() {
/* 2570 */     return this.mc.getUseAffectedRows();
/*      */   }
/*      */   
/*      */   public void setUseAffectedRows(boolean flag) {
/* 2574 */     this.mc.setUseAffectedRows(flag);
/*      */   }
/*      */   
/*      */   public String getPasswordCharacterEncoding() {
/* 2578 */     return this.mc.getPasswordCharacterEncoding();
/*      */   }
/*      */   
/*      */   public void setPasswordCharacterEncoding(String characterSet) {
/* 2582 */     this.mc.setPasswordCharacterEncoding(characterSet);
/*      */   }
/*      */   
/*      */   public int getAutoIncrementIncrement() {
/* 2586 */     return this.mc.getAutoIncrementIncrement();
/*      */   }
/*      */   
/*      */   public int getLoadBalanceBlacklistTimeout() {
/* 2590 */     return this.mc.getLoadBalanceBlacklistTimeout();
/*      */   }
/*      */   
/*      */   public void setLoadBalanceBlacklistTimeout(int loadBalanceBlacklistTimeout) {
/* 2594 */     this.mc.setLoadBalanceBlacklistTimeout(loadBalanceBlacklistTimeout);
/*      */   }
/*      */   public int getLoadBalancePingTimeout() {
/* 2597 */     return this.mc.getLoadBalancePingTimeout();
/*      */   }
/*      */   
/*      */   public void setLoadBalancePingTimeout(int loadBalancePingTimeout) {
/* 2601 */     this.mc.setLoadBalancePingTimeout(loadBalancePingTimeout);
/*      */   }
/*      */   
/*      */   public boolean getLoadBalanceValidateConnectionOnSwapServer() {
/* 2605 */     return this.mc.getLoadBalanceValidateConnectionOnSwapServer();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLoadBalanceValidateConnectionOnSwapServer(boolean loadBalanceValidateConnectionOnSwapServer) {
/* 2610 */     this.mc.setLoadBalanceValidateConnectionOnSwapServer(loadBalanceValidateConnectionOnSwapServer);
/*      */   }
/*      */   
/*      */   public void setRetriesAllDown(int retriesAllDown) {
/* 2614 */     this.mc.setRetriesAllDown(retriesAllDown);
/*      */   }
/*      */   
/*      */   public int getRetriesAllDown() {
/* 2618 */     return this.mc.getRetriesAllDown();
/*      */   }
/*      */   
/*      */   public ExceptionInterceptor getExceptionInterceptor() {
/* 2622 */     return this.pooledConnection.getExceptionInterceptor();
/*      */   }
/*      */   
/*      */   public String getExceptionInterceptors() {
/* 2626 */     return this.mc.getExceptionInterceptors();
/*      */   }
/*      */   
/*      */   public void setExceptionInterceptors(String exceptionInterceptors) {
/* 2630 */     this.mc.setExceptionInterceptors(exceptionInterceptors);
/*      */   }
/*      */   
/*      */   public boolean getQueryTimeoutKillsConnection() {
/* 2634 */     return this.mc.getQueryTimeoutKillsConnection();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setQueryTimeoutKillsConnection(boolean queryTimeoutKillsConnection) {
/* 2639 */     this.mc.setQueryTimeoutKillsConnection(queryTimeoutKillsConnection);
/*      */   }
/*      */   
/*      */   public boolean hasSameProperties(Connection c) {
/* 2643 */     return this.mc.hasSameProperties(c);
/*      */   }
/*      */   
/*      */   public Properties getProperties() {
/* 2647 */     return this.mc.getProperties();
/*      */   }
/*      */   
/*      */   public String getHost() {
/* 2651 */     return this.mc.getHost();
/*      */   }
/*      */   
/*      */   public void setProxy(MySQLConnection conn) {
/* 2655 */     this.mc.setProxy(conn);
/*      */   }
/*      */   
/*      */   public boolean getRetainStatementAfterResultSetClose() {
/* 2659 */     return this.mc.getRetainStatementAfterResultSetClose();
/*      */   }
/*      */   
/*      */   public int getMaxAllowedPacket() {
/* 2663 */     return this.mc.getMaxAllowedPacket();
/*      */   }
/*      */   
/*      */   public String getLoadBalanceConnectionGroup() {
/* 2667 */     return this.mc.getLoadBalanceConnectionGroup();
/*      */   }
/*      */   
/*      */   public boolean getLoadBalanceEnableJMX() {
/* 2671 */     return this.mc.getLoadBalanceEnableJMX();
/*      */   }
/*      */   
/*      */   public String getLoadBalanceExceptionChecker() {
/* 2675 */     return this.mc.getLoadBalanceExceptionChecker();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLoadBalanceSQLExceptionSubclassFailover() {
/* 2680 */     return this.mc.getLoadBalanceSQLExceptionSubclassFailover();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLoadBalanceSQLStateFailover() {
/* 2685 */     return this.mc.getLoadBalanceSQLStateFailover();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLoadBalanceConnectionGroup(String loadBalanceConnectionGroup) {
/* 2690 */     this.mc.setLoadBalanceConnectionGroup(loadBalanceConnectionGroup);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceEnableJMX(boolean loadBalanceEnableJMX) {
/* 2696 */     this.mc.setLoadBalanceEnableJMX(loadBalanceEnableJMX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceExceptionChecker(String loadBalanceExceptionChecker) {
/* 2703 */     this.mc.setLoadBalanceExceptionChecker(loadBalanceExceptionChecker);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceSQLExceptionSubclassFailover(String loadBalanceSQLExceptionSubclassFailover) {
/* 2710 */     this.mc.setLoadBalanceSQLExceptionSubclassFailover(loadBalanceSQLExceptionSubclassFailover);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceSQLStateFailover(String loadBalanceSQLStateFailover) {
/* 2717 */     this.mc.setLoadBalanceSQLStateFailover(loadBalanceSQLStateFailover);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLoadBalanceAutoCommitStatementRegex() {
/* 2724 */     return this.mc.getLoadBalanceAutoCommitStatementRegex();
/*      */   }
/*      */   
/*      */   public int getLoadBalanceAutoCommitStatementThreshold() {
/* 2728 */     return this.mc.getLoadBalanceAutoCommitStatementThreshold();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLoadBalanceAutoCommitStatementRegex(String loadBalanceAutoCommitStatementRegex) {
/* 2733 */     this.mc.setLoadBalanceAutoCommitStatementRegex(loadBalanceAutoCommitStatementRegex);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceAutoCommitStatementThreshold(int loadBalanceAutoCommitStatementThreshold) {
/* 2739 */     this.mc.setLoadBalanceAutoCommitStatementThreshold(loadBalanceAutoCommitStatementThreshold);
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\jdbc2\optional\ConnectionWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */