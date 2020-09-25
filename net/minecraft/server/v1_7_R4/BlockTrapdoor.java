/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockTrapdoor extends Block {
/*     */   protected BlockTrapdoor(Material material) {
/*   8 */     super(material);
/*   9 */     float f = 0.5F;
/*  10 */     float f1 = 1.0F;
/*     */     
/*  12 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/*  13 */     a(CreativeModeTab.d);
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  17 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  21 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
/*  25 */     return !d(iblockaccess.getData(i, j, k));
/*     */   }
/*     */   
/*     */   public int b() {
/*  29 */     return 0;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  33 */     updateShape(world, i, j, k);
/*  34 */     return super.a(world, i, j, k);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  38 */     b(iblockaccess.getData(i, j, k));
/*     */   }
/*     */   
/*     */   public void g() {
/*  42 */     float f = 0.1875F;
/*     */     
/*  44 */     a(0.0F, 0.5F - f / 2.0F, 0.0F, 1.0F, 0.5F + f / 2.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void b(int i) {
/*  48 */     float f = 0.1875F;
/*     */     
/*  50 */     if ((i & 0x8) != 0) {
/*  51 */       a(0.0F, 1.0F - f, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } else {
/*  53 */       a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
/*     */     } 
/*     */     
/*  56 */     if (d(i)) {
/*  57 */       if ((i & 0x3) == 0) {
/*  58 */         a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */       }
/*     */       
/*  61 */       if ((i & 0x3) == 1) {
/*  62 */         a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */       }
/*     */       
/*  65 */       if ((i & 0x3) == 2) {
/*  66 */         a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       }
/*     */       
/*  69 */       if ((i & 0x3) == 3) {
/*  70 */         a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/*  78 */     if (this.material == Material.ORE) {
/*  79 */       return true;
/*     */     }
/*  81 */     int i1 = world.getData(i, j, k);
/*     */     
/*  83 */     world.setData(i, j, k, i1 ^ 0x4, 2);
/*  84 */     world.a(entityhuman, 1003, i, j, k, 0);
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOpen(World world, int i, int j, int k, boolean flag) {
/*  90 */     int l = world.getData(i, j, k);
/*  91 */     boolean flag1 = ((l & 0x4) > 0);
/*     */     
/*  93 */     if (flag1 != flag) {
/*  94 */       world.setData(i, j, k, l ^ 0x4, 2);
/*  95 */       world.a((EntityHuman)null, 1003, i, j, k, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 100 */     if (!world.isStatic) {
/* 101 */       int l = world.getData(i, j, k);
/* 102 */       int i1 = i;
/* 103 */       int j1 = k;
/*     */       
/* 105 */       if ((l & 0x3) == 0) {
/* 106 */         j1 = k + 1;
/*     */       }
/*     */       
/* 109 */       if ((l & 0x3) == 1) {
/* 110 */         j1--;
/*     */       }
/*     */       
/* 113 */       if ((l & 0x3) == 2) {
/* 114 */         i1 = i + 1;
/*     */       }
/*     */       
/* 117 */       if ((l & 0x3) == 3) {
/* 118 */         i1--;
/*     */       }
/*     */       
/* 121 */       if (!a(world.getType(i1, j, j1))) {
/* 122 */         world.setAir(i, j, k);
/* 123 */         b(world, i, j, k, l, 0);
/*     */       } 
/*     */       
/* 126 */       boolean flag = world.isBlockIndirectlyPowered(i, j, k);
/*     */       
/* 128 */       if (flag || block.isPowerSource()) {
/*     */         
/* 130 */         CraftWorld craftWorld = world.getWorld();
/* 131 */         Block bblock = craftWorld.getBlockAt(i, j, k);
/*     */         
/* 133 */         int power = bblock.getBlockPower();
/* 134 */         int oldPower = ((world.getData(i, j, k) & 0x4) > 0) ? 15 : 0;
/*     */         
/* 136 */         if ((((oldPower == 0) ? 1 : 0) ^ ((power == 0) ? 1 : 0)) != 0 || block.isPowerSource()) {
/* 137 */           BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bblock, oldPower, power);
/* 138 */           world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/* 139 */           flag = (eventRedstone.getNewCurrent() > 0);
/*     */         } 
/*     */ 
/*     */         
/* 143 */         setOpen(world, i, j, k, flag);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
/* 149 */     updateShape(world, i, j, k);
/* 150 */     return super.a(world, i, j, k, vec3d, vec3d1);
/*     */   }
/*     */   
/*     */   public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
/* 154 */     int j1 = 0;
/*     */     
/* 156 */     if (l == 2) {
/* 157 */       j1 = 0;
/*     */     }
/*     */     
/* 160 */     if (l == 3) {
/* 161 */       j1 = 1;
/*     */     }
/*     */     
/* 164 */     if (l == 4) {
/* 165 */       j1 = 2;
/*     */     }
/*     */     
/* 168 */     if (l == 5) {
/* 169 */       j1 = 3;
/*     */     }
/*     */     
/* 172 */     if (l != 1 && l != 0 && f1 > 0.5F) {
/* 173 */       j1 |= 0x8;
/*     */     }
/*     */     
/* 176 */     return j1;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k, int l) {
/* 180 */     if (l == 0)
/* 181 */       return false; 
/* 182 */     if (l == 1) {
/* 183 */       return false;
/*     */     }
/* 185 */     if (l == 2) {
/* 186 */       k++;
/*     */     }
/*     */     
/* 189 */     if (l == 3) {
/* 190 */       k--;
/*     */     }
/*     */     
/* 193 */     if (l == 4) {
/* 194 */       i++;
/*     */     }
/*     */     
/* 197 */     if (l == 5) {
/* 198 */       i--;
/*     */     }
/*     */     
/* 201 */     return a(world.getType(i, j, k));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean d(int i) {
/* 206 */     return ((i & 0x4) != 0);
/*     */   }
/*     */   
/*     */   private static boolean a(Block block) {
/* 210 */     return ((block.material.k() && block.d()) || block == Blocks.GLOWSTONE || block instanceof BlockStepAbstract || block instanceof BlockStairs);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockTrapdoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */