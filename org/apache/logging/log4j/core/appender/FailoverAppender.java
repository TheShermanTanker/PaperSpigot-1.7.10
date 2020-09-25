/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LoggingException;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.AppenderControl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "Failover", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class FailoverAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private static final int DEFAULT_INTERVAL_SECONDS = 60;
/*     */   private final String primaryRef;
/*     */   private final String[] failovers;
/*     */   private final Configuration config;
/*     */   private AppenderControl primary;
/*  55 */   private final List<AppenderControl> failoverAppenders = new ArrayList<AppenderControl>();
/*     */   
/*     */   private final long intervalMillis;
/*     */   
/*  59 */   private long nextCheckMillis = 0L;
/*     */   
/*     */   private volatile boolean failure = false;
/*     */ 
/*     */   
/*     */   private FailoverAppender(String name, Filter filter, String primary, String[] failovers, int intervalMillis, Configuration config, boolean ignoreExceptions) {
/*  65 */     super(name, filter, (Layout<? extends Serializable>)null, ignoreExceptions);
/*  66 */     this.primaryRef = primary;
/*  67 */     this.failovers = failovers;
/*  68 */     this.config = config;
/*  69 */     this.intervalMillis = intervalMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  75 */     Map<String, Appender> map = this.config.getAppenders();
/*  76 */     int errors = 0;
/*  77 */     if (map.containsKey(this.primaryRef)) {
/*  78 */       this.primary = new AppenderControl(map.get(this.primaryRef), null, null);
/*     */     } else {
/*  80 */       LOGGER.error("Unable to locate primary Appender " + this.primaryRef);
/*  81 */       errors++;
/*     */     } 
/*  83 */     for (String name : this.failovers) {
/*  84 */       if (map.containsKey(name)) {
/*  85 */         this.failoverAppenders.add(new AppenderControl(map.get(name), null, null));
/*     */       } else {
/*  87 */         LOGGER.error("Failover appender " + name + " is not configured");
/*     */       } 
/*     */     } 
/*  90 */     if (this.failoverAppenders.size() == 0) {
/*  91 */       LOGGER.error("No failover appenders are available");
/*  92 */       errors++;
/*     */     } 
/*  94 */     if (errors == 0) {
/*  95 */       super.start();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(LogEvent event) {
/* 105 */     if (!isStarted()) {
/* 106 */       error("FailoverAppender " + getName() + " did not start successfully");
/*     */       return;
/*     */     } 
/* 109 */     if (!this.failure) {
/* 110 */       callAppender(event);
/*     */     } else {
/* 112 */       long currentMillis = System.currentTimeMillis();
/* 113 */       if (currentMillis >= this.nextCheckMillis) {
/* 114 */         callAppender(event);
/*     */       } else {
/* 116 */         failover(event, (Exception)null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void callAppender(LogEvent event) {
/*     */     try {
/* 123 */       this.primary.callAppender(event);
/* 124 */     } catch (Exception ex) {
/* 125 */       this.nextCheckMillis = System.currentTimeMillis() + this.intervalMillis;
/* 126 */       this.failure = true;
/* 127 */       failover(event, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void failover(LogEvent event, Exception ex) {
/* 132 */     LoggingException loggingException = (ex != null) ? ((ex instanceof LoggingException) ? (LoggingException)ex : new LoggingException(ex)) : null;
/*     */     
/* 134 */     boolean written = false;
/* 135 */     Exception failoverException = null;
/* 136 */     for (AppenderControl control : this.failoverAppenders) {
/*     */       try {
/* 138 */         control.callAppender(event);
/* 139 */         written = true;
/*     */         break;
/* 141 */       } catch (Exception fex) {
/* 142 */         if (failoverException == null) {
/* 143 */           failoverException = fex;
/*     */         }
/*     */       } 
/*     */     } 
/* 147 */     if (!written && !ignoreExceptions()) {
/* 148 */       if (loggingException != null) {
/* 149 */         throw loggingException;
/*     */       }
/* 151 */       throw new LoggingException("Unable to write to failover appenders", failoverException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 158 */     StringBuilder sb = new StringBuilder(getName());
/* 159 */     sb.append(" primary=").append(this.primary).append(", failover={");
/* 160 */     boolean first = true;
/* 161 */     for (String str : this.failovers) {
/* 162 */       if (!first) {
/* 163 */         sb.append(", ");
/*     */       }
/* 165 */       sb.append(str);
/* 166 */       first = false;
/*     */     } 
/* 168 */     sb.append("}");
/* 169 */     return sb.toString();
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
/*     */   public static FailoverAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("primary") String primary, @PluginElement("Failovers") String[] failovers, @PluginAttribute("retryInterval") String retryIntervalString, @PluginConfiguration Configuration config, @PluginElement("Filters") Filter filter, @PluginAttribute("ignoreExceptions") String ignore) {
/*     */     int retryIntervalMillis;
/* 193 */     if (name == null) {
/* 194 */       LOGGER.error("A name for the Appender must be specified");
/* 195 */       return null;
/*     */     } 
/* 197 */     if (primary == null) {
/* 198 */       LOGGER.error("A primary Appender must be specified");
/* 199 */       return null;
/*     */     } 
/* 201 */     if (failovers == null || failovers.length == 0) {
/* 202 */       LOGGER.error("At least one failover Appender must be specified");
/* 203 */       return null;
/*     */     } 
/*     */     
/* 206 */     int seconds = parseInt(retryIntervalString, 60);
/*     */     
/* 208 */     if (seconds >= 0) {
/* 209 */       retryIntervalMillis = seconds * 1000;
/*     */     } else {
/* 211 */       LOGGER.warn("Interval " + retryIntervalString + " is less than zero. Using default");
/* 212 */       retryIntervalMillis = 60000;
/*     */     } 
/*     */     
/* 215 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/*     */     
/* 217 */     return new FailoverAppender(name, filter, primary, failovers, retryIntervalMillis, config, ignoreExceptions);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\FailoverAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */