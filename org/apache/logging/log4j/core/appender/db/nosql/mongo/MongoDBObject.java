/*    */ package org.apache.logging.log4j.core.appender.db.nosql.mongo;
/*    */ 
/*    */ import com.mongodb.BasicDBList;
/*    */ import com.mongodb.BasicDBObject;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLObject;
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
/*    */ public final class MongoDBObject
/*    */   implements NoSQLObject<BasicDBObject>
/*    */ {
/* 33 */   private final BasicDBObject mongoObject = new BasicDBObject();
/*    */ 
/*    */ 
/*    */   
/*    */   public void set(String field, Object value) {
/* 38 */     this.mongoObject.append(field, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(String field, NoSQLObject<BasicDBObject> value) {
/* 43 */     this.mongoObject.append(field, value.unwrap());
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(String field, Object[] values) {
/* 48 */     BasicDBList list = new BasicDBList();
/* 49 */     Collections.addAll((Collection<? super Object>)list, values);
/* 50 */     this.mongoObject.append(field, list);
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(String field, NoSQLObject<BasicDBObject>[] values) {
/* 55 */     BasicDBList list = new BasicDBList();
/* 56 */     for (NoSQLObject<BasicDBObject> value : values) {
/* 57 */       list.add(value.unwrap());
/*    */     }
/* 59 */     this.mongoObject.append(field, list);
/*    */   }
/*    */ 
/*    */   
/*    */   public BasicDBObject unwrap() {
/* 64 */     return this.mongoObject;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\mongo\MongoDBObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */