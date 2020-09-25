/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
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
/*     */ @Plugin(name = "RandomAccessFile", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class RandomAccessFileAppender
/*     */   extends AbstractOutputStreamAppender
/*     */ {
/*     */   private final String fileName;
/*     */   private Object advertisement;
/*     */   private final Advertiser advertiser;
/*     */   
/*     */   private RandomAccessFileAppender(String name, Layout<? extends Serializable> layout, Filter filter, RandomAccessFileManager manager, String filename, boolean ignoreExceptions, boolean immediateFlush, Advertiser advertiser) {
/*  49 */     super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
/*  50 */     if (advertiser != null) {
/*  51 */       Map<String, String> configuration = new HashMap<String, String>(layout.getContentFormat());
/*     */       
/*  53 */       configuration.putAll(manager.getContentFormat());
/*  54 */       configuration.put("contentType", layout.getContentType());
/*  55 */       configuration.put("name", name);
/*  56 */       this.advertisement = advertiser.advertise(configuration);
/*     */     } 
/*  58 */     this.fileName = filename;
/*  59 */     this.advertiser = advertiser;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  64 */     super.stop();
/*  65 */     if (this.advertiser != null) {
/*  66 */       this.advertiser.unadvertise(this.advertisement);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(LogEvent event) {
/*  84 */     ((RandomAccessFileManager)getManager()).setEndOfBatch(event.isEndOfBatch());
/*  85 */     super.append(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/*  94 */     return this.fileName;
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
/*     */   public static RandomAccessFileAppender createAppender(@PluginAttribute("fileName") String fileName, @PluginAttribute("append") String append, @PluginAttribute("name") String name, @PluginAttribute("immediateFlush") String immediateFlush, @PluginAttribute("ignoreExceptions") String ignore, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filters") Filter filter, @PluginAttribute("advertise") String advertise, @PluginAttribute("advertiseURI") String advertiseURI, @PluginConfiguration Configuration config) {
/*     */     PatternLayout patternLayout;
/* 133 */     boolean isAppend = Booleans.parseBoolean(append, true);
/* 134 */     boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
/* 135 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/* 136 */     boolean isAdvertise = Boolean.parseBoolean(advertise);
/*     */     
/* 138 */     if (name == null) {
/* 139 */       LOGGER.error("No name provided for FileAppender");
/* 140 */       return null;
/*     */     } 
/*     */     
/* 143 */     if (fileName == null) {
/* 144 */       LOGGER.error("No filename provided for FileAppender with name " + name);
/*     */       
/* 146 */       return null;
/*     */     } 
/* 148 */     if (layout == null) {
/* 149 */       patternLayout = PatternLayout.createLayout(null, null, null, null, null);
/*     */     }
/* 151 */     RandomAccessFileManager manager = RandomAccessFileManager.getFileManager(fileName, isAppend, isFlush, advertiseURI, (Layout<? extends Serializable>)patternLayout);
/*     */ 
/*     */     
/* 154 */     if (manager == null) {
/* 155 */       return null;
/*     */     }
/*     */     
/* 158 */     return new RandomAccessFileAppender(name, (Layout<? extends Serializable>)patternLayout, filter, manager, fileName, ignoreExceptions, isFlush, isAdvertise ? config.getAdvertiser() : null);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\RandomAccessFileAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */