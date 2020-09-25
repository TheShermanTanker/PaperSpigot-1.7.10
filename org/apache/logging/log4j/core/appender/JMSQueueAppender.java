/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.layout.SerializedLayout;
/*     */ import org.apache.logging.log4j.core.net.JMSQueueManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "JMSQueue", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class JMSQueueAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private final JMSQueueManager manager;
/*     */   
/*     */   private JMSQueueAppender(String name, Filter filter, Layout<? extends Serializable> layout, JMSQueueManager manager, boolean ignoreExceptions) {
/*  42 */     super(name, filter, layout, ignoreExceptions);
/*  43 */     this.manager = manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(LogEvent event) {
/*     */     try {
/*  54 */       this.manager.send(getLayout().toSerializable(event));
/*  55 */     } catch (Exception ex) {
/*  56 */       throw new AppenderLoggingException(ex);
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
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static JMSQueueAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("factoryName") String factoryName, @PluginAttribute("providerURL") String providerURL, @PluginAttribute("urlPkgPrefixes") String urlPkgPrefixes, @PluginAttribute("securityPrincipalName") String securityPrincipalName, @PluginAttribute("securityCredentials") String securityCredentials, @PluginAttribute("factoryBindingName") String factoryBindingName, @PluginAttribute("queueBindingName") String queueBindingName, @PluginAttribute("userName") String userName, @PluginAttribute("password") String password, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filter") Filter filter, @PluginAttribute("ignoreExceptions") String ignore) {
/*     */     SerializedLayout serializedLayout;
/*  94 */     if (name == null) {
/*  95 */       LOGGER.error("No name provided for JMSQueueAppender");
/*  96 */       return null;
/*     */     } 
/*  98 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/*  99 */     JMSQueueManager manager = JMSQueueManager.getJMSQueueManager(factoryName, providerURL, urlPkgPrefixes, securityPrincipalName, securityCredentials, factoryBindingName, queueBindingName, userName, password);
/*     */     
/* 101 */     if (manager == null) {
/* 102 */       return null;
/*     */     }
/* 104 */     if (layout == null) {
/* 105 */       serializedLayout = SerializedLayout.createLayout();
/*     */     }
/* 107 */     return new JMSQueueAppender(name, filter, (Layout<? extends Serializable>)serializedLayout, manager, ignoreExceptions);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\JMSQueueAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */