/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockSign extends BlockContainer {
/*     */   private Class a;
/*     */   private boolean b;
/*     */   
/*     */   protected BlockSign(Class oclass, boolean flag) {
/*  13 */     super(Material.WOOD);
/*  14 */     this.b = flag;
/*  15 */     this.a = oclass;
/*  16 */     float f = 0.25F;
/*  17 */     float f1 = 1.0F;
/*     */     
/*  19 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  23 */     return null;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  27 */     if (!this.b) {
/*  28 */       int l = iblockaccess.getData(i, j, k);
/*  29 */       float f = 0.28125F;
/*  30 */       float f1 = 0.78125F;
/*  31 */       float f2 = 0.0F;
/*  32 */       float f3 = 1.0F;
/*  33 */       float f4 = 0.125F;
/*     */       
/*  35 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  36 */       if (l == 2) {
/*  37 */         a(f2, f, 1.0F - f4, f3, f1, 1.0F);
/*     */       }
/*     */       
/*  40 */       if (l == 3) {
/*  41 */         a(f2, f, 0.0F, f3, f1, f4);
/*     */       }
/*     */       
/*  44 */       if (l == 4) {
/*  45 */         a(1.0F - f4, f, f2, 1.0F, f1, f3);
/*     */       }
/*     */       
/*  48 */       if (l == 5) {
/*  49 */         a(0.0F, f, f2, f4, f1, f3);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int b() {
/*  55 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  59 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
/*  63 */     return true;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/*     */     try {
/*  72 */       return this.a.newInstance();
/*  73 */     } catch (Exception exception) {
/*  74 */       throw new RuntimeException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/*  79 */     return Items.SIGN;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  83 */     boolean flag = false;
/*     */     
/*  85 */     if (this.b) {
/*  86 */       if (!world.getType(i, j - 1, k).getMaterial().isBuildable()) {
/*  87 */         flag = true;
/*     */       }
/*     */     } else {
/*  90 */       int l = world.getData(i, j, k);
/*     */       
/*  92 */       flag = true;
/*  93 */       if (l == 2 && world.getType(i, j, k + 1).getMaterial().isBuildable()) {
/*  94 */         flag = false;
/*     */       }
/*     */       
/*  97 */       if (l == 3 && world.getType(i, j, k - 1).getMaterial().isBuildable()) {
/*  98 */         flag = false;
/*     */       }
/*     */       
/* 101 */       if (l == 4 && world.getType(i + 1, j, k).getMaterial().isBuildable()) {
/* 102 */         flag = false;
/*     */       }
/*     */       
/* 105 */       if (l == 5 && world.getType(i - 1, j, k).getMaterial().isBuildable()) {
/* 106 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/* 110 */     if (flag) {
/* 111 */       b(world, i, j, k, world.getData(i, j, k), 0);
/* 112 */       world.setAir(i, j, k);
/*     */     } 
/*     */     
/* 115 */     super.doPhysics(world, i, j, k, block);
/*     */ 
/*     */     
/* 118 */     if (block != null && block.isPowerSource()) {
/* 119 */       Block bukkitBlock = world.getWorld().getBlockAt(i, j, k);
/* 120 */       int power = bukkitBlock.getBlockPower();
/*     */       
/* 122 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, power, power);
/* 123 */       world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */