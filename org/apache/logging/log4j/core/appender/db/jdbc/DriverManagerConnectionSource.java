/*     */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.NameUtil;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
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
/*     */ @Plugin(name = "DriverManager", category = "Core", elementType = "connectionSource", printObject = true)
/*     */ public final class DriverManagerConnectionSource
/*     */   implements ConnectionSource
/*     */ {
/*  38 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private final String databasePassword;
/*     */   
/*     */   private final String databaseUrl;
/*     */   private final String databaseUsername;
/*     */   private final String description;
/*     */   
/*     */   private DriverManagerConnectionSource(String databaseUrl, String databaseUsername, String databasePassword) {
/*  47 */     this.databaseUrl = databaseUrl;
/*  48 */     this.databaseUsername = databaseUsername;
/*  49 */     this.databasePassword = databasePassword;
/*  50 */     this.description = "driverManager{ url=" + this.databaseUrl + ", username=" + this.databaseUsername + ", passwordHash=" + NameUtil.md5(this.databasePassword + getClass().getName()) + " }";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/*  56 */     if (this.databaseUsername == null) {
/*  57 */       return DriverManager.getConnection(this.databaseUrl);
/*     */     }
/*  59 */     return DriverManager.getConnection(this.databaseUrl, this.databaseUsername, this.databasePassword);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  64 */     return this.description;
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
/*     */   @PluginFactory
/*     */   public static DriverManagerConnectionSource createConnectionSource(@PluginAttribute("url") String url, @PluginAttribute("username") String username, @PluginAttribute("password") String password) {
/*     */     Driver driver;
/*  81 */     if (Strings.isEmpty(url)) {
/*  82 */       LOGGER.error("No JDBC URL specified for the database.");
/*  83 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  88 */       driver = DriverManager.getDriver(url);
/*  89 */     } catch (SQLException e) {
/*  90 */       LOGGER.error("No matching driver found for database URL [" + url + "].", e);
/*  91 */       return null;
/*     */     } 
/*     */     
/*  94 */     if (driver == null) {
/*  95 */       LOGGER.error("No matching driver found for database URL [" + url + "].");
/*  96 */       return null;
/*     */     } 
/*     */     
/*  99 */     if (username == null || username.trim().isEmpty()) {
/* 100 */       username = null;
/* 101 */       password = null;
/*     */     } 
/*     */     
/* 104 */     return new DriverManagerConnectionSource(url, username, password);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\DriverManagerConnectionSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */