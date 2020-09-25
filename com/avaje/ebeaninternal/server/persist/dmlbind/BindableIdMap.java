/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public final class BindableIdMap
/*     */   implements BindableId
/*     */ {
/*     */   private final BeanProperty[] uids;
/*     */   private final MatchedImportedProperty[] matches;
/*     */   
/*     */   public BindableIdMap(BeanProperty[] uids, BeanDescriptor<?> desc) {
/*  44 */     this.uids = uids;
/*  45 */     this.matches = MatchedImportedProperty.build(uids, desc);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  49 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConcatenated() {
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIdentityColumn() {
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  63 */     return Arrays.toString((Object[])this.uids);
/*     */   }
/*     */ 
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
/*     */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
/*  78 */     dmlAppend(request, false);
/*     */   }
/*     */   
/*     */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
/*  82 */     dmlAppend(request, checkIncludes);
/*     */   }
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/*  86 */     for (int i = 0; i < this.uids.length; i++) {
/*  87 */       request.appendColumn(this.uids[i].getDbColumn());
/*     */     }
/*     */   }
/*     */   
/*     */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  92 */     dmlBind(request, checkIncludes, bean, true);
/*     */   }
/*     */   
/*     */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  96 */     dmlBind(request, checkIncludes, bean, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
/* 101 */     LinkedHashMap<String, Object> mapId = new LinkedHashMap<String, Object>();
/* 102 */     for (int i = 0; i < this.uids.length; i++) {
/* 103 */       Object value = this.uids[i].getValue(bean);
/*     */       
/* 105 */       bindRequest.bind(value, this.uids[i], this.uids[i].getName(), bindNull);
/*     */ 
/*     */ 
/*     */       
/* 109 */       mapId.put(this.uids[i].getName(), value);
/*     */     } 
/* 111 */     bindRequest.setIdValue(mapId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deriveConcatenatedId(PersistRequestBean<?> persist) {
/* 116 */     if (this.matches == null) {
/* 117 */       String m = "Matches for the concatinated key columns where not found? I expect that the concatinated key was null, and this bean does not have ManyToOne assoc beans matching the primary key columns?";
/*     */ 
/*     */       
/* 120 */       throw new PersistenceException(m);
/*     */     } 
/*     */     
/* 123 */     Object bean = persist.getBean();
/*     */ 
/*     */     
/* 126 */     for (int i = 0; i < this.matches.length; i++) {
/* 127 */       this.matches[i].populate(bean, bean);
/*     */     }
/*     */     
/* 130 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableIdMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */