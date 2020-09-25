/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ConnectionGroup
/*     */ {
/*     */   private String groupName;
/*  36 */   private long connections = 0L;
/*  37 */   private long activeConnections = 0L;
/*  38 */   private HashMap<Long, LoadBalancingConnectionProxy> connectionProxies = new HashMap<Long, LoadBalancingConnectionProxy>();
/*  39 */   private Set<String> hostList = new HashSet<String>();
/*     */   private boolean isInitialized = false;
/*  41 */   private long closedProxyTotalPhysicalConnections = 0L;
/*  42 */   private long closedProxyTotalTransactions = 0L;
/*  43 */   private int activeHosts = 0;
/*  44 */   private Set<String> closedHosts = new HashSet<String>();
/*     */   
/*     */   ConnectionGroup(String groupName) {
/*  47 */     this.groupName = groupName;
/*     */   }
/*     */ 
/*     */   
/*     */   public long registerConnectionProxy(LoadBalancingConnectionProxy proxy, List<String> localHostList) {
/*     */     long currentConnectionId;
/*  53 */     synchronized (this) {
/*  54 */       if (!this.isInitialized) {
/*  55 */         this.hostList.addAll(localHostList);
/*  56 */         this.isInitialized = true;
/*  57 */         this.activeHosts = localHostList.size();
/*     */       } 
/*  59 */       currentConnectionId = ++this.connections;
/*  60 */       this.connectionProxies.put(new Long(currentConnectionId), proxy);
/*     */     } 
/*  62 */     this.activeConnections++;
/*     */     
/*  64 */     return currentConnectionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGroupName() {
/*  72 */     return this.groupName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getInitialHosts() {
/*  79 */     return this.hostList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getActiveHostCount() {
/*  86 */     return this.activeHosts;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getClosedHosts() {
/*  91 */     return this.closedHosts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTotalLogicalConnectionCount() {
/*  99 */     return this.connections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getActiveLogicalConnectionCount() {
/* 106 */     return this.activeConnections;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getActivePhysicalConnectionCount() {
/* 112 */     long connections = 0L;
/* 113 */     Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
/* 114 */     synchronized (this.connectionProxies) {
/* 115 */       proxyMap.putAll(this.connectionProxies);
/*     */     } 
/* 117 */     Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
/* 118 */     while (i.hasNext()) {
/* 119 */       LoadBalancingConnectionProxy proxy = (LoadBalancingConnectionProxy)((Map.Entry)i.next()).getValue();
/* 120 */       connections += proxy.getActivePhysicalConnectionCount();
/*     */     } 
/*     */     
/* 123 */     return connections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTotalPhysicalConnectionCount() {
/* 130 */     long allConnections = this.closedProxyTotalPhysicalConnections;
/* 131 */     Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
/* 132 */     synchronized (this.connectionProxies) {
/* 133 */       proxyMap.putAll(this.connectionProxies);
/*     */     } 
/* 135 */     Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
/* 136 */     while (i.hasNext()) {
/* 137 */       LoadBalancingConnectionProxy proxy = (LoadBalancingConnectionProxy)((Map.Entry)i.next()).getValue();
/* 138 */       allConnections += proxy.getTotalPhysicalConnectionCount();
/*     */     } 
/*     */     
/* 141 */     return allConnections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTotalTransactionCount() {
/* 149 */     long transactions = this.closedProxyTotalTransactions;
/* 150 */     Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
/* 151 */     synchronized (this.connectionProxies) {
/* 152 */       proxyMap.putAll(this.connectionProxies);
/*     */     } 
/* 154 */     Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
/* 155 */     while (i.hasNext()) {
/* 156 */       LoadBalancingConnectionProxy proxy = (LoadBalancingConnectionProxy)((Map.Entry)i.next()).getValue();
/* 157 */       transactions += proxy.getTransactionCount();
/*     */     } 
/*     */     
/* 160 */     return transactions;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeConnectionProxy(LoadBalancingConnectionProxy proxy) {
/* 165 */     this.activeConnections--;
/* 166 */     this.connectionProxies.remove(new Long(proxy.getConnectionGroupProxyID()));
/* 167 */     this.closedProxyTotalPhysicalConnections += proxy.getTotalPhysicalConnectionCount();
/* 168 */     this.closedProxyTotalTransactions += proxy.getTransactionCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeHost(String host) throws SQLException {
/* 173 */     removeHost(host, false);
/*     */   }
/*     */   
/*     */   public void removeHost(String host, boolean killExistingConnections) throws SQLException {
/* 177 */     removeHost(host, killExistingConnections, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeHost(String host, boolean killExistingConnections, boolean waitForGracefulFailover) throws SQLException {
/* 184 */     if (this.activeHosts == 1) {
/* 185 */       throw SQLError.createSQLException("Cannot remove host, only one configured host active.", null);
/*     */     }
/*     */     
/* 188 */     if (this.hostList.remove(host)) {
/* 189 */       this.activeHosts--;
/*     */     } else {
/* 191 */       throw SQLError.createSQLException("Host is not configured: " + host, null);
/*     */     } 
/*     */     
/* 194 */     if (killExistingConnections) {
/*     */       
/* 196 */       Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
/* 197 */       synchronized (this.connectionProxies) {
/* 198 */         proxyMap.putAll(this.connectionProxies);
/*     */       } 
/*     */       
/* 201 */       Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
/* 202 */       while (i.hasNext()) {
/* 203 */         LoadBalancingConnectionProxy proxy = (LoadBalancingConnectionProxy)((Map.Entry)i.next()).getValue();
/* 204 */         if (waitForGracefulFailover) {
/* 205 */           proxy.removeHostWhenNotInUse(host); continue;
/*     */         } 
/* 207 */         proxy.removeHost(host);
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     this.closedHosts.add(host);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addHost(String host) {
/* 216 */     addHost(host, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHost(String host, boolean forExisting) {
/* 225 */     synchronized (this) {
/* 226 */       if (this.hostList.add(host)) {
/* 227 */         this.activeHosts++;
/*     */       }
/*     */     } 
/*     */     
/* 231 */     if (!forExisting) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 237 */     Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
/* 238 */     synchronized (this.connectionProxies) {
/* 239 */       proxyMap.putAll(this.connectionProxies);
/*     */     } 
/*     */     
/* 242 */     Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
/* 243 */     while (i.hasNext()) {
/* 244 */       LoadBalancingConnectionProxy proxy = (LoadBalancingConnectionProxy)((Map.Entry)i.next()).getValue();
/* 245 */       proxy.addHost(host);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\ConnectionGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */