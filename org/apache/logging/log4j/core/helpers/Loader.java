/*     */ package org.apache.logging.log4j.core.helpers;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*     */ public final class Loader
/*     */ {
/*     */   private static boolean ignoreTCL = false;
/*  33 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
/*     */   
/*     */   static {
/*  38 */     String ignoreTCLProp = PropertiesUtil.getProperties().getStringProperty("log4j.ignoreTCL", null);
/*  39 */     if (ignoreTCLProp != null) {
/*  40 */       ignoreTCL = OptionConverter.toBoolean(ignoreTCLProp, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClassLoader getClassLoader() {
/*  50 */     return getClassLoader(Loader.class, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ClassLoader getClassLoader(Class<?> class1, Class<?> class2) {
/*  55 */     ClassLoader loader1 = null;
/*     */     try {
/*  57 */       loader1 = getTCL();
/*  58 */     } catch (Exception ex) {
/*  59 */       LOGGER.warn("Caught exception locating thread ClassLoader {}", new Object[] { ex.getMessage() });
/*     */     } 
/*  61 */     ClassLoader loader2 = (class1 == null) ? null : class1.getClassLoader();
/*  62 */     ClassLoader loader3 = (class2 == null) ? null : class2.getClass().getClassLoader();
/*     */     
/*  64 */     if (isChild(loader1, loader2)) {
/*  65 */       return isChild(loader1, loader3) ? loader1 : loader3;
/*     */     }
/*  67 */     return isChild(loader2, loader3) ? loader2 : loader3;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL getResource(String resource, ClassLoader defaultLoader) {
/*     */     try {
/*  94 */       ClassLoader classLoader = getTCL();
/*  95 */       if (classLoader != null) {
/*  96 */         LOGGER.trace("Trying to find [" + resource + "] using context classloader " + classLoader + '.');
/*     */         
/*  98 */         URL url = classLoader.getResource(resource);
/*  99 */         if (url != null) {
/* 100 */           return url;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 105 */       classLoader = Loader.class.getClassLoader();
/* 106 */       if (classLoader != null) {
/* 107 */         LOGGER.trace("Trying to find [" + resource + "] using " + classLoader + " class loader.");
/* 108 */         URL url = classLoader.getResource(resource);
/* 109 */         if (url != null) {
/* 110 */           return url;
/*     */         }
/*     */       } 
/*     */       
/* 114 */       if (defaultLoader != null) {
/* 115 */         LOGGER.trace("Trying to find [" + resource + "] using " + defaultLoader + " class loader.");
/* 116 */         URL url = defaultLoader.getResource(resource);
/* 117 */         if (url != null) {
/* 118 */           return url;
/*     */         }
/*     */       } 
/* 121 */     } catch (Throwable t) {
/*     */ 
/*     */ 
/*     */       
/* 125 */       LOGGER.warn("Caught Exception while in Loader.getResource. This may be innocuous.", t);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     LOGGER.trace("Trying to find [" + resource + "] using ClassLoader.getSystemResource().");
/* 133 */     return ClassLoader.getSystemResource(resource);
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
/*     */   public static InputStream getResourceAsStream(String resource, ClassLoader defaultLoader) {
/*     */     try {
/* 163 */       ClassLoader classLoader = getTCL();
/* 164 */       if (classLoader != null) {
/* 165 */         LOGGER.trace("Trying to find [" + resource + "] using context classloader " + classLoader + '.');
/* 166 */         InputStream is = classLoader.getResourceAsStream(resource);
/* 167 */         if (is != null) {
/* 168 */           return is;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 173 */       classLoader = Loader.class.getClassLoader();
/* 174 */       if (classLoader != null) {
/* 175 */         LOGGER.trace("Trying to find [" + resource + "] using " + classLoader + " class loader.");
/* 176 */         InputStream is = classLoader.getResourceAsStream(resource);
/* 177 */         if (is != null) {
/* 178 */           return is;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 183 */       if (defaultLoader != null) {
/* 184 */         LOGGER.trace("Trying to find [" + resource + "] using " + defaultLoader + " class loader.");
/* 185 */         InputStream is = defaultLoader.getResourceAsStream(resource);
/* 186 */         if (is != null) {
/* 187 */           return is;
/*     */         }
/*     */       } 
/* 190 */     } catch (Throwable t) {
/*     */ 
/*     */ 
/*     */       
/* 194 */       LOGGER.warn("Caught Exception while in Loader.getResource. This may be innocuous.", t);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     LOGGER.trace("Trying to find [" + resource + "] using ClassLoader.getSystemResource().");
/* 202 */     return ClassLoader.getSystemResourceAsStream(resource);
/*     */   }
/*     */   
/*     */   private static ClassLoader getTCL() {
/*     */     ClassLoader cl;
/* 207 */     if (System.getSecurityManager() == null) {
/* 208 */       cl = Thread.currentThread().getContextClassLoader();
/*     */     } else {
/* 210 */       cl = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */           {
/*     */             public ClassLoader run()
/*     */             {
/* 214 */               return Thread.currentThread().getContextClassLoader();
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 220 */     return cl;
/*     */   }
/*     */   
/*     */   private static boolean isChild(ClassLoader loader1, ClassLoader loader2) {
/* 224 */     if (loader1 != null && loader2 != null) {
/* 225 */       ClassLoader parent = loader1.getParent();
/* 226 */       while (parent != null && parent != loader2) {
/* 227 */         parent = parent.getParent();
/*     */       }
/* 229 */       return (parent != null);
/*     */     } 
/* 231 */     return (loader1 != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> loadClass(String className) throws ClassNotFoundException {
/* 242 */     if (ignoreTCL) {
/* 243 */       return Class.forName(className);
/*     */     }
/*     */     try {
/* 246 */       return getTCL().loadClass(className);
/* 247 */     } catch (Throwable e) {
/* 248 */       return Class.forName(className);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Loader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */