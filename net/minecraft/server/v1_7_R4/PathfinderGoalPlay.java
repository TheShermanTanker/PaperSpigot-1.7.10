/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalPlay
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityVillager a;
/*    */   private EntityLiving b;
/*    */   private double c;
/*    */   private int d;
/*    */   
/*    */   public PathfinderGoalPlay(EntityVillager paramEntityVillager, double paramDouble) {
/* 19 */     this.a = paramEntityVillager;
/* 20 */     this.c = paramDouble;
/* 21 */     a(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 26 */     if (this.a.getAge() >= 0) return false; 
/* 27 */     if (this.a.aI().nextInt(400) != 0) return false;
/*    */     
/* 29 */     List list = this.a.world.a(EntityVillager.class, this.a.boundingBox.grow(6.0D, 3.0D, 6.0D));
/* 30 */     double d = Double.MAX_VALUE;
/* 31 */     for (EntityVillager entityVillager : list) {
/* 32 */       if (entityVillager == this.a || 
/* 33 */         entityVillager.cb() || 
/* 34 */         entityVillager.getAge() >= 0)
/* 35 */         continue;  double d1 = entityVillager.f(this.a);
/* 36 */       if (d1 > d)
/* 37 */         continue;  d = d1;
/* 38 */       this.b = entityVillager;
/*    */     } 
/*    */     
/* 41 */     if (this.b == null) {
/* 42 */       Vec3D vec3D = RandomPositionGenerator.a(this.a, 16, 3);
/* 43 */       if (vec3D == null) return false; 
/*    */     } 
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 50 */     return (this.d > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 55 */     if (this.b != null) this.a.j(true); 
/* 56 */     this.d = 1000;
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 61 */     this.a.j(false);
/* 62 */     this.b = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 67 */     this.d--;
/* 68 */     if (this.b != null) {
/* 69 */       if (this.a.f(this.b) > 4.0D) this.a.getNavigation().a(this.b, this.c);
/*    */     
/* 71 */     } else if (this.a.getNavigation().g()) {
/* 72 */       Vec3D vec3D = RandomPositionGenerator.a(this.a, 16, 3);
/* 73 */       if (vec3D == null)
/* 74 */         return;  this.a.getNavigation().a(vec3D.a, vec3D.b, vec3D.c, this.c);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalPlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */