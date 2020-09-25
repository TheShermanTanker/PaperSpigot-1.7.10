/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*     */ import net.minecraft.util.io.netty.util.Recycler;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public final class ChannelOutboundBuffer
/*     */ {
/*  44 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelOutboundBuffer.class);
/*     */   
/*     */   private static final int INITIAL_CAPACITY = 32;
/*     */   
/*  48 */   private static final Recycler<ChannelOutboundBuffer> RECYCLER = new Recycler<ChannelOutboundBuffer>()
/*     */     {
/*     */       protected ChannelOutboundBuffer newObject(Recycler.Handle handle) {
/*  51 */         return new ChannelOutboundBuffer(handle);
/*     */       }
/*     */     };
/*     */   
/*     */   static ChannelOutboundBuffer newInstance(AbstractChannel channel) {
/*  56 */     ChannelOutboundBuffer buffer = (ChannelOutboundBuffer)RECYCLER.get();
/*  57 */     buffer.channel = channel;
/*  58 */     buffer.totalPendingSize = 0L;
/*  59 */     buffer.writable = 1;
/*  60 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   private final Recycler.Handle handle;
/*     */   
/*     */   private AbstractChannel channel;
/*     */   
/*     */   private Entry[] buffer;
/*     */   
/*     */   private int flushed;
/*     */   
/*     */   private int unflushed;
/*     */   
/*     */   private int tail;
/*     */   
/*     */   private ByteBuffer[] nioBuffers;
/*     */   
/*     */   private int nioBufferCount;
/*     */   private long nioBufferSize;
/*     */   private boolean inFail;
/*  81 */   private static final AtomicLongFieldUpdater<ChannelOutboundBuffer> TOTAL_PENDING_SIZE_UPDATER = AtomicLongFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
/*     */ 
/*     */   
/*     */   private volatile long totalPendingSize;
/*     */   
/*  86 */   private static final AtomicIntegerFieldUpdater<ChannelOutboundBuffer> WRITABLE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "writable");
/*     */ 
/*     */   
/*  89 */   private volatile int writable = 1;
/*     */   
/*     */   private ChannelOutboundBuffer(Recycler.Handle handle) {
/*  92 */     this.handle = handle;
/*     */     
/*  94 */     this.buffer = new Entry[32];
/*  95 */     for (int i = 0; i < this.buffer.length; i++) {
/*  96 */       this.buffer[i] = new Entry();
/*     */     }
/*     */     
/*  99 */     this.nioBuffers = new ByteBuffer[32];
/*     */   }
/*     */   
/*     */   void addMessage(Object msg, ChannelPromise promise) {
/* 103 */     int size = this.channel.estimatorHandle().size(msg);
/* 104 */     if (size < 0) {
/* 105 */       size = 0;
/*     */     }
/*     */     
/* 108 */     Entry e = this.buffer[this.tail++];
/* 109 */     e.msg = msg;
/* 110 */     e.pendingSize = size;
/* 111 */     e.promise = promise;
/* 112 */     e.total = total(msg);
/*     */     
/* 114 */     this.tail &= this.buffer.length - 1;
/*     */     
/* 116 */     if (this.tail == this.flushed) {
/* 117 */       addCapacity();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 122 */     incrementPendingOutboundBytes(size);
/*     */   }
/*     */   
/*     */   private void addCapacity() {
/* 126 */     int p = this.flushed;
/* 127 */     int n = this.buffer.length;
/* 128 */     int r = n - p;
/* 129 */     int s = size();
/*     */     
/* 131 */     int newCapacity = n << 1;
/* 132 */     if (newCapacity < 0) {
/* 133 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 136 */     Entry[] e = new Entry[newCapacity];
/* 137 */     System.arraycopy(this.buffer, p, e, 0, r);
/* 138 */     System.arraycopy(this.buffer, 0, e, r, p);
/* 139 */     for (int i = n; i < e.length; i++) {
/* 140 */       e[i] = new Entry();
/*     */     }
/*     */     
/* 143 */     this.buffer = e;
/* 144 */     this.flushed = 0;
/* 145 */     this.unflushed = s;
/* 146 */     this.tail = n;
/*     */   }
/*     */   
/*     */   void addFlush() {
/* 150 */     this.unflushed = this.tail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void incrementPendingOutboundBytes(int size) {
/* 160 */     Channel channel = this.channel;
/* 161 */     if (size == 0 || channel == null) {
/*     */       return;
/*     */     }
/*     */     
/* 165 */     long oldValue = this.totalPendingSize;
/* 166 */     long newWriteBufferSize = oldValue + size;
/* 167 */     while (!TOTAL_PENDING_SIZE_UPDATER.compareAndSet(this, oldValue, newWriteBufferSize)) {
/* 168 */       oldValue = this.totalPendingSize;
/* 169 */       newWriteBufferSize = oldValue + size;
/*     */     } 
/*     */     
/* 172 */     int highWaterMark = channel.config().getWriteBufferHighWaterMark();
/*     */     
/* 174 */     if (newWriteBufferSize > highWaterMark && 
/* 175 */       WRITABLE_UPDATER.compareAndSet(this, 1, 0)) {
/* 176 */       channel.pipeline().fireChannelWritabilityChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void decrementPendingOutboundBytes(int size) {
/* 188 */     Channel channel = this.channel;
/* 189 */     if (size == 0 || channel == null) {
/*     */       return;
/*     */     }
/*     */     
/* 193 */     long oldValue = this.totalPendingSize;
/* 194 */     long newWriteBufferSize = oldValue - size;
/* 195 */     while (!TOTAL_PENDING_SIZE_UPDATER.compareAndSet(this, oldValue, newWriteBufferSize)) {
/* 196 */       oldValue = this.totalPendingSize;
/* 197 */       newWriteBufferSize = oldValue - size;
/*     */     } 
/*     */     
/* 200 */     int lowWaterMark = channel.config().getWriteBufferLowWaterMark();
/*     */     
/* 202 */     if ((newWriteBufferSize == 0L || newWriteBufferSize < lowWaterMark) && 
/* 203 */       WRITABLE_UPDATER.compareAndSet(this, 0, 1)) {
/* 204 */       channel.pipeline().fireChannelWritabilityChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static long total(Object msg) {
/* 210 */     if (msg instanceof ByteBuf) {
/* 211 */       return ((ByteBuf)msg).readableBytes();
/*     */     }
/* 213 */     if (msg instanceof FileRegion) {
/* 214 */       return ((FileRegion)msg).count();
/*     */     }
/* 216 */     if (msg instanceof ByteBufHolder) {
/* 217 */       return ((ByteBufHolder)msg).content().readableBytes();
/*     */     }
/* 219 */     return -1L;
/*     */   }
/*     */   
/*     */   public Object current() {
/* 223 */     if (isEmpty()) {
/* 224 */       return null;
/*     */     }
/* 226 */     return (this.buffer[this.flushed]).msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void current(Object msg) {
/* 235 */     Entry entry = this.buffer[this.flushed];
/* 236 */     safeRelease(entry.msg);
/* 237 */     entry.msg = msg;
/*     */   }
/*     */   
/*     */   public void progress(long amount) {
/* 241 */     Entry e = this.buffer[this.flushed];
/* 242 */     ChannelPromise p = e.promise;
/* 243 */     if (p instanceof ChannelProgressivePromise) {
/* 244 */       long progress = e.progress + amount;
/* 245 */       e.progress = progress;
/* 246 */       ((ChannelProgressivePromise)p).tryProgress(progress, e.total);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean remove() {
/* 251 */     if (isEmpty()) {
/* 252 */       return false;
/*     */     }
/*     */     
/* 255 */     Entry e = this.buffer[this.flushed];
/* 256 */     Object msg = e.msg;
/* 257 */     if (msg == null) {
/* 258 */       return false;
/*     */     }
/*     */     
/* 261 */     ChannelPromise promise = e.promise;
/* 262 */     int size = e.pendingSize;
/*     */     
/* 264 */     e.clear();
/*     */     
/* 266 */     this.flushed = this.flushed + 1 & this.buffer.length - 1;
/*     */     
/* 268 */     safeRelease(msg);
/*     */     
/* 270 */     promise.trySuccess();
/* 271 */     decrementPendingOutboundBytes(size);
/*     */     
/* 273 */     return true;
/*     */   }
/*     */   
/*     */   public boolean remove(Throwable cause) {
/* 277 */     if (isEmpty()) {
/* 278 */       return false;
/*     */     }
/*     */     
/* 281 */     Entry e = this.buffer[this.flushed];
/* 282 */     Object msg = e.msg;
/* 283 */     if (msg == null) {
/* 284 */       return false;
/*     */     }
/*     */     
/* 287 */     ChannelPromise promise = e.promise;
/* 288 */     int size = e.pendingSize;
/*     */     
/* 290 */     e.clear();
/*     */     
/* 292 */     this.flushed = this.flushed + 1 & this.buffer.length - 1;
/*     */     
/* 294 */     safeRelease(msg);
/*     */     
/* 296 */     safeFail(promise, cause);
/* 297 */     decrementPendingOutboundBytes(size);
/*     */     
/* 299 */     return true;
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
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers() {
/* 314 */     long nioBufferSize = 0L;
/* 315 */     int nioBufferCount = 0;
/* 316 */     int mask = this.buffer.length - 1;
/* 317 */     ByteBufAllocator alloc = this.channel.alloc();
/* 318 */     ByteBuffer[] nioBuffers = this.nioBuffers;
/*     */     
/* 320 */     int i = this.flushed; Object m;
/* 321 */     while (i != this.unflushed && (m = (this.buffer[i]).msg) != null) {
/* 322 */       if (!(m instanceof ByteBuf)) {
/* 323 */         this.nioBufferCount = 0;
/* 324 */         this.nioBufferSize = 0L;
/* 325 */         return null;
/*     */       } 
/*     */       
/* 328 */       Entry entry = this.buffer[i];
/* 329 */       ByteBuf buf = (ByteBuf)m;
/* 330 */       int readerIndex = buf.readerIndex();
/* 331 */       int readableBytes = buf.writerIndex() - readerIndex;
/*     */       
/* 333 */       if (readableBytes > 0) {
/* 334 */         nioBufferSize += readableBytes;
/* 335 */         int count = entry.count;
/* 336 */         if (count == -1) {
/* 337 */           entry.count = count = buf.nioBufferCount();
/*     */         }
/* 339 */         if (nioBufferCount + count > nioBuffers.length) {
/* 340 */           this.nioBuffers = nioBuffers = doubleNioBufferArray(nioBuffers, nioBufferCount);
/*     */         }
/* 342 */         if (buf.isDirect() || !alloc.isDirectBufferPooled()) {
/* 343 */           if (count == 1) {
/* 344 */             ByteBuffer nioBuf = entry.buf;
/* 345 */             if (nioBuf == null)
/*     */             {
/*     */               
/* 348 */               entry.buf = nioBuf = buf.internalNioBuffer(readerIndex, readableBytes);
/*     */             }
/* 350 */             nioBuffers[nioBufferCount++] = nioBuf;
/*     */           } else {
/* 352 */             ByteBuffer[] nioBufs = entry.buffers;
/* 353 */             if (nioBufs == null)
/*     */             {
/* 355 */               entry.buffers = nioBufs = buf.nioBuffers();
/*     */             }
/* 357 */             nioBufferCount = fillBufferArray(nioBufs, nioBuffers, nioBufferCount);
/*     */           } 
/*     */         } else {
/* 360 */           nioBufferCount = fillBufferArrayNonDirect(entry, buf, readerIndex, readableBytes, alloc, nioBuffers, nioBufferCount);
/*     */         } 
/*     */       } 
/*     */       
/* 364 */       i = i + 1 & mask;
/*     */     } 
/* 366 */     this.nioBufferCount = nioBufferCount;
/* 367 */     this.nioBufferSize = nioBufferSize;
/*     */     
/* 369 */     return nioBuffers;
/*     */   }
/*     */   
/*     */   private static int fillBufferArray(ByteBuffer[] nioBufs, ByteBuffer[] nioBuffers, int nioBufferCount) {
/* 373 */     for (ByteBuffer nioBuf : nioBufs) {
/* 374 */       if (nioBuf == null) {
/*     */         break;
/*     */       }
/* 377 */       nioBuffers[nioBufferCount++] = nioBuf;
/*     */     } 
/* 379 */     return nioBufferCount;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int fillBufferArrayNonDirect(Entry entry, ByteBuf buf, int readerIndex, int readableBytes, ByteBufAllocator alloc, ByteBuffer[] nioBuffers, int nioBufferCount) {
/* 384 */     ByteBuf directBuf = alloc.directBuffer(readableBytes);
/* 385 */     directBuf.writeBytes(buf, readerIndex, readableBytes);
/* 386 */     buf.release();
/* 387 */     entry.msg = directBuf;
/*     */     
/* 389 */     ByteBuffer nioBuf = entry.buf = directBuf.internalNioBuffer(0, readableBytes);
/* 390 */     entry.count = 1;
/* 391 */     nioBuffers[nioBufferCount++] = nioBuf;
/* 392 */     return nioBufferCount;
/*     */   }
/*     */   
/*     */   private static ByteBuffer[] doubleNioBufferArray(ByteBuffer[] array, int size) {
/* 396 */     int newCapacity = array.length << 1;
/* 397 */     if (newCapacity < 0) {
/* 398 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 401 */     ByteBuffer[] newArray = new ByteBuffer[newCapacity];
/* 402 */     System.arraycopy(array, 0, newArray, 0, size);
/*     */     
/* 404 */     return newArray;
/*     */   }
/*     */   
/*     */   public int nioBufferCount() {
/* 408 */     return this.nioBufferCount;
/*     */   }
/*     */   
/*     */   public long nioBufferSize() {
/* 412 */     return this.nioBufferSize;
/*     */   }
/*     */   
/*     */   boolean getWritable() {
/* 416 */     return (this.writable != 0);
/*     */   }
/*     */   
/*     */   public int size() {
/* 420 */     return this.unflushed - this.flushed & this.buffer.length - 1;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 424 */     return (this.unflushed == this.flushed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void failFlushed(Throwable cause) {
/* 433 */     if (this.inFail) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 438 */       this.inFail = true; do {
/*     */       
/* 440 */       } while (remove(cause));
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 445 */       this.inFail = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   void close(final ClosedChannelException cause) {
/* 450 */     if (this.inFail) {
/* 451 */       this.channel.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 454 */               ChannelOutboundBuffer.this.close(cause);
/*     */             }
/*     */           });
/*     */       
/*     */       return;
/*     */     } 
/* 460 */     this.inFail = true;
/*     */     
/* 462 */     if (this.channel.isOpen()) {
/* 463 */       throw new IllegalStateException("close() must be invoked after the channel is closed.");
/*     */     }
/*     */     
/* 466 */     if (!isEmpty()) {
/* 467 */       throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
/*     */     }
/*     */ 
/*     */     
/* 471 */     int unflushedCount = this.tail - this.unflushed & this.buffer.length - 1;
/*     */     try {
/* 473 */       for (int i = 0; i < unflushedCount; i++) {
/* 474 */         Entry e = this.buffer[this.unflushed + i & this.buffer.length - 1];
/* 475 */         safeRelease(e.msg);
/* 476 */         e.msg = null;
/* 477 */         safeFail(e.promise, cause);
/* 478 */         e.promise = null;
/*     */ 
/*     */         
/* 481 */         int size = e.pendingSize;
/* 482 */         long oldValue = this.totalPendingSize;
/* 483 */         long newWriteBufferSize = oldValue - size;
/* 484 */         while (!TOTAL_PENDING_SIZE_UPDATER.compareAndSet(this, oldValue, newWriteBufferSize)) {
/* 485 */           oldValue = this.totalPendingSize;
/* 486 */           newWriteBufferSize = oldValue - size;
/*     */         } 
/*     */         
/* 489 */         e.pendingSize = 0;
/*     */       } 
/*     */     } finally {
/* 492 */       this.tail = this.unflushed;
/* 493 */       this.inFail = false;
/*     */     } 
/*     */     
/* 496 */     recycle();
/*     */   }
/*     */   
/*     */   private static void safeRelease(Object message) {
/*     */     try {
/* 501 */       ReferenceCountUtil.release(message);
/* 502 */     } catch (Throwable t) {
/* 503 */       logger.warn("Failed to release a message.", t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void safeFail(ChannelPromise promise, Throwable cause) {
/* 508 */     if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
/* 509 */       logger.warn("Promise done already: {} - new exception is:", promise, cause);
/*     */     }
/*     */   }
/*     */   
/*     */   public void recycle() {
/* 514 */     if (this.buffer.length > 32) {
/* 515 */       Entry[] e = new Entry[32];
/* 516 */       System.arraycopy(this.buffer, 0, e, 0, 32);
/* 517 */       this.buffer = e;
/*     */     } 
/*     */     
/* 520 */     if (this.nioBuffers.length > 32) {
/* 521 */       this.nioBuffers = new ByteBuffer[32];
/*     */     }
/*     */     else {
/*     */       
/* 525 */       Arrays.fill((Object[])this.nioBuffers, (Object)null);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 530 */     this.flushed = 0;
/* 531 */     this.unflushed = 0;
/* 532 */     this.tail = 0;
/*     */ 
/*     */     
/* 535 */     this.channel = null;
/*     */     
/* 537 */     RECYCLER.recycle(this, this.handle);
/*     */   }
/*     */   
/*     */   private static final class Entry {
/*     */     Object msg;
/*     */     ByteBuffer[] buffers;
/*     */     ByteBuffer buf;
/*     */     ChannelPromise promise;
/*     */     long progress;
/*     */     long total;
/*     */     int pendingSize;
/* 548 */     int count = -1;
/*     */     
/*     */     public void clear() {
/* 551 */       this.buffers = null;
/* 552 */       this.buf = null;
/* 553 */       this.msg = null;
/* 554 */       this.promise = null;
/* 555 */       this.progress = 0L;
/* 556 */       this.total = 0L;
/* 557 */       this.pendingSize = 0;
/* 558 */       this.count = -1;
/*     */     }
/*     */     
/*     */     private Entry() {}
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\ChannelOutboundBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */