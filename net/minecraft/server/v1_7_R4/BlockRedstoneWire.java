/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockRedstoneWire
/*     */   extends Block {
/*     */   private boolean a = true;
/*  13 */   private Set b = new HashSet();
/*     */   
/*     */   public BlockRedstoneWire() {
/*  16 */     super(Material.ORIENTABLE);
/*  17 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  21 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  25 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  29 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  33 */     return 5;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  37 */     return (World.a(world, i, j - 1, k) || world.getType(i, j - 1, k) == Blocks.GLOWSTONE);
/*     */   }
/*     */   
/*     */   private void e(World world, int i, int j, int k) {
/*  41 */     a(world, i, j, k, i, j, k);
/*  42 */     ArrayList<ChunkPosition> arraylist = new ArrayList(this.b);
/*     */     
/*  44 */     this.b.clear();
/*     */     
/*  46 */     for (int l = 0; l < arraylist.size(); l++) {
/*  47 */       ChunkPosition chunkposition = arraylist.get(l);
/*     */       
/*  49 */       world.applyPhysics(chunkposition.x, chunkposition.y, chunkposition.z, this);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(World world, int i, int j, int k, int l, int i1, int j1) {
/*  54 */     int k1 = world.getData(i, j, k);
/*  55 */     byte b0 = 0;
/*  56 */     int l1 = getPower(world, l, i1, j1, b0);
/*     */     
/*  58 */     this.a = false;
/*  59 */     int i2 = world.getHighestNeighborSignal(i, j, k);
/*     */     
/*  61 */     this.a = true;
/*  62 */     if (i2 > 0 && i2 > l1 - 1) {
/*  63 */       l1 = i2;
/*     */     }
/*     */     
/*  66 */     int j2 = 0;
/*     */     
/*  68 */     for (int k2 = 0; k2 < 4; k2++) {
/*  69 */       int l2 = i;
/*  70 */       int i3 = k;
/*     */       
/*  72 */       if (k2 == 0) {
/*  73 */         l2 = i - 1;
/*     */       }
/*     */       
/*  76 */       if (k2 == 1) {
/*  77 */         l2++;
/*     */       }
/*     */       
/*  80 */       if (k2 == 2) {
/*  81 */         i3 = k - 1;
/*     */       }
/*     */       
/*  84 */       if (k2 == 3) {
/*  85 */         i3++;
/*     */       }
/*     */       
/*  88 */       if (l2 != l || i3 != j1) {
/*  89 */         j2 = getPower(world, l2, j, i3, j2);
/*     */       }
/*     */       
/*  92 */       if (world.getType(l2, j, i3).r() && !world.getType(i, j + 1, k).r()) {
/*  93 */         if ((l2 != l || i3 != j1) && j >= i1) {
/*  94 */           j2 = getPower(world, l2, j + 1, i3, j2);
/*     */         }
/*  96 */       } else if (!world.getType(l2, j, i3).r() && (l2 != l || i3 != j1) && j <= i1) {
/*  97 */         j2 = getPower(world, l2, j - 1, i3, j2);
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     if (j2 > l1) {
/* 102 */       l1 = j2 - 1;
/* 103 */     } else if (l1 > 0) {
/* 104 */       l1--;
/*     */     } else {
/* 106 */       l1 = 0;
/*     */     } 
/*     */     
/* 109 */     if (i2 > l1 - 1) {
/* 110 */       l1 = i2;
/*     */     }
/*     */ 
/*     */     
/* 114 */     if (k1 != l1) {
/* 115 */       BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(i, j, k), k1, l1);
/* 116 */       world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/* 118 */       l1 = event.getNewCurrent();
/*     */     } 
/*     */     
/* 121 */     if (k1 != l1) {
/* 122 */       world.setData(i, j, k, l1, 2);
/* 123 */       this.b.add(new ChunkPosition(i, j, k));
/* 124 */       this.b.add(new ChunkPosition(i - 1, j, k));
/* 125 */       this.b.add(new ChunkPosition(i + 1, j, k));
/* 126 */       this.b.add(new ChunkPosition(i, j - 1, k));
/* 127 */       this.b.add(new ChunkPosition(i, j + 1, k));
/* 128 */       this.b.add(new ChunkPosition(i, j, k - 1));
/* 129 */       this.b.add(new ChunkPosition(i, j, k + 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void m(World world, int i, int j, int k) {
/* 134 */     if (world.getType(i, j, k) == this) {
/* 135 */       world.applyPhysics(i, j, k, this);
/* 136 */       world.applyPhysics(i - 1, j, k, this);
/* 137 */       world.applyPhysics(i + 1, j, k, this);
/* 138 */       world.applyPhysics(i, j, k - 1, this);
/* 139 */       world.applyPhysics(i, j, k + 1, this);
/* 140 */       world.applyPhysics(i, j - 1, k, this);
/* 141 */       world.applyPhysics(i, j + 1, k, this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/* 146 */     super.onPlace(world, i, j, k);
/* 147 */     if (!world.isStatic) {
/* 148 */       e(world, i, j, k);
/* 149 */       world.applyPhysics(i, j + 1, k, this);
/* 150 */       world.applyPhysics(i, j - 1, k, this);
/* 151 */       m(world, i - 1, j, k);
/* 152 */       m(world, i + 1, j, k);
/* 153 */       m(world, i, j, k - 1);
/* 154 */       m(world, i, j, k + 1);
/* 155 */       if (world.getType(i - 1, j, k).r()) {
/* 156 */         m(world, i - 1, j + 1, k);
/*     */       } else {
/* 158 */         m(world, i - 1, j - 1, k);
/*     */       } 
/*     */       
/* 161 */       if (world.getType(i + 1, j, k).r()) {
/* 162 */         m(world, i + 1, j + 1, k);
/*     */       } else {
/* 164 */         m(world, i + 1, j - 1, k);
/*     */       } 
/*     */       
/* 167 */       if (world.getType(i, j, k - 1).r()) {
/* 168 */         m(world, i, j + 1, k - 1);
/*     */       } else {
/* 170 */         m(world, i, j - 1, k - 1);
/*     */       } 
/*     */       
/* 173 */       if (world.getType(i, j, k + 1).r()) {
/* 174 */         m(world, i, j + 1, k + 1);
/*     */       } else {
/* 176 */         m(world, i, j - 1, k + 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/* 182 */     super.remove(world, i, j, k, block, l);
/* 183 */     if (!world.isStatic) {
/* 184 */       world.applyPhysics(i, j + 1, k, this);
/* 185 */       world.applyPhysics(i, j - 1, k, this);
/* 186 */       world.applyPhysics(i + 1, j, k, this);
/* 187 */       world.applyPhysics(i - 1, j, k, this);
/* 188 */       world.applyPhysics(i, j, k + 1, this);
/* 189 */       world.applyPhysics(i, j, k - 1, this);
/* 190 */       e(world, i, j, k);
/* 191 */       m(world, i - 1, j, k);
/* 192 */       m(world, i + 1, j, k);
/* 193 */       m(world, i, j, k - 1);
/* 194 */       m(world, i, j, k + 1);
/* 195 */       if (world.getType(i - 1, j, k).r()) {
/* 196 */         m(world, i - 1, j + 1, k);
/*     */       } else {
/* 198 */         m(world, i - 1, j - 1, k);
/*     */       } 
/*     */       
/* 201 */       if (world.getType(i + 1, j, k).r()) {
/* 202 */         m(world, i + 1, j + 1, k);
/*     */       } else {
/* 204 */         m(world, i + 1, j - 1, k);
/*     */       } 
/*     */       
/* 207 */       if (world.getType(i, j, k - 1).r()) {
/* 208 */         m(world, i, j + 1, k - 1);
/*     */       } else {
/* 210 */         m(world, i, j - 1, k - 1);
/*     */       } 
/*     */       
/* 213 */       if (world.getType(i, j, k + 1).r()) {
/* 214 */         m(world, i, j + 1, k + 1);
/*     */       } else {
/* 216 */         m(world, i, j - 1, k + 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPower(World world, int i, int j, int k, int l) {
/* 223 */     if (world.getType(i, j, k) != this) {
/* 224 */       return l;
/*     */     }
/* 226 */     int i1 = world.getData(i, j, k);
/*     */     
/* 228 */     return (i1 > l) ? i1 : l;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 233 */     if (!world.isStatic) {
/* 234 */       boolean flag = canPlace(world, i, j, k);
/*     */       
/* 236 */       if (flag) {
/* 237 */         e(world, i, j, k);
/*     */       } else {
/* 239 */         b(world, i, j, k, 0, 0);
/* 240 */         world.setAir(i, j, k);
/*     */       } 
/*     */       
/* 243 */       super.doPhysics(world, i, j, k, block);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 248 */     return Items.REDSTONE;
/*     */   }
/*     */   
/*     */   public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 252 */     return !this.a ? 0 : b(iblockaccess, i, j, k, l);
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 256 */     if (!this.a) {
/* 257 */       return 0;
/*     */     }
/* 259 */     int i1 = iblockaccess.getData(i, j, k);
/*     */     
/* 261 */     if (i1 == 0)
/* 262 */       return 0; 
/* 263 */     if (l == 1) {
/* 264 */       return i1;
/*     */     }
/* 266 */     boolean flag = (g(iblockaccess, i - 1, j, k, 1) || (!iblockaccess.getType(i - 1, j, k).r() && g(iblockaccess, i - 1, j - 1, k, -1)));
/* 267 */     boolean flag1 = (g(iblockaccess, i + 1, j, k, 3) || (!iblockaccess.getType(i + 1, j, k).r() && g(iblockaccess, i + 1, j - 1, k, -1)));
/* 268 */     boolean flag2 = (g(iblockaccess, i, j, k - 1, 2) || (!iblockaccess.getType(i, j, k - 1).r() && g(iblockaccess, i, j - 1, k - 1, -1)));
/* 269 */     boolean flag3 = (g(iblockaccess, i, j, k + 1, 0) || (!iblockaccess.getType(i, j, k + 1).r() && g(iblockaccess, i, j - 1, k + 1, -1)));
/*     */     
/* 271 */     if (!iblockaccess.getType(i, j + 1, k).r()) {
/* 272 */       if (iblockaccess.getType(i - 1, j, k).r() && g(iblockaccess, i - 1, j + 1, k, -1)) {
/* 273 */         flag = true;
/*     */       }
/*     */       
/* 276 */       if (iblockaccess.getType(i + 1, j, k).r() && g(iblockaccess, i + 1, j + 1, k, -1)) {
/* 277 */         flag1 = true;
/*     */       }
/*     */       
/* 280 */       if (iblockaccess.getType(i, j, k - 1).r() && g(iblockaccess, i, j + 1, k - 1, -1)) {
/* 281 */         flag2 = true;
/*     */       }
/*     */       
/* 284 */       if (iblockaccess.getType(i, j, k + 1).r() && g(iblockaccess, i, j + 1, k + 1, -1)) {
/* 285 */         flag3 = true;
/*     */       }
/*     */     } 
/*     */     
/* 289 */     return (!flag2 && !flag1 && !flag && !flag3 && l >= 2 && l <= 5) ? i1 : ((l == 2 && flag2 && !flag && !flag1) ? i1 : ((l == 3 && flag3 && !flag && !flag1) ? i1 : ((l == 4 && flag && !flag2 && !flag3) ? i1 : ((l == 5 && flag1 && !flag2 && !flag3) ? i1 : 0))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPowerSource() {
/* 295 */     return this.a;
/*     */   }
/*     */   
/*     */   public static boolean f(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 299 */     Block block = iblockaccess.getType(i, j, k);
/*     */     
/* 301 */     if (block == Blocks.REDSTONE_WIRE)
/* 302 */       return true; 
/* 303 */     if (!Blocks.DIODE_OFF.e(block)) {
/* 304 */       return (block.isPowerSource() && l != -1);
/*     */     }
/* 306 */     int i1 = iblockaccess.getData(i, j, k);
/*     */     
/* 308 */     return (l == (i1 & 0x3) || l == Direction.f[i1 & 0x3]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean g(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 313 */     if (f(iblockaccess, i, j, k, l))
/* 314 */       return true; 
/* 315 */     if (iblockaccess.getType(i, j, k) == Blocks.DIODE_ON) {
/* 316 */       int i1 = iblockaccess.getData(i, j, k);
/*     */       
/* 318 */       return (l == (i1 & 0x3));
/*     */     } 
/* 320 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockRedstoneWire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */