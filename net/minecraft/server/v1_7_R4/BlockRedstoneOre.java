/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ 
/*     */ public class BlockRedstoneOre extends Block {
/*     */   private boolean a;
/*     */   
/*     */   public BlockRedstoneOre(boolean flag) {
/*  15 */     super(Material.STONE);
/*  16 */     if (flag) {
/*  17 */       a(true);
/*     */     }
/*     */     
/*  20 */     this.a = flag;
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  24 */     return 30;
/*     */   }
/*     */   
/*     */   public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {
/*  28 */     e(world, i, j, k, entityhuman);
/*  29 */     super.attack(world, i, j, k, entityhuman);
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(World world, int i, int j, int k, Entity entity) {
/*  34 */     if (entity instanceof EntityHuman) {
/*  35 */       PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, i, j, k, -1, null);
/*  36 */       if (!event.isCancelled()) {
/*  37 */         e(world, i, j, k, entity);
/*  38 */         super.b(world, i, j, k, entity);
/*     */       } 
/*     */     } else {
/*  41 */       EntityInteractEvent event = new EntityInteractEvent((Entity)entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
/*  42 */       world.getServer().getPluginManager().callEvent((Event)event);
/*  43 */       if (!event.isCancelled()) {
/*  44 */         e(world, i, j, k, entity);
/*  45 */         super.b(world, i, j, k, entity);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/*  52 */     e(world, i, j, k, entityhuman);
/*  53 */     return super.interact(world, i, j, k, entityhuman, l, f, f1, f2);
/*     */   }
/*     */   
/*     */   private void e(World world, int i, int j, int k, Entity entity) {
/*  57 */     m(world, i, j, k);
/*  58 */     if (this == Blocks.REDSTONE_ORE) {
/*     */       
/*  60 */       if (CraftEventFactory.callEntityChangeBlockEvent(entity, i, j, k, Blocks.GLOWING_REDSTONE_ORE, 0).isCancelled()) {
/*     */         return;
/*     */       }
/*     */       
/*  64 */       world.setTypeUpdate(i, j, k, Blocks.GLOWING_REDSTONE_ORE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  69 */     if (this == Blocks.GLOWING_REDSTONE_ORE) {
/*     */       
/*  71 */       if (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), Blocks.REDSTONE_ORE).isCancelled()) {
/*     */         return;
/*     */       }
/*     */       
/*  75 */       world.setTypeUpdate(i, j, k, Blocks.REDSTONE_ORE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/*  80 */     return Items.REDSTONE;
/*     */   }
/*     */   
/*     */   public int getDropCount(int i, Random random) {
/*  84 */     return a(random) + random.nextInt(i + 1);
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/*  88 */     return 4 + random.nextInt(2);
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/*  92 */     super.dropNaturally(world, i, j, k, l, f, i1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExpDrop(World world, int l, int i1) {
/* 103 */     if (getDropType(l, world.random, i1) != Item.getItemOf(this)) {
/* 104 */       int j1 = 1 + world.random.nextInt(5);
/*     */       
/* 106 */       return j1;
/*     */     } 
/*     */     
/* 109 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private void m(World world, int i, int j, int k) {
/* 114 */     Random random = world.random;
/* 115 */     double d0 = 0.0625D;
/*     */     
/* 117 */     for (int l = 0; l < 6; l++) {
/* 118 */       double d1 = (i + random.nextFloat());
/* 119 */       double d2 = (j + random.nextFloat());
/* 120 */       double d3 = (k + random.nextFloat());
/*     */       
/* 122 */       if (l == 0 && !world.getType(i, j + 1, k).c()) {
/* 123 */         d2 = (j + 1) + d0;
/*     */       }
/*     */       
/* 126 */       if (l == 1 && !world.getType(i, j - 1, k).c()) {
/* 127 */         d2 = (j + 0) - d0;
/*     */       }
/*     */       
/* 130 */       if (l == 2 && !world.getType(i, j, k + 1).c()) {
/* 131 */         d3 = (k + 1) + d0;
/*     */       }
/*     */       
/* 134 */       if (l == 3 && !world.getType(i, j, k - 1).c()) {
/* 135 */         d3 = (k + 0) - d0;
/*     */       }
/*     */       
/* 138 */       if (l == 4 && !world.getType(i + 1, j, k).c()) {
/* 139 */         d1 = (i + 1) + d0;
/*     */       }
/*     */       
/* 142 */       if (l == 5 && !world.getType(i - 1, j, k).c()) {
/* 143 */         d1 = (i + 0) - d0;
/*     */       }
/*     */       
/* 146 */       if (d1 < i || d1 > (i + 1) || d2 < 0.0D || d2 > (j + 1) || d3 < k || d3 > (k + 1)) {
/* 147 */         world.addParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ItemStack j(int i) {
/* 153 */     return new ItemStack(Blocks.REDSTONE_ORE);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockRedstoneOre.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */