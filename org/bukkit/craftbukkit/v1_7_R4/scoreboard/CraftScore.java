/*    */ package org.bukkit.craftbukkit.v1_7_R4.scoreboard;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.v1_7_R4.Scoreboard;
/*    */ import net.minecraft.server.v1_7_R4.ScoreboardObjective;
/*    */ import net.minecraft.server.v1_7_R4.ScoreboardScore;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.scoreboard.Objective;
/*    */ import org.bukkit.scoreboard.Score;
/*    */ import org.bukkit.scoreboard.Scoreboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CraftScore
/*    */   implements Score
/*    */ {
/*    */   private final String entry;
/*    */   private final CraftObjective objective;
/*    */   
/*    */   CraftScore(CraftObjective objective, String entry) {
/* 24 */     this.objective = objective;
/* 25 */     this.entry = entry;
/*    */   }
/*    */   
/*    */   public OfflinePlayer getPlayer() {
/* 29 */     return Bukkit.getOfflinePlayer(this.entry);
/*    */   }
/*    */   
/*    */   public String getEntry() {
/* 33 */     return this.entry;
/*    */   }
/*    */   
/*    */   public Objective getObjective() {
/* 37 */     return this.objective;
/*    */   }
/*    */   
/*    */   public int getScore() throws IllegalStateException {
/* 41 */     Scoreboard board = (this.objective.checkState()).board;
/*    */     
/* 43 */     if (board.getPlayers().contains(this.entry)) {
/* 44 */       Map<ScoreboardObjective, ScoreboardScore> scores = board.getPlayerObjectives(this.entry);
/* 45 */       ScoreboardScore score = scores.get(this.objective.getHandle());
/* 46 */       if (score != null) {
/* 47 */         return score.getScore();
/*    */       }
/*    */     } 
/*    */     
/* 51 */     return 0;
/*    */   }
/*    */   
/*    */   public void setScore(int score) throws IllegalStateException {
/* 55 */     (this.objective.checkState()).board.getPlayerScoreForObjective(this.entry, this.objective.getHandle()).setScore(score);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isScoreSet() throws IllegalStateException {
/* 61 */     Scoreboard board = (this.objective.checkState()).board;
/*    */     
/* 63 */     return (board.getPlayers().contains(this.entry) && board.getPlayerObjectives(this.entry).containsKey(this.objective.getHandle()));
/*    */   }
/*    */ 
/*    */   
/*    */   public CraftScoreboard getScoreboard() {
/* 68 */     return this.objective.getScoreboard();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scoreboard\CraftScore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */