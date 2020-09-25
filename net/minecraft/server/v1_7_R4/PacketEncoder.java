/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.util.com.google.common.collect.BiMap;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
/*    */ 
/*    */ public class PacketEncoder
/*    */   extends MessageToByteEncoder
/*    */ {
/* 16 */   private static final Logger a = LogManager.getLogger();
/* 17 */   private static final Marker b = MarkerManager.getMarker("PACKET_SENT", NetworkManager.b);
/*    */   private final NetworkStatistics c;
/*    */   
/*    */   public PacketEncoder(NetworkStatistics networkstatistics) {
/* 21 */     this.c = networkstatistics;
/*    */   }
/*    */   
/*    */   protected void a(ChannelHandlerContext channelhandlercontext, Packet packet, ByteBuf bytebuf) throws IOException {
/* 25 */     Integer integer = (Integer)((BiMap)channelhandlercontext.channel().attr(NetworkManager.f).get()).inverse().get(packet.getClass());
/*    */     
/* 27 */     if (a.isDebugEnabled()) {
/* 28 */       a.debug(b, "OUT: [{}:{}] {}[{}]", new Object[] { channelhandlercontext.channel().attr(NetworkManager.d).get(), integer, packet.getClass().getName(), packet.b() });
/*    */     }
/*    */     
/* 31 */     if (integer == null) {
/* 32 */       throw new IOException("Can't serialize unregistered packet");
/*    */     }
/* 34 */     PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf, NetworkManager.getVersion(channelhandlercontext.channel()));
/*    */     
/* 36 */     packetdataserializer.b(integer.intValue());
/* 37 */     packet.b(packetdataserializer);
/* 38 */     this.c.b(integer.intValue(), packetdataserializer.readableBytes());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext channelhandlercontext, Object object, ByteBuf bytebuf) throws IOException {
/* 43 */     a(channelhandlercontext, (Packet)object, bytebuf);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */