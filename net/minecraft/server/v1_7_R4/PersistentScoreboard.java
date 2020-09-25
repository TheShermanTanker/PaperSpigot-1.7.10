/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersistentScoreboard
/*     */   extends PersistentBase
/*     */ {
/*  13 */   private static final Logger a = LogManager.getLogger();
/*     */   
/*     */   private Scoreboard b;
/*     */   
/*     */   private NBTTagCompound c;
/*     */   
/*     */   public PersistentScoreboard() {
/*  20 */     this("scoreboard");
/*     */   }
/*     */   
/*     */   public PersistentScoreboard(String paramString) {
/*  24 */     super(paramString);
/*     */   }
/*     */   
/*     */   public void a(Scoreboard paramScoreboard) {
/*  28 */     this.b = paramScoreboard;
/*     */     
/*  30 */     if (this.c != null) {
/*  31 */       a(this.c);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/*  37 */     if (this.b == null) {
/*  38 */       this.c = paramNBTTagCompound;
/*     */       
/*     */       return;
/*     */     } 
/*  42 */     b(paramNBTTagCompound.getList("Objectives", 10));
/*  43 */     c(paramNBTTagCompound.getList("PlayerScores", 10));
/*     */     
/*  45 */     if (paramNBTTagCompound.hasKeyOfType("DisplaySlots", 10)) {
/*  46 */       c(paramNBTTagCompound.getCompound("DisplaySlots"));
/*     */     }
/*     */     
/*  49 */     if (paramNBTTagCompound.hasKeyOfType("Teams", 9)) {
/*  50 */       a(paramNBTTagCompound.getList("Teams", 10));
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(NBTTagList paramNBTTagList) {
/*  55 */     for (byte b = 0; b < paramNBTTagList.size(); b++) {
/*  56 */       NBTTagCompound nBTTagCompound = paramNBTTagList.get(b);
/*     */       
/*  58 */       ScoreboardTeam scoreboardTeam = this.b.createTeam(nBTTagCompound.getString("Name"));
/*  59 */       scoreboardTeam.setDisplayName(nBTTagCompound.getString("DisplayName"));
/*  60 */       scoreboardTeam.setPrefix(nBTTagCompound.getString("Prefix"));
/*  61 */       scoreboardTeam.setSuffix(nBTTagCompound.getString("Suffix"));
/*  62 */       if (nBTTagCompound.hasKeyOfType("AllowFriendlyFire", 99)) scoreboardTeam.setAllowFriendlyFire(nBTTagCompound.getBoolean("AllowFriendlyFire")); 
/*  63 */       if (nBTTagCompound.hasKeyOfType("SeeFriendlyInvisibles", 99)) scoreboardTeam.setCanSeeFriendlyInvisibles(nBTTagCompound.getBoolean("SeeFriendlyInvisibles"));
/*     */       
/*  65 */       a(scoreboardTeam, nBTTagCompound.getList("Players", 8));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(ScoreboardTeam paramScoreboardTeam, NBTTagList paramNBTTagList) {
/*  70 */     for (byte b = 0; b < paramNBTTagList.size(); b++) {
/*  71 */       this.b.addPlayerToTeam(paramNBTTagList.getString(b), paramScoreboardTeam.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void c(NBTTagCompound paramNBTTagCompound) {
/*  76 */     for (byte b = 0; b < 3; b++) {
/*  77 */       if (paramNBTTagCompound.hasKeyOfType("slot_" + b, 8)) {
/*  78 */         String str = paramNBTTagCompound.getString("slot_" + b);
/*  79 */         ScoreboardObjective scoreboardObjective = this.b.getObjective(str);
/*  80 */         this.b.setDisplaySlot(b, scoreboardObjective);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(NBTTagList paramNBTTagList) {
/*  86 */     for (byte b = 0; b < paramNBTTagList.size(); b++) {
/*  87 */       NBTTagCompound nBTTagCompound = paramNBTTagList.get(b);
/*     */       
/*  89 */       IScoreboardCriteria iScoreboardCriteria = (IScoreboardCriteria)IScoreboardCriteria.criteria.get(nBTTagCompound.getString("CriteriaName"));
/*  90 */       ScoreboardObjective scoreboardObjective = this.b.registerObjective(nBTTagCompound.getString("Name"), iScoreboardCriteria);
/*  91 */       scoreboardObjective.setDisplayName(nBTTagCompound.getString("DisplayName"));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void c(NBTTagList paramNBTTagList) {
/*  96 */     for (byte b = 0; b < paramNBTTagList.size(); b++) {
/*  97 */       NBTTagCompound nBTTagCompound = paramNBTTagList.get(b);
/*     */       
/*  99 */       ScoreboardObjective scoreboardObjective = this.b.getObjective(nBTTagCompound.getString("Objective"));
/* 100 */       ScoreboardScore scoreboardScore = this.b.getPlayerScoreForObjective(nBTTagCompound.getString("Name"), scoreboardObjective);
/* 101 */       scoreboardScore.setScore(nBTTagCompound.getInt("Score"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 107 */     if (this.b == null) {
/* 108 */       a.warn("Tried to save scoreboard without having a scoreboard...");
/*     */       
/*     */       return;
/*     */     } 
/* 112 */     paramNBTTagCompound.set("Objectives", b());
/* 113 */     paramNBTTagCompound.set("PlayerScores", e());
/* 114 */     paramNBTTagCompound.set("Teams", a());
/*     */     
/* 116 */     d(paramNBTTagCompound);
/*     */   }
/*     */   
/*     */   protected NBTTagList a() {
/* 120 */     NBTTagList nBTTagList = new NBTTagList();
/* 121 */     Collection collection = this.b.getTeams();
/*     */     
/* 123 */     for (ScoreboardTeam scoreboardTeam : collection) {
/* 124 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*     */       
/* 126 */       nBTTagCompound.setString("Name", scoreboardTeam.getName());
/* 127 */       nBTTagCompound.setString("DisplayName", scoreboardTeam.getDisplayName());
/* 128 */       nBTTagCompound.setString("Prefix", scoreboardTeam.getPrefix());
/* 129 */       nBTTagCompound.setString("Suffix", scoreboardTeam.getSuffix());
/* 130 */       nBTTagCompound.setBoolean("AllowFriendlyFire", scoreboardTeam.allowFriendlyFire());
/* 131 */       nBTTagCompound.setBoolean("SeeFriendlyInvisibles", scoreboardTeam.canSeeFriendlyInvisibles());
/*     */       
/* 133 */       NBTTagList nBTTagList1 = new NBTTagList();
/*     */       
/* 135 */       for (String str : scoreboardTeam.getPlayerNameSet()) {
/* 136 */         nBTTagList1.add(new NBTTagString(str));
/*     */       }
/*     */       
/* 139 */       nBTTagCompound.set("Players", nBTTagList1);
/*     */       
/* 141 */       nBTTagList.add(nBTTagCompound);
/*     */     } 
/*     */     
/* 144 */     return nBTTagList;
/*     */   }
/*     */   
/*     */   protected void d(NBTTagCompound paramNBTTagCompound) {
/* 148 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 149 */     boolean bool = false;
/*     */     
/* 151 */     for (byte b = 0; b < 3; b++) {
/* 152 */       ScoreboardObjective scoreboardObjective = this.b.getObjectiveForSlot(b);
/*     */       
/* 154 */       if (scoreboardObjective != null) {
/* 155 */         nBTTagCompound.setString("slot_" + b, scoreboardObjective.getName());
/* 156 */         bool = true;
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     if (bool) paramNBTTagCompound.set("DisplaySlots", nBTTagCompound); 
/*     */   }
/*     */   
/*     */   protected NBTTagList b() {
/* 164 */     NBTTagList nBTTagList = new NBTTagList();
/* 165 */     Collection collection = this.b.getObjectives();
/*     */     
/* 167 */     for (ScoreboardObjective scoreboardObjective : collection) {
/* 168 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*     */       
/* 170 */       nBTTagCompound.setString("Name", scoreboardObjective.getName());
/* 171 */       nBTTagCompound.setString("CriteriaName", scoreboardObjective.getCriteria().getName());
/* 172 */       nBTTagCompound.setString("DisplayName", scoreboardObjective.getDisplayName());
/*     */       
/* 174 */       nBTTagList.add(nBTTagCompound);
/*     */     } 
/*     */     
/* 177 */     return nBTTagList;
/*     */   }
/*     */   
/*     */   protected NBTTagList e() {
/* 181 */     NBTTagList nBTTagList = new NBTTagList();
/* 182 */     Collection collection = this.b.getScores();
/*     */     
/* 184 */     for (ScoreboardScore scoreboardScore : collection) {
/* 185 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*     */       
/* 187 */       nBTTagCompound.setString("Name", scoreboardScore.getPlayerName());
/* 188 */       nBTTagCompound.setString("Objective", scoreboardScore.getObjective().getName());
/* 189 */       nBTTagCompound.setInt("Score", scoreboardScore.getScore());
/*     */       
/* 191 */       nBTTagList.add(nBTTagCompound);
/*     */     } 
/*     */     
/* 194 */     return nBTTagList;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PersistentScoreboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */