/*     */ package org.apache.logging.log4j.core.appender.rewrite;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.config.AppenderControl;
/*     */ import org.apache.logging.log4j.core.config.AppenderRef;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
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
/*     */ @Plugin(name = "Rewrite", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class RewriteAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private final Configuration config;
/*  43 */   private final ConcurrentMap<String, AppenderControl> appenders = new ConcurrentHashMap<String, AppenderControl>();
/*     */   
/*     */   private final RewritePolicy rewritePolicy;
/*     */   
/*     */   private final AppenderRef[] appenderRefs;
/*     */   
/*     */   private RewriteAppender(String name, Filter filter, boolean ignoreExceptions, AppenderRef[] appenderRefs, RewritePolicy rewritePolicy, Configuration config) {
/*  50 */     super(name, filter, null, ignoreExceptions);
/*  51 */     this.config = config;
/*  52 */     this.rewritePolicy = rewritePolicy;
/*  53 */     this.appenderRefs = appenderRefs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  58 */     Map<String, Appender> map = this.config.getAppenders();
/*  59 */     for (AppenderRef ref : this.appenderRefs) {
/*  60 */       String name = ref.getRef();
/*  61 */       Appender appender = map.get(name);
/*  62 */       if (appender != null) {
/*  63 */         Filter filter = (appender instanceof AbstractAppender) ? ((AbstractAppender)appender).getFilter() : null;
/*     */         
/*  65 */         this.appenders.put(name, new AppenderControl(appender, ref.getLevel(), filter));
/*     */       } else {
/*  67 */         LOGGER.error("Appender " + ref + " cannot be located. Reference ignored");
/*     */       } 
/*     */     } 
/*  70 */     super.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  75 */     super.stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(LogEvent event) {
/*  84 */     if (this.rewritePolicy != null) {
/*  85 */       event = this.rewritePolicy.rewrite(event);
/*     */     }
/*  87 */     for (AppenderControl control : this.appenders.values()) {
/*  88 */       control.callAppender(event);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static RewriteAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("ignoreExceptions") String ignore, @PluginElement("AppenderRef") AppenderRef[] appenderRefs, @PluginConfiguration Configuration config, @PluginElement("RewritePolicy") RewritePolicy rewritePolicy, @PluginElement("Filter") Filter filter) {
/* 112 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/* 113 */     if (name == null) {
/* 114 */       LOGGER.error("No name provided for RewriteAppender");
/* 115 */       return null;
/*     */     } 
/* 117 */     if (appenderRefs == null) {
/* 118 */       LOGGER.error("No appender references defined for RewriteAppender");
/* 119 */       return null;
/*     */     } 
/* 121 */     return new RewriteAppender(name, filter, ignoreExceptions, appenderRefs, rewritePolicy, config);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rewrite\RewriteAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */