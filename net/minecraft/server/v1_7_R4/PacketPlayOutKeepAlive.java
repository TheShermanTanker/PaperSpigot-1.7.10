/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutKeepAlive
/*    */   extends Packet {
/*    */   private int a;
/*    */   
/*    */   public PacketPlayOutKeepAlive() {}
/*    */   
/*    */   public PacketPlayOutKeepAlive(int i) {
/* 10 */     this.a = i;
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 14 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 18 */     this.a = packetdataserializer.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 23 */     if (packetdataserializer.version >= 32) {
/*    */       
/* 25 */       packetdataserializer.b(this.a);
/*    */     } else {
/*    */       
/* 28 */       packetdataserializer.writeInt(this.a);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 34 */     return true;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 38 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutKeepAlive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */