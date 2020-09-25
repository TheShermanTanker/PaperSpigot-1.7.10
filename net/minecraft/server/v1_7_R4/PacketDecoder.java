/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.com.google.common.collect.BiMap;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
/*    */ 
/*    */ public class PacketDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/* 17 */   private static final Logger a = LogManager.getLogger();
/* 18 */   private static final Marker b = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.b);
/*    */   private final NetworkStatistics c;
/*    */   
/*    */   public PacketDecoder(NetworkStatistics networkstatistics) {
/* 22 */     this.c = networkstatistics;
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, List<Packet> list) throws IOException {
/* 26 */     int i = bytebuf.readableBytes();
/*    */     
/* 28 */     if (i != 0) {
/* 29 */       PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf, NetworkManager.getVersion(channelhandlercontext.channel()));
/* 30 */       int j = packetdataserializer.a();
/* 31 */       Packet packet = Packet.a((BiMap)channelhandlercontext.channel().attr(NetworkManager.e).get(), j);
/*    */       
/* 33 */       if (packet == null) {
/* 34 */         throw new IOException("Bad packet id " + j);
/*    */       }
/* 36 */       packet.a(packetdataserializer);
/* 37 */       if (packetdataserializer.readableBytes() > 0) {
/* 38 */         throw new IOException("Packet was larger than I expected, found " + packetdataserializer.readableBytes() + " bytes extra whilst reading packet " + j);
/*    */       }
/* 40 */       list.add(packet);
/* 41 */       this.c.a(j, i);
/* 42 */       if (a.isDebugEnabled())
/* 43 */         a.debug(b, " IN: [{}:{}] {}[{}]", new Object[] { channelhandlercontext.channel().attr(NetworkManager.d).get(), Integer.valueOf(j), packet.getClass().getName(), packet.b() }); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */