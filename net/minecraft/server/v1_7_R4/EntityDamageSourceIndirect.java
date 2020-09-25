/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class EntityDamageSourceIndirect
/*    */   extends EntityDamageSource {
/*    */   private Entity owner;
/*    */   
/*    */   public EntityDamageSourceIndirect(String s, Entity entity, Entity entity1) {
/*  8 */     super(s, entity);
/*  9 */     this.owner = entity1;
/*    */   }
/*    */   
/*    */   public Entity i() {
/* 13 */     return this.p;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 17 */     return this.owner;
/*    */   }
/*    */   
/*    */   public IChatBaseComponent getLocalizedDeathMessage(EntityLiving entityliving) {
/* 21 */     IChatBaseComponent ichatbasecomponent = (this.owner == null) ? this.p.getScoreboardDisplayName() : this.owner.getScoreboardDisplayName();
/* 22 */     ItemStack itemstack = (this.owner instanceof EntityLiving) ? ((EntityLiving)this.owner).be() : null;
/* 23 */     String s = "death.attack." + this.translationIndex;
/* 24 */     String s1 = s + ".item";
/*    */     
/* 26 */     return (itemstack != null && itemstack.hasName() && LocaleI18n.c(s1)) ? new ChatMessage(s1, new Object[] { entityliving.getScoreboardDisplayName(), ichatbasecomponent, itemstack.E() }) : new ChatMessage(s, new Object[] { entityliving.getScoreboardDisplayName(), ichatbasecomponent });
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getProximateDamageSource() {
/* 31 */     return super.getEntity();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityDamageSourceIndirect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */