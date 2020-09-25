/*    */ package org.apache.logging.log4j.core.jmx;
/*    */ 
/*    */ import javax.management.ObjectName;
/*    */ import org.apache.logging.log4j.core.Appender;
/*    */ import org.apache.logging.log4j.core.helpers.Assert;
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
/*    */ 
/*    */ public class AppenderAdmin
/*    */   implements AppenderAdminMBean
/*    */ {
/*    */   private final String contextName;
/*    */   private final Appender appender;
/*    */   private final ObjectName objectName;
/*    */   
/*    */   public AppenderAdmin(String contextName, Appender appender) {
/* 42 */     this.contextName = (String)Assert.isNotNull(contextName, "contextName");
/* 43 */     this.appender = (Appender)Assert.isNotNull(appender, "appender");
/*    */     try {
/* 45 */       String ctxName = Server.escape(this.contextName);
/* 46 */       String configName = Server.escape(appender.getName());
/* 47 */       String name = String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=Appender,name=%s", new Object[] { ctxName, configName });
/* 48 */       this.objectName = new ObjectName(name);
/* 49 */     } catch (Exception e) {
/* 50 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectName getObjectName() {
/* 61 */     return this.objectName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 66 */     return this.appender.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLayout() {
/* 71 */     return String.valueOf(this.appender.getLayout());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExceptionSuppressed() {
/* 76 */     return this.appender.ignoreExceptions();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getErrorHandler() {
/* 81 */     return String.valueOf(this.appender.getHandler());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\AppenderAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */