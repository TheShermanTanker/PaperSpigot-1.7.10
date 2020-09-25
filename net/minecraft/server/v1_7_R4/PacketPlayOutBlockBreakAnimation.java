/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutBlockBreakAnimation
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private int e;
/*    */   
/*    */   public PacketPlayOutBlockBreakAnimation() {}
/*    */   
/*    */   public PacketPlayOutBlockBreakAnimation(int i, int j, int k, int l, int i1) {
/* 14 */     this.a = i;
/* 15 */     this.b = j;
/* 16 */     this.c = k;
/* 17 */     this.d = l;
/* 18 */     this.e = i1;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 22 */     this.a = packetdataserializer.a();
/* 23 */     this.b = packetdataserializer.readInt();
/* 24 */     this.c = packetdataserializer.readInt();
/* 25 */     this.d = packetdataserializer.readInt();
/* 26 */     this.e = packetdataserializer.readUnsignedByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 30 */     packetdataserializer.b(this.a);
/*    */     
/* 32 */     if (packetdataserializer.version < 16) {
/*    */       
/* 34 */       packetdataserializer.writeInt(this.b);
/* 35 */       packetdataserializer.writeInt(this.c);
/* 36 */       packetdataserializer.writeInt(this.d);
/*    */     } else {
/*    */       
/* 39 */       packetdataserializer.writePosition(this.b, this.c, this.d);
/*    */     } 
/*    */     
/* 42 */     packetdataserializer.writeByte(this.e);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 46 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 50 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutBlockBreakAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */