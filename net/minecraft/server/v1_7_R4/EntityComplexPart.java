/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityComplexPart
/*    */   extends Entity
/*    */ {
/*    */   public final IComplex owner;
/*    */   public final String b;
/*    */   
/*    */   public EntityComplexPart(IComplex paramIComplex, String paramString, float paramFloat1, float paramFloat2) {
/* 13 */     super(paramIComplex.a());
/* 14 */     a(paramFloat1, paramFloat2);
/* 15 */     this.owner = paramIComplex;
/* 16 */     this.b = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void c() {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void a(NBTTagCompound paramNBTTagCompound) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void b(NBTTagCompound paramNBTTagCompound) {}
/*    */ 
/*    */   
/*    */   public boolean R() {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean damageEntity(DamageSource paramDamageSource, float paramFloat) {
/* 38 */     if (isInvulnerable()) return false; 
/* 39 */     return this.owner.a(this, paramDamageSource, paramFloat);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean i(Entity paramEntity) {
/* 44 */     return (this == paramEntity || this.owner == paramEntity);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityComplexPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */