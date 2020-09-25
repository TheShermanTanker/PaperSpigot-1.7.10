/*    */ package org.bukkit.entity;public interface Horse extends Animals, Vehicle, InventoryHolder, Tameable { Variant getVariant();
/*    */   void setVariant(Variant paramVariant);
/*    */   Color getColor();
/*    */   void setColor(Color paramColor);
/*    */   Style getStyle();
/*    */   void setStyle(Style paramStyle);
/*    */   boolean isCarryingChest();
/*    */   void setCarryingChest(boolean paramBoolean);
/*    */   int getDomestication();
/*    */   void setDomestication(int paramInt);
/*    */   int getMaxDomestication();
/*    */   void setMaxDomestication(int paramInt);
/*    */   double getJumpStrength();
/*    */   void setJumpStrength(double paramDouble);
/*    */   
/*    */   HorseInventory getInventory();
/*    */   
/* 18 */   public enum Variant { HORSE,
/*    */ 
/*    */ 
/*    */     
/* 22 */     DONKEY,
/*    */ 
/*    */ 
/*    */     
/* 26 */     MULE,
/*    */ 
/*    */ 
/*    */     
/* 30 */     UNDEAD_HORSE,
/*    */ 
/*    */ 
/*    */     
/* 34 */     SKELETON_HORSE; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Color
/*    */   {
/* 45 */     WHITE,
/*    */ 
/*    */ 
/*    */     
/* 49 */     CREAMY,
/*    */ 
/*    */ 
/*    */     
/* 53 */     CHESTNUT,
/*    */ 
/*    */ 
/*    */     
/* 57 */     BROWN,
/*    */ 
/*    */ 
/*    */     
/* 61 */     BLACK,
/*    */ 
/*    */ 
/*    */     
/* 65 */     GRAY,
/*    */ 
/*    */ 
/*    */     
/* 69 */     DARK_BROWN;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Style
/*    */   {
/* 80 */     NONE,
/*    */ 
/*    */ 
/*    */     
/* 84 */     WHITE,
/*    */ 
/*    */ 
/*    */     
/* 88 */     WHITEFIELD,
/*    */ 
/*    */ 
/*    */     
/* 92 */     WHITE_DOTS,
/*    */ 
/*    */ 
/*    */     
/* 96 */     BLACK_DOTS;
/*    */   } }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Horse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */