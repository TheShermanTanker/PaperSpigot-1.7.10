/*     */ package org.apache.logging.log4j.core.appender.db.nosql.mongo;
/*     */ 
/*     */ import com.mongodb.BasicDBObject;
/*     */ import com.mongodb.DB;
/*     */ import com.mongodb.DBCollection;
/*     */ import com.mongodb.DBObject;
/*     */ import com.mongodb.Mongo;
/*     */ import com.mongodb.MongoException;
/*     */ import com.mongodb.WriteConcern;
/*     */ import com.mongodb.WriteResult;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLConnection;
/*     */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLObject;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.bson.BSON;
/*     */ import org.bson.Transformer;
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
/*     */ public final class MongoDBConnection
/*     */   implements NoSQLConnection<BasicDBObject, MongoDBObject>
/*     */ {
/*  41 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   static {
/*  44 */     BSON.addDecodingHook(Level.class, new Transformer()
/*     */         {
/*     */           public Object transform(Object o) {
/*  47 */             if (o instanceof Level) {
/*  48 */               return ((Level)o).name();
/*     */             }
/*  50 */             return o;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private final DBCollection collection;
/*     */   private final Mongo mongo;
/*     */   private final WriteConcern writeConcern;
/*     */   
/*     */   public MongoDBConnection(DB database, WriteConcern writeConcern, String collectionName) {
/*  60 */     this.mongo = database.getMongo();
/*  61 */     this.collection = database.getCollection(collectionName);
/*  62 */     this.writeConcern = writeConcern;
/*     */   }
/*     */ 
/*     */   
/*     */   public MongoDBObject createObject() {
/*  67 */     return new MongoDBObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public MongoDBObject[] createList(int length) {
/*  72 */     return new MongoDBObject[length];
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertObject(NoSQLObject<BasicDBObject> object) {
/*     */     try {
/*  78 */       WriteResult result = this.collection.insert((DBObject)object.unwrap(), this.writeConcern);
/*  79 */       if (result.getError() != null && result.getError().length() > 0) {
/*  80 */         throw new AppenderLoggingException("Failed to write log event to MongoDB due to error: " + result.getError() + ".");
/*     */       }
/*     */     }
/*  83 */     catch (MongoException e) {
/*  84 */       throw new AppenderLoggingException("Failed to write log event to MongoDB due to error: " + e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*  91 */     this.mongo.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/*  96 */     return !this.mongo.getConnector().isOpen();
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
/*     */   static void authenticate(DB database, String username, String password) {
/*     */     try {
/* 113 */       if (!database.authenticate(username, password.toCharArray())) {
/* 114 */         LOGGER.error("Failed to authenticate against MongoDB server. Unknown error.");
/*     */       }
/* 116 */     } catch (MongoException e) {
/* 117 */       LOGGER.error("Failed to authenticate against MongoDB: " + e.getMessage(), (Throwable)e);
/* 118 */     } catch (IllegalStateException e) {
/* 119 */       LOGGER.error("Factory-supplied MongoDB database connection already authenticated with differentcredentials but lost connection.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\mongo\MongoDBConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */