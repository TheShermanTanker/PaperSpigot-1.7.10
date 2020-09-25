/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.TextException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalarTypeBoolean
/*     */ {
/*     */   public static class Native
/*     */     extends BooleanBase
/*     */   {
/*     */     public Native() {
/*  46 */       super(true, 16);
/*     */     }
/*     */     
/*     */     public Boolean toBeanType(Object value) {
/*  50 */       return BasicTypeConverter.toBoolean(value);
/*     */     }
/*     */     
/*     */     public Object toJdbcType(Object value) {
/*  54 */       return BasicTypeConverter.convert(value, this.jdbcType);
/*     */     }
/*     */     
/*     */     public void bind(DataBind b, Boolean value) throws SQLException {
/*  58 */       if (value == null) {
/*  59 */         b.setNull(16);
/*     */       } else {
/*  61 */         b.setBoolean(value.booleanValue());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Boolean read(DataReader dataReader) throws SQLException {
/*  67 */       return dataReader.getBoolean();
/*     */     }
/*     */   }
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
/*     */   public static class BitBoolean
/*     */     extends BooleanBase
/*     */   {
/*     */     public BitBoolean() {
/*  85 */       super(true, -7);
/*     */     }
/*     */     
/*     */     public Boolean toBeanType(Object value) {
/*  89 */       return BasicTypeConverter.toBoolean(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object toJdbcType(Object value) {
/*  94 */       return BasicTypeConverter.toBoolean(value);
/*     */     }
/*     */     
/*     */     public void bind(DataBind b, Boolean value) throws SQLException {
/*  98 */       if (value == null) {
/*  99 */         b.setNull(-7);
/*     */       } else {
/*     */         
/* 102 */         b.setBoolean(value.booleanValue());
/*     */       } 
/*     */     }
/*     */     
/*     */     public Boolean read(DataReader dataReader) throws SQLException {
/* 107 */       return dataReader.getBoolean();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class IntBoolean
/*     */     extends BooleanBase
/*     */   {
/*     */     private final Integer trueValue;
/*     */     
/*     */     private final Integer falseValue;
/*     */ 
/*     */     
/*     */     public IntBoolean(Integer trueValue, Integer falseValue) {
/* 121 */       super(false, 4);
/* 122 */       this.trueValue = trueValue;
/* 123 */       this.falseValue = falseValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getLength() {
/* 128 */       return 1;
/*     */     }
/*     */     
/*     */     public void bind(DataBind b, Boolean value) throws SQLException {
/* 132 */       if (value == null) {
/* 133 */         b.setNull(4);
/*     */       } else {
/* 135 */         b.setInt(toInteger(value).intValue());
/*     */       } 
/*     */     }
/*     */     
/*     */     public Boolean read(DataReader dataReader) throws SQLException {
/* 140 */       Integer i = dataReader.getInt();
/* 141 */       if (i == null) {
/* 142 */         return null;
/*     */       }
/* 144 */       if (i.equals(this.trueValue)) {
/* 145 */         return Boolean.TRUE;
/*     */       }
/* 147 */       return Boolean.FALSE;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object toJdbcType(Object value) {
/* 152 */       return toInteger(value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Integer toInteger(Object value) {
/* 159 */       if (value == null) {
/* 160 */         return null;
/*     */       }
/* 162 */       Boolean b = (Boolean)value;
/* 163 */       if (b.booleanValue()) {
/* 164 */         return this.trueValue;
/*     */       }
/* 166 */       return this.falseValue;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Boolean toBeanType(Object value) {
/* 174 */       if (value == null) {
/* 175 */         return null;
/*     */       }
/* 177 */       if (value instanceof Boolean) {
/* 178 */         return (Boolean)value;
/*     */       }
/* 180 */       if (this.trueValue.equals(value)) {
/* 181 */         return Boolean.TRUE;
/*     */       }
/* 183 */       return Boolean.FALSE;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class StringBoolean
/*     */     extends BooleanBase
/*     */   {
/*     */     private final String trueValue;
/*     */     
/*     */     private final String falseValue;
/*     */ 
/*     */     
/*     */     public StringBoolean(String trueValue, String falseValue) {
/* 198 */       super(false, 12);
/* 199 */       this.trueValue = trueValue;
/* 200 */       this.falseValue = falseValue;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getLength() {
/* 206 */       return Math.max(this.trueValue.length(), this.falseValue.length());
/*     */     }
/*     */     
/*     */     public void bind(DataBind b, Boolean value) throws SQLException {
/* 210 */       if (value == null) {
/* 211 */         b.setNull(12);
/*     */       } else {
/* 213 */         b.setString(toString(value));
/*     */       } 
/*     */     }
/*     */     
/*     */     public Boolean read(DataReader dataReader) throws SQLException {
/* 218 */       String string = dataReader.getString();
/* 219 */       if (string == null) {
/* 220 */         return null;
/*     */       }
/*     */       
/* 223 */       if (string.equals(this.trueValue)) {
/* 224 */         return Boolean.TRUE;
/*     */       }
/* 226 */       return Boolean.FALSE;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object toJdbcType(Object value) {
/* 231 */       return toString(value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString(Object value) {
/* 238 */       if (value == null) {
/* 239 */         return null;
/*     */       }
/* 241 */       Boolean b = (Boolean)value;
/* 242 */       if (b.booleanValue()) {
/* 243 */         return this.trueValue;
/*     */       }
/* 245 */       return this.falseValue;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Boolean toBeanType(Object value) {
/* 253 */       if (value == null) {
/* 254 */         return null;
/*     */       }
/* 256 */       if (value instanceof Boolean) {
/* 257 */         return (Boolean)value;
/*     */       }
/* 259 */       if (this.trueValue.equals(value)) {
/* 260 */         return Boolean.TRUE;
/*     */       }
/* 262 */       return Boolean.FALSE;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BooleanBase
/*     */     extends ScalarTypeBase<Boolean>
/*     */   {
/*     */     public BooleanBase(boolean jdbcNative, int jdbcType) {
/* 270 */       super(Boolean.class, jdbcNative, jdbcType);
/*     */     }
/*     */     
/*     */     public String formatValue(Boolean t) {
/* 274 */       return t.toString();
/*     */     }
/*     */     
/*     */     public Boolean parse(String value) {
/* 278 */       return Boolean.valueOf(value);
/*     */     }
/*     */     
/*     */     public Boolean parseDateTime(long systemTimeMillis) {
/* 282 */       throw new TextException("Not Supported");
/*     */     }
/*     */     
/*     */     public boolean isDateTimeCapable() {
/* 286 */       return false;
/*     */     }
/*     */     
/*     */     public Object readData(DataInput dataInput) throws IOException {
/* 290 */       if (!dataInput.readBoolean()) {
/* 291 */         return null;
/*     */       }
/* 293 */       boolean val = dataInput.readBoolean();
/* 294 */       return Boolean.valueOf(val);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 300 */       Boolean val = (Boolean)v;
/* 301 */       if (val == null) {
/* 302 */         dataOutput.writeBoolean(false);
/*     */       } else {
/* 304 */         dataOutput.writeBoolean(true);
/* 305 */         dataOutput.writeBoolean(val.booleanValue());
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBoolean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */