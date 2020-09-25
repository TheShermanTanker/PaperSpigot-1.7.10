/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class PoolArena<T>
/*     */ {
/*     */   final PooledByteBufAllocator parent;
/*     */   private final int pageSize;
/*     */   private final int maxOrder;
/*     */   private final int pageShifts;
/*     */   private final int chunkSize;
/*     */   private final int subpageOverflowMask;
/*     */   private final PoolSubpage<T>[] tinySubpagePools;
/*     */   private final PoolSubpage<T>[] smallSubpagePools;
/*     */   private final PoolChunkList<T> q050;
/*     */   private final PoolChunkList<T> q025;
/*     */   private final PoolChunkList<T> q000;
/*     */   private final PoolChunkList<T> qInit;
/*     */   private final PoolChunkList<T> q075;
/*     */   private final PoolChunkList<T> q100;
/*     */   
/*     */   protected PoolArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/*  48 */     this.parent = parent;
/*  49 */     this.pageSize = pageSize;
/*  50 */     this.maxOrder = maxOrder;
/*  51 */     this.pageShifts = pageShifts;
/*  52 */     this.chunkSize = chunkSize;
/*  53 */     this.subpageOverflowMask = pageSize - 1 ^ 0xFFFFFFFF;
/*     */     
/*  55 */     this.tinySubpagePools = newSubpagePoolArray(32); int i;
/*  56 */     for (i = 0; i < this.tinySubpagePools.length; i++) {
/*  57 */       this.tinySubpagePools[i] = newSubpagePoolHead(pageSize);
/*     */     }
/*     */     
/*  60 */     this.smallSubpagePools = newSubpagePoolArray(pageShifts - 9);
/*  61 */     for (i = 0; i < this.smallSubpagePools.length; i++) {
/*  62 */       this.smallSubpagePools[i] = newSubpagePoolHead(pageSize);
/*     */     }
/*     */     
/*  65 */     this.q100 = new PoolChunkList<T>(this, null, 100, 2147483647);
/*  66 */     this.q075 = new PoolChunkList<T>(this, this.q100, 75, 100);
/*  67 */     this.q050 = new PoolChunkList<T>(this, this.q075, 50, 100);
/*  68 */     this.q025 = new PoolChunkList<T>(this, this.q050, 25, 75);
/*  69 */     this.q000 = new PoolChunkList<T>(this, this.q025, 1, 50);
/*  70 */     this.qInit = new PoolChunkList<T>(this, this.q000, -2147483648, 25);
/*     */     
/*  72 */     this.q100.prevList = this.q075;
/*  73 */     this.q075.prevList = this.q050;
/*  74 */     this.q050.prevList = this.q025;
/*  75 */     this.q025.prevList = this.q000;
/*  76 */     this.q000.prevList = null;
/*  77 */     this.qInit.prevList = this.qInit;
/*     */   }
/*     */   
/*     */   private PoolSubpage<T> newSubpagePoolHead(int pageSize) {
/*  81 */     PoolSubpage<T> head = new PoolSubpage<T>(pageSize);
/*  82 */     head.prev = head;
/*  83 */     head.next = head;
/*  84 */     return head;
/*     */   }
/*     */ 
/*     */   
/*     */   private PoolSubpage<T>[] newSubpagePoolArray(int size) {
/*  89 */     return (PoolSubpage<T>[])new PoolSubpage[size];
/*     */   }
/*     */   
/*     */   PooledByteBuf<T> allocate(PoolThreadCache cache, int reqCapacity, int maxCapacity) {
/*  93 */     PooledByteBuf<T> buf = newByteBuf(maxCapacity);
/*  94 */     allocate(cache, buf, reqCapacity);
/*  95 */     return buf;
/*     */   }
/*     */   
/*     */   private void allocate(PoolThreadCache cache, PooledByteBuf<T> buf, int reqCapacity) {
/*  99 */     int normCapacity = normalizeCapacity(reqCapacity);
/* 100 */     if ((normCapacity & this.subpageOverflowMask) == 0) {
/*     */       int tableIdx;
/*     */       PoolSubpage<T>[] table;
/* 103 */       if ((normCapacity & 0xFFFFFE00) == 0) {
/* 104 */         tableIdx = normCapacity >>> 4;
/* 105 */         table = this.tinySubpagePools;
/*     */       } else {
/* 107 */         tableIdx = 0;
/* 108 */         int i = normCapacity >>> 10;
/* 109 */         while (i != 0) {
/* 110 */           i >>>= 1;
/* 111 */           tableIdx++;
/*     */         } 
/* 113 */         table = this.smallSubpagePools;
/*     */       } 
/*     */       
/* 116 */       synchronized (this) {
/* 117 */         PoolSubpage<T> head = table[tableIdx];
/* 118 */         PoolSubpage<T> s = head.next;
/* 119 */         if (s != head) {
/* 120 */           assert s.doNotDestroy && s.elemSize == normCapacity;
/* 121 */           long handle = s.allocate();
/* 122 */           assert handle >= 0L;
/* 123 */           s.chunk.initBufWithSubpage(buf, handle, reqCapacity);
/*     */           return;
/*     */         } 
/*     */       } 
/* 127 */     } else if (normCapacity > this.chunkSize) {
/* 128 */       allocateHuge(buf, reqCapacity);
/*     */       
/*     */       return;
/*     */     } 
/* 132 */     allocateNormal(buf, reqCapacity, normCapacity);
/*     */   }
/*     */   
/*     */   private synchronized void allocateNormal(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
/* 136 */     if (this.q050.allocate(buf, reqCapacity, normCapacity) || this.q025.allocate(buf, reqCapacity, normCapacity) || this.q000.allocate(buf, reqCapacity, normCapacity) || this.qInit.allocate(buf, reqCapacity, normCapacity) || this.q075.allocate(buf, reqCapacity, normCapacity) || this.q100.allocate(buf, reqCapacity, normCapacity)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     PoolChunk<T> c = newChunk(this.pageSize, this.maxOrder, this.pageShifts, this.chunkSize);
/* 144 */     long handle = c.allocate(normCapacity);
/* 145 */     assert handle > 0L;
/* 146 */     c.initBuf(buf, handle, reqCapacity);
/* 147 */     this.qInit.add(c);
/*     */   }
/*     */   
/*     */   private void allocateHuge(PooledByteBuf<T> buf, int reqCapacity) {
/* 151 */     buf.initUnpooled(newUnpooledChunk(reqCapacity), reqCapacity);
/*     */   }
/*     */   
/*     */   synchronized void free(PoolChunk<T> chunk, long handle) {
/* 155 */     if (chunk.unpooled) {
/* 156 */       destroyChunk(chunk);
/*     */     } else {
/* 158 */       chunk.parent.free(chunk, handle);
/*     */     } 
/*     */   }
/*     */   
/*     */   PoolSubpage<T> findSubpagePoolHead(int elemSize) {
/*     */     int tableIdx;
/*     */     PoolSubpage<T>[] table;
/* 165 */     if ((elemSize & 0xFFFFFE00) == 0) {
/* 166 */       tableIdx = elemSize >>> 4;
/* 167 */       table = this.tinySubpagePools;
/*     */     } else {
/* 169 */       tableIdx = 0;
/* 170 */       elemSize >>>= 10;
/* 171 */       while (elemSize != 0) {
/* 172 */         elemSize >>>= 1;
/* 173 */         tableIdx++;
/*     */       } 
/* 175 */       table = this.smallSubpagePools;
/*     */     } 
/*     */     
/* 178 */     return table[tableIdx];
/*     */   }
/*     */   
/*     */   private int normalizeCapacity(int reqCapacity) {
/* 182 */     if (reqCapacity < 0) {
/* 183 */       throw new IllegalArgumentException("capacity: " + reqCapacity + " (expected: 0+)");
/*     */     }
/* 185 */     if (reqCapacity >= this.chunkSize) {
/* 186 */       return reqCapacity;
/*     */     }
/*     */     
/* 189 */     if ((reqCapacity & 0xFFFFFE00) != 0) {
/*     */ 
/*     */       
/* 192 */       int normalizedCapacity = reqCapacity;
/* 193 */       normalizedCapacity |= normalizedCapacity >>> 1;
/* 194 */       normalizedCapacity |= normalizedCapacity >>> 2;
/* 195 */       normalizedCapacity |= normalizedCapacity >>> 4;
/* 196 */       normalizedCapacity |= normalizedCapacity >>> 8;
/* 197 */       normalizedCapacity |= normalizedCapacity >>> 16;
/* 198 */       normalizedCapacity++;
/*     */       
/* 200 */       if (normalizedCapacity < 0) {
/* 201 */         normalizedCapacity >>>= 1;
/*     */       }
/*     */       
/* 204 */       return normalizedCapacity;
/*     */     } 
/*     */ 
/*     */     
/* 208 */     if ((reqCapacity & 0xF) == 0) {
/* 209 */       return reqCapacity;
/*     */     }
/*     */     
/* 212 */     return (reqCapacity & 0xFFFFFFF0) + 16;
/*     */   }
/*     */   
/*     */   void reallocate(PooledByteBuf<T> buf, int newCapacity, boolean freeOldMemory) {
/* 216 */     if (newCapacity < 0 || newCapacity > buf.maxCapacity()) {
/* 217 */       throw new IllegalArgumentException("newCapacity: " + newCapacity);
/*     */     }
/*     */     
/* 220 */     int oldCapacity = buf.length;
/* 221 */     if (oldCapacity == newCapacity) {
/*     */       return;
/*     */     }
/*     */     
/* 225 */     PoolChunk<T> oldChunk = buf.chunk;
/* 226 */     long oldHandle = buf.handle;
/* 227 */     T oldMemory = buf.memory;
/* 228 */     int oldOffset = buf.offset;
/*     */     
/* 230 */     int readerIndex = buf.readerIndex();
/* 231 */     int writerIndex = buf.writerIndex();
/*     */     
/* 233 */     allocate(this.parent.threadCache.get(), buf, newCapacity);
/* 234 */     if (newCapacity > oldCapacity) {
/* 235 */       memoryCopy(oldMemory, oldOffset + readerIndex, buf.memory, buf.offset + readerIndex, writerIndex - readerIndex);
/*     */     
/*     */     }
/* 238 */     else if (newCapacity < oldCapacity) {
/* 239 */       if (readerIndex < newCapacity) {
/* 240 */         if (writerIndex > newCapacity) {
/* 241 */           writerIndex = newCapacity;
/*     */         }
/* 243 */         memoryCopy(oldMemory, oldOffset + readerIndex, buf.memory, buf.offset + readerIndex, writerIndex - readerIndex);
/*     */       }
/*     */       else {
/*     */         
/* 247 */         readerIndex = writerIndex = newCapacity;
/*     */       } 
/*     */     } 
/*     */     
/* 251 */     buf.setIndex(readerIndex, writerIndex);
/*     */     
/* 253 */     if (freeOldMemory) {
/* 254 */       free(oldChunk, oldHandle);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract PoolChunk<T> newChunk(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   
/*     */   protected abstract PoolChunk<T> newUnpooledChunk(int paramInt);
/*     */   
/*     */   public synchronized String toString() {
/* 265 */     StringBuilder buf = new StringBuilder();
/* 266 */     buf.append("Chunk(s) at 0~25%:");
/* 267 */     buf.append(StringUtil.NEWLINE);
/* 268 */     buf.append(this.qInit);
/* 269 */     buf.append(StringUtil.NEWLINE);
/* 270 */     buf.append("Chunk(s) at 0~50%:");
/* 271 */     buf.append(StringUtil.NEWLINE);
/* 272 */     buf.append(this.q000);
/* 273 */     buf.append(StringUtil.NEWLINE);
/* 274 */     buf.append("Chunk(s) at 25~75%:");
/* 275 */     buf.append(StringUtil.NEWLINE);
/* 276 */     buf.append(this.q025);
/* 277 */     buf.append(StringUtil.NEWLINE);
/* 278 */     buf.append("Chunk(s) at 50~100%:");
/* 279 */     buf.append(StringUtil.NEWLINE);
/* 280 */     buf.append(this.q050);
/* 281 */     buf.append(StringUtil.NEWLINE);
/* 282 */     buf.append("Chunk(s) at 75~100%:");
/* 283 */     buf.append(StringUtil.NEWLINE);
/* 284 */     buf.append(this.q075);
/* 285 */     buf.append(StringUtil.NEWLINE);
/* 286 */     buf.append("Chunk(s) at 100%:");
/* 287 */     buf.append(StringUtil.NEWLINE);
/* 288 */     buf.append(this.q100);
/* 289 */     buf.append(StringUtil.NEWLINE);
/* 290 */     buf.append("tiny subpages:"); int i;
/* 291 */     for (i = 1; i < this.tinySubpagePools.length; i++) {
/* 292 */       PoolSubpage<T> head = this.tinySubpagePools[i];
/* 293 */       if (head.next != head) {
/*     */ 
/*     */ 
/*     */         
/* 297 */         buf.append(StringUtil.NEWLINE);
/* 298 */         buf.append(i);
/* 299 */         buf.append(": ");
/* 300 */         PoolSubpage<T> s = head.next;
/*     */         do {
/* 302 */           buf.append(s);
/* 303 */           s = s.next;
/* 304 */         } while (s != head);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 309 */     buf.append(StringUtil.NEWLINE);
/* 310 */     buf.append("small subpages:");
/* 311 */     for (i = 1; i < this.smallSubpagePools.length; i++) {
/* 312 */       PoolSubpage<T> head = this.smallSubpagePools[i];
/* 313 */       if (head.next != head) {
/*     */ 
/*     */ 
/*     */         
/* 317 */         buf.append(StringUtil.NEWLINE);
/* 318 */         buf.append(i);
/* 319 */         buf.append(": ");
/* 320 */         PoolSubpage<T> s = head.next;
/*     */         do {
/* 322 */           buf.append(s);
/* 323 */           s = s.next;
/* 324 */         } while (s != head);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 329 */     buf.append(StringUtil.NEWLINE);
/*     */     
/* 331 */     return buf.toString();
/*     */   }
/*     */   protected abstract PooledByteBuf<T> newByteBuf(int paramInt);
/*     */   protected abstract void memoryCopy(T paramT1, int paramInt1, T paramT2, int paramInt2, int paramInt3);
/*     */   protected abstract void destroyChunk(PoolChunk<T> paramPoolChunk);
/*     */   static final class HeapArena extends PoolArena<byte[]> { HeapArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/* 337 */       super(parent, pageSize, maxOrder, pageShifts, chunkSize);
/*     */     }
/*     */ 
/*     */     
/*     */     protected PoolChunk<byte[]> newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/* 342 */       return (PoolChunk)new PoolChunk<byte>(this, new byte[chunkSize], pageSize, maxOrder, pageShifts, chunkSize);
/*     */     }
/*     */ 
/*     */     
/*     */     protected PoolChunk<byte[]> newUnpooledChunk(int capacity) {
/* 347 */       return (PoolChunk)new PoolChunk<byte>(this, new byte[capacity], capacity);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void destroyChunk(PoolChunk<byte[]> chunk) {}
/*     */ 
/*     */ 
/*     */     
/*     */     protected PooledByteBuf<byte[]> newByteBuf(int maxCapacity) {
/* 357 */       return PooledHeapByteBuf.newInstance(maxCapacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void memoryCopy(byte[] src, int srcOffset, byte[] dst, int dstOffset, int length) {
/* 362 */       if (length == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 366 */       System.arraycopy(src, srcOffset, dst, dstOffset, length);
/*     */     } }
/*     */ 
/*     */   
/*     */   static final class DirectArena
/*     */     extends PoolArena<ByteBuffer> {
/* 372 */     private static final boolean HAS_UNSAFE = PlatformDependent.hasUnsafe();
/*     */     
/*     */     DirectArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/* 375 */       super(parent, pageSize, maxOrder, pageShifts, chunkSize);
/*     */     }
/*     */ 
/*     */     
/*     */     protected PoolChunk<ByteBuffer> newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/* 380 */       return new PoolChunk<ByteBuffer>(this, ByteBuffer.allocateDirect(chunkSize), pageSize, maxOrder, pageShifts, chunkSize);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PoolChunk<ByteBuffer> newUnpooledChunk(int capacity) {
/* 386 */       return new PoolChunk<ByteBuffer>(this, ByteBuffer.allocateDirect(capacity), capacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void destroyChunk(PoolChunk<ByteBuffer> chunk) {
/* 391 */       PlatformDependent.freeDirectBuffer((ByteBuffer)chunk.memory);
/*     */     }
/*     */ 
/*     */     
/*     */     protected PooledByteBuf<ByteBuffer> newByteBuf(int maxCapacity) {
/* 396 */       if (HAS_UNSAFE) {
/* 397 */         return PooledUnsafeDirectByteBuf.newInstance(maxCapacity);
/*     */       }
/* 399 */       return PooledDirectByteBuf.newInstance(maxCapacity);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void memoryCopy(ByteBuffer src, int srcOffset, ByteBuffer dst, int dstOffset, int length) {
/* 405 */       if (length == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 409 */       if (HAS_UNSAFE) {
/* 410 */         PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(src) + srcOffset, PlatformDependent.directBufferAddress(dst) + dstOffset, length);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 415 */         src = src.duplicate();
/* 416 */         dst = dst.duplicate();
/* 417 */         src.position(srcOffset).limit(srcOffset + length);
/* 418 */         dst.position(dstOffset);
/* 419 */         dst.put(src);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\PoolArena.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */