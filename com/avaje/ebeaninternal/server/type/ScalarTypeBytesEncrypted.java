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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalarTypeBytesEncrypted
/*     */   implements ScalarType<byte[]>
/*     */ {
/*     */   private final ScalarTypeBytesBase baseType;
/*     */   private final DataEncryptSupport dataEncryptSupport;
/*     */   
/*     */   public ScalarTypeBytesEncrypted(ScalarTypeBytesBase baseType, DataEncryptSupport dataEncryptSupport) {
/*  44 */     this.baseType = baseType;
/*  45 */     this.dataEncryptSupport = dataEncryptSupport;
/*     */   }
/*     */   
/*     */   public void bind(DataBind b, byte[] value) throws SQLException {
/*  49 */     value = this.dataEncryptSupport.encrypt(value);
/*  50 */     this.baseType.bind(b, value);
/*     */   }
/*     */   
/*     */   public int getJdbcType() {
/*  54 */     return this.baseType.getJdbcType();
/*     */   }
/*     */   
/*     */   public int getLength() {
/*  58 */     return this.baseType.getLength();
/*     */   }
/*     */   
/*     */   public Class<byte[]> getType() {
/*  62 */     return byte[].class;
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/*  66 */     return this.baseType.isDateTimeCapable();
/*     */   }
/*     */   
/*     */   public boolean isJdbcNative() {
/*  70 */     return this.baseType.isJdbcNative();
/*     */   }
/*     */   
/*     */   public void loadIgnore(DataReader dataReader) {
/*  74 */     this.baseType.loadIgnore(dataReader);
/*     */   }
/*     */   
/*     */   public String format(Object v) {
/*  78 */     throw new RuntimeException("Not used");
/*     */   }
/*     */   
/*     */   public String formatValue(byte[] v) {
/*  82 */     throw new RuntimeException("Not used");
/*     */   }
/*     */   
/*     */   public byte[] parse(String value) {
/*  86 */     return this.baseType.parse(value);
/*     */   }
/*     */   
/*     */   public byte[] parseDateTime(long systemTimeMillis) {
/*  90 */     return this.baseType.parseDateTime(systemTimeMillis);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] read(DataReader dataReader) throws SQLException {
/*  95 */     byte[] data = this.baseType.read(dataReader);
/*  96 */     data = this.dataEncryptSupport.decrypt(data);
/*  97 */     return data;
/*     */   }
/*     */   
/*     */   public byte[] toBeanType(Object value) {
/* 101 */     return this.baseType.toBeanType(value);
/*     */   }
/*     */   
/*     */   public Object toJdbcType(Object value) {
/* 105 */     return this.baseType.toJdbcType(value);
/*     */   }
/*     */   
/*     */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
/* 109 */     this.baseType.accumulateScalarTypes(propName, list);
/*     */   }
/*     */   
/*     */   public void jsonWrite(WriteJsonBuffer buffer, byte[] value, JsonValueAdapter ctx) {
/* 113 */     this.baseType.jsonWrite(buffer, value, ctx);
/*     */   }
/*     */   
/*     */   public String jsonToString(byte[] value, JsonValueAdapter ctx) {
/* 117 */     return this.baseType.jsonToString(value, ctx);
/*     */   }
/*     */   
/*     */   public byte[] jsonFromString(String value, JsonValueAdapter ctx) {
/* 121 */     return this.baseType.jsonFromString(value, ctx);
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 125 */     int len = dataInput.readInt();
/* 126 */     byte[] value = new byte[len];
/* 127 */     dataInput.readFully(value);
/* 128 */     return value;
/*     */   }
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 132 */     byte[] value = (byte[])v;
/* 133 */     dataOutput.writeInt(value.length);
/* 134 */     dataOutput.write(value);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBytesEncrypted.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */