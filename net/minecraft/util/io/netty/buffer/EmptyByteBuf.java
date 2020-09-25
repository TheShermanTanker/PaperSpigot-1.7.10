/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.ReadOnlyBufferException;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCounted;
/*     */ import net.minecraft.util.io.netty.util.internal.EmptyArrays;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
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
/*     */ public final class EmptyByteBuf
/*     */   extends ByteBuf
/*     */ {
/*  36 */   private static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.allocateDirect(0);
/*     */   private static final long EMPTY_BYTE_BUFFER_ADDRESS;
/*     */   
/*     */   static {
/*  40 */     long emptyByteBufferAddress = 0L;
/*     */     try {
/*  42 */       if (PlatformDependent.hasUnsafe()) {
/*  43 */         emptyByteBufferAddress = PlatformDependent.directBufferAddress(EMPTY_BYTE_BUFFER);
/*     */       }
/*  45 */     } catch (Throwable t) {}
/*     */ 
/*     */     
/*  48 */     EMPTY_BYTE_BUFFER_ADDRESS = emptyByteBufferAddress;
/*     */   }
/*     */   
/*     */   private final ByteBufAllocator alloc;
/*     */   private final ByteOrder order;
/*     */   private final String str;
/*     */   private EmptyByteBuf swapped;
/*     */   
/*     */   public EmptyByteBuf(ByteBufAllocator alloc) {
/*  57 */     this(alloc, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */   
/*     */   private EmptyByteBuf(ByteBufAllocator alloc, ByteOrder order) {
/*  61 */     if (alloc == null) {
/*  62 */       throw new NullPointerException("alloc");
/*     */     }
/*     */     
/*  65 */     this.alloc = alloc;
/*  66 */     this.order = order;
/*  67 */     this.str = getClass().getSimpleName() + ((order == ByteOrder.BIG_ENDIAN) ? "BE" : "LE");
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/*  72 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/*  77 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/*  82 */     return this.alloc;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/*  87 */     return this.order;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf unwrap() {
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxCapacity() {
/* 102 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf order(ByteOrder endianness) {
/* 107 */     if (endianness == null) {
/* 108 */       throw new NullPointerException("endianness");
/*     */     }
/* 110 */     if (endianness == order()) {
/* 111 */       return this;
/*     */     }
/*     */     
/* 114 */     EmptyByteBuf swapped = this.swapped;
/* 115 */     if (swapped != null) {
/* 116 */       return swapped;
/*     */     }
/*     */     
/* 119 */     this.swapped = swapped = new EmptyByteBuf(alloc(), endianness);
/* 120 */     return swapped;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readerIndex() {
/* 125 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readerIndex(int readerIndex) {
/* 130 */     return checkIndex(readerIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writerIndex() {
/* 135 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writerIndex(int writerIndex) {
/* 140 */     return checkIndex(writerIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setIndex(int readerIndex, int writerIndex) {
/* 145 */     checkIndex(readerIndex);
/* 146 */     checkIndex(writerIndex);
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readableBytes() {
/* 152 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int writableBytes() {
/* 157 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxWritableBytes() {
/* 162 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadable() {
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf clear() {
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf markReaderIndex() {
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf resetReaderIndex() {
/* 187 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf markWriterIndex() {
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf resetWriterIndex() {
/* 197 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf discardReadBytes() {
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf discardSomeReadBytes() {
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ensureWritable(int minWritableBytes) {
/* 212 */     if (minWritableBytes < 0) {
/* 213 */       throw new IllegalArgumentException("minWritableBytes: " + minWritableBytes + " (expected: >= 0)");
/*     */     }
/* 215 */     if (minWritableBytes != 0) {
/* 216 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int ensureWritable(int minWritableBytes, boolean force) {
/* 223 */     if (minWritableBytes < 0) {
/* 224 */       throw new IllegalArgumentException("minWritableBytes: " + minWritableBytes + " (expected: >= 0)");
/*     */     }
/*     */     
/* 227 */     if (minWritableBytes == 0) {
/* 228 */       return 0;
/*     */     }
/*     */     
/* 231 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(int index) {
/* 236 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 241 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public short getUnsignedByte(int index) {
/* 246 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 251 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedShort(int index) {
/* 256 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMedium(int index) {
/* 261 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 266 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 271 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getUnsignedInt(int index) {
/* 276 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 281 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public char getChar(int index) {
/* 286 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(int index) {
/* 291 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/* 296 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst) {
/* 301 */     return checkIndex(index, dst.writableBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/* 306 */     return checkIndex(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 311 */     return checkIndex(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst) {
/* 316 */     return checkIndex(index, dst.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 321 */     return checkIndex(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 326 */     return checkIndex(index, dst.remaining());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) {
/* 331 */     return checkIndex(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) {
/* 336 */     checkIndex(index, length);
/* 337 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBoolean(int index, boolean value) {
/* 342 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 347 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 352 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 357 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 362 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 367 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setChar(int index, int value) {
/* 372 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setFloat(int index, float value) {
/* 377 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setDouble(int index, double value) {
/* 382 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src) {
/* 387 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/* 392 */     return checkIndex(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 397 */     return checkIndex(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src) {
/* 402 */     return checkIndex(index, src.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 407 */     return checkIndex(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 412 */     return checkIndex(index, src.remaining());
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) {
/* 417 */     checkIndex(index, length);
/* 418 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) {
/* 423 */     checkIndex(index, length);
/* 424 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setZero(int index, int length) {
/* 429 */     return checkIndex(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readBoolean() {
/* 434 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() {
/* 439 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readUnsignedByte() {
/* 444 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() {
/* 449 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() {
/* 454 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readMedium() {
/* 459 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedMedium() {
/* 464 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt() {
/* 469 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readUnsignedInt() {
/* 474 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLong() {
/* 479 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public char readChar() {
/* 484 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() {
/* 489 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() {
/* 494 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(int length) {
/* 499 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readSlice(int length) {
/* 504 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst) {
/* 509 */     return checkLength(dst.writableBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int length) {
/* 514 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/* 519 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst) {
/* 524 */     return checkLength(dst.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 529 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 534 */     return checkLength(dst.remaining());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(OutputStream out, int length) {
/* 539 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) {
/* 544 */     checkLength(length);
/* 545 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf skipBytes(int length) {
/* 550 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBoolean(boolean value) {
/* 555 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeByte(int value) {
/* 560 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeShort(int value) {
/* 565 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeMedium(int value) {
/* 570 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeInt(int value) {
/* 575 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeLong(long value) {
/* 580 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeChar(int value) {
/* 585 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeFloat(float value) {
/* 590 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeDouble(double value) {
/* 595 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src) {
/* 600 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src, int length) {
/* 605 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/* 610 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(byte[] src) {
/* 615 */     return checkLength(src.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/* 620 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer src) {
/* 625 */     return checkLength(src.remaining());
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(InputStream in, int length) {
/* 630 */     checkLength(length);
/* 631 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel in, int length) {
/* 636 */     checkLength(length);
/* 637 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeZero(int length) {
/* 642 */     return checkLength(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(int fromIndex, int toIndex, byte value) {
/* 647 */     checkIndex(fromIndex);
/* 648 */     checkIndex(toIndex);
/* 649 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(byte value) {
/* 654 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(int length, byte value) {
/* 659 */     checkLength(length);
/* 660 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(int index, int length, byte value) {
/* 665 */     checkIndex(index, length);
/* 666 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(ByteBufProcessor processor) {
/* 671 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(int index, int length, ByteBufProcessor processor) {
/* 676 */     checkIndex(index, length);
/* 677 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor processor) {
/* 682 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(int index, int length, ByteBufProcessor processor) {
/* 687 */     checkIndex(index, length);
/* 688 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy() {
/* 693 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 698 */     return checkIndex(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice() {
/* 703 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/* 708 */     return checkIndex(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/* 713 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 718 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer() {
/* 723 */     return EMPTY_BYTE_BUFFER;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 728 */     checkIndex(index, length);
/* 729 */     return nioBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers() {
/* 734 */     return new ByteBuffer[] { EMPTY_BYTE_BUFFER };
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 739 */     checkIndex(index, length);
/* 740 */     return nioBuffers();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 745 */     return EMPTY_BYTE_BUFFER;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 750 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 755 */     return EmptyArrays.EMPTY_BYTES;
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 760 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 765 */     return (EMPTY_BYTE_BUFFER_ADDRESS != 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 770 */     if (hasMemoryAddress()) {
/* 771 */       return EMPTY_BYTE_BUFFER_ADDRESS;
/*     */     }
/* 773 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(Charset charset) {
/* 779 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(int index, int length, Charset charset) {
/* 784 */     checkIndex(index, length);
/* 785 */     return toString(charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 790 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 795 */     return (obj instanceof ByteBuf && !((ByteBuf)obj).isReadable());
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ByteBuf buffer) {
/* 800 */     return buffer.isReadable() ? -1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 805 */     return this.str;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadable(int size) {
/* 810 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWritable(int size) {
/* 815 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/* 820 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain() {
/* 825 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain(int increment) {
/* 830 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 835 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 840 */     return false;
/*     */   }
/*     */   
/*     */   private ByteBuf checkIndex(int index) {
/* 844 */     if (index != 0) {
/* 845 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 847 */     return this;
/*     */   }
/*     */   
/*     */   private ByteBuf checkIndex(int index, int length) {
/* 851 */     if (length < 0) {
/* 852 */       throw new IllegalArgumentException("length: " + length);
/*     */     }
/* 854 */     if (index != 0 || length != 0) {
/* 855 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 857 */     return this;
/*     */   }
/*     */   
/*     */   private ByteBuf checkLength(int length) {
/* 861 */     if (length < 0) {
/* 862 */       throw new IllegalArgumentException("length: " + length + " (expected: >= 0)");
/*     */     }
/* 864 */     if (length != 0) {
/* 865 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 867 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\EmptyByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */