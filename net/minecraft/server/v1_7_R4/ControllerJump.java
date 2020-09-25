/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class ControllerJump
/*    */ {
/*    */   private EntityInsentient a;
/*    */   private boolean b;
/*    */   
/*    */   public ControllerJump(EntityInsentient paramEntityInsentient) {
/* 10 */     this.a = paramEntityInsentient;
/*    */   }
/*    */   
/*    */   public void a() {
/* 14 */     this.b = true;
/*    */   }
/*    */   
/*    */   public void b() {
/* 18 */     this.a.f(this.b);
/* 19 */     this.b = false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ControllerJump.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */