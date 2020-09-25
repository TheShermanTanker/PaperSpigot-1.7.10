/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.LeavesDecayEvent;
/*     */ 
/*     */ public abstract class BlockLeaves
/*     */   extends BlockTransparent {
/*     */   int[] a;
/*  10 */   protected IIcon[][] M = new IIcon[2][];
/*     */   
/*     */   public BlockLeaves() {
/*  13 */     super(Material.LEAVES, false);
/*  14 */     a(true);
/*  15 */     a(CreativeModeTab.c);
/*  16 */     c(0.2F);
/*  17 */     g(1);
/*  18 */     a(h);
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/*  22 */     byte b0 = 1;
/*  23 */     int i1 = b0 + 1;
/*     */     
/*  25 */     if (world.b(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1)) {
/*  26 */       for (int j1 = -b0; j1 <= b0; j1++) {
/*  27 */         for (int k1 = -b0; k1 <= b0; k1++) {
/*  28 */           for (int l1 = -b0; l1 <= b0; l1++) {
/*  29 */             if (world.getType(i + j1, j + k1, k + l1).getMaterial() == Material.LEAVES) {
/*  30 */               int i2 = world.getData(i + j1, j + k1, k + l1);
/*     */               
/*  32 */               world.setData(i + j1, j + k1, k + l1, i2 | 0x8, 4);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  41 */     if (!world.isStatic) {
/*  42 */       int l = world.getData(i, j, k);
/*     */       
/*  44 */       if ((l & 0x8) != 0 && (l & 0x4) == 0) {
/*  45 */         byte b0 = 4;
/*  46 */         int i1 = b0 + 1;
/*  47 */         byte b1 = 32;
/*  48 */         int j1 = b1 * b1;
/*  49 */         int k1 = b1 / 2;
/*     */         
/*  51 */         if (this.a == null) {
/*  52 */           this.a = new int[b1 * b1 * b1];
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  57 */         if (world.b(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1)) {
/*     */           int m;
/*     */ 
/*     */           
/*  61 */           for (m = -b0; m <= b0; m++) {
/*  62 */             for (int i2 = -b0; i2 <= b0; i2++) {
/*  63 */               for (int j2 = -b0; j2 <= b0; j2++) {
/*  64 */                 Block block = world.getType(i + m, j + i2, k + j2);
/*     */                 
/*  66 */                 if (block != Blocks.LOG && block != Blocks.LOG2) {
/*  67 */                   if (block.getMaterial() == Material.LEAVES) {
/*  68 */                     this.a[(m + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
/*     */                   } else {
/*  70 */                     this.a[(m + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
/*     */                   } 
/*     */                 } else {
/*  73 */                   this.a[(m + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/*  79 */           for (m = 1; m <= 4; m++) {
/*  80 */             for (int i2 = -b0; i2 <= b0; i2++) {
/*  81 */               for (int j2 = -b0; j2 <= b0; j2++) {
/*  82 */                 for (int k2 = -b0; k2 <= b0; k2++) {
/*  83 */                   if (this.a[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == m - 1) {
/*  84 */                     if (this.a[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
/*  85 */                       this.a[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = m;
/*     */                     }
/*     */                     
/*  88 */                     if (this.a[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
/*  89 */                       this.a[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = m;
/*     */                     }
/*     */                     
/*  92 */                     if (this.a[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
/*  93 */                       this.a[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = m;
/*     */                     }
/*     */                     
/*  96 */                     if (this.a[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
/*  97 */                       this.a[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = m;
/*     */                     }
/*     */                     
/* 100 */                     if (this.a[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 - 1] == -2) {
/* 101 */                       this.a[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 - 1] = m;
/*     */                     }
/*     */                     
/* 104 */                     if (this.a[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
/* 105 */                       this.a[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = m;
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 114 */         int l1 = this.a[k1 * j1 + k1 * b1 + k1];
/* 115 */         if (l1 >= 0) {
/* 116 */           world.setData(i, j, k, l & 0xFFFFFFF7, 4);
/*     */         } else {
/* 118 */           e(world, i, j, k);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void e(World world, int i, int j, int k) {
/* 126 */     LeavesDecayEvent event = new LeavesDecayEvent(world.getWorld().getBlockAt(i, j, k));
/* 127 */     world.getServer().getPluginManager().callEvent((Event)event);
/*     */     
/* 129 */     if (event.isCancelled()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 134 */     b(world, i, j, k, world.getData(i, j, k), 0);
/* 135 */     world.setAir(i, j, k);
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/* 139 */     return (random.nextInt(20) == 0) ? 1 : 0;
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 143 */     return Item.getItemOf(Blocks.SAPLING);
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/* 147 */     if (!world.isStatic) {
/* 148 */       int j1 = b(l);
/*     */       
/* 150 */       if (i1 > 0) {
/* 151 */         j1 -= 2 << i1;
/* 152 */         if (j1 < 10) {
/* 153 */           j1 = 10;
/*     */         }
/*     */       } 
/*     */       
/* 157 */       if (world.random.nextInt(j1) == 0) {
/* 158 */         Item item = getDropType(l, world.random, i1);
/*     */         
/* 160 */         a(world, i, j, k, new ItemStack(item, 1, getDropData(l)));
/*     */       } 
/*     */       
/* 163 */       j1 = 200;
/* 164 */       if (i1 > 0) {
/* 165 */         j1 -= 10 << i1;
/* 166 */         if (j1 < 40) {
/* 167 */           j1 = 40;
/*     */         }
/*     */       } 
/*     */       
/* 171 */       c(world, i, j, k, l, j1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void c(World world, int i, int j, int k, int l, int i1) {}
/*     */   
/*     */   protected int b(int i) {
/* 178 */     return 20;
/*     */   }
/*     */   
/*     */   public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
/* 182 */     if (!world.isStatic && entityhuman.bF() != null && entityhuman.bF().getItem() == Items.SHEARS) {
/* 183 */       entityhuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
/* 184 */       a(world, i, j, k, new ItemStack(Item.getItemOf(this), 1, l & 0x3));
/*     */     } else {
/* 186 */       super.a(world, entityhuman, i, j, k, l);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getDropData(int i) {
/* 191 */     return i & 0x3;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 195 */     return !this.P;
/*     */   }
/*     */   
/*     */   protected ItemStack j(int i) {
/* 199 */     return new ItemStack(Item.getItemOf(this), 1, i & 0x3);
/*     */   }
/*     */   
/*     */   public abstract String[] e();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockLeaves.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */