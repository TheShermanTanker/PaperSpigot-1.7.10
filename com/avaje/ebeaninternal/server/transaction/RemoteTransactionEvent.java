/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
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
/*     */ public class RemoteTransactionEvent
/*     */   implements Runnable
/*     */ {
/*  34 */   private List<BeanPersistIds> beanPersistList = new ArrayList<BeanPersistIds>();
/*     */   
/*     */   private List<TransactionEventTable.TableIUD> tableList;
/*     */   
/*     */   private List<BeanDeltaList> beanDeltaLists;
/*     */   
/*     */   private BeanDeltaMap beanDeltaMap;
/*     */   
/*     */   private List<IndexEvent> indexEventList;
/*     */   
/*     */   private Set<IndexInvalidate> indexInvalidations;
/*     */   
/*     */   private DeleteByIdMap deleteByIdMap;
/*     */   
/*     */   private String serverName;
/*     */   
/*     */   private transient SpiEbeanServer server;
/*     */   
/*     */   public RemoteTransactionEvent(String serverName) {
/*  53 */     this.serverName = serverName;
/*     */   }
/*     */   
/*     */   public RemoteTransactionEvent(SpiEbeanServer server) {
/*  57 */     this.server = server;
/*     */   }
/*     */   
/*     */   public void run() {
/*  61 */     this.server.remoteTransactionEvent(this);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  65 */     StringBuilder sb = new StringBuilder();
/*  66 */     if (this.beanDeltaMap != null) {
/*  67 */       sb.append(this.beanDeltaMap);
/*     */     }
/*  69 */     sb.append(this.beanPersistList);
/*  70 */     if (this.tableList != null) {
/*  71 */       sb.append(this.tableList);
/*     */     }
/*  73 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/*  78 */     if (this.indexInvalidations != null) {
/*  79 */       for (IndexInvalidate indexInvalidate : this.indexInvalidations) {
/*  80 */         indexInvalidate.writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */     
/*  84 */     if (this.tableList != null) {
/*  85 */       for (int i = 0; i < this.tableList.size(); i++) {
/*  86 */         ((TransactionEventTable.TableIUD)this.tableList.get(i)).writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */     
/*  90 */     if (this.deleteByIdMap != null) {
/*  91 */       for (BeanPersistIds deleteIds : this.deleteByIdMap.values()) {
/*  92 */         deleteIds.writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */     
/*  96 */     if (this.beanPersistList != null) {
/*  97 */       for (int i = 0; i < this.beanPersistList.size(); i++) {
/*  98 */         ((BeanPersistIds)this.beanPersistList.get(i)).writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */     
/* 102 */     if (this.beanDeltaLists != null) {
/* 103 */       for (int i = 0; i < this.beanDeltaLists.size(); i++) {
/* 104 */         ((BeanDeltaList)this.beanDeltaLists.get(i)).writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */     
/* 108 */     if (this.indexEventList != null) {
/* 109 */       for (int i = 0; i < this.indexEventList.size(); i++) {
/* 110 */         ((IndexEvent)this.indexEventList.get(i)).writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 116 */     return (this.beanPersistList.isEmpty() && (this.tableList == null || this.tableList.isEmpty()));
/*     */   }
/*     */   
/*     */   public void addBeanPersistIds(BeanPersistIds beanPersist) {
/* 120 */     this.beanPersistList.add(beanPersist);
/*     */   }
/*     */   
/*     */   public void addIndexInvalidate(IndexInvalidate indexInvalidate) {
/* 124 */     if (this.indexInvalidations == null) {
/* 125 */       this.indexInvalidations = new HashSet<IndexInvalidate>();
/*     */     }
/* 127 */     this.indexInvalidations.add(indexInvalidate);
/*     */   }
/*     */   
/*     */   public void addTableIUD(TransactionEventTable.TableIUD tableIud) {
/* 131 */     if (this.tableList == null) {
/* 132 */       this.tableList = new ArrayList<TransactionEventTable.TableIUD>(4);
/*     */     }
/* 134 */     this.tableList.add(tableIud);
/*     */   }
/*     */   
/*     */   public void addBeanDeltaList(BeanDeltaList deltaList) {
/* 138 */     if (this.beanDeltaLists == null) {
/* 139 */       this.beanDeltaLists = new ArrayList<BeanDeltaList>();
/*     */     }
/* 141 */     this.beanDeltaLists.add(deltaList);
/*     */   }
/*     */   
/*     */   public void addBeanDelta(BeanDelta beanDelta) {
/* 145 */     if (this.beanDeltaMap == null) {
/* 146 */       this.beanDeltaMap = new BeanDeltaMap();
/*     */     }
/* 148 */     this.beanDeltaMap.addBeanDelta(beanDelta);
/*     */   }
/*     */   
/*     */   public void addIndexEvent(IndexEvent indexEvent) {
/* 152 */     if (this.indexEventList == null) {
/* 153 */       this.indexEventList = new ArrayList<IndexEvent>(2);
/*     */     }
/* 155 */     this.indexEventList.add(indexEvent);
/*     */   }
/*     */   
/*     */   public String getServerName() {
/* 159 */     return this.serverName;
/*     */   }
/*     */   
/*     */   public SpiEbeanServer getServer() {
/* 163 */     return this.server;
/*     */   }
/*     */   
/*     */   public void setServer(SpiEbeanServer server) {
/* 167 */     this.server = server;
/*     */   }
/*     */   
/*     */   public DeleteByIdMap getDeleteByIdMap() {
/* 171 */     return this.deleteByIdMap;
/*     */   }
/*     */   
/*     */   public void setDeleteByIdMap(DeleteByIdMap deleteByIdMap) {
/* 175 */     this.deleteByIdMap = deleteByIdMap;
/*     */   }
/*     */   
/*     */   public Set<IndexInvalidate> getIndexInvalidations() {
/* 179 */     return this.indexInvalidations;
/*     */   }
/*     */   
/*     */   public List<IndexEvent> getIndexEventList() {
/* 183 */     return this.indexEventList;
/*     */   }
/*     */   
/*     */   public List<TransactionEventTable.TableIUD> getTableIUDList() {
/* 187 */     return this.tableList;
/*     */   }
/*     */   
/*     */   public List<BeanPersistIds> getBeanPersistList() {
/* 191 */     return this.beanPersistList;
/*     */   }
/*     */   
/*     */   public List<BeanDeltaList> getBeanDeltaLists() {
/* 195 */     if (this.beanDeltaMap != null) {
/* 196 */       this.beanDeltaLists.addAll(this.beanDeltaMap.deltaLists());
/*     */     }
/* 198 */     return this.beanDeltaLists;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\RemoteTransactionEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */