/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ 
/*     */ public abstract class BlockButtonAbstract
/*     */   extends Block {
/*     */   private final boolean a;
/*     */   
/*     */   protected BlockButtonAbstract(boolean flag) {
/*  16 */     super(Material.ORIENTABLE);
/*  17 */     a(true);
/*  18 */     a(CreativeModeTab.d);
/*  19 */     this.a = flag;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  23 */     return null;
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  27 */     return this.a ? 30 : 20;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  31 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  35 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k, int l) {
/*  39 */     return (l == 2 && world.getType(i, j, k + 1).r()) ? true : ((l == 3 && world.getType(i, j, k - 1).r()) ? true : ((l == 4 && world.getType(i + 1, j, k).r()) ? true : ((l == 5 && world.getType(i - 1, j, k).r()))));
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  43 */     return world.getType(i - 1, j, k).r() ? true : (world.getType(i + 1, j, k).r() ? true : (world.getType(i, j, k - 1).r() ? true : world.getType(i, j, k + 1).r()));
/*     */   }
/*     */   
/*     */   public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
/*  47 */     int j1 = world.getData(i, j, k);
/*  48 */     int k1 = j1 & 0x8;
/*     */     
/*  50 */     j1 &= 0x7;
/*  51 */     if (l == 2 && world.getType(i, j, k + 1).r()) {
/*  52 */       j1 = 4;
/*  53 */     } else if (l == 3 && world.getType(i, j, k - 1).r()) {
/*  54 */       j1 = 3;
/*  55 */     } else if (l == 4 && world.getType(i + 1, j, k).r()) {
/*  56 */       j1 = 2;
/*  57 */     } else if (l == 5 && world.getType(i - 1, j, k).r()) {
/*  58 */       j1 = 1;
/*     */     } else {
/*  60 */       j1 = e(world, i, j, k);
/*     */     } 
/*     */     
/*  63 */     return j1 + k1;
/*     */   }
/*     */   
/*     */   private int e(World world, int i, int j, int k) {
/*  67 */     return world.getType(i - 1, j, k).r() ? 1 : (world.getType(i + 1, j, k).r() ? 2 : (world.getType(i, j, k - 1).r() ? 3 : (world.getType(i, j, k + 1).r() ? 4 : 1)));
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  71 */     if (m(world, i, j, k)) {
/*  72 */       int l = world.getData(i, j, k) & 0x7;
/*  73 */       boolean flag = false;
/*     */       
/*  75 */       if (!world.getType(i - 1, j, k).r() && l == 1) {
/*  76 */         flag = true;
/*     */       }
/*     */       
/*  79 */       if (!world.getType(i + 1, j, k).r() && l == 2) {
/*  80 */         flag = true;
/*     */       }
/*     */       
/*  83 */       if (!world.getType(i, j, k - 1).r() && l == 3) {
/*  84 */         flag = true;
/*     */       }
/*     */       
/*  87 */       if (!world.getType(i, j, k + 1).r() && l == 4) {
/*  88 */         flag = true;
/*     */       }
/*     */       
/*  91 */       if (flag) {
/*  92 */         b(world, i, j, k, world.getData(i, j, k), 0);
/*  93 */         world.setAir(i, j, k);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean m(World world, int i, int j, int k) {
/*  99 */     if (!canPlace(world, i, j, k)) {
/* 100 */       b(world, i, j, k, world.getData(i, j, k), 0);
/* 101 */       world.setAir(i, j, k);
/* 102 */       return false;
/*     */     } 
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 109 */     int l = iblockaccess.getData(i, j, k);
/*     */     
/* 111 */     b(l);
/*     */   }
/*     */   
/*     */   private void b(int i) {
/* 115 */     int j = i & 0x7;
/* 116 */     boolean flag = ((i & 0x8) > 0);
/* 117 */     float f = 0.375F;
/* 118 */     float f1 = 0.625F;
/* 119 */     float f2 = 0.1875F;
/* 120 */     float f3 = 0.125F;
/*     */     
/* 122 */     if (flag) {
/* 123 */       f3 = 0.0625F;
/*     */     }
/*     */     
/* 126 */     if (j == 1) {
/* 127 */       a(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
/* 128 */     } else if (j == 2) {
/* 129 */       a(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
/* 130 */     } else if (j == 3) {
/* 131 */       a(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
/* 132 */     } else if (j == 4) {
/* 133 */       a(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/* 140 */     int i1 = world.getData(i, j, k);
/* 141 */     int j1 = i1 & 0x7;
/* 142 */     int k1 = 8 - (i1 & 0x8);
/*     */     
/* 144 */     if (k1 == 0) {
/* 145 */       return true;
/*     */     }
/*     */     
/* 148 */     Block block = world.getWorld().getBlockAt(i, j, k);
/* 149 */     int old = (k1 != 8) ? 15 : 0;
/* 150 */     int current = (k1 == 8) ? 15 : 0;
/*     */     
/* 152 */     BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
/* 153 */     world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*     */     
/* 155 */     if (((eventRedstone.getNewCurrent() > 0) ? true : false) != ((k1 == 8) ? true : false)) {
/* 156 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 160 */     world.setData(i, j, k, j1 + k1, 3);
/* 161 */     world.c(i, j, k, i, j, k);
/* 162 */     world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.6F);
/* 163 */     a(world, i, j, k, j1);
/* 164 */     world.a(i, j, k, this, a(world));
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/* 170 */     if ((l & 0x8) > 0) {
/* 171 */       int i1 = l & 0x7;
/*     */       
/* 173 */       a(world, i, j, k, i1);
/*     */     } 
/*     */     
/* 176 */     super.remove(world, i, j, k, block, l);
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 180 */     return ((iblockaccess.getData(i, j, k) & 0x8) > 0) ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 184 */     int i1 = iblockaccess.getData(i, j, k);
/*     */     
/* 186 */     if ((i1 & 0x8) == 0) {
/* 187 */       return 0;
/*     */     }
/* 189 */     int j1 = i1 & 0x7;
/*     */     
/* 191 */     return (j1 == 5 && l == 1) ? 15 : ((j1 == 4 && l == 2) ? 15 : ((j1 == 3 && l == 3) ? 15 : ((j1 == 2 && l == 4) ? 15 : ((j1 == 1 && l == 5) ? 15 : 0))));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPowerSource() {
/* 196 */     return true;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/* 200 */     if (!world.isStatic) {
/* 201 */       int l = world.getData(i, j, k);
/*     */       
/* 203 */       if ((l & 0x8) != 0) {
/* 204 */         if (this.a) {
/* 205 */           n(world, i, j, k);
/*     */         } else {
/*     */           
/* 208 */           Block block = world.getWorld().getBlockAt(i, j, k);
/*     */           
/* 210 */           BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
/* 211 */           world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*     */           
/* 213 */           if (eventRedstone.getNewCurrent() > 0) {
/*     */             return;
/*     */           }
/*     */           
/* 217 */           world.setData(i, j, k, l & 0x7, 3);
/* 218 */           int i1 = l & 0x7;
/*     */           
/* 220 */           a(world, i, j, k, i1);
/* 221 */           world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.5F);
/* 222 */           world.c(i, j, k, i, j, k);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void g() {
/* 229 */     float f = 0.1875F;
/* 230 */     float f1 = 0.125F;
/* 231 */     float f2 = 0.125F;
/*     */     
/* 233 */     a(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity) {
/* 237 */     if (!world.isStatic && 
/* 238 */       this.a && (
/* 239 */       world.getData(i, j, k) & 0x8) == 0) {
/* 240 */       n(world, i, j, k);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void n(World world, int i, int j, int k) {
/* 247 */     int l = world.getData(i, j, k);
/* 248 */     int i1 = l & 0x7;
/* 249 */     boolean flag = ((l & 0x8) != 0);
/*     */     
/* 251 */     b(l);
/* 252 */     List list = world.a(EntityArrow.class, AxisAlignedBB.a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + this.maxY, k + this.maxZ));
/* 253 */     boolean flag1 = !list.isEmpty();
/*     */ 
/*     */     
/* 256 */     if (flag != flag1 && flag1) {
/* 257 */       Block block = world.getWorld().getBlockAt(i, j, k);
/* 258 */       boolean allowed = false;
/*     */ 
/*     */       
/* 261 */       for (Object object : list) {
/* 262 */         if (object != null) {
/* 263 */           EntityInteractEvent event = new EntityInteractEvent((Entity)((Entity)object).getBukkitEntity(), block);
/* 264 */           world.getServer().getPluginManager().callEvent((Event)event);
/*     */           
/* 266 */           if (!event.isCancelled()) {
/* 267 */             allowed = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 273 */       if (!allowed) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 279 */     if (flag1 && !flag) {
/*     */       
/* 281 */       Block block = world.getWorld().getBlockAt(i, j, k);
/*     */       
/* 283 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 0, 15);
/* 284 */       world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*     */       
/* 286 */       if (eventRedstone.getNewCurrent() <= 0) {
/*     */         return;
/*     */       }
/*     */       
/* 290 */       world.setData(i, j, k, i1 | 0x8, 3);
/* 291 */       a(world, i, j, k, i1);
/* 292 */       world.c(i, j, k, i, j, k);
/* 293 */       world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     } 
/*     */     
/* 296 */     if (!flag1 && flag) {
/*     */       
/* 298 */       Block block = world.getWorld().getBlockAt(i, j, k);
/*     */       
/* 300 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
/* 301 */       world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*     */       
/* 303 */       if (eventRedstone.getNewCurrent() > 0) {
/*     */         return;
/*     */       }
/*     */       
/* 307 */       world.setData(i, j, k, i1, 3);
/* 308 */       a(world, i, j, k, i1);
/* 309 */       world.c(i, j, k, i, j, k);
/* 310 */       world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.5F);
/*     */     } 
/*     */     
/* 313 */     if (flag1) {
/* 314 */       world.a(i, j, k, this, a(world));
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(World world, int i, int j, int k, int l) {
/* 319 */     world.applyPhysics(i, j, k, this);
/* 320 */     if (l == 1) {
/* 321 */       world.applyPhysics(i - 1, j, k, this);
/* 322 */     } else if (l == 2) {
/* 323 */       world.applyPhysics(i + 1, j, k, this);
/* 324 */     } else if (l == 3) {
/* 325 */       world.applyPhysics(i, j, k - 1, this);
/* 326 */     } else if (l == 4) {
/* 327 */       world.applyPhysics(i, j, k + 1, this);
/*     */     } else {
/* 329 */       world.applyPhysics(i, j - 1, k, this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockButtonAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */