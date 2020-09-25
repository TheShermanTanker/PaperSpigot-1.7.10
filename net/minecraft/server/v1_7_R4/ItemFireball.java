/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.BlockIgniteEvent;
/*    */ 
/*    */ 
/*    */ public class ItemFireball
/*    */   extends Item
/*    */ {
/*    */   public ItemFireball() {
/* 11 */     a(CreativeModeTab.f);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/* 15 */     if (world.isStatic) {
/* 16 */       return true;
/*    */     }
/* 18 */     if (l == 0) {
/* 19 */       j--;
/*    */     }
/*    */     
/* 22 */     if (l == 1) {
/* 23 */       j++;
/*    */     }
/*    */     
/* 26 */     if (l == 2) {
/* 27 */       k--;
/*    */     }
/*    */     
/* 30 */     if (l == 3) {
/* 31 */       k++;
/*    */     }
/*    */     
/* 34 */     if (l == 4) {
/* 35 */       i--;
/*    */     }
/*    */     
/* 38 */     if (l == 5) {
/* 39 */       i++;
/*    */     }
/*    */     
/* 42 */     if (!entityhuman.a(i, j, k, l, itemstack)) {
/* 43 */       return false;
/*    */     }
/* 45 */     if (world.getType(i, j, k).getMaterial() == Material.AIR) {
/*    */       
/* 47 */       if (CraftEventFactory.callBlockIgniteEvent(world, i, j, k, BlockIgniteEvent.IgniteCause.FIREBALL, entityhuman).isCancelled()) {
/* 48 */         if (!entityhuman.abilities.canInstantlyBuild) {
/* 49 */           itemstack.count--;
/*    */         }
/* 51 */         return false;
/*    */       } 
/*    */ 
/*    */       
/* 55 */       world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "fire.ignite", 1.0F, g.nextFloat() * 0.4F + 0.8F);
/* 56 */       world.setTypeUpdate(i, j, k, Blocks.FIRE);
/*    */     } 
/*    */     
/* 59 */     if (!entityhuman.abilities.canInstantlyBuild) {
/* 60 */       itemstack.count--;
/*    */     }
/*    */     
/* 63 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */