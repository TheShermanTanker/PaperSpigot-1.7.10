/*     */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*     */ 
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
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
/*     */ @Plugin(name = "Column", category = "Core", printObject = true)
/*     */ public final class ColumnConfig
/*     */ {
/*  35 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private final String columnName;
/*     */   
/*     */   private final PatternLayout layout;
/*     */   private final String literalValue;
/*     */   private final boolean eventTimestamp;
/*     */   private final boolean unicode;
/*     */   private final boolean clob;
/*     */   
/*     */   private ColumnConfig(String columnName, PatternLayout layout, String literalValue, boolean eventDate, boolean unicode, boolean clob) {
/*  46 */     this.columnName = columnName;
/*  47 */     this.layout = layout;
/*  48 */     this.literalValue = literalValue;
/*  49 */     this.eventTimestamp = eventDate;
/*  50 */     this.unicode = unicode;
/*  51 */     this.clob = clob;
/*     */   }
/*     */   
/*     */   public String getColumnName() {
/*  55 */     return this.columnName;
/*     */   }
/*     */   
/*     */   public PatternLayout getLayout() {
/*  59 */     return this.layout;
/*     */   }
/*     */   
/*     */   public String getLiteralValue() {
/*  63 */     return this.literalValue;
/*     */   }
/*     */   
/*     */   public boolean isEventTimestamp() {
/*  67 */     return this.eventTimestamp;
/*     */   }
/*     */   
/*     */   public boolean isUnicode() {
/*  71 */     return this.unicode;
/*     */   }
/*     */   
/*     */   public boolean isClob() {
/*  75 */     return this.clob;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  80 */     return "{ name=" + this.columnName + ", layout=" + this.layout + ", literal=" + this.literalValue + ", timestamp=" + this.eventTimestamp + " }";
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
/*     */   @PluginFactory
/*     */   public static ColumnConfig createColumnConfig(@PluginConfiguration Configuration config, @PluginAttribute("name") String name, @PluginAttribute("pattern") String pattern, @PluginAttribute("literal") String literalValue, @PluginAttribute("isEventTimestamp") String eventTimestamp, @PluginAttribute("isUnicode") String unicode, @PluginAttribute("isClob") String clob) {
/* 109 */     if (Strings.isEmpty(name)) {
/* 110 */       LOGGER.error("The column config is not valid because it does not contain a column name.");
/* 111 */       return null;
/*     */     } 
/*     */     
/* 114 */     boolean isPattern = Strings.isNotEmpty(pattern);
/* 115 */     boolean isLiteralValue = Strings.isNotEmpty(literalValue);
/* 116 */     boolean isEventTimestamp = Boolean.parseBoolean(eventTimestamp);
/* 117 */     boolean isUnicode = Booleans.parseBoolean(unicode, true);
/* 118 */     boolean isClob = Boolean.parseBoolean(clob);
/*     */     
/* 120 */     if ((isPattern && isLiteralValue) || (isPattern && isEventTimestamp) || (isLiteralValue && isEventTimestamp)) {
/* 121 */       LOGGER.error("The pattern, literal, and isEventTimestamp attributes are mutually exclusive.");
/* 122 */       return null;
/*     */     } 
/*     */     
/* 125 */     if (isEventTimestamp) {
/* 126 */       return new ColumnConfig(name, null, null, true, false, false);
/*     */     }
/* 128 */     if (isLiteralValue) {
/* 129 */       return new ColumnConfig(name, null, literalValue, false, false, false);
/*     */     }
/* 131 */     if (isPattern) {
/* 132 */       return new ColumnConfig(name, PatternLayout.createLayout(pattern, config, null, null, "false"), null, false, isUnicode, isClob);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     LOGGER.error("To configure a column you must specify a pattern or literal or set isEventDate to true.");
/* 139 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\ColumnConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */