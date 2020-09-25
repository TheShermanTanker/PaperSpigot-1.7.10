/*    */ package org.spigotmc;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.zip.Inflater;
/*    */ import net.minecraft.server.v1_7_R4.PacketDataSerializer;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.ByteToMessageDecoder;
/*    */ 
/*    */ 
/*    */ public class SpigotDecompressor
/*    */   extends ByteToMessageDecoder
/*    */ {
/* 15 */   private final Inflater inflater = new Inflater();
/*    */ 
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> objects) throws Exception {
/* 20 */     if (byteBuf.readableBytes() == 0) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 25 */     PacketDataSerializer serializer = new PacketDataSerializer(byteBuf);
/* 26 */     int size = serializer.a();
/* 27 */     if (size == 0) {
/*    */       
/* 29 */       objects.add(serializer.readBytes(serializer.readableBytes()));
/*    */     } else {
/*    */       
/* 32 */       byte[] compressedData = new byte[serializer.readableBytes()];
/* 33 */       serializer.readBytes(compressedData);
/* 34 */       this.inflater.setInput(compressedData);
/*    */       
/* 36 */       byte[] data = new byte[size];
/* 37 */       this.inflater.inflate(data);
/* 38 */       objects.add(Unpooled.wrappedBuffer(data));
/* 39 */       this.inflater.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\SpigotDecompressor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */