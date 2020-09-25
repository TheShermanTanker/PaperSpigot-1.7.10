/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ final class DispenseBehaviorArmor
/*    */   extends DispenseBehaviorItem
/*    */ {
/*    */   protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 15 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/* 16 */     int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
/* 17 */     int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
/* 18 */     int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();
/* 19 */     AxisAlignedBB axisalignedbb = AxisAlignedBB.a(i, j, k, (i + 1), (j + 1), (k + 1));
/* 20 */     List<EntityLiving> list = isourceblock.k().a(EntityLiving.class, axisalignedbb, new EntitySelectorEquipable(itemstack));
/*    */     
/* 22 */     if (list.size() > 0) {
/* 23 */       EntityLiving entityliving = list.get(0);
/* 24 */       int l = (entityliving instanceof EntityHuman) ? 1 : 0;
/* 25 */       int i1 = EntityInsentient.b(itemstack);
/*    */ 
/*    */       
/* 28 */       ItemStack itemstack1 = itemstack.a(1);
/* 29 */       World world = isourceblock.k();
/* 30 */       Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
/* 31 */       CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*    */       
/* 33 */       BlockDispenseEvent event = new BlockDispenseEvent(block, (ItemStack)craftItem.clone(), new Vector(0, 0, 0));
/* 34 */       if (!BlockDispenser.eventFired) {
/* 35 */         world.getServer().getPluginManager().callEvent((Event)event);
/*    */       }
/*    */       
/* 38 */       if (event.isCancelled()) {
/* 39 */         itemstack.count++;
/* 40 */         return itemstack;
/*    */       } 
/*    */       
/* 43 */       if (!event.getItem().equals(craftItem)) {
/* 44 */         itemstack.count++;
/*    */         
/* 46 */         ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 47 */         IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.get(eventStack.getItem());
/* 48 */         if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
/* 49 */           idispensebehavior.a(isourceblock, eventStack);
/* 50 */           return itemstack;
/*    */         } 
/*    */       } 
/*    */ 
/*    */       
/* 55 */       itemstack1.count = 1;
/* 56 */       entityliving.setEquipment(i1 - l, itemstack1);
/* 57 */       if (entityliving instanceof EntityInsentient) {
/* 58 */         ((EntityInsentient)entityliving).a(i1, 2.0F);
/*    */       }
/*    */ 
/*    */       
/* 62 */       return itemstack;
/*    */     } 
/* 64 */     return super.b(isourceblock, itemstack);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */