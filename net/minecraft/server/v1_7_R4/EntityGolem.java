/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public abstract class EntityGolem
/*    */   extends EntityCreature
/*    */   implements IAnimal
/*    */ {
/*    */   public EntityGolem(World paramWorld) {
/*  8 */     super(paramWorld);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void b(float paramFloat) {}
/*    */ 
/*    */   
/*    */   protected String t() {
/* 17 */     return "none";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String aT() {
/* 22 */     return "none";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String aU() {
/* 27 */     return "none";
/*    */   }
/*    */ 
/*    */   
/*    */   public int q() {
/* 32 */     return 120;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isTypeNotPersistent() {
/* 37 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */