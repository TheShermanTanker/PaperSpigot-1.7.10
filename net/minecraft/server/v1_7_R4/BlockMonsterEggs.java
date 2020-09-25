/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.util.org.apache.commons.lang3.tuple.ImmutablePair;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ 
/*     */ public class BlockMonsterEggs
/*     */   extends Block
/*     */ {
/*  11 */   public static final String[] a = new String[] { "stone", "cobble", "brick", "mossybrick", "crackedbrick", "chiseledbrick" };
/*     */   
/*     */   public BlockMonsterEggs() {
/*  14 */     super(Material.CLAY);
/*  15 */     c(0.0F);
/*  16 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public void postBreak(World world, int i, int j, int k, int l) {
/*  20 */     if (!world.isStatic) {
/*  21 */       EntitySilverfish entitysilverfish = new EntitySilverfish(world);
/*     */       
/*  23 */       entitysilverfish.setPositionRotation(i + 0.5D, j, k + 0.5D, 0.0F, 0.0F);
/*  24 */       world.addEntity(entitysilverfish, CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK);
/*  25 */       entitysilverfish.s();
/*     */     } 
/*     */     
/*  28 */     super.postBreak(world, i, j, k, l);
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/*  32 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean a(Block block) {
/*  36 */     return (block == Blocks.STONE || block == Blocks.COBBLESTONE || block == Blocks.SMOOTH_BRICK);
/*     */   }
/*     */   
/*     */   public static int a(Block block, int i) {
/*  40 */     if (i == 0) {
/*  41 */       if (block == Blocks.COBBLESTONE) {
/*  42 */         return 1;
/*     */       }
/*     */       
/*  45 */       if (block == Blocks.SMOOTH_BRICK) {
/*  46 */         return 2;
/*     */       }
/*  48 */     } else if (block == Blocks.SMOOTH_BRICK) {
/*  49 */       switch (i) {
/*     */         case 1:
/*  51 */           return 3;
/*     */         
/*     */         case 2:
/*  54 */           return 4;
/*     */         
/*     */         case 3:
/*  57 */           return 5;
/*     */       } 
/*     */     
/*     */     } 
/*  61 */     return 0;
/*     */   }
/*     */   
/*     */   public static ImmutablePair b(int i) {
/*  65 */     switch (i) {
/*     */       case 1:
/*  67 */         return new ImmutablePair(Blocks.COBBLESTONE, Integer.valueOf(0));
/*     */       
/*     */       case 2:
/*  70 */         return new ImmutablePair(Blocks.SMOOTH_BRICK, Integer.valueOf(0));
/*     */       
/*     */       case 3:
/*  73 */         return new ImmutablePair(Blocks.SMOOTH_BRICK, Integer.valueOf(1));
/*     */       
/*     */       case 4:
/*  76 */         return new ImmutablePair(Blocks.SMOOTH_BRICK, Integer.valueOf(2));
/*     */       
/*     */       case 5:
/*  79 */         return new ImmutablePair(Blocks.SMOOTH_BRICK, Integer.valueOf(3));
/*     */     } 
/*     */     
/*  82 */     return new ImmutablePair(Blocks.STONE, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack j(int i) {
/*  87 */     switch (i) {
/*     */       case 1:
/*  89 */         return new ItemStack(Blocks.COBBLESTONE);
/*     */       
/*     */       case 2:
/*  92 */         return new ItemStack(Blocks.SMOOTH_BRICK);
/*     */       
/*     */       case 3:
/*  95 */         return new ItemStack(Blocks.SMOOTH_BRICK, 1, 1);
/*     */       
/*     */       case 4:
/*  98 */         return new ItemStack(Blocks.SMOOTH_BRICK, 1, 2);
/*     */       
/*     */       case 5:
/* 101 */         return new ItemStack(Blocks.SMOOTH_BRICK, 1, 3);
/*     */     } 
/*     */     
/* 104 */     return new ItemStack(Blocks.STONE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/* 109 */     if (!world.isStatic) {
/* 110 */       EntitySilverfish entitysilverfish = new EntitySilverfish(world);
/*     */       
/* 112 */       entitysilverfish.setPositionRotation(i + 0.5D, j, k + 0.5D, 0.0F, 0.0F);
/* 113 */       world.addEntity(entitysilverfish, CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK);
/* 114 */       entitysilverfish.s();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getDropData(World world, int i, int j, int k) {
/* 119 */     return world.getData(i, j, k);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockMonsterEggs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */