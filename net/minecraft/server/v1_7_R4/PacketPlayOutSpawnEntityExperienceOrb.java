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
/*    */ public class PacketPlayOutSpawnEntityExperienceOrb
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private int e;
/*    */   
/*    */   public PacketPlayOutSpawnEntityExperienceOrb() {}
/*    */   
/*    */   public PacketPlayOutSpawnEntityExperienceOrb(EntityExperienceOrb paramEntityExperienceOrb) {
/* 23 */     this.a = paramEntityExperienceOrb.getId();
/* 24 */     this.b = MathHelper.floor(paramEntityExperienceOrb.locX * 32.0D);
/* 25 */     this.c = MathHelper.floor(paramEntityExperienceOrb.locY * 32.0D);
/* 26 */     this.d = MathHelper.floor(paramEntityExperienceOrb.locZ * 32.0D);
/* 27 */     this.e = paramEntityExperienceOrb.e();
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 32 */     this.a = paramPacketDataSerializer.a();
/* 33 */     this.b = paramPacketDataSerializer.readInt();
/* 34 */     this.c = paramPacketDataSerializer.readInt();
/* 35 */     this.d = paramPacketDataSerializer.readInt();
/* 36 */     this.e = paramPacketDataSerializer.readShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 41 */     paramPacketDataSerializer.b(this.a);
/* 42 */     paramPacketDataSerializer.writeInt(this.b);
/* 43 */     paramPacketDataSerializer.writeInt(this.c);
/* 44 */     paramPacketDataSerializer.writeInt(this.d);
/* 45 */     paramPacketDataSerializer.writeShort(this.e);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 50 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 55 */     return String.format("id=%d, value=%d, x=%.2f, y=%.2f, z=%.2f", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.e), Float.valueOf(this.b / 32.0F), Float.valueOf(this.c / 32.0F), Float.valueOf(this.d / 32.0F) });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutSpawnEntityExperienceOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */