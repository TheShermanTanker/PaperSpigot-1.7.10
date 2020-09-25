/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockDoor extends Block {
/*     */   protected BlockDoor(Material material) {
/*  10 */     super(material);
/*  11 */     float f = 0.5F;
/*  12 */     float f1 = 1.0F;
/*     */     
/*  14 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  18 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
/*  22 */     int l = g(iblockaccess, i, j, k);
/*     */     
/*  24 */     return ((l & 0x4) != 0);
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  28 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  32 */     return 7;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  36 */     updateShape(world, i, j, k);
/*  37 */     return super.a(world, i, j, k);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  41 */     b(g(iblockaccess, i, j, k));
/*     */   }
/*     */   
/*     */   public int e(IBlockAccess iblockaccess, int i, int j, int k) {
/*  45 */     return g(iblockaccess, i, j, k) & 0x3;
/*     */   }
/*     */   
/*     */   public boolean f(IBlockAccess iblockaccess, int i, int j, int k) {
/*  49 */     return ((g(iblockaccess, i, j, k) & 0x4) != 0);
/*     */   }
/*     */   
/*     */   private void b(int i) {
/*  53 */     float f = 0.1875F;
/*     */     
/*  55 */     a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
/*  56 */     int j = i & 0x3;
/*  57 */     boolean flag = ((i & 0x4) != 0);
/*  58 */     boolean flag1 = ((i & 0x10) != 0);
/*     */     
/*  60 */     if (j == 0) {
/*  61 */       if (flag) {
/*  62 */         if (!flag1) {
/*  63 */           a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */         } else {
/*  65 */           a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */         } 
/*     */       } else {
/*  68 */         a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */       } 
/*  70 */     } else if (j == 1) {
/*  71 */       if (flag) {
/*  72 */         if (!flag1) {
/*  73 */           a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         } else {
/*  75 */           a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */         } 
/*     */       } else {
/*  78 */         a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */       } 
/*  80 */     } else if (j == 2) {
/*  81 */       if (flag) {
/*  82 */         if (!flag1) {
/*  83 */           a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */         } else {
/*  85 */           a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */         } 
/*     */       } else {
/*  88 */         a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       } 
/*  90 */     } else if (j == 3) {
/*  91 */       if (flag) {
/*  92 */         if (!flag1) {
/*  93 */           a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */         } else {
/*  95 */           a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         } 
/*     */       } else {
/*  98 */         a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/* 106 */     if (this.material == Material.ORE) {
/* 107 */       return true;
/*     */     }
/* 109 */     int i1 = g(world, i, j, k);
/* 110 */     int j1 = i1 & 0x7;
/*     */     
/* 112 */     j1 ^= 0x4;
/* 113 */     if ((i1 & 0x8) == 0) {
/* 114 */       world.setData(i, j, k, j1, 2);
/* 115 */       world.c(i, j, k, i, j, k);
/*     */     } else {
/* 117 */       world.setData(i, j - 1, k, j1, 2);
/* 118 */       world.c(i, j - 1, k, i, j, k);
/*     */     } 
/*     */     
/* 121 */     world.a(entityhuman, 1003, i, j, k, 0);
/* 122 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDoor(World world, int i, int j, int k, boolean flag) {
/* 127 */     int l = g(world, i, j, k);
/* 128 */     boolean flag1 = ((l & 0x4) != 0);
/*     */     
/* 130 */     if (flag1 != flag) {
/* 131 */       int i1 = l & 0x7;
/*     */       
/* 133 */       i1 ^= 0x4;
/* 134 */       if ((l & 0x8) == 0) {
/* 135 */         world.setData(i, j, k, i1, 2);
/* 136 */         world.c(i, j, k, i, j, k);
/*     */       } else {
/* 138 */         world.setData(i, j - 1, k, i1, 2);
/* 139 */         world.c(i, j - 1, k, i, j, k);
/*     */       } 
/*     */       
/* 142 */       world.a((EntityHuman)null, 1003, i, j, k, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 147 */     int l = world.getData(i, j, k);
/*     */     
/* 149 */     if ((l & 0x8) == 0) {
/* 150 */       boolean flag = false;
/*     */       
/* 152 */       if (world.getType(i, j + 1, k) != this) {
/* 153 */         world.setAir(i, j, k);
/* 154 */         flag = true;
/*     */       } 
/*     */       
/* 157 */       if (!World.a(world, i, j - 1, k)) {
/* 158 */         world.setAir(i, j, k);
/* 159 */         flag = true;
/* 160 */         if (world.getType(i, j + 1, k) == this) {
/* 161 */           world.setAir(i, j + 1, k);
/*     */         }
/*     */       } 
/*     */       
/* 165 */       if (flag) {
/* 166 */         if (!world.isStatic) {
/* 167 */           b(world, i, j, k, l, 0);
/*     */         }
/*     */       }
/* 170 */       else if (block.isPowerSource()) {
/* 171 */         CraftWorld craftWorld = world.getWorld();
/* 172 */         Block bukkitBlock = craftWorld.getBlockAt(i, j, k);
/* 173 */         Block blockTop = craftWorld.getBlockAt(i, j + 1, k);
/*     */         
/* 175 */         int power = bukkitBlock.getBlockPower();
/* 176 */         int powerTop = blockTop.getBlockPower();
/* 177 */         if (powerTop > power) power = powerTop; 
/* 178 */         int oldPower = ((world.getData(i, j, k) & 0x4) > 0) ? 15 : 0;
/*     */         
/* 180 */         if ((((oldPower == 0) ? 1 : 0) ^ ((power == 0) ? 1 : 0)) != 0) {
/* 181 */           BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, oldPower, power);
/* 182 */           world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*     */           
/* 184 */           setDoor(world, i, j, k, (eventRedstone.getNewCurrent() > 0));
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 189 */       if (world.getType(i, j - 1, k) != this) {
/* 190 */         world.setAir(i, j, k);
/*     */       }
/*     */       
/* 193 */       if (block != this) {
/* 194 */         doPhysics(world, i, j - 1, k, block);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 200 */     return ((i & 0x8) != 0) ? null : ((this.material == Material.ORE) ? Items.IRON_DOOR : Items.WOOD_DOOR);
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
/* 204 */     updateShape(world, i, j, k);
/* 205 */     return super.a(world, i, j, k, vec3d, vec3d1);
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/* 209 */     return (j >= 255) ? false : ((World.a(world, i, j - 1, k) && super.canPlace(world, i, j, k) && super.canPlace(world, i, j + 1, k)));
/*     */   }
/*     */   
/*     */   public int h() {
/* 213 */     return 1;
/*     */   }
/*     */   
/*     */   public int g(IBlockAccess iblockaccess, int i, int j, int k) {
/* 217 */     int i1, j1, l = iblockaccess.getData(i, j, k);
/* 218 */     boolean flag = ((l & 0x8) != 0);
/*     */ 
/*     */ 
/*     */     
/* 222 */     if (flag) {
/* 223 */       i1 = iblockaccess.getData(i, j - 1, k);
/* 224 */       j1 = l;
/*     */     } else {
/* 226 */       i1 = l;
/* 227 */       j1 = iblockaccess.getData(i, j + 1, k);
/*     */     } 
/*     */     
/* 230 */     boolean flag1 = ((j1 & 0x1) != 0);
/*     */     
/* 232 */     return i1 & 0x7 | (flag ? 8 : 0) | (flag1 ? 16 : 0);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
/* 236 */     if (entityhuman.abilities.canInstantlyBuild && (l & 0x8) != 0 && world.getType(i, j - 1, k) == this)
/* 237 */       world.setAir(i, j - 1, k); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */