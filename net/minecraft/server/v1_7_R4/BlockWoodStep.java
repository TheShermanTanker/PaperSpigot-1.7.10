/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockWoodStep
/*    */   extends BlockStepAbstract
/*    */ {
/* 11 */   public static final String[] b = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockWoodStep(boolean paramBoolean) {
/* 16 */     super(paramBoolean, Material.WOOD);
/* 17 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 27 */     return Item.getItemOf(Blocks.WOOD_STEP);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack j(int paramInt) {
/* 32 */     return new ItemStack(Item.getItemOf(Blocks.WOOD_STEP), 2, paramInt & 0x7);
/*    */   }
/*    */ 
/*    */   
/*    */   public String b(int paramInt) {
/* 37 */     if (paramInt < 0 || paramInt >= b.length) {
/* 38 */       paramInt = 0;
/*    */     }
/* 40 */     return a() + "." + b[paramInt];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockWoodStep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */