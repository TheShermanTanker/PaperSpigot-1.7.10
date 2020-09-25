/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemHoe
/*    */   extends Item
/*    */ {
/*    */   protected EnumToolMaterial a;
/*    */   
/*    */   public ItemHoe(EnumToolMaterial paramEnumToolMaterial) {
/* 13 */     this.a = paramEnumToolMaterial;
/* 14 */     this.maxStackSize = 1;
/* 15 */     setMaxDurability(paramEnumToolMaterial.a());
/* 16 */     a(CreativeModeTab.i);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 21 */     if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) return false;
/*    */     
/* 23 */     Block block = paramWorld.getType(paramInt1, paramInt2, paramInt3);
/* 24 */     if (paramInt4 != 0 && paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3).getMaterial() == Material.AIR && (block == Blocks.GRASS || block == Blocks.DIRT)) {
/* 25 */       Block block1 = Blocks.SOIL;
/* 26 */       paramWorld.makeSound((paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), block1.stepSound.getStepSound(), (block1.stepSound.getVolume1() + 1.0F) / 2.0F, block1.stepSound.getVolume2() * 0.8F);
/*    */       
/* 28 */       if (paramWorld.isStatic) return true; 
/* 29 */       paramWorld.setTypeUpdate(paramInt1, paramInt2, paramInt3, block1);
/* 30 */       paramItemStack.damage(1, paramEntityHuman);
/*    */       
/* 32 */       return true;
/*    */     } 
/*    */     
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String i() {
/* 44 */     return this.a.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemHoe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */