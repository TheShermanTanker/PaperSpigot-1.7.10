/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityMinecartRideable
/*    */   extends EntityMinecartAbstract
/*    */ {
/*    */   public EntityMinecartRideable(World paramWorld) {
/* 10 */     super(paramWorld);
/*    */   }
/*    */   
/*    */   public EntityMinecartRideable(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 14 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean c(EntityHuman paramEntityHuman) {
/* 19 */     if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != paramEntityHuman) return true; 
/* 20 */     if (this.passenger != null && this.passenger != paramEntityHuman) return false; 
/* 21 */     if (!this.world.isStatic) {
/* 22 */       paramEntityHuman.mount(this);
/*    */     }
/*    */     
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int m() {
/* 30 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMinecartRideable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */