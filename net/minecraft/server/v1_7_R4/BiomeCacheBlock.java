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
/*    */ 
/*    */ 
/*    */ public class BiomeCacheBlock
/*    */ {
/* 18 */   public float[] a = new float[256];
/* 19 */   public BiomeBase[] b = new BiomeBase[256];
/*    */   
/*    */   public int c;
/*    */   
/*    */   public BiomeCacheBlock(BiomeCache paramBiomeCache, int paramInt1, int paramInt2) {
/* 24 */     this.c = paramInt1;
/* 25 */     this.d = paramInt2;
/* 26 */     BiomeCache.a(paramBiomeCache).getWetness(this.a, paramInt1 << 4, paramInt2 << 4, 16, 16);
/* 27 */     BiomeCache.a(paramBiomeCache).a(this.b, paramInt1 << 4, paramInt2 << 4, 16, 16, false);
/*    */   }
/*    */   public int d; public long e;
/*    */   public BiomeBase a(int paramInt1, int paramInt2) {
/* 31 */     return this.b[paramInt1 & 0xF | (paramInt2 & 0xF) << 4];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeCacheBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */