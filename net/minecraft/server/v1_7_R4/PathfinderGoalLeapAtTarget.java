/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalLeapAtTarget
/*    */   extends PathfinderGoal
/*    */ {
/*    */   EntityInsentient a;
/*    */   EntityLiving b;
/*    */   float c;
/*    */   
/*    */   public PathfinderGoalLeapAtTarget(EntityInsentient paramEntityInsentient, float paramFloat) {
/* 13 */     this.a = paramEntityInsentient;
/* 14 */     this.c = paramFloat;
/* 15 */     a(5);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 20 */     this.b = this.a.getGoalTarget();
/* 21 */     if (this.b == null) return false; 
/* 22 */     double d = this.a.f(this.b);
/* 23 */     if (d < 4.0D || d > 16.0D) return false; 
/* 24 */     if (!this.a.onGround) return false; 
/* 25 */     if (this.a.aI().nextInt(5) != 0) return false; 
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 31 */     return !this.a.onGround;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void c() {
/* 37 */     double d1 = this.b.locX - this.a.locX;
/* 38 */     double d2 = this.b.locZ - this.a.locZ;
/* 39 */     float f = MathHelper.sqrt(d1 * d1 + d2 * d2);
/* 40 */     this.a.motX += d1 / f * 0.5D * 0.800000011920929D + this.a.motX * 0.20000000298023224D;
/* 41 */     this.a.motZ += d2 / f * 0.5D * 0.800000011920929D + this.a.motZ * 0.20000000298023224D;
/* 42 */     this.a.motY = this.c;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalLeapAtTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */