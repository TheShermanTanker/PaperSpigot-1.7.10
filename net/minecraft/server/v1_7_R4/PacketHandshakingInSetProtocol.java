/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ public class PacketHandshakingInSetProtocol
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   public String b;
/*    */   public int c;
/*    */   private EnumProtocol d;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 15 */     this.a = packetdataserializer.a();
/* 16 */     this.b = packetdataserializer.c(32767);
/* 17 */     this.c = packetdataserializer.readUnsignedShort();
/* 18 */     this.d = EnumProtocol.a(packetdataserializer.a());
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 22 */     packetdataserializer.b(this.a);
/* 23 */     packetdataserializer.a(this.b);
/* 24 */     packetdataserializer.writeShort(this.c);
/* 25 */     packetdataserializer.b(this.d.c());
/*    */   }
/*    */   
/*    */   public void a(PacketHandshakingInListener packethandshakinginlistener) {
/* 29 */     packethandshakinginlistener.a(this);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 33 */     return true;
/*    */   }
/*    */   
/*    */   public EnumProtocol c() {
/* 37 */     return this.d;
/*    */   }
/*    */   
/*    */   public int d() {
/* 41 */     return this.a;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 45 */     a((PacketHandshakingInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketHandshakingInSetProtocol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */