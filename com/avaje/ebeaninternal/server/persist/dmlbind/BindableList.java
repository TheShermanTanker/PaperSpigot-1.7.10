/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
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
/*    */ public class BindableList
/*    */   implements Bindable
/*    */ {
/*    */   private final Bindable[] items;
/*    */   
/*    */   public BindableList(List<Bindable> list) {
/* 36 */     this.items = list.<Bindable>toArray(new Bindable[list.size()]);
/*    */   }
/*    */   
/*    */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
/* 40 */     for (int i = 0; i < this.items.length; i++) {
/* 41 */       this.items[i].addChanged(request, list);
/*    */     }
/*    */   }
/*    */   
/*    */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
/* 46 */     for (int i = 0; i < this.items.length; i++) {
/* 47 */       this.items[i].dmlInsert(request, checkIncludes);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/* 53 */     for (int i = 0; i < this.items.length; i++) {
/* 54 */       this.items[i].dmlAppend(request, checkIncludes);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
/* 60 */     for (int i = 0; i < this.items.length; i++) {
/* 61 */       this.items[i].dmlWhere(request, checkIncludes, bean);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
/* 68 */     for (int i = 0; i < this.items.length; i++) {
/* 69 */       this.items[i].dmlBind(bindRequest, checkIncludes, bean);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void dmlBindWhere(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
/* 76 */     for (int i = 0; i < this.items.length; i++)
/* 77 */       this.items[i].dmlBindWhere(bindRequest, checkIncludes, bean); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */