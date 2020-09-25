/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutSpawnPosition
/*    */   extends Packet {
/*    */   public int x;
/*    */   public int y;
/*    */   public int z;
/*    */   
/*    */   public PacketPlayOutSpawnPosition() {}
/*    */   
/*    */   public PacketPlayOutSpawnPosition(int i, int j, int k) {
/* 12 */     this.x = i;
/* 13 */     this.y = j;
/* 14 */     this.z = k;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 18 */     this.x = packetdataserializer.readInt();
/* 19 */     this.y = packetdataserializer.readInt();
/* 20 */     this.z = packetdataserializer.readInt();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 24 */     if (packetdataserializer.version < 16) {
/*    */       
/* 26 */       packetdataserializer.writeInt(this.x);
/* 27 */       packetdataserializer.writeInt(this.y);
/* 28 */       packetdataserializer.writeInt(this.z);
/*    */     }
/*    */     else {
/*    */       
/* 32 */       packetdataserializer.writePosition(this.x, this.y, this.z);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 37 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 41 */     return false;
/*    */   }
/*    */   
/*    */   public String b() {
/* 45 */     return String.format("x=%d, y=%d, z=%d", new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 49 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutSpawnPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */