/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.ReadOnlyBufferException;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import net.minecraft.util.io.netty.util.ResourceLeak;
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
/*     */ class ReadOnlyByteBufferBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   private final ResourceLeak leak;
/*     */   protected final ByteBuffer buffer;
/*     */   private final ByteBufAllocator allocator;
/*     */   private ByteBuffer tmpNioBuf;
/*     */   
/*     */   public ReadOnlyByteBufferBuf(ByteBufAllocator allocator, ByteBuffer buffer) {
/*  41 */     super(buffer.remaining());
/*  42 */     if (!buffer.isReadOnly()) {
/*  43 */       throw new IllegalArgumentException("must be a readonly buffer: " + buffer.getClass().getSimpleName());
/*     */     }
/*     */     
/*  46 */     this.allocator = allocator;
/*  47 */     this.buffer = buffer.slice().order(ByteOrder.BIG_ENDIAN);
/*  48 */     writerIndex(this.buffer.limit());
/*  49 */     this.leak = leakDetector.open(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/*  54 */     if (this.leak != null) {
/*  55 */       this.leak.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/*  61 */     ensureAccessible();
/*  62 */     return _getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/*  67 */     return this.buffer.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/*  72 */     ensureAccessible();
/*  73 */     return _getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/*  78 */     return this.buffer.getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/*  83 */     ensureAccessible();
/*  84 */     return _getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/*  89 */     return (getByte(index) & 0xFF) << 16 | (getByte(index + 1) & 0xFF) << 8 | getByte(index + 2) & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/*  94 */     ensureAccessible();
/*  95 */     return _getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/* 100 */     return this.buffer.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 105 */     ensureAccessible();
/* 106 */     return _getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 111 */     return this.buffer.getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 116 */     checkDstIndex(index, length, dstIndex, dst.capacity());
/* 117 */     if (dst.hasArray()) {
/* 118 */       getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
/* 119 */     } else if (dst.nioBufferCount() > 0) {
/* 120 */       for (ByteBuffer bb : dst.nioBuffers(dstIndex, length)) {
/* 121 */         int bbLen = bb.remaining();
/* 122 */         getBytes(index, bb);
/* 123 */         index += bbLen;
/*     */       } 
/*     */     } else {
/* 126 */       dst.setBytes(dstIndex, this, index, length);
/*     */     } 
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 133 */     checkDstIndex(index, length, dstIndex, dst.length);
/*     */     
/* 135 */     if (dstIndex < 0 || dstIndex > dst.length - length) {
/* 136 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(dstIndex), Integer.valueOf(length), Integer.valueOf(dst.length) }));
/*     */     }
/*     */ 
/*     */     
/* 140 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 141 */     tmpBuf.clear().position(index).limit(index + length);
/* 142 */     tmpBuf.get(dst, dstIndex, length);
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 148 */     checkIndex(index);
/* 149 */     if (dst == null) {
/* 150 */       throw new NullPointerException("dst");
/*     */     }
/*     */     
/* 153 */     int bytesToCopy = Math.min(capacity() - index, dst.remaining());
/* 154 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 155 */     tmpBuf.clear().position(index).limit(index + bytesToCopy);
/* 156 */     dst.put(tmpBuf);
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 162 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 167 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 172 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 177 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 182 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 187 */     return maxCapacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/* 192 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 197 */     return this.allocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/* 202 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf unwrap() {
/* 207 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 212 */     return this.buffer.isDirect();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 217 */     ensureAccessible();
/* 218 */     if (length == 0) {
/* 219 */       return this;
/*     */     }
/*     */     
/* 222 */     if (this.buffer.hasArray()) {
/* 223 */       out.write(this.buffer.array(), index + this.buffer.arrayOffset(), length);
/*     */     } else {
/* 225 */       byte[] tmp = new byte[length];
/* 226 */       ByteBuffer tmpBuf = internalNioBuffer();
/* 227 */       tmpBuf.clear().position(index);
/* 228 */       tmpBuf.get(tmp);
/* 229 */       out.write(tmp);
/*     */     } 
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 236 */     ensureAccessible();
/* 237 */     if (length == 0) {
/* 238 */       return 0;
/*     */     }
/*     */     
/* 241 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 242 */     tmpBuf.clear().position(index).limit(index + length);
/* 243 */     return out.write(tmpBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 248 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 253 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 258 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 263 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 268 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected final ByteBuffer internalNioBuffer() {
/* 272 */     ByteBuffer tmpNioBuf = this.tmpNioBuf;
/* 273 */     if (tmpNioBuf == null) {
/* 274 */       this.tmpNioBuf = tmpNioBuf = this.buffer.duplicate();
/*     */     }
/* 276 */     return tmpNioBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/*     */     ByteBuffer src;
/* 281 */     ensureAccessible();
/*     */     
/*     */     try {
/* 284 */       src = (ByteBuffer)internalNioBuffer().clear().position(index).limit(index + length);
/* 285 */     } catch (IllegalArgumentException e) {
/* 286 */       throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (index + length));
/*     */     } 
/*     */     
/* 289 */     ByteBuffer dst = ByteBuffer.allocateDirect(length);
/* 290 */     dst.put(src);
/* 291 */     dst.order(order());
/* 292 */     dst.clear();
/* 293 */     return new UnpooledDirectByteBuf(alloc(), dst, maxCapacity());
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 298 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 303 */     return new ByteBuffer[] { nioBuffer(index, length) };
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 308 */     return (ByteBuffer)this.buffer.duplicate().position(index).limit(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 313 */     ensureAccessible();
/* 314 */     return (ByteBuffer)internalNioBuffer().clear().position(index).limit(index + length);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 319 */     return this.buffer.hasArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 324 */     return this.buffer.array();
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 329 */     return this.buffer.arrayOffset();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 334 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 339 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\ReadOnlyByteBufferBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */