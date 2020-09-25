/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public abstract class EntityFlying
/*    */   extends EntityInsentient
/*    */ {
/*    */   public EntityFlying(World paramWorld) {
/*  8 */     super(paramWorld);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void b(float paramFloat) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void a(double paramDouble, boolean paramBoolean) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void e(float paramFloat1, float paramFloat2) {
/* 23 */     if (M()) {
/* 24 */       a(paramFloat1, paramFloat2, 0.02F);
/* 25 */       move(this.motX, this.motY, this.motZ);
/*    */       
/* 27 */       this.motX *= 0.800000011920929D;
/* 28 */       this.motY *= 0.800000011920929D;
/* 29 */       this.motZ *= 0.800000011920929D;
/* 30 */     } else if (P()) {
/* 31 */       a(paramFloat1, paramFloat2, 0.02F);
/* 32 */       move(this.motX, this.motY, this.motZ);
/* 33 */       this.motX *= 0.5D;
/* 34 */       this.motY *= 0.5D;
/* 35 */       this.motZ *= 0.5D;
/*    */     } else {
/* 37 */       float f1 = 0.91F;
/* 38 */       if (this.onGround) {
/* 39 */         f1 = (this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ))).frictionFactor * 0.91F;
/*    */       }
/*    */       
/* 42 */       float f2 = 0.16277136F / f1 * f1 * f1;
/* 43 */       a(paramFloat1, paramFloat2, this.onGround ? (0.1F * f2) : 0.02F);
/*    */       
/* 45 */       f1 = 0.91F;
/* 46 */       if (this.onGround) {
/* 47 */         f1 = (this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ))).frictionFactor * 0.91F;
/*    */       }
/*    */       
/* 50 */       move(this.motX, this.motY, this.motZ);
/*    */       
/* 52 */       this.motX *= f1;
/* 53 */       this.motY *= f1;
/* 54 */       this.motZ *= f1;
/*    */     } 
/* 56 */     this.aE = this.aF;
/* 57 */     double d1 = this.locX - this.lastX;
/* 58 */     double d2 = this.locZ - this.lastZ;
/* 59 */     float f = MathHelper.sqrt(d1 * d1 + d2 * d2) * 4.0F;
/* 60 */     if (f > 1.0F) f = 1.0F; 
/* 61 */     this.aF += (f - this.aF) * 0.4F;
/* 62 */     this.aG += this.aF;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean h_() {
/* 67 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityFlying.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */