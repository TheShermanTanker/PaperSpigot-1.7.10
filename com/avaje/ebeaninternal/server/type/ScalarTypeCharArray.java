/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.SQLException;
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
/*    */ 
/*    */ 
/*    */ public class ScalarTypeCharArray
/*    */   extends ScalarTypeBaseVarchar<char[]>
/*    */ {
/*    */   public ScalarTypeCharArray() {
/* 34 */     super((Class)char[].class, false, 12);
/*    */   }
/*    */ 
/*    */   
/*    */   public char[] convertFromDbString(String dbValue) {
/* 39 */     return dbValue.toCharArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public String convertToDbString(char[] beanValue) {
/* 44 */     return new String(beanValue);
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, char[] value) throws SQLException {
/* 48 */     if (value == null) {
/* 49 */       b.setNull(12);
/*    */     } else {
/* 51 */       String s = BasicTypeConverter.toString(value);
/* 52 */       b.setString(s);
/*    */     } 
/*    */   }
/*    */   
/*    */   public char[] read(DataReader dataReader) throws SQLException {
/* 57 */     String string = dataReader.getString();
/* 58 */     if (string == null) {
/* 59 */       return null;
/*    */     }
/* 61 */     return string.toCharArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 66 */     return BasicTypeConverter.toString(value);
/*    */   }
/*    */   
/*    */   public char[] toBeanType(Object value) {
/* 70 */     String s = BasicTypeConverter.toString(value);
/* 71 */     return s.toCharArray();
/*    */   }
/*    */   
/*    */   public String formatValue(char[] t) {
/* 75 */     return String.valueOf(t);
/*    */   }
/*    */   
/*    */   public char[] parse(String value) {
/* 79 */     return value.toCharArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public char[] jsonFromString(String value, JsonValueAdapter ctx) {
/* 84 */     return value.toCharArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public String jsonToString(char[] value, JsonValueAdapter ctx) {
/* 89 */     return EscapeJson.escapeQuote(String.valueOf(value));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeCharArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */