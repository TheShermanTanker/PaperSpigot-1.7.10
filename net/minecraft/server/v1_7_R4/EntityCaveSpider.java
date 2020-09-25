/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityCaveSpider
/*    */   extends EntitySpider
/*    */ {
/*    */   public EntityCaveSpider(World paramWorld) {
/* 11 */     super(paramWorld);
/* 12 */     a(0.7F, 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void aD() {
/* 17 */     super.aD();
/*    */     
/* 19 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(12.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean n(Entity paramEntity) {
/* 24 */     if (super.n(paramEntity)) {
/*    */       
/* 26 */       if (paramEntity instanceof EntityLiving) {
/* 27 */         byte b = 0;
/* 28 */         if (this.world.difficulty == EnumDifficulty.NORMAL) {
/* 29 */           b = 7;
/* 30 */         } else if (this.world.difficulty == EnumDifficulty.HARD) {
/* 31 */           b = 15;
/*    */         } 
/*    */         
/* 34 */         if (b > 0) {
/* 35 */           ((EntityLiving)paramEntity).addEffect(new MobEffect(MobEffectList.POISON.id, b * 20, 0));
/*    */         }
/*    */       } 
/*    */       
/* 39 */       return true;
/*    */     } 
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public GroupDataEntity prepare(GroupDataEntity paramGroupDataEntity) {
/* 47 */     return paramGroupDataEntity;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityCaveSpider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */