/*    */ package org.bukkit.entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Arrow
/*    */   extends Projectile
/*    */ {
/*    */   int getKnockbackStrength();
/*    */   
/*    */   void setKnockbackStrength(int paramInt);
/*    */   
/*    */   boolean isCritical();
/*    */   
/*    */   void setCritical(boolean paramBoolean);
/*    */   
/*    */   Spigot spigot();
/*    */   
/*    */   public static class Spigot
/*    */     extends Entity.Spigot
/*    */   {
/*    */     public double getDamage() {
/* 48 */       throw new UnsupportedOperationException("Not supported yet.");
/*    */     }
/*    */ 
/*    */     
/*    */     public void setDamage(double damage) {
/* 53 */       throw new UnsupportedOperationException("Not supported yet.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Arrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */