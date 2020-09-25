/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ScoreboardTeamBase
/*    */ {
/*    */   public boolean isAlly(ScoreboardTeamBase paramScoreboardTeamBase) {
/*  8 */     if (paramScoreboardTeamBase == null) {
/*  9 */       return false;
/*    */     }
/* 11 */     if (this == paramScoreboardTeamBase) {
/* 12 */       return true;
/*    */     }
/* 14 */     return false;
/*    */   }
/*    */   
/*    */   public abstract String getName();
/*    */   
/*    */   public abstract String getFormattedName(String paramString);
/*    */   
/*    */   public abstract boolean allowFriendlyFire();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ScoreboardTeamBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */