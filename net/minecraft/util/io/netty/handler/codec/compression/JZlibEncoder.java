/*     */ package net.minecraft.util.io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.jcraft.jzlib.Deflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromiseNotifier;
/*     */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ import net.minecraft.util.io.netty.util.internal.EmptyArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JZlibEncoder
/*     */   extends ZlibEncoder
/*     */ {
/*  38 */   private final Deflater z = new Deflater();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean finished;
/*     */ 
/*     */   
/*     */   private volatile ChannelHandlerContext ctx;
/*     */ 
/*     */ 
/*     */   
/*     */   public JZlibEncoder() {
/*  50 */     this(6);
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
/*     */   public JZlibEncoder(int compressionLevel) {
/*  66 */     this(ZlibWrapper.ZLIB, compressionLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JZlibEncoder(ZlibWrapper wrapper) {
/*  77 */     this(wrapper, 6);
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
/*     */   public JZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
/*  93 */     this(wrapper, compressionLevel, 15, 8);
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
/*     */   public JZlibEncoder(ZlibWrapper wrapper, int compressionLevel, int windowBits, int memLevel) {
/* 120 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 121 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */ 
/*     */     
/* 125 */     if (windowBits < 9 || windowBits > 15) {
/* 126 */       throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
/*     */     }
/*     */     
/* 129 */     if (memLevel < 1 || memLevel > 9) {
/* 130 */       throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
/*     */     }
/*     */     
/* 133 */     if (wrapper == null) {
/* 134 */       throw new NullPointerException("wrapper");
/*     */     }
/* 136 */     if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
/* 137 */       throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 142 */     int resultCode = this.z.init(compressionLevel, windowBits, memLevel, ZlibUtil.convertWrapperType(wrapper));
/*     */ 
/*     */     
/* 145 */     if (resultCode != 0) {
/* 146 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
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
/*     */   public JZlibEncoder(byte[] dictionary) {
/* 162 */     this(6, dictionary);
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
/*     */   public JZlibEncoder(int compressionLevel, byte[] dictionary) {
/* 181 */     this(compressionLevel, 15, 8, dictionary);
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
/*     */   public JZlibEncoder(int compressionLevel, int windowBits, int memLevel, byte[] dictionary) {
/* 210 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 211 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/* 213 */     if (windowBits < 9 || windowBits > 15) {
/* 214 */       throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
/*     */     }
/*     */     
/* 217 */     if (memLevel < 1 || memLevel > 9) {
/* 218 */       throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
/*     */     }
/*     */     
/* 221 */     if (dictionary == null) {
/* 222 */       throw new NullPointerException("dictionary");
/*     */     }
/*     */     
/* 225 */     int resultCode = this.z.deflateInit(compressionLevel, windowBits, memLevel, JZlib.W_ZLIB);
/*     */ 
/*     */     
/* 228 */     if (resultCode != 0) {
/* 229 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
/*     */     } else {
/* 231 */       resultCode = this.z.deflateSetDictionary(dictionary, dictionary.length);
/* 232 */       if (resultCode != 0) {
/* 233 */         ZlibUtil.fail(this.z, "failed to set the dictionary", resultCode);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 240 */     return close(ctx().channel().newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise promise) {
/* 245 */     ChannelHandlerContext ctx = ctx();
/* 246 */     EventExecutor executor = ctx.executor();
/* 247 */     if (executor.inEventLoop()) {
/* 248 */       return finishEncode(ctx, promise);
/*     */     }
/* 250 */     final ChannelPromise p = ctx.newPromise();
/* 251 */     executor.execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 254 */             ChannelFuture f = JZlibEncoder.this.finishEncode(JZlibEncoder.this.ctx(), p);
/* 255 */             f.addListener((GenericFutureListener)new ChannelPromiseNotifier(new ChannelPromise[] { this.val$promise }));
/*     */           }
/*     */         });
/* 258 */     return (ChannelFuture)p;
/*     */   }
/*     */ 
/*     */   
/*     */   private ChannelHandlerContext ctx() {
/* 263 */     ChannelHandlerContext ctx = this.ctx;
/* 264 */     if (ctx == null) {
/* 265 */       throw new IllegalStateException("not added to a pipeline");
/*     */     }
/* 267 */     return ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 272 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/* 277 */     if (this.finished) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 283 */       int resultCode, inputLength = in.readableBytes();
/* 284 */       boolean inHasArray = in.hasArray();
/* 285 */       this.z.avail_in = inputLength;
/* 286 */       if (inHasArray) {
/* 287 */         this.z.next_in = in.array();
/* 288 */         this.z.next_in_index = in.arrayOffset() + in.readerIndex();
/*     */       } else {
/* 290 */         byte[] array = new byte[inputLength];
/* 291 */         in.getBytes(in.readerIndex(), array);
/* 292 */         this.z.next_in = array;
/* 293 */         this.z.next_in_index = 0;
/*     */       } 
/* 295 */       int oldNextInIndex = this.z.next_in_index;
/*     */ 
/*     */       
/* 298 */       int maxOutputLength = (int)Math.ceil(inputLength * 1.001D) + 12;
/* 299 */       out.ensureWritable(maxOutputLength);
/* 300 */       this.z.avail_out = maxOutputLength;
/* 301 */       this.z.next_out = out.array();
/* 302 */       this.z.next_out_index = out.arrayOffset() + out.writerIndex();
/* 303 */       int oldNextOutIndex = this.z.next_out_index;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 308 */         resultCode = this.z.deflate(2);
/*     */       } finally {
/* 310 */         in.skipBytes(this.z.next_in_index - oldNextInIndex);
/*     */       } 
/*     */       
/* 313 */       if (resultCode != 0) {
/* 314 */         ZlibUtil.fail(this.z, "compression failure", resultCode);
/*     */       }
/*     */       
/* 317 */       int outputLength = this.z.next_out_index - oldNextOutIndex;
/* 318 */       if (outputLength > 0) {
/* 319 */         out.writerIndex(out.writerIndex() + outputLength);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 326 */       this.z.next_in = null;
/* 327 */       this.z.next_out = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) {
/* 335 */     ChannelFuture f = finishEncode(ctx, ctx.newPromise());
/* 336 */     f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture f) throws Exception {
/* 339 */             ctx.close(promise);
/*     */           }
/*     */         });
/*     */     
/* 343 */     if (!f.isDone())
/*     */     {
/* 345 */       ctx.executor().schedule(new Runnable()
/*     */           {
/*     */             public void run() {
/* 348 */               ctx.close(promise);
/*     */             }
/*     */           },  10L, TimeUnit.SECONDS); } 
/*     */   }
/*     */   
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
/*     */     ByteBuf footer;
/* 355 */     if (this.finished) {
/* 356 */       promise.setSuccess();
/* 357 */       return (ChannelFuture)promise;
/*     */     } 
/* 359 */     this.finished = true;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 364 */       this.z.next_in = EmptyArrays.EMPTY_BYTES;
/* 365 */       this.z.next_in_index = 0;
/* 366 */       this.z.avail_in = 0;
/*     */ 
/*     */       
/* 369 */       byte[] out = new byte[32];
/* 370 */       this.z.next_out = out;
/* 371 */       this.z.next_out_index = 0;
/* 372 */       this.z.avail_out = out.length;
/*     */ 
/*     */       
/* 375 */       int resultCode = this.z.deflate(4);
/* 376 */       if (resultCode != 0 && resultCode != 1) {
/* 377 */         promise.setFailure((Throwable)ZlibUtil.deflaterException(this.z, "compression failure", resultCode));
/* 378 */         return (ChannelFuture)promise;
/* 379 */       }  if (this.z.next_out_index != 0) {
/* 380 */         footer = Unpooled.wrappedBuffer(out, 0, this.z.next_out_index);
/*     */       } else {
/* 382 */         footer = Unpooled.EMPTY_BUFFER;
/*     */       } 
/*     */     } finally {
/* 385 */       this.z.deflateEnd();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 391 */       this.z.next_in = null;
/* 392 */       this.z.next_out = null;
/*     */     } 
/* 394 */     return ctx.writeAndFlush(footer, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 399 */     this.ctx = ctx;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\compression\JZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */