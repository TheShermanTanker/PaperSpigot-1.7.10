/*     */ package net.minecraft.util.io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultThreadFactory
/*     */   implements ThreadFactory
/*     */ {
/*  28 */   private static final AtomicInteger poolId = new AtomicInteger();
/*     */   
/*  30 */   private final AtomicInteger nextId = new AtomicInteger();
/*     */   private final String prefix;
/*     */   private final boolean daemon;
/*     */   private final int priority;
/*     */   
/*     */   public DefaultThreadFactory(Class<?> poolType) {
/*  36 */     this(poolType, false, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(String poolName) {
/*  40 */     this(poolName, false, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(Class<?> poolType, boolean daemon) {
/*  44 */     this(poolType, daemon, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(String poolName, boolean daemon) {
/*  48 */     this(poolName, daemon, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(Class<?> poolType, int priority) {
/*  52 */     this(poolType, false, priority);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(String poolName, int priority) {
/*  56 */     this(poolName, false, priority);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(Class<?> poolType, boolean daemon, int priority) {
/*  60 */     this(toPoolName(poolType), daemon, priority);
/*     */   }
/*     */   private static String toPoolName(Class<?> poolType) {
/*     */     String poolName;
/*  64 */     if (poolType == null) {
/*  65 */       throw new NullPointerException("poolType");
/*     */     }
/*     */     
/*  68 */     Package pkg = poolType.getPackage();
/*  69 */     if (pkg != null) {
/*  70 */       poolName = poolType.getName().substring(pkg.getName().length() + 1);
/*     */     } else {
/*  72 */       poolName = poolType.getName();
/*     */     } 
/*     */     
/*  75 */     switch (poolName.length()) {
/*     */       case 0:
/*  77 */         return "unknown";
/*     */       case 1:
/*  79 */         return poolName.toLowerCase(Locale.US);
/*     */     } 
/*  81 */     if (Character.isUpperCase(poolName.charAt(0)) && Character.isLowerCase(poolName.charAt(1))) {
/*  82 */       return Character.toLowerCase(poolName.charAt(0)) + poolName.substring(1);
/*     */     }
/*  84 */     return poolName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultThreadFactory(String poolName, boolean daemon, int priority) {
/*  90 */     if (poolName == null) {
/*  91 */       throw new NullPointerException("poolName");
/*     */     }
/*  93 */     if (priority < 1 || priority > 10) {
/*  94 */       throw new IllegalArgumentException("priority: " + priority + " (expected: Thread.MIN_PRIORITY <= priority <= Thread.MAX_PRIORITY)");
/*     */     }
/*     */ 
/*     */     
/*  98 */     this.prefix = poolName + '-' + poolId.incrementAndGet() + '-';
/*  99 */     this.daemon = daemon;
/* 100 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   
/*     */   public Thread newThread(Runnable r) {
/* 105 */     Thread t = new Thread(r, this.prefix + this.nextId.incrementAndGet());
/*     */     try {
/* 107 */       if (t.isDaemon()) {
/* 108 */         if (!this.daemon) {
/* 109 */           t.setDaemon(false);
/*     */         }
/*     */       }
/* 112 */       else if (this.daemon) {
/* 113 */         t.setDaemon(true);
/*     */       } 
/*     */ 
/*     */       
/* 117 */       if (t.getPriority() != this.priority) {
/* 118 */         t.setPriority(this.priority);
/*     */       }
/* 120 */     } catch (Exception ignored) {}
/*     */ 
/*     */     
/* 123 */     return t;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\concurrent\DefaultThreadFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */