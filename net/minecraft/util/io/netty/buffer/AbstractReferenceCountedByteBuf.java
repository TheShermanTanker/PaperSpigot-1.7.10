/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import net.minecraft.util.io.netty.util.IllegalReferenceCountException;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCounted;
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
/*     */ public abstract class AbstractReferenceCountedByteBuf
/*     */   extends AbstractByteBuf
/*     */ {
/*  29 */   private static final AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
/*     */   
/*     */   private static final long REFCNT_FIELD_OFFSET;
/*     */ 
/*     */   
/*     */   static {
/*  35 */     long refCntFieldOffset = -1L;
/*     */     try {
/*  37 */       if (PlatformDependent.hasUnsafe()) {
/*  38 */         refCntFieldOffset = PlatformDependent.objectFieldOffset(AbstractReferenceCountedByteBuf.class.getDeclaredField("refCnt"));
/*     */       }
/*     */     }
/*  41 */     catch (Throwable t) {}
/*     */ 
/*     */ 
/*     */     
/*  45 */     REFCNT_FIELD_OFFSET = refCntFieldOffset;
/*     */   }
/*     */   
/*  48 */   private volatile int refCnt = 1;
/*     */ 
/*     */   
/*     */   protected AbstractReferenceCountedByteBuf(int maxCapacity) {
/*  52 */     super(maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int refCnt() {
/*  57 */     if (REFCNT_FIELD_OFFSET >= 0L)
/*     */     {
/*  59 */       return PlatformDependent.getInt(this, REFCNT_FIELD_OFFSET);
/*     */     }
/*  61 */     return this.refCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setRefCnt(int refCnt) {
/*  69 */     this.refCnt = refCnt;
/*     */   }
/*     */   
/*     */   public ByteBuf retain() {
/*     */     int refCnt;
/*     */     do {
/*  75 */       refCnt = this.refCnt;
/*  76 */       if (refCnt == 0) {
/*  77 */         throw new IllegalReferenceCountException(0, 1);
/*     */       }
/*  79 */       if (refCnt == Integer.MAX_VALUE) {
/*  80 */         throw new IllegalReferenceCountException(2147483647, 1);
/*     */       }
/*  82 */     } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt + 1));
/*     */ 
/*     */ 
/*     */     
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf retain(int increment) {
/*     */     int refCnt;
/*  91 */     if (increment <= 0) {
/*  92 */       throw new IllegalArgumentException("increment: " + increment + " (expected: > 0)");
/*     */     }
/*     */     
/*     */     do {
/*  96 */       refCnt = this.refCnt;
/*  97 */       if (refCnt == 0) {
/*  98 */         throw new IllegalReferenceCountException(0, increment);
/*     */       }
/* 100 */       if (refCnt > Integer.MAX_VALUE - increment) {
/* 101 */         throw new IllegalReferenceCountException(refCnt, increment);
/*     */       }
/* 103 */     } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt + increment));
/*     */ 
/*     */ 
/*     */     
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean release() {
/*     */     while (true) {
/* 113 */       int refCnt = this.refCnt;
/* 114 */       if (refCnt == 0) {
/* 115 */         throw new IllegalReferenceCountException(0, -1);
/*     */       }
/*     */       
/* 118 */       if (refCntUpdater.compareAndSet(this, refCnt, refCnt - 1)) {
/* 119 */         if (refCnt == 1) {
/* 120 */           deallocate();
/* 121 */           return true;
/*     */         } 
/* 123 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean release(int decrement) {
/* 130 */     if (decrement <= 0) {
/* 131 */       throw new IllegalArgumentException("decrement: " + decrement + " (expected: > 0)");
/*     */     }
/*     */     
/*     */     while (true) {
/* 135 */       int refCnt = this.refCnt;
/* 136 */       if (refCnt < decrement) {
/* 137 */         throw new IllegalReferenceCountException(refCnt, -decrement);
/*     */       }
/*     */       
/* 140 */       if (refCntUpdater.compareAndSet(this, refCnt, refCnt - decrement)) {
/* 141 */         if (refCnt == decrement) {
/* 142 */           deallocate();
/* 143 */           return true;
/*     */         } 
/* 145 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void deallocate();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\AbstractReferenceCountedByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */