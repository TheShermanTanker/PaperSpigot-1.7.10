/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import java.math.BigInteger;
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
/*    */ public class ScalarTypeMathBigInteger
/*    */   extends ScalarTypeBase<BigInteger>
/*    */ {
/*    */   public ScalarTypeMathBigInteger() {
/* 37 */     super(BigInteger.class, false, -5);
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, BigInteger value) throws SQLException {
/* 41 */     if (value == null) {
/* 42 */       b.setNull(-5);
/*    */     } else {
/* 44 */       b.setLong(value.longValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public BigInteger read(DataReader dataReader) throws SQLException {
/* 50 */     Long l = dataReader.getLong();
/* 51 */     if (l == null) {
/* 52 */       return null;
/*    */     }
/* 54 */     return new BigInteger(String.valueOf(l));
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 58 */     return BasicTypeConverter.toLong(value);
/*    */   }
/*    */   
/*    */   public BigInteger toBeanType(Object value) {
/* 62 */     return BasicTypeConverter.toMathBigInteger(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public String formatValue(BigInteger v) {
/* 67 */     return v.toString();
/*    */   }
/*    */   
/*    */   public BigInteger parse(String value) {
/* 71 */     return new BigInteger(value);
/*    */   }
/*    */   
/*    */   public BigInteger parseDateTime(long systemTimeMillis) {
/* 75 */     return BigInteger.valueOf(systemTimeMillis);
/*    */   }
/*    */   
/*    */   public boolean isDateTimeCapable() {
/* 79 */     return true;
/*    */   }
/*    */   
/*    */   public Object readData(DataInput dataInput) throws IOException {
/* 83 */     if (!dataInput.readBoolean()) {
/* 84 */       return null;
/*    */     }
/* 86 */     long val = dataInput.readLong();
/* 87 */     return Long.valueOf(val);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 93 */     Long value = (Long)v;
/* 94 */     if (value == null) {
/* 95 */       dataOutput.writeBoolean(false);
/*    */     } else {
/* 97 */       dataOutput.writeBoolean(true);
/* 98 */       dataOutput.writeLong(value.longValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeMathBigInteger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */