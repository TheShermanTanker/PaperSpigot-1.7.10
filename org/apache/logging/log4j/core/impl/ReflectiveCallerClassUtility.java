/*     */ package org.apache.logging.log4j.core.impl;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ public final class ReflectiveCallerClassUtility
/*     */ {
/*  59 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private static final boolean GET_CALLER_CLASS_SUPPORTED;
/*     */   
/*     */   private static final Method GET_CALLER_CLASS_METHOD;
/*     */   
/*     */   static final int JAVA_7U25_COMPENSATION_OFFSET;
/*     */   
/*     */   static {
/*  68 */     Method getCallerClass = null;
/*  69 */     int java7u25CompensationOffset = 0;
/*     */     
/*     */     try {
/*  72 */       ClassLoader loader = Loader.getClassLoader();
/*     */       
/*  74 */       Class<?> clazz = loader.loadClass("sun.reflect.Reflection");
/*  75 */       Method[] methods = clazz.getMethods();
/*  76 */       for (Method method : methods) {
/*  77 */         int modifier = method.getModifiers();
/*  78 */         Class<?>[] parameterTypes = method.getParameterTypes();
/*  79 */         if (method.getName().equals("getCallerClass") && Modifier.isStatic(modifier) && parameterTypes.length == 1 && parameterTypes[0] == int.class) {
/*     */           
/*  81 */           getCallerClass = method;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  86 */       if (getCallerClass == null) {
/*  87 */         LOGGER.info("sun.reflect.Reflection#getCallerClass does not exist.");
/*     */       } else {
/*  89 */         Object o = getCallerClass.invoke(null, new Object[] { Integer.valueOf(0) });
/*  90 */         if (o == null || o != clazz) {
/*  91 */           getCallerClass = null;
/*  92 */           LOGGER.warn("sun.reflect.Reflection#getCallerClass returned unexpected value of [{}] and is unusable. Will fall back to another option.", new Object[] { o });
/*     */         } else {
/*     */           
/*  95 */           o = getCallerClass.invoke(null, new Object[] { Integer.valueOf(1) });
/*  96 */           if (o == clazz) {
/*  97 */             java7u25CompensationOffset = 1;
/*  98 */             LOGGER.warn("sun.reflect.Reflection#getCallerClass is broken in Java 7u25. You should upgrade to 7u40. Using alternate stack offset to compensate.");
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 103 */     } catch (ClassNotFoundException e) {
/* 104 */       LOGGER.info("sun.reflect.Reflection is not installed.");
/* 105 */     } catch (IllegalAccessException e) {
/* 106 */       LOGGER.info("sun.reflect.Reflection#getCallerClass is not accessible.");
/* 107 */     } catch (InvocationTargetException e) {
/* 108 */       LOGGER.info("sun.reflect.Reflection#getCallerClass is not supported.");
/*     */     } 
/*     */     
/* 111 */     if (getCallerClass == null) {
/* 112 */       GET_CALLER_CLASS_SUPPORTED = false;
/* 113 */       GET_CALLER_CLASS_METHOD = null;
/* 114 */       JAVA_7U25_COMPENSATION_OFFSET = -1;
/*     */     } else {
/* 116 */       GET_CALLER_CLASS_SUPPORTED = true;
/* 117 */       GET_CALLER_CLASS_METHOD = getCallerClass;
/* 118 */       JAVA_7U25_COMPENSATION_OFFSET = java7u25CompensationOffset;
/*     */     } 
/*     */   }
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
/*     */   public static boolean isSupported() {
/* 133 */     return GET_CALLER_CLASS_SUPPORTED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> getCaller(int depth) {
/* 145 */     if (!GET_CALLER_CLASS_SUPPORTED) {
/* 146 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 150 */       return (Class)GET_CALLER_CLASS_METHOD.invoke(null, new Object[] { Integer.valueOf(depth + 1 + JAVA_7U25_COMPENSATION_OFFSET) });
/* 151 */     } catch (IllegalAccessException ignore) {
/* 152 */       LOGGER.warn("Should not have failed to call getCallerClass.");
/* 153 */     } catch (InvocationTargetException ignore) {
/* 154 */       LOGGER.warn("Should not have failed to call getCallerClass.");
/*     */     } 
/* 156 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\ReflectiveCallerClassUtility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */