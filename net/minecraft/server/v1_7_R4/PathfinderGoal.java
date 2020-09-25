/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public abstract class PathfinderGoal
/*    */ {
/*    */   private int a;
/*    */   
/*    */   public abstract boolean a();
/*    */   
/*    */   public boolean b() {
/* 10 */     return a();
/*    */   }
/*    */   
/*    */   public boolean i() {
/* 14 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {}
/*    */ 
/*    */   
/*    */   public void d() {}
/*    */ 
/*    */   
/*    */   public void e() {}
/*    */   
/*    */   public void a(int paramInt) {
/* 27 */     this.a = paramInt;
/*    */   }
/*    */   
/*    */   public int j() {
/* 31 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */