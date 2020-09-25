/*    */ package org.apache.logging.log4j.core.web;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Log4jServletFilter
/*    */   implements Filter
/*    */ {
/* 41 */   static final String ALREADY_FILTERED_ATTRIBUTE = Log4jServletFilter.class.getName() + ".FILTERED";
/*    */   
/*    */   private ServletContext servletContext;
/*    */   
/*    */   private Log4jWebInitializer initializer;
/*    */   
/*    */   public void init(FilterConfig filterConfig) throws ServletException {
/* 48 */     this.servletContext = filterConfig.getServletContext();
/* 49 */     this.servletContext.log("Log4jServletFilter initialized.");
/*    */     
/* 51 */     this.initializer = Log4jWebInitializerImpl.getLog4jWebInitializer(this.servletContext);
/* 52 */     this.initializer.clearLoggerContext();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/* 58 */     if (request.getAttribute(ALREADY_FILTERED_ATTRIBUTE) != null) {
/* 59 */       chain.doFilter(request, response);
/*    */     } else {
/* 61 */       request.setAttribute(ALREADY_FILTERED_ATTRIBUTE, Boolean.valueOf(true));
/*    */       
/*    */       try {
/* 64 */         this.initializer.setLoggerContext();
/*    */         
/* 66 */         chain.doFilter(request, response);
/*    */       } finally {
/* 68 */         this.initializer.clearLoggerContext();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void destroy() {
/* 75 */     if (this.servletContext == null || this.initializer == null) {
/* 76 */       throw new IllegalStateException("Filter destroyed before it was initialized.");
/*    */     }
/* 78 */     this.servletContext.log("Log4jServletFilter destroyed.");
/*    */     
/* 80 */     this.initializer.setLoggerContext();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\web\Log4jServletFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */