/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
/*      */ import com.mysql.jdbc.exceptions.MySQLTimeoutException;
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*      */ import java.io.InputStream;
/*      */ import java.math.BigInteger;
/*      */ import java.sql.BatchUpdateException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.TimerTask;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StatementImpl
/*      */   implements Statement
/*      */ {
/*      */   protected static final String PING_MARKER = "/* ping */";
/*      */   
/*      */   class CancelTask
/*      */     extends TimerTask
/*      */   {
/*   73 */     long connectionId = 0L;
/*   74 */     SQLException caughtWhileCancelling = null;
/*      */     StatementImpl toCancel;
/*      */     
/*      */     CancelTask(StatementImpl cancellee) throws SQLException {
/*   78 */       this.connectionId = StatementImpl.this.connection.getIO().getThreadId();
/*   79 */       this.toCancel = cancellee;
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/*   84 */       Thread cancelThread = new Thread()
/*      */         {
/*      */           public void run() {
/*   87 */             if (StatementImpl.this.connection.getQueryTimeoutKillsConnection()) {
/*      */               try {
/*   89 */                 StatementImpl.CancelTask.this.toCancel.wasCancelled = true;
/*   90 */                 StatementImpl.CancelTask.this.toCancel.wasCancelledByTimeout = true;
/*   91 */                 StatementImpl.this.connection.realClose(false, false, true, (Throwable)new MySQLStatementCancelledException(Messages.getString("Statement.ConnectionKilledDueToTimeout")));
/*      */               }
/*   93 */               catch (NullPointerException npe) {
/*      */               
/*   95 */               } catch (SQLException sqlEx) {
/*   96 */                 StatementImpl.CancelTask.this.caughtWhileCancelling = sqlEx;
/*      */               } 
/*      */             } else {
/*   99 */               Connection cancelConn = null;
/*  100 */               Statement cancelStmt = null;
/*      */               
/*      */               try {
/*  103 */                 synchronized (StatementImpl.this.cancelTimeoutMutex) {
/*  104 */                   cancelConn = StatementImpl.this.connection.duplicate();
/*  105 */                   cancelStmt = cancelConn.createStatement();
/*  106 */                   cancelStmt.execute("KILL QUERY " + StatementImpl.CancelTask.this.connectionId);
/*  107 */                   StatementImpl.CancelTask.this.toCancel.wasCancelled = true;
/*  108 */                   StatementImpl.CancelTask.this.toCancel.wasCancelledByTimeout = true;
/*      */                 } 
/*  110 */               } catch (SQLException sqlEx) {
/*  111 */                 StatementImpl.CancelTask.this.caughtWhileCancelling = sqlEx;
/*  112 */               } catch (NullPointerException npe) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               }
/*      */               finally {
/*      */                 
/*  120 */                 if (cancelStmt != null) {
/*      */                   try {
/*  122 */                     cancelStmt.close();
/*  123 */                   } catch (SQLException sqlEx) {
/*  124 */                     throw new RuntimeException(sqlEx.toString());
/*      */                   } 
/*      */                 }
/*      */                 
/*  128 */                 if (cancelConn != null) {
/*      */                   try {
/*  130 */                     cancelConn.close();
/*  131 */                   } catch (SQLException sqlEx) {
/*  132 */                     throw new RuntimeException(sqlEx.toString());
/*      */                   } 
/*      */                 }
/*      */                 
/*  136 */                 StatementImpl.CancelTask.this.toCancel = null;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         };
/*      */       
/*  142 */       cancelThread.start();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   protected Object cancelTimeoutMutex = new Object();
/*      */ 
/*      */   
/*  152 */   protected static int statementCounter = 1;
/*      */ 
/*      */   
/*      */   public static final byte USES_VARIABLES_FALSE = 0;
/*      */   
/*      */   public static final byte USES_VARIABLES_TRUE = 1;
/*      */   
/*      */   public static final byte USES_VARIABLES_UNKNOWN = -1;
/*      */   
/*      */   protected boolean wasCancelled = false;
/*      */   
/*      */   protected boolean wasCancelledByTimeout = false;
/*      */   
/*      */   protected List batchedArgs;
/*      */   
/*  167 */   protected SingleByteCharsetConverter charConverter = null;
/*      */ 
/*      */   
/*  170 */   protected String charEncoding = null;
/*      */ 
/*      */   
/*  173 */   protected MySQLConnection connection = null;
/*      */   
/*  175 */   protected long connectionId = 0L;
/*      */ 
/*      */   
/*  178 */   protected String currentCatalog = null;
/*      */ 
/*      */   
/*      */   protected boolean doEscapeProcessing = true;
/*      */ 
/*      */   
/*  184 */   protected ProfilerEventHandler eventSink = null;
/*      */ 
/*      */   
/*  187 */   private int fetchSize = 0;
/*      */ 
/*      */   
/*      */   protected boolean isClosed = false;
/*      */ 
/*      */   
/*  193 */   protected long lastInsertId = -1L;
/*      */ 
/*      */   
/*  196 */   protected int maxFieldSize = MysqlIO.getMaxBuf();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  202 */   protected int maxRows = -1;
/*      */ 
/*      */   
/*      */   protected boolean maxRowsChanged = false;
/*      */ 
/*      */   
/*  208 */   protected Set openResults = new HashSet();
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean pedantic = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected Throwable pointOfOrigin;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean profileSQL = false;
/*      */ 
/*      */   
/*  223 */   protected ResultSetInternalMethods results = null;
/*      */ 
/*      */   
/*  226 */   protected int resultSetConcurrency = 0;
/*      */ 
/*      */   
/*  229 */   protected int resultSetType = 0;
/*      */ 
/*      */   
/*      */   protected int statementId;
/*      */ 
/*      */   
/*  235 */   protected int timeoutInMillis = 0;
/*      */ 
/*      */   
/*  238 */   protected long updateCount = -1L;
/*      */ 
/*      */   
/*      */   protected boolean useUsageAdvisor = false;
/*      */ 
/*      */   
/*  244 */   protected SQLWarning warningChain = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean holdResultsOpenOverClose = false;
/*      */ 
/*      */ 
/*      */   
/*  252 */   protected ArrayList batchedGeneratedKeys = null;
/*      */   
/*      */   protected boolean retrieveGeneratedKeys = false;
/*      */   
/*      */   protected boolean continueBatchOnError = false;
/*      */   
/*  258 */   protected PingTarget pingTarget = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean useLegacyDatetimeCode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ExceptionInterceptor exceptionInterceptor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean lastQueryIsOnDupKeyUpdate = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int originalResultSetType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int originalFetchSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isPoolable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InputStream localInfileInputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addBatch(String sql) throws SQLException {
/*  352 */     if (this.batchedArgs == null) {
/*  353 */       this.batchedArgs = new ArrayList();
/*      */     }
/*      */     
/*  356 */     if (sql != null) {
/*  357 */       this.batchedArgs.add(sql);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancel() throws SQLException {
/*  367 */     if (!this.isClosed && this.connection != null && this.connection.versionMeetsMinimum(5, 0, 0)) {
/*      */ 
/*      */       
/*  370 */       Connection cancelConn = null;
/*  371 */       Statement cancelStmt = null;
/*      */       
/*      */       try {
/*  374 */         cancelConn = this.connection.duplicate();
/*  375 */         cancelStmt = cancelConn.createStatement();
/*  376 */         cancelStmt.execute("KILL QUERY " + this.connection.getIO().getThreadId());
/*      */         
/*  378 */         this.wasCancelled = true;
/*      */       } finally {
/*  380 */         if (cancelStmt != null) {
/*  381 */           cancelStmt.close();
/*      */         }
/*      */         
/*  384 */         if (cancelConn != null) {
/*  385 */           cancelConn.close();
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
/*      */   protected void checkClosed() throws SQLException {
/*  401 */     if (this.isClosed) {
/*  402 */       throw SQLError.createSQLException(Messages.getString("Statement.49"), "08003", getExceptionInterceptor());
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
/*      */   protected void checkForDml(String sql, char firstStatementChar) throws SQLException {
/*  422 */     if (firstStatementChar == 'I' || firstStatementChar == 'U' || firstStatementChar == 'D' || firstStatementChar == 'A' || firstStatementChar == 'C') {
/*      */ 
/*      */       
/*  425 */       String noCommentSql = StringUtils.stripComments(sql, "'\"", "'\"", true, false, true, true);
/*      */ 
/*      */       
/*  428 */       if (StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "INSERT") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "UPDATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DELETE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DROP") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "CREATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "ALTER"))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  434 */         throw SQLError.createSQLException(Messages.getString("Statement.57"), "S1009", getExceptionInterceptor());
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
/*      */   protected void checkNullOrEmptyQuery(String sql) throws SQLException {
/*  451 */     if (sql == null) {
/*  452 */       throw SQLError.createSQLException(Messages.getString("Statement.59"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  457 */     if (sql.length() == 0) {
/*  458 */       throw SQLError.createSQLException(Messages.getString("Statement.61"), "S1009", getExceptionInterceptor());
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
/*      */   public synchronized void clearBatch() throws SQLException {
/*  473 */     if (this.batchedArgs != null) {
/*  474 */       this.batchedArgs.clear();
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
/*      */   public void clearWarnings() throws SQLException {
/*  486 */     this.warningChain = null;
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
/*      */   public synchronized void close() throws SQLException {
/*  505 */     realClose(true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void closeAllOpenResults() {
/*  512 */     if (this.openResults != null) {
/*  513 */       for (Iterator<ResultSetInternalMethods> iter = this.openResults.iterator(); iter.hasNext(); ) {
/*  514 */         ResultSetInternalMethods element = iter.next();
/*      */         
/*      */         try {
/*  517 */           element.realClose(false);
/*  518 */         } catch (SQLException sqlEx) {
/*  519 */           AssertionFailedException.shouldNotHappen(sqlEx);
/*      */         } 
/*      */       } 
/*      */       
/*  523 */       this.openResults.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   public synchronized void removeOpenResultSet(ResultSet rs) {
/*  528 */     if (this.openResults != null) {
/*  529 */       this.openResults.remove(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public synchronized int getOpenResultSetCount() {
/*  534 */     if (this.openResults != null) {
/*  535 */       return this.openResults.size();
/*      */     }
/*      */     
/*  538 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ResultSetInternalMethods createResultSetUsingServerFetch(String sql) throws SQLException {
/*  547 */     PreparedStatement pStmt = this.connection.prepareStatement(sql, this.resultSetType, this.resultSetConcurrency);
/*      */ 
/*      */     
/*  550 */     pStmt.setFetchSize(this.fetchSize);
/*      */     
/*  552 */     if (this.maxRows > -1) {
/*  553 */       pStmt.setMaxRows(this.maxRows);
/*      */     }
/*      */     
/*  556 */     pStmt.execute();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  562 */     ResultSetInternalMethods rs = ((StatementImpl)pStmt).getResultSetInternal();
/*      */ 
/*      */     
/*  565 */     rs.setStatementUsedForFetchingRows((PreparedStatement)pStmt);
/*      */ 
/*      */     
/*  568 */     this.results = rs;
/*      */     
/*  570 */     return rs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean createStreamingResultSet() {
/*  581 */     return (this.resultSetType == 1003 && this.resultSetConcurrency == 1007 && this.fetchSize == Integer.MIN_VALUE);
/*      */   }
/*      */   
/*      */   public StatementImpl(MySQLConnection c, String catalog) throws SQLException {
/*  585 */     this.originalResultSetType = 0;
/*  586 */     this.originalFetchSize = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2669 */     this.isPoolable = true; if (c == null || c.isClosed()) throw SQLError.createSQLException(Messages.getString("Statement.0"), "08003", null);  this.connection = c; this.connectionId = this.connection.getId(); this.exceptionInterceptor = this.connection.getExceptionInterceptor(); this.currentCatalog = catalog; this.pedantic = this.connection.getPedantic(); this.continueBatchOnError = this.connection.getContinueBatchOnError(); this.useLegacyDatetimeCode = this.connection.getUseLegacyDatetimeCode(); if (!this.connection.getDontTrackOpenResources()) this.connection.registerStatement(this);  if (this.connection != null) { this.maxFieldSize = this.connection.getMaxAllowedPacket(); int defaultFetchSize = this.connection.getDefaultFetchSize(); if (defaultFetchSize != 0) setFetchSize(defaultFetchSize);  }  if (this.connection.getUseUnicode()) { this.charEncoding = this.connection.getEncoding(); this.charConverter = this.connection.getCharsetConverter(this.charEncoding); }  boolean profiling = (this.connection.getProfileSql() || this.connection.getUseUsageAdvisor() || this.connection.getLogSlowQueries()); if (this.connection.getAutoGenerateTestcaseScript() || profiling) this.statementId = statementCounter++;  if (profiling) { this.pointOfOrigin = new Throwable(); this.profileSQL = this.connection.getProfileSql(); this.useUsageAdvisor = this.connection.getUseUsageAdvisor(); this.eventSink = ProfilerEventHandlerFactory.getInstance(this.connection); }  int maxRowsConn = this.connection.getMaxRows(); if (maxRowsConn != -1) setMaxRows(maxRowsConn);  this.holdResultsOpenOverClose = this.connection.getHoldResultsOpenOverStatementClose();
/*      */   }
/*      */   public void enableStreamingResults() throws SQLException { this.originalResultSetType = this.resultSetType; this.originalFetchSize = this.fetchSize; setFetchSize(-2147483648); setResultSetType(1003); }
/* 2672 */   public void disableStreamingResults() throws SQLException { if (this.fetchSize == Integer.MIN_VALUE && this.resultSetType == 1003) { setFetchSize(this.originalFetchSize); setResultSetType(this.originalResultSetType); }  } public boolean execute(String sql) throws SQLException { return execute(sql, false); } private boolean execute(String sql, boolean returnGeneratedKeys) throws SQLException { checkClosed(); MySQLConnection locallyScopedConn = this.connection; synchronized (locallyScopedConn.getMutex()) { this.retrieveGeneratedKeys = returnGeneratedKeys; this.lastQueryIsOnDupKeyUpdate = false; if (returnGeneratedKeys) this.lastQueryIsOnDupKeyUpdate = containsOnDuplicateKeyInString(sql);  resetCancelledState(); checkNullOrEmptyQuery(sql); checkClosed(); char firstNonWsChar = StringUtils.firstAlphaCharUc(sql, findStartOfStatement(sql)); boolean isSelect = true; if (firstNonWsChar != 'S') { isSelect = false; if (locallyScopedConn.isReadOnly()) throw SQLError.createSQLException(Messages.getString("Statement.27") + Messages.getString("Statement.28"), "S1009", getExceptionInterceptor());  }  boolean doStreaming = createStreamingResultSet(); if (doStreaming && locallyScopedConn.getNetTimeoutForStreamingResults() > 0) executeSimpleNonQuery(locallyScopedConn, "SET net_write_timeout=" + locallyScopedConn.getNetTimeoutForStreamingResults());  if (this.doEscapeProcessing) { Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, locallyScopedConn.serverSupportsConvertFn(), locallyScopedConn); if (escapedSqlResult instanceof String) { sql = (String)escapedSqlResult; } else { sql = ((EscapeProcessorResult)escapedSqlResult).escapedSql; }  }  if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose()) this.results.realClose(false);  if (sql.charAt(0) == '/' && sql.startsWith("/* ping */")) { doPingInstead(); return true; }  CachedResultSetMetaData cachedMetaData = null; ResultSetInternalMethods rs = null; this.batchedGeneratedKeys = null; if (useServerFetch()) { rs = createResultSetUsingServerFetch(sql); } else { CancelTask timeoutTask = null; String oldCatalog = null; try { if (locallyScopedConn.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) { timeoutTask = new CancelTask(this); locallyScopedConn.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis); }  if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) { oldCatalog = locallyScopedConn.getCatalog(); locallyScopedConn.setCatalog(this.currentCatalog); }  Field[] cachedFields = null; if (locallyScopedConn.getCacheResultSetMetadata()) { cachedMetaData = locallyScopedConn.getCachedMetaData(sql); if (cachedMetaData != null) cachedFields = cachedMetaData.fields;  }  if (locallyScopedConn.useMaxRows()) { int rowLimit = -1; if (isSelect) { if (StringUtils.indexOfIgnoreCase(sql, "LIMIT") != -1) { rowLimit = this.maxRows; } else if (this.maxRows <= 0) { executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT"); } else { executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows); }  } else { executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT"); }  rs = locallyScopedConn.execSQL(this, sql, rowLimit, (Buffer)null, this.resultSetType, this.resultSetConcurrency, doStreaming, this.currentCatalog, cachedFields); } else { rs = locallyScopedConn.execSQL(this, sql, -1, (Buffer)null, this.resultSetType, this.resultSetConcurrency, doStreaming, this.currentCatalog, cachedFields); }  if (timeoutTask != null) { if (timeoutTask.caughtWhileCancelling != null) throw timeoutTask.caughtWhileCancelling;  timeoutTask.cancel(); timeoutTask = null; }  synchronized (this.cancelTimeoutMutex) { if (this.wasCancelled) { MySQLStatementCancelledException mySQLStatementCancelledException; SQLException cause = null; if (this.wasCancelledByTimeout) { MySQLTimeoutException mySQLTimeoutException = new MySQLTimeoutException(); } else { mySQLStatementCancelledException = new MySQLStatementCancelledException(); }  resetCancelledState(); throw mySQLStatementCancelledException; }  }  } finally { if (timeoutTask != null) { timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); }  if (oldCatalog != null) locallyScopedConn.setCatalog(oldCatalog);  }  }  if (rs != null) { this.lastInsertId = rs.getUpdateID(); this.results = rs; rs.setFirstCharOfQuery(firstNonWsChar); if (rs.reallyResult()) if (cachedMetaData != null) { locallyScopedConn.initializeResultsMetadataFromCache(sql, cachedMetaData, this.results); } else if (this.connection.getCacheResultSetMetadata()) { locallyScopedConn.initializeResultsMetadataFromCache(sql, (CachedResultSetMetaData)null, this.results); }   }  return (rs != null && rs.reallyResult()); }  } protected synchronized void resetCancelledState() { if (this.cancelTimeoutMutex == null) return;  synchronized (this.cancelTimeoutMutex) { this.wasCancelled = false; this.wasCancelledByTimeout = false; }  } public boolean execute(String sql, int returnGeneratedKeys) throws SQLException { if (returnGeneratedKeys == 1) { checkClosed(); MySQLConnection locallyScopedConn = this.connection; synchronized (locallyScopedConn.getMutex()) { boolean readInfoMsgState = this.connection.isReadInfoMsgEnabled(); locallyScopedConn.setReadInfoMsgEnabled(true); try { return execute(sql, true); } finally { locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState); }  }  }  return execute(sql); } public boolean execute(String sql, int[] generatedKeyIndices) throws SQLException { if (generatedKeyIndices != null && generatedKeyIndices.length > 0) { checkClosed(); MySQLConnection locallyScopedConn = this.connection; synchronized (locallyScopedConn.getMutex()) { this.retrieveGeneratedKeys = true; boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled(); locallyScopedConn.setReadInfoMsgEnabled(true); try { return execute(sql, true); } finally { locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState); }  }  }  return execute(sql); } public boolean execute(String sql, String[] generatedKeyNames) throws SQLException { if (generatedKeyNames != null && generatedKeyNames.length > 0) { checkClosed(); MySQLConnection locallyScopedConn = this.connection; synchronized (locallyScopedConn.getMutex()) { this.retrieveGeneratedKeys = true; boolean readInfoMsgState = this.connection.isReadInfoMsgEnabled(); locallyScopedConn.setReadInfoMsgEnabled(true); try { return execute(sql, true); } finally { locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState); }  }  }  return execute(sql); } public synchronized int[] executeBatch() throws SQLException { checkClosed(); MySQLConnection locallyScopedConn = this.connection; if (locallyScopedConn.isReadOnly()) throw SQLError.createSQLException(Messages.getString("Statement.34") + Messages.getString("Statement.35"), "S1009", getExceptionInterceptor());  if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose()) this.results.realClose(false);  synchronized (locallyScopedConn.getMutex()) { if (this.batchedArgs == null || this.batchedArgs.size() == 0) return new int[0];  int individualStatementTimeout = this.timeoutInMillis; this.timeoutInMillis = 0; CancelTask timeoutTask = null; try { resetCancelledState(); this.retrieveGeneratedKeys = true; int[] updateCounts = null; if (this.batchedArgs != null) { int nbrCommands = this.batchedArgs.size(); this.batchedGeneratedKeys = new ArrayList(this.batchedArgs.size()); boolean multiQueriesEnabled = locallyScopedConn.getAllowMultiQueries(); if (locallyScopedConn.versionMeetsMinimum(4, 1, 1) && (multiQueriesEnabled || (locallyScopedConn.getRewriteBatchedStatements() && nbrCommands > 4))) return executeBatchUsingMultiQueries(multiQueriesEnabled, nbrCommands, individualStatementTimeout);  if (locallyScopedConn.getEnableQueryTimeouts() && individualStatementTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) { timeoutTask = new CancelTask(this); locallyScopedConn.getCancelTimer().schedule(timeoutTask, individualStatementTimeout); }  updateCounts = new int[nbrCommands]; for (int i = 0; i < nbrCommands; i++) updateCounts[i] = -3;  SQLException sqlEx = null; int commandIndex = 0; for (commandIndex = 0; commandIndex < nbrCommands; commandIndex++) { try { String sql = this.batchedArgs.get(commandIndex); updateCounts[commandIndex] = executeUpdate(sql, true, true); getBatchedGeneratedKeys(containsOnDuplicateKeyInString(sql) ? 1 : 0); } catch (SQLException ex) { updateCounts[commandIndex] = -3; if (this.continueBatchOnError && !(ex instanceof MySQLTimeoutException) && !(ex instanceof MySQLStatementCancelledException) && !hasDeadlockOrTimeoutRolledBackTx(ex)) { sqlEx = ex; } else { int[] newUpdateCounts = new int[commandIndex]; if (hasDeadlockOrTimeoutRolledBackTx(ex)) { for (int j = 0; j < newUpdateCounts.length; j++) newUpdateCounts[j] = -3;  } else { System.arraycopy(updateCounts, 0, newUpdateCounts, 0, commandIndex); }  throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts); }  }  }  if (sqlEx != null) throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);  }  if (timeoutTask != null) { if (timeoutTask.caughtWhileCancelling != null) throw timeoutTask.caughtWhileCancelling;  timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); timeoutTask = null; }  return (updateCounts != null) ? updateCounts : new int[0]; } finally { if (timeoutTask != null) { timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); }  resetCancelledState(); this.timeoutInMillis = individualStatementTimeout; clearBatch(); }  }  } protected final boolean hasDeadlockOrTimeoutRolledBackTx(SQLException ex) { int vendorCode = ex.getErrorCode(); switch (vendorCode) { case 1206: case 1213: return true;case 1205: try { return !this.connection.versionMeetsMinimum(5, 0, 13); } catch (SQLException sqlEx) { return false; }  }  return false; } private int[] executeBatchUsingMultiQueries(boolean multiQueriesEnabled, int nbrCommands, int individualStatementTimeout) throws SQLException { MySQLConnection locallyScopedConn = this.connection; if (!multiQueriesEnabled) locallyScopedConn.getIO().enableMultiQueries();  Statement batchStmt = null; CancelTask timeoutTask = null; try { int[] updateCounts = new int[nbrCommands]; for (int i = 0; i < nbrCommands; i++) updateCounts[i] = -3;  int commandIndex = 0; StringBuffer queryBuf = new StringBuffer(); batchStmt = locallyScopedConn.createStatement(); if (locallyScopedConn.getEnableQueryTimeouts() && individualStatementTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) { timeoutTask = new CancelTask((StatementImpl)batchStmt); locallyScopedConn.getCancelTimer().schedule(timeoutTask, individualStatementTimeout); }  int counter = 0; int numberOfBytesPerChar = 1; String connectionEncoding = locallyScopedConn.getEncoding(); if (StringUtils.startsWithIgnoreCase(connectionEncoding, "utf")) { numberOfBytesPerChar = 3; } else if (CharsetMapping.isMultibyteCharset(connectionEncoding)) { numberOfBytesPerChar = 2; }  int escapeAdjust = 1; batchStmt.setEscapeProcessing(this.doEscapeProcessing); if (this.doEscapeProcessing) escapeAdjust = 2;  SQLException sqlEx = null; int argumentSetsInBatchSoFar = 0; for (commandIndex = 0; commandIndex < nbrCommands; commandIndex++) { String nextQuery = this.batchedArgs.get(commandIndex); if (((queryBuf.length() + nextQuery.length()) * numberOfBytesPerChar + 1 + 4) * escapeAdjust + 32 > this.connection.getMaxAllowedPacket()) { try { batchStmt.execute(queryBuf.toString(), 1); } catch (SQLException ex) { sqlEx = handleExceptionForBatch(commandIndex, argumentSetsInBatchSoFar, updateCounts, ex); }  counter = processMultiCountsAndKeys((StatementImpl)batchStmt, counter, updateCounts); queryBuf = new StringBuffer(); argumentSetsInBatchSoFar = 0; }  queryBuf.append(nextQuery); queryBuf.append(";"); argumentSetsInBatchSoFar++; }  if (queryBuf.length() > 0) { try { batchStmt.execute(queryBuf.toString(), 1); } catch (SQLException ex) { sqlEx = handleExceptionForBatch(commandIndex - 1, argumentSetsInBatchSoFar, updateCounts, ex); }  counter = processMultiCountsAndKeys((StatementImpl)batchStmt, counter, updateCounts); }  if (timeoutTask != null) { if (timeoutTask.caughtWhileCancelling != null) throw timeoutTask.caughtWhileCancelling;  timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); timeoutTask = null; }  if (sqlEx != null) throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);  return (updateCounts != null) ? updateCounts : new int[0]; } finally { if (timeoutTask != null) { timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); }  resetCancelledState(); try { if (batchStmt != null) batchStmt.close();  } finally { if (!multiQueriesEnabled) locallyScopedConn.getIO().disableMultiQueries();  }  }  } protected int processMultiCountsAndKeys(StatementImpl batchedStatement, int updateCountCounter, int[] updateCounts) throws SQLException { updateCounts[updateCountCounter++] = batchedStatement.getUpdateCount(); boolean doGenKeys = (this.batchedGeneratedKeys != null); byte[][] row = (byte[][])null; if (doGenKeys) { long generatedKey = batchedStatement.getLastInsertID(); row = new byte[1][]; row[0] = Long.toString(generatedKey).getBytes(); this.batchedGeneratedKeys.add(new ByteArrayRow(row, getExceptionInterceptor())); }  while (batchedStatement.getMoreResults() || batchedStatement.getUpdateCount() != -1) { updateCounts[updateCountCounter++] = batchedStatement.getUpdateCount(); if (doGenKeys) { long generatedKey = batchedStatement.getLastInsertID(); row = new byte[1][]; row[0] = Long.toString(generatedKey).getBytes(); this.batchedGeneratedKeys.add(new ByteArrayRow(row, getExceptionInterceptor())); }  }  return updateCountCounter; } protected SQLException handleExceptionForBatch(int endOfBatchIndex, int numValuesPerBatch, int[] updateCounts, SQLException ex) throws BatchUpdateException { SQLException sqlEx; for (int j = endOfBatchIndex; j > endOfBatchIndex - numValuesPerBatch; j--) updateCounts[j] = -3;  if (this.continueBatchOnError && !(ex instanceof MySQLTimeoutException) && !(ex instanceof MySQLStatementCancelledException) && !hasDeadlockOrTimeoutRolledBackTx(ex)) { sqlEx = ex; } else { int[] newUpdateCounts = new int[endOfBatchIndex]; System.arraycopy(updateCounts, 0, newUpdateCounts, 0, endOfBatchIndex); throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts); }  return sqlEx; } public ResultSet executeQuery(String sql) throws SQLException { checkClosed(); MySQLConnection locallyScopedConn = this.connection; synchronized (locallyScopedConn.getMutex()) { this.retrieveGeneratedKeys = false; resetCancelledState(); checkNullOrEmptyQuery(sql); boolean doStreaming = createStreamingResultSet(); if (doStreaming && this.connection.getNetTimeoutForStreamingResults() > 0) executeSimpleNonQuery(locallyScopedConn, "SET net_write_timeout=" + this.connection.getNetTimeoutForStreamingResults());  if (this.doEscapeProcessing) { Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, locallyScopedConn.serverSupportsConvertFn(), this.connection); if (escapedSqlResult instanceof String) { sql = (String)escapedSqlResult; } else { sql = ((EscapeProcessorResult)escapedSqlResult).escapedSql; }  }  char firstStatementChar = StringUtils.firstNonWsCharUc(sql, findStartOfStatement(sql)); if (sql.charAt(0) == '/' && sql.startsWith("/* ping */")) { doPingInstead(); return this.results; }  checkForDml(sql, firstStatementChar); if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose()) this.results.realClose(false);  CachedResultSetMetaData cachedMetaData = null; if (useServerFetch()) { this.results = createResultSetUsingServerFetch(sql); return this.results; }  CancelTask timeoutTask = null; String oldCatalog = null; try { if (locallyScopedConn.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) { timeoutTask = new CancelTask(this); locallyScopedConn.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis); }  if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) { oldCatalog = locallyScopedConn.getCatalog(); locallyScopedConn.setCatalog(this.currentCatalog); }  Field[] cachedFields = null; if (locallyScopedConn.getCacheResultSetMetadata()) { cachedMetaData = locallyScopedConn.getCachedMetaData(sql); if (cachedMetaData != null) cachedFields = cachedMetaData.fields;  }  if (locallyScopedConn.useMaxRows()) { if (StringUtils.indexOfIgnoreCase(sql, "LIMIT") != -1) { this.results = locallyScopedConn.execSQL(this, sql, this.maxRows, (Buffer)null, this.resultSetType, this.resultSetConcurrency, doStreaming, this.currentCatalog, cachedFields); } else { if (this.maxRows <= 0) { executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT"); } else { executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows); }  this.results = locallyScopedConn.execSQL(this, sql, -1, (Buffer)null, this.resultSetType, this.resultSetConcurrency, doStreaming, this.currentCatalog, cachedFields); if (oldCatalog != null) locallyScopedConn.setCatalog(oldCatalog);  }  } else { this.results = locallyScopedConn.execSQL(this, sql, -1, (Buffer)null, this.resultSetType, this.resultSetConcurrency, doStreaming, this.currentCatalog, cachedFields); }  if (timeoutTask != null) { if (timeoutTask.caughtWhileCancelling != null) throw timeoutTask.caughtWhileCancelling;  timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); timeoutTask = null; }  synchronized (this.cancelTimeoutMutex) { if (this.wasCancelled) { MySQLStatementCancelledException mySQLStatementCancelledException; SQLException cause = null; if (this.wasCancelledByTimeout) { MySQLTimeoutException mySQLTimeoutException = new MySQLTimeoutException(); } else { mySQLStatementCancelledException = new MySQLStatementCancelledException(); }  resetCancelledState(); throw mySQLStatementCancelledException; }  }  } finally { if (timeoutTask != null) { timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); }  if (oldCatalog != null) locallyScopedConn.setCatalog(oldCatalog);  }  this.lastInsertId = this.results.getUpdateID(); if (cachedMetaData != null) { locallyScopedConn.initializeResultsMetadataFromCache(sql, cachedMetaData, this.results); } else if (this.connection.getCacheResultSetMetadata()) { locallyScopedConn.initializeResultsMetadataFromCache(sql, (CachedResultSetMetaData)null, this.results); }  return this.results; }  } protected void doPingInstead() throws SQLException { if (this.pingTarget != null) { this.pingTarget.doPing(); } else { this.connection.ping(); }  ResultSetInternalMethods fakeSelectOneResultSet = generatePingResultSet(); this.results = fakeSelectOneResultSet; } protected ResultSetInternalMethods generatePingResultSet() throws SQLException { Field[] fields = { new Field(null, "1", -5, 1) }; ArrayList<ByteArrayRow> rows = new ArrayList(); byte[] colVal = { 49 }; rows.add(new ByteArrayRow(new byte[][] { colVal }, getExceptionInterceptor())); return (ResultSetInternalMethods)DatabaseMetaData.buildResultSet(fields, rows, this.connection); } protected void executeSimpleNonQuery(MySQLConnection c, String nonQuery) throws SQLException { c.execSQL(this, nonQuery, -1, (Buffer)null, 1003, 1007, false, this.currentCatalog, (Field[])null, false).close(); } public int executeUpdate(String sql) throws SQLException { return executeUpdate(sql, false, false); } protected int executeUpdate(String sql, boolean isBatch, boolean returnGeneratedKeys) throws SQLException { checkClosed(); MySQLConnection locallyScopedConn = this.connection; char firstStatementChar = StringUtils.firstAlphaCharUc(sql, findStartOfStatement(sql)); ResultSetInternalMethods rs = null; synchronized (locallyScopedConn.getMutex()) { this.retrieveGeneratedKeys = returnGeneratedKeys; resetCancelledState(); checkNullOrEmptyQuery(sql); if (this.doEscapeProcessing) { Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.connection.serverSupportsConvertFn(), this.connection); if (escapedSqlResult instanceof String) { sql = (String)escapedSqlResult; } else { sql = ((EscapeProcessorResult)escapedSqlResult).escapedSql; }  }  if (locallyScopedConn.isReadOnly()) throw SQLError.createSQLException(Messages.getString("Statement.42") + Messages.getString("Statement.43"), "S1009", getExceptionInterceptor());  if (StringUtils.startsWithIgnoreCaseAndWs(sql, "select")) throw SQLError.createSQLException(Messages.getString("Statement.46"), "01S03", getExceptionInterceptor());  if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose()) this.results.realClose(false);  CancelTask timeoutTask = null; String oldCatalog = null; try { if (locallyScopedConn.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) { timeoutTask = new CancelTask(this); locallyScopedConn.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis); }  if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) { oldCatalog = locallyScopedConn.getCatalog(); locallyScopedConn.setCatalog(this.currentCatalog); }  if (locallyScopedConn.useMaxRows()) executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");  rs = locallyScopedConn.execSQL(this, sql, -1, (Buffer)null, 1003, 1007, false, this.currentCatalog, (Field[])null, isBatch); if (timeoutTask != null) { if (timeoutTask.caughtWhileCancelling != null) throw timeoutTask.caughtWhileCancelling;  timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); timeoutTask = null; }  synchronized (this.cancelTimeoutMutex) { if (this.wasCancelled) { MySQLStatementCancelledException mySQLStatementCancelledException; SQLException cause = null; if (this.wasCancelledByTimeout) { MySQLTimeoutException mySQLTimeoutException = new MySQLTimeoutException(); } else { mySQLStatementCancelledException = new MySQLStatementCancelledException(); }  resetCancelledState(); throw mySQLStatementCancelledException; }  }  } finally { if (timeoutTask != null) { timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); }  if (oldCatalog != null) locallyScopedConn.setCatalog(oldCatalog);  }  }  this.results = rs; rs.setFirstCharOfQuery(firstStatementChar); this.updateCount = rs.getUpdateCount(); int truncatedUpdateCount = 0; if (this.updateCount > 2147483647L) { truncatedUpdateCount = Integer.MAX_VALUE; } else { truncatedUpdateCount = (int)this.updateCount; }  this.lastInsertId = rs.getUpdateID(); return truncatedUpdateCount; } public int executeUpdate(String sql, int returnGeneratedKeys) throws SQLException { if (returnGeneratedKeys == 1) { checkClosed(); MySQLConnection locallyScopedConn = this.connection; synchronized (locallyScopedConn.getMutex()) { boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled(); locallyScopedConn.setReadInfoMsgEnabled(true); try { return executeUpdate(sql, false, true); } finally { locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState); }  }  }  return executeUpdate(sql); } public int executeUpdate(String sql, int[] generatedKeyIndices) throws SQLException { if (generatedKeyIndices != null && generatedKeyIndices.length > 0) { checkClosed(); MySQLConnection locallyScopedConn = this.connection; synchronized (locallyScopedConn.getMutex()) { boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled(); locallyScopedConn.setReadInfoMsgEnabled(true); try { return executeUpdate(sql, false, true); } finally { locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState); }  }  }  return executeUpdate(sql); } public int executeUpdate(String sql, String[] generatedKeyNames) throws SQLException { if (generatedKeyNames != null && generatedKeyNames.length > 0) { checkClosed(); MySQLConnection locallyScopedConn = this.connection; synchronized (locallyScopedConn.getMutex()) { boolean readInfoMsgState = this.connection.isReadInfoMsgEnabled(); locallyScopedConn.setReadInfoMsgEnabled(true); try { return executeUpdate(sql, false, true); } finally { locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState); }  }  }  return executeUpdate(sql); } protected Calendar getCalendarInstanceForSessionOrNew() { if (this.connection != null) return this.connection.getCalendarInstanceForSessionOrNew();  return new GregorianCalendar(); } public Connection getConnection() throws SQLException { return this.connection; } public int getFetchDirection() throws SQLException { return 1000; } public int getFetchSize() throws SQLException { return this.fetchSize; } public synchronized ResultSet getGeneratedKeys() throws SQLException { if (!this.retrieveGeneratedKeys) throw SQLError.createSQLException(Messages.getString("Statement.GeneratedKeysNotRequested"), "S1009", getExceptionInterceptor());  if (this.batchedGeneratedKeys == null) { if (this.lastQueryIsOnDupKeyUpdate) return getGeneratedKeysInternal(1);  return getGeneratedKeysInternal(); }  Field[] fields = new Field[1]; fields[0] = new Field("", "GENERATED_KEY", -5, 17); fields[0].setConnection(this.connection); return ResultSetImpl.getInstance(this.currentCatalog, fields, new RowDataStatic(this.batchedGeneratedKeys), this.connection, this, false); } protected ResultSet getGeneratedKeysInternal() throws SQLException { int numKeys = getUpdateCount(); return getGeneratedKeysInternal(numKeys); } protected synchronized ResultSet getGeneratedKeysInternal(int numKeys) throws SQLException { Field[] fields = new Field[1]; fields[0] = new Field("", "GENERATED_KEY", -5, 17); fields[0].setConnection(this.connection); fields[0].setUseOldNameMetadata(true); ArrayList<ByteArrayRow> rowSet = new ArrayList(); long beginAt = getLastInsertID(); if (beginAt < 0L) fields[0].setUnsigned();  if (this.results != null) { String serverInfo = this.results.getServerInfo(); if (numKeys > 0 && this.results.getFirstCharOfQuery() == 'R' && serverInfo != null && serverInfo.length() > 0) numKeys = getRecordCountFromInfo(serverInfo);  if (beginAt != 0L && numKeys > 0) for (int i = 0; i < numKeys; i++) { byte[][] row = new byte[1][]; if (beginAt > 0L) { row[0] = Long.toString(beginAt).getBytes(); } else { byte[] asBytes = new byte[8]; asBytes[7] = (byte)(int)(beginAt & 0xFFL); asBytes[6] = (byte)(int)(beginAt >>> 8L); asBytes[5] = (byte)(int)(beginAt >>> 16L); asBytes[4] = (byte)(int)(beginAt >>> 24L); asBytes[3] = (byte)(int)(beginAt >>> 32L); asBytes[2] = (byte)(int)(beginAt >>> 40L); asBytes[1] = (byte)(int)(beginAt >>> 48L); asBytes[0] = (byte)(int)(beginAt >>> 56L); BigInteger val = new BigInteger(1, asBytes); row[0] = val.toString().getBytes(); }  rowSet.add(new ByteArrayRow(row, getExceptionInterceptor())); beginAt += this.connection.getAutoIncrementIncrement(); }   }  ResultSetImpl gkRs = ResultSetImpl.getInstance(this.currentCatalog, fields, new RowDataStatic(rowSet), this.connection, this, false); this.openResults.add(gkRs); return gkRs; } protected int getId() { return this.statementId; } public boolean isPoolable() throws SQLException { return this.isPoolable; }
/*      */   public long getLastInsertID() { return this.lastInsertId; }
/*      */   public long getLongUpdateCount() { if (this.results == null) return -1L;  if (this.results.reallyResult()) return -1L;  return this.updateCount; }
/*      */   public int getMaxFieldSize() throws SQLException { return this.maxFieldSize; }
/* 2676 */   public int getMaxRows() throws SQLException { if (this.maxRows <= 0) return 0;  return this.maxRows; } public boolean getMoreResults() throws SQLException { return getMoreResults(1); } public synchronized boolean getMoreResults(int current) throws SQLException { if (this.results == null) return false;  boolean streamingMode = createStreamingResultSet(); if (streamingMode && this.results.reallyResult()) while (this.results.next());  ResultSetInternalMethods nextResultSet = this.results.getNextResultSet(); switch (current) { case 1: if (this.results != null) { if (!streamingMode) this.results.close();  this.results.clearNextResult(); }  break;case 3: if (this.results != null) { if (!streamingMode) this.results.close();  this.results.clearNextResult(); }  closeAllOpenResults(); break;case 2: if (!this.connection.getDontTrackOpenResources()) this.openResults.add(this.results);  this.results.clearNextResult(); break;default: throw SQLError.createSQLException(Messages.getString("Statement.19"), "S1009", getExceptionInterceptor()); }  this.results = nextResultSet; if (this.results == null) { this.updateCount = -1L; this.lastInsertId = -1L; } else if (this.results.reallyResult()) { this.updateCount = -1L; this.lastInsertId = -1L; } else { this.updateCount = this.results.getUpdateCount(); this.lastInsertId = this.results.getUpdateID(); }  return (this.results != null && this.results.reallyResult()); } public int getQueryTimeout() throws SQLException { return this.timeoutInMillis / 1000; } private int getRecordCountFromInfo(String serverInfo) { StringBuffer recordsBuf = new StringBuffer(); int recordsCount = 0; int duplicatesCount = 0; char c = Character.MIN_VALUE; int length = serverInfo.length(); int i = 0; for (; i < length; i++) { c = serverInfo.charAt(i); if (Character.isDigit(c)) break;  }  recordsBuf.append(c); i++; for (; i < length; i++) { c = serverInfo.charAt(i); if (!Character.isDigit(c)) break;  recordsBuf.append(c); }  recordsCount = Integer.parseInt(recordsBuf.toString()); StringBuffer duplicatesBuf = new StringBuffer(); for (; i < length; i++) { c = serverInfo.charAt(i); if (Character.isDigit(c)) break;  }  duplicatesBuf.append(c); i++; for (; i < length; i++) { c = serverInfo.charAt(i); if (!Character.isDigit(c)) break;  duplicatesBuf.append(c); }  duplicatesCount = Integer.parseInt(duplicatesBuf.toString()); return recordsCount - duplicatesCount; } public ResultSet getResultSet() throws SQLException { return (this.results != null && this.results.reallyResult()) ? this.results : null; } public int getResultSetConcurrency() throws SQLException { return this.resultSetConcurrency; } public int getResultSetHoldability() throws SQLException { return 1; } protected ResultSetInternalMethods getResultSetInternal() { return this.results; } public int getResultSetType() throws SQLException { return this.resultSetType; } public int getUpdateCount() throws SQLException { if (this.results == null) return -1;  if (this.results.reallyResult()) return -1;  int truncatedUpdateCount = 0; if (this.results.getUpdateCount() > 2147483647L) { truncatedUpdateCount = Integer.MAX_VALUE; } else { truncatedUpdateCount = (int)this.results.getUpdateCount(); }  return truncatedUpdateCount; } public SQLWarning getWarnings() throws SQLException { checkClosed(); if (this.connection != null && !this.connection.isClosed() && this.connection.versionMeetsMinimum(4, 1, 0)) { SQLWarning pendingWarningsFromServer = SQLError.convertShowWarningsToSQLWarnings(this.connection); if (this.warningChain != null) { this.warningChain.setNextWarning(pendingWarningsFromServer); } else { this.warningChain = pendingWarningsFromServer; }  return this.warningChain; }  return this.warningChain; } protected synchronized void realClose(boolean calledExplicitly, boolean closeOpenResults) throws SQLException { if (this.isClosed) return;  if (this.useUsageAdvisor && !calledExplicitly) { String message = Messages.getString("Statement.63") + Messages.getString("Statement.64"); this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.currentCatalog, this.connectionId, getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message)); }  if (closeOpenResults) closeOpenResults = !this.holdResultsOpenOverClose;  if (closeOpenResults) { if (this.results != null) try { this.results.close(); } catch (Exception ex) {}  closeAllOpenResults(); }  if (this.connection != null) { if (this.maxRowsChanged) this.connection.unsetMaxRows(this);  if (!this.connection.getDontTrackOpenResources()) this.connection.unregisterStatement(this);  }  this.isClosed = true; this.results = null; this.connection = null; this.warningChain = null; this.openResults = null; this.batchedGeneratedKeys = null; this.localInfileInputStream = null; this.pingTarget = null; } public void setCursorName(String name) throws SQLException {} public void setEscapeProcessing(boolean enable) throws SQLException { this.doEscapeProcessing = enable; } public void setFetchDirection(int direction) throws SQLException { switch (direction) { case 1000: case 1001: case 1002: return; }  throw SQLError.createSQLException(Messages.getString("Statement.5"), "S1009", getExceptionInterceptor()); } public void setFetchSize(int rows) throws SQLException { if ((rows < 0 && rows != Integer.MIN_VALUE) || (this.maxRows != 0 && this.maxRows != -1 && rows > getMaxRows())) throw SQLError.createSQLException(Messages.getString("Statement.7"), "S1009", getExceptionInterceptor());  this.fetchSize = rows; } public void setHoldResultsOpenOverClose(boolean holdResultsOpenOverClose) { this.holdResultsOpenOverClose = holdResultsOpenOverClose; } public void setMaxFieldSize(int max) throws SQLException { if (max < 0) throw SQLError.createSQLException(Messages.getString("Statement.11"), "S1009", getExceptionInterceptor());  int maxBuf = (this.connection != null) ? this.connection.getMaxAllowedPacket() : MysqlIO.getMaxBuf(); if (max > maxBuf) throw SQLError.createSQLException(Messages.getString("Statement.13", new Object[] { Constants.longValueOf(maxBuf) }), "S1009", getExceptionInterceptor());  this.maxFieldSize = max; } public void setMaxRows(int max) throws SQLException { if (max > 50000000 || max < 0) throw SQLError.createSQLException(Messages.getString("Statement.15") + max + " > " + 50000000 + ".", "S1009", getExceptionInterceptor());  if (max == 0) max = -1;  this.maxRows = max; this.maxRowsChanged = true; if (this.maxRows == -1) { this.connection.unsetMaxRows(this); this.maxRowsChanged = false; } else { this.connection.maxRowsChanged(this); }  } public void setQueryTimeout(int seconds) throws SQLException { if (seconds < 0) throw SQLError.createSQLException(Messages.getString("Statement.21"), "S1009", getExceptionInterceptor());  this.timeoutInMillis = seconds * 1000; } void setResultSetConcurrency(int concurrencyFlag) { this.resultSetConcurrency = concurrencyFlag; } void setResultSetType(int typeFlag) { this.resultSetType = typeFlag; } protected void getBatchedGeneratedKeys(Statement batchedStatement) throws SQLException { if (this.retrieveGeneratedKeys) { ResultSet rs = null; try { rs = batchedStatement.getGeneratedKeys(); while (rs.next()) { this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][] { rs.getBytes(1) }, getExceptionInterceptor())); }  } finally { if (rs != null) rs.close();  }  }  } protected void getBatchedGeneratedKeys(int maxKeys) throws SQLException { if (this.retrieveGeneratedKeys) { ResultSet rs = null; try { if (maxKeys == 0) { rs = getGeneratedKeysInternal(); } else { rs = getGeneratedKeysInternal(maxKeys); }  while (rs.next()) { this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][] { rs.getBytes(1) }, getExceptionInterceptor())); }  } finally { if (rs != null) rs.close();  }  }  } private boolean useServerFetch() throws SQLException { return (this.connection.isCursorFetchEnabled() && this.fetchSize > 0 && this.resultSetConcurrency == 1007 && this.resultSetType == 1003); } public synchronized boolean isClosed() throws SQLException { return this.isClosed; } public void setPoolable(boolean poolable) throws SQLException { this.isPoolable = poolable; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWrapperFor(Class iface) throws SQLException {
/* 2695 */     checkClosed();
/*      */ 
/*      */ 
/*      */     
/* 2699 */     return iface.isInstance(this);
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
/*      */   public Object unwrap(Class iface) throws SQLException {
/*      */     try {
/* 2720 */       return Util.cast(iface, this);
/* 2721 */     } catch (ClassCastException cce) {
/* 2722 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", getExceptionInterceptor());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected int findStartOfStatement(String sql) {
/* 2728 */     int statementStartPos = 0;
/*      */     
/* 2730 */     if (StringUtils.startsWithIgnoreCaseAndWs(sql, "/*")) {
/* 2731 */       statementStartPos = sql.indexOf("*/");
/*      */       
/* 2733 */       if (statementStartPos == -1) {
/* 2734 */         statementStartPos = 0;
/*      */       } else {
/* 2736 */         statementStartPos += 2;
/*      */       } 
/* 2738 */     } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "--") || StringUtils.startsWithIgnoreCaseAndWs(sql, "#")) {
/*      */       
/* 2740 */       statementStartPos = sql.indexOf('\n');
/*      */       
/* 2742 */       if (statementStartPos == -1) {
/* 2743 */         statementStartPos = sql.indexOf('\r');
/*      */         
/* 2745 */         if (statementStartPos == -1) {
/* 2746 */           statementStartPos = 0;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2751 */     return statementStartPos;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized InputStream getLocalInfileInputStream() {
/* 2757 */     return this.localInfileInputStream;
/*      */   }
/*      */   
/*      */   public synchronized void setLocalInfileInputStream(InputStream stream) {
/* 2761 */     this.localInfileInputStream = stream;
/*      */   }
/*      */   
/*      */   public synchronized void setPingTarget(PingTarget pingTarget) {
/* 2765 */     this.pingTarget = pingTarget;
/*      */   }
/*      */   
/*      */   public ExceptionInterceptor getExceptionInterceptor() {
/* 2769 */     return this.exceptionInterceptor;
/*      */   }
/*      */   
/*      */   protected boolean containsOnDuplicateKeyInString(String sql) {
/* 2773 */     return (getOnDuplicateKeyLocation(sql) != -1);
/*      */   }
/*      */   
/*      */   protected int getOnDuplicateKeyLocation(String sql) {
/* 2777 */     return StringUtils.indexOfIgnoreCaseRespectMarker(0, sql, " ON DUPLICATE KEY UPDATE ", "\"'`", "\"'`", !this.connection.isNoBackslashEscapesSet());
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\StatementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */