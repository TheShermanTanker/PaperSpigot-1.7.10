/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.TreeType;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockSpreadEvent;
/*     */ 
/*     */ public class BlockMushroom
/*     */   extends BlockPlant implements IBlockFragilePlantElement {
/*     */   protected BlockMushroom() {
/*  14 */     float f = 0.2F;
/*     */     
/*  16 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*  17 */     a(true);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  21 */     int sourceX = i, sourceY = j, sourceZ = k;
/*  22 */     if (random.nextInt(Math.max(1, (int)world.growthOdds / world.spigotConfig.mushroomModifier * 25)) == 0) {
/*  23 */       byte b0 = 4;
/*  24 */       int l = 5;
/*     */ 
/*     */       
/*     */       int i1;
/*     */ 
/*     */       
/*  30 */       for (i1 = i - b0; i1 <= i + b0; i1++) {
/*  31 */         for (int m = k - b0; m <= k + b0; m++) {
/*  32 */           for (int n = j - 1; n <= j + 1; n++) {
/*     */             
/*  34 */             l--;
/*  35 */             if (world.getType(i1, n, m) == this && l <= 0) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  43 */       i1 = i + random.nextInt(3) - 1;
/*  44 */       int j1 = j + random.nextInt(2) - random.nextInt(2);
/*  45 */       int k1 = k + random.nextInt(3) - 1;
/*     */       
/*  47 */       for (int l1 = 0; l1 < 4; l1++) {
/*  48 */         if (world.isEmpty(i1, j1, k1) && j(world, i1, j1, k1)) {
/*  49 */           i = i1;
/*  50 */           j = j1;
/*  51 */           k = k1;
/*     */         } 
/*     */         
/*  54 */         i1 = i + random.nextInt(3) - 1;
/*  55 */         j1 = j + random.nextInt(2) - random.nextInt(2);
/*  56 */         k1 = k + random.nextInt(3) - 1;
/*     */       } 
/*     */       
/*  59 */       if (world.isEmpty(i1, j1, k1) && j(world, i1, j1, k1)) {
/*     */         
/*  61 */         CraftWorld craftWorld = world.getWorld();
/*  62 */         BlockState blockState = craftWorld.getBlockAt(i1, j1, k1).getState();
/*  63 */         blockState.setType(CraftMagicNumbers.getMaterial(this));
/*     */         
/*  65 */         BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), craftWorld.getBlockAt(sourceX, sourceY, sourceZ), blockState);
/*  66 */         world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/*  68 */         if (!event.isCancelled()) {
/*  69 */           blockState.update(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  77 */     return (super.canPlace(world, i, j, k) && j(world, i, j, k));
/*     */   }
/*     */   
/*     */   protected boolean a(Block block) {
/*  81 */     return block.j();
/*     */   }
/*     */   
/*     */   public boolean j(World world, int i, int j, int k) {
/*  85 */     if (j >= 0 && j < 256) {
/*  86 */       Block block = world.getType(i, j - 1, k);
/*     */       
/*  88 */       return (block == Blocks.MYCEL || (block == Blocks.DIRT && world.getData(i, j - 1, k) == 2) || (world.j(i, j, k) < 13 && a(block)));
/*     */     } 
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean grow(World world, int i, int j, int k, Random random) {
/*  95 */     int l = world.getData(i, j, k);
/*  96 */     world.setAir(i, j, k);
/*  97 */     WorldGenHugeMushroom worldgenhugemushroom = null;
/*     */     
/*  99 */     if (this == Blocks.BROWN_MUSHROOM) {
/* 100 */       BlockSapling.treeType = TreeType.BROWN_MUSHROOM;
/* 101 */       worldgenhugemushroom = new WorldGenHugeMushroom(0);
/* 102 */     } else if (this == Blocks.RED_MUSHROOM) {
/* 103 */       BlockSapling.treeType = TreeType.RED_MUSHROOM;
/* 104 */       worldgenhugemushroom = new WorldGenHugeMushroom(1);
/*     */     } 
/*     */     
/* 107 */     if (worldgenhugemushroom != null && worldgenhugemushroom.generate(world, random, i, j, k)) {
/* 108 */       return true;
/*     */     }
/* 110 */     world.setTypeAndData(i, j, k, this, l, 3);
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, boolean flag) {
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, int i, int j, int k) {
/* 120 */     return (random.nextFloat() < 0.4D);
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, int i, int j, int k) {
/* 124 */     grow(world, i, j, k, random);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockMushroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */