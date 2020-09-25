/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.impl.ContextAnchor;
/*     */ import org.apache.logging.log4j.spi.LoggerContext;
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
/*     */ public final class Configurator
/*     */ {
/*  32 */   protected static final StatusLogger LOGGER = StatusLogger.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LoggerContext initialize(String name, ClassLoader loader, String configLocation) {
/*  46 */     return initialize(name, loader, configLocation, (Object)null);
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
/*     */   public static LoggerContext initialize(String name, ClassLoader loader, String configLocation, Object externalContext) {
/*     */     try {
/*  62 */       URI uri = (configLocation == null) ? null : new URI(configLocation);
/*  63 */       return initialize(name, loader, uri, externalContext);
/*  64 */     } catch (URISyntaxException ex) {
/*  65 */       ex.printStackTrace();
/*     */       
/*  67 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LoggerContext initialize(String name, String configLocation) {
/*  77 */     return initialize(name, (ClassLoader)null, configLocation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LoggerContext initialize(String name, ClassLoader loader, URI configLocation) {
/*  88 */     return initialize(name, loader, configLocation, (Object)null);
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
/*     */   public static LoggerContext initialize(String name, ClassLoader loader, URI configLocation, Object externalContext) {
/*     */     try {
/* 103 */       LoggerContext context = LogManager.getContext(loader, false, configLocation);
/* 104 */       if (context instanceof LoggerContext) {
/* 105 */         LoggerContext ctx = (LoggerContext)context;
/* 106 */         ContextAnchor.THREAD_CONTEXT.set(ctx);
/* 107 */         if (externalContext != null) {
/* 108 */           ctx.setExternalContext(externalContext);
/*     */         }
/* 110 */         Configuration config = ConfigurationFactory.getInstance().getConfiguration(name, configLocation);
/* 111 */         ctx.start(config);
/* 112 */         ContextAnchor.THREAD_CONTEXT.remove();
/* 113 */         return ctx;
/*     */       } 
/* 115 */       LOGGER.error("LogManager returned an instance of {} which does not implement {}. Unable to initialize Log4j", new Object[] { context.getClass().getName(), LoggerContext.class.getName() });
/*     */     
/*     */     }
/* 118 */     catch (Exception ex) {
/* 119 */       ex.printStackTrace();
/*     */     } 
/* 121 */     return null;
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
/*     */   public static LoggerContext initialize(ClassLoader loader, ConfigurationFactory.ConfigurationSource source) {
/*     */     try {
/* 134 */       URI configLocation = null;
/*     */       try {
/* 136 */         configLocation = (source.getLocation() == null) ? null : new URI(source.getLocation());
/* 137 */       } catch (Exception ex) {}
/*     */ 
/*     */       
/* 140 */       LoggerContext context = LogManager.getContext(loader, false, configLocation);
/* 141 */       if (context instanceof LoggerContext) {
/* 142 */         LoggerContext ctx = (LoggerContext)context;
/* 143 */         ContextAnchor.THREAD_CONTEXT.set(ctx);
/* 144 */         Configuration config = ConfigurationFactory.getInstance().getConfiguration(source);
/* 145 */         ctx.start(config);
/* 146 */         ContextAnchor.THREAD_CONTEXT.remove();
/* 147 */         return ctx;
/*     */       } 
/* 149 */       LOGGER.error("LogManager returned an instance of {} which does not implement {}. Unable to initialize Log4j", new Object[] { context.getClass().getName(), LoggerContext.class.getName() });
/*     */     
/*     */     }
/* 152 */     catch (Exception ex) {
/* 153 */       ex.printStackTrace();
/*     */     } 
/* 155 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shutdown(LoggerContext ctx) {
/* 163 */     if (ctx != null)
/* 164 */       ctx.stop(); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\Configurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */