/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.TextException;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;
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
/*     */ public abstract class ScalarTypeBaseVarchar<T>
/*     */   extends ScalarTypeBase<T>
/*     */ {
/*     */   public ScalarTypeBaseVarchar(Class<T> type) {
/*  38 */     super(type, false, 12);
/*     */   }
/*     */   
/*     */   public ScalarTypeBaseVarchar(Class<T> type, boolean jdbcNative, int jdbcType) {
/*  42 */     super(type, jdbcNative, jdbcType);
/*     */   }
/*     */   
/*     */   public abstract String formatValue(T paramT);
/*     */   
/*     */   public abstract T parse(String paramString);
/*     */   
/*     */   public abstract T convertFromDbString(String paramString);
/*     */   
/*     */   public abstract String convertToDbString(T paramT);
/*     */   
/*     */   public void bind(DataBind b, T value) throws SQLException {
/*  54 */     if (value == null) {
/*  55 */       b.setNull(12);
/*     */     } else {
/*     */       
/*  58 */       String s = convertToDbString(value);
/*  59 */       b.setString(s);
/*     */     } 
/*     */   }
/*     */   
/*     */   public T read(DataReader dataReader) throws SQLException {
/*  64 */     String s = dataReader.getString();
/*  65 */     if (s == null) {
/*  66 */       return null;
/*     */     }
/*  68 */     return convertFromDbString(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T toBeanType(Object value) {
/*  74 */     if (value == null) {
/*  75 */       return null;
/*     */     }
/*  77 */     if (value instanceof String) {
/*  78 */       return parse((String)value);
/*     */     }
/*  80 */     return (T)value;
/*     */   }
/*     */   
/*     */   public Object toJdbcType(Object value) {
/*  84 */     if (value instanceof String) {
/*  85 */       return parse((String)value);
/*     */     }
/*  87 */     return value;
/*     */   }
/*     */   
/*     */   public T parseDateTime(long systemTimeMillis) {
/*  91 */     throw new TextException("Not Supported");
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String format(Object v) {
/* 100 */     return formatValue((T)v);
/*     */   }
/*     */   
/*     */   public T jsonFromString(String value, JsonValueAdapter ctx) {
/* 104 */     return parse(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonBuffer buffer, T value, JsonValueAdapter ctx) {
/* 109 */     String s = format(value);
/* 110 */     EscapeJson.escapeQuote(s, buffer);
/*     */   }
/*     */   
/*     */   public String toJsonString(Object value, JsonValueAdapter ctx) {
/* 114 */     String s = format(value);
/* 115 */     return EscapeJson.escapeQuote(s);
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 119 */     if (!dataInput.readBoolean()) {
/* 120 */       return null;
/*     */     }
/* 122 */     String val = dataInput.readUTF();
/* 123 */     return convertFromDbString(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 130 */     T value = (T)v;
/* 131 */     if (value == null) {
/* 132 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 134 */       dataOutput.writeBoolean(true);
/* 135 */       String s = convertToDbString(value);
/* 136 */       dataOutput.writeUTF(s);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBaseVarchar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */