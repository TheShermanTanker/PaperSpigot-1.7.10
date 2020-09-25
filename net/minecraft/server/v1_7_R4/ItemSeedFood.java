/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSeedFood
/*    */   extends ItemFood
/*    */ {
/*    */   private Block b;
/*    */   private Block c;
/*    */   
/*    */   public ItemSeedFood(int paramInt, float paramFloat, Block paramBlock1, Block paramBlock2) {
/* 14 */     super(paramInt, paramFloat, false);
/*    */     
/* 16 */     this.b = paramBlock1;
/* 17 */     this.c = paramBlock2;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 22 */     if (paramInt4 != 1) return false;
/*    */     
/* 24 */     if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack) || !paramEntityHuman.a(paramInt1, paramInt2 + 1, paramInt3, paramInt4, paramItemStack)) return false;
/*    */     
/* 26 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == this.c && paramWorld.isEmpty(paramInt1, paramInt2 + 1, paramInt3)) {
/* 27 */       paramWorld.setTypeUpdate(paramInt1, paramInt2 + 1, paramInt3, this.b);
/* 28 */       paramItemStack.count--;
/* 29 */       return true;
/*    */     } 
/* 31 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemSeedFood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */