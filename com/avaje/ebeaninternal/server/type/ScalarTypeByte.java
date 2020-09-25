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
/*    */ public class ScalarTypeByte
/*    */   extends ScalarTypeBase<Byte>
/*    */ {
/*    */   public ScalarTypeByte() {
/* 37 */     super(Byte.class, true, -6);
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, Byte value) throws SQLException {
/* 41 */     if (value == null) {
/* 42 */       b.setNull(-6);
/*    */     } else {
/* 44 */       b.setByte(value.byteValue());
/*    */     } 
/*    */   }
/*    */   
/*    */   public Byte read(DataReader dataReader) throws SQLException {
/* 49 */     return dataReader.getByte();
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 53 */     return BasicTypeConverter.toByte(value);
/*    */   }
/*    */   
/*    */   public Byte toBeanType(Object value) {
/* 57 */     return BasicTypeConverter.toByte(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public String formatValue(Byte t) {
/* 62 */     return t.toString();
/*    */   }
/*    */   
/*    */   public Byte parse(String value) {
/* 66 */     throw new TextException("Not supported");
/*    */   }
/*    */   
/*    */   public Byte parseDateTime(long systemTimeMillis) {
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
/* 81 */     byte val = dataInput.readByte();
/* 82 */     return Byte.valueOf(val);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 88 */     Byte val = (Byte)v;
/* 89 */     if (val == null) {
/* 90 */       dataOutput.writeBoolean(false);
/*    */     } else {
/* 92 */       dataOutput.writeBoolean(true);
/* 93 */       dataOutput.writeByte(val.byteValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeByte.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */