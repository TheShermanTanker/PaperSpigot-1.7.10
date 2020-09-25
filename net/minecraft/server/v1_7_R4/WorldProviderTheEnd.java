/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldProviderTheEnd
/*    */   extends WorldProvider
/*    */ {
/*    */   public void b() {
/* 13 */     this.e = new WorldChunkManagerHell(BiomeBase.SKY, 0.0F);
/* 14 */     this.dimension = 1;
/* 15 */     this.g = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public IChunkProvider getChunkProvider() {
/* 20 */     return new ChunkProviderTheEnd(this.b, this.b.getSeed());
/*    */   }
/*    */ 
/*    */   
/*    */   public float a(long paramLong, float paramFloat) {
/* 25 */     return 0.0F;
/*    */   }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean e() {
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canSpawn(int paramInt1, int paramInt2) {
/* 72 */     return this.b.b(paramInt1, paramInt2).getMaterial().isSolid();
/*    */   }
/*    */ 
/*    */   
/*    */   public ChunkCoordinates h() {
/* 77 */     return new ChunkCoordinates(100, 50, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSeaLevel() {
/* 82 */     return 50;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 92 */     return "The End";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldProviderTheEnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */