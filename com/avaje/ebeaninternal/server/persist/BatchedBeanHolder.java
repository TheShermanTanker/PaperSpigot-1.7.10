/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
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
/*     */ public class BatchedBeanHolder
/*     */ {
/*     */   private final BatchControl control;
/*     */   private final String shortDesc;
/*     */   private final int order;
/*     */   private ArrayList<PersistRequest> inserts;
/*     */   private ArrayList<PersistRequest> updates;
/*     */   private ArrayList<PersistRequest> deletes;
/*  70 */   private HashSet<Integer> beanHashCodes = new HashSet<Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BatchedBeanHolder(BatchControl control, BeanDescriptor<?> beanDescriptor, int order) {
/*  76 */     this.control = control;
/*  77 */     this.shortDesc = beanDescriptor.getName() + ":" + order;
/*  78 */     this.order = order;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrder() {
/*  85 */     return this.order;
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
/*     */   public void executeNow() {
/* 101 */     if (this.inserts != null && !this.inserts.isEmpty()) {
/* 102 */       this.control.executeNow(this.inserts);
/* 103 */       this.inserts.clear();
/*     */     } 
/* 105 */     if (this.updates != null && !this.updates.isEmpty()) {
/* 106 */       this.control.executeNow(this.updates);
/* 107 */       this.updates.clear();
/*     */     } 
/* 109 */     if (this.deletes != null && !this.deletes.isEmpty()) {
/* 110 */       this.control.executeNow(this.deletes);
/* 111 */       this.deletes.clear();
/*     */     } 
/* 113 */     this.beanHashCodes.clear();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 117 */     return this.shortDesc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<PersistRequest> getList(PersistRequestBean<?> request) {
/* 125 */     Integer objHashCode = Integer.valueOf(System.identityHashCode(request.getBean()));
/*     */     
/* 127 */     if (!this.beanHashCodes.add(objHashCode))
/*     */     {
/*     */ 
/*     */       
/* 131 */       return null;
/*     */     }
/*     */     
/* 134 */     switch (request.getType()) {
/*     */       case INSERT:
/* 136 */         if (this.inserts == null) {
/* 137 */           this.inserts = new ArrayList<PersistRequest>();
/*     */         }
/* 139 */         return this.inserts;
/*     */       
/*     */       case UPDATE:
/* 142 */         if (this.updates == null) {
/* 143 */           this.updates = new ArrayList<PersistRequest>();
/*     */         }
/* 145 */         return this.updates;
/*     */       
/*     */       case DELETE:
/* 148 */         if (this.deletes == null) {
/* 149 */           this.deletes = new ArrayList<PersistRequest>();
/*     */         }
/* 151 */         return this.deletes;
/*     */     } 
/*     */     
/* 154 */     throw new RuntimeException("Invalid type code " + request.getType());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BatchedBeanHolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */