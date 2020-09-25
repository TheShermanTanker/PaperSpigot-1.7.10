/*    */ package com.avaje.ebeaninternal.server.deploy.id;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
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
/*    */ public class IdBinderFactory
/*    */ {
/* 30 */   private static final IdBinderEmpty EMPTY = new IdBinderEmpty();
/*    */   
/*    */   private final boolean idInExpandedForm;
/*    */   
/*    */   public IdBinderFactory(boolean idInExpandedForm) {
/* 35 */     this.idInExpandedForm = idInExpandedForm;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IdBinder createIdBinder(BeanProperty[] uids) {
/* 43 */     if (uids.length == 0)
/*    */     {
/* 45 */       return EMPTY;
/*    */     }
/* 47 */     if (uids.length == 1) {
/* 48 */       if (uids[0].isEmbedded()) {
/* 49 */         return new IdBinderEmbedded(this.idInExpandedForm, (BeanPropertyAssocOne)uids[0]);
/*    */       }
/* 51 */       return new IdBinderSimple(uids[0]);
/*    */     } 
/*    */ 
/*    */     
/* 55 */     return new IdBinderMultiple(uids);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\id\IdBinderFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */