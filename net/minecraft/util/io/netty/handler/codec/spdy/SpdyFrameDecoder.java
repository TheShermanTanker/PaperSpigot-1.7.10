/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.ByteToMessageDecoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpdyFrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*  52 */   private static final SpdyProtocolException INVALID_FRAME = new SpdyProtocolException("Received invalid frame");
/*     */   
/*     */   private final int spdyVersion;
/*     */   
/*     */   private final int maxChunkSize;
/*     */   
/*     */   private final SpdyHeaderBlockDecoder headerBlockDecoder;
/*     */   
/*     */   private State state;
/*     */   
/*     */   private SpdySettingsFrame spdySettingsFrame;
/*     */   private SpdyHeadersFrame spdyHeadersFrame;
/*     */   private byte flags;
/*     */   private int length;
/*     */   private int version;
/*     */   private int type;
/*     */   private int streamId;
/*     */   
/*     */   private enum State
/*     */   {
/*  72 */     READ_COMMON_HEADER,
/*  73 */     READ_CONTROL_FRAME,
/*  74 */     READ_SETTINGS_FRAME,
/*  75 */     READ_HEADER_BLOCK_FRAME,
/*  76 */     READ_HEADER_BLOCK,
/*  77 */     READ_DATA_FRAME,
/*  78 */     DISCARD_FRAME,
/*  79 */     FRAME_ERROR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdyFrameDecoder(int version) {
/*  87 */     this(version, 8192, 16384);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdyFrameDecoder(int version, int maxChunkSize, int maxHeaderSize) {
/*  94 */     this(version, maxChunkSize, SpdyHeaderBlockDecoder.newInstance(version, maxHeaderSize));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SpdyFrameDecoder(int version, int maxChunkSize, SpdyHeaderBlockDecoder headerBlockDecoder) {
/*  99 */     if (version < 2 || version > 3) {
/* 100 */       throw new IllegalArgumentException("unsupported version: " + version);
/*     */     }
/*     */     
/* 103 */     if (maxChunkSize <= 0) {
/* 104 */       throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize);
/*     */     }
/*     */     
/* 107 */     this.spdyVersion = version;
/* 108 */     this.maxChunkSize = maxChunkSize;
/* 109 */     this.headerBlockDecoder = headerBlockDecoder;
/* 110 */     this.state = State.READ_COMMON_HEADER;
/*     */   }
/*     */ 
/*     */   
/*     */   public void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*     */     try {
/* 116 */       decode(ctx, in, out);
/*     */     } finally {
/* 118 */       this.headerBlockDecoder.end();
/*     */     }  } protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception { int readableEntries; int i; int compressedBytes; ByteBuf compressed; int readBytes;
/*     */     int dataLength;
/*     */     ByteBuf data;
/*     */     SpdyDataFrame spdyDataFrame;
/*     */     int numBytes;
/* 124 */     switch (this.state) {
/*     */       case READ_COMMON_HEADER:
/* 126 */         this.state = readCommonHeader(buffer);
/* 127 */         if (this.state == State.FRAME_ERROR) {
/* 128 */           if (this.version != this.spdyVersion) {
/* 129 */             fireProtocolException(ctx, "Unsupported version: " + this.version);
/*     */           } else {
/* 131 */             fireInvalidFrameException(ctx);
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 137 */         if (this.length == 0) {
/* 138 */           if (this.state == State.READ_DATA_FRAME) {
/* 139 */             SpdyDataFrame spdyDataFrame1 = new DefaultSpdyDataFrame(this.streamId);
/* 140 */             spdyDataFrame1.setLast(((this.flags & 0x1) != 0));
/* 141 */             this.state = State.READ_COMMON_HEADER;
/* 142 */             out.add(spdyDataFrame1);
/*     */             
/*     */             return;
/*     */           } 
/* 146 */           this.state = State.READ_COMMON_HEADER;
/*     */         } 
/*     */         return;
/*     */ 
/*     */       
/*     */       case READ_CONTROL_FRAME:
/*     */         try {
/* 153 */           Object frame = readControlFrame(buffer);
/* 154 */           if (frame != null) {
/* 155 */             this.state = State.READ_COMMON_HEADER;
/* 156 */             out.add(frame);
/*     */           } 
/*     */           return;
/* 159 */         } catch (IllegalArgumentException e) {
/* 160 */           this.state = State.FRAME_ERROR;
/* 161 */           fireInvalidFrameException(ctx);
/*     */           return;
/*     */         } 
/*     */       
/*     */       case READ_SETTINGS_FRAME:
/* 166 */         if (this.spdySettingsFrame == null) {
/*     */           
/* 168 */           if (buffer.readableBytes() < 4) {
/*     */             return;
/*     */           }
/* 171 */           int numEntries = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 172 */           buffer.skipBytes(4);
/* 173 */           this.length -= 4;
/*     */ 
/*     */           
/* 176 */           if ((this.length & 0x7) != 0 || this.length >> 3 != numEntries) {
/* 177 */             this.state = State.FRAME_ERROR;
/* 178 */             fireInvalidFrameException(ctx);
/*     */             
/*     */             return;
/*     */           } 
/* 182 */           this.spdySettingsFrame = new DefaultSpdySettingsFrame();
/*     */           
/* 184 */           boolean clear = ((this.flags & 0x1) != 0);
/* 185 */           this.spdySettingsFrame.setClearPreviouslyPersistedSettings(clear);
/*     */         } 
/*     */         
/* 188 */         readableEntries = Math.min(buffer.readableBytes() >> 3, this.length >> 3);
/* 189 */         for (i = 0; i < readableEntries; i++) {
/*     */           int ID;
/*     */           byte ID_flags;
/* 192 */           if (this.version < 3) {
/*     */ 
/*     */ 
/*     */             
/* 196 */             ID = buffer.readByte() & 0xFF | (buffer.readByte() & 0xFF) << 8 | (buffer.readByte() & 0xFF) << 16;
/*     */ 
/*     */             
/* 199 */             ID_flags = buffer.readByte();
/*     */           } else {
/* 201 */             ID_flags = buffer.readByte();
/* 202 */             ID = SpdyCodecUtil.getUnsignedMedium(buffer, buffer.readerIndex());
/* 203 */             buffer.skipBytes(3);
/*     */           } 
/* 205 */           int value = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
/* 206 */           buffer.skipBytes(4);
/*     */ 
/*     */           
/* 209 */           if (ID == 0) {
/* 210 */             this.state = State.FRAME_ERROR;
/* 211 */             this.spdySettingsFrame = null;
/* 212 */             fireInvalidFrameException(ctx);
/*     */             
/*     */             return;
/*     */           } 
/* 216 */           if (!this.spdySettingsFrame.isSet(ID)) {
/* 217 */             boolean persistVal = ((ID_flags & 0x1) != 0);
/* 218 */             boolean persisted = ((ID_flags & 0x2) != 0);
/* 219 */             this.spdySettingsFrame.setValue(ID, value, persistVal, persisted);
/*     */           } 
/*     */         } 
/*     */         
/* 223 */         this.length -= 8 * readableEntries;
/* 224 */         if (this.length == 0) {
/* 225 */           this.state = State.READ_COMMON_HEADER;
/* 226 */           Object frame = this.spdySettingsFrame;
/* 227 */           this.spdySettingsFrame = null;
/* 228 */           out.add(frame);
/*     */           return;
/*     */         } 
/*     */         return;
/*     */       
/*     */       case READ_HEADER_BLOCK_FRAME:
/*     */         try {
/* 235 */           this.spdyHeadersFrame = readHeaderBlockFrame(buffer);
/* 236 */           if (this.spdyHeadersFrame != null) {
/* 237 */             if (this.length == 0) {
/* 238 */               this.state = State.READ_COMMON_HEADER;
/* 239 */               Object frame = this.spdyHeadersFrame;
/* 240 */               this.spdyHeadersFrame = null;
/* 241 */               out.add(frame);
/*     */               return;
/*     */             } 
/* 244 */             this.state = State.READ_HEADER_BLOCK;
/*     */           } 
/*     */           return;
/* 247 */         } catch (IllegalArgumentException e) {
/* 248 */           this.state = State.FRAME_ERROR;
/* 249 */           fireInvalidFrameException(ctx);
/*     */           return;
/*     */         } 
/*     */       
/*     */       case READ_HEADER_BLOCK:
/* 254 */         compressedBytes = Math.min(buffer.readableBytes(), this.length);
/* 255 */         compressed = buffer.slice(buffer.readerIndex(), compressedBytes);
/*     */         
/*     */         try {
/* 258 */           this.headerBlockDecoder.decode(compressed, this.spdyHeadersFrame);
/* 259 */         } catch (Exception e) {
/* 260 */           this.state = State.FRAME_ERROR;
/* 261 */           this.spdyHeadersFrame = null;
/* 262 */           ctx.fireExceptionCaught(e);
/*     */           
/*     */           return;
/*     */         } 
/* 266 */         readBytes = compressedBytes - compressed.readableBytes();
/* 267 */         buffer.skipBytes(readBytes);
/* 268 */         this.length -= readBytes;
/*     */         
/* 270 */         if (this.spdyHeadersFrame != null && (this.spdyHeadersFrame.isInvalid() || this.spdyHeadersFrame.isTruncated())) {
/*     */ 
/*     */           
/* 273 */           Object frame = this.spdyHeadersFrame;
/* 274 */           this.spdyHeadersFrame = null;
/* 275 */           if (this.length == 0) {
/* 276 */             this.headerBlockDecoder.reset();
/* 277 */             this.state = State.READ_COMMON_HEADER;
/*     */           } 
/* 279 */           out.add(frame);
/*     */           
/*     */           return;
/*     */         } 
/* 283 */         if (this.length == 0) {
/* 284 */           Object frame = this.spdyHeadersFrame;
/* 285 */           this.spdyHeadersFrame = null;
/* 286 */           this.headerBlockDecoder.reset();
/* 287 */           this.state = State.READ_COMMON_HEADER;
/* 288 */           if (frame != null) {
/* 289 */             out.add(frame);
/*     */           }
/*     */         } 
/*     */         return;
/*     */       
/*     */       case READ_DATA_FRAME:
/* 295 */         if (this.streamId == 0) {
/* 296 */           this.state = State.FRAME_ERROR;
/* 297 */           fireProtocolException(ctx, "Received invalid data frame");
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 302 */         dataLength = Math.min(this.maxChunkSize, this.length);
/*     */ 
/*     */         
/* 305 */         if (buffer.readableBytes() < dataLength) {
/*     */           return;
/*     */         }
/*     */         
/* 309 */         data = ctx.alloc().buffer(dataLength);
/* 310 */         data.writeBytes(buffer, dataLength);
/* 311 */         spdyDataFrame = new DefaultSpdyDataFrame(this.streamId, data);
/* 312 */         this.length -= dataLength;
/*     */         
/* 314 */         if (this.length == 0) {
/* 315 */           spdyDataFrame.setLast(((this.flags & 0x1) != 0));
/* 316 */           this.state = State.READ_COMMON_HEADER;
/*     */         } 
/* 318 */         out.add(spdyDataFrame);
/*     */         return;
/*     */       
/*     */       case DISCARD_FRAME:
/* 322 */         numBytes = Math.min(buffer.readableBytes(), this.length);
/* 323 */         buffer.skipBytes(numBytes);
/* 324 */         this.length -= numBytes;
/* 325 */         if (this.length == 0) {
/* 326 */           this.state = State.READ_COMMON_HEADER;
/*     */         }
/*     */         return;
/*     */       
/*     */       case FRAME_ERROR:
/* 331 */         buffer.skipBytes(buffer.readableBytes());
/*     */         return;
/*     */     } 
/*     */     
/* 335 */     throw new Error("Shouldn't reach here."); }
/*     */ 
/*     */ 
/*     */   
/*     */   private State readCommonHeader(ByteBuf buffer) {
/*     */     State nextState;
/* 341 */     if (buffer.readableBytes() < 8) {
/* 342 */       return State.READ_COMMON_HEADER;
/*     */     }
/*     */     
/* 345 */     int frameOffset = buffer.readerIndex();
/* 346 */     int flagsOffset = frameOffset + 4;
/* 347 */     int lengthOffset = frameOffset + 5;
/* 348 */     buffer.skipBytes(8);
/*     */ 
/*     */     
/* 351 */     boolean control = ((buffer.getByte(frameOffset) & 0x80) != 0);
/* 352 */     this.flags = buffer.getByte(flagsOffset);
/* 353 */     this.length = SpdyCodecUtil.getUnsignedMedium(buffer, lengthOffset);
/*     */     
/* 355 */     if (control) {
/*     */       
/* 357 */       this.version = SpdyCodecUtil.getUnsignedShort(buffer, frameOffset) & 0x7FFF;
/*     */       
/* 359 */       int typeOffset = frameOffset + 2;
/* 360 */       this.type = SpdyCodecUtil.getUnsignedShort(buffer, typeOffset);
/*     */       
/* 362 */       this.streamId = 0;
/*     */     } else {
/*     */       
/* 365 */       this.version = this.spdyVersion;
/*     */       
/* 367 */       this.type = 0;
/*     */       
/* 369 */       this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, frameOffset);
/*     */     } 
/*     */     
/* 372 */     if (this.version != this.spdyVersion || !isValidFrameHeader()) {
/* 373 */       return State.FRAME_ERROR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 378 */     if (willGenerateFrame())
/* 379 */     { switch (this.type)
/*     */       { case 0:
/* 381 */           nextState = State.READ_DATA_FRAME;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 402 */           return nextState;case 1: case 2: case 8: nextState = State.READ_HEADER_BLOCK_FRAME; return nextState;case 4: nextState = State.READ_SETTINGS_FRAME; return nextState; }  nextState = State.READ_CONTROL_FRAME; } else if (this.length != 0) { nextState = State.DISCARD_FRAME; } else { nextState = State.READ_COMMON_HEADER; }  return nextState; } private Object readControlFrame(ByteBuf buffer) { int streamId;
/*     */     int statusCode;
/*     */     int ID;
/*     */     int minLength;
/*     */     int lastGoodStreamId;
/*     */     int deltaWindowSize;
/* 408 */     switch (this.type) {
/*     */       case 3:
/* 410 */         if (buffer.readableBytes() < 8) {
/* 411 */           return null;
/*     */         }
/*     */         
/* 414 */         streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 415 */         statusCode = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex() + 4);
/* 416 */         buffer.skipBytes(8);
/*     */         
/* 418 */         return new DefaultSpdyRstStreamFrame(streamId, statusCode);
/*     */       
/*     */       case 6:
/* 421 */         if (buffer.readableBytes() < 4) {
/* 422 */           return null;
/*     */         }
/*     */         
/* 425 */         ID = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
/* 426 */         buffer.skipBytes(4);
/*     */         
/* 428 */         return new DefaultSpdyPingFrame(ID);
/*     */       
/*     */       case 7:
/* 431 */         minLength = (this.version < 3) ? 4 : 8;
/* 432 */         if (buffer.readableBytes() < minLength) {
/* 433 */           return null;
/*     */         }
/*     */         
/* 436 */         lastGoodStreamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 437 */         buffer.skipBytes(4);
/*     */         
/* 439 */         if (this.version < 3) {
/* 440 */           return new DefaultSpdyGoAwayFrame(lastGoodStreamId);
/*     */         }
/*     */         
/* 443 */         statusCode = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
/* 444 */         buffer.skipBytes(4);
/*     */         
/* 446 */         return new DefaultSpdyGoAwayFrame(lastGoodStreamId, statusCode);
/*     */       
/*     */       case 9:
/* 449 */         if (buffer.readableBytes() < 8) {
/* 450 */           return null;
/*     */         }
/*     */         
/* 453 */         streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 454 */         deltaWindowSize = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex() + 4);
/* 455 */         buffer.skipBytes(8);
/*     */         
/* 457 */         return new DefaultSpdyWindowUpdateFrame(streamId, deltaWindowSize);
/*     */     } 
/*     */     
/* 460 */     throw new Error("Shouldn't reach here."); } private SpdyHeadersFrame readHeaderBlockFrame(ByteBuf buffer) { int minLength; int offset;
/*     */     int streamId;
/*     */     int associatedToStreamId;
/*     */     byte priority;
/*     */     SpdySynStreamFrame spdySynStreamFrame;
/*     */     SpdySynReplyFrame spdySynReplyFrame;
/*     */     SpdyHeadersFrame spdyHeadersFrame;
/* 467 */     switch (this.type) {
/*     */       case 1:
/* 469 */         minLength = (this.version < 3) ? 12 : 10;
/* 470 */         if (buffer.readableBytes() < minLength) {
/* 471 */           return null;
/*     */         }
/*     */         
/* 474 */         offset = buffer.readerIndex();
/* 475 */         streamId = SpdyCodecUtil.getUnsignedInt(buffer, offset);
/* 476 */         associatedToStreamId = SpdyCodecUtil.getUnsignedInt(buffer, offset + 4);
/* 477 */         priority = (byte)(buffer.getByte(offset + 8) >> 5 & 0x7);
/* 478 */         if (this.version < 3) {
/* 479 */           priority = (byte)(priority >> 1);
/*     */         }
/* 481 */         buffer.skipBytes(10);
/* 482 */         this.length -= 10;
/*     */ 
/*     */         
/* 485 */         if (this.version < 3 && this.length == 2 && buffer.getShort(buffer.readerIndex()) == 0) {
/* 486 */           buffer.skipBytes(2);
/* 487 */           this.length = 0;
/*     */         } 
/*     */         
/* 490 */         spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamId, associatedToStreamId, priority);
/*     */         
/* 492 */         spdySynStreamFrame.setLast(((this.flags & 0x1) != 0));
/* 493 */         spdySynStreamFrame.setUnidirectional(((this.flags & 0x2) != 0));
/*     */         
/* 495 */         return spdySynStreamFrame;
/*     */       
/*     */       case 2:
/* 498 */         minLength = (this.version < 3) ? 8 : 4;
/* 499 */         if (buffer.readableBytes() < minLength) {
/* 500 */           return null;
/*     */         }
/*     */         
/* 503 */         streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 504 */         buffer.skipBytes(4);
/* 505 */         this.length -= 4;
/*     */ 
/*     */         
/* 508 */         if (this.version < 3) {
/* 509 */           buffer.skipBytes(2);
/* 510 */           this.length -= 2;
/*     */         } 
/*     */ 
/*     */         
/* 514 */         if (this.version < 3 && this.length == 2 && buffer.getShort(buffer.readerIndex()) == 0) {
/* 515 */           buffer.skipBytes(2);
/* 516 */           this.length = 0;
/*     */         } 
/*     */         
/* 519 */         spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
/* 520 */         spdySynReplyFrame.setLast(((this.flags & 0x1) != 0));
/*     */         
/* 522 */         return spdySynReplyFrame;
/*     */       
/*     */       case 8:
/* 525 */         if (buffer.readableBytes() < 4) {
/* 526 */           return null;
/*     */         }
/*     */ 
/*     */         
/* 530 */         if (this.version < 3 && this.length > 4 && buffer.readableBytes() < 8) {
/* 531 */           return null;
/*     */         }
/*     */         
/* 534 */         streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
/* 535 */         buffer.skipBytes(4);
/* 536 */         this.length -= 4;
/*     */ 
/*     */         
/* 539 */         if (this.version < 3 && this.length != 0) {
/* 540 */           buffer.skipBytes(2);
/* 541 */           this.length -= 2;
/*     */         } 
/*     */ 
/*     */         
/* 545 */         if (this.version < 3 && this.length == 2 && buffer.getShort(buffer.readerIndex()) == 0) {
/* 546 */           buffer.skipBytes(2);
/* 547 */           this.length = 0;
/*     */         } 
/*     */         
/* 550 */         spdyHeadersFrame = new DefaultSpdyHeadersFrame(streamId);
/* 551 */         spdyHeadersFrame.setLast(((this.flags & 0x1) != 0));
/*     */         
/* 553 */         return spdyHeadersFrame;
/*     */     } 
/*     */     
/* 556 */     throw new Error("Shouldn't reach here."); }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isValidFrameHeader() {
/* 561 */     switch (this.type) {
/*     */       case 0:
/* 563 */         return (this.streamId != 0);
/*     */       
/*     */       case 1:
/* 566 */         return (this.version < 3) ? ((this.length >= 12)) : ((this.length >= 10));
/*     */       
/*     */       case 2:
/* 569 */         return (this.version < 3) ? ((this.length >= 8)) : ((this.length >= 4));
/*     */       
/*     */       case 3:
/* 572 */         return (this.flags == 0 && this.length == 8);
/*     */       
/*     */       case 4:
/* 575 */         return (this.length >= 4);
/*     */       
/*     */       case 6:
/* 578 */         return (this.length == 4);
/*     */       
/*     */       case 7:
/* 581 */         return (this.version < 3) ? ((this.length == 4)) : ((this.length == 8));
/*     */       
/*     */       case 8:
/* 584 */         if (this.version < 3) {
/* 585 */           return (this.length == 4 || this.length >= 8);
/*     */         }
/* 587 */         return (this.length >= 4);
/*     */ 
/*     */       
/*     */       case 9:
/* 591 */         return (this.length == 8);
/*     */     } 
/*     */     
/* 594 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean willGenerateFrame() {
/* 599 */     switch (this.type) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/* 609 */         return true;
/*     */     } 
/*     */     
/* 612 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fireInvalidFrameException(ChannelHandlerContext ctx) {
/* 617 */     ctx.fireExceptionCaught(INVALID_FRAME);
/*     */   }
/*     */   
/*     */   private static void fireProtocolException(ChannelHandlerContext ctx, String message) {
/* 621 */     ctx.fireExceptionCaught(new SpdyProtocolException(message));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyFrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */