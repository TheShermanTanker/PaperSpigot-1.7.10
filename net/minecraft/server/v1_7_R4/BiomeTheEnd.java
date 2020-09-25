/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class BiomeTheEnd
/*    */   extends BiomeBase
/*    */ {
/*    */   public BiomeTheEnd(int paramInt) {
/*  8 */     super(paramInt);
/*    */     
/* 10 */     this.as.clear();
/* 11 */     this.at.clear();
/* 12 */     this.au.clear();
/* 13 */     this.av.clear();
/*    */     
/* 15 */     this.as.add(new BiomeMeta(EntityEnderman.class, 10, 4, 4));
/* 16 */     this.ai = Blocks.DIRT;
/* 17 */     this.ak = Blocks.DIRT;
/*    */     
/* 19 */     this.ar = new BiomeTheEndDecorator();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeTheEnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */