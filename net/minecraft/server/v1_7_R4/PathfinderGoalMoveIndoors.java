/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalMoveIndoors
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private VillageDoor b;
/* 14 */   private int c = -1, d = -1;
/*    */   
/*    */   public PathfinderGoalMoveIndoors(EntityCreature paramEntityCreature) {
/* 17 */     this.a = paramEntityCreature;
/* 18 */     a(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 23 */     int i = MathHelper.floor(this.a.locX);
/* 24 */     int j = MathHelper.floor(this.a.locY);
/* 25 */     int k = MathHelper.floor(this.a.locZ);
/* 26 */     if ((this.a.world.w() && !this.a.world.Q() && this.a.world.getBiome(i, k).e()) || this.a.world.worldProvider.g) return false; 
/* 27 */     if (this.a.aI().nextInt(50) != 0) return false; 
/* 28 */     if (this.c != -1 && this.a.e(this.c, this.a.locY, this.d) < 4.0D) return false; 
/* 29 */     Village village = this.a.world.villages.getClosestVillage(i, j, k, 14);
/* 30 */     if (village == null) return false; 
/* 31 */     this.b = village.c(i, j, k);
/* 32 */     return (this.b != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 37 */     return !this.a.getNavigation().g();
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 42 */     this.c = -1;
/* 43 */     if (this.a.e(this.b.getIndoorsX(), this.b.locY, this.b.getIndoorsZ()) > 256.0D)
/* 44 */     { Vec3D vec3D = RandomPositionGenerator.a(this.a, 14, 3, Vec3D.a(this.b.getIndoorsX() + 0.5D, this.b.getIndoorsY(), this.b.getIndoorsZ() + 0.5D));
/* 45 */       if (vec3D != null) this.a.getNavigation().a(vec3D.a, vec3D.b, vec3D.c, 1.0D);  }
/* 46 */     else { this.a.getNavigation().a(this.b.getIndoorsX() + 0.5D, this.b.getIndoorsY(), this.b.getIndoorsZ() + 0.5D, 1.0D); }
/*    */   
/*    */   }
/*    */   
/*    */   public void d() {
/* 51 */     this.c = this.b.getIndoorsX();
/* 52 */     this.d = this.b.getIndoorsZ();
/* 53 */     this.b = null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalMoveIndoors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */