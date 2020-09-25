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
/*     */ 
/*     */ final class PoolSubpage<T>
/*     */ {
/*     */   final PoolChunk<T> chunk;
/*     */   final int memoryMapIdx;
/*     */   final int runOffset;
/*     */   final int pageSize;
/*     */   final long[] bitmap;
/*     */   PoolSubpage<T> prev;
/*     */   PoolSubpage<T> next;
/*     */   boolean doNotDestroy;
/*     */   int elemSize;
/*     */   int maxNumElems;
/*     */   int nextAvail;
/*     */   int bitmapLength;
/*     */   int numAvail;
/*     */   
/*     */   PoolSubpage(int pageSize) {
/*  42 */     this.chunk = null;
/*  43 */     this.memoryMapIdx = -1;
/*  44 */     this.runOffset = -1;
/*  45 */     this.elemSize = -1;
/*  46 */     this.pageSize = pageSize;
/*  47 */     this.bitmap = null;
/*     */   }
/*     */   
/*     */   PoolSubpage(PoolChunk<T> chunk, int memoryMapIdx, int runOffset, int pageSize, int elemSize) {
/*  51 */     this.chunk = chunk;
/*  52 */     this.memoryMapIdx = memoryMapIdx;
/*  53 */     this.runOffset = runOffset;
/*  54 */     this.pageSize = pageSize;
/*  55 */     this.bitmap = new long[pageSize >>> 10];
/*  56 */     init(elemSize);
/*     */   }
/*     */   
/*     */   void init(int elemSize) {
/*  60 */     this.doNotDestroy = true;
/*  61 */     this.elemSize = elemSize;
/*  62 */     if (elemSize != 0) {
/*  63 */       this.maxNumElems = this.numAvail = this.pageSize / elemSize;
/*  64 */       this.nextAvail = 0;
/*  65 */       this.bitmapLength = this.maxNumElems >>> 6;
/*  66 */       if ((this.maxNumElems & 0x3F) != 0) {
/*  67 */         this.bitmapLength++;
/*     */       }
/*     */       
/*  70 */       for (int i = 0; i < this.bitmapLength; i++) {
/*  71 */         this.bitmap[i] = 0L;
/*     */       }
/*     */     } 
/*     */     
/*  75 */     addToPool();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long allocate() {
/*  82 */     if (this.elemSize == 0) {
/*  83 */       return toHandle(0);
/*     */     }
/*     */     
/*  86 */     if (this.numAvail == 0 || !this.doNotDestroy) {
/*  87 */       return -1L;
/*     */     }
/*     */     
/*  90 */     int bitmapIdx = this.nextAvail;
/*  91 */     int q = bitmapIdx >>> 6;
/*  92 */     int r = bitmapIdx & 0x3F;
/*  93 */     assert (this.bitmap[q] >>> r & 0x1L) == 0L;
/*  94 */     this.bitmap[q] = this.bitmap[q] | 1L << r;
/*     */     
/*  96 */     if (--this.numAvail == 0) {
/*  97 */       removeFromPool();
/*  98 */       this.nextAvail = -1;
/*     */     } else {
/* 100 */       this.nextAvail = findNextAvailable();
/*     */     } 
/*     */     
/* 103 */     return toHandle(bitmapIdx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean free(int bitmapIdx) {
/* 112 */     if (this.elemSize == 0) {
/* 113 */       return true;
/*     */     }
/*     */     
/* 116 */     int q = bitmapIdx >>> 6;
/* 117 */     int r = bitmapIdx & 0x3F;
/* 118 */     assert (this.bitmap[q] >>> r & 0x1L) != 0L;
/* 119 */     this.bitmap[q] = this.bitmap[q] ^ 1L << r;
/*     */     
/* 121 */     if (this.numAvail++ == 0) {
/* 122 */       this.nextAvail = bitmapIdx;
/* 123 */       addToPool();
/* 124 */       return true;
/*     */     } 
/*     */     
/* 127 */     if (this.numAvail != this.maxNumElems) {
/* 128 */       return true;
/*     */     }
/*     */     
/* 131 */     if (this.prev == this.next)
/*     */     {
/* 133 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 137 */     this.doNotDestroy = false;
/* 138 */     removeFromPool();
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToPool() {
/* 144 */     PoolSubpage<T> head = this.chunk.arena.findSubpagePoolHead(this.elemSize);
/* 145 */     assert this.prev == null && this.next == null;
/* 146 */     this.prev = head;
/* 147 */     this.next = head.next;
/* 148 */     this.next.prev = this;
/* 149 */     head.next = this;
/*     */   }
/*     */   
/*     */   private void removeFromPool() {
/* 153 */     assert this.prev != null && this.next != null;
/* 154 */     this.prev.next = this.next;
/* 155 */     this.next.prev = this.prev;
/* 156 */     this.next = null;
/* 157 */     this.prev = null;
/*     */   }
/*     */   
/*     */   private int findNextAvailable() {
/* 161 */     int newNextAvail = -1;
/*     */     int i;
/* 163 */     label20: for (i = 0; i < this.bitmapLength; i++) {
/* 164 */       long bits = this.bitmap[i];
/* 165 */       if ((bits ^ 0xFFFFFFFFFFFFFFFFL) != 0L) {
/* 166 */         for (int j = 0; j < 64; j++) {
/* 167 */           if ((bits & 0x1L) == 0L) {
/* 168 */             newNextAvail = i << 6 | j;
/*     */             break label20;
/*     */           } 
/* 171 */           bits >>>= 1L;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 176 */     if (newNextAvail < this.maxNumElems) {
/* 177 */       return newNextAvail;
/*     */     }
/* 179 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private long toHandle(int bitmapIdx) {
/* 184 */     return 0x4000000000000000L | bitmapIdx << 32L | this.memoryMapIdx;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 188 */     if (!this.doNotDestroy) {
/* 189 */       return "(" + this.memoryMapIdx + ": not in use)";
/*     */     }
/*     */     
/* 192 */     return String.valueOf('(') + this.memoryMapIdx + ": " + (this.maxNumElems - this.numAvail) + '/' + this.maxNumElems + ", offset: " + this.runOffset + ", length: " + this.pageSize + ", elemSize: " + this.elemSize + ')';
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\PoolSubpage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */