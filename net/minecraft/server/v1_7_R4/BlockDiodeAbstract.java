/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public abstract class BlockDiodeAbstract
/*     */   extends BlockDirectional
/*     */ {
/*     */   protected final boolean a;
/*     */   
/*     */   protected BlockDiodeAbstract(boolean flag) {
/*  12 */     super(Material.ORIENTABLE);
/*  13 */     this.a = flag;
/*  14 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  18 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  22 */     return !World.a(world, i, j - 1, k) ? false : super.canPlace(world, i, j, k);
/*     */   }
/*     */   
/*     */   public boolean j(World world, int i, int j, int k) {
/*  26 */     return !World.a(world, i, j - 1, k) ? false : super.j(world, i, j, k);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  30 */     int l = world.getData(i, j, k);
/*     */     
/*  32 */     if (!g(world, i, j, k, l)) {
/*  33 */       boolean flag = a(world, i, j, k, l);
/*     */       
/*  35 */       if (this.a && !flag) {
/*     */         
/*  37 */         if (CraftEventFactory.callRedstoneChange(world, i, j, k, 15, 0).getNewCurrent() != 0) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  42 */         world.setTypeAndData(i, j, k, i(), l, 2);
/*  43 */       } else if (!this.a) {
/*     */         
/*  45 */         if (CraftEventFactory.callRedstoneChange(world, i, j, k, 0, 15).getNewCurrent() != 15) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  50 */         world.setTypeAndData(i, j, k, e(), l, 2);
/*  51 */         if (!flag) {
/*  52 */           world.a(i, j, k, e(), k(l), -1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int b() {
/*  59 */     return 36;
/*     */   }
/*     */   
/*     */   protected boolean c(int i) {
/*  63 */     return this.a;
/*     */   }
/*     */   
/*     */   public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/*  67 */     return b(iblockaccess, i, j, k, l);
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/*  71 */     int i1 = iblockaccess.getData(i, j, k);
/*     */     
/*  73 */     if (!c(i1)) {
/*  74 */       return 0;
/*     */     }
/*  76 */     int j1 = l(i1);
/*     */     
/*  78 */     return (j1 == 0 && l == 3) ? f(iblockaccess, i, j, k, i1) : ((j1 == 1 && l == 4) ? f(iblockaccess, i, j, k, i1) : ((j1 == 2 && l == 2) ? f(iblockaccess, i, j, k, i1) : ((j1 == 3 && l == 5) ? f(iblockaccess, i, j, k, i1) : 0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  83 */     if (!j(world, i, j, k)) {
/*  84 */       b(world, i, j, k, world.getData(i, j, k), 0);
/*  85 */       world.setAir(i, j, k);
/*  86 */       world.applyPhysics(i + 1, j, k, this);
/*  87 */       world.applyPhysics(i - 1, j, k, this);
/*  88 */       world.applyPhysics(i, j, k + 1, this);
/*  89 */       world.applyPhysics(i, j, k - 1, this);
/*  90 */       world.applyPhysics(i, j - 1, k, this);
/*  91 */       world.applyPhysics(i, j + 1, k, this);
/*     */     } else {
/*  93 */       b(world, i, j, k, block);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(World world, int i, int j, int k, Block block) {
/*  98 */     int l = world.getData(i, j, k);
/*     */     
/* 100 */     if (!g(world, i, j, k, l)) {
/* 101 */       boolean flag = a(world, i, j, k, l);
/*     */       
/* 103 */       if (((this.a && !flag) || (!this.a && flag)) && !world.a(i, j, k, this)) {
/* 104 */         byte b0 = -1;
/*     */         
/* 106 */         if (i(world, i, j, k, l)) {
/* 107 */           b0 = -3;
/* 108 */         } else if (this.a) {
/* 109 */           b0 = -2;
/*     */         } 
/*     */         
/* 112 */         world.a(i, j, k, this, b(l), b0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean g(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean a(World world, int i, int j, int k, int l) {
/* 122 */     return (h(world, i, j, k, l) > 0);
/*     */   }
/*     */   
/*     */   protected int h(World world, int i, int j, int k, int l) {
/* 126 */     int i1 = l(l);
/* 127 */     int j1 = i + Direction.a[i1];
/* 128 */     int k1 = k + Direction.b[i1];
/* 129 */     int l1 = world.getBlockFacePower(j1, j, k1, Direction.d[i1]);
/*     */     
/* 131 */     return (l1 >= 15) ? l1 : Math.max(l1, (world.getType(j1, j, k1) == Blocks.REDSTONE_WIRE) ? world.getData(j1, j, k1) : 0);
/*     */   }
/*     */   
/*     */   protected int h(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 135 */     int i1 = l(l);
/*     */     
/* 137 */     switch (i1) {
/*     */       case 0:
/*     */       case 2:
/* 140 */         return Math.max(i(iblockaccess, i - 1, j, k, 4), i(iblockaccess, i + 1, j, k, 5));
/*     */       
/*     */       case 1:
/*     */       case 3:
/* 144 */         return Math.max(i(iblockaccess, i, j, k + 1, 3), i(iblockaccess, i, j, k - 1, 2));
/*     */     } 
/*     */     
/* 147 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int i(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 152 */     Block block = iblockaccess.getType(i, j, k);
/*     */     
/* 154 */     return a(block) ? ((block == Blocks.REDSTONE_WIRE) ? iblockaccess.getData(i, j, k) : iblockaccess.getBlockPower(i, j, k, l)) : 0;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/* 158 */     return true;
/*     */   }
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/* 162 */     int l = ((MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3) + 2) % 4;
/*     */     
/* 164 */     world.setData(i, j, k, l, 3);
/* 165 */     boolean flag = a(world, i, j, k, l);
/*     */     
/* 167 */     if (flag) {
/* 168 */       world.a(i, j, k, this, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/* 173 */     e(world, i, j, k);
/*     */   }
/*     */   
/*     */   protected void e(World world, int i, int j, int k) {
/* 177 */     int l = l(world.getData(i, j, k));
/*     */     
/* 179 */     if (l == 1) {
/* 180 */       world.e(i + 1, j, k, this);
/* 181 */       world.b(i + 1, j, k, this, 4);
/*     */     } 
/*     */     
/* 184 */     if (l == 3) {
/* 185 */       world.e(i - 1, j, k, this);
/* 186 */       world.b(i - 1, j, k, this, 5);
/*     */     } 
/*     */     
/* 189 */     if (l == 2) {
/* 190 */       world.e(i, j, k + 1, this);
/* 191 */       world.b(i, j, k + 1, this, 2);
/*     */     } 
/*     */     
/* 194 */     if (l == 0) {
/* 195 */       world.e(i, j, k - 1, this);
/* 196 */       world.b(i, j, k - 1, this, 3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void postBreak(World world, int i, int j, int k, int l) {
/* 201 */     if (this.a) {
/* 202 */       world.applyPhysics(i + 1, j, k, this);
/* 203 */       world.applyPhysics(i - 1, j, k, this);
/* 204 */       world.applyPhysics(i, j, k + 1, this);
/* 205 */       world.applyPhysics(i, j, k - 1, this);
/* 206 */       world.applyPhysics(i, j - 1, k, this);
/* 207 */       world.applyPhysics(i, j + 1, k, this);
/*     */     } 
/*     */     
/* 210 */     super.postBreak(world, i, j, k, l);
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 214 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean a(Block block) {
/* 218 */     return block.isPowerSource();
/*     */   }
/*     */   
/*     */   protected int f(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 222 */     return 15;
/*     */   }
/*     */   
/*     */   public static boolean d(Block block) {
/* 226 */     return (Blocks.DIODE_OFF.e(block) || Blocks.REDSTONE_COMPARATOR_OFF.e(block));
/*     */   }
/*     */   
/*     */   public boolean e(Block block) {
/* 230 */     return (block == e() || block == i());
/*     */   }
/*     */   
/*     */   public boolean i(World world, int i, int j, int k, int l) {
/* 234 */     int i1 = l(l);
/*     */     
/* 236 */     if (d(world.getType(i - Direction.a[i1], j, k - Direction.b[i1]))) {
/* 237 */       int j1 = world.getData(i - Direction.a[i1], j, k - Direction.b[i1]);
/* 238 */       int k1 = l(j1);
/*     */       
/* 240 */       return (k1 != i1);
/*     */     } 
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int k(int i) {
/* 247 */     return b(i);
/*     */   }
/*     */   
/*     */   protected abstract int b(int paramInt);
/*     */   
/*     */   protected abstract BlockDiodeAbstract e();
/*     */   
/*     */   protected abstract BlockDiodeAbstract i();
/*     */   
/*     */   public boolean c(Block block) {
/* 257 */     return e(block);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockDiodeAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */