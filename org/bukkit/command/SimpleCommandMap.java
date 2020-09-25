/*     */ package org.bukkit.command;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.defaults.AchievementCommand;
/*     */ import org.bukkit.command.defaults.GiveCommand;
/*     */ import org.bukkit.command.defaults.KillCommand;
/*     */ import org.bukkit.command.defaults.OpCommand;
/*     */ import org.bukkit.command.defaults.SaveOnCommand;
/*     */ import org.bukkit.command.defaults.TeleportCommand;
/*     */ import org.bukkit.command.defaults.TimingsCommand;
/*     */ import org.bukkit.command.defaults.WhitelistCommand;
/*     */ 
/*     */ public class SimpleCommandMap implements CommandMap {
/*  21 */   private static final Pattern PATTERN_ON_SPACE = Pattern.compile(" ", 16);
/*  22 */   protected final Map<String, Command> knownCommands = new HashMap<String, Command>();
/*     */   private final Server server;
/*     */   
/*     */   public SimpleCommandMap(Server server) {
/*  26 */     this.server = server;
/*  27 */     setDefaultCommands();
/*     */   }
/*     */   
/*     */   private void setDefaultCommands() {
/*  31 */     register("bukkit", (Command)new SaveCommand());
/*  32 */     register("bukkit", (Command)new SaveOnCommand());
/*  33 */     register("bukkit", (Command)new SaveOffCommand());
/*  34 */     register("bukkit", (Command)new StopCommand());
/*  35 */     register("bukkit", (Command)new VersionCommand("version"));
/*  36 */     register("bukkit", (Command)new ReloadCommand("reload"));
/*  37 */     register("bukkit", (Command)new PluginsCommand("plugins"));
/*  38 */     register("bukkit", (Command)new TimingsCommand("timings"));
/*     */   }
/*     */   
/*     */   public void setFallbackCommands() {
/*  42 */     register("bukkit", (Command)new ListCommand());
/*  43 */     register("bukkit", (Command)new OpCommand());
/*  44 */     register("bukkit", (Command)new DeopCommand());
/*  45 */     register("bukkit", (Command)new BanIpCommand());
/*  46 */     register("bukkit", (Command)new PardonIpCommand());
/*  47 */     register("bukkit", (Command)new BanCommand());
/*  48 */     register("bukkit", (Command)new PardonCommand());
/*  49 */     register("bukkit", (Command)new KickCommand());
/*  50 */     register("bukkit", (Command)new TeleportCommand());
/*  51 */     register("bukkit", (Command)new GiveCommand());
/*  52 */     register("bukkit", (Command)new TimeCommand());
/*  53 */     register("bukkit", (Command)new SayCommand());
/*  54 */     register("bukkit", (Command)new WhitelistCommand());
/*  55 */     register("bukkit", (Command)new TellCommand());
/*  56 */     register("bukkit", (Command)new MeCommand());
/*  57 */     register("bukkit", (Command)new KillCommand());
/*  58 */     register("bukkit", (Command)new GameModeCommand());
/*  59 */     register("bukkit", (Command)new HelpCommand());
/*  60 */     register("bukkit", (Command)new ExpCommand());
/*  61 */     register("bukkit", (Command)new ToggleDownfallCommand());
/*  62 */     register("bukkit", (Command)new BanListCommand());
/*  63 */     register("bukkit", (Command)new DefaultGameModeCommand());
/*  64 */     register("bukkit", (Command)new SeedCommand());
/*  65 */     register("bukkit", (Command)new DifficultyCommand());
/*  66 */     register("bukkit", (Command)new WeatherCommand());
/*  67 */     register("bukkit", (Command)new SpawnpointCommand());
/*  68 */     register("bukkit", (Command)new ClearCommand());
/*  69 */     register("bukkit", (Command)new GameRuleCommand());
/*  70 */     register("bukkit", (Command)new EnchantCommand());
/*  71 */     register("bukkit", (Command)new TestForCommand());
/*  72 */     register("bukkit", (Command)new EffectCommand());
/*  73 */     register("bukkit", (Command)new ScoreboardCommand());
/*  74 */     register("bukkit", (Command)new PlaySoundCommand());
/*  75 */     register("bukkit", (Command)new SpreadPlayersCommand());
/*  76 */     register("bukkit", (Command)new SetWorldSpawnCommand());
/*  77 */     register("bukkit", (Command)new SetIdleTimeoutCommand());
/*  78 */     register("bukkit", (Command)new AchievementCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAll(String fallbackPrefix, List<Command> commands) {
/*  85 */     if (commands != null) {
/*  86 */       for (Command c : commands) {
/*  87 */         register(fallbackPrefix, c);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean register(String fallbackPrefix, Command command) {
/*  96 */     return register(command.getName(), fallbackPrefix, command);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean register(String label, String fallbackPrefix, Command command) {
/* 103 */     label = label.toLowerCase().trim();
/* 104 */     fallbackPrefix = fallbackPrefix.toLowerCase().trim();
/* 105 */     boolean registered = register(label, command, false, fallbackPrefix);
/*     */     
/* 107 */     Iterator<String> iterator = command.getAliases().iterator();
/* 108 */     while (iterator.hasNext()) {
/* 109 */       if (!register(iterator.next(), command, true, fallbackPrefix)) {
/* 110 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 115 */     if (!registered) {
/* 116 */       command.setLabel(fallbackPrefix + ":" + label);
/*     */     }
/*     */ 
/*     */     
/* 120 */     command.register(this);
/*     */     
/* 122 */     return registered;
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
/*     */   private synchronized boolean register(String label, Command command, boolean isAlias, String fallbackPrefix) {
/* 137 */     this.knownCommands.put(fallbackPrefix + ":" + label, command);
/* 138 */     if ((command instanceof org.bukkit.command.defaults.VanillaCommand || isAlias) && this.knownCommands.containsKey(label))
/*     */     {
/*     */ 
/*     */       
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     boolean registered = true;
/*     */ 
/*     */     
/* 148 */     Command conflict = this.knownCommands.get(label);
/* 149 */     if (conflict != null && conflict.getLabel().equals(label)) {
/* 150 */       return false;
/*     */     }
/*     */     
/* 153 */     if (!isAlias) {
/* 154 */       command.setLabel(label);
/*     */     }
/* 156 */     this.knownCommands.put(label, command);
/*     */     
/* 158 */     return registered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dispatch(CommandSender sender, String commandLine) throws CommandException {
/* 165 */     String[] args = PATTERN_ON_SPACE.split(commandLine);
/*     */     
/* 167 */     if (args.length == 0) {
/* 168 */       return false;
/*     */     }
/*     */     
/* 171 */     String sentCommandLabel = args[0].toLowerCase();
/* 172 */     Command target = getCommand(sentCommandLabel);
/*     */     
/* 174 */     if (target == null) {
/* 175 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 179 */       target.timings.startTiming();
/*     */       
/* 181 */       target.execute(sender, sentCommandLabel, (String[])Java15Compat.Arrays_copyOfRange((Object[])args, 1, args.length));
/* 182 */       target.timings.stopTiming();
/* 183 */     } catch (CommandException ex) {
/* 184 */       target.timings.stopTiming();
/* 185 */       throw ex;
/* 186 */     } catch (Throwable ex) {
/* 187 */       target.timings.stopTiming();
/* 188 */       throw new CommandException("Unhandled exception executing '" + commandLine + "' in " + target, ex);
/*     */     } 
/*     */ 
/*     */     
/* 192 */     return true;
/*     */   }
/*     */   
/*     */   public synchronized void clearCommands() {
/* 196 */     for (Map.Entry<String, Command> entry : this.knownCommands.entrySet()) {
/* 197 */       ((Command)entry.getValue()).unregister(this);
/*     */     }
/* 199 */     this.knownCommands.clear();
/* 200 */     setDefaultCommands();
/*     */   }
/*     */   
/*     */   public Command getCommand(String name) {
/* 204 */     Command target = this.knownCommands.get(name.toLowerCase());
/* 205 */     return target;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String cmdLine) {
/* 209 */     Validate.notNull(sender, "Sender cannot be null");
/* 210 */     Validate.notNull(cmdLine, "Command line cannot null");
/*     */     
/* 212 */     int spaceIndex = cmdLine.indexOf(' ');
/*     */     
/* 214 */     if (spaceIndex == -1) {
/* 215 */       ArrayList<String> completions = new ArrayList<String>();
/* 216 */       Map<String, Command> knownCommands = this.knownCommands;
/*     */       
/* 218 */       String prefix = (sender instanceof org.bukkit.entity.Player) ? "/" : "";
/*     */       
/* 220 */       for (Map.Entry<String, Command> commandEntry : knownCommands.entrySet()) {
/* 221 */         Command command = commandEntry.getValue();
/*     */         
/* 223 */         if (!command.testPermissionSilent(sender)) {
/*     */           continue;
/*     */         }
/*     */         
/* 227 */         String name = commandEntry.getKey();
/*     */         
/* 229 */         if (StringUtil.startsWithIgnoreCase(name, cmdLine)) {
/* 230 */           completions.add(prefix + name);
/*     */         }
/*     */       } 
/*     */       
/* 234 */       Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
/* 235 */       return completions;
/*     */     } 
/*     */     
/* 238 */     String commandName = cmdLine.substring(0, spaceIndex);
/* 239 */     Command target = getCommand(commandName);
/*     */     
/* 241 */     if (target == null) {
/* 242 */       return null;
/*     */     }
/*     */     
/* 245 */     if (!target.testPermissionSilent(sender)) {
/* 246 */       return null;
/*     */     }
/*     */     
/* 249 */     String argLine = cmdLine.substring(spaceIndex + 1, cmdLine.length());
/* 250 */     String[] args = PATTERN_ON_SPACE.split(argLine, -1);
/*     */     
/*     */     try {
/* 253 */       return target.tabComplete(sender, commandName, args);
/* 254 */     } catch (CommandException ex) {
/* 255 */       throw ex;
/* 256 */     } catch (Throwable ex) {
/* 257 */       throw new CommandException("Unhandled exception executing tab-completer for '" + cmdLine + "' in " + target, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<Command> getCommands() {
/* 262 */     return Collections.unmodifiableCollection(this.knownCommands.values());
/*     */   }
/*     */   
/*     */   public void registerServerAliases() {
/* 266 */     Map<String, String[]> values = this.server.getCommandAliases();
/*     */     
/* 268 */     for (String alias : values.keySet()) {
/* 269 */       if (alias.contains(":") || alias.contains(" ")) {
/* 270 */         this.server.getLogger().warning("Could not register alias " + alias + " because it contains illegal characters");
/*     */         
/*     */         continue;
/*     */       } 
/* 274 */       String[] commandStrings = values.get(alias);
/* 275 */       List<String> targets = new ArrayList<String>();
/* 276 */       StringBuilder bad = new StringBuilder();
/*     */       
/* 278 */       for (String commandString : commandStrings) {
/* 279 */         String[] commandArgs = commandString.split(" ");
/* 280 */         Command command = getCommand(commandArgs[0]);
/*     */         
/* 282 */         if (command == null) {
/* 283 */           if (bad.length() > 0) {
/* 284 */             bad.append(", ");
/*     */           }
/* 286 */           bad.append(commandString);
/*     */         } else {
/* 288 */           targets.add(commandString);
/*     */         } 
/*     */       } 
/*     */       
/* 292 */       if (bad.length() > 0) {
/* 293 */         this.server.getLogger().warning("Could not register alias " + alias + " because it contains commands that do not exist: " + bad);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 298 */       if (targets.size() > 0) {
/* 299 */         this.knownCommands.put(alias.toLowerCase(), new FormattedCommandAlias(alias.toLowerCase(), targets.<String>toArray(new String[targets.size()]))); continue;
/*     */       } 
/* 301 */       this.knownCommands.remove(alias.toLowerCase());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\SimpleCommandMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */