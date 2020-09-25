/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
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
/*     */ public final class BindableIdScalar
/*     */   implements BindableId
/*     */ {
/*     */   private final BeanProperty uidProp;
/*     */   
/*     */   public BindableIdScalar(BeanProperty uidProp) {
/*  39 */     this.uidProp = uidProp;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  43 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConcatenated() {
/*  47 */     return false;
/*     */   }
/*     */   
/*     */   public String getIdentityColumn() {
/*  51 */     return this.uidProp.getDbColumn();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  56 */     return this.uidProp.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deriveConcatenatedId(PersistRequestBean<?> persist) {
/*  70 */     throw new PersistenceException("Should not be called? only for concatinated keys");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
/*  78 */     request.appendColumn(this.uidProp.getDbColumn());
/*     */   }
/*     */   
/*     */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
/*  82 */     dmlAppend(request, checkIncludes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/*  87 */     request.appendColumn(this.uidProp.getDbColumn());
/*     */   }
/*     */   
/*     */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  91 */     dmlBind(request, checkIncludes, bean, true);
/*     */   }
/*     */   
/*     */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  95 */     dmlBind(request, checkIncludes, bean, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
/* 100 */     Object value = this.uidProp.getValue(bean);
/*     */     
/* 102 */     bindRequest.bind(value, this.uidProp, this.uidProp.getName(), bindNull);
/*     */ 
/*     */     
/* 105 */     bindRequest.setIdValue(value);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableIdScalar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */