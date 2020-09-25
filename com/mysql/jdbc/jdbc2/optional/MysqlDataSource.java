/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import com.mysql.jdbc.ConnectionPropertiesImpl;
/*     */ import com.mysql.jdbc.NonRegisteringDriver;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.Referenceable;
/*     */ import javax.naming.StringRefAddr;
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
/*     */ 
/*     */ 
/*     */ public class MysqlDataSource
/*     */   extends ConnectionPropertiesImpl
/*     */   implements DataSource, Referenceable, Serializable
/*     */ {
/*  51 */   protected static NonRegisteringDriver mysqlDriver = null;
/*     */   
/*     */   static {
/*     */     try {
/*  55 */       mysqlDriver = new NonRegisteringDriver();
/*  56 */     } catch (Exception E) {
/*  57 */       throw new RuntimeException("Can not load Driver class com.mysql.jdbc.Driver");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  63 */   protected PrintWriter logWriter = null;
/*     */ 
/*     */   
/*  66 */   protected String databaseName = null;
/*     */ 
/*     */   
/*  69 */   protected String encoding = null;
/*     */ 
/*     */   
/*  72 */   protected String hostName = null;
/*     */ 
/*     */   
/*  75 */   protected String password = null;
/*     */ 
/*     */   
/*  78 */   protected String profileSql = "false";
/*     */ 
/*     */   
/*  81 */   protected String url = null;
/*     */ 
/*     */   
/*  84 */   protected String user = null;
/*     */ 
/*     */   
/*     */   protected boolean explicitUrl = false;
/*     */ 
/*     */   
/*  90 */   protected int port = 3306;
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
/*     */   public Connection getConnection() throws SQLException {
/* 108 */     return getConnection(this.user, this.password);
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
/*     */   public Connection getConnection(String userID, String pass) throws SQLException {
/* 126 */     Properties props = new Properties();
/*     */     
/* 128 */     if (userID != null) {
/* 129 */       props.setProperty("user", userID);
/*     */     }
/*     */     
/* 132 */     if (pass != null) {
/* 133 */       props.setProperty("password", pass);
/*     */     }
/*     */     
/* 136 */     exposeAsProperties(props);
/*     */     
/* 138 */     return getConnection(props);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDatabaseName(String dbName) {
/* 148 */     this.databaseName = dbName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDatabaseName() {
/* 157 */     return (this.databaseName != null) ? this.databaseName : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLogWriter(PrintWriter output) throws SQLException {
/* 166 */     this.logWriter = output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrintWriter getLogWriter() {
/* 175 */     return this.logWriter;
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
/*     */   public void setLoginTimeout(int seconds) throws SQLException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLoginTimeout() {
/* 196 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPassword(String pass) {
/* 206 */     this.password = pass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPort(int p) {
/* 216 */     this.port = p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 225 */     return this.port;
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
/*     */   public void setPortNumber(int p) {
/* 237 */     setPort(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPortNumber() {
/* 246 */     return getPort();
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
/*     */   public void setPropertiesViaRef(Reference ref) throws SQLException {
/* 259 */     initializeFromRef(ref);
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
/*     */   public Reference getReference() throws NamingException {
/* 271 */     String factoryName = "com.mysql.jdbc.jdbc2.optional.MysqlDataSourceFactory";
/* 272 */     Reference ref = new Reference(getClass().getName(), factoryName, null);
/* 273 */     ref.add(new StringRefAddr("user", getUser()));
/*     */     
/* 275 */     ref.add(new StringRefAddr("password", this.password));
/*     */     
/* 277 */     ref.add(new StringRefAddr("serverName", getServerName()));
/* 278 */     ref.add(new StringRefAddr("port", "" + getPort()));
/* 279 */     ref.add(new StringRefAddr("databaseName", getDatabaseName()));
/* 280 */     ref.add(new StringRefAddr("url", getUrl()));
/* 281 */     ref.add(new StringRefAddr("explicitUrl", String.valueOf(this.explicitUrl)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 288 */       storeToRef(ref);
/* 289 */     } catch (SQLException sqlEx) {
/* 290 */       throw new NamingException(sqlEx.getMessage());
/*     */     } 
/*     */     
/* 293 */     return ref;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServerName(String serverName) {
/* 303 */     this.hostName = serverName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 312 */     return (this.hostName != null) ? this.hostName : "";
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
/*     */   public void setURL(String url) {
/* 327 */     setUrl(url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURL() {
/* 336 */     return getUrl();
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
/*     */   public void setUrl(String url) {
/* 348 */     this.url = url;
/* 349 */     this.explicitUrl = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUrl() {
/* 358 */     if (!this.explicitUrl) {
/* 359 */       String builtUrl = "jdbc:mysql://";
/* 360 */       builtUrl = builtUrl + getServerName() + ":" + getPort() + "/" + getDatabaseName();
/*     */ 
/*     */       
/* 363 */       return builtUrl;
/*     */     } 
/*     */     
/* 366 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUser(String userID) {
/* 376 */     this.user = userID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUser() {
/* 385 */     return this.user;
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
/*     */   protected Connection getConnection(Properties props) throws SQLException {
/* 401 */     String jdbcUrlToUse = null;
/*     */     
/* 403 */     if (!this.explicitUrl) {
/* 404 */       StringBuffer jdbcUrl = new StringBuffer("jdbc:mysql://");
/*     */       
/* 406 */       if (this.hostName != null) {
/* 407 */         jdbcUrl.append(this.hostName);
/*     */       }
/*     */       
/* 410 */       jdbcUrl.append(":");
/* 411 */       jdbcUrl.append(this.port);
/* 412 */       jdbcUrl.append("/");
/*     */       
/* 414 */       if (this.databaseName != null) {
/* 415 */         jdbcUrl.append(this.databaseName);
/*     */       }
/*     */       
/* 418 */       jdbcUrlToUse = jdbcUrl.toString();
/*     */     } else {
/* 420 */       jdbcUrlToUse = this.url;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 427 */     Properties urlProps = mysqlDriver.parseURL(jdbcUrlToUse, null);
/* 428 */     urlProps.remove("DBNAME");
/* 429 */     urlProps.remove("HOST");
/* 430 */     urlProps.remove("PORT");
/*     */     
/* 432 */     Iterator<String> keys = urlProps.keySet().iterator();
/*     */     
/* 434 */     while (keys.hasNext()) {
/* 435 */       String key = keys.next();
/*     */       
/* 437 */       props.setProperty(key, urlProps.getProperty(key));
/*     */     } 
/*     */     
/* 440 */     return mysqlDriver.connect(jdbcUrlToUse, props);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\jdbc2\optional\MysqlDataSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */