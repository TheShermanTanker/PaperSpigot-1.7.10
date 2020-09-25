/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockFromToEvent;
/*     */ 
/*     */ public class BlockFlowing extends BlockFluids {
/*     */   int a;
/*  13 */   boolean[] b = new boolean[4];
/*  14 */   int[] M = new int[4];
/*     */   
/*     */   protected BlockFlowing(Material material) {
/*  17 */     super(material);
/*     */   }
/*     */   
/*     */   private void n(World world, int i, int j, int k) {
/*  21 */     int l = world.getData(i, j, k);
/*     */     
/*  23 */     world.setTypeAndData(i, j, k, Block.getById(Block.getId(this) + 1), l, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  28 */     CraftWorld craftWorld = world.getWorld();
/*  29 */     CraftServer craftServer = world.getServer();
/*  30 */     Block source = (craftWorld == null) ? null : craftWorld.getBlockAt(i, j, k);
/*     */ 
/*     */     
/*  33 */     int l = e(world, i, j, k);
/*  34 */     byte b0 = 1;
/*     */     
/*  36 */     if (this.material == Material.LAVA && !world.worldProvider.f) {
/*  37 */       b0 = 2;
/*     */     }
/*     */     
/*  40 */     boolean flag = true;
/*  41 */     int i1 = getFlowSpeed(world, i, j, k);
/*     */ 
/*     */     
/*  44 */     if (l > 0) {
/*  45 */       byte b1 = -100;
/*     */       
/*  47 */       this.a = 0;
/*  48 */       int k1 = a(world, i - 1, j, k, b1);
/*     */       
/*  50 */       k1 = a(world, i + 1, j, k, k1);
/*  51 */       k1 = a(world, i, j, k - 1, k1);
/*  52 */       k1 = a(world, i, j, k + 1, k1);
/*  53 */       int j1 = k1 + b0;
/*  54 */       if (j1 >= 8 || k1 < 0) {
/*  55 */         j1 = -1;
/*     */       }
/*     */       
/*  58 */       if (e(world, i, j + 1, k) >= 0) {
/*  59 */         int l1 = e(world, i, j + 1, k);
/*     */         
/*  61 */         if (l1 >= 8) {
/*  62 */           j1 = l1;
/*     */         } else {
/*  64 */           j1 = l1 + 8;
/*     */         } 
/*     */       } 
/*     */       
/*  68 */       if (this.a >= 2 && this.material == Material.WATER) {
/*  69 */         if (world.getType(i, j - 1, k).getMaterial().isBuildable()) {
/*  70 */           j1 = 0;
/*  71 */         } else if (world.getType(i, j - 1, k).getMaterial() == this.material && world.getData(i, j - 1, k) == 0) {
/*  72 */           j1 = 0;
/*     */         } 
/*     */       }
/*     */       
/*  76 */       if (!world.paperSpigotConfig.fastDrainLava && this.material == Material.LAVA && l < 8 && j1 < 8 && j1 > l && random.nextInt(4) != 0) {
/*  77 */         i1 *= 4;
/*     */       }
/*     */       
/*  80 */       if (j1 == l) {
/*  81 */         if (flag) {
/*  82 */           n(world, i, j, k);
/*     */         }
/*     */       } else {
/*  85 */         l = j1;
/*  86 */         if (j1 < 0 || canFastDrain(world, i, j, k)) {
/*  87 */           world.setAir(i, j, k);
/*     */         } else {
/*  89 */           world.setData(i, j, k, j1, 2);
/*  90 */           world.a(i, j, k, this, i1);
/*     */           
/*  92 */           world.e(i - 1, j, k, this);
/*  93 */           world.e(i + 1, j, k, this);
/*  94 */           world.e(i, j + 1, k, this);
/*  95 */           world.e(i, j, k - 1, this);
/*  96 */           world.e(i, j, k + 1, this);
/*  97 */           world.spigotConfig.antiXrayInstance.updateNearbyBlocks(world, i, j, k);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 102 */       n(world, i, j, k);
/*     */     } 
/*     */     
/* 105 */     if (world.getType(i, j, k).getMaterial() != this.material)
/*     */       return; 
/* 107 */     if (q(world, i, j - 1, k)) {
/*     */       
/* 109 */       BlockFromToEvent event = new BlockFromToEvent(source, BlockFace.DOWN);
/* 110 */       if (craftServer != null) {
/* 111 */         craftServer.getPluginManager().callEvent((Event)event);
/*     */       }
/*     */       
/* 114 */       if (!event.isCancelled()) {
/* 115 */         if (this.material == Material.LAVA && world.getType(i, j - 1, k).getMaterial() == Material.WATER) {
/* 116 */           world.setTypeUpdate(i, j - 1, k, Blocks.STONE);
/* 117 */           fizz(world, i, j - 1, k);
/*     */           
/*     */           return;
/*     */         } 
/* 121 */         if (l >= 8) {
/* 122 */           flow(world, i, j - 1, k, l);
/*     */         } else {
/* 124 */           flow(world, i, j - 1, k, l + 8);
/*     */         }
/*     */       
/*     */       } 
/* 128 */     } else if (l >= 0 && (l == 0 || p(world, i, j - 1, k))) {
/* 129 */       boolean[] aboolean = o(world, i, j, k);
/*     */       
/* 131 */       int j1 = l + b0;
/* 132 */       if (l >= 8) {
/* 133 */         j1 = 1;
/*     */       }
/*     */       
/* 136 */       if (j1 >= 8) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 141 */       BlockFace[] faces = { BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH };
/* 142 */       int index = 0;
/*     */       
/* 144 */       for (BlockFace currentFace : faces) {
/* 145 */         if (aboolean[index]) {
/* 146 */           BlockFromToEvent event = new BlockFromToEvent(source, currentFace);
/*     */           
/* 148 */           if (craftServer != null) {
/* 149 */             craftServer.getPluginManager().callEvent((Event)event);
/*     */           }
/*     */           
/* 152 */           if (!event.isCancelled()) {
/* 153 */             flow(world, i + currentFace.getModX(), j, k + currentFace.getModZ(), j1);
/*     */           }
/*     */         } 
/* 156 */         index++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void flow(World world, int i, int j, int k, int l) {
/* 163 */     if (q(world, i, j, k)) {
/* 164 */       Block block = world.getType(i, j, k);
/*     */       
/* 166 */       if (this.material == Material.LAVA) {
/* 167 */         fizz(world, i, j, k);
/*     */       } else {
/* 169 */         block.b(world, i, j, k, world.getData(i, j, k), 0);
/*     */       } 
/*     */       
/* 172 */       world.setTypeAndData(i, j, k, this, l, 3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int c(World world, int i, int j, int k, int l, int i1) {
/* 177 */     int j1 = 1000;
/*     */     
/* 179 */     for (int k1 = 0; k1 < 4; k1++) {
/* 180 */       if ((k1 != 0 || i1 != 1) && (k1 != 1 || i1 != 0) && (k1 != 2 || i1 != 3) && (k1 != 3 || i1 != 2)) {
/* 181 */         int l1 = i;
/* 182 */         int i2 = k;
/*     */         
/* 184 */         if (k1 == 0) {
/* 185 */           l1 = i - 1;
/*     */         }
/*     */         
/* 188 */         if (k1 == 1) {
/* 189 */           l1++;
/*     */         }
/*     */         
/* 192 */         if (k1 == 2) {
/* 193 */           i2 = k - 1;
/*     */         }
/*     */         
/* 196 */         if (k1 == 3) {
/* 197 */           i2++;
/*     */         }
/*     */         
/* 200 */         if (!p(world, l1, j, i2) && (world.getType(l1, j, i2).getMaterial() != this.material || world.getData(l1, j, i2) != 0)) {
/* 201 */           if (!p(world, l1, j - 1, i2)) {
/* 202 */             return l;
/*     */           }
/*     */           
/* 205 */           if (l < 4) {
/* 206 */             int j2 = c(world, l1, j, i2, l + 1, k1);
/*     */             
/* 208 */             if (j2 < j1) {
/* 209 */               j1 = j2;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 216 */     return j1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean[] o(World world, int i, int j, int k) {
/*     */     int l;
/* 223 */     for (l = 0; l < 4; l++) {
/* 224 */       this.M[l] = 1000;
/* 225 */       int m = i;
/* 226 */       int j1 = k;
/*     */       
/* 228 */       if (l == 0) {
/* 229 */         m = i - 1;
/*     */       }
/*     */       
/* 232 */       if (l == 1) {
/* 233 */         m++;
/*     */       }
/*     */       
/* 236 */       if (l == 2) {
/* 237 */         j1 = k - 1;
/*     */       }
/*     */       
/* 240 */       if (l == 3) {
/* 241 */         j1++;
/*     */       }
/*     */       
/* 244 */       if (!p(world, m, j, j1) && (world.getType(m, j, j1).getMaterial() != this.material || world.getData(m, j, j1) != 0)) {
/* 245 */         if (p(world, m, j - 1, j1)) {
/* 246 */           this.M[l] = c(world, m, j, j1, 1, l);
/*     */         } else {
/* 248 */           this.M[l] = 0;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 253 */     l = this.M[0];
/*     */     int i1;
/* 255 */     for (i1 = 1; i1 < 4; i1++) {
/* 256 */       if (this.M[i1] < l) {
/* 257 */         l = this.M[i1];
/*     */       }
/*     */     } 
/*     */     
/* 261 */     for (i1 = 0; i1 < 4; i1++) {
/* 262 */       this.b[i1] = (this.M[i1] == l);
/*     */     }
/*     */     
/* 265 */     return this.b;
/*     */   }
/*     */   
/*     */   private boolean p(World world, int i, int j, int k) {
/* 269 */     Block block = world.getType(i, j, k);
/*     */     
/* 271 */     return (block != Blocks.WOODEN_DOOR && block != Blocks.IRON_DOOR_BLOCK && block != Blocks.SIGN_POST && block != Blocks.LADDER && block != Blocks.SUGAR_CANE_BLOCK) ? ((block.material == Material.PORTAL) ? true : block.material.isSolid()) : true;
/*     */   }
/*     */   
/*     */   protected int a(World world, int i, int j, int k, int l) {
/* 275 */     int i1 = e(world, i, j, k);
/*     */     
/* 277 */     if (i1 < 0) {
/* 278 */       return l;
/*     */     }
/* 280 */     if (i1 == 0) {
/* 281 */       this.a++;
/*     */     }
/*     */     
/* 284 */     if (i1 >= 8) {
/* 285 */       i1 = 0;
/*     */     }
/*     */     
/* 288 */     return (l >= 0 && i1 >= l) ? l : i1;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean q(World world, int i, int j, int k) {
/* 293 */     Material material = world.getType(i, j, k).getMaterial();
/*     */     
/* 295 */     return (material == this.material) ? false : ((material == Material.LAVA) ? false : (!p(world, i, j, k)));
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/* 299 */     super.onPlace(world, i, j, k);
/* 300 */     if (world.getType(i, j, k) == this) {
/* 301 */       world.a(i, j, k, this, getFlowSpeed(world, i, j, k));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean L() {
/* 306 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFlowSpeed(World world, int x, int y, int z) {
/* 313 */     if (getMaterial() == Material.LAVA) {
/* 314 */       return world.worldProvider.g ? world.paperSpigotConfig.lavaFlowSpeedNether : world.paperSpigotConfig.lavaFlowSpeedNormal;
/*     */     }
/* 316 */     if (getMaterial() == Material.WATER && (world.getType(x, y, z - 1).getMaterial() == Material.LAVA || world.getType(x, y, z + 1).getMaterial() == Material.LAVA || world.getType(x - 1, y, z).getMaterial() == Material.LAVA || world.getType(x + 1, y, z).getMaterial() == Material.LAVA))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 321 */       return world.paperSpigotConfig.waterOverLavaFlowSpeed;
/*     */     }
/* 323 */     return a(world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getData(World world, int x, int y, int z) {
/* 330 */     int data = e(world, x, y, z);
/* 331 */     return (data < 8) ? data : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canFastDrain(World world, int x, int y, int z) {
/* 338 */     boolean result = false;
/* 339 */     int data = getData(world, x, y, z);
/* 340 */     if (this.material == Material.WATER) {
/* 341 */       if (world.paperSpigotConfig.fastDrainWater) {
/* 342 */         result = true;
/* 343 */         if (getData(world, x, y - 1, z) < 0) {
/* 344 */           result = false;
/* 345 */         } else if (world.getType(x, y, z - 1).getMaterial() == Material.WATER && getData(world, x, y, z - 1) < data) {
/* 346 */           result = false;
/* 347 */         } else if (world.getType(x, y, z + 1).getMaterial() == Material.WATER && getData(world, x, y, z + 1) < data) {
/* 348 */           result = false;
/* 349 */         } else if (world.getType(x - 1, y, z).getMaterial() == Material.WATER && getData(world, x - 1, y, z) < data) {
/* 350 */           result = false;
/* 351 */         } else if (world.getType(x + 1, y, z).getMaterial() == Material.WATER && getData(world, x + 1, y, z) < data) {
/* 352 */           result = false;
/*     */         } 
/*     */       } 
/* 355 */     } else if (this.material == Material.LAVA && 
/* 356 */       world.paperSpigotConfig.fastDrainLava) {
/* 357 */       result = true;
/* 358 */       if (getData(world, x, y - 1, z) < 0 || world.getType(x, y + 1, z).getMaterial() != Material.AIR) {
/* 359 */         result = false;
/* 360 */       } else if (world.getType(x, y, z - 1).getMaterial() == Material.LAVA && getData(world, x, y, z - 1) < data) {
/* 361 */         result = false;
/* 362 */       } else if (world.getType(x, y, z + 1).getMaterial() == Material.LAVA && getData(world, x, y, z + 1) < data) {
/* 363 */         result = false;
/* 364 */       } else if (world.getType(x - 1, y, z).getMaterial() == Material.LAVA && getData(world, x - 1, y, z) < data) {
/* 365 */         result = false;
/* 366 */       } else if (world.getType(x + 1, y, z).getMaterial() == Material.LAVA && getData(world, x + 1, y, z) < data) {
/* 367 */         result = false;
/*     */       } 
/*     */     } 
/*     */     
/* 371 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockFlowing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */