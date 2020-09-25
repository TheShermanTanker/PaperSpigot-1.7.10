/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import net.minecraft.util.io.netty.handler.codec.CorruptedFrameException;
/*    */ 
/*    */ public class PacketSplitter
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<ByteBuf> paramList) {
/* 14 */     paramByteBuf.markReaderIndex();
/*    */     
/* 16 */     byte[] arrayOfByte = new byte[3];
/* 17 */     for (byte b = 0; b < arrayOfByte.length; b++) {
/* 18 */       if (!paramByteBuf.isReadable()) {
/* 19 */         paramByteBuf.resetReaderIndex();
/*    */         
/*    */         return;
/*    */       } 
/* 23 */       arrayOfByte[b] = paramByteBuf.readByte();
/* 24 */       if (arrayOfByte[b] >= 0) {
/* 25 */         PacketDataSerializer packetDataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(arrayOfByte));
/*    */         try {
/* 27 */           int i = packetDataSerializer.a();
/*    */           
/* 29 */           if (paramByteBuf.readableBytes() < i) {
/* 30 */             paramByteBuf.resetReaderIndex();
/*    */             return;
/*    */           } 
/* 33 */           paramList.add(paramByteBuf.readBytes(i));
/*    */           
/*    */           return;
/*    */         } finally {
/* 37 */           packetDataSerializer.release();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 42 */     throw new CorruptedFrameException("length wider than 21-bit");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketSplitter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */