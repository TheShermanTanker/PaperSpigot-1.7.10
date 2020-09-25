/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ 
/*    */ public class ItemMinecart extends Item {
/*  5 */   private static final IDispenseBehavior b = new DispenseBehaviorMinecart();
/*    */   public int a;
/*    */   
/*    */   public ItemMinecart(int i) {
/*  9 */     this.maxStackSize = 1;
/* 10 */     this.a = i;
/* 11 */     a(CreativeModeTab.e);
/* 12 */     BlockDispenser.a.a(this, b);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/* 16 */     if (BlockMinecartTrackAbstract.a(world.getType(i, j, k))) {
/* 17 */       if (!world.isStatic) {
/*    */         
/* 19 */         PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, l, itemstack);
/*    */         
/* 21 */         if (event.isCancelled()) {
/* 22 */           return false;
/*    */         }
/*    */         
/* 25 */         EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(world, (i + 0.5F), (j + 0.5F), (k + 0.5F), this.a);
/*    */         
/* 27 */         if (itemstack.hasName()) {
/* 28 */           entityminecartabstract.a(itemstack.getName());
/*    */         }
/*    */         
/* 31 */         world.addEntity(entityminecartabstract);
/*    */       } 
/*    */       
/* 34 */       itemstack.count--;
/* 35 */       return true;
/*    */     } 
/* 37 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemMinecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */