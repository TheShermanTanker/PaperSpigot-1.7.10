/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockLightStone
/*    */   extends Block
/*    */ {
/*    */   public BlockLightStone(Material paramMaterial) {
/* 12 */     super(paramMaterial);
/* 13 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDropCount(int paramInt, Random paramRandom) {
/* 18 */     return MathHelper.a(a(paramRandom) + paramRandom.nextInt(paramInt + 1), 1, 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(Random paramRandom) {
/* 23 */     return 2 + paramRandom.nextInt(3);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 28 */     return Items.GLOWSTONE_DUST;
/*    */   }
/*    */ 
/*    */   
/*    */   public MaterialMapColor f(int paramInt) {
/* 33 */     return MaterialMapColor.d;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockLightStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */