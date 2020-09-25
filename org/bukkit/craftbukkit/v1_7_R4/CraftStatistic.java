/*     */ package org.bukkit.craftbukkit.v1_7_R4;
/*     */ 
/*     */ import com.google.common.base.CaseFormat;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.ImmutableBiMap;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.Achievement;
/*     */ import net.minecraft.server.v1_7_R4.EntityTypes;
/*     */ import net.minecraft.server.v1_7_R4.MonsterEggInfo;
/*     */ import net.minecraft.server.v1_7_R4.Statistic;
/*     */ import net.minecraft.server.v1_7_R4.StatisticList;
/*     */ import org.bukkit.Achievement;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Statistic;
/*     */ import org.bukkit.entity.EntityType;
/*     */ 
/*     */ public class CraftStatistic {
/*     */   private static final BiMap<String, Statistic> statistics;
/*     */   
/*     */   static {
/*  22 */     ImmutableMap<String, Achievement> specialCases = ImmutableMap.builder().put("achievement.buildWorkBench", Achievement.BUILD_WORKBENCH).put("achievement.diamonds", Achievement.GET_DIAMONDS).put("achievement.portal", Achievement.NETHER_PORTAL).put("achievement.ghast", Achievement.GHAST_RETURN).put("achievement.theEnd", Achievement.END_PORTAL).put("achievement.theEnd2", Achievement.THE_END).put("achievement.blazeRod", Achievement.GET_BLAZE_ROD).put("achievement.potion", Achievement.BREW_POTION).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  32 */     ImmutableBiMap.Builder<String, Statistic> statisticBuilder = ImmutableBiMap.builder();
/*  33 */     ImmutableBiMap.Builder<String, Achievement> achievementBuilder = ImmutableBiMap.builder();
/*  34 */     for (Statistic statistic : Statistic.values()) {
/*  35 */       if (statistic == Statistic.PLAY_ONE_TICK) {
/*  36 */         statisticBuilder.put("stat.playOneMinute", statistic);
/*     */       } else {
/*  38 */         statisticBuilder.put("stat." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, statistic.name()), statistic);
/*     */       } 
/*     */     } 
/*  41 */     for (Achievement achievement : Achievement.values()) {
/*  42 */       if (!specialCases.values().contains(achievement))
/*     */       {
/*     */         
/*  45 */         achievementBuilder.put("achievement." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, achievement.name()), achievement);
/*     */       }
/*     */     } 
/*  48 */     achievementBuilder.putAll((Map)specialCases);
/*     */     
/*  50 */     statistics = (BiMap<String, Statistic>)statisticBuilder.build();
/*  51 */     achievements = (BiMap<String, Achievement>)achievementBuilder.build();
/*     */   }
/*     */   
/*     */   private static final BiMap<String, Achievement> achievements;
/*     */   
/*     */   public static Achievement getBukkitAchievement(Achievement achievement) {
/*  57 */     return getBukkitAchievementByName(achievement.name);
/*     */   }
/*     */   
/*     */   public static Achievement getBukkitAchievementByName(String name) {
/*  61 */     return (Achievement)achievements.get(name);
/*     */   }
/*     */   
/*     */   public static Statistic getBukkitStatistic(Statistic statistic) {
/*  65 */     return getBukkitStatisticByName(statistic.name);
/*     */   }
/*     */   
/*     */   public static Statistic getBukkitStatisticByName(String name) {
/*  69 */     if (name.startsWith("stat.killEntity")) {
/*  70 */       name = "stat.killEntity";
/*     */     }
/*  72 */     if (name.startsWith("stat.entityKilledBy")) {
/*  73 */       name = "stat.entityKilledBy";
/*     */     }
/*  75 */     if (name.startsWith("stat.breakItem")) {
/*  76 */       name = "stat.breakItem";
/*     */     }
/*  78 */     if (name.startsWith("stat.useItem")) {
/*  79 */       name = "stat.useItem";
/*     */     }
/*  81 */     if (name.startsWith("stat.mineBlock")) {
/*  82 */       name = "stat.mineBlock";
/*     */     }
/*  84 */     if (name.startsWith("stat.craftItem")) {
/*  85 */       name = "stat.craftItem";
/*     */     }
/*  87 */     return (Statistic)statistics.get(name);
/*     */   }
/*     */   
/*     */   public static Statistic getNMSStatistic(Statistic statistic) {
/*  91 */     return StatisticList.getStatistic((String)statistics.inverse().get(statistic));
/*     */   }
/*     */   
/*     */   public static Achievement getNMSAchievement(Achievement achievement) {
/*  95 */     return (Achievement)StatisticList.getStatistic((String)achievements.inverse().get(achievement));
/*     */   }
/*     */   
/*     */   public static Statistic getMaterialStatistic(Statistic stat, Material material) {
/*     */     try {
/* 100 */       if (stat == Statistic.MINE_BLOCK) {
/* 101 */         return StatisticList.MINE_BLOCK_COUNT[material.getId()];
/*     */       }
/* 103 */       if (stat == Statistic.CRAFT_ITEM) {
/* 104 */         return StatisticList.CRAFT_BLOCK_COUNT[material.getId()];
/*     */       }
/* 106 */       if (stat == Statistic.USE_ITEM) {
/* 107 */         return StatisticList.USE_ITEM_COUNT[material.getId()];
/*     */       }
/* 109 */       if (stat == Statistic.BREAK_ITEM) {
/* 110 */         return StatisticList.BREAK_ITEM_COUNT[material.getId()];
/*     */       }
/* 112 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 113 */       return null;
/*     */     } 
/* 115 */     return null;
/*     */   }
/*     */   
/*     */   public static Statistic getEntityStatistic(Statistic stat, EntityType entity) {
/* 119 */     MonsterEggInfo monsteregginfo = (MonsterEggInfo)EntityTypes.eggInfo.get(Integer.valueOf(entity.getTypeId()));
/*     */     
/* 121 */     if (monsteregginfo != null) {
/* 122 */       return monsteregginfo.killEntityStatistic;
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   public static EntityType getEntityTypeFromStatistic(Statistic statistic) {
/* 128 */     String statisticString = statistic.name;
/* 129 */     return EntityType.fromName(statisticString.substring(statisticString.lastIndexOf(".") + 1));
/*     */   }
/*     */   public static Material getMaterialFromStatistic(Statistic statistic) {
/*     */     int id;
/* 133 */     String statisticString = statistic.name;
/*     */     
/*     */     try {
/* 136 */       id = Integer.valueOf(statisticString.substring(statisticString.lastIndexOf(".") + 1)).intValue();
/* 137 */     } catch (NumberFormatException e) {
/* 138 */       return null;
/*     */     } 
/* 140 */     return Material.getMaterial(id);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftStatistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */