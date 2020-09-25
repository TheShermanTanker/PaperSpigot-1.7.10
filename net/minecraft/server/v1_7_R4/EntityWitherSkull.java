/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*    */ 
/*    */ public class EntityWitherSkull extends EntityFireball {
/*    */   public EntityWitherSkull(World world) {
/*  8 */     super(world);
/*  9 */     a(0.3125F, 0.3125F);
/*    */   }
/*    */   
/*    */   public EntityWitherSkull(World world, EntityLiving entityliving, double d0, double d1, double d2) {
/* 13 */     super(world, entityliving, d0, d1, d2);
/* 14 */     a(0.3125F, 0.3125F);
/*    */   }
/*    */   
/*    */   protected float e() {
/* 18 */     return isCharged() ? 0.73F : super.e();
/*    */   }
/*    */   
/*    */   public boolean isBurning() {
/* 22 */     return false;
/*    */   }
/*    */   
/*    */   public float a(Explosion explosion, World world, int i, int j, int k, Block block) {
/* 26 */     float f = super.a(explosion, world, i, j, k, block);
/*    */     
/* 28 */     if (isCharged() && block != Blocks.BEDROCK && block != Blocks.ENDER_PORTAL && block != Blocks.ENDER_PORTAL_FRAME && block != Blocks.COMMAND) {
/* 29 */       f = Math.min(0.8F, f);
/*    */     }
/*    */     
/* 32 */     return f;
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 36 */     if (!this.world.isStatic) {
/* 37 */       if (movingobjectposition.entity != null) {
/*    */         
/* 39 */         boolean didDamage = false;
/* 40 */         if (this.shooter != null) {
/* 41 */           didDamage = movingobjectposition.entity.damageEntity(DamageSource.mobAttack(this.shooter), 8.0F);
/* 42 */           if (didDamage && !movingobjectposition.entity.isAlive()) {
/* 43 */             this.shooter.heal(5.0F, EntityRegainHealthEvent.RegainReason.WITHER);
/*    */           }
/*    */         } else {
/* 46 */           didDamage = movingobjectposition.entity.damageEntity(DamageSource.MAGIC, 5.0F);
/*    */         } 
/*    */         
/* 49 */         if (didDamage && movingobjectposition.entity instanceof EntityLiving) {
/*    */           
/* 51 */           byte b0 = 0;
/*    */           
/* 53 */           if (this.world.difficulty == EnumDifficulty.NORMAL) {
/* 54 */             b0 = 10;
/* 55 */           } else if (this.world.difficulty == EnumDifficulty.HARD) {
/* 56 */             b0 = 40;
/*    */           } 
/*    */           
/* 59 */           if (b0 > 0) {
/* 60 */             ((EntityLiving)movingobjectposition.entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 20 * b0, 1));
/*    */           }
/*    */         } 
/*    */       } 
/*    */ 
/*    */       
/* 66 */       ExplosionPrimeEvent event = new ExplosionPrimeEvent((Entity)getBukkitEntity(), 1.0F, false);
/* 67 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*    */       
/* 69 */       if (!event.isCancelled()) {
/* 70 */         this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
/*    */       }
/*    */ 
/*    */       
/* 74 */       die();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean R() {
/* 79 */     return false;
/*    */   }
/*    */   
/*    */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 83 */     return false;
/*    */   }
/*    */   
/*    */   protected void c() {
/* 87 */     this.datawatcher.a(10, Byte.valueOf((byte)0));
/*    */   }
/*    */   
/*    */   public boolean isCharged() {
/* 91 */     return (this.datawatcher.getByte(10) == 1);
/*    */   }
/*    */   
/*    */   public void setCharged(boolean flag) {
/* 95 */     this.datawatcher.watch(10, Byte.valueOf((byte)(flag ? 1 : 0)));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityWitherSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */