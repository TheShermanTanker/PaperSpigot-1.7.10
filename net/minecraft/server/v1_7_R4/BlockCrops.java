/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public class BlockCrops
/*     */   extends BlockPlant
/*     */   implements IBlockFragilePlantElement {
/*     */   protected BlockCrops() {
/*  10 */     a(true);
/*  11 */     float f = 0.5F;
/*     */     
/*  13 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  14 */     a((CreativeModeTab)null);
/*  15 */     c(0.0F);
/*  16 */     a(h);
/*  17 */     H();
/*     */   }
/*     */   
/*     */   protected boolean a(Block block) {
/*  21 */     return (block == Blocks.SOIL);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  25 */     super.a(world, i, j, k, random);
/*  26 */     if (world.getLightLevel(i, j + 1, k) >= 9) {
/*  27 */       int l = world.getData(i, j, k);
/*     */       
/*  29 */       if (l < 7) {
/*  30 */         float f = n(world, i, j, k);
/*     */         
/*  32 */         if (random.nextInt((int)(world.growthOdds / world.spigotConfig.wheatModifier * 25.0F / f) + 1) == 0) {
/*  33 */           l++;
/*  34 */           CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this, l);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void m(World world, int i, int j, int k) {
/*  41 */     int l = world.getData(i, j, k) + MathHelper.nextInt(world.random, 2, 5);
/*     */     
/*  43 */     if (l > 7) {
/*  44 */       l = 7;
/*     */     }
/*     */     
/*  47 */     CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this, l);
/*     */   }
/*     */   
/*     */   private float n(World world, int i, int j, int k) {
/*  51 */     float f = 1.0F;
/*  52 */     Block block = world.getType(i, j, k - 1);
/*  53 */     Block block1 = world.getType(i, j, k + 1);
/*  54 */     Block block2 = world.getType(i - 1, j, k);
/*  55 */     Block block3 = world.getType(i + 1, j, k);
/*  56 */     Block block4 = world.getType(i - 1, j, k - 1);
/*  57 */     Block block5 = world.getType(i + 1, j, k - 1);
/*  58 */     Block block6 = world.getType(i + 1, j, k + 1);
/*  59 */     Block block7 = world.getType(i - 1, j, k + 1);
/*  60 */     boolean flag = (block2 == this || block3 == this);
/*  61 */     boolean flag1 = (block == this || block1 == this);
/*  62 */     boolean flag2 = (block4 == this || block5 == this || block6 == this || block7 == this);
/*     */     
/*  64 */     for (int l = i - 1; l <= i + 1; l++) {
/*  65 */       for (int i1 = k - 1; i1 <= k + 1; i1++) {
/*  66 */         float f1 = 0.0F;
/*     */         
/*  68 */         if (world.getType(l, j - 1, i1) == Blocks.SOIL) {
/*  69 */           f1 = 1.0F;
/*  70 */           if (world.getData(l, j - 1, i1) > 0) {
/*  71 */             f1 = 3.0F;
/*     */           }
/*     */         } 
/*     */         
/*  75 */         if (l != i || i1 != k) {
/*  76 */           f1 /= 4.0F;
/*     */         }
/*     */         
/*  79 */         f += f1;
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     if (flag2 || (flag && flag1)) {
/*  84 */       f /= 2.0F;
/*     */     }
/*     */     
/*  87 */     return f;
/*     */   }
/*     */   
/*     */   public int b() {
/*  91 */     return 6;
/*     */   }
/*     */   
/*     */   protected Item i() {
/*  95 */     return Items.SEEDS;
/*     */   }
/*     */   
/*     */   protected Item P() {
/*  99 */     return Items.WHEAT;
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/* 103 */     super.dropNaturally(world, i, j, k, l, f, 0);
/* 104 */     if (!world.isStatic && 
/* 105 */       l >= 7) {
/* 106 */       int j1 = 3 + i1;
/*     */       
/* 108 */       for (int k1 = 0; k1 < j1; k1++) {
/* 109 */         if (world.random.nextInt(15) <= l) {
/* 110 */           a(world, i, j, k, new ItemStack(i(), 1, 0));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 118 */     return (i == 7) ? P() : i();
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/* 122 */     return 1;
/*     */   }
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, boolean flag) {
/* 126 */     return (world.getData(i, j, k) != 7);
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, int i, int j, int k) {
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, int i, int j, int k) {
/* 134 */     m(world, i, j, k);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockCrops.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */