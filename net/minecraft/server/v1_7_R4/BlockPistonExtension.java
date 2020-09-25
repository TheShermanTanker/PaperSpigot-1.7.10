/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockPistonExtension
/*     */   extends Block {
/*     */   public BlockPistonExtension() {
/*   9 */     super(Material.PISTON);
/*  10 */     a(i);
/*  11 */     c(0.5F);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
/*  15 */     if (entityhuman.abilities.canInstantlyBuild) {
/*  16 */       int i1 = b(l);
/*  17 */       Block block = world.getType(i - Facing.b[i1], j - Facing.c[i1], k - Facing.d[i1]);
/*     */       
/*  19 */       if (block == Blocks.PISTON || block == Blocks.PISTON_STICKY) {
/*  20 */         world.setAir(i - Facing.b[i1], j - Facing.c[i1], k - Facing.d[i1]);
/*     */       }
/*     */     } 
/*     */     
/*  24 */     super.a(world, i, j, k, l, entityhuman);
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/*  28 */     super.remove(world, i, j, k, block, l);
/*  29 */     if ((l & 0x7) >= Facing.OPPOSITE_FACING.length)
/*  30 */       return;  int i1 = Facing.OPPOSITE_FACING[b(l)];
/*     */     
/*  32 */     i += Facing.b[i1];
/*  33 */     j += Facing.c[i1];
/*  34 */     k += Facing.d[i1];
/*  35 */     Block block1 = world.getType(i, j, k);
/*     */     
/*  37 */     if (block1 == Blocks.PISTON || block1 == Blocks.PISTON_STICKY) {
/*  38 */       l = world.getData(i, j, k);
/*  39 */       if (BlockPiston.c(l)) {
/*  40 */         block1.b(world, i, j, k, l, 0);
/*  41 */         world.setAir(i, j, k);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int b() {
/*  47 */     return 17;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  59 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k, int l) {
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/*  67 */     return 0;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List list, Entity entity) {
/*  71 */     int l = world.getData(i, j, k);
/*  72 */     float f = 0.25F;
/*  73 */     float f1 = 0.375F;
/*  74 */     float f2 = 0.625F;
/*  75 */     float f3 = 0.25F;
/*  76 */     float f4 = 0.75F;
/*     */     
/*  78 */     switch (b(l)) {
/*     */       case 0:
/*  80 */         a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/*  81 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/*  82 */         a(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
/*  83 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/*     */         break;
/*     */       
/*     */       case 1:
/*  87 */         a(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  88 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/*  89 */         a(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
/*  90 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/*     */         break;
/*     */       
/*     */       case 2:
/*  94 */         a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
/*  95 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/*  96 */         a(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
/*  97 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/*     */         break;
/*     */       
/*     */       case 3:
/* 101 */         a(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
/* 102 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/* 103 */         a(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
/* 104 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/*     */         break;
/*     */       
/*     */       case 4:
/* 108 */         a(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
/* 109 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/* 110 */         a(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
/* 111 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/*     */         break;
/*     */       
/*     */       case 5:
/* 115 */         a(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 116 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/* 117 */         a(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
/* 118 */         super.a(world, i, j, k, axisalignedbb, list, entity);
/*     */         break;
/*     */     } 
/* 121 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 125 */     int l = iblockaccess.getData(i, j, k);
/* 126 */     float f = 0.25F;
/*     */     
/* 128 */     switch (b(l)) {
/*     */       case 0:
/* 130 */         a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/*     */         break;
/*     */       
/*     */       case 1:
/* 134 */         a(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         break;
/*     */       
/*     */       case 2:
/* 138 */         a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
/*     */         break;
/*     */       
/*     */       case 3:
/* 142 */         a(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
/*     */         break;
/*     */       
/*     */       case 4:
/* 146 */         a(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
/*     */         break;
/*     */       
/*     */       case 5:
/* 150 */         a(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 155 */     int l = b(world.getData(i, j, k));
/* 156 */     if ((l & 0x7) >= Facing.OPPOSITE_FACING.length)
/* 157 */       return;  Block block1 = world.getType(i - Facing.b[l], j - Facing.c[l], k - Facing.d[l]);
/*     */     
/* 159 */     if (block1 != Blocks.PISTON && block1 != Blocks.PISTON_STICKY) {
/* 160 */       world.setAir(i, j, k);
/*     */     } else {
/* 162 */       block1.doPhysics(world, i - Facing.b[l], j - Facing.c[l], k - Facing.d[l], block);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int b(int i) {
/* 167 */     return MathHelper.a(i & 0x7, 0, Facing.b.length - 1);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPistonExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */