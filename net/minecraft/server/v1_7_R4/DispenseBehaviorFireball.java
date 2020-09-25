/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.projectiles.CraftBlockProjectileSource;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.projectiles.ProjectileSource;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ final class DispenseBehaviorFireball extends DispenseBehaviorItem {
/*    */   public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 15 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/* 16 */     IPosition iposition = BlockDispenser.a(isourceblock);
/* 17 */     double d0 = iposition.getX() + (enumfacing.getAdjacentX() * 0.3F);
/* 18 */     double d1 = iposition.getY() + (enumfacing.getAdjacentX() * 0.3F);
/* 19 */     double d2 = iposition.getZ() + (enumfacing.getAdjacentZ() * 0.3F);
/* 20 */     World world = isourceblock.k();
/* 21 */     Random random = world.random;
/* 22 */     double d3 = random.nextGaussian() * 0.05D + enumfacing.getAdjacentX();
/* 23 */     double d4 = random.nextGaussian() * 0.05D + enumfacing.getAdjacentY();
/* 24 */     double d5 = random.nextGaussian() * 0.05D + enumfacing.getAdjacentZ();
/*    */ 
/*    */     
/* 27 */     ItemStack itemstack1 = itemstack.a(1);
/* 28 */     Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
/* 29 */     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*    */     
/* 31 */     BlockDispenseEvent event = new BlockDispenseEvent(block, (ItemStack)craftItem.clone(), new Vector(d3, d4, d5));
/* 32 */     if (!BlockDispenser.eventFired) {
/* 33 */       world.getServer().getPluginManager().callEvent((Event)event);
/*    */     }
/*    */     
/* 36 */     if (event.isCancelled()) {
/* 37 */       itemstack.count++;
/* 38 */       return itemstack;
/*    */     } 
/*    */     
/* 41 */     if (!event.getItem().equals(craftItem)) {
/* 42 */       itemstack.count++;
/*    */       
/* 44 */       ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 45 */       IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
/* 46 */       if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
/* 47 */         idispensebehavior.a(isourceblock, eventStack);
/* 48 */         return itemstack;
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, d0, d1, d2, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
/* 53 */     entitysmallfireball.projectileSource = (ProjectileSource)new CraftBlockProjectileSource((TileEntityDispenser)isourceblock.getTileEntity());
/*    */     
/* 55 */     world.addEntity(entitysmallfireball);
/*    */ 
/*    */ 
/*    */     
/* 59 */     return itemstack;
/*    */   }
/*    */   
/*    */   protected void a(ISourceBlock isourceblock) {
/* 63 */     isourceblock.k().triggerEffect(1009, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */