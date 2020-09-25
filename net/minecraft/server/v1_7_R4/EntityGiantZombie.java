/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class EntityGiantZombie
/*    */   extends EntityMonster
/*    */ {
/*    */   public EntityGiantZombie(World paramWorld) {
/*  7 */     super(paramWorld);
/*  8 */     this.height *= 6.0F;
/*  9 */     a(this.width * 6.0F, this.length * 6.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void aD() {
/* 14 */     super.aD();
/*    */     
/* 16 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(100.0D);
/* 17 */     getAttributeInstance(GenericAttributes.d).setValue(0.5D);
/* 18 */     getAttributeInstance(GenericAttributes.e).setValue(50.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public float a(int paramInt1, int paramInt2, int paramInt3) {
/* 23 */     return this.world.n(paramInt1, paramInt2, paramInt3) - 0.5F;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityGiantZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */