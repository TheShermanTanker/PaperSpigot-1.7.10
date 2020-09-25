/*    */ package org.bukkit.craftbukkit.v1_7_R4.scoreboard;
/*    */ 
/*    */ abstract class CraftScoreboardComponent {
/*    */   private CraftScoreboard scoreboard;
/*    */   
/*    */   CraftScoreboardComponent(CraftScoreboard scoreboard) {
/*  7 */     this.scoreboard = scoreboard;
/*    */   }
/*    */   
/*    */   CraftScoreboard checkState() throws IllegalStateException {
/* 11 */     CraftScoreboard scoreboard = this.scoreboard;
/* 12 */     if (scoreboard == null) {
/* 13 */       throw new IllegalStateException("Unregistered scoreboard component");
/*    */     }
/* 15 */     return scoreboard;
/*    */   }
/*    */   
/*    */   public CraftScoreboard getScoreboard() {
/* 19 */     return this.scoreboard;
/*    */   }
/*    */   
/*    */   abstract void unregister() throws IllegalStateException;
/*    */   
/*    */   final void setUnregistered() {
/* 25 */     this.scoreboard = null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scoreboard\CraftScoreboardComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */