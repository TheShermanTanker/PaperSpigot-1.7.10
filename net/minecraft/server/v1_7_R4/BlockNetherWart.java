/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockNetherWart extends BlockPlant {
/*    */   protected BlockNetherWart() {
/*  8 */     a(true);
/*  9 */     float f = 0.5F;
/*    */     
/* 11 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/* 12 */     a((CreativeModeTab)null);
/*    */   }
/*    */   
/*    */   protected boolean a(Block block) {
/* 16 */     return (block == Blocks.SOUL_SAND);
/*    */   }
/*    */   
/*    */   public boolean j(World world, int i, int j, int k) {
/* 20 */     return a(world.getType(i, j - 1, k));
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 24 */     int l = world.getData(i, j, k);
/*    */     
/* 26 */     if (l < 3 && random.nextInt(10) == 0) {
/* 27 */       l++;
/* 28 */       CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this, l);
/*    */     } 
/*    */     
/* 31 */     super.a(world, i, j, k, random);
/*    */   }
/*    */   
/*    */   public int b() {
/* 35 */     return 6;
/*    */   }
/*    */   
/*    */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/* 39 */     if (!world.isStatic) {
/* 40 */       int j1 = 1;
/*    */       
/* 42 */       if (l >= 3) {
/* 43 */         j1 = 2 + world.random.nextInt(3);
/* 44 */         if (i1 > 0) {
/* 45 */           j1 += world.random.nextInt(i1 + 1);
/*    */         }
/*    */       } 
/*    */       
/* 49 */       for (int k1 = 0; k1 < j1; k1++) {
/* 50 */         a(world, i, j, k, new ItemStack(Items.NETHER_STALK));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public Item getDropType(int i, Random random, int j) {
/* 56 */     return null;
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 60 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockNetherWart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */