/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public final class DeleteByIdMap
/*    */ {
/* 36 */   private final Map<String, BeanPersistIds> beanMap = new LinkedHashMap<String, BeanPersistIds>();
/*    */   
/*    */   public String toString() {
/* 39 */     return this.beanMap.toString();
/*    */   }
/*    */   
/*    */   public void notifyCache() {
/* 43 */     for (BeanPersistIds deleteIds : this.beanMap.values()) {
/* 44 */       BeanDescriptor<?> d = deleteIds.getBeanDescriptor();
/* 45 */       List<Serializable> idValues = deleteIds.getDeleteIds();
/* 46 */       if (idValues != null) {
/* 47 */         d.queryCacheClear();
/* 48 */         for (int i = 0; i < idValues.size(); i++) {
/* 49 */           d.cacheRemove(idValues.get(i));
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 57 */     return this.beanMap.isEmpty();
/*    */   }
/*    */   
/*    */   public Collection<BeanPersistIds> values() {
/* 61 */     return this.beanMap.values();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(BeanDescriptor<?> desc, Object id) {
/* 69 */     BeanPersistIds r = getPersistIds(desc);
/* 70 */     r.addId(PersistRequest.Type.DELETE, (Serializable)id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addList(BeanDescriptor<?> desc, List<Object> idList) {
/* 78 */     BeanPersistIds r = getPersistIds(desc);
/* 79 */     for (int i = 0; i < idList.size(); i++) {
/* 80 */       r.addId(PersistRequest.Type.DELETE, (Serializable)idList.get(i));
/*    */     }
/*    */   }
/*    */   
/*    */   private BeanPersistIds getPersistIds(BeanDescriptor<?> desc) {
/* 85 */     String beanType = desc.getFullName();
/* 86 */     BeanPersistIds r = this.beanMap.get(beanType);
/* 87 */     if (r == null) {
/* 88 */       r = new BeanPersistIds(desc);
/* 89 */       this.beanMap.put(beanType, r);
/*    */     } 
/* 91 */     return r;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\DeleteByIdMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */