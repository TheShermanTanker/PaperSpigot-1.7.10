/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntitySnowball
/*    */   extends EntityProjectile
/*    */ {
/*    */   public EntitySnowball(World paramWorld) {
/* 12 */     super(paramWorld);
/*    */   }
/*    */   
/*    */   public EntitySnowball(World paramWorld, EntityLiving paramEntityLiving) {
/* 16 */     super(paramWorld, paramEntityLiving);
/*    */   }
/*    */   
/*    */   public EntitySnowball(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 20 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(MovingObjectPosition paramMovingObjectPosition) {
/* 25 */     if (paramMovingObjectPosition.entity != null) {
/* 26 */       byte b1 = 0;
/* 27 */       if (paramMovingObjectPosition.entity instanceof EntityBlaze) {
/* 28 */         b1 = 3;
/*    */       }
/* 30 */       paramMovingObjectPosition.entity.damageEntity(DamageSource.projectile(this, getShooter()), b1);
/*    */     } 
/* 32 */     for (byte b = 0; b < 8; b++)
/* 33 */       this.world.addParticle("snowballpoof", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D); 
/* 34 */     if (!this.world.isStatic)
/* 35 */       die(); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySnowball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */