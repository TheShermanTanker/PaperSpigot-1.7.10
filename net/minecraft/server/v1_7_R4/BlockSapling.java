/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.TreeType;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.world.StructureGrowEvent;
/*     */ 
/*     */ public class BlockSapling
/*     */   extends BlockPlant
/*     */   implements IBlockFragilePlantElement {
/*  16 */   public static final String[] a = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "roofed_oak" };
/*  17 */   private static final IIcon[] b = new IIcon[a.length];
/*     */   public static TreeType treeType;
/*     */   
/*     */   protected BlockSapling() {
/*  21 */     float f = 0.4F;
/*     */     
/*  23 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*  24 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  28 */     if (!world.isStatic) {
/*  29 */       super.a(world, i, j, k, random);
/*  30 */       if (world.getLightLevel(i, j + 1, k) >= 9 && random.nextInt(Math.max(2, (int)(world.growthOdds / world.spigotConfig.saplingModifier * 7.0F + 0.5F))) == 0) {
/*     */         
/*  32 */         world.captureTreeGeneration = true;
/*     */         
/*  34 */         grow(world, i, j, k, random);
/*     */         
/*  36 */         world.captureTreeGeneration = false;
/*  37 */         if (world.capturedBlockStates.size() > 0) {
/*  38 */           TreeType treeType = BlockSapling.treeType;
/*  39 */           BlockSapling.treeType = null;
/*  40 */           Location location = new Location((World)world.getWorld(), i, j, k);
/*  41 */           List<BlockState> blocks = (List<BlockState>)world.capturedBlockStates.clone();
/*  42 */           world.capturedBlockStates.clear();
/*  43 */           StructureGrowEvent event = null;
/*  44 */           if (treeType != null) {
/*  45 */             event = new StructureGrowEvent(location, treeType, false, null, blocks);
/*  46 */             Bukkit.getPluginManager().callEvent((Event)event);
/*     */           } 
/*  48 */           if (event == null || !event.isCancelled()) {
/*  49 */             for (BlockState blockstate : blocks) {
/*  50 */               blockstate.update(true);
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World world, int i, int j, int k, Random random) {
/*  60 */     int l = world.getData(i, j, k);
/*     */     
/*  62 */     if ((l & 0x8) == 0) {
/*  63 */       world.setData(i, j, k, l | 0x8, 4);
/*     */     } else {
/*  65 */       d(world, i, j, k, random);
/*     */     }  } public void d(World world, int i, int j, int k, Random random) {
/*     */     WorldGenForest worldGenForest;
/*     */     WorldGenAcaciaTree worldGenAcaciaTree;
/*     */     Object object;
/*  70 */     int l = world.getData(i, j, k) & 0x7;
/*     */ 
/*     */ 
/*     */     
/*  74 */     if (random.nextInt(10) == 0) {
/*  75 */       treeType = TreeType.BIG_TREE;
/*  76 */       object = new WorldGenBigTree(true);
/*     */     } else {
/*  78 */       treeType = TreeType.TREE;
/*  79 */       object = new WorldGenTrees(true);
/*     */     } 
/*     */     
/*  82 */     int i1 = 0;
/*  83 */     int j1 = 0;
/*  84 */     boolean flag = false;
/*     */     
/*  86 */     switch (l) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*  93 */         label79: for (i1 = 0; i1 >= -1; i1--) {
/*  94 */           for (j1 = 0; j1 >= -1; j1--) {
/*  95 */             if (a(world, i + i1, j, k + j1, 1) && a(world, i + i1 + 1, j, k + j1, 1) && a(world, i + i1, j, k + j1 + 1, 1) && a(world, i + i1 + 1, j, k + j1 + 1, 1)) {
/*  96 */               treeType = TreeType.MEGA_REDWOOD;
/*  97 */               object = new WorldGenMegaTree(false, random.nextBoolean());
/*  98 */               flag = true;
/*     */               
/*     */               break label79;
/*     */             } 
/*     */           } 
/*     */         } 
/* 104 */         if (!flag) {
/* 105 */           j1 = 0;
/* 106 */           i1 = 0;
/* 107 */           treeType = TreeType.REDWOOD;
/* 108 */           object = new WorldGenTaiga2(true);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 2:
/* 113 */         treeType = TreeType.BIRCH;
/* 114 */         worldGenForest = new WorldGenForest(true, false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 119 */         label80: for (i1 = 0; i1 >= -1; i1--) {
/* 120 */           for (j1 = 0; j1 >= -1; j1--) {
/* 121 */             if (a(world, i + i1, j, k + j1, 3) && a(world, i + i1 + 1, j, k + j1, 3) && a(world, i + i1, j, k + j1 + 1, 3) && a(world, i + i1 + 1, j, k + j1 + 1, 3)) {
/* 122 */               treeType = TreeType.JUNGLE;
/* 123 */               object = new WorldGenJungleTree(true, 10, 20, 3, 3);
/* 124 */               flag = true;
/*     */               
/*     */               break label80;
/*     */             } 
/*     */           } 
/*     */         } 
/* 130 */         if (!flag) {
/* 131 */           j1 = 0;
/* 132 */           i1 = 0;
/* 133 */           treeType = TreeType.SMALL_JUNGLE;
/* 134 */           object = new WorldGenTrees(true, 4 + random.nextInt(7), 3, 3, false);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 4:
/* 139 */         treeType = TreeType.ACACIA;
/* 140 */         worldGenAcaciaTree = new WorldGenAcaciaTree(true);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 145 */         label81: for (i1 = 0; i1 >= -1; i1--) {
/* 146 */           for (j1 = 0; j1 >= -1; j1--) {
/* 147 */             if (a(world, i + i1, j, k + j1, 5) && a(world, i + i1 + 1, j, k + j1, 5) && a(world, i + i1, j, k + j1 + 1, 5) && a(world, i + i1 + 1, j, k + j1 + 1, 5)) {
/* 148 */               object = new WorldGenForestTree(true);
/* 149 */               treeType = TreeType.DARK_OAK;
/* 150 */               flag = true;
/*     */               
/*     */               break label81;
/*     */             } 
/*     */           } 
/*     */         } 
/* 156 */         if (!flag) {
/*     */           return;
/*     */         }
/*     */         break;
/*     */     } 
/* 161 */     Block block = Blocks.AIR;
/*     */     
/* 163 */     if (flag) {
/* 164 */       world.setTypeAndData(i + i1, j, k + j1, block, 0, 4);
/* 165 */       world.setTypeAndData(i + i1 + 1, j, k + j1, block, 0, 4);
/* 166 */       world.setTypeAndData(i + i1, j, k + j1 + 1, block, 0, 4);
/* 167 */       world.setTypeAndData(i + i1 + 1, j, k + j1 + 1, block, 0, 4);
/*     */     } else {
/* 169 */       world.setTypeAndData(i, j, k, block, 0, 4);
/*     */     } 
/*     */     
/* 172 */     if (!((WorldGenerator)object).generate(world, random, i + i1, j, k + j1)) {
/* 173 */       if (flag) {
/* 174 */         world.setTypeAndData(i + i1, j, k + j1, this, l, 4);
/* 175 */         world.setTypeAndData(i + i1 + 1, j, k + j1, this, l, 4);
/* 176 */         world.setTypeAndData(i + i1, j, k + j1 + 1, this, l, 4);
/* 177 */         world.setTypeAndData(i + i1 + 1, j, k + j1 + 1, this, l, 4);
/*     */       } else {
/* 179 */         world.setTypeAndData(i, j, k, this, l, 4);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, int l) {
/* 185 */     return (world.getType(i, j, k) == this && (world.getData(i, j, k) & 0x7) == l);
/*     */   }
/*     */   
/*     */   public int getDropData(int i) {
/* 189 */     return MathHelper.a(i & 0x7, 0, 5);
/*     */   }
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, boolean flag) {
/* 193 */     return true;
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, int i, int j, int k) {
/* 197 */     return (world.random.nextFloat() < 0.45D);
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, int i, int j, int k) {
/* 201 */     grow(world, i, j, k, random);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockSapling.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */