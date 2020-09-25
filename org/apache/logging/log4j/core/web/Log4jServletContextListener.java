/*    */ package org.apache.logging.log4j.core.web;
/*    */ 
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ import javax.servlet.UnavailableException;
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
/*    */ public class Log4jServletContextListener
/*    */   implements ServletContextListener
/*    */ {
/*    */   private ServletContext servletContext;
/*    */   private Log4jWebInitializer initializer;
/*    */   
/*    */   public void contextInitialized(ServletContextEvent event) {
/* 36 */     this.servletContext = event.getServletContext();
/* 37 */     this.servletContext.log("Log4jServletContextListener ensuring that Log4j starts up properly.");
/*    */     
/* 39 */     this.initializer = Log4jWebInitializerImpl.getLog4jWebInitializer(this.servletContext);
/*    */     try {
/* 41 */       this.initializer.initialize();
/* 42 */       this.initializer.setLoggerContext();
/* 43 */     } catch (UnavailableException e) {
/* 44 */       throw new RuntimeException("Failed to initialize Log4j properly.", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void contextDestroyed(ServletContextEvent event) {
/* 50 */     if (this.servletContext == null || this.initializer == null) {
/* 51 */       throw new IllegalStateException("Context destroyed before it was initialized.");
/*    */     }
/* 53 */     this.servletContext.log("Log4jServletContextListener ensuring that Log4j shuts down properly.");
/*    */     
/* 55 */     this.initializer.clearLoggerContext();
/* 56 */     this.initializer.deinitialize();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\web\Log4jServletContextListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */