/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufUtil;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.DecoderResult;
/*     */ import net.minecraft.util.io.netty.handler.codec.ReplayingDecoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.TooLongFrameException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HttpObjectDecoder
/*     */   extends ReplayingDecoder<HttpObjectDecoder.State>
/*     */ {
/* 103 */   private static final ThreadLocal<StringBuilder> BUILDERS = new ThreadLocal<StringBuilder>()
/*     */     {
/*     */       protected StringBuilder initialValue() {
/* 106 */         return new StringBuilder(512);
/*     */       }
/*     */ 
/*     */       
/*     */       public StringBuilder get() {
/* 111 */         StringBuilder builder = super.get();
/* 112 */         builder.setLength(0);
/* 113 */         return builder;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final int maxInitialLineLength;
/*     */   
/*     */   private final int maxHeaderSize;
/*     */   
/*     */   private final int maxChunkSize;
/*     */   
/*     */   private final boolean chunkedSupported;
/*     */   private ByteBuf content;
/*     */   private HttpMessage message;
/*     */   private long chunkSize;
/*     */   private int headerSize;
/*     */   private int contentRead;
/*     */   
/*     */   enum State
/*     */   {
/* 133 */     SKIP_CONTROL_CHARS,
/* 134 */     READ_INITIAL,
/* 135 */     READ_HEADER,
/* 136 */     READ_VARIABLE_LENGTH_CONTENT,
/* 137 */     READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS,
/* 138 */     READ_FIXED_LENGTH_CONTENT,
/* 139 */     READ_FIXED_LENGTH_CONTENT_AS_CHUNKS,
/* 140 */     READ_CHUNK_SIZE,
/* 141 */     READ_CHUNKED_CONTENT,
/* 142 */     READ_CHUNKED_CONTENT_AS_CHUNKS,
/* 143 */     READ_CHUNK_DELIMITER,
/* 144 */     READ_CHUNK_FOOTER,
/* 145 */     BAD_MESSAGE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpObjectDecoder() {
/* 154 */     this(4096, 8192, 8192, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported) {
/* 163 */     super(State.SKIP_CONTROL_CHARS);
/*     */     
/* 165 */     if (maxInitialLineLength <= 0) {
/* 166 */       throw new IllegalArgumentException("maxInitialLineLength must be a positive integer: " + maxInitialLineLength);
/*     */     }
/*     */ 
/*     */     
/* 170 */     if (maxHeaderSize <= 0) {
/* 171 */       throw new IllegalArgumentException("maxHeaderSize must be a positive integer: " + maxHeaderSize);
/*     */     }
/*     */ 
/*     */     
/* 175 */     if (maxChunkSize < 0) {
/* 176 */       throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize);
/*     */     }
/*     */ 
/*     */     
/* 180 */     this.maxInitialLineLength = maxInitialLineLength;
/* 181 */     this.maxHeaderSize = maxHeaderSize;
/* 182 */     this.maxChunkSize = maxChunkSize;
/* 183 */     this.chunkedSupported = chunkedSupported; } protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception { int toRead; HttpContent chunk; int chunkSize; ByteBuf content; int readLimit; long l; int j;
/*     */     int i;
/*     */     int k;
/*     */     HttpContent httpContent1;
/*     */     ByteBuf byteBuf1;
/* 188 */     switch ((State)state()) {
/*     */       case SKIP_CONTROL_CHARS:
/*     */         try {
/* 191 */           skipControlCharacters(buffer);
/* 192 */           checkpoint(State.READ_INITIAL);
/*     */         } finally {
/* 194 */           checkpoint();
/*     */         } 
/*     */       case READ_INITIAL:
/*     */         try {
/* 198 */           String[] initialLine = splitInitialLine(readLine(buffer, this.maxInitialLineLength));
/* 199 */           if (initialLine.length < 3) {
/*     */             
/* 201 */             checkpoint(State.SKIP_CONTROL_CHARS);
/*     */             
/*     */             return;
/*     */           } 
/* 205 */           this.message = createMessage(initialLine);
/* 206 */           checkpoint(State.READ_HEADER);
/*     */         }
/* 208 */         catch (Exception e) {
/* 209 */           out.add(invalidMessage(e)); return;
/*     */         } 
/*     */       case READ_HEADER:
/*     */         try {
/* 213 */           State nextState = readHeaders(buffer);
/* 214 */           checkpoint(nextState);
/* 215 */           if (nextState == State.READ_CHUNK_SIZE) {
/* 216 */             if (!this.chunkedSupported) {
/* 217 */               throw new IllegalArgumentException("Chunked messages not supported");
/*     */             }
/*     */             
/* 220 */             out.add(this.message);
/*     */             return;
/*     */           } 
/* 223 */           if (nextState == State.SKIP_CONTROL_CHARS) {
/*     */             
/* 225 */             reset(out);
/*     */             return;
/*     */           } 
/* 228 */           long contentLength = HttpHeaders.getContentLength(this.message, -1L);
/* 229 */           if (contentLength == 0L || (contentLength == -1L && isDecodingRequest())) {
/* 230 */             this.content = Unpooled.EMPTY_BUFFER;
/* 231 */             reset(out);
/*     */             
/*     */             return;
/*     */           } 
/* 235 */           switch (nextState) {
/*     */             case READ_FIXED_LENGTH_CONTENT:
/* 237 */               if (contentLength > this.maxChunkSize || HttpHeaders.is100ContinueExpected(this.message)) {
/*     */                 
/* 239 */                 checkpoint(State.READ_FIXED_LENGTH_CONTENT_AS_CHUNKS);
/*     */ 
/*     */                 
/* 242 */                 this.chunkSize = HttpHeaders.getContentLength(this.message, -1L);
/* 243 */                 out.add(this.message);
/*     */                 return;
/*     */               } 
/*     */               return;
/*     */             case READ_VARIABLE_LENGTH_CONTENT:
/* 248 */               if (buffer.readableBytes() > this.maxChunkSize || HttpHeaders.is100ContinueExpected(this.message)) {
/*     */                 
/* 250 */                 checkpoint(State.READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS);
/* 251 */                 out.add(this.message);
/*     */                 return;
/*     */               } 
/*     */               return;
/*     */           } 
/* 256 */           throw new IllegalStateException("Unexpected state: " + nextState);
/*     */ 
/*     */         
/*     */         }
/* 260 */         catch (Exception e) {
/* 261 */           out.add(invalidMessage(e));
/*     */           return;
/*     */         } 
/*     */       case READ_VARIABLE_LENGTH_CONTENT:
/* 265 */         toRead = actualReadableBytes();
/* 266 */         if (toRead > this.maxChunkSize) {
/* 267 */           toRead = this.maxChunkSize;
/*     */         }
/* 269 */         out.add(this.message);
/* 270 */         out.add(new DefaultHttpContent(ByteBufUtil.readBytes(ctx.alloc(), buffer, toRead)));
/*     */         return;
/*     */ 
/*     */       
/*     */       case READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS:
/* 275 */         toRead = actualReadableBytes();
/* 276 */         if (toRead > this.maxChunkSize) {
/* 277 */           toRead = this.maxChunkSize;
/*     */         }
/* 279 */         content = ByteBufUtil.readBytes(ctx.alloc(), buffer, toRead);
/* 280 */         if (!buffer.isReadable()) {
/* 281 */           reset();
/* 282 */           out.add(new DefaultLastHttpContent(content));
/*     */           return;
/*     */         } 
/* 285 */         out.add(new DefaultHttpContent(content));
/*     */         return;
/*     */       
/*     */       case READ_FIXED_LENGTH_CONTENT:
/* 289 */         readFixedLengthContent(ctx, buffer, out);
/*     */         return;
/*     */       
/*     */       case READ_FIXED_LENGTH_CONTENT_AS_CHUNKS:
/* 293 */         l = this.chunkSize;
/* 294 */         j = actualReadableBytes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 302 */         if (j == 0) {
/*     */           return;
/*     */         }
/*     */         
/* 306 */         k = j;
/* 307 */         if (k > this.maxChunkSize) {
/* 308 */           k = this.maxChunkSize;
/*     */         }
/* 310 */         if (k > l) {
/* 311 */           k = (int)l;
/*     */         }
/* 313 */         byteBuf1 = ByteBufUtil.readBytes(ctx.alloc(), buffer, k);
/* 314 */         if (l > k) {
/* 315 */           l -= k;
/*     */         } else {
/* 317 */           l = 0L;
/*     */         } 
/* 319 */         this.chunkSize = l;
/*     */         
/* 321 */         if (l == 0L) {
/*     */           
/* 323 */           reset();
/* 324 */           out.add(new DefaultLastHttpContent(byteBuf1));
/*     */           return;
/*     */         } 
/* 327 */         out.add(new DefaultHttpContent(byteBuf1));
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case READ_CHUNK_SIZE:
/*     */         try {
/* 335 */           StringBuilder line = readLine(buffer, this.maxInitialLineLength);
/* 336 */           int m = getChunkSize(line.toString());
/* 337 */           this.chunkSize = m;
/* 338 */           if (m == 0) {
/* 339 */             checkpoint(State.READ_CHUNK_FOOTER); return;
/*     */           } 
/* 341 */           if (m > this.maxChunkSize) {
/*     */             
/* 343 */             checkpoint(State.READ_CHUNKED_CONTENT_AS_CHUNKS);
/*     */           } else {
/* 345 */             checkpoint(State.READ_CHUNKED_CONTENT);
/*     */           } 
/* 347 */         } catch (Exception e) {
/* 348 */           out.add(invalidChunk(e));
/*     */           return;
/*     */         } 
/*     */       case READ_CHUNKED_CONTENT:
/* 352 */         assert this.chunkSize <= 2147483647L;
/* 353 */         chunk = new DefaultHttpContent(ByteBufUtil.readBytes(ctx.alloc(), buffer, (int)this.chunkSize));
/* 354 */         checkpoint(State.READ_CHUNK_DELIMITER);
/* 355 */         out.add(chunk);
/*     */         return;
/*     */       
/*     */       case READ_CHUNKED_CONTENT_AS_CHUNKS:
/* 359 */         assert this.chunkSize <= 2147483647L;
/* 360 */         chunkSize = (int)this.chunkSize;
/* 361 */         readLimit = actualReadableBytes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 369 */         if (readLimit == 0) {
/*     */           return;
/*     */         }
/*     */         
/* 373 */         i = chunkSize;
/* 374 */         if (i > this.maxChunkSize) {
/* 375 */           i = this.maxChunkSize;
/*     */         }
/* 377 */         if (i > readLimit) {
/* 378 */           i = readLimit;
/*     */         }
/* 380 */         httpContent1 = new DefaultHttpContent(ByteBufUtil.readBytes(ctx.alloc(), buffer, i));
/* 381 */         if (chunkSize > i) {
/* 382 */           chunkSize -= i;
/*     */         } else {
/* 384 */           chunkSize = 0;
/*     */         } 
/* 386 */         this.chunkSize = chunkSize;
/*     */         
/* 388 */         if (chunkSize == 0)
/*     */         {
/* 390 */           checkpoint(State.READ_CHUNK_DELIMITER);
/*     */         }
/*     */         
/* 393 */         out.add(httpContent1);
/*     */         return;
/*     */       
/*     */       case READ_CHUNK_DELIMITER:
/*     */         while (true) {
/* 398 */           byte next = buffer.readByte();
/* 399 */           if (next == 13) {
/* 400 */             if (buffer.readByte() == 10) {
/* 401 */               checkpoint(State.READ_CHUNK_SIZE); return;
/*     */             }  continue;
/*     */           } 
/* 404 */           if (next == 10) {
/* 405 */             checkpoint(State.READ_CHUNK_SIZE);
/*     */             return;
/*     */           } 
/* 408 */           checkpoint();
/*     */         } 
/*     */       
/*     */       case READ_CHUNK_FOOTER:
/*     */         try {
/* 413 */           LastHttpContent trailer = readTrailingHeaders(buffer);
/* 414 */           if (this.maxChunkSize == 0) {
/*     */             
/* 416 */             reset(out);
/*     */             return;
/*     */           } 
/* 419 */           reset();
/*     */           
/* 421 */           out.add(trailer);
/*     */           
/*     */           return;
/* 424 */         } catch (Exception e) {
/* 425 */           out.add(invalidChunk(e));
/*     */           return;
/*     */         } 
/*     */       
/*     */       case BAD_MESSAGE:
/* 430 */         buffer.skipBytes(actualReadableBytes());
/*     */         return;
/*     */     } 
/*     */     
/* 434 */     throw new Error("Shouldn't reach here."); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 441 */     decode(ctx, in, out);
/*     */ 
/*     */     
/* 444 */     if (this.message != null) {
/*     */       int actualContentLength; boolean prematureClosure;
/* 446 */       HttpMessage message = this.message;
/*     */       
/* 448 */       if (this.content != null) {
/* 449 */         actualContentLength = this.content.readableBytes();
/*     */       } else {
/* 451 */         actualContentLength = 0;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 456 */       if (isDecodingRequest()) {
/*     */         
/* 458 */         prematureClosure = true;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 463 */         long expectedContentLength = HttpHeaders.getContentLength(message, -1L);
/* 464 */         prematureClosure = (expectedContentLength >= 0L && actualContentLength != expectedContentLength);
/*     */       } 
/*     */       
/* 467 */       if (!prematureClosure) {
/* 468 */         if (actualContentLength == 0) {
/* 469 */           out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*     */         } else {
/* 471 */           out.add(new DefaultLastHttpContent(this.content));
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isContentAlwaysEmpty(HttpMessage msg) {
/* 478 */     if (msg instanceof HttpResponse) {
/* 479 */       HttpResponse res = (HttpResponse)msg;
/* 480 */       int code = res.getStatus().code();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 487 */       if (code >= 100 && code < 200) {
/* 488 */         if (code == 101 && !res.headers().contains("Sec-WebSocket-Accept"))
/*     */         {
/* 490 */           return false;
/*     */         }
/* 492 */         return true;
/*     */       } 
/*     */       
/* 495 */       switch (code) { case 204: case 205:
/*     */         case 304:
/* 497 */           return true; }
/*     */     
/*     */     } 
/* 500 */     return false;
/*     */   }
/*     */   
/*     */   private void reset() {
/* 504 */     reset((List<Object>)null);
/*     */   }
/*     */   
/*     */   private void reset(List<Object> out) {
/* 508 */     if (out != null) {
/* 509 */       LastHttpContent httpContent; HttpMessage message = this.message;
/* 510 */       ByteBuf content = this.content;
/*     */ 
/*     */       
/* 513 */       if (content == null || !content.isReadable()) {
/* 514 */         httpContent = LastHttpContent.EMPTY_LAST_CONTENT;
/*     */       } else {
/* 516 */         httpContent = new DefaultLastHttpContent(content);
/*     */       } 
/*     */       
/* 519 */       out.add(message);
/* 520 */       out.add(httpContent);
/*     */     } 
/*     */     
/* 523 */     this.content = null;
/* 524 */     this.message = null;
/*     */     
/* 526 */     checkpoint(State.SKIP_CONTROL_CHARS);
/*     */   }
/*     */   
/*     */   private HttpMessage invalidMessage(Exception cause) {
/* 530 */     checkpoint(State.BAD_MESSAGE);
/* 531 */     if (this.message != null) {
/* 532 */       this.message.setDecoderResult(DecoderResult.failure(cause));
/*     */     } else {
/* 534 */       this.message = createInvalidMessage();
/* 535 */       this.message.setDecoderResult(DecoderResult.failure(cause));
/*     */     } 
/* 537 */     return this.message;
/*     */   }
/*     */   
/*     */   private HttpContent invalidChunk(Exception cause) {
/* 541 */     checkpoint(State.BAD_MESSAGE);
/* 542 */     HttpContent chunk = new DefaultHttpContent(Unpooled.EMPTY_BUFFER);
/* 543 */     chunk.setDecoderResult(DecoderResult.failure(cause));
/* 544 */     return chunk;
/*     */   }
/*     */   private static void skipControlCharacters(ByteBuf buffer) {
/*     */     char c;
/*     */     do {
/* 549 */       c = (char)buffer.readUnsignedByte();
/* 550 */     } while (Character.isISOControl(c) || Character.isWhitespace(c));
/*     */     
/* 552 */     buffer.readerIndex(buffer.readerIndex() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readFixedLengthContent(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
/* 560 */     long length = HttpHeaders.getContentLength(this.message, -1L);
/* 561 */     assert length <= 2147483647L;
/* 562 */     int toRead = (int)length - this.contentRead;
/* 563 */     if (toRead > actualReadableBytes()) {
/* 564 */       toRead = actualReadableBytes();
/*     */     }
/* 566 */     this.contentRead += toRead;
/* 567 */     if (length < this.contentRead) {
/* 568 */       out.add(this.message);
/* 569 */       out.add(new DefaultHttpContent(ByteBufUtil.readBytes(ctx.alloc(), buffer, toRead)));
/*     */       return;
/*     */     } 
/* 572 */     if (this.content == null) {
/* 573 */       this.content = ByteBufUtil.readBytes(ctx.alloc(), buffer, (int)length);
/*     */     } else {
/* 575 */       this.content.writeBytes(buffer, (int)length);
/*     */     } 
/* 577 */     reset(out);
/*     */   }
/*     */   private State readHeaders(ByteBuf buffer) {
/*     */     State nextState;
/* 581 */     this.headerSize = 0;
/* 582 */     HttpMessage message = this.message;
/* 583 */     HttpHeaders headers = message.headers();
/*     */     
/* 585 */     StringBuilder line = readHeader(buffer);
/* 586 */     String name = null;
/* 587 */     String value = null;
/* 588 */     if (line.length() > 0) {
/* 589 */       headers.clear();
/*     */       do {
/* 591 */         char firstChar = line.charAt(0);
/* 592 */         if (name != null && (firstChar == ' ' || firstChar == '\t')) {
/* 593 */           value = value + ' ' + line.toString().trim();
/*     */         } else {
/* 595 */           if (name != null) {
/* 596 */             headers.add(name, value);
/*     */           }
/* 598 */           String[] header = splitHeader(line);
/* 599 */           name = header[0];
/* 600 */           value = header[1];
/*     */         } 
/*     */         
/* 603 */         line = readHeader(buffer);
/* 604 */       } while (line.length() > 0);
/*     */ 
/*     */       
/* 607 */       if (name != null) {
/* 608 */         headers.add(name, value);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 614 */     if (isContentAlwaysEmpty(message)) {
/* 615 */       HttpHeaders.removeTransferEncodingChunked(message);
/* 616 */       nextState = State.SKIP_CONTROL_CHARS;
/* 617 */     } else if (HttpHeaders.isTransferEncodingChunked(message)) {
/* 618 */       nextState = State.READ_CHUNK_SIZE;
/* 619 */     } else if (HttpHeaders.getContentLength(message, -1L) >= 0L) {
/* 620 */       nextState = State.READ_FIXED_LENGTH_CONTENT;
/*     */     } else {
/* 622 */       nextState = State.READ_VARIABLE_LENGTH_CONTENT;
/*     */     } 
/* 624 */     return nextState;
/*     */   }
/*     */   
/*     */   private LastHttpContent readTrailingHeaders(ByteBuf buffer) {
/* 628 */     this.headerSize = 0;
/* 629 */     StringBuilder line = readHeader(buffer);
/* 630 */     String lastHeader = null;
/* 631 */     if (line.length() > 0) {
/* 632 */       LastHttpContent trailer = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
/*     */       do {
/* 634 */         char firstChar = line.charAt(0);
/* 635 */         if (lastHeader != null && (firstChar == ' ' || firstChar == '\t')) {
/* 636 */           List<String> current = trailer.trailingHeaders().getAll(lastHeader);
/* 637 */           if (!current.isEmpty()) {
/* 638 */             int lastPos = current.size() - 1;
/* 639 */             String newString = (String)current.get(lastPos) + line.toString().trim();
/* 640 */             current.set(lastPos, newString);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 645 */           String[] header = splitHeader(line);
/* 646 */           String name = header[0];
/* 647 */           if (!name.equalsIgnoreCase("Content-Length") && !name.equalsIgnoreCase("Transfer-Encoding") && !name.equalsIgnoreCase("Trailer"))
/*     */           {
/*     */             
/* 650 */             trailer.trailingHeaders().add(name, header[1]);
/*     */           }
/* 652 */           lastHeader = name;
/*     */         } 
/*     */         
/* 655 */         line = readHeader(buffer);
/* 656 */       } while (line.length() > 0);
/*     */       
/* 658 */       return trailer;
/*     */     } 
/*     */     
/* 661 */     return LastHttpContent.EMPTY_LAST_CONTENT;
/*     */   }
/*     */   
/*     */   private StringBuilder readHeader(ByteBuf buffer) {
/* 665 */     StringBuilder sb = BUILDERS.get();
/* 666 */     int headerSize = this.headerSize;
/*     */ 
/*     */     
/*     */     while (true) {
/* 670 */       char nextByte = (char)buffer.readByte();
/* 671 */       headerSize++;
/*     */       
/* 673 */       switch (nextByte) {
/*     */         case '\r':
/* 675 */           nextByte = (char)buffer.readByte();
/* 676 */           headerSize++;
/* 677 */           if (nextByte == '\n') {
/*     */             break;
/*     */           }
/*     */           break;
/*     */         
/*     */         case '\n':
/*     */           break;
/*     */       } 
/*     */       
/* 686 */       if (headerSize >= this.maxHeaderSize)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 691 */         throw new TooLongFrameException("HTTP header is larger than " + this.maxHeaderSize + " bytes.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 696 */       sb.append(nextByte);
/*     */     } 
/*     */     
/* 699 */     this.headerSize = headerSize;
/* 700 */     return sb;
/*     */   }
/*     */   protected abstract boolean isDecodingRequest();
/*     */   protected abstract HttpMessage createMessage(String[] paramArrayOfString) throws Exception;
/*     */   
/*     */   protected abstract HttpMessage createInvalidMessage();
/*     */   
/*     */   private static int getChunkSize(String hex) {
/* 708 */     hex = hex.trim();
/* 709 */     for (int i = 0; i < hex.length(); i++) {
/* 710 */       char c = hex.charAt(i);
/* 711 */       if (c == ';' || Character.isWhitespace(c) || Character.isISOControl(c)) {
/* 712 */         hex = hex.substring(0, i);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 717 */     return Integer.parseInt(hex, 16);
/*     */   }
/*     */   
/*     */   private static StringBuilder readLine(ByteBuf buffer, int maxLineLength) {
/* 721 */     StringBuilder sb = BUILDERS.get();
/* 722 */     int lineLength = 0;
/*     */     while (true) {
/* 724 */       byte nextByte = buffer.readByte();
/* 725 */       if (nextByte == 13) {
/* 726 */         nextByte = buffer.readByte();
/* 727 */         if (nextByte == 10)
/* 728 */           return sb;  continue;
/*     */       } 
/* 730 */       if (nextByte == 10) {
/* 731 */         return sb;
/*     */       }
/* 733 */       if (lineLength >= maxLineLength)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 738 */         throw new TooLongFrameException("An HTTP line is larger than " + maxLineLength + " bytes.");
/*     */       }
/*     */ 
/*     */       
/* 742 */       lineLength++;
/* 743 */       sb.append((char)nextByte);
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
/*     */   private static String[] splitInitialLine(StringBuilder sb) {
/* 756 */     int aStart = findNonWhitespace(sb, 0);
/* 757 */     int aEnd = findWhitespace(sb, aStart);
/*     */     
/* 759 */     int bStart = findNonWhitespace(sb, aEnd);
/* 760 */     int bEnd = findWhitespace(sb, bStart);
/*     */     
/* 762 */     int cStart = findNonWhitespace(sb, bEnd);
/* 763 */     int cEnd = findEndOfString(sb);
/*     */     
/* 765 */     return new String[] { sb.substring(aStart, aEnd), sb.substring(bStart, bEnd), (cStart < cEnd) ? sb.substring(cStart, cEnd) : "" };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] splitHeader(StringBuilder sb) {
/* 772 */     int length = sb.length();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 779 */     int nameStart = findNonWhitespace(sb, 0); int nameEnd;
/* 780 */     for (nameEnd = nameStart; nameEnd < length; nameEnd++) {
/* 781 */       char ch = sb.charAt(nameEnd);
/* 782 */       if (ch == ':' || Character.isWhitespace(ch)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     int colonEnd;
/* 787 */     for (colonEnd = nameEnd; colonEnd < length; colonEnd++) {
/* 788 */       if (sb.charAt(colonEnd) == ':') {
/* 789 */         colonEnd++;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 794 */     int valueStart = findNonWhitespace(sb, colonEnd);
/* 795 */     if (valueStart == length) {
/* 796 */       return new String[] { sb.substring(nameStart, nameEnd), "" };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 802 */     int valueEnd = findEndOfString(sb);
/* 803 */     return new String[] { sb.substring(nameStart, nameEnd), sb.substring(valueStart, valueEnd) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int findNonWhitespace(CharSequence sb, int offset) {
/*     */     int result;
/* 811 */     for (result = offset; result < sb.length() && 
/* 812 */       Character.isWhitespace(sb.charAt(result)); result++);
/*     */ 
/*     */ 
/*     */     
/* 816 */     return result;
/*     */   }
/*     */   
/*     */   private static int findWhitespace(CharSequence sb, int offset) {
/*     */     int result;
/* 821 */     for (result = offset; result < sb.length() && 
/* 822 */       !Character.isWhitespace(sb.charAt(result)); result++);
/*     */ 
/*     */ 
/*     */     
/* 826 */     return result;
/*     */   }
/*     */   
/*     */   private static int findEndOfString(CharSequence sb) {
/*     */     int result;
/* 831 */     for (result = sb.length(); result > 0 && 
/* 832 */       Character.isWhitespace(sb.charAt(result - 1)); result--);
/*     */ 
/*     */ 
/*     */     
/* 836 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpObjectDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */