/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutBlockAction
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private int e;
/*    */   private Block f;
/*    */   
/*    */   public PacketPlayOutBlockAction() {}
/*    */   
/*    */   public PacketPlayOutBlockAction(int i, int j, int k, Block block, int l, int i1) {
/* 15 */     this.a = i;
/* 16 */     this.b = j;
/* 17 */     this.c = k;
/* 18 */     this.d = l;
/* 19 */     this.e = i1;
/* 20 */     this.f = block;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 24 */     this.a = packetdataserializer.readInt();
/* 25 */     this.b = packetdataserializer.readShort();
/* 26 */     this.c = packetdataserializer.readInt();
/* 27 */     this.d = packetdataserializer.readUnsignedByte();
/* 28 */     this.e = packetdataserializer.readUnsignedByte();
/* 29 */     this.f = Block.getById(packetdataserializer.a() & 0xFFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 34 */     if (packetdataserializer.version < 16) {
/*    */       
/* 36 */       packetdataserializer.writeInt(this.a);
/* 37 */       packetdataserializer.writeShort(this.b);
/* 38 */       packetdataserializer.writeInt(this.c);
/*    */     } else {
/*    */       
/* 41 */       packetdataserializer.writePosition(this.a, this.b, this.c);
/*    */     } 
/*    */     
/* 44 */     packetdataserializer.writeByte(this.d);
/* 45 */     packetdataserializer.writeByte(this.e);
/* 46 */     packetdataserializer.b(Block.getId(this.f) & 0xFFF);
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutBlockAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */