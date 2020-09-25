/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*    */ 
/*    */ public class BlockCake extends Block {
/*    */   protected BlockCake() {
/*  8 */     super(Material.CAKE);
/*  9 */     a(true);
/*    */   }
/*    */   
/*    */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 13 */     int l = iblockaccess.getData(i, j, k);
/* 14 */     float f = 0.0625F;
/* 15 */     float f1 = (1 + l * 2) / 16.0F;
/* 16 */     float f2 = 0.5F;
/*    */     
/* 18 */     a(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
/*    */   }
/*    */   
/*    */   public void g() {
/* 22 */     float f = 0.0625F;
/* 23 */     float f1 = 0.5F;
/*    */     
/* 25 */     a(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
/*    */   }
/*    */   
/*    */   public AxisAlignedBB a(World world, int i, int j, int k) {
/* 29 */     int l = world.getData(i, j, k);
/* 30 */     float f = 0.0625F;
/* 31 */     float f1 = (1 + l * 2) / 16.0F;
/* 32 */     float f2 = 0.5F;
/*    */     
/* 34 */     return AxisAlignedBB.a((i + f1), j, (k + f), ((i + 1) - f), (j + f2 - f), ((k + 1) - f));
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 38 */     return false;
/*    */   }
/*    */   
/*    */   public boolean c() {
/* 42 */     return false;
/*    */   }
/*    */   
/*    */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/* 46 */     b(world, i, j, k, entityhuman);
/* 47 */     return true;
/*    */   }
/*    */   
/*    */   public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {
/* 51 */     b(world, i, j, k, entityhuman);
/*    */   }
/*    */   
/*    */   private void b(World world, int i, int j, int k, EntityHuman entityhuman) {
/* 55 */     if (entityhuman.g(false)) {
/*    */       
/* 57 */       int oldFoodLevel = (entityhuman.getFoodData()).foodLevel;
/*    */       
/* 59 */       FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, 2 + oldFoodLevel);
/*    */       
/* 61 */       if (!event.isCancelled()) {
/* 62 */         entityhuman.getFoodData().eat(event.getFoodLevel() - oldFoodLevel, 0.1F);
/*    */       }
/*    */       
/* 65 */       ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)entityhuman).getBukkitEntity().getScaledHealth(), (entityhuman.getFoodData()).foodLevel, (entityhuman.getFoodData()).saturationLevel));
/*    */       
/* 67 */       int l = world.getData(i, j, k) + 1;
/*    */       
/* 69 */       if (l >= 6) {
/* 70 */         world.setAir(i, j, k);
/*    */       } else {
/* 72 */         world.setData(i, j, k, l, 2);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean canPlace(World world, int i, int j, int k) {
/* 78 */     return !super.canPlace(world, i, j, k) ? false : j(world, i, j, k);
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 82 */     if (!j(world, i, j, k)) {
/* 83 */       world.setAir(i, j, k);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean j(World world, int i, int j, int k) {
/* 88 */     return world.getType(i, j - 1, k).getMaterial().isBuildable();
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 92 */     return 0;
/*    */   }
/*    */   
/*    */   public Item getDropType(int i, Random random, int j) {
/* 96 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockCake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */