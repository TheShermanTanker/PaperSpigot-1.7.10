/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import net.minecraft.util.io.netty.util.Recycler;
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
/*     */ abstract class PooledByteBuf<T>
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   private final ResourceLeak leak;
/*     */   private final Recycler.Handle recyclerHandle;
/*     */   protected PoolChunk<T> chunk;
/*     */   protected long handle;
/*     */   protected T memory;
/*     */   protected int offset;
/*     */   protected int length;
/*     */   private int maxLength;
/*     */   private ByteBuffer tmpNioBuf;
/*     */   
/*     */   protected PooledByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
/*  40 */     super(maxCapacity);
/*  41 */     this.leak = leakDetector.open(this);
/*  42 */     this.recyclerHandle = recyclerHandle;
/*     */   }
/*     */   
/*     */   void init(PoolChunk<T> chunk, long handle, int offset, int length, int maxLength) {
/*  46 */     assert handle >= 0L;
/*  47 */     assert chunk != null;
/*     */     
/*  49 */     this.chunk = chunk;
/*  50 */     this.handle = handle;
/*  51 */     this.memory = chunk.memory;
/*  52 */     this.offset = offset;
/*  53 */     this.length = length;
/*  54 */     this.maxLength = maxLength;
/*  55 */     setIndex(0, 0);
/*  56 */     this.tmpNioBuf = null;
/*     */   }
/*     */   
/*     */   void initUnpooled(PoolChunk<T> chunk, int length) {
/*  60 */     assert chunk != null;
/*     */     
/*  62 */     this.chunk = chunk;
/*  63 */     this.handle = 0L;
/*  64 */     this.memory = chunk.memory;
/*  65 */     this.offset = 0;
/*  66 */     this.length = this.maxLength = length;
/*  67 */     setIndex(0, 0);
/*  68 */     this.tmpNioBuf = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int capacity() {
/*  73 */     return this.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf capacity(int newCapacity) {
/*  78 */     ensureAccessible();
/*     */ 
/*     */     
/*  81 */     if (this.chunk.unpooled) {
/*  82 */       if (newCapacity == this.length) {
/*  83 */         return this;
/*     */       }
/*     */     }
/*  86 */     else if (newCapacity > this.length) {
/*  87 */       if (newCapacity <= this.maxLength) {
/*  88 */         this.length = newCapacity;
/*  89 */         return this;
/*     */       } 
/*  91 */     } else if (newCapacity < this.length) {
/*  92 */       if (newCapacity > this.maxLength >>> 1) {
/*  93 */         if (this.maxLength <= 512) {
/*  94 */           if (newCapacity > this.maxLength - 16) {
/*  95 */             this.length = newCapacity;
/*  96 */             setIndex(Math.min(readerIndex(), newCapacity), Math.min(writerIndex(), newCapacity));
/*  97 */             return this;
/*     */           } 
/*     */         } else {
/* 100 */           this.length = newCapacity;
/* 101 */           setIndex(Math.min(readerIndex(), newCapacity), Math.min(writerIndex(), newCapacity));
/* 102 */           return this;
/*     */         } 
/*     */       }
/*     */     } else {
/* 106 */       return this;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 111 */     this.chunk.arena.reallocate(this, newCapacity, true);
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBufAllocator alloc() {
/* 117 */     return this.chunk.arena.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteOrder order() {
/* 122 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf unwrap() {
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   protected final ByteBuffer internalNioBuffer() {
/* 131 */     ByteBuffer tmpNioBuf = this.tmpNioBuf;
/* 132 */     if (tmpNioBuf == null) {
/* 133 */       this.tmpNioBuf = tmpNioBuf = newInternalNioBuffer(this.memory);
/*     */     }
/* 135 */     return tmpNioBuf;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract ByteBuffer newInternalNioBuffer(T paramT);
/*     */   
/*     */   protected final void deallocate() {
/* 142 */     if (this.handle >= 0L) {
/* 143 */       long handle = this.handle;
/* 144 */       this.handle = -1L;
/* 145 */       this.memory = null;
/* 146 */       this.chunk.arena.free(this.chunk, handle);
/* 147 */       if (this.leak != null) {
/* 148 */         this.leak.close();
/*     */       } else {
/* 150 */         recycle();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void recycle() {
/* 157 */     Recycler.Handle recyclerHandle = this.recyclerHandle;
/* 158 */     if (recyclerHandle != null) {
/* 159 */       recycler().recycle(this, recyclerHandle);
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract Recycler<?> recycler();
/*     */   
/*     */   protected final int idx(int index) {
/* 166 */     return this.offset + index;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\PooledByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */