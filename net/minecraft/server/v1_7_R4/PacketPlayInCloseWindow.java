/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayInCloseWindow
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   
/*    */   public PacketPlayInCloseWindow() {}
/*    */   
/*    */   public PacketPlayInCloseWindow(int id) {
/* 11 */     this.a = id;
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 15 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 19 */     this.a = packetdataserializer.readByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 23 */     packetdataserializer.writeByte(this.a);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 27 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInCloseWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */