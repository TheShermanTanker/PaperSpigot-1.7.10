/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
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
/*     */ public final class DefaultTransactionThreadLocal
/*     */ {
/*  31 */   private static ThreadLocal<TransactionMap> local = new ThreadLocal<TransactionMap>() {
/*     */       protected synchronized TransactionMap initialValue() {
/*  33 */         return new TransactionMap();
/*     */       }
/*     */     };
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
/*     */   private static TransactionMap.State getState(String serverName) {
/*  48 */     return ((TransactionMap)local.get()).getStateWithCreate(serverName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void set(String serverName, SpiTransaction trans) {
/*  55 */     getState(serverName).set(trans);
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
/*     */   public static void replace(String serverName, SpiTransaction trans) {
/*  68 */     getState(serverName).replace(trans);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SpiTransaction get(String serverName) {
/*  75 */     TransactionMap map = local.get();
/*  76 */     TransactionMap.State state = map.getState(serverName);
/*  77 */     SpiTransaction t = (state == null) ? null : state.transaction;
/*  78 */     if (map.isEmpty()) {
/*  79 */       local.remove();
/*     */     }
/*  81 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void commit(String serverName) {
/*  88 */     TransactionMap map = local.get();
/*  89 */     TransactionMap.State state = map.removeState(serverName);
/*  90 */     if (state == null) {
/*  91 */       throw new IllegalStateException("No current transaction for [" + serverName + "]");
/*     */     }
/*  93 */     state.commit();
/*  94 */     if (map.isEmpty()) {
/*  95 */       local.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void rollback(String serverName) {
/* 103 */     TransactionMap map = local.get();
/* 104 */     TransactionMap.State state = map.removeState(serverName);
/* 105 */     if (state == null) {
/* 106 */       throw new IllegalStateException("No current transaction for [" + serverName + "]");
/*     */     }
/* 108 */     state.rollback();
/* 109 */     if (map.isEmpty()) {
/* 110 */       local.remove();
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
/*     */   public static void end(String serverName) {
/* 137 */     TransactionMap map = local.get();
/* 138 */     TransactionMap.State state = map.removeState(serverName);
/* 139 */     if (state != null) {
/* 140 */       state.end();
/*     */     }
/* 142 */     if (map.isEmpty())
/* 143 */       local.remove(); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\DefaultTransactionThreadLocal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */