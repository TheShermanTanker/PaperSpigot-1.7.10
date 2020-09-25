/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemRedstone
/*    */   extends Item
/*    */ {
/*    */   public ItemRedstone() {
/*  9 */     a(CreativeModeTab.d);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 14 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3) != Blocks.SNOW) {
/* 15 */       if (paramInt4 == 0) paramInt2--; 
/* 16 */       if (paramInt4 == 1) paramInt2++; 
/* 17 */       if (paramInt4 == 2) paramInt3--; 
/* 18 */       if (paramInt4 == 3) paramInt3++; 
/* 19 */       if (paramInt4 == 4) paramInt1--; 
/* 20 */       if (paramInt4 == 5) paramInt1++; 
/* 21 */       if (!paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) return false; 
/*    */     } 
/* 23 */     if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) return false; 
/* 24 */     if (Blocks.REDSTONE_WIRE.canPlace(paramWorld, paramInt1, paramInt2, paramInt3)) {
/* 25 */       paramItemStack.count--;
/* 26 */       paramWorld.setTypeUpdate(paramInt1, paramInt2, paramInt3, Blocks.REDSTONE_WIRE);
/*    */     } 
/*    */     
/* 29 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemRedstone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */