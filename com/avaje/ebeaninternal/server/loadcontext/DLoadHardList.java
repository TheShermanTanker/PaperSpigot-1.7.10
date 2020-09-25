/*     */ package com.avaje.ebeaninternal.server.loadcontext;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class DLoadHardList<T>
/*     */   implements DLoadList<T>
/*     */ {
/*  29 */   private static final Logger logger = Logger.getLogger(DLoadHardList.class.getName());
/*     */   
/*  31 */   protected final ArrayList<T> list = new ArrayList<T>();
/*     */ 
/*     */ 
/*     */   
/*     */   protected int removedFromTop;
/*     */ 
/*     */ 
/*     */   
/*     */   public int add(T e) {
/*  40 */     synchronized (this) {
/*  41 */       int i = this.list.size();
/*  42 */       this.list.add(e);
/*  43 */       return i;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeEntry(int position) {
/*  48 */     synchronized (this) {
/*  49 */       T object = this.list.get(position);
/*  50 */       if (object == null) {
/*  51 */         logger.log(Level.WARNING, "removeEntry found no Object for position[" + position + "]");
/*     */       } else {
/*     */         
/*  54 */         this.list.set(position, null);
/*     */       } 
/*  56 */       if (position == this.removedFromTop) {
/*  57 */         this.removedFromTop++;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<T> getNextBatch(int batchSize) {
/*  63 */     if (this.removedFromTop >= this.list.size()) {
/*  64 */       return new ArrayList<T>(0);
/*     */     }
/*  66 */     return getLoadBatch(this.removedFromTop, batchSize, true);
/*     */   }
/*     */   
/*     */   public List<T> getLoadBatch(int position, int batchSize) {
/*  70 */     return getLoadBatch(position, batchSize, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<T> getLoadBatch(int position, int batchSize, boolean ignoreMissing) {
/*  75 */     synchronized (this) {
/*  76 */       if (batchSize < 1) {
/*  77 */         throw new RuntimeException("batchSize " + batchSize + " < 1 ??!!");
/*     */       }
/*     */       
/*  80 */       ArrayList<T> batch = new ArrayList<T>();
/*     */       
/*  82 */       if (!addObjectToBatchAt(batch, position) && !ignoreMissing) {
/*  83 */         String msg = "getLoadBatch position[" + position + "] didn't find a bean in the list?";
/*  84 */         throw new IllegalStateException(msg);
/*     */       } 
/*     */       int i;
/*  87 */       for (i = position; i < this.list.size(); i++) {
/*  88 */         addObjectToBatchAt(batch, i);
/*  89 */         if (batch.size() == batchSize)
/*     */         {
/*  91 */           return batch;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  96 */       for (i = this.removedFromTop; i < position; i++) {
/*  97 */         addObjectToBatchAt(batch, i);
/*  98 */         if (batch.size() == batchSize)
/*     */         {
/* 100 */           return batch;
/*     */         }
/*     */       } 
/*     */       
/* 104 */       return batch;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean addObjectToBatchAt(ArrayList<T> batch, int i) {
/* 110 */     boolean found = false;
/* 111 */     T object = this.list.get(i);
/* 112 */     if (object != null) {
/* 113 */       found = true;
/* 114 */       batch.add(object);
/*     */       
/* 116 */       this.list.set(i, null);
/*     */     } 
/*     */     
/* 119 */     if (i == this.removedFromTop) {
/* 120 */       this.removedFromTop++;
/*     */     }
/* 122 */     return found;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\loadcontext\DLoadHardList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */