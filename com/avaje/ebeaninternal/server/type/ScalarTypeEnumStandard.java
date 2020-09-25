/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.TextException;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.EnumSet;
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
/*     */ 
/*     */ public class ScalarTypeEnumStandard
/*     */ {
/*     */   public static class StringEnum
/*     */     extends EnumBase
/*     */     implements ScalarTypeEnum
/*     */   {
/*     */     private final int length;
/*     */     
/*     */     public StringEnum(Class<?> enumType) {
/*  55 */       super(enumType, false, 12);
/*  56 */       this.length = maxValueLength(enumType);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getContraintInValues() {
/*  64 */       StringBuilder sb = new StringBuilder();
/*     */       
/*  66 */       sb.append("(");
/*  67 */       Object[] ea = this.enumType.getEnumConstants();
/*  68 */       for (int i = 0; i < ea.length; i++) {
/*  69 */         Enum<?> e = (Enum)ea[i];
/*  70 */         if (i > 0) {
/*  71 */           sb.append(",");
/*     */         }
/*  73 */         sb.append("'").append(e.toString()).append("'");
/*     */       } 
/*  75 */       sb.append(")");
/*     */       
/*  77 */       return sb.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     private int maxValueLength(Class<?> enumType) {
/*  82 */       int maxLen = 0;
/*     */       
/*  84 */       Object[] ea = enumType.getEnumConstants();
/*  85 */       for (int i = 0; i < ea.length; i++) {
/*  86 */         Enum<?> e = (Enum)ea[i];
/*  87 */         maxLen = Math.max(maxLen, e.toString().length());
/*     */       } 
/*     */       
/*  90 */       return maxLen;
/*     */     }
/*     */     
/*     */     public int getLength() {
/*  94 */       return this.length;
/*     */     }
/*     */     
/*     */     public void bind(DataBind b, Object value) throws SQLException {
/*  98 */       if (value == null) {
/*  99 */         b.setNull(12);
/*     */       } else {
/* 101 */         b.setString(value.toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object read(DataReader dataReader) throws SQLException {
/* 107 */       String string = dataReader.getString();
/* 108 */       if (string == null) {
/* 109 */         return null;
/*     */       }
/* 111 */       return Enum.valueOf(this.enumType, string);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object toJdbcType(Object beanValue) {
/* 119 */       if (beanValue == null) {
/* 120 */         return null;
/*     */       }
/* 122 */       Enum<?> e = (Enum)beanValue;
/* 123 */       return e.toString();
/*     */     }
/*     */     
/*     */     public Object toBeanType(Object dbValue) {
/* 127 */       if (dbValue == null) {
/* 128 */         return null;
/*     */       }
/*     */       
/* 131 */       return Enum.valueOf(this.enumType, (String)dbValue);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class OrdinalEnum
/*     */     extends EnumBase
/*     */     implements ScalarTypeEnum
/*     */   {
/*     */     private final Object[] enumArray;
/*     */ 
/*     */     
/*     */     public OrdinalEnum(Class<?> enumType) {
/* 145 */       super(enumType, false, 4);
/* 146 */       this.enumArray = EnumSet.allOf(enumType).toArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getContraintInValues() {
/* 154 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 156 */       sb.append("(");
/* 157 */       for (int i = 0; i < this.enumArray.length; i++) {
/* 158 */         Enum<?> e = (Enum)this.enumArray[i];
/* 159 */         if (i > 0) {
/* 160 */           sb.append(",");
/*     */         }
/* 162 */         sb.append(e.ordinal());
/*     */       } 
/* 164 */       sb.append(")");
/*     */       
/* 166 */       return sb.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public void bind(DataBind b, Object value) throws SQLException {
/* 171 */       if (value == null) {
/* 172 */         b.setNull(4);
/*     */       } else {
/* 174 */         Enum<?> e = (Enum)value;
/* 175 */         b.setInt(e.ordinal());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object read(DataReader dataReader) throws SQLException {
/* 181 */       Integer ordinal = dataReader.getInt();
/* 182 */       if (ordinal == null) {
/* 183 */         return null;
/*     */       }
/* 185 */       if (ordinal.intValue() < 0 || ordinal.intValue() >= this.enumArray.length) {
/* 186 */         String m = "Unexpected ordinal [" + ordinal + "] out of range [" + this.enumArray.length + "]";
/* 187 */         throw new IllegalStateException(m);
/*     */       } 
/* 189 */       return this.enumArray[ordinal.intValue()];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object toJdbcType(Object beanValue) {
/* 197 */       if (beanValue == null) {
/* 198 */         return null;
/*     */       }
/* 200 */       Enum e = (Enum)beanValue;
/* 201 */       return Integer.valueOf(e.ordinal());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object toBeanType(Object dbValue) {
/* 208 */       if (dbValue == null) {
/* 209 */         return null;
/*     */       }
/*     */       
/* 212 */       int ordinal = ((Integer)dbValue).intValue();
/* 213 */       if (ordinal < 0 || ordinal >= this.enumArray.length) {
/* 214 */         String m = "Unexpected ordinal [" + ordinal + "] out of range [" + this.enumArray.length + "]";
/* 215 */         throw new IllegalStateException(m);
/*     */       } 
/* 217 */       return this.enumArray[ordinal];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class EnumBase
/*     */     extends ScalarTypeBase
/*     */   {
/*     */     protected final Class enumType;
/*     */     
/*     */     public EnumBase(Class<?> type, boolean jdbcNative, int jdbcType) {
/* 228 */       super((Class)type, jdbcNative, jdbcType);
/* 229 */       this.enumType = type;
/*     */     }
/*     */     
/*     */     public String format(Object t) {
/* 233 */       return t.toString();
/*     */     }
/*     */     
/*     */     public String formatValue(Object t) {
/* 237 */       return t.toString();
/*     */     }
/*     */     
/*     */     public Object parse(String value) {
/* 241 */       return Enum.valueOf(this.enumType, value);
/*     */     }
/*     */     
/*     */     public Object parseDateTime(long systemTimeMillis) {
/* 245 */       throw new TextException("Not Supported");
/*     */     }
/*     */     
/*     */     public boolean isDateTimeCapable() {
/* 249 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object jsonFromString(String value, JsonValueAdapter ctx) {
/* 254 */       return parse(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public String jsonToString(Object value, JsonValueAdapter ctx) {
/* 259 */       return EscapeJson.escapeQuote(value.toString());
/*     */     }
/*     */     
/*     */     public Object readData(DataInput dataInput) throws IOException {
/* 263 */       if (!dataInput.readBoolean()) {
/* 264 */         return null;
/*     */       }
/* 266 */       String s = dataInput.readUTF();
/* 267 */       return parse(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 272 */       if (v == null) {
/* 273 */         dataOutput.writeBoolean(false);
/*     */       } else {
/* 275 */         dataOutput.writeBoolean(true);
/* 276 */         dataOutput.writeUTF(format(v));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeEnumStandard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */