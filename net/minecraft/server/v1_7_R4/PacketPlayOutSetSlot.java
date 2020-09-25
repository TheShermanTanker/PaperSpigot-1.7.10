/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutSetSlot
/*    */   extends Packet {
/*    */   public int a;
/*    */   public int b;
/*    */   private ItemStack c;
/*    */   
/*    */   public PacketPlayOutSetSlot() {}
/*    */   
/*    */   public PacketPlayOutSetSlot(int i, int j, ItemStack itemstack) {
/* 12 */     this.a = i;
/* 13 */     this.b = j;
/* 14 */     this.c = (itemstack == null) ? null : itemstack.cloneItemStack();
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 18 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 22 */     this.a = packetdataserializer.readByte();
/* 23 */     this.b = packetdataserializer.readShort();
/* 24 */     this.c = packetdataserializer.c();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 28 */     packetdataserializer.writeByte(this.a);
/* 29 */     packetdataserializer.writeShort(this.b);
/* 30 */     packetdataserializer.a(this.c);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 34 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutSetSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */