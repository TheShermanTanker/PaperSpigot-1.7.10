/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class BlockOre
/*    */   extends Block {
/*    */   public BlockOre() {
/*  8 */     super(Material.STONE);
/*  9 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public Item getDropType(int i, Random random, int j) {
/* 13 */     return (this == Blocks.COAL_ORE) ? Items.COAL : ((this == Blocks.DIAMOND_ORE) ? Items.DIAMOND : ((this == Blocks.LAPIS_ORE) ? Items.INK_SACK : ((this == Blocks.EMERALD_ORE) ? Items.EMERALD : ((this == Blocks.QUARTZ_ORE) ? Items.QUARTZ : Item.getItemOf(this)))));
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 17 */     return (this == Blocks.LAPIS_ORE) ? (4 + random.nextInt(5)) : 1;
/*    */   }
/*    */   
/*    */   public int getDropCount(int i, Random random) {
/* 21 */     if (i > 0 && Item.getItemOf(this) != getDropType(0, random, i)) {
/* 22 */       int j = random.nextInt(i + 2) - 1;
/*    */       
/* 24 */       if (j < 0) {
/* 25 */         j = 0;
/*    */       }
/*    */       
/* 28 */       return a(random) * (j + 1);
/*    */     } 
/* 30 */     return a(random);
/*    */   }
/*    */ 
/*    */   
/*    */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/* 35 */     super.dropNaturally(world, i, j, k, l, f, i1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExpDrop(World world, int l, int i1) {
/* 58 */     if (getDropType(l, world.random, i1) != Item.getItemOf(this)) {
/* 59 */       int j1 = 0;
/*    */       
/* 61 */       if (this == Blocks.COAL_ORE) {
/* 62 */         j1 = MathHelper.nextInt(world.random, 0, 2);
/* 63 */       } else if (this == Blocks.DIAMOND_ORE) {
/* 64 */         j1 = MathHelper.nextInt(world.random, 3, 7);
/* 65 */       } else if (this == Blocks.EMERALD_ORE) {
/* 66 */         j1 = MathHelper.nextInt(world.random, 3, 7);
/* 67 */       } else if (this == Blocks.LAPIS_ORE) {
/* 68 */         j1 = MathHelper.nextInt(world.random, 2, 5);
/* 69 */       } else if (this == Blocks.QUARTZ_ORE) {
/* 70 */         j1 = MathHelper.nextInt(world.random, 2, 5);
/*    */       } 
/*    */       
/* 73 */       return j1;
/*    */     } 
/*    */     
/* 76 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDropData(int i) {
/* 81 */     return (this == Blocks.LAPIS_ORE) ? 4 : 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockOre.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */