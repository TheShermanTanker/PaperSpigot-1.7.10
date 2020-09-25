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
/*    */ 
/*    */ public class PacketPlayOutSpawnEntityWeather
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private int e;
/*    */   
/*    */   public PacketPlayOutSpawnEntityWeather() {}
/*    */   
/*    */   public PacketPlayOutSpawnEntityWeather(Entity paramEntity) {
/* 26 */     this.a = paramEntity.getId();
/* 27 */     this.b = MathHelper.floor(paramEntity.locX * 32.0D);
/* 28 */     this.c = MathHelper.floor(paramEntity.locY * 32.0D);
/* 29 */     this.d = MathHelper.floor(paramEntity.locZ * 32.0D);
/* 30 */     if (paramEntity instanceof EntityLightning) {
/* 31 */       this.e = 1;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 37 */     this.a = paramPacketDataSerializer.a();
/* 38 */     this.e = paramPacketDataSerializer.readByte();
/* 39 */     this.b = paramPacketDataSerializer.readInt();
/* 40 */     this.c = paramPacketDataSerializer.readInt();
/* 41 */     this.d = paramPacketDataSerializer.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 46 */     paramPacketDataSerializer.b(this.a);
/* 47 */     paramPacketDataSerializer.writeByte(this.e);
/* 48 */     paramPacketDataSerializer.writeInt(this.b);
/* 49 */     paramPacketDataSerializer.writeInt(this.c);
/* 50 */     paramPacketDataSerializer.writeInt(this.d);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 55 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 60 */     return String.format("id=%d, type=%d, x=%.2f, y=%.2f, z=%.2f", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.e), Float.valueOf(this.b / 32.0F), Float.valueOf(this.c / 32.0F), Float.valueOf(this.d / 32.0F) });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutSpawnEntityWeather.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */