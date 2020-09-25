/*     */ package org.bukkit.craftbukkit.v1_7_R4.scoreboard;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*     */ import net.minecraft.server.v1_7_R4.IScoreboardCriteria;
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import net.minecraft.server.v1_7_R4.Packet;
/*     */ import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardObjective;
/*     */ import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardTeam;
/*     */ import net.minecraft.server.v1_7_R4.Scoreboard;
/*     */ import net.minecraft.server.v1_7_R4.ScoreboardObjective;
/*     */ import net.minecraft.server.v1_7_R4.ScoreboardScore;
/*     */ import net.minecraft.server.v1_7_R4.ScoreboardServer;
/*     */ import net.minecraft.server.v1_7_R4.ScoreboardTeam;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.WeakCollection;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ import org.bukkit.scoreboard.ScoreboardManager;
/*     */ import org.spigotmc.AsyncCatcher;
/*     */ 
/*     */ public final class CraftScoreboardManager implements ScoreboardManager {
/*     */   private final CraftScoreboard mainScoreboard;
/*  31 */   private final Collection<CraftScoreboard> scoreboards = (Collection<CraftScoreboard>)new WeakCollection(); private final MinecraftServer server;
/*  32 */   private final Map<CraftPlayer, CraftScoreboard> playerBoards = new HashMap<CraftPlayer, CraftScoreboard>();
/*     */   
/*     */   public CraftScoreboardManager(MinecraftServer minecraftserver, Scoreboard scoreboardServer) {
/*  35 */     this.mainScoreboard = new CraftScoreboard(scoreboardServer);
/*  36 */     this.server = minecraftserver;
/*  37 */     this.scoreboards.add(this.mainScoreboard);
/*     */   }
/*     */   
/*     */   public CraftScoreboard getMainScoreboard() {
/*  41 */     return this.mainScoreboard;
/*     */   }
/*     */   
/*     */   public CraftScoreboard getNewScoreboard() {
/*  45 */     AsyncCatcher.catchOp("scoreboard creation");
/*  46 */     CraftScoreboard scoreboard = new CraftScoreboard((Scoreboard)new ScoreboardServer(this.server));
/*  47 */     this.scoreboards.add(scoreboard);
/*  48 */     return scoreboard;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftScoreboard getPlayerBoard(CraftPlayer player) {
/*  53 */     CraftScoreboard board = this.playerBoards.get(player);
/*  54 */     return (board == null) ? getMainScoreboard() : board;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerBoard(CraftPlayer player, Scoreboard bukkitScoreboard) throws IllegalArgumentException {
/*  59 */     Validate.isTrue(bukkitScoreboard instanceof CraftScoreboard, "Cannot set player scoreboard to an unregistered Scoreboard");
/*     */     
/*  61 */     CraftScoreboard scoreboard = (CraftScoreboard)bukkitScoreboard;
/*  62 */     Scoreboard oldboard = getPlayerBoard(player).getHandle();
/*  63 */     Scoreboard newboard = scoreboard.getHandle();
/*  64 */     EntityPlayer entityplayer = player.getHandle();
/*     */     
/*  66 */     if (oldboard == newboard) {
/*     */       return;
/*     */     }
/*     */     
/*  70 */     if (scoreboard == this.mainScoreboard) {
/*  71 */       this.playerBoards.remove(player);
/*     */     } else {
/*  73 */       this.playerBoards.put(player, scoreboard);
/*     */     } 
/*     */ 
/*     */     
/*  77 */     HashSet<ScoreboardObjective> removed = new HashSet<ScoreboardObjective>();
/*  78 */     for (int i = 0; i < 3; i++) {
/*  79 */       ScoreboardObjective scoreboardobjective = oldboard.getObjectiveForSlot(i);
/*  80 */       if (scoreboardobjective != null && !removed.contains(scoreboardobjective)) {
/*  81 */         entityplayer.playerConnection.sendPacket((Packet)new PacketPlayOutScoreboardObjective(scoreboardobjective, 1));
/*  82 */         removed.add(scoreboardobjective);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  87 */     Iterator<?> iterator = oldboard.getTeams().iterator();
/*  88 */     while (iterator.hasNext()) {
/*  89 */       ScoreboardTeam scoreboardteam = (ScoreboardTeam)iterator.next();
/*  90 */       entityplayer.playerConnection.sendPacket((Packet)new PacketPlayOutScoreboardTeam(scoreboardteam, 1));
/*     */     } 
/*     */ 
/*     */     
/*  94 */     this.server.getPlayerList().sendScoreboard((ScoreboardServer)newboard, player.getHandle());
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayer(Player player) {
/*  99 */     this.playerBoards.remove(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ScoreboardScore> getScoreboardScores(IScoreboardCriteria criteria, String name, Collection<ScoreboardScore> collection) {
/* 104 */     for (CraftScoreboard scoreboard : this.scoreboards) {
/* 105 */       Scoreboard board = scoreboard.board;
/* 106 */       for (ScoreboardObjective objective : board.getObjectivesForCriteria(criteria)) {
/* 107 */         collection.add(board.getPlayerScoreForObjective(name, objective));
/*     */       }
/*     */     } 
/* 110 */     return collection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAllScoresForList(IScoreboardCriteria criteria, String name, List<EntityPlayer> of) {
/* 115 */     for (ScoreboardScore score : getScoreboardScores(criteria, name, new ArrayList<ScoreboardScore>()))
/* 116 */       score.updateForList(of); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scoreboard\CraftScoreboardManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */