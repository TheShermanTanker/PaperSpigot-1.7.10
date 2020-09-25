/*    */ package com.avaje.ebeaninternal.server.cache;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CachedBeanDataUpdate
/*    */ {
/*    */   public static CachedBeanData update(BeanDescriptor<?> desc, CachedBeanData data, PersistRequestBean<?> updateRequest) {
/* 15 */     Set<String> loadedProperties = data.getLoadedProperties();
/* 16 */     Object[] copyOfData = data.copyData();
/*    */     
/* 18 */     Object updateBean = updateRequest.getBean();
/* 19 */     Set<String> updatedProperties = updateRequest.getUpdatedProperties();
/*    */     
/* 21 */     int naturalKeyUpdate = -1;
/* 22 */     boolean mergeProperties = false;
/* 23 */     BeanProperty[] props = desc.propertiesNonMany();
/* 24 */     for (int i = 0; i < props.length; i++) {
/* 25 */       if (updatedProperties.contains(props[i].getName())) {
/* 26 */         if (props[i].isNaturalKey()) {
/* 27 */           naturalKeyUpdate = i;
/*    */         }
/* 29 */         copyOfData[i] = props[i].getCacheDataValue(updateBean);
/* 30 */         if (loadedProperties != null && !mergeProperties && !loadedProperties.contains(props[i].getName())) {
/* 31 */           mergeProperties = true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 36 */     if (mergeProperties) {
/* 37 */       HashSet<String> mergeProps = new HashSet<String>();
/* 38 */       mergeProps.addAll(loadedProperties);
/* 39 */       mergeProps.addAll(updatedProperties);
/* 40 */       loadedProperties = mergeProps;
/*    */     } 
/*    */     
/* 43 */     return new CachedBeanData(null, loadedProperties, copyOfData, naturalKeyUpdate);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cache\CachedBeanDataUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */