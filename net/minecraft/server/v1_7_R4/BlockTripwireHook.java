/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockTripwireHook extends Block {
/*     */   public BlockTripwireHook() {
/*  10 */     super(Material.ORIENTABLE);
/*  11 */     a(CreativeModeTab.d);
/*  12 */     a(true);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  16 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  20 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  24 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  28 */     return 29;
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  32 */     return 10;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k, int l) {
/*  36 */     return (l == 2 && world.getType(i, j, k + 1).r()) ? true : ((l == 3 && world.getType(i, j, k - 1).r()) ? true : ((l == 4 && world.getType(i + 1, j, k).r()) ? true : ((l == 5 && world.getType(i - 1, j, k).r()))));
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  40 */     return world.getType(i - 1, j, k).r() ? true : (world.getType(i + 1, j, k).r() ? true : (world.getType(i, j, k - 1).r() ? true : world.getType(i, j, k + 1).r()));
/*     */   }
/*     */   
/*     */   public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
/*  44 */     byte b0 = 0;
/*     */     
/*  46 */     if (l == 2 && world.c(i, j, k + 1, true)) {
/*  47 */       b0 = 2;
/*     */     }
/*     */     
/*  50 */     if (l == 3 && world.c(i, j, k - 1, true)) {
/*  51 */       b0 = 0;
/*     */     }
/*     */     
/*  54 */     if (l == 4 && world.c(i + 1, j, k, true)) {
/*  55 */       b0 = 1;
/*     */     }
/*     */     
/*  58 */     if (l == 5 && world.c(i - 1, j, k, true)) {
/*  59 */       b0 = 3;
/*     */     }
/*     */     
/*  62 */     return b0;
/*     */   }
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, int l) {
/*  66 */     a(world, i, j, k, false, l, false, -1, 0);
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  70 */     if (block != this && 
/*  71 */       e(world, i, j, k)) {
/*  72 */       int l = world.getData(i, j, k);
/*  73 */       int i1 = l & 0x3;
/*  74 */       boolean flag = false;
/*     */       
/*  76 */       if (!world.getType(i - 1, j, k).r() && i1 == 3) {
/*  77 */         flag = true;
/*     */       }
/*     */       
/*  80 */       if (!world.getType(i + 1, j, k).r() && i1 == 1) {
/*  81 */         flag = true;
/*     */       }
/*     */       
/*  84 */       if (!world.getType(i, j, k - 1).r() && i1 == 0) {
/*  85 */         flag = true;
/*     */       }
/*     */       
/*  88 */       if (!world.getType(i, j, k + 1).r() && i1 == 2) {
/*  89 */         flag = true;
/*     */       }
/*     */       
/*  92 */       if (flag) {
/*  93 */         b(world, i, j, k, l, 0);
/*  94 */         world.setAir(i, j, k);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, boolean flag, int l, boolean flag1, int i1, int j1) {
/* 101 */     int m, n, k1 = l & 0x3;
/* 102 */     int flag2 = ((l & 0x4) == 4) ? 1 : 0;
/* 103 */     boolean flag3 = ((l & 0x8) == 8);
/* 104 */     boolean flag4 = !flag;
/* 105 */     boolean flag5 = false;
/* 106 */     boolean flag6 = !World.a(world, i, j - 1, k);
/* 107 */     int l1 = Direction.a[k1];
/* 108 */     int i2 = Direction.b[k1];
/* 109 */     int j2 = 0;
/* 110 */     int[] aint = new int[42];
/*     */ 
/*     */ 
/*     */     
/*     */     int l2;
/*     */ 
/*     */     
/* 117 */     for (l2 = 1; l2 < 42; l2++) {
/* 118 */       int k2 = i + l1 * l2;
/* 119 */       int i3 = k + i2 * l2;
/* 120 */       Block block1 = world.getType(k2, j, i3);
/*     */       
/* 122 */       if (block1 == Blocks.TRIPWIRE_SOURCE) {
/* 123 */         int j3 = world.getData(k2, j, i3);
/* 124 */         if ((j3 & 0x3) == Direction.f[k1]) {
/* 125 */           j2 = l2;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/* 130 */       if (block1 != Blocks.TRIPWIRE && l2 != i1) {
/* 131 */         aint[l2] = -1;
/* 132 */         flag4 = false;
/*     */       } else {
/* 134 */         int j3 = (l2 == i1) ? j1 : world.getData(k2, j, i3);
/* 135 */         int flag7 = ((j3 & 0x8) != 8) ? 1 : 0;
/* 136 */         boolean flag8 = ((j3 & 0x1) == 1);
/* 137 */         boolean flag9 = ((j3 & 0x2) == 2);
/*     */         
/* 139 */         int i4 = flag4 & ((flag9 == flag6) ? 1 : 0);
/* 140 */         n = flag5 | ((flag7 && flag8) ? 1 : 0);
/* 141 */         aint[l2] = j3;
/* 142 */         if (l2 == i1) {
/* 143 */           world.a(i, j, k, this, a(world));
/* 144 */           m = i4 & flag7;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     m &= (j2 > 1) ? 1 : 0;
/* 150 */     n &= m;
/* 151 */     l2 = ((m != 0) ? 4 : 0) | ((n != 0) ? 8 : 0);
/* 152 */     l = k1 | l2;
/*     */ 
/*     */     
/* 155 */     if (j2 > 0) {
/* 156 */       int k2 = i + l1 * j2;
/* 157 */       int i3 = k + i2 * j2;
/* 158 */       int k3 = Direction.f[k1];
/* 159 */       world.setData(k2, j, i3, k3 | l2, 3);
/* 160 */       a(world, k2, j, i3, k3);
/* 161 */       a(world, k2, j, i3, m, n, flag2, flag3);
/*     */     } 
/*     */ 
/*     */     
/* 165 */     Block block = world.getWorld().getBlockAt(i, j, k);
/*     */     
/* 167 */     BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
/* 168 */     world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*     */     
/* 170 */     if (eventRedstone.getNewCurrent() > 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 175 */     a(world, i, j, k, m, n, flag2, flag3);
/* 176 */     if (!flag) {
/* 177 */       world.setData(i, j, k, l, 3);
/* 178 */       if (flag1) {
/* 179 */         a(world, i, j, k, k1);
/*     */       }
/*     */     } 
/*     */     
/* 183 */     if (flag2 != m) {
/* 184 */       for (int k2 = 1; k2 < j2; k2++) {
/* 185 */         int i3 = i + l1 * k2;
/* 186 */         int k3 = k + i2 * k2;
/* 187 */         int j3 = aint[k2];
/* 188 */         if (j3 >= 0) {
/* 189 */           if (m != 0) {
/* 190 */             j3 |= 0x4;
/*     */           } else {
/* 192 */             j3 &= 0xFFFFFFFB;
/*     */           } 
/*     */           
/* 195 */           world.setData(i3, j, k3, j3, 3);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/* 202 */     a(world, i, j, k, false, world.getData(i, j, k), true, -1, 0);
/*     */   }
/*     */   
/*     */   private void a(World world, int i, int j, int k, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
/* 206 */     if (flag1 && !flag3) {
/* 207 */       world.makeSound(i + 0.5D, j + 0.1D, k + 0.5D, "random.click", 0.4F, 0.6F);
/* 208 */     } else if (!flag1 && flag3) {
/* 209 */       world.makeSound(i + 0.5D, j + 0.1D, k + 0.5D, "random.click", 0.4F, 0.5F);
/* 210 */     } else if (flag && !flag2) {
/* 211 */       world.makeSound(i + 0.5D, j + 0.1D, k + 0.5D, "random.click", 0.4F, 0.7F);
/* 212 */     } else if (!flag && flag2) {
/* 213 */       world.makeSound(i + 0.5D, j + 0.1D, k + 0.5D, "random.bowhit", 0.4F, 1.2F / (world.random.nextFloat() * 0.2F + 0.9F));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(World world, int i, int j, int k, int l) {
/* 218 */     world.applyPhysics(i, j, k, this);
/* 219 */     if (l == 3) {
/* 220 */       world.applyPhysics(i - 1, j, k, this);
/* 221 */     } else if (l == 1) {
/* 222 */       world.applyPhysics(i + 1, j, k, this);
/* 223 */     } else if (l == 0) {
/* 224 */       world.applyPhysics(i, j, k - 1, this);
/* 225 */     } else if (l == 2) {
/* 226 */       world.applyPhysics(i, j, k + 1, this);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean e(World world, int i, int j, int k) {
/* 231 */     if (!canPlace(world, i, j, k)) {
/* 232 */       b(world, i, j, k, world.getData(i, j, k), 0);
/* 233 */       world.setAir(i, j, k);
/* 234 */       return false;
/*     */     } 
/* 236 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 241 */     int l = iblockaccess.getData(i, j, k) & 0x3;
/* 242 */     float f = 0.1875F;
/*     */     
/* 244 */     if (l == 3) {
/* 245 */       a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/* 246 */     } else if (l == 1) {
/* 247 */       a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/* 248 */     } else if (l == 0) {
/* 249 */       a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/* 250 */     } else if (l == 2) {
/* 251 */       a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/* 256 */     boolean flag = ((l & 0x4) == 4);
/* 257 */     boolean flag1 = ((l & 0x8) == 8);
/*     */     
/* 259 */     if (flag || flag1) {
/* 260 */       a(world, i, j, k, true, l, false, -1, 0);
/*     */     }
/*     */     
/* 263 */     if (flag1) {
/* 264 */       world.applyPhysics(i, j, k, this);
/* 265 */       int i1 = l & 0x3;
/*     */       
/* 267 */       if (i1 == 3) {
/* 268 */         world.applyPhysics(i - 1, j, k, this);
/* 269 */       } else if (i1 == 1) {
/* 270 */         world.applyPhysics(i + 1, j, k, this);
/* 271 */       } else if (i1 == 0) {
/* 272 */         world.applyPhysics(i, j, k - 1, this);
/* 273 */       } else if (i1 == 2) {
/* 274 */         world.applyPhysics(i, j, k + 1, this);
/*     */       } 
/*     */     } 
/*     */     
/* 278 */     super.remove(world, i, j, k, block, l);
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 282 */     return ((iblockaccess.getData(i, j, k) & 0x8) == 8) ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 286 */     int i1 = iblockaccess.getData(i, j, k);
/*     */     
/* 288 */     if ((i1 & 0x8) != 8) {
/* 289 */       return 0;
/*     */     }
/* 291 */     int j1 = i1 & 0x3;
/*     */     
/* 293 */     return (j1 == 2 && l == 2) ? 15 : ((j1 == 0 && l == 3) ? 15 : ((j1 == 1 && l == 4) ? 15 : ((j1 == 3 && l == 5) ? 15 : 0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPowerSource() {
/* 298 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockTripwireHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */