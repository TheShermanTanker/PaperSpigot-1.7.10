/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public interface IScoreboardCriteria
/*    */ {
/* 10 */   public static final Map criteria = new HashMap<Object, Object>();
/*    */   
/* 12 */   public static final IScoreboardCriteria b = new ScoreboardBaseCriteria("dummy");
/* 13 */   public static final IScoreboardCriteria c = new ScoreboardBaseCriteria("deathCount");
/* 14 */   public static final IScoreboardCriteria d = new ScoreboardBaseCriteria("playerKillCount");
/* 15 */   public static final IScoreboardCriteria e = new ScoreboardBaseCriteria("totalKillCount");
/* 16 */   public static final IScoreboardCriteria f = new ScoreboardHealthCriteria("health");
/*    */   
/*    */   String getName();
/*    */   
/*    */   int getScoreModifier(List paramList);
/*    */   
/*    */   boolean isReadOnly();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IScoreboardCriteria.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */