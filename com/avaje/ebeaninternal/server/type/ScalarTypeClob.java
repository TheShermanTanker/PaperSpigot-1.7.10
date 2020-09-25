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
/*    */ 
/*    */ public class ScalarTypeClob
/*    */   extends ScalarTypeBaseVarchar<String>
/*    */ {
/*    */   static final int clobBufferSize = 512;
/*    */   static final int stringInitialSize = 512;
/*    */   
/*    */   protected ScalarTypeClob(boolean jdbcNative, int jdbcType) {
/* 38 */     super(String.class, jdbcNative, jdbcType);
/*    */   }
/*    */   
/*    */   public ScalarTypeClob() {
/* 42 */     super(String.class, true, 2005);
/*    */   }
/*    */ 
/*    */   
/*    */   public String convertFromDbString(String dbValue) {
/* 47 */     return dbValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public String convertToDbString(String beanValue) {
/* 52 */     return beanValue;
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, String value) throws SQLException {
/* 56 */     if (value == null) {
/* 57 */       b.setNull(12);
/*    */     } else {
/* 59 */       b.setString(value);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String read(DataReader dataReader) throws SQLException {
/* 65 */     return dataReader.getStringClob();
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 69 */     return BasicTypeConverter.toString(value);
/*    */   }
/*    */   
/*    */   public String toBeanType(Object value) {
/* 73 */     return BasicTypeConverter.toString(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public String formatValue(String t) {
/* 78 */     return t;
/*    */   }
/*    */   
/*    */   public String parse(String value) {
/* 82 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String jsonFromString(String value, JsonValueAdapter ctx) {
/* 87 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String jsonToString(String value, JsonValueAdapter ctx) {
/* 92 */     return EscapeJson.escapeQuote(value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeClob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */