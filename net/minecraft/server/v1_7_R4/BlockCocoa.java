/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public class BlockCocoa
/*     */   extends BlockDirectional
/*     */   implements IBlockFragilePlantElement {
/*     */   public BlockCocoa() {
/*  10 */     super(Material.PLANT);
/*  11 */     a(true);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  15 */     if (!j(world, i, j, k)) {
/*  16 */       b(world, i, j, k, world.getData(i, j, k), 0);
/*  17 */       world.setTypeAndData(i, j, k, getById(0), 0, 2);
/*  18 */     } else if (world.random.nextInt(5) == 0) {
/*  19 */       int l = world.getData(i, j, k);
/*  20 */       int i1 = c(l);
/*     */       
/*  22 */       if (i1 < 2) {
/*  23 */         i1++;
/*     */         
/*  25 */         CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this, i1 << 2 | l(l));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean j(World world, int i, int j, int k) {
/*  31 */     int l = l(world.getData(i, j, k));
/*     */     
/*  33 */     i += Direction.a[l];
/*  34 */     k += Direction.b[l];
/*  35 */     Block block = world.getType(i, j, k);
/*     */     
/*  37 */     return (block == Blocks.LOG && BlockLogAbstract.c(world.getData(i, j, k)) == 3);
/*     */   }
/*     */   
/*     */   public int b() {
/*  41 */     return 28;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  45 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  49 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  53 */     updateShape(world, i, j, k);
/*  54 */     return super.a(world, i, j, k);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  58 */     int l = iblockaccess.getData(i, j, k);
/*  59 */     int i1 = l(l);
/*  60 */     int j1 = c(l);
/*  61 */     int k1 = 4 + j1 * 2;
/*  62 */     int l1 = 5 + j1 * 2;
/*  63 */     float f = k1 / 2.0F;
/*     */     
/*  65 */     switch (i1) {
/*     */       case 0:
/*  67 */         a((8.0F - f) / 16.0F, (12.0F - l1) / 16.0F, (15.0F - k1) / 16.0F, (8.0F + f) / 16.0F, 0.75F, 0.9375F);
/*     */         break;
/*     */       
/*     */       case 1:
/*  71 */         a(0.0625F, (12.0F - l1) / 16.0F, (8.0F - f) / 16.0F, (1.0F + k1) / 16.0F, 0.75F, (8.0F + f) / 16.0F);
/*     */         break;
/*     */       
/*     */       case 2:
/*  75 */         a((8.0F - f) / 16.0F, (12.0F - l1) / 16.0F, 0.0625F, (8.0F + f) / 16.0F, 0.75F, (1.0F + k1) / 16.0F);
/*     */         break;
/*     */       
/*     */       case 3:
/*  79 */         a((15.0F - k1) / 16.0F, (12.0F - l1) / 16.0F, (8.0F - f) / 16.0F, 0.9375F, 0.75F, (8.0F + f) / 16.0F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/*  84 */     int l = ((MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3) + 0) % 4;
/*     */     
/*  86 */     world.setData(i, j, k, l, 2);
/*     */   }
/*     */   
/*     */   public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
/*  90 */     if (l == 1 || l == 0) {
/*  91 */       l = 2;
/*     */     }
/*     */     
/*  94 */     return Direction.f[Direction.e[l]];
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  98 */     if (!j(world, i, j, k)) {
/*  99 */       b(world, i, j, k, world.getData(i, j, k), 0);
/* 100 */       world.setTypeAndData(i, j, k, getById(0), 0, 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int c(int i) {
/* 105 */     return (i & 0xC) >> 2;
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/* 109 */     int j1 = c(l);
/* 110 */     byte b0 = 1;
/*     */     
/* 112 */     if (j1 >= 2) {
/* 113 */       b0 = 3;
/*     */     }
/*     */     
/* 116 */     for (int k1 = 0; k1 < b0; k1++) {
/* 117 */       a(world, i, j, k, new ItemStack(Items.INK_SACK, 1, 3));
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDropData(World world, int i, int j, int k) {
/* 122 */     return 3;
/*     */   }
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, boolean flag) {
/* 126 */     int l = world.getData(i, j, k);
/* 127 */     int i1 = c(l);
/*     */     
/* 129 */     return (i1 < 2);
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, int i, int j, int k) {
/* 133 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, int i, int j, int k) {
/* 137 */     int l = world.getData(i, j, k);
/* 138 */     int i1 = BlockDirectional.l(l);
/* 139 */     int j1 = c(l);
/*     */     
/* 141 */     j1++;
/* 142 */     CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this, j1 << 2 | i1);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockCocoa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */