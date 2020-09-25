/*     */ package org.apache.logging.log4j.core.web;
/*     */ 
/*     */ import java.net.URI;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.UnavailableException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.config.Configurator;
/*     */ import org.apache.logging.log4j.core.impl.ContextAnchor;
/*     */ import org.apache.logging.log4j.core.impl.Log4jContextFactory;
/*     */ import org.apache.logging.log4j.core.lookup.Interpolator;
/*     */ import org.apache.logging.log4j.core.lookup.StrLookup;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.core.selector.ContextSelector;
/*     */ import org.apache.logging.log4j.core.selector.NamedContextSelector;
/*     */ import org.apache.logging.log4j.spi.LoggerContextFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Log4jWebInitializerImpl
/*     */   implements Log4jWebInitializer
/*     */ {
/*  38 */   private static final Object MUTEX = new Object();
/*     */   
/*     */   static {
/*     */     try {
/*  42 */       Class.forName("org.apache.logging.log4j.core.web.JNDIContextFilter");
/*  43 */       throw new IllegalStateException("You are using Log4j 2 in a web application with the old, extinct log4j-web artifact. This is not supported and could cause serious runtime problems. Pleaseremove the log4j-web JAR file from your application.");
/*     */     
/*     */     }
/*  46 */     catch (ClassNotFoundException ignore) {
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*  51 */   private final StrSubstitutor substitutor = new StrSubstitutor((StrLookup)new Interpolator());
/*     */   
/*     */   private final ServletContext servletContext;
/*     */   
/*     */   private String name;
/*     */   private NamedContextSelector selector;
/*     */   private LoggerContext loggerContext;
/*     */   private boolean initialized = false;
/*     */   private boolean deinitialized = false;
/*     */   
/*     */   private Log4jWebInitializerImpl(ServletContext servletContext) {
/*  62 */     this.servletContext = servletContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void initialize() throws UnavailableException {
/*  67 */     if (this.deinitialized) {
/*  68 */       throw new IllegalStateException("Cannot initialize Log4jWebInitializer after it was destroyed.");
/*     */     }
/*     */ 
/*     */     
/*  72 */     if (!this.initialized) {
/*  73 */       this.initialized = true;
/*     */       
/*  75 */       this.name = this.substitutor.replace(this.servletContext.getInitParameter("log4jContextName"));
/*  76 */       String location = this.substitutor.replace(this.servletContext.getInitParameter("log4jConfiguration"));
/*  77 */       boolean isJndi = "true".equals(this.servletContext.getInitParameter("isLog4jContextSelectorNamed"));
/*     */       
/*  79 */       if (isJndi) {
/*  80 */         initializeJndi(location);
/*     */       } else {
/*  82 */         initializeNonJndi(location);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void initializeJndi(String location) throws UnavailableException {
/*     */     LoggerContext loggerContext;
/*  88 */     URI configLocation = null;
/*  89 */     if (location != null) {
/*     */       try {
/*  91 */         configLocation = new URI(location);
/*  92 */       } catch (Exception e) {
/*  93 */         this.servletContext.log("Unable to convert configuration location [" + location + "] to a URI!", e);
/*     */       } 
/*     */     }
/*     */     
/*  97 */     if (this.name == null) {
/*  98 */       throw new UnavailableException("A log4jContextName context parameter is required");
/*     */     }
/*     */ 
/*     */     
/* 102 */     LoggerContextFactory factory = LogManager.getFactory();
/* 103 */     if (factory instanceof Log4jContextFactory) {
/* 104 */       ContextSelector selector = ((Log4jContextFactory)factory).getSelector();
/* 105 */       if (selector instanceof NamedContextSelector) {
/* 106 */         this.selector = (NamedContextSelector)selector;
/* 107 */         loggerContext = this.selector.locateContext(this.name, this.servletContext, configLocation);
/* 108 */         ContextAnchor.THREAD_CONTEXT.set(loggerContext);
/* 109 */         if (loggerContext.getStatus() == LoggerContext.Status.INITIALIZED) {
/* 110 */           loggerContext.start();
/*     */         }
/* 112 */         ContextAnchor.THREAD_CONTEXT.remove();
/*     */       } else {
/* 114 */         this.servletContext.log("Potential problem: Selector is not an instance of NamedContextSelector.");
/*     */         return;
/*     */       } 
/*     */     } else {
/* 118 */       this.servletContext.log("Potential problem: Factory is not an instance of Log4jContextFactory.");
/*     */       return;
/*     */     } 
/* 121 */     this.loggerContext = loggerContext;
/* 122 */     this.servletContext.log("Created logger context for [" + this.name + "] using [" + loggerContext.getClass().getClassLoader() + "].");
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeNonJndi(String location) {
/* 127 */     if (this.name == null) {
/* 128 */       this.name = this.servletContext.getServletContextName();
/*     */     }
/*     */     
/* 131 */     if (this.name == null && location == null) {
/* 132 */       this.servletContext.log("No Log4j context configuration provided. This is very unusual.");
/*     */       
/*     */       return;
/*     */     } 
/* 136 */     this.loggerContext = Configurator.initialize(this.name, getClassLoader(), location, this.servletContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void deinitialize() {
/* 141 */     if (!this.initialized) {
/* 142 */       throw new IllegalStateException("Cannot deinitialize Log4jWebInitializer because it has not initialized.");
/*     */     }
/*     */ 
/*     */     
/* 146 */     if (!this.deinitialized) {
/* 147 */       this.deinitialized = true;
/*     */       
/* 149 */       if (this.loggerContext != null) {
/* 150 */         this.servletContext.log("Removing LoggerContext for [" + this.name + "].");
/* 151 */         if (this.selector != null) {
/* 152 */           this.selector.removeContext(this.name);
/*     */         }
/* 154 */         this.loggerContext.stop();
/* 155 */         this.loggerContext.setExternalContext(null);
/* 156 */         this.loggerContext = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLoggerContext() {
/* 163 */     if (this.loggerContext != null) {
/* 164 */       ContextAnchor.THREAD_CONTEXT.set(this.loggerContext);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearLoggerContext() {
/* 170 */     ContextAnchor.THREAD_CONTEXT.remove();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassLoader getClassLoader() {
/*     */     try {
/* 178 */       return this.servletContext.getClassLoader();
/* 179 */     } catch (Throwable ignore) {
/*     */       
/* 181 */       return Log4jWebInitializerImpl.class.getClassLoader();
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
/*     */   static Log4jWebInitializer getLog4jWebInitializer(ServletContext servletContext) {
/* 193 */     synchronized (MUTEX) {
/* 194 */       Log4jWebInitializer initializer = (Log4jWebInitializer)servletContext.getAttribute(INITIALIZER_ATTRIBUTE);
/* 195 */       if (initializer == null) {
/* 196 */         initializer = new Log4jWebInitializerImpl(servletContext);
/* 197 */         servletContext.setAttribute(INITIALIZER_ATTRIBUTE, initializer);
/*     */       } 
/* 199 */       return initializer;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\web\Log4jWebInitializerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */