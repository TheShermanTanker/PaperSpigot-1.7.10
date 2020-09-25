/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class PathfinderGoalTame
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityHorse entity;
/*    */   private double b;
/*    */   
/*    */   public PathfinderGoalTame(EntityHorse entityhorse, double d0) {
/* 12 */     this.entity = entityhorse;
/* 13 */     this.b = d0;
/* 14 */     a(1);
/*    */   }
/*    */   private double c; private double d; private double e;
/*    */   public boolean a() {
/* 18 */     if (!this.entity.isTame() && this.entity.passenger != null) {
/* 19 */       Vec3D vec3d = RandomPositionGenerator.a(this.entity, 5, 4);
/*    */       
/* 21 */       if (vec3d == null) {
/* 22 */         return false;
/*    */       }
/* 24 */       this.c = vec3d.a;
/* 25 */       this.d = vec3d.b;
/* 26 */       this.e = vec3d.c;
/* 27 */       return true;
/*    */     } 
/*    */     
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 35 */     this.entity.getNavigation().a(this.c, this.d, this.e, this.b);
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 39 */     return (!this.entity.getNavigation().g() && this.entity.passenger != null);
/*    */   }
/*    */   
/*    */   public void e() {
/* 43 */     if (this.entity.aI().nextInt(50) == 0) {
/* 44 */       if (this.entity.passenger instanceof EntityHuman) {
/* 45 */         int i = this.entity.getTemper();
/* 46 */         int j = this.entity.getMaxDomestication();
/*    */ 
/*    */         
/* 49 */         if (j > 0 && this.entity.aI().nextInt(j) < i && !CraftEventFactory.callEntityTameEvent(this.entity, (EntityHuman)this.entity.passenger).isCancelled() && this.entity.passenger instanceof EntityHuman) {
/* 50 */           this.entity.h((EntityHuman)this.entity.passenger);
/* 51 */           this.entity.world.broadcastEntityEffect(this.entity, (byte)7);
/*    */           
/*    */           return;
/*    */         } 
/* 55 */         this.entity.v(5);
/*    */       } 
/*    */ 
/*    */       
/* 59 */       if (this.entity.passenger != null) {
/* 60 */         this.entity.passenger.mount((Entity)null);
/*    */         
/* 62 */         if (this.entity.passenger != null) {
/*    */           return;
/*    */         }
/*    */       } 
/*    */ 
/*    */       
/* 68 */       this.entity.cJ();
/* 69 */       this.entity.world.broadcastEntityEffect(this.entity, (byte)6);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalTame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */