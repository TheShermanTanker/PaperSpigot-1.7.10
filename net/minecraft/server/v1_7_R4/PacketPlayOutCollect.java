/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutCollect
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public PacketPlayOutCollect() {}
/*    */   
/*    */   public PacketPlayOutCollect(int i, int j) {
/* 11 */     this.a = i;
/* 12 */     this.b = j;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 16 */     this.a = packetdataserializer.readInt();
/* 17 */     this.b = packetdataserializer.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 22 */     if (packetdataserializer.version < 16) {
/*    */       
/* 24 */       packetdataserializer.writeInt(this.a);
/* 25 */       packetdataserializer.writeInt(this.b);
/*    */     } else {
/*    */       
/* 28 */       packetdataserializer.b(this.a);
/* 29 */       packetdataserializer.b(this.b);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 35 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 39 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutCollect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */