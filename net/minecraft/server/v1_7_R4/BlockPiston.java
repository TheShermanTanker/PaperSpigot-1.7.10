/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockPistonExtendEvent;
/*     */ import org.bukkit.event.block.BlockPistonRetractEvent;
/*     */ 
/*     */ public class BlockPiston
/*     */   extends Block
/*     */ {
/*     */   private final boolean a;
/*     */   
/*     */   public BlockPiston(boolean flag) {
/*  16 */     super(Material.PISTON);
/*  17 */     this.a = flag;
/*  18 */     a(i);
/*  19 */     c(0.5F);
/*  20 */     a(CreativeModeTab.d);
/*     */   }
/*     */   
/*     */   public int b() {
/*  24 */     return 16;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  28 */     return false;
/*     */   }
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/*  32 */     return false;
/*     */   }
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/*  36 */     int l = a(world, i, j, k, entityliving);
/*     */     
/*  38 */     world.setData(i, j, k, l, 2);
/*  39 */     if (!world.isStatic) {
/*  40 */       e(world, i, j, k);
/*     */     }
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  45 */     if (!world.isStatic) {
/*  46 */       e(world, i, j, k);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/*  51 */     if (!world.isStatic && world.getTileEntity(i, j, k) == null) {
/*  52 */       e(world, i, j, k);
/*     */     }
/*     */   }
/*     */   
/*     */   private void e(World world, int i, int j, int k) {
/*  57 */     int l = world.getData(i, j, k);
/*  58 */     int i1 = b(l);
/*     */     
/*  60 */     if (i1 != 7) {
/*  61 */       boolean flag = a(world, i, j, k, i1);
/*     */       
/*  63 */       if (flag && !c(l)) {
/*     */         
/*  65 */         int length = h(world, i, j, k, i1);
/*  66 */         if (length >= 0) {
/*  67 */           Block block = world.getWorld().getBlockAt(i, j, k);
/*  68 */           BlockPistonExtendEvent event = new BlockPistonExtendEvent(block, length, CraftBlock.notchToBlockFace(i1));
/*  69 */           world.getServer().getPluginManager().callEvent((Event)event);
/*     */           
/*  71 */           if (event.isCancelled()) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*  76 */           world.playBlockAction(i, j, k, this, 0, i1);
/*     */         } 
/*  78 */       } else if (!flag && c(l)) {
/*     */         
/*  80 */         Block block = world.getWorld().getBlockAt(i, j, k);
/*  81 */         BlockPistonRetractEvent event = new BlockPistonRetractEvent(block, CraftBlock.notchToBlockFace(i1));
/*  82 */         world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/*  84 */         if (event.isCancelled()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  89 */         world.setData(i, j, k, i1, 2);
/*  90 */         world.playBlockAction(i, j, k, this, 1, i1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean a(World world, int i, int j, int k, int l) {
/*  96 */     return (l != 0 && world.isBlockFacePowered(i, j - 1, k, 0)) ? true : ((l != 1 && world.isBlockFacePowered(i, j + 1, k, 1)) ? true : ((l != 2 && world.isBlockFacePowered(i, j, k - 1, 2)) ? true : ((l != 3 && world.isBlockFacePowered(i, j, k + 1, 3)) ? true : ((l != 5 && world.isBlockFacePowered(i + 1, j, k, 5)) ? true : ((l != 4 && world.isBlockFacePowered(i - 1, j, k, 4)) ? true : (world.isBlockFacePowered(i, j, k, 0) ? true : (world.isBlockFacePowered(i, j + 2, k, 1) ? true : (world.isBlockFacePowered(i, j + 1, k - 1, 2) ? true : (world.isBlockFacePowered(i, j + 1, k + 1, 3) ? true : (world.isBlockFacePowered(i - 1, j + 1, k, 4) ? true : world.isBlockFacePowered(i + 1, j + 1, k, 5)))))))))));
/*     */   }
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, int l, int i1) {
/* 100 */     if (!world.isStatic) {
/* 101 */       boolean flag = a(world, i, j, k, i1);
/*     */       
/* 103 */       if (flag && l == 1) {
/* 104 */         world.setData(i, j, k, i1 | 0x8, 2);
/* 105 */         return false;
/*     */       } 
/*     */       
/* 108 */       if (!flag && l == 0) {
/* 109 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 113 */     if (l == 0) {
/* 114 */       if (!i(world, i, j, k, i1)) {
/* 115 */         return false;
/*     */       }
/*     */       
/* 118 */       world.setData(i, j, k, i1 | 0x8, 2);
/* 119 */       world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "tile.piston.out", 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
/* 120 */     } else if (l == 1) {
/* 121 */       TileEntity tileentity = world.getTileEntity(i + Facing.b[i1], j + Facing.c[i1], k + Facing.d[i1]);
/*     */       
/* 123 */       if (tileentity instanceof TileEntityPiston) {
/* 124 */         ((TileEntityPiston)tileentity).f();
/*     */       }
/*     */       
/* 127 */       world.setTypeAndData(i, j, k, Blocks.PISTON_MOVING, i1, 3);
/* 128 */       world.setTileEntity(i, j, k, BlockPistonMoving.a(this, i1, i1, false, true));
/* 129 */       if (this.a) {
/* 130 */         int j1 = i + Facing.b[i1] * 2;
/* 131 */         int k1 = j + Facing.c[i1] * 2;
/* 132 */         int l1 = k + Facing.d[i1] * 2;
/* 133 */         Block block = world.getType(j1, k1, l1);
/* 134 */         int i2 = world.getData(j1, k1, l1);
/* 135 */         boolean flag1 = false;
/*     */         
/* 137 */         if (block == Blocks.PISTON_MOVING) {
/* 138 */           TileEntity tileentity1 = world.getTileEntity(j1, k1, l1);
/*     */           
/* 140 */           if (tileentity1 instanceof TileEntityPiston) {
/* 141 */             TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity1;
/*     */             
/* 143 */             if (tileentitypiston.c() == i1 && tileentitypiston.b()) {
/* 144 */               tileentitypiston.f();
/* 145 */               block = tileentitypiston.a();
/* 146 */               i2 = tileentitypiston.p();
/* 147 */               flag1 = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 152 */         if (!flag1 && block.getMaterial() != Material.AIR && a(block, world, j1, k1, l1, false) && (block.h() == 0 || block == Blocks.PISTON || block == Blocks.PISTON_STICKY)) {
/* 153 */           i += Facing.b[i1];
/* 154 */           j += Facing.c[i1];
/* 155 */           k += Facing.d[i1];
/* 156 */           world.setTypeAndData(i, j, k, Blocks.PISTON_MOVING, i2, 3);
/* 157 */           world.setTileEntity(i, j, k, BlockPistonMoving.a(block, i2, i1, false, false));
/* 158 */           world.setAir(j1, k1, l1);
/* 159 */         } else if (!flag1) {
/* 160 */           world.setAir(i + Facing.b[i1], j + Facing.c[i1], k + Facing.d[i1]);
/*     */         } 
/*     */       } else {
/* 163 */         world.setAir(i + Facing.b[i1], j + Facing.c[i1], k + Facing.d[i1]);
/*     */       } 
/*     */       
/* 166 */       world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "tile.piston.in", 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
/*     */     } 
/*     */     
/* 169 */     return true;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 173 */     int l = iblockaccess.getData(i, j, k);
/*     */     
/* 175 */     if (c(l)) {
/* 176 */       float f = 0.25F;
/*     */       
/* 178 */       switch (b(l)) {
/*     */         case 0:
/* 180 */           a(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case 1:
/* 184 */           a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/*     */           break;
/*     */         
/*     */         case 2:
/* 188 */           a(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case 3:
/* 192 */           a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
/*     */           break;
/*     */         
/*     */         case 4:
/* 196 */           a(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case 5:
/* 200 */           a(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F); break;
/*     */       } 
/*     */     } else {
/* 203 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void g() {
/* 208 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List list, Entity entity) {
/* 212 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 213 */     super.a(world, i, j, k, axisalignedbb, list, entity);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/* 217 */     updateShape(world, i, j, k);
/* 218 */     return super.a(world, i, j, k);
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 222 */     return false;
/*     */   }
/*     */   
/*     */   public static int b(int i) {
/* 226 */     if ((i & 0x7) >= Facing.OPPOSITE_FACING.length) return 7; 
/* 227 */     return i & 0x7;
/*     */   }
/*     */   
/*     */   public static boolean c(int i) {
/* 231 */     return ((i & 0x8) != 0);
/*     */   }
/*     */   
/*     */   public static int a(World world, int i, int j, int k, EntityLiving entityliving) {
/* 235 */     if (MathHelper.abs((float)entityliving.locX - i) < 2.0F && MathHelper.abs((float)entityliving.locZ - k) < 2.0F) {
/* 236 */       double d0 = entityliving.locY + 1.82D - entityliving.height;
/*     */       
/* 238 */       if (d0 - j > 2.0D) {
/* 239 */         return 1;
/*     */       }
/*     */       
/* 242 */       if (j - d0 > 0.0D) {
/* 243 */         return 0;
/*     */       }
/*     */     } 
/*     */     
/* 247 */     int l = MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */     
/* 249 */     return (l == 0) ? 2 : ((l == 1) ? 5 : ((l == 2) ? 3 : ((l == 3) ? 4 : 0)));
/*     */   }
/*     */   
/*     */   private static boolean a(Block block, World world, int i, int j, int k, boolean flag) {
/* 253 */     if (block == Blocks.OBSIDIAN) {
/* 254 */       return false;
/*     */     }
/* 256 */     if (block != Blocks.PISTON && block != Blocks.PISTON_STICKY) {
/* 257 */       if (block.f(world, i, j, k) == -1.0F) {
/* 258 */         return false;
/*     */       }
/*     */       
/* 261 */       if (block.h() == 2) {
/* 262 */         return false;
/*     */       }
/*     */       
/* 265 */       if (block.h() == 1) {
/* 266 */         if (!flag) {
/* 267 */           return false;
/*     */         }
/*     */         
/* 270 */         return true;
/*     */       } 
/* 272 */     } else if (c(world.getData(i, j, k))) {
/* 273 */       return false;
/*     */     } 
/*     */     
/* 276 */     return !(block instanceof IContainer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int h(World world, int i, int j, int k, int l) {
/* 282 */     int i1 = i + Facing.b[l];
/* 283 */     int j1 = j + Facing.c[l];
/* 284 */     int k1 = k + Facing.d[l];
/* 285 */     int l1 = 0;
/*     */ 
/*     */     
/* 288 */     while (l1 < 13) {
/* 289 */       if (j1 <= 0 || j1 >= 255) {
/* 290 */         return -1;
/*     */       }
/*     */       
/* 293 */       Block block = world.getType(i1, j1, k1);
/*     */       
/* 295 */       if (block.getMaterial() != Material.AIR) {
/* 296 */         if (!a(block, world, i1, j1, k1, true)) {
/* 297 */           return -1;
/*     */         }
/*     */         
/* 300 */         if (block.h() != 1) {
/* 301 */           if (l1 == 12) {
/* 302 */             return -1;
/*     */           }
/*     */           
/* 305 */           i1 += Facing.b[l];
/* 306 */           j1 += Facing.c[l];
/* 307 */           k1 += Facing.d[l];
/* 308 */           l1++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 314 */     return l1;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean i(World world, int i, int j, int k, int l) {
/* 319 */     int i1 = i + Facing.b[l];
/* 320 */     int j1 = j + Facing.c[l];
/* 321 */     int k1 = k + Facing.d[l];
/* 322 */     int l1 = 0;
/*     */ 
/*     */     
/* 325 */     while (l1 < 13) {
/* 326 */       if (j1 <= 0 || j1 >= 255) {
/* 327 */         return false;
/*     */       }
/*     */       
/* 330 */       Block block = world.getType(i1, j1, k1);
/*     */       
/* 332 */       if (block.getMaterial() != Material.AIR) {
/* 333 */         if (!a(block, world, i1, j1, k1, true)) {
/* 334 */           return false;
/*     */         }
/*     */         
/* 337 */         if (block.h() != 1) {
/* 338 */           if (l1 == 12) {
/* 339 */             return false;
/*     */           }
/*     */           
/* 342 */           i1 += Facing.b[l];
/* 343 */           j1 += Facing.c[l];
/* 344 */           k1 += Facing.d[l];
/* 345 */           l1++;
/*     */           
/*     */           continue;
/*     */         } 
/* 349 */         block.b(world, i1, j1, k1, world.getData(i1, j1, k1), 0);
/* 350 */         world.setAir(i1, j1, k1);
/*     */       } 
/*     */     } 
/*     */     
/* 354 */     l1 = i1;
/* 355 */     int i2 = j1;
/* 356 */     int j2 = k1;
/* 357 */     int k2 = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     Block[] ablock;
/*     */ 
/*     */     
/* 364 */     for (ablock = new Block[13]; i1 != i || j1 != j || k1 != k; k1 = j3) {
/* 365 */       int l2 = i1 - Facing.b[l];
/* 366 */       int i3 = j1 - Facing.c[l];
/* 367 */       int j3 = k1 - Facing.d[l];
/* 368 */       Block block1 = world.getType(l2, i3, j3);
/* 369 */       int k3 = world.getData(l2, i3, j3);
/*     */       
/* 371 */       if (block1 == this && l2 == i && i3 == j && j3 == k) {
/* 372 */         world.setTypeAndData(i1, j1, k1, Blocks.PISTON_MOVING, l | (this.a ? 8 : 0), 4);
/* 373 */         world.setTileEntity(i1, j1, k1, BlockPistonMoving.a(Blocks.PISTON_EXTENSION, l | (this.a ? 8 : 0), l, true, false));
/*     */       } else {
/* 375 */         world.setTypeAndData(i1, j1, k1, Blocks.PISTON_MOVING, k3, 4);
/* 376 */         world.setTileEntity(i1, j1, k1, BlockPistonMoving.a(block1, k3, l, true, false));
/*     */       } 
/*     */       
/* 379 */       ablock[k2++] = block1;
/* 380 */       i1 = l2;
/* 381 */       j1 = i3;
/*     */     } 
/*     */     
/* 384 */     i1 = l1;
/* 385 */     j1 = i2;
/* 386 */     k1 = j2;
/*     */     
/* 388 */     for (k2 = 0; i1 != i || j1 != j || k1 != k; k1 = j3) {
/* 389 */       int l2 = i1 - Facing.b[l];
/* 390 */       int i3 = j1 - Facing.c[l];
/* 391 */       int j3 = k1 - Facing.d[l];
/* 392 */       world.applyPhysics(l2, i3, j3, ablock[k2++]);
/* 393 */       i1 = l2;
/* 394 */       j1 = i3;
/*     */     } 
/*     */     
/* 397 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPiston.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */