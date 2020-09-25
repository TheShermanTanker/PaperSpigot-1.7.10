/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ public final class BindableIdEmbedded
/*     */   implements BindableId
/*     */ {
/*     */   private final BeanPropertyAssocOne<?> embId;
/*     */   private final BeanProperty[] props;
/*     */   private final MatchedImportedProperty[] matches;
/*     */   
/*     */   public BindableIdEmbedded(BeanPropertyAssocOne<?> embId, BeanDescriptor<?> desc) {
/*  46 */     this.embId = embId;
/*  47 */     this.props = embId.getTargetDescriptor().propertiesBaseScalar();
/*  48 */     this.matches = MatchedImportedProperty.build(this.props, desc);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  52 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConcatenated() {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIdentityColumn() {
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  66 */     return this.embId + " props:" + Arrays.toString((Object[])this.props);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  77 */     dmlBind(request, checkIncludes, bean, true);
/*     */   }
/*     */   
/*     */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  81 */     dmlBind(request, checkIncludes, bean, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
/*  86 */     if (checkIncludes && !bindRequest.isIncluded((BeanProperty)this.embId)) {
/*     */       return;
/*     */     }
/*     */     
/*  90 */     Object idValue = this.embId.getValue(bean);
/*     */     
/*  92 */     for (int i = 0; i < this.props.length; i++) {
/*     */       
/*  94 */       Object value = this.props[i].getValue(idValue);
/*  95 */       bindRequest.bind(value, this.props[i], this.props[i].getDbColumn(), bindNull);
/*     */     } 
/*     */     
/*  98 */     bindRequest.setIdValue(idValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
/* 105 */     if (checkIncludes && !request.isIncluded((BeanProperty)this.embId)) {
/*     */       return;
/*     */     }
/* 108 */     dmlAppend(request, false);
/*     */   }
/*     */   
/*     */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
/* 112 */     dmlAppend(request, checkIncludes);
/*     */   }
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/* 116 */     if (checkIncludes && !request.isIncluded((BeanProperty)this.embId)) {
/*     */       return;
/*     */     }
/* 119 */     for (int i = 0; i < this.props.length; i++) {
/* 120 */       request.appendColumn(this.props[i].getDbColumn());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deriveConcatenatedId(PersistRequestBean<?> persist) {
/* 126 */     if (this.matches == null) {
/* 127 */       String m = "Matches for the concatinated key columns where not found? I expect that the concatinated key was null, and this bean does not have ManyToOne assoc beans matching the primary key columns?";
/*     */ 
/*     */       
/* 130 */       throw new PersistenceException(m);
/*     */     } 
/*     */     
/* 133 */     Object bean = persist.getBean();
/*     */ 
/*     */     
/* 136 */     Object newId = this.embId.createEmbeddedId();
/*     */ 
/*     */     
/* 139 */     for (int i = 0; i < this.matches.length; i++) {
/* 140 */       this.matches[i].populate(bean, newId);
/*     */     }
/*     */ 
/*     */     
/* 144 */     this.embId.setValueIntercept(bean, newId);
/* 145 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableIdEmbedded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */