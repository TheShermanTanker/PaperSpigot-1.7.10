/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.event.entity.ExpBottleEvent;
/*    */ 
/*    */ public class EntityThrownExpBottle extends EntityProjectile {
/*    */   public EntityThrownExpBottle(World world) {
/*  6 */     super(world);
/*    */   }
/*    */   
/*    */   public EntityThrownExpBottle(World world, EntityLiving entityliving) {
/* 10 */     super(world, entityliving);
/*    */   }
/*    */   
/*    */   public EntityThrownExpBottle(World world, double d0, double d1, double d2) {
/* 14 */     super(world, d0, d1, d2);
/*    */   }
/*    */   
/*    */   protected float i() {
/* 18 */     return 0.07F;
/*    */   }
/*    */   
/*    */   protected float e() {
/* 22 */     return 0.7F;
/*    */   }
/*    */   
/*    */   protected float f() {
/* 26 */     return -20.0F;
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 30 */     if (!this.world.isStatic) {
/*    */ 
/*    */       
/* 33 */       int i = 3 + this.world.random.nextInt(5) + this.world.random.nextInt(5);
/*    */ 
/*    */       
/* 36 */       ExpBottleEvent event = CraftEventFactory.callExpBottleEvent(this, i);
/* 37 */       i = event.getExperience();
/* 38 */       if (event.getShowEffect()) {
/* 39 */         this.world.triggerEffect(2002, (int)Math.round(this.locX), (int)Math.round(this.locY), (int)Math.round(this.locZ), 0);
/*    */       }
/*    */ 
/*    */       
/* 43 */       while (i > 0) {
/* 44 */         int j = EntityExperienceOrb.getOrbValue(i);
/*    */         
/* 46 */         i -= j;
/* 47 */         this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
/*    */       } 
/*    */       
/* 50 */       die();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityThrownExpBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */