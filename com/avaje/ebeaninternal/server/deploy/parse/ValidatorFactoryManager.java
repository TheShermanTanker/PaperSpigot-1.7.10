/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebean.validation.ValidatorMeta;
/*    */ import com.avaje.ebean.validation.factory.Validator;
/*    */ import com.avaje.ebean.validation.factory.ValidatorFactory;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ public class ValidatorFactoryManager
/*    */ {
/* 15 */   static final Logger logger = Logger.getLogger(ValidatorFactoryManager.class.getName());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 20 */   Map<Class<?>, ValidatorFactory> factoryMap = new HashMap<Class<?>, ValidatorFactory>();
/*    */ 
/*    */   
/*    */   public Validator create(Annotation ann, Class<?> type) {
/* 24 */     synchronized (this) {
/* 25 */       ValidatorMeta meta = ann.annotationType().<ValidatorMeta>getAnnotation(ValidatorMeta.class);
/* 26 */       if (meta == null) {
/* 27 */         return null;
/*    */       }
/* 29 */       Class<?> factoryClass = meta.factory();
/* 30 */       ValidatorFactory factory = getFactory(factoryClass);
/* 31 */       return factory.create(ann, type);
/*    */     } 
/*    */   }
/*    */   
/*    */   private ValidatorFactory getFactory(Class<?> factoryClass) {
/*    */     try {
/* 37 */       ValidatorFactory factory = this.factoryMap.get(factoryClass);
/* 38 */       if (factory == null) {
/* 39 */         factory = (ValidatorFactory)factoryClass.newInstance();
/* 40 */         this.factoryMap.put(factoryClass, factory);
/*    */       } 
/* 42 */       return factory;
/*    */     }
/* 44 */     catch (Exception e) {
/* 45 */       String msg = "Error creating ValidatorFactory " + factoryClass.getName();
/* 46 */       logger.log(Level.SEVERE, msg, e);
/* 47 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\ValidatorFactoryManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */