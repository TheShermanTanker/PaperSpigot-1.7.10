/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ final class DispenseBehaviorFlintAndSteel extends DispenseBehaviorItem {
/*    */   private boolean b = true;
/*    */   
/*    */   protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 15 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/* 16 */     World world = isourceblock.k();
/* 17 */     int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
/* 18 */     int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
/* 19 */     int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
/*    */ 
/*    */     
/* 22 */     Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
/* 23 */     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
/*    */     
/* 25 */     BlockDispenseEvent event = new BlockDispenseEvent(block, (ItemStack)craftItem.clone(), new Vector(0, 0, 0));
/* 26 */     if (!BlockDispenser.eventFired) {
/* 27 */       world.getServer().getPluginManager().callEvent((Event)event);
/*    */     }
/*    */     
/* 30 */     if (event.isCancelled()) {
/* 31 */       return itemstack;
/*    */     }
/*    */     
/* 34 */     if (!event.getItem().equals(craftItem)) {
/*    */       
/* 36 */       ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 37 */       IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
/* 38 */       if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
/* 39 */         idispensebehavior.a(isourceblock, eventStack);
/* 40 */         return itemstack;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 45 */     if (world.isEmpty(i, j, k)) {
/*    */       
/* 47 */       if (!CraftEventFactory.callBlockIgniteEvent(world, i, j, k, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ()).isCancelled()) {
/* 48 */         world.setTypeUpdate(i, j, k, Blocks.FIRE);
/* 49 */         if (itemstack.isDamaged(1, world.random)) {
/* 50 */           itemstack.count = 0;
/*    */         }
/*    */       }
/*    */     
/* 54 */     } else if (world.getType(i, j, k) == Blocks.TNT) {
/* 55 */       Blocks.TNT.postBreak(world, i, j, k, 1);
/* 56 */       world.setAir(i, j, k);
/*    */     } else {
/* 58 */       this.b = false;
/*    */     } 
/*    */     
/* 61 */     return itemstack;
/*    */   }
/*    */   
/*    */   protected void a(ISourceBlock isourceblock) {
/* 65 */     if (this.b) {
/* 66 */       isourceblock.k().triggerEffect(1000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
/*    */     } else {
/* 68 */       isourceblock.k().triggerEffect(1001, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorFlintAndSteel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */