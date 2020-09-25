/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockReed extends Block {
/*    */   protected BlockReed() {
/*  8 */     super(Material.PLANT);
/*  9 */     float f = 0.375F;
/*    */     
/* 11 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
/* 12 */     a(true);
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 16 */     if ((world.getType(i, j - 1, k) == Blocks.SUGAR_CANE_BLOCK || e(world, i, j, k)) && 
/* 17 */       world.isEmpty(i, j + 1, k)) {
/*    */       int l;
/*    */       
/* 20 */       for (l = 1; world.getType(i, j - l, k) == this; l++);
/*    */ 
/*    */ 
/*    */       
/* 24 */       if (l < world.paperSpigotConfig.reedMaxHeight) {
/* 25 */         int i1 = world.getData(i, j, k);
/*    */         
/* 27 */         if (i1 >= (byte)(int)range(3.0F, world.growthOdds / world.spigotConfig.caneModifier * 15.0F + 0.5F, 15.0F)) {
/* 28 */           CraftEventFactory.handleBlockGrowEvent(world, i, j + 1, k, this, 0);
/* 29 */           world.setData(i, j, k, 0, 4);
/*    */         } else {
/* 31 */           world.setData(i, j, k, i1 + 1, 4);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlace(World world, int i, int j, int k) {
/* 39 */     Block block = world.getType(i, j - 1, k);
/*    */     
/* 41 */     return (block == this) ? true : ((block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.SAND) ? false : ((world.getType(i - 1, j - 1, k).getMaterial() == Material.WATER) ? true : ((world.getType(i + 1, j - 1, k).getMaterial() == Material.WATER) ? true : ((world.getType(i, j - 1, k - 1).getMaterial() == Material.WATER) ? true : ((world.getType(i, j - 1, k + 1).getMaterial() == Material.WATER))))));
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 45 */     e(world, i, j, k);
/*    */   }
/*    */   
/*    */   protected final boolean e(World world, int i, int j, int k) {
/* 49 */     if (!j(world, i, j, k)) {
/* 50 */       b(world, i, j, k, world.getData(i, j, k), 0);
/* 51 */       world.setAir(i, j, k);
/* 52 */       return false;
/*    */     } 
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean j(World world, int i, int j, int k) {
/* 59 */     return canPlace(world, i, j, k);
/*    */   }
/*    */   
/*    */   public AxisAlignedBB a(World world, int i, int j, int k) {
/* 63 */     return null;
/*    */   }
/*    */   
/*    */   public Item getDropType(int i, Random random, int j) {
/* 67 */     return Items.SUGAR_CANE;
/*    */   }
/*    */   
/*    */   public boolean c() {
/* 71 */     return false;
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   public int b() {
/* 79 */     return 1;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockReed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */