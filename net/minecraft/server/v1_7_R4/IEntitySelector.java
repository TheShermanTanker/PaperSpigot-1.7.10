/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IEntitySelector
/*    */ {
/*  8 */   public static final IEntitySelector a = new EntitySelectorLiving();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 14 */   public static final IEntitySelector b = new EntitySelectorChickenJockey();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 20 */   public static final IEntitySelector c = new EntitySelectorContainer();
/*    */   
/*    */   boolean a(Entity paramEntity);
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IEntitySelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */