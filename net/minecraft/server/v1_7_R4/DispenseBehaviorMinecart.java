/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ final class DispenseBehaviorMinecart extends DispenseBehaviorItem {
/* 10 */   private final DispenseBehaviorItem b = new DispenseBehaviorItem();
/*    */ 
/*    */   
/*    */   public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/*    */     double d3;
/* 15 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/* 16 */     World world = isourceblock.k();
/* 17 */     double d0 = isourceblock.getX() + (enumfacing.getAdjacentX() * 1.125F);
/* 18 */     double d1 = isourceblock.getY() + (enumfacing.getAdjacentY() * 1.125F);
/* 19 */     double d2 = isourceblock.getZ() + (enumfacing.getAdjacentZ() * 1.125F);
/* 20 */     int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
/* 21 */     int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
/* 22 */     int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
/* 23 */     Block block = world.getType(i, j, k);
/*    */ 
/*    */     
/* 26 */     if (BlockMinecartTrackAbstract.a(block)) {
/* 27 */       d3 = 0.0D;
/*    */     } else {
/* 29 */       if (block.getMaterial() != Material.AIR || !BlockMinecartTrackAbstract.a(world.getType(i, j - 1, k))) {
/* 30 */         return this.b.a(isourceblock, itemstack);
/*    */       }
/*    */       
/* 33 */       d3 = -1.0D;
/*    */     } 
/*    */ 
/*    */     
/* 37 */     ItemStack itemstack1 = itemstack.a(1);
/* 38 */     Block block2 = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
/* 39 */     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*    */     
/* 41 */     BlockDispenseEvent event = new BlockDispenseEvent(block2, (ItemStack)craftItem.clone(), new Vector(d0, d1 + d3, d2));
/* 42 */     if (!BlockDispenser.eventFired) {
/* 43 */       world.getServer().getPluginManager().callEvent((Event)event);
/*    */     }
/*    */     
/* 46 */     if (event.isCancelled()) {
/* 47 */       itemstack.count++;
/* 48 */       return itemstack;
/*    */     } 
/*    */     
/* 51 */     if (!event.getItem().equals(craftItem)) {
/* 52 */       itemstack.count++;
/*    */       
/* 54 */       ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 55 */       IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
/* 56 */       if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
/* 57 */         idispensebehavior.a(isourceblock, eventStack);
/* 58 */         return itemstack;
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     itemstack1 = CraftItemStack.asNMSCopy(event.getItem());
/* 63 */     EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), ((ItemMinecart)itemstack1.getItem()).a);
/*    */ 
/*    */     
/* 66 */     if (itemstack.hasName()) {
/* 67 */       entityminecartabstract.a(itemstack.getName());
/*    */     }
/*    */     
/* 70 */     world.addEntity(entityminecartabstract);
/*    */     
/* 72 */     return itemstack;
/*    */   }
/*    */   
/*    */   protected void a(ISourceBlock isourceblock) {
/* 76 */     isourceblock.k().triggerEffect(1000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorMinecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */