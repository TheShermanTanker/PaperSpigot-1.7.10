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
/*     */ import org.apache.logging.log4j.core.net.JMSTopicManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "JMSTopic", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class JMSTopicAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private final JMSTopicManager manager;
/*     */   
/*     */   private JMSTopicAppender(String name, Filter filter, Layout<? extends Serializable> layout, JMSTopicManager manager, boolean ignoreExceptions) {
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
/*     */   
/*     */   @PluginFactory
/*     */   public static JMSTopicAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("factoryName") String factoryName, @PluginAttribute("providerURL") String providerURL, @PluginAttribute("urlPkgPrefixes") String urlPkgPrefixes, @PluginAttribute("securityPrincipalName") String securityPrincipalName, @PluginAttribute("securityCredentials") String securityCredentials, @PluginAttribute("factoryBindingName") String factoryBindingName, @PluginAttribute("topicBindingName") String topicBindingName, @PluginAttribute("userName") String userName, @PluginAttribute("password") String password, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filters") Filter filter, @PluginAttribute("ignoreExceptions") String ignore) {
/*     */     SerializedLayout serializedLayout;
/*  95 */     if (name == null) {
/*  96 */       LOGGER.error("No name provided for JMSQueueAppender");
/*  97 */       return null;
/*     */     } 
/*  99 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/* 100 */     JMSTopicManager manager = JMSTopicManager.getJMSTopicManager(factoryName, providerURL, urlPkgPrefixes, securityPrincipalName, securityCredentials, factoryBindingName, topicBindingName, userName, password);
/*     */     
/* 102 */     if (manager == null) {
/* 103 */       return null;
/*     */     }
/* 105 */     if (layout == null) {
/* 106 */       serializedLayout = SerializedLayout.createLayout();
/*     */     }
/* 108 */     return new JMSTopicAppender(name, filter, (Layout<? extends Serializable>)serializedLayout, manager, ignoreExceptions);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\JMSTopicAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */