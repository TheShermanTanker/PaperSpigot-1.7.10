/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.cache.ServerCacheManager;
/*     */ import com.avaje.ebean.config.ExternalTransactionManager;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.ldap.LdapConfig;
/*     */ import com.avaje.ebean.config.ldap.LdapContextFactory;
/*     */ import com.avaje.ebean.text.json.JsonContext;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.api.SpiBackgroundExecutor;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*     */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManagerFactory;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployOrmXml;
/*     */ import com.avaje.ebeaninternal.server.deploy.parse.DeployCreateProperties;
/*     */ import com.avaje.ebeaninternal.server.deploy.parse.DeployInherit;
/*     */ import com.avaje.ebeaninternal.server.deploy.parse.DeployUtil;
/*     */ import com.avaje.ebeaninternal.server.expression.DefaultExpressionFactory;
/*     */ import com.avaje.ebeaninternal.server.jmx.MAdminLogging;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import com.avaje.ebeaninternal.server.persist.DefaultPersister;
/*     */ import com.avaje.ebeaninternal.server.query.CQueryEngine;
/*     */ import com.avaje.ebeaninternal.server.query.DefaultOrmQueryEngine;
/*     */ import com.avaje.ebeaninternal.server.query.DefaultRelationalQueryEngine;
/*     */ import com.avaje.ebeaninternal.server.resource.ResourceManager;
/*     */ import com.avaje.ebeaninternal.server.resource.ResourceManagerFactory;
/*     */ import com.avaje.ebeaninternal.server.subclass.SubClassManager;
/*     */ import com.avaje.ebeaninternal.server.text.json.DJsonContext;
/*     */ import com.avaje.ebeaninternal.server.text.json.DefaultJsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.transaction.DefaultTransactionScopeManager;
/*     */ import com.avaje.ebeaninternal.server.transaction.ExternalTransactionScopeManager;
/*     */ import com.avaje.ebeaninternal.server.transaction.JtaTransactionManager;
/*     */ import com.avaje.ebeaninternal.server.transaction.TransactionManager;
/*     */ import com.avaje.ebeaninternal.server.transaction.TransactionScopeManager;
/*     */ import com.avaje.ebeaninternal.server.type.DefaultTypeManager;
/*     */ import com.avaje.ebeaninternal.server.type.TypeManager;
/*     */ import java.util.logging.Logger;
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
/*     */ public class InternalConfiguration
/*     */ {
/*  72 */   private static final Logger logger = Logger.getLogger(InternalConfiguration.class.getName());
/*     */   
/*     */   private final ServerConfig serverConfig;
/*     */   
/*     */   private final BootupClasses bootupClasses;
/*     */   
/*     */   private final SubClassManager subClassManager;
/*     */   
/*     */   private final DeployInherit deployInherit;
/*     */   
/*     */   private final ResourceManager resourceManager;
/*     */   
/*     */   private final DeployOrmXml deployOrmXml;
/*     */   
/*     */   private final TypeManager typeManager;
/*     */   
/*     */   private final Binder binder;
/*     */   
/*     */   private final DeployCreateProperties deployCreateProperties;
/*     */   
/*     */   private final DeployUtil deployUtil;
/*     */   
/*     */   private final BeanDescriptorManager beanDescriptorManager;
/*     */   
/*     */   private final MAdminLogging logControl;
/*     */   
/*     */   private final DebugLazyLoad debugLazyLoad;
/*     */   
/*     */   private final TransactionManager transactionManager;
/*     */   
/*     */   private final TransactionScopeManager transactionScopeManager;
/*     */   
/*     */   private final CQueryEngine cQueryEngine;
/*     */   
/*     */   private final ClusterManager clusterManager;
/*     */   
/*     */   private final ServerCacheManager cacheManager;
/*     */   
/*     */   private final ExpressionFactory expressionFactory;
/*     */   
/*     */   private final SpiBackgroundExecutor backgroundExecutor;
/*     */   
/*     */   private final PstmtBatch pstmtBatch;
/*     */   
/*     */   private final XmlConfig xmlConfig;
/*     */ 
/*     */   
/*     */   public InternalConfiguration(XmlConfig xmlConfig, ClusterManager clusterManager, ServerCacheManager cacheManager, SpiBackgroundExecutor backgroundExecutor, ServerConfig serverConfig, BootupClasses bootupClasses, PstmtBatch pstmtBatch) {
/*     */     JtaTransactionManager jtaTransactionManager;
/* 121 */     this.xmlConfig = xmlConfig;
/* 122 */     this.pstmtBatch = pstmtBatch;
/* 123 */     this.clusterManager = clusterManager;
/* 124 */     this.backgroundExecutor = backgroundExecutor;
/* 125 */     this.cacheManager = cacheManager;
/* 126 */     this.serverConfig = serverConfig;
/* 127 */     this.bootupClasses = bootupClasses;
/* 128 */     this.expressionFactory = (ExpressionFactory)new DefaultExpressionFactory();
/*     */     
/* 130 */     this.subClassManager = new SubClassManager(serverConfig);
/*     */     
/* 132 */     this.typeManager = (TypeManager)new DefaultTypeManager(serverConfig, bootupClasses);
/* 133 */     this.binder = new Binder(this.typeManager);
/*     */     
/* 135 */     this.resourceManager = ResourceManagerFactory.createResourceManager(serverConfig);
/* 136 */     this.deployOrmXml = new DeployOrmXml(this.resourceManager.getResourceSource());
/* 137 */     this.deployInherit = new DeployInherit(bootupClasses);
/*     */     
/* 139 */     this.deployCreateProperties = new DeployCreateProperties(this.typeManager);
/* 140 */     this.deployUtil = new DeployUtil(this.typeManager, serverConfig);
/*     */     
/* 142 */     this.beanDescriptorManager = new BeanDescriptorManager(this);
/* 143 */     this.beanDescriptorManager.deploy();
/*     */     
/* 145 */     this.debugLazyLoad = new DebugLazyLoad(serverConfig.isDebugLazyLoad());
/*     */     
/* 147 */     this.transactionManager = new TransactionManager(clusterManager, (BackgroundExecutor)backgroundExecutor, serverConfig, this.beanDescriptorManager, getBootupClasses());
/*     */ 
/*     */     
/* 150 */     this.logControl = new MAdminLogging(serverConfig, this.transactionManager);
/*     */     
/* 152 */     this.cQueryEngine = new CQueryEngine(serverConfig.getDatabasePlatform(), this.logControl, this.binder, (BackgroundExecutor)backgroundExecutor);
/*     */     
/* 154 */     ExternalTransactionManager externalTransactionManager = serverConfig.getExternalTransactionManager();
/* 155 */     if (externalTransactionManager == null && serverConfig.isUseJtaTransactionManager()) {
/* 156 */       jtaTransactionManager = new JtaTransactionManager();
/*     */     }
/* 158 */     if (jtaTransactionManager != null) {
/* 159 */       jtaTransactionManager.setTransactionManager(this.transactionManager);
/* 160 */       this.transactionScopeManager = (TransactionScopeManager)new ExternalTransactionScopeManager(this.transactionManager, (ExternalTransactionManager)jtaTransactionManager);
/* 161 */       logger.info("Using Transaction Manager [" + jtaTransactionManager.getClass() + "]");
/*     */     } else {
/* 163 */       this.transactionScopeManager = (TransactionScopeManager)new DefaultTransactionScopeManager(this.transactionManager);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonContext createJsonContext(SpiEbeanServer server) {
/*     */     JsonValueAdapter jsonValueAdapter;
/* 170 */     String s = this.serverConfig.getProperty("json.pretty", "false");
/* 171 */     boolean dfltPretty = "true".equalsIgnoreCase(s);
/*     */     
/* 173 */     s = this.serverConfig.getProperty("json.jsonValueAdapter", null);
/*     */     
/* 175 */     DefaultJsonValueAdapter defaultJsonValueAdapter = new DefaultJsonValueAdapter();
/* 176 */     if (s != null) {
/* 177 */       jsonValueAdapter = (JsonValueAdapter)ClassUtil.newInstance(s, getClass());
/*     */     }
/* 179 */     return (JsonContext)new DJsonContext(server, jsonValueAdapter, dfltPretty);
/*     */   }
/*     */   
/*     */   public XmlConfig getXmlConfig() {
/* 183 */     return this.xmlConfig;
/*     */   }
/*     */   
/*     */   public AutoFetchManager createAutoFetchManager(SpiEbeanServer server) {
/* 187 */     return AutoFetchManagerFactory.create(server, this.serverConfig, this.resourceManager);
/*     */   }
/*     */   
/*     */   public RelationalQueryEngine createRelationalQueryEngine() {
/* 191 */     return (RelationalQueryEngine)new DefaultRelationalQueryEngine(this.logControl, this.binder, this.serverConfig.getDatabaseBooleanTrue());
/*     */   }
/*     */   
/*     */   public OrmQueryEngine createOrmQueryEngine() {
/* 195 */     return (OrmQueryEngine)new DefaultOrmQueryEngine(this.beanDescriptorManager, this.cQueryEngine);
/*     */   }
/*     */   
/*     */   public Persister createPersister(SpiEbeanServer server) {
/* 199 */     LdapContextFactory ldapCtxFactory = null;
/* 200 */     LdapConfig ldapConfig = this.serverConfig.getLdapConfig();
/* 201 */     if (ldapConfig != null) {
/* 202 */       ldapCtxFactory = ldapConfig.getContextFactory();
/*     */     }
/* 204 */     return (Persister)new DefaultPersister(server, this.serverConfig.isValidateOnSave(), this.binder, this.beanDescriptorManager, this.pstmtBatch, ldapCtxFactory);
/*     */   }
/*     */   
/*     */   public PstmtBatch getPstmtBatch() {
/* 208 */     return this.pstmtBatch;
/*     */   }
/*     */   
/*     */   public ServerCacheManager getCacheManager() {
/* 212 */     return this.cacheManager;
/*     */   }
/*     */   
/*     */   public BootupClasses getBootupClasses() {
/* 216 */     return this.bootupClasses;
/*     */   }
/*     */   
/*     */   public DatabasePlatform getDatabasePlatform() {
/* 220 */     return this.serverConfig.getDatabasePlatform();
/*     */   }
/*     */   
/*     */   public ServerConfig getServerConfig() {
/* 224 */     return this.serverConfig;
/*     */   }
/*     */   
/*     */   public ExpressionFactory getExpressionFactory() {
/* 228 */     return this.expressionFactory;
/*     */   }
/*     */   
/*     */   public TypeManager getTypeManager() {
/* 232 */     return this.typeManager;
/*     */   }
/*     */   
/*     */   public Binder getBinder() {
/* 236 */     return this.binder;
/*     */   }
/*     */   
/*     */   public BeanDescriptorManager getBeanDescriptorManager() {
/* 240 */     return this.beanDescriptorManager;
/*     */   }
/*     */   
/*     */   public SubClassManager getSubClassManager() {
/* 244 */     return this.subClassManager;
/*     */   }
/*     */   
/*     */   public DeployInherit getDeployInherit() {
/* 248 */     return this.deployInherit;
/*     */   }
/*     */   
/*     */   public ResourceManager getResourceManager() {
/* 252 */     return this.resourceManager;
/*     */   }
/*     */   
/*     */   public DeployOrmXml getDeployOrmXml() {
/* 256 */     return this.deployOrmXml;
/*     */   }
/*     */   
/*     */   public DeployCreateProperties getDeployCreateProperties() {
/* 260 */     return this.deployCreateProperties;
/*     */   }
/*     */   
/*     */   public DeployUtil getDeployUtil() {
/* 264 */     return this.deployUtil;
/*     */   }
/*     */   
/*     */   public MAdminLogging getLogControl() {
/* 268 */     return this.logControl;
/*     */   }
/*     */   
/*     */   public TransactionManager getTransactionManager() {
/* 272 */     return this.transactionManager;
/*     */   }
/*     */   
/*     */   public TransactionScopeManager getTransactionScopeManager() {
/* 276 */     return this.transactionScopeManager;
/*     */   }
/*     */   
/*     */   public CQueryEngine getCQueryEngine() {
/* 280 */     return this.cQueryEngine;
/*     */   }
/*     */   
/*     */   public ClusterManager getClusterManager() {
/* 284 */     return this.clusterManager;
/*     */   }
/*     */   
/*     */   public DebugLazyLoad getDebugLazyLoad() {
/* 288 */     return this.debugLazyLoad;
/*     */   }
/*     */   
/*     */   public SpiBackgroundExecutor getBackgroundExecutor() {
/* 292 */     return this.backgroundExecutor;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\InternalConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */