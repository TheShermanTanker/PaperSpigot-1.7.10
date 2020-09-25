/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.CorruptedFrameException;
/*     */ import net.minecraft.util.io.netty.handler.codec.ReplayingDecoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.TooLongFrameException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocket08FrameDecoder
/*     */   extends ReplayingDecoder<WebSocket08FrameDecoder.State>
/*     */   implements WebSocketFrameDecoder
/*     */ {
/*  75 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameDecoder.class);
/*     */   
/*     */   private static final byte OPCODE_CONT = 0;
/*     */   
/*     */   private static final byte OPCODE_TEXT = 1;
/*     */   
/*     */   private static final byte OPCODE_BINARY = 2;
/*     */   private static final byte OPCODE_CLOSE = 8;
/*     */   private static final byte OPCODE_PING = 9;
/*     */   private static final byte OPCODE_PONG = 10;
/*     */   private UTF8Output fragmentedFramesText;
/*     */   private int fragmentedFramesCount;
/*     */   private final long maxFramePayloadLength;
/*     */   private boolean frameFinalFlag;
/*     */   private int frameRsv;
/*     */   private int frameOpcode;
/*     */   private long framePayloadLength;
/*     */   private ByteBuf framePayload;
/*     */   private int framePayloadBytesRead;
/*     */   private byte[] maskingKey;
/*     */   private ByteBuf payloadBuffer;
/*     */   private final boolean allowExtensions;
/*     */   private final boolean maskedPayload;
/*     */   private boolean receivedClosingHandshake;
/*     */   
/*     */   enum State
/*     */   {
/* 102 */     FRAME_START, MASKING_KEY, PAYLOAD, CORRUPT;
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
/*     */   public WebSocket08FrameDecoder(boolean maskedPayload, boolean allowExtensions, int maxFramePayloadLength) {
/* 118 */     super(State.FRAME_START);
/* 119 */     this.maskedPayload = maskedPayload;
/* 120 */     this.allowExtensions = allowExtensions;
/* 121 */     this.maxFramePayloadLength = maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 128 */     if (this.receivedClosingHandshake) {
/* 129 */       in.skipBytes(actualReadableBytes()); return;
/*     */     }  try {
/*     */       byte b; boolean frameMasked; int framePayloadLen1; int rbytes;
/*     */       long willHaveReadByteCount;
/*     */       String aggregatedText;
/* 134 */       switch ((State)state()) {
/*     */         case FRAME_START:
/* 136 */           this.framePayloadBytesRead = 0;
/* 137 */           this.framePayloadLength = -1L;
/* 138 */           this.framePayload = null;
/* 139 */           this.payloadBuffer = null;
/*     */ 
/*     */           
/* 142 */           b = in.readByte();
/* 143 */           this.frameFinalFlag = ((b & 0x80) != 0);
/* 144 */           this.frameRsv = (b & 0x70) >> 4;
/* 145 */           this.frameOpcode = b & 0xF;
/*     */           
/* 147 */           if (logger.isDebugEnabled()) {
/* 148 */             logger.debug("Decoding WebSocket Frame opCode={}", Integer.valueOf(this.frameOpcode));
/*     */           }
/*     */ 
/*     */           
/* 152 */           b = in.readByte();
/* 153 */           frameMasked = ((b & 0x80) != 0);
/* 154 */           framePayloadLen1 = b & Byte.MAX_VALUE;
/*     */           
/* 156 */           if (this.frameRsv != 0 && !this.allowExtensions) {
/* 157 */             protocolViolation(ctx, "RSV != 0 and no extension negotiated, RSV:" + this.frameRsv);
/*     */             
/*     */             return;
/*     */           } 
/* 161 */           if (this.maskedPayload && !frameMasked) {
/* 162 */             protocolViolation(ctx, "unmasked client to server frame");
/*     */             return;
/*     */           } 
/* 165 */           if (this.frameOpcode > 7) {
/*     */ 
/*     */             
/* 168 */             if (!this.frameFinalFlag) {
/* 169 */               protocolViolation(ctx, "fragmented control frame");
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 174 */             if (framePayloadLen1 > 125) {
/* 175 */               protocolViolation(ctx, "control frame with payload length > 125 octets");
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 180 */             if (this.frameOpcode != 8 && this.frameOpcode != 9 && this.frameOpcode != 10) {
/*     */               
/* 182 */               protocolViolation(ctx, "control frame using reserved opcode " + this.frameOpcode);
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */ 
/*     */             
/* 189 */             if (this.frameOpcode == 8 && framePayloadLen1 == 1) {
/* 190 */               protocolViolation(ctx, "received close control frame with payload len 1");
/*     */               
/*     */               return;
/*     */             } 
/*     */           } else {
/* 195 */             if (this.frameOpcode != 0 && this.frameOpcode != 1 && this.frameOpcode != 2) {
/*     */               
/* 197 */               protocolViolation(ctx, "data frame using reserved opcode " + this.frameOpcode);
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 202 */             if (this.fragmentedFramesCount == 0 && this.frameOpcode == 0) {
/* 203 */               protocolViolation(ctx, "received continuation data frame outside fragmented message");
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 208 */             if (this.fragmentedFramesCount != 0 && this.frameOpcode != 0 && this.frameOpcode != 9) {
/* 209 */               protocolViolation(ctx, "received non-continuation data frame while inside fragmented message");
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*     */           
/* 216 */           if (framePayloadLen1 == 126) {
/* 217 */             this.framePayloadLength = in.readUnsignedShort();
/* 218 */             if (this.framePayloadLength < 126L) {
/* 219 */               protocolViolation(ctx, "invalid data frame length (not using minimal length encoding)");
/*     */               return;
/*     */             } 
/* 222 */           } else if (framePayloadLen1 == 127) {
/* 223 */             this.framePayloadLength = in.readLong();
/*     */ 
/*     */ 
/*     */             
/* 227 */             if (this.framePayloadLength < 65536L) {
/* 228 */               protocolViolation(ctx, "invalid data frame length (not using minimal length encoding)");
/*     */               return;
/*     */             } 
/*     */           } else {
/* 232 */             this.framePayloadLength = framePayloadLen1;
/*     */           } 
/*     */           
/* 235 */           if (this.framePayloadLength > this.maxFramePayloadLength) {
/* 236 */             protocolViolation(ctx, "Max frame length of " + this.maxFramePayloadLength + " has been exceeded.");
/*     */             
/*     */             return;
/*     */           } 
/* 240 */           if (logger.isDebugEnabled()) {
/* 241 */             logger.debug("Decoding WebSocket Frame length={}", Long.valueOf(this.framePayloadLength));
/*     */           }
/*     */           
/* 244 */           checkpoint(State.MASKING_KEY);
/*     */         case MASKING_KEY:
/* 246 */           if (this.maskedPayload) {
/* 247 */             if (this.maskingKey == null) {
/* 248 */               this.maskingKey = new byte[4];
/*     */             }
/* 250 */             in.readBytes(this.maskingKey);
/*     */           } 
/* 252 */           checkpoint(State.PAYLOAD);
/*     */ 
/*     */         
/*     */         case PAYLOAD:
/* 256 */           rbytes = actualReadableBytes();
/*     */           
/* 258 */           willHaveReadByteCount = (this.framePayloadBytesRead + rbytes);
/*     */ 
/*     */ 
/*     */           
/* 262 */           if (willHaveReadByteCount == this.framePayloadLength)
/*     */           
/* 264 */           { this.payloadBuffer = ctx.alloc().buffer(rbytes);
/* 265 */             this.payloadBuffer.writeBytes(in, rbytes); }
/* 266 */           else { if (willHaveReadByteCount < this.framePayloadLength) {
/*     */ 
/*     */ 
/*     */               
/* 270 */               if (this.framePayload == null) {
/* 271 */                 this.framePayload = ctx.alloc().buffer(toFrameLength(this.framePayloadLength));
/*     */               }
/* 273 */               this.framePayload.writeBytes(in, rbytes);
/* 274 */               this.framePayloadBytesRead += rbytes;
/*     */               
/*     */               return;
/*     */             } 
/* 278 */             if (willHaveReadByteCount > this.framePayloadLength) {
/*     */ 
/*     */               
/* 281 */               if (this.framePayload == null) {
/* 282 */                 this.framePayload = ctx.alloc().buffer(toFrameLength(this.framePayloadLength));
/*     */               }
/* 284 */               this.framePayload.writeBytes(in, toFrameLength(this.framePayloadLength - this.framePayloadBytesRead));
/*     */             }  }
/*     */ 
/*     */ 
/*     */           
/* 289 */           checkpoint(State.FRAME_START);
/*     */ 
/*     */           
/* 292 */           if (this.framePayload == null) {
/* 293 */             this.framePayload = this.payloadBuffer;
/* 294 */             this.payloadBuffer = null;
/* 295 */           } else if (this.payloadBuffer != null) {
/* 296 */             this.framePayload.writeBytes(this.payloadBuffer);
/* 297 */             this.payloadBuffer.release();
/* 298 */             this.payloadBuffer = null;
/*     */           } 
/*     */ 
/*     */           
/* 302 */           if (this.maskedPayload) {
/* 303 */             unmask(this.framePayload);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 308 */           if (this.frameOpcode == 9) {
/* 309 */             out.add(new PingWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 310 */             this.framePayload = null;
/*     */             return;
/*     */           } 
/* 313 */           if (this.frameOpcode == 10) {
/* 314 */             out.add(new PongWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 315 */             this.framePayload = null;
/*     */             return;
/*     */           } 
/* 318 */           if (this.frameOpcode == 8) {
/* 319 */             checkCloseFrameBody(ctx, this.framePayload);
/* 320 */             this.receivedClosingHandshake = true;
/* 321 */             out.add(new CloseWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 322 */             this.framePayload = null;
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 328 */           aggregatedText = null;
/* 329 */           if (this.frameFinalFlag) {
/*     */ 
/*     */             
/* 332 */             if (this.frameOpcode != 9) {
/* 333 */               this.fragmentedFramesCount = 0;
/*     */ 
/*     */               
/* 336 */               if (this.frameOpcode == 1 || this.fragmentedFramesText != null)
/*     */               {
/* 338 */                 checkUTF8String(ctx, this.framePayload);
/*     */ 
/*     */ 
/*     */                 
/* 342 */                 aggregatedText = this.fragmentedFramesText.toString();
/*     */                 
/* 344 */                 this.fragmentedFramesText = null;
/*     */               }
/*     */             
/*     */             } 
/*     */           } else {
/*     */             
/* 350 */             if (this.fragmentedFramesCount == 0) {
/*     */               
/* 352 */               this.fragmentedFramesText = null;
/* 353 */               if (this.frameOpcode == 1) {
/* 354 */                 checkUTF8String(ctx, this.framePayload);
/*     */               
/*     */               }
/*     */             }
/* 358 */             else if (this.fragmentedFramesText != null) {
/* 359 */               checkUTF8String(ctx, this.framePayload);
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 364 */             this.fragmentedFramesCount++;
/*     */           } 
/*     */ 
/*     */           
/* 368 */           if (this.frameOpcode == 1) {
/* 369 */             out.add(new TextWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 370 */             this.framePayload = null; return;
/*     */           } 
/* 372 */           if (this.frameOpcode == 2) {
/* 373 */             out.add(new BinaryWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 374 */             this.framePayload = null; return;
/*     */           } 
/* 376 */           if (this.frameOpcode == 0) {
/* 377 */             out.add(new ContinuationWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload, aggregatedText));
/* 378 */             this.framePayload = null;
/*     */             return;
/*     */           } 
/* 381 */           throw new UnsupportedOperationException("Cannot decode web socket frame with opcode: " + this.frameOpcode);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case CORRUPT:
/* 387 */           in.readByte();
/*     */           return;
/*     */       } 
/* 390 */       throw new Error("Shouldn't reach here.");
/*     */     }
/* 392 */     catch (Exception e) {
/* 393 */       if (this.payloadBuffer != null) {
/* 394 */         if (this.payloadBuffer.refCnt() > 0) {
/* 395 */           this.payloadBuffer.release();
/*     */         }
/* 397 */         this.payloadBuffer = null;
/*     */       } 
/* 399 */       if (this.framePayload != null) {
/* 400 */         if (this.framePayload.refCnt() > 0) {
/* 401 */           this.framePayload.release();
/*     */         }
/* 403 */         this.framePayload = null;
/*     */       } 
/* 405 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void unmask(ByteBuf frame) {
/* 410 */     for (int i = frame.readerIndex(); i < frame.writerIndex(); i++) {
/* 411 */       frame.setByte(i, frame.getByte(i) ^ this.maskingKey[i % 4]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void protocolViolation(ChannelHandlerContext ctx, String reason) {
/* 416 */     checkpoint(State.CORRUPT);
/* 417 */     if (ctx.channel().isActive()) {
/* 418 */       ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */     }
/* 420 */     throw new CorruptedFrameException(reason);
/*     */   }
/*     */   
/*     */   private static int toFrameLength(long l) {
/* 424 */     if (l > 2147483647L) {
/* 425 */       throw new TooLongFrameException("Length:" + l);
/*     */     }
/* 427 */     return (int)l;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkUTF8String(ChannelHandlerContext ctx, ByteBuf buffer) {
/*     */     try {
/* 433 */       if (this.fragmentedFramesText == null) {
/* 434 */         this.fragmentedFramesText = new UTF8Output(buffer);
/*     */       } else {
/* 436 */         this.fragmentedFramesText.write(buffer);
/*     */       } 
/* 438 */     } catch (UTF8Exception ex) {
/* 439 */       protocolViolation(ctx, "invalid UTF-8 bytes");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkCloseFrameBody(ChannelHandlerContext ctx, ByteBuf buffer) {
/* 446 */     if (buffer == null || !buffer.isReadable()) {
/*     */       return;
/*     */     }
/* 449 */     if (buffer.readableBytes() == 1) {
/* 450 */       protocolViolation(ctx, "Invalid close frame body");
/*     */     }
/*     */ 
/*     */     
/* 454 */     int idx = buffer.readerIndex();
/* 455 */     buffer.readerIndex(0);
/*     */ 
/*     */     
/* 458 */     int statusCode = buffer.readShort();
/* 459 */     if ((statusCode >= 0 && statusCode <= 999) || (statusCode >= 1004 && statusCode <= 1006) || (statusCode >= 1012 && statusCode <= 2999))
/*     */     {
/* 461 */       protocolViolation(ctx, "Invalid close frame getStatus code: " + statusCode);
/*     */     }
/*     */ 
/*     */     
/* 465 */     if (buffer.isReadable()) {
/*     */       try {
/* 467 */         new UTF8Output(buffer);
/* 468 */       } catch (UTF8Exception ex) {
/* 469 */         protocolViolation(ctx, "Invalid close frame reason text. Invalid UTF-8 bytes");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 474 */     buffer.readerIndex(idx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 479 */     super.channelInactive(ctx);
/*     */ 
/*     */ 
/*     */     
/* 483 */     if (this.framePayload != null) {
/* 484 */       this.framePayload.release();
/*     */     }
/* 486 */     if (this.payloadBuffer != null)
/* 487 */       this.payloadBuffer.release(); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocket08FrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */