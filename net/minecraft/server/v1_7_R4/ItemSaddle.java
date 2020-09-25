/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSaddle
/*    */   extends Item
/*    */ {
/*    */   public ItemSaddle() {
/*  9 */     this.maxStackSize = 1;
/* 10 */     a(CreativeModeTab.e);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, EntityLiving paramEntityLiving) {
/* 15 */     if (paramEntityLiving instanceof EntityPig) {
/* 16 */       EntityPig entityPig = (EntityPig)paramEntityLiving;
/* 17 */       if (!entityPig.hasSaddle() && !entityPig.isBaby()) {
/* 18 */         entityPig.setSaddle(true);
/* 19 */         entityPig.world.makeSound(entityPig, "mob.horse.leather", 0.5F, 1.0F);
/* 20 */         paramItemStack.count--;
/*    */       } 
/*    */       
/* 23 */       return true;
/*    */     } 
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) {
/* 30 */     a(paramItemStack, (EntityHuman)null, paramEntityLiving1);
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemSaddle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */