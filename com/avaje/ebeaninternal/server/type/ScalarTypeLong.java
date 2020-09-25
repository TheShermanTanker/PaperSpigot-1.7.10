/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
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
/*    */ public class ScalarTypeLong
/*    */   extends ScalarTypeBase<Long>
/*    */ {
/*    */   public ScalarTypeLong() {
/* 36 */     super(Long.class, true, -5);
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, Long value) throws SQLException {
/* 40 */     if (value == null) {
/* 41 */       b.setNull(-5);
/*    */     } else {
/* 43 */       b.setLong(value.longValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Long read(DataReader dataReader) throws SQLException {
/* 49 */     return dataReader.getLong();
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 53 */     return BasicTypeConverter.toLong(value);
/*    */   }
/*    */   
/*    */   public Long toBeanType(Object value) {
/* 57 */     return BasicTypeConverter.toLong(value);
/*    */   }
/*    */   
/*    */   public String formatValue(Long t) {
/* 61 */     return t.toString();
/*    */   }
/*    */   
/*    */   public Long parse(String value) {
/* 65 */     return Long.valueOf(value);
/*    */   }
/*    */   
/*    */   public Long parseDateTime(long systemTimeMillis) {
/* 69 */     return Long.valueOf(systemTimeMillis);
/*    */   }
/*    */   
/*    */   public boolean isDateTimeCapable() {
/* 73 */     return true;
/*    */   }
/*    */   
/*    */   public Object readData(DataInput dataInput) throws IOException {
/* 77 */     if (!dataInput.readBoolean()) {
/* 78 */       return null;
/*    */     }
/* 80 */     long val = dataInput.readLong();
/* 81 */     return Long.valueOf(val);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 87 */     Long value = (Long)v;
/* 88 */     if (value == null) {
/* 89 */       dataOutput.writeBoolean(false);
/*    */     } else {
/* 91 */       dataOutput.writeBoolean(true);
/* 92 */       dataOutput.writeLong(value.longValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLong.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */