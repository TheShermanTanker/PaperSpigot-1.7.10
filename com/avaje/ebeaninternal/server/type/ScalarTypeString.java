/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
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
/*     */ public class ScalarTypeString
/*     */   extends ScalarTypeBase<String>
/*     */ {
/*     */   public ScalarTypeString() {
/*  38 */     super(String.class, true, 12);
/*     */   }
/*     */   
/*     */   public void bind(DataBind b, String value) throws SQLException {
/*  42 */     if (value == null) {
/*  43 */       b.setNull(12);
/*     */     } else {
/*  45 */       b.setString(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String read(DataReader dataReader) throws SQLException {
/*  51 */     return dataReader.getString();
/*     */   }
/*     */   
/*     */   public Object toJdbcType(Object value) {
/*  55 */     return BasicTypeConverter.toString(value);
/*     */   }
/*     */   
/*     */   public String toBeanType(Object value) {
/*  59 */     return BasicTypeConverter.toString(value);
/*     */   }
/*     */   
/*     */   public String formatValue(String t) {
/*  63 */     return t;
/*     */   }
/*     */   
/*     */   public String parse(String value) {
/*  67 */     return value;
/*     */   }
/*     */   
/*     */   public String parseDateTime(long systemTimeMillis) {
/*  71 */     return String.valueOf(systemTimeMillis);
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonBuffer buffer, String value, JsonValueAdapter ctx) {
/*  81 */     String s = format(value);
/*  82 */     EscapeJson.escapeQuote(s, buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public String jsonFromString(String value, JsonValueAdapter ctx) {
/*  87 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String jsonToString(String value, JsonValueAdapter ctx) {
/*  92 */     return EscapeJson.escapeQuote(value);
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  96 */     if (!dataInput.readBoolean()) {
/*  97 */       return null;
/*     */     }
/*  99 */     return dataInput.readUTF();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 105 */     String value = (String)v;
/* 106 */     if (value == null) {
/* 107 */       dataOutput.writeBoolean(false);
/*     */     } else {
/* 109 */       dataOutput.writeBoolean(true);
/* 110 */       dataOutput.writeUTF(value);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */