/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scoreboard.Team;
/*     */ 
/*     */ public class SpreadPlayersCommand extends VanillaCommand {
/*  20 */   private static final Random random = new Random();
/*     */   
/*     */   public SpreadPlayersCommand() {
/*  23 */     super("spreadplayers");
/*  24 */     this.description = "Spreads players around a point";
/*  25 */     this.usageMessage = "/spreadplayers <x> <z> <spreadDistance> <maxRange> <respectTeams true|false> <player ...>";
/*  26 */     setPermission("bukkit.command.spreadplayers");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
/*  31 */     if (!testPermission(sender)) {
/*  32 */       return true;
/*     */     }
/*     */     
/*  35 */     if (args.length < 6) {
/*  36 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  37 */       return false;
/*     */     } 
/*     */     
/*  40 */     double x = getDouble(sender, args[0], -3.0E7D, 3.0E7D);
/*  41 */     double z = getDouble(sender, args[1], -3.0E7D, 3.0E7D);
/*  42 */     double distance = getDouble(sender, args[2]);
/*  43 */     double range = getDouble(sender, args[3]);
/*     */     
/*  45 */     if (distance < 0.0D) {
/*  46 */       sender.sendMessage(ChatColor.RED + "Distance is too small.");
/*  47 */       return false;
/*     */     } 
/*     */     
/*  50 */     if (range < distance + 1.0D) {
/*  51 */       sender.sendMessage(ChatColor.RED + "Max range is too small.");
/*  52 */       return false;
/*     */     } 
/*     */     
/*  55 */     String respectTeams = args[4];
/*  56 */     boolean teams = false;
/*     */     
/*  58 */     if (respectTeams.equalsIgnoreCase("true")) {
/*  59 */       teams = true;
/*  60 */     } else if (!respectTeams.equalsIgnoreCase("false")) {
/*  61 */       sender.sendMessage(String.format(ChatColor.RED + "'%s' is not true or false", new Object[] { args[4] }));
/*  62 */       return false;
/*     */     } 
/*     */     
/*  65 */     List<Player> players = Lists.newArrayList();
/*  66 */     World world = null;
/*     */     
/*  68 */     for (int i = 5; i < args.length; i++) {
/*  69 */       Player player = Bukkit.getPlayerExact(args[i]);
/*  70 */       if (player != null) {
/*     */ 
/*     */ 
/*     */         
/*  74 */         if (world == null) {
/*  75 */           world = player.getWorld();
/*     */         }
/*  77 */         players.add(player);
/*     */       } 
/*     */     } 
/*  80 */     if (world == null) {
/*  81 */       return true;
/*     */     }
/*     */     
/*  84 */     double xRangeMin = x - range;
/*  85 */     double zRangeMin = z - range;
/*  86 */     double xRangeMax = x + range;
/*  87 */     double zRangeMax = z + range;
/*     */     
/*  89 */     int spreadSize = teams ? getTeams(players) : players.size();
/*     */     
/*  91 */     Location[] locations = getSpreadLocations(world, spreadSize, xRangeMin, zRangeMin, xRangeMax, zRangeMax);
/*  92 */     int rangeSpread = range(world, distance, xRangeMin, zRangeMin, xRangeMax, zRangeMax, locations);
/*     */     
/*  94 */     if (rangeSpread == -1) {
/*  95 */       sender.sendMessage(String.format("Could not spread %d %s around %s,%s (too many players for space - try using spread of at most %s)", new Object[] { Integer.valueOf(spreadSize), teams ? "teams" : "players", Double.valueOf(x), Double.valueOf(z) }));
/*  96 */       return false;
/*     */     } 
/*     */     
/*  99 */     double distanceSpread = spread(world, players, locations, teams);
/*     */     
/* 101 */     sender.sendMessage(String.format("Succesfully spread %d %s around %s,%s", new Object[] { Integer.valueOf(locations.length), teams ? "teams" : "players", Double.valueOf(x), Double.valueOf(z) }));
/* 102 */     if (locations.length > 1) {
/* 103 */       sender.sendMessage(String.format("(Average distance between %s is %s blocks apart after %s iterations)", new Object[] { teams ? "teams" : "players", String.format("%.2f", new Object[] { Double.valueOf(distanceSpread) }), Integer.valueOf(rangeSpread) }));
/*     */     }
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   private int range(World world, double distance, double xRangeMin, double zRangeMin, double xRangeMax, double zRangeMax, Location[] locations) {
/* 109 */     boolean flag = true;
/*     */ 
/*     */     
/*     */     int i;
/*     */     
/* 114 */     for (i = 0; i < 10000 && flag; i++) {
/* 115 */       flag = false;
/* 116 */       double max = 3.4028234663852886E38D;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       for (int k = 0; k < locations.length; k++) {
/* 122 */         Location loc2 = locations[k];
/*     */         
/* 124 */         int j = 0;
/* 125 */         Location loc1 = new Location(world, 0.0D, 0.0D, 0.0D);
/*     */         
/* 127 */         for (int l = 0; l < locations.length; l++) {
/* 128 */           if (k != l) {
/* 129 */             Location loc3 = locations[l];
/* 130 */             double dis = loc2.distanceSquared(loc3);
/*     */             
/* 132 */             max = Math.min(dis, max);
/* 133 */             if (dis < distance) {
/* 134 */               j++;
/* 135 */               loc1.add(loc3.getX() - loc2.getX(), 0.0D, 0.0D);
/* 136 */               loc1.add(loc3.getZ() - loc2.getZ(), 0.0D, 0.0D);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 141 */         if (j > 0) {
/* 142 */           loc2.setX(loc2.getX() / j);
/* 143 */           loc2.setZ(loc2.getZ() / j);
/* 144 */           double d7 = Math.sqrt(loc1.getX() * loc1.getX() + loc1.getZ() * loc1.getZ());
/*     */           
/* 146 */           if (d7 > 0.0D) {
/* 147 */             loc1.setX(loc1.getX() / d7);
/* 148 */             loc2.add(-loc1.getX(), 0.0D, -loc1.getZ());
/*     */           } else {
/* 150 */             double x = (xRangeMin >= xRangeMax) ? xRangeMin : (random.nextDouble() * (xRangeMax - xRangeMin) + xRangeMin);
/* 151 */             double z = (zRangeMin >= zRangeMax) ? zRangeMin : (random.nextDouble() * (zRangeMax - zRangeMin) + zRangeMin);
/* 152 */             loc2.setX(x);
/* 153 */             loc2.setZ(z);
/*     */           } 
/*     */           
/* 156 */           flag = true;
/*     */         } 
/*     */         
/* 159 */         boolean swap = false;
/*     */         
/* 161 */         if (loc2.getX() < xRangeMin) {
/* 162 */           loc2.setX(xRangeMin);
/* 163 */           swap = true;
/* 164 */         } else if (loc2.getX() > xRangeMax) {
/* 165 */           loc2.setX(xRangeMax);
/* 166 */           swap = true;
/*     */         } 
/*     */         
/* 169 */         if (loc2.getZ() < zRangeMin) {
/* 170 */           loc2.setZ(zRangeMin);
/* 171 */           swap = true;
/* 172 */         } else if (loc2.getZ() > zRangeMax) {
/* 173 */           loc2.setZ(zRangeMax);
/* 174 */           swap = true;
/*     */         } 
/* 176 */         if (swap) {
/* 177 */           flag = true;
/*     */         }
/*     */       } 
/*     */       
/* 181 */       if (!flag) {
/* 182 */         Location[] locs = locations;
/* 183 */         int i1 = locations.length;
/*     */         
/* 185 */         for (int j = 0; j < i1; j++) {
/* 186 */           Location loc1 = locs[j];
/* 187 */           if (world.getHighestBlockYAt(loc1) == 0) {
/* 188 */             double x = (xRangeMin >= xRangeMax) ? xRangeMin : (random.nextDouble() * (xRangeMax - xRangeMin) + xRangeMin);
/* 189 */             double z = (zRangeMin >= zRangeMax) ? zRangeMin : (random.nextDouble() * (zRangeMax - zRangeMin) + zRangeMin);
/* 190 */             locations[i] = new Location(world, x, 0.0D, z);
/* 191 */             loc1.setX(x);
/* 192 */             loc1.setZ(z);
/* 193 */             flag = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 199 */     if (i >= 10000) {
/* 200 */       return -1;
/*     */     }
/* 202 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private double spread(World world, List<Player> list, Location[] locations, boolean teams) {
/* 207 */     double distance = 0.0D;
/* 208 */     int i = 0;
/* 209 */     Map<Team, Location> hashmap = Maps.newHashMap();
/*     */     
/* 211 */     for (int j = 0; j < list.size(); j++) {
/* 212 */       Location location; Player player = list.get(j);
/*     */ 
/*     */       
/* 215 */       if (teams) {
/* 216 */         Team team = player.getScoreboard().getPlayerTeam((OfflinePlayer)player);
/*     */         
/* 218 */         if (!hashmap.containsKey(team)) {
/* 219 */           hashmap.put(team, locations[i++]);
/*     */         }
/*     */         
/* 222 */         location = hashmap.get(team);
/*     */       } else {
/* 224 */         location = locations[i++];
/*     */       } 
/*     */       
/* 227 */       player.teleport(new Location(world, Math.floor(location.getX()) + 0.5D, world.getHighestBlockYAt((int)location.getX(), (int)location.getZ()), Math.floor(location.getZ()) + 0.5D));
/* 228 */       double value = Double.MAX_VALUE;
/*     */       
/* 230 */       for (int k = 0; k < locations.length; k++) {
/* 231 */         if (location != locations[k]) {
/* 232 */           double d = location.distanceSquared(locations[k]);
/* 233 */           value = Math.min(d, value);
/*     */         } 
/*     */       } 
/*     */       
/* 237 */       distance += value;
/*     */     } 
/*     */     
/* 240 */     distance /= list.size();
/* 241 */     return distance;
/*     */   }
/*     */   
/*     */   private int getTeams(List<Player> players) {
/* 245 */     Set<Team> teams = Sets.newHashSet();
/*     */     
/* 247 */     for (Player player : players) {
/* 248 */       teams.add(player.getScoreboard().getPlayerTeam((OfflinePlayer)player));
/*     */     }
/*     */     
/* 251 */     return teams.size();
/*     */   }
/*     */   
/*     */   private Location[] getSpreadLocations(World world, int size, double xRangeMin, double zRangeMin, double xRangeMax, double zRangeMax) {
/* 255 */     Location[] locations = new Location[size];
/*     */     
/* 257 */     for (int i = 0; i < size; i++) {
/* 258 */       double x = (xRangeMin >= xRangeMax) ? xRangeMin : (random.nextDouble() * (xRangeMax - xRangeMin) + xRangeMin);
/* 259 */       double z = (zRangeMin >= zRangeMax) ? zRangeMin : (random.nextDouble() * (zRangeMax - zRangeMin) + zRangeMin);
/* 260 */       locations[i] = new Location(world, x, 0.0D, z);
/*     */     } 
/*     */     
/* 263 */     return locations;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SpreadPlayersCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */