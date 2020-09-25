/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
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
/*    */ public class BindableDiscriminator
/*    */   implements Bindable
/*    */ {
/*    */   private final String columnName;
/*    */   private final Object discValue;
/*    */   private final int sqlType;
/*    */   
/*    */   public BindableDiscriminator(InheritInfo inheritInfo) {
/* 41 */     this.columnName = inheritInfo.getDiscriminatorColumn();
/* 42 */     this.discValue = inheritInfo.getDiscriminatorValue();
/* 43 */     this.sqlType = inheritInfo.getDiscriminatorType();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     return this.columnName + " = " + this.discValue;
/*    */   }
/*    */   
/*    */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
/* 51 */     throw new PersistenceException("Never called (only for inserts)");
/*    */   }
/*    */   
/*    */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
/* 55 */     dmlAppend(request, checkIncludes);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/* 66 */     request.appendColumn(this.columnName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
/* 71 */     bindRequest.bind(this.columnName, this.discValue, this.sqlType);
/*    */   }
/*    */ 
/*    */   
/*    */   public void dmlBindWhere(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
/* 76 */     bindRequest.bind(this.columnName, this.discValue, this.sqlType);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableDiscriminator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */