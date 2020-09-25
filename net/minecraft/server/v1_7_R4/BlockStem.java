/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public class BlockStem
/*     */   extends BlockPlant
/*     */   implements IBlockFragilePlantElement {
/*     */   private final Block blockFruit;
/*     */   
/*     */   protected BlockStem(Block block) {
/*  12 */     this.blockFruit = block;
/*  13 */     a(true);
/*  14 */     float f = 0.125F;
/*     */     
/*  16 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  17 */     a((CreativeModeTab)null);
/*     */   }
/*     */   
/*     */   protected boolean a(Block block) {
/*  21 */     return (block == Blocks.SOIL);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  25 */     super.a(world, i, j, k, random);
/*  26 */     if (world.getLightLevel(i, j + 1, k) >= 9) {
/*  27 */       float f = n(world, i, j, k);
/*     */       
/*  29 */       if (random.nextInt((int)(world.growthOdds / ((this == Blocks.PUMPKIN_STEM) ? world.spigotConfig.pumpkinModifier : world.spigotConfig.melonModifier) * 25.0F / f) + 1) == 0) {
/*  30 */         int l = world.getData(i, j, k);
/*     */         
/*  32 */         if (l < 7) {
/*  33 */           l++;
/*  34 */           CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this, l);
/*     */         } else {
/*  36 */           if (world.getType(i - 1, j, k) == this.blockFruit) {
/*     */             return;
/*     */           }
/*     */           
/*  40 */           if (world.getType(i + 1, j, k) == this.blockFruit) {
/*     */             return;
/*     */           }
/*     */           
/*  44 */           if (world.getType(i, j, k - 1) == this.blockFruit) {
/*     */             return;
/*     */           }
/*     */           
/*  48 */           if (world.getType(i, j, k + 1) == this.blockFruit) {
/*     */             return;
/*     */           }
/*     */           
/*  52 */           int i1 = random.nextInt(4);
/*  53 */           int j1 = i;
/*  54 */           int k1 = k;
/*     */           
/*  56 */           if (i1 == 0) {
/*  57 */             j1 = i - 1;
/*     */           }
/*     */           
/*  60 */           if (i1 == 1) {
/*  61 */             j1++;
/*     */           }
/*     */           
/*  64 */           if (i1 == 2) {
/*  65 */             k1 = k - 1;
/*     */           }
/*     */           
/*  68 */           if (i1 == 3) {
/*  69 */             k1++;
/*     */           }
/*     */           
/*  72 */           Block block = world.getType(j1, j - 1, k1);
/*     */           
/*  74 */           if ((world.getType(j1, j, k1)).material == Material.AIR && (block == Blocks.SOIL || block == Blocks.DIRT || block == Blocks.GRASS)) {
/*  75 */             CraftEventFactory.handleBlockGrowEvent(world, j1, j, k1, this.blockFruit, 0);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void m(World world, int i, int j, int k) {
/*  83 */     int l = world.getData(i, j, k) + MathHelper.nextInt(world.random, 2, 5);
/*     */     
/*  85 */     if (l > 7) {
/*  86 */       l = 7;
/*     */     }
/*     */     
/*  89 */     CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this, l);
/*     */   }
/*     */   
/*     */   private float n(World world, int i, int j, int k) {
/*  93 */     float f = 1.0F;
/*  94 */     Block block = world.getType(i, j, k - 1);
/*  95 */     Block block1 = world.getType(i, j, k + 1);
/*  96 */     Block block2 = world.getType(i - 1, j, k);
/*  97 */     Block block3 = world.getType(i + 1, j, k);
/*  98 */     Block block4 = world.getType(i - 1, j, k - 1);
/*  99 */     Block block5 = world.getType(i + 1, j, k - 1);
/* 100 */     Block block6 = world.getType(i + 1, j, k + 1);
/* 101 */     Block block7 = world.getType(i - 1, j, k + 1);
/* 102 */     boolean flag = (block2 == this || block3 == this);
/* 103 */     boolean flag1 = (block == this || block1 == this);
/* 104 */     boolean flag2 = (block4 == this || block5 == this || block6 == this || block7 == this);
/*     */     
/* 106 */     for (int l = i - 1; l <= i + 1; l++) {
/* 107 */       for (int i1 = k - 1; i1 <= k + 1; i1++) {
/* 108 */         Block block8 = world.getType(l, j - 1, i1);
/* 109 */         float f1 = 0.0F;
/*     */         
/* 111 */         if (block8 == Blocks.SOIL) {
/* 112 */           f1 = 1.0F;
/* 113 */           if (world.getData(l, j - 1, i1) > 0) {
/* 114 */             f1 = 3.0F;
/*     */           }
/*     */         } 
/*     */         
/* 118 */         if (l != i || i1 != k) {
/* 119 */           f1 /= 4.0F;
/*     */         }
/*     */         
/* 122 */         f += f1;
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     if (flag2 || (flag && flag1)) {
/* 127 */       f /= 2.0F;
/*     */     }
/*     */     
/* 130 */     return f;
/*     */   }
/*     */   
/*     */   public void g() {
/* 134 */     float f = 0.125F;
/*     */     
/* 136 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 140 */     this.maxY = ((iblockaccess.getData(i, j, k) * 2 + 2) / 16.0F);
/* 141 */     float f = 0.125F;
/*     */     
/* 143 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float)this.maxY, 0.5F + f);
/*     */   }
/*     */   
/*     */   public int b() {
/* 147 */     return 19;
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/* 151 */     super.dropNaturally(world, i, j, k, l, f, i1);
/* 152 */     if (!world.isStatic) {
/* 153 */       Item item = null;
/*     */       
/* 155 */       if (this.blockFruit == Blocks.PUMPKIN) {
/* 156 */         item = Items.PUMPKIN_SEEDS;
/*     */       }
/*     */       
/* 159 */       if (this.blockFruit == Blocks.MELON) {
/* 160 */         item = Items.MELON_SEEDS;
/*     */       }
/*     */       
/* 163 */       for (int j1 = 0; j1 < 3; j1++) {
/* 164 */         if (world.random.nextInt(15) <= l) {
/* 165 */           a(world, i, j, k, new ItemStack(item));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 172 */     return null;
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/* 176 */     return 1;
/*     */   }
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, boolean flag) {
/* 180 */     return (world.getData(i, j, k) != 7);
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, int i, int j, int k) {
/* 184 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, int i, int j, int k) {
/* 188 */     m(world, i, j, k);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockStem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */