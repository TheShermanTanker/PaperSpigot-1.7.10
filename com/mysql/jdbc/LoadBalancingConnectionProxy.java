/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationHandler;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LoadBalancingConnectionProxy
/*      */   implements InvocationHandler, PingTarget
/*      */ {
/*      */   private static Method getLocalTimeMethod;
/*   67 */   private long totalPhysicalConnections = 0L;
/*   68 */   private long activePhysicalConnections = 0L;
/*   69 */   private String hostToRemove = null;
/*   70 */   private long lastUsed = 0L;
/*   71 */   private long transactionCount = 0L;
/*   72 */   private ConnectionGroup connectionGroup = null;
/*   73 */   private String closedReason = null; public static final String BLACKLIST_TIMEOUT_PROPERTY_KEY = "loadBalanceBlacklistTimeout"; protected MySQLConnection currentConn; protected List<String> hostList; protected Map<String, ConnectionImpl> liveConnections; private Map<ConnectionImpl, String> connectionsToHostsMap;
/*      */   private long[] responseTimes;
/*      */   private Map<String, Integer> hostsToListIndexMap;
/*      */   
/*      */   static {
/*      */     try {
/*   79 */       getLocalTimeMethod = System.class.getMethod("nanoTime", new Class[0]);
/*      */     }
/*   81 */     catch (SecurityException e) {
/*      */     
/*   83 */     } catch (NoSuchMethodException e) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ConnectionErrorFiringInvocationHandler
/*      */     implements InvocationHandler
/*      */   {
/*   92 */     Object invokeOn = null;
/*      */     
/*      */     public ConnectionErrorFiringInvocationHandler(Object toInvokeOn) {
/*   95 */       this.invokeOn = toInvokeOn;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/*  100 */       Object result = null;
/*      */       
/*      */       try {
/*  103 */         result = method.invoke(this.invokeOn, args);
/*      */         
/*  105 */         if (result != null) {
/*  106 */           result = LoadBalancingConnectionProxy.this.proxyIfInterfaceIsJdbc(result, result.getClass());
/*      */         }
/*  108 */       } catch (InvocationTargetException e) {
/*  109 */         LoadBalancingConnectionProxy.this.dealWithInvocationException(e);
/*      */       } 
/*      */       
/*  112 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean inTransaction = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  130 */   private long transactionStartTime = 0L;
/*      */   
/*      */   private Properties localProps;
/*      */   
/*      */   private boolean isClosed = false;
/*      */   
/*      */   private BalanceStrategy balancer;
/*      */   
/*      */   private int retriesAllDown;
/*      */   
/*  140 */   private static Map<String, Long> globalBlacklist = new HashMap<String, Long>();
/*      */   
/*  142 */   private int globalBlacklistTimeout = 0;
/*      */   
/*  144 */   private long connectionGroupProxyID = 0L;
/*      */   
/*      */   private LoadBalanceExceptionChecker exceptionChecker;
/*      */   
/*  148 */   private Map<Class, Boolean> jdbcInterfacesForProxyCache = (Map)new HashMap<Class<?>, Boolean>();
/*      */   
/*  150 */   private MySQLConnection thisAsConnection = null;
/*      */   
/*  152 */   private int autoCommitSwapThreshold = 0;
/*      */   private static Constructor JDBC_4_LB_CONNECTION_CTOR;
/*      */   private Map<Class, Class[]> allInterfacesToProxy;
/*      */   
/*      */   static {
/*  157 */     if (Util.isJdbc4()) {
/*      */       try {
/*  159 */         JDBC_4_LB_CONNECTION_CTOR = Class.forName("com.mysql.jdbc.JDBC4LoadBalancedMySQLConnection").getConstructor(new Class[] { LoadBalancingConnectionProxy.class });
/*      */       
/*      */       }
/*  162 */       catch (SecurityException e) {
/*  163 */         throw new RuntimeException(e);
/*  164 */       } catch (NoSuchMethodException e) {
/*  165 */         throw new RuntimeException(e);
/*  166 */       } catch (ClassNotFoundException e) {
/*  167 */         throw new RuntimeException(e);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized ConnectionImpl createConnectionForHost(String hostPortSpec) throws SQLException {
/*  344 */     Properties connProps = (Properties)this.localProps.clone();
/*      */     
/*  346 */     String[] hostPortPair = NonRegisteringDriver.parseHostPortPair(hostPortSpec);
/*      */     
/*  348 */     String hostName = hostPortPair[0];
/*  349 */     String portNumber = hostPortPair[1];
/*  350 */     String dbName = connProps.getProperty("DBNAME");
/*      */ 
/*      */     
/*  353 */     if (hostName == null) {
/*  354 */       throw new SQLException("Could not find a hostname to start a connection to");
/*      */     }
/*      */     
/*  357 */     if (portNumber == null) {
/*  358 */       portNumber = "3306";
/*      */     }
/*      */     
/*  361 */     connProps.setProperty("HOST", hostName);
/*  362 */     connProps.setProperty("PORT", portNumber);
/*      */     
/*  364 */     connProps.setProperty("HOST.1", hostName);
/*      */     
/*  366 */     connProps.setProperty("PORT.1", portNumber);
/*      */     
/*  368 */     connProps.setProperty("NUM_HOSTS", "1");
/*  369 */     connProps.setProperty("roundRobinLoadBalance", "false");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  376 */     ConnectionImpl conn = (ConnectionImpl)ConnectionImpl.getInstance(hostName, Integer.parseInt(portNumber), connProps, dbName, "jdbc:mysql://" + hostName + ":" + portNumber + "/");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  382 */     this.liveConnections.put(hostPortSpec, conn);
/*  383 */     this.connectionsToHostsMap.put(conn, hostPortSpec);
/*      */ 
/*      */     
/*  386 */     this.activePhysicalConnections++;
/*  387 */     this.totalPhysicalConnections++;
/*      */     
/*  389 */     conn.setProxy(this.thisAsConnection);
/*      */     
/*  391 */     return conn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void dealWithInvocationException(InvocationTargetException e) throws SQLException, Throwable, InvocationTargetException {
/*  402 */     Throwable t = e.getTargetException();
/*      */     
/*  404 */     if (t != null) {
/*  405 */       if (t instanceof SQLException && shouldExceptionTriggerFailover((SQLException)t)) {
/*  406 */         invalidateCurrentConnection();
/*  407 */         pickNewConnection();
/*      */       } 
/*      */       
/*  410 */       throw t;
/*      */     } 
/*      */     
/*  413 */     throw e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void invalidateCurrentConnection() throws SQLException {
/*      */     try {
/*  423 */       if (!this.currentConn.isClosed()) {
/*  424 */         this.currentConn.close();
/*      */       }
/*      */     }
/*      */     finally {
/*      */       
/*  429 */       if (isGlobalBlacklistEnabled()) {
/*  430 */         addToGlobalBlacklist(this.connectionsToHostsMap.get(this.currentConn));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  435 */       this.liveConnections.remove(this.connectionsToHostsMap.get(this.currentConn));
/*      */       
/*  437 */       Object mappedHost = this.connectionsToHostsMap.remove(this.currentConn);
/*      */       
/*  439 */       if (mappedHost != null && this.hostsToListIndexMap.containsKey(mappedHost)) {
/*      */         
/*  441 */         int hostIndex = ((Integer)this.hostsToListIndexMap.get(mappedHost)).intValue();
/*      */ 
/*      */         
/*  444 */         synchronized (this.responseTimes) {
/*  445 */           this.responseTimes[hostIndex] = 0L;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void closeAllConnections() {
/*  452 */     synchronized (this) {
/*      */       
/*  454 */       Iterator<ConnectionImpl> allConnections = this.liveConnections.values().iterator();
/*      */       
/*  456 */       while (allConnections.hasNext()) {
/*      */         try {
/*  458 */           this.activePhysicalConnections--;
/*  459 */           ((ConnectionImpl)allConnections.next()).close();
/*  460 */         } catch (SQLException e) {}
/*      */       } 
/*      */ 
/*      */       
/*  464 */       if (!this.isClosed) {
/*  465 */         this.balancer.destroy();
/*  466 */         if (this.connectionGroup != null) {
/*  467 */           this.connectionGroup.closeConnectionProxy(this);
/*      */         }
/*      */       } 
/*      */       
/*  471 */       this.liveConnections.clear();
/*  472 */       this.connectionsToHostsMap.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/*  479 */     return invoke(proxy, method, args, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object invoke(Object proxy, Method method, Object[] args, boolean swapAtTransactionBoundary) throws Throwable {
/*  490 */     String methodName = method.getName();
/*      */     
/*  492 */     if ("getLoadBalanceSafeProxy".equals(methodName)) {
/*  493 */       return this.currentConn;
/*      */     }
/*      */     
/*  496 */     if ("equals".equals(methodName) && args.length == 1) {
/*  497 */       if (args[0] instanceof Proxy) {
/*  498 */         return Boolean.valueOf(((Proxy)args[0]).equals(this));
/*      */       }
/*  500 */       return Boolean.valueOf(equals(args[0]));
/*      */     } 
/*      */     
/*  503 */     if ("hashCode".equals(methodName)) {
/*  504 */       return new Integer(hashCode());
/*      */     }
/*      */     
/*  507 */     if ("close".equals(methodName)) {
/*  508 */       closeAllConnections();
/*  509 */       this.isClosed = true;
/*  510 */       this.closedReason = "Connection explicitly closed.";
/*  511 */       return null;
/*      */     } 
/*      */     
/*  514 */     if ("isClosed".equals(methodName)) {
/*  515 */       return Boolean.valueOf(this.isClosed);
/*      */     }
/*      */     
/*  518 */     if (this.isClosed) {
/*  519 */       String reason = "No operations allowed after connection closed.";
/*  520 */       if (this.closedReason != null) {
/*  521 */         reason = reason + "  " + this.closedReason;
/*      */       }
/*  523 */       throw SQLError.createSQLException(reason, "08003", null);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  532 */     if (!this.inTransaction) {
/*  533 */       this.inTransaction = true;
/*  534 */       this.transactionStartTime = getLocalTimeBestResolution();
/*  535 */       this.transactionCount++;
/*      */     } 
/*      */     
/*  538 */     Object result = null;
/*      */     
/*      */     try {
/*  541 */       this.lastUsed = System.currentTimeMillis();
/*  542 */       result = method.invoke(this.thisAsConnection, args);
/*      */       
/*  544 */       if (result != null) {
/*  545 */         if (result instanceof Statement) {
/*  546 */           ((Statement)result).setPingTarget(this);
/*      */         }
/*      */         
/*  549 */         result = proxyIfInterfaceIsJdbc(result, result.getClass());
/*      */       } 
/*  551 */     } catch (InvocationTargetException e) {
/*  552 */       dealWithInvocationException(e);
/*      */     } finally {
/*  554 */       if (swapAtTransactionBoundary && ("commit".equals(methodName) || "rollback".equals(methodName))) {
/*  555 */         this.inTransaction = false;
/*      */ 
/*      */         
/*  558 */         String host = this.connectionsToHostsMap.get(this.currentConn);
/*      */ 
/*      */ 
/*      */         
/*  562 */         if (host != null) {
/*  563 */           synchronized (this.responseTimes) {
/*  564 */             int hostIndex = ((Integer)this.hostsToListIndexMap.get(host)).intValue();
/*      */ 
/*      */             
/*  567 */             if (hostIndex < this.responseTimes.length) {
/*  568 */               this.responseTimes[hostIndex] = getLocalTimeBestResolution() - this.transactionStartTime;
/*      */             }
/*      */           } 
/*      */         }
/*      */         
/*  573 */         pickNewConnection();
/*      */       } 
/*      */     } 
/*      */     
/*  577 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void pickNewConnection() throws SQLException {
/*  587 */     if (this.currentConn == null) {
/*  588 */       this.currentConn = this.balancer.pickConnection(this, Collections.unmodifiableList(this.hostList), Collections.unmodifiableMap(this.liveConnections), (long[])this.responseTimes.clone(), this.retriesAllDown);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  595 */     if (this.currentConn.isClosed()) {
/*  596 */       invalidateCurrentConnection();
/*      */     }
/*      */     
/*  599 */     int pingTimeout = this.currentConn.getLoadBalancePingTimeout();
/*  600 */     boolean pingBeforeReturn = this.currentConn.getLoadBalanceValidateConnectionOnSwapServer();
/*      */     
/*  602 */     for (int hostsTried = 0, hostsToTry = this.hostList.size(); hostsTried <= hostsToTry; hostsTried++) {
/*      */       try {
/*  604 */         ConnectionImpl newConn = this.balancer.pickConnection(this, Collections.unmodifiableList(this.hostList), Collections.unmodifiableMap(this.liveConnections), (long[])this.responseTimes.clone(), this.retriesAllDown);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  610 */         if (this.currentConn != null) {
/*  611 */           if (pingBeforeReturn) {
/*  612 */             if (pingTimeout == 0) {
/*  613 */               newConn.ping();
/*      */             } else {
/*  615 */               newConn.pingInternal(true, pingTimeout);
/*      */             } 
/*      */           }
/*      */           
/*  619 */           syncSessionState(this.currentConn, newConn);
/*      */         } 
/*      */         
/*  622 */         this.currentConn = newConn;
/*      */         return;
/*  624 */       } catch (SQLException e) {
/*      */         
/*  626 */         if (shouldExceptionTriggerFailover(e))
/*      */         {
/*      */           
/*  629 */           invalidateCurrentConnection();
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  635 */     this.isClosed = true;
/*  636 */     this.closedReason = "Connection closed after inability to pick valid new connection during fail-over.";
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
/*      */   Object proxyIfInterfaceIsJdbc(Object toProxy, Class clazz) {
/*  651 */     if (isInterfaceJdbc(clazz)) {
/*      */       
/*  653 */       Class[] interfacesToProxy = getAllInterfacesToProxy(clazz);
/*      */       
/*  655 */       return Proxy.newProxyInstance(toProxy.getClass().getClassLoader(), interfacesToProxy, createConnectionProxy(toProxy));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  660 */     return toProxy;
/*      */   }
/*      */   
/*  663 */   LoadBalancingConnectionProxy(List<String> hosts, Properties props) throws SQLException { this.allInterfacesToProxy = (Map)new HashMap<Class<?>, Class<?>>(); String group = props.getProperty("loadBalanceConnectionGroup", null); boolean enableJMX = false; String enableJMXAsString = props.getProperty("loadBalanceEnableJMX", "false"); try { enableJMX = Boolean.parseBoolean(enableJMXAsString); } catch (Exception e) { throw SQLError.createSQLException(Messages.getString("LoadBalancingConnectionProxy.badValueForLoadBalanceEnableJMX", new Object[] { enableJMXAsString }), "S1009", null); }  if (group != null) { this.connectionGroup = ConnectionGroupManager.getConnectionGroupInstance(group); if (enableJMX)
/*      */         ConnectionGroupManager.registerJmx();  this.connectionGroupProxyID = this.connectionGroup.registerConnectionProxy(this, hosts); hosts = new ArrayList<String>(this.connectionGroup.getInitialHosts()); }  this.hostList = hosts; int numHosts = this.hostList.size(); this.liveConnections = new HashMap<String, ConnectionImpl>(numHosts); this.connectionsToHostsMap = new HashMap<ConnectionImpl, String>(numHosts); this.responseTimes = new long[numHosts]; this.hostsToListIndexMap = new HashMap<String, Integer>(numHosts); this.localProps = (Properties)props.clone(); this.localProps.remove("HOST"); this.localProps.remove("PORT"); for (int i = 0; i < numHosts; i++) { this.hostsToListIndexMap.put(this.hostList.get(i), new Integer(i)); this.localProps.remove("HOST." + (i + 1)); this.localProps.remove("PORT." + (i + 1)); }  this.localProps.remove("NUM_HOSTS"); this.localProps.setProperty("useLocalSessionState", "true"); String strategy = this.localProps.getProperty("loadBalanceStrategy", "random"); String lbExceptionChecker = this.localProps.getProperty("loadBalanceExceptionChecker", "com.mysql.jdbc.StandardLoadBalanceExceptionChecker"); String retriesAllDownAsString = this.localProps.getProperty("retriesAllDown", "120"); try { this.retriesAllDown = Integer.parseInt(retriesAllDownAsString); } catch (NumberFormatException nfe) { throw SQLError.createSQLException(Messages.getString("LoadBalancingConnectionProxy.badValueForRetriesAllDown", new Object[] { retriesAllDownAsString }), "S1009", null); }  String blacklistTimeoutAsString = this.localProps.getProperty("loadBalanceBlacklistTimeout", "0"); try { this.globalBlacklistTimeout = Integer.parseInt(blacklistTimeoutAsString); } catch (NumberFormatException nfe) { throw SQLError.createSQLException(Messages.getString("LoadBalancingConnectionProxy.badValueForLoadBalanceBlacklistTimeout", new Object[] { retriesAllDownAsString }), "S1009", null); }  if ("random".equals(strategy)) { this.balancer = Util.loadExtensions(null, props, "com.mysql.jdbc.RandomBalanceStrategy", "InvalidLoadBalanceStrategy", null).get(0); } else if ("bestResponseTime".equals(strategy)) { this.balancer = Util.loadExtensions(null, props, "com.mysql.jdbc.BestResponseTimeBalanceStrategy", "InvalidLoadBalanceStrategy", null).get(0); } else { this.balancer = Util.loadExtensions(null, props, strategy, "InvalidLoadBalanceStrategy", null).get(0); }  String autoCommitSwapThresholdAsString = props.getProperty("loadBalanceAutoCommitStatementThreshold", "0"); try { this.autoCommitSwapThreshold = Integer.parseInt(autoCommitSwapThresholdAsString); } catch (NumberFormatException nfe) { throw SQLError.createSQLException(Messages.getString("LoadBalancingConnectionProxy.badValueForLoadBalanceAutoCommitStatementThreshold", new Object[] { autoCommitSwapThresholdAsString }), "S1009", null); }  String autoCommitSwapRegex = props.getProperty("loadBalanceAutoCommitStatementRegex", ""); if (!"".equals(autoCommitSwapRegex))
/*      */       try { "".matches(autoCommitSwapRegex); } catch (Exception e) { throw SQLError.createSQLException(Messages.getString("LoadBalancingConnectionProxy.badValueForLoadBalanceAutoCommitStatementRegex", new Object[] { autoCommitSwapRegex }), "S1009", null); }   if (this.autoCommitSwapThreshold > 0) { String statementInterceptors = this.localProps.getProperty("statementInterceptors"); if (statementInterceptors == null) { this.localProps.setProperty("statementInterceptors", "com.mysql.jdbc.LoadBalancedAutoCommitInterceptor"); } else if (statementInterceptors.length() > 0) { this.localProps.setProperty("statementInterceptors", statementInterceptors + ",com.mysql.jdbc.LoadBalancedAutoCommitInterceptor"); }  props.setProperty("statementInterceptors", this.localProps.getProperty("statementInterceptors")); }  this.balancer.init(null, props); this.exceptionChecker = Util.loadExtensions(null, props, lbExceptionChecker, "InvalidLoadBalanceExceptionChecker", null).get(0); this.exceptionChecker.init(null, props); if (Util.isJdbc4() || JDBC_4_LB_CONNECTION_CTOR != null) { this.thisAsConnection = (MySQLConnection)Util.handleNewInstance(JDBC_4_LB_CONNECTION_CTOR, new Object[] { this }, null); } else { this.thisAsConnection = new LoadBalancedMySQLConnection(this); }
/*  666 */      pickNewConnection(); } private Class[] getAllInterfacesToProxy(Class clazz) { Class[] interfacesToProxy = this.allInterfacesToProxy.get(clazz);
/*      */     
/*  668 */     if (interfacesToProxy != null) {
/*  669 */       return interfacesToProxy;
/*      */     }
/*      */     
/*  672 */     List<Class<?>> interfaces = new LinkedList<Class<?>>();
/*      */     
/*  674 */     Class superClass = clazz;
/*      */     
/*  676 */     while (!superClass.equals(Object.class)) {
/*  677 */       Class[] declared = superClass.getInterfaces();
/*      */       
/*  679 */       for (int i = 0; i < declared.length; i++) {
/*  680 */         interfaces.add(declared[i]);
/*      */       }
/*      */       
/*  683 */       superClass = superClass.getSuperclass();
/*      */     } 
/*      */     
/*  686 */     interfacesToProxy = new Class[interfaces.size()];
/*  687 */     interfaces.toArray((Class<?>[][])interfacesToProxy);
/*      */     
/*  689 */     this.allInterfacesToProxy.put(clazz, interfacesToProxy);
/*      */     
/*  691 */     return interfacesToProxy; }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isInterfaceJdbc(Class clazz) {
/*  696 */     if (this.jdbcInterfacesForProxyCache.containsKey(clazz)) {
/*  697 */       return ((Boolean)this.jdbcInterfacesForProxyCache.get(clazz)).booleanValue();
/*      */     }
/*      */     
/*  700 */     Class[] interfaces = clazz.getInterfaces();
/*      */     
/*  702 */     for (int i = 0; i < interfaces.length; i++) {
/*  703 */       String packageName = interfaces[i].getPackage().getName();
/*      */       
/*  705 */       if ("java.sql".equals(packageName) || "javax.sql".equals(packageName) || "com.mysql.jdbc".equals(packageName)) {
/*      */ 
/*      */         
/*  708 */         this.jdbcInterfacesForProxyCache.put(clazz, new Boolean(true));
/*  709 */         return true;
/*      */       } 
/*      */       
/*  712 */       if (isInterfaceJdbc(interfaces[i])) {
/*  713 */         this.jdbcInterfacesForProxyCache.put(clazz, new Boolean(true));
/*  714 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  718 */     this.jdbcInterfacesForProxyCache.put(clazz, new Boolean(false));
/*  719 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ConnectionErrorFiringInvocationHandler createConnectionProxy(Object toProxy) {
/*  725 */     return new ConnectionErrorFiringInvocationHandler(toProxy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long getLocalTimeBestResolution() {
/*  733 */     if (getLocalTimeMethod != null) {
/*      */       try {
/*  735 */         return ((Long)getLocalTimeMethod.invoke(null, (Object[])null)).longValue();
/*      */       }
/*  737 */       catch (IllegalArgumentException e) {
/*      */       
/*  739 */       } catch (IllegalAccessException e) {
/*      */       
/*  741 */       } catch (InvocationTargetException e) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  746 */     return System.currentTimeMillis();
/*      */   }
/*      */   
/*      */   public synchronized void doPing() throws SQLException {
/*  750 */     SQLException se = null;
/*  751 */     boolean foundHost = false;
/*  752 */     int pingTimeout = this.currentConn.getLoadBalancePingTimeout();
/*  753 */     synchronized (this) {
/*  754 */       for (Iterator<String> i = this.hostList.iterator(); i.hasNext(); ) {
/*  755 */         String host = i.next();
/*  756 */         ConnectionImpl conn = this.liveConnections.get(host);
/*  757 */         if (conn == null) {
/*      */           continue;
/*      */         }
/*      */         try {
/*  761 */           if (pingTimeout == 0) {
/*  762 */             conn.ping();
/*      */           } else {
/*  764 */             conn.pingInternal(true, pingTimeout);
/*      */           } 
/*  766 */           foundHost = true;
/*  767 */         } catch (SQLException e) {
/*  768 */           this.activePhysicalConnections--;
/*      */ 
/*      */           
/*  771 */           if (host.equals(this.connectionsToHostsMap.get(this.currentConn))) {
/*      */ 
/*      */ 
/*      */             
/*  775 */             closeAllConnections();
/*  776 */             this.isClosed = true;
/*  777 */             this.closedReason = "Connection closed because ping of current connection failed.";
/*  778 */             throw e;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  783 */           if (e.getMessage().equals(Messages.getString("Connection.exceededConnectionLifetime"))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  789 */             if (se == null) {
/*  790 */               se = e;
/*      */             }
/*      */           } else {
/*      */             
/*  794 */             se = e;
/*  795 */             if (isGlobalBlacklistEnabled()) {
/*  796 */               addToGlobalBlacklist(host);
/*      */             }
/*      */           } 
/*      */           
/*  800 */           this.liveConnections.remove(this.connectionsToHostsMap.get(conn));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  806 */     if (!foundHost) {
/*  807 */       closeAllConnections();
/*  808 */       this.isClosed = true;
/*  809 */       this.closedReason = "Connection closed due to inability to ping any active connections.";
/*      */       
/*  811 */       if (se != null) {
/*  812 */         throw se;
/*      */       }
/*      */ 
/*      */       
/*  816 */       ((ConnectionImpl)this.currentConn).throwConnectionClosedException();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addToGlobalBlacklist(String host, long timeout) {
/*  822 */     if (isGlobalBlacklistEnabled()) {
/*  823 */       synchronized (globalBlacklist) {
/*  824 */         globalBlacklist.put(host, new Long(timeout));
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void addToGlobalBlacklist(String host) {
/*  830 */     addToGlobalBlacklist(host, System.currentTimeMillis() + this.globalBlacklistTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isGlobalBlacklistEnabled() {
/*  836 */     return (this.globalBlacklistTimeout > 0);
/*      */   }
/*      */   
/*      */   public Map<String, Long> getGlobalBlacklist() {
/*  840 */     if (!isGlobalBlacklistEnabled()) {
/*  841 */       String localHostToRemove = this.hostToRemove;
/*  842 */       if (this.hostToRemove != null) {
/*  843 */         HashMap<String, Long> fakedBlacklist = new HashMap<String, Long>();
/*  844 */         fakedBlacklist.put(localHostToRemove, new Long(System.currentTimeMillis() + 5000L));
/*  845 */         return fakedBlacklist;
/*      */       } 
/*  847 */       return new HashMap<String, Long>(1);
/*      */     } 
/*      */ 
/*      */     
/*  851 */     Map<String, Long> blacklistClone = new HashMap<String, Long>(globalBlacklist.size());
/*      */ 
/*      */     
/*  854 */     synchronized (globalBlacklist) {
/*  855 */       blacklistClone.putAll(globalBlacklist);
/*      */     } 
/*  857 */     Set<String> keys = blacklistClone.keySet();
/*      */ 
/*      */     
/*  860 */     keys.retainAll(this.hostList);
/*  861 */     if (keys.size() == this.hostList.size())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  867 */       return new HashMap<String, Long>(1);
/*      */     }
/*      */ 
/*      */     
/*  871 */     for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
/*  872 */       String host = i.next();
/*      */ 
/*      */       
/*  875 */       Long timeout = globalBlacklist.get(host);
/*  876 */       if (timeout != null && timeout.longValue() < System.currentTimeMillis()) {
/*      */ 
/*      */         
/*  879 */         synchronized (globalBlacklist) {
/*  880 */           globalBlacklist.remove(host);
/*      */         } 
/*  882 */         i.remove();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  887 */     return blacklistClone;
/*      */   }
/*      */   
/*      */   public boolean shouldExceptionTriggerFailover(SQLException ex) {
/*  891 */     return this.exceptionChecker.shouldExceptionTriggerFailover(ex);
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeHostWhenNotInUse(String host) throws SQLException {
/*  896 */     int timeBetweenChecks = 1000;
/*  897 */     long timeBeforeHardFail = 15000L;
/*  898 */     addToGlobalBlacklist(host, timeBeforeHardFail + 1000L);
/*  899 */     long cur = System.currentTimeMillis();
/*  900 */     while (System.currentTimeMillis() - timeBeforeHardFail < cur) {
/*  901 */       synchronized (this) {
/*  902 */         this.hostToRemove = host;
/*  903 */         if (!host.equals(this.currentConn.getHost())) {
/*  904 */           removeHost(host);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       try {
/*  909 */         Thread.sleep(timeBetweenChecks);
/*  910 */       } catch (InterruptedException e) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  915 */     removeHost(host);
/*      */   }
/*      */   
/*      */   public void removeHost(String host) throws SQLException {
/*  919 */     synchronized (this) {
/*  920 */       if (this.connectionGroup != null && 
/*  921 */         this.connectionGroup.getInitialHosts().size() == 1 && this.connectionGroup.getInitialHosts().contains(host)) {
/*  922 */         throw SQLError.createSQLException("Cannot remove only configured host.", null);
/*      */       }
/*      */       
/*  925 */       this.hostToRemove = host;
/*  926 */       if (host.equals(this.currentConn.getHost())) {
/*  927 */         closeAllConnections();
/*      */       } else {
/*  929 */         this.connectionsToHostsMap.remove(this.liveConnections.remove(host));
/*  930 */         Integer idx = this.hostsToListIndexMap.remove(host);
/*  931 */         long[] newResponseTimes = new long[this.responseTimes.length - 1];
/*  932 */         int newIdx = 0;
/*  933 */         for (Iterator<String> i = this.hostList.iterator(); i.hasNext(); newIdx++) {
/*  934 */           String copyHost = i.next();
/*  935 */           if (idx != null && idx.intValue() < this.responseTimes.length) {
/*  936 */             newResponseTimes[newIdx] = this.responseTimes[idx.intValue()];
/*  937 */             this.hostsToListIndexMap.put(copyHost, new Integer(newIdx));
/*      */           } 
/*      */         } 
/*  940 */         this.responseTimes = newResponseTimes;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean addHost(String host) {
/*  947 */     synchronized (this) {
/*  948 */       if (this.hostsToListIndexMap.containsKey(host)) {
/*  949 */         return false;
/*      */       }
/*  951 */       long[] newResponseTimes = new long[this.responseTimes.length + 1];
/*  952 */       for (int i = 0; i < this.responseTimes.length; i++) {
/*  953 */         newResponseTimes[i] = this.responseTimes[i];
/*      */       }
/*  955 */       this.responseTimes = newResponseTimes;
/*  956 */       this.hostList.add(host);
/*  957 */       this.hostsToListIndexMap.put(host, new Integer(this.responseTimes.length - 1));
/*      */     } 
/*  959 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLastUsed() {
/*  965 */     return this.lastUsed;
/*      */   }
/*      */   
/*      */   public boolean inTransaction() {
/*  969 */     return this.inTransaction;
/*      */   }
/*      */   
/*      */   public long getTransactionCount() {
/*  973 */     return this.transactionCount;
/*      */   }
/*      */   
/*      */   public long getActivePhysicalConnectionCount() {
/*  977 */     return this.activePhysicalConnections;
/*      */   }
/*      */   public long getTotalPhysicalConnectionCount() {
/*  980 */     return this.totalPhysicalConnections;
/*      */   }
/*      */   
/*      */   public long getConnectionGroupProxyID() {
/*  984 */     return this.connectionGroupProxyID;
/*      */   }
/*      */   
/*      */   public String getCurrentActiveHost() {
/*  988 */     MySQLConnection c = this.currentConn;
/*  989 */     if (c != null) {
/*  990 */       Object o = this.connectionsToHostsMap.get(c);
/*  991 */       if (o != null) {
/*  992 */         return o.toString();
/*      */       }
/*      */     } 
/*  995 */     return null;
/*      */   }
/*      */   
/*      */   public long getCurrentTransactionDuration() {
/*  999 */     long st = 0L;
/*      */     
/* 1001 */     if (this.inTransaction && (st = this.transactionStartTime) > 0L) {
/* 1002 */       return getLocalTimeBestResolution() - this.transactionStartTime;
/*      */     }
/* 1004 */     return 0L;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void syncSessionState(Connection initial, Connection target) throws SQLException {
/* 1009 */     if (initial == null || target == null) {
/*      */       return;
/*      */     }
/* 1012 */     target.setAutoCommit(initial.getAutoCommit());
/* 1013 */     target.setCatalog(initial.getCatalog());
/* 1014 */     target.setTransactionIsolation(initial.getTransactionIsolation());
/* 1015 */     target.setReadOnly(initial.isReadOnly());
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\LoadBalancingConnectionProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */