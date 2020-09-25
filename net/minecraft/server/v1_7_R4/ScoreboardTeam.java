/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScoreboardTeam
/*     */   extends ScoreboardTeamBase
/*     */ {
/*     */   private final Scoreboard a;
/*     */   private final String b;
/*  16 */   private final Set c = new HashSet();
/*     */   private String d;
/*  18 */   private String e = "";
/*  19 */   private String f = "";
/*     */   private boolean g = true;
/*     */   private boolean h = true;
/*     */   
/*     */   public ScoreboardTeam(Scoreboard paramScoreboard, String paramString) {
/*  24 */     this.a = paramScoreboard;
/*  25 */     this.b = paramString;
/*  26 */     this.d = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  35 */     return this.b;
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/*  39 */     return this.d;
/*     */   }
/*     */   
/*     */   public void setDisplayName(String paramString) {
/*  43 */     if (paramString == null) throw new IllegalArgumentException("Name cannot be null"); 
/*  44 */     this.d = paramString;
/*  45 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */   
/*     */   public Collection getPlayerNameSet() {
/*  49 */     return this.c;
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/*  53 */     return this.e;
/*     */   }
/*     */   
/*     */   public void setPrefix(String paramString) {
/*  57 */     if (paramString == null) throw new IllegalArgumentException("Prefix cannot be null"); 
/*  58 */     this.e = paramString;
/*  59 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */   
/*     */   public String getSuffix() {
/*  63 */     return this.f;
/*     */   }
/*     */   
/*     */   public void setSuffix(String paramString) {
/*  67 */     if (paramString == null) throw new IllegalArgumentException("Suffix cannot be null"); 
/*  68 */     this.f = paramString;
/*  69 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormattedName(String paramString) {
/*  74 */     return getPrefix() + paramString + getSuffix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPlayerDisplayName(ScoreboardTeamBase paramScoreboardTeamBase, String paramString) {
/*  82 */     if (paramScoreboardTeamBase == null) return paramString; 
/*  83 */     return paramScoreboardTeamBase.getFormattedName(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowFriendlyFire() {
/*  88 */     return this.g;
/*     */   }
/*     */   
/*     */   public void setAllowFriendlyFire(boolean paramBoolean) {
/*  92 */     this.g = paramBoolean;
/*  93 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSeeFriendlyInvisibles() {
/*  98 */     return this.h;
/*     */   }
/*     */   
/*     */   public void setCanSeeFriendlyInvisibles(boolean paramBoolean) {
/* 102 */     this.h = paramBoolean;
/* 103 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */   
/*     */   public int packOptionData() {
/* 107 */     int i = 0;
/*     */     
/* 109 */     if (allowFriendlyFire()) i |= 0x1; 
/* 110 */     if (canSeeFriendlyInvisibles()) i |= 0x2;
/*     */     
/* 112 */     return i;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ScoreboardTeam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */