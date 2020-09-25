/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_7_R4.TrigMath;
/*    */ 
/*    */ public class ControllerLook
/*    */ {
/*    */   private EntityInsentient a;
/*    */   private float b;
/*    */   private float c;
/*    */   private boolean d;
/*    */   private double e;
/*    */   private double f;
/*    */   private double g;
/*    */   
/*    */   public ControllerLook(EntityInsentient entityinsentient) {
/* 16 */     this.a = entityinsentient;
/*    */   }
/*    */   
/*    */   public void a(Entity entity, float f, float f1) {
/* 20 */     this.e = entity.locX;
/* 21 */     if (entity instanceof EntityLiving) {
/* 22 */       this.f = entity.locY + entity.getHeadHeight();
/*    */     } else {
/* 24 */       this.f = (entity.boundingBox.b + entity.boundingBox.e) / 2.0D;
/*    */     } 
/*    */     
/* 27 */     this.g = entity.locZ;
/* 28 */     this.b = f;
/* 29 */     this.c = f1;
/* 30 */     this.d = true;
/*    */   }
/*    */   
/*    */   public void a(double d0, double d1, double d2, float f, float f1) {
/* 34 */     this.e = d0;
/* 35 */     this.f = d1;
/* 36 */     this.g = d2;
/* 37 */     this.b = f;
/* 38 */     this.c = f1;
/* 39 */     this.d = true;
/*    */   }
/*    */   
/*    */   public void a() {
/* 43 */     this.a.pitch = 0.0F;
/* 44 */     if (this.d) {
/* 45 */       this.d = false;
/* 46 */       double d0 = this.e - this.a.locX;
/* 47 */       double d1 = this.f - this.a.locY + this.a.getHeadHeight();
/* 48 */       double d2 = this.g - this.a.locZ;
/* 49 */       double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/*    */       
/* 51 */       float f = (float)(TrigMath.atan2(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 52 */       float f1 = (float)-(TrigMath.atan2(d1, d3) * 180.0D / 3.1415927410125732D);
/*    */ 
/*    */       
/* 55 */       this.a.pitch = a(this.a.pitch, f1, this.c);
/* 56 */       this.a.aO = a(this.a.aO, f, this.b);
/*    */     } else {
/* 58 */       this.a.aO = a(this.a.aO, this.a.aM, 10.0F);
/*    */     } 
/*    */     
/* 61 */     float f2 = MathHelper.g(this.a.aO - this.a.aM);
/*    */     
/* 63 */     if (!this.a.getNavigation().g()) {
/* 64 */       if (f2 < -75.0F) {
/* 65 */         this.a.aO = this.a.aM - 75.0F;
/*    */       }
/*    */       
/* 68 */       if (f2 > 75.0F) {
/* 69 */         this.a.aO = this.a.aM + 75.0F;
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private float a(float f, float f1, float f2) {
/* 75 */     float f3 = MathHelper.g(f1 - f);
/*    */     
/* 77 */     if (f3 > f2) {
/* 78 */       f3 = f2;
/*    */     }
/*    */     
/* 81 */     if (f3 < -f2) {
/* 82 */       f3 = -f2;
/*    */     }
/*    */     
/* 85 */     return f + f3;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ControllerLook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */