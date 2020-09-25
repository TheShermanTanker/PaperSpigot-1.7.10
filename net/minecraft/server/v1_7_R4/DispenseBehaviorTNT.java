/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ final class DispenseBehaviorTNT extends DispenseBehaviorItem {
/*    */   protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 13 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/* 14 */     World world = isourceblock.k();
/* 15 */     int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
/* 16 */     int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
/* 17 */     int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
/*    */ 
/*    */     
/* 20 */     ItemStack itemstack1 = itemstack.a(1);
/* 21 */     Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
/* 22 */     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*    */     
/* 24 */     BlockDispenseEvent event = new BlockDispenseEvent(block, (ItemStack)craftItem.clone(), new Vector(i + 0.5D, j + 0.5D, k + 0.5D));
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
/*    */     
/* 46 */     Location loc = new Location((World)world.getWorld(), event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
/* 47 */     EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(loc, world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), (EntityLiving)null);
/*    */ 
/*    */ 
/*    */     
/* 51 */     world.addEntity(entitytntprimed);
/*    */     
/* 53 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorTNT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */