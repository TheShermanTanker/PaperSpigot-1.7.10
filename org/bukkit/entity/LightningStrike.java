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
/*    */ public interface LightningStrike
/*    */   extends Weather
/*    */ {
/*    */   boolean isEffect();
/*    */   
/*    */   Spigot spigot();
/*    */   
/*    */   public static class Spigot
/*    */     extends Entity.Spigot
/*    */   {
/*    */     public boolean isSilent() {
/* 26 */       throw new UnsupportedOperationException("Not supported yet.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\LightningStrike.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */