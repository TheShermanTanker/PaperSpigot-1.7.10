/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemReed
/*    */   extends Item
/*    */ {
/*    */   private Block block;
/*    */   
/*    */   public ItemReed(Block paramBlock) {
/* 14 */     this.block = paramBlock;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 19 */     Block block = paramWorld.getType(paramInt1, paramInt2, paramInt3);
/* 20 */     if (block == Blocks.SNOW && (paramWorld.getData(paramInt1, paramInt2, paramInt3) & 0x7) < 1) {
/* 21 */       paramInt4 = 1;
/* 22 */     } else if (block != Blocks.VINE && block != Blocks.LONG_GRASS && block != Blocks.DEAD_BUSH) {
/*    */       
/* 24 */       if (paramInt4 == 0) paramInt2--; 
/* 25 */       if (paramInt4 == 1) paramInt2++; 
/* 26 */       if (paramInt4 == 2) paramInt3--; 
/* 27 */       if (paramInt4 == 3) paramInt3++; 
/* 28 */       if (paramInt4 == 4) paramInt1--; 
/* 29 */       if (paramInt4 == 5) paramInt1++;
/*    */     
/*    */     } 
/* 32 */     if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) return false; 
/* 33 */     if (paramItemStack.count == 0) return false;
/*    */     
/* 35 */     if (paramWorld.mayPlace(this.block, paramInt1, paramInt2, paramInt3, false, paramInt4, null, paramItemStack)) {
/* 36 */       int i = this.block.getPlacedData(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3, 0);
/* 37 */       if (paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.block, i, 3)) {
/*    */ 
/*    */ 
/*    */         
/* 41 */         if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == this.block) {
/* 42 */           this.block.postPlace(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman, paramItemStack);
/* 43 */           this.block.postPlace(paramWorld, paramInt1, paramInt2, paramInt3, i);
/*    */         } 
/* 45 */         paramWorld.makeSound((paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume1() + 1.0F) / 2.0F, this.block.stepSound.getVolume2() * 0.8F);
/* 46 */         paramItemStack.count--;
/*    */       } 
/*    */     } 
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemReed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */