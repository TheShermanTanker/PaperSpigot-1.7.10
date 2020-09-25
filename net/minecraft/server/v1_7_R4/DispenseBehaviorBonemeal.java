/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ final class DispenseBehaviorBonemeal
/*    */   extends DispenseBehaviorItem {
/*    */   private boolean b = true;
/*    */   
/*    */   protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 15 */     if (itemstack.getData() == 15) {
/* 16 */       EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/* 17 */       World world = isourceblock.k();
/* 18 */       int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
/* 19 */       int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
/* 20 */       int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
/*    */ 
/*    */       
/* 23 */       Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
/* 24 */       CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
/*    */       
/* 26 */       BlockDispenseEvent event = new BlockDispenseEvent(block, (ItemStack)craftItem.clone(), new Vector(0, 0, 0));
/* 27 */       if (!BlockDispenser.eventFired) {
/* 28 */         world.getServer().getPluginManager().callEvent((Event)event);
/*    */       }
/*    */       
/* 31 */       if (event.isCancelled()) {
/* 32 */         return itemstack;
/*    */       }
/*    */       
/* 35 */       if (!event.getItem().equals(craftItem)) {
/*    */         
/* 37 */         ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 38 */         IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
/* 39 */         if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
/* 40 */           idispensebehavior.a(isourceblock, eventStack);
/* 41 */           return itemstack;
/*    */         } 
/*    */       } 
/*    */ 
/*    */       
/* 46 */       if (ItemDye.a(itemstack, world, i, j, k)) {
/* 47 */         if (!world.isStatic) {
/* 48 */           world.triggerEffect(2005, i, j, k, 0);
/*    */         }
/*    */       } else {
/* 51 */         this.b = false;
/*    */       } 
/*    */       
/* 54 */       return itemstack;
/*    */     } 
/* 56 */     return super.b(isourceblock, itemstack);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(ISourceBlock isourceblock) {
/* 61 */     if (this.b) {
/* 62 */       isourceblock.k().triggerEffect(1000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
/*    */     } else {
/* 64 */       isourceblock.k().triggerEffect(1001, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorBonemeal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */