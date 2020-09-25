/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAIBodyControl
/*    */ {
/*    */   private EntityLiving entity;
/*    */   private int b;
/*    */   private float c;
/*    */   
/*    */   public EntityAIBodyControl(EntityLiving paramEntityLiving) {
/* 14 */     this.entity = paramEntityLiving;
/*    */   }
/*    */   
/*    */   public void a() {
/* 18 */     double d1 = this.entity.locX - this.entity.lastX;
/* 19 */     double d2 = this.entity.locZ - this.entity.lastZ;
/*    */     
/* 21 */     if (d1 * d1 + d2 * d2 > 2.500000277905201E-7D) {
/*    */       
/* 23 */       this.entity.aM = this.entity.yaw;
/* 24 */       this.entity.aO = a(this.entity.aM, this.entity.aO, 75.0F);
/* 25 */       this.c = this.entity.aO;
/* 26 */       this.b = 0;
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 31 */     float f = 75.0F;
/* 32 */     if (Math.abs(this.entity.aO - this.c) > 15.0F) {
/* 33 */       this.b = 0;
/* 34 */       this.c = this.entity.aO;
/*    */     } else {
/* 36 */       this.b++;
/* 37 */       byte b = 10;
/* 38 */       if (this.b > 10) f = Math.max(1.0F - (this.b - 10) / 10.0F, 0.0F) * 75.0F;
/*    */     
/*    */     } 
/* 41 */     this.entity.aM = a(this.entity.aO, this.entity.aM, f);
/*    */   }
/*    */   
/*    */   private float a(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 45 */     float f = MathHelper.g(paramFloat1 - paramFloat2);
/* 46 */     if (f < -paramFloat3) f = -paramFloat3; 
/* 47 */     if (f >= paramFloat3) f = paramFloat3; 
/* 48 */     return paramFloat1 - f;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityAIBodyControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */