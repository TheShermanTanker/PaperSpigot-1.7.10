/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockTNT extends Block {
/*    */   public BlockTNT() {
/*  8 */     super(Material.TNT);
/*  9 */     a(CreativeModeTab.d);
/*    */   }
/*    */   
/*    */   public void onPlace(World world, int i, int j, int k) {
/* 13 */     super.onPlace(world, i, j, k);
/* 14 */     if (world.isBlockIndirectlyPowered(i, j, k)) {
/* 15 */       postBreak(world, i, j, k, 1);
/* 16 */       world.setAir(i, j, k);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 21 */     if (world.isBlockIndirectlyPowered(i, j, k)) {
/* 22 */       postBreak(world, i, j, k, 1);
/* 23 */       world.setAir(i, j, k);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 28 */     return 1;
/*    */   }
/*    */   
/*    */   public void wasExploded(World world, int i, int j, int k, Explosion explosion) {
/* 32 */     if (!world.isStatic) {
/*    */       
/* 34 */       Location loc = (explosion.source instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)explosion.source).sourceLoc : new Location((World)world.getWorld(), (i + 0.5F), (j + 0.5F), (k + 0.5F));
/* 35 */       EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(loc, world, (i + 0.5F), (j + 0.5F), (k + 0.5F), explosion.c());
/*    */ 
/*    */       
/* 38 */       entitytntprimed.fuseTicks = world.random.nextInt(entitytntprimed.fuseTicks / 4) + entitytntprimed.fuseTicks / 8;
/* 39 */       world.addEntity(entitytntprimed);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void postBreak(World world, int i, int j, int k, int l) {
/* 44 */     a(world, i, j, k, l, (EntityLiving)null);
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, int l, EntityLiving entityliving) {
/* 48 */     if (!world.isStatic && (
/* 49 */       l & 0x1) == 1) {
/*    */       
/* 51 */       Location loc = new Location((World)world.getWorld(), (i + 0.5F), (j + 0.5F), (k + 0.5F));
/* 52 */       EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(loc, world, (i + 0.5F), (j + 0.5F), (k + 0.5F), entityliving);
/*    */ 
/*    */       
/* 55 */       world.addEntity(entitytntprimed);
/* 56 */       world.makeSound(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/* 62 */     if (entityhuman.bF() != null && entityhuman.bF().getItem() == Items.FLINT_AND_STEEL) {
/* 63 */       a(world, i, j, k, 1, entityhuman);
/* 64 */       world.setAir(i, j, k);
/* 65 */       entityhuman.bF().damage(1, entityhuman);
/* 66 */       return true;
/*    */     } 
/* 68 */     return super.interact(world, i, j, k, entityhuman, l, f, f1, f2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World world, int i, int j, int k, Entity entity) {
/* 73 */     if (entity instanceof EntityArrow && !world.isStatic) {
/* 74 */       EntityArrow entityarrow = (EntityArrow)entity;
/*    */       
/* 76 */       if (entityarrow.isBurning()) {
/*    */         
/* 78 */         if (CraftEventFactory.callEntityChangeBlockEvent(entityarrow, i, j, k, Blocks.AIR, 0).isCancelled()) {
/*    */           return;
/*    */         }
/*    */         
/* 82 */         a(world, i, j, k, 1, (entityarrow.shooter instanceof EntityLiving) ? (EntityLiving)entityarrow.shooter : null);
/* 83 */         world.setAir(i, j, k);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean a(Explosion explosion) {
/* 89 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockTNT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */