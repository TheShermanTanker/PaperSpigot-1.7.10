/*     */ package net.minecraft.util.io.netty.handler.traffic;
/*     */ 
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ 
/*     */ 
/*     */ public class TrafficCounter
/*     */ {
/*  39 */   private final AtomicLong currentWrittenBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private final AtomicLong currentReadBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private final AtomicLong cumulativeReadBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastCumulativeTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastWriteThroughput;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastReadThroughput;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private final AtomicLong lastTime = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastWrittenBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastReadBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   final AtomicLong checkInterval = new AtomicLong(1000L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final AbstractTrafficShapingHandler trafficShapingHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ScheduledExecutorService executor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Runnable monitor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile ScheduledFuture<?> scheduledFuture;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   final AtomicBoolean monitorActive = new AtomicBoolean();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TrafficMonitoringTask
/*     */     implements Runnable
/*     */   {
/*     */     private final AbstractTrafficShapingHandler trafficShapingHandler1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final TrafficCounter counter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected TrafficMonitoringTask(AbstractTrafficShapingHandler trafficShapingHandler, TrafficCounter counter) {
/* 144 */       this.trafficShapingHandler1 = trafficShapingHandler;
/* 145 */       this.counter = counter;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 150 */       if (!this.counter.monitorActive.get()) {
/*     */         return;
/*     */       }
/* 153 */       long endTime = System.currentTimeMillis();
/* 154 */       this.counter.resetAccounting(endTime);
/* 155 */       if (this.trafficShapingHandler1 != null) {
/* 156 */         this.trafficShapingHandler1.doAccounting(this.counter);
/*     */       }
/* 158 */       this.counter.scheduledFuture = this.counter.executor.schedule(this, this.counter.checkInterval.get(), TimeUnit.MILLISECONDS);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 167 */     synchronized (this.lastTime) {
/* 168 */       if (this.monitorActive.get()) {
/*     */         return;
/*     */       }
/* 171 */       this.lastTime.set(System.currentTimeMillis());
/* 172 */       if (this.checkInterval.get() > 0L) {
/* 173 */         this.monitorActive.set(true);
/* 174 */         this.monitor = new TrafficMonitoringTask(this.trafficShapingHandler, this);
/* 175 */         this.scheduledFuture = this.executor.schedule(this.monitor, this.checkInterval.get(), TimeUnit.MILLISECONDS);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 185 */     synchronized (this.lastTime) {
/* 186 */       if (!this.monitorActive.get()) {
/*     */         return;
/*     */       }
/* 189 */       this.monitorActive.set(false);
/* 190 */       resetAccounting(System.currentTimeMillis());
/* 191 */       if (this.trafficShapingHandler != null) {
/* 192 */         this.trafficShapingHandler.doAccounting(this);
/*     */       }
/* 194 */       if (this.scheduledFuture != null) {
/* 195 */         this.scheduledFuture.cancel(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void resetAccounting(long newLastTime) {
/* 206 */     synchronized (this.lastTime) {
/* 207 */       long interval = newLastTime - this.lastTime.getAndSet(newLastTime);
/* 208 */       if (interval == 0L) {
/*     */         return;
/*     */       }
/*     */       
/* 212 */       this.lastReadBytes = this.currentReadBytes.getAndSet(0L);
/* 213 */       this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0L);
/* 214 */       this.lastReadThroughput = this.lastReadBytes / interval * 1000L;
/*     */       
/* 216 */       this.lastWriteThroughput = this.lastWrittenBytes / interval * 1000L;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public TrafficCounter(AbstractTrafficShapingHandler trafficShapingHandler, ScheduledExecutorService executor, String name, long checkInterval) {
/* 231 */     this.trafficShapingHandler = trafficShapingHandler;
/* 232 */     this.executor = executor;
/* 233 */     this.name = name;
/* 234 */     this.lastCumulativeTime = System.currentTimeMillis();
/* 235 */     configure(checkInterval);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configure(long newcheckInterval) {
/* 244 */     long newInterval = newcheckInterval / 10L * 10L;
/* 245 */     if (this.checkInterval.get() != newInterval) {
/* 246 */       this.checkInterval.set(newInterval);
/* 247 */       if (newInterval <= 0L) {
/* 248 */         stop();
/*     */         
/* 250 */         this.lastTime.set(System.currentTimeMillis());
/*     */       } else {
/*     */         
/* 253 */         start();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void bytesRecvFlowControl(long recv) {
/* 265 */     this.currentReadBytes.addAndGet(recv);
/* 266 */     this.cumulativeReadBytes.addAndGet(recv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void bytesWriteFlowControl(long write) {
/* 276 */     this.currentWrittenBytes.addAndGet(write);
/* 277 */     this.cumulativeWrittenBytes.addAndGet(write);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long checkInterval() {
/* 286 */     return this.checkInterval.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastReadThroughput() {
/* 294 */     return this.lastReadThroughput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastWriteThroughput() {
/* 302 */     return this.lastWriteThroughput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastReadBytes() {
/* 310 */     return this.lastReadBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastWrittenBytes() {
/* 318 */     return this.lastWrittenBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentReadBytes() {
/* 326 */     return this.currentReadBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentWrittenBytes() {
/* 334 */     return this.currentWrittenBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastTime() {
/* 341 */     return this.lastTime.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long cumulativeWrittenBytes() {
/* 348 */     return this.cumulativeWrittenBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long cumulativeReadBytes() {
/* 355 */     return this.cumulativeReadBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastCumulativeTime() {
/* 363 */     return this.lastCumulativeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetCumulativeTime() {
/* 370 */     this.lastCumulativeTime = System.currentTimeMillis();
/* 371 */     this.cumulativeReadBytes.set(0L);
/* 372 */     this.cumulativeWrittenBytes.set(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/* 379 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 387 */     return "Monitor " + this.name + " Current Speed Read: " + (this.lastReadThroughput >> 10L) + " KB/s, Write: " + (this.lastWriteThroughput >> 10L) + " KB/s Current Read: " + (this.currentReadBytes.get() >> 10L) + " KB Current Write: " + (this.currentWrittenBytes.get() >> 10L) + " KB";
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\traffic\TrafficCounter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */