/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSnow
/*    */   extends ItemBlockWithAuxData
/*    */ {
/*    */   public ItemSnow(Block paramBlock1, Block paramBlock2) {
/* 11 */     super(paramBlock1, paramBlock2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 16 */     if (paramItemStack.count == 0) return false; 
/* 17 */     if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) return false;
/*    */     
/* 19 */     Block block = paramWorld.getType(paramInt1, paramInt2, paramInt3);
/* 20 */     if (block == Blocks.SNOW) {
/* 21 */       int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 22 */       int j = i & 0x7;
/*    */       
/* 24 */       if (j <= 6 && 
/* 25 */         paramWorld.b(this.block.a(paramWorld, paramInt1, paramInt2, paramInt3)) && paramWorld.setData(paramInt1, paramInt2, paramInt3, j + 1 | i & 0xFFFFFFF8, 2)) {
/* 26 */         paramWorld.makeSound((paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume1() + 1.0F) / 2.0F, this.block.stepSound.getVolume2() * 0.8F);
/* 27 */         paramItemStack.count--;
/* 28 */         return true;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 33 */     return super.interactWith(paramItemStack, paramEntityHuman, paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemSnow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */