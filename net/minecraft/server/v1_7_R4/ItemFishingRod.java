/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.entity.Fish;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.player.PlayerFishEvent;
/*    */ 
/*    */ public class ItemFishingRod extends Item {
/*    */   public ItemFishingRod() {
/*  8 */     setMaxDurability(64);
/*  9 */     e(1);
/* 10 */     a(CreativeModeTab.i);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 14 */     if (entityhuman.hookedFish != null) {
/* 15 */       int i = entityhuman.hookedFish.e();
/*    */       
/* 17 */       itemstack.damage(i, entityhuman);
/* 18 */       entityhuman.ba();
/*    */     } else {
/*    */       
/* 21 */       EntityFishingHook hook = new EntityFishingHook(world, entityhuman);
/* 22 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)entityhuman.getBukkitEntity(), null, (Fish)hook.getBukkitEntity(), PlayerFishEvent.State.FISHING);
/* 23 */       world.getServer().getPluginManager().callEvent((Event)playerFishEvent);
/*    */       
/* 25 */       if (playerFishEvent.isCancelled()) {
/* 26 */         entityhuman.hookedFish = null;
/* 27 */         return itemstack;
/*    */       } 
/*    */       
/* 30 */       world.makeSound(entityhuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 31 */       if (!world.isStatic) {
/* 32 */         world.addEntity(hook);
/*    */       }
/*    */       
/* 35 */       entityhuman.ba();
/*    */     } 
/*    */     
/* 38 */     return itemstack;
/*    */   }
/*    */   
/*    */   public boolean e_(ItemStack itemstack) {
/* 42 */     return super.e_(itemstack);
/*    */   }
/*    */   
/*    */   public int c() {
/* 46 */     return 1;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemFishingRod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */