/*     */ package net.minecraft.util.io.netty.handler.codec;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufProcessor;
/*     */ import net.minecraft.util.io.netty.buffer.SwappedByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCounted;
/*     */ import net.minecraft.util.io.netty.util.Signal;
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
/*     */ final class ReplayingDecoderBuffer
/*     */   extends ByteBuf
/*     */ {
/*  38 */   private static final Signal REPLAY = ReplayingDecoder.REPLAY;
/*     */   
/*     */   private ByteBuf buffer;
/*     */   
/*     */   private boolean terminated;
/*     */   private SwappedByteBuf swapped;
/*  44 */   static final ReplayingDecoderBuffer EMPTY_BUFFER = new ReplayingDecoderBuffer(Unpooled.EMPTY_BUFFER);
/*     */   
/*     */   static {
/*  47 */     EMPTY_BUFFER.terminate();
/*     */   }
/*     */   
/*     */   ReplayingDecoderBuffer() {}
/*     */   
/*     */   ReplayingDecoderBuffer(ByteBuf buffer) {
/*  53 */     setCumulation(buffer);
/*     */   }
/*     */   
/*     */   void setCumulation(ByteBuf buffer) {
/*  57 */     this.buffer = buffer;
/*     */   }
/*     */   
/*     */   void terminate() {
/*  61 */     this.terminated = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/*  66 */     if (this.terminated) {
/*  67 */       return this.buffer.capacity();
/*     */     }
/*  69 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/*  75 */     reject();
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxCapacity() {
/*  81 */     return capacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/*  86 */     return this.buffer.alloc();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/*  91 */     return this.buffer.isDirect();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 106 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 116 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf clear() {
/* 121 */     reject();
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 127 */     return (this == obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ByteBuf buffer) {
/* 132 */     reject();
/* 133 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy() {
/* 138 */     reject();
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 144 */     checkIndex(index, length);
/* 145 */     return this.buffer.copy(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf discardReadBytes() {
/* 150 */     reject();
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ensureWritable(int writableBytes) {
/* 156 */     reject();
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int ensureWritable(int minWritableBytes, boolean force) {
/* 162 */     reject();
/* 163 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/* 168 */     reject();
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(int index) {
/* 174 */     checkIndex(index, 1);
/* 175 */     return this.buffer.getBoolean(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 180 */     checkIndex(index, 1);
/* 181 */     return this.buffer.getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getUnsignedByte(int index) {
/* 186 */     checkIndex(index, 1);
/* 187 */     return this.buffer.getUnsignedByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 192 */     checkIndex(index, length);
/* 193 */     this.buffer.getBytes(index, dst, dstIndex, length);
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst) {
/* 199 */     checkIndex(index, dst.length);
/* 200 */     this.buffer.getBytes(index, dst);
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 206 */     reject();
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 212 */     checkIndex(index, length);
/* 213 */     this.buffer.getBytes(index, dst, dstIndex, length);
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/* 219 */     reject();
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst) {
/* 225 */     reject();
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) {
/* 231 */     reject();
/* 232 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) {
/* 237 */     reject();
/* 238 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 243 */     checkIndex(index, 4);
/* 244 */     return this.buffer.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getUnsignedInt(int index) {
/* 249 */     checkIndex(index, 4);
/* 250 */     return this.buffer.getUnsignedInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 255 */     checkIndex(index, 8);
/* 256 */     return this.buffer.getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMedium(int index) {
/* 261 */     checkIndex(index, 3);
/* 262 */     return this.buffer.getMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 267 */     checkIndex(index, 3);
/* 268 */     return this.buffer.getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 273 */     checkIndex(index, 2);
/* 274 */     return this.buffer.getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedShort(int index) {
/* 279 */     checkIndex(index, 2);
/* 280 */     return this.buffer.getUnsignedShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public char getChar(int index) {
/* 285 */     checkIndex(index, 2);
/* 286 */     return this.buffer.getChar(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(int index) {
/* 291 */     checkIndex(index, 4);
/* 292 */     return this.buffer.getFloat(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/* 297 */     checkIndex(index, 8);
/* 298 */     return this.buffer.getDouble(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 303 */     reject();
/* 304 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(int fromIndex, int toIndex, byte value) {
/* 309 */     int endIndex = this.buffer.indexOf(fromIndex, toIndex, value);
/* 310 */     if (endIndex < 0) {
/* 311 */       throw REPLAY;
/*     */     }
/* 313 */     return endIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(byte value) {
/* 318 */     int bytes = this.buffer.bytesBefore(value);
/* 319 */     if (bytes < 0) {
/* 320 */       throw REPLAY;
/*     */     }
/* 322 */     return bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(int length, byte value) {
/* 327 */     checkReadableBytes(length);
/* 328 */     int bytes = this.buffer.bytesBefore(length, value);
/* 329 */     if (bytes < 0) {
/* 330 */       throw REPLAY;
/*     */     }
/* 332 */     return bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(int index, int length, byte value) {
/* 337 */     int bytes = this.buffer.bytesBefore(index, length, value);
/* 338 */     if (bytes < 0) {
/* 339 */       throw REPLAY;
/*     */     }
/* 341 */     return bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(ByteBufProcessor processor) {
/* 346 */     int ret = this.buffer.forEachByte(processor);
/* 347 */     if (!this.terminated && ret < 0) {
/* 348 */       throw REPLAY;
/*     */     }
/* 350 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int forEachByte(int index, int length, ByteBufProcessor processor) {
/* 356 */     int writerIndex = this.buffer.writerIndex();
/* 357 */     if (index >= writerIndex) {
/* 358 */       throw REPLAY;
/*     */     }
/*     */     
/* 361 */     if (index + length <= writerIndex) {
/* 362 */       return this.buffer.forEachByte(index, length, processor);
/*     */     }
/*     */     
/* 365 */     int ret = this.buffer.forEachByte(index, writerIndex - index, processor);
/* 366 */     if (ret < 0) {
/* 367 */       throw REPLAY;
/*     */     }
/* 369 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor processor) {
/* 375 */     if (this.terminated) {
/* 376 */       return this.buffer.forEachByteDesc(processor);
/*     */     }
/* 378 */     reject();
/* 379 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(int index, int length, ByteBufProcessor processor) {
/* 385 */     if (index + length > this.buffer.writerIndex()) {
/* 386 */       throw REPLAY;
/*     */     }
/*     */     
/* 389 */     return this.buffer.forEachByteDesc(index, length, processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf markReaderIndex() {
/* 394 */     this.buffer.markReaderIndex();
/* 395 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf markWriterIndex() {
/* 400 */     reject();
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/* 406 */     return this.buffer.order();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf order(ByteOrder endianness) {
/* 411 */     if (endianness == null) {
/* 412 */       throw new NullPointerException("endianness");
/*     */     }
/* 414 */     if (endianness == order()) {
/* 415 */       return this;
/*     */     }
/*     */     
/* 418 */     SwappedByteBuf swapped = this.swapped;
/* 419 */     if (swapped == null) {
/* 420 */       this.swapped = swapped = new SwappedByteBuf(this);
/*     */     }
/* 422 */     return (ByteBuf)swapped;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadable() {
/* 427 */     return this.terminated ? this.buffer.isReadable() : true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadable(int size) {
/* 432 */     return this.terminated ? this.buffer.isReadable(size) : true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readableBytes() {
/* 437 */     if (this.terminated) {
/* 438 */       return this.buffer.readableBytes();
/*     */     }
/* 440 */     return Integer.MAX_VALUE - this.buffer.readerIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean readBoolean() {
/* 446 */     checkReadableBytes(1);
/* 447 */     return this.buffer.readBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() {
/* 452 */     checkReadableBytes(1);
/* 453 */     return this.buffer.readByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readUnsignedByte() {
/* 458 */     checkReadableBytes(1);
/* 459 */     return this.buffer.readUnsignedByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 464 */     checkReadableBytes(length);
/* 465 */     this.buffer.readBytes(dst, dstIndex, length);
/* 466 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst) {
/* 471 */     checkReadableBytes(dst.length);
/* 472 */     this.buffer.readBytes(dst);
/* 473 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 478 */     reject();
/* 479 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/* 484 */     checkReadableBytes(length);
/* 485 */     this.buffer.readBytes(dst, dstIndex, length);
/* 486 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int length) {
/* 491 */     reject();
/* 492 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst) {
/* 497 */     checkReadableBytes(dst.writableBytes());
/* 498 */     this.buffer.readBytes(dst);
/* 499 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) {
/* 504 */     reject();
/* 505 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(int length) {
/* 510 */     checkReadableBytes(length);
/* 511 */     return this.buffer.readBytes(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readSlice(int length) {
/* 516 */     checkReadableBytes(length);
/* 517 */     return this.buffer.readSlice(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(OutputStream out, int length) {
/* 522 */     reject();
/* 523 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readerIndex() {
/* 528 */     return this.buffer.readerIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readerIndex(int readerIndex) {
/* 533 */     this.buffer.readerIndex(readerIndex);
/* 534 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt() {
/* 539 */     checkReadableBytes(4);
/* 540 */     return this.buffer.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readUnsignedInt() {
/* 545 */     checkReadableBytes(4);
/* 546 */     return this.buffer.readUnsignedInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLong() {
/* 551 */     checkReadableBytes(8);
/* 552 */     return this.buffer.readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readMedium() {
/* 557 */     checkReadableBytes(3);
/* 558 */     return this.buffer.readMedium();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedMedium() {
/* 563 */     checkReadableBytes(3);
/* 564 */     return this.buffer.readUnsignedMedium();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() {
/* 569 */     checkReadableBytes(2);
/* 570 */     return this.buffer.readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() {
/* 575 */     checkReadableBytes(2);
/* 576 */     return this.buffer.readUnsignedShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public char readChar() {
/* 581 */     checkReadableBytes(2);
/* 582 */     return this.buffer.readChar();
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() {
/* 587 */     checkReadableBytes(4);
/* 588 */     return this.buffer.readFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() {
/* 593 */     checkReadableBytes(8);
/* 594 */     return this.buffer.readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf resetReaderIndex() {
/* 599 */     this.buffer.resetReaderIndex();
/* 600 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf resetWriterIndex() {
/* 605 */     reject();
/* 606 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBoolean(int index, boolean value) {
/* 611 */     reject();
/* 612 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 617 */     reject();
/* 618 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 623 */     reject();
/* 624 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src) {
/* 629 */     reject();
/* 630 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 635 */     reject();
/* 636 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 641 */     reject();
/* 642 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/* 647 */     reject();
/* 648 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src) {
/* 653 */     reject();
/* 654 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) {
/* 659 */     reject();
/* 660 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setZero(int index, int length) {
/* 665 */     reject();
/* 666 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) {
/* 671 */     reject();
/* 672 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setIndex(int readerIndex, int writerIndex) {
/* 677 */     reject();
/* 678 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 683 */     reject();
/* 684 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 689 */     reject();
/* 690 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 695 */     reject();
/* 696 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 701 */     reject();
/* 702 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setChar(int index, int value) {
/* 707 */     reject();
/* 708 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setFloat(int index, float value) {
/* 713 */     reject();
/* 714 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setDouble(int index, double value) {
/* 719 */     reject();
/* 720 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf skipBytes(int length) {
/* 725 */     checkReadableBytes(length);
/* 726 */     this.buffer.skipBytes(length);
/* 727 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice() {
/* 732 */     reject();
/* 733 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/* 738 */     checkIndex(index, length);
/* 739 */     return this.buffer.slice(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 744 */     return this.buffer.nioBufferCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer() {
/* 749 */     reject();
/* 750 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 755 */     checkIndex(index, length);
/* 756 */     return this.buffer.nioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers() {
/* 761 */     reject();
/* 762 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 767 */     checkIndex(index, length);
/* 768 */     return this.buffer.nioBuffers(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 773 */     checkIndex(index, length);
/* 774 */     return this.buffer.internalNioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(int index, int length, Charset charset) {
/* 779 */     checkIndex(index, length);
/* 780 */     return this.buffer.toString(index, length, charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(Charset charsetName) {
/* 785 */     reject();
/* 786 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 791 */     return getClass().getSimpleName() + '(' + "ridx=" + readerIndex() + ", " + "widx=" + writerIndex() + ')';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/* 802 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWritable(int size) {
/* 807 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int writableBytes() {
/* 812 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxWritableBytes() {
/* 817 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBoolean(boolean value) {
/* 822 */     reject();
/* 823 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeByte(int value) {
/* 828 */     reject();
/* 829 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/* 834 */     reject();
/* 835 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(byte[] src) {
/* 840 */     reject();
/* 841 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer src) {
/* 846 */     reject();
/* 847 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/* 852 */     reject();
/* 853 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src, int length) {
/* 858 */     reject();
/* 859 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src) {
/* 864 */     reject();
/* 865 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(InputStream in, int length) {
/* 870 */     reject();
/* 871 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel in, int length) {
/* 876 */     reject();
/* 877 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeInt(int value) {
/* 882 */     reject();
/* 883 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeLong(long value) {
/* 888 */     reject();
/* 889 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeMedium(int value) {
/* 894 */     reject();
/* 895 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeZero(int length) {
/* 900 */     reject();
/* 901 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int writerIndex() {
/* 906 */     return this.buffer.writerIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writerIndex(int writerIndex) {
/* 911 */     reject();
/* 912 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeShort(int value) {
/* 917 */     reject();
/* 918 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeChar(int value) {
/* 923 */     reject();
/* 924 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeFloat(float value) {
/* 929 */     reject();
/* 930 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeDouble(double value) {
/* 935 */     reject();
/* 936 */     return this;
/*     */   }
/*     */   
/*     */   private void checkIndex(int index, int length) {
/* 940 */     if (index + length > this.buffer.writerIndex()) {
/* 941 */       throw REPLAY;
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkReadableBytes(int readableBytes) {
/* 946 */     if (this.buffer.readableBytes() < readableBytes) {
/* 947 */       throw REPLAY;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf discardSomeReadBytes() {
/* 953 */     reject();
/* 954 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/* 959 */     return this.buffer.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain() {
/* 964 */     reject();
/* 965 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain(int increment) {
/* 970 */     reject();
/* 971 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 976 */     reject();
/* 977 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 982 */     reject();
/* 983 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf unwrap() {
/* 988 */     reject();
/* 989 */     return this;
/*     */   }
/*     */   
/*     */   private static void reject() {
/* 993 */     throw new UnsupportedOperationException("not a replayable operation");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\ReplayingDecoderBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */