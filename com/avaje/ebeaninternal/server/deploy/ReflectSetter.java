/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*    */ import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
/*    */ import java.lang.reflect.Method;
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
/*    */ public class ReflectSetter
/*    */ {
/*    */   public static BeanReflectSetter create(DeployBeanProperty prop) {
/* 42 */     String fullName = prop.getFullBeanName();
/* 43 */     Method writeMethod = prop.getWriteMethod();
/* 44 */     return new RefCalled(fullName, writeMethod);
/*    */   }
/*    */   
/*    */   static class RefCalled
/*    */     implements BeanReflectSetter {
/*    */     final String fullName;
/*    */     final Method writeMethod;
/*    */     
/*    */     RefCalled(String fullName, Method writeMethod) {
/* 53 */       this.fullName = fullName;
/* 54 */       this.writeMethod = writeMethod;
/*    */     }
/*    */     public void set(Object bean, Object value) {
/* 57 */       Object[] a = new Object[1];
/* 58 */       a[0] = value;
/*    */       try {
/* 60 */         this.writeMethod.invoke(bean, a);
/* 61 */       } catch (Exception e) {
/* 62 */         String beanType = (bean == null) ? "null" : bean.getClass().toString();
/* 63 */         String msg = "Error setting value on " + this.fullName + " value[" + value + "] on type[" + beanType + "]";
/* 64 */         throw new RuntimeException(msg, e);
/*    */       } 
/*    */     }
/*    */     public void setIntercept(Object bean, Object value) {
/* 68 */       String msg = "Not expecting setIntercept to be called. Refer Bug 368";
/* 69 */       throw new RuntimeException(msg);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\ReflectSetter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */