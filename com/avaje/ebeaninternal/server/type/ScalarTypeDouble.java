/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalarTypeDouble
/*     */   extends ScalarTypeBase<Double>
/*     */ {
/*     */   public ScalarTypeDouble() {
/*  36 */     super(Double.class, true, 8);
/*     */   }
/*     */   
/*     */   public void bind(DataBind b, Double value) throws SQLException {
/*  40 */     if (value == null) {
/*  41 */       b.setNull(8);
/*     */     } else {
/*  43 */       b.setDouble(value.doubleValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Double read(DataReader dataReader) throws SQLException {
/*  49 */     return dataReader.getDouble();
/*     */   }
/*     */   
/*     */   public Object toJdbcType(Object value) {
/*  53 */     return BasicTypeConverter.toDouble(value);
/*     */   }
/*     */   
/*     */   public Double toBeanType(Object value) {
/*  57 */     return BasicTypeConverter.toDouble(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatValue(Double t) {
/*  62 */     return t.toString();
/*     */   }
/*     */   
/*     */   public Double parse(String value) {
/*  66 */     return Double.valueOf(value);
/*     */   }
/*     */   
/*     */   public Double parseDateTime(long systemTimeMillis) {
/*  70 */     return Double.valueOf(systemTimeMillis);
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/*  74 */     return true;
/*     */   }
/*     */   
/*     */   public String toJsonString(Double value) {
/*  78 */     if (value.isInfinite() || value.isNaN()) {
/*  79 */       return "null";
/*     */     }
/*  81 */     return value.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  86 */     if (!dataInput.readBoolean()) {
/*  87 */       return null;
/*     */     }
/*  89 */     double val = dataInput.readDouble();
/*  90 */     return Double.valueOf(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/*  96 */     Double value = (Double)v;
/*  97 */     if (value == null) {
/*  98 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 100 */       dataOutput.writeBoolean(true);
/* 101 */       dataOutput.writeDouble(value.doubleValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeDouble.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */