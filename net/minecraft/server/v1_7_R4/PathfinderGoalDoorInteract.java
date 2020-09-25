/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public abstract class PathfinderGoalDoorInteract
/*    */   extends PathfinderGoal
/*    */ {
/*    */   protected EntityInsentient a;
/*    */   protected int b;
/*    */   protected int c;
/*    */   protected int d;
/*    */   protected BlockDoor e;
/*    */   boolean f;
/*    */   float g;
/*    */   float h;
/*    */   
/*    */   public PathfinderGoalDoorInteract(EntityInsentient paramEntityInsentient) {
/* 17 */     this.a = paramEntityInsentient;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 22 */     if (!this.a.positionChanged) return false; 
/* 23 */     Navigation navigation = this.a.getNavigation();
/* 24 */     PathEntity pathEntity = navigation.e();
/* 25 */     if (pathEntity == null || pathEntity.b() || !navigation.c()) return false;
/*    */     
/* 27 */     for (byte b = 0; b < Math.min(pathEntity.e() + 2, pathEntity.d()); b++) {
/* 28 */       PathPoint pathPoint = pathEntity.a(b);
/* 29 */       this.b = pathPoint.a;
/* 30 */       this.c = pathPoint.b + 1;
/* 31 */       this.d = pathPoint.c;
/* 32 */       if (this.a.e(this.b, this.a.locY, this.d) <= 2.25D) {
/* 33 */         this.e = a(this.b, this.c, this.d);
/* 34 */         if (this.e != null)
/* 35 */           return true; 
/*    */       } 
/*    */     } 
/* 38 */     this.b = MathHelper.floor(this.a.locX);
/* 39 */     this.c = MathHelper.floor(this.a.locY + 1.0D);
/* 40 */     this.d = MathHelper.floor(this.a.locZ);
/* 41 */     this.e = a(this.b, this.c, this.d);
/* 42 */     return (this.e != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 47 */     return !this.f;
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 52 */     this.f = false;
/* 53 */     this.g = (float)((this.b + 0.5F) - this.a.locX);
/* 54 */     this.h = (float)((this.d + 0.5F) - this.a.locZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 59 */     float f1 = (float)((this.b + 0.5F) - this.a.locX);
/* 60 */     float f2 = (float)((this.d + 0.5F) - this.a.locZ);
/* 61 */     float f3 = this.g * f1 + this.h * f2;
/* 62 */     if (f3 < 0.0F) {
/* 63 */       this.f = true;
/*    */     }
/*    */   }
/*    */   
/*    */   private BlockDoor a(int paramInt1, int paramInt2, int paramInt3) {
/* 68 */     Block block = this.a.world.getType(paramInt1, paramInt2, paramInt3);
/* 69 */     if (block != Blocks.WOODEN_DOOR) return null; 
/* 70 */     return (BlockDoor)block;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalDoorInteract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */