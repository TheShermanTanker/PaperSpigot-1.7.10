/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ScoreboardServer
/*     */   extends Scoreboard {
/*     */   private final MinecraftServer a;
/*  13 */   private final Set b = new HashSet();
/*     */   private PersistentScoreboard c;
/*     */   
/*     */   public ScoreboardServer(MinecraftServer minecraftserver) {
/*  17 */     this.a = minecraftserver;
/*     */   }
/*     */   
/*     */   public void handleScoreChanged(ScoreboardScore scoreboardscore) {
/*  21 */     super.handleScoreChanged(scoreboardscore);
/*  22 */     if (this.b.contains(scoreboardscore.getObjective())) {
/*  23 */       sendAll(new PacketPlayOutScoreboardScore(scoreboardscore, 0));
/*     */     }
/*     */     
/*  26 */     b();
/*     */   }
/*     */   
/*     */   public void handlePlayerRemoved(String s) {
/*  30 */     super.handlePlayerRemoved(s);
/*  31 */     sendAll(new PacketPlayOutScoreboardScore(s));
/*  32 */     b();
/*     */   }
/*     */   
/*     */   public void setDisplaySlot(int i, ScoreboardObjective scoreboardobjective) {
/*  36 */     ScoreboardObjective scoreboardobjective1 = getObjectiveForSlot(i);
/*     */     
/*  38 */     super.setDisplaySlot(i, scoreboardobjective);
/*  39 */     if (scoreboardobjective1 != scoreboardobjective && scoreboardobjective1 != null) {
/*  40 */       if (h(scoreboardobjective1) > 0) {
/*  41 */         sendAll(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
/*     */       } else {
/*  43 */         g(scoreboardobjective1);
/*     */       } 
/*     */     }
/*     */     
/*  47 */     if (scoreboardobjective != null) {
/*  48 */       if (this.b.contains(scoreboardobjective)) {
/*  49 */         sendAll(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
/*     */       } else {
/*  51 */         e(scoreboardobjective);
/*     */       } 
/*     */     }
/*     */     
/*  55 */     b();
/*     */   }
/*     */   
/*     */   public boolean addPlayerToTeam(String s, String s1) {
/*  59 */     if (super.addPlayerToTeam(s, s1)) {
/*  60 */       ScoreboardTeam scoreboardteam = getTeam(s1);
/*     */       
/*  62 */       sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, Arrays.asList(new String[] { s }, ), 3));
/*  63 */       b();
/*  64 */       return true;
/*     */     } 
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String s, ScoreboardTeam scoreboardteam) {
/*  71 */     super.removePlayerFromTeam(s, scoreboardteam);
/*  72 */     sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, Arrays.asList(new String[] { s }, ), 4));
/*  73 */     b();
/*     */   }
/*     */   
/*     */   public void handleObjectiveAdded(ScoreboardObjective scoreboardobjective) {
/*  77 */     super.handleObjectiveAdded(scoreboardobjective);
/*  78 */     b();
/*     */   }
/*     */   
/*     */   public void handleObjectiveChanged(ScoreboardObjective scoreboardobjective) {
/*  82 */     super.handleObjectiveChanged(scoreboardobjective);
/*  83 */     if (this.b.contains(scoreboardobjective)) {
/*  84 */       sendAll(new PacketPlayOutScoreboardObjective(scoreboardobjective, 2));
/*     */     }
/*     */     
/*  87 */     b();
/*     */   }
/*     */   
/*     */   public void handleObjectiveRemoved(ScoreboardObjective scoreboardobjective) {
/*  91 */     super.handleObjectiveRemoved(scoreboardobjective);
/*  92 */     if (this.b.contains(scoreboardobjective)) {
/*  93 */       g(scoreboardobjective);
/*     */     }
/*     */     
/*  96 */     b();
/*     */   }
/*     */   
/*     */   public void handleTeamAdded(ScoreboardTeam scoreboardteam) {
/* 100 */     super.handleTeamAdded(scoreboardteam);
/* 101 */     sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, 0));
/* 102 */     b();
/*     */   }
/*     */   
/*     */   public void handleTeamChanged(ScoreboardTeam scoreboardteam) {
/* 106 */     super.handleTeamChanged(scoreboardteam);
/* 107 */     sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, 2));
/* 108 */     b();
/*     */   }
/*     */   
/*     */   public void handleTeamRemoved(ScoreboardTeam scoreboardteam) {
/* 112 */     super.handleTeamRemoved(scoreboardteam);
/* 113 */     sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, 1));
/* 114 */     b();
/*     */   }
/*     */   
/*     */   public void a(PersistentScoreboard persistentscoreboard) {
/* 118 */     this.c = persistentscoreboard;
/*     */   }
/*     */   
/*     */   protected void b() {
/* 122 */     if (this.c != null) {
/* 123 */       this.c.c();
/*     */     }
/*     */   }
/*     */   
/*     */   public List getScoreboardScorePacketsForObjective(ScoreboardObjective scoreboardobjective) {
/* 128 */     ArrayList<PacketPlayOutScoreboardObjective> arraylist = new ArrayList();
/*     */     
/* 130 */     arraylist.add(new PacketPlayOutScoreboardObjective(scoreboardobjective, 0));
/*     */     
/* 132 */     for (int i = 0; i < 3; i++) {
/* 133 */       if (getObjectiveForSlot(i) == scoreboardobjective) {
/* 134 */         arraylist.add(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
/*     */       }
/*     */     } 
/*     */     
/* 138 */     Iterator<ScoreboardScore> iterator = getScoresForObjective(scoreboardobjective).iterator();
/*     */     
/* 140 */     while (iterator.hasNext()) {
/* 141 */       ScoreboardScore scoreboardscore = iterator.next();
/*     */       
/* 143 */       arraylist.add(new PacketPlayOutScoreboardScore(scoreboardscore, 0));
/*     */     } 
/*     */     
/* 146 */     return arraylist;
/*     */   }
/*     */   
/*     */   public void e(ScoreboardObjective scoreboardobjective) {
/* 150 */     List list = getScoreboardScorePacketsForObjective(scoreboardobjective);
/* 151 */     Iterator<EntityPlayer> iterator = (this.a.getPlayerList()).players.iterator();
/*     */     
/* 153 */     while (iterator.hasNext()) {
/* 154 */       EntityPlayer entityplayer = iterator.next();
/* 155 */       if (entityplayer.getBukkitEntity().getScoreboard().getHandle() != this)
/* 156 */         continue;  Iterator<Packet> iterator1 = list.iterator();
/*     */       
/* 158 */       while (iterator1.hasNext()) {
/* 159 */         Packet packet = iterator1.next();
/*     */         
/* 161 */         entityplayer.playerConnection.sendPacket(packet);
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     this.b.add(scoreboardobjective);
/*     */   }
/*     */   
/*     */   public List f(ScoreboardObjective scoreboardobjective) {
/* 169 */     ArrayList<PacketPlayOutScoreboardObjective> arraylist = new ArrayList();
/*     */     
/* 171 */     arraylist.add(new PacketPlayOutScoreboardObjective(scoreboardobjective, 1));
/*     */     
/* 173 */     for (int i = 0; i < 3; i++) {
/* 174 */       if (getObjectiveForSlot(i) == scoreboardobjective) {
/* 175 */         arraylist.add(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
/*     */       }
/*     */     } 
/*     */     
/* 179 */     return arraylist;
/*     */   }
/*     */   
/*     */   public void g(ScoreboardObjective scoreboardobjective) {
/* 183 */     List list = f(scoreboardobjective);
/* 184 */     Iterator<EntityPlayer> iterator = (this.a.getPlayerList()).players.iterator();
/*     */     
/* 186 */     while (iterator.hasNext()) {
/* 187 */       EntityPlayer entityplayer = iterator.next();
/* 188 */       if (entityplayer.getBukkitEntity().getScoreboard().getHandle() != this)
/* 189 */         continue;  Iterator<Packet> iterator1 = list.iterator();
/*     */       
/* 191 */       while (iterator1.hasNext()) {
/* 192 */         Packet packet = iterator1.next();
/*     */         
/* 194 */         entityplayer.playerConnection.sendPacket(packet);
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     this.b.remove(scoreboardobjective);
/*     */   }
/*     */   
/*     */   public int h(ScoreboardObjective scoreboardobjective) {
/* 202 */     int i = 0;
/*     */     
/* 204 */     for (int j = 0; j < 3; j++) {
/* 205 */       if (getObjectiveForSlot(j) == scoreboardobjective) {
/* 206 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 210 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendAll(Packet packet) {
/* 215 */     for (EntityPlayer entityplayer : (this.a.getPlayerList()).players) {
/* 216 */       if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this)
/* 217 */         entityplayer.playerConnection.sendPacket(packet); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ScoreboardServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */