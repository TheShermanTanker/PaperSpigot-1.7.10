/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.util.io.netty.channel.embedded.EmbeddedChannel;
/*     */ import net.minecraft.util.io.netty.handler.codec.compression.ZlibCodecFactory;
/*     */ import net.minecraft.util.io.netty.handler.codec.compression.ZlibWrapper;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpContentCompressor
/*     */   extends HttpContentEncoder
/*     */ {
/*     */   private final int compressionLevel;
/*     */   private final int windowBits;
/*     */   private final int memLevel;
/*     */   
/*     */   public HttpContentCompressor() {
/*  41 */     this(6);
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
/*     */   public HttpContentCompressor(int compressionLevel) {
/*  54 */     this(compressionLevel, 15, 8);
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
/*     */   public HttpContentCompressor(int compressionLevel, int windowBits, int memLevel) {
/*  77 */     if (compressionLevel < 0 || compressionLevel > 9) {
/*  78 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */ 
/*     */     
/*  82 */     if (windowBits < 9 || windowBits > 15) {
/*  83 */       throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
/*     */     }
/*     */     
/*  86 */     if (memLevel < 1 || memLevel > 9) {
/*  87 */       throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
/*     */     }
/*     */     
/*  90 */     this.compressionLevel = compressionLevel;
/*  91 */     this.windowBits = windowBits;
/*  92 */     this.memLevel = memLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpContentEncoder.Result beginEncode(HttpResponse headers, String acceptEncoding) throws Exception {
/*  97 */     String targetContentEncoding, contentEncoding = headers.headers().get("Content-Encoding");
/*  98 */     if (contentEncoding != null && !"identity".equalsIgnoreCase(contentEncoding))
/*     */     {
/* 100 */       return null;
/*     */     }
/*     */     
/* 103 */     ZlibWrapper wrapper = determineWrapper(acceptEncoding);
/* 104 */     if (wrapper == null) {
/* 105 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 109 */     switch (wrapper) {
/*     */       case GZIP:
/* 111 */         targetContentEncoding = "gzip";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 120 */         return new HttpContentEncoder.Result(targetContentEncoding, new EmbeddedChannel(new ChannelHandler[] { (ChannelHandler)ZlibCodecFactory.newZlibEncoder(wrapper, this.compressionLevel, this.windowBits, this.memLevel) }));case ZLIB: targetContentEncoding = "deflate"; return new HttpContentEncoder.Result(targetContentEncoding, new EmbeddedChannel(new ChannelHandler[] { (ChannelHandler)ZlibCodecFactory.newZlibEncoder(wrapper, this.compressionLevel, this.windowBits, this.memLevel) }));
/*     */     } 
/*     */     throw new Error();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ZlibWrapper determineWrapper(String acceptEncoding) {
/* 127 */     float starQ = -1.0F;
/* 128 */     float gzipQ = -1.0F;
/* 129 */     float deflateQ = -1.0F;
/* 130 */     for (String encoding : StringUtil.split(acceptEncoding, ',')) {
/* 131 */       float q = 1.0F;
/* 132 */       int equalsPos = encoding.indexOf('=');
/* 133 */       if (equalsPos != -1) {
/*     */         try {
/* 135 */           q = Float.valueOf(encoding.substring(equalsPos + 1)).floatValue();
/* 136 */         } catch (NumberFormatException e) {
/*     */           
/* 138 */           q = 0.0F;
/*     */         } 
/*     */       }
/* 141 */       if (encoding.contains("*")) {
/* 142 */         starQ = q;
/* 143 */       } else if (encoding.contains("gzip") && q > gzipQ) {
/* 144 */         gzipQ = q;
/* 145 */       } else if (encoding.contains("deflate") && q > deflateQ) {
/* 146 */         deflateQ = q;
/*     */       } 
/*     */     } 
/* 149 */     if (gzipQ > 0.0F || deflateQ > 0.0F) {
/* 150 */       if (gzipQ >= deflateQ) {
/* 151 */         return ZlibWrapper.GZIP;
/*     */       }
/* 153 */       return ZlibWrapper.ZLIB;
/*     */     } 
/*     */     
/* 156 */     if (starQ > 0.0F) {
/* 157 */       if (gzipQ == -1.0F) {
/* 158 */         return ZlibWrapper.GZIP;
/*     */       }
/* 160 */       if (deflateQ == -1.0F) {
/* 161 */         return ZlibWrapper.ZLIB;
/*     */       }
/*     */     } 
/* 164 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpContentCompressor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */