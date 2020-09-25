/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
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
/*     */ public class DeleteUnloadedForeignKeys
/*     */ {
/*  45 */   private final List<BeanPropertyAssocOne<?>> propList = new ArrayList<BeanPropertyAssocOne<?>>(4);
/*     */   
/*     */   private final SpiEbeanServer server;
/*     */   
/*     */   private final PersistRequestBean<?> request;
/*     */   
/*     */   private Object beanWithForeignKeys;
/*     */   
/*     */   public DeleteUnloadedForeignKeys(SpiEbeanServer server, PersistRequestBean<?> request) {
/*  54 */     this.server = server;
/*  55 */     this.request = request;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  59 */     return this.propList.isEmpty();
/*     */   }
/*     */   
/*     */   public void add(BeanPropertyAssocOne<?> prop) {
/*  63 */     this.propList.add(prop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryForeignKeys() {
/*  72 */     BeanDescriptor<?> descriptor = this.request.getBeanDescriptor();
/*  73 */     SpiQuery<?> q = (SpiQuery)this.server.createQuery(descriptor.getBeanType());
/*     */     
/*  75 */     Object id = this.request.getBeanId();
/*     */     
/*  77 */     StringBuilder sb = new StringBuilder(30);
/*  78 */     for (int i = 0; i < this.propList.size(); i++) {
/*  79 */       sb.append(((BeanPropertyAssocOne)this.propList.get(i)).getName()).append(",");
/*     */     }
/*     */ 
/*     */     
/*  83 */     q.setPersistenceContext((PersistenceContext)new DefaultPersistenceContext());
/*  84 */     q.setAutofetch(false);
/*  85 */     q.select(sb.toString());
/*  86 */     q.where().idEq(id);
/*     */     
/*  88 */     SpiTransaction t = this.request.getTransaction();
/*  89 */     if (t.isLogSummary()) {
/*  90 */       t.logInternal("-- Ebean fetching foreign key values for delete of " + descriptor.getName() + " id:" + id);
/*     */     }
/*  92 */     this.beanWithForeignKeys = this.server.findUnique((Query)q, (Transaction)t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteCascade() {
/* 101 */     for (int i = 0; i < this.propList.size(); i++) {
/* 102 */       BeanPropertyAssocOne<?> prop = this.propList.get(i);
/* 103 */       Object detailBean = prop.getValue(this.beanWithForeignKeys);
/*     */ 
/*     */       
/* 106 */       if (detailBean != null && prop.hasId(detailBean))
/* 107 */         this.server.delete(detailBean, (Transaction)this.request.getTransaction()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\DeleteUnloadedForeignKeys.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */