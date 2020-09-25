/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockHopper
/*     */   extends BlockContainer {
/*   8 */   private final Random a = new Random();
/*     */   
/*     */   public BlockHopper() {
/*  11 */     super(Material.ORE);
/*  12 */     a(CreativeModeTab.d);
/*  13 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  17 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List list, Entity entity) {
/*  21 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*  22 */     super.a(world, i, j, k, axisalignedbb, list, entity);
/*  23 */     float f = 0.125F;
/*     */     
/*  25 */     a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  26 */     super.a(world, i, j, k, axisalignedbb, list, entity);
/*  27 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  28 */     super.a(world, i, j, k, axisalignedbb, list, entity);
/*  29 */     a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  30 */     super.a(world, i, j, k, axisalignedbb, list, entity);
/*  31 */     a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  32 */     super.a(world, i, j, k, axisalignedbb, list, entity);
/*  33 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
/*  37 */     int j1 = Facing.OPPOSITE_FACING[l];
/*     */     
/*  39 */     if (j1 == 1) {
/*  40 */       j1 = 0;
/*     */     }
/*     */     
/*  43 */     return j1;
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/*  47 */     return new TileEntityHopper();
/*     */   }
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/*  51 */     super.postPlace(world, i, j, k, entityliving, itemstack);
/*  52 */     if (itemstack.hasName()) {
/*  53 */       TileEntityHopper tileentityhopper = e(world, i, j, k);
/*     */       
/*  55 */       tileentityhopper.a(itemstack.getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/*  60 */     super.onPlace(world, i, j, k);
/*  61 */     e(world, i, j, k);
/*     */   }
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/*  65 */     if (world.isStatic) {
/*  66 */       return true;
/*     */     }
/*  68 */     TileEntityHopper tileentityhopper = e(world, i, j, k);
/*     */     
/*  70 */     if (tileentityhopper != null) {
/*  71 */       entityhuman.openHopper(tileentityhopper);
/*     */     }
/*     */     
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  79 */     e(world, i, j, k);
/*     */   }
/*     */   
/*     */   private void e(World world, int i, int j, int k) {
/*  83 */     int l = world.getData(i, j, k);
/*  84 */     int i1 = b(l);
/*  85 */     boolean flag = !world.isBlockIndirectlyPowered(i, j, k);
/*  86 */     boolean flag1 = c(l);
/*     */     
/*  88 */     if (flag != flag1) {
/*  89 */       world.setData(i, j, k, i1 | (flag ? 0 : 8), 4);
/*     */ 
/*     */ 
/*     */       
/*  93 */       if (world.spigotConfig.altHopperTicking) {
/*     */         
/*  95 */         TileEntityHopper hopper = e(world, i, j, k);
/*  96 */         if (flag && hopper != null) {
/*  97 */           hopper.makeTick();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/* 105 */     TileEntityHopper tileentityhopper = (TileEntityHopper)world.getTileEntity(i, j, k);
/*     */     
/* 107 */     if (tileentityhopper != null) {
/* 108 */       for (int i1 = 0; i1 < tileentityhopper.getSize(); i1++) {
/* 109 */         ItemStack itemstack = tileentityhopper.getItem(i1);
/*     */         
/* 111 */         if (itemstack != null) {
/* 112 */           float f = this.a.nextFloat() * 0.8F + 0.1F;
/* 113 */           float f1 = this.a.nextFloat() * 0.8F + 0.1F;
/* 114 */           float f2 = this.a.nextFloat() * 0.8F + 0.1F;
/*     */           
/* 116 */           while (itemstack.count > 0) {
/* 117 */             int j1 = this.a.nextInt(21) + 10;
/*     */             
/* 119 */             if (j1 > itemstack.count) {
/* 120 */               j1 = itemstack.count;
/*     */             }
/*     */             
/* 123 */             itemstack.count -= j1;
/* 124 */             EntityItem entityitem = new EntityItem(world, (i + f), (j + f1), (k + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getData()));
/*     */             
/* 126 */             if (itemstack.hasTag()) {
/* 127 */               entityitem.getItemStack().setTag((NBTTagCompound)itemstack.getTag().clone());
/*     */             }
/*     */             
/* 130 */             float f3 = 0.05F;
/*     */             
/* 132 */             entityitem.motX = ((float)this.a.nextGaussian() * f3);
/* 133 */             entityitem.motY = ((float)this.a.nextGaussian() * f3 + 0.2F);
/* 134 */             entityitem.motZ = ((float)this.a.nextGaussian() * f3);
/* 135 */             world.addEntity(entityitem);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 140 */       world.updateAdjacentComparators(i, j, k, block);
/*     */     } 
/*     */     
/* 143 */     super.remove(world, i, j, k, block, l);
/*     */   }
/*     */   
/*     */   public int b() {
/* 147 */     return 38;
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 151 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 155 */     return false;
/*     */   }
/*     */   
/*     */   public static int b(int i) {
/* 159 */     return Math.min(i & 0x7, 5);
/*     */   }
/*     */   
/*     */   public static boolean c(int i) {
/* 163 */     return ((i & 0x8) != 8);
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 167 */     return true;
/*     */   }
/*     */   
/*     */   public int g(World world, int i, int j, int k, int l) {
/* 171 */     return Container.b(e(world, i, j, k));
/*     */   }
/*     */   
/*     */   public static TileEntityHopper e(IBlockAccess iblockaccess, int i, int j, int k) {
/* 175 */     return (TileEntityHopper)iblockaccess.getTileEntity(i, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/* 181 */     if (world.spigotConfig.altHopperTicking) {
/*     */       
/* 183 */       TileEntityHopper hopper = e(world, i, j, k);
/* 184 */       if (hopper != null)
/* 185 */         hopper.makeTick(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */