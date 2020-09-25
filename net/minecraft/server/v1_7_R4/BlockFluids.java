/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public abstract class BlockFluids
/*     */   extends Block {
/*     */   protected BlockFluids(Material material) {
/*   8 */     super(material);
/*   9 */     float f = 0.0F;
/*  10 */     float f1 = 0.0F;
/*     */     
/*  12 */     a(0.0F + f1, 0.0F + f, 0.0F + f1, 1.0F + f1, 1.0F + f, 1.0F + f1);
/*  13 */     a(true);
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
/*  17 */     return (this.material != Material.LAVA);
/*     */   }
/*     */   
/*     */   public static float b(int i) {
/*  21 */     if (i >= 8) {
/*  22 */       i = 0;
/*     */     }
/*     */     
/*  25 */     return (i + 1) / 9.0F;
/*     */   }
/*     */   
/*     */   protected int e(World world, int i, int j, int k) {
/*  29 */     return (world.getType(i, j, k).getMaterial() == this.material) ? world.getData(i, j, k) : -1;
/*     */   }
/*     */   
/*     */   protected int e(IBlockAccess iblockaccess, int i, int j, int k) {
/*  33 */     if (iblockaccess.getType(i, j, k).getMaterial() != this.material) {
/*  34 */       return -1;
/*     */     }
/*  36 */     int l = iblockaccess.getData(i, j, k);
/*     */     
/*  38 */     if (l >= 8) {
/*  39 */       l = 0;
/*     */     }
/*     */     
/*  42 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  47 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(int i, boolean flag) {
/*  55 */     return (flag && i == 0);
/*     */   }
/*     */   
/*     */   public boolean d(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/*  59 */     Material material = iblockaccess.getType(i, j, k).getMaterial();
/*     */     
/*  61 */     return (material == this.material) ? false : ((l == 1) ? true : ((material == Material.ICE) ? false : super.d(iblockaccess, i, j, k, l)));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  65 */     return null;
/*     */   }
/*     */   
/*     */   public int b() {
/*  69 */     return 4;
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/*  73 */     return null;
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/*  77 */     return 0;
/*     */   }
/*     */   
/*     */   private Vec3D f(IBlockAccess iblockaccess, int i, int j, int k) {
/*  81 */     Vec3D vec3d = Vec3D.a(0.0D, 0.0D, 0.0D);
/*  82 */     int l = e(iblockaccess, i, j, k);
/*     */     
/*  84 */     for (int i1 = 0; i1 < 4; i1++) {
/*  85 */       int j1 = i;
/*  86 */       int k1 = k;
/*     */       
/*  88 */       if (i1 == 0) {
/*  89 */         j1 = i - 1;
/*     */       }
/*     */       
/*  92 */       if (i1 == 1) {
/*  93 */         k1 = k - 1;
/*     */       }
/*     */       
/*  96 */       if (i1 == 2) {
/*  97 */         j1++;
/*     */       }
/*     */       
/* 100 */       if (i1 == 3) {
/* 101 */         k1++;
/*     */       }
/*     */       
/* 104 */       int l1 = e(iblockaccess, j1, j, k1);
/*     */ 
/*     */       
/* 107 */       if (l1 < 0) {
/* 108 */         if (!iblockaccess.getType(j1, j, k1).getMaterial().isSolid()) {
/* 109 */           l1 = e(iblockaccess, j1, j - 1, k1);
/* 110 */           if (l1 >= 0) {
/* 111 */             int i2 = l1 - l - 8;
/* 112 */             vec3d = vec3d.add(((j1 - i) * i2), ((j - j) * i2), ((k1 - k) * i2));
/*     */           } 
/*     */         } 
/* 115 */       } else if (l1 >= 0) {
/* 116 */         int i2 = l1 - l;
/* 117 */         vec3d = vec3d.add(((j1 - i) * i2), ((j - j) * i2), ((k1 - k) * i2));
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     if (iblockaccess.getData(i, j, k) >= 8) {
/* 122 */       boolean flag = false;
/*     */       
/* 124 */       if (flag || d(iblockaccess, i, j, k - 1, 2)) {
/* 125 */         flag = true;
/*     */       }
/*     */       
/* 128 */       if (flag || d(iblockaccess, i, j, k + 1, 3)) {
/* 129 */         flag = true;
/*     */       }
/*     */       
/* 132 */       if (flag || d(iblockaccess, i - 1, j, k, 4)) {
/* 133 */         flag = true;
/*     */       }
/*     */       
/* 136 */       if (flag || d(iblockaccess, i + 1, j, k, 5)) {
/* 137 */         flag = true;
/*     */       }
/*     */       
/* 140 */       if (flag || d(iblockaccess, i, j + 1, k - 1, 2)) {
/* 141 */         flag = true;
/*     */       }
/*     */       
/* 144 */       if (flag || d(iblockaccess, i, j + 1, k + 1, 3)) {
/* 145 */         flag = true;
/*     */       }
/*     */       
/* 148 */       if (flag || d(iblockaccess, i - 1, j + 1, k, 4)) {
/* 149 */         flag = true;
/*     */       }
/*     */       
/* 152 */       if (flag || d(iblockaccess, i + 1, j + 1, k, 5)) {
/* 153 */         flag = true;
/*     */       }
/*     */       
/* 156 */       if (flag) {
/* 157 */         vec3d = vec3d.a().add(0.0D, -6.0D, 0.0D);
/*     */       }
/*     */     } 
/*     */     
/* 161 */     vec3d = vec3d.a();
/* 162 */     return vec3d;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity, Vec3D vec3d) {
/* 166 */     Vec3D vec3d1 = f(world, i, j, k);
/*     */     
/* 168 */     vec3d.a += vec3d1.a;
/* 169 */     vec3d.b += vec3d1.b;
/* 170 */     vec3d.c += vec3d1.c;
/*     */   }
/*     */   
/*     */   public int a(World world) {
/* 174 */     return (this.material == Material.WATER) ? 5 : ((this.material == Material.LAVA) ? (world.worldProvider.g ? 10 : 30) : 0);
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/* 178 */     n(world, i, j, k);
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 182 */     n(world, i, j, k);
/*     */   }
/*     */   
/*     */   private void n(World world, int i, int j, int k) {
/* 186 */     if (world.getType(i, j, k) == this && 
/* 187 */       this.material == Material.LAVA) {
/* 188 */       boolean flag = false;
/*     */       
/* 190 */       if (flag || world.getType(i, j, k - 1).getMaterial() == Material.WATER) {
/* 191 */         flag = true;
/*     */       }
/*     */       
/* 194 */       if (flag || world.getType(i, j, k + 1).getMaterial() == Material.WATER) {
/* 195 */         flag = true;
/*     */       }
/*     */       
/* 198 */       if (flag || world.getType(i - 1, j, k).getMaterial() == Material.WATER) {
/* 199 */         flag = true;
/*     */       }
/*     */       
/* 202 */       if (flag || world.getType(i + 1, j, k).getMaterial() == Material.WATER) {
/* 203 */         flag = true;
/*     */       }
/*     */       
/* 206 */       if (flag || world.getType(i, j + 1, k).getMaterial() == Material.WATER) {
/* 207 */         flag = true;
/*     */       }
/*     */       
/* 210 */       if (flag) {
/* 211 */         int l = world.getData(i, j, k);
/*     */         
/* 213 */         if (l == 0) {
/* 214 */           world.setTypeUpdate(i, j, k, Blocks.OBSIDIAN);
/* 215 */         } else if (l > 0) {
/* 216 */           world.setTypeUpdate(i, j, k, Blocks.COBBLESTONE);
/*     */         } 
/*     */         
/* 219 */         fizz(world, i, j, k);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fizz(World world, int i, int j, int k) {
/* 226 */     world.makeSound((i + 0.5F), (j + 0.5F), (k + 0.5F), "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
/*     */     
/* 228 */     for (int l = 0; l < 8; l++)
/* 229 */       world.addParticle("largesmoke", i + Math.random(), j + 1.2D, k + Math.random(), 0.0D, 0.0D, 0.0D); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockFluids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */