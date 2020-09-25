/*     */ package net.minecraft.util.io.netty.buffer;
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
/*     */ final class PoolChunk<T>
/*     */ {
/*     */   private static final int ST_UNUSED = 0;
/*     */   private static final int ST_BRANCH = 1;
/*     */   private static final int ST_ALLOCATED = 2;
/*     */   private static final int ST_ALLOCATED_SUBPAGE = 3;
/*     */   private static final long multiplier = 25214903917L;
/*     */   private static final long addend = 11L;
/*     */   private static final long mask = 281474976710655L;
/*     */   final PoolArena<T> arena;
/*     */   final T memory;
/*     */   final boolean unpooled;
/*     */   private final int[] memoryMap;
/*     */   private final PoolSubpage<T>[] subpages;
/*     */   private final int subpageOverflowMask;
/*     */   private final int pageSize;
/*     */   private final int pageShifts;
/*     */   private final int chunkSize;
/*     */   private final int maxSubpageAllocs;
/*  43 */   private long random = (System.nanoTime() ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL;
/*     */   
/*     */   private int freeBytes;
/*     */   
/*     */   PoolChunkList<T> parent;
/*     */   
/*     */   PoolChunk<T> prev;
/*     */   
/*     */   PoolChunk<T> next;
/*     */ 
/*     */   
/*     */   PoolChunk(PoolArena<T> arena, T memory, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
/*  55 */     this.unpooled = false;
/*  56 */     this.arena = arena;
/*  57 */     this.memory = memory;
/*  58 */     this.pageSize = pageSize;
/*  59 */     this.pageShifts = pageShifts;
/*  60 */     this.chunkSize = chunkSize;
/*  61 */     this.subpageOverflowMask = pageSize - 1 ^ 0xFFFFFFFF;
/*  62 */     this.freeBytes = chunkSize;
/*     */     
/*  64 */     int chunkSizeInPages = chunkSize >>> pageShifts;
/*  65 */     this.maxSubpageAllocs = 1 << maxOrder;
/*     */ 
/*     */     
/*  68 */     this.memoryMap = new int[this.maxSubpageAllocs << 1];
/*  69 */     int memoryMapIndex = 1;
/*  70 */     for (int i = 0; i <= maxOrder; i++) {
/*  71 */       int runSizeInPages = chunkSizeInPages >>> i; int j;
/*  72 */       for (j = 0; j < chunkSizeInPages; j += runSizeInPages)
/*     */       {
/*  74 */         this.memoryMap[memoryMapIndex++] = j << 17 | runSizeInPages << 2 | 0x0;
/*     */       }
/*     */     } 
/*     */     
/*  78 */     this.subpages = newSubpageArray(this.maxSubpageAllocs);
/*     */   }
/*     */ 
/*     */   
/*     */   PoolChunk(PoolArena<T> arena, T memory, int size) {
/*  83 */     this.unpooled = true;
/*  84 */     this.arena = arena;
/*  85 */     this.memory = memory;
/*  86 */     this.memoryMap = null;
/*  87 */     this.subpages = null;
/*  88 */     this.subpageOverflowMask = 0;
/*  89 */     this.pageSize = 0;
/*  90 */     this.pageShifts = 0;
/*  91 */     this.chunkSize = size;
/*  92 */     this.maxSubpageAllocs = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private PoolSubpage<T>[] newSubpageArray(int size) {
/*  97 */     return (PoolSubpage<T>[])new PoolSubpage[size];
/*     */   }
/*     */   
/*     */   int usage() {
/* 101 */     if (this.freeBytes == 0) {
/* 102 */       return 100;
/*     */     }
/*     */     
/* 105 */     int freePercentage = (int)(this.freeBytes * 100L / this.chunkSize);
/* 106 */     if (freePercentage == 0) {
/* 107 */       return 99;
/*     */     }
/* 109 */     return 100 - freePercentage;
/*     */   }
/*     */   
/*     */   long allocate(int normCapacity) {
/* 113 */     int firstVal = this.memoryMap[1];
/* 114 */     if ((normCapacity & this.subpageOverflowMask) != 0) {
/* 115 */       return allocateRun(normCapacity, 1, firstVal);
/*     */     }
/* 117 */     return allocateSubpage(normCapacity, 1, firstVal);
/*     */   }
/*     */ 
/*     */   
/*     */   private long allocateRun(int normCapacity, int curIdx, int val) {
/*     */     while (true) {
/* 123 */       if ((val & 0x2) != 0) {
/* 124 */         return -1L;
/*     */       }
/*     */       
/* 127 */       if ((val & 0x1) != 0) {
/* 128 */         int nextIdx = curIdx << 1 ^ nextRandom();
/* 129 */         long res = allocateRun(normCapacity, nextIdx, this.memoryMap[nextIdx]);
/* 130 */         if (res > 0L) {
/* 131 */           return res;
/*     */         }
/*     */         
/* 134 */         curIdx = nextIdx ^ 0x1;
/* 135 */         val = this.memoryMap[curIdx];
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 140 */     return allocateRunSimple(normCapacity, curIdx, val);
/*     */   }
/*     */ 
/*     */   
/*     */   private long allocateRunSimple(int normCapacity, int curIdx, int val) {
/* 145 */     int runLength = runLength(val);
/* 146 */     if (normCapacity > runLength) {
/* 147 */       return -1L;
/*     */     }
/*     */     
/*     */     while (true) {
/* 151 */       if (normCapacity == runLength) {
/*     */ 
/*     */ 
/*     */         
/* 155 */         this.memoryMap[curIdx] = val & 0xFFFFFFFC | 0x2;
/* 156 */         this.freeBytes -= runLength;
/* 157 */         return curIdx;
/*     */       } 
/*     */       
/* 160 */       int nextIdx = curIdx << 1 ^ nextRandom();
/* 161 */       int unusedIdx = nextIdx ^ 0x1;
/*     */       
/* 163 */       this.memoryMap[curIdx] = val & 0xFFFFFFFC | 0x1;
/*     */       
/* 165 */       this.memoryMap[unusedIdx] = this.memoryMap[unusedIdx] & 0xFFFFFFFC | 0x0;
/*     */       
/* 167 */       runLength >>>= 1;
/* 168 */       curIdx = nextIdx;
/* 169 */       val = this.memoryMap[curIdx];
/*     */     } 
/*     */   }
/*     */   
/*     */   private long allocateSubpage(int normCapacity, int curIdx, int val) {
/* 174 */     int state = val & 0x3;
/* 175 */     if (state == 1) {
/* 176 */       int nextIdx = curIdx << 1 ^ nextRandom();
/* 177 */       long res = branchSubpage(normCapacity, nextIdx);
/* 178 */       if (res > 0L) {
/* 179 */         return res;
/*     */       }
/*     */       
/* 182 */       return branchSubpage(normCapacity, nextIdx ^ 0x1);
/*     */     } 
/*     */     
/* 185 */     if (state == 0) {
/* 186 */       return allocateSubpageSimple(normCapacity, curIdx, val);
/*     */     }
/*     */     
/* 189 */     if (state == 3) {
/* 190 */       PoolSubpage<T> subpage = this.subpages[subpageIdx(curIdx)];
/* 191 */       int elemSize = subpage.elemSize;
/* 192 */       if (normCapacity != elemSize) {
/* 193 */         return -1L;
/*     */       }
/*     */       
/* 196 */       return subpage.allocate();
/*     */     } 
/*     */     
/* 199 */     return -1L;
/*     */   }
/*     */   
/*     */   private long allocateSubpageSimple(int normCapacity, int curIdx, int val) {
/* 203 */     int runLength = runLength(val);
/*     */     while (true) {
/* 205 */       if (runLength == this.pageSize) {
/* 206 */         this.memoryMap[curIdx] = val & 0xFFFFFFFC | 0x3;
/* 207 */         this.freeBytes -= runLength;
/*     */         
/* 209 */         int subpageIdx = subpageIdx(curIdx);
/* 210 */         PoolSubpage<T> subpage = this.subpages[subpageIdx];
/* 211 */         if (subpage == null) {
/* 212 */           subpage = new PoolSubpage<T>(this, curIdx, runOffset(val), this.pageSize, normCapacity);
/* 213 */           this.subpages[subpageIdx] = subpage;
/*     */         } else {
/* 215 */           subpage.init(normCapacity);
/*     */         } 
/* 217 */         return subpage.allocate();
/*     */       } 
/*     */       
/* 220 */       int nextIdx = curIdx << 1 ^ nextRandom();
/* 221 */       int unusedIdx = nextIdx ^ 0x1;
/*     */       
/* 223 */       this.memoryMap[curIdx] = val & 0xFFFFFFFC | 0x1;
/*     */       
/* 225 */       this.memoryMap[unusedIdx] = this.memoryMap[unusedIdx] & 0xFFFFFFFC | 0x0;
/*     */       
/* 227 */       runLength >>>= 1;
/* 228 */       curIdx = nextIdx;
/* 229 */       val = this.memoryMap[curIdx];
/*     */     } 
/*     */   }
/*     */   
/*     */   private long branchSubpage(int normCapacity, int nextIdx) {
/* 234 */     int nextVal = this.memoryMap[nextIdx];
/* 235 */     if ((nextVal & 0x3) != 2) {
/* 236 */       return allocateSubpage(normCapacity, nextIdx, nextVal);
/*     */     }
/* 238 */     return -1L;
/*     */   }
/*     */   
/*     */   void free(long handle) {
/* 242 */     int memoryMapIdx = (int)handle;
/* 243 */     int bitmapIdx = (int)(handle >>> 32L);
/*     */     
/* 245 */     int val = this.memoryMap[memoryMapIdx];
/* 246 */     int state = val & 0x3;
/* 247 */     if (state == 3) {
/* 248 */       assert bitmapIdx != 0;
/* 249 */       PoolSubpage<T> subpage = this.subpages[subpageIdx(memoryMapIdx)];
/* 250 */       assert subpage != null && subpage.doNotDestroy;
/* 251 */       if (subpage.free(bitmapIdx & 0x3FFFFFFF)) {
/*     */         return;
/*     */       }
/*     */     } else {
/* 255 */       assert state == 2 : "state: " + state;
/* 256 */       assert bitmapIdx == 0;
/*     */     } 
/*     */     
/* 259 */     this.freeBytes += runLength(val);
/*     */ 
/*     */     
/*     */     while (true) {
/* 263 */       this.memoryMap[memoryMapIdx] = val & 0xFFFFFFFC | 0x0;
/* 264 */       if (memoryMapIdx == 1) {
/* 265 */         assert this.freeBytes == this.chunkSize;
/*     */         
/*     */         return;
/*     */       } 
/* 269 */       if ((this.memoryMap[siblingIdx(memoryMapIdx)] & 0x3) != 0) {
/*     */         break;
/*     */       }
/*     */       
/* 273 */       memoryMapIdx = parentIdx(memoryMapIdx);
/* 274 */       val = this.memoryMap[memoryMapIdx];
/*     */     } 
/*     */   }
/*     */   
/*     */   void initBuf(PooledByteBuf<T> buf, long handle, int reqCapacity) {
/* 279 */     int memoryMapIdx = (int)handle;
/* 280 */     int bitmapIdx = (int)(handle >>> 32L);
/* 281 */     if (bitmapIdx == 0) {
/* 282 */       int val = this.memoryMap[memoryMapIdx];
/* 283 */       assert (val & 0x3) == 2 : String.valueOf(val & 0x3);
/* 284 */       buf.init(this, handle, runOffset(val), reqCapacity, runLength(val));
/*     */     } else {
/* 286 */       initBufWithSubpage(buf, handle, bitmapIdx, reqCapacity);
/*     */     } 
/*     */   }
/*     */   
/*     */   void initBufWithSubpage(PooledByteBuf<T> buf, long handle, int reqCapacity) {
/* 291 */     initBufWithSubpage(buf, handle, (int)(handle >>> 32L), reqCapacity);
/*     */   }
/*     */   
/*     */   private void initBufWithSubpage(PooledByteBuf<T> buf, long handle, int bitmapIdx, int reqCapacity) {
/* 295 */     assert bitmapIdx != 0;
/*     */     
/* 297 */     int memoryMapIdx = (int)handle;
/* 298 */     int val = this.memoryMap[memoryMapIdx];
/* 299 */     assert (val & 0x3) == 3;
/*     */     
/* 301 */     PoolSubpage<T> subpage = this.subpages[subpageIdx(memoryMapIdx)];
/* 302 */     assert subpage.doNotDestroy;
/* 303 */     assert reqCapacity <= subpage.elemSize;
/*     */     
/* 305 */     buf.init(this, handle, runOffset(val) + (bitmapIdx & 0x3FFFFFFF) * subpage.elemSize, reqCapacity, subpage.elemSize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parentIdx(int memoryMapIdx) {
/* 311 */     return memoryMapIdx >>> 1;
/*     */   }
/*     */   
/*     */   private static int siblingIdx(int memoryMapIdx) {
/* 315 */     return memoryMapIdx ^ 0x1;
/*     */   }
/*     */   
/*     */   private int runLength(int val) {
/* 319 */     return (val >>> 2 & 0x7FFF) << this.pageShifts;
/*     */   }
/*     */   
/*     */   private int runOffset(int val) {
/* 323 */     return val >>> 17 << this.pageShifts;
/*     */   }
/*     */   
/*     */   private int subpageIdx(int memoryMapIdx) {
/* 327 */     return memoryMapIdx - this.maxSubpageAllocs;
/*     */   }
/*     */   
/*     */   private int nextRandom() {
/* 331 */     this.random = this.random * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
/* 332 */     return (int)(this.random >>> 47L) & 0x1;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 336 */     StringBuilder buf = new StringBuilder();
/* 337 */     buf.append("Chunk(");
/* 338 */     buf.append(Integer.toHexString(System.identityHashCode(this)));
/* 339 */     buf.append(": ");
/* 340 */     buf.append(usage());
/* 341 */     buf.append("%, ");
/* 342 */     buf.append(this.chunkSize - this.freeBytes);
/* 343 */     buf.append('/');
/* 344 */     buf.append(this.chunkSize);
/* 345 */     buf.append(')');
/* 346 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\PoolChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */