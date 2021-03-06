/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanPersistListener;
/*    */ import com.avaje.ebeaninternal.server.core.BootupClasses;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*    */ import java.util.List;
/*    */ import java.util.logging.Logger;
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
/*    */ 
/*    */ 
/*    */ public class PersistListenerManager
/*    */ {
/* 37 */   private static final Logger logger = Logger.getLogger(PersistListenerManager.class.getName());
/*    */   
/*    */   private final List<BeanPersistListener<?>> list;
/*    */   
/*    */   public PersistListenerManager(BootupClasses bootupClasses) {
/* 42 */     this.list = bootupClasses.getBeanPersistListeners();
/*    */   }
/*    */   
/*    */   public int getRegisterCount() {
/* 46 */     return this.list.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> void addPersistListeners(DeployBeanDescriptor<T> deployDesc) {
/* 55 */     for (int i = 0; i < this.list.size(); i++) {
/* 56 */       BeanPersistListener<?> c = this.list.get(i);
/* 57 */       if (isRegisterFor(deployDesc.getBeanType(), c)) {
/* 58 */         logger.fine("BeanPersistListener on[" + deployDesc.getFullName() + "] " + c.getClass().getName());
/* 59 */         deployDesc.addPersistListener(c);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean isRegisterFor(Class<?> beanType, BeanPersistListener<?> c) {
/* 65 */     Class<?> listenerEntity = getEntityClass(c.getClass());
/* 66 */     return beanType.equals(listenerEntity);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Class<?> getEntityClass(Class<?> controller) {
/* 77 */     Class<?> cls = ParamTypeUtil.findParamType(controller, BeanPersistListener.class);
/* 78 */     if (cls == null) {
/* 79 */       String msg = "Could not determine the entity class (generics parameter type) from " + controller + " using reflection.";
/*    */       
/* 81 */       throw new PersistenceException(msg);
/*    */     } 
/* 83 */     return cls;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\PersistListenerManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */