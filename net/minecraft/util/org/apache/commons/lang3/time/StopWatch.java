/*     */ package net.minecraft.util.org.apache.commons.lang3.time;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StopWatch
/*     */ {
/*     */   private static final long NANO_2_MILLIS = 1000000L;
/*     */   
/*     */   private enum State
/*     */   {
/*  67 */     UNSTARTED {
/*  68 */       boolean isStarted() { return false; }
/*  69 */       boolean isStopped() { return true; } boolean isSuspended() {
/*  70 */         return false;
/*     */       } },
/*  72 */     RUNNING {
/*  73 */       boolean isStarted() { return true; }
/*  74 */       boolean isStopped() { return false; } boolean isSuspended() {
/*  75 */         return false;
/*     */       } },
/*  77 */     STOPPED {
/*  78 */       boolean isStarted() { return false; }
/*  79 */       boolean isStopped() { return true; } boolean isSuspended() {
/*  80 */         return false;
/*     */       } },
/*  82 */     SUSPENDED {
/*  83 */       boolean isStarted() { return true; }
/*  84 */       boolean isStopped() { return false; } boolean isSuspended() {
/*  85 */         return true;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract boolean isStarted();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract boolean isStopped();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract boolean isSuspended();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private enum SplitState
/*     */   {
/* 129 */     SPLIT,
/* 130 */     UNSPLIT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 135 */   private State runningState = State.UNSTARTED;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   private SplitState splitState = SplitState.UNSPLIT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long startTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long startTimeMillis;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long stopTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 181 */     if (this.runningState == State.STOPPED) {
/* 182 */       throw new IllegalStateException("Stopwatch must be reset before being restarted. ");
/*     */     }
/* 184 */     if (this.runningState != State.UNSTARTED) {
/* 185 */       throw new IllegalStateException("Stopwatch already started. ");
/*     */     }
/* 187 */     this.startTime = System.nanoTime();
/* 188 */     this.startTimeMillis = System.currentTimeMillis();
/* 189 */     this.runningState = State.RUNNING;
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
/*     */   public void stop() {
/* 206 */     if (this.runningState != State.RUNNING && this.runningState != State.SUSPENDED) {
/* 207 */       throw new IllegalStateException("Stopwatch is not running. ");
/*     */     }
/* 209 */     if (this.runningState == State.RUNNING) {
/* 210 */       this.stopTime = System.nanoTime();
/*     */     }
/* 212 */     this.runningState = State.STOPPED;
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
/*     */   public void reset() {
/* 225 */     this.runningState = State.UNSTARTED;
/* 226 */     this.splitState = SplitState.UNSPLIT;
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
/*     */   public void split() {
/* 243 */     if (this.runningState != State.RUNNING) {
/* 244 */       throw new IllegalStateException("Stopwatch is not running. ");
/*     */     }
/* 246 */     this.stopTime = System.nanoTime();
/* 247 */     this.splitState = SplitState.SPLIT;
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
/*     */   public void unsplit() {
/* 264 */     if (this.splitState != SplitState.SPLIT) {
/* 265 */       throw new IllegalStateException("Stopwatch has not been split. ");
/*     */     }
/* 267 */     this.splitState = SplitState.UNSPLIT;
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
/*     */   public void suspend() {
/* 284 */     if (this.runningState != State.RUNNING) {
/* 285 */       throw new IllegalStateException("Stopwatch must be running to suspend. ");
/*     */     }
/* 287 */     this.stopTime = System.nanoTime();
/* 288 */     this.runningState = State.SUSPENDED;
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
/*     */   public void resume() {
/* 305 */     if (this.runningState != State.SUSPENDED) {
/* 306 */       throw new IllegalStateException("Stopwatch must be suspended to resume. ");
/*     */     }
/* 308 */     this.startTime += System.nanoTime() - this.stopTime;
/* 309 */     this.runningState = State.RUNNING;
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
/*     */   public long getTime() {
/* 325 */     return getNanoTime() / 1000000L;
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
/*     */   public long getNanoTime() {
/* 341 */     if (this.runningState == State.STOPPED || this.runningState == State.SUSPENDED)
/* 342 */       return this.stopTime - this.startTime; 
/* 343 */     if (this.runningState == State.UNSTARTED)
/* 344 */       return 0L; 
/* 345 */     if (this.runningState == State.RUNNING) {
/* 346 */       return System.nanoTime() - this.startTime;
/*     */     }
/* 348 */     throw new RuntimeException("Illegal running state has occurred.");
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
/*     */   public long getSplitTime() {
/* 367 */     return getSplitNanoTime() / 1000000L;
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
/*     */   public long getSplitNanoTime() {
/* 385 */     if (this.splitState != SplitState.SPLIT) {
/* 386 */       throw new IllegalStateException("Stopwatch must be split to get the split time. ");
/*     */     }
/* 388 */     return this.stopTime - this.startTime;
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
/*     */   public long getStartTime() {
/* 400 */     if (this.runningState == State.UNSTARTED) {
/* 401 */       throw new IllegalStateException("Stopwatch has not been started");
/*     */     }
/*     */     
/* 404 */     return this.startTimeMillis;
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
/*     */   public String toString() {
/* 420 */     return DurationFormatUtils.formatDurationHMS(getTime());
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
/*     */   public String toSplitString() {
/* 436 */     return DurationFormatUtils.formatDurationHMS(getSplitTime());
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
/*     */   public boolean isStarted() {
/* 450 */     return this.runningState.isStarted();
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
/*     */   public boolean isSuspended() {
/* 463 */     return this.runningState.isSuspended();
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
/*     */   public boolean isStopped() {
/* 478 */     return this.runningState.isStopped();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\time\StopWatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */