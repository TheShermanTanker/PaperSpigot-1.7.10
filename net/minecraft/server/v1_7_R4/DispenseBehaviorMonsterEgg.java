/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ final class DispenseBehaviorMonsterEgg extends DispenseBehaviorItem {
/*    */   public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 13 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/* 14 */     double d0 = isourceblock.getX() + enumfacing.getAdjacentX();
/* 15 */     double d1 = (isourceblock.getBlockY() + 0.2F);
/* 16 */     double d2 = isourceblock.getZ() + enumfacing.getAdjacentZ();
/*    */ 
/*    */     
/* 19 */     World world = isourceblock.k();
/* 20 */     ItemStack itemstack1 = itemstack.a(1);
/* 21 */     Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
/* 22 */     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*    */     
/* 24 */     BlockDispenseEvent event = new BlockDispenseEvent(block, (ItemStack)craftItem.clone(), new Vector(d0, d1, d2));
/* 25 */     if (!BlockDispenser.eventFired) {
/* 26 */       world.getServer().getPluginManager().callEvent((Event)event);
/*    */     }
/*    */     
/* 29 */     if (event.isCancelled()) {
/* 30 */       itemstack.count++;
/* 31 */       return itemstack;
/*    */     } 
/*    */     
/* 34 */     if (!event.getItem().equals(craftItem)) {
/* 35 */       itemstack.count++;
/*    */       
/* 37 */       ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 38 */       IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
/* 39 */       if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
/* 40 */         idispensebehavior.a(isourceblock, eventStack);
/* 41 */         return itemstack;
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     itemstack1 = CraftItemStack.asNMSCopy(event.getItem());
/*    */     
/* 47 */     Entity entity = ItemMonsterEgg.spawnCreature(isourceblock.k(), itemstack.getData(), event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), CreatureSpawnEvent.SpawnReason.DISPENSE_EGG);
/*    */     
/* 49 */     if (entity instanceof EntityLiving && itemstack.hasName()) {
/* 50 */       ((EntityInsentient)entity).setCustomName(itemstack.getName());
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 55 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorMonsterEgg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */