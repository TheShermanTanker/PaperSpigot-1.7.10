/*     */ package net.minecraft.util.com.google.common.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.concurrent.ThreadSafe;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.base.Ticker;
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
/*     */ @ThreadSafe
/*     */ @Beta
/*     */ public abstract class RateLimiter
/*     */ {
/*     */   private final SleepingTicker ticker;
/*     */   private final long offsetNanos;
/*     */   double storedPermits;
/*     */   double maxPermits;
/*     */   volatile double stableIntervalMicros;
/*     */   
/*     */   public static RateLimiter create(double permitsPerSecond) {
/* 242 */     return create(SleepingTicker.SYSTEM_TICKER, permitsPerSecond);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static RateLimiter create(SleepingTicker ticker, double permitsPerSecond) {
/* 247 */     RateLimiter rateLimiter = new Bursty(ticker, 1.0D);
/* 248 */     rateLimiter.setRate(permitsPerSecond);
/* 249 */     return rateLimiter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit) {
/* 275 */     return create(SleepingTicker.SYSTEM_TICKER, permitsPerSecond, warmupPeriod, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static RateLimiter create(SleepingTicker ticker, double permitsPerSecond, long warmupPeriod, TimeUnit unit) {
/* 281 */     RateLimiter rateLimiter = new WarmingUp(ticker, warmupPeriod, unit);
/* 282 */     rateLimiter.setRate(permitsPerSecond);
/* 283 */     return rateLimiter;
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static RateLimiter createWithCapacity(SleepingTicker ticker, double permitsPerSecond, long maxBurstBuildup, TimeUnit unit) {
/* 289 */     double maxBurstSeconds = unit.toNanos(maxBurstBuildup) / 1.0E9D;
/* 290 */     Bursty rateLimiter = new Bursty(ticker, maxBurstSeconds);
/* 291 */     rateLimiter.setRate(permitsPerSecond);
/* 292 */     return rateLimiter;
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
/* 323 */   private final Object mutex = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 329 */   private long nextFreeTicketMicros = 0L;
/*     */   
/*     */   private RateLimiter(SleepingTicker ticker) {
/* 332 */     this.ticker = ticker;
/* 333 */     this.offsetNanos = ticker.read();
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
/*     */ 
/*     */   
/*     */   public final void setRate(double permitsPerSecond) {
/* 355 */     Preconditions.checkArgument((permitsPerSecond > 0.0D && !Double.isNaN(permitsPerSecond)), "rate must be positive");
/*     */     
/* 357 */     synchronized (this.mutex) {
/* 358 */       resync(readSafeMicros());
/* 359 */       double stableIntervalMicros = TimeUnit.SECONDS.toMicros(1L) / permitsPerSecond;
/* 360 */       this.stableIntervalMicros = stableIntervalMicros;
/* 361 */       doSetRate(permitsPerSecond, stableIntervalMicros);
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
/*     */   public final double getRate() {
/* 375 */     return TimeUnit.SECONDS.toMicros(1L) / this.stableIntervalMicros;
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
/*     */   public double acquire() {
/* 388 */     return acquire(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double acquire(int permits) {
/*     */     long microsToWait;
/* 400 */     checkPermits(permits);
/*     */     
/* 402 */     synchronized (this.mutex) {
/* 403 */       microsToWait = reserveNextTicket(permits, readSafeMicros());
/*     */     } 
/* 405 */     this.ticker.sleepMicrosUninterruptibly(microsToWait);
/* 406 */     return 1.0D * microsToWait / TimeUnit.SECONDS.toMicros(1L);
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
/*     */   public boolean tryAcquire(long timeout, TimeUnit unit) {
/* 422 */     return tryAcquire(1, timeout, unit);
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
/*     */   public boolean tryAcquire(int permits) {
/* 436 */     return tryAcquire(permits, 0L, TimeUnit.MICROSECONDS);
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
/*     */   public boolean tryAcquire() {
/* 450 */     return tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
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
/*     */   public boolean tryAcquire(int permits, long timeout, TimeUnit unit) {
/* 465 */     long microsToWait, timeoutMicros = unit.toMicros(timeout);
/* 466 */     checkPermits(permits);
/*     */     
/* 468 */     synchronized (this.mutex) {
/* 469 */       long nowMicros = readSafeMicros();
/* 470 */       if (this.nextFreeTicketMicros > nowMicros + timeoutMicros) {
/* 471 */         return false;
/*     */       }
/* 473 */       microsToWait = reserveNextTicket(permits, nowMicros);
/*     */     } 
/*     */     
/* 476 */     this.ticker.sleepMicrosUninterruptibly(microsToWait);
/* 477 */     return true;
/*     */   }
/*     */   
/*     */   private static void checkPermits(int permits) {
/* 481 */     Preconditions.checkArgument((permits > 0), "Requested permits must be positive");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long reserveNextTicket(double requiredPermits, long nowMicros) {
/* 488 */     resync(nowMicros);
/* 489 */     long microsToNextFreeTicket = this.nextFreeTicketMicros - nowMicros;
/* 490 */     double storedPermitsToSpend = Math.min(requiredPermits, this.storedPermits);
/* 491 */     double freshPermits = requiredPermits - storedPermitsToSpend;
/*     */     
/* 493 */     long waitMicros = storedPermitsToWaitTime(this.storedPermits, storedPermitsToSpend) + (long)(freshPermits * this.stableIntervalMicros);
/*     */ 
/*     */     
/* 496 */     this.nextFreeTicketMicros += waitMicros;
/* 497 */     this.storedPermits -= storedPermitsToSpend;
/* 498 */     return microsToNextFreeTicket;
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
/*     */   private void resync(long nowMicros) {
/* 513 */     if (nowMicros > this.nextFreeTicketMicros) {
/* 514 */       this.storedPermits = Math.min(this.maxPermits, this.storedPermits + (nowMicros - this.nextFreeTicketMicros) / this.stableIntervalMicros);
/*     */       
/* 516 */       this.nextFreeTicketMicros = nowMicros;
/*     */     } 
/*     */   }
/*     */   
/*     */   private long readSafeMicros() {
/* 521 */     return TimeUnit.NANOSECONDS.toMicros(this.ticker.read() - this.offsetNanos);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 526 */     return String.format("RateLimiter[stableRate=%3.1fqps]", new Object[] { Double.valueOf(1000000.0D / this.stableIntervalMicros) });
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
/*     */   abstract void doSetRate(double paramDouble1, double paramDouble2);
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
/*     */   abstract long storedPermitsToWaitTime(double paramDouble1, double paramDouble2);
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
/*     */   private static class WarmingUp
/*     */     extends RateLimiter
/*     */   {
/*     */     final long warmupPeriodMicros;
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
/*     */     private double slope;
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
/*     */     private double halfPermits;
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
/*     */     WarmingUp(RateLimiter.SleepingTicker ticker, long warmupPeriod, TimeUnit timeUnit) {
/* 615 */       super(ticker);
/* 616 */       this.warmupPeriodMicros = timeUnit.toMicros(warmupPeriod);
/*     */     }
/*     */ 
/*     */     
/*     */     void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
/* 621 */       double oldMaxPermits = this.maxPermits;
/* 622 */       this.maxPermits = this.warmupPeriodMicros / stableIntervalMicros;
/* 623 */       this.halfPermits = this.maxPermits / 2.0D;
/*     */       
/* 625 */       double coldIntervalMicros = stableIntervalMicros * 3.0D;
/* 626 */       this.slope = (coldIntervalMicros - stableIntervalMicros) / this.halfPermits;
/* 627 */       if (oldMaxPermits == Double.POSITIVE_INFINITY) {
/*     */         
/* 629 */         this.storedPermits = 0.0D;
/*     */       } else {
/* 631 */         this.storedPermits = (oldMaxPermits == 0.0D) ? this.maxPermits : (this.storedPermits * this.maxPermits / oldMaxPermits);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
/* 639 */       double availablePermitsAboveHalf = storedPermits - this.halfPermits;
/* 640 */       long micros = 0L;
/*     */       
/* 642 */       if (availablePermitsAboveHalf > 0.0D) {
/* 643 */         double permitsAboveHalfToTake = Math.min(availablePermitsAboveHalf, permitsToTake);
/* 644 */         micros = (long)(permitsAboveHalfToTake * (permitsToTime(availablePermitsAboveHalf) + permitsToTime(availablePermitsAboveHalf - permitsAboveHalfToTake)) / 2.0D);
/*     */         
/* 646 */         permitsToTake -= permitsAboveHalfToTake;
/*     */       } 
/*     */       
/* 649 */       micros = (long)(micros + this.stableIntervalMicros * permitsToTake);
/* 650 */       return micros;
/*     */     }
/*     */     
/*     */     private double permitsToTime(double permits) {
/* 654 */       return this.stableIntervalMicros + permits * this.slope;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Bursty
/*     */     extends RateLimiter
/*     */   {
/*     */     final double maxBurstSeconds;
/*     */ 
/*     */ 
/*     */     
/*     */     Bursty(RateLimiter.SleepingTicker ticker, double maxBurstSeconds) {
/* 669 */       super(ticker);
/* 670 */       this.maxBurstSeconds = maxBurstSeconds;
/*     */     }
/*     */ 
/*     */     
/*     */     void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
/* 675 */       double oldMaxPermits = this.maxPermits;
/* 676 */       this.maxPermits = this.maxBurstSeconds * permitsPerSecond;
/* 677 */       this.storedPermits = (oldMaxPermits == 0.0D) ? 0.0D : (this.storedPermits * this.maxPermits / oldMaxPermits);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
/* 684 */       return 0L;
/*     */     }
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static abstract class SleepingTicker
/*     */     extends Ticker
/*     */   {
/* 692 */     static final SleepingTicker SYSTEM_TICKER = new SleepingTicker()
/*     */       {
/*     */         public long read() {
/* 695 */           return systemTicker().read();
/*     */         }
/*     */ 
/*     */         
/*     */         public void sleepMicrosUninterruptibly(long micros) {
/* 700 */           if (micros > 0L)
/* 701 */             Uninterruptibles.sleepUninterruptibly(micros, TimeUnit.MICROSECONDS); 
/*     */         }
/*     */       };
/*     */     
/*     */     abstract void sleepMicrosUninterruptibly(long param1Long);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\RateLimiter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */