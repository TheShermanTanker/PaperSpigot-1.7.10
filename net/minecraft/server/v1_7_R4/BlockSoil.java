/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ 
/*     */ public class BlockSoil extends Block {
/*     */   protected BlockSoil() {
/*  13 */     super(Material.EARTH);
/*  14 */     a(true);
/*  15 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
/*  16 */     g(255);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  20 */     return AxisAlignedBB.a((i + 0), (j + 0), (k + 0), (i + 1), (j + 1), (k + 1));
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  24 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  28 */     return false;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  32 */     if (!m(world, i, j, k) && !world.isRainingAt(i, j + 1, k)) {
/*  33 */       int l = world.getData(i, j, k);
/*     */       
/*  35 */       if (l > 0) {
/*  36 */         world.setData(i, j, k, l - 1, 2);
/*  37 */       } else if (!e(world, i, j, k)) {
/*     */         
/*  39 */         Block block = world.getWorld().getBlockAt(i, j, k);
/*  40 */         if (CraftEventFactory.callBlockFadeEvent(block, Blocks.DIRT).isCancelled()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  45 */         world.setTypeUpdate(i, j, k, Blocks.DIRT);
/*     */       } 
/*     */     } else {
/*  48 */       world.setData(i, j, k, 7, 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity, float f) {
/*  53 */     if (!world.isStatic && world.random.nextFloat() < f - 0.5F) {
/*  54 */       EntityInteractEvent entityInteractEvent; if (!(entity instanceof EntityHuman) && !world.getGameRules().getBoolean("mobGriefing")) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  60 */       if (entity instanceof EntityHuman) {
/*  61 */         PlayerInteractEvent playerInteractEvent = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, i, j, k, -1, null);
/*     */       } else {
/*  63 */         entityInteractEvent = new EntityInteractEvent((Entity)entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
/*  64 */         world.getServer().getPluginManager().callEvent((Event)entityInteractEvent);
/*     */       } 
/*     */       
/*  67 */       if (entityInteractEvent.isCancelled()) {
/*     */         return;
/*     */       }
/*     */       
/*  71 */       if (CraftEventFactory.callEntityChangeBlockEvent(entity, i, j, k, Blocks.DIRT, 0).isCancelled()) {
/*     */         return;
/*     */       }
/*     */       
/*  75 */       world.setTypeUpdate(i, j, k, Blocks.DIRT);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean e(World world, int i, int j, int k) {
/*  80 */     byte b0 = 0;
/*     */     
/*  82 */     for (int l = i - b0; l <= i + b0; l++) {
/*  83 */       for (int i1 = k - b0; i1 <= k + b0; i1++) {
/*  84 */         Block block = world.getType(l, j + 1, i1);
/*     */         
/*  86 */         if (block == Blocks.CROPS || block == Blocks.MELON_STEM || block == Blocks.PUMPKIN_STEM || block == Blocks.POTATOES || block == Blocks.CARROTS) {
/*  87 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   private boolean m(World world, int i, int j, int k) {
/*  96 */     for (int l = i - 4; l <= i + 4; l++) {
/*  97 */       for (int i1 = j; i1 <= j + 1; i1++) {
/*  98 */         for (int j1 = k - 4; j1 <= k + 4; j1++) {
/*  99 */           if (world.getType(l, i1, j1).getMaterial() == Material.WATER) {
/* 100 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 110 */     super.doPhysics(world, i, j, k, block);
/* 111 */     Material material = world.getType(i, j + 1, k).getMaterial();
/*     */     
/* 113 */     if (material.isBuildable()) {
/* 114 */       world.setTypeUpdate(i, j, k, Blocks.DIRT);
/*     */     }
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 119 */     return Blocks.DIRT.getDropType(0, random, j);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockSoil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */