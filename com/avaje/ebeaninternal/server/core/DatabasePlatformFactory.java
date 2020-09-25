/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.H2Platform;
/*     */ import com.avaje.ebean.config.dbplatform.HsqldbPlatform;
/*     */ import com.avaje.ebean.config.dbplatform.MsSqlServer2000Platform;
/*     */ import com.avaje.ebean.config.dbplatform.MsSqlServer2005Platform;
/*     */ import com.avaje.ebean.config.dbplatform.MySqlPlatform;
/*     */ import com.avaje.ebean.config.dbplatform.Oracle10Platform;
/*     */ import com.avaje.ebean.config.dbplatform.Oracle9Platform;
/*     */ import com.avaje.ebean.config.dbplatform.PostgresPlatform;
/*     */ import com.avaje.ebean.config.dbplatform.SQLitePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.SqlAnywherePlatform;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.sql.DataSource;
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
/*     */ public class DatabasePlatformFactory
/*     */ {
/*  53 */   private static final Logger logger = Logger.getLogger(DatabasePlatformFactory.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabasePlatform create(ServerConfig serverConfig) {
/*     */     try {
/*  62 */       if (serverConfig.getDatabasePlatformName() != null)
/*     */       {
/*  64 */         return byDatabaseName(serverConfig.getDatabasePlatformName());
/*     */       }
/*     */       
/*  67 */       if (serverConfig.getDataSourceConfig().isOffline()) {
/*  68 */         String m = "You must specify a DatabasePlatformName when you are offline";
/*  69 */         throw new PersistenceException(m);
/*     */       } 
/*     */       
/*  72 */       return byDataSource(serverConfig.getDataSource());
/*     */     }
/*  74 */     catch (Exception ex) {
/*  75 */       throw new PersistenceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DatabasePlatform byDatabaseName(String dbName) throws SQLException {
/*  84 */     dbName = dbName.toLowerCase();
/*  85 */     if (dbName.equals("postgres83")) {
/*  86 */       return (DatabasePlatform)new PostgresPlatform();
/*     */     }
/*  88 */     if (dbName.equals("oracle9")) {
/*  89 */       return (DatabasePlatform)new Oracle9Platform();
/*     */     }
/*  91 */     if (dbName.equals("oracle10")) {
/*  92 */       return (DatabasePlatform)new Oracle10Platform();
/*     */     }
/*  94 */     if (dbName.equals("oracle")) {
/*  95 */       return (DatabasePlatform)new Oracle10Platform();
/*     */     }
/*  97 */     if (dbName.equals("sqlserver2005")) {
/*  98 */       return (DatabasePlatform)new MsSqlServer2005Platform();
/*     */     }
/* 100 */     if (dbName.equals("sqlserver2000")) {
/* 101 */       return (DatabasePlatform)new MsSqlServer2000Platform();
/*     */     }
/* 103 */     if (dbName.equals("sqlanywhere")) {
/* 104 */       return (DatabasePlatform)new SqlAnywherePlatform();
/*     */     }
/*     */     
/* 107 */     if (dbName.equals("mysql")) {
/* 108 */       return (DatabasePlatform)new MySqlPlatform();
/*     */     }
/*     */     
/* 111 */     if (dbName.equals("sqlite")) {
/* 112 */       return (DatabasePlatform)new SQLitePlatform();
/*     */     }
/*     */     
/* 115 */     throw new RuntimeException("database platform " + dbName + " is not known?");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DatabasePlatform byDataSource(DataSource dataSource) {
/* 123 */     Connection conn = null;
/*     */     try {
/* 125 */       conn = dataSource.getConnection();
/* 126 */       DatabaseMetaData metaData = conn.getMetaData();
/*     */       
/* 128 */       return byDatabaseMeta(metaData);
/*     */     }
/* 130 */     catch (SQLException ex) {
/* 131 */       throw new PersistenceException(ex);
/*     */     } finally {
/*     */       
/*     */       try {
/* 135 */         if (conn != null) {
/* 136 */           conn.close();
/*     */         }
/* 138 */       } catch (SQLException ex) {
/* 139 */         logger.log(Level.SEVERE, (String)null, ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DatabasePlatform byDatabaseMeta(DatabaseMetaData metaData) throws SQLException {
/* 149 */     String dbProductName = metaData.getDatabaseProductName();
/* 150 */     dbProductName = dbProductName.toLowerCase();
/*     */     
/* 152 */     int majorVersion = metaData.getDatabaseMajorVersion();
/*     */     
/* 154 */     if (dbProductName.indexOf("oracle") > -1) {
/* 155 */       if (majorVersion > 9) {
/* 156 */         return (DatabasePlatform)new Oracle10Platform();
/*     */       }
/* 158 */       return (DatabasePlatform)new Oracle9Platform();
/*     */     } 
/*     */     
/* 161 */     if (dbProductName.indexOf("microsoft") > -1) {
/* 162 */       if (majorVersion > 8) {
/* 163 */         return (DatabasePlatform)new MsSqlServer2005Platform();
/*     */       }
/* 165 */       return (DatabasePlatform)new MsSqlServer2000Platform();
/*     */     } 
/*     */ 
/*     */     
/* 169 */     if (dbProductName.indexOf("mysql") > -1) {
/* 170 */       return (DatabasePlatform)new MySqlPlatform();
/*     */     }
/* 172 */     if (dbProductName.indexOf("h2") > -1) {
/* 173 */       return (DatabasePlatform)new H2Platform();
/*     */     }
/* 175 */     if (dbProductName.indexOf("hsql database engine") > -1) {
/* 176 */       return (DatabasePlatform)new HsqldbPlatform();
/*     */     }
/* 178 */     if (dbProductName.indexOf("postgres") > -1) {
/* 179 */       return (DatabasePlatform)new PostgresPlatform();
/*     */     }
/* 181 */     if (dbProductName.indexOf("sqlite") > -1) {
/* 182 */       return (DatabasePlatform)new SQLitePlatform();
/*     */     }
/* 184 */     if (dbProductName.indexOf("sql anywhere") > -1) {
/* 185 */       return (DatabasePlatform)new SqlAnywherePlatform();
/*     */     }
/*     */ 
/*     */     
/* 189 */     return new DatabasePlatform();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\DatabasePlatformFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */