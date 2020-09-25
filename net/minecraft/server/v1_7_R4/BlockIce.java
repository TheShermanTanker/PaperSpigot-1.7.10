/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockIce extends BlockHalfTransparent {
/*    */   public BlockIce() {
/*  8 */     super("ice", Material.ICE, false);
/*  9 */     this.frictionFactor = 0.98F;
/* 10 */     a(true);
/* 11 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
/* 15 */     entityhuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
/* 16 */     entityhuman.applyExhaustion(0.025F);
/* 17 */     if (E() && EnchantmentManager.hasSilkTouchEnchantment(entityhuman)) {
/* 18 */       ItemStack itemstack = j(l);
/*    */       
/* 20 */       if (itemstack != null) {
/* 21 */         a(world, i, j, k, itemstack);
/*    */       }
/*    */     } else {
/* 24 */       if (world.worldProvider.f) {
/* 25 */         world.setAir(i, j, k);
/*    */         
/*    */         return;
/*    */       } 
/* 29 */       int i1 = EnchantmentManager.getBonusBlockLootEnchantmentLevel(entityhuman);
/*    */       
/* 31 */       b(world, i, j, k, l, i1);
/* 32 */       Material material = world.getType(i, j - 1, k).getMaterial();
/*    */       
/* 34 */       if (material.isSolid() || material.isLiquid()) {
/* 35 */         world.setTypeUpdate(i, j, k, Blocks.WATER);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 41 */     return 0;
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 45 */     if (world.b(EnumSkyBlock.BLOCK, i, j, k) > 11 - k()) {
/*    */       
/* 47 */       if (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), world.worldProvider.f ? Blocks.AIR : Blocks.STATIONARY_WATER).isCancelled()) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 52 */       if (world.worldProvider.f) {
/* 53 */         world.setAir(i, j, k);
/*    */         
/*    */         return;
/*    */       } 
/* 57 */       b(world, i, j, k, world.getData(i, j, k), 0);
/* 58 */       world.setTypeUpdate(i, j, k, Blocks.STATIONARY_WATER);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int h() {
/* 63 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockIce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */