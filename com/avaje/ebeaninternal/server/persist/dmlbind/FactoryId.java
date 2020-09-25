/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactoryId
/*    */ {
/*    */   public BindableId createId(BeanDescriptor<?> desc) {
/* 39 */     BeanProperty[] uids = desc.propertiesId();
/* 40 */     if (uids.length == 0) {
/* 41 */       return new BindableIdEmpty();
/*    */     }
/* 43 */     if (uids.length == 1) {
/* 44 */       if (!uids[0].isEmbedded()) {
/* 45 */         return new BindableIdScalar(uids[0]);
/*    */       }
/*    */       
/* 48 */       BeanPropertyAssocOne<?> embId = (BeanPropertyAssocOne)uids[0];
/* 49 */       return new BindableIdEmbedded(embId, desc);
/*    */     } 
/*    */     
/* 52 */     return new BindableIdMap(uids, desc);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\FactoryId.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */