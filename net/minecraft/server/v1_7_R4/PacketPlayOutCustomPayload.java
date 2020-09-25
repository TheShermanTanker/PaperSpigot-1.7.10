/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public class PacketPlayOutCustomPayload
/*    */   extends Packet
/*    */ {
/*    */   private String tag;
/*    */   private byte[] data;
/*    */   
/*    */   public PacketPlayOutCustomPayload() {}
/*    */   
/*    */   public PacketPlayOutCustomPayload(String s, ByteBuf bytebuf) {
/* 15 */     this(s, bytebuf.array());
/*    */   }
/*    */   
/*    */   public PacketPlayOutCustomPayload(String s, byte[] abyte) {
/* 19 */     this.tag = s;
/* 20 */     this.data = abyte;
/* 21 */     if (abyte.length >= 1048576) {
/* 22 */       throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 27 */     this.tag = packetdataserializer.c(20);
/* 28 */     this.data = new byte[packetdataserializer.readUnsignedShort()];
/* 29 */     packetdataserializer.readBytes(this.data);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 33 */     packetdataserializer.a(this.tag);
/*    */     
/* 35 */     if (packetdataserializer.version < 29)
/*    */     {
/* 37 */       packetdataserializer.writeShort(this.data.length);
/*    */     }
/* 39 */     if (packetdataserializer.version >= 47 && this.tag.equals("MC|Brand")) {
/*    */       
/* 41 */       packetdataserializer.a(new String(this.data, "UTF-8"));
/*    */       return;
/*    */     } 
/* 44 */     packetdataserializer.writeBytes(this.data);
/* 45 */     if (packetdataserializer.version >= 29 && this.tag.equals("MC|AdvCdm"))
/*    */     {
/* 47 */       packetdataserializer.writeBoolean(true);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 53 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 57 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutCustomPayload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */