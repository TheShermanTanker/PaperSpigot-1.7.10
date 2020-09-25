/*    */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandler.Sharable;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageEncoder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public class WebSocket00FrameEncoder
/*    */   extends MessageToMessageEncoder<WebSocketFrame>
/*    */   implements WebSocketFrameEncoder
/*    */ {
/* 34 */   private static final ByteBuf _0X00 = Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(0));
/*    */   
/* 36 */   private static final ByteBuf _0XFF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(-1));
/*    */   
/* 38 */   private static final ByteBuf _0XFF_0X00 = Unpooled.unreleasableBuffer(Unpooled.directBuffer(2, 2).writeByte(-1).writeByte(0));
/*    */ 
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
/* 43 */     if (msg instanceof TextWebSocketFrame) {
/*    */       
/* 45 */       ByteBuf data = msg.content();
/*    */       
/* 47 */       out.add(_0X00.duplicate());
/* 48 */       out.add(data.retain());
/* 49 */       out.add(_0XFF.duplicate());
/* 50 */     } else if (msg instanceof CloseWebSocketFrame) {
/*    */       
/* 52 */       out.add(_0XFF_0X00);
/*    */     } else {
/*    */       
/* 55 */       ByteBuf data = msg.content();
/* 56 */       int dataLen = data.readableBytes();
/*    */       
/* 58 */       ByteBuf buf = ctx.alloc().buffer(5);
/* 59 */       boolean release = true;
/*    */       
/*    */       try {
/* 62 */         buf.writeByte(-128);
/*    */ 
/*    */         
/* 65 */         int b1 = dataLen >>> 28 & 0x7F;
/* 66 */         int b2 = dataLen >>> 14 & 0x7F;
/* 67 */         int b3 = dataLen >>> 7 & 0x7F;
/* 68 */         int b4 = dataLen & 0x7F;
/* 69 */         if (b1 == 0) {
/* 70 */           if (b2 == 0) {
/* 71 */             if (b3 == 0) {
/* 72 */               buf.writeByte(b4);
/*    */             } else {
/* 74 */               buf.writeByte(b3 | 0x80);
/* 75 */               buf.writeByte(b4);
/*    */             } 
/*    */           } else {
/* 78 */             buf.writeByte(b2 | 0x80);
/* 79 */             buf.writeByte(b3 | 0x80);
/* 80 */             buf.writeByte(b4);
/*    */           } 
/*    */         } else {
/* 83 */           buf.writeByte(b1 | 0x80);
/* 84 */           buf.writeByte(b2 | 0x80);
/* 85 */           buf.writeByte(b3 | 0x80);
/* 86 */           buf.writeByte(b4);
/*    */         } 
/*    */ 
/*    */         
/* 90 */         out.add(buf);
/* 91 */         out.add(data.retain());
/* 92 */         release = false;
/*    */       } finally {
/* 94 */         if (release)
/* 95 */           buf.release(); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocket00FrameEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */