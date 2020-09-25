/*     */ package org.apache.logging.log4j.core.async;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.AppenderRef;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.LoggerConfig;
/*     */ import org.apache.logging.log4j.core.config.Property;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "asyncLogger", category = "Core", printObject = true)
/*     */ public class AsyncLoggerConfig
/*     */   extends LoggerConfig
/*     */ {
/*     */   private AsyncLoggerConfigHelper helper;
/*     */   
/*     */   public AsyncLoggerConfig() {}
/*     */   
/*     */   public AsyncLoggerConfig(String name, Level level, boolean additive) {
/*  88 */     super(name, level, additive);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AsyncLoggerConfig(String name, List<AppenderRef> appenders, Filter filter, Level level, boolean additive, Property[] properties, Configuration config, boolean includeLocation) {
/*  96 */     super(name, appenders, filter, level, additive, properties, config, includeLocation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void callAppenders(LogEvent event) {
/* 107 */     event.getSource();
/* 108 */     event.getThreadName();
/*     */ 
/*     */     
/* 111 */     this.helper.callAppendersFromAnotherThread(event);
/*     */   }
/*     */ 
/*     */   
/*     */   void asyncCallAppenders(LogEvent event) {
/* 116 */     super.callAppenders(event);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startFilter() {
/* 121 */     if (this.helper == null) {
/* 122 */       this.helper = new AsyncLoggerConfigHelper(this);
/*     */     } else {
/* 124 */       AsyncLoggerConfigHelper.claim();
/*     */     } 
/* 126 */     super.startFilter();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopFilter() {
/* 131 */     AsyncLoggerConfigHelper.release();
/* 132 */     super.stopFilter();
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
/*     */   @PluginFactory
/*     */   public static LoggerConfig createLogger(@PluginAttribute("additivity") String additivity, @PluginAttribute("level") String levelName, @PluginAttribute("name") String loggerName, @PluginAttribute("includeLocation") String includeLocation, @PluginElement("AppenderRef") AppenderRef[] refs, @PluginElement("Properties") Property[] properties, @PluginConfiguration Configuration config, @PluginElement("Filters") Filter filter) {
/*     */     Level level;
/* 158 */     if (loggerName == null) {
/* 159 */       LOGGER.error("Loggers cannot be configured without a name");
/* 160 */       return null;
/*     */     } 
/*     */     
/* 163 */     List<AppenderRef> appenderRefs = Arrays.asList(refs);
/*     */     
/*     */     try {
/* 166 */       level = Level.toLevel(levelName, Level.ERROR);
/* 167 */     } catch (Exception ex) {
/* 168 */       LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", new Object[] { levelName });
/*     */ 
/*     */       
/* 171 */       level = Level.ERROR;
/*     */     } 
/* 173 */     String name = loggerName.equals("root") ? "" : loggerName;
/* 174 */     boolean additive = Booleans.parseBoolean(additivity, true);
/*     */     
/* 176 */     return new AsyncLoggerConfig(name, appenderRefs, filter, level, additive, properties, config, includeLocation(includeLocation));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean includeLocation(String includeLocationConfigValue) {
/* 182 */     return Boolean.parseBoolean(includeLocationConfigValue);
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
/*     */   @Plugin(name = "asyncRoot", category = "Core", printObject = true)
/*     */   public static class RootLogger
/*     */     extends LoggerConfig
/*     */   {
/*     */     @PluginFactory
/*     */     public static LoggerConfig createLogger(@PluginAttribute("additivity") String additivity, @PluginAttribute("level") String levelName, @PluginAttribute("includeLocation") String includeLocation, @PluginElement("AppenderRef") AppenderRef[] refs, @PluginElement("Properties") Property[] properties, @PluginConfiguration Configuration config, @PluginElement("Filters") Filter filter) {
/*     */       Level level;
/* 200 */       List<AppenderRef> appenderRefs = Arrays.asList(refs);
/*     */       
/*     */       try {
/* 203 */         level = Level.toLevel(levelName, Level.ERROR);
/* 204 */       } catch (Exception ex) {
/* 205 */         LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", new Object[] { levelName });
/*     */ 
/*     */         
/* 208 */         level = Level.ERROR;
/*     */       } 
/* 210 */       boolean additive = Booleans.parseBoolean(additivity, true);
/*     */       
/* 212 */       return new AsyncLoggerConfig("", appenderRefs, filter, level, additive, properties, config, includeLocation(includeLocation));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\AsyncLoggerConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */