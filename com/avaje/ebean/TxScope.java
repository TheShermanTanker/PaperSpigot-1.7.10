/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ public final class TxScope
/*     */ {
/*     */   TxType type;
/*     */   String serverName;
/*     */   TxIsolation isolation;
/*     */   boolean readOnly;
/*     */   ArrayList<Class<? extends Throwable>> rollbackFor;
/*     */   ArrayList<Class<? extends Throwable>> noRollbackFor;
/*     */   
/*     */   public static TxScope required() {
/*  40 */     return new TxScope(TxType.REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TxScope requiresNew() {
/*  47 */     return new TxScope(TxType.REQUIRES_NEW);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TxScope mandatory() {
/*  54 */     return new TxScope(TxType.MANDATORY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TxScope supports() {
/*  61 */     return new TxScope(TxType.SUPPORTS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TxScope notSupported() {
/*  68 */     return new TxScope(TxType.NOT_SUPPORTED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TxScope never() {
/*  75 */     return new TxScope(TxType.NEVER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope() {
/*  82 */     this.type = TxType.REQUIRED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope(TxType type) {
/*  89 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  96 */     return "TxScope[" + this.type + "] readOnly[" + this.readOnly + "] isolation[" + this.isolation + "] serverName[" + this.serverName + "] rollbackFor[" + this.rollbackFor + "] noRollbackFor[" + this.noRollbackFor + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxType getType() {
/* 104 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setType(TxType type) {
/* 111 */     this.type = type;
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadonly() {
/* 119 */     return this.readOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setReadOnly(boolean readOnly) {
/* 126 */     this.readOnly = readOnly;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxIsolation getIsolation() {
/* 134 */     return this.isolation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setIsolation(TxIsolation isolation) {
/* 141 */     this.isolation = isolation;
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 151 */     return this.serverName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setServerName(String serverName) {
/* 160 */     this.serverName = serverName;
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<? extends Throwable>> getRollbackFor() {
/* 168 */     return this.rollbackFor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setRollbackFor(Class<? extends Throwable> rollbackThrowable) {
/* 175 */     if (this.rollbackFor == null) {
/* 176 */       this.rollbackFor = new ArrayList<Class<? extends Throwable>>(2);
/*     */     }
/* 178 */     this.rollbackFor.add(rollbackThrowable);
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setRollbackFor(Class<?>[] rollbackThrowables) {
/* 187 */     if (this.rollbackFor == null) {
/* 188 */       this.rollbackFor = new ArrayList<Class<? extends Throwable>>(rollbackThrowables.length);
/*     */     }
/* 190 */     for (int i = 0; i < rollbackThrowables.length; i++) {
/* 191 */       this.rollbackFor.add(rollbackThrowables[i]);
/*     */     }
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<? extends Throwable>> getNoRollbackFor() {
/* 200 */     return this.noRollbackFor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setNoRollbackFor(Class<? extends Throwable> noRollback) {
/* 209 */     if (this.noRollbackFor == null) {
/* 210 */       this.noRollbackFor = new ArrayList<Class<? extends Throwable>>(2);
/*     */     }
/* 212 */     this.noRollbackFor.add(noRollback);
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TxScope setNoRollbackFor(Class<?>[] noRollbacks) {
/* 221 */     if (this.noRollbackFor == null) {
/* 222 */       this.noRollbackFor = new ArrayList<Class<? extends Throwable>>(noRollbacks.length);
/*     */     }
/* 224 */     for (int i = 0; i < noRollbacks.length; i++) {
/* 225 */       this.noRollbackFor.add(noRollbacks[i]);
/*     */     }
/* 227 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\TxScope.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */