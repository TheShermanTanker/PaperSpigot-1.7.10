/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BindableIdEmpty
/*    */   implements BindableId
/*    */ {
/*    */   public boolean isEmpty() {
/* 12 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {}
/*    */ 
/*    */   
/*    */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {}
/*    */ 
/*    */   
/*    */   public boolean isConcatenated() {
/* 40 */     return false;
/*    */   }
/*    */   
/*    */   public String getIdentityColumn() {
/* 44 */     return null;
/*    */   }
/*    */   
/*    */   public boolean deriveConcatenatedId(PersistRequestBean<?> persist) {
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableIdEmpty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */