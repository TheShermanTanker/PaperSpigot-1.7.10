/*     */ package org.apache.logging.log4j.core.jmx;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.management.ObjectName;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.core.config.AppenderRef;
/*     */ import org.apache.logging.log4j.core.config.LoggerConfig;
/*     */ import org.apache.logging.log4j.core.helpers.Assert;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoggerConfigAdmin
/*     */   implements LoggerConfigAdminMBean
/*     */ {
/*     */   private final String contextName;
/*     */   private final LoggerConfig loggerConfig;
/*     */   private final ObjectName objectName;
/*     */   
/*     */   public LoggerConfigAdmin(String contextName, LoggerConfig loggerConfig) {
/*  45 */     this.contextName = (String)Assert.isNotNull(contextName, "contextName");
/*  46 */     this.loggerConfig = (LoggerConfig)Assert.isNotNull(loggerConfig, "loggerConfig");
/*     */     try {
/*  48 */       String ctxName = Server.escape(this.contextName);
/*  49 */       String configName = Server.escape(loggerConfig.getName());
/*  50 */       String name = String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=LoggerConfig,name=%s", new Object[] { ctxName, configName });
/*  51 */       this.objectName = new ObjectName(name);
/*  52 */     } catch (Exception e) {
/*  53 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName getObjectName() {
/*  64 */     return this.objectName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  69 */     return this.loggerConfig.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLevel() {
/*  74 */     return this.loggerConfig.getLevel().name();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(String level) {
/*  79 */     this.loggerConfig.setLevel(Level.valueOf(level));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAdditive() {
/*  84 */     return this.loggerConfig.isAdditive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAdditive(boolean additive) {
/*  89 */     this.loggerConfig.setAdditive(additive);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncludeLocation() {
/*  94 */     return this.loggerConfig.isIncludeLocation();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFilter() {
/*  99 */     return String.valueOf(this.loggerConfig.getFilter());
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getAppenderRefs() {
/* 104 */     List<AppenderRef> refs = this.loggerConfig.getAppenderRefs();
/* 105 */     String[] result = new String[refs.size()];
/* 106 */     for (int i = 0; i < result.length; i++) {
/* 107 */       result[i] = ((AppenderRef)refs.get(i)).getRef();
/*     */     }
/* 109 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\LoggerConfigAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */