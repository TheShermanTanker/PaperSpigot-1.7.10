/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import net.minecraft.util.io.netty.util.ResourceLeak;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnpooledDirectByteBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   private final ResourceLeak leak;
/*     */   private final ByteBufAllocator alloc;
/*     */   private ByteBuffer buffer;
/*     */   private ByteBuffer tmpNioBuf;
/*     */   private int capacity;
/*     */   private boolean doNotFree;
/*     */   
/*     */   protected UnpooledDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
/*  52 */     super(maxCapacity);
/*  53 */     if (alloc == null) {
/*  54 */       throw new NullPointerException("alloc");
/*     */     }
/*  56 */     if (initialCapacity < 0) {
/*  57 */       throw new IllegalArgumentException("initialCapacity: " + initialCapacity);
/*     */     }
/*  59 */     if (maxCapacity < 0) {
/*  60 */       throw new IllegalArgumentException("maxCapacity: " + maxCapacity);
/*     */     }
/*  62 */     if (initialCapacity > maxCapacity) {
/*  63 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity) }));
/*     */     }
/*     */ 
/*     */     
/*  67 */     this.alloc = alloc;
/*  68 */     setByteBuffer(ByteBuffer.allocateDirect(initialCapacity));
/*  69 */     this.leak = leakDetector.open(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UnpooledDirectByteBuf(ByteBufAllocator alloc, ByteBuffer initialBuffer, int maxCapacity) {
/*  78 */     super(maxCapacity);
/*  79 */     if (alloc == null) {
/*  80 */       throw new NullPointerException("alloc");
/*     */     }
/*  82 */     if (initialBuffer == null) {
/*  83 */       throw new NullPointerException("initialBuffer");
/*     */     }
/*  85 */     if (!initialBuffer.isDirect()) {
/*  86 */       throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
/*     */     }
/*  88 */     if (initialBuffer.isReadOnly()) {
/*  89 */       throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
/*     */     }
/*     */     
/*  92 */     int initialCapacity = initialBuffer.remaining();
/*  93 */     if (initialCapacity > maxCapacity) {
/*  94 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity) }));
/*     */     }
/*     */ 
/*     */     
/*  98 */     this.alloc = alloc;
/*  99 */     this.doNotFree = true;
/* 100 */     setByteBuffer(initialBuffer.slice().order(ByteOrder.BIG_ENDIAN));
/* 101 */     writerIndex(initialCapacity);
/* 102 */     this.leak = leakDetector.open(this);
/*     */   }
/*     */   
/*     */   private void setByteBuffer(ByteBuffer buffer) {
/* 106 */     ByteBuffer oldBuffer = this.buffer;
/* 107 */     if (oldBuffer != null) {
/* 108 */       if (this.doNotFree) {
/* 109 */         this.doNotFree = false;
/*     */       } else {
/* 111 */         PlatformDependent.freeDirectBuffer(oldBuffer);
/*     */       } 
/*     */     }
/*     */     
/* 115 */     this.buffer = buffer;
/* 116 */     this.tmpNioBuf = null;
/* 117 */     this.capacity = buffer.remaining();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 122 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 127 */     return this.capacity;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/* 132 */     ensureAccessible();
/* 133 */     if (newCapacity < 0 || newCapacity > maxCapacity()) {
/* 134 */       throw new IllegalArgumentException("newCapacity: " + newCapacity);
/*     */     }
/*     */     
/* 137 */     int readerIndex = readerIndex();
/* 138 */     int writerIndex = writerIndex();
/*     */     
/* 140 */     int oldCapacity = this.capacity;
/* 141 */     if (newCapacity > oldCapacity) {
/* 142 */       ByteBuffer oldBuffer = this.buffer;
/* 143 */       ByteBuffer newBuffer = ByteBuffer.allocateDirect(newCapacity);
/* 144 */       oldBuffer.position(readerIndex).limit(writerIndex);
/* 145 */       newBuffer.position(readerIndex).limit(writerIndex);
/* 146 */       newBuffer.put(oldBuffer);
/* 147 */       newBuffer.clear();
/* 148 */       setByteBuffer(newBuffer);
/* 149 */     } else if (newCapacity < oldCapacity) {
/* 150 */       ByteBuffer oldBuffer = this.buffer;
/* 151 */       ByteBuffer newBuffer = ByteBuffer.allocateDirect(newCapacity);
/* 152 */       if (readerIndex < newCapacity) {
/* 153 */         if (writerIndex > newCapacity) {
/* 154 */           writerIndex(writerIndex = newCapacity);
/*     */         }
/* 156 */         oldBuffer.position(readerIndex).limit(writerIndex);
/* 157 */         newBuffer.position(readerIndex).limit(writerIndex);
/* 158 */         newBuffer.put(oldBuffer);
/* 159 */         newBuffer.clear();
/*     */       } else {
/* 161 */         setIndex(newCapacity, newCapacity);
/*     */       } 
/* 163 */       setByteBuffer(newBuffer);
/*     */     } 
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 170 */     return this.alloc;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/* 175 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 185 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 190 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 200 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 205 */     ensureAccessible();
/* 206 */     return _getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/* 211 */     return this.buffer.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 216 */     ensureAccessible();
/* 217 */     return _getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/* 222 */     return this.buffer.getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 227 */     ensureAccessible();
/* 228 */     return _getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/* 233 */     return (getByte(index) & 0xFF) << 16 | (getByte(index + 1) & 0xFF) << 8 | getByte(index + 2) & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 238 */     ensureAccessible();
/* 239 */     return _getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/* 244 */     return this.buffer.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 249 */     ensureAccessible();
/* 250 */     return _getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 255 */     return this.buffer.getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 260 */     checkDstIndex(index, length, dstIndex, dst.capacity());
/* 261 */     if (dst.hasArray()) {
/* 262 */       getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
/* 263 */     } else if (dst.nioBufferCount() > 0) {
/* 264 */       for (ByteBuffer bb : dst.nioBuffers(dstIndex, length)) {
/* 265 */         int bbLen = bb.remaining();
/* 266 */         getBytes(index, bb);
/* 267 */         index += bbLen;
/*     */       } 
/*     */     } else {
/* 270 */       dst.setBytes(dstIndex, this, index, length);
/*     */     } 
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 277 */     getBytes(index, dst, dstIndex, length, false);
/* 278 */     return this;
/*     */   }
/*     */   private void getBytes(int index, byte[] dst, int dstIndex, int length, boolean internal) {
/*     */     ByteBuffer tmpBuf;
/* 282 */     checkDstIndex(index, length, dstIndex, dst.length);
/*     */     
/* 284 */     if (dstIndex < 0 || dstIndex > dst.length - length) {
/* 285 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(dstIndex), Integer.valueOf(length), Integer.valueOf(dst.length) }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 290 */     if (internal) {
/* 291 */       tmpBuf = internalNioBuffer();
/*     */     } else {
/* 293 */       tmpBuf = this.buffer.duplicate();
/*     */     } 
/* 295 */     tmpBuf.clear().position(index).limit(index + length);
/* 296 */     tmpBuf.get(dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 301 */     checkReadableBytes(length);
/* 302 */     getBytes(this.readerIndex, dst, dstIndex, length, true);
/* 303 */     this.readerIndex += length;
/* 304 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 309 */     getBytes(index, dst, false);
/* 310 */     return this;
/*     */   }
/*     */   private void getBytes(int index, ByteBuffer dst, boolean internal) {
/*     */     ByteBuffer tmpBuf;
/* 314 */     checkIndex(index);
/* 315 */     if (dst == null) {
/* 316 */       throw new NullPointerException("dst");
/*     */     }
/*     */     
/* 319 */     int bytesToCopy = Math.min(capacity() - index, dst.remaining());
/*     */     
/* 321 */     if (internal) {
/* 322 */       tmpBuf = internalNioBuffer();
/*     */     } else {
/* 324 */       tmpBuf = this.buffer.duplicate();
/*     */     } 
/* 326 */     tmpBuf.clear().position(index).limit(index + bytesToCopy);
/* 327 */     dst.put(tmpBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 332 */     int length = dst.remaining();
/* 333 */     checkReadableBytes(length);
/* 334 */     getBytes(this.readerIndex, dst, true);
/* 335 */     this.readerIndex += length;
/* 336 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 341 */     ensureAccessible();
/* 342 */     _setByte(index, value);
/* 343 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 348 */     this.buffer.put(index, (byte)value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 353 */     ensureAccessible();
/* 354 */     _setShort(index, value);
/* 355 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 360 */     this.buffer.putShort(index, (short)value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 365 */     ensureAccessible();
/* 366 */     _setMedium(index, value);
/* 367 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 372 */     setByte(index, (byte)(value >>> 16));
/* 373 */     setByte(index + 1, (byte)(value >>> 8));
/* 374 */     setByte(index + 2, (byte)value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 379 */     ensureAccessible();
/* 380 */     _setInt(index, value);
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 386 */     this.buffer.putInt(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 391 */     ensureAccessible();
/* 392 */     _setLong(index, value);
/* 393 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 398 */     this.buffer.putLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 403 */     checkSrcIndex(index, length, srcIndex, src.capacity());
/* 404 */     if (this.buffer.hasArray()) {
/* 405 */       src.getBytes(srcIndex, this.buffer.array(), index + this.buffer.arrayOffset(), length);
/* 406 */     } else if (src.nioBufferCount() > 0) {
/* 407 */       for (ByteBuffer bb : src.nioBuffers(srcIndex, length)) {
/* 408 */         int bbLen = bb.remaining();
/* 409 */         setBytes(index, bb);
/* 410 */         index += bbLen;
/*     */       } 
/*     */     } else {
/* 413 */       src.getBytes(srcIndex, this, index, length);
/*     */     } 
/* 415 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 420 */     checkSrcIndex(index, length, srcIndex, src.length);
/* 421 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 422 */     tmpBuf.clear().position(index).limit(index + length);
/* 423 */     tmpBuf.put(src, srcIndex, length);
/* 424 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 429 */     ensureAccessible();
/* 430 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 431 */     if (src == tmpBuf) {
/* 432 */       src = src.duplicate();
/*     */     }
/*     */     
/* 435 */     tmpBuf.clear().position(index).limit(index + src.remaining());
/* 436 */     tmpBuf.put(src);
/* 437 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 442 */     getBytes(index, out, length, false);
/* 443 */     return this;
/*     */   }
/*     */   
/*     */   private void getBytes(int index, OutputStream out, int length, boolean internal) throws IOException {
/* 447 */     ensureAccessible();
/* 448 */     if (length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 452 */     if (this.buffer.hasArray()) {
/* 453 */       out.write(this.buffer.array(), index + this.buffer.arrayOffset(), length);
/*     */     } else {
/* 455 */       ByteBuffer tmpBuf; byte[] tmp = new byte[length];
/*     */       
/* 457 */       if (internal) {
/* 458 */         tmpBuf = internalNioBuffer();
/*     */       } else {
/* 460 */         tmpBuf = this.buffer.duplicate();
/*     */       } 
/* 462 */       tmpBuf.clear().position(index);
/* 463 */       tmpBuf.get(tmp);
/* 464 */       out.write(tmp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/* 470 */     checkReadableBytes(length);
/* 471 */     getBytes(this.readerIndex, out, length, true);
/* 472 */     this.readerIndex += length;
/* 473 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 478 */     return getBytes(index, out, length, false);
/*     */   }
/*     */   private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
/*     */     ByteBuffer tmpBuf;
/* 482 */     ensureAccessible();
/* 483 */     if (length == 0) {
/* 484 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 488 */     if (internal) {
/* 489 */       tmpBuf = internalNioBuffer();
/*     */     } else {
/* 491 */       tmpBuf = this.buffer.duplicate();
/*     */     } 
/* 493 */     tmpBuf.clear().position(index).limit(index + length);
/* 494 */     return out.write(tmpBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/* 499 */     checkReadableBytes(length);
/* 500 */     int readBytes = getBytes(this.readerIndex, out, length, true);
/* 501 */     this.readerIndex += readBytes;
/* 502 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 507 */     ensureAccessible();
/* 508 */     if (this.buffer.hasArray()) {
/* 509 */       return in.read(this.buffer.array(), this.buffer.arrayOffset() + index, length);
/*     */     }
/* 511 */     byte[] tmp = new byte[length];
/* 512 */     int readBytes = in.read(tmp);
/* 513 */     if (readBytes <= 0) {
/* 514 */       return readBytes;
/*     */     }
/* 516 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 517 */     tmpBuf.clear().position(index);
/* 518 */     tmpBuf.put(tmp, 0, readBytes);
/* 519 */     return readBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 525 */     ensureAccessible();
/* 526 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 527 */     tmpBuf.clear().position(index).limit(index + length);
/*     */     try {
/* 529 */       return in.read(this.tmpNioBuf);
/* 530 */     } catch (ClosedChannelException e) {
/* 531 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 537 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 542 */     return new ByteBuffer[] { nioBuffer(index, length) };
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/*     */     ByteBuffer src;
/* 547 */     ensureAccessible();
/*     */     
/*     */     try {
/* 550 */       src = (ByteBuffer)this.buffer.duplicate().clear().position(index).limit(index + length);
/* 551 */     } catch (IllegalArgumentException e) {
/* 552 */       throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (index + length));
/*     */     } 
/*     */     
/* 555 */     ByteBuffer dst = src.isDirect() ? ByteBuffer.allocateDirect(length) : ByteBuffer.allocate(length);
/*     */     
/* 557 */     dst.put(src);
/* 558 */     dst.order(order());
/* 559 */     dst.clear();
/* 560 */     return new UnpooledDirectByteBuf(alloc(), dst, maxCapacity());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 565 */     return (ByteBuffer)internalNioBuffer().clear().position(index).limit(index + length);
/*     */   }
/*     */   
/*     */   private ByteBuffer internalNioBuffer() {
/* 569 */     ByteBuffer tmpNioBuf = this.tmpNioBuf;
/* 570 */     if (tmpNioBuf == null) {
/* 571 */       this.tmpNioBuf = tmpNioBuf = this.buffer.duplicate();
/*     */     }
/* 573 */     return tmpNioBuf;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 578 */     return (ByteBuffer)this.buffer.duplicate().position(index).limit(index + length);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/* 583 */     ByteBuffer buffer = this.buffer;
/* 584 */     if (buffer == null) {
/*     */       return;
/*     */     }
/*     */     
/* 588 */     this.buffer = null;
/*     */     
/* 590 */     if (!this.doNotFree) {
/* 591 */       PlatformDependent.freeDirectBuffer(buffer);
/*     */     }
/*     */     
/* 594 */     if (this.leak != null) {
/* 595 */       this.leak.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf unwrap() {
/* 601 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\UnpooledDirectByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */