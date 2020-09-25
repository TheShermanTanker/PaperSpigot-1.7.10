/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class ScoreboardScore
/*    */ {
/*  9 */   public static final Comparator a = new ScoreboardComparator();
/*    */ 
/*    */ 
/*    */   
/*    */   private final Scoreboard b;
/*    */ 
/*    */ 
/*    */   
/*    */   private final ScoreboardObjective c;
/*    */ 
/*    */   
/*    */   private final String playerName;
/*    */ 
/*    */   
/*    */   private int score;
/*    */ 
/*    */ 
/*    */   
/*    */   public ScoreboardScore(Scoreboard paramScoreboard, ScoreboardObjective paramScoreboardObjective, String paramString) {
/* 28 */     this.b = paramScoreboard;
/* 29 */     this.c = paramScoreboardObjective;
/* 30 */     this.playerName = paramString;
/*    */   }
/*    */   
/*    */   public void addScore(int paramInt) {
/* 34 */     if (this.c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score"); 
/* 35 */     setScore(getScore() + paramInt);
/*    */   }
/*    */   
/*    */   public void removeScore(int paramInt) {
/* 39 */     if (this.c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score"); 
/* 40 */     setScore(getScore() - paramInt);
/*    */   }
/*    */   
/*    */   public void incrementScore() {
/* 44 */     if (this.c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score"); 
/* 45 */     addScore(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getScore() {
/* 54 */     return this.score;
/*    */   }
/*    */   
/*    */   public void setScore(int paramInt) {
/* 58 */     int i = this.score;
/* 59 */     this.score = paramInt;
/* 60 */     if (i != paramInt) f().handleScoreChanged(this); 
/*    */   }
/*    */   
/*    */   public ScoreboardObjective getObjective() {
/* 64 */     return this.c;
/*    */   }
/*    */   
/*    */   public String getPlayerName() {
/* 68 */     return this.playerName;
/*    */   }
/*    */   
/*    */   public Scoreboard f() {
/* 72 */     return this.b;
/*    */   }
/*    */   
/*    */   public void updateForList(List paramList) {
/* 76 */     setScore(this.c.getCriteria().getScoreModifier(paramList));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ScoreboardScore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */