/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockCactus
/*    */   extends Block
/*    */ {
/*    */   protected BlockCactus() {
/* 10 */     super(Material.CACTUS);
/* 11 */     a(true);
/* 12 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 16 */     if (world.isEmpty(i, j + 1, k)) {
/*    */       int l;
/*    */       
/* 19 */       for (l = 1; world.getType(i, j - l, k) == this; l++);
/*    */ 
/*    */ 
/*    */       
/* 23 */       if (l < world.paperSpigotConfig.cactusMaxHeight) {
/* 24 */         int i1 = world.getData(i, j, k);
/*    */         
/* 26 */         if (i1 >= (byte)(int)range(3.0F, world.growthOdds / world.spigotConfig.cactusModifier * 15.0F + 0.5F, 15.0F)) {
/* 27 */           CraftEventFactory.handleBlockGrowEvent(world, i, j + 1, k, this, 0);
/* 28 */           world.setData(i, j, k, 0, 4);
/* 29 */           doPhysics(world, i, j + 1, k, this);
/*    */         } else {
/* 31 */           world.setData(i, j, k, i1 + 1, 4);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public AxisAlignedBB a(World world, int i, int j, int k) {
/* 38 */     float f = 0.0625F;
/*    */     
/* 40 */     return AxisAlignedBB.a((i + f), j, (k + f), ((i + 1) - f), ((j + 1) - f), ((k + 1) - f));
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 44 */     return false;
/*    */   }
/*    */   
/*    */   public boolean c() {
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   public int b() {
/* 52 */     return 13;
/*    */   }
/*    */   
/*    */   public boolean canPlace(World world, int i, int j, int k) {
/* 56 */     return !super.canPlace(world, i, j, k) ? false : j(world, i, j, k);
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 60 */     if (!j(world, i, j, k)) {
/* 61 */       world.setAir(i, j, k, true);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean j(World world, int i, int j, int k) {
/* 66 */     if (world.getType(i - 1, j, k).getMaterial().isBuildable())
/* 67 */       return false; 
/* 68 */     if (world.getType(i + 1, j, k).getMaterial().isBuildable())
/* 69 */       return false; 
/* 70 */     if (world.getType(i, j, k - 1).getMaterial().isBuildable())
/* 71 */       return false; 
/* 72 */     if (world.getType(i, j, k + 1).getMaterial().isBuildable()) {
/* 73 */       return false;
/*    */     }
/* 75 */     Block block = world.getType(i, j - 1, k);
/*    */     
/* 77 */     return (block == Blocks.CACTUS || block == Blocks.SAND);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World world, int i, int j, int k, Entity entity) {
/* 82 */     CraftEventFactory.blockDamage = world.getWorld().getBlockAt(i, j, k);
/* 83 */     entity.damageEntity(DamageSource.CACTUS, 1.0F);
/* 84 */     CraftEventFactory.blockDamage = null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockCactus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */