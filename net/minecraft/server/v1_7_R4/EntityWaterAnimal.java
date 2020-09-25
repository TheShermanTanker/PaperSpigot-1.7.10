/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntityWaterAnimal
/*    */   extends EntityCreature
/*    */   implements IAnimal
/*    */ {
/*    */   public EntityWaterAnimal(World paramWorld) {
/* 19 */     super(paramWorld);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean aE() {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSpawn() {
/* 30 */     return this.world.b(this.boundingBox);
/*    */   }
/*    */ 
/*    */   
/*    */   public int q() {
/* 35 */     return 120;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isTypeNotPersistent() {
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getExpValue(EntityHuman paramEntityHuman) {
/* 45 */     return 1 + this.world.random.nextInt(3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void C() {
/* 50 */     int i = getAirTicks();
/*    */     
/* 52 */     super.C();
/*    */     
/* 54 */     if (isAlive() && !M()) {
/* 55 */       setAirTicks(--i);
/* 56 */       if (getAirTicks() == -20) {
/* 57 */         setAirTicks(0);
/* 58 */         damageEntity(DamageSource.DROWN, 2.0F);
/*    */       } 
/*    */     } else {
/* 61 */       setAirTicks(300);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityWaterAnimal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */