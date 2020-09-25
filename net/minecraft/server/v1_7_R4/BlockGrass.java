/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockFadeEvent;
/*     */ import org.bukkit.event.block.BlockSpreadEvent;
/*     */ 
/*     */ public class BlockGrass
/*     */   extends Block
/*     */   implements IBlockFragilePlantElement
/*     */ {
/*  18 */   private static final Logger a = LogManager.getLogger();
/*     */   
/*     */   protected BlockGrass() {
/*  21 */     super(Material.GRASS);
/*  22 */     a(true);
/*  23 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  27 */     if (!world.isStatic) {
/*  28 */       if (world.getLightLevel(i, j + 1, k) < 4 && world.getType(i, j + 1, k).k() > 2) {
/*     */         
/*  30 */         CraftWorld craftWorld = world.getWorld();
/*  31 */         BlockState blockState = craftWorld.getBlockAt(i, j, k).getState();
/*  32 */         blockState.setType(CraftMagicNumbers.getMaterial(Blocks.DIRT));
/*     */         
/*  34 */         BlockFadeEvent event = new BlockFadeEvent(blockState.getBlock(), blockState);
/*  35 */         world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/*  37 */         if (!event.isCancelled()) {
/*  38 */           blockState.update(true);
/*     */         }
/*     */       }
/*  41 */       else if (world.getLightLevel(i, j + 1, k) >= 9) {
/*  42 */         int numGrowth = Math.min(4, Math.max(20, (int)(400.0F / world.growthOdds)));
/*  43 */         for (int l = 0; l < numGrowth; l++) {
/*  44 */           int i1 = i + random.nextInt(3) - 1;
/*  45 */           int j1 = j + random.nextInt(5) - 3;
/*  46 */           int k1 = k + random.nextInt(3) - 1;
/*  47 */           Block block = world.getType(i1, j1 + 1, k1);
/*     */           
/*  49 */           if (world.getType(i1, j1, k1) == Blocks.DIRT && world.getData(i1, j1, k1) == 0 && world.getLightLevel(i1, j1 + 1, k1) >= 4 && block.k() <= 2) {
/*     */             
/*  51 */             CraftWorld craftWorld = world.getWorld();
/*  52 */             BlockState blockState = craftWorld.getBlockAt(i1, j1, k1).getState();
/*  53 */             blockState.setType(CraftMagicNumbers.getMaterial(Blocks.GRASS));
/*     */             
/*  55 */             BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), craftWorld.getBlockAt(i, j, k), blockState);
/*  56 */             world.getServer().getPluginManager().callEvent((Event)event);
/*     */             
/*  58 */             if (!event.isCancelled()) {
/*  59 */               blockState.update(true);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/*  69 */     return Blocks.DIRT.getDropType(0, random, j);
/*     */   }
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, boolean flag) {
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, int i, int j, int k) {
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, int i, int j, int k) {
/*  81 */     int l = 0;
/*     */     
/*  83 */     label26: while (l < 128) {
/*  84 */       int i1 = i;
/*  85 */       int j1 = j + 1;
/*  86 */       int k1 = k;
/*  87 */       int l1 = 0;
/*     */ 
/*     */       
/*  90 */       while (l1 < l / 16) {
/*  91 */         i1 += random.nextInt(3) - 1;
/*  92 */         j1 += (random.nextInt(3) - 1) * random.nextInt(3) / 2;
/*  93 */         k1 += random.nextInt(3) - 1;
/*  94 */         if (world.getType(i1, j1 - 1, k1) == Blocks.GRASS) { if (!world.getType(i1, j1, k1).r()) {
/*  95 */             l1++; continue;
/*     */           }  continue label26; }
/*     */          continue label26;
/*  98 */       }  if ((world.getType(i1, j1, k1)).material == Material.AIR) {
/*  99 */         if (random.nextInt(8) != 0) {
/* 100 */           if (Blocks.LONG_GRASS.j(world, i1, j1, k1)) {
/* 101 */             CraftEventFactory.handleBlockGrowEvent(world, i1, j1, k1, Blocks.LONG_GRASS, 1);
/*     */           }
/*     */         } else {
/* 104 */           String s = world.getBiome(i1, k1).a(random, i1, j1, k1);
/*     */           
/* 106 */           a.debug("Flower in " + (world.getBiome(i1, k1)).af + ": " + s);
/* 107 */           BlockFlowers blockflowers = BlockFlowers.e(s);
/*     */           
/* 109 */           if (blockflowers != null && blockflowers.j(world, i1, j1, k1)) {
/* 110 */             int i2 = BlockFlowers.f(s);
/*     */             
/* 112 */             CraftEventFactory.handleBlockGrowEvent(world, i1, j1, k1, blockflowers, i2);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 117 */       l++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockGrass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */