/*     */ package org.apache.logging.log4j.core.appender.db.jpa;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "JPA", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class JPAAppender
/*     */   extends AbstractDatabaseAppender<JPADatabaseManager>
/*     */ {
/*     */   private final String description;
/*     */   
/*     */   private JPAAppender(String name, Filter filter, boolean ignoreExceptions, JPADatabaseManager manager) {
/*  45 */     super(name, filter, ignoreExceptions, manager);
/*  46 */     this.description = getName() + "{ manager=" + getManager() + " }";
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  51 */     return this.description;
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
/*     */   @PluginFactory
/*     */   public static JPAAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("ignoreExceptions") String ignore, @PluginElement("Filter") Filter filter, @PluginAttribute("bufferSize") String bufferSize, @PluginAttribute("entityClassName") String entityClassName, @PluginAttribute("persistenceUnitName") String persistenceUnitName) {
/*  76 */     if (Strings.isEmpty(entityClassName) || Strings.isEmpty(persistenceUnitName)) {
/*  77 */       LOGGER.error("Attributes entityClassName and persistenceUnitName are required for JPA Appender.");
/*  78 */       return null;
/*     */     } 
/*     */     
/*  81 */     int bufferSizeInt = AbstractAppender.parseInt(bufferSize, 0);
/*  82 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/*     */ 
/*     */     
/*     */     try {
/*  86 */       Class<? extends AbstractLogEventWrapperEntity> entityClass = (Class)Class.forName(entityClassName);
/*     */ 
/*     */       
/*  89 */       if (!AbstractLogEventWrapperEntity.class.isAssignableFrom(entityClass)) {
/*  90 */         LOGGER.error("Entity class [{}] does not extend AbstractLogEventWrapperEntity.", new Object[] { entityClassName });
/*  91 */         return null;
/*     */       } 
/*     */       
/*     */       try {
/*  95 */         entityClass.getConstructor(new Class[0]);
/*  96 */       } catch (NoSuchMethodException e) {
/*  97 */         LOGGER.error("Entity class [{}] does not have a no-arg constructor. The JPA provider will reject it.", new Object[] { entityClassName });
/*     */         
/*  99 */         return null;
/*     */       } 
/*     */       
/* 102 */       Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor = entityClass.getConstructor(new Class[] { LogEvent.class });
/*     */ 
/*     */       
/* 105 */       String managerName = "jpaManager{ description=" + name + ", bufferSize=" + bufferSizeInt + ", persistenceUnitName=" + persistenceUnitName + ", entityClass=" + entityClass.getName() + "}";
/*     */ 
/*     */       
/* 108 */       JPADatabaseManager manager = JPADatabaseManager.getJPADatabaseManager(managerName, bufferSizeInt, entityClass, entityConstructor, persistenceUnitName);
/*     */ 
/*     */       
/* 111 */       if (manager == null) {
/* 112 */         return null;
/*     */       }
/*     */       
/* 115 */       return new JPAAppender(name, filter, ignoreExceptions, manager);
/* 116 */     } catch (ClassNotFoundException e) {
/* 117 */       LOGGER.error("Could not load entity class [{}].", new Object[] { entityClassName, e });
/* 118 */       return null;
/* 119 */     } catch (NoSuchMethodException e) {
/* 120 */       LOGGER.error("Entity class [{}] does not have a constructor with a single argument of type LogEvent.", new Object[] { entityClassName });
/*     */       
/* 122 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\JPAAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */