/*     */ package net.minecraft.util.io.netty.util.internal.chmv8;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Random;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class Striped64
/*     */   extends Number
/*     */ {
/*     */   static final class Cell
/*     */   {
/*     */     volatile long p0;
/*     */     volatile long p1;
/*     */     volatile long p2;
/*     */     volatile long p3;
/*     */     volatile long p4;
/*     */     volatile long p5;
/*     */     volatile long p6;
/*     */     volatile long value;
/*     */     volatile long q0;
/*     */     volatile long q1;
/*     */     volatile long q2;
/*     */     volatile long q3;
/*     */     volatile long q4;
/*     */     volatile long q5;
/*     */     volatile long q6;
/*     */     private static final Unsafe UNSAFE;
/*     */     private static final long valueOffset;
/*     */     
/*     */     Cell(long x) {
/* 109 */       this.value = x;
/*     */     }
/*     */     final boolean cas(long cmp, long val) {
/* 112 */       return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       try {
/* 120 */         UNSAFE = Striped64.getUnsafe();
/* 121 */         Class<?> ak = Cell.class;
/* 122 */         valueOffset = UNSAFE.objectFieldOffset(ak.getDeclaredField("value"));
/*     */       }
/* 124 */       catch (Exception e) {
/* 125 */         throw new Error(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class HashCode
/*     */   {
/* 136 */     static final Random rng = new Random(); int code;
/*     */     
/*     */     HashCode() {
/* 139 */       int h = rng.nextInt();
/* 140 */       this.code = (h == 0) ? 1 : h;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class ThreadHashCode
/*     */     extends ThreadLocal<HashCode>
/*     */   {
/*     */     public Striped64.HashCode initialValue() {
/* 148 */       return new Striped64.HashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   static final ThreadHashCode threadHashCode = new ThreadHashCode();
/*     */ 
/*     */   
/* 160 */   static final int NCPU = Runtime.getRuntime().availableProcessors();
/*     */ 
/*     */ 
/*     */   
/*     */   volatile transient Cell[] cells;
/*     */ 
/*     */ 
/*     */   
/*     */   volatile transient long base;
/*     */ 
/*     */ 
/*     */   
/*     */   volatile transient int busy;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Unsafe UNSAFE;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long baseOffset;
/*     */ 
/*     */   
/*     */   private static final long busyOffset;
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean casBase(long cmp, long val) {
/* 188 */     return UNSAFE.compareAndSwapLong(this, baseOffset, cmp, val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean casBusy() {
/* 195 */     return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
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
/*     */   final void retryUpdate(long x, HashCode hc, boolean wasUncontended) {
/* 221 */     int h = hc.code;
/* 222 */     boolean collide = false; while (true) {
/*     */       Cell[] as;
/*     */       int n;
/* 225 */       if ((as = this.cells) != null && (n = as.length) > 0) {
/* 226 */         Cell a; if ((a = as[n - 1 & h]) == null)
/* 227 */         { if (this.busy == 0) {
/* 228 */             Cell r = new Cell(x);
/* 229 */             if (this.busy == 0 && casBusy()) {
/* 230 */               boolean created = false; try {
/*     */                 Cell[] rs; int m;
/*     */                 int j;
/* 233 */                 if ((rs = this.cells) != null && (m = rs.length) > 0 && rs[j = m - 1 & h] == null) {
/*     */ 
/*     */                   
/* 236 */                   rs[j] = r;
/* 237 */                   created = true;
/*     */                 } 
/*     */               } finally {
/* 240 */                 this.busy = 0;
/*     */               } 
/* 242 */               if (created)
/*     */                 break; 
/*     */               continue;
/*     */             } 
/*     */           } 
/* 247 */           collide = false; }
/*     */         
/* 249 */         else if (!wasUncontended)
/* 250 */         { wasUncontended = true; }
/* 251 */         else { long l; if (a.cas(l = a.value, fn(l, x)))
/*     */             break; 
/* 253 */           if (n >= NCPU || this.cells != as) {
/* 254 */             collide = false;
/* 255 */           } else if (!collide) {
/* 256 */             collide = true;
/* 257 */           } else if (this.busy == 0 && casBusy()) {
/*     */             try {
/* 259 */               if (this.cells == as) {
/* 260 */                 Cell[] rs = new Cell[n << 1];
/* 261 */                 for (int i = 0; i < n; i++)
/* 262 */                   rs[i] = as[i]; 
/* 263 */                 this.cells = rs;
/*     */               } 
/*     */             } finally {
/* 266 */               this.busy = 0;
/*     */             } 
/* 268 */             collide = false; continue;
/*     */           }  }
/*     */         
/* 271 */         h ^= h << 13;
/* 272 */         h ^= h >>> 17;
/* 273 */         h ^= h << 5; continue;
/*     */       } 
/* 275 */       if (this.busy == 0 && this.cells == as && casBusy()) {
/* 276 */         boolean init = false;
/*     */         try {
/* 278 */           if (this.cells == as) {
/* 279 */             Cell[] rs = new Cell[2];
/* 280 */             rs[h & 0x1] = new Cell(x);
/* 281 */             this.cells = rs;
/* 282 */             init = true;
/*     */           } 
/*     */         } finally {
/* 285 */           this.busy = 0;
/*     */         } 
/* 287 */         if (init)
/*     */           break;  continue;
/*     */       }  long v;
/* 290 */       if (casBase(v = this.base, fn(v, x)))
/*     */         break; 
/*     */     } 
/* 293 */     hc.code = h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void internalReset(long initialValue) {
/* 301 */     Cell[] as = this.cells;
/* 302 */     this.base = initialValue;
/* 303 */     if (as != null) {
/* 304 */       int n = as.length;
/* 305 */       for (int i = 0; i < n; i++) {
/* 306 */         Cell a = as[i];
/* 307 */         if (a != null) {
/* 308 */           a.value = initialValue;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 319 */       UNSAFE = getUnsafe();
/* 320 */       Class<?> sk = Striped64.class;
/* 321 */       baseOffset = UNSAFE.objectFieldOffset(sk.getDeclaredField("base"));
/*     */       
/* 323 */       busyOffset = UNSAFE.objectFieldOffset(sk.getDeclaredField("busy"));
/*     */     }
/* 325 */     catch (Exception e) {
/* 326 */       throw new Error(e);
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
/*     */   private static Unsafe getUnsafe() {
/*     */     try {
/* 339 */       return Unsafe.getUnsafe();
/* 340 */     } catch (SecurityException tryReflectionInstead) {
/*     */       try {
/* 342 */         return AccessController.<Unsafe>doPrivileged(new PrivilegedExceptionAction<Unsafe>()
/*     */             {
/*     */               public Unsafe run() throws Exception {
/* 345 */                 Class<Unsafe> k = Unsafe.class;
/* 346 */                 for (Field f : k.getDeclaredFields()) {
/* 347 */                   f.setAccessible(true);
/* 348 */                   Object x = f.get(null);
/* 349 */                   if (k.isInstance(x))
/* 350 */                     return k.cast(x); 
/*     */                 } 
/* 352 */                 throw new NoSuchFieldError("the Unsafe"); }
/*     */             });
/* 354 */       } catch (PrivilegedActionException e) {
/* 355 */         throw new RuntimeException("Could not initialize intrinsics", e.getCause());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   abstract long fn(long paramLong1, long paramLong2);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\chmv8\Striped64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */