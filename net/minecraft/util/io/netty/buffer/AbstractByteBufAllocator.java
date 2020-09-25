/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
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
/*     */ 
/*     */ public abstract class AbstractByteBufAllocator
/*     */   implements ByteBufAllocator
/*     */ {
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 256;
/*     */   private static final int DEFAULT_MAX_COMPONENTS = 16;
/*     */   private final boolean directByDefault;
/*     */   private final ByteBuf emptyBuf;
/*     */   
/*     */   protected AbstractByteBufAllocator() {
/*  35 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractByteBufAllocator(boolean preferDirect) {
/*  45 */     this.directByDefault = (preferDirect && PlatformDependent.hasUnsafe());
/*  46 */     this.emptyBuf = new EmptyByteBuf(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer() {
/*  51 */     if (this.directByDefault) {
/*  52 */       return directBuffer();
/*     */     }
/*  54 */     return heapBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer(int initialCapacity) {
/*  59 */     if (this.directByDefault) {
/*  60 */       return directBuffer(initialCapacity);
/*     */     }
/*  62 */     return heapBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer(int initialCapacity, int maxCapacity) {
/*  67 */     if (this.directByDefault) {
/*  68 */       return directBuffer(initialCapacity, maxCapacity);
/*     */     }
/*  70 */     return heapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer() {
/*  75 */     if (PlatformDependent.hasUnsafe()) {
/*  76 */       return directBuffer(0);
/*     */     }
/*  78 */     return heapBuffer(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer(int initialCapacity) {
/*  83 */     if (PlatformDependent.hasUnsafe()) {
/*  84 */       return directBuffer(initialCapacity);
/*     */     }
/*  86 */     return heapBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer(int initialCapacity, int maxCapacity) {
/*  91 */     if (PlatformDependent.hasUnsafe()) {
/*  92 */       return directBuffer(initialCapacity, maxCapacity);
/*     */     }
/*  94 */     return heapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer() {
/*  99 */     return heapBuffer(256, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer(int initialCapacity) {
/* 104 */     return heapBuffer(initialCapacity, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer(int initialCapacity, int maxCapacity) {
/* 109 */     if (initialCapacity == 0 && maxCapacity == 0) {
/* 110 */       return this.emptyBuf;
/*     */     }
/* 112 */     validate(initialCapacity, maxCapacity);
/* 113 */     return newHeapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer() {
/* 118 */     return directBuffer(256, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer(int initialCapacity) {
/* 123 */     return directBuffer(initialCapacity, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
/* 128 */     if (initialCapacity == 0 && maxCapacity == 0) {
/* 129 */       return this.emptyBuf;
/*     */     }
/* 131 */     validate(initialCapacity, maxCapacity);
/* 132 */     return newDirectBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeBuffer() {
/* 137 */     if (this.directByDefault) {
/* 138 */       return compositeDirectBuffer();
/*     */     }
/* 140 */     return compositeHeapBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeBuffer(int maxNumComponents) {
/* 145 */     if (this.directByDefault) {
/* 146 */       return compositeDirectBuffer(maxNumComponents);
/*     */     }
/* 148 */     return compositeHeapBuffer(maxNumComponents);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer() {
/* 153 */     return compositeHeapBuffer(16);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
/* 158 */     return new CompositeByteBuf(this, false, maxNumComponents);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer() {
/* 163 */     return compositeDirectBuffer(16);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
/* 168 */     return new CompositeByteBuf(this, true, maxNumComponents);
/*     */   }
/*     */   
/*     */   private static void validate(int initialCapacity, int maxCapacity) {
/* 172 */     if (initialCapacity < 0) {
/* 173 */       throw new IllegalArgumentException("initialCapacity: " + initialCapacity + " (expectd: 0+)");
/*     */     }
/* 175 */     if (initialCapacity > maxCapacity)
/* 176 */       throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", new Object[] { Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity) })); 
/*     */   }
/*     */   
/*     */   protected abstract ByteBuf newHeapBuffer(int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract ByteBuf newDirectBuffer(int paramInt1, int paramInt2);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\AbstractByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */