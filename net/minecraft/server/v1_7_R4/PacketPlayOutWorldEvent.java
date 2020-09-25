/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutWorldEvent
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private int e;
/*    */   private boolean f;
/*    */   
/*    */   public PacketPlayOutWorldEvent() {}
/*    */   
/*    */   public PacketPlayOutWorldEvent(int i, int j, int k, int l, int i1, boolean flag) {
/* 15 */     this.a = i;
/* 16 */     this.c = j;
/* 17 */     this.d = k;
/* 18 */     this.e = l;
/* 19 */     this.b = i1;
/* 20 */     this.f = flag;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 24 */     this.a = packetdataserializer.readInt();
/* 25 */     this.c = packetdataserializer.readInt();
/* 26 */     this.d = packetdataserializer.readByte() & 0xFF;
/* 27 */     this.e = packetdataserializer.readInt();
/* 28 */     this.b = packetdataserializer.readInt();
/* 29 */     this.f = packetdataserializer.readBoolean();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 33 */     packetdataserializer.writeInt(this.a);
/*    */     
/* 35 */     if (packetdataserializer.version < 16) {
/*    */       
/* 37 */       packetdataserializer.writeInt(this.c);
/* 38 */       packetdataserializer.writeByte(this.d & 0xFF);
/* 39 */       packetdataserializer.writeInt(this.e);
/*    */     } else {
/*    */       
/* 42 */       packetdataserializer.writePosition(this.c, this.d, this.e);
/*    */     } 
/*    */     
/* 45 */     packetdataserializer.writeInt(this.b);
/* 46 */     packetdataserializer.writeBoolean(this.f);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 50 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 54 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutWorldEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */