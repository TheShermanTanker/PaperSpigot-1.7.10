/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_7_R4.TrigMath;
/*    */ 
/*    */ 
/*    */ public class ControllerMove
/*    */ {
/*    */   private EntityInsentient a;
/*    */   private double b;
/*    */   private double c;
/*    */   
/*    */   public ControllerMove(EntityInsentient entityinsentient) {
/* 13 */     this.a = entityinsentient;
/* 14 */     this.b = entityinsentient.locX;
/* 15 */     this.c = entityinsentient.locY;
/* 16 */     this.d = entityinsentient.locZ;
/*    */   }
/*    */   private double d; private double e; private boolean f;
/*    */   public boolean a() {
/* 20 */     return this.f;
/*    */   }
/*    */   
/*    */   public double b() {
/* 24 */     return this.e;
/*    */   }
/*    */   
/*    */   public void a(double d0, double d1, double d2, double d3) {
/* 28 */     this.b = d0;
/* 29 */     this.c = d1;
/* 30 */     this.d = d2;
/* 31 */     this.e = d3;
/* 32 */     this.f = true;
/*    */   }
/*    */   
/*    */   public void c() {
/* 36 */     this.a.n(0.0F);
/* 37 */     if (this.f) {
/* 38 */       this.f = false;
/* 39 */       int i = MathHelper.floor(this.a.boundingBox.b + 0.5D);
/* 40 */       double d0 = this.b - this.a.locX;
/* 41 */       double d1 = this.d - this.a.locZ;
/* 42 */       double d2 = this.c - i;
/* 43 */       double d3 = d0 * d0 + d2 * d2 + d1 * d1;
/*    */       
/* 45 */       if (d3 >= 2.500000277905201E-7D) {
/*    */         
/* 47 */         float f = (float)(TrigMath.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
/*    */         
/* 49 */         this.a.yaw = a(this.a.yaw, f, 30.0F);
/* 50 */         this.a.i((float)(this.e * this.a.getAttributeInstance(GenericAttributes.d).getValue()));
/* 51 */         if (d2 > 0.0D && d0 * d0 + d1 * d1 < 1.0D) {
/* 52 */           this.a.getControllerJump().a();
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private float a(float f, float f1, float f2) {
/* 59 */     float f3 = MathHelper.g(f1 - f);
/*    */     
/* 61 */     if (f3 > f2) {
/* 62 */       f3 = f2;
/*    */     }
/*    */     
/* 65 */     if (f3 < -f2) {
/* 66 */       f3 = -f2;
/*    */     }
/*    */     
/* 69 */     return f + f3;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ControllerMove.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */