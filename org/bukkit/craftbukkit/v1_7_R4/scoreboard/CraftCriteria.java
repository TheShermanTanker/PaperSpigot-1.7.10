/*    */ package org.bukkit.craftbukkit.v1_7_R4.scoreboard;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.v1_7_R4.IScoreboardCriteria;
/*    */ import net.minecraft.server.v1_7_R4.ScoreboardObjective;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CraftCriteria
/*    */ {
/*    */   static final Map<String, CraftCriteria> DEFAULTS;
/*    */   
/*    */   static {
/* 15 */     ImmutableMap.Builder<String, CraftCriteria> defaults = ImmutableMap.builder();
/*    */     
/* 17 */     for (Map.Entry<?, ?> entry : (Iterable<Map.Entry<?, ?>>)IScoreboardCriteria.criteria.entrySet()) {
/* 18 */       String name = entry.getKey().toString();
/* 19 */       IScoreboardCriteria criteria = (IScoreboardCriteria)entry.getValue();
/* 20 */       if (!criteria.getName().equals(name)) {
/* 21 */         throw new AssertionError("Unexpected entry " + name + " to criteria " + criteria + "(" + criteria.getName() + ")");
/*    */       }
/*    */       
/* 24 */       defaults.put(name, new CraftCriteria(criteria));
/*    */     } 
/*    */     
/* 27 */     DEFAULTS = (Map<String, CraftCriteria>)defaults.build();
/* 28 */   } static final CraftCriteria DUMMY = DEFAULTS.get("dummy");
/*    */   
/*    */   final IScoreboardCriteria criteria;
/*    */   
/*    */   final String bukkitName;
/*    */   
/*    */   private CraftCriteria(String bukkitName) {
/* 35 */     this.bukkitName = bukkitName;
/* 36 */     this.criteria = DUMMY.criteria;
/*    */   }
/*    */   
/*    */   private CraftCriteria(IScoreboardCriteria criteria) {
/* 40 */     this.criteria = criteria;
/* 41 */     this.bukkitName = criteria.getName();
/*    */   }
/*    */   
/*    */   static CraftCriteria getFromNMS(ScoreboardObjective objective) {
/* 45 */     return DEFAULTS.get(objective.getCriteria().getName());
/*    */   }
/*    */   
/*    */   static CraftCriteria getFromBukkit(String name) {
/* 49 */     CraftCriteria criteria = DEFAULTS.get(name);
/* 50 */     if (criteria != null) {
/* 51 */       return criteria;
/*    */     }
/* 53 */     return new CraftCriteria(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object that) {
/* 58 */     if (!(that instanceof CraftCriteria)) {
/* 59 */       return false;
/*    */     }
/* 61 */     return ((CraftCriteria)that).bukkitName.equals(this.bukkitName);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     return this.bukkitName.hashCode() ^ CraftCriteria.class.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scoreboard\CraftCriteria.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */