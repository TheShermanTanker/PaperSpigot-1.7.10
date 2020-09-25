/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PathfinderGoalOpenDoor
/*    */   extends PathfinderGoalDoorInteract
/*    */ {
/*    */   boolean i;
/*    */   int j;
/*    */   
/*    */   public PathfinderGoalOpenDoor(EntityInsentient paramEntityInsentient, boolean paramBoolean) {
/* 10 */     super(paramEntityInsentient);
/* 11 */     this.a = paramEntityInsentient;
/* 12 */     this.i = paramBoolean;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 17 */     return (this.i && this.j > 0 && super.b());
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 22 */     this.j = 20;
/* 23 */     this.e.setDoor(this.a.world, this.b, this.c, this.d, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 28 */     if (this.i) {
/* 29 */       this.e.setDoor(this.a.world, this.b, this.c, this.d, false);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 35 */     this.j--;
/* 36 */     super.e();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalOpenDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */