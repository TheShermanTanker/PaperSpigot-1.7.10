/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalFleeSun
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private double b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   private World f;
/*    */   
/*    */   public PathfinderGoalFleeSun(EntityCreature paramEntityCreature, double paramDouble) {
/* 19 */     this.a = paramEntityCreature;
/* 20 */     this.e = paramDouble;
/* 21 */     this.f = paramEntityCreature.world;
/* 22 */     a(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 27 */     if (!this.f.w()) return false; 
/* 28 */     if (!this.a.isBurning()) return false; 
/* 29 */     if (!this.f.i(MathHelper.floor(this.a.locX), (int)this.a.boundingBox.b, MathHelper.floor(this.a.locZ))) return false;
/*    */     
/* 31 */     Vec3D vec3D = f();
/* 32 */     if (vec3D == null) return false; 
/* 33 */     this.b = vec3D.a;
/* 34 */     this.c = vec3D.b;
/* 35 */     this.d = vec3D.c;
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 41 */     return !this.a.getNavigation().g();
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 46 */     this.a.getNavigation().a(this.b, this.c, this.d, this.e);
/*    */   }
/*    */   
/*    */   private Vec3D f() {
/* 50 */     Random random = this.a.aI();
/* 51 */     for (byte b = 0; b < 10; b++) {
/* 52 */       int i = MathHelper.floor(this.a.locX + random.nextInt(20) - 10.0D);
/* 53 */       int j = MathHelper.floor(this.a.boundingBox.b + random.nextInt(6) - 3.0D);
/* 54 */       int k = MathHelper.floor(this.a.locZ + random.nextInt(20) - 10.0D);
/* 55 */       if (!this.f.i(i, j, k) && this.a.a(i, j, k) < 0.0F) return Vec3D.a(i, j, k); 
/*    */     } 
/* 57 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalFleeSun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */