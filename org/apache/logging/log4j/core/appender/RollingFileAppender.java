/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
/*     */ import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
/*     */ import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
/*     */ import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*     */ import org.apache.logging.log4j.core.net.Advertiser;
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
/*     */ @Plugin(name = "RollingFile", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class RollingFileAppender
/*     */   extends AbstractOutputStreamAppender
/*     */ {
/*     */   private final String fileName;
/*     */   private final String filePattern;
/*     */   private Object advertisement;
/*     */   private final Advertiser advertiser;
/*     */   
/*     */   private RollingFileAppender(String name, Layout<? extends Serializable> layout, Filter filter, RollingFileManager manager, String fileName, String filePattern, boolean ignoreExceptions, boolean immediateFlush, Advertiser advertiser) {
/*  57 */     super(name, layout, filter, ignoreExceptions, immediateFlush, (OutputStreamManager)manager);
/*  58 */     if (advertiser != null) {
/*  59 */       Map<String, String> configuration = new HashMap<String, String>(layout.getContentFormat());
/*  60 */       configuration.put("contentType", layout.getContentType());
/*  61 */       configuration.put("name", name);
/*  62 */       this.advertisement = advertiser.advertise(configuration);
/*     */     } 
/*  64 */     this.fileName = fileName;
/*  65 */     this.filePattern = filePattern;
/*  66 */     this.advertiser = advertiser;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  71 */     super.stop();
/*  72 */     if (this.advertiser != null) {
/*  73 */       this.advertiser.unadvertise(this.advertisement);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(LogEvent event) {
/*  84 */     ((RollingFileManager)getManager()).checkRollover(event);
/*  85 */     super.append(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/*  93 */     return this.fileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFilePattern() {
/* 101 */     return this.filePattern;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static RollingFileAppender createAppender(@PluginAttribute("fileName") String fileName, @PluginAttribute("filePattern") String filePattern, @PluginAttribute("append") String append, @PluginAttribute("name") String name, @PluginAttribute("bufferedIO") String bufferedIO, @PluginAttribute("immediateFlush") String immediateFlush, @PluginElement("Policy") TriggeringPolicy policy, @PluginElement("Strategy") RolloverStrategy strategy, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filter") Filter filter, @PluginAttribute("ignoreExceptions") String ignore, @PluginAttribute("advertise") String advertise, @PluginAttribute("advertiseURI") String advertiseURI, @PluginConfiguration Configuration config) {
/*     */     DefaultRolloverStrategy defaultRolloverStrategy;
/*     */     PatternLayout patternLayout;
/* 141 */     boolean isAppend = Booleans.parseBoolean(append, true);
/* 142 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/* 143 */     boolean isBuffered = Booleans.parseBoolean(bufferedIO, true);
/* 144 */     boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
/* 145 */     boolean isAdvertise = Boolean.parseBoolean(advertise);
/* 146 */     if (name == null) {
/* 147 */       LOGGER.error("No name provided for FileAppender");
/* 148 */       return null;
/*     */     } 
/*     */     
/* 151 */     if (fileName == null) {
/* 152 */       LOGGER.error("No filename was provided for FileAppender with name " + name);
/* 153 */       return null;
/*     */     } 
/*     */     
/* 156 */     if (filePattern == null) {
/* 157 */       LOGGER.error("No filename pattern provided for FileAppender with name " + name);
/* 158 */       return null;
/*     */     } 
/*     */     
/* 161 */     if (policy == null) {
/* 162 */       LOGGER.error("A TriggeringPolicy must be provided");
/* 163 */       return null;
/*     */     } 
/*     */     
/* 166 */     if (strategy == null) {
/* 167 */       defaultRolloverStrategy = DefaultRolloverStrategy.createStrategy(null, null, null, String.valueOf(-1), config);
/*     */     }
/*     */ 
/*     */     
/* 171 */     if (layout == null) {
/* 172 */       patternLayout = PatternLayout.createLayout(null, null, null, null, null);
/*     */     }
/*     */     
/* 175 */     RollingFileManager manager = RollingFileManager.getFileManager(fileName, filePattern, isAppend, isBuffered, policy, (RolloverStrategy)defaultRolloverStrategy, advertiseURI, (Layout)patternLayout);
/*     */     
/* 177 */     if (manager == null) {
/* 178 */       return null;
/*     */     }
/*     */     
/* 181 */     return new RollingFileAppender(name, (Layout<? extends Serializable>)patternLayout, filter, manager, fileName, filePattern, ignoreExceptions, isFlush, isAdvertise ? config.getAdvertiser() : null);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\RollingFileAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */