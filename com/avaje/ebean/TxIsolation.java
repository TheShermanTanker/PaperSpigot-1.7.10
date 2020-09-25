/*     */ package com.avaje.ebean;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum TxIsolation
/*     */ {
/*  43 */   READ_COMMITED(2),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   READ_UNCOMMITTED(1),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   REPEATABLE_READ(4),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   SERIALIZABLE(8),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   NONE(0),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   DEFAULT(-1);
/*     */   
/*     */   final int level;
/*     */   
/*     */   TxIsolation(int level) {
/*  74 */     this.level = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLevel() {
/*  84 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TxIsolation fromLevel(int connectionIsolationLevel) {
/*  95 */     switch (connectionIsolationLevel) {
/*     */       case 1:
/*  97 */         return READ_UNCOMMITTED;
/*     */       
/*     */       case 2:
/* 100 */         return READ_COMMITED;
/*     */       
/*     */       case 4:
/* 103 */         return REPEATABLE_READ;
/*     */       
/*     */       case 8:
/* 106 */         return SERIALIZABLE;
/*     */       
/*     */       case 0:
/* 109 */         return NONE;
/*     */       
/*     */       case -1:
/* 112 */         return DEFAULT;
/*     */     } 
/*     */     
/* 115 */     throw new RuntimeException("Unknown isolation level " + connectionIsolationLevel);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\TxIsolation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */