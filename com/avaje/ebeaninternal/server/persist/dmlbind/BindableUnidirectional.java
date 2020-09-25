/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*    */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BindableUnidirectional
/*    */   implements Bindable
/*    */ {
/*    */   private final BeanPropertyAssocOne<?> unidirectional;
/*    */   private final ImportedId importedId;
/*    */   private final BeanDescriptor<?> desc;
/*    */   
/*    */   public BindableUnidirectional(BeanDescriptor<?> desc, BeanPropertyAssocOne<?> unidirectional) {
/* 49 */     this.desc = desc;
/* 50 */     this.unidirectional = unidirectional;
/* 51 */     this.importedId = unidirectional.getImportedId();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return "BindableShadowFKey " + this.unidirectional;
/*    */   }
/*    */   
/*    */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
/* 60 */     throw new PersistenceException("Never called (for insert only)");
/*    */   }
/*    */   
/*    */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
/* 64 */     dmlAppend(request, checkIncludes);
/*    */   }
/*    */ 
/*    */   
/*    */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/* 69 */     this.importedId.dmlAppend(request);
/*    */   }
/*    */   
/*    */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
/* 73 */     throw new RuntimeException("Never called");
/*    */   }
/*    */   
/*    */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/* 77 */     dmlBind(request, checkIncludes, bean, true);
/*    */   }
/*    */   
/*    */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/* 81 */     dmlBind(request, checkIncludes, bean, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void dmlBind(BindableRequest request, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
/* 87 */     PersistRequestBean<?> persistRequest = request.getPersistRequest();
/* 88 */     Object parentBean = persistRequest.getParentBean();
/*    */     
/* 90 */     if (parentBean == null) {
/* 91 */       Class<?> localType = this.desc.getBeanType();
/* 92 */       Class<?> targetType = this.unidirectional.getTargetType();
/*    */       
/* 94 */       String msg = "Error inserting bean [" + localType + "] with unidirectional relationship. ";
/* 95 */       msg = msg + "For inserts you must use cascade save on the master bean [" + targetType + "].";
/* 96 */       throw new PersistenceException(msg);
/*    */     } 
/*    */     
/* 99 */     this.importedId.bind(request, parentBean, bindNull);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableUnidirectional.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */