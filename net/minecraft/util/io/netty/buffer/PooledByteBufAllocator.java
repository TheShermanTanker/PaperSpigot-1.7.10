/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
/*     */ import net.minecraft.util.io.netty.util.internal.SystemPropertyUtil;
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
/*     */ public class PooledByteBufAllocator
/*     */   extends AbstractByteBufAllocator
/*     */ {
/*  30 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
/*     */   private static final int DEFAULT_NUM_HEAP_ARENA;
/*     */   private static final int DEFAULT_NUM_DIRECT_ARENA;
/*     */   private static final int DEFAULT_PAGE_SIZE;
/*     */   private static final int DEFAULT_MAX_ORDER;
/*     */   private static final int MIN_PAGE_SIZE = 4096;
/*     */   private static final int MAX_CHUNK_SIZE = 1073741824;
/*     */   public static final PooledByteBufAllocator DEFAULT;
/*     */   private final PoolArena<byte[]>[] heapArenas;
/*     */   private final PoolArena<ByteBuffer>[] directArenas;
/*     */   
/*     */   static {
/*  42 */     int defaultPageSize = SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
/*  43 */     Throwable pageSizeFallbackCause = null;
/*     */     try {
/*  45 */       validateAndCalculatePageShifts(defaultPageSize);
/*  46 */     } catch (Throwable t) {
/*  47 */       pageSizeFallbackCause = t;
/*  48 */       defaultPageSize = 8192;
/*     */     } 
/*  50 */     DEFAULT_PAGE_SIZE = defaultPageSize;
/*     */     
/*  52 */     int defaultMaxOrder = SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 11);
/*  53 */     Throwable maxOrderFallbackCause = null;
/*     */     try {
/*  55 */       validateAndCalculateChunkSize(DEFAULT_PAGE_SIZE, defaultMaxOrder);
/*  56 */     } catch (Throwable t) {
/*  57 */       maxOrderFallbackCause = t;
/*  58 */       defaultMaxOrder = 11;
/*     */     } 
/*  60 */     DEFAULT_MAX_ORDER = defaultMaxOrder;
/*     */ 
/*     */ 
/*     */     
/*  64 */     Runtime runtime = Runtime.getRuntime();
/*  65 */     int defaultChunkSize = DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER;
/*  66 */     DEFAULT_NUM_HEAP_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", (int)Math.min(runtime.availableProcessors(), Runtime.getRuntime().maxMemory() / defaultChunkSize / 2L / 3L)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     DEFAULT_NUM_DIRECT_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", (int)Math.min(runtime.availableProcessors(), PlatformDependent.maxDirectMemory() / defaultChunkSize / 2L / 3L)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     if (logger.isDebugEnabled()) {
/*  80 */       logger.debug("-Dio.netty.allocator.numHeapArenas: {}", Integer.valueOf(DEFAULT_NUM_HEAP_ARENA));
/*  81 */       logger.debug("-Dio.netty.allocator.numDirectArenas: {}", Integer.valueOf(DEFAULT_NUM_DIRECT_ARENA));
/*  82 */       if (pageSizeFallbackCause == null) {
/*  83 */         logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE));
/*     */       } else {
/*  85 */         logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE), pageSizeFallbackCause);
/*     */       } 
/*  87 */       if (maxOrderFallbackCause == null) {
/*  88 */         logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER));
/*     */       } else {
/*  90 */         logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER), maxOrderFallbackCause);
/*     */       } 
/*  92 */       logger.debug("-Dio.netty.allocator.chunkSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER));
/*     */     } 
/*     */ 
/*     */     
/*  96 */     DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   final ThreadLocal<PoolThreadCache> threadCache = new ThreadLocal<PoolThreadCache>() {
/* 103 */       private final AtomicInteger index = new AtomicInteger(); protected PoolThreadCache initialValue() {
/*     */         PoolArena<byte[]> heapArena;
/*     */         PoolArena<ByteBuffer> directArena;
/* 106 */         int idx = this.index.getAndIncrement();
/*     */ 
/*     */ 
/*     */         
/* 110 */         if (PooledByteBufAllocator.this.heapArenas != null) {
/* 111 */           heapArena = PooledByteBufAllocator.this.heapArenas[Math.abs(idx % PooledByteBufAllocator.this.heapArenas.length)];
/*     */         } else {
/* 113 */           heapArena = null;
/*     */         } 
/*     */         
/* 116 */         if (PooledByteBufAllocator.this.directArenas != null) {
/* 117 */           directArena = PooledByteBufAllocator.this.directArenas[Math.abs(idx % PooledByteBufAllocator.this.directArenas.length)];
/*     */         } else {
/* 119 */           directArena = null;
/*     */         } 
/*     */         
/* 122 */         return new PoolThreadCache(heapArena, directArena);
/*     */       }
/*     */     };
/*     */   
/*     */   public PooledByteBufAllocator() {
/* 127 */     this(false);
/*     */   }
/*     */   
/*     */   public PooledByteBufAllocator(boolean preferDirect) {
/* 131 */     this(preferDirect, DEFAULT_NUM_HEAP_ARENA, DEFAULT_NUM_DIRECT_ARENA, DEFAULT_PAGE_SIZE, DEFAULT_MAX_ORDER);
/*     */   }
/*     */   
/*     */   public PooledByteBufAllocator(int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
/* 135 */     this(false, nHeapArena, nDirectArena, pageSize, maxOrder);
/*     */   }
/*     */   
/*     */   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
/* 139 */     super(preferDirect);
/*     */     
/* 141 */     int chunkSize = validateAndCalculateChunkSize(pageSize, maxOrder);
/*     */     
/* 143 */     if (nHeapArena < 0) {
/* 144 */       throw new IllegalArgumentException("nHeapArena: " + nHeapArena + " (expected: >= 0)");
/*     */     }
/* 146 */     if (nDirectArena < 0) {
/* 147 */       throw new IllegalArgumentException("nDirectArea: " + nDirectArena + " (expected: >= 0)");
/*     */     }
/*     */     
/* 150 */     int pageShifts = validateAndCalculatePageShifts(pageSize);
/*     */     
/* 152 */     if (nHeapArena > 0) {
/* 153 */       this.heapArenas = newArenaArray(nHeapArena);
/* 154 */       for (int i = 0; i < this.heapArenas.length; i++) {
/* 155 */         this.heapArenas[i] = new PoolArena.HeapArena(this, pageSize, maxOrder, pageShifts, chunkSize);
/*     */       }
/*     */     } else {
/* 158 */       this.heapArenas = null;
/*     */     } 
/*     */     
/* 161 */     if (nDirectArena > 0) {
/* 162 */       this.directArenas = newArenaArray(nDirectArena);
/* 163 */       for (int i = 0; i < this.directArenas.length; i++) {
/* 164 */         this.directArenas[i] = new PoolArena.DirectArena(this, pageSize, maxOrder, pageShifts, chunkSize);
/*     */       }
/*     */     } else {
/* 167 */       this.directArenas = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> PoolArena<T>[] newArenaArray(int size) {
/* 173 */     return (PoolArena<T>[])new PoolArena[size];
/*     */   }
/*     */   
/*     */   private static int validateAndCalculatePageShifts(int pageSize) {
/* 177 */     if (pageSize < 4096) {
/* 178 */       throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: 4096+)");
/*     */     }
/*     */ 
/*     */     
/* 182 */     boolean found1 = false;
/* 183 */     int pageShifts = 0;
/* 184 */     for (int i = pageSize; i != 0; i >>= 1) {
/* 185 */       if ((i & 0x1) != 0) {
/* 186 */         if (!found1) {
/* 187 */           found1 = true;
/*     */         } else {
/* 189 */           throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: power of 2");
/*     */         }
/*     */       
/* 192 */       } else if (!found1) {
/* 193 */         pageShifts++;
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     return pageShifts;
/*     */   }
/*     */   
/*     */   private static int validateAndCalculateChunkSize(int pageSize, int maxOrder) {
/* 201 */     if (maxOrder > 14) {
/* 202 */       throw new IllegalArgumentException("maxOrder: " + maxOrder + " (expected: 0-14)");
/*     */     }
/*     */ 
/*     */     
/* 206 */     int chunkSize = pageSize;
/* 207 */     for (int i = maxOrder; i > 0; i--) {
/* 208 */       if (chunkSize > 536870912) {
/* 209 */         throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", new Object[] { Integer.valueOf(pageSize), Integer.valueOf(maxOrder), Integer.valueOf(1073741824) }));
/*     */       }
/*     */       
/* 212 */       chunkSize <<= 1;
/*     */     } 
/* 214 */     return chunkSize;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
/* 219 */     PoolThreadCache cache = this.threadCache.get();
/* 220 */     PoolArena<byte[]> heapArena = cache.heapArena;
/* 221 */     if (heapArena != null) {
/* 222 */       return heapArena.allocate(cache, initialCapacity, maxCapacity);
/*     */     }
/* 224 */     return new UnpooledHeapByteBuf(this, initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
/* 230 */     PoolThreadCache cache = this.threadCache.get();
/* 231 */     PoolArena<ByteBuffer> directArena = cache.directArena;
/* 232 */     if (directArena != null) {
/* 233 */       return directArena.allocate(cache, initialCapacity, maxCapacity);
/*     */     }
/* 235 */     if (PlatformDependent.hasUnsafe()) {
/* 236 */       return new UnpooledUnsafeDirectByteBuf(this, initialCapacity, maxCapacity);
/*     */     }
/* 238 */     return new UnpooledDirectByteBuf(this, initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirectBufferPooled() {
/* 245 */     return (this.directArenas != null);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 249 */     StringBuilder buf = new StringBuilder();
/* 250 */     buf.append(this.heapArenas.length);
/* 251 */     buf.append(" heap arena(s):");
/* 252 */     buf.append(StringUtil.NEWLINE);
/* 253 */     for (PoolArena<byte[]> a : this.heapArenas) {
/* 254 */       buf.append(a);
/*     */     }
/* 256 */     buf.append(this.directArenas.length);
/* 257 */     buf.append(" direct arena(s):");
/* 258 */     buf.append(StringUtil.NEWLINE);
/* 259 */     for (PoolArena<ByteBuffer> a : this.directArenas) {
/* 260 */       buf.append(a);
/*     */     }
/* 262 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\PooledByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */