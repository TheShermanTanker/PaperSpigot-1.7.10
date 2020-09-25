/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.event.entity.EntityTargetEvent;
/*    */ 
/*    */ public class PathfinderGoalOcelotAttack extends PathfinderGoal {
/*    */   World a;
/*    */   EntityInsentient b;
/*    */   EntityLiving c;
/*    */   int d;
/*    */   
/*    */   public PathfinderGoalOcelotAttack(EntityInsentient entityinsentient) {
/* 13 */     this.b = entityinsentient;
/* 14 */     this.a = entityinsentient.world;
/* 15 */     a(3);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 19 */     EntityLiving entityliving = this.b.getGoalTarget();
/*    */     
/* 21 */     if (entityliving == null) {
/* 22 */       return false;
/*    */     }
/* 24 */     this.c = entityliving;
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 30 */     return !this.c.isAlive() ? false : ((this.b.f(this.c) > 225.0D) ? false : ((!this.b.getNavigation().g() || a())));
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 35 */     EntityTargetEvent.TargetReason reason = this.c.isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
/* 36 */     CraftEventFactory.callEntityTargetEvent(this.c, null, reason);
/*    */     
/* 38 */     this.c = null;
/* 39 */     this.b.getNavigation().h();
/*    */   }
/*    */   
/*    */   public void e() {
/* 43 */     this.b.getControllerLook().a(this.c, 30.0F, 30.0F);
/* 44 */     double d0 = (this.b.width * 2.0F * this.b.width * 2.0F);
/* 45 */     double d1 = this.b.e(this.c.locX, this.c.boundingBox.b, this.c.locZ);
/* 46 */     double d2 = 0.8D;
/*    */     
/* 48 */     if (d1 > d0 && d1 < 16.0D) {
/* 49 */       d2 = 1.33D;
/* 50 */     } else if (d1 < 225.0D) {
/* 51 */       d2 = 0.6D;
/*    */     } 
/*    */     
/* 54 */     this.b.getNavigation().a(this.c, d2);
/* 55 */     this.d = Math.max(this.d - 1, 0);
/* 56 */     if (d1 <= d0 && 
/* 57 */       this.d <= 0) {
/* 58 */       this.d = 20;
/* 59 */       this.b.n(this.c);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalOcelotAttack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */