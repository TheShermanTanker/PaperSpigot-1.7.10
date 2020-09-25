/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.Date;
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
/*     */ public abstract class ScalarTypeBaseDate<T>
/*     */   extends ScalarTypeBase<T>
/*     */ {
/*     */   public ScalarTypeBaseDate(Class<T> type, boolean jdbcNative, int jdbcType) {
/*  38 */     super(type, jdbcNative, jdbcType);
/*     */   }
/*     */   
/*     */   public abstract Date convertToDate(T paramT);
/*     */   
/*     */   public abstract T convertFromDate(Date paramDate);
/*     */   
/*     */   public void bind(DataBind b, T value) throws SQLException {
/*  46 */     if (value == null) {
/*  47 */       b.setNull(91);
/*     */     } else {
/*  49 */       Date date = convertToDate(value);
/*  50 */       b.setDate(date);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(DataReader dataReader) throws SQLException {
/*  56 */     Date ts = dataReader.getDate();
/*  57 */     if (ts == null) {
/*  58 */       return null;
/*     */     }
/*  60 */     return convertFromDate(ts);
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatValue(T t) {
/*  65 */     Date date = convertToDate(t);
/*  66 */     return date.toString();
/*     */   }
/*     */   
/*     */   public T parse(String value) {
/*  70 */     Date date = Date.valueOf(value);
/*  71 */     return convertFromDate(date);
/*     */   }
/*     */   
/*     */   public T parseDateTime(long systemTimeMillis) {
/*  75 */     Date ts = new Date(systemTimeMillis);
/*  76 */     return convertFromDate(ts);
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String jsonToString(T value, JsonValueAdapter ctx) {
/*  85 */     Date date = convertToDate(value);
/*  86 */     return ctx.jsonFromDate(date);
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonBuffer buffer, T value, JsonValueAdapter ctx) {
/*  91 */     String s = jsonToString(value, ctx);
/*  92 */     buffer.append(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public T jsonFromString(String value, JsonValueAdapter ctx) {
/*  97 */     Date ts = ctx.jsonToDate(value);
/*  98 */     return convertFromDate(ts);
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 102 */     if (!dataInput.readBoolean()) {
/* 103 */       return null;
/*     */     }
/* 105 */     long val = dataInput.readLong();
/* 106 */     Date date = new Date(val);
/* 107 */     return convertFromDate(date);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 114 */     T value = (T)v;
/* 115 */     if (value == null) {
/* 116 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 118 */       dataOutput.writeBoolean(true);
/* 119 */       Date date = convertToDate(value);
/* 120 */       dataOutput.writeLong(date.getTime());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBaseDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */