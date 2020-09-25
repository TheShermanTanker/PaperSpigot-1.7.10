/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalAvoidPlayer
/*    */   extends PathfinderGoal
/*    */ {
/* 14 */   public final IEntitySelector a = new EntitySelectorViewable(this);
/*    */   
/*    */   private EntityCreature b;
/*    */   
/*    */   private double c;
/*    */   
/*    */   private double d;
/*    */   
/*    */   private Entity e;
/*    */   
/*    */   private float f;
/*    */   private PathEntity g;
/*    */   private Navigation h;
/*    */   private Class i;
/*    */   
/*    */   public PathfinderGoalAvoidPlayer(EntityCreature paramEntityCreature, Class paramClass, float paramFloat, double paramDouble1, double paramDouble2) {
/* 30 */     this.b = paramEntityCreature;
/* 31 */     this.i = paramClass;
/* 32 */     this.f = paramFloat;
/* 33 */     this.c = paramDouble1;
/* 34 */     this.d = paramDouble2;
/* 35 */     this.h = paramEntityCreature.getNavigation();
/* 36 */     a(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 41 */     if (this.i == EntityHuman.class) {
/* 42 */       if (this.b instanceof EntityTameableAnimal && ((EntityTameableAnimal)this.b).isTamed()) return false; 
/* 43 */       this.e = this.b.world.findNearbyPlayer(this.b, this.f);
/* 44 */       if (this.e == null) return false; 
/*    */     } else {
/* 46 */       List<Entity> list = this.b.world.a(this.i, this.b.boundingBox.grow(this.f, 3.0D, this.f), this.a);
/* 47 */       if (list.isEmpty()) return false; 
/* 48 */       this.e = list.get(0);
/*    */     } 
/*    */     
/* 51 */     Vec3D vec3D = RandomPositionGenerator.b(this.b, 16, 7, Vec3D.a(this.e.locX, this.e.locY, this.e.locZ));
/* 52 */     if (vec3D == null) return false; 
/* 53 */     if (this.e.e(vec3D.a, vec3D.b, vec3D.c) < this.e.f(this.b)) return false; 
/* 54 */     this.g = this.h.a(vec3D.a, vec3D.b, vec3D.c);
/* 55 */     if (this.g == null) return false; 
/* 56 */     if (!this.g.b(vec3D)) return false; 
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 62 */     return !this.h.g();
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 67 */     this.h.a(this.g, this.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 72 */     this.e = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 77 */     if (this.b.f(this.e) < 49.0D) { this.b.getNavigation().a(this.d); }
/* 78 */     else { this.b.getNavigation().a(this.c); }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalAvoidPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */