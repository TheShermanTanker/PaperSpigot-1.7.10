/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
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
/*     */ public abstract class ScalarTypeBaseDateTime<T>
/*     */   extends ScalarTypeBase<T>
/*     */ {
/*     */   public ScalarTypeBaseDateTime(Class<T> type, boolean jdbcNative, int jdbcType) {
/*  38 */     super(type, jdbcNative, jdbcType);
/*     */   }
/*     */   
/*     */   public abstract Timestamp convertToTimestamp(T paramT);
/*     */   
/*     */   public abstract T convertFromTimestamp(Timestamp paramTimestamp);
/*     */   
/*     */   public void bind(DataBind b, T value) throws SQLException {
/*  46 */     if (value == null) {
/*  47 */       b.setNull(93);
/*     */     } else {
/*  49 */       Timestamp ts = convertToTimestamp(value);
/*  50 */       b.setTimestamp(ts);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(DataReader dataReader) throws SQLException {
/*  56 */     Timestamp ts = dataReader.getTimestamp();
/*  57 */     if (ts == null) {
/*  58 */       return null;
/*     */     }
/*  60 */     return convertFromTimestamp(ts);
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatValue(T t) {
/*  65 */     Timestamp ts = convertToTimestamp(t);
/*  66 */     return ts.toString();
/*     */   }
/*     */   
/*     */   public T parse(String value) {
/*  70 */     Timestamp ts = Timestamp.valueOf(value);
/*  71 */     return convertFromTimestamp(ts);
/*     */   }
/*     */   
/*     */   public T parseDateTime(long systemTimeMillis) {
/*  75 */     Timestamp ts = new Timestamp(systemTimeMillis);
/*  76 */     return convertFromTimestamp(ts);
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonBuffer buffer, T value, JsonValueAdapter ctx) {
/*  85 */     String v = jsonToString(value, ctx);
/*  86 */     buffer.append(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public String jsonToString(T value, JsonValueAdapter ctx) {
/*  91 */     Timestamp ts = convertToTimestamp(value);
/*  92 */     return ctx.jsonFromTimestamp(ts);
/*     */   }
/*     */ 
/*     */   
/*     */   public T jsonFromString(String value, JsonValueAdapter ctx) {
/*  97 */     Timestamp ts = ctx.jsonToTimestamp(value);
/*  98 */     return convertFromTimestamp(ts);
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 102 */     if (!dataInput.readBoolean()) {
/* 103 */       return null;
/*     */     }
/* 105 */     long val = dataInput.readLong();
/* 106 */     Timestamp ts = new Timestamp(val);
/* 107 */     return convertFromTimestamp(ts);
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
/* 119 */       Timestamp ts = convertToTimestamp(value);
/* 120 */       dataOutput.writeLong(ts.getTime());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBaseDateTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */