/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
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
/*     */ public class ScalarTypeLdapDate<T>
/*     */   implements ScalarType<T>
/*     */ {
/*     */   private static final String timestampLDAPFormat = "yyyyMMddHHmmss'Z'";
/*     */   private final ScalarType<T> baseType;
/*     */   
/*     */   public ScalarTypeLdapDate(ScalarType<T> baseType) {
/*  47 */     this.baseType = baseType;
/*     */   }
/*     */   
/*     */   public T toBeanType(Object value) {
/*  51 */     if (value == null) {
/*  52 */       return null;
/*     */     }
/*  54 */     if (!(value instanceof String)) {
/*  55 */       String msg = "Expecting a String type but got " + value.getClass() + " value[" + value + "]";
/*  56 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     try {
/*  59 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
/*  60 */       Date date = sdf.parse((String)value);
/*     */       
/*  62 */       return this.baseType.parseDateTime(date.getTime());
/*     */     }
/*  64 */     catch (Exception e) {
/*  65 */       String msg = "Error parsing LDAP timestamp " + value;
/*  66 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object toJdbcType(Object value) {
/*  72 */     if (value == null) {
/*  73 */       return null;
/*     */     }
/*     */     
/*  76 */     Object ts = this.baseType.toJdbcType(value);
/*  77 */     if (!(ts instanceof Date)) {
/*  78 */       String msg = "Expecting a java.sql.Date type but got " + value.getClass() + " value[" + value + "]";
/*  79 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     
/*  82 */     Date t = (Date)ts;
/*  83 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
/*  84 */     return sdf.format(t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, T value) throws SQLException {
/*  89 */     this.baseType.bind(b, value);
/*     */   }
/*     */   
/*     */   public int getJdbcType() {
/*  93 */     return 12;
/*     */   }
/*     */   
/*     */   public int getLength() {
/*  97 */     return this.baseType.getLength();
/*     */   }
/*     */   
/*     */   public Class<T> getType() {
/* 101 */     return this.baseType.getType();
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/* 105 */     return this.baseType.isDateTimeCapable();
/*     */   }
/*     */   
/*     */   public boolean isJdbcNative() {
/* 109 */     return false;
/*     */   }
/*     */   
/*     */   public void loadIgnore(DataReader dataReader) {
/* 113 */     this.baseType.loadIgnore(dataReader);
/*     */   }
/*     */   
/*     */   public String format(Object v) {
/* 117 */     return this.baseType.format(v);
/*     */   }
/*     */   
/*     */   public String formatValue(T t) {
/* 121 */     return this.baseType.formatValue(t);
/*     */   }
/*     */   
/*     */   public T parse(String value) {
/* 125 */     return this.baseType.parse(value);
/*     */   }
/*     */   
/*     */   public T parseDateTime(long systemTimeMillis) {
/* 129 */     return this.baseType.parseDateTime(systemTimeMillis);
/*     */   }
/*     */   
/*     */   public T read(DataReader dataReader) throws SQLException {
/* 133 */     return this.baseType.read(dataReader);
/*     */   }
/*     */   
/*     */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
/* 137 */     this.baseType.accumulateScalarTypes(propName, list);
/*     */   }
/*     */   
/*     */   public String jsonToString(T value, JsonValueAdapter ctx) {
/* 141 */     return this.baseType.jsonToString(value, ctx);
/*     */   }
/*     */   
/*     */   public void jsonWrite(WriteJsonBuffer buffer, T value, JsonValueAdapter ctx) {
/* 145 */     this.baseType.jsonWrite(buffer, value, ctx);
/*     */   }
/*     */   
/*     */   public T jsonFromString(String value, JsonValueAdapter ctx) {
/* 149 */     return this.baseType.jsonFromString(value, ctx);
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/* 153 */     return this.baseType.readData(dataInput);
/*     */   }
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 157 */     this.baseType.writeData(dataOutput, v);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLdapDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */