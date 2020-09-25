/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class PacketPlayInWindowClick
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   public int slot;
/*    */   private int button;
/*    */   private short d;
/*    */   private ItemStack item;
/*    */   private int shift;
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 15 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 19 */     this.a = packetdataserializer.readByte();
/* 20 */     this.slot = packetdataserializer.readShort();
/* 21 */     this.button = packetdataserializer.readByte();
/* 22 */     this.d = packetdataserializer.readShort();
/* 23 */     this.shift = packetdataserializer.readByte();
/* 24 */     this.item = packetdataserializer.c();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 28 */     packetdataserializer.writeByte(this.a);
/* 29 */     packetdataserializer.writeShort(this.slot);
/* 30 */     packetdataserializer.writeByte(this.button);
/* 31 */     packetdataserializer.writeShort(this.d);
/* 32 */     packetdataserializer.writeByte(this.shift);
/* 33 */     packetdataserializer.a(this.item);
/*    */   }
/*    */   
/*    */   public String b() {
/* 37 */     return (this.item != null) ? String.format("id=%d, slot=%d, button=%d, type=%d, itemid=%d, itemcount=%d, itemaux=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.slot), Integer.valueOf(this.button), Integer.valueOf(this.shift), Integer.valueOf(Item.getId(this.item.getItem())), Integer.valueOf(this.item.count), Integer.valueOf(this.item.getData()) }) : String.format("id=%d, slot=%d, button=%d, type=%d, itemid=-1", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.slot), Integer.valueOf(this.button), Integer.valueOf(this.shift) });
/*    */   }
/*    */   
/*    */   public int c() {
/* 41 */     return this.a;
/*    */   }
/*    */   
/*    */   public int d() {
/* 45 */     return this.slot;
/*    */   }
/*    */   
/*    */   public int e() {
/* 49 */     return this.button;
/*    */   }
/*    */   
/*    */   public short f() {
/* 53 */     return this.d;
/*    */   }
/*    */   
/*    */   public ItemStack g() {
/* 57 */     return this.item;
/*    */   }
/*    */   
/*    */   public int h() {
/* 61 */     return this.shift;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 65 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInWindowClick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */