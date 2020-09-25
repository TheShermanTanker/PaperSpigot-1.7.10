/*     */ package net.minecraft.util.io.netty.handler.codec;
/*     */ 
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LengthFieldBasedFrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final ByteOrder byteOrder;
/*     */   private final int maxFrameLength;
/*     */   private final int lengthFieldOffset;
/*     */   private final int lengthFieldLength;
/*     */   private final int lengthFieldEndOffset;
/*     */   private final int lengthAdjustment;
/*     */   private final int initialBytesToStrip;
/*     */   private final boolean failFast;
/*     */   private boolean discardingTooLongFrame;
/*     */   private long tooLongFrameLength;
/*     */   private long bytesToDiscard;
/*     */   
/*     */   public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
/* 213 */     this(maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0);
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
/*     */   public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
/* 236 */     this(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, true);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
/* 268 */     this(ByteOrder.BIG_ENDIAN, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LengthFieldBasedFrameDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
/* 301 */     if (byteOrder == null) {
/* 302 */       throw new NullPointerException("byteOrder");
/*     */     }
/*     */     
/* 305 */     if (maxFrameLength <= 0) {
/* 306 */       throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + maxFrameLength);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 311 */     if (lengthFieldOffset < 0) {
/* 312 */       throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + lengthFieldOffset);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 317 */     if (initialBytesToStrip < 0) {
/* 318 */       throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + initialBytesToStrip);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 323 */     if (lengthFieldLength != 1 && lengthFieldLength != 2 && lengthFieldLength != 3 && lengthFieldLength != 4 && lengthFieldLength != 8)
/*     */     {
/*     */       
/* 326 */       throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + lengthFieldLength);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 331 */     if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
/* 332 */       throw new IllegalArgumentException("maxFrameLength (" + maxFrameLength + ") " + "must be equal to or greater than " + "lengthFieldOffset (" + lengthFieldOffset + ") + " + "lengthFieldLength (" + lengthFieldLength + ").");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     this.byteOrder = byteOrder;
/* 340 */     this.maxFrameLength = maxFrameLength;
/* 341 */     this.lengthFieldOffset = lengthFieldOffset;
/* 342 */     this.lengthFieldLength = lengthFieldLength;
/* 343 */     this.lengthAdjustment = lengthAdjustment;
/* 344 */     this.lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
/* 345 */     this.initialBytesToStrip = initialBytesToStrip;
/* 346 */     this.failFast = failFast;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 351 */     Object decoded = decode(ctx, in);
/* 352 */     if (decoded != null) {
/* 353 */       out.add(decoded);
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
/*     */   protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
/* 366 */     if (this.discardingTooLongFrame) {
/* 367 */       long bytesToDiscard = this.bytesToDiscard;
/* 368 */       int localBytesToDiscard = (int)Math.min(bytesToDiscard, in.readableBytes());
/* 369 */       in.skipBytes(localBytesToDiscard);
/* 370 */       bytesToDiscard -= localBytesToDiscard;
/* 371 */       this.bytesToDiscard = bytesToDiscard;
/*     */       
/* 373 */       failIfNecessary(false);
/*     */     } 
/*     */     
/* 376 */     if (in.readableBytes() < this.lengthFieldEndOffset) {
/* 377 */       return null;
/*     */     }
/*     */     
/* 380 */     int actualLengthFieldOffset = in.readerIndex() + this.lengthFieldOffset;
/* 381 */     long frameLength = getFrameLength(in, actualLengthFieldOffset);
/*     */     
/* 383 */     if (frameLength < 0L) {
/* 384 */       in.skipBytes(this.lengthFieldEndOffset);
/* 385 */       throw new CorruptedFrameException("negative pre-adjustment length field: " + frameLength);
/*     */     } 
/*     */ 
/*     */     
/* 389 */     frameLength += (this.lengthAdjustment + this.lengthFieldEndOffset);
/*     */     
/* 391 */     if (frameLength < this.lengthFieldEndOffset) {
/* 392 */       in.skipBytes(this.lengthFieldEndOffset);
/* 393 */       throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less " + "than lengthFieldEndOffset: " + this.lengthFieldEndOffset);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 398 */     if (frameLength > this.maxFrameLength) {
/* 399 */       long discard = frameLength - in.readableBytes();
/* 400 */       this.tooLongFrameLength = frameLength;
/*     */       
/* 402 */       if (discard < 0L) {
/*     */         
/* 404 */         in.skipBytes((int)frameLength);
/*     */       } else {
/*     */         
/* 407 */         this.discardingTooLongFrame = true;
/* 408 */         this.bytesToDiscard = discard;
/* 409 */         in.skipBytes(in.readableBytes());
/*     */       } 
/* 411 */       failIfNecessary(true);
/* 412 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 416 */     int frameLengthInt = (int)frameLength;
/* 417 */     if (in.readableBytes() < frameLengthInt) {
/* 418 */       return null;
/*     */     }
/*     */     
/* 421 */     if (this.initialBytesToStrip > frameLengthInt) {
/* 422 */       in.skipBytes(frameLengthInt);
/* 423 */       throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less " + "than initialBytesToStrip: " + this.initialBytesToStrip);
/*     */     } 
/*     */ 
/*     */     
/* 427 */     in.skipBytes(this.initialBytesToStrip);
/*     */ 
/*     */     
/* 430 */     int readerIndex = in.readerIndex();
/* 431 */     int actualFrameLength = frameLengthInt - this.initialBytesToStrip;
/* 432 */     ByteBuf frame = extractFrame(ctx, in, readerIndex, actualFrameLength);
/* 433 */     in.readerIndex(readerIndex + actualFrameLength);
/* 434 */     return frame;
/*     */   }
/*     */   private long getFrameLength(ByteBuf in, int actualLengthFieldOffset) {
/*     */     long frameLength;
/* 438 */     in = in.order(this.byteOrder);
/*     */     
/* 440 */     switch (this.lengthFieldLength) {
/*     */       case 1:
/* 442 */         frameLength = in.getUnsignedByte(actualLengthFieldOffset);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 459 */         return frameLength;case 2: frameLength = in.getUnsignedShort(actualLengthFieldOffset); return frameLength;case 3: frameLength = in.getUnsignedMedium(actualLengthFieldOffset); return frameLength;case 4: frameLength = in.getUnsignedInt(actualLengthFieldOffset); return frameLength;case 8: frameLength = in.getLong(actualLengthFieldOffset); return frameLength;
/*     */     } 
/*     */     throw new Error("should not reach here");
/*     */   } private void failIfNecessary(boolean firstDetectionOfTooLongFrame) {
/* 463 */     if (this.bytesToDiscard == 0L) {
/*     */ 
/*     */       
/* 466 */       long tooLongFrameLength = this.tooLongFrameLength;
/* 467 */       this.tooLongFrameLength = 0L;
/* 468 */       this.discardingTooLongFrame = false;
/* 469 */       if (!this.failFast || (this.failFast && firstDetectionOfTooLongFrame))
/*     */       {
/* 471 */         fail(tooLongFrameLength);
/*     */       
/*     */       }
/*     */     }
/* 475 */     else if (this.failFast && firstDetectionOfTooLongFrame) {
/* 476 */       fail(this.tooLongFrameLength);
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
/*     */   
/*     */   protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
/* 493 */     ByteBuf frame = ctx.alloc().buffer(length);
/* 494 */     frame.writeBytes(buffer, index, length);
/* 495 */     return frame;
/*     */   }
/*     */   
/*     */   private void fail(long frameLength) {
/* 499 */     if (frameLength > 0L) {
/* 500 */       throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded");
/*     */     }
/*     */ 
/*     */     
/* 504 */     throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + " - discarding");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\LengthFieldBasedFrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */