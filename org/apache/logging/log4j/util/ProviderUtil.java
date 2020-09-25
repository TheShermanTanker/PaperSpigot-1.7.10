/*     */ package org.apache.logging.log4j.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.spi.Provider;
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
/*     */ public final class ProviderUtil
/*     */ {
/*     */   private static final String PROVIDER_RESOURCE = "META-INF/log4j-provider.properties";
/*     */   private static final String API_VERSION = "Log4jAPIVersion";
/*  39 */   private static final String[] COMPATIBLE_API_VERSIONS = new String[] { "2.0.0" };
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*  45 */   private static final List<Provider> PROVIDERS = new ArrayList<Provider>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  51 */     ClassLoader cl = findClassLoader();
/*  52 */     Enumeration<URL> enumResources = null;
/*     */     try {
/*  54 */       enumResources = cl.getResources("META-INF/log4j-provider.properties");
/*  55 */     } catch (IOException e) {
/*  56 */       LOGGER.fatal("Unable to locate META-INF/log4j-provider.properties", e);
/*     */     } 
/*     */     
/*  59 */     if (enumResources != null) {
/*  60 */       while (enumResources.hasMoreElements()) {
/*  61 */         URL url = enumResources.nextElement();
/*     */         
/*     */         try {
/*  64 */           Properties props = PropertiesUtil.loadClose(url.openStream(), url);
/*  65 */           if (!validVersion(props.getProperty("Log4jAPIVersion"))) {
/*     */             continue;
/*     */           }
/*  68 */           PROVIDERS.add(new Provider(props, url));
/*  69 */         } catch (IOException ioe) {
/*  70 */           LOGGER.error("Unable to open " + url.toString(), ioe);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static Iterator<Provider> getProviders() {
/*  77 */     return PROVIDERS.iterator();
/*     */   }
/*     */   
/*     */   public static boolean hasProviders() {
/*  81 */     return (PROVIDERS.size() > 0);
/*     */   }
/*     */   
/*     */   public static ClassLoader findClassLoader() {
/*     */     ClassLoader cl;
/*  86 */     if (System.getSecurityManager() == null) {
/*  87 */       cl = Thread.currentThread().getContextClassLoader();
/*     */     } else {
/*  89 */       cl = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */           {
/*     */             public ClassLoader run()
/*     */             {
/*  93 */               return Thread.currentThread().getContextClassLoader();
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  98 */     if (cl == null) {
/*  99 */       cl = ProviderUtil.class.getClassLoader();
/*     */     }
/*     */     
/* 102 */     return cl;
/*     */   }
/*     */   
/*     */   private static boolean validVersion(String version) {
/* 106 */     for (String v : COMPATIBLE_API_VERSIONS) {
/* 107 */       if (version.startsWith(v)) {
/* 108 */         return true;
/*     */       }
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4\\util\ProviderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */