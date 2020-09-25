/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.layout.LoggerFields;
/*     */ import org.apache.logging.log4j.core.layout.RFC5424Layout;
/*     */ import org.apache.logging.log4j.core.layout.SyslogLayout;
/*     */ import org.apache.logging.log4j.core.net.AbstractSocketManager;
/*     */ import org.apache.logging.log4j.core.net.Advertiser;
/*     */ import org.apache.logging.log4j.core.net.TLSSocketManager;
/*     */ import org.apache.logging.log4j.core.net.ssl.SSLConfiguration;
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
/*     */ @Plugin(name = "TLSSyslog", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class TLSSyslogAppender
/*     */   extends SyslogAppender
/*     */ {
/*     */   protected TLSSyslogAppender(String name, Layout<? extends Serializable> layout, Filter filter, boolean ignoreExceptions, boolean immediateFlush, AbstractSocketManager manager, Advertiser advertiser) {
/*  45 */     super(name, layout, filter, ignoreExceptions, immediateFlush, manager, advertiser);
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
/*     */   public static TLSSyslogAppender createAppender(@PluginAttribute("host") String host, @PluginAttribute("port") String portNum, @PluginElement("ssl") SSLConfiguration sslConfig, @PluginAttribute("reconnectionDelay") String delay, @PluginAttribute("immediateFail") String immediateFail, @PluginAttribute("name") String name, @PluginAttribute("immediateFlush") String immediateFlush, @PluginAttribute("ignoreExceptions") String ignore, @PluginAttribute("facility") String facility, @PluginAttribute("id") String id, @PluginAttribute("enterpriseNumber") String ein, @PluginAttribute("includeMDC") String includeMDC, @PluginAttribute("mdcId") String mdcId, @PluginAttribute("mdcPrefix") String mdcPrefix, @PluginAttribute("eventPrefix") String eventPrefix, @PluginAttribute("newLine") String includeNL, @PluginAttribute("newLineEscape") String escapeNL, @PluginAttribute("appName") String appName, @PluginAttribute("messageId") String msgId, @PluginAttribute("mdcExcludes") String excludes, @PluginAttribute("mdcIncludes") String includes, @PluginAttribute("mdcRequired") String required, @PluginAttribute("format") String format, @PluginElement("filters") Filter filter, @PluginConfiguration Configuration config, @PluginAttribute("charset") String charsetName, @PluginAttribute("exceptionPattern") String exceptionPattern, @PluginElement("LoggerFields") LoggerFields[] loggerFields, @PluginAttribute("advertise") String advertise) {
/* 114 */     boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
/* 115 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/* 116 */     int reconnectDelay = AbstractAppender.parseInt(delay, 0);
/* 117 */     boolean fail = Booleans.parseBoolean(immediateFail, true);
/* 118 */     int port = AbstractAppender.parseInt(portNum, 0);
/* 119 */     boolean isAdvertise = Boolean.parseBoolean(advertise);
/*     */     
/* 121 */     Layout<? extends Serializable> layout = "RFC5424".equalsIgnoreCase(format) ? (Layout<? extends Serializable>)RFC5424Layout.createLayout(facility, id, ein, includeMDC, mdcId, mdcPrefix, eventPrefix, includeNL, escapeNL, appName, msgId, excludes, includes, required, exceptionPattern, "true", loggerFields, config) : (Layout<? extends Serializable>)SyslogLayout.createLayout(facility, includeNL, escapeNL, charsetName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (name == null) {
/* 128 */       LOGGER.error("No name provided for TLSSyslogAppender");
/* 129 */       return null;
/*     */     } 
/* 131 */     AbstractSocketManager manager = createSocketManager(sslConfig, host, port, reconnectDelay, fail, layout);
/* 132 */     if (manager == null) {
/* 133 */       return null;
/*     */     }
/*     */     
/* 136 */     return new TLSSyslogAppender(name, layout, filter, ignoreExceptions, isFlush, manager, isAdvertise ? config.getAdvertiser() : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AbstractSocketManager createSocketManager(SSLConfiguration sslConf, String host, int port, int reconnectDelay, boolean fail, Layout<? extends Serializable> layout) {
/* 143 */     return (AbstractSocketManager)TLSSocketManager.getSocketManager(sslConf, host, port, reconnectDelay, fail, layout);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\TLSSyslogAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */