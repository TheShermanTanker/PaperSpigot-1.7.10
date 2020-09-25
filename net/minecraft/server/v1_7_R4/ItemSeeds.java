/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSeeds
/*    */   extends Item
/*    */ {
/*    */   private Block block;
/*    */   private Block b;
/*    */   
/*    */   public ItemSeeds(Block paramBlock1, Block paramBlock2) {
/* 12 */     this.block = paramBlock1;
/* 13 */     this.b = paramBlock2;
/* 14 */     a(CreativeModeTab.l);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 19 */     if (paramInt4 != 1) return false;
/*    */     
/* 21 */     if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack) || !paramEntityHuman.a(paramInt1, paramInt2 + 1, paramInt3, paramInt4, paramItemStack)) return false;
/*    */     
/* 23 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == this.b && paramWorld.isEmpty(paramInt1, paramInt2 + 1, paramInt3)) {
/* 24 */       paramWorld.setTypeUpdate(paramInt1, paramInt2 + 1, paramInt3, this.block);
/* 25 */       paramItemStack.count--;
/* 26 */       return true;
/*    */     } 
/* 28 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemSeeds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */