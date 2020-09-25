/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSnowBlock
/*    */   extends Block
/*    */ {
/*    */   protected BlockSnowBlock() {
/* 11 */     super(Material.SNOW_BLOCK);
/* 12 */     a(true);
/* 13 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 18 */     return Items.SNOW_BALL;
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(Random paramRandom) {
/* 23 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/* 28 */     if (paramWorld.b(EnumSkyBlock.BLOCK, paramInt1, paramInt2, paramInt3) > 11) {
/* 29 */       b(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3), 0);
/* 30 */       paramWorld.setAir(paramInt1, paramInt2, paramInt3);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockSnowBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */