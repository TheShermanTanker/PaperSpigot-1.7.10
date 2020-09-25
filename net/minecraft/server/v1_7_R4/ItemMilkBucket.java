/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.github.paperspigot.PaperSpigotConfig;
/*    */ 
/*    */ public class ItemMilkBucket
/*    */   extends Item {
/*    */   public ItemMilkBucket() {
/*  9 */     if (PaperSpigotConfig.stackableMilkBuckets) {
/* 10 */       e(Material.BUCKET.getMaxStackSize());
/*    */     } else {
/* 12 */       e(1);
/*    */     } 
/*    */     
/* 15 */     a(CreativeModeTab.f);
/*    */   }
/*    */   
/*    */   public ItemStack b(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 19 */     if (!entityhuman.abilities.canInstantlyBuild) {
/* 20 */       itemstack.count--;
/*    */     }
/*    */     
/* 23 */     if (!world.isStatic) {
/* 24 */       entityhuman.removeAllEffects();
/*    */     }
/*    */ 
/*    */     
/* 28 */     if (PaperSpigotConfig.stackableMilkBuckets) {
/* 29 */       if (itemstack.count <= 0)
/* 30 */         return new ItemStack(Items.BUCKET); 
/* 31 */       if (!entityhuman.inventory.pickup(new ItemStack(Items.BUCKET))) {
/* 32 */         entityhuman.drop(new ItemStack(Items.BUCKET), false);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 37 */     return (itemstack.count <= 0) ? new ItemStack(Items.BUCKET) : itemstack;
/*    */   }
/*    */   
/*    */   public int d_(ItemStack itemstack) {
/* 41 */     return 32;
/*    */   }
/*    */   
/*    */   public EnumAnimation d(ItemStack itemstack) {
/* 45 */     return EnumAnimation.DRINK;
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 49 */     entityhuman.a(itemstack, d_(itemstack));
/* 50 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemMilkBucket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */