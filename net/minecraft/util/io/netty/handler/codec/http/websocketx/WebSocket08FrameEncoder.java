/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.TooLongFrameException;
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
/*     */ 
/*     */ 
/*     */ public class WebSocket08FrameEncoder
/*     */   extends MessageToMessageEncoder<WebSocketFrame>
/*     */   implements WebSocketFrameEncoder
/*     */ {
/*  75 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameEncoder.class);
/*     */ 
/*     */   
/*     */   private static final byte OPCODE_CONT = 0;
/*     */   
/*     */   private static final byte OPCODE_TEXT = 1;
/*     */   
/*     */   private static final byte OPCODE_BINARY = 2;
/*     */   
/*     */   private static final byte OPCODE_CLOSE = 8;
/*     */   
/*     */   private static final byte OPCODE_PING = 9;
/*     */   
/*     */   private static final byte OPCODE_PONG = 10;
/*     */   
/*     */   private final boolean maskPayload;
/*     */ 
/*     */   
/*     */   public WebSocket08FrameEncoder(boolean maskPayload) {
/*  94 */     this.maskPayload = maskPayload;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
/*     */     byte opcode;
/* 102 */     ByteBuf data = msg.content();
/* 103 */     if (data == null) {
/* 104 */       data = Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */ 
/*     */     
/* 108 */     if (msg instanceof TextWebSocketFrame) {
/* 109 */       opcode = 1;
/* 110 */     } else if (msg instanceof PingWebSocketFrame) {
/* 111 */       opcode = 9;
/* 112 */     } else if (msg instanceof PongWebSocketFrame) {
/* 113 */       opcode = 10;
/* 114 */     } else if (msg instanceof CloseWebSocketFrame) {
/* 115 */       opcode = 8;
/* 116 */     } else if (msg instanceof BinaryWebSocketFrame) {
/* 117 */       opcode = 2;
/* 118 */     } else if (msg instanceof ContinuationWebSocketFrame) {
/* 119 */       opcode = 0;
/*     */     } else {
/* 121 */       throw new UnsupportedOperationException("Cannot encode frame of type: " + msg.getClass().getName());
/*     */     } 
/*     */     
/* 124 */     int length = data.readableBytes();
/*     */     
/* 126 */     if (logger.isDebugEnabled()) {
/* 127 */       logger.debug("Encoding WebSocket Frame opCode=" + opcode + " length=" + length);
/*     */     }
/*     */     
/* 130 */     int b0 = 0;
/* 131 */     if (msg.isFinalFragment()) {
/* 132 */       b0 |= 0x80;
/*     */     }
/* 134 */     b0 |= msg.rsv() % 8 << 4;
/* 135 */     b0 |= opcode % 128;
/*     */     
/* 137 */     if (opcode == 9 && length > 125) {
/* 138 */       throw new TooLongFrameException("invalid payload for PING (payload length must be <= 125, was " + length);
/*     */     }
/*     */ 
/*     */     
/* 142 */     boolean release = true;
/* 143 */     ByteBuf buf = null;
/*     */     try {
/* 145 */       int maskLength = this.maskPayload ? 4 : 0;
/* 146 */       if (length <= 125) {
/* 147 */         int size = 2 + maskLength;
/* 148 */         if (this.maskPayload) {
/* 149 */           size += length;
/*     */         }
/* 151 */         buf = ctx.alloc().buffer(size);
/* 152 */         buf.writeByte(b0);
/* 153 */         byte b = (byte)(this.maskPayload ? (0x80 | (byte)length) : (byte)length);
/* 154 */         buf.writeByte(b);
/* 155 */       } else if (length <= 65535) {
/* 156 */         int size = 4 + maskLength;
/* 157 */         if (this.maskPayload) {
/* 158 */           size += length;
/*     */         }
/* 160 */         buf = ctx.alloc().buffer(size);
/* 161 */         buf.writeByte(b0);
/* 162 */         buf.writeByte(this.maskPayload ? 254 : 126);
/* 163 */         buf.writeByte(length >>> 8 & 0xFF);
/* 164 */         buf.writeByte(length & 0xFF);
/*     */       } else {
/* 166 */         int size = 10 + maskLength;
/* 167 */         if (this.maskPayload) {
/* 168 */           size += length;
/*     */         }
/* 170 */         buf = ctx.alloc().buffer(size);
/* 171 */         buf.writeByte(b0);
/* 172 */         buf.writeByte(this.maskPayload ? 255 : 127);
/* 173 */         buf.writeLong(length);
/*     */       } 
/*     */ 
/*     */       
/* 177 */       if (this.maskPayload) {
/* 178 */         int random = (int)(Math.random() * 2.147483647E9D);
/* 179 */         byte[] mask = ByteBuffer.allocate(4).putInt(random).array();
/* 180 */         buf.writeBytes(mask);
/*     */         
/* 182 */         int counter = 0;
/* 183 */         for (int i = data.readerIndex(); i < data.writerIndex(); i++) {
/* 184 */           byte byteData = data.getByte(i);
/* 185 */           buf.writeByte(byteData ^ mask[counter++ % 4]);
/*     */         } 
/* 187 */         out.add(buf);
/*     */       } else {
/* 189 */         out.add(buf);
/* 190 */         out.add(data.retain());
/*     */       } 
/* 192 */       release = false;
/*     */     } finally {
/* 194 */       if (release && buf != null)
/* 195 */         buf.release(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocket08FrameEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */