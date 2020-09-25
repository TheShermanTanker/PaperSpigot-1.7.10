/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.DerivedRelationshipData;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import java.sql.SQLException;
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
/*     */ public class BindableAssocOne
/*     */   implements Bindable
/*     */ {
/*     */   private final BeanPropertyAssocOne<?> assocOne;
/*     */   private final ImportedId importedId;
/*     */   
/*     */   public BindableAssocOne(BeanPropertyAssocOne<?> assocOne) {
/*  41 */     this.assocOne = assocOne;
/*  42 */     this.importedId = assocOne.getImportedId();
/*     */   }
/*     */   
/*     */   public String toString() {
/*  46 */     return "BindableAssocOne " + this.assocOne;
/*     */   }
/*     */   
/*     */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
/*  50 */     if (request.hasChanged((BeanProperty)this.assocOne)) {
/*  51 */       list.add(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
/*  56 */     dmlAppend(request, checkIncludes);
/*     */   }
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/*  60 */     if (checkIncludes && !request.isIncluded((BeanProperty)this.assocOne)) {
/*     */       return;
/*     */     }
/*  63 */     this.importedId.dmlAppend(request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
/*  70 */     if (checkIncludes && !request.isIncludedWhere((BeanProperty)this.assocOne)) {
/*     */       return;
/*     */     }
/*  73 */     Object assocBean = this.assocOne.getValue(bean);
/*  74 */     this.importedId.dmlWhere(request, assocBean);
/*     */   }
/*     */   
/*     */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  78 */     if (checkIncludes && !request.isIncluded((BeanProperty)this.assocOne)) {
/*     */       return;
/*     */     }
/*  81 */     dmlBind(request, bean, true);
/*     */   }
/*     */   
/*     */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  85 */     if (checkIncludes && !request.isIncludedWhere((BeanProperty)this.assocOne)) {
/*     */       return;
/*     */     }
/*  88 */     dmlBind(request, bean, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void dmlBind(BindableRequest request, Object bean, boolean bindNull) throws SQLException {
/*  94 */     Object assocBean = this.assocOne.getValue(bean);
/*  95 */     Object boundValue = this.importedId.bind(request, assocBean, bindNull);
/*  96 */     if (bindNull && boundValue == null && assocBean != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       DerivedRelationshipData d = new DerivedRelationshipData(assocBean, this.assocOne.getName(), bean);
/* 103 */       request.registerDerivedRelationship(d);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableAssocOne.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */