/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GameRules
/*     */ {
/*  20 */   private TreeMap a = new TreeMap<Object, Object>();
/*     */   
/*     */   public GameRules() {
/*  23 */     a("doFireTick", "true");
/*  24 */     a("mobGriefing", "true");
/*  25 */     a("keepInventory", "false");
/*  26 */     a("doMobSpawning", "true");
/*  27 */     a("doMobLoot", "true");
/*  28 */     a("doTileDrops", "true");
/*  29 */     a("commandBlockOutput", "true");
/*  30 */     a("naturalRegeneration", "true");
/*  31 */     a("doDaylightCycle", "true");
/*     */   }
/*     */   
/*     */   public void a(String paramString1, String paramString2) {
/*  35 */     this.a.put(paramString1, new GameRuleValue(paramString2));
/*     */   }
/*     */   
/*     */   public void set(String paramString1, String paramString2) {
/*  39 */     GameRuleValue gameRuleValue = (GameRuleValue)this.a.get(paramString1);
/*  40 */     if (gameRuleValue != null) {
/*  41 */       gameRuleValue.a(paramString2);
/*     */     } else {
/*  43 */       a(paramString1, paramString2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String get(String paramString) {
/*  48 */     GameRuleValue gameRuleValue = (GameRuleValue)this.a.get(paramString);
/*  49 */     if (gameRuleValue != null) {
/*  50 */       return gameRuleValue.a();
/*     */     }
/*  52 */     return "";
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String paramString) {
/*  56 */     GameRuleValue gameRuleValue = (GameRuleValue)this.a.get(paramString);
/*  57 */     if (gameRuleValue != null) {
/*  58 */       return gameRuleValue.b();
/*     */     }
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound a() {
/*  80 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*     */     
/*  82 */     for (String str : this.a.keySet()) {
/*  83 */       GameRuleValue gameRuleValue = (GameRuleValue)this.a.get(str);
/*  84 */       nBTTagCompound.setString(str, gameRuleValue.a());
/*     */     } 
/*     */     
/*  87 */     return nBTTagCompound;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/*  91 */     Set set = paramNBTTagCompound.c();
/*  92 */     for (String str1 : set) {
/*  93 */       String str2 = str1;
/*  94 */       String str3 = paramNBTTagCompound.getString(str1);
/*     */       
/*  96 */       set(str2, str3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String[] getGameRules() {
/* 101 */     return (String[])this.a.keySet().toArray((Object[])new String[0]);
/*     */   }
/*     */   
/*     */   public boolean contains(String paramString) {
/* 105 */     return this.a.containsKey(paramString);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GameRules.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */