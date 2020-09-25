/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ public class PacketPlayInCustomPayload
/*    */   extends Packet
/*    */ {
/*    */   private String tag;
/*    */   public int length;
/*    */   private byte[] data;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 14 */     this.tag = packetdataserializer.c(20);
/*    */     
/* 16 */     if (packetdataserializer.version < 29) {
/*    */       
/* 18 */       this.length = packetdataserializer.readShort();
/*    */     } else {
/*    */       
/* 21 */       this.length = packetdataserializer.readableBytes();
/*    */     } 
/*    */     
/* 24 */     if (this.length > 0 && this.length < 32767) {
/* 25 */       this.data = new byte[this.length];
/* 26 */       packetdataserializer.readBytes(this.data);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 31 */     packetdataserializer.a(this.tag);
/* 32 */     packetdataserializer.writeShort((short)this.length);
/* 33 */     if (this.data != null) {
/* 34 */       packetdataserializer.writeBytes(this.data);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 39 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public String c() {
/* 43 */     return this.tag;
/*    */   }
/*    */   
/*    */   public byte[] e() {
/* 47 */     return this.data;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 51 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInCustomPayload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */