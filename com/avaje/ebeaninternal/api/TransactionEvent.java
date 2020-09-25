/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanDelta;
/*     */ import com.avaje.ebeaninternal.server.transaction.DeleteByIdMap;
/*     */ import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ public class TransactionEvent
/*     */   implements Serializable
/*     */ {
/*  45 */   private static final Logger logger = Logger.getLogger(TransactionEvent.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 7230903304106097120L;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient boolean local = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean invalidateAll;
/*     */ 
/*     */   
/*     */   private TransactionEventTable eventTables;
/*     */ 
/*     */   
/*     */   private transient TransactionEventBeans eventBeans;
/*     */ 
/*     */   
/*     */   private transient List<BeanDelta> beanDeltas;
/*     */ 
/*     */   
/*     */   private transient DeleteByIdMap deleteByIdMap;
/*     */ 
/*     */   
/*     */   private transient Set<IndexInvalidate> indexInvalidations;
/*     */ 
/*     */   
/*     */   private transient Set<String> pauseIndexInvalidate;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInvalidateAll(boolean isInvalidateAll) {
/*  80 */     this.invalidateAll = isInvalidateAll;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInvalidateAll() {
/*  88 */     return this.invalidateAll;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pauseIndexInvalidate(Class<?> beanType) {
/*  95 */     if (this.pauseIndexInvalidate == null) {
/*  96 */       this.pauseIndexInvalidate = new HashSet<String>();
/*     */     }
/*  98 */     this.pauseIndexInvalidate.add(beanType.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeIndexInvalidate(Class<?> beanType) {
/* 105 */     if (this.pauseIndexInvalidate != null) {
/* 106 */       this.pauseIndexInvalidate.remove(beanType.getName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addIndexInvalidate(IndexInvalidate indexEvent) {
/* 114 */     if (this.pauseIndexInvalidate != null && this.pauseIndexInvalidate.contains(indexEvent.getIndexName())) {
/* 115 */       logger.fine("--- IGNORE Invalidate on " + indexEvent.getIndexName());
/*     */       return;
/*     */     } 
/* 118 */     if (this.indexInvalidations == null) {
/* 119 */       this.indexInvalidations = new HashSet<IndexInvalidate>();
/*     */     }
/* 121 */     this.indexInvalidations.add(indexEvent);
/*     */   }
/*     */   
/*     */   public void addDeleteById(BeanDescriptor<?> desc, Object id) {
/* 125 */     if (this.deleteByIdMap == null) {
/* 126 */       this.deleteByIdMap = new DeleteByIdMap();
/*     */     }
/* 128 */     this.deleteByIdMap.add(desc, id);
/*     */   }
/*     */   
/*     */   public void addDeleteByIdList(BeanDescriptor<?> desc, List<Object> idList) {
/* 132 */     if (this.deleteByIdMap == null) {
/* 133 */       this.deleteByIdMap = new DeleteByIdMap();
/*     */     }
/* 135 */     this.deleteByIdMap.addList(desc, idList);
/*     */   }
/*     */   
/*     */   public DeleteByIdMap getDeleteByIdMap() {
/* 139 */     return this.deleteByIdMap;
/*     */   }
/*     */   
/*     */   public void addBeanDelta(BeanDelta delta) {
/* 143 */     if (this.beanDeltas == null) {
/* 144 */       this.beanDeltas = new ArrayList<BeanDelta>();
/*     */     }
/* 146 */     this.beanDeltas.add(delta);
/*     */   }
/*     */   
/*     */   public List<BeanDelta> getBeanDeltas() {
/* 150 */     return this.beanDeltas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocal() {
/* 158 */     return this.local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionEventBeans getEventBeans() {
/* 165 */     return this.eventBeans;
/*     */   }
/*     */   
/*     */   public TransactionEventTable getEventTables() {
/* 169 */     return this.eventTables;
/*     */   }
/*     */   
/*     */   public Set<IndexInvalidate> getIndexInvalidations() {
/* 173 */     return this.indexInvalidations;
/*     */   }
/*     */   
/*     */   public void add(String tableName, boolean inserts, boolean updates, boolean deletes) {
/* 177 */     if (this.eventTables == null) {
/* 178 */       this.eventTables = new TransactionEventTable();
/*     */     }
/* 180 */     this.eventTables.add(tableName, inserts, updates, deletes);
/*     */   }
/*     */   
/*     */   public void add(TransactionEventTable table) {
/* 184 */     if (this.eventTables == null) {
/* 185 */       this.eventTables = new TransactionEventTable();
/*     */     }
/* 187 */     this.eventTables.add(table);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(PersistRequestBean<?> request) {
/* 195 */     if (request.isNotify(this)) {
/*     */       
/* 197 */       if (this.eventBeans == null) {
/* 198 */         this.eventBeans = new TransactionEventBeans();
/*     */       }
/* 200 */       this.eventBeans.add(request);
/*     */     } 
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
/*     */   public void notifyCache() {
/* 213 */     if (this.eventBeans != null) {
/* 214 */       this.eventBeans.notifyCache();
/*     */     }
/* 216 */     if (this.deleteByIdMap != null)
/* 217 */       this.deleteByIdMap.notifyCache(); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\TransactionEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */