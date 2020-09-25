/*     */ package com.avaje.ebeaninternal.server.loadcontext;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
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
/*     */ public class DLoadWeakList<T>
/*     */   implements DLoadList<T>
/*     */ {
/*  30 */   private static final Logger logger = Logger.getLogger(DLoadWeakList.class.getName());
/*     */   
/*  32 */   protected final ArrayList<WeakReference<T>> list = new ArrayList<WeakReference<T>>();
/*     */ 
/*     */ 
/*     */   
/*     */   protected int removedFromTop;
/*     */ 
/*     */ 
/*     */   
/*     */   public int add(T e) {
/*  41 */     synchronized (this) {
/*  42 */       int i = this.list.size();
/*  43 */       this.list.add(new WeakReference<T>(e));
/*  44 */       return i;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeEntry(int position) {
/*  49 */     synchronized (this) {
/*  50 */       WeakReference<T> wref = this.list.get(position);
/*  51 */       if (wref == null) {
/*  52 */         logger.log(Level.WARNING, "removeEntry found no WeakReference for position[" + position + "]");
/*     */       } else {
/*     */         
/*  55 */         this.list.set(position, null);
/*  56 */         T object = wref.get();
/*  57 */         if (object == null) {
/*  58 */           logger.log(Level.WARNING, "removeEntry found no Object held by WeakReference for position[" + position + "]");
/*     */         }
/*     */       } 
/*  61 */       if (position == this.removedFromTop) {
/*  62 */         this.removedFromTop++;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<T> getNextBatch(int batchSize) {
/*  68 */     if (this.removedFromTop >= this.list.size()) {
/*  69 */       return new ArrayList<T>(0);
/*     */     }
/*  71 */     return getLoadBatch(this.removedFromTop, batchSize, true);
/*     */   }
/*     */   
/*     */   public List<T> getLoadBatch(int position, int batchSize) {
/*  75 */     return getLoadBatch(position, batchSize, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<T> getLoadBatch(int position, int batchSize, boolean ignoreMissing) {
/*  80 */     synchronized (this) {
/*  81 */       if (batchSize < 1) {
/*  82 */         throw new RuntimeException("batchSize " + batchSize + " < 1 ??!!");
/*     */       }
/*     */       
/*  85 */       ArrayList<T> batch = new ArrayList<T>();
/*     */       
/*  87 */       if (!addObjectToBatchAt(batch, position) && !ignoreMissing) {
/*  88 */         String msg = "getLoadBatch position[" + position + "] didn't find a bean in the list?";
/*  89 */         throw new IllegalStateException(msg);
/*     */       } 
/*     */       int i;
/*  92 */       for (i = position; i < this.list.size(); i++) {
/*  93 */         addObjectToBatchAt(batch, i);
/*  94 */         if (batch.size() == batchSize)
/*     */         {
/*  96 */           return batch;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 101 */       for (i = this.removedFromTop; i < position; i++) {
/* 102 */         addObjectToBatchAt(batch, i);
/* 103 */         if (batch.size() == batchSize)
/*     */         {
/* 105 */           return batch;
/*     */         }
/*     */       } 
/*     */       
/* 109 */       return batch;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean addObjectToBatchAt(ArrayList<T> batch, int i) {
/* 115 */     boolean found = false;
/* 116 */     WeakReference<T> wref = this.list.get(i);
/* 117 */     if (wref != null) {
/* 118 */       T object = wref.get();
/* 119 */       if (object == null) {
/* 120 */         logger.log(Level.WARNING, "Bean is null from weak reference");
/*     */       } else {
/* 122 */         found = true;
/* 123 */         batch.add(object);
/*     */       } 
/*     */       
/* 126 */       this.list.set(i, null);
/*     */     } 
/* 128 */     if (i == this.removedFromTop) {
/* 129 */       this.removedFromTop++;
/*     */     }
/* 131 */     return found;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\loadcontext\DLoadWeakList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */