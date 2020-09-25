/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Date;
/*     */ import java.sql.Ref;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
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
/*     */ public class RsetDataReader
/*     */   implements DataReader
/*     */ {
/*     */   private static final int bufferSize = 512;
/*     */   static final int clobBufferSize = 512;
/*     */   static final int stringInitialSize = 512;
/*     */   private final ResultSet rset;
/*     */   protected int pos;
/*     */   
/*     */   public RsetDataReader(ResultSet rset) {
/*  52 */     this.rset = rset;
/*     */   }
/*     */   
/*     */   public void close() throws SQLException {
/*  56 */     this.rset.close();
/*     */   }
/*     */   
/*     */   public boolean next() throws SQLException {
/*  60 */     return this.rset.next();
/*     */   }
/*     */   
/*     */   public void resetColumnPosition() {
/*  64 */     this.pos = 0;
/*     */   }
/*     */   
/*     */   public void incrementPos(int increment) {
/*  68 */     this.pos += increment;
/*     */   }
/*     */   
/*     */   protected int pos() {
/*  72 */     return ++this.pos;
/*     */   }
/*     */   
/*     */   public Array getArray() throws SQLException {
/*  76 */     return this.rset.getArray(pos());
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getAsciiStream() throws SQLException {
/*  81 */     return this.rset.getAsciiStream(pos());
/*     */   }
/*     */   
/*     */   public Object getObject() throws SQLException {
/*  85 */     return this.rset.getObject(pos());
/*     */   }
/*     */   
/*     */   public BigDecimal getBigDecimal() throws SQLException {
/*  89 */     return this.rset.getBigDecimal(pos());
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getBinaryStream() throws SQLException {
/*  94 */     return this.rset.getBinaryStream(pos());
/*     */   }
/*     */   
/*     */   public Boolean getBoolean() throws SQLException {
/*  98 */     boolean v = this.rset.getBoolean(pos());
/*  99 */     if (this.rset.wasNull()) {
/* 100 */       return null;
/*     */     }
/* 102 */     return Boolean.valueOf(v);
/*     */   }
/*     */   
/*     */   public Byte getByte() throws SQLException {
/* 106 */     byte v = this.rset.getByte(pos());
/* 107 */     if (this.rset.wasNull()) {
/* 108 */       return null;
/*     */     }
/* 110 */     return Byte.valueOf(v);
/*     */   }
/*     */   
/*     */   public byte[] getBytes() throws SQLException {
/* 114 */     return this.rset.getBytes(pos());
/*     */   }
/*     */   
/*     */   public Date getDate() throws SQLException {
/* 118 */     return this.rset.getDate(pos());
/*     */   }
/*     */   
/*     */   public Double getDouble() throws SQLException {
/* 122 */     double v = this.rset.getDouble(pos());
/* 123 */     if (this.rset.wasNull()) {
/* 124 */       return null;
/*     */     }
/* 126 */     return Double.valueOf(v);
/*     */   }
/*     */   
/*     */   public Float getFloat() throws SQLException {
/* 130 */     float v = this.rset.getFloat(pos());
/* 131 */     if (this.rset.wasNull()) {
/* 132 */       return null;
/*     */     }
/* 134 */     return Float.valueOf(v);
/*     */   }
/*     */   
/*     */   public Integer getInt() throws SQLException {
/* 138 */     int v = this.rset.getInt(pos());
/* 139 */     if (this.rset.wasNull()) {
/* 140 */       return null;
/*     */     }
/* 142 */     return Integer.valueOf(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public Long getLong() throws SQLException {
/* 147 */     long v = this.rset.getLong(pos());
/* 148 */     if (this.rset.wasNull()) {
/* 149 */       return null;
/*     */     }
/* 151 */     return Long.valueOf(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public Ref getRef() throws SQLException {
/* 156 */     return this.rset.getRef(pos());
/*     */   }
/*     */ 
/*     */   
/*     */   public Short getShort() throws SQLException {
/* 161 */     short s = this.rset.getShort(pos());
/* 162 */     if (this.rset.wasNull()) {
/* 163 */       return null;
/*     */     }
/* 165 */     return Short.valueOf(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() throws SQLException {
/* 170 */     return this.rset.getString(pos());
/*     */   }
/*     */ 
/*     */   
/*     */   public Time getTime() throws SQLException {
/* 175 */     return this.rset.getTime(pos());
/*     */   }
/*     */ 
/*     */   
/*     */   public Timestamp getTimestamp() throws SQLException {
/* 180 */     return this.rset.getTimestamp(pos());
/*     */   }
/*     */   
/*     */   public String getStringFromStream() throws SQLException {
/* 184 */     Reader reader = this.rset.getCharacterStream(pos());
/* 185 */     if (reader == null) {
/* 186 */       return null;
/*     */     }
/* 188 */     return readStringLob(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStringClob() throws SQLException {
/* 193 */     Clob clob = this.rset.getClob(pos());
/* 194 */     if (clob == null) {
/* 195 */       return null;
/*     */     }
/* 197 */     Reader reader = clob.getCharacterStream();
/* 198 */     if (reader == null) {
/* 199 */       return null;
/*     */     }
/* 201 */     return readStringLob(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String readStringLob(Reader reader) throws SQLException {
/* 206 */     char[] buffer = new char[512];
/* 207 */     int readLength = 0;
/* 208 */     StringBuilder out = new StringBuilder(512);
/*     */     try {
/* 210 */       while ((readLength = reader.read(buffer)) != -1) {
/* 211 */         out.append(buffer, 0, readLength);
/*     */       }
/* 213 */       reader.close();
/* 214 */     } catch (IOException e) {
/* 215 */       throw new SQLException(Message.msg("persist.clob.io", e.getMessage()));
/*     */     } 
/*     */     
/* 218 */     return out.toString();
/*     */   }
/*     */   
/*     */   public byte[] getBinaryBytes() throws SQLException {
/* 222 */     InputStream in = this.rset.getBinaryStream(pos());
/* 223 */     return getBinaryLob(in);
/*     */   }
/*     */   
/*     */   public byte[] getBlobBytes() throws SQLException {
/* 227 */     Blob blob = this.rset.getBlob(pos());
/* 228 */     if (blob == null) {
/* 229 */       return null;
/*     */     }
/* 231 */     InputStream in = blob.getBinaryStream();
/* 232 */     return getBinaryLob(in);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] getBinaryLob(InputStream in) throws SQLException {
/*     */     try {
/* 238 */       if (in == null) {
/* 239 */         return null;
/*     */       }
/* 241 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/*     */       
/* 243 */       byte[] buf = new byte[512];
/*     */       int len;
/* 245 */       while ((len = in.read(buf, 0, buf.length)) != -1) {
/* 246 */         out.write(buf, 0, len);
/*     */       }
/* 248 */       byte[] data = out.toByteArray();
/*     */       
/* 250 */       if (data.length == 0) {
/* 251 */         data = null;
/*     */       }
/* 253 */       in.close();
/* 254 */       out.close();
/* 255 */       return data;
/*     */     }
/* 257 */     catch (IOException e) {
/* 258 */       throw new SQLException(e.getClass().getName() + ":" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\RsetDataReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */