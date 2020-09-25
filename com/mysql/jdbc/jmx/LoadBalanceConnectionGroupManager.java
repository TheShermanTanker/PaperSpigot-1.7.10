/*     */ package com.mysql.jdbc.jmx;
/*     */ 
/*     */ import com.mysql.jdbc.ConnectionGroupManager;
/*     */ import com.mysql.jdbc.SQLError;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.sql.SQLException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoadBalanceConnectionGroupManager
/*     */   implements LoadBalanceConnectionGroupManagerMBean
/*     */ {
/*     */   private boolean isJmxRegistered = false;
/*     */   
/*     */   public synchronized void registerJmx() throws SQLException {
/*  44 */     if (this.isJmxRegistered) {
/*     */       return;
/*     */     }
/*  47 */     MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
/*     */     try {
/*  49 */       ObjectName name = new ObjectName("com.mysql.jdbc.jmx:type=LoadBalanceConnectionGroupManager");
/*  50 */       mbs.registerMBean(this, name);
/*  51 */       this.isJmxRegistered = true;
/*  52 */     } catch (Exception e) {
/*  53 */       throw SQLError.createSQLException("Uable to register load-balance management bean with JMX", null, e, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addHost(String group, String host, boolean forExisting) {
/*     */     try {
/*  60 */       ConnectionGroupManager.addHost(group, host, forExisting);
/*  61 */     } catch (Exception e) {
/*  62 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getActiveHostCount(String group) {
/*  67 */     return ConnectionGroupManager.getActiveHostCount(group);
/*     */   }
/*     */   
/*     */   public long getActiveLogicalConnectionCount(String group) {
/*  71 */     return ConnectionGroupManager.getActiveLogicalConnectionCount(group);
/*     */   }
/*     */   
/*     */   public long getActivePhysicalConnectionCount(String group) {
/*  75 */     return ConnectionGroupManager.getActivePhysicalConnectionCount(group);
/*     */   }
/*     */   
/*     */   public int getTotalHostCount(String group) {
/*  79 */     return ConnectionGroupManager.getTotalHostCount(group);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTotalLogicalConnectionCount(String group) {
/*  84 */     return ConnectionGroupManager.getTotalLogicalConnectionCount(group);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTotalPhysicalConnectionCount(String group) {
/*  89 */     return ConnectionGroupManager.getTotalPhysicalConnectionCount(group);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTotalTransactionCount(String group) {
/*  94 */     return ConnectionGroupManager.getTotalTransactionCount(group);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeHost(String group, String host) throws SQLException {
/*  99 */     ConnectionGroupManager.removeHost(group, host);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getActiveHostsList(String group) {
/* 104 */     return ConnectionGroupManager.getActiveHostLists(group);
/*     */   }
/*     */   
/*     */   public String getRegisteredConnectionGroups() {
/* 108 */     return ConnectionGroupManager.getRegisteredConnectionGroups();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopNewConnectionsToHost(String group, String host) throws SQLException {
/* 113 */     ConnectionGroupManager.removeHost(group, host);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\jmx\LoadBalanceConnectionGroupManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */