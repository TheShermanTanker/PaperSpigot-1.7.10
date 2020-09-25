/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.com.google.common.collect.Maps;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.event.Cancellable;
/*    */ 
/*    */ public class StatisticManager {
/*  9 */   protected final Map a = Maps.newConcurrentMap();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasAchievement(Achievement achievement) {
/* 15 */     return (getStatisticValue(achievement) > 0);
/*    */   }
/*    */   
/*    */   public boolean b(Achievement achievement) {
/* 19 */     return (achievement.c == null || hasAchievement(achievement.c));
/*    */   }
/*    */   
/*    */   public void b(EntityHuman entityhuman, Statistic statistic, int i) {
/* 23 */     if (!statistic.d() || b((Achievement)statistic)) {
/*    */       
/* 25 */       Cancellable cancellable = CraftEventFactory.handleStatisticsIncrease(entityhuman, statistic, getStatisticValue(statistic), i);
/* 26 */       if (cancellable != null && cancellable.isCancelled()) {
/*    */         return;
/*    */       }
/*    */       
/* 30 */       setStatistic(entityhuman, statistic, getStatisticValue(statistic) + i);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setStatistic(EntityHuman entityhuman, Statistic statistic, int i) {
/* 35 */     StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
/*    */     
/* 37 */     if (statisticwrapper == null) {
/* 38 */       statisticwrapper = new StatisticWrapper();
/* 39 */       this.a.put(statistic, statisticwrapper);
/*    */     } 
/*    */     
/* 42 */     statisticwrapper.a(i);
/*    */   }
/*    */   
/*    */   public int getStatisticValue(Statistic statistic) {
/* 46 */     StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
/*    */     
/* 48 */     return (statisticwrapper == null) ? 0 : statisticwrapper.a();
/*    */   }
/*    */   
/*    */   public IJsonStatistic b(Statistic statistic) {
/* 52 */     StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
/*    */     
/* 54 */     return (statisticwrapper != null) ? statisticwrapper.b() : null;
/*    */   }
/*    */   
/*    */   public IJsonStatistic a(Statistic statistic, IJsonStatistic ijsonstatistic) {
/* 58 */     StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
/*    */     
/* 60 */     if (statisticwrapper == null) {
/* 61 */       statisticwrapper = new StatisticWrapper();
/* 62 */       this.a.put(statistic, statisticwrapper);
/*    */     } 
/*    */     
/* 65 */     statisticwrapper.a(ijsonstatistic);
/* 66 */     return ijsonstatistic;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\StatisticManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */