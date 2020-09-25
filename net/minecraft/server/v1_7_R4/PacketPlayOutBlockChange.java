/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.spigotmc.SpigotDebreakifier;
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutBlockChange
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   
/*    */   public PacketPlayOutBlockChange(int i, int j, int k, World world) {
/* 14 */     this.a = i;
/* 15 */     this.b = j;
/* 16 */     this.c = k;
/* 17 */     this.block = world.getType(i, j, k);
/* 18 */     this.data = world.getData(i, j, k);
/*    */   } public Block block; public int data;
/*    */   public PacketPlayOutBlockChange() {}
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 22 */     this.a = packetdataserializer.readInt();
/* 23 */     this.b = packetdataserializer.readUnsignedByte();
/* 24 */     this.c = packetdataserializer.readInt();
/* 25 */     this.block = Block.getById(packetdataserializer.a());
/* 26 */     this.data = packetdataserializer.readUnsignedByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 31 */     if (packetdataserializer.version < 25) {
/*    */       
/* 33 */       packetdataserializer.writeInt(this.a);
/* 34 */       packetdataserializer.writeByte(this.b);
/* 35 */       packetdataserializer.writeInt(this.c);
/* 36 */       packetdataserializer.b(Block.getId(this.block));
/* 37 */       packetdataserializer.writeByte(this.data);
/*    */     } else {
/*    */       
/* 40 */       packetdataserializer.writePosition(this.a, this.b, this.c);
/* 41 */       int id = Block.getId(this.block);
/* 42 */       this.data = SpigotDebreakifier.getCorrectedData(id, this.data);
/* 43 */       packetdataserializer.b(id << 4 | this.data);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 49 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 53 */     return String.format("type=%d, data=%d, x=%d, y=%d, z=%d", new Object[] { Integer.valueOf(Block.getId(this.block)), Integer.valueOf(this.data), Integer.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(this.c) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 57 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutBlockChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */