/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
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
/*     */ @Plugin(name = "File", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class FileAppender
/*     */   extends AbstractOutputStreamAppender
/*     */ {
/*     */   private final String fileName;
/*     */   private final Advertiser advertiser;
/*     */   private Object advertisement;
/*     */   
/*     */   private FileAppender(String name, Layout<? extends Serializable> layout, Filter filter, FileManager manager, String filename, boolean ignoreExceptions, boolean immediateFlush, Advertiser advertiser) {
/*  48 */     super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
/*  49 */     if (advertiser != null) {
/*  50 */       Map<String, String> configuration = new HashMap<String, String>(layout.getContentFormat());
/*  51 */       configuration.putAll(manager.getContentFormat());
/*  52 */       configuration.put("contentType", layout.getContentType());
/*  53 */       configuration.put("name", name);
/*  54 */       this.advertisement = advertiser.advertise(configuration);
/*     */     } 
/*  56 */     this.fileName = filename;
/*  57 */     this.advertiser = advertiser;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  62 */     super.stop();
/*  63 */     if (this.advertiser != null) {
/*  64 */       this.advertiser.unadvertise(this.advertisement);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/*  73 */     return this.fileName;
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
/*     */   @PluginFactory
/*     */   public static FileAppender createAppender(@PluginAttribute("fileName") String fileName, @PluginAttribute("append") String append, @PluginAttribute("locking") String locking, @PluginAttribute("name") String name, @PluginAttribute("immediateFlush") String immediateFlush, @PluginAttribute("ignoreExceptions") String ignore, @PluginAttribute("bufferedIO") String bufferedIO, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filters") Filter filter, @PluginAttribute("advertise") String advertise, @PluginAttribute("advertiseURI") String advertiseURI, @PluginConfiguration Configuration config) {
/*     */     PatternLayout patternLayout;
/* 111 */     boolean isAppend = Booleans.parseBoolean(append, true);
/* 112 */     boolean isLocking = Boolean.parseBoolean(locking);
/* 113 */     boolean isBuffered = Booleans.parseBoolean(bufferedIO, true);
/* 114 */     boolean isAdvertise = Boolean.parseBoolean(advertise);
/* 115 */     if (isLocking && isBuffered) {
/* 116 */       if (bufferedIO != null) {
/* 117 */         LOGGER.warn("Locking and buffering are mutually exclusive. No buffering will occur for " + fileName);
/*     */       }
/* 119 */       isBuffered = false;
/*     */     } 
/* 121 */     boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
/* 122 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/*     */     
/* 124 */     if (name == null) {
/* 125 */       LOGGER.error("No name provided for FileAppender");
/* 126 */       return null;
/*     */     } 
/*     */     
/* 129 */     if (fileName == null) {
/* 130 */       LOGGER.error("No filename provided for FileAppender with name " + name);
/* 131 */       return null;
/*     */     } 
/* 133 */     if (layout == null) {
/* 134 */       patternLayout = PatternLayout.createLayout(null, null, null, null, null);
/*     */     }
/*     */     
/* 137 */     FileManager manager = FileManager.getFileManager(fileName, isAppend, isLocking, isBuffered, advertiseURI, (Layout<? extends Serializable>)patternLayout);
/*     */     
/* 139 */     if (manager == null) {
/* 140 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 144 */     return new FileAppender(name, (Layout<? extends Serializable>)patternLayout, filter, manager, fileName, ignoreExceptions, isFlush, isAdvertise ? config.getAdvertiser() : null);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\FileAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */