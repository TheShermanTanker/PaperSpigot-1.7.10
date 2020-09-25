/*     */ package net.minecraft.util.io.netty.util.internal;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import sun.misc.Cleaner;
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
/*     */ final class PlatformDependent0
/*     */ {
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PlatformDependent0.class);
/*     */   private static final Unsafe UNSAFE;
/*  36 */   private static final boolean BIG_ENDIAN = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
/*     */   
/*     */   private static final long CLEANER_FIELD_OFFSET;
/*     */   
/*     */   private static final long ADDRESS_FIELD_OFFSET;
/*     */   private static final Field CLEANER_FIELD;
/*     */   private static final boolean UNALIGNED;
/*     */   
/*     */   static {
/*     */     Field field1, field2;
/*     */     Unsafe unsafe;
/*     */   }
/*     */   
/*     */   static {
/*  50 */     ByteBuffer direct = ByteBuffer.allocateDirect(1);
/*     */     
/*     */     try {
/*  53 */       field1 = direct.getClass().getDeclaredField("cleaner");
/*  54 */       field1.setAccessible(true);
/*  55 */       field2 = (Field)field1.get(direct);
/*  56 */       field2.clean();
/*  57 */     } catch (Throwable t) {
/*  58 */       field1 = null;
/*     */     } 
/*  60 */     CLEANER_FIELD = field1;
/*  61 */     logger.debug("java.nio.ByteBuffer.cleaner: {}", (field1 != null) ? "available" : "unavailable");
/*     */ 
/*     */     
/*     */     try {
/*  65 */       field2 = Buffer.class.getDeclaredField("address");
/*  66 */       field2.setAccessible(true);
/*  67 */       if (field2.getLong(ByteBuffer.allocate(1)) != 0L) {
/*  68 */         field2 = null;
/*     */       } else {
/*  70 */         direct = ByteBuffer.allocateDirect(1);
/*  71 */         if (field2.getLong(direct) == 0L) {
/*  72 */           field2 = null;
/*     */         }
/*  74 */         Cleaner cleaner = (Cleaner)field1.get(direct);
/*  75 */         cleaner.clean();
/*     */       } 
/*  77 */     } catch (Throwable t) {
/*  78 */       field2 = null;
/*     */     } 
/*  80 */     logger.debug("java.nio.Buffer.address: {}", (field2 != null) ? "available" : "unavailable");
/*     */ 
/*     */     
/*  83 */     if (field2 != null && field1 != null) {
/*     */       try {
/*  85 */         Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
/*  86 */         unsafeField.setAccessible(true);
/*  87 */         unsafe = (Unsafe)unsafeField.get(null);
/*  88 */         logger.debug("sun.misc.Unsafe.theUnsafe: {}", (unsafe != null) ? "available" : "unavailable");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  94 */           unsafe.getClass().getDeclaredMethod("copyMemory", new Class[] { Object.class, long.class, Object.class, long.class, long.class });
/*     */ 
/*     */ 
/*     */           
/*  98 */           logger.debug("sun.misc.Unsafe.copyMemory: available");
/*  99 */         } catch (NoSuchMethodError t) {
/* 100 */           logger.debug("sun.misc.Unsafe.copyMemory: unavailable");
/* 101 */           throw t;
/* 102 */         } catch (NoSuchMethodException e) {
/* 103 */           logger.debug("sun.misc.Unsafe.copyMemory: unavailable");
/* 104 */           throw e;
/*     */         } 
/* 106 */       } catch (Throwable cause) {
/* 107 */         unsafe = null;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 112 */       unsafe = null;
/*     */     } 
/* 114 */     UNSAFE = unsafe;
/*     */     
/* 116 */     if (unsafe == null) {
/* 117 */       CLEANER_FIELD_OFFSET = -1L;
/* 118 */       ADDRESS_FIELD_OFFSET = -1L;
/* 119 */       UNALIGNED = false;
/*     */     } else {
/* 121 */       boolean bool; ADDRESS_FIELD_OFFSET = objectFieldOffset(field2);
/* 122 */       CLEANER_FIELD_OFFSET = objectFieldOffset(field1);
/*     */ 
/*     */       
/*     */       try {
/* 126 */         Class<?> bitsClass = Class.forName("java.nio.Bits", false, ClassLoader.getSystemClassLoader());
/* 127 */         Method unalignedMethod = bitsClass.getDeclaredMethod("unaligned", new Class[0]);
/* 128 */         unalignedMethod.setAccessible(true);
/* 129 */         bool = Boolean.TRUE.equals(unalignedMethod.invoke(null, new Object[0]));
/* 130 */       } catch (Throwable t) {
/*     */         
/* 132 */         String arch = SystemPropertyUtil.get("os.arch", "");
/*     */         
/* 134 */         bool = arch.matches("^(i[3-6]86|x86(_64)?|x64|amd64)$");
/*     */       } 
/*     */       
/* 137 */       UNALIGNED = bool;
/* 138 */       logger.debug("java.nio.Bits.unaligned: {}", Boolean.valueOf(UNALIGNED));
/*     */     } 
/*     */   }
/*     */   
/*     */   static boolean hasUnsafe() {
/* 143 */     return (UNSAFE != null);
/*     */   }
/*     */   
/*     */   static void throwException(Throwable t) {
/* 147 */     UNSAFE.throwException(t);
/*     */   }
/*     */ 
/*     */   
/*     */   static void freeDirectBufferUnsafe(ByteBuffer buffer) {
/*     */     try {
/* 153 */       Cleaner cleaner = (Cleaner)getObject(buffer, CLEANER_FIELD_OFFSET);
/* 154 */       if (cleaner == null) {
/* 155 */         throw new IllegalArgumentException("attempted to deallocate the buffer which was allocated via JNIEnv->NewDirectByteBuffer()");
/*     */       }
/*     */       
/* 158 */       cleaner.clean();
/* 159 */     } catch (Throwable t) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void freeDirectBuffer(ByteBuffer buffer) {
/* 165 */     if (CLEANER_FIELD == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 169 */       Cleaner cleaner = (Cleaner)CLEANER_FIELD.get(buffer);
/* 170 */       if (cleaner == null) {
/* 171 */         throw new IllegalArgumentException("attempted to deallocate the buffer which was allocated via JNIEnv->NewDirectByteBuffer()");
/*     */       }
/*     */       
/* 174 */       cleaner.clean();
/* 175 */     } catch (Throwable t) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static long directBufferAddress(ByteBuffer buffer) {
/* 181 */     return getLong(buffer, ADDRESS_FIELD_OFFSET);
/*     */   }
/*     */   
/*     */   static long arrayBaseOffset() {
/* 185 */     return UNSAFE.arrayBaseOffset(byte[].class);
/*     */   }
/*     */   
/*     */   static Object getObject(Object object, long fieldOffset) {
/* 189 */     return UNSAFE.getObject(object, fieldOffset);
/*     */   }
/*     */   
/*     */   static int getInt(Object object, long fieldOffset) {
/* 193 */     return UNSAFE.getInt(object, fieldOffset);
/*     */   }
/*     */   
/*     */   private static long getLong(Object object, long fieldOffset) {
/* 197 */     return UNSAFE.getLong(object, fieldOffset);
/*     */   }
/*     */   
/*     */   static long objectFieldOffset(Field field) {
/* 201 */     return UNSAFE.objectFieldOffset(field);
/*     */   }
/*     */   
/*     */   static byte getByte(long address) {
/* 205 */     return UNSAFE.getByte(address);
/*     */   }
/*     */   
/*     */   static short getShort(long address) {
/* 209 */     if (UNALIGNED)
/* 210 */       return UNSAFE.getShort(address); 
/* 211 */     if (BIG_ENDIAN) {
/* 212 */       return (short)(getByte(address) << 8 | getByte(address + 1L) & 0xFF);
/*     */     }
/* 214 */     return (short)(getByte(address + 1L) << 8 | getByte(address) & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   static int getInt(long address) {
/* 219 */     if (UNALIGNED)
/* 220 */       return UNSAFE.getInt(address); 
/* 221 */     if (BIG_ENDIAN) {
/* 222 */       return getByte(address) << 24 | (getByte(address + 1L) & 0xFF) << 16 | (getByte(address + 2L) & 0xFF) << 8 | getByte(address + 3L) & 0xFF;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 227 */     return getByte(address + 3L) << 24 | (getByte(address + 2L) & 0xFF) << 16 | (getByte(address + 1L) & 0xFF) << 8 | getByte(address) & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getLong(long address) {
/* 235 */     if (UNALIGNED)
/* 236 */       return UNSAFE.getLong(address); 
/* 237 */     if (BIG_ENDIAN) {
/* 238 */       return getByte(address) << 56L | (getByte(address + 1L) & 0xFFL) << 48L | (getByte(address + 2L) & 0xFFL) << 40L | (getByte(address + 3L) & 0xFFL) << 32L | (getByte(address + 4L) & 0xFFL) << 24L | (getByte(address + 5L) & 0xFFL) << 16L | (getByte(address + 6L) & 0xFFL) << 8L | getByte(address + 7L) & 0xFFL;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     return getByte(address + 7L) << 56L | (getByte(address + 6L) & 0xFFL) << 48L | (getByte(address + 5L) & 0xFFL) << 40L | (getByte(address + 4L) & 0xFFL) << 32L | (getByte(address + 3L) & 0xFFL) << 24L | (getByte(address + 2L) & 0xFFL) << 16L | (getByte(address + 1L) & 0xFFL) << 8L | getByte(address) & 0xFFL;
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
/*     */   static void putByte(long address, byte value) {
/* 259 */     UNSAFE.putByte(address, value);
/*     */   }
/*     */   
/*     */   static void putShort(long address, short value) {
/* 263 */     if (UNALIGNED) {
/* 264 */       UNSAFE.putShort(address, value);
/* 265 */     } else if (BIG_ENDIAN) {
/* 266 */       putByte(address, (byte)(value >>> 8));
/* 267 */       putByte(address + 1L, (byte)value);
/*     */     } else {
/* 269 */       putByte(address + 1L, (byte)(value >>> 8));
/* 270 */       putByte(address, (byte)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void putInt(long address, int value) {
/* 275 */     if (UNALIGNED) {
/* 276 */       UNSAFE.putInt(address, value);
/* 277 */     } else if (BIG_ENDIAN) {
/* 278 */       putByte(address, (byte)(value >>> 24));
/* 279 */       putByte(address + 1L, (byte)(value >>> 16));
/* 280 */       putByte(address + 2L, (byte)(value >>> 8));
/* 281 */       putByte(address + 3L, (byte)value);
/*     */     } else {
/* 283 */       putByte(address + 3L, (byte)(value >>> 24));
/* 284 */       putByte(address + 2L, (byte)(value >>> 16));
/* 285 */       putByte(address + 1L, (byte)(value >>> 8));
/* 286 */       putByte(address, (byte)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void putLong(long address, long value) {
/* 291 */     if (UNALIGNED) {
/* 292 */       UNSAFE.putLong(address, value);
/* 293 */     } else if (BIG_ENDIAN) {
/* 294 */       putByte(address, (byte)(int)(value >>> 56L));
/* 295 */       putByte(address + 1L, (byte)(int)(value >>> 48L));
/* 296 */       putByte(address + 2L, (byte)(int)(value >>> 40L));
/* 297 */       putByte(address + 3L, (byte)(int)(value >>> 32L));
/* 298 */       putByte(address + 4L, (byte)(int)(value >>> 24L));
/* 299 */       putByte(address + 5L, (byte)(int)(value >>> 16L));
/* 300 */       putByte(address + 6L, (byte)(int)(value >>> 8L));
/* 301 */       putByte(address + 7L, (byte)(int)value);
/*     */     } else {
/* 303 */       putByte(address + 7L, (byte)(int)(value >>> 56L));
/* 304 */       putByte(address + 6L, (byte)(int)(value >>> 48L));
/* 305 */       putByte(address + 5L, (byte)(int)(value >>> 40L));
/* 306 */       putByte(address + 4L, (byte)(int)(value >>> 32L));
/* 307 */       putByte(address + 3L, (byte)(int)(value >>> 24L));
/* 308 */       putByte(address + 2L, (byte)(int)(value >>> 16L));
/* 309 */       putByte(address + 1L, (byte)(int)(value >>> 8L));
/* 310 */       putByte(address, (byte)(int)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void copyMemory(long srcAddr, long dstAddr, long length) {
/* 315 */     UNSAFE.copyMemory(srcAddr, dstAddr, length);
/*     */   }
/*     */   
/*     */   static void copyMemory(Object src, long srcOffset, Object dst, long dstOffset, long length) {
/* 319 */     UNSAFE.copyMemory(src, srcOffset, dst, dstOffset, length);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\PlatformDependent0.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */