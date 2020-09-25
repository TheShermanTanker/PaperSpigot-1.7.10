/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.projectiles.CraftBlockProjectileSource;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.projectiles.ProjectileSource;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public abstract class DispenseBehaviorProjectile extends DispenseBehaviorItem {
/*    */   public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 13 */     World world = isourceblock.k();
/* 14 */     IPosition iposition = BlockDispenser.a(isourceblock);
/* 15 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/* 16 */     IProjectile iprojectile = a(world, iposition);
/*    */ 
/*    */     
/* 19 */     ItemStack itemstack1 = itemstack.a(1);
/* 20 */     Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
/* 21 */     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*    */     
/* 23 */     BlockDispenseEvent event = new BlockDispenseEvent(block, (ItemStack)craftItem.clone(), new Vector(enumfacing.getAdjacentX(), (enumfacing.getAdjacentY() + 0.1F), enumfacing.getAdjacentZ()));
/* 24 */     if (!BlockDispenser.eventFired) {
/* 25 */       world.getServer().getPluginManager().callEvent((Event)event);
/*    */     }
/*    */     
/* 28 */     if (event.isCancelled()) {
/* 29 */       itemstack.count++;
/* 30 */       return itemstack;
/*    */     } 
/*    */     
/* 33 */     if (!event.getItem().equals(craftItem)) {
/* 34 */       itemstack.count++;
/*    */       
/* 36 */       ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 37 */       IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
/* 38 */       if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
/* 39 */         idispensebehavior.a(isourceblock, eventStack);
/* 40 */         return itemstack;
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     iprojectile.shoot(event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), b(), a());
/* 45 */     ((Entity)iprojectile).projectileSource = (ProjectileSource)new CraftBlockProjectileSource((TileEntityDispenser)isourceblock.getTileEntity());
/*    */ 
/*    */     
/* 48 */     world.addEntity((Entity)iprojectile);
/*    */     
/* 50 */     return itemstack;
/*    */   }
/*    */   
/*    */   protected void a(ISourceBlock isourceblock) {
/* 54 */     isourceblock.k().triggerEffect(1002, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
/*    */   }
/*    */   
/*    */   protected abstract IProjectile a(World paramWorld, IPosition paramIPosition);
/*    */   
/*    */   protected float a() {
/* 60 */     return 6.0F;
/*    */   }
/*    */   
/*    */   protected float b() {
/* 64 */     return 1.1F;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorProjectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */