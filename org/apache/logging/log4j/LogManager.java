/*     */ package org.apache.logging.log4j;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
/*     */ import org.apache.logging.log4j.message.StringFormatterMessageFactory;
/*     */ import org.apache.logging.log4j.simple.SimpleLoggerContextFactory;
/*     */ import org.apache.logging.log4j.spi.LoggerContext;
/*     */ import org.apache.logging.log4j.spi.LoggerContextFactory;
/*     */ import org.apache.logging.log4j.spi.Provider;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
/*     */ import org.apache.logging.log4j.util.ProviderUtil;
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
/*     */ public class LogManager
/*     */ {
/*     */   private static LoggerContextFactory factory;
/*     */   private static final String FACTORY_PROPERTY_NAME = "log4j2.loggerContextFactory";
/*  44 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String ROOT_LOGGER_NAME = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  57 */     PropertiesUtil managerProps = PropertiesUtil.getProperties();
/*  58 */     String factoryClass = managerProps.getStringProperty("log4j2.loggerContextFactory");
/*  59 */     ClassLoader cl = ProviderUtil.findClassLoader();
/*  60 */     if (factoryClass != null) {
/*     */       try {
/*  62 */         Class<?> clazz = cl.loadClass(factoryClass);
/*  63 */         if (LoggerContextFactory.class.isAssignableFrom(clazz)) {
/*  64 */           factory = (LoggerContextFactory)clazz.newInstance();
/*     */         }
/*  66 */       } catch (ClassNotFoundException cnfe) {
/*  67 */         LOGGER.error("Unable to locate configured LoggerContextFactory {}", new Object[] { factoryClass });
/*  68 */       } catch (Exception ex) {
/*  69 */         LOGGER.error("Unable to create configured LoggerContextFactory {}", new Object[] { factoryClass, ex });
/*     */       } 
/*     */     }
/*     */     
/*  73 */     if (factory == null) {
/*  74 */       SortedMap<Integer, LoggerContextFactory> factories = new TreeMap<Integer, LoggerContextFactory>();
/*     */       
/*  76 */       if (ProviderUtil.hasProviders()) {
/*  77 */         Iterator<Provider> providers = ProviderUtil.getProviders();
/*  78 */         while (providers.hasNext()) {
/*  79 */           Provider provider = providers.next();
/*  80 */           String className = provider.getClassName();
/*  81 */           if (className != null) {
/*     */             try {
/*  83 */               Class<?> clazz = cl.loadClass(className);
/*  84 */               if (LoggerContextFactory.class.isAssignableFrom(clazz)) {
/*  85 */                 factories.put(provider.getPriority(), (LoggerContextFactory)clazz.newInstance()); continue;
/*     */               } 
/*  87 */               LOGGER.error(className + " does not implement " + LoggerContextFactory.class.getName());
/*     */             }
/*  89 */             catch (ClassNotFoundException cnfe) {
/*  90 */               LOGGER.error("Unable to locate class " + className + " specified in " + provider.getURL().toString(), cnfe);
/*     */             }
/*  92 */             catch (IllegalAccessException iae) {
/*  93 */               LOGGER.error("Unable to create class " + className + " specified in " + provider.getURL().toString(), iae);
/*     */             }
/*  95 */             catch (Exception e) {
/*  96 */               LOGGER.error("Unable to create class " + className + " specified in " + provider.getURL().toString(), e);
/*     */               
/*  98 */               e.printStackTrace();
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 103 */         if (factories.size() == 0) {
/* 104 */           LOGGER.error("Unable to locate a logging implementation, using SimpleLogger");
/* 105 */           factory = (LoggerContextFactory)new SimpleLoggerContextFactory();
/*     */         } else {
/* 107 */           StringBuilder sb = new StringBuilder("Multiple logging implementations found: \n");
/* 108 */           for (Map.Entry<Integer, LoggerContextFactory> entry : factories.entrySet()) {
/* 109 */             sb.append("Factory: ").append(((LoggerContextFactory)entry.getValue()).getClass().getName());
/* 110 */             sb.append(", Weighting: ").append(entry.getKey()).append("\n");
/*     */           } 
/* 112 */           factory = factories.get(factories.lastKey());
/* 113 */           sb.append("Using factory: ").append(factory.getClass().getName());
/* 114 */           LOGGER.warn(sb.toString());
/*     */         } 
/*     */       } else {
/*     */         
/* 118 */         LOGGER.error("Unable to locate a logging implementation, using SimpleLogger");
/* 119 */         factory = (LoggerContextFactory)new SimpleLoggerContextFactory();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getClassName(int depth) {
/* 131 */     return (new Throwable()).getStackTrace()[depth].getClassName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LoggerContext getContext() {
/* 142 */     return factory.getContext(LogManager.class.getName(), null, true);
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
/*     */   public static LoggerContext getContext(boolean currentContext) {
/* 155 */     return factory.getContext(LogManager.class.getName(), null, currentContext);
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
/*     */   public static LoggerContext getContext(ClassLoader loader, boolean currentContext) {
/* 170 */     return factory.getContext(LogManager.class.getName(), loader, currentContext);
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
/*     */   public static LoggerContext getContext(ClassLoader loader, boolean currentContext, URI configLocation) {
/* 187 */     return factory.getContext(LogManager.class.getName(), loader, currentContext, configLocation);
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
/*     */   protected static LoggerContext getContext(String fqcn, boolean currentContext) {
/* 200 */     return factory.getContext(fqcn, null, currentContext);
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
/*     */   protected static LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext) {
/* 216 */     return factory.getContext(fqcn, loader, currentContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LoggerContextFactory getFactory() {
/* 224 */     return factory;
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
/*     */   
/*     */   public static Logger getFormatterLogger(Class<?> clazz) {
/* 254 */     return getLogger((clazz != null) ? clazz.getName() : getClassName(2), (MessageFactory)StringFormatterMessageFactory.INSTANCE);
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
/*     */   
/*     */   public static Logger getFormatterLogger(Object value) {
/* 284 */     return getLogger((value != null) ? value.getClass().getName() : getClassName(2), (MessageFactory)StringFormatterMessageFactory.INSTANCE);
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
/*     */   
/*     */   public static Logger getFormatterLogger(String name) {
/* 314 */     return getLogger((name != null) ? name : getClassName(2), (MessageFactory)StringFormatterMessageFactory.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Logger getLogger() {
/* 322 */     return getLogger(getClassName(2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Logger getLogger(Class<?> clazz) {
/* 332 */     return getLogger((clazz != null) ? clazz.getName() : getClassName(2));
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
/*     */   public static Logger getLogger(Class<?> clazz, MessageFactory messageFactory) {
/* 344 */     return getLogger((clazz != null) ? clazz.getName() : getClassName(2), messageFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Logger getLogger(MessageFactory messageFactory) {
/* 354 */     return getLogger(getClassName(2), messageFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Logger getLogger(Object value) {
/* 364 */     return getLogger((value != null) ? value.getClass().getName() : getClassName(2));
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
/*     */   public static Logger getLogger(Object value, MessageFactory messageFactory) {
/* 376 */     return getLogger((value != null) ? value.getClass().getName() : getClassName(2), messageFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Logger getLogger(String name) {
/* 386 */     String actualName = (name != null) ? name : getClassName(2);
/* 387 */     return factory.getContext(LogManager.class.getName(), null, false).getLogger(actualName);
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
/*     */   public static Logger getLogger(String name, MessageFactory messageFactory) {
/* 399 */     String actualName = (name != null) ? name : getClassName(2);
/* 400 */     return factory.getContext(LogManager.class.getName(), null, false).getLogger(actualName, messageFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Logger getLogger(String fqcn, String name) {
/* 411 */     return factory.getContext(fqcn, null, false).getLogger(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Logger getRootLogger() {
/* 420 */     return getLogger("");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\LogManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */