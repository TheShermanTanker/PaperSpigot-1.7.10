/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlockState;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.BlockIgniteEvent;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ public class ItemFlintAndSteel extends Item {
/*    */   public ItemFlintAndSteel() {
/* 11 */     this.maxStackSize = 1;
/* 12 */     setMaxDurability(64);
/* 13 */     a(CreativeModeTab.i);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/* 17 */     int clickedX = i, clickedY = j, clickedZ = k;
/* 18 */     if (l == 0) {
/* 19 */       j--;
/*    */     }
/*    */     
/* 22 */     if (l == 1) {
/* 23 */       j++;
/*    */     }
/*    */     
/* 26 */     if (l == 2) {
/* 27 */       k--;
/*    */     }
/*    */     
/* 30 */     if (l == 3) {
/* 31 */       k++;
/*    */     }
/*    */     
/* 34 */     if (l == 4) {
/* 35 */       i--;
/*    */     }
/*    */     
/* 38 */     if (l == 5) {
/* 39 */       i++;
/*    */     }
/*    */     
/* 42 */     if (!entityhuman.a(i, j, k, l, itemstack)) {
/* 43 */       return false;
/*    */     }
/* 45 */     if (world.getType(i, j, k).getMaterial() == Material.AIR) {
/*    */       
/* 47 */       if (CraftEventFactory.callBlockIgniteEvent(world, i, j, k, BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, entityhuman).isCancelled()) {
/* 48 */         itemstack.damage(1, entityhuman);
/* 49 */         return false;
/*    */       } 
/*    */       
/* 52 */       CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
/*    */ 
/*    */       
/* 55 */       world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "fire.ignite", 1.0F, g.nextFloat() * 0.4F + 0.8F);
/* 56 */       world.setTypeUpdate(i, j, k, Blocks.FIRE);
/*    */ 
/*    */       
/* 59 */       BlockPlaceEvent placeEvent = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, (BlockState)blockState, clickedX, clickedY, clickedZ);
/*    */       
/* 61 */       if (placeEvent.isCancelled() || !placeEvent.canBuild()) {
/* 62 */         placeEvent.getBlockPlaced().setTypeIdAndData(0, (byte)0, false);
/* 63 */         return false;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 68 */     itemstack.damage(1, entityhuman);
/* 69 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemFlintAndSteel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */