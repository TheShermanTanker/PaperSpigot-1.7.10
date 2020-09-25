/*    */ package com.avaje.ebeaninternal.server.deploy.meta;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebeaninternal.server.deploy.ManyType;
/*    */ import com.avaje.ebeaninternal.server.type.ScalarType;
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
/*    */ public class DeployBeanPropertySimpleCollection<T>
/*    */   extends DeployBeanPropertyAssocMany<T>
/*    */ {
/*    */   private final ScalarType<T> collectionScalarType;
/*    */   
/*    */   public DeployBeanPropertySimpleCollection(DeployBeanDescriptor<?> desc, Class<T> targetType, ScalarType<T> scalarType, ManyType manyType) {
/* 31 */     super(desc, targetType, manyType);
/* 32 */     this.collectionScalarType = scalarType;
/* 33 */     this.modifyListenMode = BeanCollection.ModifyListenMode.ALL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ScalarType<T> getCollectionScalarType() {
/* 40 */     return this.collectionScalarType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isManyToMany() {
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUnidirectional() {
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertySimpleCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */