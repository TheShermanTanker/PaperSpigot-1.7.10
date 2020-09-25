/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.LocalTime;
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
/*     */ 
/*     */ public class ScalarTypeJodaLocalTime
/*     */   extends ScalarTypeBase<LocalTime>
/*     */ {
/*     */   public ScalarTypeJodaLocalTime() {
/*  40 */     super(LocalTime.class, false, 92);
/*     */   }
/*     */   
/*     */   public void bind(DataBind b, LocalTime value) throws SQLException {
/*  44 */     if (value == null) {
/*  45 */       b.setNull(92);
/*     */     } else {
/*  47 */       Time sqlTime = new Time(value.getMillisOfDay());
/*  48 */       b.setTime(sqlTime);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalTime read(DataReader dataReader) throws SQLException {
/*  54 */     Time sqlTime = dataReader.getTime();
/*  55 */     if (sqlTime == null) {
/*  56 */       return null;
/*     */     }
/*  58 */     return new LocalTime(sqlTime, DateTimeZone.UTC);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object toJdbcType(Object value) {
/*  63 */     if (value instanceof LocalTime) {
/*  64 */       return new Time(((LocalTime)value).getMillisOfDay());
/*     */     }
/*  66 */     return BasicTypeConverter.toTime(value);
/*     */   }
/*     */   
/*     */   public LocalTime toBeanType(Object value) {
/*  70 */     if (value instanceof java.util.Date) {
/*  71 */       return new LocalTime(value, DateTimeZone.UTC);
/*     */     }
/*  73 */     return (LocalTime)value;
/*     */   }
/*     */   
/*     */   public String formatValue(LocalTime v) {
/*  77 */     return v.toString();
/*     */   }
/*     */   
/*     */   public LocalTime parse(String value) {
/*  81 */     return new LocalTime(value);
/*     */   }
/*     */   
/*     */   public LocalTime parseDateTime(long systemTimeMillis) {
/*  85 */     return new LocalTime(systemTimeMillis);
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  93 */     if (!dataInput.readBoolean()) {
/*  94 */       return null;
/*     */     }
/*  96 */     String val = dataInput.readUTF();
/*  97 */     return parse(val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 103 */     Time value = (Time)v;
/* 104 */     if (value == null) {
/* 105 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 107 */       dataOutput.writeBoolean(true);
/* 108 */       dataOutput.writeUTF(format(value));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaLocalTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */