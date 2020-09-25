/*      */ package net.minecraft.util.io.netty.buffer;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.nio.charset.Charset;
/*      */ import net.minecraft.util.io.netty.util.IllegalReferenceCountException;
/*      */ import net.minecraft.util.io.netty.util.ResourceLeakDetector;
/*      */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractByteBuf
/*      */   extends ByteBuf
/*      */ {
/*   37 */   static final ResourceLeakDetector<ByteBuf> leakDetector = new ResourceLeakDetector(ByteBuf.class);
/*      */   
/*      */   int readerIndex;
/*      */   
/*      */   private int writerIndex;
/*      */   
/*      */   private int markedReaderIndex;
/*      */   private int markedWriterIndex;
/*      */   private int maxCapacity;
/*      */   private SwappedByteBuf swappedBuf;
/*      */   
/*      */   protected AbstractByteBuf(int maxCapacity) {
/*   49 */     if (maxCapacity < 0) {
/*   50 */       throw new IllegalArgumentException("maxCapacity: " + maxCapacity + " (expected: >= 0)");
/*      */     }
/*   52 */     this.maxCapacity = maxCapacity;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxCapacity() {
/*   57 */     return this.maxCapacity;
/*      */   }
/*      */   
/*      */   protected final void maxCapacity(int maxCapacity) {
/*   61 */     this.maxCapacity = maxCapacity;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readerIndex() {
/*   66 */     return this.readerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readerIndex(int readerIndex) {
/*   71 */     if (readerIndex < 0 || readerIndex > this.writerIndex) {
/*   72 */       throw new IndexOutOfBoundsException(String.format("readerIndex: %d (expected: 0 <= readerIndex <= writerIndex(%d))", new Object[] { Integer.valueOf(readerIndex), Integer.valueOf(this.writerIndex) }));
/*      */     }
/*      */     
/*   75 */     this.readerIndex = readerIndex;
/*   76 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writerIndex() {
/*   81 */     return this.writerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writerIndex(int writerIndex) {
/*   86 */     if (writerIndex < this.readerIndex || writerIndex > capacity()) {
/*   87 */       throw new IndexOutOfBoundsException(String.format("writerIndex: %d (expected: readerIndex(%d) <= writerIndex <= capacity(%d))", new Object[] { Integer.valueOf(writerIndex), Integer.valueOf(this.readerIndex), Integer.valueOf(capacity()) }));
/*      */     }
/*      */ 
/*      */     
/*   91 */     this.writerIndex = writerIndex;
/*   92 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIndex(int readerIndex, int writerIndex) {
/*   97 */     if (readerIndex < 0 || readerIndex > writerIndex || writerIndex > capacity()) {
/*   98 */       throw new IndexOutOfBoundsException(String.format("readerIndex: %d, writerIndex: %d (expected: 0 <= readerIndex <= writerIndex <= capacity(%d))", new Object[] { Integer.valueOf(readerIndex), Integer.valueOf(writerIndex), Integer.valueOf(capacity()) }));
/*      */     }
/*      */ 
/*      */     
/*  102 */     this.readerIndex = readerIndex;
/*  103 */     this.writerIndex = writerIndex;
/*  104 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf clear() {
/*  109 */     this.readerIndex = this.writerIndex = 0;
/*  110 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable() {
/*  115 */     return (this.writerIndex > this.readerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable(int numBytes) {
/*  120 */     return (this.writerIndex - this.readerIndex >= numBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable() {
/*  125 */     return (capacity() > this.writerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable(int numBytes) {
/*  130 */     return (capacity() - this.writerIndex >= numBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readableBytes() {
/*  135 */     return this.writerIndex - this.readerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writableBytes() {
/*  140 */     return capacity() - this.writerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxWritableBytes() {
/*  145 */     return maxCapacity() - this.writerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markReaderIndex() {
/*  150 */     this.markedReaderIndex = this.readerIndex;
/*  151 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetReaderIndex() {
/*  156 */     readerIndex(this.markedReaderIndex);
/*  157 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markWriterIndex() {
/*  162 */     this.markedWriterIndex = this.writerIndex;
/*  163 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetWriterIndex() {
/*  168 */     this.writerIndex = this.markedWriterIndex;
/*  169 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardReadBytes() {
/*  174 */     ensureAccessible();
/*  175 */     if (this.readerIndex == 0) {
/*  176 */       return this;
/*      */     }
/*      */     
/*  179 */     if (this.readerIndex != this.writerIndex) {
/*  180 */       setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
/*  181 */       this.writerIndex -= this.readerIndex;
/*  182 */       adjustMarkers(this.readerIndex);
/*  183 */       this.readerIndex = 0;
/*      */     } else {
/*  185 */       adjustMarkers(this.readerIndex);
/*  186 */       this.writerIndex = this.readerIndex = 0;
/*      */     } 
/*  188 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardSomeReadBytes() {
/*  193 */     ensureAccessible();
/*  194 */     if (this.readerIndex == 0) {
/*  195 */       return this;
/*      */     }
/*      */     
/*  198 */     if (this.readerIndex == this.writerIndex) {
/*  199 */       adjustMarkers(this.readerIndex);
/*  200 */       this.writerIndex = this.readerIndex = 0;
/*  201 */       return this;
/*      */     } 
/*      */     
/*  204 */     if (this.readerIndex >= capacity() >>> 1) {
/*  205 */       setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
/*  206 */       this.writerIndex -= this.readerIndex;
/*  207 */       adjustMarkers(this.readerIndex);
/*  208 */       this.readerIndex = 0;
/*      */     } 
/*  210 */     return this;
/*      */   }
/*      */   
/*      */   protected final void adjustMarkers(int decrement) {
/*  214 */     int markedReaderIndex = this.markedReaderIndex;
/*  215 */     if (markedReaderIndex <= decrement) {
/*  216 */       this.markedReaderIndex = 0;
/*  217 */       int markedWriterIndex = this.markedWriterIndex;
/*  218 */       if (markedWriterIndex <= decrement) {
/*  219 */         this.markedWriterIndex = 0;
/*      */       } else {
/*  221 */         this.markedWriterIndex = markedWriterIndex - decrement;
/*      */       } 
/*      */     } else {
/*  224 */       this.markedReaderIndex = markedReaderIndex - decrement;
/*  225 */       this.markedWriterIndex -= decrement;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf ensureWritable(int minWritableBytes) {
/*  231 */     if (minWritableBytes < 0) {
/*  232 */       throw new IllegalArgumentException(String.format("minWritableBytes: %d (expected: >= 0)", new Object[] { Integer.valueOf(minWritableBytes) }));
/*      */     }
/*      */ 
/*      */     
/*  236 */     if (minWritableBytes <= writableBytes()) {
/*  237 */       return this;
/*      */     }
/*      */     
/*  240 */     if (minWritableBytes > this.maxCapacity - this.writerIndex) {
/*  241 */       throw new IndexOutOfBoundsException(String.format("writerIndex(%d) + minWritableBytes(%d) exceeds maxCapacity(%d): %s", new Object[] { Integer.valueOf(this.writerIndex), Integer.valueOf(minWritableBytes), Integer.valueOf(this.maxCapacity), this }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  247 */     int newCapacity = calculateNewCapacity(this.writerIndex + minWritableBytes);
/*      */ 
/*      */     
/*  250 */     capacity(newCapacity);
/*  251 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int ensureWritable(int minWritableBytes, boolean force) {
/*  256 */     if (minWritableBytes < 0) {
/*  257 */       throw new IllegalArgumentException(String.format("minWritableBytes: %d (expected: >= 0)", new Object[] { Integer.valueOf(minWritableBytes) }));
/*      */     }
/*      */ 
/*      */     
/*  261 */     if (minWritableBytes <= writableBytes()) {
/*  262 */       return 0;
/*      */     }
/*      */     
/*  265 */     if (minWritableBytes > this.maxCapacity - this.writerIndex && 
/*  266 */       force) {
/*  267 */       if (capacity() == maxCapacity()) {
/*  268 */         return 1;
/*      */       }
/*      */       
/*  271 */       capacity(maxCapacity());
/*  272 */       return 3;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  277 */     int newCapacity = calculateNewCapacity(this.writerIndex + minWritableBytes);
/*      */ 
/*      */     
/*  280 */     capacity(newCapacity);
/*  281 */     return 2;
/*      */   }
/*      */   
/*      */   private int calculateNewCapacity(int minNewCapacity) {
/*  285 */     int maxCapacity = this.maxCapacity;
/*  286 */     int threshold = 4194304;
/*      */     
/*  288 */     if (minNewCapacity == 4194304) {
/*  289 */       return 4194304;
/*      */     }
/*      */ 
/*      */     
/*  293 */     if (minNewCapacity > 4194304) {
/*  294 */       int i = minNewCapacity / 4194304 * 4194304;
/*  295 */       if (i > maxCapacity - 4194304) {
/*  296 */         i = maxCapacity;
/*      */       } else {
/*  298 */         i += 4194304;
/*      */       } 
/*  300 */       return i;
/*      */     } 
/*      */ 
/*      */     
/*  304 */     int newCapacity = 64;
/*  305 */     while (newCapacity < minNewCapacity) {
/*  306 */       newCapacity <<= 1;
/*      */     }
/*      */     
/*  309 */     return Math.min(newCapacity, maxCapacity);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf order(ByteOrder endianness) {
/*  314 */     if (endianness == null) {
/*  315 */       throw new NullPointerException("endianness");
/*      */     }
/*  317 */     if (endianness == order()) {
/*  318 */       return this;
/*      */     }
/*      */     
/*  321 */     SwappedByteBuf swappedBuf = this.swappedBuf;
/*  322 */     if (swappedBuf == null) {
/*  323 */       this.swappedBuf = swappedBuf = new SwappedByteBuf(this);
/*      */     }
/*  325 */     return swappedBuf;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int index) {
/*  330 */     checkIndex(index);
/*  331 */     return _getByte(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int index) {
/*  338 */     return (getByte(index) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getUnsignedByte(int index) {
/*  343 */     return (short)(getByte(index) & 0xFF);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int index) {
/*  348 */     checkIndex(index, 2);
/*  349 */     return _getShort(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUnsignedShort(int index) {
/*  356 */     return getShort(index) & 0xFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMedium(int index) {
/*  361 */     checkIndex(index, 3);
/*  362 */     return _getUnsignedMedium(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMedium(int index) {
/*  369 */     int value = getUnsignedMedium(index);
/*  370 */     if ((value & 0x800000) != 0) {
/*  371 */       value |= 0xFF000000;
/*      */     }
/*  373 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int index) {
/*  378 */     checkIndex(index, 4);
/*  379 */     return _getInt(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getUnsignedInt(int index) {
/*  386 */     return getInt(index) & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int index) {
/*  391 */     checkIndex(index, 8);
/*  392 */     return _getLong(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getChar(int index) {
/*  399 */     return (char)getShort(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int index) {
/*  404 */     return Float.intBitsToFloat(getInt(index));
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int index) {
/*  409 */     return Double.longBitsToDouble(getLong(index));
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst) {
/*  414 */     getBytes(index, dst, 0, dst.length);
/*  415 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst) {
/*  420 */     getBytes(index, dst, dst.writableBytes());
/*  421 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/*  426 */     getBytes(index, dst, dst.writerIndex(), length);
/*  427 */     dst.writerIndex(dst.writerIndex() + length);
/*  428 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setByte(int index, int value) {
/*  433 */     checkIndex(index);
/*  434 */     _setByte(index, value);
/*  435 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setBoolean(int index, boolean value) {
/*  442 */     setByte(index, value ? 1 : 0);
/*  443 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShort(int index, int value) {
/*  448 */     checkIndex(index, 2);
/*  449 */     _setShort(index, value);
/*  450 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setChar(int index, int value) {
/*  457 */     setShort(index, value);
/*  458 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMedium(int index, int value) {
/*  463 */     checkIndex(index, 3);
/*  464 */     _setMedium(index, value);
/*  465 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setInt(int index, int value) {
/*  472 */     checkIndex(index, 4);
/*  473 */     _setInt(index, value);
/*  474 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setFloat(int index, float value) {
/*  481 */     setInt(index, Float.floatToRawIntBits(value));
/*  482 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLong(int index, long value) {
/*  487 */     checkIndex(index, 8);
/*  488 */     _setLong(index, value);
/*  489 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setDouble(int index, double value) {
/*  496 */     setLong(index, Double.doubleToRawLongBits(value));
/*  497 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src) {
/*  502 */     setBytes(index, src, 0, src.length);
/*  503 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src) {
/*  508 */     setBytes(index, src, src.readableBytes());
/*  509 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/*  514 */     checkIndex(index, length);
/*  515 */     if (src == null) {
/*  516 */       throw new NullPointerException("src");
/*      */     }
/*  518 */     if (length > src.readableBytes()) {
/*  519 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", new Object[] { Integer.valueOf(length), Integer.valueOf(src.readableBytes()), src }));
/*      */     }
/*      */ 
/*      */     
/*  523 */     setBytes(index, src, src.readerIndex(), length);
/*  524 */     src.readerIndex(src.readerIndex() + length);
/*  525 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setZero(int index, int length) {
/*  530 */     if (length == 0) {
/*  531 */       return this;
/*      */     }
/*      */     
/*  534 */     checkIndex(index, length);
/*      */     
/*  536 */     int nLong = length >>> 3;
/*  537 */     int nBytes = length & 0x7; int i;
/*  538 */     for (i = nLong; i > 0; i--) {
/*  539 */       setLong(index, 0L);
/*  540 */       index += 8;
/*      */     } 
/*  542 */     if (nBytes == 4) {
/*  543 */       setInt(index, 0);
/*  544 */     } else if (nBytes < 4) {
/*  545 */       for (i = nBytes; i > 0; i--) {
/*  546 */         setByte(index, 0);
/*  547 */         index++;
/*      */       } 
/*      */     } else {
/*  550 */       setInt(index, 0);
/*  551 */       index += 4;
/*  552 */       for (i = nBytes - 4; i > 0; i--) {
/*  553 */         setByte(index, 0);
/*  554 */         index++;
/*      */       } 
/*      */     } 
/*  557 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte readByte() {
/*  562 */     checkReadableBytes(1);
/*  563 */     int i = this.readerIndex;
/*  564 */     byte b = getByte(i);
/*  565 */     this.readerIndex = i + 1;
/*  566 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean readBoolean() {
/*  571 */     return (readByte() != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short readUnsignedByte() {
/*  576 */     return (short)(readByte() & 0xFF);
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShort() {
/*  581 */     checkReadableBytes(2);
/*  582 */     short v = _getShort(this.readerIndex);
/*  583 */     this.readerIndex += 2;
/*  584 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShort() {
/*  589 */     return readShort() & 0xFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMedium() {
/*  594 */     int value = readUnsignedMedium();
/*  595 */     if ((value & 0x800000) != 0) {
/*  596 */       value |= 0xFF000000;
/*      */     }
/*  598 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMedium() {
/*  603 */     checkReadableBytes(3);
/*  604 */     int v = _getUnsignedMedium(this.readerIndex);
/*  605 */     this.readerIndex += 3;
/*  606 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt() {
/*  611 */     checkReadableBytes(4);
/*  612 */     int v = _getInt(this.readerIndex);
/*  613 */     this.readerIndex += 4;
/*  614 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedInt() {
/*  619 */     return readInt() & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLong() {
/*  624 */     checkReadableBytes(8);
/*  625 */     long v = _getLong(this.readerIndex);
/*  626 */     this.readerIndex += 8;
/*  627 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public char readChar() {
/*  632 */     return (char)readShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() {
/*  637 */     return Float.intBitsToFloat(readInt());
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() {
/*  642 */     return Double.longBitsToDouble(readLong());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(int length) {
/*  647 */     checkReadableBytes(length);
/*  648 */     if (length == 0) {
/*  649 */       return Unpooled.EMPTY_BUFFER;
/*      */     }
/*      */ 
/*      */     
/*  653 */     ByteBuf buf = Unpooled.buffer(length, this.maxCapacity);
/*  654 */     buf.writeBytes(this, this.readerIndex, length);
/*  655 */     this.readerIndex += length;
/*  656 */     return buf;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readSlice(int length) {
/*  661 */     ByteBuf slice = slice(this.readerIndex, length);
/*  662 */     this.readerIndex += length;
/*  663 */     return slice;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/*  668 */     checkReadableBytes(length);
/*  669 */     getBytes(this.readerIndex, dst, dstIndex, length);
/*  670 */     this.readerIndex += length;
/*  671 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst) {
/*  676 */     readBytes(dst, 0, dst.length);
/*  677 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst) {
/*  682 */     readBytes(dst, dst.writableBytes());
/*  683 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int length) {
/*  688 */     if (length > dst.writableBytes()) {
/*  689 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds dst.writableBytes(%d) where dst is: %s", new Object[] { Integer.valueOf(length), Integer.valueOf(dst.writableBytes()), dst }));
/*      */     }
/*      */     
/*  692 */     readBytes(dst, dst.writerIndex(), length);
/*  693 */     dst.writerIndex(dst.writerIndex() + length);
/*  694 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/*  699 */     checkReadableBytes(length);
/*  700 */     getBytes(this.readerIndex, dst, dstIndex, length);
/*  701 */     this.readerIndex += length;
/*  702 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer dst) {
/*  707 */     int length = dst.remaining();
/*  708 */     checkReadableBytes(length);
/*  709 */     getBytes(this.readerIndex, dst);
/*  710 */     this.readerIndex += length;
/*  711 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/*  717 */     checkReadableBytes(length);
/*  718 */     int readBytes = getBytes(this.readerIndex, out, length);
/*  719 */     this.readerIndex += readBytes;
/*  720 */     return readBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/*  725 */     checkReadableBytes(length);
/*  726 */     getBytes(this.readerIndex, out, length);
/*  727 */     this.readerIndex += length;
/*  728 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf skipBytes(int length) {
/*  733 */     checkReadableBytes(length);
/*      */     
/*  735 */     int newReaderIndex = this.readerIndex + length;
/*  736 */     if (newReaderIndex > this.writerIndex) {
/*  737 */       throw new IndexOutOfBoundsException(String.format("length: %d (expected: readerIndex(%d) + length <= writerIndex(%d))", new Object[] { Integer.valueOf(length), Integer.valueOf(this.readerIndex), Integer.valueOf(this.writerIndex) }));
/*      */     }
/*      */ 
/*      */     
/*  741 */     this.readerIndex = newReaderIndex;
/*  742 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBoolean(boolean value) {
/*  747 */     writeByte(value ? 1 : 0);
/*  748 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeByte(int value) {
/*  753 */     ensureWritable(1);
/*  754 */     setByte(this.writerIndex++, value);
/*  755 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShort(int value) {
/*  760 */     ensureWritable(2);
/*  761 */     _setShort(this.writerIndex, value);
/*  762 */     this.writerIndex += 2;
/*  763 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMedium(int value) {
/*  768 */     ensureWritable(3);
/*  769 */     _setMedium(this.writerIndex, value);
/*  770 */     this.writerIndex += 3;
/*  771 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeInt(int value) {
/*  776 */     ensureWritable(4);
/*  777 */     _setInt(this.writerIndex, value);
/*  778 */     this.writerIndex += 4;
/*  779 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLong(long value) {
/*  784 */     ensureWritable(8);
/*  785 */     _setLong(this.writerIndex, value);
/*  786 */     this.writerIndex += 8;
/*  787 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeChar(int value) {
/*  792 */     writeShort(value);
/*  793 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeFloat(float value) {
/*  798 */     writeInt(Float.floatToRawIntBits(value));
/*  799 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeDouble(double value) {
/*  804 */     writeLong(Double.doubleToRawLongBits(value));
/*  805 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/*  810 */     ensureWritable(length);
/*  811 */     setBytes(this.writerIndex, src, srcIndex, length);
/*  812 */     this.writerIndex += length;
/*  813 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src) {
/*  818 */     writeBytes(src, 0, src.length);
/*  819 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src) {
/*  824 */     writeBytes(src, src.readableBytes());
/*  825 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int length) {
/*  830 */     if (length > src.readableBytes()) {
/*  831 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", new Object[] { Integer.valueOf(length), Integer.valueOf(src.readableBytes()), src }));
/*      */     }
/*      */     
/*  834 */     writeBytes(src, src.readerIndex(), length);
/*  835 */     src.readerIndex(src.readerIndex() + length);
/*  836 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/*  841 */     ensureWritable(length);
/*  842 */     setBytes(this.writerIndex, src, srcIndex, length);
/*  843 */     this.writerIndex += length;
/*  844 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer src) {
/*  849 */     int length = src.remaining();
/*  850 */     ensureWritable(length);
/*  851 */     setBytes(this.writerIndex, src);
/*  852 */     this.writerIndex += length;
/*  853 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int writeBytes(InputStream in, int length) throws IOException {
/*  859 */     ensureWritable(length);
/*  860 */     int writtenBytes = setBytes(this.writerIndex, in, length);
/*  861 */     if (writtenBytes > 0) {
/*  862 */       this.writerIndex += writtenBytes;
/*      */     }
/*  864 */     return writtenBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
/*  869 */     ensureWritable(length);
/*  870 */     int writtenBytes = setBytes(this.writerIndex, in, length);
/*  871 */     if (writtenBytes > 0) {
/*  872 */       this.writerIndex += writtenBytes;
/*      */     }
/*  874 */     return writtenBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeZero(int length) {
/*  879 */     if (length == 0) {
/*  880 */       return this;
/*      */     }
/*      */     
/*  883 */     ensureWritable(length);
/*  884 */     checkIndex(this.writerIndex, length);
/*      */     
/*  886 */     int nLong = length >>> 3;
/*  887 */     int nBytes = length & 0x7; int i;
/*  888 */     for (i = nLong; i > 0; i--) {
/*  889 */       writeLong(0L);
/*      */     }
/*  891 */     if (nBytes == 4) {
/*  892 */       writeInt(0);
/*  893 */     } else if (nBytes < 4) {
/*  894 */       for (i = nBytes; i > 0; i--) {
/*  895 */         writeByte(0);
/*      */       }
/*      */     } else {
/*  898 */       writeInt(0);
/*  899 */       for (i = nBytes - 4; i > 0; i--) {
/*  900 */         writeByte(0);
/*      */       }
/*      */     } 
/*  903 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy() {
/*  908 */     return copy(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf duplicate() {
/*  913 */     return new DuplicatedByteBuf(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice() {
/*  918 */     return slice(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice(int index, int length) {
/*  923 */     if (length == 0) {
/*  924 */       return Unpooled.EMPTY_BUFFER;
/*      */     }
/*      */     
/*  927 */     return new SlicedByteBuf(this, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer() {
/*  932 */     return nioBuffer(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/*  937 */     return nioBuffers(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(Charset charset) {
/*  942 */     return toString(this.readerIndex, readableBytes(), charset);
/*      */   }
/*      */   
/*      */   public String toString(int index, int length, Charset charset) {
/*      */     ByteBuffer nioBuffer;
/*  947 */     if (length == 0) {
/*  948 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  952 */     if (nioBufferCount() == 1) {
/*  953 */       nioBuffer = nioBuffer(index, length);
/*      */     } else {
/*  955 */       nioBuffer = ByteBuffer.allocate(length);
/*  956 */       getBytes(index, nioBuffer);
/*  957 */       nioBuffer.flip();
/*      */     } 
/*      */     
/*  960 */     return ByteBufUtil.decodeString(nioBuffer, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(int fromIndex, int toIndex, byte value) {
/*  965 */     return ByteBufUtil.indexOf(this, fromIndex, toIndex, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(byte value) {
/*  970 */     return bytesBefore(readerIndex(), readableBytes(), value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int length, byte value) {
/*  975 */     checkReadableBytes(length);
/*  976 */     return bytesBefore(readerIndex(), length, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int index, int length, byte value) {
/*  981 */     int endIndex = indexOf(index, index + length, value);
/*  982 */     if (endIndex < 0) {
/*  983 */       return -1;
/*      */     }
/*  985 */     return endIndex - index;
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteBufProcessor processor) {
/*  990 */     int index = this.readerIndex;
/*  991 */     int length = this.writerIndex - index;
/*  992 */     return forEachByteAsc0(index, length, processor);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(int index, int length, ByteBufProcessor processor) {
/*  997 */     checkIndex(index, length);
/*  998 */     return forEachByteAsc0(index, length, processor);
/*      */   }
/*      */   
/*      */   private int forEachByteAsc0(int index, int length, ByteBufProcessor processor) {
/* 1002 */     if (processor == null) {
/* 1003 */       throw new NullPointerException("processor");
/*      */     }
/*      */     
/* 1006 */     if (length == 0) {
/* 1007 */       return -1;
/*      */     }
/*      */     
/* 1010 */     int endIndex = index + length;
/* 1011 */     int i = index;
/*      */     try {
/*      */       do {
/* 1014 */         if (processor.process(_getByte(i))) {
/* 1015 */           i++;
/*      */         } else {
/* 1017 */           return i;
/*      */         } 
/* 1019 */       } while (i < endIndex);
/* 1020 */     } catch (Exception e) {
/* 1021 */       PlatformDependent.throwException(e);
/*      */     } 
/*      */     
/* 1024 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteBufProcessor processor) {
/* 1029 */     int index = this.readerIndex;
/* 1030 */     int length = this.writerIndex - index;
/* 1031 */     return forEachByteDesc0(index, length, processor);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int index, int length, ByteBufProcessor processor) {
/* 1036 */     checkIndex(index, length);
/* 1037 */     return forEachByteDesc0(index, length, processor);
/*      */   }
/*      */   
/*      */   private int forEachByteDesc0(int index, int length, ByteBufProcessor processor) {
/* 1041 */     if (processor == null) {
/* 1042 */       throw new NullPointerException("processor");
/*      */     }
/*      */     
/* 1045 */     if (length == 0) {
/* 1046 */       return -1;
/*      */     }
/*      */     
/* 1049 */     int i = index + length - 1;
/*      */     try {
/*      */       do {
/* 1052 */         if (processor.process(_getByte(i))) {
/* 1053 */           i--;
/*      */         } else {
/* 1055 */           return i;
/*      */         } 
/* 1057 */       } while (i >= index);
/* 1058 */     } catch (Exception e) {
/* 1059 */       PlatformDependent.throwException(e);
/*      */     } 
/*      */     
/* 1062 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1067 */     return ByteBufUtil.hashCode(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1072 */     if (this == o) {
/* 1073 */       return true;
/*      */     }
/* 1075 */     if (o instanceof ByteBuf) {
/* 1076 */       return ByteBufUtil.equals(this, (ByteBuf)o);
/*      */     }
/* 1078 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(ByteBuf that) {
/* 1083 */     return ByteBufUtil.compare(this, that);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1088 */     if (refCnt() == 0) {
/* 1089 */       return getClass().getSimpleName() + "(freed)";
/*      */     }
/*      */     
/* 1092 */     StringBuilder buf = new StringBuilder();
/* 1093 */     buf.append(getClass().getSimpleName());
/* 1094 */     buf.append("(ridx: ");
/* 1095 */     buf.append(this.readerIndex);
/* 1096 */     buf.append(", widx: ");
/* 1097 */     buf.append(this.writerIndex);
/* 1098 */     buf.append(", cap: ");
/* 1099 */     buf.append(capacity());
/* 1100 */     if (this.maxCapacity != Integer.MAX_VALUE) {
/* 1101 */       buf.append('/');
/* 1102 */       buf.append(this.maxCapacity);
/*      */     } 
/*      */     
/* 1105 */     ByteBuf unwrapped = unwrap();
/* 1106 */     if (unwrapped != null) {
/* 1107 */       buf.append(", unwrapped: ");
/* 1108 */       buf.append(unwrapped);
/*      */     } 
/* 1110 */     buf.append(')');
/* 1111 */     return buf.toString();
/*      */   }
/*      */   
/*      */   protected final void checkIndex(int index) {
/* 1115 */     ensureAccessible();
/* 1116 */     if (index < 0 || index >= capacity()) {
/* 1117 */       throw new IndexOutOfBoundsException(String.format("index: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(index), Integer.valueOf(capacity()) }));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void checkIndex(int index, int fieldLength) {
/* 1123 */     ensureAccessible();
/* 1124 */     if (fieldLength < 0) {
/* 1125 */       throw new IllegalArgumentException("length: " + fieldLength + " (expected: >= 0)");
/*      */     }
/* 1127 */     if (index < 0 || index > capacity() - fieldLength) {
/* 1128 */       throw new IndexOutOfBoundsException(String.format("index: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(index), Integer.valueOf(fieldLength), Integer.valueOf(capacity()) }));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void checkSrcIndex(int index, int length, int srcIndex, int srcCapacity) {
/* 1134 */     checkIndex(index, length);
/* 1135 */     if (srcIndex < 0 || srcIndex > srcCapacity - length) {
/* 1136 */       throw new IndexOutOfBoundsException(String.format("srcIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(srcIndex), Integer.valueOf(length), Integer.valueOf(srcCapacity) }));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void checkDstIndex(int index, int length, int dstIndex, int dstCapacity) {
/* 1142 */     checkIndex(index, length);
/* 1143 */     if (dstIndex < 0 || dstIndex > dstCapacity - length) {
/* 1144 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(dstIndex), Integer.valueOf(length), Integer.valueOf(dstCapacity) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void checkReadableBytes(int minimumReadableBytes) {
/* 1155 */     ensureAccessible();
/* 1156 */     if (minimumReadableBytes < 0) {
/* 1157 */       throw new IllegalArgumentException("minimumReadableBytes: " + minimumReadableBytes + " (expected: >= 0)");
/*      */     }
/* 1159 */     if (this.readerIndex > this.writerIndex - minimumReadableBytes) {
/* 1160 */       throw new IndexOutOfBoundsException(String.format("readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s", new Object[] { Integer.valueOf(this.readerIndex), Integer.valueOf(minimumReadableBytes), Integer.valueOf(this.writerIndex), this }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void ensureAccessible() {
/* 1171 */     if (refCnt() == 0)
/* 1172 */       throw new IllegalReferenceCountException(0); 
/*      */   }
/*      */   
/*      */   protected abstract byte _getByte(int paramInt);
/*      */   
/*      */   protected abstract short _getShort(int paramInt);
/*      */   
/*      */   protected abstract int _getUnsignedMedium(int paramInt);
/*      */   
/*      */   protected abstract int _getInt(int paramInt);
/*      */   
/*      */   protected abstract long _getLong(int paramInt);
/*      */   
/*      */   protected abstract void _setByte(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setShort(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setMedium(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setInt(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setLong(int paramInt, long paramLong);
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\AbstractByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */