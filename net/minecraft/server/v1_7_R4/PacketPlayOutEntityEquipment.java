/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutEntityEquipment
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   private ItemStack c;
/*    */   
/*    */   public PacketPlayOutEntityEquipment() {}
/*    */   
/*    */   public PacketPlayOutEntityEquipment(int i, int j, ItemStack itemstack) {
/* 12 */     this.a = i;
/* 13 */     this.b = j;
/* 14 */     this.c = (itemstack == null) ? null : itemstack.cloneItemStack();
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 18 */     this.a = packetdataserializer.readInt();
/* 19 */     this.b = packetdataserializer.readShort();
/* 20 */     this.c = packetdataserializer.c();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 25 */     if (packetdataserializer.version < 16) {
/*    */       
/* 27 */       packetdataserializer.writeInt(this.a);
/*    */     } else {
/* 29 */       packetdataserializer.b(this.a);
/*    */     } 
/*    */     
/* 32 */     packetdataserializer.writeShort(this.b);
/* 33 */     packetdataserializer.a(this.c);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 37 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 41 */     return String.format("entity=%d, slot=%d, item=%s", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b), this.c });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 45 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutEntityEquipment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */