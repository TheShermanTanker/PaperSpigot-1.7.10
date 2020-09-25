/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ final class DispenseBehaviorEmptyBucket extends DispenseBehaviorItem {
/* 10 */   private final DispenseBehaviorItem b = new DispenseBehaviorItem();
/*    */ 
/*    */   
/*    */   public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/*    */     Item item;
/* 15 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/* 16 */     World world = isourceblock.k();
/* 17 */     int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
/* 18 */     int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
/* 19 */     int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
/* 20 */     Material material = world.getType(i, j, k).getMaterial();
/* 21 */     int l = world.getData(i, j, k);
/*    */ 
/*    */     
/* 24 */     if (Material.WATER.equals(material) && l == 0) {
/* 25 */       item = Items.WATER_BUCKET;
/*    */     } else {
/* 27 */       if (!Material.LAVA.equals(material) || l != 0) {
/* 28 */         return super.b(isourceblock, itemstack);
/*    */       }
/*    */       
/* 31 */       item = Items.LAVA_BUCKET;
/*    */     } 
/*    */ 
/*    */     
/* 35 */     Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
/* 36 */     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
/*    */     
/* 38 */     BlockDispenseEvent event = new BlockDispenseEvent(block, (ItemStack)craftItem.clone(), new Vector(i, j, k));
/* 39 */     if (!BlockDispenser.eventFired) {
/* 40 */       world.getServer().getPluginManager().callEvent((Event)event);
/*    */     }
/*    */     
/* 43 */     if (event.isCancelled()) {
/* 44 */       return itemstack;
/*    */     }
/*    */     
/* 47 */     if (!event.getItem().equals(craftItem)) {
/*    */       
/* 49 */       ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 50 */       IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
/* 51 */       if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
/* 52 */         idispensebehavior.a(isourceblock, eventStack);
/* 53 */         return itemstack;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 58 */     world.setAir(i, j, k);
/* 59 */     if (--itemstack.count == 0) {
/* 60 */       itemstack.setItem(item);
/* 61 */       itemstack.count = 1;
/* 62 */     } else if (((TileEntityDispenser)isourceblock.getTileEntity()).addItem(new ItemStack(item)) < 0) {
/* 63 */       this.b.a(isourceblock, new ItemStack(item));
/*    */     } 
/*    */     
/* 66 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorEmptyBucket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */