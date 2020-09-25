/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockLever extends Block {
/*     */   protected BlockLever() {
/*   8 */     super(Material.ORIENTABLE);
/*   9 */     a(CreativeModeTab.d);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  13 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  17 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  21 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  25 */     return 12;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k, int l) {
/*  29 */     return (l == 0 && world.getType(i, j + 1, k).r()) ? true : ((l == 1 && World.a(world, i, j - 1, k)) ? true : ((l == 2 && world.getType(i, j, k + 1).r()) ? true : ((l == 3 && world.getType(i, j, k - 1).r()) ? true : ((l == 4 && world.getType(i + 1, j, k).r()) ? true : ((l == 5 && world.getType(i - 1, j, k).r()))))));
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  33 */     return world.getType(i - 1, j, k).r() ? true : (world.getType(i + 1, j, k).r() ? true : (world.getType(i, j, k - 1).r() ? true : (world.getType(i, j, k + 1).r() ? true : (World.a(world, i, j - 1, k) ? true : world.getType(i, j + 1, k).r()))));
/*     */   }
/*     */   
/*     */   public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
/*  37 */     int j1 = i1 & 0x8;
/*  38 */     int k1 = i1 & 0x7;
/*  39 */     byte b0 = -1;
/*     */     
/*  41 */     if (l == 0 && world.getType(i, j + 1, k).r()) {
/*  42 */       b0 = 0;
/*     */     }
/*     */     
/*  45 */     if (l == 1 && World.a(world, i, j - 1, k)) {
/*  46 */       b0 = 5;
/*     */     }
/*     */     
/*  49 */     if (l == 2 && world.getType(i, j, k + 1).r()) {
/*  50 */       b0 = 4;
/*     */     }
/*     */     
/*  53 */     if (l == 3 && world.getType(i, j, k - 1).r()) {
/*  54 */       b0 = 3;
/*     */     }
/*     */     
/*  57 */     if (l == 4 && world.getType(i + 1, j, k).r()) {
/*  58 */       b0 = 2;
/*     */     }
/*     */     
/*  61 */     if (l == 5 && world.getType(i - 1, j, k).r()) {
/*  62 */       b0 = 1;
/*     */     }
/*     */     
/*  65 */     return b0 + j1;
/*     */   }
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/*  69 */     int l = world.getData(i, j, k);
/*  70 */     int i1 = l & 0x7;
/*  71 */     int j1 = l & 0x8;
/*     */     
/*  73 */     if (i1 == b(1)) {
/*  74 */       if ((MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x1) == 0) {
/*  75 */         world.setData(i, j, k, 0x5 | j1, 2);
/*     */       } else {
/*  77 */         world.setData(i, j, k, 0x6 | j1, 2);
/*     */       } 
/*  79 */     } else if (i1 == b(0)) {
/*  80 */       if ((MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x1) == 0) {
/*  81 */         world.setData(i, j, k, 0x7 | j1, 2);
/*     */       } else {
/*  83 */         world.setData(i, j, k, 0x0 | j1, 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int b(int i) {
/*  89 */     switch (i) {
/*     */       case 0:
/*  91 */         return 0;
/*     */       
/*     */       case 1:
/*  94 */         return 5;
/*     */       
/*     */       case 2:
/*  97 */         return 4;
/*     */       
/*     */       case 3:
/* 100 */         return 3;
/*     */       
/*     */       case 4:
/* 103 */         return 2;
/*     */       
/*     */       case 5:
/* 106 */         return 1;
/*     */     } 
/*     */     
/* 109 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 114 */     if (e(world, i, j, k)) {
/* 115 */       int l = world.getData(i, j, k) & 0x7;
/* 116 */       boolean flag = false;
/*     */       
/* 118 */       if (!world.getType(i - 1, j, k).r() && l == 1) {
/* 119 */         flag = true;
/*     */       }
/*     */       
/* 122 */       if (!world.getType(i + 1, j, k).r() && l == 2) {
/* 123 */         flag = true;
/*     */       }
/*     */       
/* 126 */       if (!world.getType(i, j, k - 1).r() && l == 3) {
/* 127 */         flag = true;
/*     */       }
/*     */       
/* 130 */       if (!world.getType(i, j, k + 1).r() && l == 4) {
/* 131 */         flag = true;
/*     */       }
/*     */       
/* 134 */       if (!World.a(world, i, j - 1, k) && l == 5) {
/* 135 */         flag = true;
/*     */       }
/*     */       
/* 138 */       if (!World.a(world, i, j - 1, k) && l == 6) {
/* 139 */         flag = true;
/*     */       }
/*     */       
/* 142 */       if (!world.getType(i, j + 1, k).r() && l == 0) {
/* 143 */         flag = true;
/*     */       }
/*     */       
/* 146 */       if (!world.getType(i, j + 1, k).r() && l == 7) {
/* 147 */         flag = true;
/*     */       }
/*     */       
/* 150 */       if (flag) {
/* 151 */         b(world, i, j, k, world.getData(i, j, k), 0);
/* 152 */         world.setAir(i, j, k);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean e(World world, int i, int j, int k) {
/* 158 */     if (!canPlace(world, i, j, k)) {
/* 159 */       b(world, i, j, k, world.getData(i, j, k), 0);
/* 160 */       world.setAir(i, j, k);
/* 161 */       return false;
/*     */     } 
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 168 */     int l = iblockaccess.getData(i, j, k) & 0x7;
/* 169 */     float f = 0.1875F;
/*     */     
/* 171 */     if (l == 1) {
/* 172 */       a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/* 173 */     } else if (l == 2) {
/* 174 */       a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/* 175 */     } else if (l == 3) {
/* 176 */       a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/* 177 */     } else if (l == 4) {
/* 178 */       a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/* 179 */     } else if (l != 5 && l != 6) {
/* 180 */       if (l == 0 || l == 7) {
/* 181 */         f = 0.25F;
/* 182 */         a(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
/*     */       } 
/*     */     } else {
/* 185 */       f = 0.25F;
/* 186 */       a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/* 191 */     if (world.isStatic) {
/* 192 */       return true;
/*     */     }
/* 194 */     int i1 = world.getData(i, j, k);
/* 195 */     int j1 = i1 & 0x7;
/* 196 */     int k1 = 8 - (i1 & 0x8);
/*     */ 
/*     */     
/* 199 */     Block block = world.getWorld().getBlockAt(i, j, k);
/* 200 */     int old = (k1 != 8) ? 15 : 0;
/* 201 */     int current = (k1 == 8) ? 15 : 0;
/*     */     
/* 203 */     BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
/* 204 */     world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*     */     
/* 206 */     if (((eventRedstone.getNewCurrent() > 0) ? true : false) != ((k1 == 8) ? true : false)) {
/* 207 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 211 */     world.setData(i, j, k, j1 + k1, 3);
/* 212 */     world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, (k1 > 0) ? 0.6F : 0.5F);
/* 213 */     world.applyPhysics(i, j, k, this);
/* 214 */     if (j1 == 1) {
/* 215 */       world.applyPhysics(i - 1, j, k, this);
/* 216 */     } else if (j1 == 2) {
/* 217 */       world.applyPhysics(i + 1, j, k, this);
/* 218 */     } else if (j1 == 3) {
/* 219 */       world.applyPhysics(i, j, k - 1, this);
/* 220 */     } else if (j1 == 4) {
/* 221 */       world.applyPhysics(i, j, k + 1, this);
/* 222 */     } else if (j1 != 5 && j1 != 6) {
/* 223 */       if (j1 == 0 || j1 == 7) {
/* 224 */         world.applyPhysics(i, j + 1, k, this);
/*     */       }
/*     */     } else {
/* 227 */       world.applyPhysics(i, j - 1, k, this);
/*     */     } 
/*     */     
/* 230 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/* 235 */     if ((l & 0x8) > 0) {
/* 236 */       world.applyPhysics(i, j, k, this);
/* 237 */       int i1 = l & 0x7;
/*     */       
/* 239 */       if (i1 == 1) {
/* 240 */         world.applyPhysics(i - 1, j, k, this);
/* 241 */       } else if (i1 == 2) {
/* 242 */         world.applyPhysics(i + 1, j, k, this);
/* 243 */       } else if (i1 == 3) {
/* 244 */         world.applyPhysics(i, j, k - 1, this);
/* 245 */       } else if (i1 == 4) {
/* 246 */         world.applyPhysics(i, j, k + 1, this);
/* 247 */       } else if (i1 != 5 && i1 != 6) {
/* 248 */         if (i1 == 0 || i1 == 7) {
/* 249 */           world.applyPhysics(i, j + 1, k, this);
/*     */         }
/*     */       } else {
/* 252 */         world.applyPhysics(i, j - 1, k, this);
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     super.remove(world, i, j, k, block, l);
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 260 */     return ((iblockaccess.getData(i, j, k) & 0x8) > 0) ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 264 */     int i1 = iblockaccess.getData(i, j, k);
/*     */     
/* 266 */     if ((i1 & 0x8) == 0) {
/* 267 */       return 0;
/*     */     }
/* 269 */     int j1 = i1 & 0x7;
/*     */     
/* 271 */     return (j1 == 0 && l == 0) ? 15 : ((j1 == 7 && l == 0) ? 15 : ((j1 == 6 && l == 1) ? 15 : ((j1 == 5 && l == 1) ? 15 : ((j1 == 4 && l == 2) ? 15 : ((j1 == 3 && l == 3) ? 15 : ((j1 == 2 && l == 4) ? 15 : ((j1 == 1 && l == 5) ? 15 : 0)))))));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPowerSource() {
/* 276 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockLever.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */