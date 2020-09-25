/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MobSpawner
/*    */   extends MobSpawnerAbstract
/*    */ {
/*    */   MobSpawner(TileEntityMobSpawner paramTileEntityMobSpawner) {}
/*    */   
/*    */   public void a(int paramInt) {
/* 14 */     this.a.world.playBlockAction(this.a.x, this.a.y, this.a.z, Blocks.MOB_SPAWNER, paramInt, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public World a() {
/* 19 */     return this.a.world;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b() {
/* 24 */     return this.a.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public int c() {
/* 29 */     return this.a.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public int d() {
/* 34 */     return this.a.z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(TileEntityMobSpawnerData paramTileEntityMobSpawnerData) {
/* 39 */     super.a(paramTileEntityMobSpawnerData);
/* 40 */     if (a() != null) a().notify(this.a.x, this.a.y, this.a.z); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MobSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */