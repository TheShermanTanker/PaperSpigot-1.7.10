/*     */ package net.minecraft.util.io.netty.util;
/*     */ 
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*     */ import net.minecraft.util.io.netty.util.internal.SystemPropertyUtil;
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
/*     */ public final class ResourceLeakDetector<T>
/*     */ {
/*     */   private static boolean disabled;
/*  33 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
/*     */   
/*     */   static {
/*  36 */     boolean DISABLED = SystemPropertyUtil.getBoolean("io.netty.noResourceLeakDetection", false);
/*  37 */     logger.debug("-Dio.netty.noResourceLeakDetection: {}", Boolean.valueOf(DISABLED));
/*  38 */     disabled = DISABLED;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_SAMPLING_INTERVAL = 113;
/*     */ 
/*     */   
/*     */   public static void setEnabled(boolean enabled) {
/*  47 */     disabled = !enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEnabled() {
/*  54 */     return !disabled;
/*     */   }
/*     */ 
/*     */   
/*  58 */   private final DefaultResourceLeak head = new DefaultResourceLeak(null);
/*  59 */   private final DefaultResourceLeak tail = new DefaultResourceLeak(null);
/*     */   
/*  61 */   private final ReferenceQueue<Object> refQueue = new ReferenceQueue();
/*  62 */   private final ConcurrentMap<Exception, Boolean> reportedLeaks = PlatformDependent.newConcurrentHashMap();
/*     */   
/*     */   private final String resourceType;
/*     */   private final int samplingInterval;
/*     */   private final long maxActive;
/*     */   private long active;
/*  68 */   private final AtomicBoolean loggedTooManyActive = new AtomicBoolean();
/*     */   
/*     */   private long leakCheckCnt;
/*     */   
/*     */   public ResourceLeakDetector(Class<?> resourceType) {
/*  73 */     this(resourceType.getSimpleName());
/*     */   }
/*     */   
/*     */   public ResourceLeakDetector(String resourceType) {
/*  77 */     this(resourceType, 113, Long.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public ResourceLeakDetector(Class<?> resourceType, int samplingInterval, long maxActive) {
/*  81 */     this(resourceType.getSimpleName(), samplingInterval, maxActive);
/*     */   }
/*     */   
/*     */   public ResourceLeakDetector(String resourceType, int samplingInterval, long maxActive) {
/*  85 */     if (resourceType == null) {
/*  86 */       throw new NullPointerException("resourceType");
/*     */     }
/*  88 */     if (samplingInterval <= 0) {
/*  89 */       throw new IllegalArgumentException("samplingInterval: " + samplingInterval + " (expected: 1+)");
/*     */     }
/*  91 */     if (maxActive <= 0L) {
/*  92 */       throw new IllegalArgumentException("maxActive: " + maxActive + " (expected: 1+)");
/*     */     }
/*     */     
/*  95 */     this.resourceType = resourceType;
/*  96 */     this.samplingInterval = samplingInterval;
/*  97 */     this.maxActive = maxActive;
/*     */     
/*  99 */     this.head.next = this.tail;
/* 100 */     this.tail.prev = this.head;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLeak open(T obj) {
/* 110 */     if (disabled || this.leakCheckCnt++ % this.samplingInterval != 0L) {
/* 111 */       return null;
/*     */     }
/*     */     
/* 114 */     reportLeak();
/*     */     
/* 116 */     return new DefaultResourceLeak(obj);
/*     */   }
/*     */   
/*     */   private void reportLeak() {
/* 120 */     if (!logger.isWarnEnabled()) {
/*     */       
/*     */       while (true) {
/* 123 */         DefaultResourceLeak ref = (DefaultResourceLeak)this.refQueue.poll();
/* 124 */         if (ref == null) {
/*     */           break;
/*     */         }
/* 127 */         ref.close();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 133 */     if (this.active * this.samplingInterval > this.maxActive && this.loggedTooManyActive.compareAndSet(false, true)) {
/* 134 */       logger.warn("LEAK: You are creating too many " + this.resourceType + " instances.  " + this.resourceType + " is a shared resource that must be reused across the JVM," + "so that only a few instances are created.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 143 */       DefaultResourceLeak ref = (DefaultResourceLeak)this.refQueue.poll();
/* 144 */       if (ref == null) {
/*     */         break;
/*     */       }
/*     */       
/* 148 */       ref.clear();
/*     */       
/* 150 */       if (!ref.close()) {
/*     */         continue;
/*     */       }
/*     */       
/* 154 */       if (this.reportedLeaks.putIfAbsent(ref.exception, Boolean.TRUE) == null) {
/* 155 */         logger.warn("LEAK: " + this.resourceType + " was GC'd before being released correctly.  " + "The following stack trace shows where the leaked object was created, " + "rather than where you failed to release it.", ref.exception);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final class DefaultResourceLeak
/*     */     extends PhantomReference<Object>
/*     */     implements ResourceLeak
/*     */   {
/*     */     private final ResourceLeakException exception;
/*     */     private final AtomicBoolean freed;
/*     */     private DefaultResourceLeak prev;
/*     */     private DefaultResourceLeak next;
/*     */     
/*     */     public DefaultResourceLeak(Object referent) {
/* 171 */       super(referent, (referent != null) ? ResourceLeakDetector.this.refQueue : null);
/*     */       
/* 173 */       if (referent != null) {
/* 174 */         this.exception = new ResourceLeakException(referent.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(referent)));
/*     */ 
/*     */ 
/*     */         
/* 178 */         synchronized (ResourceLeakDetector.this.head) {
/* 179 */           this.prev = ResourceLeakDetector.this.head;
/* 180 */           this.next = ResourceLeakDetector.this.head.next;
/* 181 */           ResourceLeakDetector.this.head.next.prev = this;
/* 182 */           ResourceLeakDetector.this.head.next = this;
/* 183 */           ResourceLeakDetector.this.active++;
/*     */         } 
/* 185 */         this.freed = new AtomicBoolean();
/*     */       } else {
/* 187 */         this.exception = null;
/* 188 */         this.freed = new AtomicBoolean(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean close() {
/* 194 */       if (this.freed.compareAndSet(false, true)) {
/* 195 */         synchronized (ResourceLeakDetector.this.head) {
/* 196 */           ResourceLeakDetector.this.active--;
/* 197 */           this.prev.next = this.next;
/* 198 */           this.next.prev = this.prev;
/* 199 */           this.prev = null;
/* 200 */           this.next = null;
/*     */         } 
/* 202 */         return true;
/*     */       } 
/* 204 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\ResourceLeakDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */