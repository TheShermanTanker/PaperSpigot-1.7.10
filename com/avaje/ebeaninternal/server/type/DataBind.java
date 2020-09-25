/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
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
/*     */ public class DataBind
/*     */ {
/*     */   private final PreparedStatement pstmt;
/*     */   private int pos;
/*     */   
/*     */   public DataBind(PreparedStatement pstmt) {
/*  38 */     this.pstmt = pstmt;
/*     */   }
/*     */   
/*     */   public void close() throws SQLException {
/*  42 */     this.pstmt.close();
/*     */   }
/*     */   
/*     */   public int currentPos() {
/*  46 */     return this.pos;
/*     */   }
/*     */   
/*     */   public void resetPos() {
/*  50 */     this.pos = 0;
/*     */   }
/*     */   
/*     */   public void setObject(Object value) throws SQLException {
/*  54 */     this.pstmt.setObject(++this.pos, value);
/*     */   }
/*     */   
/*     */   public void setObject(Object value, int sqlType) throws SQLException {
/*  58 */     this.pstmt.setObject(++this.pos, value, sqlType);
/*     */   }
/*     */   
/*     */   public void setNull(int jdbcType) throws SQLException {
/*  62 */     this.pstmt.setNull(++this.pos, jdbcType);
/*     */   }
/*     */   
/*     */   public int nextPos() {
/*  66 */     return ++this.pos;
/*     */   }
/*     */   
/*     */   public int decrementPos() {
/*  70 */     return ++this.pos;
/*     */   }
/*     */   
/*     */   public int executeUpdate() throws SQLException {
/*  74 */     return this.pstmt.executeUpdate();
/*     */   }
/*     */   
/*     */   public PreparedStatement getPstmt() {
/*  78 */     return this.pstmt;
/*     */   }
/*     */   
/*     */   public void setString(String s) throws SQLException {
/*  82 */     this.pstmt.setString(++this.pos, s);
/*     */   }
/*     */   
/*     */   public void setInt(int i) throws SQLException {
/*  86 */     this.pstmt.setInt(++this.pos, i);
/*     */   }
/*     */   
/*     */   public void setLong(long i) throws SQLException {
/*  90 */     this.pstmt.setLong(++this.pos, i);
/*     */   }
/*     */   
/*     */   public void setShort(short i) throws SQLException {
/*  94 */     this.pstmt.setShort(++this.pos, i);
/*     */   }
/*     */   
/*     */   public void setFloat(float i) throws SQLException {
/*  98 */     this.pstmt.setFloat(++this.pos, i);
/*     */   }
/*     */   
/*     */   public void setDouble(double i) throws SQLException {
/* 102 */     this.pstmt.setDouble(++this.pos, i);
/*     */   }
/*     */   
/*     */   public void setBigDecimal(BigDecimal v) throws SQLException {
/* 106 */     this.pstmt.setBigDecimal(++this.pos, v);
/*     */   }
/*     */   
/*     */   public void setDate(Date v) throws SQLException {
/* 110 */     this.pstmt.setDate(++this.pos, v);
/*     */   }
/*     */   
/*     */   public void setTimestamp(Timestamp v) throws SQLException {
/* 114 */     this.pstmt.setTimestamp(++this.pos, v);
/*     */   }
/*     */   
/*     */   public void setTime(Time v) throws SQLException {
/* 118 */     this.pstmt.setTime(++this.pos, v);
/*     */   }
/*     */   
/*     */   public void setBoolean(boolean v) throws SQLException {
/* 122 */     this.pstmt.setBoolean(++this.pos, v);
/*     */   }
/*     */   
/*     */   public void setBytes(byte[] v) throws SQLException {
/* 126 */     this.pstmt.setBytes(++this.pos, v);
/*     */   }
/*     */   
/*     */   public void setByte(byte v) throws SQLException {
/* 130 */     this.pstmt.setByte(++this.pos, v);
/*     */   }
/*     */   
/*     */   public void setChar(char v) throws SQLException {
/* 134 */     this.pstmt.setString(++this.pos, String.valueOf(v));
/*     */   }
/*     */   
/*     */   public void setBlob(byte[] bytes) throws SQLException {
/* 138 */     ByteArrayInputStream is = new ByteArrayInputStream(bytes);
/* 139 */     this.pstmt.setBinaryStream(++this.pos, is, bytes.length);
/*     */   }
/*     */   
/*     */   public void setClob(String content) throws SQLException {
/* 143 */     Reader reader = new StringReader(content);
/* 144 */     this.pstmt.setCharacterStream(++this.pos, reader, content.length());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\DataBind.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */