/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityMinecartChest
/*    */   extends EntityMinecartContainer
/*    */ {
/*    */   public EntityMinecartChest(World paramWorld) {
/* 11 */     super(paramWorld);
/*    */   }
/*    */   
/*    */   public EntityMinecartChest(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 15 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(DamageSource paramDamageSource) {
/* 20 */     super.a(paramDamageSource);
/*    */     
/* 22 */     a(Item.getItemOf(Blocks.CHEST), 1, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 27 */     return 27;
/*    */   }
/*    */ 
/*    */   
/*    */   public int m() {
/* 32 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Block o() {
/* 37 */     return Blocks.CHEST;
/*    */   }
/*    */ 
/*    */   
/*    */   public int s() {
/* 42 */     return 8;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMinecartChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */