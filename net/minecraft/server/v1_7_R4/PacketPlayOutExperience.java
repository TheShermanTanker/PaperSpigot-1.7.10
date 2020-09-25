/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutExperience
/*    */   extends Packet {
/*    */   private float a;
/*    */   private int b;
/*    */   private int c;
/*    */   
/*    */   public PacketPlayOutExperience() {}
/*    */   
/*    */   public PacketPlayOutExperience(float f, int i, int j) {
/* 12 */     this.a = f;
/* 13 */     this.b = i;
/* 14 */     this.c = j;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 18 */     this.a = packetdataserializer.readFloat();
/* 19 */     this.c = packetdataserializer.readShort();
/* 20 */     this.b = packetdataserializer.readShort();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 24 */     packetdataserializer.writeFloat(this.a);
/*    */     
/* 26 */     if (packetdataserializer.version < 16) {
/*    */       
/* 28 */       packetdataserializer.writeShort(this.c);
/* 29 */       packetdataserializer.writeShort(this.b);
/*    */     } else {
/*    */       
/* 32 */       packetdataserializer.b(this.c);
/* 33 */       packetdataserializer.b(this.b);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 39 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 43 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutExperience.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */