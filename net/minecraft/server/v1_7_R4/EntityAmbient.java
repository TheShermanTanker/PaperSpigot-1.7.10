/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntityAmbient
/*    */   extends EntityInsentient
/*    */   implements IAnimal
/*    */ {
/*    */   public EntityAmbient(World paramWorld) {
/* 10 */     super(paramWorld);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean bM() {
/* 15 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean a(EntityHuman paramEntityHuman) {
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityAmbient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */