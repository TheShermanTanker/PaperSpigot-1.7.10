/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.entity.Hanging;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.hanging.HangingPlaceEvent;
/*    */ 
/*    */ public class ItemLeash extends Item {
/*    */   public ItemLeash() {
/* 11 */     a(CreativeModeTab.i);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/* 15 */     Block block = world.getType(i, j, k);
/*    */     
/* 17 */     if (block.b() == 11) {
/* 18 */       if (world.isStatic) {
/* 19 */         return true;
/*    */       }
/* 21 */       a(entityhuman, world, i, j, k);
/* 22 */       return true;
/*    */     } 
/*    */     
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean a(EntityHuman entityhuman, World world, int i, int j, int k) {
/* 30 */     EntityLeash entityleash = EntityLeash.b(world, i, j, k);
/* 31 */     boolean flag = false;
/* 32 */     double d0 = 7.0D;
/* 33 */     List list = world.a(EntityInsentient.class, AxisAlignedBB.a(i - d0, j - d0, k - d0, i + d0, j + d0, k + d0));
/*    */     
/* 35 */     if (list != null) {
/* 36 */       Iterator<EntityInsentient> iterator = list.iterator();
/*    */       
/* 38 */       while (iterator.hasNext()) {
/* 39 */         EntityInsentient entityinsentient = iterator.next();
/*    */         
/* 41 */         if (entityinsentient.bN() && entityinsentient.getLeashHolder() == entityhuman) {
/* 42 */           if (entityleash == null) {
/* 43 */             entityleash = EntityLeash.a(world, i, j, k);
/*    */ 
/*    */             
/* 46 */             HangingPlaceEvent event = new HangingPlaceEvent((Hanging)entityleash.getBukkitEntity(), (entityhuman != null) ? (Player)entityhuman.getBukkitEntity() : null, world.getWorld().getBlockAt(i, j, k), BlockFace.SELF);
/* 47 */             world.getServer().getPluginManager().callEvent((Event)event);
/*    */             
/* 49 */             if (event.isCancelled()) {
/* 50 */               entityleash.die();
/* 51 */               return false;
/*    */             } 
/*    */           } 
/*    */ 
/*    */ 
/*    */           
/* 57 */           if (CraftEventFactory.callPlayerLeashEntityEvent(entityinsentient, entityleash, entityhuman).isCancelled()) {
/*    */             continue;
/*    */           }
/*    */ 
/*    */           
/* 62 */           entityinsentient.setLeashHolder(entityleash, true);
/* 63 */           flag = true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 68 */     return flag;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemLeash.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */