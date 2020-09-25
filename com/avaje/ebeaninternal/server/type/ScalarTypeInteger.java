/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
/*    */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
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
/*    */ public class ScalarTypeInteger
/*    */   extends ScalarTypeBase<Integer>
/*    */ {
/*    */   public ScalarTypeInteger() {
/* 38 */     super(Integer.class, true, 4);
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, Integer value) throws SQLException {
/* 42 */     if (value == null) {
/* 43 */       b.setNull(4);
/*    */     } else {
/* 45 */       b.setInt(value.intValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer read(DataReader dataReader) throws SQLException {
/* 51 */     return dataReader.getInt();
/*    */   }
/*    */   
/*    */   public Object readData(DataInput dataInput) throws IOException {
/* 55 */     return Integer.valueOf(dataInput.readInt());
/*    */   }
/*    */   
/*    */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 59 */     dataOutput.writeInt(((Integer)v).intValue());
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 63 */     return BasicTypeConverter.toInteger(value);
/*    */   }
/*    */   
/*    */   public Integer toBeanType(Object value) {
/* 67 */     return BasicTypeConverter.toInteger(value);
/*    */   }
/*    */   
/*    */   public String formatValue(Integer v) {
/* 71 */     return v.toString();
/*    */   }
/*    */   
/*    */   public Integer parse(String value) {
/* 75 */     return Integer.valueOf(value);
/*    */   }
/*    */   
/*    */   public Integer parseDateTime(long systemTimeMillis) {
/* 79 */     throw new TextException("Not Supported");
/*    */   }
/*    */   
/*    */   public boolean isDateTimeCapable() {
/* 83 */     return false;
/*    */   }
/*    */   
/*    */   public String jsonToString(Integer value, JsonValueAdapter ctx) {
/* 87 */     return value.toString();
/*    */   }
/*    */   
/*    */   public Integer jsonFromString(String value, JsonValueAdapter ctx) {
/* 91 */     return Integer.valueOf(value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeInteger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */