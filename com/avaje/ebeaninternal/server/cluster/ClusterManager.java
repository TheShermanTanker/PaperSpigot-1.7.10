/*     */ package com.avaje.ebeaninternal.server.cluster;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.cluster.mcast.McastClusterManager;
/*     */ import com.avaje.ebeaninternal.server.cluster.socket.SocketClusterBroadcast;
/*     */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
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
/*     */ public class ClusterManager
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(ClusterManager.class.getName());
/*     */   
/*  38 */   private final ConcurrentHashMap<String, EbeanServer> serverMap = new ConcurrentHashMap<String, EbeanServer>();
/*     */   
/*  40 */   private final Object monitor = new Object();
/*     */   
/*     */   private final ClusterBroadcast broadcast;
/*     */   
/*     */   private boolean started;
/*     */ 
/*     */   
/*     */   public ClusterManager() {
/*  48 */     String clusterType = GlobalProperties.get("ebean.cluster.type", null);
/*  49 */     if (clusterType == null || clusterType.trim().length() == 0) {
/*     */       
/*  51 */       this.broadcast = null;
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*  56 */         if ("mcast".equalsIgnoreCase(clusterType)) {
/*  57 */           this.broadcast = (ClusterBroadcast)new McastClusterManager();
/*     */         }
/*  59 */         else if ("socket".equalsIgnoreCase(clusterType)) {
/*  60 */           this.broadcast = (ClusterBroadcast)new SocketClusterBroadcast();
/*     */         } else {
/*     */           
/*  63 */           logger.info("Clustering using [" + clusterType + "]");
/*  64 */           this.broadcast = (ClusterBroadcast)ClassUtil.newInstance(clusterType);
/*     */         }
/*     */       
/*  67 */       } catch (Exception e) {
/*  68 */         String msg = "Error initialising ClusterManager type [" + clusterType + "]";
/*  69 */         logger.log(Level.SEVERE, msg, e);
/*  70 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerServer(EbeanServer server) {
/*  76 */     synchronized (this.monitor) {
/*  77 */       if (!this.started) {
/*  78 */         startup();
/*     */       }
/*  80 */       this.serverMap.put(server.getName(), server);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EbeanServer getServer(String name) {
/*  85 */     synchronized (this.monitor) {
/*  86 */       return this.serverMap.get(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startup() {
/*  91 */     this.started = true;
/*  92 */     if (this.broadcast != null) {
/*  93 */       this.broadcast.startup(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClustering() {
/* 101 */     return (this.broadcast != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcast(RemoteTransactionEvent remoteTransEvent) {
/* 108 */     if (this.broadcast != null) {
/* 109 */       this.broadcast.broadcast(remoteTransEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 117 */     if (this.broadcast != null) {
/* 118 */       logger.info("ClusterManager shutdown ");
/* 119 */       this.broadcast.shutdown();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\ClusterManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */