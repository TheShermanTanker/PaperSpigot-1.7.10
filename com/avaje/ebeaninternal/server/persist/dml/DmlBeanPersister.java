/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.persist.BeanPersister;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public final class DmlBeanPersister
/*     */   implements BeanPersister
/*     */ {
/*  45 */   private static final Logger logger = Logger.getLogger(DmlBeanPersister.class.getName());
/*     */ 
/*     */   
/*     */   private final UpdateMeta updateMeta;
/*     */   
/*     */   private final InsertMeta insertMeta;
/*     */   
/*     */   private final DeleteMeta deleteMeta;
/*     */ 
/*     */   
/*     */   public DmlBeanPersister(UpdateMeta updateMeta, InsertMeta insertMeta, DeleteMeta deleteMeta) {
/*  56 */     this.updateMeta = updateMeta;
/*  57 */     this.insertMeta = insertMeta;
/*  58 */     this.deleteMeta = deleteMeta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(PersistRequestBean<?> request) {
/*  66 */     DeleteHandler delete = new DeleteHandler(request, this.deleteMeta);
/*  67 */     execute((PersistRequest)request, delete);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(PersistRequestBean<?> request) {
/*  75 */     InsertHandler insert = new InsertHandler(request, this.insertMeta);
/*  76 */     execute((PersistRequest)request, insert);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(PersistRequestBean<?> request) {
/*  84 */     UpdateHandler update = new UpdateHandler(request, this.updateMeta);
/*  85 */     execute((PersistRequest)request, update);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void execute(PersistRequest request, PersistHandler handler) {
/*  93 */     SpiTransaction trans = request.getTransaction();
/*  94 */     boolean batchThisRequest = trans.isBatchThisRequest();
/*     */ 
/*     */     
/*     */     try {
/*  98 */       handler.bind();
/*     */       
/* 100 */       if (batchThisRequest) {
/* 101 */         handler.addBatch();
/*     */       }
/*     */       else {
/*     */         
/* 105 */         handler.execute();
/*     */       }
/*     */     
/* 108 */     } catch (SQLException e) {
/*     */       
/* 110 */       String errMsg = StringHelper.replaceStringMulti(e.getMessage(), new String[] { "\r", "\n" }, "\\n ");
/* 111 */       String msg = "ERROR executing DML bindLog[" + handler.getBindLog() + "] error[" + errMsg + "]";
/* 112 */       if (request.getTransaction().isLogSummary()) {
/* 113 */         request.getTransaction().logInternal(msg);
/*     */       }
/*     */       
/* 116 */       throw new PersistenceException(msg, e);
/*     */     } finally {
/*     */       
/* 119 */       if (!batchThisRequest && handler != null)
/*     */         try {
/* 121 */           handler.close();
/* 122 */         } catch (SQLException e) {
/* 123 */           logger.log(Level.SEVERE, (String)null, e);
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\DmlBeanPersister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */