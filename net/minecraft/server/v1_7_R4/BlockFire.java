/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockBurnEvent;
/*     */ import org.bukkit.event.block.BlockSpreadEvent;
/*     */ 
/*     */ public class BlockFire extends Block {
/*  13 */   private int[] a = new int[256];
/*  14 */   private int[] b = new int[256];
/*     */   
/*     */   protected BlockFire() {
/*  17 */     super(Material.FIRE);
/*  18 */     a(true);
/*     */   }
/*     */   
/*     */   public static void e() {
/*  22 */     Blocks.FIRE.a(getId(Blocks.WOOD), 5, 20);
/*  23 */     Blocks.FIRE.a(getId(Blocks.WOOD_DOUBLE_STEP), 5, 20);
/*  24 */     Blocks.FIRE.a(getId(Blocks.WOOD_STEP), 5, 20);
/*  25 */     Blocks.FIRE.a(getId(Blocks.FENCE), 5, 20);
/*  26 */     Blocks.FIRE.a(getId(Blocks.WOOD_STAIRS), 5, 20);
/*  27 */     Blocks.FIRE.a(getId(Blocks.BIRCH_WOOD_STAIRS), 5, 20);
/*  28 */     Blocks.FIRE.a(getId(Blocks.SPRUCE_WOOD_STAIRS), 5, 20);
/*  29 */     Blocks.FIRE.a(getId(Blocks.JUNGLE_WOOD_STAIRS), 5, 20);
/*  30 */     Blocks.FIRE.a(getId(Blocks.LOG), 5, 5);
/*  31 */     Blocks.FIRE.a(getId(Blocks.LOG2), 5, 5);
/*  32 */     Blocks.FIRE.a(getId(Blocks.LEAVES), 30, 60);
/*  33 */     Blocks.FIRE.a(getId(Blocks.LEAVES2), 30, 60);
/*  34 */     Blocks.FIRE.a(getId(Blocks.BOOKSHELF), 30, 20);
/*  35 */     Blocks.FIRE.a(getId(Blocks.TNT), 15, 100);
/*  36 */     Blocks.FIRE.a(getId(Blocks.LONG_GRASS), 60, 100);
/*  37 */     Blocks.FIRE.a(getId(Blocks.DOUBLE_PLANT), 60, 100);
/*  38 */     Blocks.FIRE.a(getId(Blocks.YELLOW_FLOWER), 60, 100);
/*  39 */     Blocks.FIRE.a(getId(Blocks.RED_ROSE), 60, 100);
/*  40 */     Blocks.FIRE.a(getId(Blocks.WOOL), 30, 60);
/*  41 */     Blocks.FIRE.a(getId(Blocks.VINE), 15, 100);
/*  42 */     Blocks.FIRE.a(getId(Blocks.COAL_BLOCK), 5, 5);
/*  43 */     Blocks.FIRE.a(getId(Blocks.HAY_BLOCK), 60, 20);
/*  44 */     Blocks.FIRE.a(getId(Blocks.WOOL_CARPET), 60, 20);
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k) {
/*  48 */     this.a[i] = j;
/*  49 */     this.b[i] = k;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  53 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  57 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  65 */     return 3;
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/*  69 */     return 0;
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  73 */     return 30;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  77 */     if (world.getGameRules().getBoolean("doFireTick")) {
/*  78 */       boolean flag = (world.getType(i, j - 1, k) == Blocks.NETHERRACK);
/*     */       
/*  80 */       if (world.worldProvider instanceof WorldProviderTheEnd && world.getType(i, j - 1, k) == Blocks.BEDROCK) {
/*  81 */         flag = true;
/*     */       }
/*     */       
/*  84 */       if (!canPlace(world, i, j, k)) {
/*  85 */         fireExtinguished(world, i, j, k);
/*     */       }
/*     */       
/*  88 */       if (!flag && world.Q() && (world.isRainingAt(i, j, k) || world.isRainingAt(i - 1, j, k) || world.isRainingAt(i + 1, j, k) || world.isRainingAt(i, j, k - 1) || world.isRainingAt(i, j, k + 1))) {
/*  89 */         fireExtinguished(world, i, j, k);
/*     */       } else {
/*  91 */         int l = world.getData(i, j, k);
/*     */         
/*  93 */         if (l < 15) {
/*  94 */           world.setData(i, j, k, l + random.nextInt(3) / 2, 4);
/*     */         }
/*     */         
/*  97 */         world.a(i, j, k, this, a(world) + random.nextInt(10));
/*  98 */         if (!flag && !e(world, i, j, k)) {
/*  99 */           if (!World.a(world, i, j - 1, k) || l > 3) {
/* 100 */             fireExtinguished(world, i, j, k);
/*     */           }
/* 102 */         } else if (!flag && !e(world, i, j - 1, k) && l == 15 && random.nextInt(4) == 0) {
/* 103 */           fireExtinguished(world, i, j, k);
/*     */         } else {
/* 105 */           boolean flag1 = world.z(i, j, k);
/* 106 */           byte b0 = 0;
/*     */           
/* 108 */           if (flag1) {
/* 109 */             b0 = -50;
/*     */           }
/*     */           
/* 112 */           a(world, i + 1, j, k, 300 + b0, random, l);
/* 113 */           a(world, i - 1, j, k, 300 + b0, random, l);
/* 114 */           a(world, i, j - 1, k, 250 + b0, random, l);
/* 115 */           a(world, i, j + 1, k, 250 + b0, random, l);
/* 116 */           a(world, i, j, k - 1, 300 + b0, random, l);
/* 117 */           a(world, i, j, k + 1, 300 + b0, random, l);
/*     */           
/* 119 */           for (int i1 = i - 1; i1 <= i + 1; i1++) {
/* 120 */             for (int j1 = k - 1; j1 <= k + 1; j1++) {
/* 121 */               for (int k1 = j - 1; k1 <= j + 4; k1++) {
/* 122 */                 if (i1 != i || k1 != j || j1 != k) {
/* 123 */                   int l1 = 100;
/*     */                   
/* 125 */                   if (k1 > j + 1) {
/* 126 */                     l1 += (k1 - j + 1) * 100;
/*     */                   }
/*     */                   
/* 129 */                   int i2 = m(world, i1, k1, j1);
/*     */                   
/* 131 */                   if (i2 > 0) {
/* 132 */                     int j2 = (i2 + 40 + world.difficulty.a() * 7) / (l + 30);
/*     */                     
/* 134 */                     if (flag1) {
/* 135 */                       j2 /= 2;
/*     */                     }
/*     */                     
/* 138 */                     if (j2 > 0 && random.nextInt(l1) <= j2 && (!world.Q() || !world.isRainingAt(i1, k1, j1)) && !world.isRainingAt(i1 - 1, k1, k) && !world.isRainingAt(i1 + 1, k1, j1) && !world.isRainingAt(i1, k1, j1 - 1) && !world.isRainingAt(i1, k1, j1 + 1)) {
/* 139 */                       int k2 = l + random.nextInt(5) / 4;
/*     */                       
/* 141 */                       if (k2 > 15) {
/* 142 */                         k2 = 15;
/*     */                       }
/*     */ 
/*     */                       
/* 146 */                       if (world.getType(i1, k1, j1) != Blocks.FIRE && 
/* 147 */                         !CraftEventFactory.callBlockIgniteEvent(world, i1, k1, j1, i, j, k).isCancelled()) {
/*     */ 
/*     */ 
/*     */                         
/* 151 */                         CraftServer craftServer = world.getServer();
/* 152 */                         CraftWorld craftWorld = world.getWorld();
/* 153 */                         BlockState blockState = craftWorld.getBlockAt(i1, k1, j1).getState();
/* 154 */                         blockState.setTypeId(Block.getId(this));
/* 155 */                         blockState.setData(new MaterialData(Block.getId(this), (byte)k2));
/*     */                         
/* 157 */                         BlockSpreadEvent spreadEvent = new BlockSpreadEvent(blockState.getBlock(), craftWorld.getBlockAt(i, j, k), blockState);
/* 158 */                         craftServer.getPluginManager().callEvent((Event)spreadEvent);
/*     */                         
/* 160 */                         if (!spreadEvent.isCancelled()) {
/* 161 */                           blockState.update(true);
/*     */                         }
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean L() {
/* 177 */     return false;
/*     */   }
/*     */   
/*     */   private void a(World world, int i, int j, int k, int l, Random random, int i1) {
/* 181 */     int j1 = this.b[Block.getId(world.getType(i, j, k))];
/*     */     
/* 183 */     if (random.nextInt(l) < j1) {
/* 184 */       boolean flag = (world.getType(i, j, k) == Blocks.TNT);
/*     */ 
/*     */       
/* 187 */       Block theBlock = world.getWorld().getBlockAt(i, j, k);
/*     */       
/* 189 */       BlockBurnEvent event = new BlockBurnEvent(theBlock);
/* 190 */       world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/* 192 */       if (event.isCancelled()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 197 */       if (random.nextInt(i1 + 10) < 5 && !world.isRainingAt(i, j, k)) {
/* 198 */         int k1 = i1 + random.nextInt(5) / 4;
/*     */         
/* 200 */         if (k1 > 15) {
/* 201 */           k1 = 15;
/*     */         }
/*     */         
/* 204 */         world.setTypeAndData(i, j, k, this, k1, 3);
/*     */       } else {
/* 206 */         world.setAir(i, j, k);
/*     */       } 
/*     */       
/* 209 */       if (flag) {
/* 210 */         Blocks.TNT.postBreak(world, i, j, k, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean e(World world, int i, int j, int k) {
/* 216 */     return e(world, i + 1, j, k) ? true : (e(world, i - 1, j, k) ? true : (e(world, i, j - 1, k) ? true : (e(world, i, j + 1, k) ? true : (e(world, i, j, k - 1) ? true : e(world, i, j, k + 1)))));
/*     */   }
/*     */   
/*     */   private int m(World world, int i, int j, int k) {
/* 220 */     byte b0 = 0;
/*     */     
/* 222 */     if (!world.isEmpty(i, j, k)) {
/* 223 */       return 0;
/*     */     }
/* 225 */     int l = a(world, i + 1, j, k, b0);
/*     */     
/* 227 */     l = a(world, i - 1, j, k, l);
/* 228 */     l = a(world, i, j - 1, k, l);
/* 229 */     l = a(world, i, j + 1, k, l);
/* 230 */     l = a(world, i, j, k - 1, l);
/* 231 */     l = a(world, i, j, k + 1, l);
/* 232 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean v() {
/* 237 */     return false;
/*     */   }
/*     */   
/*     */   public boolean e(IBlockAccess iblockaccess, int i, int j, int k) {
/* 241 */     return (this.a[Block.getId(iblockaccess.getType(i, j, k))] > 0);
/*     */   }
/*     */   
/*     */   public int a(World world, int i, int j, int k, int l) {
/* 245 */     int i1 = this.a[Block.getId(world.getType(i, j, k))];
/*     */     
/* 247 */     return (i1 > l) ? i1 : l;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/* 251 */     return (World.a(world, i, j - 1, k) || e(world, i, j, k));
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 255 */     if (!World.a(world, i, j - 1, k) && !e(world, i, j, k)) {
/* 256 */       fireExtinguished(world, i, j, k);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/* 261 */     if (world.worldProvider.dimension > 0 || !Blocks.PORTAL.e(world, i, j, k)) {
/* 262 */       if (!World.a(world, i, j - 1, k) && !e(world, i, j, k)) {
/* 263 */         fireExtinguished(world, i, j, k);
/*     */       } else {
/* 265 */         world.a(i, j, k, this, a(world) + world.random.nextInt(10));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public MaterialMapColor f(int i) {
/* 271 */     return MaterialMapColor.f;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fireExtinguished(World world, int x, int y, int z) {
/* 276 */     if (!CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(x, y, z), Blocks.AIR).isCancelled())
/* 277 */       world.setAir(x, y, z); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockFire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */