/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class BlockMobSpawner
/*    */   extends BlockContainer {
/*    */   protected BlockMobSpawner() {
/*  8 */     super(Material.STONE);
/*    */   }
/*    */   
/*    */   public TileEntity a(World world, int i) {
/* 12 */     return new TileEntityMobSpawner();
/*    */   }
/*    */   
/*    */   public Item getDropType(int i, Random random, int j) {
/* 16 */     return null;
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 20 */     return 0;
/*    */   }
/*    */   
/*    */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/* 24 */     super.dropNaturally(world, i, j, k, l, f, i1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExpDrop(World world, int data, int enchantmentLevel) {
/* 32 */     int j1 = 15 + world.random.nextInt(15) + world.random.nextInt(15);
/*    */     
/* 34 */     return j1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean c() {
/* 39 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockMobSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */