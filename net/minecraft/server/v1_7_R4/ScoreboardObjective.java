/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScoreboardObjective
/*    */ {
/*    */   private final Scoreboard a;
/*    */   private final String b;
/*    */   private final IScoreboardCriteria c;
/*    */   private String d;
/*    */   
/*    */   public ScoreboardObjective(Scoreboard paramScoreboard, String paramString, IScoreboardCriteria paramIScoreboardCriteria) {
/* 15 */     this.a = paramScoreboard;
/* 16 */     this.b = paramString;
/* 17 */     this.c = paramIScoreboardCriteria;
/*    */     
/* 19 */     this.d = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 27 */     return this.b;
/*    */   }
/*    */   
/*    */   public IScoreboardCriteria getCriteria() {
/* 31 */     return this.c;
/*    */   }
/*    */   
/*    */   public String getDisplayName() {
/* 35 */     return this.d;
/*    */   }
/*    */   
/*    */   public void setDisplayName(String paramString) {
/* 39 */     this.d = paramString;
/* 40 */     this.a.handleObjectiveChanged(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ScoreboardObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */