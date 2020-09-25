/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.cache.ServerCacheFactory;
/*     */ import com.avaje.ebean.cache.ServerCacheManager;
/*     */ import com.avaje.ebean.cache.ServerCacheOptions;
/*     */ import com.avaje.ebean.common.BootupEbeanManager;
/*     */ import com.avaje.ebean.config.DataSourceConfig;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.config.NamingConvention;
/*     */ import com.avaje.ebean.config.PstmtDelegate;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.config.UnderscoreNamingConvention;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebeaninternal.api.SpiBackgroundExecutor;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.cache.DefaultServerCacheFactory;
/*     */ import com.avaje.ebeaninternal.server.cache.DefaultServerCacheManager;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.jdbc.OraclePstmtBatch;
/*     */ import com.avaje.ebeaninternal.server.jdbc.StandardPstmtDelegate;
/*     */ import com.avaje.ebeaninternal.server.lib.ShutdownManager;
/*     */ import com.avaje.ebeaninternal.server.lib.sql.DataSourceGlobalManager;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MBeanServerFactory;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.sql.DataSource;
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
/*     */ public class DefaultServerFactory
/*     */   implements BootupEbeanManager
/*     */ {
/*  65 */   private static final Logger logger = Logger.getLogger(DefaultServerFactory.class.getName());
/*     */   
/*     */   private final ClusterManager clusterManager;
/*     */   
/*     */   private final JndiDataSourceLookup jndiDataSourceFactory;
/*     */   
/*     */   private final BootupClassPathSearch bootupClassSearch;
/*     */   
/*  73 */   private final AtomicInteger serverId = new AtomicInteger(1);
/*     */   
/*     */   private final XmlConfigLoader xmlConfigLoader;
/*     */   
/*     */   private final XmlConfig xmlConfig;
/*     */ 
/*     */   
/*     */   public DefaultServerFactory() {
/*  81 */     this.clusterManager = new ClusterManager();
/*  82 */     this.jndiDataSourceFactory = new JndiDataSourceLookup();
/*     */     
/*  84 */     List<String> packages = getSearchJarsPackages(GlobalProperties.get("ebean.search.packages", null));
/*  85 */     List<String> jars = getSearchJarsPackages(GlobalProperties.get("ebean.search.jars", null));
/*     */     
/*  87 */     this.bootupClassSearch = new BootupClassPathSearch(null, packages, jars);
/*  88 */     this.xmlConfigLoader = new XmlConfigLoader(null);
/*     */     
/*  90 */     this.xmlConfig = this.xmlConfigLoader.load();
/*     */ 
/*     */ 
/*     */     
/*  94 */     ShutdownManager.registerServerFactory(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> getSearchJarsPackages(String searchPackages) {
/*  99 */     List<String> hitList = new ArrayList<String>();
/*     */     
/* 101 */     if (searchPackages != null) {
/*     */       
/* 103 */       String[] entries = searchPackages.split("[ ,;]");
/* 104 */       for (int i = 0; i < entries.length; i++) {
/* 105 */         hitList.add(entries[i].trim());
/*     */       }
/*     */     } 
/* 108 */     return hitList;
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 112 */     this.clusterManager.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiEbeanServer createServer(String name) {
/* 120 */     ConfigBuilder b = new ConfigBuilder();
/* 121 */     ServerConfig config = b.build(name);
/*     */     
/* 123 */     return createServer(config);
/*     */   }
/*     */ 
/*     */   
/*     */   private SpiBackgroundExecutor createBackgroundExecutor(ServerConfig serverConfig, int uniqueServerId) {
/* 128 */     String namePrefix = "Ebean-" + serverConfig.getName();
/*     */ 
/*     */ 
/*     */     
/* 132 */     int schedulePoolSize = GlobalProperties.getInt("backgroundExecutor.schedulePoolsize", 1);
/*     */ 
/*     */     
/* 135 */     int minPoolSize = GlobalProperties.getInt("backgroundExecutor.minPoolSize", 1);
/* 136 */     int poolSize = GlobalProperties.getInt("backgroundExecutor.poolsize", 20);
/* 137 */     int maxPoolSize = GlobalProperties.getInt("backgroundExecutor.maxPoolSize", poolSize);
/*     */     
/* 139 */     int idleSecs = GlobalProperties.getInt("backgroundExecutor.idlesecs", 60);
/* 140 */     int shutdownSecs = GlobalProperties.getInt("backgroundExecutor.shutdownSecs", 30);
/*     */     
/* 142 */     boolean useTrad = GlobalProperties.getBoolean("backgroundExecutor.traditional", true);
/*     */     
/* 144 */     if (useTrad) {
/*     */ 
/*     */ 
/*     */       
/* 148 */       ThreadPool pool = ThreadPoolManager.getThreadPool(namePrefix);
/* 149 */       pool.setMinSize(minPoolSize);
/* 150 */       pool.setMaxSize(maxPoolSize);
/* 151 */       pool.setMaxIdleTime((idleSecs * 1000));
/* 152 */       return new TraditionalBackgroundExecutor(pool, schedulePoolSize, shutdownSecs, namePrefix);
/*     */     } 
/* 154 */     return new DefaultBackgroundExecutor(poolSize, schedulePoolSize, idleSecs, shutdownSecs, namePrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiEbeanServer createServer(ServerConfig serverConfig) {
/* 163 */     synchronized (this) {
/* 164 */       OraclePstmtBatch oraclePstmtBatch; MBeanServer mbeanServer; setNamingConvention(serverConfig);
/*     */       
/* 166 */       BootupClasses bootupClasses = getBootupClasses(serverConfig);
/*     */       
/* 168 */       setDataSource(serverConfig);
/*     */       
/* 170 */       boolean online = checkDataSource(serverConfig);
/*     */ 
/*     */       
/* 173 */       setDatabasePlatform(serverConfig);
/* 174 */       if (serverConfig.getDbEncrypt() != null)
/*     */       {
/* 176 */         serverConfig.getDatabasePlatform().setDbEncrypt(serverConfig.getDbEncrypt());
/*     */       }
/*     */       
/* 179 */       DatabasePlatform dbPlatform = serverConfig.getDatabasePlatform();
/*     */       
/* 181 */       PstmtBatch pstmtBatch = null;
/*     */       
/* 183 */       if (dbPlatform.getName().startsWith("oracle")) {
/* 184 */         PstmtDelegate pstmtDelegate = serverConfig.getPstmtDelegate();
/* 185 */         if (pstmtDelegate == null)
/*     */         {
/* 187 */           pstmtDelegate = getOraclePstmtDelegate(serverConfig.getDataSource());
/*     */         }
/* 189 */         if (pstmtDelegate != null)
/*     */         {
/*     */           
/* 192 */           oraclePstmtBatch = new OraclePstmtBatch(pstmtDelegate);
/*     */         }
/* 194 */         if (oraclePstmtBatch == null) {
/*     */           
/* 196 */           logger.warning("Can not support JDBC batching with Oracle without a PstmtDelegate");
/* 197 */           serverConfig.setPersistBatching(false);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 202 */       serverConfig.getNamingConvention().setDatabasePlatform(serverConfig.getDatabasePlatform());
/*     */       
/* 204 */       ServerCacheManager cacheManager = getCacheManager(serverConfig);
/*     */       
/* 206 */       int uniqueServerId = this.serverId.incrementAndGet();
/* 207 */       SpiBackgroundExecutor bgExecutor = createBackgroundExecutor(serverConfig, uniqueServerId);
/*     */       
/* 209 */       InternalConfiguration c = new InternalConfiguration(this.xmlConfig, this.clusterManager, cacheManager, bgExecutor, serverConfig, bootupClasses, (PstmtBatch)oraclePstmtBatch);
/*     */ 
/*     */       
/* 212 */       DefaultServer server = new DefaultServer(c, cacheManager);
/*     */       
/* 214 */       cacheManager.init((EbeanServer)server);
/*     */ 
/*     */       
/* 217 */       ArrayList<?> list = MBeanServerFactory.findMBeanServer(null);
/* 218 */       if (list.size() == 0) {
/*     */         
/* 220 */         mbeanServer = MBeanServerFactory.createMBeanServer();
/*     */       } else {
/*     */         
/* 223 */         mbeanServer = (MBeanServer)list.get(0);
/*     */       } 
/*     */       
/* 226 */       server.registerMBeans(mbeanServer, uniqueServerId);
/*     */ 
/*     */       
/* 229 */       executeDDL(server, online);
/*     */ 
/*     */       
/* 232 */       server.initialise();
/*     */       
/* 234 */       if (online) {
/* 235 */         if (this.clusterManager.isClustering())
/*     */         {
/* 237 */           this.clusterManager.registerServer((EbeanServer)server);
/*     */         }
/*     */ 
/*     */         
/* 241 */         int delaySecs = GlobalProperties.getInt("ebean.cacheWarmingDelay", 30);
/* 242 */         long sleepMillis = (1000 * delaySecs);
/*     */         
/* 244 */         if (sleepMillis > 0L) {
/* 245 */           Timer t = new Timer("EbeanCacheWarmer", true);
/* 246 */           t.schedule(new CacheWarmer(sleepMillis, (EbeanServer)server), sleepMillis);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 251 */       server.start();
/* 252 */       return server;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private PstmtDelegate getOraclePstmtDelegate(DataSource ds) {
/* 258 */     if (ds instanceof com.avaje.ebeaninternal.server.lib.sql.DataSourcePool)
/*     */     {
/* 260 */       return (PstmtDelegate)new StandardPstmtDelegate();
/*     */     }
/*     */     
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerCacheManager getCacheManager(ServerConfig serverConfig) {
/*     */     DefaultServerCacheFactory defaultServerCacheFactory;
/* 271 */     ServerCacheManager serverCacheManager = serverConfig.getServerCacheManager();
/* 272 */     if (serverCacheManager != null) {
/* 273 */       return serverCacheManager;
/*     */     }
/*     */ 
/*     */     
/* 277 */     ServerCacheOptions beanOptions = new ServerCacheOptions();
/* 278 */     beanOptions.setMaxSize(GlobalProperties.getInt("cache.maxSize", 1000));
/*     */     
/* 280 */     beanOptions.setMaxIdleSecs(GlobalProperties.getInt("cache.maxIdleTime", 600));
/*     */     
/* 282 */     beanOptions.setMaxSecsToLive(GlobalProperties.getInt("cache.maxTimeToLive", 21600));
/*     */ 
/*     */     
/* 285 */     ServerCacheOptions queryOptions = new ServerCacheOptions();
/* 286 */     queryOptions.setMaxSize(GlobalProperties.getInt("querycache.maxSize", 100));
/*     */     
/* 288 */     queryOptions.setMaxIdleSecs(GlobalProperties.getInt("querycache.maxIdleTime", 600));
/*     */     
/* 290 */     queryOptions.setMaxSecsToLive(GlobalProperties.getInt("querycache.maxTimeToLive", 21600));
/*     */     
/* 292 */     ServerCacheFactory cacheFactory = serverConfig.getServerCacheFactory();
/* 293 */     if (cacheFactory == null) {
/* 294 */       defaultServerCacheFactory = new DefaultServerCacheFactory();
/*     */     }
/*     */     
/* 297 */     return (ServerCacheManager)new DefaultServerCacheManager((ServerCacheFactory)defaultServerCacheFactory, beanOptions, queryOptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BootupClasses getBootupClasses(ServerConfig serverConfig) {
/* 306 */     BootupClasses bootupClasses = getBootupClasses1(serverConfig);
/* 307 */     bootupClasses.addPersistControllers(serverConfig.getPersistControllers());
/* 308 */     bootupClasses.addTransactionEventListeners(serverConfig.getTransactionEventListeners());
/* 309 */     bootupClasses.addPersistListeners(serverConfig.getPersistListeners());
/* 310 */     bootupClasses.addQueryAdapters(serverConfig.getQueryAdapters());
/* 311 */     bootupClasses.addServerConfigStartup(serverConfig.getServerConfigStartupListeners());
/*     */ 
/*     */     
/* 314 */     bootupClasses.runServerConfigStartup(serverConfig);
/* 315 */     return bootupClasses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BootupClasses getBootupClasses1(ServerConfig serverConfig) {
/* 323 */     List<Class<?>> entityClasses = serverConfig.getClasses();
/* 324 */     if (entityClasses != null && entityClasses.size() > 0)
/*     */     {
/* 326 */       return new BootupClasses(serverConfig.getClasses());
/*     */     }
/*     */     
/* 329 */     List<String> jars = serverConfig.getJars();
/* 330 */     List<String> packages = serverConfig.getPackages();
/*     */     
/* 332 */     if ((packages != null && !packages.isEmpty()) || (jars != null && !jars.isEmpty())) {
/*     */       
/* 334 */       BootupClassPathSearch search = new BootupClassPathSearch(null, packages, jars);
/* 335 */       return search.getBootupClasses();
/*     */     } 
/*     */ 
/*     */     
/* 339 */     return this.bootupClassSearch.getBootupClasses().createCopy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeDDL(SpiEbeanServer server, boolean online) {
/* 347 */     server.getDdlGenerator().execute(online);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNamingConvention(ServerConfig config) {
/* 354 */     if (config.getNamingConvention() == null) {
/* 355 */       UnderscoreNamingConvention nc = new UnderscoreNamingConvention();
/* 356 */       config.setNamingConvention((NamingConvention)nc);
/*     */       
/* 358 */       String v = config.getProperty("namingConvention.useForeignKeyPrefix");
/* 359 */       if (v != null) {
/* 360 */         boolean useForeignKeyPrefix = Boolean.valueOf(v).booleanValue();
/* 361 */         nc.setUseForeignKeyPrefix(useForeignKeyPrefix);
/*     */       } 
/*     */       
/* 364 */       String sequenceFormat = config.getProperty("namingConvention.sequenceFormat");
/* 365 */       if (sequenceFormat != null) {
/* 366 */         nc.setSequenceFormat(sequenceFormat);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDatabasePlatform(ServerConfig config) {
/* 376 */     DatabasePlatform dbPlatform = config.getDatabasePlatform();
/* 377 */     if (dbPlatform == null) {
/*     */       
/* 379 */       DatabasePlatformFactory factory = new DatabasePlatformFactory();
/*     */       
/* 381 */       DatabasePlatform db = factory.create(config);
/* 382 */       config.setDatabasePlatform(db);
/* 383 */       logger.info("DatabasePlatform name:" + config.getName() + " platform:" + db.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDataSource(ServerConfig config) {
/* 391 */     if (config.getDataSource() == null) {
/* 392 */       DataSource ds = getDataSourceFromConfig(config);
/* 393 */       config.setDataSource(ds);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private DataSource getDataSourceFromConfig(ServerConfig config) {
/* 399 */     DataSource ds = null;
/*     */     
/* 401 */     if (config.getDataSourceJndiName() != null) {
/* 402 */       ds = this.jndiDataSourceFactory.lookup(config.getDataSourceJndiName());
/* 403 */       if (ds == null) {
/* 404 */         String m = "JNDI lookup for DataSource " + config.getDataSourceJndiName() + " returned null.";
/* 405 */         throw new PersistenceException(m);
/*     */       } 
/* 407 */       return ds;
/*     */     } 
/*     */ 
/*     */     
/* 411 */     DataSourceConfig dsConfig = config.getDataSourceConfig();
/* 412 */     if (dsConfig == null) {
/* 413 */       String m = "No DataSourceConfig definded for " + config.getName();
/* 414 */       throw new PersistenceException(m);
/*     */     } 
/*     */     
/* 417 */     if (dsConfig.isOffline()) {
/* 418 */       if (config.getDatabasePlatformName() == null) {
/* 419 */         String m = "You MUST specify a DatabasePlatformName on ServerConfig when offline";
/* 420 */         throw new PersistenceException(m);
/*     */       } 
/* 422 */       return null;
/*     */     } 
/*     */     
/* 425 */     if (dsConfig.getHeartbeatSql() == null) {
/*     */       
/* 427 */       String heartbeatSql = getHeartbeatSql(dsConfig.getDriver());
/* 428 */       dsConfig.setHeartbeatSql(heartbeatSql);
/*     */     } 
/*     */     
/* 431 */     return (DataSource)DataSourceGlobalManager.getDataSource(config.getName(), dsConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getHeartbeatSql(String driver) {
/* 438 */     if (driver != null) {
/* 439 */       String d = driver.toLowerCase();
/* 440 */       if (d.contains("oracle")) {
/* 441 */         return "select 'x' from dual";
/*     */       }
/* 443 */       if (d.contains(".h2.") || d.contains(".mysql.") || d.contains("postgre")) {
/* 444 */         return "select 1";
/*     */       }
/*     */     } 
/* 447 */     return null;
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
/*     */   private boolean checkDataSource(ServerConfig serverConfig) {
/* 462 */     if (serverConfig.getDataSource() == null) {
/* 463 */       if (serverConfig.getDataSourceConfig().isOffline())
/*     */       {
/* 465 */         return false;
/*     */       }
/* 467 */       throw new RuntimeException("DataSource not set?");
/*     */     } 
/*     */     
/* 470 */     Connection c = null;
/*     */     try {
/* 472 */       c = serverConfig.getDataSource().getConnection();
/*     */       
/* 474 */       if (c.getAutoCommit()) {
/* 475 */         String m = "DataSource [" + serverConfig.getName() + "] has autoCommit defaulting to true!";
/* 476 */         logger.warning(m);
/*     */       } 
/*     */       
/* 479 */       return true;
/*     */     }
/* 481 */     catch (SQLException ex) {
/* 482 */       throw new PersistenceException(ex);
/*     */     } finally {
/*     */       
/* 485 */       if (c != null)
/*     */         try {
/* 487 */           c.close();
/* 488 */         } catch (SQLException ex) {
/* 489 */           logger.log(Level.SEVERE, (String)null, ex);
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class CacheWarmer
/*     */     extends TimerTask
/*     */   {
/* 497 */     private static final Logger log = Logger.getLogger(CacheWarmer.class.getName());
/*     */     
/*     */     private final long sleepMillis;
/*     */     private final EbeanServer server;
/*     */     
/*     */     CacheWarmer(long sleepMillis, EbeanServer server) {
/* 503 */       this.sleepMillis = sleepMillis;
/* 504 */       this.server = server;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/* 509 */         Thread.sleep(this.sleepMillis);
/* 510 */       } catch (InterruptedException e) {
/* 511 */         String msg = "Error while sleeping prior to cache warming";
/* 512 */         log.log(Level.SEVERE, msg, e);
/*     */       } 
/* 514 */       this.server.runCacheWarming();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\DefaultServerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */