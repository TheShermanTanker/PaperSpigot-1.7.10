/*     */ package net.minecraft.util.io.netty.handler.stream;
/*     */ 
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelProgressivePromise;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
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
/*     */ public class ChunkedWriteHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  72 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
/*     */ 
/*     */   
/*  75 */   private final Queue<PendingWrite> queue = new ArrayDeque<PendingWrite>();
/*     */ 
/*     */   
/*     */   private volatile ChannelHandlerContext ctx;
/*     */ 
/*     */   
/*     */   private PendingWrite currentWrite;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ChunkedWriteHandler(int maxPendingWrites) {
/*  87 */     if (maxPendingWrites <= 0) {
/*  88 */       throw new IllegalArgumentException("maxPendingWrites: " + maxPendingWrites + " (expected: > 0)");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  95 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeTransfer() {
/* 102 */     final ChannelHandlerContext ctx = this.ctx;
/* 103 */     if (ctx == null) {
/*     */       return;
/*     */     }
/* 106 */     if (ctx.executor().inEventLoop()) {
/*     */       try {
/* 108 */         doFlush(ctx);
/* 109 */       } catch (Exception e) {
/* 110 */         if (logger.isWarnEnabled()) {
/* 111 */           logger.warn("Unexpected exception while sending chunks.", e);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 116 */       ctx.executor().execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*     */               try {
/* 121 */                 ChunkedWriteHandler.this.doFlush(ctx);
/* 122 */               } catch (Exception e) {
/* 123 */                 if (ChunkedWriteHandler.logger.isWarnEnabled()) {
/* 124 */                   ChunkedWriteHandler.logger.warn("Unexpected exception while sending chunks.", e);
/*     */                 }
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 134 */     this.queue.add(new PendingWrite(msg, promise));
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 139 */     Channel channel = ctx.channel();
/* 140 */     if (channel.isWritable() || !channel.isActive()) {
/* 141 */       doFlush(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 147 */     doFlush(ctx);
/* 148 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
/* 153 */     if (ctx.channel().isWritable())
/*     */     {
/* 155 */       doFlush(ctx);
/*     */     }
/* 157 */     ctx.fireChannelWritabilityChanged();
/*     */   }
/*     */   
/*     */   private void discard(Throwable cause) {
/*     */     while (true) {
/* 162 */       PendingWrite currentWrite = this.currentWrite;
/*     */       
/* 164 */       if (this.currentWrite == null) {
/* 165 */         currentWrite = this.queue.poll();
/*     */       } else {
/* 167 */         this.currentWrite = null;
/*     */       } 
/*     */       
/* 170 */       if (currentWrite == null) {
/*     */         break;
/*     */       }
/* 173 */       Object message = currentWrite.msg;
/* 174 */       if (message instanceof ChunkedInput) {
/* 175 */         ChunkedInput<?> in = (ChunkedInput)message;
/*     */         try {
/* 177 */           if (!in.isEndOfInput()) {
/* 178 */             if (cause == null) {
/* 179 */               cause = new ClosedChannelException();
/*     */             }
/* 181 */             currentWrite.fail(cause);
/*     */           } else {
/* 183 */             currentWrite.success();
/*     */           } 
/* 185 */           closeInput(in);
/* 186 */         } catch (Exception e) {
/* 187 */           currentWrite.fail(e);
/* 188 */           logger.warn(ChunkedInput.class.getSimpleName() + ".isEndOfInput() failed", e);
/* 189 */           closeInput(in);
/*     */         }  continue;
/*     */       } 
/* 192 */       if (cause == null) {
/* 193 */         cause = new ClosedChannelException();
/*     */       }
/* 195 */       currentWrite.fail(cause);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void doFlush(final ChannelHandlerContext ctx) throws Exception {
/* 201 */     final Channel channel = ctx.channel();
/* 202 */     if (!channel.isActive()) {
/* 203 */       discard(null);
/*     */       
/*     */       return;
/*     */     } 
/* 207 */     while (channel.isWritable()) {
/* 208 */       if (this.currentWrite == null) {
/* 209 */         this.currentWrite = this.queue.poll();
/*     */       }
/*     */       
/* 212 */       if (this.currentWrite == null) {
/*     */         break;
/*     */       }
/* 215 */       boolean needsFlush = true;
/* 216 */       final PendingWrite currentWrite = this.currentWrite;
/* 217 */       final Object pendingMessage = currentWrite.msg;
/*     */       
/* 219 */       if (pendingMessage instanceof ChunkedInput) {
/* 220 */         boolean endOfInput, suspend; final ChunkedInput<?> chunks = (ChunkedInput)pendingMessage;
/*     */ 
/*     */         
/* 223 */         Object message = null;
/*     */         try {
/* 225 */           message = chunks.readChunk(ctx);
/* 226 */           endOfInput = chunks.isEndOfInput();
/*     */           
/* 228 */           if (message == null) {
/*     */             
/* 230 */             suspend = !endOfInput;
/*     */           } else {
/* 232 */             suspend = false;
/*     */           } 
/* 234 */         } catch (Throwable t) {
/* 235 */           this.currentWrite = null;
/*     */           
/* 237 */           if (message != null) {
/* 238 */             ReferenceCountUtil.release(message);
/*     */           }
/*     */           
/* 241 */           currentWrite.fail(t);
/* 242 */           if (ctx.executor().inEventLoop()) {
/* 243 */             ctx.fireExceptionCaught(t);
/*     */           } else {
/* 245 */             ctx.executor().execute(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 248 */                     ctx.fireExceptionCaught(t);
/*     */                   }
/*     */                 });
/*     */           } 
/*     */           
/* 253 */           closeInput(chunks);
/*     */           
/*     */           break;
/*     */         } 
/* 257 */         if (suspend) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 264 */         if (message == null)
/*     */         {
/*     */           
/* 267 */           message = Unpooled.EMPTY_BUFFER;
/*     */         }
/*     */         
/* 270 */         final int amount = amount(message);
/* 271 */         ChannelFuture f = ctx.write(message);
/* 272 */         if (endOfInput) {
/* 273 */           this.currentWrite = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 280 */           f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 283 */                   currentWrite.progress(amount);
/* 284 */                   currentWrite.success();
/* 285 */                   ChunkedWriteHandler.closeInput(chunks);
/*     */                 }
/*     */               });
/* 288 */         } else if (channel.isWritable()) {
/* 289 */           f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 292 */                   if (!future.isSuccess()) {
/* 293 */                     ChunkedWriteHandler.closeInput((ChunkedInput)pendingMessage);
/* 294 */                     currentWrite.fail(future.cause());
/*     */                   } else {
/* 296 */                     currentWrite.progress(amount);
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } else {
/* 301 */           f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 304 */                   if (!future.isSuccess()) {
/* 305 */                     ChunkedWriteHandler.closeInput((ChunkedInput)pendingMessage);
/* 306 */                     currentWrite.fail(future.cause());
/*     */                   } else {
/* 308 */                     currentWrite.progress(amount);
/* 309 */                     if (channel.isWritable()) {
/* 310 */                       ChunkedWriteHandler.this.resumeTransfer();
/*     */                     }
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/*     */       } else {
/* 317 */         ctx.write(pendingMessage, currentWrite.promise);
/* 318 */         this.currentWrite = null;
/*     */       } 
/*     */       
/* 321 */       if (needsFlush) {
/* 322 */         ctx.flush();
/*     */       }
/* 324 */       if (!channel.isActive()) {
/* 325 */         discard(new ClosedChannelException());
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void closeInput(ChunkedInput<?> chunks) {
/*     */     try {
/* 333 */       chunks.close();
/* 334 */     } catch (Throwable t) {
/* 335 */       if (logger.isWarnEnabled())
/* 336 */         logger.warn("Failed to close a chunked input.", t); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class PendingWrite
/*     */   {
/*     */     final Object msg;
/*     */     final ChannelPromise promise;
/*     */     private long progress;
/*     */     
/*     */     PendingWrite(Object msg, ChannelPromise promise) {
/* 347 */       this.msg = msg;
/* 348 */       this.promise = promise;
/*     */     }
/*     */     
/*     */     void fail(Throwable cause) {
/* 352 */       ReferenceCountUtil.release(this.msg);
/* 353 */       if (this.promise != null) {
/* 354 */         this.promise.setFailure(cause);
/*     */       }
/*     */     }
/*     */     
/*     */     void success() {
/* 359 */       if (this.promise instanceof ChannelProgressivePromise)
/*     */       {
/* 361 */         ((ChannelProgressivePromise)this.promise).tryProgress(this.progress, this.progress);
/*     */       }
/* 363 */       this.promise.setSuccess();
/*     */     }
/*     */     
/*     */     void progress(int amount) {
/* 367 */       this.progress += amount;
/* 368 */       if (this.promise instanceof ChannelProgressivePromise) {
/* 369 */         ((ChannelProgressivePromise)this.promise).tryProgress(this.progress, -1L);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static int amount(Object msg) {
/* 375 */     if (msg instanceof ByteBuf) {
/* 376 */       return ((ByteBuf)msg).readableBytes();
/*     */     }
/* 378 */     if (msg instanceof ByteBufHolder) {
/* 379 */       return ((ByteBufHolder)msg).content().readableBytes();
/*     */     }
/* 381 */     return 1;
/*     */   }
/*     */   
/*     */   public ChunkedWriteHandler() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\stream\ChunkedWriteHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */