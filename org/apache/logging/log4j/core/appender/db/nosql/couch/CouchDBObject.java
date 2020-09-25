/*    */ package org.apache.logging.log4j.core.appender.db.nosql.couch;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public final class CouchDBObject
/*    */   implements NoSQLObject<Map<String, Object>>
/*    */ {
/* 33 */   private final Map<String, Object> map = new HashMap<String, Object>();
/*    */ 
/*    */ 
/*    */   
/*    */   public void set(String field, Object value) {
/* 38 */     this.map.put(field, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(String field, NoSQLObject<Map<String, Object>> value) {
/* 43 */     this.map.put(field, value.unwrap());
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(String field, Object[] values) {
/* 48 */     this.map.put(field, Arrays.asList(values));
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(String field, NoSQLObject<Map<String, Object>>[] values) {
/* 53 */     ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
/* 54 */     for (NoSQLObject<Map<String, Object>> value : values) {
/* 55 */       list.add(value.unwrap());
/*    */     }
/* 57 */     this.map.put(field, list);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Object> unwrap() {
/* 62 */     return this.map;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\couch\CouchDBObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */