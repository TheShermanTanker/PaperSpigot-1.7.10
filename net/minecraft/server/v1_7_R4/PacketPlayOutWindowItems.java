/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class PacketPlayOutWindowItems
/*    */   extends Packet {
/*    */   public int a;
/*    */   public ItemStack[] b;
/*    */   
/*    */   public PacketPlayOutWindowItems() {}
/*    */   
/*    */   public PacketPlayOutWindowItems(int i, List<ItemStack> list) {
/* 13 */     this.a = i;
/* 14 */     this.b = new ItemStack[list.size()];
/*    */     
/* 16 */     for (int j = 0; j < this.b.length; j++) {
/* 17 */       ItemStack itemstack = list.get(j);
/*    */       
/* 19 */       this.b[j] = (itemstack == null) ? null : itemstack.cloneItemStack();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 24 */     this.a = packetdataserializer.readUnsignedByte();
/* 25 */     short short1 = packetdataserializer.readShort();
/*    */     
/* 27 */     this.b = new ItemStack[short1];
/*    */     
/* 29 */     for (int i = 0; i < short1; i++) {
/* 30 */       this.b[i] = packetdataserializer.c();
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 35 */     packetdataserializer.writeByte(this.a);
/* 36 */     packetdataserializer.writeShort(this.b.length);
/* 37 */     ItemStack[] aitemstack = this.b;
/* 38 */     int i = aitemstack.length;
/*    */     
/* 40 */     for (int j = 0; j < i; j++) {
/* 41 */       ItemStack itemstack = aitemstack[j];
/*    */       
/* 43 */       packetdataserializer.a(itemstack);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 48 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 52 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutWindowItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */