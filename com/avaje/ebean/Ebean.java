/*      */ package com.avaje.ebean;
/*      */ 
/*      */ import com.avaje.ebean.cache.ServerCacheManager;
/*      */ import com.avaje.ebean.config.GlobalProperties;
/*      */ import com.avaje.ebean.text.csv.CsvReader;
/*      */ import com.avaje.ebean.text.json.JsonContext;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.logging.Logger;
/*      */ import javax.persistence.OptimisticLockException;
/*      */ import javax.persistence.PersistenceException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Ebean
/*      */ {
/*  136 */   private static final Logger logger = Logger.getLogger(Ebean.class.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  141 */   private static final ServerManager serverMgr = new ServerManager();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ServerManager
/*      */   {
/*  152 */     private final ConcurrentHashMap<String, EbeanServer> concMap = new ConcurrentHashMap<String, EbeanServer>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  158 */     private final HashMap<String, EbeanServer> syncMap = new HashMap<String, EbeanServer>();
/*      */     
/*  160 */     private final Object monitor = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private EbeanServer primaryServer;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ServerManager() {
/*  171 */       if (GlobalProperties.isSkipPrimaryServer()) {
/*      */ 
/*      */         
/*  174 */         Ebean.logger.fine("GlobalProperties.isSkipPrimaryServer()");
/*      */       }
/*      */       else {
/*      */         
/*  178 */         String primaryName = getPrimaryServerName();
/*  179 */         Ebean.logger.fine("primaryName:" + primaryName);
/*  180 */         if (primaryName != null && primaryName.trim().length() > 0) {
/*  181 */           this.primaryServer = getWithCreate(primaryName.trim());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private String getPrimaryServerName() {
/*  188 */       String serverName = GlobalProperties.get("ebean.default.datasource", null);
/*  189 */       return GlobalProperties.get("datasource.default", serverName);
/*      */     }
/*      */     
/*      */     private EbeanServer getPrimaryServer() {
/*  193 */       if (this.primaryServer == null) {
/*  194 */         String msg = "The default EbeanServer has not been defined?";
/*  195 */         msg = msg + " This is normally set via the ebean.datasource.default property.";
/*  196 */         msg = msg + " Otherwise it should be registered programatically via registerServer()";
/*  197 */         throw new PersistenceException(msg);
/*      */       } 
/*  199 */       return this.primaryServer;
/*      */     }
/*      */     
/*      */     private EbeanServer get(String name) {
/*  203 */       if (name == null || name.length() == 0) {
/*  204 */         return this.primaryServer;
/*      */       }
/*      */       
/*  207 */       EbeanServer server = this.concMap.get(name);
/*  208 */       if (server != null) {
/*  209 */         return server;
/*      */       }
/*      */       
/*  212 */       return getWithCreate(name);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private EbeanServer getWithCreate(String name) {
/*  220 */       synchronized (this.monitor) {
/*      */         
/*  222 */         EbeanServer server = this.syncMap.get(name);
/*  223 */         if (server == null) {
/*      */           
/*  225 */           server = EbeanServerFactory.create(name);
/*  226 */           register(server, false);
/*      */         } 
/*  228 */         return server;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void register(EbeanServer server, boolean isPrimaryServer) {
/*  236 */       synchronized (this.monitor) {
/*  237 */         this.concMap.put(server.getName(), server);
/*  238 */         EbeanServer existingServer = this.syncMap.put(server.getName(), server);
/*  239 */         if (existingServer != null) {
/*  240 */           String msg = "Existing EbeanServer [" + server.getName() + "] is being replaced?";
/*  241 */           Ebean.logger.warning(msg);
/*      */         } 
/*      */         
/*  244 */         if (isPrimaryServer) {
/*  245 */           this.primaryServer = server;
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
/*      */ 
/*      */   
/*      */   public static EbeanServer getServer(String name) {
/*  276 */     return serverMgr.get(name);
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
/*      */   public static ExpressionFactory getExpressionFactory() {
/*  297 */     return serverMgr.getPrimaryServer().getExpressionFactory();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void register(EbeanServer server, boolean isPrimaryServer) {
/*  305 */     serverMgr.register(server, isPrimaryServer);
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
/*      */   public static Object nextId(Class<?> beanType) {
/*  321 */     return serverMgr.getPrimaryServer().nextId(beanType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void logComment(String msg) {
/*  331 */     serverMgr.getPrimaryServer().logComment(msg);
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
/*      */   public static Transaction beginTransaction() {
/*  374 */     return serverMgr.getPrimaryServer().beginTransaction();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Transaction beginTransaction(TxIsolation isolation) {
/*  385 */     return serverMgr.getPrimaryServer().beginTransaction(isolation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Transaction currentTransaction() {
/*  393 */     return serverMgr.getPrimaryServer().currentTransaction();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void commitTransaction() {
/*  400 */     serverMgr.getPrimaryServer().commitTransaction();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void rollbackTransaction() {
/*  407 */     serverMgr.getPrimaryServer().rollbackTransaction();
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
/*      */   public static void endTransaction() {
/*  434 */     serverMgr.getPrimaryServer().endTransaction();
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
/*      */   public static InvalidValue validate(Object bean) {
/*  453 */     return serverMgr.getPrimaryServer().validate(bean);
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
/*      */   public static InvalidValue[] validate(Object bean, String propertyName, Object value) {
/*  477 */     return serverMgr.getPrimaryServer().validate(bean, propertyName, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, ValuePair> diff(Object a, Object b) {
/*  488 */     return serverMgr.getPrimaryServer().diff(a, b);
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
/*      */   public static void save(Object bean) throws OptimisticLockException {
/*  526 */     serverMgr.getPrimaryServer().save(bean);
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
/*      */   public static void update(Object bean) {
/*  569 */     serverMgr.getPrimaryServer().update(bean);
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
/*      */   public static void update(Object bean, Set<String> updateProps) {
/*  589 */     serverMgr.getPrimaryServer().update(bean, updateProps);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int save(Iterator<?> iterator) throws OptimisticLockException {
/*  596 */     return serverMgr.getPrimaryServer().save(iterator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int save(Collection<?> c) throws OptimisticLockException {
/*  603 */     return save(c.iterator());
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
/*      */   public static int deleteManyToManyAssociations(Object ownerBean, String propertyName) {
/*  617 */     return serverMgr.getPrimaryServer().deleteManyToManyAssociations(ownerBean, propertyName);
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
/*      */   public static void saveManyToManyAssociations(Object ownerBean, String propertyName) {
/*  635 */     serverMgr.getPrimaryServer().saveManyToManyAssociations(ownerBean, propertyName);
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
/*      */   public static void saveAssociation(Object ownerBean, String propertyName) {
/*  655 */     serverMgr.getPrimaryServer().saveAssociation(ownerBean, propertyName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void delete(Object bean) throws OptimisticLockException {
/*  666 */     serverMgr.getPrimaryServer().delete(bean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int delete(Class<?> beanType, Object id) {
/*  673 */     return serverMgr.getPrimaryServer().delete(beanType, id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void delete(Class<?> beanType, Collection<?> ids) {
/*  680 */     serverMgr.getPrimaryServer().delete(beanType, ids);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int delete(Iterator<?> it) throws OptimisticLockException {
/*  687 */     return serverMgr.getPrimaryServer().delete(it);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int delete(Collection<?> c) throws OptimisticLockException {
/*  694 */     return delete(c.iterator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void refresh(Object bean) {
/*  704 */     serverMgr.getPrimaryServer().refresh(bean);
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
/*      */   public static void refreshMany(Object bean, String manyPropertyName) {
/*  723 */     serverMgr.getPrimaryServer().refreshMany(bean, manyPropertyName);
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
/*      */   public static <T> T getReference(Class<T> beanType, Object id) {
/*  749 */     return serverMgr.getPrimaryServer().getReference(beanType, id);
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
/*      */   public static <T> void sort(List<T> list, String sortByClause) {
/*  795 */     serverMgr.getPrimaryServer().sort(list, sortByClause);
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
/*      */   public static <T> T find(Class<T> beanType, Object id) {
/*  843 */     return serverMgr.getPrimaryServer().find(beanType, id);
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
/*      */   public static SqlQuery createSqlQuery(String sql) {
/*  855 */     return serverMgr.getPrimaryServer().createSqlQuery(sql);
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
/*      */   public static SqlQuery createNamedSqlQuery(String namedQuery) {
/*  868 */     return serverMgr.getPrimaryServer().createNamedSqlQuery(namedQuery);
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
/*      */   public static SqlUpdate createSqlUpdate(String sql) {
/*  888 */     return serverMgr.getPrimaryServer().createSqlUpdate(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CallableSql createCallableSql(String sql) {
/*  897 */     return serverMgr.getPrimaryServer().createCallableSql(sql);
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
/*      */   public static SqlUpdate createNamedSqlUpdate(String namedQuery) {
/*  918 */     return serverMgr.getPrimaryServer().createNamedSqlUpdate(namedQuery);
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
/*      */   public static <T> Query<T> createNamedQuery(Class<T> beanType, String namedQuery) {
/*  945 */     return serverMgr.getPrimaryServer().createNamedQuery(beanType, namedQuery);
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
/*      */   public static <T> Query<T> createQuery(Class<T> beanType, String query) {
/*  973 */     return serverMgr.getPrimaryServer().createQuery(beanType, query);
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
/*      */   public static <T> Update<T> createNamedUpdate(Class<T> beanType, String namedUpdate) {
/* 1029 */     return serverMgr.getPrimaryServer().createNamedUpdate(beanType, namedUpdate);
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
/*      */   public static <T> Update<T> createUpdate(Class<T> beanType, String ormUpdate) {
/* 1062 */     return serverMgr.getPrimaryServer().createUpdate(beanType, ormUpdate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> CsvReader<T> createCsvReader(Class<T> beanType) {
/* 1070 */     return serverMgr.getPrimaryServer().createCsvReader(beanType);
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
/*      */   public static <T> Query<T> createQuery(Class<T> beanType) {
/* 1126 */     return serverMgr.getPrimaryServer().createQuery(beanType);
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
/*      */   public static <T> Query<T> find(Class<T> beanType) {
/* 1143 */     return serverMgr.getPrimaryServer().find(beanType);
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
/*      */   public static <T> Filter<T> filter(Class<T> beanType) {
/* 1157 */     return serverMgr.getPrimaryServer().filter(beanType);
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
/*      */   public static int execute(SqlUpdate sqlUpdate) {
/* 1202 */     return serverMgr.getPrimaryServer().execute(sqlUpdate);
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
/*      */   public static int execute(CallableSql callableSql) {
/* 1229 */     return serverMgr.getPrimaryServer().execute(callableSql);
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
/*      */   public static void execute(TxScope scope, TxRunnable r) {
/* 1253 */     serverMgr.getPrimaryServer().execute(scope, r);
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
/*      */   public static void execute(TxRunnable r) {
/* 1279 */     serverMgr.getPrimaryServer().execute(r);
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
/*      */   public static <T> T execute(TxScope scope, TxCallable<T> c) {
/* 1304 */     return serverMgr.getPrimaryServer().execute(scope, c);
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
/*      */   public static <T> T execute(TxCallable<T> c) {
/* 1336 */     return serverMgr.getPrimaryServer().execute(c);
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
/*      */   public static void externalModification(String tableName, boolean inserts, boolean updates, boolean deletes) {
/* 1376 */     serverMgr.getPrimaryServer().externalModification(tableName, inserts, updates, deletes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BeanState getBeanState(Object bean) {
/* 1387 */     return serverMgr.getPrimaryServer().getBeanState(bean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ServerCacheManager getServerCacheManager() {
/* 1395 */     return serverMgr.getPrimaryServer().getServerCacheManager();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BackgroundExecutor getBackgroundExecutor() {
/* 1403 */     return serverMgr.getPrimaryServer().getBackgroundExecutor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void runCacheWarming() {
/* 1414 */     serverMgr.getPrimaryServer().runCacheWarming();
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
/*      */   public static void runCacheWarming(Class<?> beanType) {
/* 1426 */     serverMgr.getPrimaryServer().runCacheWarming(beanType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JsonContext createJsonContext() {
/* 1434 */     return serverMgr.getPrimaryServer().createJsonContext();
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\Ebean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */