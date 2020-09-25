/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.EntityBlockFormEvent;
/*    */ 
/*    */ public class EntitySnowman extends EntityGolem implements IRangedEntity {
/*    */   public EntitySnowman(World world) {
/* 12 */     super(world);
/* 13 */     a(0.4F, 1.8F);
/* 14 */     getNavigation().a(true);
/* 15 */     this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 1.25D, 20, 10.0F));
/* 16 */     this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
/* 17 */     this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/* 18 */     this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
/* 19 */     this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityInsentient.class, 0, true, false, IMonster.a));
/*    */   }
/*    */   
/*    */   public boolean bk() {
/* 23 */     return true;
/*    */   }
/*    */   
/*    */   protected void aD() {
/* 27 */     super.aD();
/* 28 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(4.0D);
/* 29 */     getAttributeInstance(GenericAttributes.d).setValue(0.20000000298023224D);
/*    */   }
/*    */   
/*    */   public void e() {
/* 33 */     super.e();
/* 34 */     int i = MathHelper.floor(this.locX);
/* 35 */     int j = MathHelper.floor(this.locY);
/* 36 */     int k = MathHelper.floor(this.locZ);
/*    */     
/* 38 */     if (L()) {
/* 39 */       damageEntity(DamageSource.DROWN, 1.0F);
/*    */     }
/*    */     
/* 42 */     if (this.world.getBiome(i, k).a(i, j, k) > 1.0F) {
/* 43 */       damageEntity(CraftEventFactory.MELTING, 1.0F);
/*    */     }
/*    */     
/* 46 */     for (int l = 0; l < 4; l++) {
/* 47 */       i = MathHelper.floor(this.locX + ((l % 2 * 2 - 1) * 0.25F));
/* 48 */       j = MathHelper.floor(this.locY);
/* 49 */       k = MathHelper.floor(this.locZ + ((l / 2 % 2 * 2 - 1) * 0.25F));
/* 50 */       if (this.world.getType(i, j, k).getMaterial() == Material.AIR && this.world.getBiome(i, k).a(i, j, k) < 0.8F && Blocks.SNOW.canPlace(this.world, i, j, k)) {
/*    */         
/* 52 */         BlockState blockState = this.world.getWorld().getBlockAt(i, j, k).getState();
/* 53 */         blockState.setType(CraftMagicNumbers.getMaterial(Blocks.SNOW));
/*    */         
/* 55 */         EntityBlockFormEvent event = new EntityBlockFormEvent((Entity)getBukkitEntity(), blockState.getBlock(), blockState);
/* 56 */         this.world.getServer().getPluginManager().callEvent((Event)event);
/*    */         
/* 58 */         if (!event.isCancelled()) {
/* 59 */           blockState.update(true);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getLoot() {
/* 67 */     return Items.SNOW_BALL;
/*    */   }
/*    */   
/*    */   protected void dropDeathLoot(boolean flag, int i) {
/* 71 */     int j = this.random.nextInt(16);
/*    */     
/* 73 */     for (int k = 0; k < j; k++) {
/* 74 */       a(Items.SNOW_BALL, 1);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(EntityLiving entityliving, float f) {
/* 79 */     EntitySnowball entitysnowball = new EntitySnowball(this.world, this);
/* 80 */     double d0 = entityliving.locX - this.locX;
/* 81 */     double d1 = entityliving.locY + entityliving.getHeadHeight() - 1.100000023841858D - entitysnowball.locY;
/* 82 */     double d2 = entityliving.locZ - this.locZ;
/* 83 */     float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
/*    */     
/* 85 */     entitysnowball.shoot(d0, d1 + f1, d2, 1.6F, 12.0F);
/* 86 */     makeSound("random.bow", 1.0F, 1.0F / (aI().nextFloat() * 0.4F + 0.8F));
/* 87 */     this.world.addEntity(entitysnowball);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySnowman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */