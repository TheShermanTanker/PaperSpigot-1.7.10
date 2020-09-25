/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class ScoreboardBaseCriteria
/*    */   implements IScoreboardCriteria
/*    */ {
/*    */   private final String g;
/*    */   
/*    */   public ScoreboardBaseCriteria(String paramString) {
/* 11 */     this.g = paramString;
/* 12 */     IScoreboardCriteria.criteria.put(paramString, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 17 */     return this.g;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getScoreModifier(List paramList) {
/* 22 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isReadOnly() {
/* 27 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ScoreboardBaseCriteria.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */