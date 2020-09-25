/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.log.Log;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Savepoint;
/*      */ import java.sql.Statement;
/*      */ import java.util.Calendar;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.TimeZone;
/*      */ import java.util.Timer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LoadBalancedMySQLConnection
/*      */   implements MySQLConnection
/*      */ {
/*      */   protected LoadBalancingConnectionProxy proxy;
/*      */   
/*      */   public LoadBalancingConnectionProxy getProxy() {
/*   46 */     return this.proxy;
/*      */   }
/*      */   
/*      */   protected MySQLConnection getActiveMySQLConnection() {
/*   50 */     return this.proxy.currentConn;
/*      */   }
/*      */   
/*      */   public LoadBalancedMySQLConnection(LoadBalancingConnectionProxy proxy) {
/*   54 */     this.proxy = proxy;
/*      */   }
/*      */   
/*      */   public void abortInternal() throws SQLException {
/*   58 */     getActiveMySQLConnection().abortInternal();
/*      */   }
/*      */ 
/*      */   
/*      */   public void changeUser(String userName, String newPassword) throws SQLException {
/*   63 */     getActiveMySQLConnection().changeUser(userName, newPassword);
/*      */   }
/*      */   
/*      */   public void checkClosed() throws SQLException {
/*   67 */     getActiveMySQLConnection().checkClosed();
/*      */   }
/*      */   
/*      */   public void clearHasTriedMaster() {
/*   71 */     getActiveMySQLConnection().clearHasTriedMaster();
/*      */   }
/*      */   
/*      */   public void clearWarnings() throws SQLException {
/*   75 */     getActiveMySQLConnection().clearWarnings();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/*   81 */     return getActiveMySQLConnection().clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*   87 */     return getActiveMySQLConnection().clientPrepareStatement(sql, resultSetType, resultSetConcurrency);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/*   93 */     return getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/*   99 */     return getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyIndexes);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/*  105 */     return getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyColNames);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql) throws SQLException {
/*  111 */     return getActiveMySQLConnection().clientPrepareStatement(sql);
/*      */   }
/*      */   
/*      */   public synchronized void close() throws SQLException {
/*  115 */     getActiveMySQLConnection().close();
/*      */   }
/*      */   
/*      */   public void commit() throws SQLException {
/*  119 */     getActiveMySQLConnection().commit();
/*      */   }
/*      */   
/*      */   public void createNewIO(boolean isForReconnect) throws SQLException {
/*  123 */     getActiveMySQLConnection().createNewIO(isForReconnect);
/*      */   }
/*      */   
/*      */   public Statement createStatement() throws SQLException {
/*  127 */     return getActiveMySQLConnection().createStatement();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/*  133 */     return getActiveMySQLConnection().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
/*  139 */     return getActiveMySQLConnection().createStatement(resultSetType, resultSetConcurrency);
/*      */   }
/*      */ 
/*      */   
/*      */   public void dumpTestcaseQuery(String query) {
/*  144 */     getActiveMySQLConnection().dumpTestcaseQuery(query);
/*      */   }
/*      */   
/*      */   public Connection duplicate() throws SQLException {
/*  148 */     return getActiveMySQLConnection().duplicate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSetInternalMethods execSQL(StatementImpl callingStatement, String sql, int maxRows, Buffer packet, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Field[] cachedMetadata, boolean isBatch) throws SQLException {
/*  155 */     return getActiveMySQLConnection().execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata, isBatch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSetInternalMethods execSQL(StatementImpl callingStatement, String sql, int maxRows, Buffer packet, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Field[] cachedMetadata) throws SQLException {
/*  164 */     return getActiveMySQLConnection().execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String extractSqlFromPacket(String possibleSqlQuery, Buffer queryPacket, int endOfQueryPacketPosition) throws SQLException {
/*  172 */     return getActiveMySQLConnection().extractSqlFromPacket(possibleSqlQuery, queryPacket, endOfQueryPacketPosition);
/*      */   }
/*      */ 
/*      */   
/*      */   public String exposeAsXml() throws SQLException {
/*  177 */     return getActiveMySQLConnection().exposeAsXml();
/*      */   }
/*      */   
/*      */   public boolean getAllowLoadLocalInfile() {
/*  181 */     return getActiveMySQLConnection().getAllowLoadLocalInfile();
/*      */   }
/*      */   
/*      */   public boolean getAllowMultiQueries() {
/*  185 */     return getActiveMySQLConnection().getAllowMultiQueries();
/*      */   }
/*      */   
/*      */   public boolean getAllowNanAndInf() {
/*  189 */     return getActiveMySQLConnection().getAllowNanAndInf();
/*      */   }
/*      */   
/*      */   public boolean getAllowUrlInLocalInfile() {
/*  193 */     return getActiveMySQLConnection().getAllowUrlInLocalInfile();
/*      */   }
/*      */   
/*      */   public boolean getAlwaysSendSetIsolation() {
/*  197 */     return getActiveMySQLConnection().getAlwaysSendSetIsolation();
/*      */   }
/*      */   
/*      */   public boolean getAutoClosePStmtStreams() {
/*  201 */     return getActiveMySQLConnection().getAutoClosePStmtStreams();
/*      */   }
/*      */   
/*      */   public boolean getAutoDeserialize() {
/*  205 */     return getActiveMySQLConnection().getAutoDeserialize();
/*      */   }
/*      */   
/*      */   public boolean getAutoGenerateTestcaseScript() {
/*  209 */     return getActiveMySQLConnection().getAutoGenerateTestcaseScript();
/*      */   }
/*      */   
/*      */   public boolean getAutoReconnectForPools() {
/*  213 */     return getActiveMySQLConnection().getAutoReconnectForPools();
/*      */   }
/*      */   
/*      */   public boolean getAutoSlowLog() {
/*  217 */     return getActiveMySQLConnection().getAutoSlowLog();
/*      */   }
/*      */   
/*      */   public int getBlobSendChunkSize() {
/*  221 */     return getActiveMySQLConnection().getBlobSendChunkSize();
/*      */   }
/*      */   
/*      */   public boolean getBlobsAreStrings() {
/*  225 */     return getActiveMySQLConnection().getBlobsAreStrings();
/*      */   }
/*      */   
/*      */   public boolean getCacheCallableStatements() {
/*  229 */     return getActiveMySQLConnection().getCacheCallableStatements();
/*      */   }
/*      */   
/*      */   public boolean getCacheCallableStmts() {
/*  233 */     return getActiveMySQLConnection().getCacheCallableStmts();
/*      */   }
/*      */   
/*      */   public boolean getCachePrepStmts() {
/*  237 */     return getActiveMySQLConnection().getCachePrepStmts();
/*      */   }
/*      */   
/*      */   public boolean getCachePreparedStatements() {
/*  241 */     return getActiveMySQLConnection().getCachePreparedStatements();
/*      */   }
/*      */   
/*      */   public boolean getCacheResultSetMetadata() {
/*  245 */     return getActiveMySQLConnection().getCacheResultSetMetadata();
/*      */   }
/*      */   
/*      */   public boolean getCacheServerConfiguration() {
/*  249 */     return getActiveMySQLConnection().getCacheServerConfiguration();
/*      */   }
/*      */   
/*      */   public int getCallableStatementCacheSize() {
/*  253 */     return getActiveMySQLConnection().getCallableStatementCacheSize();
/*      */   }
/*      */   
/*      */   public int getCallableStmtCacheSize() {
/*  257 */     return getActiveMySQLConnection().getCallableStmtCacheSize();
/*      */   }
/*      */   
/*      */   public boolean getCapitalizeTypeNames() {
/*  261 */     return getActiveMySQLConnection().getCapitalizeTypeNames();
/*      */   }
/*      */   
/*      */   public String getCharacterSetResults() {
/*  265 */     return getActiveMySQLConnection().getCharacterSetResults();
/*      */   }
/*      */   
/*      */   public String getClientCertificateKeyStorePassword() {
/*  269 */     return getActiveMySQLConnection().getClientCertificateKeyStorePassword();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getClientCertificateKeyStoreType() {
/*  274 */     return getActiveMySQLConnection().getClientCertificateKeyStoreType();
/*      */   }
/*      */   
/*      */   public String getClientCertificateKeyStoreUrl() {
/*  278 */     return getActiveMySQLConnection().getClientCertificateKeyStoreUrl();
/*      */   }
/*      */   
/*      */   public String getClientInfoProvider() {
/*  282 */     return getActiveMySQLConnection().getClientInfoProvider();
/*      */   }
/*      */   
/*      */   public String getClobCharacterEncoding() {
/*  286 */     return getActiveMySQLConnection().getClobCharacterEncoding();
/*      */   }
/*      */   
/*      */   public boolean getClobberStreamingResults() {
/*  290 */     return getActiveMySQLConnection().getClobberStreamingResults();
/*      */   }
/*      */   
/*      */   public boolean getCompensateOnDuplicateKeyUpdateCounts() {
/*  294 */     return getActiveMySQLConnection().getCompensateOnDuplicateKeyUpdateCounts();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getConnectTimeout() {
/*  299 */     return getActiveMySQLConnection().getConnectTimeout();
/*      */   }
/*      */   
/*      */   public String getConnectionCollation() {
/*  303 */     return getActiveMySQLConnection().getConnectionCollation();
/*      */   }
/*      */   
/*      */   public String getConnectionLifecycleInterceptors() {
/*  307 */     return getActiveMySQLConnection().getConnectionLifecycleInterceptors();
/*      */   }
/*      */   
/*      */   public boolean getContinueBatchOnError() {
/*  311 */     return getActiveMySQLConnection().getContinueBatchOnError();
/*      */   }
/*      */   
/*      */   public boolean getCreateDatabaseIfNotExist() {
/*  315 */     return getActiveMySQLConnection().getCreateDatabaseIfNotExist();
/*      */   }
/*      */   
/*      */   public int getDefaultFetchSize() {
/*  319 */     return getActiveMySQLConnection().getDefaultFetchSize();
/*      */   }
/*      */   
/*      */   public boolean getDontTrackOpenResources() {
/*  323 */     return getActiveMySQLConnection().getDontTrackOpenResources();
/*      */   }
/*      */   
/*      */   public boolean getDumpMetadataOnColumnNotFound() {
/*  327 */     return getActiveMySQLConnection().getDumpMetadataOnColumnNotFound();
/*      */   }
/*      */   
/*      */   public boolean getDumpQueriesOnException() {
/*  331 */     return getActiveMySQLConnection().getDumpQueriesOnException();
/*      */   }
/*      */   
/*      */   public boolean getDynamicCalendars() {
/*  335 */     return getActiveMySQLConnection().getDynamicCalendars();
/*      */   }
/*      */   
/*      */   public boolean getElideSetAutoCommits() {
/*  339 */     return getActiveMySQLConnection().getElideSetAutoCommits();
/*      */   }
/*      */   
/*      */   public boolean getEmptyStringsConvertToZero() {
/*  343 */     return getActiveMySQLConnection().getEmptyStringsConvertToZero();
/*      */   }
/*      */   
/*      */   public boolean getEmulateLocators() {
/*  347 */     return getActiveMySQLConnection().getEmulateLocators();
/*      */   }
/*      */   
/*      */   public boolean getEmulateUnsupportedPstmts() {
/*  351 */     return getActiveMySQLConnection().getEmulateUnsupportedPstmts();
/*      */   }
/*      */   
/*      */   public boolean getEnablePacketDebug() {
/*  355 */     return getActiveMySQLConnection().getEnablePacketDebug();
/*      */   }
/*      */   
/*      */   public boolean getEnableQueryTimeouts() {
/*  359 */     return getActiveMySQLConnection().getEnableQueryTimeouts();
/*      */   }
/*      */   
/*      */   public String getEncoding() {
/*  363 */     return getActiveMySQLConnection().getEncoding();
/*      */   }
/*      */   
/*      */   public String getExceptionInterceptors() {
/*  367 */     return getActiveMySQLConnection().getExceptionInterceptors();
/*      */   }
/*      */   
/*      */   public boolean getExplainSlowQueries() {
/*  371 */     return getActiveMySQLConnection().getExplainSlowQueries();
/*      */   }
/*      */   
/*      */   public boolean getFailOverReadOnly() {
/*  375 */     return getActiveMySQLConnection().getFailOverReadOnly();
/*      */   }
/*      */   
/*      */   public boolean getFunctionsNeverReturnBlobs() {
/*  379 */     return getActiveMySQLConnection().getFunctionsNeverReturnBlobs();
/*      */   }
/*      */   
/*      */   public boolean getGatherPerfMetrics() {
/*  383 */     return getActiveMySQLConnection().getGatherPerfMetrics();
/*      */   }
/*      */   
/*      */   public boolean getGatherPerformanceMetrics() {
/*  387 */     return getActiveMySQLConnection().getGatherPerformanceMetrics();
/*      */   }
/*      */   
/*      */   public boolean getGenerateSimpleParameterMetadata() {
/*  391 */     return getActiveMySQLConnection().getGenerateSimpleParameterMetadata();
/*      */   }
/*      */   
/*      */   public boolean getIgnoreNonTxTables() {
/*  395 */     return getActiveMySQLConnection().getIgnoreNonTxTables();
/*      */   }
/*      */   
/*      */   public boolean getIncludeInnodbStatusInDeadlockExceptions() {
/*  399 */     return getActiveMySQLConnection().getIncludeInnodbStatusInDeadlockExceptions();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInitialTimeout() {
/*  404 */     return getActiveMySQLConnection().getInitialTimeout();
/*      */   }
/*      */   
/*      */   public boolean getInteractiveClient() {
/*  408 */     return getActiveMySQLConnection().getInteractiveClient();
/*      */   }
/*      */   
/*      */   public boolean getIsInteractiveClient() {
/*  412 */     return getActiveMySQLConnection().getIsInteractiveClient();
/*      */   }
/*      */   
/*      */   public boolean getJdbcCompliantTruncation() {
/*  416 */     return getActiveMySQLConnection().getJdbcCompliantTruncation();
/*      */   }
/*      */   
/*      */   public boolean getJdbcCompliantTruncationForReads() {
/*  420 */     return getActiveMySQLConnection().getJdbcCompliantTruncationForReads();
/*      */   }
/*      */   
/*      */   public String getLargeRowSizeThreshold() {
/*  424 */     return getActiveMySQLConnection().getLargeRowSizeThreshold();
/*      */   }
/*      */   
/*      */   public int getLoadBalanceBlacklistTimeout() {
/*  428 */     return getActiveMySQLConnection().getLoadBalanceBlacklistTimeout();
/*      */   }
/*      */   
/*      */   public int getLoadBalancePingTimeout() {
/*  432 */     return getActiveMySQLConnection().getLoadBalancePingTimeout();
/*      */   }
/*      */   
/*      */   public String getLoadBalanceStrategy() {
/*  436 */     return getActiveMySQLConnection().getLoadBalanceStrategy();
/*      */   }
/*      */   
/*      */   public boolean getLoadBalanceValidateConnectionOnSwapServer() {
/*  440 */     return getActiveMySQLConnection().getLoadBalanceValidateConnectionOnSwapServer();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLocalSocketAddress() {
/*  445 */     return getActiveMySQLConnection().getLocalSocketAddress();
/*      */   }
/*      */   
/*      */   public int getLocatorFetchBufferSize() {
/*  449 */     return getActiveMySQLConnection().getLocatorFetchBufferSize();
/*      */   }
/*      */   
/*      */   public boolean getLogSlowQueries() {
/*  453 */     return getActiveMySQLConnection().getLogSlowQueries();
/*      */   }
/*      */   
/*      */   public boolean getLogXaCommands() {
/*  457 */     return getActiveMySQLConnection().getLogXaCommands();
/*      */   }
/*      */   
/*      */   public String getLogger() {
/*  461 */     return getActiveMySQLConnection().getLogger();
/*      */   }
/*      */   
/*      */   public String getLoggerClassName() {
/*  465 */     return getActiveMySQLConnection().getLoggerClassName();
/*      */   }
/*      */   
/*      */   public boolean getMaintainTimeStats() {
/*  469 */     return getActiveMySQLConnection().getMaintainTimeStats();
/*      */   }
/*      */   
/*      */   public int getMaxAllowedPacket() {
/*  473 */     return getActiveMySQLConnection().getMaxAllowedPacket();
/*      */   }
/*      */   
/*      */   public int getMaxQuerySizeToLog() {
/*  477 */     return getActiveMySQLConnection().getMaxQuerySizeToLog();
/*      */   }
/*      */   
/*      */   public int getMaxReconnects() {
/*  481 */     return getActiveMySQLConnection().getMaxReconnects();
/*      */   }
/*      */   
/*      */   public int getMaxRows() {
/*  485 */     return getActiveMySQLConnection().getMaxRows();
/*      */   }
/*      */   
/*      */   public int getMetadataCacheSize() {
/*  489 */     return getActiveMySQLConnection().getMetadataCacheSize();
/*      */   }
/*      */   
/*      */   public int getNetTimeoutForStreamingResults() {
/*  493 */     return getActiveMySQLConnection().getNetTimeoutForStreamingResults();
/*      */   }
/*      */   
/*      */   public boolean getNoAccessToProcedureBodies() {
/*  497 */     return getActiveMySQLConnection().getNoAccessToProcedureBodies();
/*      */   }
/*      */   
/*      */   public boolean getNoDatetimeStringSync() {
/*  501 */     return getActiveMySQLConnection().getNoDatetimeStringSync();
/*      */   }
/*      */   
/*      */   public boolean getNoTimezoneConversionForTimeType() {
/*  505 */     return getActiveMySQLConnection().getNoTimezoneConversionForTimeType();
/*      */   }
/*      */   
/*      */   public boolean getNullCatalogMeansCurrent() {
/*  509 */     return getActiveMySQLConnection().getNullCatalogMeansCurrent();
/*      */   }
/*      */   
/*      */   public boolean getNullNamePatternMatchesAll() {
/*  513 */     return getActiveMySQLConnection().getNullNamePatternMatchesAll();
/*      */   }
/*      */   
/*      */   public boolean getOverrideSupportsIntegrityEnhancementFacility() {
/*  517 */     return getActiveMySQLConnection().getOverrideSupportsIntegrityEnhancementFacility();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getPacketDebugBufferSize() {
/*  522 */     return getActiveMySQLConnection().getPacketDebugBufferSize();
/*      */   }
/*      */   
/*      */   public boolean getPadCharsWithSpace() {
/*  526 */     return getActiveMySQLConnection().getPadCharsWithSpace();
/*      */   }
/*      */   
/*      */   public boolean getParanoid() {
/*  530 */     return getActiveMySQLConnection().getParanoid();
/*      */   }
/*      */   
/*      */   public String getPasswordCharacterEncoding() {
/*  534 */     return getActiveMySQLConnection().getPasswordCharacterEncoding();
/*      */   }
/*      */   
/*      */   public boolean getPedantic() {
/*  538 */     return getActiveMySQLConnection().getPedantic();
/*      */   }
/*      */   
/*      */   public boolean getPinGlobalTxToPhysicalConnection() {
/*  542 */     return getActiveMySQLConnection().getPinGlobalTxToPhysicalConnection();
/*      */   }
/*      */   
/*      */   public boolean getPopulateInsertRowWithDefaultValues() {
/*  546 */     return getActiveMySQLConnection().getPopulateInsertRowWithDefaultValues();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getPrepStmtCacheSize() {
/*  551 */     return getActiveMySQLConnection().getPrepStmtCacheSize();
/*      */   }
/*      */   
/*      */   public int getPrepStmtCacheSqlLimit() {
/*  555 */     return getActiveMySQLConnection().getPrepStmtCacheSqlLimit();
/*      */   }
/*      */   
/*      */   public int getPreparedStatementCacheSize() {
/*  559 */     return getActiveMySQLConnection().getPreparedStatementCacheSize();
/*      */   }
/*      */   
/*      */   public int getPreparedStatementCacheSqlLimit() {
/*  563 */     return getActiveMySQLConnection().getPreparedStatementCacheSqlLimit();
/*      */   }
/*      */   
/*      */   public boolean getProcessEscapeCodesForPrepStmts() {
/*  567 */     return getActiveMySQLConnection().getProcessEscapeCodesForPrepStmts();
/*      */   }
/*      */   
/*      */   public boolean getProfileSQL() {
/*  571 */     return getActiveMySQLConnection().getProfileSQL();
/*      */   }
/*      */   
/*      */   public boolean getProfileSql() {
/*  575 */     return getActiveMySQLConnection().getProfileSql();
/*      */   }
/*      */   
/*      */   public String getProfilerEventHandler() {
/*  579 */     return getActiveMySQLConnection().getProfilerEventHandler();
/*      */   }
/*      */   
/*      */   public String getPropertiesTransform() {
/*  583 */     return getActiveMySQLConnection().getPropertiesTransform();
/*      */   }
/*      */   
/*      */   public int getQueriesBeforeRetryMaster() {
/*  587 */     return getActiveMySQLConnection().getQueriesBeforeRetryMaster();
/*      */   }
/*      */   
/*      */   public boolean getQueryTimeoutKillsConnection() {
/*  591 */     return getActiveMySQLConnection().getQueryTimeoutKillsConnection();
/*      */   }
/*      */   
/*      */   public boolean getReconnectAtTxEnd() {
/*  595 */     return getActiveMySQLConnection().getReconnectAtTxEnd();
/*      */   }
/*      */   
/*      */   public boolean getRelaxAutoCommit() {
/*  599 */     return getActiveMySQLConnection().getRelaxAutoCommit();
/*      */   }
/*      */   
/*      */   public int getReportMetricsIntervalMillis() {
/*  603 */     return getActiveMySQLConnection().getReportMetricsIntervalMillis();
/*      */   }
/*      */   
/*      */   public boolean getRequireSSL() {
/*  607 */     return getActiveMySQLConnection().getRequireSSL();
/*      */   }
/*      */   
/*      */   public String getResourceId() {
/*  611 */     return getActiveMySQLConnection().getResourceId();
/*      */   }
/*      */   
/*      */   public int getResultSetSizeThreshold() {
/*  615 */     return getActiveMySQLConnection().getResultSetSizeThreshold();
/*      */   }
/*      */   
/*      */   public boolean getRetainStatementAfterResultSetClose() {
/*  619 */     return getActiveMySQLConnection().getRetainStatementAfterResultSetClose();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRetriesAllDown() {
/*  624 */     return getActiveMySQLConnection().getRetriesAllDown();
/*      */   }
/*      */   
/*      */   public boolean getRewriteBatchedStatements() {
/*  628 */     return getActiveMySQLConnection().getRewriteBatchedStatements();
/*      */   }
/*      */   
/*      */   public boolean getRollbackOnPooledClose() {
/*  632 */     return getActiveMySQLConnection().getRollbackOnPooledClose();
/*      */   }
/*      */   
/*      */   public boolean getRoundRobinLoadBalance() {
/*  636 */     return getActiveMySQLConnection().getRoundRobinLoadBalance();
/*      */   }
/*      */   
/*      */   public boolean getRunningCTS13() {
/*  640 */     return getActiveMySQLConnection().getRunningCTS13();
/*      */   }
/*      */   
/*      */   public int getSecondsBeforeRetryMaster() {
/*  644 */     return getActiveMySQLConnection().getSecondsBeforeRetryMaster();
/*      */   }
/*      */   
/*      */   public int getSelfDestructOnPingMaxOperations() {
/*  648 */     return getActiveMySQLConnection().getSelfDestructOnPingMaxOperations();
/*      */   }
/*      */   
/*      */   public int getSelfDestructOnPingSecondsLifetime() {
/*  652 */     return getActiveMySQLConnection().getSelfDestructOnPingSecondsLifetime();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerTimezone() {
/*  657 */     return getActiveMySQLConnection().getServerTimezone();
/*      */   }
/*      */   
/*      */   public String getSessionVariables() {
/*  661 */     return getActiveMySQLConnection().getSessionVariables();
/*      */   }
/*      */   
/*      */   public int getSlowQueryThresholdMillis() {
/*  665 */     return getActiveMySQLConnection().getSlowQueryThresholdMillis();
/*      */   }
/*      */   
/*      */   public long getSlowQueryThresholdNanos() {
/*  669 */     return getActiveMySQLConnection().getSlowQueryThresholdNanos();
/*      */   }
/*      */   
/*      */   public String getSocketFactory() {
/*  673 */     return getActiveMySQLConnection().getSocketFactory();
/*      */   }
/*      */   
/*      */   public String getSocketFactoryClassName() {
/*  677 */     return getActiveMySQLConnection().getSocketFactoryClassName();
/*      */   }
/*      */   
/*      */   public int getSocketTimeout() {
/*  681 */     return getActiveMySQLConnection().getSocketTimeout();
/*      */   }
/*      */   
/*      */   public String getStatementInterceptors() {
/*  685 */     return getActiveMySQLConnection().getStatementInterceptors();
/*      */   }
/*      */   
/*      */   public boolean getStrictFloatingPoint() {
/*  689 */     return getActiveMySQLConnection().getStrictFloatingPoint();
/*      */   }
/*      */   
/*      */   public boolean getStrictUpdates() {
/*  693 */     return getActiveMySQLConnection().getStrictUpdates();
/*      */   }
/*      */   
/*      */   public boolean getTcpKeepAlive() {
/*  697 */     return getActiveMySQLConnection().getTcpKeepAlive();
/*      */   }
/*      */   
/*      */   public boolean getTcpNoDelay() {
/*  701 */     return getActiveMySQLConnection().getTcpNoDelay();
/*      */   }
/*      */   
/*      */   public int getTcpRcvBuf() {
/*  705 */     return getActiveMySQLConnection().getTcpRcvBuf();
/*      */   }
/*      */   
/*      */   public int getTcpSndBuf() {
/*  709 */     return getActiveMySQLConnection().getTcpSndBuf();
/*      */   }
/*      */   
/*      */   public int getTcpTrafficClass() {
/*  713 */     return getActiveMySQLConnection().getTcpTrafficClass();
/*      */   }
/*      */   
/*      */   public boolean getTinyInt1isBit() {
/*  717 */     return getActiveMySQLConnection().getTinyInt1isBit();
/*      */   }
/*      */   
/*      */   public boolean getTraceProtocol() {
/*  721 */     return getActiveMySQLConnection().getTraceProtocol();
/*      */   }
/*      */   
/*      */   public boolean getTransformedBitIsBoolean() {
/*  725 */     return getActiveMySQLConnection().getTransformedBitIsBoolean();
/*      */   }
/*      */   
/*      */   public boolean getTreatUtilDateAsTimestamp() {
/*  729 */     return getActiveMySQLConnection().getTreatUtilDateAsTimestamp();
/*      */   }
/*      */   
/*      */   public String getTrustCertificateKeyStorePassword() {
/*  733 */     return getActiveMySQLConnection().getTrustCertificateKeyStorePassword();
/*      */   }
/*      */   
/*      */   public String getTrustCertificateKeyStoreType() {
/*  737 */     return getActiveMySQLConnection().getTrustCertificateKeyStoreType();
/*      */   }
/*      */   
/*      */   public String getTrustCertificateKeyStoreUrl() {
/*  741 */     return getActiveMySQLConnection().getTrustCertificateKeyStoreUrl();
/*      */   }
/*      */   
/*      */   public boolean getUltraDevHack() {
/*  745 */     return getActiveMySQLConnection().getUltraDevHack();
/*      */   }
/*      */   
/*      */   public boolean getUseAffectedRows() {
/*  749 */     return getActiveMySQLConnection().getUseAffectedRows();
/*      */   }
/*      */   
/*      */   public boolean getUseBlobToStoreUTF8OutsideBMP() {
/*  753 */     return getActiveMySQLConnection().getUseBlobToStoreUTF8OutsideBMP();
/*      */   }
/*      */   
/*      */   public boolean getUseColumnNamesInFindColumn() {
/*  757 */     return getActiveMySQLConnection().getUseColumnNamesInFindColumn();
/*      */   }
/*      */   
/*      */   public boolean getUseCompression() {
/*  761 */     return getActiveMySQLConnection().getUseCompression();
/*      */   }
/*      */   
/*      */   public String getUseConfigs() {
/*  765 */     return getActiveMySQLConnection().getUseConfigs();
/*      */   }
/*      */   
/*      */   public boolean getUseCursorFetch() {
/*  769 */     return getActiveMySQLConnection().getUseCursorFetch();
/*      */   }
/*      */   
/*      */   public boolean getUseDirectRowUnpack() {
/*  773 */     return getActiveMySQLConnection().getUseDirectRowUnpack();
/*      */   }
/*      */   
/*      */   public boolean getUseDynamicCharsetInfo() {
/*  777 */     return getActiveMySQLConnection().getUseDynamicCharsetInfo();
/*      */   }
/*      */   
/*      */   public boolean getUseFastDateParsing() {
/*  781 */     return getActiveMySQLConnection().getUseFastDateParsing();
/*      */   }
/*      */   
/*      */   public boolean getUseFastIntParsing() {
/*  785 */     return getActiveMySQLConnection().getUseFastIntParsing();
/*      */   }
/*      */   
/*      */   public boolean getUseGmtMillisForDatetimes() {
/*  789 */     return getActiveMySQLConnection().getUseGmtMillisForDatetimes();
/*      */   }
/*      */   
/*      */   public boolean getUseHostsInPrivileges() {
/*  793 */     return getActiveMySQLConnection().getUseHostsInPrivileges();
/*      */   }
/*      */   
/*      */   public boolean getUseInformationSchema() {
/*  797 */     return getActiveMySQLConnection().getUseInformationSchema();
/*      */   }
/*      */   
/*      */   public boolean getUseJDBCCompliantTimezoneShift() {
/*  801 */     return getActiveMySQLConnection().getUseJDBCCompliantTimezoneShift();
/*      */   }
/*      */   
/*      */   public boolean getUseJvmCharsetConverters() {
/*  805 */     return getActiveMySQLConnection().getUseJvmCharsetConverters();
/*      */   }
/*      */   
/*      */   public boolean getUseLegacyDatetimeCode() {
/*  809 */     return getActiveMySQLConnection().getUseLegacyDatetimeCode();
/*      */   }
/*      */   
/*      */   public boolean getUseLocalSessionState() {
/*  813 */     return getActiveMySQLConnection().getUseLocalSessionState();
/*      */   }
/*      */   
/*      */   public boolean getUseLocalTransactionState() {
/*  817 */     return getActiveMySQLConnection().getUseLocalTransactionState();
/*      */   }
/*      */   
/*      */   public boolean getUseNanosForElapsedTime() {
/*  821 */     return getActiveMySQLConnection().getUseNanosForElapsedTime();
/*      */   }
/*      */   
/*      */   public boolean getUseOldAliasMetadataBehavior() {
/*  825 */     return getActiveMySQLConnection().getUseOldAliasMetadataBehavior();
/*      */   }
/*      */   
/*      */   public boolean getUseOldUTF8Behavior() {
/*  829 */     return getActiveMySQLConnection().getUseOldUTF8Behavior();
/*      */   }
/*      */   
/*      */   public boolean getUseOnlyServerErrorMessages() {
/*  833 */     return getActiveMySQLConnection().getUseOnlyServerErrorMessages();
/*      */   }
/*      */   
/*      */   public boolean getUseReadAheadInput() {
/*  837 */     return getActiveMySQLConnection().getUseReadAheadInput();
/*      */   }
/*      */   
/*      */   public boolean getUseSSL() {
/*  841 */     return getActiveMySQLConnection().getUseSSL();
/*      */   }
/*      */   
/*      */   public boolean getUseSSPSCompatibleTimezoneShift() {
/*  845 */     return getActiveMySQLConnection().getUseSSPSCompatibleTimezoneShift();
/*      */   }
/*      */   
/*      */   public boolean getUseServerPrepStmts() {
/*  849 */     return getActiveMySQLConnection().getUseServerPrepStmts();
/*      */   }
/*      */   
/*      */   public boolean getUseServerPreparedStmts() {
/*  853 */     return getActiveMySQLConnection().getUseServerPreparedStmts();
/*      */   }
/*      */   
/*      */   public boolean getUseSqlStateCodes() {
/*  857 */     return getActiveMySQLConnection().getUseSqlStateCodes();
/*      */   }
/*      */   
/*      */   public boolean getUseStreamLengthsInPrepStmts() {
/*  861 */     return getActiveMySQLConnection().getUseStreamLengthsInPrepStmts();
/*      */   }
/*      */   
/*      */   public boolean getUseTimezone() {
/*  865 */     return getActiveMySQLConnection().getUseTimezone();
/*      */   }
/*      */   
/*      */   public boolean getUseUltraDevWorkAround() {
/*  869 */     return getActiveMySQLConnection().getUseUltraDevWorkAround();
/*      */   }
/*      */   
/*      */   public boolean getUseUnbufferedInput() {
/*  873 */     return getActiveMySQLConnection().getUseUnbufferedInput();
/*      */   }
/*      */   
/*      */   public boolean getUseUnicode() {
/*  877 */     return getActiveMySQLConnection().getUseUnicode();
/*      */   }
/*      */   
/*      */   public boolean getUseUsageAdvisor() {
/*  881 */     return getActiveMySQLConnection().getUseUsageAdvisor();
/*      */   }
/*      */   
/*      */   public String getUtf8OutsideBmpExcludedColumnNamePattern() {
/*  885 */     return getActiveMySQLConnection().getUtf8OutsideBmpExcludedColumnNamePattern();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getUtf8OutsideBmpIncludedColumnNamePattern() {
/*  890 */     return getActiveMySQLConnection().getUtf8OutsideBmpIncludedColumnNamePattern();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getVerifyServerCertificate() {
/*  895 */     return getActiveMySQLConnection().getVerifyServerCertificate();
/*      */   }
/*      */   
/*      */   public boolean getYearIsDateType() {
/*  899 */     return getActiveMySQLConnection().getYearIsDateType();
/*      */   }
/*      */   
/*      */   public String getZeroDateTimeBehavior() {
/*  903 */     return getActiveMySQLConnection().getZeroDateTimeBehavior();
/*      */   }
/*      */   
/*      */   public void setAllowLoadLocalInfile(boolean property) {
/*  907 */     getActiveMySQLConnection().setAllowLoadLocalInfile(property);
/*      */   }
/*      */   
/*      */   public void setAllowMultiQueries(boolean property) {
/*  911 */     getActiveMySQLConnection().setAllowMultiQueries(property);
/*      */   }
/*      */   
/*      */   public void setAllowNanAndInf(boolean flag) {
/*  915 */     getActiveMySQLConnection().setAllowNanAndInf(flag);
/*      */   }
/*      */   
/*      */   public void setAllowUrlInLocalInfile(boolean flag) {
/*  919 */     getActiveMySQLConnection().setAllowUrlInLocalInfile(flag);
/*      */   }
/*      */   
/*      */   public void setAlwaysSendSetIsolation(boolean flag) {
/*  923 */     getActiveMySQLConnection().setAlwaysSendSetIsolation(flag);
/*      */   }
/*      */   
/*      */   public void setAutoClosePStmtStreams(boolean flag) {
/*  927 */     getActiveMySQLConnection().setAutoClosePStmtStreams(flag);
/*      */   }
/*      */   
/*      */   public void setAutoDeserialize(boolean flag) {
/*  931 */     getActiveMySQLConnection().setAutoDeserialize(flag);
/*      */   }
/*      */   
/*      */   public void setAutoGenerateTestcaseScript(boolean flag) {
/*  935 */     getActiveMySQLConnection().setAutoGenerateTestcaseScript(flag);
/*      */   }
/*      */   
/*      */   public void setAutoReconnect(boolean flag) {
/*  939 */     getActiveMySQLConnection().setAutoReconnect(flag);
/*      */   }
/*      */   
/*      */   public void setAutoReconnectForConnectionPools(boolean property) {
/*  943 */     getActiveMySQLConnection().setAutoReconnectForConnectionPools(property);
/*      */   }
/*      */   
/*      */   public void setAutoReconnectForPools(boolean flag) {
/*  947 */     getActiveMySQLConnection().setAutoReconnectForPools(flag);
/*      */   }
/*      */   
/*      */   public void setAutoSlowLog(boolean flag) {
/*  951 */     getActiveMySQLConnection().setAutoSlowLog(flag);
/*      */   }
/*      */   
/*      */   public void setBlobSendChunkSize(String value) throws SQLException {
/*  955 */     getActiveMySQLConnection().setBlobSendChunkSize(value);
/*      */   }
/*      */   
/*      */   public void setBlobsAreStrings(boolean flag) {
/*  959 */     getActiveMySQLConnection().setBlobsAreStrings(flag);
/*      */   }
/*      */   
/*      */   public void setCacheCallableStatements(boolean flag) {
/*  963 */     getActiveMySQLConnection().setCacheCallableStatements(flag);
/*      */   }
/*      */   
/*      */   public void setCacheCallableStmts(boolean flag) {
/*  967 */     getActiveMySQLConnection().setCacheCallableStmts(flag);
/*      */   }
/*      */   
/*      */   public void setCachePrepStmts(boolean flag) {
/*  971 */     getActiveMySQLConnection().setCachePrepStmts(flag);
/*      */   }
/*      */   
/*      */   public void setCachePreparedStatements(boolean flag) {
/*  975 */     getActiveMySQLConnection().setCachePreparedStatements(flag);
/*      */   }
/*      */   
/*      */   public void setCacheResultSetMetadata(boolean property) {
/*  979 */     getActiveMySQLConnection().setCacheResultSetMetadata(property);
/*      */   }
/*      */   
/*      */   public void setCacheServerConfiguration(boolean flag) {
/*  983 */     getActiveMySQLConnection().setCacheServerConfiguration(flag);
/*      */   }
/*      */   
/*      */   public void setCallableStatementCacheSize(int size) {
/*  987 */     getActiveMySQLConnection().setCallableStatementCacheSize(size);
/*      */   }
/*      */   
/*      */   public void setCallableStmtCacheSize(int cacheSize) {
/*  991 */     getActiveMySQLConnection().setCallableStmtCacheSize(cacheSize);
/*      */   }
/*      */   
/*      */   public void setCapitalizeDBMDTypes(boolean property) {
/*  995 */     getActiveMySQLConnection().setCapitalizeDBMDTypes(property);
/*      */   }
/*      */   
/*      */   public void setCapitalizeTypeNames(boolean flag) {
/*  999 */     getActiveMySQLConnection().setCapitalizeTypeNames(flag);
/*      */   }
/*      */   
/*      */   public void setCharacterEncoding(String encoding) {
/* 1003 */     getActiveMySQLConnection().setCharacterEncoding(encoding);
/*      */   }
/*      */   
/*      */   public void setCharacterSetResults(String characterSet) {
/* 1007 */     getActiveMySQLConnection().setCharacterSetResults(characterSet);
/*      */   }
/*      */   
/*      */   public void setClientCertificateKeyStorePassword(String value) {
/* 1011 */     getActiveMySQLConnection().setClientCertificateKeyStorePassword(value);
/*      */   }
/*      */   
/*      */   public void setClientCertificateKeyStoreType(String value) {
/* 1015 */     getActiveMySQLConnection().setClientCertificateKeyStoreType(value);
/*      */   }
/*      */   
/*      */   public void setClientCertificateKeyStoreUrl(String value) {
/* 1019 */     getActiveMySQLConnection().setClientCertificateKeyStoreUrl(value);
/*      */   }
/*      */   
/*      */   public void setClientInfoProvider(String classname) {
/* 1023 */     getActiveMySQLConnection().setClientInfoProvider(classname);
/*      */   }
/*      */   
/*      */   public void setClobCharacterEncoding(String encoding) {
/* 1027 */     getActiveMySQLConnection().setClobCharacterEncoding(encoding);
/*      */   }
/*      */   
/*      */   public void setClobberStreamingResults(boolean flag) {
/* 1031 */     getActiveMySQLConnection().setClobberStreamingResults(flag);
/*      */   }
/*      */   
/*      */   public void setCompensateOnDuplicateKeyUpdateCounts(boolean flag) {
/* 1035 */     getActiveMySQLConnection().setCompensateOnDuplicateKeyUpdateCounts(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setConnectTimeout(int timeoutMs) {
/* 1040 */     getActiveMySQLConnection().setConnectTimeout(timeoutMs);
/*      */   }
/*      */   
/*      */   public void setConnectionCollation(String collation) {
/* 1044 */     getActiveMySQLConnection().setConnectionCollation(collation);
/*      */   }
/*      */   
/*      */   public void setConnectionLifecycleInterceptors(String interceptors) {
/* 1048 */     getActiveMySQLConnection().setConnectionLifecycleInterceptors(interceptors);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setContinueBatchOnError(boolean property) {
/* 1053 */     getActiveMySQLConnection().setContinueBatchOnError(property);
/*      */   }
/*      */   
/*      */   public void setCreateDatabaseIfNotExist(boolean flag) {
/* 1057 */     getActiveMySQLConnection().setCreateDatabaseIfNotExist(flag);
/*      */   }
/*      */   
/*      */   public void setDefaultFetchSize(int n) {
/* 1061 */     getActiveMySQLConnection().setDefaultFetchSize(n);
/*      */   }
/*      */   
/*      */   public void setDetectServerPreparedStmts(boolean property) {
/* 1065 */     getActiveMySQLConnection().setDetectServerPreparedStmts(property);
/*      */   }
/*      */   
/*      */   public void setDontTrackOpenResources(boolean flag) {
/* 1069 */     getActiveMySQLConnection().setDontTrackOpenResources(flag);
/*      */   }
/*      */   
/*      */   public void setDumpMetadataOnColumnNotFound(boolean flag) {
/* 1073 */     getActiveMySQLConnection().setDumpMetadataOnColumnNotFound(flag);
/*      */   }
/*      */   
/*      */   public void setDumpQueriesOnException(boolean flag) {
/* 1077 */     getActiveMySQLConnection().setDumpQueriesOnException(flag);
/*      */   }
/*      */   
/*      */   public void setDynamicCalendars(boolean flag) {
/* 1081 */     getActiveMySQLConnection().setDynamicCalendars(flag);
/*      */   }
/*      */   
/*      */   public void setElideSetAutoCommits(boolean flag) {
/* 1085 */     getActiveMySQLConnection().setElideSetAutoCommits(flag);
/*      */   }
/*      */   
/*      */   public void setEmptyStringsConvertToZero(boolean flag) {
/* 1089 */     getActiveMySQLConnection().setEmptyStringsConvertToZero(flag);
/*      */   }
/*      */   
/*      */   public void setEmulateLocators(boolean property) {
/* 1093 */     getActiveMySQLConnection().setEmulateLocators(property);
/*      */   }
/*      */   
/*      */   public void setEmulateUnsupportedPstmts(boolean flag) {
/* 1097 */     getActiveMySQLConnection().setEmulateUnsupportedPstmts(flag);
/*      */   }
/*      */   
/*      */   public void setEnablePacketDebug(boolean flag) {
/* 1101 */     getActiveMySQLConnection().setEnablePacketDebug(flag);
/*      */   }
/*      */   
/*      */   public void setEnableQueryTimeouts(boolean flag) {
/* 1105 */     getActiveMySQLConnection().setEnableQueryTimeouts(flag);
/*      */   }
/*      */   
/*      */   public void setEncoding(String property) {
/* 1109 */     getActiveMySQLConnection().setEncoding(property);
/*      */   }
/*      */   
/*      */   public void setExceptionInterceptors(String exceptionInterceptors) {
/* 1113 */     getActiveMySQLConnection().setExceptionInterceptors(exceptionInterceptors);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setExplainSlowQueries(boolean flag) {
/* 1118 */     getActiveMySQLConnection().setExplainSlowQueries(flag);
/*      */   }
/*      */   
/*      */   public void setFailOverReadOnly(boolean flag) {
/* 1122 */     getActiveMySQLConnection().setFailOverReadOnly(flag);
/*      */   }
/*      */   
/*      */   public void setFunctionsNeverReturnBlobs(boolean flag) {
/* 1126 */     getActiveMySQLConnection().setFunctionsNeverReturnBlobs(flag);
/*      */   }
/*      */   
/*      */   public void setGatherPerfMetrics(boolean flag) {
/* 1130 */     getActiveMySQLConnection().setGatherPerfMetrics(flag);
/*      */   }
/*      */   
/*      */   public void setGatherPerformanceMetrics(boolean flag) {
/* 1134 */     getActiveMySQLConnection().setGatherPerformanceMetrics(flag);
/*      */   }
/*      */   
/*      */   public void setGenerateSimpleParameterMetadata(boolean flag) {
/* 1138 */     getActiveMySQLConnection().setGenerateSimpleParameterMetadata(flag);
/*      */   }
/*      */   
/*      */   public void setHoldResultsOpenOverStatementClose(boolean flag) {
/* 1142 */     getActiveMySQLConnection().setHoldResultsOpenOverStatementClose(flag);
/*      */   }
/*      */   
/*      */   public void setIgnoreNonTxTables(boolean property) {
/* 1146 */     getActiveMySQLConnection().setIgnoreNonTxTables(property);
/*      */   }
/*      */   
/*      */   public void setIncludeInnodbStatusInDeadlockExceptions(boolean flag) {
/* 1150 */     getActiveMySQLConnection().setIncludeInnodbStatusInDeadlockExceptions(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInitialTimeout(int property) {
/* 1155 */     getActiveMySQLConnection().setInitialTimeout(property);
/*      */   }
/*      */   
/*      */   public void setInteractiveClient(boolean property) {
/* 1159 */     getActiveMySQLConnection().setInteractiveClient(property);
/*      */   }
/*      */   
/*      */   public void setIsInteractiveClient(boolean property) {
/* 1163 */     getActiveMySQLConnection().setIsInteractiveClient(property);
/*      */   }
/*      */   
/*      */   public void setJdbcCompliantTruncation(boolean flag) {
/* 1167 */     getActiveMySQLConnection().setJdbcCompliantTruncation(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setJdbcCompliantTruncationForReads(boolean jdbcCompliantTruncationForReads) {
/* 1172 */     getActiveMySQLConnection().setJdbcCompliantTruncationForReads(jdbcCompliantTruncationForReads);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLargeRowSizeThreshold(String value) {
/* 1177 */     getActiveMySQLConnection().setLargeRowSizeThreshold(value);
/*      */   }
/*      */   
/*      */   public void setLoadBalanceBlacklistTimeout(int loadBalanceBlacklistTimeout) {
/* 1181 */     getActiveMySQLConnection().setLoadBalanceBlacklistTimeout(loadBalanceBlacklistTimeout);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLoadBalancePingTimeout(int loadBalancePingTimeout) {
/* 1186 */     getActiveMySQLConnection().setLoadBalancePingTimeout(loadBalancePingTimeout);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLoadBalanceStrategy(String strategy) {
/* 1191 */     getActiveMySQLConnection().setLoadBalanceStrategy(strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceValidateConnectionOnSwapServer(boolean loadBalanceValidateConnectionOnSwapServer) {
/* 1197 */     getActiveMySQLConnection().setLoadBalanceValidateConnectionOnSwapServer(loadBalanceValidateConnectionOnSwapServer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocalSocketAddress(String address) {
/* 1204 */     getActiveMySQLConnection().setLocalSocketAddress(address);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLocatorFetchBufferSize(String value) throws SQLException {
/* 1209 */     getActiveMySQLConnection().setLocatorFetchBufferSize(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLogSlowQueries(boolean flag) {
/* 1214 */     getActiveMySQLConnection().setLogSlowQueries(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLogXaCommands(boolean flag) {
/* 1219 */     getActiveMySQLConnection().setLogXaCommands(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLogger(String property) {
/* 1224 */     getActiveMySQLConnection().setLogger(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLoggerClassName(String className) {
/* 1229 */     getActiveMySQLConnection().setLoggerClassName(className);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMaintainTimeStats(boolean flag) {
/* 1234 */     getActiveMySQLConnection().setMaintainTimeStats(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMaxQuerySizeToLog(int sizeInBytes) {
/* 1239 */     getActiveMySQLConnection().setMaxQuerySizeToLog(sizeInBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMaxReconnects(int property) {
/* 1244 */     getActiveMySQLConnection().setMaxReconnects(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMaxRows(int property) {
/* 1249 */     getActiveMySQLConnection().setMaxRows(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMetadataCacheSize(int value) {
/* 1254 */     getActiveMySQLConnection().setMetadataCacheSize(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNetTimeoutForStreamingResults(int value) {
/* 1259 */     getActiveMySQLConnection().setNetTimeoutForStreamingResults(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNoAccessToProcedureBodies(boolean flag) {
/* 1264 */     getActiveMySQLConnection().setNoAccessToProcedureBodies(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNoDatetimeStringSync(boolean flag) {
/* 1269 */     getActiveMySQLConnection().setNoDatetimeStringSync(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNoTimezoneConversionForTimeType(boolean flag) {
/* 1274 */     getActiveMySQLConnection().setNoTimezoneConversionForTimeType(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNullCatalogMeansCurrent(boolean value) {
/* 1279 */     getActiveMySQLConnection().setNullCatalogMeansCurrent(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNullNamePatternMatchesAll(boolean value) {
/* 1284 */     getActiveMySQLConnection().setNullNamePatternMatchesAll(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOverrideSupportsIntegrityEnhancementFacility(boolean flag) {
/* 1289 */     getActiveMySQLConnection().setOverrideSupportsIntegrityEnhancementFacility(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPacketDebugBufferSize(int size) {
/* 1295 */     getActiveMySQLConnection().setPacketDebugBufferSize(size);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPadCharsWithSpace(boolean flag) {
/* 1300 */     getActiveMySQLConnection().setPadCharsWithSpace(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setParanoid(boolean property) {
/* 1305 */     getActiveMySQLConnection().setParanoid(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPasswordCharacterEncoding(String characterSet) {
/* 1310 */     getActiveMySQLConnection().setPasswordCharacterEncoding(characterSet);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPedantic(boolean property) {
/* 1315 */     getActiveMySQLConnection().setPedantic(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPinGlobalTxToPhysicalConnection(boolean flag) {
/* 1320 */     getActiveMySQLConnection().setPinGlobalTxToPhysicalConnection(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPopulateInsertRowWithDefaultValues(boolean flag) {
/* 1325 */     getActiveMySQLConnection().setPopulateInsertRowWithDefaultValues(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPrepStmtCacheSize(int cacheSize) {
/* 1330 */     getActiveMySQLConnection().setPrepStmtCacheSize(cacheSize);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPrepStmtCacheSqlLimit(int sqlLimit) {
/* 1335 */     getActiveMySQLConnection().setPrepStmtCacheSqlLimit(sqlLimit);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPreparedStatementCacheSize(int cacheSize) {
/* 1340 */     getActiveMySQLConnection().setPreparedStatementCacheSize(cacheSize);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPreparedStatementCacheSqlLimit(int cacheSqlLimit) {
/* 1345 */     getActiveMySQLConnection().setPreparedStatementCacheSqlLimit(cacheSqlLimit);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProcessEscapeCodesForPrepStmts(boolean flag) {
/* 1351 */     getActiveMySQLConnection().setProcessEscapeCodesForPrepStmts(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProfileSQL(boolean flag) {
/* 1356 */     getActiveMySQLConnection().setProfileSQL(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProfileSql(boolean property) {
/* 1361 */     getActiveMySQLConnection().setProfileSql(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProfilerEventHandler(String handler) {
/* 1366 */     getActiveMySQLConnection().setProfilerEventHandler(handler);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPropertiesTransform(String value) {
/* 1371 */     getActiveMySQLConnection().setPropertiesTransform(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setQueriesBeforeRetryMaster(int property) {
/* 1376 */     getActiveMySQLConnection().setQueriesBeforeRetryMaster(property);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQueryTimeoutKillsConnection(boolean queryTimeoutKillsConnection) {
/* 1382 */     getActiveMySQLConnection().setQueryTimeoutKillsConnection(queryTimeoutKillsConnection);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReconnectAtTxEnd(boolean property) {
/* 1388 */     getActiveMySQLConnection().setReconnectAtTxEnd(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRelaxAutoCommit(boolean property) {
/* 1393 */     getActiveMySQLConnection().setRelaxAutoCommit(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setReportMetricsIntervalMillis(int millis) {
/* 1398 */     getActiveMySQLConnection().setReportMetricsIntervalMillis(millis);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRequireSSL(boolean property) {
/* 1403 */     getActiveMySQLConnection().setRequireSSL(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setResourceId(String resourceId) {
/* 1408 */     getActiveMySQLConnection().setResourceId(resourceId);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setResultSetSizeThreshold(int threshold) {
/* 1413 */     getActiveMySQLConnection().setResultSetSizeThreshold(threshold);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRetainStatementAfterResultSetClose(boolean flag) {
/* 1418 */     getActiveMySQLConnection().setRetainStatementAfterResultSetClose(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRetriesAllDown(int retriesAllDown) {
/* 1423 */     getActiveMySQLConnection().setRetriesAllDown(retriesAllDown);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRewriteBatchedStatements(boolean flag) {
/* 1428 */     getActiveMySQLConnection().setRewriteBatchedStatements(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRollbackOnPooledClose(boolean flag) {
/* 1433 */     getActiveMySQLConnection().setRollbackOnPooledClose(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRoundRobinLoadBalance(boolean flag) {
/* 1438 */     getActiveMySQLConnection().setRoundRobinLoadBalance(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRunningCTS13(boolean flag) {
/* 1443 */     getActiveMySQLConnection().setRunningCTS13(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSecondsBeforeRetryMaster(int property) {
/* 1448 */     getActiveMySQLConnection().setSecondsBeforeRetryMaster(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSelfDestructOnPingMaxOperations(int maxOperations) {
/* 1453 */     getActiveMySQLConnection().setSelfDestructOnPingMaxOperations(maxOperations);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelfDestructOnPingSecondsLifetime(int seconds) {
/* 1459 */     getActiveMySQLConnection().setSelfDestructOnPingSecondsLifetime(seconds);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerTimezone(String property) {
/* 1465 */     getActiveMySQLConnection().setServerTimezone(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSessionVariables(String variables) {
/* 1470 */     getActiveMySQLConnection().setSessionVariables(variables);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSlowQueryThresholdMillis(int millis) {
/* 1475 */     getActiveMySQLConnection().setSlowQueryThresholdMillis(millis);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSlowQueryThresholdNanos(long nanos) {
/* 1480 */     getActiveMySQLConnection().setSlowQueryThresholdNanos(nanos);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSocketFactory(String name) {
/* 1485 */     getActiveMySQLConnection().setSocketFactory(name);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSocketFactoryClassName(String property) {
/* 1490 */     getActiveMySQLConnection().setSocketFactoryClassName(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSocketTimeout(int property) {
/* 1495 */     getActiveMySQLConnection().setSocketTimeout(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStatementInterceptors(String value) {
/* 1500 */     getActiveMySQLConnection().setStatementInterceptors(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStrictFloatingPoint(boolean property) {
/* 1505 */     getActiveMySQLConnection().setStrictFloatingPoint(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStrictUpdates(boolean property) {
/* 1510 */     getActiveMySQLConnection().setStrictUpdates(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTcpKeepAlive(boolean flag) {
/* 1515 */     getActiveMySQLConnection().setTcpKeepAlive(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTcpNoDelay(boolean flag) {
/* 1520 */     getActiveMySQLConnection().setTcpNoDelay(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTcpRcvBuf(int bufSize) {
/* 1525 */     getActiveMySQLConnection().setTcpRcvBuf(bufSize);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTcpSndBuf(int bufSize) {
/* 1530 */     getActiveMySQLConnection().setTcpSndBuf(bufSize);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTcpTrafficClass(int classFlags) {
/* 1535 */     getActiveMySQLConnection().setTcpTrafficClass(classFlags);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTinyInt1isBit(boolean flag) {
/* 1540 */     getActiveMySQLConnection().setTinyInt1isBit(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTraceProtocol(boolean flag) {
/* 1545 */     getActiveMySQLConnection().setTraceProtocol(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTransformedBitIsBoolean(boolean flag) {
/* 1550 */     getActiveMySQLConnection().setTransformedBitIsBoolean(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTreatUtilDateAsTimestamp(boolean flag) {
/* 1555 */     getActiveMySQLConnection().setTreatUtilDateAsTimestamp(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTrustCertificateKeyStorePassword(String value) {
/* 1560 */     getActiveMySQLConnection().setTrustCertificateKeyStorePassword(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTrustCertificateKeyStoreType(String value) {
/* 1565 */     getActiveMySQLConnection().setTrustCertificateKeyStoreType(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTrustCertificateKeyStoreUrl(String value) {
/* 1570 */     getActiveMySQLConnection().setTrustCertificateKeyStoreUrl(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUltraDevHack(boolean flag) {
/* 1575 */     getActiveMySQLConnection().setUltraDevHack(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseAffectedRows(boolean flag) {
/* 1580 */     getActiveMySQLConnection().setUseAffectedRows(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseBlobToStoreUTF8OutsideBMP(boolean flag) {
/* 1585 */     getActiveMySQLConnection().setUseBlobToStoreUTF8OutsideBMP(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseColumnNamesInFindColumn(boolean flag) {
/* 1590 */     getActiveMySQLConnection().setUseColumnNamesInFindColumn(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseCompression(boolean property) {
/* 1595 */     getActiveMySQLConnection().setUseCompression(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseConfigs(String configs) {
/* 1600 */     getActiveMySQLConnection().setUseConfigs(configs);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseCursorFetch(boolean flag) {
/* 1605 */     getActiveMySQLConnection().setUseCursorFetch(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseDirectRowUnpack(boolean flag) {
/* 1610 */     getActiveMySQLConnection().setUseDirectRowUnpack(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseDynamicCharsetInfo(boolean flag) {
/* 1615 */     getActiveMySQLConnection().setUseDynamicCharsetInfo(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseFastDateParsing(boolean flag) {
/* 1620 */     getActiveMySQLConnection().setUseFastDateParsing(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseFastIntParsing(boolean flag) {
/* 1625 */     getActiveMySQLConnection().setUseFastIntParsing(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseGmtMillisForDatetimes(boolean flag) {
/* 1630 */     getActiveMySQLConnection().setUseGmtMillisForDatetimes(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseHostsInPrivileges(boolean property) {
/* 1635 */     getActiveMySQLConnection().setUseHostsInPrivileges(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseInformationSchema(boolean flag) {
/* 1640 */     getActiveMySQLConnection().setUseInformationSchema(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseJDBCCompliantTimezoneShift(boolean flag) {
/* 1645 */     getActiveMySQLConnection().setUseJDBCCompliantTimezoneShift(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseJvmCharsetConverters(boolean flag) {
/* 1650 */     getActiveMySQLConnection().setUseJvmCharsetConverters(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseLegacyDatetimeCode(boolean flag) {
/* 1655 */     getActiveMySQLConnection().setUseLegacyDatetimeCode(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseLocalSessionState(boolean flag) {
/* 1660 */     getActiveMySQLConnection().setUseLocalSessionState(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseLocalTransactionState(boolean flag) {
/* 1665 */     getActiveMySQLConnection().setUseLocalTransactionState(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseNanosForElapsedTime(boolean flag) {
/* 1670 */     getActiveMySQLConnection().setUseNanosForElapsedTime(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseOldAliasMetadataBehavior(boolean flag) {
/* 1675 */     getActiveMySQLConnection().setUseOldAliasMetadataBehavior(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseOldUTF8Behavior(boolean flag) {
/* 1680 */     getActiveMySQLConnection().setUseOldUTF8Behavior(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseOnlyServerErrorMessages(boolean flag) {
/* 1685 */     getActiveMySQLConnection().setUseOnlyServerErrorMessages(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseReadAheadInput(boolean flag) {
/* 1690 */     getActiveMySQLConnection().setUseReadAheadInput(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseSSL(boolean property) {
/* 1695 */     getActiveMySQLConnection().setUseSSL(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseSSPSCompatibleTimezoneShift(boolean flag) {
/* 1700 */     getActiveMySQLConnection().setUseSSPSCompatibleTimezoneShift(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseServerPrepStmts(boolean flag) {
/* 1705 */     getActiveMySQLConnection().setUseServerPrepStmts(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseServerPreparedStmts(boolean flag) {
/* 1710 */     getActiveMySQLConnection().setUseServerPreparedStmts(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseSqlStateCodes(boolean flag) {
/* 1715 */     getActiveMySQLConnection().setUseSqlStateCodes(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseStreamLengthsInPrepStmts(boolean property) {
/* 1720 */     getActiveMySQLConnection().setUseStreamLengthsInPrepStmts(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseTimezone(boolean property) {
/* 1725 */     getActiveMySQLConnection().setUseTimezone(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseUltraDevWorkAround(boolean property) {
/* 1730 */     getActiveMySQLConnection().setUseUltraDevWorkAround(property);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseUnbufferedInput(boolean flag) {
/* 1735 */     getActiveMySQLConnection().setUseUnbufferedInput(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseUnicode(boolean flag) {
/* 1740 */     getActiveMySQLConnection().setUseUnicode(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseUsageAdvisor(boolean useUsageAdvisorFlag) {
/* 1745 */     getActiveMySQLConnection().setUseUsageAdvisor(useUsageAdvisorFlag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUtf8OutsideBmpExcludedColumnNamePattern(String regexPattern) {
/* 1750 */     getActiveMySQLConnection().setUtf8OutsideBmpExcludedColumnNamePattern(regexPattern);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUtf8OutsideBmpIncludedColumnNamePattern(String regexPattern) {
/* 1756 */     getActiveMySQLConnection().setUtf8OutsideBmpIncludedColumnNamePattern(regexPattern);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVerifyServerCertificate(boolean flag) {
/* 1762 */     getActiveMySQLConnection().setVerifyServerCertificate(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setYearIsDateType(boolean flag) {
/* 1767 */     getActiveMySQLConnection().setYearIsDateType(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setZeroDateTimeBehavior(String behavior) {
/* 1772 */     getActiveMySQLConnection().setZeroDateTimeBehavior(behavior);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean useUnbufferedInput() {
/* 1777 */     return getActiveMySQLConnection().useUnbufferedInput();
/*      */   }
/*      */ 
/*      */   
/*      */   public StringBuffer generateConnectionCommentBlock(StringBuffer buf) {
/* 1782 */     return getActiveMySQLConnection().generateConnectionCommentBlock(buf);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getActiveStatementCount() {
/* 1787 */     return getActiveMySQLConnection().getActiveStatementCount();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAutoCommit() throws SQLException {
/* 1792 */     return getActiveMySQLConnection().getAutoCommit();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAutoIncrementIncrement() {
/* 1797 */     return getActiveMySQLConnection().getAutoIncrementIncrement();
/*      */   }
/*      */ 
/*      */   
/*      */   public CachedResultSetMetaData getCachedMetaData(String sql) {
/* 1802 */     return getActiveMySQLConnection().getCachedMetaData(sql);
/*      */   }
/*      */ 
/*      */   
/*      */   public Calendar getCalendarInstanceForSessionOrNew() {
/* 1807 */     return getActiveMySQLConnection().getCalendarInstanceForSessionOrNew();
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized Timer getCancelTimer() {
/* 1812 */     return getActiveMySQLConnection().getCancelTimer();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCatalog() throws SQLException {
/* 1817 */     return getActiveMySQLConnection().getCatalog();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCharacterSetMetadata() {
/* 1822 */     return getActiveMySQLConnection().getCharacterSetMetadata();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SingleByteCharsetConverter getCharsetConverter(String javaEncodingName) throws SQLException {
/* 1828 */     return getActiveMySQLConnection().getCharsetConverter(javaEncodingName);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCharsetNameForIndex(int charsetIndex) throws SQLException {
/* 1833 */     return getActiveMySQLConnection().getCharsetNameForIndex(charsetIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public TimeZone getDefaultTimeZone() {
/* 1838 */     return getActiveMySQLConnection().getDefaultTimeZone();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getErrorMessageEncoding() {
/* 1843 */     return getActiveMySQLConnection().getErrorMessageEncoding();
/*      */   }
/*      */ 
/*      */   
/*      */   public ExceptionInterceptor getExceptionInterceptor() {
/* 1848 */     return getActiveMySQLConnection().getExceptionInterceptor();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHoldability() throws SQLException {
/* 1853 */     return getActiveMySQLConnection().getHoldability();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getHost() {
/* 1858 */     return getActiveMySQLConnection().getHost();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getId() {
/* 1863 */     return getActiveMySQLConnection().getId();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getIdleFor() {
/* 1868 */     return getActiveMySQLConnection().getIdleFor();
/*      */   }
/*      */ 
/*      */   
/*      */   public MysqlIO getIO() throws SQLException {
/* 1873 */     return getActiveMySQLConnection().getIO();
/*      */   }
/*      */ 
/*      */   
/*      */   public MySQLConnection getLoadBalanceSafeProxy() {
/* 1878 */     return getActiveMySQLConnection().getLoadBalanceSafeProxy();
/*      */   }
/*      */ 
/*      */   
/*      */   public Log getLog() throws SQLException {
/* 1883 */     return getActiveMySQLConnection().getLog();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxBytesPerChar(String javaCharsetName) throws SQLException {
/* 1888 */     return getActiveMySQLConnection().getMaxBytesPerChar(javaCharsetName);
/*      */   }
/*      */ 
/*      */   
/*      */   public DatabaseMetaData getMetaData() throws SQLException {
/* 1893 */     return getActiveMySQLConnection().getMetaData();
/*      */   }
/*      */ 
/*      */   
/*      */   public Statement getMetadataSafeStatement() throws SQLException {
/* 1898 */     return getActiveMySQLConnection().getMetadataSafeStatement();
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getMutex() throws SQLException {
/* 1903 */     return getActiveMySQLConnection().getMutex();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNetBufferLength() {
/* 1908 */     return getActiveMySQLConnection().getNetBufferLength();
/*      */   }
/*      */ 
/*      */   
/*      */   public Properties getProperties() {
/* 1913 */     return getActiveMySQLConnection().getProperties();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getRequiresEscapingEncoder() {
/* 1918 */     return getActiveMySQLConnection().getRequiresEscapingEncoder();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerCharacterEncoding() {
/* 1923 */     return getActiveMySQLConnection().getServerCharacterEncoding();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getServerMajorVersion() {
/* 1928 */     return getActiveMySQLConnection().getServerMajorVersion();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getServerMinorVersion() {
/* 1933 */     return getActiveMySQLConnection().getServerMinorVersion();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getServerSubMinorVersion() {
/* 1938 */     return getActiveMySQLConnection().getServerSubMinorVersion();
/*      */   }
/*      */ 
/*      */   
/*      */   public TimeZone getServerTimezoneTZ() {
/* 1943 */     return getActiveMySQLConnection().getServerTimezoneTZ();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerVariable(String variableName) {
/* 1948 */     return getActiveMySQLConnection().getServerVariable(variableName);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerVersion() {
/* 1953 */     return getActiveMySQLConnection().getServerVersion();
/*      */   }
/*      */ 
/*      */   
/*      */   public Calendar getSessionLockedCalendar() {
/* 1958 */     return getActiveMySQLConnection().getSessionLockedCalendar();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getStatementComment() {
/* 1963 */     return getActiveMySQLConnection().getStatementComment();
/*      */   }
/*      */ 
/*      */   
/*      */   public List getStatementInterceptorsInstances() {
/* 1968 */     return getActiveMySQLConnection().getStatementInterceptorsInstances();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTransactionIsolation() throws SQLException {
/* 1973 */     return getActiveMySQLConnection().getTransactionIsolation();
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized Map getTypeMap() throws SQLException {
/* 1978 */     return getActiveMySQLConnection().getTypeMap();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getURL() {
/* 1983 */     return getActiveMySQLConnection().getURL();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getUser() {
/* 1988 */     return getActiveMySQLConnection().getUser();
/*      */   }
/*      */ 
/*      */   
/*      */   public Calendar getUtcCalendar() {
/* 1993 */     return getActiveMySQLConnection().getUtcCalendar();
/*      */   }
/*      */ 
/*      */   
/*      */   public SQLWarning getWarnings() throws SQLException {
/* 1998 */     return getActiveMySQLConnection().getWarnings();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasSameProperties(Connection c) {
/* 2003 */     return getActiveMySQLConnection().hasSameProperties(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasTriedMaster() {
/* 2008 */     return getActiveMySQLConnection().hasTriedMaster();
/*      */   }
/*      */ 
/*      */   
/*      */   public void incrementNumberOfPreparedExecutes() {
/* 2013 */     getActiveMySQLConnection().incrementNumberOfPreparedExecutes();
/*      */   }
/*      */ 
/*      */   
/*      */   public void incrementNumberOfPrepares() {
/* 2018 */     getActiveMySQLConnection().incrementNumberOfPrepares();
/*      */   }
/*      */ 
/*      */   
/*      */   public void incrementNumberOfResultSetsCreated() {
/* 2023 */     getActiveMySQLConnection().incrementNumberOfResultSetsCreated();
/*      */   }
/*      */ 
/*      */   
/*      */   public void initializeExtension(Extension ex) throws SQLException {
/* 2028 */     getActiveMySQLConnection().initializeExtension(ex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initializeResultsMetadataFromCache(String sql, CachedResultSetMetaData cachedMetaData, ResultSetInternalMethods resultSet) throws SQLException {
/* 2035 */     getActiveMySQLConnection().initializeResultsMetadataFromCache(sql, cachedMetaData, resultSet);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void initializeSafeStatementInterceptors() throws SQLException {
/* 2041 */     getActiveMySQLConnection().initializeSafeStatementInterceptors();
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean isAbonormallyLongQuery(long millisOrNanos) {
/* 2046 */     return getActiveMySQLConnection().isAbonormallyLongQuery(millisOrNanos);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isClientTzUTC() {
/* 2051 */     return getActiveMySQLConnection().isClientTzUTC();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCursorFetchEnabled() throws SQLException {
/* 2056 */     return getActiveMySQLConnection().isCursorFetchEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInGlobalTx() {
/* 2061 */     return getActiveMySQLConnection().isInGlobalTx();
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean isMasterConnection() {
/* 2066 */     return getActiveMySQLConnection().isMasterConnection();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNoBackslashEscapesSet() {
/* 2071 */     return getActiveMySQLConnection().isNoBackslashEscapesSet();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadInfoMsgEnabled() {
/* 2076 */     return getActiveMySQLConnection().isReadInfoMsgEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() throws SQLException {
/* 2081 */     return getActiveMySQLConnection().isReadOnly();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRunningOnJDK13() {
/* 2086 */     return getActiveMySQLConnection().isRunningOnJDK13();
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean isSameResource(Connection otherConnection) {
/* 2091 */     return getActiveMySQLConnection().isSameResource(otherConnection);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isServerTzUTC() {
/* 2096 */     return getActiveMySQLConnection().isServerTzUTC();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean lowerCaseTableNames() {
/* 2101 */     return getActiveMySQLConnection().lowerCaseTableNames();
/*      */   }
/*      */ 
/*      */   
/*      */   public void maxRowsChanged(Statement stmt) {
/* 2106 */     getActiveMySQLConnection().maxRowsChanged(stmt);
/*      */   }
/*      */ 
/*      */   
/*      */   public String nativeSQL(String sql) throws SQLException {
/* 2111 */     return getActiveMySQLConnection().nativeSQL(sql);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean parserKnowsUnicode() {
/* 2116 */     return getActiveMySQLConnection().parserKnowsUnicode();
/*      */   }
/*      */ 
/*      */   
/*      */   public void ping() throws SQLException {
/* 2121 */     getActiveMySQLConnection().ping();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void pingInternal(boolean checkForClosedConnection, int timeoutMillis) throws SQLException {
/* 2127 */     getActiveMySQLConnection().pingInternal(checkForClosedConnection, timeoutMillis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 2135 */     return getActiveMySQLConnection().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/* 2142 */     return getActiveMySQLConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String sql) throws SQLException {
/* 2148 */     return getActiveMySQLConnection().prepareCall(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 2155 */     return getActiveMySQLConnection().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/* 2162 */     return getActiveMySQLConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/* 2169 */     return getActiveMySQLConnection().prepareStatement(sql, autoGenKeyIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/* 2176 */     return getActiveMySQLConnection().prepareStatement(sql, autoGenKeyIndexes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/* 2183 */     return getActiveMySQLConnection().prepareStatement(sql, autoGenKeyColNames);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql) throws SQLException {
/* 2189 */     return getActiveMySQLConnection().prepareStatement(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void realClose(boolean calledExplicitly, boolean issueRollback, boolean skipLocalTeardown, Throwable reason) throws SQLException {
/* 2195 */     getActiveMySQLConnection().realClose(calledExplicitly, issueRollback, skipLocalTeardown, reason);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void recachePreparedStatement(ServerPreparedStatement pstmt) throws SQLException {
/* 2202 */     getActiveMySQLConnection().recachePreparedStatement(pstmt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void registerQueryExecutionTime(long queryTimeMs) {
/* 2207 */     getActiveMySQLConnection().registerQueryExecutionTime(queryTimeMs);
/*      */   }
/*      */ 
/*      */   
/*      */   public void registerStatement(Statement stmt) {
/* 2212 */     getActiveMySQLConnection().registerStatement(stmt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void releaseSavepoint(Savepoint arg0) throws SQLException {
/* 2217 */     getActiveMySQLConnection().releaseSavepoint(arg0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void reportNumberOfTablesAccessed(int numTablesAccessed) {
/* 2222 */     getActiveMySQLConnection().reportNumberOfTablesAccessed(numTablesAccessed);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void reportQueryTime(long millisOrNanos) {
/* 2228 */     getActiveMySQLConnection().reportQueryTime(millisOrNanos);
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetServerState() throws SQLException {
/* 2233 */     getActiveMySQLConnection().resetServerState();
/*      */   }
/*      */ 
/*      */   
/*      */   public void rollback() throws SQLException {
/* 2238 */     getActiveMySQLConnection().rollback();
/*      */   }
/*      */ 
/*      */   
/*      */   public void rollback(Savepoint savepoint) throws SQLException {
/* 2243 */     getActiveMySQLConnection().rollback(savepoint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 2250 */     return getActiveMySQLConnection().serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/* 2257 */     return getActiveMySQLConnection().serverPrepareStatement(sql, resultSetType, resultSetConcurrency);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/* 2264 */     return getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/* 2271 */     return getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyIndexes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/* 2278 */     return getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyColNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql) throws SQLException {
/* 2285 */     return getActiveMySQLConnection().serverPrepareStatement(sql);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean serverSupportsConvertFn() throws SQLException {
/* 2290 */     return getActiveMySQLConnection().serverSupportsConvertFn();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAutoCommit(boolean autoCommitFlag) throws SQLException {
/* 2295 */     getActiveMySQLConnection().setAutoCommit(autoCommitFlag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCatalog(String catalog) throws SQLException {
/* 2300 */     getActiveMySQLConnection().setCatalog(catalog);
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void setFailedOver(boolean flag) {
/* 2305 */     getActiveMySQLConnection().setFailedOver(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHoldability(int arg0) throws SQLException {
/* 2310 */     getActiveMySQLConnection().setHoldability(arg0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInGlobalTx(boolean flag) {
/* 2315 */     getActiveMySQLConnection().setInGlobalTx(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPreferSlaveDuringFailover(boolean flag) {
/* 2320 */     getActiveMySQLConnection().setPreferSlaveDuringFailover(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProxy(MySQLConnection proxy) {
/* 2325 */     getActiveMySQLConnection().setProxy(proxy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setReadInfoMsgEnabled(boolean flag) {
/* 2330 */     getActiveMySQLConnection().setReadInfoMsgEnabled(flag);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setReadOnly(boolean readOnlyFlag) throws SQLException {
/* 2335 */     getActiveMySQLConnection().setReadOnly(readOnlyFlag);
/*      */   }
/*      */   
/*      */   public void setReadOnlyInternal(boolean readOnlyFlag) throws SQLException {
/* 2339 */     getActiveMySQLConnection().setReadOnlyInternal(readOnlyFlag);
/*      */   }
/*      */   
/*      */   public Savepoint setSavepoint() throws SQLException {
/* 2343 */     return getActiveMySQLConnection().setSavepoint();
/*      */   }
/*      */   
/*      */   public synchronized Savepoint setSavepoint(String name) throws SQLException {
/* 2347 */     return getActiveMySQLConnection().setSavepoint(name);
/*      */   }
/*      */   
/*      */   public void setStatementComment(String comment) {
/* 2351 */     getActiveMySQLConnection().setStatementComment(comment);
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void setTransactionIsolation(int level) throws SQLException {
/* 2356 */     getActiveMySQLConnection().setTransactionIsolation(level);
/*      */   }
/*      */ 
/*      */   
/*      */   public void shutdownServer() throws SQLException {
/* 2361 */     getActiveMySQLConnection().shutdownServer();
/*      */   }
/*      */   
/*      */   public boolean storesLowerCaseTableName() {
/* 2365 */     return getActiveMySQLConnection().storesLowerCaseTableName();
/*      */   }
/*      */   
/*      */   public boolean supportsIsolationLevel() {
/* 2369 */     return getActiveMySQLConnection().supportsIsolationLevel();
/*      */   }
/*      */   
/*      */   public boolean supportsQuotedIdentifiers() {
/* 2373 */     return getActiveMySQLConnection().supportsQuotedIdentifiers();
/*      */   }
/*      */   
/*      */   public boolean supportsTransactions() {
/* 2377 */     return getActiveMySQLConnection().supportsTransactions();
/*      */   }
/*      */   
/*      */   public void throwConnectionClosedException() throws SQLException {
/* 2381 */     getActiveMySQLConnection().throwConnectionClosedException();
/*      */   }
/*      */   
/*      */   public void transactionBegun() throws SQLException {
/* 2385 */     getActiveMySQLConnection().transactionBegun();
/*      */   }
/*      */   
/*      */   public void transactionCompleted() throws SQLException {
/* 2389 */     getActiveMySQLConnection().transactionCompleted();
/*      */   }
/*      */   
/*      */   public void unregisterStatement(Statement stmt) {
/* 2393 */     getActiveMySQLConnection().unregisterStatement(stmt);
/*      */   }
/*      */   
/*      */   public void unSafeStatementInterceptors() throws SQLException {
/* 2397 */     getActiveMySQLConnection().unSafeStatementInterceptors();
/*      */   }
/*      */   
/*      */   public void unsetMaxRows(Statement stmt) throws SQLException {
/* 2401 */     getActiveMySQLConnection().unsetMaxRows(stmt);
/*      */   }
/*      */   
/*      */   public boolean useAnsiQuotedIdentifiers() {
/* 2405 */     return getActiveMySQLConnection().useAnsiQuotedIdentifiers();
/*      */   }
/*      */   
/*      */   public boolean useMaxRows() {
/* 2409 */     return getActiveMySQLConnection().useMaxRows();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean versionMeetsMinimum(int major, int minor, int subminor) throws SQLException {
/* 2414 */     return getActiveMySQLConnection().versionMeetsMinimum(major, minor, subminor);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isClosed() throws SQLException {
/* 2419 */     return getActiveMySQLConnection().isClosed();
/*      */   }
/*      */   
/*      */   public boolean getHoldResultsOpenOverStatementClose() {
/* 2423 */     return getActiveMySQLConnection().getHoldResultsOpenOverStatementClose();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLoadBalanceConnectionGroup() {
/* 2428 */     return getActiveMySQLConnection().getLoadBalanceConnectionGroup();
/*      */   }
/*      */   
/*      */   public boolean getLoadBalanceEnableJMX() {
/* 2432 */     return getActiveMySQLConnection().getLoadBalanceEnableJMX();
/*      */   }
/*      */   
/*      */   public String getLoadBalanceExceptionChecker() {
/* 2436 */     return getActiveMySQLConnection().getLoadBalanceExceptionChecker();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLoadBalanceSQLExceptionSubclassFailover() {
/* 2441 */     return getActiveMySQLConnection().getLoadBalanceSQLExceptionSubclassFailover();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLoadBalanceSQLStateFailover() {
/* 2446 */     return getActiveMySQLConnection().getLoadBalanceSQLStateFailover();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLoadBalanceConnectionGroup(String loadBalanceConnectionGroup) {
/* 2451 */     getActiveMySQLConnection().setLoadBalanceConnectionGroup(loadBalanceConnectionGroup);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceEnableJMX(boolean loadBalanceEnableJMX) {
/* 2457 */     getActiveMySQLConnection().setLoadBalanceEnableJMX(loadBalanceEnableJMX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceExceptionChecker(String loadBalanceExceptionChecker) {
/* 2464 */     getActiveMySQLConnection().setLoadBalanceExceptionChecker(loadBalanceExceptionChecker);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceSQLExceptionSubclassFailover(String loadBalanceSQLExceptionSubclassFailover) {
/* 2471 */     getActiveMySQLConnection().setLoadBalanceSQLExceptionSubclassFailover(loadBalanceSQLExceptionSubclassFailover);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceSQLStateFailover(String loadBalanceSQLStateFailover) {
/* 2478 */     getActiveMySQLConnection().setLoadBalanceSQLStateFailover(loadBalanceSQLStateFailover);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldExecutionTriggerServerSwapAfter(String SQL) {
/* 2484 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isProxySet() {
/* 2488 */     return getActiveMySQLConnection().isProxySet();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLoadBalanceAutoCommitStatementRegex() {
/* 2493 */     return getActiveMySQLConnection().getLoadBalanceAutoCommitStatementRegex();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLoadBalanceAutoCommitStatementThreshold() {
/* 2498 */     return getActiveMySQLConnection().getLoadBalanceAutoCommitStatementThreshold();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceAutoCommitStatementRegex(String loadBalanceAutoCommitStatementRegex) {
/* 2504 */     getActiveMySQLConnection().setLoadBalanceAutoCommitStatementRegex(loadBalanceAutoCommitStatementRegex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceAutoCommitStatementThreshold(int loadBalanceAutoCommitStatementThreshold) {
/* 2511 */     getActiveMySQLConnection().setLoadBalanceAutoCommitStatementThreshold(loadBalanceAutoCommitStatementThreshold);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setTypeMap(Map<String, Class<?>> map) throws SQLException {
/* 2517 */     getActiveMySQLConnection().setTypeMap(map);
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\LoadBalancedMySQLConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */