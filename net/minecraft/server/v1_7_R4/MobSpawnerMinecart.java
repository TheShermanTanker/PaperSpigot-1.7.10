/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MobSpawnerMinecart
/*    */   extends MobSpawnerAbstract
/*    */ {
/*    */   MobSpawnerMinecart(EntityMinecartMobSpawner paramEntityMinecartMobSpawner) {}
/*    */   
/*    */   public void a(int paramInt) {
/* 16 */     this.a.world.broadcastEntityEffect(this.a, (byte)paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public World a() {
/* 21 */     return this.a.world;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b() {
/* 26 */     return MathHelper.floor(this.a.locX);
/*    */   }
/*    */ 
/*    */   
/*    */   public int c() {
/* 31 */     return MathHelper.floor(this.a.locY);
/*    */   }
/*    */ 
/*    */   
/*    */   public int d() {
/* 36 */     return MathHelper.floor(this.a.locZ);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MobSpawnerMinecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */