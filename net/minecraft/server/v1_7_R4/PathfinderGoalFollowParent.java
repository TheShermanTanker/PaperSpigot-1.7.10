/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class PathfinderGoalFollowParent
/*    */   extends PathfinderGoal
/*    */ {
/*    */   EntityAnimal a;
/*    */   EntityAnimal b;
/*    */   double c;
/*    */   private int d;
/*    */   
/*    */   public PathfinderGoalFollowParent(EntityAnimal paramEntityAnimal, double paramDouble) {
/* 14 */     this.a = paramEntityAnimal;
/* 15 */     this.c = paramDouble;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 20 */     if (this.a.getAge() >= 0) return false;
/*    */     
/* 22 */     List list = this.a.world.a(this.a.getClass(), this.a.boundingBox.grow(8.0D, 4.0D, 8.0D));
/*    */     
/* 24 */     EntityAnimal entityAnimal = null;
/* 25 */     double d = Double.MAX_VALUE;
/* 26 */     for (EntityAnimal entityAnimal1 : list) {
/* 27 */       if (entityAnimal1.getAge() < 0)
/* 28 */         continue;  double d1 = this.a.f(entityAnimal1);
/* 29 */       if (d1 > d)
/* 30 */         continue;  d = d1;
/* 31 */       entityAnimal = entityAnimal1;
/*    */     } 
/*    */     
/* 34 */     if (entityAnimal == null) return false; 
/* 35 */     if (d < 9.0D) return false; 
/* 36 */     this.b = entityAnimal;
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 42 */     if (!this.b.isAlive()) return false; 
/* 43 */     double d = this.a.f(this.b);
/* 44 */     if (d < 9.0D || d > 256.0D) return false; 
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 50 */     this.d = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 55 */     this.b = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 60 */     if (--this.d > 0)
/* 61 */       return;  this.d = 10;
/* 62 */     this.a.getNavigation().a(this.b, this.c);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalFollowParent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */