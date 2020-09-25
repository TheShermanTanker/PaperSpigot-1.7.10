/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class BiomeHell
/*    */   extends BiomeBase
/*    */ {
/*    */   public BiomeHell(int paramInt) {
/*  7 */     super(paramInt);
/*    */     
/*  9 */     this.as.clear();
/* 10 */     this.at.clear();
/* 11 */     this.au.clear();
/* 12 */     this.av.clear();
/*    */     
/* 14 */     this.as.add(new BiomeMeta(EntityGhast.class, 50, 4, 4));
/* 15 */     this.as.add(new BiomeMeta(EntityPigZombie.class, 100, 4, 4));
/* 16 */     this.as.add(new BiomeMeta(EntityMagmaCube.class, 1, 4, 4));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */