/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class DispenseBehaviorItem
/*    */   implements IDispenseBehavior {
/*    */   public final ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
/* 13 */     ItemStack itemstack1 = b(isourceblock, itemstack);
/*    */     
/* 15 */     a(isourceblock);
/* 16 */     a(isourceblock, BlockDispenser.b(isourceblock.h()));
/* 17 */     return itemstack1;
/*    */   }
/*    */   
/*    */   protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 21 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/* 22 */     IPosition iposition = BlockDispenser.a(isourceblock);
/* 23 */     ItemStack itemstack1 = itemstack.a(1);
/*    */ 
/*    */     
/* 26 */     if (!a(isourceblock.k(), itemstack1, 6, enumfacing, isourceblock)) {
/* 27 */       itemstack.count++;
/*    */     }
/*    */ 
/*    */     
/* 31 */     return itemstack;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean a(World world, ItemStack itemstack, int i, EnumFacing enumfacing, ISourceBlock isourceblock) {
/* 36 */     IPosition iposition = BlockDispenser.a(isourceblock);
/*    */     
/* 38 */     double d0 = iposition.getX();
/* 39 */     double d1 = iposition.getY();
/* 40 */     double d2 = iposition.getZ();
/* 41 */     EntityItem entityitem = new EntityItem(world, d0, d1 - 0.3D, d2, itemstack);
/* 42 */     double d3 = world.random.nextDouble() * 0.1D + 0.2D;
/*    */     
/* 44 */     entityitem.motX = enumfacing.getAdjacentX() * d3;
/* 45 */     entityitem.motY = 0.20000000298023224D;
/* 46 */     entityitem.motZ = enumfacing.getAdjacentZ() * d3;
/* 47 */     entityitem.motX += world.random.nextGaussian() * 0.007499999832361937D * i;
/* 48 */     entityitem.motY += world.random.nextGaussian() * 0.007499999832361937D * i;
/* 49 */     entityitem.motZ += world.random.nextGaussian() * 0.007499999832361937D * i;
/*    */ 
/*    */     
/* 52 */     Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
/* 53 */     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
/*    */     
/* 55 */     BlockDispenseEvent event = new BlockDispenseEvent(block, (ItemStack)craftItem.clone(), new Vector(entityitem.motX, entityitem.motY, entityitem.motZ));
/* 56 */     if (!BlockDispenser.eventFired) {
/* 57 */       world.getServer().getPluginManager().callEvent((Event)event);
/*    */     }
/*    */     
/* 60 */     if (event.isCancelled()) {
/* 61 */       return false;
/*    */     }
/*    */     
/* 64 */     entityitem.setItemStack(CraftItemStack.asNMSCopy(event.getItem()));
/* 65 */     entityitem.motX = event.getVelocity().getX();
/* 66 */     entityitem.motY = event.getVelocity().getY();
/* 67 */     entityitem.motZ = event.getVelocity().getZ();
/*    */     
/* 69 */     if (!event.getItem().equals(craftItem)) {
/*    */       
/* 71 */       ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 72 */       IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
/* 73 */       if (idispensebehavior != IDispenseBehavior.a && idispensebehavior.getClass() != DispenseBehaviorItem.class) {
/* 74 */         idispensebehavior.a(isourceblock, eventStack);
/*    */       } else {
/* 76 */         world.addEntity(entityitem);
/*    */       } 
/* 78 */       return false;
/*    */     } 
/*    */     
/* 81 */     world.addEntity(entityitem);
/*    */     
/* 83 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(ISourceBlock isourceblock) {
/* 88 */     isourceblock.k().triggerEffect(1000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
/*    */   }
/*    */   
/*    */   protected void a(ISourceBlock isourceblock, EnumFacing enumfacing) {
/* 92 */     isourceblock.k().triggerEffect(2000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), a(enumfacing));
/*    */   }
/*    */   
/*    */   private int a(EnumFacing enumfacing) {
/* 96 */     return enumfacing.getAdjacentX() + 1 + (enumfacing.getAdjacentZ() + 1) * 3;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */