/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebean.event.BulkTableEvent;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.TransactionEvent;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventBeans;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PostCommitProcessing
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(PostCommitProcessing.class.getName());
/*     */ 
/*     */   
/*     */   private final ClusterManager clusterManager;
/*     */ 
/*     */   
/*     */   private final TransactionEvent event;
/*     */ 
/*     */   
/*     */   private final String serverName;
/*     */ 
/*     */   
/*     */   private final TransactionManager manager;
/*     */ 
/*     */   
/*     */   private final List<PersistRequestBean<?>> persistBeanRequests;
/*     */   
/*     */   private final BeanPersistIdMap beanPersistIdMap;
/*     */   
/*     */   private final RemoteTransactionEvent remoteTransactionEvent;
/*     */   
/*     */   private final DeleteByIdMap deleteByIdMap;
/*     */ 
/*     */   
/*     */   public PostCommitProcessing(ClusterManager clusterManager, TransactionManager manager, SpiTransaction transaction, TransactionEvent event) {
/*  69 */     this.clusterManager = clusterManager;
/*  70 */     this.manager = manager;
/*  71 */     this.serverName = manager.getServerName();
/*  72 */     this.event = event;
/*  73 */     this.deleteByIdMap = event.getDeleteByIdMap();
/*  74 */     this.persistBeanRequests = createPersistBeanRequests();
/*     */     
/*  76 */     this.beanPersistIdMap = createBeanPersistIdMap();
/*     */ 
/*     */     
/*  79 */     this.remoteTransactionEvent = createRemoteTransactionEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyLocalCacheIndex() {
/*  85 */     processTableEvents(this.event.getEventTables());
/*     */ 
/*     */     
/*  88 */     this.event.notifyCache();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processTableEvents(TransactionEventTable tableEvents) {
/*  97 */     if (tableEvents != null && !tableEvents.isEmpty()) {
/*     */       
/*  99 */       BeanDescriptorManager dm = this.manager.getBeanDescriptorManager();
/* 100 */       for (TransactionEventTable.TableIUD tableIUD : tableEvents.values()) {
/* 101 */         dm.cacheNotify(tableIUD);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void notifyCluster() {
/* 107 */     if (this.remoteTransactionEvent != null && !this.remoteTransactionEvent.isEmpty()) {
/*     */       
/* 109 */       if (this.manager.getClusterDebugLevel() > 0 || logger.isLoggable(Level.FINE)) {
/* 110 */         logger.info("Cluster Send: " + this.remoteTransactionEvent.toString());
/*     */       }
/*     */       
/* 113 */       this.clusterManager.broadcast(this.remoteTransactionEvent);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Runnable notifyPersistListeners() {
/* 118 */     return new Runnable() {
/*     */         public void run() {
/* 120 */           PostCommitProcessing.this.localPersistListenersNotify();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private void localPersistListenersNotify() {
/* 126 */     if (this.persistBeanRequests != null) {
/* 127 */       for (int i = 0; i < this.persistBeanRequests.size(); i++) {
/* 128 */         ((PersistRequestBean)this.persistBeanRequests.get(i)).notifyLocalPersistListener();
/*     */       }
/*     */     }
/* 131 */     TransactionEventTable eventTables = this.event.getEventTables();
/* 132 */     if (eventTables != null && !eventTables.isEmpty()) {
/* 133 */       BulkEventListenerMap map = this.manager.getBulkEventListenerMap();
/* 134 */       for (TransactionEventTable.TableIUD tableIUD : eventTables.values()) {
/* 135 */         map.process((BulkTableEvent)tableIUD);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<PersistRequestBean<?>> createPersistBeanRequests() {
/* 141 */     TransactionEventBeans eventBeans = this.event.getEventBeans();
/* 142 */     if (eventBeans != null) {
/* 143 */       return eventBeans.getRequests();
/*     */     }
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private BeanPersistIdMap createBeanPersistIdMap() {
/* 150 */     if (this.persistBeanRequests == null) {
/* 151 */       return null;
/*     */     }
/*     */     
/* 154 */     BeanPersistIdMap m = new BeanPersistIdMap();
/* 155 */     for (int i = 0; i < this.persistBeanRequests.size(); i++) {
/* 156 */       ((PersistRequestBean)this.persistBeanRequests.get(i)).addToPersistMap(m);
/*     */     }
/* 158 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   private RemoteTransactionEvent createRemoteTransactionEvent() {
/* 163 */     if (!this.clusterManager.isClustering()) {
/* 164 */       return null;
/*     */     }
/*     */     
/* 167 */     RemoteTransactionEvent remoteTransactionEvent = new RemoteTransactionEvent(this.serverName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     if (this.beanPersistIdMap != null) {
/* 176 */       for (BeanPersistIds beanPersist : this.beanPersistIdMap.values()) {
/* 177 */         remoteTransactionEvent.addBeanPersistIds(beanPersist);
/*     */       }
/*     */     }
/*     */     
/* 181 */     if (this.deleteByIdMap != null) {
/* 182 */       remoteTransactionEvent.setDeleteByIdMap(this.deleteByIdMap);
/*     */     }
/*     */     
/* 185 */     TransactionEventTable eventTables = this.event.getEventTables();
/* 186 */     if (eventTables != null && !eventTables.isEmpty()) {
/* 187 */       for (TransactionEventTable.TableIUD tableIUD : eventTables.values()) {
/* 188 */         remoteTransactionEvent.addTableIUD(tableIUD);
/*     */       }
/*     */     }
/*     */     
/* 192 */     Set<IndexInvalidate> indexInvalidations = this.event.getIndexInvalidations();
/* 193 */     if (indexInvalidations != null) {
/* 194 */       for (IndexInvalidate indexInvalidate : indexInvalidations) {
/* 195 */         remoteTransactionEvent.addIndexInvalidate(indexInvalidate);
/*     */       }
/*     */     }
/*     */     
/* 199 */     return remoteTransactionEvent;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\PostCommitProcessing.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */