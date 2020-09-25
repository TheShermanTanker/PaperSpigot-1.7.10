/*    */ package com.avaje.ebeaninternal.server.persist.dml;
/*    */ 
/*    */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.persist.BeanPersister;
/*    */ import com.avaje.ebeaninternal.server.persist.BeanPersisterFactory;
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
/*    */ public class DmlBeanPersisterFactory
/*    */   implements BeanPersisterFactory
/*    */ {
/*    */   private final MetaFactory metaFactory;
/*    */   
/*    */   public DmlBeanPersisterFactory(DatabasePlatform dbPlatform) {
/* 35 */     this.metaFactory = new MetaFactory(dbPlatform);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BeanPersister create(BeanDescriptor<?> desc) {
/* 44 */     UpdateMeta updMeta = this.metaFactory.createUpdate(desc);
/* 45 */     DeleteMeta delMeta = this.metaFactory.createDelete(desc);
/* 46 */     InsertMeta insMeta = this.metaFactory.createInsert(desc);
/*    */     
/* 48 */     return new DmlBeanPersister(updMeta, insMeta, delMeta);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\DmlBeanPersisterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */