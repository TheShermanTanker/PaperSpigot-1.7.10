/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
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
/*     */ public final class ChannelFlushPromiseNotifier
/*     */ {
/*     */   private long writeCounter;
/*  28 */   private final Queue<FlushCheckpoint> flushCheckpoints = new ArrayDeque<FlushCheckpoint>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean tryNotify;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier(boolean tryNotify) {
/*  40 */     this.tryNotify = tryNotify;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier() {
/*  48 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier add(ChannelPromise promise, int pendingDataSize) {
/*  56 */     if (promise == null) {
/*  57 */       throw new NullPointerException("promise");
/*     */     }
/*  59 */     if (pendingDataSize < 0) {
/*  60 */       throw new IllegalArgumentException("pendingDataSize must be >= 0 but was" + pendingDataSize);
/*     */     }
/*  62 */     long checkpoint = this.writeCounter + pendingDataSize;
/*  63 */     if (promise instanceof FlushCheckpoint) {
/*  64 */       FlushCheckpoint cp = (FlushCheckpoint)promise;
/*  65 */       cp.flushCheckpoint(checkpoint);
/*  66 */       this.flushCheckpoints.add(cp);
/*     */     } else {
/*  68 */       this.flushCheckpoints.add(new DefaultFlushCheckpoint(checkpoint, promise));
/*     */     } 
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier increaseWriteCounter(long delta) {
/*  77 */     if (delta < 0L) {
/*  78 */       throw new IllegalArgumentException("delta must be >= 0 but was" + delta);
/*     */     }
/*  80 */     this.writeCounter += delta;
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long writeCounter() {
/*  88 */     return this.writeCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier notifyFlushFutures() {
/*  99 */     notifyFlushFutures0(null);
/* 100 */     return this;
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
/*     */   public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable cause) {
/* 115 */     notifyFlushFutures();
/*     */     while (true) {
/* 117 */       FlushCheckpoint cp = this.flushCheckpoints.poll();
/* 118 */       if (cp == null) {
/*     */         break;
/*     */       }
/* 121 */       if (this.tryNotify) {
/* 122 */         cp.promise().tryFailure(cause); continue;
/*     */       } 
/* 124 */       cp.promise().setFailure(cause);
/*     */     } 
/*     */     
/* 127 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable cause1, Throwable cause2) {
/* 147 */     notifyFlushFutures0(cause1);
/*     */     while (true) {
/* 149 */       FlushCheckpoint cp = this.flushCheckpoints.poll();
/* 150 */       if (cp == null) {
/*     */         break;
/*     */       }
/* 153 */       if (this.tryNotify) {
/* 154 */         cp.promise().tryFailure(cause2); continue;
/*     */       } 
/* 156 */       cp.promise().setFailure(cause2);
/*     */     } 
/*     */     
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   private void notifyFlushFutures0(Throwable cause) {
/* 163 */     if (this.flushCheckpoints.isEmpty()) {
/* 164 */       this.writeCounter = 0L;
/*     */       
/*     */       return;
/*     */     } 
/* 168 */     long writeCounter = this.writeCounter;
/*     */     while (true) {
/* 170 */       FlushCheckpoint cp = this.flushCheckpoints.peek();
/* 171 */       if (cp == null) {
/*     */         
/* 173 */         this.writeCounter = 0L;
/*     */         
/*     */         break;
/*     */       } 
/* 177 */       if (cp.flushCheckpoint() > writeCounter) {
/* 178 */         if (writeCounter > 0L && this.flushCheckpoints.size() == 1) {
/* 179 */           this.writeCounter = 0L;
/* 180 */           cp.flushCheckpoint(cp.flushCheckpoint() - writeCounter);
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/* 185 */       this.flushCheckpoints.remove();
/* 186 */       if (cause == null) {
/* 187 */         if (this.tryNotify) {
/* 188 */           cp.promise().trySuccess(); continue;
/*     */         } 
/* 190 */         cp.promise().setSuccess();
/*     */         continue;
/*     */       } 
/* 193 */       if (this.tryNotify) {
/* 194 */         cp.promise().tryFailure(cause); continue;
/*     */       } 
/* 196 */       cp.promise().setFailure(cause);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     long newWriteCounter = this.writeCounter;
/* 203 */     if (newWriteCounter >= 549755813888L) {
/*     */ 
/*     */       
/* 206 */       this.writeCounter = 0L;
/* 207 */       for (FlushCheckpoint cp : this.flushCheckpoints) {
/* 208 */         cp.flushCheckpoint(cp.flushCheckpoint() - newWriteCounter);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DefaultFlushCheckpoint
/*     */     implements FlushCheckpoint
/*     */   {
/*     */     private long checkpoint;
/*     */     
/*     */     private final ChannelPromise future;
/*     */ 
/*     */     
/*     */     DefaultFlushCheckpoint(long checkpoint, ChannelPromise future) {
/* 224 */       this.checkpoint = checkpoint;
/* 225 */       this.future = future;
/*     */     }
/*     */ 
/*     */     
/*     */     public long flushCheckpoint() {
/* 230 */       return this.checkpoint;
/*     */     }
/*     */ 
/*     */     
/*     */     public void flushCheckpoint(long checkpoint) {
/* 235 */       this.checkpoint = checkpoint;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelPromise promise() {
/* 240 */       return this.future;
/*     */     }
/*     */   }
/*     */   
/*     */   static interface FlushCheckpoint {
/*     */     long flushCheckpoint();
/*     */     
/*     */     void flushCheckpoint(long param1Long);
/*     */     
/*     */     ChannelPromise promise();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\ChannelFlushPromiseNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */