/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCounted;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*     */ final class UnreleasableByteBuf
/*     */   extends ByteBuf
/*     */ {
/*     */   private final ByteBuf buf;
/*     */   private SwappedByteBuf swappedBuf;
/*     */   
/*     */   UnreleasableByteBuf(ByteBuf buf) {
/*  39 */     if (buf == null) {
/*  40 */       throw new NullPointerException("buf");
/*     */     }
/*  42 */     this.buf = buf;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/*  47 */     return this.buf.hasMemoryAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/*  52 */     return this.buf.memoryAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/*  57 */     return this.buf.capacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/*  62 */     this.buf.capacity(newCapacity);
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxCapacity() {
/*  68 */     return this.buf.maxCapacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/*  73 */     return this.buf.alloc();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/*  78 */     return this.buf.order();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf order(ByteOrder endianness) {
/*  83 */     if (endianness == null) {
/*  84 */       throw new NullPointerException("endianness");
/*     */     }
/*  86 */     if (endianness == order()) {
/*  87 */       return this;
/*     */     }
/*     */     
/*  90 */     SwappedByteBuf swappedBuf = this.swappedBuf;
/*  91 */     if (swappedBuf == null) {
/*  92 */       this.swappedBuf = swappedBuf = new SwappedByteBuf(this);
/*     */     }
/*  94 */     return swappedBuf;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf unwrap() {
/*  99 */     return this.buf;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 104 */     return this.buf.isDirect();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readerIndex() {
/* 109 */     return this.buf.readerIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readerIndex(int readerIndex) {
/* 114 */     this.buf.readerIndex(readerIndex);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int writerIndex() {
/* 120 */     return this.buf.writerIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writerIndex(int writerIndex) {
/* 125 */     this.buf.writerIndex(writerIndex);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setIndex(int readerIndex, int writerIndex) {
/* 131 */     this.buf.setIndex(readerIndex, writerIndex);
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readableBytes() {
/* 137 */     return this.buf.readableBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public int writableBytes() {
/* 142 */     return this.buf.writableBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxWritableBytes() {
/* 147 */     return this.buf.maxWritableBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadable() {
/* 152 */     return this.buf.isReadable();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/* 157 */     return this.buf.isWritable();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf clear() {
/* 162 */     this.buf.clear();
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf markReaderIndex() {
/* 168 */     this.buf.markReaderIndex();
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf resetReaderIndex() {
/* 174 */     this.buf.resetReaderIndex();
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf markWriterIndex() {
/* 180 */     this.buf.markWriterIndex();
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf resetWriterIndex() {
/* 186 */     this.buf.resetWriterIndex();
/* 187 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf discardReadBytes() {
/* 192 */     this.buf.discardReadBytes();
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf discardSomeReadBytes() {
/* 198 */     this.buf.discardSomeReadBytes();
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ensureWritable(int minWritableBytes) {
/* 204 */     this.buf.ensureWritable(minWritableBytes);
/* 205 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int ensureWritable(int minWritableBytes, boolean force) {
/* 210 */     return this.buf.ensureWritable(minWritableBytes, force);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(int index) {
/* 215 */     return this.buf.getBoolean(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 220 */     return this.buf.getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getUnsignedByte(int index) {
/* 225 */     return this.buf.getUnsignedByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 230 */     return this.buf.getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedShort(int index) {
/* 235 */     return this.buf.getUnsignedShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMedium(int index) {
/* 240 */     return this.buf.getMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 245 */     return this.buf.getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 250 */     return this.buf.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getUnsignedInt(int index) {
/* 255 */     return this.buf.getUnsignedInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 260 */     return this.buf.getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public char getChar(int index) {
/* 265 */     return this.buf.getChar(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(int index) {
/* 270 */     return this.buf.getFloat(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/* 275 */     return this.buf.getDouble(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst) {
/* 280 */     this.buf.getBytes(index, dst);
/* 281 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/* 286 */     this.buf.getBytes(index, dst, length);
/* 287 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 292 */     this.buf.getBytes(index, dst, dstIndex, length);
/* 293 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst) {
/* 298 */     this.buf.getBytes(index, dst);
/* 299 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 304 */     this.buf.getBytes(index, dst, dstIndex, length);
/* 305 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 310 */     this.buf.getBytes(index, dst);
/* 311 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 316 */     this.buf.getBytes(index, out, length);
/* 317 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 322 */     return this.buf.getBytes(index, out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBoolean(int index, boolean value) {
/* 327 */     this.buf.setBoolean(index, value);
/* 328 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 333 */     this.buf.setByte(index, value);
/* 334 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 339 */     this.buf.setShort(index, value);
/* 340 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 345 */     this.buf.setMedium(index, value);
/* 346 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 351 */     this.buf.setInt(index, value);
/* 352 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 357 */     this.buf.setLong(index, value);
/* 358 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setChar(int index, int value) {
/* 363 */     this.buf.setChar(index, value);
/* 364 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setFloat(int index, float value) {
/* 369 */     this.buf.setFloat(index, value);
/* 370 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setDouble(int index, double value) {
/* 375 */     this.buf.setDouble(index, value);
/* 376 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src) {
/* 381 */     this.buf.setBytes(index, src);
/* 382 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/* 387 */     this.buf.setBytes(index, src, length);
/* 388 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 393 */     this.buf.setBytes(index, src, srcIndex, length);
/* 394 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src) {
/* 399 */     this.buf.setBytes(index, src);
/* 400 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 405 */     this.buf.setBytes(index, src, srcIndex, length);
/* 406 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 411 */     this.buf.setBytes(index, src);
/* 412 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 417 */     return this.buf.setBytes(index, in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 422 */     return this.buf.setBytes(index, in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setZero(int index, int length) {
/* 427 */     this.buf.setZero(index, length);
/* 428 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readBoolean() {
/* 433 */     return this.buf.readBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() {
/* 438 */     return this.buf.readByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readUnsignedByte() {
/* 443 */     return this.buf.readUnsignedByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() {
/* 448 */     return this.buf.readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() {
/* 453 */     return this.buf.readUnsignedShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readMedium() {
/* 458 */     return this.buf.readMedium();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedMedium() {
/* 463 */     return this.buf.readUnsignedMedium();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt() {
/* 468 */     return this.buf.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readUnsignedInt() {
/* 473 */     return this.buf.readUnsignedInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLong() {
/* 478 */     return this.buf.readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public char readChar() {
/* 483 */     return this.buf.readChar();
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() {
/* 488 */     return this.buf.readFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() {
/* 493 */     return this.buf.readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(int length) {
/* 498 */     return this.buf.readBytes(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readSlice(int length) {
/* 503 */     return new UnreleasableByteBuf(this.buf.readSlice(length));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst) {
/* 508 */     this.buf.readBytes(dst);
/* 509 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int length) {
/* 514 */     this.buf.readBytes(dst, length);
/* 515 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/* 520 */     this.buf.readBytes(dst, dstIndex, length);
/* 521 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst) {
/* 526 */     this.buf.readBytes(dst);
/* 527 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 532 */     this.buf.readBytes(dst, dstIndex, length);
/* 533 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 538 */     this.buf.readBytes(dst);
/* 539 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/* 544 */     this.buf.readBytes(out, length);
/* 545 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/* 550 */     return this.buf.readBytes(out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf skipBytes(int length) {
/* 555 */     this.buf.skipBytes(length);
/* 556 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBoolean(boolean value) {
/* 561 */     this.buf.writeBoolean(value);
/* 562 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeByte(int value) {
/* 567 */     this.buf.writeByte(value);
/* 568 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeShort(int value) {
/* 573 */     this.buf.writeShort(value);
/* 574 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeMedium(int value) {
/* 579 */     this.buf.writeMedium(value);
/* 580 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeInt(int value) {
/* 585 */     this.buf.writeInt(value);
/* 586 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeLong(long value) {
/* 591 */     this.buf.writeLong(value);
/* 592 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeChar(int value) {
/* 597 */     this.buf.writeChar(value);
/* 598 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeFloat(float value) {
/* 603 */     this.buf.writeFloat(value);
/* 604 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeDouble(double value) {
/* 609 */     this.buf.writeDouble(value);
/* 610 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src) {
/* 615 */     this.buf.writeBytes(src);
/* 616 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src, int length) {
/* 621 */     this.buf.writeBytes(src, length);
/* 622 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/* 627 */     this.buf.writeBytes(src, srcIndex, length);
/* 628 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(byte[] src) {
/* 633 */     this.buf.writeBytes(src);
/* 634 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/* 639 */     this.buf.writeBytes(src, srcIndex, length);
/* 640 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer src) {
/* 645 */     this.buf.writeBytes(src);
/* 646 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(InputStream in, int length) throws IOException {
/* 651 */     return this.buf.writeBytes(in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
/* 656 */     return this.buf.writeBytes(in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeZero(int length) {
/* 661 */     this.buf.writeZero(length);
/* 662 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(int fromIndex, int toIndex, byte value) {
/* 667 */     return this.buf.indexOf(fromIndex, toIndex, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(byte value) {
/* 672 */     return this.buf.bytesBefore(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(int length, byte value) {
/* 677 */     return this.buf.bytesBefore(length, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(int index, int length, byte value) {
/* 682 */     return this.buf.bytesBefore(index, length, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(ByteBufProcessor processor) {
/* 687 */     return this.buf.forEachByte(processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(int index, int length, ByteBufProcessor processor) {
/* 692 */     return this.buf.forEachByte(index, length, processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor processor) {
/* 697 */     return this.buf.forEachByteDesc(processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(int index, int length, ByteBufProcessor processor) {
/* 702 */     return this.buf.forEachByteDesc(index, length, processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy() {
/* 707 */     return this.buf.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 712 */     return this.buf.copy(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice() {
/* 717 */     return new UnreleasableByteBuf(this.buf.slice());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/* 722 */     return new UnreleasableByteBuf(this.buf.slice(index, length));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/* 727 */     return new UnreleasableByteBuf(this.buf.duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 732 */     return this.buf.nioBufferCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer() {
/* 737 */     return this.buf.nioBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 742 */     return this.buf.nioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers() {
/* 747 */     return this.buf.nioBuffers();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 752 */     return this.buf.nioBuffers(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 757 */     return this.buf.internalNioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 762 */     return this.buf.hasArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 767 */     return this.buf.array();
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 772 */     return this.buf.arrayOffset();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(Charset charset) {
/* 777 */     return this.buf.toString(charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(int index, int length, Charset charset) {
/* 782 */     return this.buf.toString(index, length, charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 787 */     return this.buf.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 792 */     return this.buf.equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ByteBuf buffer) {
/* 797 */     return this.buf.compareTo(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 802 */     return StringUtil.simpleClassName(this) + '(' + this.buf.toString() + ')';
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain(int increment) {
/* 807 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain() {
/* 812 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadable(int size) {
/* 817 */     return this.buf.isReadable(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWritable(int size) {
/* 822 */     return this.buf.isWritable(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/* 827 */     return this.buf.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 832 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 837 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\UnreleasableByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */