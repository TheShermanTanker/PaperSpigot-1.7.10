/*     */ package org.apache.logging.log4j.core.appender.db.nosql.couch;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLConnection;
/*     */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLProvider;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.NameUtil;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.lightcouch.CouchDbClient;
/*     */ import org.lightcouch.CouchDbProperties;
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
/*     */ @Plugin(name = "CouchDb", category = "Core", printObject = true)
/*     */ public final class CouchDBProvider
/*     */   implements NoSQLProvider<CouchDBConnection>
/*     */ {
/*     */   private static final int HTTP = 80;
/*     */   private static final int HTTPS = 443;
/*  40 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private final CouchDbClient client;
/*     */   private final String description;
/*     */   
/*     */   private CouchDBProvider(CouchDbClient client, String description) {
/*  46 */     this.client = client;
/*  47 */     this.description = "couchDb{ " + description + " }";
/*     */   }
/*     */ 
/*     */   
/*     */   public CouchDBConnection getConnection() {
/*  52 */     return new CouchDBConnection(this.client);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  57 */     return this.description;
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
/*     */   @PluginFactory
/*     */   public static CouchDBProvider createNoSQLProvider(@PluginAttribute("databaseName") String databaseName, @PluginAttribute("protocol") String protocol, @PluginAttribute("server") String server, @PluginAttribute("port") String port, @PluginAttribute("username") String username, @PluginAttribute("password") String password, @PluginAttribute("factoryClassName") String factoryClassName, @PluginAttribute("factoryMethodName") String factoryMethodName) {
/*     */     CouchDbClient client;
/*     */     String description;
/*  93 */     if (factoryClassName != null && factoryClassName.length() > 0 && factoryMethodName != null && factoryMethodName.length() > 0) {
/*     */       
/*     */       try {
/*  96 */         Class<?> factoryClass = Class.forName(factoryClassName);
/*  97 */         Method method = factoryClass.getMethod(factoryMethodName, new Class[0]);
/*  98 */         Object object = method.invoke(null, new Object[0]);
/*     */         
/* 100 */         if (object instanceof CouchDbClient) {
/* 101 */           client = (CouchDbClient)object;
/* 102 */           description = "uri=" + client.getDBUri();
/* 103 */         } else if (object instanceof CouchDbProperties) {
/* 104 */           CouchDbProperties properties = (CouchDbProperties)object;
/* 105 */           client = new CouchDbClient(properties);
/* 106 */           description = "uri=" + client.getDBUri() + ", username=" + properties.getUsername() + ", passwordHash=" + NameUtil.md5(password + CouchDBProvider.class.getName()) + ", maxConnections=" + properties.getMaxConnections() + ", connectionTimeout=" + properties.getConnectionTimeout() + ", socketTimeout=" + properties.getSocketTimeout();
/*     */         }
/*     */         else {
/*     */           
/* 110 */           if (object == null) {
/* 111 */             LOGGER.error("The factory method [{}.{}()] returned null.", new Object[] { factoryClassName, factoryMethodName });
/* 112 */             return null;
/*     */           } 
/* 114 */           LOGGER.error("The factory method [{}.{}()] returned an unsupported type [{}].", new Object[] { factoryClassName, factoryMethodName, object.getClass().getName() });
/*     */           
/* 116 */           return null;
/*     */         } 
/* 118 */       } catch (ClassNotFoundException e) {
/* 119 */         LOGGER.error("The factory class [{}] could not be loaded.", new Object[] { factoryClassName, e });
/* 120 */         return null;
/* 121 */       } catch (NoSuchMethodException e) {
/* 122 */         LOGGER.error("The factory class [{}] does not have a no-arg method named [{}].", new Object[] { factoryClassName, factoryMethodName, e });
/*     */         
/* 124 */         return null;
/* 125 */       } catch (Exception e) {
/* 126 */         LOGGER.error("The factory method [{}.{}()] could not be invoked.", new Object[] { factoryClassName, factoryMethodName, e });
/*     */         
/* 128 */         return null;
/*     */       } 
/* 130 */     } else if (databaseName != null && databaseName.length() > 0) {
/* 131 */       if (protocol != null && protocol.length() > 0) {
/* 132 */         protocol = protocol.toLowerCase();
/* 133 */         if (!protocol.equals("http") && !protocol.equals("https")) {
/* 134 */           LOGGER.error("Only protocols [http] and [https] are supported, [{}] specified.", new Object[] { protocol });
/* 135 */           return null;
/*     */         } 
/*     */       } else {
/* 138 */         protocol = "http";
/* 139 */         LOGGER.warn("No protocol specified, using default port [http].");
/*     */       } 
/*     */       
/* 142 */       int portInt = AbstractAppender.parseInt(port, protocol.equals("https") ? 443 : 80);
/*     */       
/* 144 */       if (Strings.isEmpty(server)) {
/* 145 */         server = "localhost";
/* 146 */         LOGGER.warn("No server specified, using default server localhost.");
/*     */       } 
/*     */       
/* 149 */       if (Strings.isEmpty(username) || Strings.isEmpty(password)) {
/* 150 */         LOGGER.error("You must provide a username and password for the CouchDB provider.");
/* 151 */         return null;
/*     */       } 
/*     */       
/* 154 */       client = new CouchDbClient(databaseName, false, protocol, server, portInt, username, password);
/* 155 */       description = "uri=" + client.getDBUri() + ", username=" + username + ", passwordHash=" + NameUtil.md5(password + CouchDBProvider.class.getName());
/*     */     } else {
/*     */       
/* 158 */       LOGGER.error("No factory method was provided so the database name is required.");
/* 159 */       return null;
/*     */     } 
/*     */     
/* 162 */     return new CouchDBProvider(client, description);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\couch\CouchDBProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */