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
/*    */ public class ScalarTypeChar
/*    */   extends ScalarTypeBaseVarchar<Character>
/*    */ {
/*    */   public ScalarTypeChar() {
/* 34 */     super((Class)char.class, false, 12);
/*    */   }
/*    */ 
/*    */   
/*    */   public Character convertFromDbString(String dbValue) {
/* 39 */     return Character.valueOf(dbValue.charAt(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public String convertToDbString(Character beanValue) {
/* 44 */     return beanValue.toString();
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, Character value) throws SQLException {
/* 48 */     if (value == null) {
/* 49 */       b.setNull(12);
/*    */     } else {
/* 51 */       String s = BasicTypeConverter.toString(value);
/* 52 */       b.setString(s);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Character read(DataReader dataReader) throws SQLException {
/* 57 */     String string = dataReader.getString();
/* 58 */     if (string == null || string.length() == 0) {
/* 59 */       return null;
/*    */     }
/* 61 */     return Character.valueOf(string.charAt(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 66 */     return BasicTypeConverter.toString(value);
/*    */   }
/*    */   
/*    */   public Character toBeanType(Object value) {
/* 70 */     String s = BasicTypeConverter.toString(value);
/* 71 */     return Character.valueOf(s.charAt(0));
/*    */   }
/*    */   
/*    */   public String formatValue(Character t) {
/* 75 */     return t.toString();
/*    */   }
/*    */   
/*    */   public Character parse(String value) {
/* 79 */     return Character.valueOf(value.charAt(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Character jsonFromString(String value, JsonValueAdapter ctx) {
/* 84 */     return Character.valueOf(value.charAt(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public String jsonToString(Character value, JsonValueAdapter ctx) {
/* 89 */     return EscapeJson.escapeQuote(value.toString());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeChar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */