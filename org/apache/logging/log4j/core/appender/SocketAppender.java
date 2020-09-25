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
/*     */ import org.apache.logging.log4j.core.layout.SerializedLayout;
/*     */ import org.apache.logging.log4j.core.net.AbstractSocketManager;
/*     */ import org.apache.logging.log4j.core.net.Advertiser;
/*     */ import org.apache.logging.log4j.core.net.DatagramSocketManager;
/*     */ import org.apache.logging.log4j.core.net.Protocol;
/*     */ import org.apache.logging.log4j.core.net.TCPSocketManager;
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
/*     */ @Plugin(name = "Socket", category = "Core", elementType = "appender", printObject = true)
/*     */ public class SocketAppender
/*     */   extends AbstractOutputStreamAppender
/*     */ {
/*     */   private Object advertisement;
/*     */   private final Advertiser advertiser;
/*     */   
/*     */   protected SocketAppender(String name, Layout<? extends Serializable> layout, Filter filter, AbstractSocketManager manager, boolean ignoreExceptions, boolean immediateFlush, Advertiser advertiser) {
/*  51 */     super(name, layout, filter, ignoreExceptions, immediateFlush, (OutputStreamManager)manager);
/*  52 */     if (advertiser != null) {
/*  53 */       Map<String, String> configuration = new HashMap<String, String>(layout.getContentFormat());
/*  54 */       configuration.putAll(manager.getContentFormat());
/*  55 */       configuration.put("contentType", layout.getContentType());
/*  56 */       configuration.put("name", name);
/*  57 */       this.advertisement = advertiser.advertise(configuration);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static SocketAppender createAppender(@PluginAttribute("host") String host, @PluginAttribute("port") String portNum, @PluginAttribute("protocol") String protocol, @PluginAttribute("reconnectionDelay") String delay, @PluginAttribute("immediateFail") String immediateFail, @PluginAttribute("name") String name, @PluginAttribute("immediateFlush") String immediateFlush, @PluginAttribute("ignoreExceptions") String ignore, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filters") Filter filter, @PluginAttribute("advertise") String advertise, @PluginConfiguration Configuration config) {
/*     */     SerializedLayout serializedLayout;
/* 102 */     boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
/* 103 */     boolean isAdvertise = Boolean.parseBoolean(advertise);
/* 104 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/* 105 */     boolean fail = Booleans.parseBoolean(immediateFail, true);
/* 106 */     int reconnectDelay = AbstractAppender.parseInt(delay, 0);
/* 107 */     int port = AbstractAppender.parseInt(portNum, 0);
/* 108 */     if (layout == null) {
/* 109 */       serializedLayout = SerializedLayout.createLayout();
/*     */     }
/*     */     
/* 112 */     if (name == null) {
/* 113 */       LOGGER.error("No name provided for SocketAppender");
/* 114 */       return null;
/*     */     } 
/*     */     
/* 117 */     Protocol p = (Protocol)EnglishEnums.valueOf(Protocol.class, (protocol != null) ? protocol : Protocol.TCP.name());
/* 118 */     if (p.equals(Protocol.UDP)) {
/* 119 */       isFlush = true;
/*     */     }
/*     */     
/* 122 */     AbstractSocketManager manager = createSocketManager(p, host, port, reconnectDelay, fail, (Layout<? extends Serializable>)serializedLayout);
/* 123 */     if (manager == null) {
/* 124 */       return null;
/*     */     }
/*     */     
/* 127 */     return new SocketAppender(name, (Layout<? extends Serializable>)serializedLayout, filter, manager, ignoreExceptions, isFlush, isAdvertise ? config.getAdvertiser() : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static AbstractSocketManager createSocketManager(Protocol p, String host, int port, int delay, boolean immediateFail, Layout<? extends Serializable> layout) {
/* 134 */     switch (p) {
/*     */       case TCP:
/* 136 */         return (AbstractSocketManager)TCPSocketManager.getSocketManager(host, port, delay, immediateFail, layout);
/*     */       case UDP:
/* 138 */         return (AbstractSocketManager)DatagramSocketManager.getSocketManager(host, port, layout);
/*     */     } 
/* 140 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\SocketAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */