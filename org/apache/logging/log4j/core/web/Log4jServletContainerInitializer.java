/*    */ package org.apache.logging.log4j.core.web;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.EventListener;
/*    */ import java.util.Set;
/*    */ import javax.servlet.DispatcherType;
/*    */ import javax.servlet.FilterRegistration;
/*    */ import javax.servlet.ServletContainerInitializer;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
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
/*    */ public class Log4jServletContainerInitializer
/*    */   implements ServletContainerInitializer
/*    */ {
/*    */   public void onStartup(Set<Class<?>> classes, ServletContext servletContext) throws ServletException {
/* 37 */     if (servletContext.getMajorVersion() > 2) {
/* 38 */       servletContext.log("Log4jServletContainerInitializer starting up Log4j in Servlet 3.0+ environment.");
/*    */       
/* 40 */       Log4jWebInitializer initializer = Log4jWebInitializerImpl.getLog4jWebInitializer(servletContext);
/* 41 */       initializer.initialize();
/* 42 */       initializer.setLoggerContext();
/*    */       
/* 44 */       servletContext.addListener((EventListener)new Log4jServletContextListener());
/*    */       
/* 46 */       FilterRegistration.Dynamic filter = servletContext.addFilter("log4jServletFilter", new Log4jServletFilter());
/*    */       
/* 48 */       if (filter == null) {
/* 49 */         throw new UnavailableException("In a Servlet 3.0+ application, you must not define a log4jServletFilter in web.xml. Log4j 2 defines this for you automatically.");
/*    */       }
/*    */       
/* 52 */       filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, new String[] { "/*" });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\web\Log4jServletContainerInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */