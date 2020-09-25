/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.LogLevel;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import java.sql.Connection;
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
/*     */ public abstract class BeanRequest
/*     */ {
/*     */   final SpiEbeanServer ebeanServer;
/*     */   final String serverName;
/*     */   SpiTransaction transaction;
/*     */   boolean createdTransaction;
/*     */   boolean readOnly;
/*     */   
/*     */   public BeanRequest(SpiEbeanServer ebeanServer, SpiTransaction t) {
/*  51 */     this.ebeanServer = ebeanServer;
/*  52 */     this.serverName = ebeanServer.getName();
/*  53 */     this.transaction = t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void initTransIfRequired();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createImplicitTransIfRequired(boolean readOnlyTransaction) {
/*  73 */     if (this.transaction == null) {
/*  74 */       this.transaction = this.ebeanServer.getCurrentServerTransaction();
/*  75 */       if (this.transaction == null || !this.transaction.isActive()) {
/*     */         
/*  77 */         this.transaction = this.ebeanServer.createServerTransaction(false, -1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  84 */         this.createdTransaction = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void commitTransIfRequired() {
/*  93 */     if (this.createdTransaction) {
/*  94 */       if (this.readOnly) {
/*  95 */         this.transaction.rollback();
/*     */       } else {
/*  97 */         this.transaction.commit();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollbackTransIfRequired() {
/* 106 */     if (this.createdTransaction) {
/* 107 */       this.transaction.rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EbeanServer getEbeanServer() {
/* 116 */     return (EbeanServer)this.ebeanServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiTransaction getTransaction() {
/* 123 */     return this.transaction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/* 130 */     return this.transaction.getInternalConnection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLogSql() {
/* 137 */     return (this.transaction.getLogLevel().ordinal() >= LogLevel.SQL.ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLogSummary() {
/* 144 */     return (this.transaction.getLogLevel().ordinal() >= LogLevel.SUMMARY.ordinal());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\BeanRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */