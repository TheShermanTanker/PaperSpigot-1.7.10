/*     */ package net.minecraft.util.io.netty.handler.codec.compression;
/*     */ 
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.Deflater;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromiseNotifier;
/*     */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JdkZlibEncoder
/*     */   extends ZlibEncoder
/*     */ {
/*  36 */   private final byte[] encodeBuf = new byte[8192];
/*     */   
/*     */   private final Deflater deflater;
/*     */   
/*     */   private volatile boolean finished;
/*     */   
/*     */   private volatile ChannelHandlerContext ctx;
/*     */   
/*     */   private final boolean gzip;
/*  45 */   private final CRC32 crc = new CRC32();
/*  46 */   private static final byte[] gzipHeader = new byte[] { 31, -117, 8, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean writeHeader = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibEncoder() {
/*  56 */     this(6);
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
/*     */   public JdkZlibEncoder(int compressionLevel) {
/*  71 */     this(ZlibWrapper.ZLIB, compressionLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibEncoder(ZlibWrapper wrapper) {
/*  81 */     this(wrapper, 6);
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
/*     */   public JdkZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
/*  96 */     if (compressionLevel < 0 || compressionLevel > 9) {
/*  97 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/* 100 */     if (wrapper == null) {
/* 101 */       throw new NullPointerException("wrapper");
/*     */     }
/* 103 */     if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
/* 104 */       throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 109 */     this.gzip = (wrapper == ZlibWrapper.GZIP);
/* 110 */     this.deflater = new Deflater(compressionLevel, (wrapper != ZlibWrapper.ZLIB));
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
/*     */   public JdkZlibEncoder(byte[] dictionary) {
/* 124 */     this(6, dictionary);
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
/*     */   public JdkZlibEncoder(int compressionLevel, byte[] dictionary) {
/* 142 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 143 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/* 146 */     if (dictionary == null) {
/* 147 */       throw new NullPointerException("dictionary");
/*     */     }
/*     */     
/* 150 */     this.gzip = false;
/* 151 */     this.deflater = new Deflater(compressionLevel);
/* 152 */     this.deflater.setDictionary(dictionary);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 157 */     return close(ctx().newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise promise) {
/* 162 */     ChannelHandlerContext ctx = ctx();
/* 163 */     EventExecutor executor = ctx.executor();
/* 164 */     if (executor.inEventLoop()) {
/* 165 */       return finishEncode(ctx, promise);
/*     */     }
/* 167 */     final ChannelPromise p = ctx.newPromise();
/* 168 */     executor.execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 171 */             ChannelFuture f = JdkZlibEncoder.this.finishEncode(JdkZlibEncoder.this.ctx(), p);
/* 172 */             f.addListener((GenericFutureListener)new ChannelPromiseNotifier(new ChannelPromise[] { this.val$promise }));
/*     */           }
/*     */         });
/* 175 */     return (ChannelFuture)p;
/*     */   }
/*     */ 
/*     */   
/*     */   private ChannelHandlerContext ctx() {
/* 180 */     ChannelHandlerContext ctx = this.ctx;
/* 181 */     if (ctx == null) {
/* 182 */       throw new IllegalStateException("not added to a pipeline");
/*     */     }
/* 184 */     return ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 189 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf uncompressed, ByteBuf out) throws Exception {
/* 194 */     if (this.finished) {
/* 195 */       out.writeBytes(uncompressed);
/*     */       
/*     */       return;
/*     */     } 
/* 199 */     byte[] inAry = new byte[uncompressed.readableBytes()];
/* 200 */     uncompressed.readBytes(inAry);
/*     */     
/* 202 */     int sizeEstimate = (int)Math.ceil(inAry.length * 1.001D) + 12;
/* 203 */     out.ensureWritable(sizeEstimate);
/*     */     
/* 205 */     if (this.gzip) {
/* 206 */       this.crc.update(inAry);
/* 207 */       if (this.writeHeader) {
/* 208 */         out.writeBytes(gzipHeader);
/* 209 */         this.writeHeader = false;
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     this.deflater.setInput(inAry);
/* 214 */     while (!this.deflater.needsInput()) {
/* 215 */       int numBytes = this.deflater.deflate(this.encodeBuf, 0, this.encodeBuf.length, 2);
/* 216 */       out.writeBytes(this.encodeBuf, 0, numBytes);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
/* 222 */     ChannelFuture f = finishEncode(ctx, ctx.newPromise());
/* 223 */     f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture f) throws Exception {
/* 226 */             ctx.close(promise);
/*     */           }
/*     */         });
/*     */     
/* 230 */     if (!f.isDone())
/*     */     {
/* 232 */       ctx.executor().schedule(new Runnable()
/*     */           {
/*     */             public void run() {
/* 235 */               ctx.close(promise);
/*     */             }
/*     */           },  10L, TimeUnit.SECONDS);
/*     */     }
/*     */   }
/*     */   
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 242 */     if (this.finished) {
/* 243 */       promise.setSuccess();
/* 244 */       return (ChannelFuture)promise;
/*     */     } 
/*     */     
/* 247 */     this.finished = true;
/* 248 */     ByteBuf footer = ctx.alloc().buffer();
/* 249 */     this.deflater.finish();
/* 250 */     while (!this.deflater.finished()) {
/* 251 */       int numBytes = this.deflater.deflate(this.encodeBuf, 0, this.encodeBuf.length);
/* 252 */       footer.writeBytes(this.encodeBuf, 0, numBytes);
/*     */     } 
/* 254 */     if (this.gzip) {
/* 255 */       int crcValue = (int)this.crc.getValue();
/* 256 */       int uncBytes = this.deflater.getTotalIn();
/* 257 */       footer.writeByte(crcValue);
/* 258 */       footer.writeByte(crcValue >>> 8);
/* 259 */       footer.writeByte(crcValue >>> 16);
/* 260 */       footer.writeByte(crcValue >>> 24);
/* 261 */       footer.writeByte(uncBytes);
/* 262 */       footer.writeByte(uncBytes >>> 8);
/* 263 */       footer.writeByte(uncBytes >>> 16);
/* 264 */       footer.writeByte(uncBytes >>> 24);
/*     */     } 
/* 266 */     this.deflater.end();
/* 267 */     return ctx.writeAndFlush(footer, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 272 */     this.ctx = ctx;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\compression\JdkZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */