/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Stopwatch
/*     */ {
/*     */   private final Ticker ticker;
/*     */   private boolean isRunning;
/*     */   private long elapsedNanos;
/*     */   private long startTick;
/*     */   
/*     */   public Stopwatch() {
/*  84 */     this(Ticker.systemTicker());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stopwatch(Ticker ticker) {
/*  92 */     this.ticker = Preconditions.<Ticker>checkNotNull(ticker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRunning() {
/* 101 */     return this.isRunning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stopwatch start() {
/* 110 */     Preconditions.checkState(!this.isRunning);
/* 111 */     this.isRunning = true;
/* 112 */     this.startTick = this.ticker.read();
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stopwatch stop() {
/* 123 */     long tick = this.ticker.read();
/* 124 */     Preconditions.checkState(this.isRunning);
/* 125 */     this.isRunning = false;
/* 126 */     this.elapsedNanos += tick - this.startTick;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stopwatch reset() {
/* 135 */     this.elapsedNanos = 0L;
/* 136 */     this.isRunning = false;
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   private long elapsedNanos() {
/* 141 */     return this.isRunning ? (this.ticker.read() - this.startTick + this.elapsedNanos) : this.elapsedNanos;
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
/*     */   public long elapsedTime(TimeUnit desiredUnit) {
/* 153 */     return desiredUnit.convert(elapsedNanos(), TimeUnit.NANOSECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long elapsedMillis() {
/* 162 */     return elapsedTime(TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("String.format()")
/*     */   public String toString() {
/* 171 */     return toString(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("String.format()")
/*     */   public String toString(int significantDigits) {
/* 182 */     long nanos = elapsedNanos();
/*     */     
/* 184 */     TimeUnit unit = chooseUnit(nanos);
/* 185 */     double value = nanos / TimeUnit.NANOSECONDS.convert(1L, unit);
/*     */ 
/*     */     
/* 188 */     return String.format("%." + significantDigits + "g %s", new Object[] { Double.valueOf(value), abbreviate(unit) });
/*     */   }
/*     */ 
/*     */   
/*     */   private static TimeUnit chooseUnit(long nanos) {
/* 193 */     if (TimeUnit.SECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 194 */       return TimeUnit.SECONDS;
/*     */     }
/* 196 */     if (TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 197 */       return TimeUnit.MILLISECONDS;
/*     */     }
/* 199 */     if (TimeUnit.MICROSECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 200 */       return TimeUnit.MICROSECONDS;
/*     */     }
/* 202 */     return TimeUnit.NANOSECONDS;
/*     */   }
/*     */   
/*     */   private static String abbreviate(TimeUnit unit) {
/* 206 */     switch (unit) {
/*     */       case NANOSECONDS:
/* 208 */         return "ns";
/*     */       case MICROSECONDS:
/* 210 */         return "μs";
/*     */       case MILLISECONDS:
/* 212 */         return "ms";
/*     */       case SECONDS:
/* 214 */         return "s";
/*     */     } 
/* 216 */     throw new AssertionError();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Stopwatch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */