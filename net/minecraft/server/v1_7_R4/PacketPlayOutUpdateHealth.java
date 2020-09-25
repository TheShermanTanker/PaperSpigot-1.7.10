/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutUpdateHealth
/*    */   extends Packet {
/*    */   private float a;
/*    */   private int b;
/*    */   private float c;
/*    */   
/*    */   public PacketPlayOutUpdateHealth() {}
/*    */   
/*    */   public PacketPlayOutUpdateHealth(float f, int i, float f1) {
/* 12 */     this.a = f;
/* 13 */     this.b = i;
/* 14 */     this.c = f1;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 18 */     this.a = packetdataserializer.readFloat();
/* 19 */     this.b = packetdataserializer.readShort();
/* 20 */     this.c = packetdataserializer.readFloat();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 24 */     packetdataserializer.writeFloat(this.a);
/*    */     
/* 26 */     if (packetdataserializer.version < 16) {
/*    */       
/* 28 */       packetdataserializer.writeShort(this.b);
/*    */     } else {
/*    */       
/* 31 */       packetdataserializer.b(this.b);
/*    */     } 
/*    */     
/* 34 */     packetdataserializer.writeFloat(this.c);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 38 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 42 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutUpdateHealth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */