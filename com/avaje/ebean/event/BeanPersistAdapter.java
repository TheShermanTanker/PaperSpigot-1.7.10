/*    */ package com.avaje.ebean.event;
/*    */ 
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BeanPersistAdapter
/*    */   implements BeanPersistController
/*    */ {
/*    */   public abstract boolean isRegisterFor(Class<?> paramClass);
/*    */   
/*    */   public int getExecutionOrder() {
/* 45 */     return 10;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean preDelete(BeanPersistRequest<?> request) {
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean preInsert(BeanPersistRequest<?> request) {
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean preUpdate(BeanPersistRequest<?> request) {
/* 67 */     return true;
/*    */   }
/*    */   
/*    */   public void postDelete(BeanPersistRequest<?> request) {}
/*    */   
/*    */   public void postInsert(BeanPersistRequest<?> request) {}
/*    */   
/*    */   public void postUpdate(BeanPersistRequest<?> request) {}
/*    */   
/*    */   public void postLoad(Object bean, Set<String> includedProperties) {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BeanPersistAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */