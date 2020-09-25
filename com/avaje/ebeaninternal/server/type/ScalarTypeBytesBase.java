/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ScalarTypeBytesBase
/*    */   extends ScalarTypeBase<byte[]>
/*    */ {
/*    */   protected ScalarTypeBytesBase(boolean jdbcNative, int jdbcType) {
/* 35 */     super((Class)byte[].class, jdbcNative, jdbcType);
/*    */   }
/*    */   
/*    */   public Object convertFromBytes(byte[] bytes) {
/* 39 */     return bytes;
/*    */   }
/*    */   
/*    */   public byte[] convertToBytes(Object value) {
/* 43 */     return (byte[])value;
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, byte[] value) throws SQLException {
/* 47 */     if (value == null) {
/* 48 */       b.setNull(this.jdbcType);
/*    */     } else {
/* 50 */       b.setBytes(value);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 55 */     return value;
/*    */   }
/*    */   
/*    */   public byte[] toBeanType(Object value) {
/* 59 */     return (byte[])value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String formatValue(byte[] t) {
/* 64 */     throw new TextException("Not supported");
/*    */   }
/*    */   
/*    */   public byte[] parse(String value) {
/* 68 */     throw new TextException("Not supported");
/*    */   }
/*    */   
/*    */   public byte[] parseDateTime(long systemTimeMillis) {
/* 72 */     throw new TextException("Not supported");
/*    */   }
/*    */   
/*    */   public boolean isDateTimeCapable() {
/* 76 */     return false;
/*    */   }
/*    */   
/*    */   public Object readData(DataInput dataInput) throws IOException {
/* 80 */     if (!dataInput.readBoolean()) {
/* 81 */       return null;
/*    */     }
/* 83 */     int len = dataInput.readInt();
/* 84 */     byte[] buf = new byte[len];
/* 85 */     dataInput.readFully(buf, 0, buf.length);
/* 86 */     return buf;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutput dataOutput, Object v) throws IOException {
/* 91 */     if (v == null) {
/* 92 */       dataOutput.writeBoolean(false);
/*    */     } else {
/* 94 */       byte[] bytes = convertToBytes(v);
/* 95 */       dataOutput.writeInt(bytes.length);
/* 96 */       dataOutput.write(bytes);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBytesBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */