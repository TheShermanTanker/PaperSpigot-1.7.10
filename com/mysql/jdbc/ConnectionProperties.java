package com.mysql.jdbc;

import java.sql.SQLException;

public interface ConnectionProperties {
  String exposeAsXml() throws SQLException;
  
  boolean getAllowLoadLocalInfile();
  
  boolean getAllowMultiQueries();
  
  boolean getAllowNanAndInf();
  
  boolean getAllowUrlInLocalInfile();
  
  boolean getAlwaysSendSetIsolation();
  
  boolean getAutoDeserialize();
  
  boolean getAutoGenerateTestcaseScript();
  
  boolean getAutoReconnectForPools();
  
  int getBlobSendChunkSize();
  
  boolean getCacheCallableStatements();
  
  boolean getCachePreparedStatements();
  
  boolean getCacheResultSetMetadata();
  
  boolean getCacheServerConfiguration();
  
  int getCallableStatementCacheSize();
  
  boolean getCapitalizeTypeNames();
  
  String getCharacterSetResults();
  
  boolean getClobberStreamingResults();
  
  String getClobCharacterEncoding();
  
  String getConnectionCollation();
  
  int getConnectTimeout();
  
  boolean getContinueBatchOnError();
  
  boolean getCreateDatabaseIfNotExist();
  
  int getDefaultFetchSize();
  
  boolean getDontTrackOpenResources();
  
  boolean getDumpQueriesOnException();
  
  boolean getDynamicCalendars();
  
  boolean getElideSetAutoCommits();
  
  boolean getEmptyStringsConvertToZero();
  
  boolean getEmulateLocators();
  
  boolean getEmulateUnsupportedPstmts();
  
  boolean getEnablePacketDebug();
  
  String getEncoding();
  
  boolean getExplainSlowQueries();
  
  boolean getFailOverReadOnly();
  
  boolean getGatherPerformanceMetrics();
  
  boolean getHoldResultsOpenOverStatementClose();
  
  boolean getIgnoreNonTxTables();
  
  int getInitialTimeout();
  
  boolean getInteractiveClient();
  
  boolean getIsInteractiveClient();
  
  boolean getJdbcCompliantTruncation();
  
  int getLocatorFetchBufferSize();
  
  String getLogger();
  
  String getLoggerClassName();
  
  boolean getLogSlowQueries();
  
  boolean getMaintainTimeStats();
  
  int getMaxQuerySizeToLog();
  
  int getMaxReconnects();
  
  int getMaxRows();
  
  int getMetadataCacheSize();
  
  boolean getNoDatetimeStringSync();
  
  boolean getNullCatalogMeansCurrent();
  
  boolean getNullNamePatternMatchesAll();
  
  int getPacketDebugBufferSize();
  
  boolean getParanoid();
  
  boolean getPedantic();
  
  int getPreparedStatementCacheSize();
  
  int getPreparedStatementCacheSqlLimit();
  
  boolean getProfileSql();
  
  boolean getProfileSQL();
  
  String getPropertiesTransform();
  
  int getQueriesBeforeRetryMaster();
  
  boolean getReconnectAtTxEnd();
  
  boolean getRelaxAutoCommit();
  
  int getReportMetricsIntervalMillis();
  
  boolean getRequireSSL();
  
  boolean getRollbackOnPooledClose();
  
  boolean getRoundRobinLoadBalance();
  
  boolean getRunningCTS13();
  
  int getSecondsBeforeRetryMaster();
  
  String getServerTimezone();
  
  String getSessionVariables();
  
  int getSlowQueryThresholdMillis();
  
  String getSocketFactoryClassName();
  
  int getSocketTimeout();
  
  boolean getStrictFloatingPoint();
  
  boolean getStrictUpdates();
  
  boolean getTinyInt1isBit();
  
  boolean getTraceProtocol();
  
  boolean getTransformedBitIsBoolean();
  
  boolean getUseCompression();
  
  boolean getUseFastIntParsing();
  
  boolean getUseHostsInPrivileges();
  
  boolean getUseInformationSchema();
  
  boolean getUseLocalSessionState();
  
  boolean getUseOldUTF8Behavior();
  
  boolean getUseOnlyServerErrorMessages();
  
  boolean getUseReadAheadInput();
  
  boolean getUseServerPreparedStmts();
  
  boolean getUseSqlStateCodes();
  
  boolean getUseSSL();
  
  boolean getUseStreamLengthsInPrepStmts();
  
  boolean getUseTimezone();
  
  boolean getUseUltraDevWorkAround();
  
  boolean getUseUnbufferedInput();
  
  boolean getUseUnicode();
  
  boolean getUseUsageAdvisor();
  
  boolean getYearIsDateType();
  
  String getZeroDateTimeBehavior();
  
  void setAllowLoadLocalInfile(boolean paramBoolean);
  
  void setAllowMultiQueries(boolean paramBoolean);
  
  void setAllowNanAndInf(boolean paramBoolean);
  
  void setAllowUrlInLocalInfile(boolean paramBoolean);
  
  void setAlwaysSendSetIsolation(boolean paramBoolean);
  
  void setAutoDeserialize(boolean paramBoolean);
  
  void setAutoGenerateTestcaseScript(boolean paramBoolean);
  
  void setAutoReconnect(boolean paramBoolean);
  
  void setAutoReconnectForConnectionPools(boolean paramBoolean);
  
  void setAutoReconnectForPools(boolean paramBoolean);
  
  void setBlobSendChunkSize(String paramString) throws SQLException;
  
  void setCacheCallableStatements(boolean paramBoolean);
  
  void setCachePreparedStatements(boolean paramBoolean);
  
  void setCacheResultSetMetadata(boolean paramBoolean);
  
  void setCacheServerConfiguration(boolean paramBoolean);
  
  void setCallableStatementCacheSize(int paramInt);
  
  void setCapitalizeDBMDTypes(boolean paramBoolean);
  
  void setCapitalizeTypeNames(boolean paramBoolean);
  
  void setCharacterEncoding(String paramString);
  
  void setCharacterSetResults(String paramString);
  
  void setClobberStreamingResults(boolean paramBoolean);
  
  void setClobCharacterEncoding(String paramString);
  
  void setConnectionCollation(String paramString);
  
  void setConnectTimeout(int paramInt);
  
  void setContinueBatchOnError(boolean paramBoolean);
  
  void setCreateDatabaseIfNotExist(boolean paramBoolean);
  
  void setDefaultFetchSize(int paramInt);
  
  void setDetectServerPreparedStmts(boolean paramBoolean);
  
  void setDontTrackOpenResources(boolean paramBoolean);
  
  void setDumpQueriesOnException(boolean paramBoolean);
  
  void setDynamicCalendars(boolean paramBoolean);
  
  void setElideSetAutoCommits(boolean paramBoolean);
  
  void setEmptyStringsConvertToZero(boolean paramBoolean);
  
  void setEmulateLocators(boolean paramBoolean);
  
  void setEmulateUnsupportedPstmts(boolean paramBoolean);
  
  void setEnablePacketDebug(boolean paramBoolean);
  
  void setEncoding(String paramString);
  
  void setExplainSlowQueries(boolean paramBoolean);
  
  void setFailOverReadOnly(boolean paramBoolean);
  
  void setGatherPerformanceMetrics(boolean paramBoolean);
  
  void setHoldResultsOpenOverStatementClose(boolean paramBoolean);
  
  void setIgnoreNonTxTables(boolean paramBoolean);
  
  void setInitialTimeout(int paramInt);
  
  void setIsInteractiveClient(boolean paramBoolean);
  
  void setJdbcCompliantTruncation(boolean paramBoolean);
  
  void setLocatorFetchBufferSize(String paramString) throws SQLException;
  
  void setLogger(String paramString);
  
  void setLoggerClassName(String paramString);
  
  void setLogSlowQueries(boolean paramBoolean);
  
  void setMaintainTimeStats(boolean paramBoolean);
  
  void setMaxQuerySizeToLog(int paramInt);
  
  void setMaxReconnects(int paramInt);
  
  void setMaxRows(int paramInt);
  
  void setMetadataCacheSize(int paramInt);
  
  void setNoDatetimeStringSync(boolean paramBoolean);
  
  void setNullCatalogMeansCurrent(boolean paramBoolean);
  
  void setNullNamePatternMatchesAll(boolean paramBoolean);
  
  void setPacketDebugBufferSize(int paramInt);
  
  void setParanoid(boolean paramBoolean);
  
  void setPedantic(boolean paramBoolean);
  
  void setPreparedStatementCacheSize(int paramInt);
  
  void setPreparedStatementCacheSqlLimit(int paramInt);
  
  void setProfileSql(boolean paramBoolean);
  
  void setProfileSQL(boolean paramBoolean);
  
  void setPropertiesTransform(String paramString);
  
  void setQueriesBeforeRetryMaster(int paramInt);
  
  void setReconnectAtTxEnd(boolean paramBoolean);
  
  void setRelaxAutoCommit(boolean paramBoolean);
  
  void setReportMetricsIntervalMillis(int paramInt);
  
  void setRequireSSL(boolean paramBoolean);
  
  void setRetainStatementAfterResultSetClose(boolean paramBoolean);
  
  void setRollbackOnPooledClose(boolean paramBoolean);
  
  void setRoundRobinLoadBalance(boolean paramBoolean);
  
  void setRunningCTS13(boolean paramBoolean);
  
  void setSecondsBeforeRetryMaster(int paramInt);
  
  void setServerTimezone(String paramString);
  
  void setSessionVariables(String paramString);
  
  void setSlowQueryThresholdMillis(int paramInt);
  
  void setSocketFactoryClassName(String paramString);
  
  void setSocketTimeout(int paramInt);
  
  void setStrictFloatingPoint(boolean paramBoolean);
  
  void setStrictUpdates(boolean paramBoolean);
  
  void setTinyInt1isBit(boolean paramBoolean);
  
  void setTraceProtocol(boolean paramBoolean);
  
  void setTransformedBitIsBoolean(boolean paramBoolean);
  
  void setUseCompression(boolean paramBoolean);
  
  void setUseFastIntParsing(boolean paramBoolean);
  
  void setUseHostsInPrivileges(boolean paramBoolean);
  
  void setUseInformationSchema(boolean paramBoolean);
  
  void setUseLocalSessionState(boolean paramBoolean);
  
  void setUseOldUTF8Behavior(boolean paramBoolean);
  
  void setUseOnlyServerErrorMessages(boolean paramBoolean);
  
  void setUseReadAheadInput(boolean paramBoolean);
  
  void setUseServerPreparedStmts(boolean paramBoolean);
  
  void setUseSqlStateCodes(boolean paramBoolean);
  
  void setUseSSL(boolean paramBoolean);
  
  void setUseStreamLengthsInPrepStmts(boolean paramBoolean);
  
  void setUseTimezone(boolean paramBoolean);
  
  void setUseUltraDevWorkAround(boolean paramBoolean);
  
  void setUseUnbufferedInput(boolean paramBoolean);
  
  void setUseUnicode(boolean paramBoolean);
  
  void setUseUsageAdvisor(boolean paramBoolean);
  
  void setYearIsDateType(boolean paramBoolean);
  
  void setZeroDateTimeBehavior(String paramString);
  
  boolean useUnbufferedInput();
  
  boolean getUseCursorFetch();
  
  void setUseCursorFetch(boolean paramBoolean);
  
  boolean getOverrideSupportsIntegrityEnhancementFacility();
  
  void setOverrideSupportsIntegrityEnhancementFacility(boolean paramBoolean);
  
  boolean getNoTimezoneConversionForTimeType();
  
  void setNoTimezoneConversionForTimeType(boolean paramBoolean);
  
  boolean getUseJDBCCompliantTimezoneShift();
  
  void setUseJDBCCompliantTimezoneShift(boolean paramBoolean);
  
  boolean getAutoClosePStmtStreams();
  
  void setAutoClosePStmtStreams(boolean paramBoolean);
  
  boolean getProcessEscapeCodesForPrepStmts();
  
  void setProcessEscapeCodesForPrepStmts(boolean paramBoolean);
  
  boolean getUseGmtMillisForDatetimes();
  
  void setUseGmtMillisForDatetimes(boolean paramBoolean);
  
  boolean getDumpMetadataOnColumnNotFound();
  
  void setDumpMetadataOnColumnNotFound(boolean paramBoolean);
  
  String getResourceId();
  
  void setResourceId(String paramString);
  
  boolean getRewriteBatchedStatements();
  
  void setRewriteBatchedStatements(boolean paramBoolean);
  
  boolean getJdbcCompliantTruncationForReads();
  
  void setJdbcCompliantTruncationForReads(boolean paramBoolean);
  
  boolean getUseJvmCharsetConverters();
  
  void setUseJvmCharsetConverters(boolean paramBoolean);
  
  boolean getPinGlobalTxToPhysicalConnection();
  
  void setPinGlobalTxToPhysicalConnection(boolean paramBoolean);
  
  void setGatherPerfMetrics(boolean paramBoolean);
  
  boolean getGatherPerfMetrics();
  
  void setUltraDevHack(boolean paramBoolean);
  
  boolean getUltraDevHack();
  
  void setInteractiveClient(boolean paramBoolean);
  
  void setSocketFactory(String paramString);
  
  String getSocketFactory();
  
  void setUseServerPrepStmts(boolean paramBoolean);
  
  boolean getUseServerPrepStmts();
  
  void setCacheCallableStmts(boolean paramBoolean);
  
  boolean getCacheCallableStmts();
  
  void setCachePrepStmts(boolean paramBoolean);
  
  boolean getCachePrepStmts();
  
  void setCallableStmtCacheSize(int paramInt);
  
  int getCallableStmtCacheSize();
  
  void setPrepStmtCacheSize(int paramInt);
  
  int getPrepStmtCacheSize();
  
  void setPrepStmtCacheSqlLimit(int paramInt);
  
  int getPrepStmtCacheSqlLimit();
  
  boolean getNoAccessToProcedureBodies();
  
  void setNoAccessToProcedureBodies(boolean paramBoolean);
  
  boolean getUseOldAliasMetadataBehavior();
  
  void setUseOldAliasMetadataBehavior(boolean paramBoolean);
  
  String getClientCertificateKeyStorePassword();
  
  void setClientCertificateKeyStorePassword(String paramString);
  
  String getClientCertificateKeyStoreType();
  
  void setClientCertificateKeyStoreType(String paramString);
  
  String getClientCertificateKeyStoreUrl();
  
  void setClientCertificateKeyStoreUrl(String paramString);
  
  String getTrustCertificateKeyStorePassword();
  
  void setTrustCertificateKeyStorePassword(String paramString);
  
  String getTrustCertificateKeyStoreType();
  
  void setTrustCertificateKeyStoreType(String paramString);
  
  String getTrustCertificateKeyStoreUrl();
  
  void setTrustCertificateKeyStoreUrl(String paramString);
  
  boolean getUseSSPSCompatibleTimezoneShift();
  
  void setUseSSPSCompatibleTimezoneShift(boolean paramBoolean);
  
  boolean getTreatUtilDateAsTimestamp();
  
  void setTreatUtilDateAsTimestamp(boolean paramBoolean);
  
  boolean getUseFastDateParsing();
  
  void setUseFastDateParsing(boolean paramBoolean);
  
  String getLocalSocketAddress();
  
  void setLocalSocketAddress(String paramString);
  
  void setUseConfigs(String paramString);
  
  String getUseConfigs();
  
  boolean getGenerateSimpleParameterMetadata();
  
  void setGenerateSimpleParameterMetadata(boolean paramBoolean);
  
  boolean getLogXaCommands();
  
  void setLogXaCommands(boolean paramBoolean);
  
  int getResultSetSizeThreshold();
  
  void setResultSetSizeThreshold(int paramInt);
  
  int getNetTimeoutForStreamingResults();
  
  void setNetTimeoutForStreamingResults(int paramInt);
  
  boolean getEnableQueryTimeouts();
  
  void setEnableQueryTimeouts(boolean paramBoolean);
  
  boolean getPadCharsWithSpace();
  
  void setPadCharsWithSpace(boolean paramBoolean);
  
  boolean getUseDynamicCharsetInfo();
  
  void setUseDynamicCharsetInfo(boolean paramBoolean);
  
  String getClientInfoProvider();
  
  void setClientInfoProvider(String paramString);
  
  boolean getPopulateInsertRowWithDefaultValues();
  
  void setPopulateInsertRowWithDefaultValues(boolean paramBoolean);
  
  String getLoadBalanceStrategy();
  
  void setLoadBalanceStrategy(String paramString);
  
  boolean getTcpNoDelay();
  
  void setTcpNoDelay(boolean paramBoolean);
  
  boolean getTcpKeepAlive();
  
  void setTcpKeepAlive(boolean paramBoolean);
  
  int getTcpRcvBuf();
  
  void setTcpRcvBuf(int paramInt);
  
  int getTcpSndBuf();
  
  void setTcpSndBuf(int paramInt);
  
  int getTcpTrafficClass();
  
  void setTcpTrafficClass(int paramInt);
  
  boolean getUseNanosForElapsedTime();
  
  void setUseNanosForElapsedTime(boolean paramBoolean);
  
  long getSlowQueryThresholdNanos();
  
  void setSlowQueryThresholdNanos(long paramLong);
  
  String getStatementInterceptors();
  
  void setStatementInterceptors(String paramString);
  
  boolean getUseDirectRowUnpack();
  
  void setUseDirectRowUnpack(boolean paramBoolean);
  
  String getLargeRowSizeThreshold();
  
  void setLargeRowSizeThreshold(String paramString);
  
  boolean getUseBlobToStoreUTF8OutsideBMP();
  
  void setUseBlobToStoreUTF8OutsideBMP(boolean paramBoolean);
  
  String getUtf8OutsideBmpExcludedColumnNamePattern();
  
  void setUtf8OutsideBmpExcludedColumnNamePattern(String paramString);
  
  String getUtf8OutsideBmpIncludedColumnNamePattern();
  
  void setUtf8OutsideBmpIncludedColumnNamePattern(String paramString);
  
  boolean getIncludeInnodbStatusInDeadlockExceptions();
  
  void setIncludeInnodbStatusInDeadlockExceptions(boolean paramBoolean);
  
  boolean getBlobsAreStrings();
  
  void setBlobsAreStrings(boolean paramBoolean);
  
  boolean getFunctionsNeverReturnBlobs();
  
  void setFunctionsNeverReturnBlobs(boolean paramBoolean);
  
  boolean getAutoSlowLog();
  
  void setAutoSlowLog(boolean paramBoolean);
  
  String getConnectionLifecycleInterceptors();
  
  void setConnectionLifecycleInterceptors(String paramString);
  
  String getProfilerEventHandler();
  
  void setProfilerEventHandler(String paramString);
  
  boolean getVerifyServerCertificate();
  
  void setVerifyServerCertificate(boolean paramBoolean);
  
  boolean getUseLegacyDatetimeCode();
  
  void setUseLegacyDatetimeCode(boolean paramBoolean);
  
  int getSelfDestructOnPingSecondsLifetime();
  
  void setSelfDestructOnPingSecondsLifetime(int paramInt);
  
  int getSelfDestructOnPingMaxOperations();
  
  void setSelfDestructOnPingMaxOperations(int paramInt);
  
  boolean getUseColumnNamesInFindColumn();
  
  void setUseColumnNamesInFindColumn(boolean paramBoolean);
  
  boolean getUseLocalTransactionState();
  
  void setUseLocalTransactionState(boolean paramBoolean);
  
  boolean getCompensateOnDuplicateKeyUpdateCounts();
  
  void setCompensateOnDuplicateKeyUpdateCounts(boolean paramBoolean);
  
  void setUseAffectedRows(boolean paramBoolean);
  
  boolean getUseAffectedRows();
  
  void setPasswordCharacterEncoding(String paramString);
  
  String getPasswordCharacterEncoding();
  
  int getLoadBalanceBlacklistTimeout();
  
  void setLoadBalanceBlacklistTimeout(int paramInt);
  
  void setRetriesAllDown(int paramInt);
  
  int getRetriesAllDown();
  
  ExceptionInterceptor getExceptionInterceptor();
  
  void setExceptionInterceptors(String paramString);
  
  String getExceptionInterceptors();
  
  boolean getQueryTimeoutKillsConnection();
  
  void setQueryTimeoutKillsConnection(boolean paramBoolean);
  
  int getMaxAllowedPacket();
  
  boolean getRetainStatementAfterResultSetClose();
  
  int getLoadBalancePingTimeout();
  
  void setLoadBalancePingTimeout(int paramInt);
  
  boolean getLoadBalanceValidateConnectionOnSwapServer();
  
  void setLoadBalanceValidateConnectionOnSwapServer(boolean paramBoolean);
  
  String getLoadBalanceConnectionGroup();
  
  void setLoadBalanceConnectionGroup(String paramString);
  
  String getLoadBalanceExceptionChecker();
  
  void setLoadBalanceExceptionChecker(String paramString);
  
  String getLoadBalanceSQLStateFailover();
  
  void setLoadBalanceSQLStateFailover(String paramString);
  
  String getLoadBalanceSQLExceptionSubclassFailover();
  
  void setLoadBalanceSQLExceptionSubclassFailover(String paramString);
  
  boolean getLoadBalanceEnableJMX();
  
  void setLoadBalanceEnableJMX(boolean paramBoolean);
  
  void setLoadBalanceAutoCommitStatementThreshold(int paramInt);
  
  int getLoadBalanceAutoCommitStatementThreshold();
  
  void setLoadBalanceAutoCommitStatementRegex(String paramString);
  
  String getLoadBalanceAutoCommitStatementRegex();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\ConnectionProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */