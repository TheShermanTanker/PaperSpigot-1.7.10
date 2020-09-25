/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
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
/*    */ public class BlockStep
/*    */   extends BlockStepAbstract
/*    */ {
/* 20 */   public static final String[] b = new String[] { "stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick", "netherBrick", "quartz" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockStep(boolean paramBoolean) {
/* 27 */     super(paramBoolean, Material.STONE);
/* 28 */     a(CreativeModeTab.b);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 66 */     return Item.getItemOf(Blocks.STEP);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack j(int paramInt) {
/* 71 */     return new ItemStack(Item.getItemOf(Blocks.STEP), 2, paramInt & 0x7);
/*    */   }
/*    */ 
/*    */   
/*    */   public String b(int paramInt) {
/* 76 */     if (paramInt < 0 || paramInt >= b.length) {
/* 77 */       paramInt = 0;
/*    */     }
/* 79 */     return a() + "." + b[paramInt];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockStep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */