/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BeanCollectionHelpFactory
/*    */ {
/*    */   public static <T> BeanCollectionHelp<T> create(BeanPropertyAssocMany<T> manyProperty) {
/* 17 */     ManyType manyType = manyProperty.getManyType();
/* 18 */     switch (manyType.getUnderlying()) {
/*    */       case LIST:
/* 20 */         return new BeanListHelp<T>(manyProperty);
/*    */       case SET:
/* 22 */         return new BeanSetHelp<T>(manyProperty);
/*    */       case MAP:
/* 24 */         return new BeanMapHelp<T>(manyProperty);
/*    */     } 
/* 26 */     throw new RuntimeException("Invalid type " + manyType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> BeanCollectionHelp<T> create(OrmQueryRequest<T> request) {
/* 33 */     SpiQuery.Type manyType = request.getQuery().getType();
/*    */     
/* 35 */     if (manyType.equals(SpiQuery.Type.LIST)) {
/* 36 */       return new BeanListHelp<T>();
/*    */     }
/* 38 */     if (manyType.equals(SpiQuery.Type.SET)) {
/* 39 */       return new BeanSetHelp<T>();
/*    */     }
/*    */     
/* 42 */     BeanDescriptor<T> target = request.getBeanDescriptor();
/* 43 */     String mapKey = request.getQuery().getMapKey();
/* 44 */     return new BeanMapHelp<T>(target, mapKey);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanCollectionHelpFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */