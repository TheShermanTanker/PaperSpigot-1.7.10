/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
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
/*    */ public class ScalarTypeShort
/*    */   extends ScalarTypeBase<Short>
/*    */ {
/*    */   public ScalarTypeShort() {
/* 37 */     super(Short.class, true, 5);
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, Short value) throws SQLException {
/* 41 */     if (value == null) {
/* 42 */       b.setNull(5);
/*    */     } else {
/* 44 */       b.setShort(value.shortValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Short read(DataReader dataReader) throws SQLException {
/* 50 */     return dataReader.getShort();
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 54 */     return BasicTypeConverter.toShort(value);
/*    */   }
/*    */   
/*    */   public Short toBeanType(Object value) {
/* 58 */     return BasicTypeConverter.toShort(value);
/*    */   }
/*    */   
/*    */   public String formatValue(Short v) {
/* 62 */     return v.toString();
/*    */   }
/*    */   
/*    */   public Short parse(String value) {
/* 66 */     return Short.valueOf(value);
/*    */   }
/*    */   
/*    */   public Short parseDateTime(long systemTimeMillis) {
/* 70 */     throw new TextException("Not Supported");
/*    */   }
/*    */   
/*    */   public boolean isDateTimeCapable() {
/* 74 */     return false;
/*    */   }
/*    */   
/*    */   public Object readData(DataInput dataInput) throws IOException {
/* 78 */     if (!dataInput.readBoolean()) {
/* 79 */       return null;
/*    */     }
/* 81 */     short val = dataInput.readShort();
/* 82 */     return Short.valueOf(val);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 88 */     Short value = (Short)v;
/* 89 */     if (value == null) {
/* 90 */       dataOutput.writeBoolean(false);
/*    */     } else {
/* 92 */       dataOutput.writeBoolean(true);
/* 93 */       dataOutput.writeShort(value.shortValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeShort.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */