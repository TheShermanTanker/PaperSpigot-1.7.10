/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public abstract class BlockPressurePlateAbstract
/*     */   extends Block {
/*     */   protected BlockPressurePlateAbstract(String s, Material material) {
/*  12 */     super(material);
/*  13 */     this.a = s;
/*  14 */     a(CreativeModeTab.d);
/*  15 */     a(true);
/*  16 */     b(d(15));
/*     */   }
/*     */   private String a;
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  20 */     b(iblockaccess.getData(i, j, k));
/*     */   }
/*     */   
/*     */   protected void b(int i) {
/*  24 */     boolean flag = (c(i) > 0);
/*  25 */     float f = 0.0625F;
/*     */     
/*  27 */     if (flag) {
/*  28 */       a(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
/*     */     } else {
/*  30 */       a(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  35 */     return 20;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  39 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  43 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  47 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
/*  51 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  55 */     return (World.a(world, i, j - 1, k) || BlockFence.a(world.getType(i, j - 1, k)));
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  59 */     boolean flag = false;
/*     */     
/*  61 */     if (!World.a(world, i, j - 1, k) && !BlockFence.a(world.getType(i, j - 1, k))) {
/*  62 */       flag = true;
/*     */     }
/*     */     
/*  65 */     if (flag) {
/*  66 */       b(world, i, j, k, world.getData(i, j, k), 0);
/*  67 */       world.setAir(i, j, k);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  72 */     if (!world.isStatic) {
/*  73 */       int l = c(world.getData(i, j, k));
/*     */       
/*  75 */       if (l > 0) {
/*  76 */         a(world, i, j, k, l);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity) {
/*  82 */     if (!world.isStatic) {
/*  83 */       int l = c(world.getData(i, j, k));
/*     */       
/*  85 */       if (l == 0) {
/*  86 */         a(world, i, j, k, l);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(World world, int i, int j, int k, int l) {
/*  92 */     int i1 = e(world, i, j, k);
/*  93 */     boolean flag = (l > 0);
/*  94 */     boolean flag1 = (i1 > 0);
/*     */ 
/*     */     
/*  97 */     CraftWorld craftWorld = world.getWorld();
/*  98 */     PluginManager manager = world.getServer().getPluginManager();
/*     */     
/* 100 */     if (flag != flag1) {
/* 101 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(craftWorld.getBlockAt(i, j, k), l, i1);
/* 102 */       manager.callEvent((Event)eventRedstone);
/*     */       
/* 104 */       flag1 = (eventRedstone.getNewCurrent() > 0);
/* 105 */       i1 = eventRedstone.getNewCurrent();
/*     */     } 
/*     */ 
/*     */     
/* 109 */     if (l != i1) {
/* 110 */       world.setData(i, j, k, d(i1), 2);
/* 111 */       a_(world, i, j, k);
/* 112 */       world.c(i, j, k, i, j, k);
/*     */     } 
/*     */     
/* 115 */     if (!flag1 && flag) {
/* 116 */       world.makeSound(i + 0.5D, j + 0.1D, k + 0.5D, "random.click", 0.3F, 0.5F);
/* 117 */     } else if (flag1 && !flag) {
/* 118 */       world.makeSound(i + 0.5D, j + 0.1D, k + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     } 
/*     */     
/* 121 */     if (flag1) {
/* 122 */       world.a(i, j, k, this, a(world));
/*     */     }
/*     */   }
/*     */   
/*     */   protected AxisAlignedBB a(int i, int j, int k) {
/* 127 */     float f = 0.125F;
/*     */     
/* 129 */     return AxisAlignedBB.a((i + f), j, (k + f), ((i + 1) - f), j + 0.25D, ((k + 1) - f));
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/* 133 */     if (c(l) > 0) {
/* 134 */       a_(world, i, j, k);
/*     */     }
/*     */     
/* 137 */     super.remove(world, i, j, k, block, l);
/*     */   }
/*     */   
/*     */   protected void a_(World world, int i, int j, int k) {
/* 141 */     world.applyPhysics(i, j, k, this);
/* 142 */     world.applyPhysics(i, j - 1, k, this);
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 146 */     return c(iblockaccess.getData(i, j, k));
/*     */   }
/*     */   
/*     */   public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 150 */     return (l == 1) ? c(iblockaccess.getData(i, j, k)) : 0;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/* 154 */     return true;
/*     */   }
/*     */   
/*     */   public void g() {
/* 158 */     float f = 0.5F;
/* 159 */     float f1 = 0.125F;
/* 160 */     float f2 = 0.5F;
/*     */     
/* 162 */     a(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
/*     */   }
/*     */   
/*     */   public int h() {
/* 166 */     return 1;
/*     */   }
/*     */   
/*     */   protected abstract int e(World paramWorld, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   protected abstract int c(int paramInt);
/*     */   
/*     */   protected abstract int d(int paramInt);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPressurePlateAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */