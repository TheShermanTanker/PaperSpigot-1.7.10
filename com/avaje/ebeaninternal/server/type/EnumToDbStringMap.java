/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnumToDbStringMap
/*    */   extends EnumToDbValueMap<String>
/*    */ {
/*    */   public int getDbType() {
/* 15 */     return 12;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumToDbStringMap add(Object beanValue, String dbValue) {
/* 20 */     addInternal(beanValue, dbValue);
/* 21 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void bind(DataBind b, Object value) throws SQLException {
/* 26 */     if (value == null) {
/* 27 */       b.setNull(12);
/*    */     } else {
/* 29 */       String s = getDbValue(value);
/* 30 */       b.setString(s);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object read(DataReader dataReader) throws SQLException {
/* 37 */     String s = dataReader.getString();
/* 38 */     if (s == null) {
/* 39 */       return null;
/*    */     }
/* 41 */     return getBeanValue(s);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\EnumToDbStringMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */