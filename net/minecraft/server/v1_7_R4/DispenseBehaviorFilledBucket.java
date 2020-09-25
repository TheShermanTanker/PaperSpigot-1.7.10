/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ final class DispenseBehaviorFilledBucket extends DispenseBehaviorItem {
/* 10 */   private final DispenseBehaviorItem b = new DispenseBehaviorItem();
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 15 */     ItemBucket itembucket = (ItemBucket)itemstack.getItem();
/* 16 */     int i = isourceblock.getBlockX();
/* 17 */     int j = isourceblock.getBlockY();
/* 18 */     int k = isourceblock.getBlockZ();
/* 19 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/*    */ 
/*    */     
/* 22 */     World world = isourceblock.k();
/* 23 */     int x = i + enumfacing.getAdjacentX();
/* 24 */     int y = j + enumfacing.getAdjacentY();
/* 25 */     int z = k + enumfacing.getAdjacentZ();
/* 26 */     if (world.isEmpty(x, y, z) || !world.getType(x, y, z).getMaterial().isBuildable()) {
/* 27 */       Block block = world.getWorld().getBlockAt(i, j, k);
/* 28 */       CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
/*    */       
/* 30 */       BlockDispenseEvent event = new BlockDispenseEvent(block, (ItemStack)craftItem.clone(), new Vector(x, y, z));
/* 31 */       if (!BlockDispenser.eventFired) {
/* 32 */         world.getServer().getPluginManager().callEvent((Event)event);
/*    */       }
/*    */       
/* 35 */       if (event.isCancelled()) {
/* 36 */         return itemstack;
/*    */       }
/*    */       
/* 39 */       if (!event.getItem().equals(craftItem)) {
/*    */         
/* 41 */         ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 42 */         IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
/* 43 */         if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
/* 44 */           idispensebehavior.a(isourceblock, eventStack);
/* 45 */           return itemstack;
/*    */         } 
/*    */       } 
/*    */       
/* 49 */       itembucket = (ItemBucket)CraftItemStack.asNMSCopy(event.getItem()).getItem();
/*    */     } 
/*    */ 
/*    */     
/* 53 */     if (itembucket.a(isourceblock.k(), i + enumfacing.getAdjacentX(), j + enumfacing.getAdjacentY(), k + enumfacing.getAdjacentZ())) {
/*    */       
/* 55 */       Item item = Items.BUCKET;
/* 56 */       if (--itemstack.count == 0) {
/* 57 */         itemstack.setItem(Items.BUCKET);
/* 58 */         itemstack.count = 1;
/* 59 */       } else if (((TileEntityDispenser)isourceblock.getTileEntity()).addItem(new ItemStack(item)) < 0) {
/* 60 */         this.b.a(isourceblock, new ItemStack(item));
/*    */       } 
/*    */ 
/*    */       
/* 64 */       return itemstack;
/*    */     } 
/* 66 */     return this.b.a(isourceblock, itemstack);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorFilledBucket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */