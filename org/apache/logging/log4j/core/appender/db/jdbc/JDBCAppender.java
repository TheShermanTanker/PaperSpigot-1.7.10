/*     */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*     */ 
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "JDBC", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class JDBCAppender
/*     */   extends AbstractDatabaseAppender<JDBCDatabaseManager>
/*     */ {
/*     */   private final String description;
/*     */   
/*     */   private JDBCAppender(String name, Filter filter, boolean ignoreExceptions, JDBCDatabaseManager manager) {
/*  43 */     super(name, filter, ignoreExceptions, manager);
/*  44 */     this.description = getName() + "{ manager=" + getManager() + " }";
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  49 */     return this.description;
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
/*     */   @PluginFactory
/*     */   public static JDBCAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("ignoreExceptions") String ignore, @PluginElement("Filter") Filter filter, @PluginElement("ConnectionSource") ConnectionSource connectionSource, @PluginAttribute("bufferSize") String bufferSize, @PluginAttribute("tableName") String tableName, @PluginElement("ColumnConfigs") ColumnConfig[] columnConfigs) {
/*  77 */     int bufferSizeInt = AbstractAppender.parseInt(bufferSize, 0);
/*  78 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/*     */     
/*  80 */     StringBuilder managerName = (new StringBuilder("jdbcManager{ description=")).append(name).append(", bufferSize=").append(bufferSizeInt).append(", connectionSource=").append(connectionSource.toString()).append(", tableName=").append(tableName).append(", columns=[ ");
/*     */ 
/*     */ 
/*     */     
/*  84 */     int i = 0;
/*  85 */     for (ColumnConfig column : columnConfigs) {
/*  86 */       if (i++ > 0) {
/*  87 */         managerName.append(", ");
/*     */       }
/*  89 */       managerName.append(column.toString());
/*     */     } 
/*     */     
/*  92 */     managerName.append(" ] }");
/*     */     
/*  94 */     JDBCDatabaseManager manager = JDBCDatabaseManager.getJDBCDatabaseManager(managerName.toString(), bufferSizeInt, connectionSource, tableName, columnConfigs);
/*     */ 
/*     */     
/*  97 */     if (manager == null) {
/*  98 */       return null;
/*     */     }
/*     */     
/* 101 */     return new JDBCAppender(name, filter, ignoreExceptions, manager);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\JDBCAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */