/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Random;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityPortalEnterEvent;
/*     */ 
/*     */ public class BlockPortal extends BlockHalfTransparent {
/*   9 */   public static final int[][] a = new int[][] { {}, { 3, 1 }, { 2, 0 } };
/*     */   
/*     */   public BlockPortal() {
/*  12 */     super("portal", Material.PORTAL, false);
/*  13 */     a(true);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  17 */     super.a(world, i, j, k, random);
/*  18 */     if (world.spigotConfig.enableZombiePigmenPortalSpawns && world.worldProvider.d() && world.getGameRules().getBoolean("doMobSpawning") && random.nextInt(2000) < world.difficulty.a()) {
/*     */       int l;
/*     */       
/*  21 */       for (l = j; !World.a(world, i, l, k) && l > 0; l--);
/*     */ 
/*     */ 
/*     */       
/*  25 */       if (l > 0 && !world.getType(i, l + 1, k).r()) {
/*     */         
/*  27 */         Entity entity = ItemMonsterEgg.spawnCreature(world, 57, i + 0.5D, l + 1.1D, k + 0.5D, CreatureSpawnEvent.SpawnReason.NETHER_PORTAL);
/*     */         
/*  29 */         if (entity != null) {
/*  30 */           entity.portalCooldown = entity.ai();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  37 */     return null;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  41 */     int l = b(iblockaccess.getData(i, j, k));
/*     */     
/*  43 */     if (l == 0) {
/*  44 */       if (iblockaccess.getType(i - 1, j, k) != this && iblockaccess.getType(i + 1, j, k) != this) {
/*  45 */         l = 2;
/*     */       } else {
/*  47 */         l = 1;
/*     */       } 
/*     */       
/*  50 */       if (iblockaccess instanceof World && !((World)iblockaccess).isStatic) {
/*  51 */         ((World)iblockaccess).setData(i, j, k, l, 2);
/*     */       }
/*     */     } 
/*     */     
/*  55 */     float f = 0.125F;
/*  56 */     float f1 = 0.125F;
/*     */     
/*  58 */     if (l == 1) {
/*  59 */       f = 0.5F;
/*     */     }
/*     */     
/*  62 */     if (l == 2) {
/*  63 */       f1 = 0.5F;
/*     */     }
/*     */     
/*  66 */     a(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  70 */     return false;
/*     */   }
/*     */   
/*     */   public boolean e(World world, int i, int j, int k) {
/*  74 */     PortalCreator portalcreator = new PortalCreator(world, i, j, k, 1);
/*  75 */     PortalCreator portalcreator1 = new PortalCreator(world, i, j, k, 2);
/*     */     
/*  77 */     if (portalcreator.b() && PortalCreator.a(portalcreator) == 0)
/*     */     {
/*  79 */       return portalcreator.c();
/*     */     }
/*  81 */     if (portalcreator1.b() && PortalCreator.a(portalcreator1) == 0) {
/*  82 */       return portalcreator1.c();
/*     */     }
/*     */ 
/*     */     
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  91 */     int l = b(world.getData(i, j, k));
/*  92 */     PortalCreator portalcreator = new PortalCreator(world, i, j, k, 1);
/*  93 */     PortalCreator portalcreator1 = new PortalCreator(world, i, j, k, 2);
/*     */     
/*  95 */     if (l == 1 && (!portalcreator.b() || PortalCreator.a(portalcreator) < PortalCreator.b(portalcreator) * PortalCreator.c(portalcreator))) {
/*  96 */       world.setTypeUpdate(i, j, k, Blocks.AIR);
/*  97 */     } else if (l == 2 && (!portalcreator1.b() || PortalCreator.a(portalcreator1) < PortalCreator.b(portalcreator1) * PortalCreator.c(portalcreator1))) {
/*  98 */       world.setTypeUpdate(i, j, k, Blocks.AIR);
/*  99 */     } else if (l == 0 && !portalcreator.b() && !portalcreator1.b()) {
/* 100 */       world.setTypeUpdate(i, j, k, Blocks.AIR);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/* 105 */     return 0;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity) {
/* 109 */     if (entity.vehicle == null && entity.passenger == null) {
/*     */       
/* 111 */       EntityPortalEnterEvent event = new EntityPortalEnterEvent((Entity)entity.getBukkitEntity(), new Location((World)world.getWorld(), i, j, k));
/* 112 */       world.getServer().getPluginManager().callEvent((Event)event);
/*     */ 
/*     */       
/* 115 */       entity.ah();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int b(int i) {
/* 120 */     return i & 0x3;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPortal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */