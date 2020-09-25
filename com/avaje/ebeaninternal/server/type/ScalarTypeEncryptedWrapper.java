/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
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
/*     */ public class ScalarTypeEncryptedWrapper<T>
/*     */   implements ScalarType<T>
/*     */ {
/*     */   private final ScalarType<T> wrapped;
/*     */   private final DataEncryptSupport dataEncryptSupport;
/*     */   private final ScalarTypeBytesBase byteArrayType;
/*     */   
/*     */   public ScalarTypeEncryptedWrapper(ScalarType<T> wrapped, ScalarTypeBytesBase byteArrayType, DataEncryptSupport dataEncryptSupport) {
/*  39 */     this.wrapped = wrapped;
/*  40 */     this.byteArrayType = byteArrayType;
/*  41 */     this.dataEncryptSupport = dataEncryptSupport;
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  45 */     return this.wrapped.readData(dataInput);
/*     */   }
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/*  49 */     this.wrapped.writeData(dataOutput, v);
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(DataReader dataReader) throws SQLException {
/*  54 */     byte[] data = dataReader.getBytes();
/*  55 */     String formattedValue = this.dataEncryptSupport.decryptObject(data);
/*  56 */     if (formattedValue == null) {
/*  57 */       return null;
/*     */     }
/*  59 */     return this.wrapped.parse(formattedValue);
/*     */   }
/*     */   
/*     */   private byte[] encrypt(T value) {
/*  63 */     String formatValue = this.wrapped.formatValue(value);
/*  64 */     return this.dataEncryptSupport.encryptObject(formatValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, T value) throws SQLException {
/*  69 */     byte[] encryptedValue = encrypt(value);
/*  70 */     this.byteArrayType.bind(b, encryptedValue);
/*     */   }
/*     */   
/*     */   public int getJdbcType() {
/*  74 */     return this.byteArrayType.getJdbcType();
/*     */   }
/*     */   
/*     */   public int getLength() {
/*  78 */     return this.byteArrayType.getLength();
/*     */   }
/*     */   
/*     */   public Class<T> getType() {
/*  82 */     return this.wrapped.getType();
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/*  86 */     return this.wrapped.isDateTimeCapable();
/*     */   }
/*     */   
/*     */   public boolean isJdbcNative() {
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   public void loadIgnore(DataReader dataReader) {
/*  94 */     this.wrapped.loadIgnore(dataReader);
/*     */   }
/*     */ 
/*     */   
/*     */   public String format(Object v) {
/*  99 */     return formatValue((T)v);
/*     */   }
/*     */   
/*     */   public String formatValue(T v) {
/* 103 */     return this.wrapped.formatValue(v);
/*     */   }
/*     */   
/*     */   public T parse(String value) {
/* 107 */     return this.wrapped.parse(value);
/*     */   }
/*     */   
/*     */   public T parseDateTime(long systemTimeMillis) {
/* 111 */     return this.wrapped.parseDateTime(systemTimeMillis);
/*     */   }
/*     */   
/*     */   public T toBeanType(Object value) {
/* 115 */     return this.wrapped.toBeanType(value);
/*     */   }
/*     */   
/*     */   public Object toJdbcType(Object value) {
/* 119 */     return this.wrapped.toJdbcType(value);
/*     */   }
/*     */   
/*     */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
/* 123 */     this.wrapped.accumulateScalarTypes(propName, list);
/*     */   }
/*     */   
/*     */   public String jsonToString(T value, JsonValueAdapter ctx) {
/* 127 */     return this.wrapped.jsonToString(value, ctx);
/*     */   }
/*     */   
/*     */   public void jsonWrite(WriteJsonBuffer buffer, T value, JsonValueAdapter ctx) {
/* 131 */     this.wrapped.jsonWrite(buffer, value, ctx);
/*     */   }
/*     */   
/*     */   public T jsonFromString(String value, JsonValueAdapter ctx) {
/* 135 */     return this.wrapped.jsonFromString(value, ctx);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeEncryptedWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */