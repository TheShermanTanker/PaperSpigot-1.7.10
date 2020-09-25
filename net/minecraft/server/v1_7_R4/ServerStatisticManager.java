/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ import net.minecraft.util.com.google.common.collect.Sets;
/*     */ import net.minecraft.util.com.google.gson.JsonElement;
/*     */ import net.minecraft.util.com.google.gson.JsonObject;
/*     */ import net.minecraft.util.com.google.gson.JsonParseException;
/*     */ import net.minecraft.util.com.google.gson.JsonParser;
/*     */ import net.minecraft.util.org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class ServerStatisticManager
/*     */   extends StatisticManager
/*     */ {
/*  25 */   private static final Logger b = LogManager.getLogger();
/*     */   private final MinecraftServer c;
/*     */   private final File d;
/*  28 */   private final Set e = Sets.newHashSet();
/*  29 */   private int f = -300;
/*     */   private boolean g = false;
/*     */   
/*     */   public ServerStatisticManager(MinecraftServer minecraftserver, File file1) {
/*  33 */     this.c = minecraftserver;
/*  34 */     this.d = file1;
/*     */     
/*  36 */     for (String name : SpigotConfig.forcedStats.keySet()) {
/*     */       
/*  38 */       StatisticWrapper wrapper = new StatisticWrapper();
/*  39 */       wrapper.a(SpigotConfig.forcedStats.get(name));
/*  40 */       this.a.put(StatisticList.getStatistic(name), wrapper);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a() {
/*  46 */     if (this.d.isFile()) {
/*     */       try {
/*  48 */         this.a.clear();
/*  49 */         this.a.putAll(a(FileUtils.readFileToString(this.d)));
/*  50 */       } catch (IOException ioexception) {
/*  51 */         b.error("Couldn't read statistics file " + this.d, ioexception);
/*  52 */       } catch (JsonParseException jsonparseexception) {
/*  53 */         b.error("Couldn't parse statistics file " + this.d, (Throwable)jsonparseexception);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void b() {
/*  59 */     if (SpigotConfig.disableStatSaving)
/*     */       return;  try {
/*  61 */       FileUtils.writeStringToFile(this.d, a(this.a));
/*  62 */     } catch (IOException ioexception) {
/*  63 */       b.error("Couldn't save stats", ioexception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setStatistic(EntityHuman entityhuman, Statistic statistic, int i) {
/*  68 */     if (SpigotConfig.disableStatSaving)
/*  69 */       return;  int j = statistic.d() ? getStatisticValue(statistic) : 0;
/*     */     
/*  71 */     super.setStatistic(entityhuman, statistic, i);
/*  72 */     this.e.add(statistic);
/*  73 */     if (statistic.d() && j == 0 && i > 0) {
/*  74 */       this.g = true;
/*  75 */       if (this.c.at()) {
/*  76 */         this.c.getPlayerList().sendMessage(new ChatMessage("chat.type.achievement", new Object[] { entityhuman.getScoreboardDisplayName(), statistic.j() }));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set c() {
/*  82 */     HashSet hashset = Sets.newHashSet(this.e);
/*     */     
/*  84 */     this.e.clear();
/*  85 */     this.g = false;
/*  86 */     return hashset;
/*     */   }
/*     */   
/*     */   public Map a(String s) {
/*  90 */     JsonElement jsonelement = (new JsonParser()).parse(s);
/*     */     
/*  92 */     if (!jsonelement.isJsonObject()) {
/*  93 */       return Maps.newHashMap();
/*     */     }
/*  95 */     JsonObject jsonobject = jsonelement.getAsJsonObject();
/*  96 */     HashMap<Statistic, StatisticWrapper> hashmap = Maps.newHashMap();
/*  97 */     Iterator<Map.Entry> iterator = jsonobject.entrySet().iterator();
/*     */     
/*  99 */     while (iterator.hasNext()) {
/* 100 */       Map.Entry entry = iterator.next();
/* 101 */       Statistic statistic = StatisticList.getStatistic((String)entry.getKey());
/*     */       
/* 103 */       if (statistic != null) {
/* 104 */         StatisticWrapper statisticwrapper = new StatisticWrapper();
/*     */         
/* 106 */         if (((JsonElement)entry.getValue()).isJsonPrimitive() && ((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber()) {
/* 107 */           statisticwrapper.a(((JsonElement)entry.getValue()).getAsInt());
/* 108 */         } else if (((JsonElement)entry.getValue()).isJsonObject()) {
/* 109 */           JsonObject jsonobject1 = ((JsonElement)entry.getValue()).getAsJsonObject();
/*     */           
/* 111 */           if (jsonobject1.has("value") && jsonobject1.get("value").isJsonPrimitive() && jsonobject1.get("value").getAsJsonPrimitive().isNumber()) {
/* 112 */             statisticwrapper.a(jsonobject1.getAsJsonPrimitive("value").getAsInt());
/*     */           }
/*     */           
/* 115 */           if (jsonobject1.has("progress") && statistic.l() != null) {
/*     */             try {
/* 117 */               Constructor<IJsonStatistic> constructor = statistic.l().getConstructor(new Class[0]);
/* 118 */               IJsonStatistic ijsonstatistic = constructor.newInstance(new Object[0]);
/*     */               
/* 120 */               ijsonstatistic.a(jsonobject1.get("progress"));
/* 121 */               statisticwrapper.a(ijsonstatistic);
/* 122 */             } catch (Throwable throwable) {
/* 123 */               b.warn("Invalid statistic progress in " + this.d, throwable);
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 128 */         hashmap.put(statistic, statisticwrapper); continue;
/*     */       } 
/* 130 */       b.warn("Invalid statistic in " + this.d + ": Don't know what " + (String)entry.getKey() + " is");
/*     */     } 
/*     */ 
/*     */     
/* 134 */     return hashmap;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String a(Map map) {
/* 139 */     JsonObject jsonobject = new JsonObject();
/* 140 */     Iterator<Map.Entry> iterator = map.entrySet().iterator();
/*     */     
/* 142 */     while (iterator.hasNext()) {
/* 143 */       Map.Entry entry = iterator.next();
/*     */       
/* 145 */       if (((StatisticWrapper)entry.getValue()).b() != null) {
/* 146 */         JsonObject jsonobject1 = new JsonObject();
/*     */         
/* 148 */         jsonobject1.addProperty("value", Integer.valueOf(((StatisticWrapper)entry.getValue()).a()));
/*     */         
/*     */         try {
/* 151 */           jsonobject1.add("progress", ((StatisticWrapper)entry.getValue()).b().a());
/* 152 */         } catch (Throwable throwable) {
/* 153 */           b.warn("Couldn't save statistic " + ((Statistic)entry.getKey()).e() + ": error serializing progress", throwable);
/*     */         } 
/*     */         
/* 156 */         jsonobject.add(((Statistic)entry.getKey()).name, (JsonElement)jsonobject1); continue;
/*     */       } 
/* 158 */       jsonobject.addProperty(((Statistic)entry.getKey()).name, Integer.valueOf(((StatisticWrapper)entry.getValue()).a()));
/*     */     } 
/*     */ 
/*     */     
/* 162 */     return jsonobject.toString();
/*     */   }
/*     */   
/*     */   public void d() {
/* 166 */     Iterator<Statistic> iterator = this.a.keySet().iterator();
/*     */     
/* 168 */     while (iterator.hasNext()) {
/* 169 */       Statistic statistic = iterator.next();
/*     */       
/* 171 */       this.e.add(statistic);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(EntityPlayer entityplayer) {
/* 176 */     int i = this.c.al();
/* 177 */     HashMap<Statistic, Integer> hashmap = Maps.newHashMap();
/*     */     
/* 179 */     if (this.g || i - this.f > 300) {
/* 180 */       this.f = i;
/* 181 */       Iterator<Statistic> iterator = c().iterator();
/*     */       
/* 183 */       while (iterator.hasNext()) {
/* 184 */         Statistic statistic = iterator.next();
/*     */         
/* 186 */         hashmap.put(statistic, Integer.valueOf(getStatisticValue(statistic)));
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     entityplayer.playerConnection.sendPacket(new PacketPlayOutStatistic(hashmap));
/*     */   }
/*     */   
/*     */   public void updateStatistics(EntityPlayer entityplayer) {
/* 194 */     HashMap<Achievement, Integer> hashmap = Maps.newHashMap();
/* 195 */     Iterator<Achievement> iterator = AchievementList.e.iterator();
/*     */     
/* 197 */     while (iterator.hasNext()) {
/* 198 */       Achievement achievement = iterator.next();
/*     */       
/* 200 */       if (hasAchievement(achievement)) {
/* 201 */         hashmap.put(achievement, Integer.valueOf(getStatisticValue(achievement)));
/* 202 */         this.e.remove(achievement);
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     entityplayer.playerConnection.sendPacket(new PacketPlayOutStatistic(hashmap));
/*     */   }
/*     */   
/*     */   public boolean e() {
/* 210 */     return this.g;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerStatisticManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */