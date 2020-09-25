/*     */ package org.apache.logging.log4j.core.jmx;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.StringWriter;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationBroadcasterSupport;
/*     */ import javax.management.ObjectName;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Assert;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.Closer;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoggerContextAdmin
/*     */   extends NotificationBroadcasterSupport
/*     */   implements LoggerContextAdminMBean, PropertyChangeListener
/*     */ {
/*     */   private static final int PAGE = 4096;
/*     */   private static final int TEXT_BUFFER = 65536;
/*     */   private static final int BUFFER_SIZE = 2048;
/*  58 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
/*     */   
/*  60 */   private final AtomicLong sequenceNo = new AtomicLong();
/*     */ 
/*     */   
/*     */   private final ObjectName objectName;
/*     */ 
/*     */   
/*     */   private final LoggerContext loggerContext;
/*     */ 
/*     */   
/*     */   private String customConfigText;
/*     */ 
/*     */   
/*     */   public LoggerContextAdmin(LoggerContext loggerContext, Executor executor) {
/*  73 */     super(executor, new MBeanNotificationInfo[] { createNotificationInfo() });
/*  74 */     this.loggerContext = (LoggerContext)Assert.isNotNull(loggerContext, "loggerContext");
/*     */     try {
/*  76 */       String ctxName = Server.escape(loggerContext.getName());
/*  77 */       String name = String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s", new Object[] { ctxName });
/*  78 */       this.objectName = new ObjectName(name);
/*  79 */     } catch (Exception e) {
/*  80 */       throw new IllegalStateException(e);
/*     */     } 
/*  82 */     loggerContext.addPropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   private static MBeanNotificationInfo createNotificationInfo() {
/*  86 */     String[] notifTypes = { "com.apache.logging.log4j.core.jmx.config.reconfigured" };
/*     */     
/*  88 */     String name = Notification.class.getName();
/*  89 */     String description = "Configuration reconfigured";
/*  90 */     return new MBeanNotificationInfo(notifTypes, name, "Configuration reconfigured");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatus() {
/*  95 */     return this.loggerContext.getStatus().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 100 */     return this.loggerContext.getName();
/*     */   }
/*     */   
/*     */   private Configuration getConfig() {
/* 104 */     return this.loggerContext.getConfiguration();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConfigLocationURI() {
/* 109 */     if (this.loggerContext.getConfigLocation() != null) {
/* 110 */       return String.valueOf(this.loggerContext.getConfigLocation());
/*     */     }
/* 112 */     if (getConfigName() != null) {
/* 113 */       return String.valueOf((new File(getConfigName())).toURI());
/*     */     }
/* 115 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConfigLocationURI(String configLocation) throws URISyntaxException, IOException {
/* 121 */     LOGGER.debug("---------");
/* 122 */     LOGGER.debug("Remote request to reconfigure using location " + configLocation);
/*     */     
/* 124 */     URI uri = new URI(configLocation);
/*     */ 
/*     */ 
/*     */     
/* 128 */     uri.toURL().openStream().close();
/*     */     
/* 130 */     this.loggerContext.setConfigLocation(uri);
/* 131 */     LOGGER.debug("Completed remote request to reconfigure.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent evt) {
/* 136 */     if (!"config".equals(evt.getPropertyName())) {
/*     */       return;
/*     */     }
/*     */     
/* 140 */     if (this.loggerContext.getConfiguration().getName() != null) {
/* 141 */       this.customConfigText = null;
/*     */     }
/* 143 */     Notification notif = new Notification("com.apache.logging.log4j.core.jmx.config.reconfigured", getObjectName(), nextSeqNo(), now(), null);
/*     */     
/* 145 */     sendNotification(notif);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConfigText() throws IOException {
/* 150 */     return getConfigText(Charsets.UTF_8.name());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConfigText(String charsetName) throws IOException {
/* 155 */     if (this.customConfigText != null) {
/* 156 */       return this.customConfigText;
/*     */     }
/*     */     try {
/* 159 */       Charset charset = Charset.forName(charsetName);
/* 160 */       return readContents(new URI(getConfigLocationURI()), charset);
/* 161 */     } catch (Exception ex) {
/* 162 */       StringWriter sw = new StringWriter(2048);
/* 163 */       ex.printStackTrace(new PrintWriter(sw));
/* 164 */       return sw.toString();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setConfigText(String configText, String charsetName) {
/* 170 */     String old = this.customConfigText;
/* 171 */     this.customConfigText = (String)Assert.isNotNull(configText, "configText");
/* 172 */     LOGGER.debug("---------");
/* 173 */     LOGGER.debug("Remote request to reconfigure from config text.");
/*     */     
/*     */     try {
/* 176 */       InputStream in = new ByteArrayInputStream(configText.getBytes(charsetName));
/*     */       
/* 178 */       ConfigurationFactory.ConfigurationSource source = new ConfigurationFactory.ConfigurationSource(in);
/* 179 */       Configuration updated = ConfigurationFactory.getInstance().getConfiguration(source);
/*     */       
/* 181 */       this.loggerContext.start(updated);
/* 182 */       LOGGER.debug("Completed remote request to reconfigure from config text.");
/* 183 */     } catch (Exception ex) {
/* 184 */       this.customConfigText = old;
/* 185 */       String msg = "Could not reconfigure from config text";
/* 186 */       LOGGER.error("Could not reconfigure from config text", ex);
/* 187 */       throw new IllegalArgumentException("Could not reconfigure from config text", ex);
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
/*     */   private String readContents(URI uri, Charset charset) throws IOException {
/* 199 */     InputStream in = null;
/* 200 */     Reader reader = null;
/*     */     try {
/* 202 */       in = uri.toURL().openStream();
/* 203 */       reader = new InputStreamReader(in, charset);
/* 204 */       StringBuilder result = new StringBuilder(65536);
/* 205 */       char[] buff = new char[4096];
/* 206 */       int count = -1;
/* 207 */       while ((count = reader.read(buff)) >= 0) {
/* 208 */         result.append(buff, 0, count);
/*     */       }
/* 210 */       return result.toString();
/*     */     } finally {
/* 212 */       Closer.closeSilent(in);
/* 213 */       Closer.closeSilent(reader);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConfigName() {
/* 219 */     return getConfig().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConfigClassName() {
/* 224 */     return getConfig().getClass().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConfigFilter() {
/* 229 */     return String.valueOf(getConfig().getFilter());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConfigMonitorClassName() {
/* 234 */     return getConfig().getConfigurationMonitor().getClass().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, String> getConfigProperties() {
/* 239 */     return getConfig().getProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName getObjectName() {
/* 249 */     return this.objectName;
/*     */   }
/*     */   
/*     */   private long nextSeqNo() {
/* 253 */     return this.sequenceNo.getAndIncrement();
/*     */   }
/*     */   
/*     */   private long now() {
/* 257 */     return System.currentTimeMillis();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\LoggerContextAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */