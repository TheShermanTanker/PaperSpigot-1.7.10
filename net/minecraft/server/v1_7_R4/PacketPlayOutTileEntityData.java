/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutTileEntityData
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private NBTTagCompound e;
/*    */   
/*    */   public PacketPlayOutTileEntityData() {}
/*    */   
/*    */   public PacketPlayOutTileEntityData(int i, int j, int k, int l, NBTTagCompound nbttagcompound) {
/* 14 */     this.a = i;
/* 15 */     this.b = j;
/* 16 */     this.c = k;
/* 17 */     this.d = l;
/* 18 */     this.e = nbttagcompound;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 22 */     this.a = packetdataserializer.readInt();
/* 23 */     this.b = packetdataserializer.readShort();
/* 24 */     this.c = packetdataserializer.readInt();
/* 25 */     this.d = packetdataserializer.readUnsignedByte();
/* 26 */     this.e = packetdataserializer.b();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 31 */     if (packetdataserializer.version < 16) {
/*    */       
/* 33 */       packetdataserializer.writeInt(this.a);
/* 34 */       packetdataserializer.writeShort(this.b);
/* 35 */       packetdataserializer.writeInt(this.c);
/*    */     } else {
/*    */       
/* 38 */       packetdataserializer.writePosition(this.a, this.b, this.c);
/*    */     } 
/*    */     
/* 41 */     packetdataserializer.writeByte((byte)this.d);
/* 42 */     packetdataserializer.a(this.e);
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutTileEntityData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */