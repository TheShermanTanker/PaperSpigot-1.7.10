/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalarTypeWrapper<B, S>
/*     */   implements ScalarType<B>
/*     */ {
/*     */   private final ScalarType<S> scalarType;
/*     */   private final ScalarTypeConverter<B, S> converter;
/*     */   private final Class<B> wrapperType;
/*     */   private final B nullValue;
/*     */   
/*     */   public ScalarTypeWrapper(Class<B> wrapperType, ScalarType<S> scalarType, ScalarTypeConverter<B, S> converter) {
/*  54 */     this.scalarType = scalarType;
/*  55 */     this.converter = converter;
/*  56 */     this.nullValue = (B)converter.getNullValue();
/*  57 */     this.wrapperType = wrapperType;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  61 */     return "ScalarTypeWrapper " + this.wrapperType + " to " + this.scalarType.getType();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readData(DataInput dataInput) throws IOException {
/*  66 */     Object v = this.scalarType.readData(dataInput);
/*  67 */     return this.converter.wrapValue(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/*  72 */     S sv = (S)this.converter.unwrapValue(v);
/*  73 */     this.scalarType.writeData(dataOutput, sv);
/*     */   }
/*     */   
/*     */   public void bind(DataBind b, B value) throws SQLException {
/*  77 */     if (value == null) {
/*  78 */       this.scalarType.bind(b, null);
/*     */     } else {
/*  80 */       S sv = (S)this.converter.unwrapValue(value);
/*  81 */       this.scalarType.bind(b, sv);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getJdbcType() {
/*  86 */     return this.scalarType.getJdbcType();
/*     */   }
/*     */   
/*     */   public int getLength() {
/*  90 */     return this.scalarType.getLength();
/*     */   }
/*     */   
/*     */   public Class<B> getType() {
/*  94 */     return this.wrapperType;
/*     */   }
/*     */   
/*     */   public boolean isDateTimeCapable() {
/*  98 */     return this.scalarType.isDateTimeCapable();
/*     */   }
/*     */   
/*     */   public boolean isJdbcNative() {
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String format(Object v) {
/* 107 */     return formatValue((B)v);
/*     */   }
/*     */   
/*     */   public String formatValue(B v) {
/* 111 */     S sv = (S)this.converter.unwrapValue(v);
/* 112 */     return this.scalarType.formatValue(sv);
/*     */   }
/*     */   
/*     */   public B parse(String value) {
/* 116 */     S sv = this.scalarType.parse(value);
/* 117 */     if (sv == null) {
/* 118 */       return this.nullValue;
/*     */     }
/* 120 */     return (B)this.converter.wrapValue(sv);
/*     */   }
/*     */   
/*     */   public B parseDateTime(long systemTimeMillis) {
/* 124 */     S sv = this.scalarType.parseDateTime(systemTimeMillis);
/* 125 */     if (sv == null) {
/* 126 */       return this.nullValue;
/*     */     }
/* 128 */     return (B)this.converter.wrapValue(sv);
/*     */   }
/*     */   
/*     */   public void loadIgnore(DataReader dataReader) {
/* 132 */     dataReader.incrementPos(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public B read(DataReader dataReader) throws SQLException {
/* 137 */     S sv = this.scalarType.read(dataReader);
/* 138 */     if (sv == null) {
/* 139 */       return this.nullValue;
/*     */     }
/* 141 */     return (B)this.converter.wrapValue(sv);
/*     */   }
/*     */ 
/*     */   
/*     */   public B toBeanType(Object value) {
/* 146 */     if (value == null) {
/* 147 */       return this.nullValue;
/*     */     }
/* 149 */     if (getType().isAssignableFrom(value.getClass())) {
/* 150 */       return (B)value;
/*     */     }
/* 152 */     if (value instanceof String) {
/* 153 */       return parse((String)value);
/*     */     }
/* 155 */     S sv = this.scalarType.toBeanType(value);
/* 156 */     return (B)this.converter.wrapValue(sv);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object toJdbcType(Object value) {
/* 162 */     Object sv = this.converter.unwrapValue(value);
/* 163 */     if (sv == null) {
/* 164 */       return this.nullValue;
/*     */     }
/* 166 */     return this.scalarType.toJdbcType(sv);
/*     */   }
/*     */   
/*     */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
/* 170 */     list.addScalarType(propName, this);
/*     */   }
/*     */   
/*     */   public ScalarType<?> getScalarType() {
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String jsonToString(B value, JsonValueAdapter ctx) {
/* 179 */     S sv = (S)this.converter.unwrapValue(value);
/* 180 */     return this.scalarType.jsonToString(sv, ctx);
/*     */   }
/*     */   
/*     */   public void jsonWrite(WriteJsonBuffer buffer, B value, JsonValueAdapter ctx) {
/* 184 */     S sv = (S)this.converter.unwrapValue(value);
/* 185 */     this.scalarType.jsonWrite(buffer, sv, ctx);
/*     */   }
/*     */   
/*     */   public B jsonFromString(String value, JsonValueAdapter ctx) {
/* 189 */     S s = this.scalarType.jsonFromString(value, ctx);
/* 190 */     return (B)this.converter.wrapValue(s);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */