/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityDamageSource
/*    */   extends DamageSource
/*    */ {
/*    */   protected Entity p;
/*    */   
/*    */   public EntityDamageSource(String paramString, Entity paramEntity) {
/* 15 */     super(paramString);
/* 16 */     this.p = paramEntity;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getEntity() {
/* 21 */     return this.p;
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatBaseComponent getLocalizedDeathMessage(EntityLiving paramEntityLiving) {
/* 26 */     ItemStack itemStack = (this.p instanceof EntityLiving) ? ((EntityLiving)this.p).be() : null;
/* 27 */     String str1 = "death.attack." + this.translationIndex;
/* 28 */     String str2 = str1 + ".item";
/*    */     
/* 30 */     if (itemStack != null && itemStack.hasName() && LocaleI18n.c(str2)) {
/* 31 */       return new ChatMessage(str2, new Object[] { paramEntityLiving.getScoreboardDisplayName(), this.p.getScoreboardDisplayName(), itemStack.E() });
/*    */     }
/* 33 */     return new ChatMessage(str1, new Object[] { paramEntityLiving.getScoreboardDisplayName(), this.p.getScoreboardDisplayName() });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean r() {
/* 39 */     return (this.p != null && this.p instanceof EntityLiving && !(this.p instanceof EntityHuman));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityDamageSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */