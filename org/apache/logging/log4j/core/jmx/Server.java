/*     */ package org.apache.logging.log4j.core.jmx;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.JMException;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.ObjectName;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.config.LoggerConfig;
/*     */ import org.apache.logging.log4j.core.selector.ContextSelector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Server
/*     */ {
/*     */   private static final String PROPERTY_DISABLE_JMX = "log4j2.disable.jmx";
/*     */   
/*     */   public static String escape(String name) {
/*  65 */     StringBuilder sb = new StringBuilder(name.length() * 2);
/*  66 */     boolean needsQuotes = false;
/*  67 */     for (int i = 0; i < name.length(); i++) {
/*  68 */       char c = name.charAt(i);
/*  69 */       switch (c) {
/*     */         case '*':
/*     */         case ',':
/*     */         case ':':
/*     */         case '=':
/*     */         case '?':
/*     */         case '\\':
/*  76 */           sb.append('\\');
/*  77 */           needsQuotes = true; break;
/*     */       } 
/*  79 */       sb.append(c);
/*     */     } 
/*  81 */     if (needsQuotes) {
/*  82 */       sb.insert(0, '"');
/*  83 */       sb.append('"');
/*     */     } 
/*  85 */     return sb.toString();
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
/*     */   public static void registerMBeans(ContextSelector selector) throws JMException {
/* 102 */     if (Boolean.getBoolean("log4j2.disable.jmx")) {
/* 103 */       StatusLogger.getLogger().debug("JMX disabled for log4j2. Not registering MBeans.");
/*     */       
/*     */       return;
/*     */     } 
/* 107 */     MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
/* 108 */     registerMBeans(selector, mbs);
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
/*     */   public static void registerMBeans(ContextSelector selector, final MBeanServer mbs) throws JMException {
/* 126 */     if (Boolean.getBoolean("log4j2.disable.jmx")) {
/* 127 */       StatusLogger.getLogger().debug("JMX disabled for log4j2. Not registering MBeans.");
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     final Executor executor = Executors.newFixedThreadPool(1);
/* 132 */     registerStatusLogger(mbs, executor);
/* 133 */     registerContextSelector(selector, mbs, executor);
/*     */     
/* 135 */     List<LoggerContext> contexts = selector.getLoggerContexts();
/* 136 */     registerContexts(contexts, mbs, executor);
/*     */     
/* 138 */     for (LoggerContext context : contexts) {
/* 139 */       context.addPropertyChangeListener(new PropertyChangeListener()
/*     */           {
/*     */             public void propertyChange(PropertyChangeEvent evt)
/*     */             {
/* 143 */               if (!"config".equals(evt.getPropertyName())) {
/*     */                 return;
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 149 */               Server.unregisterLoggerConfigs(context, mbs);
/* 150 */               Server.unregisterAppenders(context, mbs);
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 155 */                 Server.registerLoggerConfigs(context, mbs, executor);
/* 156 */                 Server.registerAppenders(context, mbs, executor);
/* 157 */               } catch (Exception ex) {
/* 158 */                 StatusLogger.getLogger().error("Could not register mbeans", ex);
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void registerStatusLogger(MBeanServer mbs, Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/* 169 */     StatusLoggerAdmin mbean = new StatusLoggerAdmin(executor);
/* 170 */     mbs.registerMBean(mbean, mbean.getObjectName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void registerContextSelector(ContextSelector selector, MBeanServer mbs, Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/* 177 */     ContextSelectorAdmin mbean = new ContextSelectorAdmin(selector);
/* 178 */     mbs.registerMBean(mbean, mbean.getObjectName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void registerContexts(List<LoggerContext> contexts, MBeanServer mbs, Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/* 185 */     for (LoggerContext ctx : contexts) {
/* 186 */       LoggerContextAdmin mbean = new LoggerContextAdmin(ctx, executor);
/* 187 */       mbs.registerMBean(mbean, mbean.getObjectName());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void unregisterLoggerConfigs(LoggerContext context, MBeanServer mbs) {
/* 193 */     String pattern = "org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=LoggerConfig,name=%s";
/* 194 */     String search = String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=LoggerConfig,name=%s", new Object[] { context.getName(), "*" });
/* 195 */     unregisterAllMatching(search, mbs);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void unregisterAppenders(LoggerContext context, MBeanServer mbs) {
/* 200 */     String pattern = "org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=Appender,name=%s";
/* 201 */     String search = String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=Appender,name=%s", new Object[] { context.getName(), "*" });
/* 202 */     unregisterAllMatching(search, mbs);
/*     */   }
/*     */   
/*     */   private static void unregisterAllMatching(String search, MBeanServer mbs) {
/*     */     try {
/* 207 */       ObjectName pattern = new ObjectName(search);
/* 208 */       Set<ObjectName> found = mbs.queryNames(pattern, null);
/* 209 */       for (ObjectName objectName : found) {
/* 210 */         mbs.unregisterMBean(objectName);
/*     */       }
/* 212 */     } catch (Exception ex) {
/* 213 */       StatusLogger.getLogger().error("Could not unregister " + search, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void registerLoggerConfigs(LoggerContext ctx, MBeanServer mbs, Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/* 221 */     Map<String, LoggerConfig> map = ctx.getConfiguration().getLoggers();
/* 222 */     for (String name : map.keySet()) {
/* 223 */       LoggerConfig cfg = map.get(name);
/* 224 */       LoggerConfigAdmin mbean = new LoggerConfigAdmin(ctx.getName(), cfg);
/* 225 */       mbs.registerMBean(mbean, mbean.getObjectName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void registerAppenders(LoggerContext ctx, MBeanServer mbs, Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/* 232 */     Map<String, Appender> map = ctx.getConfiguration().getAppenders();
/* 233 */     for (String name : map.keySet()) {
/* 234 */       Appender appender = map.get(name);
/* 235 */       AppenderAdmin mbean = new AppenderAdmin(ctx.getName(), appender);
/* 236 */       mbs.registerMBean(mbean, mbean.getObjectName());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\Server.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */