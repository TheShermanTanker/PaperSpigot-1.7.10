/*     */ package net.minecraft.util.io.netty.util.internal;
/*     */ 
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadLocalRandom
/*     */   extends Random
/*     */ {
/*  62 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadLocalRandom.class);
/*     */   
/*  64 */   private static final AtomicLong seedUniquifier = new AtomicLong(); private static volatile long initialSeedUniquifier; private static final long multiplier = 25214903917L; private static final long addend = 11L; private static final long mask = 281474976710655L; private long rnd;
/*     */   boolean initialized;
/*     */   private long pad0;
/*     */   
/*     */   public static void setInitialSeedUniquifier(long initialSeedUniquifier) {
/*  69 */     ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier;
/*     */   }
/*     */   private long pad1; private long pad2; private long pad3; private long pad4; private long pad5; private long pad6; private long pad7;
/*     */   
/*     */   public static synchronized long getInitialSeedUniquifier() {
/*  74 */     long initialSeedUniquifier = ThreadLocalRandom.initialSeedUniquifier;
/*  75 */     if (initialSeedUniquifier == 0L)
/*     */     {
/*  77 */       ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0L);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  82 */     if (initialSeedUniquifier == 0L) {
/*     */ 
/*     */       
/*  85 */       final BlockingQueue<Long> queue = new LinkedBlockingQueue<Long>();
/*  86 */       Thread generatorThread = new Thread("initialSeedUniquifierGenerator")
/*     */         {
/*     */           public void run() {
/*  89 */             SecureRandom random = new SecureRandom();
/*  90 */             queue.add(Long.valueOf(random.nextLong()));
/*     */           }
/*     */         };
/*  93 */       generatorThread.start();
/*     */ 
/*     */       
/*  96 */       long timeoutSeconds = 3L;
/*  97 */       long deadLine = System.nanoTime() + TimeUnit.SECONDS.toNanos(3L);
/*     */       while (true) {
/*  99 */         long waitTime = deadLine - System.nanoTime();
/* 100 */         if (waitTime <= 0L) {
/* 101 */           logger.warn("Failed to get the secure random number from SecureRandom within {} seconds. Not enough entrophy?", Long.valueOf(3L));
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/*     */         try {
/* 108 */           Long result = queue.poll(waitTime, TimeUnit.NANOSECONDS);
/* 109 */           if (result != null) {
/* 110 */             initialSeedUniquifier = result.longValue();
/*     */             break;
/*     */           } 
/* 113 */         } catch (InterruptedException ignore) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 119 */       initialSeedUniquifier ^= 0x3255ECDC33BAE119L;
/* 120 */       initialSeedUniquifier ^= Long.reverse(System.nanoTime());
/*     */       
/* 122 */       ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier;
/*     */     } 
/*     */     
/* 125 */     return initialSeedUniquifier;
/*     */   }
/*     */   
/*     */   private static long newSeed() {
/*     */     while (true) {
/* 130 */       long current = seedUniquifier.get();
/* 131 */       long actualCurrent = (current != 0L) ? current : getInitialSeedUniquifier();
/*     */ 
/*     */       
/* 134 */       long next = actualCurrent * 181783497276652981L;
/*     */       
/* 136 */       if (seedUniquifier.compareAndSet(current, next)) {
/* 137 */         if (current == 0L && logger.isDebugEnabled()) {
/* 138 */           logger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x", new Object[] { Long.valueOf(actualCurrent) }));
/*     */         }
/* 140 */         return next ^ System.nanoTime();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ThreadLocalRandom() {
/* 172 */     super(newSeed());
/* 173 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   private static final ThreadLocal<ThreadLocalRandom> localRandom = new ThreadLocal<ThreadLocalRandom>()
/*     */     {
/*     */       protected ThreadLocalRandom initialValue() {
/* 182 */         return new ThreadLocalRandom();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -5851777807851030925L;
/*     */ 
/*     */   
/*     */   public static ThreadLocalRandom current() {
/* 192 */     return localRandom.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {
/* 202 */     if (this.initialized) {
/* 203 */       throw new UnsupportedOperationException();
/*     */     }
/* 205 */     this.rnd = (seed ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL;
/*     */   }
/*     */   
/*     */   protected int next(int bits) {
/* 209 */     this.rnd = this.rnd * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
/* 210 */     return (int)(this.rnd >>> 48 - bits);
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
/*     */   public int nextInt(int least, int bound) {
/* 224 */     if (least >= bound) {
/* 225 */       throw new IllegalArgumentException();
/*     */     }
/* 227 */     return nextInt(bound - least) + least;
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
/*     */   public long nextLong(long n) {
/* 240 */     if (n <= 0L) {
/* 241 */       throw new IllegalArgumentException("n must be positive");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     long offset = 0L;
/* 250 */     while (n >= 2147483647L) {
/* 251 */       int bits = next(2);
/* 252 */       long half = n >>> 1L;
/* 253 */       long nextn = ((bits & 0x2) == 0) ? half : (n - half);
/* 254 */       if ((bits & 0x1) == 0) {
/* 255 */         offset += n - nextn;
/*     */       }
/* 257 */       n = nextn;
/*     */     } 
/* 259 */     return offset + nextInt((int)n);
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
/*     */   public long nextLong(long least, long bound) {
/* 273 */     if (least >= bound) {
/* 274 */       throw new IllegalArgumentException();
/*     */     }
/* 276 */     return nextLong(bound - least) + least;
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
/*     */   public double nextDouble(double n) {
/* 289 */     if (n <= 0.0D) {
/* 290 */       throw new IllegalArgumentException("n must be positive");
/*     */     }
/* 292 */     return nextDouble() * n;
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
/*     */   public double nextDouble(double least, double bound) {
/* 306 */     if (least >= bound) {
/* 307 */       throw new IllegalArgumentException();
/*     */     }
/* 309 */     return nextDouble() * (bound - least) + least;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\ThreadLocalRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */