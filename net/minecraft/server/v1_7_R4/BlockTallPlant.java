/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public class BlockTallPlant extends BlockPlant implements IBlockFragilePlantElement {
/*   7 */   public static final String[] a = new String[] { "sunflower", "syringa", "grass", "fern", "rose", "paeonia" };
/*     */   
/*     */   public BlockTallPlant() {
/*  10 */     super(Material.PLANT);
/*  11 */     c(0.0F);
/*  12 */     a(h);
/*  13 */     c("doublePlant");
/*     */   }
/*     */   
/*     */   public int b() {
/*  17 */     return 40;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  21 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public int e(IBlockAccess iblockaccess, int i, int j, int k) {
/*  25 */     int l = iblockaccess.getData(i, j, k);
/*     */     
/*  27 */     return !c(l) ? (l & 0x7) : (iblockaccess.getData(i, j - 1, k) & 0x7);
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  31 */     return (super.canPlace(world, i, j, k) && world.isEmpty(i, j + 1, k));
/*     */   }
/*     */   
/*     */   protected void e(World world, int i, int j, int k) {
/*  35 */     if (!j(world, i, j, k)) {
/*  36 */       int l = world.getData(i, j, k);
/*     */       
/*  38 */       if (!c(l)) {
/*  39 */         b(world, i, j, k, l, 0);
/*  40 */         if (world.getType(i, j + 1, k) == this) {
/*  41 */           world.setTypeAndData(i, j + 1, k, Blocks.AIR, 0, 2);
/*     */         }
/*     */       } 
/*     */       
/*  45 */       world.setTypeAndData(i, j, k, Blocks.AIR, 0, 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean j(World world, int i, int j, int k) {
/*  50 */     int l = world.getData(i, j, k);
/*     */     
/*  52 */     return c(l) ? ((world.getType(i, j - 1, k) == this)) : ((world.getType(i, j + 1, k) == this && super.j(world, i, j, k)));
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/*  56 */     if (c(i)) {
/*  57 */       return null;
/*     */     }
/*  59 */     int k = d(i);
/*     */     
/*  61 */     return (k != 3 && k != 2) ? Item.getItemOf(this) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDropData(int i) {
/*  66 */     return c(i) ? 0 : (i & 0x7);
/*     */   }
/*     */   
/*     */   public static boolean c(int i) {
/*  70 */     return ((i & 0x8) != 0);
/*     */   }
/*     */   
/*     */   public static int d(int i) {
/*  74 */     return i & 0x7;
/*     */   }
/*     */   
/*     */   public void c(World world, int i, int j, int k, int l, int i1) {
/*  78 */     world.setTypeAndData(i, j, k, this, l, i1);
/*  79 */     CraftEventFactory.handleBlockGrowEvent(world, i, j + 1, k, this, 8);
/*     */   }
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/*  83 */     int l = ((MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3) + 2) % 4;
/*     */     
/*  85 */     world.setTypeAndData(i, j + 1, k, this, 0x8 | l, 2);
/*     */   }
/*     */   
/*     */   public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
/*  89 */     if (world.isStatic || entityhuman.bF() == null || entityhuman.bF().getItem() != Items.SHEARS || c(l) || !b(world, i, j, k, l, entityhuman)) {
/*  90 */       super.a(world, entityhuman, i, j, k, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
/*  95 */     if (c(l)) {
/*  96 */       if (world.getType(i, j - 1, k) == this) {
/*  97 */         if (!entityhuman.abilities.canInstantlyBuild) {
/*  98 */           int i1 = world.getData(i, j - 1, k);
/*  99 */           int j1 = d(i1);
/*     */           
/* 101 */           if (j1 != 3 && j1 != 2) {
/* 102 */             world.setAir(i, j - 1, k, true);
/*     */           } else {
/* 104 */             if (!world.isStatic && entityhuman.bF() != null && entityhuman.bF().getItem() == Items.SHEARS) {
/* 105 */               b(world, i, j, k, i1, entityhuman);
/*     */             }
/*     */             
/* 108 */             world.setAir(i, j - 1, k);
/*     */           } 
/*     */         } else {
/* 111 */           world.setAir(i, j - 1, k);
/*     */         } 
/*     */       }
/* 114 */     } else if (entityhuman.abilities.canInstantlyBuild && world.getType(i, j + 1, k) == this) {
/* 115 */       world.setTypeAndData(i, j + 1, k, Blocks.AIR, 0, 2);
/*     */     } 
/*     */     
/* 118 */     super.a(world, i, j, k, l, entityhuman);
/*     */   }
/*     */   
/*     */   private boolean b(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
/* 122 */     int i1 = d(l);
/*     */     
/* 124 */     if (i1 != 3 && i1 != 2) {
/* 125 */       return false;
/*     */     }
/* 127 */     entityhuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
/* 128 */     byte b0 = 1;
/*     */     
/* 130 */     if (i1 == 3) {
/* 131 */       b0 = 2;
/*     */     }
/*     */     
/* 134 */     a(world, i, j, k, new ItemStack(Blocks.LONG_GRASS, 2, b0));
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDropData(World world, int i, int j, int k) {
/* 140 */     int l = world.getData(i, j, k);
/*     */     
/* 142 */     return c(l) ? d(world.getData(i, j - 1, k)) : d(l);
/*     */   }
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, boolean flag) {
/* 146 */     int l = e(world, i, j, k);
/*     */     
/* 148 */     return (l != 2 && l != 3);
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, int i, int j, int k) {
/* 152 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, int i, int j, int k) {
/* 156 */     int l = e(world, i, j, k);
/*     */     
/* 158 */     a(world, i, j, k, new ItemStack(this, 1, l));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockTallPlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */