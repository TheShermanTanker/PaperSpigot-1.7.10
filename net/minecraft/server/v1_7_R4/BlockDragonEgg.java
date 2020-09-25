/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockFromToEvent;
/*     */ 
/*     */ public class BlockDragonEgg extends Block {
/*     */   public BlockDragonEgg() {
/*  10 */     super(Material.DRAGON_EGG);
/*  11 */     a(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/*  15 */     world.a(i, j, k, this, a(world));
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  19 */     world.a(i, j, k, this, a(world));
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  23 */     e(world, i, j, k);
/*     */   }
/*     */   
/*     */   private void e(World world, int i, int j, int k) {
/*  27 */     if (BlockFalling.canFall(world, i, j - 1, k) && j >= 0) {
/*  28 */       byte b0 = 32;
/*     */       
/*  30 */       if (!BlockFalling.instaFall && world.b(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
/*     */ 
/*     */         
/*  33 */         Location loc = new Location((World)world.getWorld(), (i + 0.5F), (j + 0.5F), (k + 0.5F));
/*  34 */         EntityFallingBlock entityfallingblock = new EntityFallingBlock(loc, world, (i + 0.5F), (j + 0.5F), (k + 0.5F), this, world.getData(i, j, k));
/*     */ 
/*     */         
/*  37 */         world.addEntity(entityfallingblock);
/*     */       } else {
/*  39 */         world.setAir(i, j, k);
/*     */         
/*  41 */         while (BlockFalling.canFall(world, i, j - 1, k) && j > 0) {
/*  42 */           j--;
/*     */         }
/*     */         
/*  45 */         if (j > 0) {
/*  46 */           world.setTypeAndData(i, j, k, this, 0, 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/*  53 */     m(world, i, j, k);
/*  54 */     return true;
/*     */   }
/*     */   
/*     */   public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {
/*  58 */     m(world, i, j, k);
/*     */   }
/*     */   
/*     */   private void m(World world, int i, int j, int k) {
/*  62 */     if (world.getType(i, j, k) == this) {
/*  63 */       for (int l = 0; l < 1000; l++) {
/*  64 */         int i1 = i + world.random.nextInt(16) - world.random.nextInt(16);
/*  65 */         int j1 = j + world.random.nextInt(8) - world.random.nextInt(8);
/*  66 */         int k1 = k + world.random.nextInt(16) - world.random.nextInt(16);
/*     */         
/*  68 */         if ((world.getType(i1, j1, k1)).material == Material.AIR) {
/*     */           
/*  70 */           Block from = world.getWorld().getBlockAt(i, j, k);
/*  71 */           Block to = world.getWorld().getBlockAt(i1, j1, k1);
/*  72 */           BlockFromToEvent event = new BlockFromToEvent(from, to);
/*  73 */           Bukkit.getPluginManager().callEvent((Event)event);
/*     */           
/*  75 */           if (event.isCancelled()) {
/*     */             return;
/*     */           }
/*     */           
/*  79 */           i1 = event.getToBlock().getX();
/*  80 */           j1 = event.getToBlock().getY();
/*  81 */           k1 = event.getToBlock().getZ();
/*     */ 
/*     */           
/*  84 */           if (!world.isStatic) {
/*  85 */             world.setTypeAndData(i1, j1, k1, this, world.getData(i, j, k), 2);
/*  86 */             world.setAir(i, j, k);
/*     */           } else {
/*  88 */             short short1 = 128;
/*     */             
/*  90 */             for (int l1 = 0; l1 < short1; l1++) {
/*  91 */               double d0 = world.random.nextDouble();
/*  92 */               float f = (world.random.nextFloat() - 0.5F) * 0.2F;
/*  93 */               float f1 = (world.random.nextFloat() - 0.5F) * 0.2F;
/*  94 */               float f2 = (world.random.nextFloat() - 0.5F) * 0.2F;
/*  95 */               double d1 = i1 + (i - i1) * d0 + (world.random.nextDouble() - 0.5D) * 1.0D + 0.5D;
/*  96 */               double d2 = j1 + (j - j1) * d0 + world.random.nextDouble() * 1.0D - 0.5D;
/*  97 */               double d3 = k1 + (k - k1) * d0 + (world.random.nextDouble() - 0.5D) * 1.0D + 0.5D;
/*     */               
/*  99 */               world.addParticle("portal", d1, d2, d3, f, f1, f2);
/*     */             } 
/*     */           } 
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int a(World world) {
/* 110 */     return 5;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 114 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/* 122 */     return 27;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockDragonEgg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */