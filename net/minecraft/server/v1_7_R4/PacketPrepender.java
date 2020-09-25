/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPrepender
/*    */   extends MessageToByteEncoder
/*    */ {
/*    */   protected void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) {
/* 16 */     int i = paramByteBuf1.readableBytes();
/* 17 */     int j = PacketDataSerializer.a(i);
/*    */     
/* 19 */     if (j > 3) {
/* 20 */       throw new IllegalArgumentException("unable to fit " + i + " into " + '\003');
/*    */     }
/*    */     
/* 23 */     PacketDataSerializer packetDataSerializer = new PacketDataSerializer(paramByteBuf2);
/*    */     
/* 25 */     packetDataSerializer.ensureWritable(j + i);
/*    */     
/* 27 */     packetDataSerializer.b(i);
/* 28 */     packetDataSerializer.writeBytes(paramByteBuf1, paramByteBuf1.readerIndex(), i);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPrepender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */