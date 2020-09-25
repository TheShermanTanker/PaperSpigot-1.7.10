/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeJungle
/*    */   extends BiomeBase
/*    */ {
/*    */   private boolean aC;
/*    */   
/*    */   public BiomeJungle(int paramInt, boolean paramBoolean) {
/* 15 */     super(paramInt);
/* 16 */     this.aC = paramBoolean;
/* 17 */     if (paramBoolean) {
/* 18 */       this.ar.x = 2;
/*    */     } else {
/* 20 */       this.ar.x = 50;
/*    */     } 
/* 22 */     this.ar.z = 25;
/* 23 */     this.ar.y = 4;
/*    */     
/* 25 */     if (!paramBoolean) {
/* 26 */       this.as.add(new BiomeMeta(EntityOcelot.class, 2, 1, 1));
/*    */     }
/*    */ 
/*    */     
/* 30 */     this.at.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom) {
/* 35 */     if (paramRandom.nextInt(10) == 0) {
/* 36 */       return this.aA;
/*    */     }
/* 38 */     if (paramRandom.nextInt(2) == 0) {
/* 39 */       return new WorldGenGroundBush(3, 0);
/*    */     }
/* 41 */     if (!this.aC && paramRandom.nextInt(3) == 0) {
/* 42 */       return new WorldGenJungleTree(false, 10, 20, 3, 3);
/*    */     }
/* 44 */     return new WorldGenTrees(false, 4 + paramRandom.nextInt(7), 3, 3, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenerator b(Random paramRandom) {
/* 49 */     if (paramRandom.nextInt(4) == 0) {
/* 50 */       return new WorldGenGrass(Blocks.LONG_GRASS, 2);
/*    */     }
/* 52 */     return new WorldGenGrass(Blocks.LONG_GRASS, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 57 */     super.a(paramWorld, paramRandom, paramInt1, paramInt2);
/*    */ 
/*    */     
/* 60 */     int i = paramInt1 + paramRandom.nextInt(16) + 8;
/* 61 */     int j = paramInt2 + paramRandom.nextInt(16) + 8;
/* 62 */     int k = paramRandom.nextInt(paramWorld.getHighestBlockYAt(i, j) * 2);
/* 63 */     (new WorldGenMelon()).generate(paramWorld, paramRandom, i, k, j);
/*    */ 
/*    */     
/* 66 */     WorldGenVines worldGenVines = new WorldGenVines();
/*    */     
/* 68 */     for (j = 0; j < 50; j++) {
/* 69 */       k = paramInt1 + paramRandom.nextInt(16) + 8;
/* 70 */       char c = '';
/* 71 */       int m = paramInt2 + paramRandom.nextInt(16) + 8;
/* 72 */       worldGenVines.generate(paramWorld, paramRandom, k, c, m);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeJungle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */