/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldProviderHell
/*    */   extends WorldProvider
/*    */ {
/*    */   public void b() {
/* 12 */     this.e = new WorldChunkManagerHell(BiomeBase.HELL, 0.0F);
/* 13 */     this.f = true;
/* 14 */     this.g = true;
/* 15 */     this.dimension = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void a() {
/* 25 */     float f = 0.1F;
/* 26 */     for (byte b = 0; b <= 15; b++) {
/* 27 */       float f1 = 1.0F - b / 15.0F;
/* 28 */       this.h[b] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public IChunkProvider getChunkProvider() {
/* 34 */     return new ChunkProviderHell(this.b, this.b.getSeed());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSpawn(int paramInt1, int paramInt2) {
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public float a(long paramLong, float paramFloat) {
/* 49 */     return 0.5F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean e() {
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 64 */     return "Nether";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldProviderHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */