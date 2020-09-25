/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Time;
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
/*    */ public class ScalarTypeTime
/*    */   extends ScalarTypeBase<Time>
/*    */ {
/*    */   public ScalarTypeTime() {
/* 37 */     super(Time.class, true, 92);
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, Time value) throws SQLException {
/* 41 */     if (value == null) {
/* 42 */       b.setNull(92);
/*    */     } else {
/* 44 */       b.setTime(value);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Time read(DataReader dataReader) throws SQLException {
/* 50 */     return dataReader.getTime();
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 54 */     return BasicTypeConverter.toTime(value);
/*    */   }
/*    */   
/*    */   public Time toBeanType(Object value) {
/* 58 */     return BasicTypeConverter.toTime(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public String formatValue(Time v) {
/* 63 */     return v.toString();
/*    */   }
/*    */   
/*    */   public Time parse(String value) {
/* 67 */     return Time.valueOf(value);
/*    */   }
/*    */   
/*    */   public Time parseDateTime(long systemTimeMillis) {
/* 71 */     return new Time(systemTimeMillis);
/*    */   }
/*    */   
/*    */   public boolean isDateTimeCapable() {
/* 75 */     return true;
/*    */   }
/*    */   
/*    */   public Object readData(DataInput dataInput) throws IOException {
/* 79 */     if (!dataInput.readBoolean()) {
/* 80 */       return null;
/*    */     }
/* 82 */     String val = dataInput.readUTF();
/* 83 */     return parse(val);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 89 */     Time value = (Time)v;
/* 90 */     if (value == null) {
/* 91 */       dataOutput.writeBoolean(false);
/*    */     } else {
/* 93 */       dataOutput.writeBoolean(true);
/* 94 */       dataOutput.writeUTF(format(value));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */