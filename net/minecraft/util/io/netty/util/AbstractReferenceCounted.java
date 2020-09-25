/*     */ package net.minecraft.util.io.netty.util;
/*     */ 
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*     */ public abstract class AbstractReferenceCounted
/*     */   implements ReferenceCounted
/*     */ {
/*  27 */   private static final AtomicIntegerFieldUpdater<AbstractReferenceCounted> refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCounted.class, "refCnt");
/*     */   
/*     */   private static final long REFCNT_FIELD_OFFSET;
/*     */ 
/*     */   
/*     */   static {
/*  33 */     long refCntFieldOffset = -1L;
/*     */     try {
/*  35 */       if (PlatformDependent.hasUnsafe()) {
/*  36 */         refCntFieldOffset = PlatformDependent.objectFieldOffset(AbstractReferenceCounted.class.getDeclaredField("refCnt"));
/*     */       }
/*     */     }
/*  39 */     catch (Throwable t) {}
/*     */ 
/*     */ 
/*     */     
/*  43 */     REFCNT_FIELD_OFFSET = refCntFieldOffset;
/*     */   }
/*     */   
/*  46 */   private volatile int refCnt = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public final int refCnt() {
/*  51 */     if (REFCNT_FIELD_OFFSET >= 0L)
/*     */     {
/*  53 */       return PlatformDependent.getInt(this, REFCNT_FIELD_OFFSET);
/*     */     }
/*  55 */     return this.refCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setRefCnt(int refCnt) {
/*  63 */     this.refCnt = refCnt;
/*     */   }
/*     */   
/*     */   public ReferenceCounted retain() {
/*     */     int refCnt;
/*     */     do {
/*  69 */       refCnt = this.refCnt;
/*  70 */       if (refCnt == 0) {
/*  71 */         throw new IllegalReferenceCountException(0, 1);
/*     */       }
/*  73 */       if (refCnt == Integer.MAX_VALUE) {
/*  74 */         throw new IllegalReferenceCountException(2147483647, 1);
/*     */       }
/*  76 */     } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt + 1));
/*     */ 
/*     */ 
/*     */     
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   public ReferenceCounted retain(int increment) {
/*     */     int refCnt;
/*  85 */     if (increment <= 0) {
/*  86 */       throw new IllegalArgumentException("increment: " + increment + " (expected: > 0)");
/*     */     }
/*     */     
/*     */     do {
/*  90 */       refCnt = this.refCnt;
/*  91 */       if (refCnt == 0) {
/*  92 */         throw new IllegalReferenceCountException(0, 1);
/*     */       }
/*  94 */       if (refCnt > Integer.MAX_VALUE - increment) {
/*  95 */         throw new IllegalReferenceCountException(refCnt, increment);
/*     */       }
/*  97 */     } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt + increment));
/*     */ 
/*     */ 
/*     */     
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean release() {
/*     */     while (true) {
/* 107 */       int refCnt = this.refCnt;
/* 108 */       if (refCnt == 0) {
/* 109 */         throw new IllegalReferenceCountException(0, -1);
/*     */       }
/*     */       
/* 112 */       if (refCntUpdater.compareAndSet(this, refCnt, refCnt - 1)) {
/* 113 */         if (refCnt == 1) {
/* 114 */           deallocate();
/* 115 */           return true;
/*     */         } 
/* 117 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean release(int decrement) {
/* 124 */     if (decrement <= 0) {
/* 125 */       throw new IllegalArgumentException("decrement: " + decrement + " (expected: > 0)");
/*     */     }
/*     */     
/*     */     while (true) {
/* 129 */       int refCnt = this.refCnt;
/* 130 */       if (refCnt < decrement) {
/* 131 */         throw new IllegalReferenceCountException(refCnt, -decrement);
/*     */       }
/*     */       
/* 134 */       if (refCntUpdater.compareAndSet(this, refCnt, refCnt - decrement)) {
/* 135 */         if (refCnt == decrement) {
/* 136 */           deallocate();
/* 137 */           return true;
/*     */         } 
/* 139 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void deallocate();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\AbstractReferenceCounted.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */