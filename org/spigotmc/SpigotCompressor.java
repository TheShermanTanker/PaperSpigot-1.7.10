/*    */ package org.spigotmc;
/*    */ 
/*    */ import java.util.zip.Deflater;
/*    */ import net.minecraft.server.v1_7_R4.PacketDataSerializer;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder;
/*    */ 
/*    */ 
/*    */ public class SpigotCompressor
/*    */   extends MessageToByteEncoder
/*    */ {
/* 13 */   private final byte[] buffer = new byte[8192];
/* 14 */   private final Deflater deflater = new Deflater();
/*    */ 
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
/* 19 */     ByteBuf in = (ByteBuf)msg;
/* 20 */     int origSize = in.readableBytes();
/* 21 */     PacketDataSerializer serializer = new PacketDataSerializer(out);
/*    */     
/* 23 */     if (origSize < 256) {
/*    */       
/* 25 */       serializer.b(0);
/* 26 */       serializer.writeBytes(in);
/*    */     } else {
/*    */       
/* 29 */       byte[] data = new byte[origSize];
/* 30 */       in.readBytes(data);
/*    */       
/* 32 */       serializer.b(data.length);
/*    */       
/* 34 */       this.deflater.setInput(data);
/* 35 */       this.deflater.finish();
/* 36 */       while (!this.deflater.finished()) {
/* 37 */         int count = this.deflater.deflate(this.buffer);
/* 38 */         serializer.writeBytes(this.buffer, 0, count);
/*    */       } 
/* 40 */       this.deflater.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\SpigotCompressor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */