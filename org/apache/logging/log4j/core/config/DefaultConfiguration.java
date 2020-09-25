/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.core.Appender;
/*    */ import org.apache.logging.log4j.core.Layout;
/*    */ import org.apache.logging.log4j.core.appender.ConsoleAppender;
/*    */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*    */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultConfiguration
/*    */   extends BaseConfiguration
/*    */ {
/*    */   public static final String DEFAULT_NAME = "Default";
/*    */   public static final String DEFAULT_LEVEL = "org.apache.logging.log4j.level";
/*    */   
/*    */   public DefaultConfiguration() {
/* 50 */     setName("Default");
/* 51 */     PatternLayout patternLayout = PatternLayout.createLayout("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n", null, null, null, null);
/*    */     
/* 53 */     ConsoleAppender consoleAppender = ConsoleAppender.createAppender((Layout)patternLayout, null, "SYSTEM_OUT", "Console", "false", "true");
/*    */     
/* 55 */     consoleAppender.start();
/* 56 */     addAppender((Appender)consoleAppender);
/* 57 */     LoggerConfig root = getRootLogger();
/* 58 */     root.addAppender((Appender)consoleAppender, null, null);
/*    */     
/* 60 */     String levelName = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.level");
/* 61 */     Level level = (levelName != null && Level.valueOf(levelName) != null) ? Level.valueOf(levelName) : Level.ERROR;
/*    */     
/* 63 */     root.setLevel(level);
/*    */   }
/*    */   
/*    */   protected void doConfigure() {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\DefaultConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */