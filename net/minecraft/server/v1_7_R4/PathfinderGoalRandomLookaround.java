/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalRandomLookaround
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityInsentient a;
/*    */   private double b;
/*    */   private double c;
/*    */   private int d;
/*    */   
/*    */   public PathfinderGoalRandomLookaround(EntityInsentient paramEntityInsentient) {
/* 13 */     this.a = paramEntityInsentient;
/* 14 */     a(3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 19 */     return (this.a.aI().nextFloat() < 0.02F);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 24 */     return (this.d >= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 29 */     double d = 6.283185307179586D * this.a.aI().nextDouble();
/* 30 */     this.b = Math.cos(d);
/* 31 */     this.c = Math.sin(d);
/* 32 */     this.d = 20 + this.a.aI().nextInt(20);
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 37 */     this.d--;
/* 38 */     this.a.getControllerLook().a(this.a.locX + this.b, this.a.locY + this.a.getHeadHeight(), this.a.locZ + this.c, 10.0F, this.a.x());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalRandomLookaround.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */