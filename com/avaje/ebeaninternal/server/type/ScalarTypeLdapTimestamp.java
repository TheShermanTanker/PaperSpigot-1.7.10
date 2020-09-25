/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class ScalarTypeLdapTimestamp<T>
/*     */   implements ScalarType<T>
/*     */ {
/*     */   private static final String timestampLDAPFormat = "yyyyMMddHHmmss'Z'";
/*     */   private final ScalarType<T> baseType;
/*     */   
/*     */   public ScalarTypeLdapTimestamp(ScalarType<T> baseType) {
/*  48 */     this.baseType = baseType;
/*     */   }
/*     */   
/*     */   public T toBeanType(Object value) {
/*  52 */     if (value == null) {
/*  53 */       return null;
/*     */     }
/*  55 */     if (!(value instanceof String)) {
/*  56 */       String msg = "Expecting a String type but got " + value.getClass() + " value[" + value + "]";
/*  57 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     try {
/*  60 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
/*  61 */       Date date = sdf.parse((String)value);
/*     */       
/*  63 */       return this.baseType.parseDateTime(date.getTime());
/*     */     }
/*  65 */     catch (Exception e) {
/*  66 */       String msg = "Error parsing LDAP timestamp " + value;
/*  67 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object toJdbcType(Object value) {
/*  73 */     if (value == null) {
/*  74 */       return null;
/*     */     }
/*     */     
/*  77 */     Object ts = this.baseType.toJdbcType(value);
/*  78 */     if (!(ts instanceof Timestamp)) {
/*  79 */       String msg = "Expecting a Timestamp type but got " + value.getClass() + " value[" + value + "]";
/*  80 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     
/*  83 */     Timestamp t = (Timestamp)ts;
/*  84 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
/*  85 */     return sdf.format(t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, T value) throws SQLException {
/*  90 */     this.baseType.bind(b, value);
/*     */   }
/*     */   
/*     */   public int getJdbcType() {
/*  94 */     return 12;
/*     */   }
/*     */   
/*     */   public int getLength() {
/*  98 */     return this.baseType.getLength();
/*     */   }
/*     */   
/*     */   public Class<T> getType() {
/* 102 */     return this.baseType.getType();
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/* 106 */     return this.baseType.isDateTimeCapable();
/*     */   }
/*     */   
/*     */   public boolean isJdbcNative() {
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   public void loadIgnore(DataReader dataReader) {
/* 114 */     this.baseType.loadIgnore(dataReader);
/*     */   }
/*     */   
/*     */   public String format(Object v) {
/* 118 */     return this.baseType.format(v);
/*     */   }
/*     */   
/*     */   public String formatValue(T t) {
/* 122 */     return this.baseType.formatValue(t);
/*     */   }
/*     */   
/*     */   public T parse(String value) {
/* 126 */     return this.baseType.parse(value);
/*     */   }
/*     */   
/*     */   public T parseDateTime(long systemTimeMillis) {
/* 130 */     return this.baseType.parseDateTime(systemTimeMillis);
/*     */   }
/*     */   
/*     */   public T read(DataReader dataReader) throws SQLException {
/* 134 */     return this.baseType.read(dataReader);
/*     */   }
/*     */   
/*     */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
/* 138 */     this.baseType.accumulateScalarTypes(propName, list);
/*     */   }
/*     */   
/*     */   public String jsonToString(T value, JsonValueAdapter ctx) {
/* 142 */     return this.baseType.jsonToString(value, ctx);
/*     */   }
/*     */   
/*     */   public void jsonWrite(WriteJsonBuffer buffer, T value, JsonValueAdapter ctx) {
/* 146 */     this.baseType.jsonWrite(buffer, value, ctx);
/*     */   }
/*     */   
/*     */   public T jsonFromString(String value, JsonValueAdapter ctx) {
/* 150 */     return this.baseType.jsonFromString(value, ctx);
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 154 */     return this.baseType.readData(dataInput);
/*     */   }
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 158 */     this.baseType.writeData(dataOutput, v);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLdapTimestamp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */