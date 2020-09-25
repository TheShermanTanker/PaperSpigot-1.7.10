/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ 
/*    */ public class PathfinderGoalMakeLove
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityVillager b;
/*    */   private EntityVillager c;
/*    */   
/*    */   public PathfinderGoalMakeLove(EntityVillager entityvillager) {
/* 12 */     this.b = entityvillager;
/* 13 */     this.d = entityvillager.world;
/* 14 */     a(3);
/*    */   }
/*    */   private World d; private int e; Village a;
/*    */   public boolean a() {
/* 18 */     if (this.b.getAge() != 0)
/* 19 */       return false; 
/* 20 */     if (this.b.aI().nextInt(500) != 0) {
/* 21 */       return false;
/*    */     }
/* 23 */     this.a = this.d.villages.getClosestVillage(MathHelper.floor(this.b.locX), MathHelper.floor(this.b.locY), MathHelper.floor(this.b.locZ), 0);
/* 24 */     if (this.a == null)
/* 25 */       return false; 
/* 26 */     if (!f()) {
/* 27 */       return false;
/*    */     }
/* 29 */     Entity entity = this.d.a(EntityVillager.class, this.b.boundingBox.grow(8.0D, 3.0D, 8.0D), this.b);
/*    */     
/* 31 */     if (entity == null) {
/* 32 */       return false;
/*    */     }
/* 34 */     this.c = (EntityVillager)entity;
/* 35 */     return (this.c.getAge() == 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void c() {
/* 42 */     this.e = 300;
/* 43 */     this.b.i(true);
/*    */   }
/*    */   
/*    */   public void d() {
/* 47 */     this.a = null;
/* 48 */     this.c = null;
/* 49 */     this.b.i(false);
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 53 */     return (this.e >= 0 && f() && this.b.getAge() == 0);
/*    */   }
/*    */   
/*    */   public void e() {
/* 57 */     this.e--;
/* 58 */     this.b.getControllerLook().a(this.c, 10.0F, 30.0F);
/* 59 */     if (this.b.f(this.c) > 2.25D) {
/* 60 */       this.b.getNavigation().a(this.c, 0.25D);
/* 61 */     } else if (this.e == 0 && this.c.ca()) {
/* 62 */       g();
/*    */     } 
/*    */     
/* 65 */     if (this.b.aI().nextInt(35) == 0) {
/* 66 */       this.d.broadcastEntityEffect(this.b, (byte)12);
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean f() {
/* 71 */     if (!this.a.i()) {
/* 72 */       return false;
/*    */     }
/* 74 */     int i = (int)(this.a.getDoorCount() * 0.35D);
/*    */     
/* 76 */     return (this.a.getPopulationCount() < i);
/*    */   }
/*    */ 
/*    */   
/*    */   private void g() {
/* 81 */     EntityVillager entityvillager = this.b.b(this.c);
/*    */     
/* 83 */     this.c.setAge(6000);
/* 84 */     this.b.setAge(6000);
/* 85 */     entityvillager.setAge(-24000);
/* 86 */     entityvillager.setPositionRotation(this.b.locX, this.b.locY, this.b.locZ, 0.0F, 0.0F);
/* 87 */     this.d.addEntity(entityvillager, CreatureSpawnEvent.SpawnReason.BREEDING);
/* 88 */     this.d.broadcastEntityEffect(entityvillager, (byte)12);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalMakeLove.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */