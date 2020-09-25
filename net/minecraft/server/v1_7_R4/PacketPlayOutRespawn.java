/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutRespawn
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private EnumDifficulty b;
/*    */   private EnumGamemode c;
/*    */   private WorldType d;
/*    */   
/*    */   public PacketPlayOutRespawn() {}
/*    */   
/*    */   public PacketPlayOutRespawn(int paramInt, EnumDifficulty paramEnumDifficulty, WorldType paramWorldType, EnumGamemode paramEnumGamemode) {
/* 24 */     this.a = paramInt;
/* 25 */     this.b = paramEnumDifficulty;
/* 26 */     this.c = paramEnumGamemode;
/* 27 */     this.d = paramWorldType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 32 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 37 */     this.a = paramPacketDataSerializer.readInt();
/* 38 */     this.b = EnumDifficulty.getById(paramPacketDataSerializer.readUnsignedByte());
/* 39 */     this.c = EnumGamemode.getById(paramPacketDataSerializer.readUnsignedByte());
/* 40 */     this.d = WorldType.getType(paramPacketDataSerializer.c(16));
/* 41 */     if (this.d == null) {
/* 42 */       this.d = WorldType.NORMAL;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 48 */     paramPacketDataSerializer.writeInt(this.a);
/* 49 */     paramPacketDataSerializer.writeByte(this.b.a());
/* 50 */     paramPacketDataSerializer.writeByte(this.c.getId());
/* 51 */     paramPacketDataSerializer.a(this.d.name());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutRespawn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */