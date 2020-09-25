/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*    */ import java.sql.SQLException;
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
/*    */ public class BindablePropertyInsertGenerated
/*    */   extends BindableProperty
/*    */ {
/*    */   private final GeneratedProperty gen;
/*    */   
/*    */   public BindablePropertyInsertGenerated(BeanProperty prop, GeneratedProperty gen) {
/* 39 */     super(prop);
/* 40 */     this.gen = gen;
/*    */   }
/*    */   
/*    */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/* 44 */     dmlBind(request, checkIncludes, bean, true);
/*    */   }
/*    */   
/*    */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/* 48 */     dmlBind(request, checkIncludes, bean, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void dmlBind(BindableRequest request, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
/* 56 */     Object value = this.gen.getInsertValue(this.prop, bean);
/*    */ 
/*    */     
/* 59 */     if (bean != null) {
/*    */       
/* 61 */       this.prop.setValueIntercept(bean, value);
/* 62 */       request.registerAdditionalProperty(this.prop.getName());
/*    */     } 
/*    */     
/* 65 */     request.bind(value, this.prop, this.prop.getName(), bindNull);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/* 73 */     request.appendColumn(this.prop.getDbColumn());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindablePropertyInsertGenerated.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */