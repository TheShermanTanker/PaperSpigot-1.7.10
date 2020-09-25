/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public abstract class BlockMinecartTrackAbstract
/*     */   extends Block {
/*     */   protected final boolean a;
/*     */   
/*     */   public static final boolean b_(World world, int i, int j, int k) {
/*  10 */     return a(world.getType(i, j, k));
/*     */   }
/*     */   
/*     */   public static final boolean a(Block block) {
/*  14 */     return (block == Blocks.RAILS || block == Blocks.GOLDEN_RAIL || block == Blocks.DETECTOR_RAIL || block == Blocks.ACTIVATOR_RAIL);
/*     */   }
/*     */   
/*     */   protected BlockMinecartTrackAbstract(boolean flag) {
/*  18 */     super(Material.ORIENTABLE);
/*  19 */     this.a = flag;
/*  20 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  21 */     a(CreativeModeTab.e);
/*     */   }
/*     */   
/*     */   public boolean e() {
/*  25 */     return this.a;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  29 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  33 */     return false;
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
/*  37 */     updateShape(world, i, j, k);
/*  38 */     return super.a(world, i, j, k, vec3d, vec3d1);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  42 */     int l = iblockaccess.getData(i, j, k);
/*     */     
/*  44 */     if (l >= 2 && l <= 5) {
/*  45 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*     */     } else {
/*  47 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  52 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  56 */     return 9;
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/*  60 */     return 1;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  64 */     return World.a(world, i, j - 1, k);
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/*  68 */     if (!world.isStatic) {
/*  69 */       a(world, i, j, k, true);
/*  70 */       if (this.a) {
/*  71 */         doPhysics(world, i, j, k, this);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  77 */     if (!world.isStatic) {
/*  78 */       int l = world.getData(i, j, k);
/*  79 */       int i1 = l;
/*     */       
/*  81 */       if (this.a) {
/*  82 */         i1 = l & 0x7;
/*     */       }
/*     */       
/*  85 */       boolean flag = false;
/*     */       
/*  87 */       if (!World.a(world, i, j - 1, k)) {
/*  88 */         flag = true;
/*     */       }
/*     */       
/*  91 */       if (i1 == 2 && !World.a(world, i + 1, j, k)) {
/*  92 */         flag = true;
/*     */       }
/*     */       
/*  95 */       if (i1 == 3 && !World.a(world, i - 1, j, k)) {
/*  96 */         flag = true;
/*     */       }
/*     */       
/*  99 */       if (i1 == 4 && !World.a(world, i, j, k - 1)) {
/* 100 */         flag = true;
/*     */       }
/*     */       
/* 103 */       if (i1 == 5 && !World.a(world, i, j, k + 1)) {
/* 104 */         flag = true;
/*     */       }
/*     */       
/* 107 */       if (flag) {
/*     */         
/* 109 */         if (world.getType(i, j, k).getMaterial() != Material.AIR) {
/* 110 */           b(world, i, j, k, world.getData(i, j, k), 0);
/* 111 */           world.setAir(i, j, k);
/*     */         } 
/*     */       } else {
/*     */         
/* 115 */         a(world, i, j, k, l, i1, block);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(World world, int i, int j, int k, int l, int i1, Block block) {}
/*     */   
/*     */   protected void a(World world, int i, int j, int k, boolean flag) {
/* 123 */     if (!world.isStatic) {
/* 124 */       (new MinecartTrackLogic(this, world, i, j, k)).a(world.isBlockIndirectlyPowered(i, j, k), flag);
/*     */     }
/*     */   }
/*     */   
/*     */   public int h() {
/* 129 */     return 0;
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/* 133 */     int i1 = l;
/*     */     
/* 135 */     if (this.a) {
/* 136 */       i1 = l & 0x7;
/*     */     }
/*     */     
/* 139 */     super.remove(world, i, j, k, block, l);
/* 140 */     if (i1 == 2 || i1 == 3 || i1 == 4 || i1 == 5) {
/* 141 */       world.applyPhysics(i, j + 1, k, block);
/*     */     }
/*     */     
/* 144 */     if (this.a) {
/* 145 */       world.applyPhysics(i, j, k, block);
/* 146 */       world.applyPhysics(i, j - 1, k, block);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockMinecartTrackAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */