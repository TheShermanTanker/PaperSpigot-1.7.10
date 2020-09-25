/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutBed
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   
/*    */   public PacketPlayOutBed() {}
/*    */   
/*    */   public PacketPlayOutBed(EntityHuman entityhuman, int i, int j, int k) {
/* 13 */     this.b = i;
/* 14 */     this.c = j;
/* 15 */     this.d = k;
/* 16 */     this.a = entityhuman.getId();
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 20 */     this.a = packetdataserializer.readInt();
/* 21 */     this.b = packetdataserializer.readInt();
/* 22 */     this.c = packetdataserializer.readByte();
/* 23 */     this.d = packetdataserializer.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 28 */     if (packetdataserializer.version < 16) {
/*    */       
/* 30 */       packetdataserializer.writeInt(this.a);
/* 31 */       packetdataserializer.writeInt(this.b);
/* 32 */       packetdataserializer.writeByte(this.c);
/* 33 */       packetdataserializer.writeInt(this.d);
/*    */     } else {
/*    */       
/* 36 */       packetdataserializer.b(this.a);
/* 37 */       packetdataserializer.writePosition(this.b, this.c, this.d);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 43 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 47 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutBed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */