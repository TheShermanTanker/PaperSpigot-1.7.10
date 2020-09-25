/*     */ package net.minecraft.util.com.google.common.base;
/*     */ 
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static Stopwatch createUnstarted() {
/*  89 */     return new Stopwatch();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Stopwatch createUnstarted(Ticker ticker) {
/*  99 */     return new Stopwatch(ticker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Stopwatch createStarted() {
/* 109 */     return (new Stopwatch()).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Stopwatch createStarted(Ticker ticker) {
/* 119 */     return (new Stopwatch(ticker)).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Stopwatch() {
/* 131 */     this(Ticker.systemTicker());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Stopwatch(Ticker ticker) {
/* 143 */     this.ticker = Preconditions.<Ticker>checkNotNull(ticker, "ticker");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRunning() {
/* 152 */     return this.isRunning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stopwatch start() {
/* 162 */     Preconditions.checkState(!this.isRunning, "This stopwatch is already running.");
/* 163 */     this.isRunning = true;
/* 164 */     this.startTick = this.ticker.read();
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stopwatch stop() {
/* 176 */     long tick = this.ticker.read();
/* 177 */     Preconditions.checkState(this.isRunning, "This stopwatch is already stopped.");
/* 178 */     this.isRunning = false;
/* 179 */     this.elapsedNanos += tick - this.startTick;
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stopwatch reset() {
/* 190 */     this.elapsedNanos = 0L;
/* 191 */     this.isRunning = false;
/* 192 */     return this;
/*     */   }
/*     */   
/*     */   private long elapsedNanos() {
/* 196 */     return this.isRunning ? (this.ticker.read() - this.startTick + this.elapsedNanos) : this.elapsedNanos;
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
/*     */   public long elapsed(TimeUnit desiredUnit) {
/* 210 */     return desiredUnit.convert(elapsedNanos(), TimeUnit.NANOSECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GwtIncompatible("String.format()")
/*     */   public String toString() {
/* 218 */     long nanos = elapsedNanos();
/*     */     
/* 220 */     TimeUnit unit = chooseUnit(nanos);
/* 221 */     double value = nanos / TimeUnit.NANOSECONDS.convert(1L, unit);
/*     */ 
/*     */     
/* 224 */     return String.format("%.4g %s", new Object[] { Double.valueOf(value), abbreviate(unit) });
/*     */   }
/*     */   
/*     */   private static TimeUnit chooseUnit(long nanos) {
/* 228 */     if (TimeUnit.DAYS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 229 */       return TimeUnit.DAYS;
/*     */     }
/* 231 */     if (TimeUnit.HOURS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 232 */       return TimeUnit.HOURS;
/*     */     }
/* 234 */     if (TimeUnit.MINUTES.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 235 */       return TimeUnit.MINUTES;
/*     */     }
/* 237 */     if (TimeUnit.SECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 238 */       return TimeUnit.SECONDS;
/*     */     }
/* 240 */     if (TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 241 */       return TimeUnit.MILLISECONDS;
/*     */     }
/* 243 */     if (TimeUnit.MICROSECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 244 */       return TimeUnit.MICROSECONDS;
/*     */     }
/* 246 */     return TimeUnit.NANOSECONDS;
/*     */   }
/*     */   
/*     */   private static String abbreviate(TimeUnit unit) {
/* 250 */     switch (unit) {
/*     */       case NANOSECONDS:
/* 252 */         return "ns";
/*     */       case MICROSECONDS:
/* 254 */         return "μs";
/*     */       case MILLISECONDS:
/* 256 */         return "ms";
/*     */       case SECONDS:
/* 258 */         return "s";
/*     */       case MINUTES:
/* 260 */         return "min";
/*     */       case HOURS:
/* 262 */         return "h";
/*     */       case DAYS:
/* 264 */         return "d";
/*     */     } 
/* 266 */     throw new AssertionError();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\base\Stopwatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */