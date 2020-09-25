/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public class BlockVine
/*     */   extends Block {
/*     */   public BlockVine() {
/*  10 */     super(Material.REPLACEABLE_PLANT);
/*  11 */     a(true);
/*  12 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public void g() {
/*  16 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public int b() {
/*  20 */     return 20;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  24 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  28 */     return false;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  32 */     float f = 0.0625F;
/*  33 */     int l = iblockaccess.getData(i, j, k);
/*  34 */     float f1 = 1.0F;
/*  35 */     float f2 = 1.0F;
/*  36 */     float f3 = 1.0F;
/*  37 */     float f4 = 0.0F;
/*  38 */     float f5 = 0.0F;
/*  39 */     float f6 = 0.0F;
/*  40 */     boolean flag = (l > 0);
/*     */     
/*  42 */     if ((l & 0x2) != 0) {
/*  43 */       f4 = Math.max(f4, 0.0625F);
/*  44 */       f1 = 0.0F;
/*  45 */       f2 = 0.0F;
/*  46 */       f5 = 1.0F;
/*  47 */       f3 = 0.0F;
/*  48 */       f6 = 1.0F;
/*  49 */       flag = true;
/*     */     } 
/*     */     
/*  52 */     if ((l & 0x8) != 0) {
/*  53 */       f1 = Math.min(f1, 0.9375F);
/*  54 */       f4 = 1.0F;
/*  55 */       f2 = 0.0F;
/*  56 */       f5 = 1.0F;
/*  57 */       f3 = 0.0F;
/*  58 */       f6 = 1.0F;
/*  59 */       flag = true;
/*     */     } 
/*     */     
/*  62 */     if ((l & 0x4) != 0) {
/*  63 */       f6 = Math.max(f6, 0.0625F);
/*  64 */       f3 = 0.0F;
/*  65 */       f1 = 0.0F;
/*  66 */       f4 = 1.0F;
/*  67 */       f2 = 0.0F;
/*  68 */       f5 = 1.0F;
/*  69 */       flag = true;
/*     */     } 
/*     */     
/*  72 */     if ((l & 0x1) != 0) {
/*  73 */       f3 = Math.min(f3, 0.9375F);
/*  74 */       f6 = 1.0F;
/*  75 */       f1 = 0.0F;
/*  76 */       f4 = 1.0F;
/*  77 */       f2 = 0.0F;
/*  78 */       f5 = 1.0F;
/*  79 */       flag = true;
/*     */     } 
/*     */     
/*  82 */     if (!flag && a(iblockaccess.getType(i, j + 1, k))) {
/*  83 */       f2 = Math.min(f2, 0.9375F);
/*  84 */       f5 = 1.0F;
/*  85 */       f1 = 0.0F;
/*  86 */       f4 = 1.0F;
/*  87 */       f3 = 0.0F;
/*  88 */       f6 = 1.0F;
/*     */     } 
/*     */     
/*  91 */     a(f1, f2, f3, f4, f5, f6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k, int l) {
/*  99 */     switch (l) {
/*     */       case 1:
/* 101 */         return a(world.getType(i, j + 1, k));
/*     */       
/*     */       case 2:
/* 104 */         return a(world.getType(i, j, k + 1));
/*     */       
/*     */       case 3:
/* 107 */         return a(world.getType(i, j, k - 1));
/*     */       
/*     */       case 4:
/* 110 */         return a(world.getType(i + 1, j, k));
/*     */       
/*     */       case 5:
/* 113 */         return a(world.getType(i - 1, j, k));
/*     */     } 
/*     */     
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean a(Block block) {
/* 121 */     return (block.d() && block.material.isSolid());
/*     */   }
/*     */   
/*     */   private boolean e(World world, int i, int j, int k) {
/* 125 */     int l = world.getData(i, j, k);
/* 126 */     int i1 = l;
/*     */     
/* 128 */     if (l > 0) {
/* 129 */       for (int j1 = 0; j1 <= 3; j1++) {
/* 130 */         int k1 = 1 << j1;
/*     */         
/* 132 */         if ((l & k1) != 0 && !a(world.getType(i + Direction.a[j1], j, k + Direction.b[j1])) && (world.getType(i, j + 1, k) != this || (world.getData(i, j + 1, k) & k1) == 0)) {
/* 133 */           i1 &= k1 ^ 0xFFFFFFFF;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 138 */     if (i1 == 0 && !a(world.getType(i, j + 1, k))) {
/* 139 */       return false;
/*     */     }
/* 141 */     if (i1 != l) {
/* 142 */       world.setData(i, j, k, i1, 2);
/*     */     }
/*     */     
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 150 */     if (!world.isStatic && !e(world, i, j, k)) {
/* 151 */       b(world, i, j, k, world.getData(i, j, k), 0);
/* 152 */       world.setAir(i, j, k);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/* 157 */     if (!world.isStatic && world.random.nextInt(4) == 0) {
/* 158 */       byte b0 = 4;
/* 159 */       int l = 5;
/* 160 */       boolean flag = false;
/*     */ 
/*     */ 
/*     */       
/*     */       int i1;
/*     */ 
/*     */       
/* 167 */       label85: for (i1 = i - b0; i1 <= i + b0; i1++) {
/* 168 */         for (int m = k - b0; m <= k + b0; m++) {
/* 169 */           for (int n = j - 1; n <= j + 1; n++) {
/*     */             
/* 171 */             l--;
/* 172 */             if (world.getType(i1, n, m) == this && l <= 0) {
/* 173 */               flag = true;
/*     */               
/*     */               break label85;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 181 */       i1 = world.getData(i, j, k);
/* 182 */       int j1 = world.random.nextInt(6);
/* 183 */       int k1 = Direction.e[j1];
/*     */ 
/*     */       
/* 186 */       if (j1 == 1 && j < 255 && world.isEmpty(i, j + 1, k)) {
/* 187 */         if (flag) {
/*     */           return;
/*     */         }
/*     */         
/* 191 */         int i2 = world.random.nextInt(16) & i1;
/*     */         
/* 193 */         if (i2 > 0) {
/* 194 */           int l1; for (l1 = 0; l1 <= 3; l1++) {
/* 195 */             if (!a(world.getType(i + Direction.a[l1], j + 1, k + Direction.b[l1]))) {
/* 196 */               i2 &= 1 << l1 ^ 0xFFFFFFFF;
/*     */             }
/*     */           } 
/*     */           
/* 200 */           if (i2 > 0)
/*     */           {
/* 202 */             Block source = world.getWorld().getBlockAt(i, j, k);
/* 203 */             Block block = world.getWorld().getBlockAt(i, j + 1, k);
/* 204 */             CraftEventFactory.handleBlockSpreadEvent(block, source, this, l1);
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 212 */       else if (j1 >= 2 && j1 <= 5 && (i1 & 1 << k1) == 0) {
/* 213 */         if (flag) {
/*     */           return;
/*     */         }
/*     */         
/* 217 */         Block block = world.getType(i + Direction.a[k1], j, k + Direction.b[k1]);
/* 218 */         if (block.material == Material.AIR) {
/* 219 */           int l1 = k1 + 1 & 0x3;
/* 220 */           int j2 = k1 + 3 & 0x3;
/*     */ 
/*     */           
/* 223 */           Block source = world.getWorld().getBlockAt(i, j, k);
/* 224 */           Block bukkitBlock = world.getWorld().getBlockAt(i + Direction.a[k1], j, k + Direction.b[k1]);
/* 225 */           if ((i1 & 1 << l1) != 0 && a(world.getType(i + Direction.a[k1] + Direction.a[l1], j, k + Direction.b[k1] + Direction.b[l1]))) {
/* 226 */             CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, 1 << l1);
/* 227 */           } else if ((i1 & 1 << j2) != 0 && a(world.getType(i + Direction.a[k1] + Direction.a[j2], j, k + Direction.b[k1] + Direction.b[j2]))) {
/* 228 */             CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, 1 << j2);
/* 229 */           } else if ((i1 & 1 << l1) != 0 && world.isEmpty(i + Direction.a[k1] + Direction.a[l1], j, k + Direction.b[k1] + Direction.b[l1]) && a(world.getType(i + Direction.a[l1], j, k + Direction.b[l1]))) {
/* 230 */             bukkitBlock = world.getWorld().getBlockAt(i + Direction.a[k1] + Direction.a[l1], j, k + Direction.b[k1] + Direction.b[l1]);
/* 231 */             CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, 1 << (k1 + 2 & 0x3));
/* 232 */           } else if ((i1 & 1 << j2) != 0 && world.isEmpty(i + Direction.a[k1] + Direction.a[j2], j, k + Direction.b[k1] + Direction.b[j2]) && a(world.getType(i + Direction.a[j2], j, k + Direction.b[j2]))) {
/* 233 */             bukkitBlock = world.getWorld().getBlockAt(i + Direction.a[k1] + Direction.a[j2], j, k + Direction.b[k1] + Direction.b[j2]);
/* 234 */             CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, 1 << (k1 + 2 & 0x3));
/* 235 */           } else if (a(world.getType(i + Direction.a[k1], j + 1, k + Direction.b[k1]))) {
/* 236 */             CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, 0);
/*     */           }
/*     */         
/* 239 */         } else if (block.material.k() && block.d()) {
/* 240 */           world.setData(i, j, k, i1 | 1 << k1, 2);
/*     */         } 
/* 242 */       } else if (j > 1) {
/* 243 */         Block block = world.getType(i, j - 1, k);
/* 244 */         if (block.material == Material.AIR) {
/* 245 */           int l1 = world.random.nextInt(16) & i1;
/* 246 */           if (l1 > 0)
/*     */           {
/* 248 */             Block source = world.getWorld().getBlockAt(i, j, k);
/* 249 */             Block bukkitBlock = world.getWorld().getBlockAt(i, j - 1, k);
/* 250 */             CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, l1);
/*     */           }
/*     */         
/* 253 */         } else if (block == this) {
/* 254 */           int l1 = world.random.nextInt(16) & i1;
/* 255 */           int j2 = world.getData(i, j - 1, k);
/* 256 */           if (j2 != (j2 | l1)) {
/* 257 */             world.setData(i, j - 1, k, j2 | l1, 2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
/* 266 */     byte b0 = 0;
/*     */     
/* 268 */     switch (l) {
/*     */       case 2:
/* 270 */         b0 = 1;
/*     */         break;
/*     */       
/*     */       case 3:
/* 274 */         b0 = 4;
/*     */         break;
/*     */       
/*     */       case 4:
/* 278 */         b0 = 8;
/*     */         break;
/*     */       
/*     */       case 5:
/* 282 */         b0 = 2;
/*     */         break;
/*     */     } 
/* 285 */     return (b0 != 0) ? b0 : i1;
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 289 */     return null;
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/* 293 */     return 0;
/*     */   }
/*     */   
/*     */   public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
/* 297 */     if (!world.isStatic && entityhuman.bF() != null && entityhuman.bF().getItem() == Items.SHEARS) {
/* 298 */       entityhuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
/* 299 */       a(world, i, j, k, new ItemStack(Blocks.VINE, 1, 0));
/*     */     } else {
/* 301 */       super.a(world, entityhuman, i, j, k, l);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockVine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */