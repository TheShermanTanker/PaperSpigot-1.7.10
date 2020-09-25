/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockFurnace
/*     */   extends BlockContainer
/*     */ {
/*  22 */   private final Random a = new Random();
/*     */   
/*     */   private final boolean b;
/*     */   
/*     */   private static boolean M;
/*     */   
/*     */   protected BlockFurnace(boolean paramBoolean) {
/*  29 */     super(Material.STONE);
/*  30 */     this.b = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/*  35 */     return Item.getItemOf(Blocks.FURNACE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  40 */     super.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
/*  41 */     e(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   private void e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  46 */     if (paramWorld.isStatic) {
/*     */       return;
/*     */     }
/*     */     
/*  50 */     Block block1 = paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1);
/*  51 */     Block block2 = paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1);
/*  52 */     Block block3 = paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3);
/*  53 */     Block block4 = paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3);
/*     */     
/*  55 */     byte b = 3;
/*  56 */     if (block1.j() && !block2.j()) b = 3; 
/*  57 */     if (block2.j() && !block1.j()) b = 2; 
/*  58 */     if (block3.j() && !block4.j()) b = 5; 
/*  59 */     if (block4.j() && !block3.j()) b = 4; 
/*  60 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, b, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 108 */     if (paramWorld.isStatic) {
/* 109 */       return true;
/*     */     }
/* 111 */     TileEntityFurnace tileEntityFurnace = (TileEntityFurnace)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 112 */     if (tileEntityFurnace != null) paramEntityHuman.openFurnace(tileEntityFurnace); 
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   public static void a(boolean paramBoolean, World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 117 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 118 */     TileEntity tileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/*     */     
/* 120 */     M = true;
/* 121 */     if (paramBoolean) { paramWorld.setTypeUpdate(paramInt1, paramInt2, paramInt3, Blocks.BURNING_FURNACE); }
/* 122 */     else { paramWorld.setTypeUpdate(paramInt1, paramInt2, paramInt3, Blocks.FURNACE); }
/* 123 */      M = false;
/*     */     
/* 125 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 2);
/*     */     
/* 127 */     if (tileEntity != null) {
/* 128 */       tileEntity.t();
/* 129 */       paramWorld.setTileEntity(paramInt1, paramInt2, paramInt3, tileEntity);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt) {
/* 135 */     return new TileEntityFurnace();
/*     */   }
/*     */ 
/*     */   
/*     */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack) {
/* 140 */     int i = MathHelper.floor((paramEntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */     
/* 142 */     if (i == 0) paramWorld.setData(paramInt1, paramInt2, paramInt3, 2, 2); 
/* 143 */     if (i == 1) paramWorld.setData(paramInt1, paramInt2, paramInt3, 5, 2); 
/* 144 */     if (i == 2) paramWorld.setData(paramInt1, paramInt2, paramInt3, 3, 2); 
/* 145 */     if (i == 3) paramWorld.setData(paramInt1, paramInt2, paramInt3, 4, 2);
/*     */     
/* 147 */     if (paramItemStack.hasName()) {
/* 148 */       ((TileEntityFurnace)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3)).a(paramItemStack.getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 154 */     if (!M) {
/* 155 */       TileEntityFurnace tileEntityFurnace = (TileEntityFurnace)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 156 */       if (tileEntityFurnace != null) {
/* 157 */         for (byte b = 0; b < tileEntityFurnace.getSize(); b++) {
/* 158 */           ItemStack itemStack = tileEntityFurnace.getItem(b);
/* 159 */           if (itemStack != null) {
/* 160 */             float f1 = this.a.nextFloat() * 0.8F + 0.1F;
/* 161 */             float f2 = this.a.nextFloat() * 0.8F + 0.1F;
/* 162 */             float f3 = this.a.nextFloat() * 0.8F + 0.1F;
/*     */             
/* 164 */             while (itemStack.count > 0) {
/* 165 */               int i = this.a.nextInt(21) + 10;
/* 166 */               if (i > itemStack.count) i = itemStack.count; 
/* 167 */               itemStack.count -= i;
/*     */               
/* 169 */               EntityItem entityItem = new EntityItem(paramWorld, (paramInt1 + f1), (paramInt2 + f2), (paramInt3 + f3), new ItemStack(itemStack.getItem(), i, itemStack.getData()));
/*     */               
/* 171 */               if (itemStack.hasTag()) {
/* 172 */                 entityItem.getItemStack().setTag((NBTTagCompound)itemStack.getTag().clone());
/*     */               }
/*     */               
/* 175 */               float f = 0.05F;
/* 176 */               entityItem.motX = ((float)this.a.nextGaussian() * f);
/* 177 */               entityItem.motY = ((float)this.a.nextGaussian() * f + 0.2F);
/* 178 */               entityItem.motZ = ((float)this.a.nextGaussian() * f);
/* 179 */               paramWorld.addEntity(entityItem);
/*     */             } 
/*     */           } 
/*     */         } 
/* 183 */         paramWorld.updateAdjacentComparators(paramInt1, paramInt2, paramInt3, paramBlock);
/*     */       } 
/*     */     } 
/* 186 */     super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 196 */     return Container.b((IInventory)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */