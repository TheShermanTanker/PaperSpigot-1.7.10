/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
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
/*     */ public class WebSocket00FrameDecoder
/*     */   extends ReplayingDecoder<Void>
/*     */   implements WebSocketFrameDecoder
/*     */ {
/*     */   static final int DEFAULT_MAX_FRAME_SIZE = 16384;
/*     */   private final long maxFrameSize;
/*     */   private boolean receivedClosingHandshake;
/*     */   
/*     */   public WebSocket00FrameDecoder() {
/*  39 */     this(16384);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocket00FrameDecoder(int maxFrameSize) {
/*  50 */     this.maxFrameSize = maxFrameSize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  56 */     if (this.receivedClosingHandshake) {
/*  57 */       in.skipBytes(actualReadableBytes());
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  62 */     byte type = in.readByte();
/*  63 */     if ((type & 0x80) == 128) {
/*     */       
/*  65 */       out.add(decodeBinaryFrame(ctx, type, in));
/*     */     } else {
/*     */       
/*  68 */       out.add(decodeTextFrame(ctx, in));
/*     */     } 
/*     */   }
/*     */   private WebSocketFrame decodeBinaryFrame(ChannelHandlerContext ctx, byte type, ByteBuf buffer) {
/*     */     byte b;
/*  73 */     long frameSize = 0L;
/*  74 */     int lengthFieldSize = 0;
/*     */     
/*     */     do {
/*  77 */       b = buffer.readByte();
/*  78 */       frameSize <<= 7L;
/*  79 */       frameSize |= (b & Byte.MAX_VALUE);
/*  80 */       if (frameSize > this.maxFrameSize) {
/*  81 */         throw new TooLongFrameException();
/*     */       }
/*  83 */       lengthFieldSize++;
/*  84 */       if (lengthFieldSize > 8)
/*     */       {
/*  86 */         throw new TooLongFrameException();
/*     */       }
/*  88 */     } while ((b & 0x80) == 128);
/*     */     
/*  90 */     if (type == -1 && frameSize == 0L) {
/*  91 */       this.receivedClosingHandshake = true;
/*  92 */       return new CloseWebSocketFrame();
/*     */     } 
/*  94 */     ByteBuf payload = ctx.alloc().buffer((int)frameSize);
/*  95 */     buffer.readBytes(payload);
/*  96 */     return new BinaryWebSocketFrame(payload);
/*     */   }
/*     */   
/*     */   private WebSocketFrame decodeTextFrame(ChannelHandlerContext ctx, ByteBuf buffer) {
/* 100 */     int ridx = buffer.readerIndex();
/* 101 */     int rbytes = actualReadableBytes();
/* 102 */     int delimPos = buffer.indexOf(ridx, ridx + rbytes, (byte)-1);
/* 103 */     if (delimPos == -1) {
/*     */       
/* 105 */       if (rbytes > this.maxFrameSize)
/*     */       {
/* 107 */         throw new TooLongFrameException();
/*     */       }
/*     */       
/* 110 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 114 */     int frameSize = delimPos - ridx;
/* 115 */     if (frameSize > this.maxFrameSize) {
/* 116 */       throw new TooLongFrameException();
/*     */     }
/*     */     
/* 119 */     ByteBuf binaryData = ctx.alloc().buffer(frameSize);
/* 120 */     buffer.readBytes(binaryData);
/* 121 */     buffer.skipBytes(1);
/*     */     
/* 123 */     int ffDelimPos = binaryData.indexOf(binaryData.readerIndex(), binaryData.writerIndex(), (byte)-1);
/* 124 */     if (ffDelimPos >= 0) {
/* 125 */       throw new IllegalArgumentException("a text frame should not contain 0xFF.");
/*     */     }
/*     */     
/* 128 */     return new TextWebSocketFrame(binaryData);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocket00FrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */