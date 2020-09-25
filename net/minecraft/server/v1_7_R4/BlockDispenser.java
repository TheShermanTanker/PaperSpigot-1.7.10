/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockDispenser
/*     */   extends BlockContainer {
/*   7 */   public static final IRegistry a = new RegistryDefault(new DispenseBehaviorItem());
/*   8 */   protected Random b = new Random();
/*     */   public static boolean eventFired = false;
/*     */   
/*     */   protected BlockDispenser() {
/*  12 */     super(Material.STONE);
/*  13 */     a(CreativeModeTab.d);
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  17 */     return 4;
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/*  21 */     super.onPlace(world, i, j, k);
/*  22 */     m(world, i, j, k);
/*     */   }
/*     */   
/*     */   private void m(World world, int i, int j, int k) {
/*  26 */     if (!world.isStatic) {
/*  27 */       Block block = world.getType(i, j, k - 1);
/*  28 */       Block block1 = world.getType(i, j, k + 1);
/*  29 */       Block block2 = world.getType(i - 1, j, k);
/*  30 */       Block block3 = world.getType(i + 1, j, k);
/*  31 */       byte b0 = 3;
/*     */       
/*  33 */       if (block.j() && !block1.j()) {
/*  34 */         b0 = 3;
/*     */       }
/*     */       
/*  37 */       if (block1.j() && !block.j()) {
/*  38 */         b0 = 2;
/*     */       }
/*     */       
/*  41 */       if (block2.j() && !block3.j()) {
/*  42 */         b0 = 5;
/*     */       }
/*     */       
/*  45 */       if (block3.j() && !block2.j()) {
/*  46 */         b0 = 4;
/*     */       }
/*     */       
/*  49 */       world.setData(i, j, k, b0, 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/*  54 */     if (world.isStatic) {
/*  55 */       return true;
/*     */     }
/*  57 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)world.getTileEntity(i, j, k);
/*     */     
/*  59 */     if (tileentitydispenser != null) {
/*  60 */       entityhuman.openDispenser(tileentitydispenser);
/*     */     }
/*     */     
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispense(World world, int i, int j, int k) {
/*  69 */     SourceBlock sourceblock = new SourceBlock(world, i, j, k);
/*  70 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)sourceblock.getTileEntity();
/*     */     
/*  72 */     if (tileentitydispenser != null) {
/*  73 */       int l = tileentitydispenser.i();
/*     */       
/*  75 */       if (l < 0) {
/*  76 */         world.triggerEffect(1001, i, j, k, 0);
/*     */       } else {
/*  78 */         ItemStack itemstack = tileentitydispenser.getItem(l);
/*  79 */         IDispenseBehavior idispensebehavior = a(itemstack);
/*     */         
/*  81 */         if (idispensebehavior != IDispenseBehavior.a) {
/*  82 */           ItemStack itemstack1 = idispensebehavior.a(sourceblock, itemstack);
/*  83 */           eventFired = false;
/*     */           
/*  85 */           tileentitydispenser.setItem(l, (itemstack1.count == 0) ? null : itemstack1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected IDispenseBehavior a(ItemStack itemstack) {
/*  92 */     return (IDispenseBehavior)a.get(itemstack.getItem());
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  96 */     boolean flag = (world.isBlockIndirectlyPowered(i, j, k) || world.isBlockIndirectlyPowered(i, j + 1, k));
/*  97 */     int l = world.getData(i, j, k);
/*  98 */     boolean flag1 = ((l & 0x8) != 0);
/*     */     
/* 100 */     if (flag && !flag1) {
/* 101 */       world.a(i, j, k, this, a(world));
/* 102 */       world.setData(i, j, k, l | 0x8, 4);
/* 103 */     } else if (!flag && flag1) {
/* 104 */       world.setData(i, j, k, l & 0xFFFFFFF7, 4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/* 109 */     if (!world.isStatic) {
/* 110 */       dispense(world, i, j, k);
/*     */     }
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/* 115 */     return new TileEntityDispenser();
/*     */   }
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/* 119 */     int l = BlockPiston.a(world, i, j, k, entityliving);
/*     */     
/* 121 */     world.setData(i, j, k, l, 2);
/* 122 */     if (itemstack.hasName()) {
/* 123 */       ((TileEntityDispenser)world.getTileEntity(i, j, k)).a(itemstack.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/* 128 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)world.getTileEntity(i, j, k);
/*     */     
/* 130 */     if (tileentitydispenser != null) {
/* 131 */       for (int i1 = 0; i1 < tileentitydispenser.getSize(); i1++) {
/* 132 */         ItemStack itemstack = tileentitydispenser.getItem(i1);
/*     */         
/* 134 */         if (itemstack != null) {
/* 135 */           float f = this.b.nextFloat() * 0.8F + 0.1F;
/* 136 */           float f1 = this.b.nextFloat() * 0.8F + 0.1F;
/* 137 */           float f2 = this.b.nextFloat() * 0.8F + 0.1F;
/*     */           
/* 139 */           while (itemstack.count > 0) {
/* 140 */             int j1 = this.b.nextInt(21) + 10;
/*     */             
/* 142 */             if (j1 > itemstack.count) {
/* 143 */               j1 = itemstack.count;
/*     */             }
/*     */             
/* 146 */             itemstack.count -= j1;
/* 147 */             EntityItem entityitem = new EntityItem(world, (i + f), (j + f1), (k + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getData()));
/*     */             
/* 149 */             if (itemstack.hasTag()) {
/* 150 */               entityitem.getItemStack().setTag((NBTTagCompound)itemstack.getTag().clone());
/*     */             }
/*     */             
/* 153 */             float f3 = 0.05F;
/*     */             
/* 155 */             entityitem.motX = ((float)this.b.nextGaussian() * f3);
/* 156 */             entityitem.motY = ((float)this.b.nextGaussian() * f3 + 0.2F);
/* 157 */             entityitem.motZ = ((float)this.b.nextGaussian() * f3);
/* 158 */             world.addEntity(entityitem);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 163 */       world.updateAdjacentComparators(i, j, k, block);
/*     */     } 
/*     */     
/* 166 */     super.remove(world, i, j, k, block, l);
/*     */   }
/*     */   
/*     */   public static IPosition a(ISourceBlock isourceblock) {
/* 170 */     EnumFacing enumfacing = b(isourceblock.h());
/* 171 */     double d0 = isourceblock.getX() + 0.7D * enumfacing.getAdjacentX();
/* 172 */     double d1 = isourceblock.getY() + 0.7D * enumfacing.getAdjacentY();
/* 173 */     double d2 = isourceblock.getZ() + 0.7D * enumfacing.getAdjacentZ();
/*     */     
/* 175 */     return new Position(d0, d1, d2);
/*     */   }
/*     */   
/*     */   public static EnumFacing b(int i) {
/* 179 */     return EnumFacing.a(i & 0x7);
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 183 */     return true;
/*     */   }
/*     */   
/*     */   public int g(World world, int i, int j, int k, int l) {
/* 187 */     return Container.b((IInventory)world.getTileEntity(i, j, k));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockDispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */