/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.DerivedRelationshipData;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.SpiUpdatePlan;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Set;
/*     */ import javax.persistence.OptimisticLockException;
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
/*     */ public class UpdateHandler
/*     */   extends DmlHandler
/*     */ {
/*     */   private final UpdateMeta meta;
/*     */   private Set<String> updatedProperties;
/*     */   private boolean emptySetClause;
/*     */   
/*     */   public UpdateHandler(PersistRequestBean<?> persist, UpdateMeta meta) {
/*  48 */     super(persist, meta.isEmptyStringAsNull());
/*  49 */     this.meta = meta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() throws SQLException {
/*     */     PreparedStatement pstmt;
/*  57 */     SpiUpdatePlan updatePlan = this.meta.getUpdatePlan(this.persistRequest);
/*     */     
/*  59 */     if (updatePlan.isEmptySetClause()) {
/*  60 */       this.emptySetClause = true;
/*     */       
/*     */       return;
/*     */     } 
/*  64 */     this.updatedProperties = updatePlan.getProperties();
/*     */     
/*  66 */     this.sql = updatePlan.getSql();
/*     */     
/*  68 */     SpiTransaction t = this.persistRequest.getTransaction();
/*  69 */     boolean isBatch = t.isBatchThisRequest();
/*     */ 
/*     */     
/*  72 */     if (isBatch) {
/*  73 */       pstmt = getPstmt(t, this.sql, this.persistRequest, false);
/*     */     } else {
/*     */       
/*  76 */       logSql(this.sql);
/*  77 */       pstmt = getPstmt(t, this.sql, false);
/*     */     } 
/*  79 */     this.dataBind = new DataBind(pstmt);
/*     */     
/*  81 */     bindLogAppend("Binding Update [");
/*  82 */     bindLogAppend(this.meta.getTableName());
/*  83 */     bindLogAppend("] ");
/*     */     
/*  85 */     this.meta.bind(this.persistRequest, this, updatePlan);
/*     */     
/*  87 */     setUpdateGenValues();
/*     */     
/*  89 */     bindLogAppend("]");
/*  90 */     logBinding();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBatch() throws SQLException {
/*  95 */     if (!this.emptySetClause) {
/*  96 */       super.addBatch();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute() throws SQLException, OptimisticLockException {
/* 105 */     if (!this.emptySetClause) {
/* 106 */       int rowCount = this.dataBind.executeUpdate();
/* 107 */       checkRowCount(rowCount);
/* 108 */       setAdditionalProperties();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIncluded(BeanProperty prop) {
/* 115 */     return (prop.isDbUpdatable() && (this.updatedProperties == null || this.updatedProperties.contains(prop.getName())));
/*     */   }
/*     */   
/*     */   public void registerDerivedRelationship(DerivedRelationshipData derivedRelationship) {
/* 119 */     this.persistRequest.getTransaction().registerDerivedRelationship(derivedRelationship);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\UpdateHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */