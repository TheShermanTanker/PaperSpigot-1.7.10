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
/*     */ public class UnpooledUnsafeDirectByteBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*  37 */   private static final boolean NATIVE_ORDER = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
/*     */   
/*     */   private final ResourceLeak leak;
/*     */   
/*     */   private final ByteBufAllocator alloc;
/*     */   
/*     */   private long memoryAddress;
/*     */   
/*     */   private ByteBuffer buffer;
/*     */   
/*     */   private ByteBuffer tmpNioBuf;
/*     */   
/*     */   private int capacity;
/*     */   
/*     */   private boolean doNotFree;
/*     */ 
/*     */   
/*     */   protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
/*  55 */     super(maxCapacity);
/*  56 */     if (alloc == null) {
/*  57 */       throw new NullPointerException("alloc");
/*     */     }
/*  59 */     if (initialCapacity < 0) {
/*  60 */       throw new IllegalArgumentException("initialCapacity: " + initialCapacity);
/*     */     }
/*  62 */     if (maxCapacity < 0) {
/*  63 */       throw new IllegalArgumentException("maxCapacity: " + maxCapacity);
/*     */     }
/*  65 */     if (initialCapacity > maxCapacity) {
/*  66 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity) }));
/*     */     }
/*     */ 
/*     */     
/*  70 */     this.alloc = alloc;
/*  71 */     setByteBuffer(ByteBuffer.allocateDirect(initialCapacity));
/*  72 */     this.leak = leakDetector.open(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, ByteBuffer initialBuffer, int maxCapacity) {
/*  81 */     super(maxCapacity);
/*  82 */     if (alloc == null) {
/*  83 */       throw new NullPointerException("alloc");
/*     */     }
/*  85 */     if (initialBuffer == null) {
/*  86 */       throw new NullPointerException("initialBuffer");
/*     */     }
/*  88 */     if (!initialBuffer.isDirect()) {
/*  89 */       throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
/*     */     }
/*  91 */     if (initialBuffer.isReadOnly()) {
/*  92 */       throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
/*     */     }
/*     */     
/*  95 */     int initialCapacity = initialBuffer.remaining();
/*  96 */     if (initialCapacity > maxCapacity) {
/*  97 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity) }));
/*     */     }
/*     */ 
/*     */     
/* 101 */     this.alloc = alloc;
/* 102 */     this.doNotFree = true;
/* 103 */     setByteBuffer(initialBuffer.slice().order(ByteOrder.BIG_ENDIAN));
/* 104 */     writerIndex(initialCapacity);
/* 105 */     this.leak = leakDetector.open(this);
/*     */   }
/*     */   
/*     */   private void setByteBuffer(ByteBuffer buffer) {
/* 109 */     ByteBuffer oldBuffer = this.buffer;
/* 110 */     if (oldBuffer != null) {
/* 111 */       if (this.doNotFree) {
/* 112 */         this.doNotFree = false;
/*     */       } else {
/* 114 */         PlatformDependent.freeDirectBuffer(oldBuffer);
/*     */       } 
/*     */     }
/*     */     
/* 118 */     this.buffer = buffer;
/* 119 */     this.memoryAddress = PlatformDependent.directBufferAddress(buffer);
/* 120 */     this.tmpNioBuf = null;
/* 121 */     this.capacity = buffer.remaining();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 131 */     return this.capacity;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/* 136 */     ensureAccessible();
/* 137 */     if (newCapacity < 0 || newCapacity > maxCapacity()) {
/* 138 */       throw new IllegalArgumentException("newCapacity: " + newCapacity);
/*     */     }
/*     */     
/* 141 */     int readerIndex = readerIndex();
/* 142 */     int writerIndex = writerIndex();
/*     */     
/* 144 */     int oldCapacity = this.capacity;
/* 145 */     if (newCapacity > oldCapacity) {
/* 146 */       ByteBuffer oldBuffer = this.buffer;
/* 147 */       ByteBuffer newBuffer = ByteBuffer.allocateDirect(newCapacity);
/* 148 */       oldBuffer.position(readerIndex).limit(writerIndex);
/* 149 */       newBuffer.position(readerIndex).limit(writerIndex);
/* 150 */       newBuffer.put(oldBuffer);
/* 151 */       newBuffer.clear();
/* 152 */       setByteBuffer(newBuffer);
/* 153 */     } else if (newCapacity < oldCapacity) {
/* 154 */       ByteBuffer oldBuffer = this.buffer;
/* 155 */       ByteBuffer newBuffer = ByteBuffer.allocateDirect(newCapacity);
/* 156 */       if (readerIndex < newCapacity) {
/* 157 */         if (writerIndex > newCapacity) {
/* 158 */           writerIndex(writerIndex = newCapacity);
/*     */         }
/* 160 */         oldBuffer.position(readerIndex).limit(writerIndex);
/* 161 */         newBuffer.position(readerIndex).limit(writerIndex);
/* 162 */         newBuffer.put(oldBuffer);
/* 163 */         newBuffer.clear();
/*     */       } else {
/* 165 */         setIndex(newCapacity, newCapacity);
/*     */       } 
/* 167 */       setByteBuffer(newBuffer);
/*     */     } 
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 174 */     return this.alloc;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/* 179 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 189 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 194 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 204 */     return this.memoryAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/* 209 */     return PlatformDependent.getByte(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/* 214 */     short v = PlatformDependent.getShort(addr(index));
/* 215 */     return NATIVE_ORDER ? v : Short.reverseBytes(v);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/* 220 */     long addr = addr(index);
/* 221 */     return (PlatformDependent.getByte(addr) & 0xFF) << 16 | (PlatformDependent.getByte(addr + 1L) & 0xFF) << 8 | PlatformDependent.getByte(addr + 2L) & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/* 228 */     int v = PlatformDependent.getInt(addr(index));
/* 229 */     return NATIVE_ORDER ? v : Integer.reverseBytes(v);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 234 */     long v = PlatformDependent.getLong(addr(index));
/* 235 */     return NATIVE_ORDER ? v : Long.reverseBytes(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 240 */     checkIndex(index, length);
/* 241 */     if (dst == null) {
/* 242 */       throw new NullPointerException("dst");
/*     */     }
/* 244 */     if (dstIndex < 0 || dstIndex > dst.capacity() - length) {
/* 245 */       throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
/*     */     }
/*     */     
/* 248 */     if (dst.hasMemoryAddress()) {
/* 249 */       PlatformDependent.copyMemory(addr(index), dst.memoryAddress() + dstIndex, length);
/* 250 */     } else if (dst.hasArray()) {
/* 251 */       PlatformDependent.copyMemory(addr(index), dst.array(), dst.arrayOffset() + dstIndex, length);
/*     */     } else {
/* 253 */       dst.setBytes(dstIndex, this, index, length);
/*     */     } 
/* 255 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 260 */     checkIndex(index, length);
/* 261 */     if (dst == null) {
/* 262 */       throw new NullPointerException("dst");
/*     */     }
/* 264 */     if (dstIndex < 0 || dstIndex > dst.length - length) {
/* 265 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(dstIndex), Integer.valueOf(length), Integer.valueOf(dst.length) }));
/*     */     }
/*     */ 
/*     */     
/* 269 */     if (length != 0) {
/* 270 */       PlatformDependent.copyMemory(addr(index), dst, dstIndex, length);
/*     */     }
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 277 */     getBytes(index, dst, false);
/* 278 */     return this;
/*     */   }
/*     */   private void getBytes(int index, ByteBuffer dst, boolean internal) {
/*     */     ByteBuffer tmpBuf;
/* 282 */     checkIndex(index);
/* 283 */     if (dst == null) {
/* 284 */       throw new NullPointerException("dst");
/*     */     }
/*     */     
/* 287 */     int bytesToCopy = Math.min(capacity() - index, dst.remaining());
/*     */     
/* 289 */     if (internal) {
/* 290 */       tmpBuf = internalNioBuffer();
/*     */     } else {
/* 292 */       tmpBuf = this.buffer.duplicate();
/*     */     } 
/* 294 */     tmpBuf.clear().position(index).limit(index + bytesToCopy);
/* 295 */     dst.put(tmpBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 300 */     int length = dst.remaining();
/* 301 */     checkReadableBytes(length);
/* 302 */     getBytes(this.readerIndex, dst, true);
/* 303 */     this.readerIndex += length;
/* 304 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 309 */     PlatformDependent.putByte(addr(index), (byte)value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 314 */     PlatformDependent.putShort(addr(index), NATIVE_ORDER ? (short)value : Short.reverseBytes((short)value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 319 */     long addr = addr(index);
/* 320 */     PlatformDependent.putByte(addr, (byte)(value >>> 16));
/* 321 */     PlatformDependent.putByte(addr + 1L, (byte)(value >>> 8));
/* 322 */     PlatformDependent.putByte(addr + 2L, (byte)value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 327 */     PlatformDependent.putInt(addr(index), NATIVE_ORDER ? value : Integer.reverseBytes(value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 332 */     PlatformDependent.putLong(addr(index), NATIVE_ORDER ? value : Long.reverseBytes(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 337 */     checkIndex(index, length);
/* 338 */     if (src == null) {
/* 339 */       throw new NullPointerException("src");
/*     */     }
/* 341 */     if (srcIndex < 0 || srcIndex > src.capacity() - length) {
/* 342 */       throw new IndexOutOfBoundsException("srcIndex: " + srcIndex);
/*     */     }
/*     */     
/* 345 */     if (length != 0) {
/* 346 */       if (src.hasMemoryAddress()) {
/* 347 */         PlatformDependent.copyMemory(src.memoryAddress() + srcIndex, addr(index), length);
/* 348 */       } else if (this.buffer.hasArray()) {
/* 349 */         PlatformDependent.copyMemory(src.array(), src.arrayOffset() + srcIndex, addr(index), length);
/*     */       } else {
/* 351 */         src.getBytes(srcIndex, this, index, length);
/*     */       } 
/*     */     }
/* 354 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 359 */     checkIndex(index, length);
/* 360 */     if (length != 0) {
/* 361 */       PlatformDependent.copyMemory(src, srcIndex, addr(index), length);
/*     */     }
/* 363 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 368 */     ensureAccessible();
/* 369 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 370 */     if (src == tmpBuf) {
/* 371 */       src = src.duplicate();
/*     */     }
/*     */     
/* 374 */     tmpBuf.clear().position(index).limit(index + src.remaining());
/* 375 */     tmpBuf.put(src);
/* 376 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 381 */     ensureAccessible();
/* 382 */     if (length != 0) {
/* 383 */       byte[] tmp = new byte[length];
/* 384 */       PlatformDependent.copyMemory(addr(index), tmp, 0, length);
/* 385 */       out.write(tmp);
/*     */     } 
/* 387 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 392 */     return getBytes(index, out, length, false);
/*     */   }
/*     */   private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
/*     */     ByteBuffer tmpBuf;
/* 396 */     ensureAccessible();
/* 397 */     if (length == 0) {
/* 398 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 402 */     if (internal) {
/* 403 */       tmpBuf = internalNioBuffer();
/*     */     } else {
/* 405 */       tmpBuf = this.buffer.duplicate();
/*     */     } 
/* 407 */     tmpBuf.clear().position(index).limit(index + length);
/* 408 */     return out.write(tmpBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/* 413 */     checkReadableBytes(length);
/* 414 */     int readBytes = getBytes(this.readerIndex, out, length, true);
/* 415 */     this.readerIndex += readBytes;
/* 416 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 421 */     checkIndex(index, length);
/* 422 */     byte[] tmp = new byte[length];
/* 423 */     int readBytes = in.read(tmp);
/* 424 */     if (readBytes > 0) {
/* 425 */       PlatformDependent.copyMemory(tmp, 0, addr(index), readBytes);
/*     */     }
/* 427 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 432 */     ensureAccessible();
/* 433 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 434 */     tmpBuf.clear().position(index).limit(index + length);
/*     */     try {
/* 436 */       return in.read(tmpBuf);
/* 437 */     } catch (ClosedChannelException e) {
/* 438 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 444 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 449 */     return new ByteBuffer[] { nioBuffer(index, length) };
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 454 */     checkIndex(index, length);
/* 455 */     UnpooledUnsafeDirectByteBuf copy = (UnpooledUnsafeDirectByteBuf)alloc().directBuffer(length, maxCapacity());
/* 456 */     if (length != 0) {
/* 457 */       PlatformDependent.copyMemory(addr(index), copy.addr(0), length);
/* 458 */       copy.setIndex(0, length);
/*     */     } 
/* 460 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 465 */     return (ByteBuffer)internalNioBuffer().clear().position(index).limit(index + length);
/*     */   }
/*     */   
/*     */   private ByteBuffer internalNioBuffer() {
/* 469 */     ByteBuffer tmpNioBuf = this.tmpNioBuf;
/* 470 */     if (tmpNioBuf == null) {
/* 471 */       this.tmpNioBuf = tmpNioBuf = this.buffer.duplicate();
/*     */     }
/* 473 */     return tmpNioBuf;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 478 */     return (ByteBuffer)this.buffer.duplicate().position(index).limit(index + length);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/* 483 */     ByteBuffer buffer = this.buffer;
/* 484 */     if (buffer == null) {
/*     */       return;
/*     */     }
/*     */     
/* 488 */     this.buffer = null;
/*     */     
/* 490 */     if (!this.doNotFree) {
/* 491 */       PlatformDependent.freeDirectBuffer(buffer);
/*     */     }
/*     */     
/* 494 */     if (this.leak != null) {
/* 495 */       this.leak.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf unwrap() {
/* 501 */     return null;
/*     */   }
/*     */   
/*     */   long addr(int index) {
/* 505 */     return this.memoryAddress + index;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\UnpooledUnsafeDirectByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */