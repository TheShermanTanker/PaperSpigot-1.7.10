/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ import org.bukkit.scoreboard.Objective;
/*     */ import org.bukkit.scoreboard.Score;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ import org.bukkit.scoreboard.Team;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ 
/*     */ public class ScoreboardCommand
/*     */   extends VanillaCommand
/*     */ {
/*  31 */   private static final List<String> MAIN_CHOICES = (List<String>)ImmutableList.of("objectives", "players", "teams");
/*  32 */   private static final List<String> OBJECTIVES_CHOICES = (List<String>)ImmutableList.of("list", "add", "remove", "setdisplay");
/*  33 */   private static final List<String> OBJECTIVES_CRITERIA = (List<String>)ImmutableList.of("health", "playerKillCount", "totalKillCount", "deathCount", "dummy");
/*  34 */   private static final List<String> PLAYERS_CHOICES = (List<String>)ImmutableList.of("set", "add", "remove", "reset", "list");
/*  35 */   private static final List<String> TEAMS_CHOICES = (List<String>)ImmutableList.of("add", "remove", "join", "leave", "empty", "list", "option");
/*  36 */   private static final List<String> TEAMS_OPTION_CHOICES = (List<String>)ImmutableList.of("color", "friendlyfire", "seeFriendlyInvisibles");
/*  37 */   private static final Map<String, DisplaySlot> OBJECTIVES_DISPLAYSLOT = (Map<String, DisplaySlot>)ImmutableMap.of("belowName", DisplaySlot.BELOW_NAME, "list", DisplaySlot.PLAYER_LIST, "sidebar", DisplaySlot.SIDEBAR);
/*  38 */   private static final Map<String, ChatColor> TEAMS_OPTION_COLOR = (Map<String, ChatColor>)ImmutableMap.builder().put("aqua", ChatColor.AQUA).put("black", ChatColor.BLACK).put("blue", ChatColor.BLUE).put("bold", ChatColor.BOLD).put("dark_aqua", ChatColor.DARK_AQUA).put("dark_blue", ChatColor.DARK_BLUE).put("dark_gray", ChatColor.DARK_GRAY).put("dark_green", ChatColor.DARK_GREEN).put("dark_purple", ChatColor.DARK_PURPLE).put("dark_red", ChatColor.DARK_RED).put("gold", ChatColor.GOLD).put("gray", ChatColor.GRAY).put("green", ChatColor.GREEN).put("italic", ChatColor.ITALIC).put("light_purple", ChatColor.LIGHT_PURPLE).put("obfuscated", ChatColor.MAGIC).put("red", ChatColor.RED).put("reset", ChatColor.RESET).put("strikethrough", ChatColor.STRIKETHROUGH).put("underline", ChatColor.UNDERLINE).put("white", ChatColor.WHITE).put("yellow", ChatColor.YELLOW).build();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final List<String> BOOLEAN = (List<String>)ImmutableList.of("true", "false");
/*     */   
/*     */   public ScoreboardCommand() {
/*  65 */     super("scoreboard");
/*  66 */     this.description = "Scoreboard control";
/*  67 */     this.usageMessage = "/scoreboard";
/*  68 */     setPermission("bukkit.command.scoreboard");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/*  73 */     if (!testPermission(sender))
/*  74 */       return true; 
/*  75 */     if (args.length < 1 || args[0].length() == 0) {
/*  76 */       sender.sendMessage(ChatColor.RED + "Usage: /scoreboard <objectives|players|teams>");
/*  77 */       return false;
/*     */     } 
/*     */     
/*  80 */     Scoreboard mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
/*     */     
/*  82 */     if (args[0].equalsIgnoreCase("objectives")) {
/*  83 */       if (args.length == 1) {
/*  84 */         sender.sendMessage(ChatColor.RED + "Usage: /scoreboard objectives <list|add|remove|setdisplay>");
/*  85 */         return false;
/*     */       } 
/*  87 */       if (args[1].equalsIgnoreCase("list")) {
/*  88 */         Set<Objective> objectives = mainScoreboard.getObjectives();
/*  89 */         if (objectives.isEmpty()) {
/*  90 */           sender.sendMessage(ChatColor.RED + "There are no objectives on the scoreboard");
/*  91 */           return false;
/*     */         } 
/*  93 */         sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + objectives.size() + " objective(s) on scoreboard");
/*  94 */         for (Objective objective : objectives) {
/*  95 */           sender.sendMessage("- " + objective.getName() + ": displays as '" + objective.getDisplayName() + "' and is type '" + objective.getCriteria() + "'");
/*     */         }
/*  97 */       } else if (args[1].equalsIgnoreCase("add")) {
/*  98 */         if (args.length < 4) {
/*  99 */           sender.sendMessage(ChatColor.RED + "/scoreboard objectives add <name> <criteriaType> [display name ...]");
/* 100 */           return false;
/*     */         } 
/* 102 */         String name = args[2];
/* 103 */         String criteria = args[3];
/*     */         
/* 105 */         if (criteria == null) {
/* 106 */           sender.sendMessage(ChatColor.RED + "Invalid objective criteria type. Valid types are: " + stringCollectionToString(OBJECTIVES_CRITERIA));
/* 107 */         } else if (name.length() > 16) {
/* 108 */           sender.sendMessage(ChatColor.RED + "The name '" + name + "' is too long for an objective, it can be at most 16 characters long");
/* 109 */         } else if (mainScoreboard.getObjective(name) != null) {
/* 110 */           sender.sendMessage(ChatColor.RED + "An objective with the name '" + name + "' already exists");
/*     */         } else {
/* 112 */           String displayName = null;
/* 113 */           if (args.length > 4) {
/* 114 */             displayName = StringUtils.join(ArrayUtils.subarray((Object[])args, 4, args.length), ' ');
/* 115 */             if (displayName.length() > 32) {
/* 116 */               sender.sendMessage(ChatColor.RED + "The name '" + displayName + "' is too long for an objective, it can be at most 32 characters long");
/* 117 */               return false;
/*     */             } 
/*     */           } 
/* 120 */           Objective objective = mainScoreboard.registerNewObjective(name, criteria);
/* 121 */           if (displayName != null && displayName.length() > 0) {
/* 122 */             objective.setDisplayName(displayName);
/*     */           }
/* 124 */           sender.sendMessage("Added new objective '" + name + "' successfully");
/*     */         } 
/* 126 */       } else if (args[1].equalsIgnoreCase("remove")) {
/* 127 */         if (args.length != 3) {
/* 128 */           sender.sendMessage(ChatColor.RED + "/scoreboard objectives remove <name>");
/* 129 */           return false;
/*     */         } 
/* 131 */         String name = args[2];
/* 132 */         Objective objective = mainScoreboard.getObjective(name);
/* 133 */         if (objective == null) {
/* 134 */           sender.sendMessage(ChatColor.RED + "No objective was found by the name '" + name + "'");
/*     */         } else {
/* 136 */           objective.unregister();
/* 137 */           sender.sendMessage("Removed objective '" + name + "' successfully");
/*     */         } 
/* 139 */       } else if (args[1].equalsIgnoreCase("setdisplay")) {
/* 140 */         if (args.length != 3 && args.length != 4) {
/* 141 */           sender.sendMessage(ChatColor.RED + "/scoreboard objectives setdisplay <slot> [objective]");
/* 142 */           return false;
/*     */         } 
/* 144 */         String slotName = args[2];
/* 145 */         DisplaySlot slot = OBJECTIVES_DISPLAYSLOT.get(slotName);
/* 146 */         if (slot == null) {
/* 147 */           sender.sendMessage(ChatColor.RED + "No such display slot '" + slotName + "'");
/*     */         }
/* 149 */         else if (args.length == 4) {
/* 150 */           String objectiveName = args[3];
/* 151 */           Objective objective = mainScoreboard.getObjective(objectiveName);
/* 152 */           if (objective == null) {
/* 153 */             sender.sendMessage(ChatColor.RED + "No objective was found by the name '" + objectiveName + "'");
/* 154 */             return false;
/*     */           } 
/*     */           
/* 157 */           objective.setDisplaySlot(slot);
/* 158 */           sender.sendMessage("Set the display objective in slot '" + slotName + "' to '" + objective.getName() + "'");
/*     */         } else {
/* 160 */           Objective objective = mainScoreboard.getObjective(slot);
/* 161 */           if (objective != null) {
/* 162 */             objective.setDisplaySlot(null);
/*     */           }
/* 164 */           sender.sendMessage("Cleared objective display slot '" + slotName + "'");
/*     */         }
/*     */       
/*     */       } 
/* 168 */     } else if (args[0].equalsIgnoreCase("players")) {
/* 169 */       if (args.length == 1) {
/* 170 */         sender.sendMessage(ChatColor.RED + "/scoreboard players <set|add|remove|reset|list>");
/* 171 */         return false;
/*     */       } 
/* 173 */       if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) {
/* 174 */         int value, newScore; if (args.length != 5) {
/* 175 */           if (args[1].equalsIgnoreCase("set")) {
/* 176 */             sender.sendMessage(ChatColor.RED + "/scoreboard players set <player> <objective> <score>");
/* 177 */           } else if (args[1].equalsIgnoreCase("add")) {
/* 178 */             sender.sendMessage(ChatColor.RED + "/scoreboard players add <player> <objective> <count>");
/*     */           } else {
/* 180 */             sender.sendMessage(ChatColor.RED + "/scoreboard players remove <player> <objective> <count>");
/*     */           } 
/* 182 */           return false;
/*     */         } 
/* 184 */         String objectiveName = args[3];
/* 185 */         Objective objective = mainScoreboard.getObjective(objectiveName);
/* 186 */         if (objective == null) {
/* 187 */           sender.sendMessage(ChatColor.RED + "No objective was found by the name '" + objectiveName + "'");
/* 188 */           return false;
/* 189 */         }  if (!objective.isModifiable()) {
/* 190 */           sender.sendMessage(ChatColor.RED + "The objective '" + objectiveName + "' is read-only and cannot be set");
/* 191 */           return false;
/*     */         } 
/*     */         
/* 194 */         String valueString = args[4];
/*     */         
/*     */         try {
/* 197 */           value = Integer.parseInt(valueString);
/* 198 */         } catch (NumberFormatException e) {
/* 199 */           sender.sendMessage(ChatColor.RED + "'" + valueString + "' is not a valid number");
/* 200 */           return false;
/*     */         } 
/* 202 */         if (value < 1 && !args[1].equalsIgnoreCase("set")) {
/* 203 */           sender.sendMessage(ChatColor.RED + "The number you have entered (" + value + ") is too small, it must be at least 1");
/* 204 */           return false;
/*     */         } 
/*     */         
/* 207 */         String playerName = args[2];
/* 208 */         if (playerName.length() > 16) {
/* 209 */           sender.sendMessage(ChatColor.RED + "'" + playerName + "' is too long for a player name");
/* 210 */           return false;
/*     */         } 
/* 212 */         Score score = objective.getScore(playerName);
/*     */         
/* 214 */         if (args[1].equalsIgnoreCase("set")) {
/* 215 */           newScore = value;
/* 216 */         } else if (args[1].equalsIgnoreCase("add")) {
/* 217 */           newScore = score.getScore() + value;
/*     */         } else {
/* 219 */           newScore = score.getScore() - value;
/*     */         } 
/* 221 */         score.setScore(newScore);
/* 222 */         sender.sendMessage("Set score of " + objectiveName + " for player " + playerName + " to " + newScore);
/* 223 */       } else if (args[1].equalsIgnoreCase("reset")) {
/* 224 */         if (args.length != 3) {
/* 225 */           sender.sendMessage(ChatColor.RED + "/scoreboard players reset <player>");
/* 226 */           return false;
/*     */         } 
/* 228 */         String playerName = args[2];
/* 229 */         if (playerName.length() > 16) {
/* 230 */           sender.sendMessage(ChatColor.RED + "'" + playerName + "' is too long for a player name");
/* 231 */           return false;
/*     */         } 
/* 233 */         mainScoreboard.resetScores(playerName);
/* 234 */         sender.sendMessage("Reset all scores of player " + playerName);
/* 235 */       } else if (args[1].equalsIgnoreCase("list")) {
/* 236 */         if (args.length > 3) {
/* 237 */           sender.sendMessage(ChatColor.RED + "/scoreboard players list <player>");
/* 238 */           return false;
/*     */         } 
/* 240 */         if (args.length == 2) {
/* 241 */           Set<String> entries = mainScoreboard.getEntries();
/* 242 */           if (entries.isEmpty()) {
/* 243 */             sender.sendMessage(ChatColor.RED + "There are no tracked players on the scoreboard");
/*     */           } else {
/* 245 */             sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + entries.size() + " tracked players on the scoreboard");
/* 246 */             sender.sendMessage(stringCollectionToString(entries));
/*     */           } 
/*     */         } else {
/* 249 */           String playerName = args[2];
/* 250 */           if (playerName.length() > 16) {
/* 251 */             sender.sendMessage(ChatColor.RED + "'" + playerName + "' is too long for a player name");
/* 252 */             return false;
/*     */           } 
/* 254 */           Set<Score> scores = mainScoreboard.getScores(playerName);
/* 255 */           if (scores.isEmpty()) {
/* 256 */             sender.sendMessage(ChatColor.RED + "Player " + playerName + " has no scores recorded");
/*     */           } else {
/* 258 */             sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + scores.size() + " tracked objective(s) for " + playerName);
/* 259 */             for (Score score : scores) {
/* 260 */               sender.sendMessage("- " + score.getObjective().getDisplayName() + ": " + score.getScore() + " (" + score.getObjective().getName() + ")");
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 265 */     } else if (args[0].equalsIgnoreCase("teams")) {
/* 266 */       if (args.length == 1) {
/* 267 */         sender.sendMessage(ChatColor.RED + "/scoreboard teams <list|add|remove|empty|join|leave|option>");
/* 268 */         return false;
/*     */       } 
/* 270 */       if (args[1].equalsIgnoreCase("list")) {
/* 271 */         if (args.length == 2) {
/* 272 */           Set<Team> teams = mainScoreboard.getTeams();
/* 273 */           if (teams.isEmpty()) {
/* 274 */             sender.sendMessage(ChatColor.RED + "There are no teams registered on the scoreboard");
/*     */           } else {
/* 276 */             sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + teams.size() + " teams on the scoreboard");
/* 277 */             for (Team team : teams) {
/* 278 */               sender.sendMessage("- " + team.getName() + ": '" + team.getDisplayName() + "' has " + team.getSize() + " players");
/*     */             }
/*     */           } 
/* 281 */         } else if (args.length == 3) {
/* 282 */           String teamName = args[2];
/* 283 */           Team team = mainScoreboard.getTeam(teamName);
/* 284 */           if (team == null) {
/* 285 */             sender.sendMessage(ChatColor.RED + "No team was found by the name '" + teamName + "'");
/*     */           } else {
/* 287 */             Set<OfflinePlayer> players = team.getPlayers();
/* 288 */             if (players.isEmpty()) {
/* 289 */               sender.sendMessage(ChatColor.RED + "Team " + team.getName() + " has no players");
/*     */             } else {
/* 291 */               sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + players.size() + " player(s) in team " + team.getName());
/* 292 */               sender.sendMessage(offlinePlayerSetToString(players));
/*     */             } 
/*     */           } 
/*     */         } else {
/* 296 */           sender.sendMessage(ChatColor.RED + "/scoreboard teams list [name]");
/* 297 */           return false;
/*     */         } 
/* 299 */       } else if (args[1].equalsIgnoreCase("add")) {
/* 300 */         if (args.length < 3) {
/* 301 */           sender.sendMessage(ChatColor.RED + "/scoreboard teams add <name> [display name ...]");
/* 302 */           return false;
/*     */         } 
/* 304 */         String name = args[2];
/* 305 */         if (name.length() > 16) {
/* 306 */           sender.sendMessage(ChatColor.RED + "The name '" + name + "' is too long for a team, it can be at most 16 characters long");
/* 307 */         } else if (mainScoreboard.getTeam(name) != null) {
/* 308 */           sender.sendMessage(ChatColor.RED + "A team with the name '" + name + "' already exists");
/*     */         } else {
/* 310 */           String displayName = null;
/* 311 */           if (args.length > 3) {
/* 312 */             displayName = StringUtils.join(ArrayUtils.subarray((Object[])args, 3, args.length), ' ');
/* 313 */             if (displayName.length() > 32) {
/* 314 */               sender.sendMessage(ChatColor.RED + "The display name '" + displayName + "' is too long for a team, it can be at most 32 characters long");
/* 315 */               return false;
/*     */             } 
/*     */           } 
/* 318 */           Team team = mainScoreboard.registerNewTeam(name);
/* 319 */           if (displayName != null && displayName.length() > 0) {
/* 320 */             team.setDisplayName(displayName);
/*     */           }
/* 322 */           sender.sendMessage("Added new team '" + team.getName() + "' successfully");
/*     */         } 
/* 324 */       } else if (args[1].equalsIgnoreCase("remove")) {
/* 325 */         if (args.length != 3) {
/* 326 */           sender.sendMessage(ChatColor.RED + "/scoreboard teams remove <name>");
/* 327 */           return false;
/*     */         } 
/* 329 */         String name = args[2];
/* 330 */         Team team = mainScoreboard.getTeam(name);
/* 331 */         if (team == null) {
/* 332 */           sender.sendMessage(ChatColor.RED + "No team was found by the name '" + name + "'");
/*     */         } else {
/* 334 */           team.unregister();
/* 335 */           sender.sendMessage("Removed team " + name);
/*     */         } 
/* 337 */       } else if (args[1].equalsIgnoreCase("empty")) {
/* 338 */         if (args.length != 3) {
/* 339 */           sender.sendMessage(ChatColor.RED + "/scoreboard teams clear <name>");
/* 340 */           return false;
/*     */         } 
/* 342 */         String name = args[2];
/* 343 */         Team team = mainScoreboard.getTeam(name);
/* 344 */         if (team == null) {
/* 345 */           sender.sendMessage(ChatColor.RED + "No team was found by the name '" + name + "'");
/*     */         } else {
/* 347 */           Set<OfflinePlayer> players = team.getPlayers();
/* 348 */           if (players.isEmpty()) {
/* 349 */             sender.sendMessage(ChatColor.RED + "Team " + team.getName() + " is already empty, cannot remove nonexistant players");
/*     */           } else {
/* 351 */             for (OfflinePlayer player : players) {
/* 352 */               team.removePlayer(player);
/*     */             }
/* 354 */             sender.sendMessage("Removed all " + players.size() + " player(s) from team " + team.getName());
/*     */           } 
/*     */         } 
/* 357 */       } else if (args[1].equalsIgnoreCase("join")) {
/* 358 */         if ((sender instanceof Player) ? (args.length < 3) : (args.length < 4)) {
/* 359 */           sender.sendMessage(ChatColor.RED + "/scoreboard teams join <team> [player...]");
/* 360 */           return false;
/*     */         } 
/* 362 */         String teamName = args[2];
/* 363 */         Team team = mainScoreboard.getTeam(teamName);
/* 364 */         if (team == null) {
/* 365 */           sender.sendMessage(ChatColor.RED + "No team was found by the name '" + teamName + "'");
/*     */         } else {
/* 367 */           Set<String> addedPlayers = new HashSet<String>();
/* 368 */           if (sender instanceof Player && args.length == 3) {
/* 369 */             team.addPlayer((OfflinePlayer)sender);
/* 370 */             addedPlayers.add(sender.getName());
/*     */           } else {
/* 372 */             for (int i = 3; i < args.length; i++) {
/* 373 */               OfflinePlayer offlinePlayer; String playerName = args[i];
/*     */               
/* 375 */               Player player = Bukkit.getPlayerExact(playerName);
/* 376 */               if (player != null) {
/* 377 */                 Player player1 = player;
/*     */               } else {
/* 379 */                 offlinePlayer = Bukkit.getOfflinePlayer(playerName);
/*     */               } 
/* 381 */               team.addPlayer(offlinePlayer);
/* 382 */               addedPlayers.add(offlinePlayer.getName());
/*     */             } 
/*     */           } 
/* 385 */           sender.sendMessage("Added " + addedPlayers.size() + " player(s) to team " + team.getName() + ": " + stringCollectionToString(addedPlayers));
/*     */         } 
/* 387 */       } else if (args[1].equalsIgnoreCase("leave")) {
/* 388 */         if (!(sender instanceof Player) && args.length < 3) {
/* 389 */           sender.sendMessage(ChatColor.RED + "/scoreboard teams leave [player...]");
/* 390 */           return false;
/*     */         } 
/* 392 */         Set<String> left = new HashSet<String>();
/* 393 */         Set<String> noTeam = new HashSet<String>();
/* 394 */         if (sender instanceof Player && args.length == 2) {
/* 395 */           Team team = mainScoreboard.getPlayerTeam((OfflinePlayer)sender);
/* 396 */           if (team != null) {
/* 397 */             team.removePlayer((OfflinePlayer)sender);
/* 398 */             left.add(sender.getName());
/*     */           } else {
/* 400 */             noTeam.add(sender.getName());
/*     */           } 
/*     */         } else {
/* 403 */           for (int i = 2; i < args.length; i++) {
/* 404 */             OfflinePlayer offlinePlayer; String playerName = args[i];
/*     */             
/* 406 */             Player player = Bukkit.getPlayerExact(playerName);
/* 407 */             if (player != null) {
/* 408 */               Player player1 = player;
/*     */             } else {
/* 410 */               offlinePlayer = Bukkit.getOfflinePlayer(playerName);
/*     */             } 
/* 412 */             Team team = mainScoreboard.getPlayerTeam(offlinePlayer);
/* 413 */             if (team != null) {
/* 414 */               team.removePlayer(offlinePlayer);
/* 415 */               left.add(offlinePlayer.getName());
/*     */             } else {
/* 417 */               noTeam.add(offlinePlayer.getName());
/*     */             } 
/*     */           } 
/*     */         } 
/* 421 */         if (!left.isEmpty()) {
/* 422 */           sender.sendMessage("Removed " + left.size() + " player(s) from their teams: " + stringCollectionToString(left));
/*     */         }
/* 424 */         if (!noTeam.isEmpty()) {
/* 425 */           sender.sendMessage("Could not remove " + noTeam.size() + " player(s) from their teams: " + stringCollectionToString(noTeam));
/*     */         }
/* 427 */       } else if (args[1].equalsIgnoreCase("option")) {
/* 428 */         if (args.length != 4 && args.length != 5) {
/* 429 */           sender.sendMessage(ChatColor.RED + "/scoreboard teams option <team> <friendlyfire|color|seefriendlyinvisibles> <value>");
/* 430 */           return false;
/*     */         } 
/* 432 */         String teamName = args[2];
/* 433 */         Team team = mainScoreboard.getTeam(teamName);
/* 434 */         if (team == null) {
/* 435 */           sender.sendMessage(ChatColor.RED + "No team was found by the name '" + teamName + "'");
/* 436 */           return false;
/*     */         } 
/* 438 */         String option = args[3].toLowerCase();
/* 439 */         if (!option.equals("friendlyfire") && !option.equals("color") && !option.equals("seefriendlyinvisibles")) {
/* 440 */           sender.sendMessage(ChatColor.RED + "/scoreboard teams option <team> <friendlyfire|color|seefriendlyinvisibles> <value>");
/* 441 */           return false;
/*     */         } 
/* 443 */         if (args.length == 4) {
/* 444 */           if (option.equals("color")) {
/* 445 */             sender.sendMessage(ChatColor.RED + "Valid values for option color are: " + stringCollectionToString(TEAMS_OPTION_COLOR.keySet()));
/*     */           } else {
/* 447 */             sender.sendMessage(ChatColor.RED + "Valid values for option " + option + " are: true and false");
/*     */           } 
/*     */         } else {
/* 450 */           String value = args[4].toLowerCase();
/* 451 */           if (option.equals("color")) {
/* 452 */             ChatColor color = TEAMS_OPTION_COLOR.get(value);
/* 453 */             if (color == null) {
/* 454 */               sender.sendMessage(ChatColor.RED + "Valid values for option color are: " + stringCollectionToString(TEAMS_OPTION_COLOR.keySet()));
/* 455 */               return false;
/*     */             } 
/* 457 */             team.setPrefix(color.toString());
/* 458 */             team.setSuffix(ChatColor.RESET.toString());
/*     */           } else {
/* 460 */             if (!value.equals("true") && !value.equals("false")) {
/* 461 */               sender.sendMessage(ChatColor.RED + "Valid values for option " + option + " are: true and false");
/* 462 */               return false;
/*     */             } 
/* 464 */             if (option.equals("friendlyfire")) {
/* 465 */               team.setAllowFriendlyFire(value.equals("true"));
/*     */             } else {
/* 467 */               team.setCanSeeFriendlyInvisibles(value.equals("true"));
/*     */             } 
/*     */           } 
/* 470 */           sender.sendMessage("Set option " + option + " for team " + team.getName() + " to " + value);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 474 */       sender.sendMessage(ChatColor.RED + "Usage: /scoreboard <objectives|players|teams>");
/* 475 */       return false;
/*     */     } 
/* 477 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 482 */     Validate.notNull(sender, "Sender cannot be null");
/* 483 */     Validate.notNull(args, "Arguments cannot be null");
/* 484 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 486 */     if (args.length == 1) {
/* 487 */       return (List<String>)StringUtil.copyPartialMatches(args[0], MAIN_CHOICES, new ArrayList());
/*     */     }
/* 489 */     if (args.length > 1) {
/* 490 */       if (args[0].equalsIgnoreCase("objectives")) {
/* 491 */         if (args.length == 2) {
/* 492 */           return (List<String>)StringUtil.copyPartialMatches(args[1], OBJECTIVES_CHOICES, new ArrayList());
/*     */         }
/* 494 */         if (args[1].equalsIgnoreCase("add")) {
/* 495 */           if (args.length == 4) {
/* 496 */             return (List<String>)StringUtil.copyPartialMatches(args[3], OBJECTIVES_CRITERIA, new ArrayList());
/*     */           }
/* 498 */         } else if (args[1].equalsIgnoreCase("remove")) {
/* 499 */           if (args.length == 3) {
/* 500 */             return (List<String>)StringUtil.copyPartialMatches(args[2], getCurrentObjectives(), new ArrayList());
/*     */           }
/* 502 */         } else if (args[1].equalsIgnoreCase("setdisplay")) {
/* 503 */           if (args.length == 3) {
/* 504 */             return (List<String>)StringUtil.copyPartialMatches(args[2], OBJECTIVES_DISPLAYSLOT.keySet(), new ArrayList());
/*     */           }
/* 506 */           if (args.length == 4) {
/* 507 */             return (List<String>)StringUtil.copyPartialMatches(args[3], getCurrentObjectives(), new ArrayList());
/*     */           }
/*     */         } 
/* 510 */       } else if (args[0].equalsIgnoreCase("players")) {
/* 511 */         if (args.length == 2) {
/* 512 */           return (List<String>)StringUtil.copyPartialMatches(args[1], PLAYERS_CHOICES, new ArrayList());
/*     */         }
/* 514 */         if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) {
/* 515 */           if (args.length == 3) {
/* 516 */             return super.tabComplete(sender, alias, args);
/*     */           }
/* 518 */           if (args.length == 4) {
/* 519 */             return (List<String>)StringUtil.copyPartialMatches(args[3], getCurrentObjectives(), new ArrayList());
/*     */           }
/*     */         }
/* 522 */         else if (args.length == 3) {
/* 523 */           return (List<String>)StringUtil.copyPartialMatches(args[2], getCurrentEntries(), new ArrayList());
/*     */         }
/*     */       
/* 526 */       } else if (args[0].equalsIgnoreCase("teams")) {
/* 527 */         if (args.length == 2) {
/* 528 */           return (List<String>)StringUtil.copyPartialMatches(args[1], TEAMS_CHOICES, new ArrayList());
/*     */         }
/* 530 */         if (args[1].equalsIgnoreCase("join")) {
/* 531 */           if (args.length == 3) {
/* 532 */             return (List<String>)StringUtil.copyPartialMatches(args[2], getCurrentTeams(), new ArrayList());
/*     */           }
/* 534 */           if (args.length >= 4)
/* 535 */             return super.tabComplete(sender, alias, args); 
/*     */         } else {
/* 537 */           if (args[1].equalsIgnoreCase("leave"))
/* 538 */             return super.tabComplete(sender, alias, args); 
/* 539 */           if (args[1].equalsIgnoreCase("option")) {
/* 540 */             if (args.length == 3) {
/* 541 */               return (List<String>)StringUtil.copyPartialMatches(args[2], getCurrentTeams(), new ArrayList());
/*     */             }
/* 543 */             if (args.length == 4) {
/* 544 */               return (List<String>)StringUtil.copyPartialMatches(args[3], TEAMS_OPTION_CHOICES, new ArrayList());
/*     */             }
/* 546 */             if (args.length == 5) {
/* 547 */               if (args[3].equalsIgnoreCase("color")) {
/* 548 */                 return (List<String>)StringUtil.copyPartialMatches(args[4], TEAMS_OPTION_COLOR.keySet(), new ArrayList());
/*     */               }
/* 550 */               return (List<String>)StringUtil.copyPartialMatches(args[4], BOOLEAN, new ArrayList());
/*     */             }
/*     */           
/*     */           }
/* 554 */           else if (args.length == 3) {
/* 555 */             return (List<String>)StringUtil.copyPartialMatches(args[2], getCurrentTeams(), new ArrayList());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 561 */     return (List<String>)ImmutableList.of();
/*     */   }
/*     */   
/*     */   private static String offlinePlayerSetToString(Set<OfflinePlayer> set) {
/* 565 */     StringBuilder string = new StringBuilder();
/* 566 */     String lastValue = null;
/* 567 */     for (OfflinePlayer value : set) {
/* 568 */       string.append(lastValue = value.getName()).append(", ");
/*     */     }
/* 570 */     string.delete(string.length() - 2, 2147483647);
/* 571 */     if (string.length() != lastValue.length()) {
/* 572 */       string.insert(string.length() - lastValue.length(), "and ");
/*     */     }
/* 574 */     return string.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String stringCollectionToString(Collection<String> set) {
/* 579 */     StringBuilder string = new StringBuilder();
/* 580 */     String lastValue = null;
/* 581 */     for (String value : set) {
/* 582 */       string.append(lastValue = value).append(", ");
/*     */     }
/* 584 */     string.delete(string.length() - 2, 2147483647);
/* 585 */     if (string.length() != lastValue.length()) {
/* 586 */       string.insert(string.length() - lastValue.length(), "and ");
/*     */     }
/* 588 */     return string.toString();
/*     */   }
/*     */   
/*     */   private List<String> getCurrentObjectives() {
/* 592 */     List<String> list = new ArrayList<String>();
/* 593 */     for (Objective objective : Bukkit.getScoreboardManager().getMainScoreboard().getObjectives()) {
/* 594 */       list.add(objective.getName());
/*     */     }
/* 596 */     Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
/* 597 */     return list;
/*     */   }
/*     */   
/*     */   private List<String> getCurrentEntries() {
/* 601 */     List<String> list = new ArrayList<String>();
/* 602 */     for (String entry : Bukkit.getScoreboardManager().getMainScoreboard().getEntries()) {
/* 603 */       list.add(entry);
/*     */     }
/* 605 */     Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
/* 606 */     return list;
/*     */   }
/*     */   
/*     */   private List<String> getCurrentTeams() {
/* 610 */     List<String> list = new ArrayList<String>();
/* 611 */     for (Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
/* 612 */       list.add(team.getName());
/*     */     }
/* 614 */     Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
/* 615 */     return list;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ScoreboardCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */