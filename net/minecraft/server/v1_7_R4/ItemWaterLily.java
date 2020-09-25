/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlockState;
/*    */ 
/*    */ public class ItemWaterLily extends ItemWithAuxData {
/*    */   public ItemWaterLily(Block block) {
/*  6 */     super(block, false);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 10 */     MovingObjectPosition movingobjectposition = a(world, entityhuman, true);
/*    */     
/* 12 */     if (movingobjectposition == null) {
/* 13 */       return itemstack;
/*    */     }
/* 15 */     if (movingobjectposition.type == EnumMovingObjectType.BLOCK) {
/* 16 */       int i = movingobjectposition.b;
/* 17 */       int j = movingobjectposition.c;
/* 18 */       int k = movingobjectposition.d;
/*    */       
/* 20 */       if (!world.a(entityhuman, i, j, k)) {
/* 21 */         return itemstack;
/*    */       }
/*    */       
/* 24 */       if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
/* 25 */         return itemstack;
/*    */       }
/*    */       
/* 28 */       if (world.getType(i, j, k).getMaterial() == Material.WATER && world.getData(i, j, k) == 0 && world.isEmpty(i, j + 1, k)) {
/*    */         
/* 30 */         CraftBlockState craftBlockState = CraftBlockState.getBlockState(world, i, j + 1, k);
/* 31 */         world.setTypeUpdate(i, j + 1, k, Blocks.WATER_LILY);
/* 32 */         BlockPlaceEvent placeEvent = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, (BlockState)craftBlockState, i, j, k);
/* 33 */         if (placeEvent != null && (placeEvent.isCancelled() || !placeEvent.canBuild())) {
/* 34 */           craftBlockState.update(true, false);
/* 35 */           return itemstack;
/*    */         } 
/*    */ 
/*    */         
/* 39 */         if (!entityhuman.abilities.canInstantlyBuild) {
/* 40 */           itemstack.count--;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemWaterLily.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */