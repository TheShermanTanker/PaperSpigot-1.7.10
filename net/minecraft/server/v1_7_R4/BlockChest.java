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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockChest
/*     */   extends BlockContainer
/*     */ {
/*  27 */   private final Random b = new Random();
/*     */   public final int a;
/*     */   
/*     */   protected BlockChest(int paramInt) {
/*  31 */     super(Material.WOOD);
/*  32 */     this.a = paramInt;
/*  33 */     a(CreativeModeTab.c);
/*     */     
/*  35 */     a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/*  50 */     return 22;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  55 */     if (paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 - 1) == this) {
/*  56 */       a(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
/*  57 */     } else if (paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 + 1) == this) {
/*  58 */       a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
/*  59 */     } else if (paramIBlockAccess.getType(paramInt1 - 1, paramInt2, paramInt3) == this) {
/*  60 */       a(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*  61 */     } else if (paramIBlockAccess.getType(paramInt1 + 1, paramInt2, paramInt3) == this) {
/*  62 */       a(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
/*     */     } else {
/*  64 */       a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  70 */     super.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
/*  71 */     e(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */     
/*  73 */     Block block1 = paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1);
/*  74 */     Block block2 = paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1);
/*  75 */     Block block3 = paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3);
/*  76 */     Block block4 = paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3);
/*  77 */     if (block1 == this) e(paramWorld, paramInt1, paramInt2, paramInt3 - 1); 
/*  78 */     if (block2 == this) e(paramWorld, paramInt1, paramInt2, paramInt3 + 1); 
/*  79 */     if (block3 == this) e(paramWorld, paramInt1 - 1, paramInt2, paramInt3); 
/*  80 */     if (block4 == this) e(paramWorld, paramInt1 + 1, paramInt2, paramInt3);
/*     */   
/*     */   }
/*     */   
/*     */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack) {
/*  85 */     Block block1 = paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1);
/*  86 */     Block block2 = paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1);
/*  87 */     Block block3 = paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3);
/*  88 */     Block block4 = paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3);
/*     */     
/*  90 */     byte b = 0;
/*  91 */     int i = MathHelper.floor((paramEntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */     
/*  93 */     if (i == 0) b = 2; 
/*  94 */     if (i == 1) b = 5; 
/*  95 */     if (i == 2) b = 3; 
/*  96 */     if (i == 3) b = 4;
/*     */     
/*  98 */     if (block1 != this && block2 != this && block3 != this && block4 != this) {
/*  99 */       paramWorld.setData(paramInt1, paramInt2, paramInt3, b, 3);
/*     */     } else {
/* 101 */       if ((block1 == this || block2 == this) && (b == 4 || b == 5)) {
/* 102 */         if (block1 == this) { paramWorld.setData(paramInt1, paramInt2, paramInt3 - 1, b, 3); }
/* 103 */         else { paramWorld.setData(paramInt1, paramInt2, paramInt3 + 1, b, 3); }
/* 104 */          paramWorld.setData(paramInt1, paramInt2, paramInt3, b, 3);
/*     */       } 
/* 106 */       if ((block3 == this || block4 == this) && (b == 2 || b == 3)) {
/* 107 */         if (block3 == this) { paramWorld.setData(paramInt1 - 1, paramInt2, paramInt3, b, 3); }
/* 108 */         else { paramWorld.setData(paramInt1 + 1, paramInt2, paramInt3, b, 3); }
/* 109 */          paramWorld.setData(paramInt1, paramInt2, paramInt3, b, 3);
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     if (paramItemStack.hasName()) {
/* 114 */       ((TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3)).a(paramItemStack.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public void e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 119 */     if (paramWorld.isStatic) {
/*     */       return;
/*     */     }
/*     */     
/* 123 */     Block block1 = paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1);
/* 124 */     Block block2 = paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1);
/* 125 */     Block block3 = paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3);
/* 126 */     Block block4 = paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3);
/*     */ 
/*     */     
/* 129 */     byte b = 4;
/* 130 */     if (block1 == this || block2 == this) {
/* 131 */       int i = (block1 == this) ? (paramInt3 - 1) : (paramInt3 + 1);
/* 132 */       Block block5 = paramWorld.getType(paramInt1 - 1, paramInt2, i);
/* 133 */       int j = (block1 == this) ? (paramInt3 - 1) : (paramInt3 + 1);
/* 134 */       Block block6 = paramWorld.getType(paramInt1 + 1, paramInt2, j);
/*     */       
/* 136 */       b = 5;
/*     */       
/* 138 */       int k = -1;
/* 139 */       if (block1 == this) { k = paramWorld.getData(paramInt1, paramInt2, paramInt3 - 1); }
/* 140 */       else { k = paramWorld.getData(paramInt1, paramInt2, paramInt3 + 1); }
/* 141 */        if (k == 4) b = 4;
/*     */       
/* 143 */       if ((block3.j() || block5.j()) && !block4.j() && !block6.j()) b = 5; 
/* 144 */       if ((block4.j() || block6.j()) && !block3.j() && !block5.j()) b = 4; 
/* 145 */     } else if (block3 == this || block4 == this) {
/* 146 */       int i = (block3 == this) ? (paramInt1 - 1) : (paramInt1 + 1);
/* 147 */       Block block5 = paramWorld.getType(i, paramInt2, paramInt3 - 1);
/* 148 */       int j = (block3 == this) ? (paramInt1 - 1) : (paramInt1 + 1);
/* 149 */       Block block6 = paramWorld.getType(j, paramInt2, paramInt3 + 1);
/*     */       
/* 151 */       b = 3;
/* 152 */       int k = -1;
/* 153 */       if (block3 == this) { k = paramWorld.getData(paramInt1 - 1, paramInt2, paramInt3); }
/* 154 */       else { k = paramWorld.getData(paramInt1 + 1, paramInt2, paramInt3); }
/* 155 */        if (k == 2) b = 2;
/*     */       
/* 157 */       if ((block1.j() || block5.j()) && !block2.j() && !block6.j()) b = 3; 
/* 158 */       if ((block2.j() || block6.j()) && !block1.j() && !block5.j()) b = 2; 
/*     */     } else {
/* 160 */       b = 3;
/* 161 */       if (block1.j() && !block2.j()) b = 3; 
/* 162 */       if (block2.j() && !block1.j()) b = 2; 
/* 163 */       if (block3.j() && !block4.j()) b = 5; 
/* 164 */       if (block4.j() && !block3.j()) b = 4;
/*     */     
/*     */     } 
/* 167 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, b, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 172 */     byte b = 0;
/*     */     
/* 174 */     if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == this) b++; 
/* 175 */     if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == this) b++; 
/* 176 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == this) b++; 
/* 177 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == this) b++;
/*     */     
/* 179 */     if (b > 1) return false;
/*     */     
/* 181 */     if (n(paramWorld, paramInt1 - 1, paramInt2, paramInt3)) return false; 
/* 182 */     if (n(paramWorld, paramInt1 + 1, paramInt2, paramInt3)) return false; 
/* 183 */     if (n(paramWorld, paramInt1, paramInt2, paramInt3 - 1)) return false; 
/* 184 */     if (n(paramWorld, paramInt1, paramInt2, paramInt3 + 1)) return false; 
/* 185 */     return true;
/*     */   }
/*     */   
/*     */   private boolean n(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 189 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3) != this) return false; 
/* 190 */     if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == this) return true; 
/* 191 */     if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == this) return true; 
/* 192 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == this) return true; 
/* 193 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == this) return true; 
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/* 199 */     super.doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock);
/* 200 */     TileEntityChest tileEntityChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 201 */     if (tileEntityChest != null) tileEntityChest.u();
/*     */   
/*     */   }
/*     */   
/*     */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 206 */     TileEntityChest tileEntityChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 207 */     if (tileEntityChest != null) {
/* 208 */       for (byte b = 0; b < tileEntityChest.getSize(); b++) {
/* 209 */         ItemStack itemStack = tileEntityChest.getItem(b);
/* 210 */         if (itemStack != null) {
/* 211 */           float f1 = this.b.nextFloat() * 0.8F + 0.1F;
/* 212 */           float f2 = this.b.nextFloat() * 0.8F + 0.1F;
/* 213 */           float f3 = this.b.nextFloat() * 0.8F + 0.1F;
/*     */           
/* 215 */           while (itemStack.count > 0) {
/* 216 */             int i = this.b.nextInt(21) + 10;
/* 217 */             if (i > itemStack.count) i = itemStack.count; 
/* 218 */             itemStack.count -= i;
/*     */             
/* 220 */             EntityItem entityItem = new EntityItem(paramWorld, (paramInt1 + f1), (paramInt2 + f2), (paramInt3 + f3), new ItemStack(itemStack.getItem(), i, itemStack.getData()));
/* 221 */             float f = 0.05F;
/* 222 */             entityItem.motX = ((float)this.b.nextGaussian() * f);
/* 223 */             entityItem.motY = ((float)this.b.nextGaussian() * f + 0.2F);
/* 224 */             entityItem.motZ = ((float)this.b.nextGaussian() * f);
/* 225 */             if (itemStack.hasTag()) {
/* 226 */               entityItem.getItemStack().setTag((NBTTagCompound)itemStack.getTag().clone());
/*     */             }
/* 228 */             paramWorld.addEntity(entityItem);
/*     */           } 
/*     */         } 
/*     */       } 
/* 232 */       paramWorld.updateAdjacentComparators(paramInt1, paramInt2, paramInt3, paramBlock);
/*     */     } 
/* 234 */     super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 239 */     if (paramWorld.isStatic) return true; 
/* 240 */     IInventory iInventory = m(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */     
/* 242 */     if (iInventory != null) {
/* 243 */       paramEntityHuman.openContainer(iInventory);
/*     */     }
/*     */     
/* 246 */     return true;
/*     */   }
/*     */   public IInventory m(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*     */     InventoryLargeChest inventoryLargeChest;
/* 250 */     TileEntityChest tileEntityChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 251 */     if (tileEntityChest == null) return null;
/*     */     
/* 253 */     if (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3).r()) return null; 
/* 254 */     if (o(paramWorld, paramInt1, paramInt2, paramInt3)) return null;
/*     */     
/* 256 */     if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == this && (paramWorld.getType(paramInt1 - 1, paramInt2 + 1, paramInt3).r() || o(paramWorld, paramInt1 - 1, paramInt2, paramInt3))) return null; 
/* 257 */     if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == this && (paramWorld.getType(paramInt1 + 1, paramInt2 + 1, paramInt3).r() || o(paramWorld, paramInt1 + 1, paramInt2, paramInt3))) return null; 
/* 258 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == this && (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3 - 1).r() || o(paramWorld, paramInt1, paramInt2, paramInt3 - 1))) return null; 
/* 259 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == this && (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3 + 1).r() || o(paramWorld, paramInt1, paramInt2, paramInt3 + 1))) return null;
/*     */     
/* 261 */     if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == this) inventoryLargeChest = new InventoryLargeChest("container.chestDouble", (TileEntityChest)paramWorld.getTileEntity(paramInt1 - 1, paramInt2, paramInt3), tileEntityChest); 
/* 262 */     if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == this) inventoryLargeChest = new InventoryLargeChest("container.chestDouble", inventoryLargeChest, (TileEntityChest)paramWorld.getTileEntity(paramInt1 + 1, paramInt2, paramInt3)); 
/* 263 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == this) inventoryLargeChest = new InventoryLargeChest("container.chestDouble", (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3 - 1), inventoryLargeChest); 
/* 264 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == this) inventoryLargeChest = new InventoryLargeChest("container.chestDouble", inventoryLargeChest, (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3 + 1));
/*     */     
/* 266 */     return inventoryLargeChest;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt) {
/* 271 */     return new TileEntityChest();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPowerSource() {
/* 277 */     return (this.a == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 282 */     if (!isPowerSource()) return 0;
/*     */     
/* 284 */     int i = ((TileEntityChest)paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3)).o;
/* 285 */     return MathHelper.a(i, 0, 15);
/*     */   }
/*     */ 
/*     */   
/*     */   public int c(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 290 */     if (paramInt4 == 1) {
/* 291 */       return b(paramIBlockAccess, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/* 293 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean o(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 298 */     for (Entity entity : paramWorld.a(EntityOcelot.class, AxisAlignedBB.a(paramInt1, (paramInt2 + 1), paramInt3, (paramInt1 + 1), (paramInt2 + 2), (paramInt3 + 1)))) {
/* 299 */       EntityOcelot entityOcelot = (EntityOcelot)entity;
/* 300 */       if (entityOcelot.isSitting()) return true; 
/*     */     } 
/* 302 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 307 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 312 */     return Container.b(m(paramWorld, paramInt1, paramInt2, paramInt3));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */