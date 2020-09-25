/*      */ package com.avaje.ebean.config;
/*      */ 
/*      */ import com.avaje.ebean.LogLevel;
/*      */ import com.avaje.ebean.cache.ServerCacheFactory;
/*      */ import com.avaje.ebean.cache.ServerCacheManager;
/*      */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*      */ import com.avaje.ebean.config.dbplatform.DbEncrypt;
/*      */ import com.avaje.ebean.config.ldap.LdapConfig;
/*      */ import com.avaje.ebean.config.ldap.LdapContextFactory;
/*      */ import com.avaje.ebean.event.BeanPersistController;
/*      */ import com.avaje.ebean.event.BeanPersistListener;
/*      */ import com.avaje.ebean.event.BeanQueryAdapter;
/*      */ import com.avaje.ebean.event.BulkTableEventListener;
/*      */ import com.avaje.ebean.event.ServerConfigStartup;
/*      */ import com.avaje.ebean.event.TransactionEventListener;
/*      */ import com.avaje.ebeaninternal.api.ClassUtil;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ServerConfig
/*      */ {
/*      */   private static final int DEFAULT_QUERY_BATCH_SIZE = 100;
/*      */   private String name;
/*      */   private String resourceDirectory;
/*      */   private int enhanceLogLevel;
/*      */   private boolean register = true;
/*      */   private boolean defaultServer;
/*      */   private boolean validateOnSave = true;
/*  129 */   private List<Class<?>> classes = new ArrayList<Class<?>>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  135 */   private List<String> packages = new ArrayList<String>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  141 */   private List<String> searchJars = new ArrayList<String>();
/*      */ 
/*      */   
/*  144 */   private AutofetchConfig autofetchConfig = new AutofetchConfig();
/*      */ 
/*      */ 
/*      */   
/*      */   private String databasePlatformName;
/*      */ 
/*      */ 
/*      */   
/*      */   private DatabasePlatform databasePlatform;
/*      */ 
/*      */   
/*  155 */   private int databaseSequenceBatchSize = 20;
/*      */   
/*      */   private boolean persistBatching;
/*      */   
/*  159 */   private int persistBatchSize = 20;
/*      */ 
/*      */   
/*  162 */   private int lazyLoadBatchSize = 1;
/*      */ 
/*      */   
/*  165 */   private int queryBatchSize = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ddlGenerate;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ddlRun;
/*      */ 
/*      */   
/*      */   private boolean debugSql;
/*      */ 
/*      */   
/*      */   private boolean debugLazyLoad;
/*      */ 
/*      */   
/*      */   private boolean useJtaTransactionManager;
/*      */ 
/*      */   
/*      */   private ExternalTransactionManager externalTransactionManager;
/*      */ 
/*      */   
/*      */   private boolean loggingToJavaLogger;
/*      */ 
/*      */   
/*  191 */   private String loggingDirectory = "logs";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  196 */   private LogLevel loggingLevel = LogLevel.NONE;
/*      */ 
/*      */ 
/*      */   
/*      */   private PstmtDelegate pstmtDelegate;
/*      */ 
/*      */ 
/*      */   
/*      */   private DataSource dataSource;
/*      */ 
/*      */   
/*  207 */   private DataSourceConfig dataSourceConfig = new DataSourceConfig();
/*      */ 
/*      */   
/*      */   private String dataSourceJndiName;
/*      */ 
/*      */   
/*      */   private String databaseBooleanTrue;
/*      */ 
/*      */   
/*      */   private String databaseBooleanFalse;
/*      */ 
/*      */   
/*      */   private NamingConvention namingConvention;
/*      */ 
/*      */   
/*      */   private boolean updateChangesOnly = true;
/*      */   
/*  224 */   private List<BeanPersistController> persistControllers = new ArrayList<BeanPersistController>();
/*  225 */   private List<BeanPersistListener<?>> persistListeners = new ArrayList<BeanPersistListener<?>>();
/*  226 */   private List<BeanQueryAdapter> queryAdapters = new ArrayList<BeanQueryAdapter>();
/*  227 */   private List<BulkTableEventListener> bulkTableEventListeners = new ArrayList<BulkTableEventListener>();
/*  228 */   private List<ServerConfigStartup> configStartupListeners = new ArrayList<ServerConfigStartup>();
/*  229 */   private List<TransactionEventListener> transactionEventListeners = new ArrayList<TransactionEventListener>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EncryptKeyManager encryptKeyManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private EncryptDeployManager encryptDeployManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private Encryptor encryptor;
/*      */ 
/*      */ 
/*      */   
/*      */   private DbEncrypt dbEncrypt;
/*      */ 
/*      */ 
/*      */   
/*      */   private LdapConfig ldapConfig;
/*      */ 
/*      */ 
/*      */   
/*      */   private ServerCacheFactory serverCacheFactory;
/*      */ 
/*      */ 
/*      */   
/*      */   private ServerCacheManager serverCacheManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean vanillaMode;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean vanillaRefMode;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean allowSubclassing = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  276 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setName(String name) {
/*  283 */     this.name = name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRegister() {
/*  294 */     return this.register;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRegister(boolean register) {
/*  305 */     this.register = register;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDefaultServer() {
/*  316 */     return this.defaultServer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultServer(boolean defaultServer) {
/*  327 */     this.defaultServer = defaultServer;
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
/*      */   public boolean isPersistBatching() {
/*  339 */     return this.persistBatching;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsePersistBatching() {
/*  348 */     return this.persistBatching;
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
/*      */   public void setPersistBatching(boolean persistBatching) {
/*  360 */     this.persistBatching = persistBatching;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUsePersistBatching(boolean persistBatching) {
/*  369 */     this.persistBatching = persistBatching;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPersistBatchSize() {
/*  376 */     return this.persistBatchSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPersistBatchSize(int persistBatchSize) {
/*  383 */     this.persistBatchSize = persistBatchSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLazyLoadBatchSize() {
/*  390 */     return this.lazyLoadBatchSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getQueryBatchSize() {
/*  399 */     return this.queryBatchSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQueryBatchSize(int queryBatchSize) {
/*  409 */     this.queryBatchSize = queryBatchSize;
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
/*      */   public void setLazyLoadBatchSize(int lazyLoadBatchSize) {
/*  427 */     this.lazyLoadBatchSize = lazyLoadBatchSize;
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
/*      */   public void setDatabaseSequenceBatchSize(int databaseSequenceBatchSize) {
/*  439 */     this.databaseSequenceBatchSize = databaseSequenceBatchSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUseJtaTransactionManager() {
/*  446 */     return this.useJtaTransactionManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseJtaTransactionManager(boolean useJtaTransactionManager) {
/*  453 */     this.useJtaTransactionManager = useJtaTransactionManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExternalTransactionManager getExternalTransactionManager() {
/*  460 */     return this.externalTransactionManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExternalTransactionManager(ExternalTransactionManager externalTransactionManager) {
/*  467 */     this.externalTransactionManager = externalTransactionManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ServerCacheFactory getServerCacheFactory() {
/*  474 */     return this.serverCacheFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerCacheFactory(ServerCacheFactory serverCacheFactory) {
/*  481 */     this.serverCacheFactory = serverCacheFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ServerCacheManager getServerCacheManager() {
/*  488 */     return this.serverCacheManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerCacheManager(ServerCacheManager serverCacheManager) {
/*  495 */     this.serverCacheManager = serverCacheManager;
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
/*      */   public boolean isVanillaMode() {
/*  507 */     return this.vanillaMode;
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
/*      */   public void setVanillaMode(boolean vanillaMode) {
/*  526 */     this.vanillaMode = vanillaMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVanillaRefMode() {
/*  537 */     return this.vanillaRefMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVanillaRefMode(boolean vanillaRefMode) {
/*  545 */     this.vanillaRefMode = vanillaRefMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValidateOnSave() {
/*  552 */     return this.validateOnSave;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValidateOnSave(boolean validateOnSave) {
/*  559 */     this.validateOnSave = validateOnSave;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEnhanceLogLevel() {
/*  566 */     return this.enhanceLogLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnhanceLogLevel(int enhanceLogLevel) {
/*  573 */     this.enhanceLogLevel = enhanceLogLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NamingConvention getNamingConvention() {
/*  583 */     return this.namingConvention;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNamingConvention(NamingConvention namingConvention) {
/*  593 */     this.namingConvention = namingConvention;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AutofetchConfig getAutofetchConfig() {
/*  600 */     return this.autofetchConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutofetchConfig(AutofetchConfig autofetchConfig) {
/*  607 */     this.autofetchConfig = autofetchConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PstmtDelegate getPstmtDelegate() {
/*  614 */     return this.pstmtDelegate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPstmtDelegate(PstmtDelegate pstmtDelegate) {
/*  625 */     this.pstmtDelegate = pstmtDelegate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DataSource getDataSource() {
/*  632 */     return this.dataSource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataSource(DataSource dataSource) {
/*  639 */     this.dataSource = dataSource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DataSourceConfig getDataSourceConfig() {
/*  647 */     return this.dataSourceConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
/*  655 */     this.dataSourceConfig = dataSourceConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDataSourceJndiName() {
/*  662 */     return this.dataSourceJndiName;
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
/*      */   public void setDataSourceJndiName(String dataSourceJndiName) {
/*  674 */     this.dataSourceJndiName = dataSourceJndiName;
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
/*      */   public String getDatabaseBooleanTrue() {
/*  687 */     return this.databaseBooleanTrue;
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
/*      */   public void setDatabaseBooleanTrue(String databaseTrue) {
/*  700 */     this.databaseBooleanTrue = databaseTrue;
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
/*      */   public String getDatabaseBooleanFalse() {
/*  713 */     return this.databaseBooleanFalse;
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
/*      */   public void setDatabaseBooleanFalse(String databaseFalse) {
/*  726 */     this.databaseBooleanFalse = databaseFalse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDatabaseSequenceBatchSize() {
/*  733 */     return this.databaseSequenceBatchSize;
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
/*      */   public void setDatabaseSequenceBatch(int databaseSequenceBatchSize) {
/*  752 */     this.databaseSequenceBatchSize = databaseSequenceBatchSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDatabasePlatformName() {
/*  763 */     return this.databasePlatformName;
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
/*      */   public void setDatabasePlatformName(String databasePlatformName) {
/*  785 */     this.databasePlatformName = databasePlatformName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DatabasePlatform getDatabasePlatform() {
/*  792 */     return this.databasePlatform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDatabasePlatform(DatabasePlatform databasePlatform) {
/*  803 */     this.databasePlatform = databasePlatform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptKeyManager getEncryptKeyManager() {
/*  810 */     return this.encryptKeyManager;
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
/*      */   public void setEncryptKeyManager(EncryptKeyManager encryptKeyManager) {
/*  829 */     this.encryptKeyManager = encryptKeyManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptDeployManager getEncryptDeployManager() {
/*  840 */     return this.encryptDeployManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEncryptDeployManager(EncryptDeployManager encryptDeployManager) {
/*  851 */     this.encryptDeployManager = encryptDeployManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Encryptor getEncryptor() {
/*  859 */     return this.encryptor;
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
/*      */   public void setEncryptor(Encryptor encryptor) {
/*  871 */     this.encryptor = encryptor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DbEncrypt getDbEncrypt() {
/*  882 */     return this.dbEncrypt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDbEncrypt(DbEncrypt dbEncrypt) {
/*  893 */     this.dbEncrypt = dbEncrypt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDebugSql() {
/*  904 */     return this.debugSql;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDebugSql(boolean debugSql) {
/*  915 */     this.debugSql = debugSql;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDebugLazyLoad() {
/*  922 */     return this.debugLazyLoad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDebugLazyLoad(boolean debugLazyLoad) {
/*  929 */     this.debugLazyLoad = debugLazyLoad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LogLevel getLoggingLevel() {
/*  939 */     return this.loggingLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoggingLevel(LogLevel logLevel) {
/*  949 */     this.loggingLevel = logLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLoggingDirectory() {
/*  956 */     return this.loggingDirectory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLoggingDirectoryWithEval() {
/*  964 */     return GlobalProperties.evaluateExpressions(this.loggingDirectory);
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
/*      */   public void setLoggingDirectory(String loggingDirectory) {
/*  985 */     this.loggingDirectory = loggingDirectory;
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
/*      */   public boolean isLoggingToJavaLogger() {
/*  997 */     return this.loggingToJavaLogger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoggingToJavaLogger(boolean transactionLogToJavaLogger) {
/* 1006 */     this.loggingToJavaLogger = transactionLogToJavaLogger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUseJuliTransactionLogger() {
/* 1015 */     return isLoggingToJavaLogger();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseJuliTransactionLogger(boolean transactionLogToJavaLogger) {
/* 1024 */     setLoggingToJavaLogger(transactionLogToJavaLogger);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDdlGenerate(boolean ddlGenerate) {
/* 1031 */     this.ddlGenerate = ddlGenerate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDdlRun(boolean ddlRun) {
/* 1038 */     this.ddlRun = ddlRun;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDdlGenerate() {
/* 1045 */     return this.ddlGenerate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDdlRun() {
/* 1052 */     return this.ddlRun;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LdapConfig getLdapConfig() {
/* 1059 */     return this.ldapConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLdapConfig(LdapConfig ldapConfig) {
/* 1066 */     this.ldapConfig = ldapConfig;
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
/*      */   public void addClass(Class<?> cls) {
/* 1089 */     if (this.classes == null) {
/* 1090 */       this.classes = new ArrayList<Class<?>>();
/*      */     }
/* 1092 */     this.classes.add(cls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPackage(String packageName) {
/* 1102 */     if (this.packages == null) {
/* 1103 */       this.packages = new ArrayList<String>();
/*      */     }
/* 1105 */     this.packages.add(packageName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getPackages() {
/* 1115 */     return this.packages;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPackages(List<String> packages) {
/* 1125 */     this.packages = packages;
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
/*      */   public void addJar(String jarName) {
/* 1146 */     if (this.searchJars == null) {
/* 1147 */       this.searchJars = new ArrayList<String>();
/*      */     }
/* 1149 */     this.searchJars.add(jarName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getJars() {
/* 1159 */     return this.searchJars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setJars(List<String> searchJars) {
/* 1169 */     this.searchJars = searchJars;
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
/*      */   public void setClasses(List<Class<?>> classes) {
/* 1184 */     this.classes = classes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Class<?>> getClasses() {
/* 1192 */     return this.classes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUpdateChangesOnly() {
/* 1199 */     return this.updateChangesOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUpdateChangesOnly(boolean updateChangesOnly) {
/* 1206 */     this.updateChangesOnly = updateChangesOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowSubclassing(boolean allowSubclassing) {
/* 1214 */     this.allowSubclassing = allowSubclassing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAllowSubclassing() {
/* 1221 */     return this.allowSubclassing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getResourceDirectory() {
/* 1228 */     return this.resourceDirectory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResourceDirectory(String resourceDirectory) {
/* 1235 */     this.resourceDirectory = resourceDirectory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(BeanQueryAdapter beanQueryAdapter) {
/* 1246 */     this.queryAdapters.add(beanQueryAdapter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<BeanQueryAdapter> getQueryAdapters() {
/* 1253 */     return this.queryAdapters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQueryAdapters(List<BeanQueryAdapter> queryAdapters) {
/* 1264 */     this.queryAdapters = queryAdapters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(BeanPersistController beanPersistController) {
/* 1275 */     this.persistControllers.add(beanPersistController);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<BeanPersistController> getPersistControllers() {
/* 1282 */     return this.persistControllers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPersistControllers(List<BeanPersistController> persistControllers) {
/* 1293 */     this.persistControllers = persistControllers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(TransactionEventListener listener) {
/* 1304 */     this.transactionEventListeners.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<TransactionEventListener> getTransactionEventListeners() {
/* 1311 */     return this.transactionEventListeners;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransactionEventListeners(List<TransactionEventListener> transactionEventListeners) {
/* 1322 */     this.transactionEventListeners = transactionEventListeners;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(BeanPersistListener<?> beanPersistListener) {
/* 1333 */     this.persistListeners.add(beanPersistListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<BeanPersistListener<?>> getPersistListeners() {
/* 1340 */     return this.persistListeners;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(BulkTableEventListener bulkTableEventListener) {
/* 1347 */     this.bulkTableEventListeners.add(bulkTableEventListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<BulkTableEventListener> getBulkTableEventListeners() {
/* 1354 */     return this.bulkTableEventListeners;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addServerConfigStartup(ServerConfigStartup configStartupListener) {
/* 1361 */     this.configStartupListeners.add(configStartupListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ServerConfigStartup> getServerConfigStartupListeners() {
/* 1368 */     return this.configStartupListeners;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPersistListeners(List<BeanPersistListener<?>> persistListeners) {
/* 1379 */     this.persistListeners = persistListeners;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadFromProperties() {
/* 1386 */     ConfigPropertyMap p = new ConfigPropertyMap(this.name);
/* 1387 */     loadSettings(p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GlobalProperties.PropertySource getPropertySource() {
/* 1394 */     return GlobalProperties.getPropertySource(this.name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProperty(String propertyName, String defaultValue) {
/* 1401 */     GlobalProperties.PropertySource p = new ConfigPropertyMap(this.name);
/* 1402 */     return p.get(propertyName, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProperty(String propertyName) {
/* 1409 */     return getProperty(propertyName, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> T createInstance(GlobalProperties.PropertySource p, Class<T> type, String key) {
/* 1415 */     String classname = p.get(key, null);
/* 1416 */     if (classname == null) {
/* 1417 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1421 */       Class<?> cls = ClassUtil.forName(classname, getClass());
/* 1422 */       return (T)cls.newInstance();
/* 1423 */     } catch (Exception e) {
/* 1424 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadSettings(ConfigPropertyMap p) {
/* 1433 */     if (this.autofetchConfig == null) {
/* 1434 */       this.autofetchConfig = new AutofetchConfig();
/*      */     }
/* 1436 */     this.autofetchConfig.loadSettings(p);
/* 1437 */     if (this.dataSourceConfig == null) {
/* 1438 */       this.dataSourceConfig = new DataSourceConfig();
/*      */     }
/* 1440 */     this.dataSourceConfig.loadSettings(p.getServerName());
/*      */     
/* 1442 */     if (this.ldapConfig == null) {
/* 1443 */       LdapContextFactory ctxFact = createInstance(p, LdapContextFactory.class, "ldapContextFactory");
/* 1444 */       if (ctxFact != null) {
/* 1445 */         this.ldapConfig = new LdapConfig();
/* 1446 */         this.ldapConfig.setContextFactory(ctxFact);
/* 1447 */         this.ldapConfig.setVanillaMode(p.getBoolean("ldapVanillaMode", false));
/*      */       } 
/*      */     } 
/*      */     
/* 1451 */     this.useJtaTransactionManager = p.getBoolean("useJtaTransactionManager", false);
/* 1452 */     this.namingConvention = createNamingConvention(p);
/* 1453 */     this.databasePlatform = createInstance(p, DatabasePlatform.class, "databasePlatform");
/* 1454 */     this.encryptKeyManager = createInstance(p, EncryptKeyManager.class, "encryptKeyManager");
/* 1455 */     this.encryptDeployManager = createInstance(p, EncryptDeployManager.class, "encryptDeployManager");
/* 1456 */     this.encryptor = createInstance(p, Encryptor.class, "encryptor");
/* 1457 */     this.dbEncrypt = createInstance(p, DbEncrypt.class, "dbEncrypt");
/* 1458 */     this.serverCacheFactory = createInstance(p, ServerCacheFactory.class, "serverCacheFactory");
/* 1459 */     this.serverCacheManager = createInstance(p, ServerCacheManager.class, "serverCacheManager");
/*      */     
/* 1461 */     String jarsProp = p.get("search.jars", p.get("jars", null));
/* 1462 */     if (jarsProp != null) {
/* 1463 */       this.searchJars = getSearchJarsPackages(jarsProp);
/*      */     }
/*      */     
/* 1466 */     String packagesProp = p.get("search.packages", p.get("packages", null));
/* 1467 */     if (this.packages != null) {
/* 1468 */       this.packages = getSearchJarsPackages(packagesProp);
/*      */     }
/*      */     
/* 1471 */     this.allowSubclassing = p.getBoolean("allowSubclassing", true);
/* 1472 */     this.validateOnSave = p.getBoolean("validateOnSave", true);
/* 1473 */     this.vanillaMode = p.getBoolean("vanillaMode", false);
/* 1474 */     this.vanillaRefMode = p.getBoolean("vanillaRefMode", false);
/* 1475 */     this.updateChangesOnly = p.getBoolean("updateChangesOnly", true);
/*      */     
/* 1477 */     boolean batchMode = p.getBoolean("batch.mode", false);
/* 1478 */     this.persistBatching = p.getBoolean("persistBatching", batchMode);
/*      */     
/* 1480 */     int batchSize = p.getInt("batch.size", 20);
/* 1481 */     this.persistBatchSize = p.getInt("persistBatchSize", batchSize);
/*      */     
/* 1483 */     this.dataSourceJndiName = p.get("dataSourceJndiName", null);
/* 1484 */     this.databaseSequenceBatchSize = p.getInt("databaseSequenceBatchSize", 20);
/* 1485 */     this.databaseBooleanTrue = p.get("databaseBooleanTrue", null);
/* 1486 */     this.databaseBooleanFalse = p.get("databaseBooleanFalse", null);
/* 1487 */     this.databasePlatformName = p.get("databasePlatformName", null);
/*      */     
/* 1489 */     this.lazyLoadBatchSize = p.getInt("lazyLoadBatchSize", 1);
/* 1490 */     this.queryBatchSize = p.getInt("queryBatchSize", 100);
/*      */     
/* 1492 */     this.ddlGenerate = p.getBoolean("ddl.generate", false);
/* 1493 */     this.ddlRun = p.getBoolean("ddl.run", false);
/* 1494 */     this.debugSql = p.getBoolean("debug.sql", false);
/* 1495 */     this.debugLazyLoad = p.getBoolean("debug.lazyload", false);
/*      */     
/* 1497 */     this.loggingLevel = getLogLevelValue(p);
/*      */     
/* 1499 */     String s = p.get("useJuliTransactionLogger", null);
/* 1500 */     s = p.get("loggingToJavaLogger", s);
/* 1501 */     this.loggingToJavaLogger = "true".equalsIgnoreCase(s);
/*      */     
/* 1503 */     s = p.get("log.directory", "logs");
/* 1504 */     this.loggingDirectory = p.get("logging.directory", s);
/*      */     
/* 1506 */     this.classes = getClasses(p);
/*      */   }
/*      */ 
/*      */   
/*      */   private LogLevel getLogLevelValue(ConfigPropertyMap p) {
/* 1511 */     String logValue = p.get("logging", "NONE");
/* 1512 */     logValue = p.get("log.level", logValue);
/* 1513 */     logValue = p.get("logging.level", logValue);
/* 1514 */     if (logValue.trim().equalsIgnoreCase("ALL")) {
/* 1515 */       logValue = "SQL";
/*      */     }
/* 1517 */     return Enum.<LogLevel>valueOf(LogLevel.class, logValue.toUpperCase());
/*      */   }
/*      */ 
/*      */   
/*      */   private NamingConvention createNamingConvention(GlobalProperties.PropertySource p) {
/* 1522 */     NamingConvention nc = createInstance(p, NamingConvention.class, "namingconvention");
/* 1523 */     if (nc == null) {
/* 1524 */       return null;
/*      */     }
/* 1526 */     if (nc instanceof AbstractNamingConvention) {
/* 1527 */       AbstractNamingConvention anc = (AbstractNamingConvention)nc;
/* 1528 */       String v = p.get("namingConvention.useForeignKeyPrefix", null);
/* 1529 */       if (v != null) {
/* 1530 */         boolean useForeignKeyPrefix = Boolean.valueOf(v).booleanValue();
/* 1531 */         anc.setUseForeignKeyPrefix(useForeignKeyPrefix);
/*      */       } 
/*      */       
/* 1534 */       String sequenceFormat = p.get("namingConvention.sequenceFormat", null);
/* 1535 */       if (sequenceFormat != null) {
/* 1536 */         anc.setSequenceFormat(sequenceFormat);
/*      */       }
/*      */     } 
/* 1539 */     return nc;
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
/*      */   private ArrayList<Class<?>> getClasses(GlobalProperties.PropertySource p) {
/* 1552 */     String classNames = p.get("classes", null);
/* 1553 */     if (classNames == null)
/*      */     {
/* 1555 */       return null;
/*      */     }
/*      */     
/* 1558 */     ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
/*      */     
/* 1560 */     String[] split = classNames.split("[ ,;]");
/* 1561 */     for (int i = 0; i < split.length; i++) {
/* 1562 */       String cn = split[i].trim();
/* 1563 */       if (cn.length() > 0 && !"class".equalsIgnoreCase(cn)) {
/*      */         try {
/* 1565 */           classes.add(ClassUtil.forName(cn, getClass()));
/* 1566 */         } catch (ClassNotFoundException e) {
/* 1567 */           String msg = "Error registering class [" + cn + "] from [" + classNames + "]";
/* 1568 */           throw new RuntimeException(msg, e);
/*      */         } 
/*      */       }
/*      */     } 
/* 1572 */     return classes;
/*      */   }
/*      */ 
/*      */   
/*      */   private List<String> getSearchJarsPackages(String searchPackages) {
/* 1577 */     List<String> hitList = new ArrayList<String>();
/*      */     
/* 1579 */     if (searchPackages != null) {
/*      */       
/* 1581 */       String[] entries = searchPackages.split("[ ,;]");
/* 1582 */       for (int i = 0; i < entries.length; i++) {
/* 1583 */         hitList.add(entries[i].trim());
/*      */       }
/*      */     } 
/* 1586 */     return hitList;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\ServerConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */