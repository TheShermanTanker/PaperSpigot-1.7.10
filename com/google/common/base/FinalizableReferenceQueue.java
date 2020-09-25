/*     */ package com.google.common.base;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class FinalizableReferenceQueue
/*     */ {
/*  77 */   private static final Logger logger = Logger.getLogger(FinalizableReferenceQueue.class.getName());
/*     */   private static final String FINALIZER_CLASS_NAME = "com.google.common.base.internal.Finalizer";
/*     */   private static final Method startFinalizer;
/*     */   final ReferenceQueue<Object> queue;
/*     */   final boolean threadStarted;
/*     */   
/*     */   static {
/*  84 */     Class<?> finalizer = loadFinalizer(new FinalizerLoader[] { new SystemLoader(), new DecoupledLoader(), new DirectLoader() });
/*     */     
/*  86 */     startFinalizer = getStartFinalizer(finalizer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FinalizableReferenceQueue() {
/*     */     ReferenceQueue<Object> referenceQueue;
/* 106 */     boolean threadStarted = false;
/*     */     try {
/* 108 */       referenceQueue = (ReferenceQueue<Object>)startFinalizer.invoke(null, new Object[] { FinalizableReference.class, this });
/*     */       
/* 110 */       threadStarted = true;
/* 111 */     } catch (IllegalAccessException impossible) {
/* 112 */       throw new AssertionError(impossible);
/* 113 */     } catch (Throwable t) {
/* 114 */       logger.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", t);
/*     */       
/* 116 */       referenceQueue = new ReferenceQueue();
/*     */     } 
/*     */     
/* 119 */     this.queue = referenceQueue;
/* 120 */     this.threadStarted = threadStarted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cleanUp() {
/* 129 */     if (this.threadStarted) {
/*     */       return;
/*     */     }
/*     */     
/*     */     Reference<?> reference;
/* 134 */     while ((reference = this.queue.poll()) != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       reference.clear();
/*     */       try {
/* 141 */         ((FinalizableReference)reference).finalizeReferent();
/* 142 */       } catch (Throwable t) {
/* 143 */         logger.log(Level.SEVERE, "Error cleaning up after reference.", t);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> loadFinalizer(FinalizerLoader... loaders) {
/* 154 */     for (FinalizerLoader loader : loaders) {
/* 155 */       Class<?> finalizer = loader.loadFinalizer();
/* 156 */       if (finalizer != null) {
/* 157 */         return finalizer;
/*     */       }
/*     */     } 
/*     */     
/* 161 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static interface FinalizerLoader
/*     */   {
/*     */     Class<?> loadFinalizer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class SystemLoader
/*     */     implements FinalizerLoader
/*     */   {
/*     */     public Class<?> loadFinalizer() {
/*     */       ClassLoader systemLoader;
/*     */       try {
/* 186 */         systemLoader = ClassLoader.getSystemClassLoader();
/* 187 */       } catch (SecurityException e) {
/* 188 */         FinalizableReferenceQueue.logger.info("Not allowed to access system class loader.");
/* 189 */         return null;
/*     */       } 
/* 191 */       if (systemLoader != null) {
/*     */         try {
/* 193 */           return systemLoader.loadClass("com.google.common.base.internal.Finalizer");
/* 194 */         } catch (ClassNotFoundException e) {
/*     */           
/* 196 */           return null;
/*     */         } 
/*     */       }
/* 199 */       return null;
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
/*     */   static class DecoupledLoader
/*     */     implements FinalizerLoader
/*     */   {
/*     */     private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader.Loading Finalizer in the current class loader instead. As a result, you will not be ableto garbage collect this class loader. To support reclaiming this class loader, eitherresolve the underlying issue, or move Google Collections to your system class path.";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Class<?> loadFinalizer() {
/*     */       try {
/* 227 */         ClassLoader finalizerLoader = newLoader(getBaseUrl());
/* 228 */         return finalizerLoader.loadClass("com.google.common.base.internal.Finalizer");
/* 229 */       } catch (Exception e) {
/* 230 */         FinalizableReferenceQueue.logger.log(Level.WARNING, "Could not load Finalizer in its own class loader.Loading Finalizer in the current class loader instead. As a result, you will not be ableto garbage collect this class loader. To support reclaiming this class loader, eitherresolve the underlying issue, or move Google Collections to your system class path.", e);
/* 231 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     URL getBaseUrl() throws IOException {
/* 240 */       String finalizerPath = "com.google.common.base.internal.Finalizer".replace('.', '/') + ".class";
/* 241 */       URL finalizerUrl = getClass().getClassLoader().getResource(finalizerPath);
/* 242 */       if (finalizerUrl == null) {
/* 243 */         throw new FileNotFoundException(finalizerPath);
/*     */       }
/*     */ 
/*     */       
/* 247 */       String urlString = finalizerUrl.toString();
/* 248 */       if (!urlString.endsWith(finalizerPath)) {
/* 249 */         throw new IOException("Unsupported path style: " + urlString);
/*     */       }
/* 251 */       urlString = urlString.substring(0, urlString.length() - finalizerPath.length());
/* 252 */       return new URL(finalizerUrl, urlString);
/*     */     }
/*     */ 
/*     */     
/*     */     URLClassLoader newLoader(URL base) {
/* 257 */       return new URLClassLoader(new URL[] { base });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DirectLoader
/*     */     implements FinalizerLoader
/*     */   {
/*     */     public Class<?> loadFinalizer() {
/*     */       try {
/* 269 */         return Class.forName("com.google.common.base.internal.Finalizer");
/* 270 */       } catch (ClassNotFoundException e) {
/* 271 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Method getStartFinalizer(Class<?> finalizer) {
/*     */     try {
/* 281 */       return finalizer.getMethod("startFinalizer", new Class[] { Class.class, Object.class });
/* 282 */     } catch (NoSuchMethodException e) {
/* 283 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\FinalizableReferenceQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */