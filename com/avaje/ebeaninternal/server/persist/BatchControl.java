/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BatchControl
/*     */ {
/*  49 */   private static final Logger logger = Logger.getLogger(BatchControl.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static final BatchDepthComparator depthComparator = new BatchDepthComparator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SpiTransaction transaction;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private final BatchedPstmtHolder pstmtHolder = new BatchedPstmtHolder();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int batchSize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean getGeneratedKeys;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean batchFlushOnMixed = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private final BatchedBeanControl beanControl;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BatchControl(SpiTransaction t, int batchSize, boolean getGenKeys) {
/*  89 */     this.transaction = t;
/*  90 */     this.batchSize = batchSize;
/*  91 */     this.getGeneratedKeys = getGenKeys;
/*  92 */     this.beanControl = new BatchedBeanControl(t, this);
/*  93 */     this.transaction.setBatchControl(this);
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
/*     */   public void setBatchFlushOnMixed(boolean flushBatchOnMixed) {
/* 107 */     this.batchFlushOnMixed = flushBatchOnMixed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBatchSize() {
/* 114 */     return this.batchSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBatchSize(int batchSize) {
/* 124 */     if (batchSize > 1) {
/* 125 */       this.batchSize = batchSize;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGetGeneratedKeys(Boolean getGeneratedKeys) {
/* 136 */     if (getGeneratedKeys != null) {
/* 137 */       this.getGeneratedKeys = getGeneratedKeys.booleanValue();
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
/*     */   public int executeStatementOrBatch(PersistRequest request, boolean batch) {
/* 150 */     if (!batch || (this.batchFlushOnMixed && !this.beanControl.isEmpty()))
/*     */     {
/* 152 */       flush();
/*     */     }
/* 154 */     if (!batch)
/*     */     {
/* 156 */       return request.executeNow();
/*     */     }
/*     */     
/* 159 */     if (this.pstmtHolder.getMaxSize() >= this.batchSize) {
/* 160 */       flush();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 165 */     request.executeNow();
/* 166 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeOrQueue(PersistRequestBean<?> request, boolean batch) {
/* 176 */     if (!batch || (this.batchFlushOnMixed && !this.pstmtHolder.isEmpty()))
/*     */     {
/* 178 */       flush();
/*     */     }
/* 180 */     if (!batch) {
/* 181 */       return request.executeNow();
/*     */     }
/*     */ 
/*     */     
/* 185 */     ArrayList<PersistRequest> persistList = this.beanControl.getPersistList(request);
/* 186 */     if (persistList == null) {
/*     */ 
/*     */       
/* 189 */       if (logger.isLoggable(Level.FINE)) {
/* 190 */         logger.fine("Bean instance already in this batch: " + request.getBean());
/*     */       }
/* 192 */       return -1;
/*     */     } 
/*     */     
/* 195 */     if (persistList.size() >= this.batchSize) {
/*     */       
/* 197 */       flush();
/*     */ 
/*     */ 
/*     */       
/* 201 */       persistList = this.beanControl.getPersistList(request);
/*     */     } 
/*     */     
/* 204 */     persistList.add(request);
/* 205 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BatchedPstmtHolder getPstmtHolder() {
/* 212 */     return this.pstmtHolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 219 */     return (this.beanControl.isEmpty() && this.pstmtHolder.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flushPstmtHolder() {
/* 226 */     this.pstmtHolder.flush(this.getGeneratedKeys);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeNow(ArrayList<PersistRequest> list) {
/* 233 */     for (int i = 0; i < list.size(); i++) {
/* 234 */       ((PersistRequest)list.get(i)).executeNow();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws PersistenceException {
/* 243 */     if (!this.pstmtHolder.isEmpty())
/*     */     {
/* 245 */       flushPstmtHolder();
/*     */     }
/* 247 */     if (this.beanControl.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 253 */     BatchedBeanHolder[] bsArray = this.beanControl.getArray();
/*     */ 
/*     */     
/* 256 */     Arrays.sort(bsArray, depthComparator);
/*     */     
/* 258 */     if (this.transaction.isLogSummary()) {
/* 259 */       this.transaction.logInternal("BatchControl flush " + Arrays.toString((Object[])bsArray));
/*     */     }
/* 261 */     for (int i = 0; i < bsArray.length; i++) {
/* 262 */       BatchedBeanHolder bs = bsArray[i];
/* 263 */       bs.executeNow();
/*     */       
/* 265 */       flushPstmtHolder();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BatchControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */