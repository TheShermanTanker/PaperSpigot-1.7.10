/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.inventory.FurnaceExtractEvent;
/*    */ 
/*    */ public class SlotFurnaceResult extends Slot {
/*    */   private EntityHuman a;
/*    */   private int b;
/*    */   
/*    */   public SlotFurnaceResult(EntityHuman entityhuman, IInventory iinventory, int i, int j, int k) {
/* 14 */     super(iinventory, i, j, k);
/* 15 */     this.a = entityhuman;
/*    */   }
/*    */   
/*    */   public boolean isAllowed(ItemStack itemstack) {
/* 19 */     return false;
/*    */   }
/*    */   
/*    */   public ItemStack a(int i) {
/* 23 */     if (hasItem()) {
/* 24 */       this.b += Math.min(i, (getItem()).count);
/*    */     }
/*    */     
/* 27 */     return super.a(i);
/*    */   }
/*    */   
/*    */   public void a(EntityHuman entityhuman, ItemStack itemstack) {
/* 31 */     b(itemstack);
/* 32 */     super.a(entityhuman, itemstack);
/*    */   }
/*    */   
/*    */   protected void a(ItemStack itemstack, int i) {
/* 36 */     this.b += i;
/* 37 */     b(itemstack);
/*    */   }
/*    */   
/*    */   protected void b(ItemStack itemstack) {
/* 41 */     itemstack.a(this.a.world, this.a, this.b);
/* 42 */     if (!this.a.world.isStatic) {
/* 43 */       int i = this.b;
/* 44 */       float f = RecipesFurnace.getInstance().b(itemstack);
/*    */ 
/*    */       
/* 47 */       if (f == 0.0F) {
/* 48 */         i = 0;
/* 49 */       } else if (f < 1.0F) {
/* 50 */         int j = MathHelper.d(i * f);
/* 51 */         if (j < MathHelper.f(i * f) && (float)Math.random() < i * f - j) {
/* 52 */           j++;
/*    */         }
/*    */         
/* 55 */         i = j;
/*    */       } 
/*    */ 
/*    */       
/* 59 */       Player player = (Player)this.a.getBukkitEntity();
/* 60 */       TileEntityFurnace furnace = (TileEntityFurnace)this.inventory;
/* 61 */       Block block = this.a.world.getWorld().getBlockAt(furnace.x, furnace.y, furnace.z);
/*    */       
/* 63 */       FurnaceExtractEvent event = new FurnaceExtractEvent(player, block, CraftMagicNumbers.getMaterial(itemstack.getItem()), itemstack.count, i);
/* 64 */       this.a.world.getServer().getPluginManager().callEvent((Event)event);
/*    */       
/* 66 */       i = event.getExpToDrop();
/*    */ 
/*    */       
/* 69 */       while (i > 0) {
/* 70 */         int j = EntityExperienceOrb.getOrbValue(i);
/* 71 */         i -= j;
/* 72 */         this.a.world.addEntity(new EntityExperienceOrb(this.a.world, this.a.locX, this.a.locY + 0.5D, this.a.locZ + 0.5D, j));
/*    */       } 
/*    */     } 
/*    */     
/* 76 */     this.b = 0;
/* 77 */     if (itemstack.getItem() == Items.IRON_INGOT) {
/* 78 */       this.a.a(AchievementList.k, 1);
/*    */     }
/*    */     
/* 81 */     if (itemstack.getItem() == Items.COOKED_FISH)
/* 82 */       this.a.a(AchievementList.p, 1); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SlotFurnaceResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */