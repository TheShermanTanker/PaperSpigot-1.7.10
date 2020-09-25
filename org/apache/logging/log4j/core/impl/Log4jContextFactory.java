/*     */ package org.apache.logging.log4j.core.impl;
/*     */ 
/*     */ import java.net.URI;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.core.jmx.Server;
/*     */ import org.apache.logging.log4j.core.selector.ClassLoaderContextSelector;
/*     */ import org.apache.logging.log4j.core.selector.ContextSelector;
/*     */ import org.apache.logging.log4j.spi.LoggerContext;
/*     */ import org.apache.logging.log4j.spi.LoggerContextFactory;
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
/*     */ public class Log4jContextFactory
/*     */   implements LoggerContextFactory
/*     */ {
/*  36 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
/*     */ 
/*     */   
/*     */   private ContextSelector selector;
/*     */ 
/*     */ 
/*     */   
/*     */   public Log4jContextFactory() {
/*  44 */     String sel = PropertiesUtil.getProperties().getStringProperty("Log4jContextSelector");
/*  45 */     if (sel != null) {
/*     */       try {
/*  47 */         Class<?> clazz = Loader.loadClass(sel);
/*  48 */         if (clazz != null && ContextSelector.class.isAssignableFrom(clazz)) {
/*  49 */           this.selector = (ContextSelector)clazz.newInstance();
/*     */         }
/*  51 */       } catch (Exception ex) {
/*  52 */         LOGGER.error("Unable to create context " + sel, ex);
/*     */       } 
/*     */     }
/*  55 */     if (this.selector == null) {
/*  56 */       this.selector = (ContextSelector)new ClassLoaderContextSelector();
/*     */     }
/*     */     try {
/*  59 */       Server.registerMBeans(this.selector);
/*  60 */     } catch (Exception ex) {
/*  61 */       LOGGER.error("Could not start JMX", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContextSelector getSelector() {
/*  70 */     return this.selector;
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
/*     */   public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext) {
/*  83 */     LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext);
/*  84 */     if (ctx.getStatus() == LoggerContext.Status.INITIALIZED) {
/*  85 */       ctx.start();
/*     */     }
/*  87 */     return ctx;
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
/*     */   public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
/* 102 */     LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext, configLocation);
/* 103 */     if (ctx.getStatus() == LoggerContext.Status.INITIALIZED) {
/* 104 */       ctx.start();
/*     */     }
/* 106 */     return ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeContext(LoggerContext context) {
/* 117 */     if (context instanceof LoggerContext)
/* 118 */       this.selector.removeContext((LoggerContext)context); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\Log4jContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */