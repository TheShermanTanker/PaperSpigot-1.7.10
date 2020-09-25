/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class Buffer
/*     */ {
/*     */   static final int MAX_BYTES_TO_DUMP = 512;
/*     */   static final int NO_LENGTH_LIMIT = -1;
/*     */   static final long NULL_LENGTH = -1L;
/*  45 */   private int bufLength = 0;
/*     */   
/*     */   private byte[] byteBuffer;
/*     */   
/*  49 */   private int position = 0;
/*     */   
/*     */   protected boolean wasMultiPacket = false;
/*     */   
/*     */   Buffer(byte[] buf) {
/*  54 */     this.byteBuffer = buf;
/*  55 */     setBufLength(buf.length);
/*     */   }
/*     */   
/*     */   Buffer(int size) {
/*  59 */     this.byteBuffer = new byte[size];
/*  60 */     setBufLength(this.byteBuffer.length);
/*  61 */     this.position = 4;
/*     */   }
/*     */   
/*     */   final void clear() {
/*  65 */     this.position = 4;
/*     */   }
/*     */   
/*     */   final void dump() {
/*  69 */     dump(getBufLength());
/*     */   }
/*     */   
/*     */   final String dump(int numBytes) {
/*  73 */     return StringUtils.dumpAsHex(getBytes(0, (numBytes > getBufLength()) ? getBufLength() : numBytes), (numBytes > getBufLength()) ? getBufLength() : numBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final String dumpClampedBytes(int numBytes) {
/*  79 */     int numBytesToDump = (numBytes < 512) ? numBytes : 512;
/*     */ 
/*     */     
/*  82 */     String dumped = StringUtils.dumpAsHex(getBytes(0, (numBytesToDump > getBufLength()) ? getBufLength() : numBytesToDump), (numBytesToDump > getBufLength()) ? getBufLength() : numBytesToDump);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     if (numBytesToDump < numBytes) {
/*  89 */       return dumped + " ....(packet exceeds max. dump length)";
/*     */     }
/*     */     
/*  92 */     return dumped;
/*     */   }
/*     */   
/*     */   final void dumpHeader() {
/*  96 */     for (int i = 0; i < 4; i++) {
/*  97 */       String hexVal = Integer.toHexString(readByte(i) & 0xFF);
/*     */       
/*  99 */       if (hexVal.length() == 1) {
/* 100 */         hexVal = "0" + hexVal;
/*     */       }
/*     */       
/* 103 */       System.out.print(hexVal + " ");
/*     */     } 
/*     */   }
/*     */   
/*     */   final void dumpNBytes(int start, int nBytes) {
/* 108 */     StringBuffer asciiBuf = new StringBuffer();
/*     */     
/* 110 */     for (int i = start; i < start + nBytes && i < getBufLength(); i++) {
/* 111 */       String hexVal = Integer.toHexString(readByte(i) & 0xFF);
/*     */       
/* 113 */       if (hexVal.length() == 1) {
/* 114 */         hexVal = "0" + hexVal;
/*     */       }
/*     */       
/* 117 */       System.out.print(hexVal + " ");
/*     */       
/* 119 */       if (readByte(i) > 32 && readByte(i) < Byte.MAX_VALUE) {
/* 120 */         asciiBuf.append((char)readByte(i));
/*     */       } else {
/* 122 */         asciiBuf.append(".");
/*     */       } 
/*     */       
/* 125 */       asciiBuf.append(" ");
/*     */     } 
/*     */     
/* 128 */     System.out.println("    " + asciiBuf.toString());
/*     */   }
/*     */   
/*     */   final void ensureCapacity(int additionalData) throws SQLException {
/* 132 */     if (this.position + additionalData > getBufLength()) {
/* 133 */       if (this.position + additionalData < this.byteBuffer.length) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 139 */         setBufLength(this.byteBuffer.length);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 145 */         int newLength = (int)(this.byteBuffer.length * 1.25D);
/*     */         
/* 147 */         if (newLength < this.byteBuffer.length + additionalData) {
/* 148 */           newLength = this.byteBuffer.length + (int)(additionalData * 1.25D);
/*     */         }
/*     */ 
/*     */         
/* 152 */         if (newLength < this.byteBuffer.length) {
/* 153 */           newLength = this.byteBuffer.length + additionalData;
/*     */         }
/*     */         
/* 156 */         byte[] newBytes = new byte[newLength];
/*     */         
/* 158 */         System.arraycopy(this.byteBuffer, 0, newBytes, 0, this.byteBuffer.length);
/*     */         
/* 160 */         this.byteBuffer = newBytes;
/* 161 */         setBufLength(this.byteBuffer.length);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int fastSkipLenString() {
/* 172 */     long len = readFieldLength();
/*     */     
/* 174 */     this.position = (int)(this.position + len);
/*     */     
/* 176 */     return (int)len;
/*     */   }
/*     */   
/*     */   public void fastSkipLenByteArray() {
/* 180 */     long len = readFieldLength();
/*     */     
/* 182 */     if (len == -1L || len == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 186 */     this.position = (int)(this.position + len);
/*     */   }
/*     */   
/*     */   protected final byte[] getBufferSource() {
/* 190 */     return this.byteBuffer;
/*     */   }
/*     */   
/*     */   int getBufLength() {
/* 194 */     return this.bufLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getByteBuffer() {
/* 203 */     return this.byteBuffer;
/*     */   }
/*     */   
/*     */   final byte[] getBytes(int len) {
/* 207 */     byte[] b = new byte[len];
/* 208 */     System.arraycopy(this.byteBuffer, this.position, b, 0, len);
/* 209 */     this.position += len;
/*     */     
/* 211 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getBytes(int offset, int len) {
/* 220 */     byte[] dest = new byte[len];
/* 221 */     System.arraycopy(this.byteBuffer, offset, dest, 0, len);
/*     */     
/* 223 */     return dest;
/*     */   }
/*     */   
/*     */   int getCapacity() {
/* 227 */     return this.byteBuffer.length;
/*     */   }
/*     */   
/*     */   public ByteBuffer getNioBuffer() {
/* 231 */     throw new IllegalArgumentException(Messages.getString("ByteArrayBuffer.0"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPosition() {
/* 241 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   final boolean isLastDataPacket() {
/* 246 */     return (getBufLength() < 9 && (this.byteBuffer[0] & 0xFF) == 254);
/*     */   }
/*     */   
/*     */   final long newReadLength() {
/* 250 */     int sw = this.byteBuffer[this.position++] & 0xFF;
/*     */     
/* 252 */     switch (sw) {
/*     */       case 251:
/* 254 */         return 0L;
/*     */       
/*     */       case 252:
/* 257 */         return readInt();
/*     */       
/*     */       case 253:
/* 260 */         return readLongInt();
/*     */       
/*     */       case 254:
/* 263 */         return readLongLong();
/*     */     } 
/*     */     
/* 266 */     return sw;
/*     */   }
/*     */ 
/*     */   
/*     */   final byte readByte() {
/* 271 */     return this.byteBuffer[this.position++];
/*     */   }
/*     */   
/*     */   final byte readByte(int readAt) {
/* 275 */     return this.byteBuffer[readAt];
/*     */   }
/*     */   
/*     */   final long readFieldLength() {
/* 279 */     int sw = this.byteBuffer[this.position++] & 0xFF;
/*     */     
/* 281 */     switch (sw) {
/*     */       case 251:
/* 283 */         return -1L;
/*     */       
/*     */       case 252:
/* 286 */         return readInt();
/*     */       
/*     */       case 253:
/* 289 */         return readLongInt();
/*     */       
/*     */       case 254:
/* 292 */         return readLongLong();
/*     */     } 
/*     */     
/* 295 */     return sw;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final int readInt() {
/* 301 */     byte[] b = this.byteBuffer;
/*     */     
/* 303 */     return b[this.position++] & 0xFF | (b[this.position++] & 0xFF) << 8;
/*     */   }
/*     */   
/*     */   final int readIntAsLong() {
/* 307 */     byte[] b = this.byteBuffer;
/*     */     
/* 309 */     return b[this.position++] & 0xFF | (b[this.position++] & 0xFF) << 8 | (b[this.position++] & 0xFF) << 16 | (b[this.position++] & 0xFF) << 24;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final byte[] readLenByteArray(int offset) {
/* 315 */     long len = readFieldLength();
/*     */     
/* 317 */     if (len == -1L) {
/* 318 */       return null;
/*     */     }
/*     */     
/* 321 */     if (len == 0L) {
/* 322 */       return Constants.EMPTY_BYTE_ARRAY;
/*     */     }
/*     */     
/* 325 */     this.position += offset;
/*     */     
/* 327 */     return getBytes((int)len);
/*     */   }
/*     */   
/*     */   final long readLength() {
/* 331 */     int sw = this.byteBuffer[this.position++] & 0xFF;
/*     */     
/* 333 */     switch (sw) {
/*     */       case 251:
/* 335 */         return 0L;
/*     */       
/*     */       case 252:
/* 338 */         return readInt();
/*     */       
/*     */       case 253:
/* 341 */         return readLongInt();
/*     */       
/*     */       case 254:
/* 344 */         return readLong();
/*     */     } 
/*     */     
/* 347 */     return sw;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final long readLong() {
/* 353 */     byte[] b = this.byteBuffer;
/*     */     
/* 355 */     return b[this.position++] & 0xFFL | (b[this.position++] & 0xFFL) << 8L | (b[this.position++] & 0xFF) << 16L | (b[this.position++] & 0xFF) << 24L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int readLongInt() {
/* 363 */     byte[] b = this.byteBuffer;
/*     */     
/* 365 */     return b[this.position++] & 0xFF | (b[this.position++] & 0xFF) << 8 | (b[this.position++] & 0xFF) << 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final long readLongLong() {
/* 371 */     byte[] b = this.byteBuffer;
/*     */     
/* 373 */     return (b[this.position++] & 0xFF) | (b[this.position++] & 0xFF) << 8L | (b[this.position++] & 0xFF) << 16L | (b[this.position++] & 0xFF) << 24L | (b[this.position++] & 0xFF) << 32L | (b[this.position++] & 0xFF) << 40L | (b[this.position++] & 0xFF) << 48L | (b[this.position++] & 0xFF) << 56L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int readnBytes() {
/* 384 */     int sw = this.byteBuffer[this.position++] & 0xFF;
/*     */     
/* 386 */     switch (sw) {
/*     */       case 1:
/* 388 */         return this.byteBuffer[this.position++] & 0xFF;
/*     */       
/*     */       case 2:
/* 391 */         return readInt();
/*     */       
/*     */       case 3:
/* 394 */         return readLongInt();
/*     */       
/*     */       case 4:
/* 397 */         return (int)readLong();
/*     */     } 
/*     */     
/* 400 */     return 255;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String readString() {
/* 411 */     int i = this.position;
/* 412 */     int len = 0;
/* 413 */     int maxLen = getBufLength();
/*     */     
/* 415 */     while (i < maxLen && this.byteBuffer[i] != 0) {
/* 416 */       len++;
/* 417 */       i++;
/*     */     } 
/*     */     
/* 420 */     String s = new String(this.byteBuffer, this.position, len);
/* 421 */     this.position += len + 1;
/*     */     
/* 423 */     return s;
/*     */   }
/*     */   
/*     */   final String readString(String encoding, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/* 427 */     int i = this.position;
/* 428 */     int len = 0;
/* 429 */     int maxLen = getBufLength();
/*     */     
/* 431 */     while (i < maxLen && this.byteBuffer[i] != 0) {
/* 432 */       len++;
/* 433 */       i++;
/*     */     } 
/*     */     
/*     */     try {
/* 437 */       return new String(this.byteBuffer, this.position, len, encoding);
/* 438 */     } catch (UnsupportedEncodingException uEE) {
/* 439 */       throw SQLError.createSQLException(Messages.getString("ByteArrayBuffer.1") + encoding + "'", "S1009", exceptionInterceptor);
/*     */     } finally {
/*     */       
/* 442 */       this.position += len + 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   void setBufLength(int bufLengthToSet) {
/* 447 */     this.bufLength = bufLengthToSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteBuffer(byte[] byteBufferToSet) {
/* 457 */     this.byteBuffer = byteBufferToSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(int positionToSet) {
/* 467 */     this.position = positionToSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWasMultiPacket(boolean flag) {
/* 477 */     this.wasMultiPacket = flag;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 481 */     return dumpClampedBytes(getPosition());
/*     */   }
/*     */   
/*     */   public String toSuperString() {
/* 485 */     return super.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wasMultiPacket() {
/* 494 */     return this.wasMultiPacket;
/*     */   }
/*     */   
/*     */   final void writeByte(byte b) throws SQLException {
/* 498 */     ensureCapacity(1);
/*     */     
/* 500 */     this.byteBuffer[this.position++] = b;
/*     */   }
/*     */ 
/*     */   
/*     */   final void writeBytesNoNull(byte[] bytes) throws SQLException {
/* 505 */     int len = bytes.length;
/* 506 */     ensureCapacity(len);
/* 507 */     System.arraycopy(bytes, 0, this.byteBuffer, this.position, len);
/* 508 */     this.position += len;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final void writeBytesNoNull(byte[] bytes, int offset, int length) throws SQLException {
/* 514 */     ensureCapacity(length);
/* 515 */     System.arraycopy(bytes, offset, this.byteBuffer, this.position, length);
/* 516 */     this.position += length;
/*     */   }
/*     */   
/*     */   final void writeDouble(double d) throws SQLException {
/* 520 */     long l = Double.doubleToLongBits(d);
/* 521 */     writeLongLong(l);
/*     */   }
/*     */   
/*     */   final void writeFieldLength(long length) throws SQLException {
/* 525 */     if (length < 251L) {
/* 526 */       writeByte((byte)(int)length);
/* 527 */     } else if (length < 65536L) {
/* 528 */       ensureCapacity(3);
/* 529 */       writeByte((byte)-4);
/* 530 */       writeInt((int)length);
/* 531 */     } else if (length < 16777216L) {
/* 532 */       ensureCapacity(4);
/* 533 */       writeByte((byte)-3);
/* 534 */       writeLongInt((int)length);
/*     */     } else {
/* 536 */       ensureCapacity(9);
/* 537 */       writeByte((byte)-2);
/* 538 */       writeLongLong(length);
/*     */     } 
/*     */   }
/*     */   
/*     */   final void writeFloat(float f) throws SQLException {
/* 543 */     ensureCapacity(4);
/*     */     
/* 545 */     int i = Float.floatToIntBits(f);
/* 546 */     byte[] b = this.byteBuffer;
/* 547 */     b[this.position++] = (byte)(i & 0xFF);
/* 548 */     b[this.position++] = (byte)(i >>> 8);
/* 549 */     b[this.position++] = (byte)(i >>> 16);
/* 550 */     b[this.position++] = (byte)(i >>> 24);
/*     */   }
/*     */ 
/*     */   
/*     */   final void writeInt(int i) throws SQLException {
/* 555 */     ensureCapacity(2);
/*     */     
/* 557 */     byte[] b = this.byteBuffer;
/* 558 */     b[this.position++] = (byte)(i & 0xFF);
/* 559 */     b[this.position++] = (byte)(i >>> 8);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final void writeLenBytes(byte[] b) throws SQLException {
/* 565 */     int len = b.length;
/* 566 */     ensureCapacity(len + 9);
/* 567 */     writeFieldLength(len);
/* 568 */     System.arraycopy(b, 0, this.byteBuffer, this.position, len);
/* 569 */     this.position += len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void writeLenString(String s, String encoding, String serverEncoding, SingleByteCharsetConverter converter, boolean parserKnowsUnicode, MySQLConnection conn) throws UnsupportedEncodingException, SQLException {
/* 578 */     byte[] b = null;
/*     */     
/* 580 */     if (converter != null) {
/* 581 */       b = converter.toBytes(s);
/*     */     } else {
/* 583 */       b = StringUtils.getBytes(s, encoding, serverEncoding, parserKnowsUnicode, conn, conn.getExceptionInterceptor());
/*     */     } 
/*     */ 
/*     */     
/* 587 */     int len = b.length;
/* 588 */     ensureCapacity(len + 9);
/* 589 */     writeFieldLength(len);
/* 590 */     System.arraycopy(b, 0, this.byteBuffer, this.position, len);
/* 591 */     this.position += len;
/*     */   }
/*     */ 
/*     */   
/*     */   final void writeLong(long i) throws SQLException {
/* 596 */     ensureCapacity(4);
/*     */     
/* 598 */     byte[] b = this.byteBuffer;
/* 599 */     b[this.position++] = (byte)(int)(i & 0xFFL);
/* 600 */     b[this.position++] = (byte)(int)(i >>> 8L);
/* 601 */     b[this.position++] = (byte)(int)(i >>> 16L);
/* 602 */     b[this.position++] = (byte)(int)(i >>> 24L);
/*     */   }
/*     */ 
/*     */   
/*     */   final void writeLongInt(int i) throws SQLException {
/* 607 */     ensureCapacity(3);
/* 608 */     byte[] b = this.byteBuffer;
/* 609 */     b[this.position++] = (byte)(i & 0xFF);
/* 610 */     b[this.position++] = (byte)(i >>> 8);
/* 611 */     b[this.position++] = (byte)(i >>> 16);
/*     */   }
/*     */   
/*     */   final void writeLongLong(long i) throws SQLException {
/* 615 */     ensureCapacity(8);
/* 616 */     byte[] b = this.byteBuffer;
/* 617 */     b[this.position++] = (byte)(int)(i & 0xFFL);
/* 618 */     b[this.position++] = (byte)(int)(i >>> 8L);
/* 619 */     b[this.position++] = (byte)(int)(i >>> 16L);
/* 620 */     b[this.position++] = (byte)(int)(i >>> 24L);
/* 621 */     b[this.position++] = (byte)(int)(i >>> 32L);
/* 622 */     b[this.position++] = (byte)(int)(i >>> 40L);
/* 623 */     b[this.position++] = (byte)(int)(i >>> 48L);
/* 624 */     b[this.position++] = (byte)(int)(i >>> 56L);
/*     */   }
/*     */ 
/*     */   
/*     */   final void writeString(String s) throws SQLException {
/* 629 */     ensureCapacity(s.length() * 2 + 1);
/* 630 */     writeStringNoNull(s);
/* 631 */     this.byteBuffer[this.position++] = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   final void writeString(String s, String encoding, MySQLConnection conn) throws SQLException {
/* 636 */     ensureCapacity(s.length() * 2 + 1);
/*     */     try {
/* 638 */       writeStringNoNull(s, encoding, encoding, false, conn);
/* 639 */     } catch (UnsupportedEncodingException ue) {
/* 640 */       throw new SQLException(ue.toString(), "S1000");
/*     */     } 
/*     */     
/* 643 */     this.byteBuffer[this.position++] = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   final void writeStringNoNull(String s) throws SQLException {
/* 648 */     int len = s.length();
/* 649 */     ensureCapacity(len * 2);
/* 650 */     System.arraycopy(s.getBytes(), 0, this.byteBuffer, this.position, len);
/* 651 */     this.position += len;
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
/*     */   final void writeStringNoNull(String s, String encoding, String serverEncoding, boolean parserKnowsUnicode, MySQLConnection conn) throws UnsupportedEncodingException, SQLException {
/* 664 */     byte[] b = StringUtils.getBytes(s, encoding, serverEncoding, parserKnowsUnicode, conn, conn.getExceptionInterceptor());
/*     */ 
/*     */     
/* 667 */     int len = b.length;
/* 668 */     ensureCapacity(len);
/* 669 */     System.arraycopy(b, 0, this.byteBuffer, this.position, len);
/* 670 */     this.position += len;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\Buffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */