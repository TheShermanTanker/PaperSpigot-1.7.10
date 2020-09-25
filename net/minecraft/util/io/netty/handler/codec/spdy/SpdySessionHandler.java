/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
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
/*     */ public class SpdySessionHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  33 */   private static final SpdyProtocolException PROTOCOL_EXCEPTION = new SpdyProtocolException();
/*  34 */   private static final SpdyProtocolException STREAM_CLOSED = new SpdyProtocolException("Stream closed");
/*     */   
/*     */   static {
/*  37 */     PROTOCOL_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  38 */     STREAM_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */   
/*  41 */   private final SpdySession spdySession = new SpdySession();
/*     */   
/*     */   private int lastGoodStreamId;
/*     */   private static final int DEFAULT_MAX_CONCURRENT_STREAMS = 2147483647;
/*  45 */   private int remoteConcurrentStreams = Integer.MAX_VALUE;
/*  46 */   private int localConcurrentStreams = Integer.MAX_VALUE;
/*  47 */   private int maxConcurrentStreams = Integer.MAX_VALUE;
/*     */   
/*     */   private static final int DEFAULT_WINDOW_SIZE = 65536;
/*  50 */   private int initialSendWindowSize = 65536;
/*  51 */   private int initialReceiveWindowSize = 65536;
/*     */   
/*  53 */   private final Object flowControlLock = new Object();
/*     */   
/*  55 */   private final AtomicInteger pings = new AtomicInteger();
/*     */ 
/*     */   
/*     */   private boolean sentGoAwayFrame;
/*     */ 
/*     */   
/*     */   private boolean receivedGoAwayFrame;
/*     */ 
/*     */   
/*     */   private ChannelFutureListener closeSessionFutureListener;
/*     */ 
/*     */   
/*     */   private final boolean server;
/*     */ 
/*     */   
/*     */   private final boolean flowControl;
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdySessionHandler(int version, boolean server) {
/*  75 */     if (version < 2 || version > 3) {
/*  76 */       throw new IllegalArgumentException("unsupported version: " + version);
/*     */     }
/*     */     
/*  79 */     this.server = server;
/*  80 */     this.flowControl = (version >= 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  85 */     if (msg instanceof SpdyDataFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 109 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/* 110 */       int streamId = spdyDataFrame.getStreamId();
/*     */ 
/*     */ 
/*     */       
/* 114 */       if (!this.spdySession.isActiveStream(streamId)) {
/* 115 */         spdyDataFrame.release();
/* 116 */         if (streamId <= this.lastGoodStreamId) {
/* 117 */           issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/* 118 */         } else if (!this.sentGoAwayFrame) {
/* 119 */           issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 126 */       if (this.spdySession.isRemoteSideClosed(streamId)) {
/* 127 */         spdyDataFrame.release();
/* 128 */         issueStreamError(ctx, streamId, SpdyStreamStatus.STREAM_ALREADY_CLOSED);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 133 */       if (!isRemoteInitiatedID(streamId) && !this.spdySession.hasReceivedReply(streamId)) {
/* 134 */         spdyDataFrame.release();
/* 135 */         issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 145 */       if (this.flowControl) {
/*     */         
/* 147 */         int deltaWindowSize = -1 * spdyDataFrame.content().readableBytes();
/* 148 */         int newWindowSize = this.spdySession.updateReceiveWindowSize(streamId, deltaWindowSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 155 */         if (newWindowSize < this.spdySession.getReceiveWindowSizeLowerBound(streamId)) {
/* 156 */           spdyDataFrame.release();
/* 157 */           issueStreamError(ctx, streamId, SpdyStreamStatus.FLOW_CONTROL_ERROR);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 163 */         if (newWindowSize < 0) {
/* 164 */           while (spdyDataFrame.content().readableBytes() > this.initialReceiveWindowSize) {
/* 165 */             SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readSlice(this.initialReceiveWindowSize).retain());
/*     */             
/* 167 */             ctx.writeAndFlush(partialDataFrame);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 172 */         if (newWindowSize <= this.initialReceiveWindowSize / 2 && !spdyDataFrame.isLast()) {
/* 173 */           deltaWindowSize = this.initialReceiveWindowSize - newWindowSize;
/* 174 */           this.spdySession.updateReceiveWindowSize(streamId, deltaWindowSize);
/* 175 */           SpdyWindowUpdateFrame spdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(streamId, deltaWindowSize);
/*     */           
/* 177 */           ctx.writeAndFlush(spdyWindowUpdateFrame);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 182 */       if (spdyDataFrame.isLast()) {
/* 183 */         halfCloseStream(streamId, true, ctx.newSucceededFuture());
/*     */       }
/*     */     }
/* 186 */     else if (msg instanceof SpdySynStreamFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 203 */       int streamId = spdySynStreamFrame.getStreamId();
/*     */ 
/*     */       
/* 206 */       if (spdySynStreamFrame.isInvalid() || !isRemoteInitiatedID(streamId) || this.spdySession.isActiveStream(streamId)) {
/*     */ 
/*     */         
/* 209 */         issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 214 */       if (streamId <= this.lastGoodStreamId) {
/* 215 */         issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 220 */       byte priority = spdySynStreamFrame.getPriority();
/* 221 */       boolean remoteSideClosed = spdySynStreamFrame.isLast();
/* 222 */       boolean localSideClosed = spdySynStreamFrame.isUnidirectional();
/* 223 */       if (!acceptStream(streamId, priority, remoteSideClosed, localSideClosed)) {
/* 224 */         issueStreamError(ctx, streamId, SpdyStreamStatus.REFUSED_STREAM);
/*     */         
/*     */         return;
/*     */       } 
/* 228 */     } else if (msg instanceof SpdySynReplyFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 237 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 238 */       int streamId = spdySynReplyFrame.getStreamId();
/*     */ 
/*     */       
/* 241 */       if (spdySynReplyFrame.isInvalid() || isRemoteInitiatedID(streamId) || this.spdySession.isRemoteSideClosed(streamId)) {
/*     */ 
/*     */         
/* 244 */         issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 249 */       if (this.spdySession.hasReceivedReply(streamId)) {
/* 250 */         issueStreamError(ctx, streamId, SpdyStreamStatus.STREAM_IN_USE);
/*     */         
/*     */         return;
/*     */       } 
/* 254 */       this.spdySession.receivedReply(streamId);
/*     */ 
/*     */       
/* 257 */       if (spdySynReplyFrame.isLast()) {
/* 258 */         halfCloseStream(streamId, true, ctx.newSucceededFuture());
/*     */       }
/*     */     }
/* 261 */     else if (msg instanceof SpdyRstStreamFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 272 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 273 */       removeStream(spdyRstStreamFrame.getStreamId(), ctx.newSucceededFuture());
/*     */     }
/* 275 */     else if (msg instanceof SpdySettingsFrame) {
/*     */       
/* 277 */       SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
/*     */       
/* 279 */       int newConcurrentStreams = spdySettingsFrame.getValue(4);
/*     */       
/* 281 */       if (newConcurrentStreams >= 0) {
/* 282 */         updateConcurrentStreams(newConcurrentStreams, true);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 288 */       if (spdySettingsFrame.isPersisted(7)) {
/* 289 */         spdySettingsFrame.removeValue(7);
/*     */       }
/* 291 */       spdySettingsFrame.setPersistValue(7, false);
/*     */       
/* 293 */       if (this.flowControl) {
/* 294 */         int newInitialWindowSize = spdySettingsFrame.getValue(7);
/*     */         
/* 296 */         if (newInitialWindowSize >= 0) {
/* 297 */           updateInitialSendWindowSize(newInitialWindowSize);
/*     */         }
/*     */       }
/*     */     
/* 301 */     } else if (msg instanceof SpdyPingFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 312 */       SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
/*     */       
/* 314 */       if (isRemoteInitiatedID(spdyPingFrame.getId())) {
/* 315 */         ctx.writeAndFlush(spdyPingFrame);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 320 */       if (this.pings.get() == 0) {
/*     */         return;
/*     */       }
/* 323 */       this.pings.getAndDecrement();
/*     */     }
/* 325 */     else if (msg instanceof SpdyGoAwayFrame) {
/*     */       
/* 327 */       this.receivedGoAwayFrame = true;
/*     */     }
/* 329 */     else if (msg instanceof SpdyHeadersFrame) {
/*     */       
/* 331 */       SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 332 */       int streamId = spdyHeadersFrame.getStreamId();
/*     */ 
/*     */       
/* 335 */       if (spdyHeadersFrame.isInvalid()) {
/* 336 */         issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/* 340 */       if (this.spdySession.isRemoteSideClosed(streamId)) {
/* 341 */         issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 346 */       if (spdyHeadersFrame.isLast()) {
/* 347 */         halfCloseStream(streamId, true, ctx.newSucceededFuture());
/*     */       }
/*     */     }
/* 350 */     else if (msg instanceof SpdyWindowUpdateFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 362 */       if (this.flowControl) {
/* 363 */         SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)msg;
/* 364 */         int streamId = spdyWindowUpdateFrame.getStreamId();
/* 365 */         int deltaWindowSize = spdyWindowUpdateFrame.getDeltaWindowSize();
/*     */ 
/*     */         
/* 368 */         if (this.spdySession.isLocalSideClosed(streamId)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 373 */         if (this.spdySession.getSendWindowSize(streamId) > Integer.MAX_VALUE - deltaWindowSize) {
/* 374 */           issueStreamError(ctx, streamId, SpdyStreamStatus.FLOW_CONTROL_ERROR);
/*     */           
/*     */           return;
/*     */         } 
/* 378 */         updateSendWindowSize(ctx, streamId, deltaWindowSize);
/*     */       } 
/*     */     } 
/*     */     
/* 382 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 387 */     for (Integer streamId : this.spdySession.getActiveStreams()) {
/* 388 */       removeStream(streamId.intValue(), ctx.newSucceededFuture());
/*     */     }
/* 390 */     ctx.fireChannelInactive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 395 */     if (cause instanceof SpdyProtocolException) {
/* 396 */       issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */     }
/*     */     
/* 399 */     ctx.fireExceptionCaught(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 404 */     sendGoAwayFrame(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 409 */     if (msg instanceof SpdyDataFrame || msg instanceof SpdySynStreamFrame || msg instanceof SpdySynReplyFrame || msg instanceof SpdyRstStreamFrame || msg instanceof SpdySettingsFrame || msg instanceof SpdyPingFrame || msg instanceof SpdyGoAwayFrame || msg instanceof SpdyHeadersFrame || msg instanceof SpdyWindowUpdateFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 419 */       handleOutboundMessage(ctx, msg, promise);
/*     */     } else {
/* 421 */       ctx.write(msg, promise);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleOutboundMessage(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 426 */     if (msg instanceof SpdyDataFrame) {
/*     */       
/* 428 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/* 429 */       final int streamId = spdyDataFrame.getStreamId();
/*     */ 
/*     */       
/* 432 */       if (this.spdySession.isLocalSideClosed(streamId)) {
/* 433 */         spdyDataFrame.release();
/* 434 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 451 */       if (this.flowControl) {
/* 452 */         synchronized (this.flowControlLock) {
/* 453 */           int dataLength = spdyDataFrame.content().readableBytes();
/* 454 */           int sendWindowSize = this.spdySession.getSendWindowSize(streamId);
/*     */           
/* 456 */           if (sendWindowSize <= 0) {
/*     */             
/* 458 */             this.spdySession.putPendingWrite(streamId, new SpdySession.PendingWrite(spdyDataFrame, promise)); return;
/*     */           } 
/* 460 */           if (sendWindowSize < dataLength) {
/*     */             
/* 462 */             this.spdySession.updateSendWindowSize(streamId, -1 * sendWindowSize);
/*     */ 
/*     */             
/* 465 */             SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readSlice(sendWindowSize).retain());
/*     */ 
/*     */ 
/*     */             
/* 469 */             this.spdySession.putPendingWrite(streamId, new SpdySession.PendingWrite(spdyDataFrame, promise));
/*     */ 
/*     */ 
/*     */             
/* 473 */             final ChannelHandlerContext context = ctx;
/* 474 */             ctx.write(partialDataFrame).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */                 {
/*     */                   public void operationComplete(ChannelFuture future) throws Exception {
/* 477 */                     if (!future.isSuccess()) {
/* 478 */                       SpdySessionHandler.this.issueStreamError(context, streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */                     }
/*     */                   }
/*     */                 });
/*     */             
/*     */             return;
/*     */           } 
/* 485 */           this.spdySession.updateSendWindowSize(streamId, -1 * dataLength);
/*     */ 
/*     */ 
/*     */           
/* 489 */           final ChannelHandlerContext context = ctx;
/* 490 */           promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 493 */                   if (!future.isSuccess()) {
/* 494 */                     SpdySessionHandler.this.issueStreamError(context, streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */                   }
/*     */                 }
/*     */               });
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 503 */       if (spdyDataFrame.isLast()) {
/* 504 */         halfCloseStream(streamId, false, (ChannelFuture)promise);
/*     */       }
/*     */     }
/* 507 */     else if (msg instanceof SpdySynStreamFrame) {
/*     */       
/* 509 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 510 */       final int streamId = spdySynStreamFrame.getStreamId();
/*     */       
/* 512 */       if (isRemoteInitiatedID(streamId)) {
/* 513 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/* 517 */       byte priority = spdySynStreamFrame.getPriority();
/* 518 */       boolean remoteSideClosed = spdySynStreamFrame.isUnidirectional();
/* 519 */       boolean localSideClosed = spdySynStreamFrame.isLast();
/* 520 */       if (!acceptStream(streamId, priority, remoteSideClosed, localSideClosed)) {
/* 521 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/* 525 */     } else if (msg instanceof SpdySynReplyFrame) {
/*     */       
/* 527 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 528 */       final int streamId = spdySynReplyFrame.getStreamId();
/*     */ 
/*     */       
/* 531 */       if (!isRemoteInitiatedID(streamId) || this.spdySession.isLocalSideClosed(streamId)) {
/* 532 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 537 */       if (spdySynReplyFrame.isLast()) {
/* 538 */         halfCloseStream(streamId, false, (ChannelFuture)promise);
/*     */       }
/*     */     }
/* 541 */     else if (msg instanceof SpdyRstStreamFrame) {
/*     */       
/* 543 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 544 */       removeStream(spdyRstStreamFrame.getStreamId(), (ChannelFuture)promise);
/*     */     }
/* 546 */     else if (msg instanceof SpdySettingsFrame) {
/*     */       
/* 548 */       SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
/*     */       
/* 550 */       int newConcurrentStreams = spdySettingsFrame.getValue(4);
/*     */       
/* 552 */       if (newConcurrentStreams >= 0) {
/* 553 */         updateConcurrentStreams(newConcurrentStreams, false);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 559 */       if (spdySettingsFrame.isPersisted(7)) {
/* 560 */         spdySettingsFrame.removeValue(7);
/*     */       }
/* 562 */       spdySettingsFrame.setPersistValue(7, false);
/*     */       
/* 564 */       if (this.flowControl) {
/* 565 */         int newInitialWindowSize = spdySettingsFrame.getValue(7);
/*     */         
/* 567 */         if (newInitialWindowSize >= 0) {
/* 568 */           updateInitialReceiveWindowSize(newInitialWindowSize);
/*     */         }
/*     */       }
/*     */     
/* 572 */     } else if (msg instanceof SpdyPingFrame) {
/*     */       
/* 574 */       SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
/* 575 */       if (isRemoteInitiatedID(spdyPingFrame.getId())) {
/* 576 */         ctx.fireExceptionCaught(new IllegalArgumentException("invalid PING ID: " + spdyPingFrame.getId()));
/*     */         
/*     */         return;
/*     */       } 
/* 580 */       this.pings.getAndIncrement();
/*     */     } else {
/* 582 */       if (msg instanceof SpdyGoAwayFrame) {
/*     */ 
/*     */ 
/*     */         
/* 586 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         return;
/*     */       } 
/* 589 */       if (msg instanceof SpdyHeadersFrame) {
/*     */         
/* 591 */         SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 592 */         final int streamId = spdyHeadersFrame.getStreamId();
/*     */ 
/*     */         
/* 595 */         if (this.spdySession.isLocalSideClosed(streamId)) {
/* 596 */           promise.setFailure(PROTOCOL_EXCEPTION);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 601 */         if (spdyHeadersFrame.isLast()) {
/* 602 */           halfCloseStream(streamId, false, (ChannelFuture)promise);
/*     */         }
/*     */       }
/* 605 */       else if (msg instanceof SpdyWindowUpdateFrame) {
/*     */ 
/*     */         
/* 608 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         return;
/*     */       } 
/*     */     } 
/* 612 */     ctx.write(msg, promise);
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
/*     */   private void issueSessionError(ChannelHandlerContext ctx, SpdySessionStatus status) {
/* 627 */     sendGoAwayFrame(ctx, status).addListener((GenericFutureListener)new ClosingChannelFutureListener(ctx, ctx.newPromise()));
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
/*     */   private void issueStreamError(ChannelHandlerContext ctx, int streamId, SpdyStreamStatus status) {
/* 642 */     boolean fireChannelRead = !this.spdySession.isRemoteSideClosed(streamId);
/* 643 */     ChannelPromise promise = ctx.newPromise();
/* 644 */     removeStream(streamId, (ChannelFuture)promise);
/*     */     
/* 646 */     SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, status);
/* 647 */     ctx.writeAndFlush(spdyRstStreamFrame, promise);
/* 648 */     if (fireChannelRead) {
/* 649 */       ctx.fireChannelRead(spdyRstStreamFrame);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRemoteInitiatedID(int id) {
/* 658 */     boolean serverId = SpdyCodecUtil.isServerId(id);
/* 659 */     return ((this.server && !serverId) || (!this.server && serverId));
/*     */   }
/*     */   
/*     */   private void updateConcurrentStreams(int newConcurrentStreams, boolean remote) {
/* 663 */     if (remote) {
/* 664 */       this.remoteConcurrentStreams = newConcurrentStreams;
/*     */     } else {
/* 666 */       this.localConcurrentStreams = newConcurrentStreams;
/*     */     } 
/* 668 */     this.maxConcurrentStreams = Math.min(this.localConcurrentStreams, this.remoteConcurrentStreams);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void updateInitialSendWindowSize(int newInitialWindowSize) {
/* 673 */     int deltaWindowSize = newInitialWindowSize - this.initialSendWindowSize;
/* 674 */     this.initialSendWindowSize = newInitialWindowSize;
/* 675 */     this.spdySession.updateAllSendWindowSizes(deltaWindowSize);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void updateInitialReceiveWindowSize(int newInitialWindowSize) {
/* 680 */     int deltaWindowSize = newInitialWindowSize - this.initialReceiveWindowSize;
/* 681 */     this.initialReceiveWindowSize = newInitialWindowSize;
/* 682 */     this.spdySession.updateAllReceiveWindowSizes(deltaWindowSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized boolean acceptStream(int streamId, byte priority, boolean remoteSideClosed, boolean localSideClosed) {
/* 689 */     if (this.receivedGoAwayFrame || this.sentGoAwayFrame) {
/* 690 */       return false;
/*     */     }
/*     */     
/* 693 */     if (this.spdySession.numActiveStreams() >= this.maxConcurrentStreams) {
/* 694 */       return false;
/*     */     }
/* 696 */     this.spdySession.acceptStream(streamId, priority, remoteSideClosed, localSideClosed, this.initialSendWindowSize, this.initialReceiveWindowSize);
/*     */ 
/*     */     
/* 699 */     if (isRemoteInitiatedID(streamId)) {
/* 700 */       this.lastGoodStreamId = streamId;
/*     */     }
/* 702 */     return true;
/*     */   }
/*     */   
/*     */   private void halfCloseStream(int streamId, boolean remote, ChannelFuture future) {
/* 706 */     if (remote) {
/* 707 */       this.spdySession.closeRemoteSide(streamId);
/*     */     } else {
/* 709 */       this.spdySession.closeLocalSide(streamId);
/*     */     } 
/* 711 */     if (this.closeSessionFutureListener != null && this.spdySession.noActiveStreams()) {
/* 712 */       future.addListener((GenericFutureListener)this.closeSessionFutureListener);
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeStream(int streamId, ChannelFuture future) {
/* 717 */     this.spdySession.removeStream(streamId, STREAM_CLOSED);
/*     */     
/* 719 */     if (this.closeSessionFutureListener != null && this.spdySession.noActiveStreams()) {
/* 720 */       future.addListener((GenericFutureListener)this.closeSessionFutureListener);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateSendWindowSize(final ChannelHandlerContext ctx, final int streamId, int deltaWindowSize) {
/* 725 */     synchronized (this.flowControlLock) {
/* 726 */       int newWindowSize = this.spdySession.updateSendWindowSize(streamId, deltaWindowSize);
/*     */       
/* 728 */       while (newWindowSize > 0) {
/*     */         
/* 730 */         SpdySession.PendingWrite pendingWrite = this.spdySession.getPendingWrite(streamId);
/* 731 */         if (pendingWrite == null) {
/*     */           break;
/*     */         }
/*     */         
/* 735 */         SpdyDataFrame spdyDataFrame = pendingWrite.spdyDataFrame;
/* 736 */         int dataFrameSize = spdyDataFrame.content().readableBytes();
/*     */         
/* 738 */         if (newWindowSize >= dataFrameSize) {
/*     */           
/* 740 */           this.spdySession.removePendingWrite(streamId);
/* 741 */           newWindowSize = this.spdySession.updateSendWindowSize(streamId, -1 * dataFrameSize);
/*     */ 
/*     */           
/* 744 */           if (spdyDataFrame.isLast()) {
/* 745 */             halfCloseStream(streamId, false, (ChannelFuture)pendingWrite.promise);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 750 */           ctx.writeAndFlush(spdyDataFrame, pendingWrite.promise).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 753 */                   if (!future.isSuccess()) {
/* 754 */                     SpdySessionHandler.this.issueStreamError(ctx, streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */                   }
/*     */                 }
/*     */               });
/*     */           continue;
/*     */         } 
/* 760 */         this.spdySession.updateSendWindowSize(streamId, -1 * newWindowSize);
/*     */ 
/*     */         
/* 763 */         SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readSlice(newWindowSize).retain());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 768 */         ctx.writeAndFlush(partialDataFrame).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/* 771 */                 if (!future.isSuccess()) {
/* 772 */                   SpdySessionHandler.this.issueStreamError(ctx, streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */                 }
/*     */               }
/*     */             });
/*     */         
/* 777 */         newWindowSize = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendGoAwayFrame(ChannelHandlerContext ctx, ChannelPromise future) {
/* 785 */     if (!ctx.channel().isActive()) {
/* 786 */       ctx.close(future);
/*     */       
/*     */       return;
/*     */     } 
/* 790 */     ChannelFuture f = sendGoAwayFrame(ctx, SpdySessionStatus.OK);
/* 791 */     if (this.spdySession.noActiveStreams()) {
/* 792 */       f.addListener((GenericFutureListener)new ClosingChannelFutureListener(ctx, future));
/*     */     } else {
/* 794 */       this.closeSessionFutureListener = new ClosingChannelFutureListener(ctx, future);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized ChannelFuture sendGoAwayFrame(ChannelHandlerContext ctx, SpdySessionStatus status) {
/* 801 */     if (!this.sentGoAwayFrame) {
/* 802 */       this.sentGoAwayFrame = true;
/* 803 */       SpdyGoAwayFrame spdyGoAwayFrame = new DefaultSpdyGoAwayFrame(this.lastGoodStreamId, status);
/* 804 */       return ctx.writeAndFlush(spdyGoAwayFrame);
/*     */     } 
/* 806 */     return ctx.newSucceededFuture();
/*     */   }
/*     */   
/*     */   private static final class ClosingChannelFutureListener
/*     */     implements ChannelFutureListener {
/*     */     private final ChannelHandlerContext ctx;
/*     */     private final ChannelPromise promise;
/*     */     
/*     */     ClosingChannelFutureListener(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 815 */       this.ctx = ctx;
/* 816 */       this.promise = promise;
/*     */     }
/*     */ 
/*     */     
/*     */     public void operationComplete(ChannelFuture sentGoAwayFuture) throws Exception {
/* 821 */       this.ctx.close(this.promise);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdySessionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */