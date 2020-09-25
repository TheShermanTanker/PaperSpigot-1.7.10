/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBed
/*    */   extends Item
/*    */ {
/*    */   public ItemBed() {
/* 12 */     a(CreativeModeTab.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 17 */     if (paramWorld.isStatic) return true;
/*    */     
/* 19 */     if (paramInt4 != 1) {
/* 20 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 24 */     paramInt2++;
/*    */     
/* 26 */     BlockBed blockBed = (BlockBed)Blocks.BED;
/*    */     
/* 28 */     int i = MathHelper.floor((paramEntityHuman.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 29 */     byte b1 = 0;
/* 30 */     byte b2 = 0;
/*    */     
/* 32 */     if (i == 0) b2 = 1; 
/* 33 */     if (i == 1) b1 = -1; 
/* 34 */     if (i == 2) b2 = -1; 
/* 35 */     if (i == 3) b1 = 1;
/*    */     
/* 37 */     if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack) || !paramEntityHuman.a(paramInt1 + b1, paramInt2, paramInt3 + b2, paramInt4, paramItemStack)) return false;
/*    */     
/* 39 */     if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3) && paramWorld.isEmpty(paramInt1 + b1, paramInt2, paramInt3 + b2) && World.a(paramWorld, paramInt1, paramInt2 - 1, paramInt3) && World.a(paramWorld, paramInt1 + b1, paramInt2 - 1, paramInt3 + b2)) {
/*    */       
/* 41 */       paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, blockBed, i, 3);
/*    */       
/* 43 */       if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == blockBed) {
/* 44 */         paramWorld.setTypeAndData(paramInt1 + b1, paramInt2, paramInt3 + b2, blockBed, i + 8, 3);
/*    */       }
/*    */       
/* 47 */       paramItemStack.count--;
/* 48 */       return true;
/*    */     } 
/*    */     
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemBed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */