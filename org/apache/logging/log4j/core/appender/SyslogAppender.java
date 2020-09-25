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
/*     */ import org.apache.logging.log4j.core.net.Protocol;
/*     */ import org.apache.logging.log4j.util.EnglishEnums;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "Syslog", category = "Core", elementType = "appender", printObject = true)
/*     */ public class SyslogAppender
/*     */   extends SocketAppender
/*     */ {
/*     */   protected static final String RFC5424 = "RFC5424";
/*     */   
/*     */   protected SyslogAppender(String name, Layout<? extends Serializable> layout, Filter filter, boolean ignoreExceptions, boolean immediateFlush, AbstractSocketManager manager, Advertiser advertiser) {
/*  49 */     super(name, layout, filter, manager, ignoreExceptions, immediateFlush, advertiser);
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
/*     */ 
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static SyslogAppender createAppender(@PluginAttribute("host") String host, @PluginAttribute("port") String portNum, @PluginAttribute("protocol") String protocol, @PluginAttribute("reconnectionDelay") String delay, @PluginAttribute("immediateFail") String immediateFail, @PluginAttribute("name") String name, @PluginAttribute("immediateFlush") String immediateFlush, @PluginAttribute("ignoreExceptions") String ignore, @PluginAttribute("facility") String facility, @PluginAttribute("id") String id, @PluginAttribute("enterpriseNumber") String ein, @PluginAttribute("includeMDC") String includeMDC, @PluginAttribute("mdcId") String mdcId, @PluginAttribute("mdcPrefix") String mdcPrefix, @PluginAttribute("eventPrefix") String eventPrefix, @PluginAttribute("newLine") String includeNL, @PluginAttribute("newLineEscape") String escapeNL, @PluginAttribute("appName") String appName, @PluginAttribute("messageId") String msgId, @PluginAttribute("mdcExcludes") String excludes, @PluginAttribute("mdcIncludes") String includes, @PluginAttribute("mdcRequired") String required, @PluginAttribute("format") String format, @PluginElement("Filters") Filter filter, @PluginConfiguration Configuration config, @PluginAttribute("charset") String charsetName, @PluginAttribute("exceptionPattern") String exceptionPattern, @PluginElement("LoggerFields") LoggerFields[] loggerFields, @PluginAttribute("advertise") String advertise) {
/* 121 */     boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
/* 122 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/* 123 */     int reconnectDelay = AbstractAppender.parseInt(delay, 0);
/* 124 */     boolean fail = Booleans.parseBoolean(immediateFail, true);
/* 125 */     int port = AbstractAppender.parseInt(portNum, 0);
/* 126 */     boolean isAdvertise = Boolean.parseBoolean(advertise);
/* 127 */     Layout<? extends Serializable> layout = "RFC5424".equalsIgnoreCase(format) ? (Layout<? extends Serializable>)RFC5424Layout.createLayout(facility, id, ein, includeMDC, mdcId, mdcPrefix, eventPrefix, includeNL, escapeNL, appName, msgId, excludes, includes, required, exceptionPattern, "false", loggerFields, config) : (Layout<? extends Serializable>)SyslogLayout.createLayout(facility, includeNL, escapeNL, charsetName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     if (name == null) {
/* 134 */       LOGGER.error("No name provided for SyslogAppender");
/* 135 */       return null;
/*     */     } 
/* 137 */     Protocol p = (Protocol)EnglishEnums.valueOf(Protocol.class, protocol);
/* 138 */     AbstractSocketManager manager = createSocketManager(p, host, port, reconnectDelay, fail, layout);
/* 139 */     if (manager == null) {
/* 140 */       return null;
/*     */     }
/*     */     
/* 143 */     return new SyslogAppender(name, layout, filter, ignoreExceptions, isFlush, manager, isAdvertise ? config.getAdvertiser() : null);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\SyslogAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */