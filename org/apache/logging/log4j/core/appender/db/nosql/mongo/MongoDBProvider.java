/*     */ package org.apache.logging.log4j.core.appender.db.nosql.mongo;
/*     */ 
/*     */ import com.mongodb.DB;
/*     */ import com.mongodb.MongoClient;
/*     */ import com.mongodb.ServerAddress;
/*     */ import com.mongodb.WriteConcern;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLConnection;
/*     */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLProvider;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.NameUtil;
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
/*     */ @Plugin(name = "MongoDb", category = "Core", printObject = true)
/*     */ public final class MongoDBProvider
/*     */   implements NoSQLProvider<MongoDBConnection>
/*     */ {
/*  42 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private final String collectionName;
/*     */   
/*     */   private final DB database;
/*     */   
/*     */   private final String description;
/*     */   private final WriteConcern writeConcern;
/*     */   
/*     */   private MongoDBProvider(DB database, WriteConcern writeConcern, String collectionName, String description) {
/*  52 */     this.database = database;
/*  53 */     this.writeConcern = writeConcern;
/*  54 */     this.collectionName = collectionName;
/*  55 */     this.description = "mongoDb{ " + description + " }";
/*     */   }
/*     */ 
/*     */   
/*     */   public MongoDBConnection getConnection() {
/*  60 */     return new MongoDBConnection(this.database, this.writeConcern, this.collectionName);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  65 */     return this.description;
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
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static MongoDBProvider createNoSQLProvider(@PluginAttribute("collectionName") String collectionName, @PluginAttribute("writeConcernConstant") String writeConcernConstant, @PluginAttribute("writeConcernConstantClass") String writeConcernConstantClassName, @PluginAttribute("databaseName") String databaseName, @PluginAttribute("server") String server, @PluginAttribute("port") String port, @PluginAttribute("username") String username, @PluginAttribute("password") String password, @PluginAttribute("factoryClassName") String factoryClassName, @PluginAttribute("factoryMethodName") String factoryMethodName) {
/*     */     WriteConcern writeConcern;
/*     */     DB database;
/*     */     String description;
/* 104 */     if (factoryClassName != null && factoryClassName.length() > 0 && factoryMethodName != null && factoryMethodName.length() > 0) {
/*     */       
/*     */       try {
/* 107 */         Class<?> factoryClass = Class.forName(factoryClassName);
/* 108 */         Method method = factoryClass.getMethod(factoryMethodName, new Class[0]);
/* 109 */         Object object = method.invoke(null, new Object[0]);
/*     */         
/* 111 */         if (object instanceof DB)
/* 112 */         { database = (DB)object; }
/* 113 */         else if (object instanceof MongoClient)
/* 114 */         { if (databaseName != null && databaseName.length() > 0) {
/* 115 */             database = ((MongoClient)object).getDB(databaseName);
/*     */           } else {
/* 117 */             LOGGER.error("The factory method [{}.{}()] returned a MongoClient so the database name is required.", new Object[] { factoryClassName, factoryMethodName });
/*     */             
/* 119 */             return null;
/*     */           }  }
/* 121 */         else { if (object == null) {
/* 122 */             LOGGER.error("The factory method [{}.{}()] returned null.", new Object[] { factoryClassName, factoryMethodName });
/* 123 */             return null;
/*     */           } 
/* 125 */           LOGGER.error("The factory method [{}.{}()] returned an unsupported type [{}].", new Object[] { factoryClassName, factoryMethodName, object.getClass().getName() });
/*     */           
/* 127 */           return null; }
/*     */ 
/*     */         
/* 130 */         description = "database=" + database.getName();
/* 131 */         List<ServerAddress> addresses = database.getMongo().getAllAddress();
/* 132 */         if (addresses.size() == 1) {
/* 133 */           description = description + ", server=" + ((ServerAddress)addresses.get(0)).getHost() + ", port=" + ((ServerAddress)addresses.get(0)).getPort();
/*     */         } else {
/* 135 */           description = description + ", servers=[";
/* 136 */           for (ServerAddress address : addresses) {
/* 137 */             description = description + " { " + address.getHost() + ", " + address.getPort() + " } ";
/*     */           }
/* 139 */           description = description + "]";
/*     */         } 
/* 141 */       } catch (ClassNotFoundException e) {
/* 142 */         LOGGER.error("The factory class [{}] could not be loaded.", new Object[] { factoryClassName, e });
/* 143 */         return null;
/* 144 */       } catch (NoSuchMethodException e) {
/* 145 */         LOGGER.error("The factory class [{}] does not have a no-arg method named [{}].", new Object[] { factoryClassName, factoryMethodName, e });
/*     */         
/* 147 */         return null;
/* 148 */       } catch (Exception e) {
/* 149 */         LOGGER.error("The factory method [{}.{}()] could not be invoked.", new Object[] { factoryClassName, factoryMethodName, e });
/*     */         
/* 151 */         return null;
/*     */       } 
/* 153 */     } else if (databaseName != null && databaseName.length() > 0) {
/* 154 */       description = "database=" + databaseName;
/*     */       try {
/* 156 */         if (server != null && server.length() > 0) {
/* 157 */           int portInt = AbstractAppender.parseInt(port, 0);
/* 158 */           description = description + ", server=" + server;
/* 159 */           if (portInt > 0) {
/* 160 */             description = description + ", port=" + portInt;
/* 161 */             database = (new MongoClient(server, portInt)).getDB(databaseName);
/*     */           } else {
/* 163 */             database = (new MongoClient(server)).getDB(databaseName);
/*     */           } 
/*     */         } else {
/* 166 */           database = (new MongoClient()).getDB(databaseName);
/*     */         } 
/* 168 */       } catch (Exception e) {
/* 169 */         LOGGER.error("Failed to obtain a database instance from the MongoClient at server [{}] and port [{}].", new Object[] { server, port });
/*     */         
/* 171 */         return null;
/*     */       } 
/*     */     } else {
/* 174 */       LOGGER.error("No factory method was provided so the database name is required.");
/* 175 */       return null;
/*     */     } 
/*     */     
/* 178 */     if (!database.isAuthenticated()) {
/* 179 */       if (username != null && username.length() > 0 && password != null && password.length() > 0) {
/* 180 */         description = description + ", username=" + username + ", passwordHash=" + NameUtil.md5(password + MongoDBProvider.class.getName());
/*     */         
/* 182 */         MongoDBConnection.authenticate(database, username, password);
/*     */       } else {
/* 184 */         LOGGER.error("The database is not already authenticated so you must supply a username and password for the MongoDB provider.");
/*     */         
/* 186 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 191 */     if (writeConcernConstant != null && writeConcernConstant.length() > 0) {
/* 192 */       if (writeConcernConstantClassName != null && writeConcernConstantClassName.length() > 0) {
/*     */         try {
/* 194 */           Class<?> writeConcernConstantClass = Class.forName(writeConcernConstantClassName);
/* 195 */           Field field = writeConcernConstantClass.getField(writeConcernConstant);
/* 196 */           writeConcern = (WriteConcern)field.get(null);
/* 197 */         } catch (Exception e) {
/* 198 */           LOGGER.error("Write concern constant [{}.{}] not found, using default.", new Object[] { writeConcernConstantClassName, writeConcernConstant });
/*     */           
/* 200 */           writeConcern = WriteConcern.ACKNOWLEDGED;
/*     */         } 
/*     */       } else {
/* 203 */         writeConcern = WriteConcern.valueOf(writeConcernConstant);
/* 204 */         if (writeConcern == null) {
/* 205 */           LOGGER.warn("Write concern constant [{}] not found, using default.", new Object[] { writeConcernConstant });
/* 206 */           writeConcern = WriteConcern.ACKNOWLEDGED;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 210 */       writeConcern = WriteConcern.ACKNOWLEDGED;
/*     */     } 
/*     */     
/* 213 */     return new MongoDBProvider(database, writeConcern, collectionName, description);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\mongo\MongoDBProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */