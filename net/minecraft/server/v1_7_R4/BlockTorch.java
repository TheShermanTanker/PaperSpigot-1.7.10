/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockTorch
/*     */   extends Block {
/*     */   protected BlockTorch() {
/*   8 */     super(Material.ORIENTABLE);
/*   9 */     a(true);
/*  10 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  14 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  18 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  22 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  26 */     return 2;
/*     */   }
/*     */   
/*     */   private boolean m(World world, int i, int j, int k) {
/*  30 */     if (World.a(world, i, j, k)) {
/*  31 */       return true;
/*     */     }
/*  33 */     Block block = world.getType(i, j, k);
/*     */ 
/*     */     
/*  36 */     return world.paperSpigotConfig.lessPickyTorches ? ((block == Blocks.FENCE || block == Blocks.NETHER_FENCE || block == Blocks.GLASS || block == Blocks.STAINED_GLASS || block == Blocks.COBBLE_WALL)) : ((block == Blocks.FENCE || block == Blocks.NETHER_FENCE || block == Blocks.GLASS || block == Blocks.COBBLE_WALL));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  41 */     return world.c(i - 1, j, k, true) ? true : (world.c(i + 1, j, k, true) ? true : (world.c(i, j, k - 1, true) ? true : (world.c(i, j, k + 1, true) ? true : m(world, i, j - 1, k))));
/*     */   }
/*     */   
/*     */   public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
/*  45 */     int j1 = i1;
/*     */     
/*  47 */     if (l == 1 && m(world, i, j - 1, k)) {
/*  48 */       j1 = 5;
/*     */     }
/*     */     
/*  51 */     if (l == 2 && world.c(i, j, k + 1, true)) {
/*  52 */       j1 = 4;
/*     */     }
/*     */     
/*  55 */     if (l == 3 && world.c(i, j, k - 1, true)) {
/*  56 */       j1 = 3;
/*     */     }
/*     */     
/*  59 */     if (l == 4 && world.c(i + 1, j, k, true)) {
/*  60 */       j1 = 2;
/*     */     }
/*     */     
/*  63 */     if (l == 5 && world.c(i - 1, j, k, true)) {
/*  64 */       j1 = 1;
/*     */     }
/*     */     
/*  67 */     return j1;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  71 */     super.a(world, i, j, k, random);
/*  72 */     if (world.getData(i, j, k) == 0) {
/*  73 */       onPlace(world, i, j, k);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/*  78 */     if (world.getData(i, j, k) == 0) {
/*  79 */       if (world.c(i - 1, j, k, true)) {
/*  80 */         world.setData(i, j, k, 1, 2);
/*  81 */       } else if (world.c(i + 1, j, k, true)) {
/*  82 */         world.setData(i, j, k, 2, 2);
/*  83 */       } else if (world.c(i, j, k - 1, true)) {
/*  84 */         world.setData(i, j, k, 3, 2);
/*  85 */       } else if (world.c(i, j, k + 1, true)) {
/*  86 */         world.setData(i, j, k, 4, 2);
/*  87 */       } else if (m(world, i, j - 1, k)) {
/*  88 */         world.setData(i, j, k, 5, 2);
/*     */       } 
/*     */     }
/*     */     
/*  92 */     e(world, i, j, k);
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  96 */     b(world, i, j, k, block);
/*     */   }
/*     */   
/*     */   protected boolean b(World world, int i, int j, int k, Block block) {
/* 100 */     if (e(world, i, j, k)) {
/* 101 */       int l = world.getData(i, j, k);
/* 102 */       boolean flag = false;
/*     */       
/* 104 */       if (!world.c(i - 1, j, k, true) && l == 1) {
/* 105 */         flag = true;
/*     */       }
/*     */       
/* 108 */       if (!world.c(i + 1, j, k, true) && l == 2) {
/* 109 */         flag = true;
/*     */       }
/*     */       
/* 112 */       if (!world.c(i, j, k - 1, true) && l == 3) {
/* 113 */         flag = true;
/*     */       }
/*     */       
/* 116 */       if (!world.c(i, j, k + 1, true) && l == 4) {
/* 117 */         flag = true;
/*     */       }
/*     */       
/* 120 */       if (!m(world, i, j - 1, k) && l == 5) {
/* 121 */         flag = true;
/*     */       }
/*     */       
/* 124 */       if (flag) {
/* 125 */         b(world, i, j, k, world.getData(i, j, k), 0);
/* 126 */         world.setAir(i, j, k);
/* 127 */         return true;
/*     */       } 
/* 129 */       return false;
/*     */     } 
/*     */     
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean e(World world, int i, int j, int k) {
/* 137 */     if (!canPlace(world, i, j, k)) {
/* 138 */       if (world.getType(i, j, k) == this) {
/* 139 */         b(world, i, j, k, world.getData(i, j, k), 0);
/* 140 */         world.setAir(i, j, k);
/*     */       } 
/*     */       
/* 143 */       return false;
/*     */     } 
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
/* 150 */     int l = world.getData(i, j, k) & 0x7;
/* 151 */     float f = 0.15F;
/*     */     
/* 153 */     if (l == 1) {
/* 154 */       a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/* 155 */     } else if (l == 2) {
/* 156 */       a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/* 157 */     } else if (l == 3) {
/* 158 */       a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/* 159 */     } else if (l == 4) {
/* 160 */       a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */     } else {
/* 162 */       f = 0.1F;
/* 163 */       a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */     } 
/*     */     
/* 166 */     return super.a(world, i, j, k, vec3d, vec3d1);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockTorch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */