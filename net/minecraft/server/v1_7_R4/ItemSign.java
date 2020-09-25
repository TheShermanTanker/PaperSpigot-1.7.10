/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSign
/*    */   extends Item
/*    */ {
/*    */   public ItemSign() {
/* 12 */     this.maxStackSize = 16;
/* 13 */     a(CreativeModeTab.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 18 */     if (paramInt4 == 0) return false; 
/* 19 */     if (!paramWorld.getType(paramInt1, paramInt2, paramInt3).getMaterial().isBuildable()) return false;
/*    */     
/* 21 */     if (paramInt4 == 1) paramInt2++;
/*    */     
/* 23 */     if (paramInt4 == 2) paramInt3--; 
/* 24 */     if (paramInt4 == 3) paramInt3++; 
/* 25 */     if (paramInt4 == 4) paramInt1--; 
/* 26 */     if (paramInt4 == 5) paramInt1++;
/*    */     
/* 28 */     if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) return false; 
/* 29 */     if (!Blocks.SIGN_POST.canPlace(paramWorld, paramInt1, paramInt2, paramInt3)) return false;
/*    */     
/* 31 */     if (paramWorld.isStatic) {
/* 32 */       return true;
/*    */     }
/*    */     
/* 35 */     if (paramInt4 == 1) {
/* 36 */       int i = MathHelper.floor(((paramEntityHuman.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 0xF;
/* 37 */       paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.SIGN_POST, i, 3);
/*    */     } else {
/* 39 */       paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.WALL_SIGN, paramInt4, 3);
/*    */     } 
/*    */     
/* 42 */     paramItemStack.count--;
/* 43 */     TileEntitySign tileEntitySign = (TileEntitySign)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 44 */     if (tileEntitySign != null) paramEntityHuman.a(tileEntitySign); 
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */