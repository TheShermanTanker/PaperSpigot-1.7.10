/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.SpiUpdate;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
/*     */ import com.avaje.ebeaninternal.server.persist.PersistExecute;
/*     */ import java.sql.SQLException;
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
/*     */ public final class PersistRequestOrmUpdate
/*     */   extends PersistRequest
/*     */ {
/*     */   private final BeanDescriptor<?> beanDescriptor;
/*     */   private SpiUpdate<?> ormUpdate;
/*     */   private int rowCount;
/*     */   private String bindLog;
/*     */   
/*     */   public PersistRequestOrmUpdate(SpiEbeanServer server, BeanManager<?> mgr, SpiUpdate<?> ormUpdate, SpiTransaction t, PersistExecute persistExecute) {
/*  51 */     super(server, t, persistExecute);
/*  52 */     this.beanDescriptor = mgr.getBeanDescriptor();
/*  53 */     this.ormUpdate = ormUpdate;
/*     */   }
/*     */   
/*     */   public BeanDescriptor<?> getBeanDescriptor() {
/*  57 */     return this.beanDescriptor;
/*     */   }
/*     */ 
/*     */   
/*     */   public int executeNow() {
/*  62 */     return this.persistExecute.executeOrmUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int executeOrQueue() {
/*  67 */     return executeStatement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiUpdate<?> getOrmUpdate() {
/*  75 */     return this.ormUpdate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkRowCount(int count) throws SQLException {
/*  82 */     this.rowCount = count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useGeneratedKeys() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGeneratedKey(Object idValue) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBindLog(String bindLog) {
/* 102 */     this.bindLog = bindLog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postExecute() throws SQLException {
/* 110 */     SpiUpdate.OrmUpdateType ormUpdateType = this.ormUpdate.getOrmUpdateType();
/* 111 */     String tableName = this.ormUpdate.getBaseTable();
/*     */     
/* 113 */     if (this.transaction.isLogSummary()) {
/* 114 */       String m = ormUpdateType + " table[" + tableName + "] rows[" + this.rowCount + "] bind[" + this.bindLog + "]";
/* 115 */       this.transaction.logInternal(m);
/*     */     } 
/*     */     
/* 118 */     if (this.ormUpdate.isNotifyCache())
/*     */     {
/*     */ 
/*     */       
/* 122 */       switch (ormUpdateType) {
/*     */         case INSERT:
/* 124 */           this.transaction.getEvent().add(tableName, true, false, false);
/*     */           break;
/*     */         case UPDATE:
/* 127 */           this.transaction.getEvent().add(tableName, false, true, false);
/*     */           break;
/*     */         case DELETE:
/* 130 */           this.transaction.getEvent().add(tableName, false, false, true);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\PersistRequestOrmUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */