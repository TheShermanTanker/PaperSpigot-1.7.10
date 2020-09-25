/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class WorldGenGroundBush
/*    */   extends WorldGenTrees {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenGroundBush(int i, int j) {
/* 11 */     super(false);
/* 12 */     this.b = i;
/* 13 */     this.a = j;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World world, Random random, int i, int j, int k) {
/*    */     Block block;
/* 19 */     while (((block = world.getType(i, j, k)).getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) && j > 0) {
/* 20 */       j--;
/*    */     }
/*    */     
/* 23 */     Block block1 = world.getType(i, j, k);
/*    */     
/* 25 */     if (block1 == Blocks.DIRT || block1 == Blocks.GRASS) {
/* 26 */       j++;
/* 27 */       setTypeAndData(world, i, j, k, Blocks.LOG, this.b);
/*    */       
/* 29 */       for (int l = j; l <= j + 2; l++) {
/* 30 */         int i1 = l - j;
/* 31 */         int j1 = 2 - i1;
/*    */         
/* 33 */         for (int k1 = i - j1; k1 <= i + j1; k1++) {
/* 34 */           int l1 = k1 - i;
/*    */           
/* 36 */           for (int i2 = k - j1; i2 <= k + j1; i2++) {
/* 37 */             int j2 = i2 - k;
/*    */             
/* 39 */             if ((Math.abs(l1) != j1 || Math.abs(j2) != j1 || random.nextInt(2) != 0) && !world.getType(k1, l, i2).j()) {
/* 40 */               setTypeAndData(world, k1, l, i2, Blocks.LEAVES, this.a);
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } else {
/*    */       
/* 47 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenGroundBush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */