/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class TransactionLogBuffer
/*     */ {
/*     */   private final String transactionId;
/*     */   private final ArrayList<LogEntry> buffer;
/*     */   private final int maxSize;
/*     */   private int currentSize;
/*     */   
/*     */   public TransactionLogBuffer(int maxSize, String transactionId) {
/*  50 */     this.maxSize = maxSize;
/*  51 */     this.transactionId = transactionId;
/*  52 */     this.buffer = new ArrayList<LogEntry>(maxSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionLogBuffer newBuffer() {
/*  59 */     return new TransactionLogBuffer(this.maxSize, this.transactionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTransactionId() {
/*  66 */     return this.transactionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(String msg) {
/*  73 */     this.buffer.add(new LogEntry(msg)); return 
/*  74 */       (++this.currentSize >= this.maxSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  81 */     return this.buffer.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<LogEntry> messages() {
/*  88 */     return this.buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class LogEntry
/*     */   {
/*     */     private final long timestamp;
/*     */ 
/*     */     
/*     */     private final String msg;
/*     */ 
/*     */ 
/*     */     
/*     */     public LogEntry(String msg) {
/* 103 */       this.timestamp = System.currentTimeMillis();
/* 104 */       this.msg = msg;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getTimestamp() {
/* 111 */       return this.timestamp;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getMsg() {
/* 118 */       return this.msg;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\TransactionLogBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */