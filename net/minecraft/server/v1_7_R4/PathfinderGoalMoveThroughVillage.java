/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalMoveThroughVillage
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private double b;
/*    */   private PathEntity c;
/*    */   private VillageDoor d;
/*    */   private boolean e;
/* 20 */   private List f = new ArrayList();
/*    */   
/*    */   public PathfinderGoalMoveThroughVillage(EntityCreature paramEntityCreature, double paramDouble, boolean paramBoolean) {
/* 23 */     this.a = paramEntityCreature;
/* 24 */     this.b = paramDouble;
/* 25 */     this.e = paramBoolean;
/* 26 */     a(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 31 */     f();
/*    */     
/* 33 */     if (this.e && this.a.world.w()) return false;
/*    */     
/* 35 */     Village village = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 0);
/* 36 */     if (village == null) return false;
/*    */     
/* 38 */     this.d = a(village);
/* 39 */     if (this.d == null) return false;
/*    */     
/* 41 */     boolean bool = this.a.getNavigation().c();
/* 42 */     this.a.getNavigation().b(false);
/* 43 */     this.c = this.a.getNavigation().a(this.d.locX, this.d.locY, this.d.locZ);
/* 44 */     this.a.getNavigation().b(bool);
/* 45 */     if (this.c != null) return true;
/*    */     
/* 47 */     Vec3D vec3D = RandomPositionGenerator.a(this.a, 10, 7, Vec3D.a(this.d.locX, this.d.locY, this.d.locZ));
/* 48 */     if (vec3D == null) return false; 
/* 49 */     this.a.getNavigation().b(false);
/* 50 */     this.c = this.a.getNavigation().a(vec3D.a, vec3D.b, vec3D.c);
/* 51 */     this.a.getNavigation().b(bool);
/* 52 */     return (this.c != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 57 */     if (this.a.getNavigation().g()) return false; 
/* 58 */     float f = this.a.width + 4.0F;
/* 59 */     return (this.a.e(this.d.locX, this.d.locY, this.d.locZ) > (f * f));
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 64 */     this.a.getNavigation().a(this.c, this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 69 */     if (this.a.getNavigation().g() || this.a.e(this.d.locX, this.d.locY, this.d.locZ) < 16.0D) this.f.add(this.d); 
/*    */   }
/*    */   
/*    */   private VillageDoor a(Village paramVillage) {
/* 73 */     VillageDoor villageDoor = null;
/* 74 */     int i = Integer.MAX_VALUE;
/* 75 */     List list = paramVillage.getDoors();
/* 76 */     for (VillageDoor villageDoor1 : list) {
/* 77 */       int j = villageDoor1.b(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
/* 78 */       if (j >= i || 
/* 79 */         a(villageDoor1))
/* 80 */         continue;  villageDoor = villageDoor1;
/* 81 */       i = j;
/*    */     } 
/*    */     
/* 84 */     return villageDoor;
/*    */   }
/*    */   
/*    */   private boolean a(VillageDoor paramVillageDoor) {
/* 88 */     for (VillageDoor villageDoor : this.f) {
/* 89 */       if (paramVillageDoor.locX == villageDoor.locX && paramVillageDoor.locY == villageDoor.locY && paramVillageDoor.locZ == villageDoor.locZ) return true; 
/* 90 */     }  return false;
/*    */   }
/*    */   
/*    */   private void f() {
/* 94 */     if (this.f.size() > 15) this.f.remove(0); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalMoveThroughVillage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */