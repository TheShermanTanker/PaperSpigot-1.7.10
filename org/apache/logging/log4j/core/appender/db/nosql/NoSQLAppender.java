/*    */ package org.apache.logging.log4j.core.appender.db.nosql;
/*    */ 
/*    */ import org.apache.logging.log4j.core.Filter;
/*    */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*    */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.core.helpers.Booleans;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name = "NoSql", category = "Core", elementType = "appender", printObject = true)
/*    */ public final class NoSQLAppender
/*    */   extends AbstractDatabaseAppender<NoSQLDatabaseManager<?>>
/*    */ {
/*    */   private final String description;
/*    */   
/*    */   private NoSQLAppender(String name, Filter filter, boolean ignoreExceptions, NoSQLDatabaseManager<?> manager) {
/* 46 */     super(name, filter, ignoreExceptions, manager);
/* 47 */     this.description = getName() + "{ manager=" + getManager() + " }";
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return this.description;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @PluginFactory
/*    */   public static NoSQLAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("ignoreExceptions") String ignore, @PluginElement("Filter") Filter filter, @PluginAttribute("bufferSize") String bufferSize, @PluginElement("NoSqlProvider") NoSQLProvider<?> provider) {
/* 74 */     if (provider == null) {
/* 75 */       LOGGER.error("NoSQL provider not specified for appender [{}].", new Object[] { name });
/* 76 */       return null;
/*    */     } 
/*    */     
/* 79 */     int bufferSizeInt = AbstractAppender.parseInt(bufferSize, 0);
/* 80 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/*    */     
/* 82 */     String managerName = "noSqlManager{ description=" + name + ", bufferSize=" + bufferSizeInt + ", provider=" + provider + " }";
/*    */ 
/*    */     
/* 85 */     NoSQLDatabaseManager<?> manager = NoSQLDatabaseManager.getNoSQLDatabaseManager(managerName, bufferSizeInt, provider);
/*    */ 
/*    */     
/* 88 */     if (manager == null) {
/* 89 */       return null;
/*    */     }
/*    */     
/* 92 */     return new NoSQLAppender(name, filter, ignoreExceptions, manager);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */