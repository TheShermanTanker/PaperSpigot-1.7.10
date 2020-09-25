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
/*     */ public class ScalarTypeFloat
/*     */   extends ScalarTypeBase<Float>
/*     */ {
/*     */   public ScalarTypeFloat() {
/*  36 */     super(Float.class, true, 7);
/*     */   }
/*     */   
/*     */   public void bind(DataBind b, Float value) throws SQLException {
/*  40 */     if (value == null) {
/*  41 */       b.setNull(7);
/*     */     } else {
/*  43 */       b.setFloat(value.floatValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Float read(DataReader dataReader) throws SQLException {
/*  49 */     return dataReader.getFloat();
/*     */   }
/*     */   
/*     */   public Object toJdbcType(Object value) {
/*  53 */     return BasicTypeConverter.toFloat(value);
/*     */   }
/*     */   
/*     */   public Float toBeanType(Object value) {
/*  57 */     return BasicTypeConverter.toFloat(value);
/*     */   }
/*     */   
/*     */   public String formatValue(Float t) {
/*  61 */     return t.toString();
/*     */   }
/*     */   
/*     */   public Float parse(String value) {
/*  65 */     return Float.valueOf(value);
/*     */   }
/*     */   
/*     */   public Float parseDateTime(long systemTimeMillis) {
/*  69 */     return Float.valueOf((float)systemTimeMillis);
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   public String toJsonString(Float value) {
/*  77 */     if (value.isInfinite() || value.isNaN()) {
/*  78 */       return "null";
/*     */     }
/*  80 */     return value.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  85 */     if (!dataInput.readBoolean()) {
/*  86 */       return null;
/*     */     }
/*  88 */     float val = dataInput.readFloat();
/*  89 */     return Float.valueOf(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/*  95 */     Float value = (Float)v;
/*  96 */     if (value == null) {
/*  97 */       dataOutput.writeBoolean(false);
/*     */     } else {
/*  99 */       dataOutput.writeBoolean(true);
/* 100 */       dataOutput.writeFloat(value.floatValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeFloat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */