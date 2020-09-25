/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import java.math.BigDecimal;
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
/*    */ public class ScalarTypeBigDecimal
/*    */   extends ScalarTypeBase<BigDecimal>
/*    */ {
/*    */   public ScalarTypeBigDecimal() {
/* 37 */     super(BigDecimal.class, true, 3);
/*    */   }
/*    */   
/*    */   public Object readData(DataInput dataInput) throws IOException {
/* 41 */     if (!dataInput.readBoolean()) {
/* 42 */       return null;
/*    */     }
/* 44 */     double val = dataInput.readDouble();
/* 45 */     return new BigDecimal(val);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 51 */     BigDecimal b = (BigDecimal)v;
/* 52 */     if (b == null) {
/* 53 */       dataOutput.writeBoolean(false);
/*    */     } else {
/* 55 */       dataOutput.writeBoolean(true);
/* 56 */       dataOutput.writeDouble(b.doubleValue());
/*    */     } 
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, BigDecimal value) throws SQLException {
/* 61 */     if (value == null) {
/* 62 */       b.setNull(3);
/*    */     } else {
/* 64 */       b.setBigDecimal(value);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal read(DataReader dataReader) throws SQLException {
/* 70 */     return dataReader.getBigDecimal();
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 74 */     return BasicTypeConverter.toBigDecimal(value);
/*    */   }
/*    */   
/*    */   public BigDecimal toBeanType(Object value) {
/* 78 */     return BasicTypeConverter.toBigDecimal(value);
/*    */   }
/*    */   
/*    */   public String formatValue(BigDecimal t) {
/* 82 */     return t.toPlainString();
/*    */   }
/*    */   
/*    */   public BigDecimal parse(String value) {
/* 86 */     return new BigDecimal(value);
/*    */   }
/*    */   
/*    */   public BigDecimal parseDateTime(long systemTimeMillis) {
/* 90 */     return BigDecimal.valueOf(systemTimeMillis);
/*    */   }
/*    */   
/*    */   public boolean isDateTimeCapable() {
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBigDecimal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */