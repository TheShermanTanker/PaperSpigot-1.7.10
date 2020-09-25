/*     */ package net.minecraft.util.io.netty.util.internal;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.ServerSocket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.util.io.netty.util.CharsetUtil;
/*     */ import net.minecraft.util.io.netty.util.internal.chmv8.ConcurrentHashMapV8;
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
/*     */ public final class PlatformDependent
/*     */ {
/*  51 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PlatformDependent.class);
/*     */   
/*  53 */   private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
/*     */ 
/*     */   
/*  56 */   private static final boolean IS_ANDROID = isAndroid0();
/*  57 */   private static final boolean IS_WINDOWS = isWindows0();
/*  58 */   private static final boolean IS_ROOT = isRoot0();
/*     */   
/*  60 */   private static final int JAVA_VERSION = javaVersion0();
/*     */   
/*  62 */   private static final boolean CAN_ENABLE_TCP_NODELAY_BY_DEFAULT = !isAndroid();
/*     */   
/*  64 */   private static final boolean HAS_UNSAFE = hasUnsafe0();
/*  65 */   private static final boolean CAN_USE_CHM_V8 = (HAS_UNSAFE && JAVA_VERSION < 8);
/*  66 */   private static final boolean DIRECT_BUFFER_PREFERRED = (HAS_UNSAFE && !SystemPropertyUtil.getBoolean("io.netty.noPreferDirect", false));
/*     */   
/*  68 */   private static final long MAX_DIRECT_MEMORY = maxDirectMemory0();
/*     */   
/*  70 */   private static final long ARRAY_BASE_OFFSET = arrayBaseOffset0();
/*     */   
/*  72 */   private static final boolean HAS_JAVASSIST = hasJavassist0();
/*     */   
/*     */   static {
/*  75 */     if (logger.isDebugEnabled()) {
/*  76 */       logger.debug("-Dio.netty.noPreferDirect: {}", Boolean.valueOf(!DIRECT_BUFFER_PREFERRED));
/*     */     }
/*     */     
/*  79 */     if (!hasUnsafe()) {
/*  80 */       logger.info("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential system unstability.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAndroid() {
/*  91 */     return IS_ANDROID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWindows() {
/*  98 */     return IS_WINDOWS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRoot() {
/* 106 */     return IS_ROOT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int javaVersion() {
/* 113 */     return JAVA_VERSION;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canEnableTcpNoDelayByDefault() {
/* 120 */     return CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasUnsafe() {
/* 128 */     return HAS_UNSAFE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean directBufferPreferred() {
/* 136 */     return DIRECT_BUFFER_PREFERRED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long maxDirectMemory() {
/* 143 */     return MAX_DIRECT_MEMORY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasJavassist() {
/* 150 */     return HAS_JAVASSIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void throwException(Throwable t) {
/* 157 */     if (hasUnsafe()) {
/* 158 */       PlatformDependent0.throwException(t);
/*     */     } else {
/* 160 */       throwException0(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static <E extends Throwable> void throwException0(Throwable t) throws E {
/* 166 */     throw (E)t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
/* 173 */     if (CAN_USE_CHM_V8) {
/* 174 */       return (ConcurrentMap<K, V>)new ConcurrentHashMapV8();
/*     */     }
/* 176 */     return new ConcurrentHashMap<K, V>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity) {
/* 184 */     if (CAN_USE_CHM_V8) {
/* 185 */       return (ConcurrentMap<K, V>)new ConcurrentHashMapV8(initialCapacity);
/*     */     }
/* 187 */     return new ConcurrentHashMap<K, V>(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity, float loadFactor) {
/* 195 */     if (CAN_USE_CHM_V8) {
/* 196 */       return (ConcurrentMap<K, V>)new ConcurrentHashMapV8(initialCapacity, loadFactor);
/*     */     }
/* 198 */     return new ConcurrentHashMap<K, V>(initialCapacity, loadFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
/* 207 */     if (CAN_USE_CHM_V8) {
/* 208 */       return (ConcurrentMap<K, V>)new ConcurrentHashMapV8(initialCapacity, loadFactor, concurrencyLevel);
/*     */     }
/* 210 */     return new ConcurrentHashMap<K, V>(initialCapacity, loadFactor, concurrencyLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> map) {
/* 218 */     if (CAN_USE_CHM_V8) {
/* 219 */       return (ConcurrentMap<K, V>)new ConcurrentHashMapV8(map);
/*     */     }
/* 221 */     return new ConcurrentHashMap<K, V>(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void freeDirectBuffer(ByteBuffer buffer) {
/* 230 */     if (buffer.isDirect()) {
/* 231 */       if (hasUnsafe()) {
/* 232 */         PlatformDependent0.freeDirectBufferUnsafe(buffer);
/*     */       } else {
/* 234 */         PlatformDependent0.freeDirectBuffer(buffer);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static long directBufferAddress(ByteBuffer buffer) {
/* 240 */     return PlatformDependent0.directBufferAddress(buffer);
/*     */   }
/*     */   
/*     */   public static Object getObject(Object object, long fieldOffset) {
/* 244 */     return PlatformDependent0.getObject(object, fieldOffset);
/*     */   }
/*     */   
/*     */   public static int getInt(Object object, long fieldOffset) {
/* 248 */     return PlatformDependent0.getInt(object, fieldOffset);
/*     */   }
/*     */   
/*     */   public static long objectFieldOffset(Field field) {
/* 252 */     return PlatformDependent0.objectFieldOffset(field);
/*     */   }
/*     */   
/*     */   public static byte getByte(long address) {
/* 256 */     return PlatformDependent0.getByte(address);
/*     */   }
/*     */   
/*     */   public static short getShort(long address) {
/* 260 */     return PlatformDependent0.getShort(address);
/*     */   }
/*     */   
/*     */   public static int getInt(long address) {
/* 264 */     return PlatformDependent0.getInt(address);
/*     */   }
/*     */   
/*     */   public static long getLong(long address) {
/* 268 */     return PlatformDependent0.getLong(address);
/*     */   }
/*     */   
/*     */   public static void putByte(long address, byte value) {
/* 272 */     PlatformDependent0.putByte(address, value);
/*     */   }
/*     */   
/*     */   public static void putShort(long address, short value) {
/* 276 */     PlatformDependent0.putShort(address, value);
/*     */   }
/*     */   
/*     */   public static void putInt(long address, int value) {
/* 280 */     PlatformDependent0.putInt(address, value);
/*     */   }
/*     */   
/*     */   public static void putLong(long address, long value) {
/* 284 */     PlatformDependent0.putLong(address, value);
/*     */   }
/*     */   
/*     */   public static void copyMemory(long srcAddr, long dstAddr, long length) {
/* 288 */     PlatformDependent0.copyMemory(srcAddr, dstAddr, length);
/*     */   }
/*     */   
/*     */   public static void copyMemory(byte[] src, int srcIndex, long dstAddr, long length) {
/* 292 */     PlatformDependent0.copyMemory(src, ARRAY_BASE_OFFSET + srcIndex, null, dstAddr, length);
/*     */   }
/*     */   
/*     */   public static void copyMemory(long srcAddr, byte[] dst, int dstIndex, long length) {
/* 296 */     PlatformDependent0.copyMemory(null, srcAddr, dst, ARRAY_BASE_OFFSET + dstIndex, length);
/*     */   }
/*     */   
/*     */   private static boolean isAndroid0() {
/*     */     boolean bool;
/*     */     try {
/* 302 */       Class.forName("android.app.Application", false, ClassLoader.getSystemClassLoader());
/* 303 */       bool = true;
/* 304 */     } catch (Exception e) {
/* 305 */       bool = false;
/*     */     } 
/*     */     
/* 308 */     if (bool) {
/* 309 */       logger.debug("Platform: Android");
/*     */     }
/* 311 */     return bool;
/*     */   }
/*     */   
/*     */   private static boolean isWindows0() {
/* 315 */     boolean windows = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
/* 316 */     if (windows) {
/* 317 */       logger.debug("Platform: Windows");
/*     */     }
/* 319 */     return windows;
/*     */   }
/*     */   
/*     */   private static boolean isRoot0() {
/* 323 */     if (isWindows()) {
/* 324 */       return false;
/*     */     }
/*     */     
/* 327 */     String[] ID_COMMANDS = { "/usr/bin/id", "/bin/id", "id" };
/* 328 */     Pattern UID_PATTERN = Pattern.compile("^(?:0|[1-9][0-9]*)$");
/* 329 */     for (String idCmd : ID_COMMANDS) {
/* 330 */       Process p = null;
/* 331 */       BufferedReader in = null;
/* 332 */       String uid = null;
/*     */       try {
/* 334 */         p = Runtime.getRuntime().exec(new String[] { idCmd, "-u" });
/* 335 */         in = new BufferedReader(new InputStreamReader(p.getInputStream(), CharsetUtil.US_ASCII));
/* 336 */         uid = in.readLine();
/* 337 */         in.close();
/*     */         
/*     */         while (true) {
/*     */           try {
/* 341 */             int exitCode = p.waitFor();
/* 342 */             if (exitCode != 0) {
/* 343 */               uid = null;
/*     */             }
/*     */             break;
/* 346 */           } catch (InterruptedException e) {}
/*     */         }
/*     */       
/*     */       }
/* 350 */       catch (Exception e) {
/* 351 */         uid = null;
/*     */       } finally {
/* 353 */         if (in != null) {
/*     */           try {
/* 355 */             in.close();
/* 356 */           } catch (IOException e) {}
/*     */         }
/*     */ 
/*     */         
/* 360 */         if (p != null) {
/*     */           try {
/* 362 */             p.destroy();
/* 363 */           } catch (Exception e) {}
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 369 */       if (uid != null && UID_PATTERN.matcher(uid).matches()) {
/* 370 */         logger.debug("UID: {}", uid);
/* 371 */         return "0".equals(uid);
/*     */       } 
/*     */     } 
/*     */     
/* 375 */     logger.debug("Could not determine the current UID using /usr/bin/id; attempting to bind at privileged ports.");
/*     */     
/* 377 */     Pattern PERMISSION_DENIED = Pattern.compile(".*(?:denied|not.*permitted).*");
/* 378 */     for (int i = 1023; i > 0; i--) {
/* 379 */       ServerSocket ss = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 410 */     logger.debug("UID: non-root (failed to bind at any privileged ports)");
/* 411 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int javaVersion0() {
/*     */     byte b;
/* 421 */     if (isAndroid()) {
/* 422 */       b = 6;
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 427 */         Class.forName("java.time.Clock", false, Object.class.getClassLoader());
/* 428 */         b = 8;
/*     */       }
/* 430 */       catch (Exception e) {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 435 */           Class.forName("java.util.concurrent.LinkedTransferQueue", false, BlockingQueue.class.getClassLoader());
/* 436 */           b = 7;
/*     */         }
/* 438 */         catch (Exception exception) {
/*     */ 
/*     */ 
/*     */           
/* 442 */           b = 6;
/*     */         } 
/*     */       } 
/*     */     } 
/* 446 */     if (logger.isDebugEnabled()) {
/* 447 */       logger.debug("Java version: {}", Integer.valueOf(b));
/*     */     }
/* 449 */     return b;
/*     */   }
/*     */   
/*     */   private static boolean hasUnsafe0() {
/* 453 */     boolean tryUnsafe, noUnsafe = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
/* 454 */     logger.debug("-Dio.netty.noUnsafe: {}", Boolean.valueOf(noUnsafe));
/*     */     
/* 456 */     if (isAndroid()) {
/* 457 */       logger.debug("sun.misc.Unsafe: unavailable (Android)");
/* 458 */       return false;
/*     */     } 
/*     */     
/* 461 */     if (noUnsafe) {
/* 462 */       logger.debug("sun.misc.Unsafe: unavailable (io.netty.noUnsafe)");
/* 463 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 468 */     if (SystemPropertyUtil.contains("io.netty.tryUnsafe")) {
/* 469 */       tryUnsafe = SystemPropertyUtil.getBoolean("io.netty.tryUnsafe", true);
/*     */     } else {
/* 471 */       tryUnsafe = SystemPropertyUtil.getBoolean("org.jboss.netty.tryUnsafe", true);
/*     */     } 
/*     */     
/* 474 */     if (!tryUnsafe) {
/* 475 */       logger.debug("sun.misc.Unsafe: unavailable (io.netty.tryUnsafe/org.jboss.netty.tryUnsafe)");
/* 476 */       return false;
/*     */     } 
/*     */     
/*     */     try {
/* 480 */       boolean hasUnsafe = PlatformDependent0.hasUnsafe();
/* 481 */       logger.debug("sun.misc.Unsafe: {}", hasUnsafe ? "available" : "unavailable");
/* 482 */       return hasUnsafe;
/* 483 */     } catch (Throwable t) {
/* 484 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static long arrayBaseOffset0() {
/* 489 */     if (!hasUnsafe()) {
/* 490 */       return -1L;
/*     */     }
/*     */     
/* 493 */     return PlatformDependent0.arrayBaseOffset();
/*     */   }
/*     */   
/*     */   private static long maxDirectMemory0() {
/* 497 */     long maxDirectMemory = 0L;
/*     */     
/*     */     try {
/* 500 */       Class<?> vmClass = Class.forName("sun.misc.VM", true, ClassLoader.getSystemClassLoader());
/* 501 */       Method m = vmClass.getDeclaredMethod("maxDirectMemory", new Class[0]);
/* 502 */       maxDirectMemory = ((Number)m.invoke(null, new Object[0])).longValue();
/* 503 */     } catch (Throwable t) {}
/*     */ 
/*     */ 
/*     */     
/* 507 */     if (maxDirectMemory > 0L) {
/* 508 */       return maxDirectMemory;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 514 */       Class<?> mgmtFactoryClass = Class.forName("java.lang.management.ManagementFactory", true, ClassLoader.getSystemClassLoader());
/*     */       
/* 516 */       Class<?> runtimeClass = Class.forName("java.lang.management.RuntimeMXBean", true, ClassLoader.getSystemClassLoader());
/*     */ 
/*     */       
/* 519 */       Object runtime = mgmtFactoryClass.getDeclaredMethod("getRuntimeMXBean", new Class[0]).invoke(null, new Object[0]);
/*     */ 
/*     */       
/* 522 */       List<String> vmArgs = (List<String>)runtimeClass.getDeclaredMethod("getInputArguments", new Class[0]).invoke(runtime, new Object[0]);
/* 523 */       for (int i = vmArgs.size() - 1; i >= 0; ) {
/* 524 */         Matcher m = MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN.matcher(vmArgs.get(i));
/* 525 */         if (!m.matches()) {
/*     */           i--;
/*     */           continue;
/*     */         } 
/* 529 */         maxDirectMemory = Long.parseLong(m.group(1));
/* 530 */         switch (m.group(2).charAt(0)) { case 'K':
/*     */           case 'k':
/* 532 */             maxDirectMemory *= 1024L; break;
/*     */           case 'M':
/*     */           case 'm':
/* 535 */             maxDirectMemory *= 1048576L; break;
/*     */           case 'G':
/*     */           case 'g':
/* 538 */             maxDirectMemory *= 1073741824L;
/*     */             break; }
/*     */ 
/*     */       
/*     */       } 
/* 543 */     } catch (Throwable t) {}
/*     */ 
/*     */ 
/*     */     
/* 547 */     if (maxDirectMemory <= 0L) {
/* 548 */       maxDirectMemory = Runtime.getRuntime().maxMemory();
/* 549 */       logger.debug("maxDirectMemory: {} bytes (maybe)", Long.valueOf(maxDirectMemory));
/*     */     } else {
/* 551 */       logger.debug("maxDirectMemory: {} bytes", Long.valueOf(maxDirectMemory));
/*     */     } 
/*     */     
/* 554 */     return maxDirectMemory;
/*     */   }
/*     */   
/*     */   private static boolean hasJavassist0() {
/* 558 */     boolean noJavassist = SystemPropertyUtil.getBoolean("io.netty.noJavassist", false);
/* 559 */     logger.debug("-Dio.netty.noJavassist: {}", Boolean.valueOf(noJavassist));
/*     */     
/* 561 */     if (noJavassist) {
/* 562 */       logger.debug("Javassist: unavailable (io.netty.noJavassist)");
/* 563 */       return false;
/*     */     } 
/*     */     
/*     */     try {
/* 567 */       JavassistTypeParameterMatcherGenerator.generate(Object.class, PlatformDependent.class.getClassLoader());
/* 568 */       logger.debug("Javassist: available");
/* 569 */       return true;
/* 570 */     } catch (Throwable t) {
/* 571 */       logger.debug("Javassist: unavailable");
/* 572 */       logger.debug("You don't have Javassist in your class path or you don't have enough permission to load dynamically generated classes.  Please check the configuration for better performance.");
/*     */ 
/*     */       
/* 575 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\PlatformDependent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */