/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalRestrictOpenDoor
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private VillageDoor b;
/*    */   
/*    */   public PathfinderGoalRestrictOpenDoor(EntityCreature paramEntityCreature) {
/* 12 */     this.a = paramEntityCreature;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 17 */     if (this.a.world.w()) return false; 
/* 18 */     Village village = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 16);
/* 19 */     if (village == null) return false; 
/* 20 */     this.b = village.b(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
/* 21 */     if (this.b == null) return false; 
/* 22 */     return (this.b.c(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ)) < 2.25D);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 27 */     if (this.a.world.w()) return false; 
/* 28 */     return (!this.b.removed && this.b.a(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locZ)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 33 */     this.a.getNavigation().b(false);
/* 34 */     this.a.getNavigation().c(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 39 */     this.a.getNavigation().b(true);
/* 40 */     this.a.getNavigation().c(true);
/* 41 */     this.b = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 46 */     this.b.e();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalRestrictOpenDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */