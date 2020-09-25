/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ScoreboardComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int a(ScoreboardScore paramScoreboardScore1, ScoreboardScore paramScoreboardScore2) {
/* 12 */     if (paramScoreboardScore1.getScore() > paramScoreboardScore2.getScore())
/* 13 */       return 1; 
/* 14 */     if (paramScoreboardScore1.getScore() < paramScoreboardScore2.getScore()) {
/* 15 */       return -1;
/*    */     }
/* 17 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ScoreboardComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */