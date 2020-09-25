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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockPotatoes
/*    */   extends BlockCrops
/*    */ {
/*    */   protected Item i() {
/* 25 */     return Items.POTATO;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item P() {
/* 30 */     return Items.POTATO;
/*    */   }
/*    */ 
/*    */   
/*    */   public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5) {
/* 35 */     super.dropNaturally(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat, paramInt5);
/*    */     
/* 37 */     if (paramWorld.isStatic) {
/*    */       return;
/*    */     }
/* 40 */     if (paramInt4 >= 7 && 
/* 41 */       paramWorld.random.nextInt(50) == 0)
/* 42 */       a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Items.POTATO_POISON)); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPotatoes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */