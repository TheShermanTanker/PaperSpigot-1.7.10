/*     */ package net.minecraft.util.io.netty.handler.codec.compression;
/*     */ 
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
/*     */ public final class ZlibCodecFactory
/*     */ {
/*  27 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ZlibCodecFactory.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   private static final boolean noJdkZlibDecoder = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibDecoder", true); static {
/*  33 */     logger.debug("-Dio.netty.noJdkZlibDecoder: {}", Boolean.valueOf(noJdkZlibDecoder));
/*     */   }
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int compressionLevel) {
/*  37 */     if (PlatformDependent.javaVersion() < 7) {
/*  38 */       return new JZlibEncoder(compressionLevel);
/*     */     }
/*  40 */     return new JdkZlibEncoder(compressionLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper) {
/*  45 */     if (PlatformDependent.javaVersion() < 7) {
/*  46 */       return new JZlibEncoder(wrapper);
/*     */     }
/*  48 */     return new JdkZlibEncoder(wrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
/*  53 */     if (PlatformDependent.javaVersion() < 7) {
/*  54 */       return new JZlibEncoder(wrapper, compressionLevel);
/*     */     }
/*  56 */     return new JdkZlibEncoder(wrapper, compressionLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper, int compressionLevel, int windowBits, int memLevel) {
/*  61 */     if (PlatformDependent.javaVersion() < 7) {
/*  62 */       return new JZlibEncoder(wrapper, compressionLevel, windowBits, memLevel);
/*     */     }
/*  64 */     return new JdkZlibEncoder(wrapper, compressionLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(byte[] dictionary) {
/*  69 */     if (PlatformDependent.javaVersion() < 7) {
/*  70 */       return new JZlibEncoder(dictionary);
/*     */     }
/*  72 */     return new JdkZlibEncoder(dictionary);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int compressionLevel, byte[] dictionary) {
/*  77 */     if (PlatformDependent.javaVersion() < 7) {
/*  78 */       return new JZlibEncoder(compressionLevel, dictionary);
/*     */     }
/*  80 */     return new JdkZlibEncoder(compressionLevel, dictionary);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int compressionLevel, int windowBits, int memLevel, byte[] dictionary) {
/*  85 */     if (PlatformDependent.javaVersion() < 7) {
/*  86 */       return new JZlibEncoder(compressionLevel, windowBits, memLevel, dictionary);
/*     */     }
/*  88 */     return new JdkZlibEncoder(compressionLevel, dictionary);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder() {
/*  93 */     if (PlatformDependent.javaVersion() < 7 || noJdkZlibDecoder) {
/*  94 */       return new JZlibDecoder();
/*     */     }
/*  96 */     return new JdkZlibDecoder();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder(ZlibWrapper wrapper) {
/* 101 */     switch (wrapper) {
/*     */       case ZLIB_OR_NONE:
/* 103 */         return new JZlibDecoder(wrapper);
/*     */     } 
/* 105 */     if (PlatformDependent.javaVersion() < 7 || noJdkZlibDecoder) {
/* 106 */       return new JZlibDecoder(wrapper);
/*     */     }
/* 108 */     return new JdkZlibDecoder(wrapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder(byte[] dictionary) {
/* 114 */     if (PlatformDependent.javaVersion() < 7 || noJdkZlibDecoder) {
/* 115 */       return new JZlibDecoder(dictionary);
/*     */     }
/* 117 */     return new JdkZlibDecoder(dictionary);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\compression\ZlibCodecFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */