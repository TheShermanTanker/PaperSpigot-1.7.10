/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockBrewingStand
/*     */   extends BlockContainer {
/*   8 */   private Random a = new Random();
/*     */   
/*     */   public BlockBrewingStand() {
/*  11 */     super(Material.ORE);
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  15 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  19 */     return 25;
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/*  23 */     return new TileEntityBrewingStand();
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  27 */     return false;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List list, Entity entity) {
/*  31 */     a(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
/*  32 */     super.a(world, i, j, k, axisalignedbb, list, entity);
/*  33 */     g();
/*  34 */     super.a(world, i, j, k, axisalignedbb, list, entity);
/*     */   }
/*     */   
/*     */   public void g() {
/*  38 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/*  42 */     if (world.isStatic) {
/*  43 */       return true;
/*     */     }
/*  45 */     TileEntityBrewingStand tileentitybrewingstand = (TileEntityBrewingStand)world.getTileEntity(i, j, k);
/*     */     
/*  47 */     if (tileentitybrewingstand != null) {
/*  48 */       entityhuman.openBrewingStand(tileentitybrewingstand);
/*     */     }
/*     */     
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/*  56 */     if (itemstack.hasName()) {
/*  57 */       ((TileEntityBrewingStand)world.getTileEntity(i, j, k)).a(itemstack.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/*  62 */     TileEntity tileentity = world.getTileEntity(i, j, k);
/*     */     
/*  64 */     if (tileentity instanceof TileEntityBrewingStand) {
/*  65 */       TileEntityBrewingStand tileentitybrewingstand = (TileEntityBrewingStand)tileentity;
/*     */       
/*  67 */       for (int i1 = 0; i1 < tileentitybrewingstand.getSize(); i1++) {
/*  68 */         ItemStack itemstack = tileentitybrewingstand.getItem(i1);
/*     */         
/*  70 */         if (itemstack != null) {
/*  71 */           float f = this.a.nextFloat() * 0.8F + 0.1F;
/*  72 */           float f1 = this.a.nextFloat() * 0.8F + 0.1F;
/*  73 */           float f2 = this.a.nextFloat() * 0.8F + 0.1F;
/*     */           
/*  75 */           while (itemstack.count > 0) {
/*  76 */             int j1 = this.a.nextInt(21) + 10;
/*     */             
/*  78 */             if (j1 > itemstack.count) {
/*  79 */               j1 = itemstack.count;
/*     */             }
/*     */             
/*  82 */             itemstack.count -= j1;
/*  83 */             EntityItem entityitem = new EntityItem(world, (i + f), (j + f1), (k + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getData()));
/*  84 */             float f3 = 0.05F;
/*     */             
/*  86 */             entityitem.motX = ((float)this.a.nextGaussian() * f3);
/*  87 */             entityitem.motY = ((float)this.a.nextGaussian() * f3 + 0.2F);
/*  88 */             entityitem.motZ = ((float)this.a.nextGaussian() * f3);
/*     */             
/*  90 */             if (itemstack.hasTag())
/*     */             {
/*  92 */               entityitem.getItemStack().setTag((NBTTagCompound)itemstack.getTag().clone());
/*     */             }
/*     */             
/*  95 */             world.addEntity(entityitem);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     super.remove(world, i, j, k, block, l);
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 105 */     return Items.BREWING_STAND;
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 109 */     return true;
/*     */   }
/*     */   
/*     */   public int g(World world, int i, int j, int k, int l) {
/* 113 */     return Container.b((IInventory)world.getTileEntity(i, j, k));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockBrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */