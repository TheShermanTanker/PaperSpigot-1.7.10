/*      */ package net.minecraft.util.io.netty.buffer;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import net.minecraft.util.io.netty.util.ReferenceCounted;
/*      */ import net.minecraft.util.io.netty.util.ResourceLeak;
/*      */ import net.minecraft.util.io.netty.util.internal.EmptyArrays;
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
/*      */ public class CompositeByteBuf
/*      */   extends AbstractReferenceCountedByteBuf
/*      */ {
/*      */   private final ResourceLeak leak;
/*      */   private final ByteBufAllocator alloc;
/*      */   private final boolean direct;
/*   45 */   private final List<Component> components = new ArrayList<Component>();
/*      */   private final int maxNumComponents;
/*   47 */   private static final ByteBuffer FULL_BYTEBUFFER = (ByteBuffer)ByteBuffer.allocate(1).position(1);
/*      */   
/*      */   private boolean freed;
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents) {
/*   52 */     super(2147483647);
/*   53 */     if (alloc == null) {
/*   54 */       throw new NullPointerException("alloc");
/*      */     }
/*   56 */     this.alloc = alloc;
/*   57 */     this.direct = direct;
/*   58 */     this.maxNumComponents = maxNumComponents;
/*   59 */     this.leak = leakDetector.open(this);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, ByteBuf... buffers) {
/*   63 */     super(2147483647);
/*   64 */     if (alloc == null) {
/*   65 */       throw new NullPointerException("alloc");
/*      */     }
/*   67 */     if (maxNumComponents < 2) {
/*   68 */       throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents + " (expected: >= 2)");
/*      */     }
/*      */ 
/*      */     
/*   72 */     this.alloc = alloc;
/*   73 */     this.direct = direct;
/*   74 */     this.maxNumComponents = maxNumComponents;
/*      */     
/*   76 */     addComponents0(0, buffers);
/*   77 */     consolidateIfNeeded();
/*   78 */     setIndex(0, capacity());
/*   79 */     this.leak = leakDetector.open(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, Iterable<ByteBuf> buffers) {
/*   84 */     super(2147483647);
/*   85 */     if (alloc == null) {
/*   86 */       throw new NullPointerException("alloc");
/*      */     }
/*   88 */     if (maxNumComponents < 2) {
/*   89 */       throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents + " (expected: >= 2)");
/*      */     }
/*      */ 
/*      */     
/*   93 */     this.alloc = alloc;
/*   94 */     this.direct = direct;
/*   95 */     this.maxNumComponents = maxNumComponents;
/*   96 */     addComponents0(0, buffers);
/*   97 */     consolidateIfNeeded();
/*   98 */     setIndex(0, capacity());
/*   99 */     this.leak = leakDetector.open(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponent(ByteBuf buffer) {
/*  111 */     addComponent0(this.components.size(), buffer);
/*  112 */     consolidateIfNeeded();
/*  113 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponents(ByteBuf... buffers) {
/*  125 */     addComponents0(this.components.size(), buffers);
/*  126 */     consolidateIfNeeded();
/*  127 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf addComponents(Iterable<ByteBuf> buffers) {
/*  139 */     addComponents0(this.components.size(), buffers);
/*  140 */     consolidateIfNeeded();
/*  141 */     return this;
/*      */   }
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
/*      */   public CompositeByteBuf addComponent(int cIndex, ByteBuf buffer) {
/*  154 */     addComponent0(cIndex, buffer);
/*  155 */     consolidateIfNeeded();
/*  156 */     return this;
/*      */   }
/*      */   
/*      */   private int addComponent0(int cIndex, ByteBuf buffer) {
/*  160 */     checkComponentIndex(cIndex);
/*      */     
/*  162 */     if (buffer == null) {
/*  163 */       throw new NullPointerException("buffer");
/*      */     }
/*      */     
/*  166 */     int readableBytes = buffer.readableBytes();
/*  167 */     if (readableBytes == 0) {
/*  168 */       return cIndex;
/*      */     }
/*      */ 
/*      */     
/*  172 */     Component c = new Component(buffer.order(ByteOrder.BIG_ENDIAN).slice());
/*  173 */     if (cIndex == this.components.size()) {
/*  174 */       this.components.add(c);
/*  175 */       if (cIndex == 0) {
/*  176 */         c.endOffset = readableBytes;
/*      */       } else {
/*  178 */         Component prev = this.components.get(cIndex - 1);
/*  179 */         c.offset = prev.endOffset;
/*  180 */         c.endOffset = c.offset + readableBytes;
/*      */       } 
/*      */     } else {
/*  183 */       this.components.add(cIndex, c);
/*  184 */       updateComponentOffsets(cIndex);
/*      */     } 
/*  186 */     return cIndex;
/*      */   }
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
/*      */   public CompositeByteBuf addComponents(int cIndex, ByteBuf... buffers) {
/*  199 */     addComponents0(cIndex, buffers);
/*  200 */     consolidateIfNeeded();
/*  201 */     return this;
/*      */   }
/*      */   
/*      */   private int addComponents0(int cIndex, ByteBuf... buffers) {
/*  205 */     checkComponentIndex(cIndex);
/*      */     
/*  207 */     if (buffers == null) {
/*  208 */       throw new NullPointerException("buffers");
/*      */     }
/*      */     
/*  211 */     int readableBytes = 0;
/*  212 */     for (ByteBuf b : buffers) {
/*  213 */       if (b == null) {
/*      */         break;
/*      */       }
/*  216 */       readableBytes += b.readableBytes();
/*      */     } 
/*      */     
/*  219 */     if (readableBytes == 0) {
/*  220 */       return cIndex;
/*      */     }
/*      */ 
/*      */     
/*  224 */     for (ByteBuf b : buffers) {
/*  225 */       if (b == null) {
/*      */         break;
/*      */       }
/*  228 */       if (b.isReadable()) {
/*  229 */         cIndex = addComponent0(cIndex, b) + 1;
/*  230 */         int size = this.components.size();
/*  231 */         if (cIndex > size) {
/*  232 */           cIndex = size;
/*      */         }
/*      */       } else {
/*  235 */         b.release();
/*      */       } 
/*      */     } 
/*  238 */     return cIndex;
/*      */   }
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
/*      */   public CompositeByteBuf addComponents(int cIndex, Iterable<ByteBuf> buffers) {
/*  251 */     addComponents0(cIndex, buffers);
/*  252 */     consolidateIfNeeded();
/*  253 */     return this;
/*      */   }
/*      */   
/*      */   private int addComponents0(int cIndex, Iterable<ByteBuf> buffers) {
/*  257 */     if (buffers == null) {
/*  258 */       throw new NullPointerException("buffers");
/*      */     }
/*      */     
/*  261 */     if (buffers instanceof ByteBuf)
/*      */     {
/*  263 */       return addComponent0(cIndex, (ByteBuf)buffers);
/*      */     }
/*      */     
/*  266 */     if (!(buffers instanceof Collection)) {
/*  267 */       List<ByteBuf> list = new ArrayList<ByteBuf>();
/*  268 */       for (ByteBuf b : buffers) {
/*  269 */         list.add(b);
/*      */       }
/*  271 */       buffers = list;
/*      */     } 
/*      */     
/*  274 */     Collection<ByteBuf> col = (Collection<ByteBuf>)buffers;
/*  275 */     return addComponents0(cIndex, col.<ByteBuf>toArray(new ByteBuf[col.size()]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void consolidateIfNeeded() {
/*  285 */     int numComponents = this.components.size();
/*  286 */     if (numComponents > this.maxNumComponents) {
/*  287 */       int capacity = ((Component)this.components.get(numComponents - 1)).endOffset;
/*      */       
/*  289 */       ByteBuf consolidated = allocBuffer(capacity);
/*      */ 
/*      */ 
/*      */       
/*  293 */       for (int i = 0; i < numComponents; i++) {
/*  294 */         Component component = this.components.get(i);
/*  295 */         ByteBuf b = component.buf;
/*  296 */         consolidated.writeBytes(b);
/*  297 */         component.freeIfNecessary();
/*      */       } 
/*  299 */       Component c = new Component(consolidated);
/*  300 */       c.endOffset = c.length;
/*  301 */       this.components.clear();
/*  302 */       this.components.add(c);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkComponentIndex(int cIndex) {
/*  307 */     assert !this.freed;
/*  308 */     if (cIndex < 0 || cIndex > this.components.size()) {
/*  309 */       throw new IndexOutOfBoundsException(String.format("cIndex: %d (expected: >= 0 && <= numComponents(%d))", new Object[] { Integer.valueOf(cIndex), Integer.valueOf(this.components.size()) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkComponentIndex(int cIndex, int numComponents) {
/*  316 */     assert !this.freed;
/*  317 */     if (cIndex < 0 || cIndex + numComponents > this.components.size()) {
/*  318 */       throw new IndexOutOfBoundsException(String.format("cIndex: %d, numComponents: %d (expected: cIndex >= 0 && cIndex + numComponents <= totalNumComponents(%d))", new Object[] { Integer.valueOf(cIndex), Integer.valueOf(numComponents), Integer.valueOf(this.components.size()) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateComponentOffsets(int cIndex) {
/*  326 */     int size = this.components.size();
/*  327 */     if (size <= cIndex) {
/*      */       return;
/*      */     }
/*      */     
/*  331 */     Component c = this.components.get(cIndex);
/*  332 */     if (cIndex == 0) {
/*  333 */       c.offset = 0;
/*  334 */       c.endOffset = c.length;
/*  335 */       cIndex++;
/*      */     } 
/*      */     
/*  338 */     for (int i = cIndex; i < size; i++) {
/*  339 */       Component prev = this.components.get(i - 1);
/*  340 */       Component cur = this.components.get(i);
/*  341 */       cur.offset = prev.endOffset;
/*  342 */       cur.endOffset = cur.offset + cur.length;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf removeComponent(int cIndex) {
/*  352 */     checkComponentIndex(cIndex);
/*  353 */     ((Component)this.components.remove(cIndex)).freeIfNecessary();
/*  354 */     updateComponentOffsets(cIndex);
/*  355 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf removeComponents(int cIndex, int numComponents) {
/*  365 */     checkComponentIndex(cIndex, numComponents);
/*      */     
/*  367 */     List<Component> toRemove = this.components.subList(cIndex, cIndex + numComponents);
/*  368 */     for (Component c : toRemove) {
/*  369 */       c.freeIfNecessary();
/*      */     }
/*  371 */     toRemove.clear();
/*      */     
/*  373 */     updateComponentOffsets(cIndex);
/*  374 */     return this;
/*      */   }
/*      */   
/*      */   public Iterator<ByteBuf> iterator() {
/*  378 */     assert !this.freed;
/*  379 */     List<ByteBuf> list = new ArrayList<ByteBuf>(this.components.size());
/*  380 */     for (Component c : this.components) {
/*  381 */       list.add(c.buf);
/*      */     }
/*  383 */     return list.iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ByteBuf> decompose(int offset, int length) {
/*  390 */     checkIndex(offset, length);
/*  391 */     if (length == 0) {
/*  392 */       return Collections.emptyList();
/*      */     }
/*      */     
/*  395 */     int componentId = toComponentIndex(offset);
/*  396 */     List<ByteBuf> slice = new ArrayList<ByteBuf>(this.components.size());
/*      */ 
/*      */     
/*  399 */     Component firstC = this.components.get(componentId);
/*  400 */     ByteBuf first = firstC.buf.duplicate();
/*  401 */     first.readerIndex(offset - firstC.offset);
/*      */     
/*  403 */     ByteBuf buf = first;
/*  404 */     int bytesToSlice = length;
/*      */     do {
/*  406 */       int readableBytes = buf.readableBytes();
/*  407 */       if (bytesToSlice <= readableBytes) {
/*      */         
/*  409 */         buf.writerIndex(buf.readerIndex() + bytesToSlice);
/*  410 */         slice.add(buf);
/*      */         
/*      */         break;
/*      */       } 
/*  414 */       slice.add(buf);
/*  415 */       bytesToSlice -= readableBytes;
/*  416 */       componentId++;
/*      */ 
/*      */       
/*  419 */       buf = ((Component)this.components.get(componentId)).buf.duplicate();
/*      */     }
/*  421 */     while (bytesToSlice > 0);
/*      */ 
/*      */     
/*  424 */     for (int i = 0; i < slice.size(); i++) {
/*  425 */       slice.set(i, ((ByteBuf)slice.get(i)).slice());
/*      */     }
/*      */     
/*  428 */     return slice;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*  433 */     int size = this.components.size();
/*  434 */     if (size == 0) {
/*  435 */       return false;
/*      */     }
/*  437 */     for (int i = 0; i < size; i++) {
/*  438 */       if (!((Component)this.components.get(i)).buf.isDirect()) {
/*  439 */         return false;
/*      */       }
/*      */     } 
/*  442 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasArray() {
/*  447 */     if (this.components.size() == 1) {
/*  448 */       return ((Component)this.components.get(0)).buf.hasArray();
/*      */     }
/*  450 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] array() {
/*  455 */     if (this.components.size() == 1) {
/*  456 */       return ((Component)this.components.get(0)).buf.array();
/*      */     }
/*  458 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/*  463 */     if (this.components.size() == 1) {
/*  464 */       return ((Component)this.components.get(0)).buf.arrayOffset();
/*      */     }
/*  466 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasMemoryAddress() {
/*  471 */     if (this.components.size() == 1) {
/*  472 */       return ((Component)this.components.get(0)).buf.hasMemoryAddress();
/*      */     }
/*  474 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public long memoryAddress() {
/*  479 */     if (this.components.size() == 1) {
/*  480 */       return ((Component)this.components.get(0)).buf.memoryAddress();
/*      */     }
/*  482 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int capacity() {
/*  487 */     if (this.components.isEmpty()) {
/*  488 */       return 0;
/*      */     }
/*  490 */     return ((Component)this.components.get(this.components.size() - 1)).endOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf capacity(int newCapacity) {
/*  495 */     assert !this.freed;
/*  496 */     if (newCapacity < 0 || newCapacity > maxCapacity()) {
/*  497 */       throw new IllegalArgumentException("newCapacity: " + newCapacity);
/*      */     }
/*      */     
/*  500 */     int oldCapacity = capacity();
/*  501 */     if (newCapacity > oldCapacity) {
/*  502 */       int paddingLength = newCapacity - oldCapacity;
/*      */       
/*  504 */       int nComponents = this.components.size();
/*  505 */       if (nComponents < this.maxNumComponents) {
/*  506 */         ByteBuf padding = allocBuffer(paddingLength);
/*  507 */         padding.setIndex(0, paddingLength);
/*  508 */         addComponent0(this.components.size(), padding);
/*      */       } else {
/*  510 */         ByteBuf padding = allocBuffer(paddingLength);
/*  511 */         padding.setIndex(0, paddingLength);
/*      */ 
/*      */         
/*  514 */         addComponent0(this.components.size(), padding);
/*  515 */         consolidateIfNeeded();
/*      */       } 
/*  517 */     } else if (newCapacity < oldCapacity) {
/*  518 */       int bytesToTrim = oldCapacity - newCapacity;
/*  519 */       for (ListIterator<Component> i = this.components.listIterator(this.components.size()); i.hasPrevious(); ) {
/*  520 */         Component c = i.previous();
/*  521 */         if (bytesToTrim >= c.length) {
/*  522 */           bytesToTrim -= c.length;
/*  523 */           i.remove();
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  528 */         Component newC = new Component(c.buf.slice(0, c.length - bytesToTrim));
/*  529 */         newC.offset = c.offset;
/*  530 */         newC.endOffset = newC.offset + newC.length;
/*  531 */         i.set(newC);
/*      */       } 
/*      */ 
/*      */       
/*  535 */       if (readerIndex() > newCapacity) {
/*  536 */         setIndex(newCapacity, newCapacity);
/*  537 */       } else if (writerIndex() > newCapacity) {
/*  538 */         writerIndex(newCapacity);
/*      */       } 
/*      */     } 
/*  541 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*  546 */     return this.alloc;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteOrder order() {
/*  551 */     return ByteOrder.BIG_ENDIAN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int numComponents() {
/*  558 */     return this.components.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int maxNumComponents() {
/*  565 */     return this.maxNumComponents;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int toComponentIndex(int offset) {
/*  572 */     assert !this.freed;
/*  573 */     checkIndex(offset);
/*      */     
/*  575 */     for (int low = 0, high = this.components.size(); low <= high; ) {
/*  576 */       int mid = low + high >>> 1;
/*  577 */       Component c = this.components.get(mid);
/*  578 */       if (offset >= c.endOffset) {
/*  579 */         low = mid + 1; continue;
/*  580 */       }  if (offset < c.offset) {
/*  581 */         high = mid - 1; continue;
/*      */       } 
/*  583 */       return mid;
/*      */     } 
/*      */ 
/*      */     
/*  587 */     throw new Error("should not reach here");
/*      */   }
/*      */   
/*      */   public int toByteIndex(int cIndex) {
/*  591 */     checkComponentIndex(cIndex);
/*  592 */     return ((Component)this.components.get(cIndex)).offset;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int index) {
/*  597 */     return _getByte(index);
/*      */   }
/*      */ 
/*      */   
/*      */   protected byte _getByte(int index) {
/*  602 */     Component c = findComponent(index);
/*  603 */     return c.buf.getByte(index - c.offset);
/*      */   }
/*      */ 
/*      */   
/*      */   protected short _getShort(int index) {
/*  608 */     Component c = findComponent(index);
/*  609 */     if (index + 2 <= c.endOffset)
/*  610 */       return c.buf.getShort(index - c.offset); 
/*  611 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  612 */       return (short)((_getByte(index) & 0xFF) << 8 | _getByte(index + 1) & 0xFF);
/*      */     }
/*  614 */     return (short)(_getByte(index) & 0xFF | (_getByte(index + 1) & 0xFF) << 8);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _getUnsignedMedium(int index) {
/*  620 */     Component c = findComponent(index);
/*  621 */     if (index + 3 <= c.endOffset)
/*  622 */       return c.buf.getUnsignedMedium(index - c.offset); 
/*  623 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  624 */       return (_getShort(index) & 0xFFFF) << 8 | _getByte(index + 2) & 0xFF;
/*      */     }
/*  626 */     return _getShort(index) & 0xFFFF | (_getByte(index + 2) & 0xFF) << 16;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _getInt(int index) {
/*  632 */     Component c = findComponent(index);
/*  633 */     if (index + 4 <= c.endOffset)
/*  634 */       return c.buf.getInt(index - c.offset); 
/*  635 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  636 */       return (_getShort(index) & 0xFFFF) << 16 | _getShort(index + 2) & 0xFFFF;
/*      */     }
/*  638 */     return _getShort(index) & 0xFFFF | (_getShort(index + 2) & 0xFFFF) << 16;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected long _getLong(int index) {
/*  644 */     Component c = findComponent(index);
/*  645 */     if (index + 8 <= c.endOffset)
/*  646 */       return c.buf.getLong(index - c.offset); 
/*  647 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  648 */       return (_getInt(index) & 0xFFFFFFFFL) << 32L | _getInt(index + 4) & 0xFFFFFFFFL;
/*      */     }
/*  650 */     return _getInt(index) & 0xFFFFFFFFL | (_getInt(index + 4) & 0xFFFFFFFFL) << 32L;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/*  656 */     checkDstIndex(index, length, dstIndex, dst.length);
/*  657 */     if (length == 0) {
/*  658 */       return this;
/*      */     }
/*      */     
/*  661 */     int i = toComponentIndex(index);
/*  662 */     while (length > 0) {
/*  663 */       Component c = this.components.get(i);
/*  664 */       ByteBuf s = c.buf;
/*  665 */       int adjustment = c.offset;
/*  666 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  667 */       s.getBytes(index - adjustment, dst, dstIndex, localLength);
/*  668 */       index += localLength;
/*  669 */       dstIndex += localLength;
/*  670 */       length -= localLength;
/*  671 */       i++;
/*      */     } 
/*  673 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuffer dst) {
/*  678 */     int limit = dst.limit();
/*  679 */     int length = dst.remaining();
/*      */     
/*  681 */     checkIndex(index, length);
/*  682 */     if (length == 0) {
/*  683 */       return this;
/*      */     }
/*      */     
/*  686 */     int i = toComponentIndex(index);
/*      */     try {
/*  688 */       while (length > 0) {
/*  689 */         Component c = this.components.get(i);
/*  690 */         ByteBuf s = c.buf;
/*  691 */         int adjustment = c.offset;
/*  692 */         int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  693 */         dst.limit(dst.position() + localLength);
/*  694 */         s.getBytes(index - adjustment, dst);
/*  695 */         index += localLength;
/*  696 */         length -= localLength;
/*  697 */         i++;
/*      */       } 
/*      */     } finally {
/*  700 */       dst.limit(limit);
/*      */     } 
/*  702 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/*  707 */     checkDstIndex(index, length, dstIndex, dst.capacity());
/*  708 */     if (length == 0) {
/*  709 */       return this;
/*      */     }
/*      */     
/*  712 */     int i = toComponentIndex(index);
/*  713 */     while (length > 0) {
/*  714 */       Component c = this.components.get(i);
/*  715 */       ByteBuf s = c.buf;
/*  716 */       int adjustment = c.offset;
/*  717 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  718 */       s.getBytes(index - adjustment, dst, dstIndex, localLength);
/*  719 */       index += localLength;
/*  720 */       dstIndex += localLength;
/*  721 */       length -= localLength;
/*  722 */       i++;
/*      */     } 
/*  724 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/*  730 */     int count = nioBufferCount();
/*  731 */     if (count == 1) {
/*  732 */       return out.write(internalNioBuffer(index, length));
/*      */     }
/*  734 */     long writtenBytes = out.write(nioBuffers(index, length));
/*  735 */     if (writtenBytes > 2147483647L) {
/*  736 */       return Integer.MAX_VALUE;
/*      */     }
/*  738 */     return (int)writtenBytes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/*  745 */     checkIndex(index, length);
/*  746 */     if (length == 0) {
/*  747 */       return this;
/*      */     }
/*      */     
/*  750 */     int i = toComponentIndex(index);
/*  751 */     while (length > 0) {
/*  752 */       Component c = this.components.get(i);
/*  753 */       ByteBuf s = c.buf;
/*  754 */       int adjustment = c.offset;
/*  755 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  756 */       s.getBytes(index - adjustment, out, localLength);
/*  757 */       index += localLength;
/*  758 */       length -= localLength;
/*  759 */       i++;
/*      */     } 
/*  761 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setByte(int index, int value) {
/*  766 */     Component c = findComponent(index);
/*  767 */     c.buf.setByte(index - c.offset, value);
/*  768 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setByte(int index, int value) {
/*  773 */     setByte(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setShort(int index, int value) {
/*  778 */     return (CompositeByteBuf)super.setShort(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setShort(int index, int value) {
/*  783 */     Component c = findComponent(index);
/*  784 */     if (index + 2 <= c.endOffset) {
/*  785 */       c.buf.setShort(index - c.offset, value);
/*  786 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  787 */       _setByte(index, (byte)(value >>> 8));
/*  788 */       _setByte(index + 1, (byte)value);
/*      */     } else {
/*  790 */       _setByte(index, (byte)value);
/*  791 */       _setByte(index + 1, (byte)(value >>> 8));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setMedium(int index, int value) {
/*  797 */     return (CompositeByteBuf)super.setMedium(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setMedium(int index, int value) {
/*  802 */     Component c = findComponent(index);
/*  803 */     if (index + 3 <= c.endOffset) {
/*  804 */       c.buf.setMedium(index - c.offset, value);
/*  805 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  806 */       _setShort(index, (short)(value >> 8));
/*  807 */       _setByte(index + 2, (byte)value);
/*      */     } else {
/*  809 */       _setShort(index, (short)value);
/*  810 */       _setByte(index + 2, (byte)(value >>> 16));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setInt(int index, int value) {
/*  816 */     return (CompositeByteBuf)super.setInt(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setInt(int index, int value) {
/*  821 */     Component c = findComponent(index);
/*  822 */     if (index + 4 <= c.endOffset) {
/*  823 */       c.buf.setInt(index - c.offset, value);
/*  824 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  825 */       _setShort(index, (short)(value >>> 16));
/*  826 */       _setShort(index + 2, (short)value);
/*      */     } else {
/*  828 */       _setShort(index, (short)value);
/*  829 */       _setShort(index + 2, (short)(value >>> 16));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setLong(int index, long value) {
/*  835 */     return (CompositeByteBuf)super.setLong(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _setLong(int index, long value) {
/*  840 */     Component c = findComponent(index);
/*  841 */     if (index + 8 <= c.endOffset) {
/*  842 */       c.buf.setLong(index - c.offset, value);
/*  843 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  844 */       _setInt(index, (int)(value >>> 32L));
/*  845 */       _setInt(index + 4, (int)value);
/*      */     } else {
/*  847 */       _setInt(index, (int)value);
/*  848 */       _setInt(index + 4, (int)(value >>> 32L));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/*  854 */     checkSrcIndex(index, length, srcIndex, src.length);
/*  855 */     if (length == 0) {
/*  856 */       return this;
/*      */     }
/*      */     
/*  859 */     int i = toComponentIndex(index);
/*  860 */     while (length > 0) {
/*  861 */       Component c = this.components.get(i);
/*  862 */       ByteBuf s = c.buf;
/*  863 */       int adjustment = c.offset;
/*  864 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  865 */       s.setBytes(index - adjustment, src, srcIndex, localLength);
/*  866 */       index += localLength;
/*  867 */       srcIndex += localLength;
/*  868 */       length -= localLength;
/*  869 */       i++;
/*      */     } 
/*  871 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuffer src) {
/*  876 */     int limit = src.limit();
/*  877 */     int length = src.remaining();
/*      */     
/*  879 */     checkIndex(index, length);
/*  880 */     if (length == 0) {
/*  881 */       return this;
/*      */     }
/*      */     
/*  884 */     int i = toComponentIndex(index);
/*      */     try {
/*  886 */       while (length > 0) {
/*  887 */         Component c = this.components.get(i);
/*  888 */         ByteBuf s = c.buf;
/*  889 */         int adjustment = c.offset;
/*  890 */         int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  891 */         src.limit(src.position() + localLength);
/*  892 */         s.setBytes(index - adjustment, src);
/*  893 */         index += localLength;
/*  894 */         length -= localLength;
/*  895 */         i++;
/*      */       } 
/*      */     } finally {
/*  898 */       src.limit(limit);
/*      */     } 
/*  900 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/*  905 */     checkSrcIndex(index, length, srcIndex, src.capacity());
/*  906 */     if (length == 0) {
/*  907 */       return this;
/*      */     }
/*      */     
/*  910 */     int i = toComponentIndex(index);
/*  911 */     while (length > 0) {
/*  912 */       Component c = this.components.get(i);
/*  913 */       ByteBuf s = c.buf;
/*  914 */       int adjustment = c.offset;
/*  915 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  916 */       s.setBytes(index - adjustment, src, srcIndex, localLength);
/*  917 */       index += localLength;
/*  918 */       srcIndex += localLength;
/*  919 */       length -= localLength;
/*  920 */       i++;
/*      */     } 
/*  922 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, InputStream in, int length) throws IOException {
/*  927 */     checkIndex(index, length);
/*  928 */     if (length == 0) {
/*  929 */       return in.read(EmptyArrays.EMPTY_BYTES);
/*      */     }
/*      */     
/*  932 */     int i = toComponentIndex(index);
/*  933 */     int readBytes = 0;
/*      */     
/*      */     do {
/*  936 */       Component c = this.components.get(i);
/*  937 */       ByteBuf s = c.buf;
/*  938 */       int adjustment = c.offset;
/*  939 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  940 */       int localReadBytes = s.setBytes(index - adjustment, in, localLength);
/*  941 */       if (localReadBytes < 0) {
/*  942 */         if (readBytes == 0) {
/*  943 */           return -1;
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/*  949 */       if (localReadBytes == localLength) {
/*  950 */         index += localLength;
/*  951 */         length -= localLength;
/*  952 */         readBytes += localLength;
/*  953 */         i++;
/*      */       } else {
/*  955 */         index += localReadBytes;
/*  956 */         length -= localReadBytes;
/*  957 */         readBytes += localReadBytes;
/*      */       } 
/*  959 */     } while (length > 0);
/*      */     
/*  961 */     return readBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/*  966 */     checkIndex(index, length);
/*  967 */     if (length == 0) {
/*  968 */       return in.read(FULL_BYTEBUFFER);
/*      */     }
/*      */     
/*  971 */     int i = toComponentIndex(index);
/*  972 */     int readBytes = 0;
/*      */     do {
/*  974 */       Component c = this.components.get(i);
/*  975 */       ByteBuf s = c.buf;
/*  976 */       int adjustment = c.offset;
/*  977 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/*  978 */       int localReadBytes = s.setBytes(index - adjustment, in, localLength);
/*      */       
/*  980 */       if (localReadBytes == 0) {
/*      */         break;
/*      */       }
/*      */       
/*  984 */       if (localReadBytes < 0) {
/*  985 */         if (readBytes == 0) {
/*  986 */           return -1;
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/*  992 */       if (localReadBytes == localLength) {
/*  993 */         index += localLength;
/*  994 */         length -= localLength;
/*  995 */         readBytes += localLength;
/*  996 */         i++;
/*      */       } else {
/*  998 */         index += localReadBytes;
/*  999 */         length -= localReadBytes;
/* 1000 */         readBytes += localReadBytes;
/*      */       } 
/* 1002 */     } while (length > 0);
/*      */     
/* 1004 */     return readBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy(int index, int length) {
/* 1009 */     checkIndex(index, length);
/* 1010 */     ByteBuf dst = Unpooled.buffer(length);
/* 1011 */     if (length != 0) {
/* 1012 */       copyTo(index, length, toComponentIndex(index), dst);
/*      */     }
/* 1014 */     return dst;
/*      */   }
/*      */   
/*      */   private void copyTo(int index, int length, int componentId, ByteBuf dst) {
/* 1018 */     int dstIndex = 0;
/* 1019 */     int i = componentId;
/*      */     
/* 1021 */     while (length > 0) {
/* 1022 */       Component c = this.components.get(i);
/* 1023 */       ByteBuf s = c.buf;
/* 1024 */       int adjustment = c.offset;
/* 1025 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/* 1026 */       s.getBytes(index - adjustment, dst, dstIndex, localLength);
/* 1027 */       index += localLength;
/* 1028 */       dstIndex += localLength;
/* 1029 */       length -= localLength;
/* 1030 */       i++;
/*      */     } 
/*      */     
/* 1033 */     dst.writerIndex(dst.capacity());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf component(int cIndex) {
/* 1043 */     return internalComponent(cIndex).duplicate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf componentAtOffset(int offset) {
/* 1053 */     return internalComponentAtOffset(offset).duplicate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf internalComponent(int cIndex) {
/* 1063 */     checkComponentIndex(cIndex);
/* 1064 */     return ((Component)this.components.get(cIndex)).buf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf internalComponentAtOffset(int offset) {
/* 1074 */     return (findComponent(offset)).buf;
/*      */   }
/*      */   
/*      */   private Component findComponent(int offset) {
/* 1078 */     assert !this.freed;
/* 1079 */     checkIndex(offset);
/*      */     
/* 1081 */     assert !this.freed;
/* 1082 */     checkIndex(offset);
/*      */     
/* 1084 */     for (int low = 0, high = this.components.size(); low <= high; ) {
/* 1085 */       int mid = low + high >>> 1;
/* 1086 */       Component c = this.components.get(mid);
/* 1087 */       if (offset >= c.endOffset) {
/* 1088 */         low = mid + 1; continue;
/* 1089 */       }  if (offset < c.offset) {
/* 1090 */         high = mid - 1; continue;
/*      */       } 
/* 1092 */       return c;
/*      */     } 
/*      */ 
/*      */     
/* 1096 */     throw new Error("should not reach here");
/*      */   }
/*      */ 
/*      */   
/*      */   public int nioBufferCount() {
/* 1101 */     if (this.components.size() == 1) {
/* 1102 */       return ((Component)this.components.get(0)).buf.nioBufferCount();
/*      */     }
/* 1104 */     int count = 0;
/* 1105 */     int componentsCount = this.components.size();
/*      */     
/* 1107 */     for (int i = 0; i < componentsCount; i++) {
/* 1108 */       Component c = this.components.get(i);
/* 1109 */       count += c.buf.nioBufferCount();
/*      */     } 
/* 1111 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 1117 */     if (this.components.size() == 1) {
/* 1118 */       return ((Component)this.components.get(0)).buf.internalNioBuffer(index, length);
/*      */     }
/* 1120 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer(int index, int length) {
/* 1125 */     if (this.components.size() == 1) {
/* 1126 */       ByteBuf buf = ((Component)this.components.get(0)).buf;
/* 1127 */       if (buf.nioBufferCount() == 1) {
/* 1128 */         return ((Component)this.components.get(0)).buf.nioBuffer(index, length);
/*      */       }
/*      */     } 
/* 1131 */     ByteBuffer merged = ByteBuffer.allocate(length).order(order());
/* 1132 */     ByteBuffer[] buffers = nioBuffers(index, length);
/*      */ 
/*      */     
/* 1135 */     for (int i = 0; i < buffers.length; i++) {
/* 1136 */       merged.put(buffers[i]);
/*      */     }
/*      */     
/* 1139 */     merged.flip();
/* 1140 */     return merged;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 1145 */     checkIndex(index, length);
/* 1146 */     if (length == 0) {
/* 1147 */       return EmptyArrays.EMPTY_BYTE_BUFFERS;
/*      */     }
/*      */     
/* 1150 */     List<ByteBuffer> buffers = new ArrayList<ByteBuffer>(this.components.size());
/* 1151 */     int i = toComponentIndex(index);
/* 1152 */     while (length > 0) {
/* 1153 */       Component c = this.components.get(i);
/* 1154 */       ByteBuf s = c.buf;
/* 1155 */       int adjustment = c.offset;
/* 1156 */       int localLength = Math.min(length, s.capacity() - index - adjustment);
/* 1157 */       switch (s.nioBufferCount()) {
/*      */         case 0:
/* 1159 */           throw new UnsupportedOperationException();
/*      */         case 1:
/* 1161 */           buffers.add(s.nioBuffer(index - adjustment, localLength));
/*      */           break;
/*      */         default:
/* 1164 */           Collections.addAll(buffers, s.nioBuffers(index - adjustment, localLength));
/*      */           break;
/*      */       } 
/* 1167 */       index += localLength;
/* 1168 */       length -= localLength;
/* 1169 */       i++;
/*      */     } 
/*      */     
/* 1172 */     return buffers.<ByteBuffer>toArray(new ByteBuffer[buffers.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf consolidate() {
/* 1179 */     assert !this.freed;
/* 1180 */     int numComponents = numComponents();
/* 1181 */     if (numComponents <= 1) {
/* 1182 */       return this;
/*      */     }
/*      */     
/* 1185 */     Component last = this.components.get(numComponents - 1);
/* 1186 */     int capacity = last.endOffset;
/* 1187 */     ByteBuf consolidated = allocBuffer(capacity);
/*      */     
/* 1189 */     for (int i = 0; i < numComponents; i++) {
/* 1190 */       Component c = this.components.get(i);
/* 1191 */       ByteBuf b = c.buf;
/* 1192 */       consolidated.writeBytes(b);
/* 1193 */       c.freeIfNecessary();
/*      */     } 
/*      */     
/* 1196 */     this.components.clear();
/* 1197 */     this.components.add(new Component(consolidated));
/* 1198 */     updateComponentOffsets(0);
/* 1199 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf consolidate(int cIndex, int numComponents) {
/* 1209 */     checkComponentIndex(cIndex, numComponents);
/* 1210 */     if (numComponents <= 1) {
/* 1211 */       return this;
/*      */     }
/*      */     
/* 1214 */     int endCIndex = cIndex + numComponents;
/* 1215 */     Component last = this.components.get(endCIndex - 1);
/* 1216 */     int capacity = last.endOffset - ((Component)this.components.get(cIndex)).offset;
/* 1217 */     ByteBuf consolidated = allocBuffer(capacity);
/*      */     
/* 1219 */     for (int i = cIndex; i < endCIndex; i++) {
/* 1220 */       Component c = this.components.get(i);
/* 1221 */       ByteBuf b = c.buf;
/* 1222 */       consolidated.writeBytes(b);
/* 1223 */       c.freeIfNecessary();
/*      */     } 
/*      */     
/* 1226 */     this.components.subList(cIndex + 1, endCIndex).clear();
/* 1227 */     this.components.set(cIndex, new Component(consolidated));
/* 1228 */     updateComponentOffsets(cIndex);
/* 1229 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeByteBuf discardReadComponents() {
/* 1236 */     assert !this.freed;
/* 1237 */     int readerIndex = readerIndex();
/* 1238 */     if (readerIndex == 0) {
/* 1239 */       return this;
/*      */     }
/*      */ 
/*      */     
/* 1243 */     int writerIndex = writerIndex();
/* 1244 */     if (readerIndex == writerIndex && writerIndex == capacity()) {
/* 1245 */       for (Component c : this.components) {
/* 1246 */         c.freeIfNecessary();
/*      */       }
/* 1248 */       this.components.clear();
/* 1249 */       setIndex(0, 0);
/* 1250 */       adjustMarkers(readerIndex);
/* 1251 */       return this;
/*      */     } 
/*      */ 
/*      */     
/* 1255 */     int firstComponentId = toComponentIndex(readerIndex);
/* 1256 */     for (int i = 0; i < firstComponentId; i++) {
/* 1257 */       ((Component)this.components.get(i)).freeIfNecessary();
/*      */     }
/* 1259 */     this.components.subList(0, firstComponentId).clear();
/*      */ 
/*      */     
/* 1262 */     Component first = this.components.get(0);
/* 1263 */     updateComponentOffsets(0);
/* 1264 */     setIndex(readerIndex - first.offset, writerIndex - first.offset);
/* 1265 */     adjustMarkers(first.offset);
/* 1266 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf discardReadBytes() {
/* 1271 */     assert !this.freed;
/* 1272 */     int readerIndex = readerIndex();
/* 1273 */     if (readerIndex == 0) {
/* 1274 */       return this;
/*      */     }
/*      */ 
/*      */     
/* 1278 */     int writerIndex = writerIndex();
/* 1279 */     if (readerIndex == writerIndex && writerIndex == capacity()) {
/* 1280 */       for (Component component : this.components) {
/* 1281 */         component.freeIfNecessary();
/*      */       }
/* 1283 */       this.components.clear();
/* 1284 */       setIndex(0, 0);
/* 1285 */       adjustMarkers(readerIndex);
/* 1286 */       return this;
/*      */     } 
/*      */ 
/*      */     
/* 1290 */     int firstComponentId = toComponentIndex(readerIndex);
/* 1291 */     for (int i = 0; i < firstComponentId; i++) {
/* 1292 */       ((Component)this.components.get(i)).freeIfNecessary();
/*      */     }
/* 1294 */     this.components.subList(0, firstComponentId).clear();
/*      */ 
/*      */     
/* 1297 */     Component c = this.components.get(0);
/* 1298 */     int adjustment = readerIndex - c.offset;
/* 1299 */     if (adjustment == c.length) {
/*      */       
/* 1301 */       this.components.remove(0);
/*      */     } else {
/* 1303 */       Component newC = new Component(c.buf.slice(adjustment, c.length - adjustment));
/* 1304 */       this.components.set(0, newC);
/*      */     } 
/*      */ 
/*      */     
/* 1308 */     updateComponentOffsets(0);
/* 1309 */     setIndex(0, writerIndex - readerIndex);
/* 1310 */     adjustMarkers(readerIndex);
/* 1311 */     return this;
/*      */   }
/*      */   
/*      */   private ByteBuf allocBuffer(int capacity) {
/* 1315 */     if (this.direct) {
/* 1316 */       return alloc().directBuffer(capacity);
/*      */     }
/* 1318 */     return alloc().heapBuffer(capacity);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1323 */     String result = super.toString();
/* 1324 */     result = result.substring(0, result.length() - 1);
/* 1325 */     return result + ", components=" + this.components.size() + ')';
/*      */   }
/*      */   
/*      */   private final class Component {
/*      */     final ByteBuf buf;
/*      */     final int length;
/*      */     int offset;
/*      */     int endOffset;
/*      */     
/*      */     Component(ByteBuf buf) {
/* 1335 */       this.buf = buf;
/* 1336 */       this.length = buf.readableBytes();
/*      */     }
/*      */ 
/*      */     
/*      */     void freeIfNecessary() {
/* 1341 */       this.buf.release();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readerIndex(int readerIndex) {
/* 1347 */     return (CompositeByteBuf)super.readerIndex(readerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writerIndex(int writerIndex) {
/* 1352 */     return (CompositeByteBuf)super.writerIndex(writerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setIndex(int readerIndex, int writerIndex) {
/* 1357 */     return (CompositeByteBuf)super.setIndex(readerIndex, writerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf clear() {
/* 1362 */     return (CompositeByteBuf)super.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf markReaderIndex() {
/* 1367 */     return (CompositeByteBuf)super.markReaderIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf resetReaderIndex() {
/* 1372 */     return (CompositeByteBuf)super.resetReaderIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf markWriterIndex() {
/* 1377 */     return (CompositeByteBuf)super.markWriterIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf resetWriterIndex() {
/* 1382 */     return (CompositeByteBuf)super.resetWriterIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf ensureWritable(int minWritableBytes) {
/* 1387 */     return (CompositeByteBuf)super.ensureWritable(minWritableBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuf dst) {
/* 1392 */     return (CompositeByteBuf)super.getBytes(index, dst);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, ByteBuf dst, int length) {
/* 1397 */     return (CompositeByteBuf)super.getBytes(index, dst, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf getBytes(int index, byte[] dst) {
/* 1402 */     return (CompositeByteBuf)super.getBytes(index, dst);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBoolean(int index, boolean value) {
/* 1407 */     return (CompositeByteBuf)super.setBoolean(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setChar(int index, int value) {
/* 1412 */     return (CompositeByteBuf)super.setChar(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setFloat(int index, float value) {
/* 1417 */     return (CompositeByteBuf)super.setFloat(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setDouble(int index, double value) {
/* 1422 */     return (CompositeByteBuf)super.setDouble(index, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuf src) {
/* 1427 */     return (CompositeByteBuf)super.setBytes(index, src);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, ByteBuf src, int length) {
/* 1432 */     return (CompositeByteBuf)super.setBytes(index, src, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setBytes(int index, byte[] src) {
/* 1437 */     return (CompositeByteBuf)super.setBytes(index, src);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf setZero(int index, int length) {
/* 1442 */     return (CompositeByteBuf)super.setZero(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf dst) {
/* 1447 */     return (CompositeByteBuf)super.readBytes(dst);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf dst, int length) {
/* 1452 */     return (CompositeByteBuf)super.readBytes(dst, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/* 1457 */     return (CompositeByteBuf)super.readBytes(dst, dstIndex, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(byte[] dst) {
/* 1462 */     return (CompositeByteBuf)super.readBytes(dst);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 1467 */     return (CompositeByteBuf)super.readBytes(dst, dstIndex, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuffer dst) {
/* 1472 */     return (CompositeByteBuf)super.readBytes(dst);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf readBytes(OutputStream out, int length) throws IOException {
/* 1477 */     return (CompositeByteBuf)super.readBytes(out, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf skipBytes(int length) {
/* 1482 */     return (CompositeByteBuf)super.skipBytes(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBoolean(boolean value) {
/* 1487 */     return (CompositeByteBuf)super.writeBoolean(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeByte(int value) {
/* 1492 */     return (CompositeByteBuf)super.writeByte(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeShort(int value) {
/* 1497 */     return (CompositeByteBuf)super.writeShort(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeMedium(int value) {
/* 1502 */     return (CompositeByteBuf)super.writeMedium(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeInt(int value) {
/* 1507 */     return (CompositeByteBuf)super.writeInt(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeLong(long value) {
/* 1512 */     return (CompositeByteBuf)super.writeLong(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeChar(int value) {
/* 1517 */     return (CompositeByteBuf)super.writeChar(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeFloat(float value) {
/* 1522 */     return (CompositeByteBuf)super.writeFloat(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeDouble(double value) {
/* 1527 */     return (CompositeByteBuf)super.writeDouble(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf src) {
/* 1532 */     return (CompositeByteBuf)super.writeBytes(src);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf src, int length) {
/* 1537 */     return (CompositeByteBuf)super.writeBytes(src, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/* 1542 */     return (CompositeByteBuf)super.writeBytes(src, srcIndex, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(byte[] src) {
/* 1547 */     return (CompositeByteBuf)super.writeBytes(src);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/* 1552 */     return (CompositeByteBuf)super.writeBytes(src, srcIndex, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuffer src) {
/* 1557 */     return (CompositeByteBuf)super.writeBytes(src);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf writeZero(int length) {
/* 1562 */     return (CompositeByteBuf)super.writeZero(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf retain(int increment) {
/* 1567 */     return (CompositeByteBuf)super.retain(increment);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf retain() {
/* 1572 */     return (CompositeByteBuf)super.retain();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/* 1577 */     return nioBuffers(readerIndex(), readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public CompositeByteBuf discardSomeReadBytes() {
/* 1582 */     return discardReadComponents();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void deallocate() {
/* 1587 */     if (this.freed) {
/*      */       return;
/*      */     }
/*      */     
/* 1591 */     this.freed = true;
/* 1592 */     for (Component c : this.components) {
/* 1593 */       c.freeIfNecessary();
/*      */     }
/*      */     
/* 1596 */     if (this.leak != null) {
/* 1597 */       this.leak.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf unwrap() {
/* 1603 */     return null;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\CompositeByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */