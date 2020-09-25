/*    */ package org.apache.logging.log4j.core.appender.db.nosql.couch;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*    */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLConnection;
/*    */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLObject;
/*    */ import org.lightcouch.CouchDbClient;
/*    */ import org.lightcouch.Response;
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
/*    */ public final class CouchDBConnection
/*    */   implements NoSQLConnection<Map<String, Object>, CouchDBObject>
/*    */ {
/*    */   private final CouchDbClient client;
/*    */   private boolean closed = false;
/*    */   
/*    */   public CouchDBConnection(CouchDbClient client) {
/* 35 */     this.client = client;
/*    */   }
/*    */ 
/*    */   
/*    */   public CouchDBObject createObject() {
/* 40 */     return new CouchDBObject();
/*    */   }
/*    */ 
/*    */   
/*    */   public CouchDBObject[] createList(int length) {
/* 45 */     return new CouchDBObject[length];
/*    */   }
/*    */ 
/*    */   
/*    */   public void insertObject(NoSQLObject<Map<String, Object>> object) {
/*    */     try {
/* 51 */       Response response = this.client.save(object.unwrap());
/* 52 */       if (response.getError() != null && response.getError().length() > 0) {
/* 53 */         throw new AppenderLoggingException("Failed to write log event to CouchDB due to error: " + response.getError() + ".");
/*    */       }
/*    */     }
/* 56 */     catch (Exception e) {
/* 57 */       throw new AppenderLoggingException("Failed to write log event to CouchDB due to error: " + e.getMessage(), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void close() {
/* 64 */     this.closed = true;
/* 65 */     this.client.shutdown();
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized boolean isClosed() {
/* 70 */     return this.closed;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\couch\CouchDBConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */