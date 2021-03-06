/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectGetter
/*     */ {
/*     */   public static BeanReflectGetter create(DeployBeanProperty prop) {
/*  43 */     if (!prop.isId())
/*     */     {
/*  45 */       return new NonIdGetter(prop.getFullBeanName());
/*     */     }
/*     */     
/*  48 */     String property = prop.getFullBeanName();
/*  49 */     Method readMethod = prop.getReadMethod();
/*  50 */     if (readMethod == null) {
/*  51 */       String m = "Abstract class with no readMethod for " + property;
/*  52 */       throw new RuntimeException(m);
/*     */     } 
/*  54 */     return new IdGetter(property, readMethod);
/*     */   }
/*     */   
/*     */   public static class IdGetter
/*     */     implements BeanReflectGetter
/*     */   {
/*  60 */     public static final Object[] NO_ARGS = new Object[0];
/*     */     
/*     */     private final Method readMethod;
/*     */     private final String property;
/*     */     
/*     */     public IdGetter(String property, Method readMethod) {
/*  66 */       this.property = property;
/*  67 */       this.readMethod = readMethod;
/*     */     }
/*     */     
/*     */     public Object get(Object bean) {
/*     */       try {
/*  72 */         return this.readMethod.invoke(bean, NO_ARGS);
/*  73 */       } catch (Exception e) {
/*  74 */         String m = "Error on [" + this.property + "] using readMethod " + this.readMethod;
/*  75 */         throw new RuntimeException(m, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object getIntercept(Object bean) {
/*  80 */       return get(bean);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NonIdGetter
/*     */     implements BeanReflectGetter {
/*     */     private final String property;
/*     */     
/*     */     public NonIdGetter(String property) {
/*  89 */       this.property = property;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object get(Object bean) {
/*  94 */       String m = "Not expecting this method to be called on [" + this.property + "] as it is a NON ID property on an abstract class";
/*     */       
/*  96 */       throw new RuntimeException(m);
/*     */     }
/*     */     
/*     */     public Object getIntercept(Object bean) {
/* 100 */       return get(bean);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\ReflectGetter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */