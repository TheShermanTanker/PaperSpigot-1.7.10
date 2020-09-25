/*     */ package org.bukkit.command;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.minecart.CommandMinecart;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ import org.bukkit.util.StringUtil;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Command
/*     */ {
/*     */   private final String name;
/*     */   private String nextLabel;
/*     */   private String label;
/*     */   private List<String> aliases;
/*     */   private List<String> activeAliases;
/*  29 */   private CommandMap commandMap = null;
/*  30 */   protected String description = "";
/*     */   protected String usageMessage;
/*     */   private String permission;
/*     */   private String permissionMessage;
/*     */   public CustomTimingsHandler timings;
/*     */   
/*     */   protected Command(String name) {
/*  37 */     this(name, "", "/" + name, new ArrayList<String>());
/*     */   }
/*     */   
/*     */   protected Command(String name, String description, String usageMessage, List<String> aliases) {
/*  41 */     this.name = name;
/*  42 */     this.nextLabel = name;
/*  43 */     this.label = name;
/*  44 */     this.description = description;
/*  45 */     this.usageMessage = usageMessage;
/*  46 */     this.aliases = aliases;
/*  47 */     this.activeAliases = new ArrayList<String>(aliases);
/*  48 */     this.timings = new CustomTimingsHandler("** Command: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<String> tabComplete(CommandSender sender, String[] args) {
/*  66 */     return null;
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
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/*  81 */     Validate.notNull(sender, "Sender cannot be null");
/*  82 */     Validate.notNull(args, "Arguments cannot be null");
/*  83 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/*  85 */     if (args.length == 0) {
/*  86 */       return (List<String>)ImmutableList.of();
/*     */     }
/*     */     
/*  89 */     String lastWord = args[args.length - 1];
/*     */     
/*  91 */     Player senderPlayer = (sender instanceof Player) ? (Player)sender : null;
/*     */     
/*  93 */     ArrayList<String> matchedPlayers = new ArrayList<String>();
/*  94 */     for (Player player : sender.getServer().getOnlinePlayers()) {
/*  95 */       String name = player.getName();
/*  96 */       if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(name, lastWord)) {
/*  97 */         matchedPlayers.add(name);
/*     */       }
/*     */     } 
/*     */     
/* 101 */     Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
/* 102 */     return matchedPlayers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 111 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermission() {
/* 121 */     return this.permission;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPermission(String permission) {
/* 131 */     this.permission = permission;
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
/*     */   public boolean testPermission(CommandSender target) {
/* 145 */     if (testPermissionSilent(target)) {
/* 146 */       return true;
/*     */     }
/*     */     
/* 149 */     if (this.permissionMessage == null) {
/* 150 */       target.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
/* 151 */     } else if (this.permissionMessage.length() != 0) {
/* 152 */       for (String line : this.permissionMessage.replace("<permission>", this.permission).split("\n")) {
/* 153 */         target.sendMessage(line);
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return false;
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
/*     */   public boolean testPermissionSilent(CommandSender target) {
/* 170 */     if (this.permission == null || this.permission.length() == 0) {
/* 171 */       return true;
/*     */     }
/*     */     
/* 174 */     for (String p : this.permission.split(";")) {
/* 175 */       if (target.hasPermission(p)) {
/* 176 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLabel() {
/* 189 */     return this.label;
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
/*     */   public boolean setLabel(String name) {
/* 203 */     this.nextLabel = name;
/* 204 */     if (!isRegistered()) {
/* 205 */       this.timings = new CustomTimingsHandler("** Command: " + name);
/* 206 */       this.label = name;
/* 207 */       return true;
/*     */     } 
/* 209 */     return false;
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
/*     */   public boolean register(CommandMap commandMap) {
/* 221 */     if (allowChangesFrom(commandMap)) {
/* 222 */       this.commandMap = commandMap;
/* 223 */       return true;
/*     */     } 
/*     */     
/* 226 */     return false;
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
/*     */   public boolean unregister(CommandMap commandMap) {
/* 239 */     if (allowChangesFrom(commandMap)) {
/* 240 */       this.commandMap = null;
/* 241 */       this.activeAliases = new ArrayList<String>(this.aliases);
/* 242 */       this.label = this.nextLabel;
/* 243 */       return true;
/*     */     } 
/*     */     
/* 246 */     return false;
/*     */   }
/*     */   
/*     */   private boolean allowChangesFrom(CommandMap commandMap) {
/* 250 */     return (null == this.commandMap || this.commandMap == commandMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegistered() {
/* 259 */     return (null != this.commandMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getAliases() {
/* 268 */     return this.activeAliases;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionMessage() {
/* 278 */     return this.permissionMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 287 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsage() {
/* 296 */     return this.usageMessage;
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
/*     */   public Command setAliases(List<String> aliases) {
/* 309 */     this.aliases = aliases;
/* 310 */     if (!isRegistered()) {
/* 311 */       this.activeAliases = new ArrayList<String>(aliases);
/*     */     }
/* 313 */     return this;
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
/*     */   public Command setDescription(String description) {
/* 325 */     this.description = description;
/* 326 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Command setPermissionMessage(String permissionMessage) {
/* 337 */     this.permissionMessage = permissionMessage;
/* 338 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Command setUsage(String usage) {
/* 348 */     this.usageMessage = usage;
/* 349 */     return this;
/*     */   }
/*     */   
/*     */   public static void broadcastCommandMessage(CommandSender source, String message) {
/* 353 */     broadcastCommandMessage(source, message, true);
/*     */   }
/*     */   
/*     */   public static void broadcastCommandMessage(CommandSender source, String message, boolean sendToSource) {
/* 357 */     String result = source.getName() + ": " + message;
/*     */     
/* 359 */     if (source instanceof BlockCommandSender) {
/* 360 */       BlockCommandSender blockCommandSender = (BlockCommandSender)source;
/*     */       
/* 362 */       if (blockCommandSender.getBlock().getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("false")) {
/* 363 */         Bukkit.getConsoleSender().sendMessage(result);
/*     */         return;
/*     */       } 
/* 366 */     } else if (source instanceof CommandMinecart) {
/* 367 */       CommandMinecart commandMinecart = (CommandMinecart)source;
/*     */       
/* 369 */       if (commandMinecart.getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("false")) {
/* 370 */         Bukkit.getConsoleSender().sendMessage(result);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 375 */     Set<Permissible> users = Bukkit.getPluginManager().getPermissionSubscriptions("bukkit.broadcast.admin");
/* 376 */     String colored = ChatColor.GRAY + "" + ChatColor.ITALIC + "[" + result + ChatColor.GRAY + ChatColor.ITALIC + "]";
/*     */     
/* 378 */     if (sendToSource && !(source instanceof ConsoleCommandSender)) {
/* 379 */       source.sendMessage(message);
/*     */     }
/*     */     
/* 382 */     for (Permissible user : users) {
/* 383 */       if (user instanceof CommandSender) {
/* 384 */         CommandSender target = (CommandSender)user;
/*     */         
/* 386 */         if (target instanceof ConsoleCommandSender) {
/* 387 */           target.sendMessage(result); continue;
/* 388 */         }  if (target != source) {
/* 389 */           target.sendMessage(colored);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 397 */     return getClass().getName() + '(' + this.name + ')';
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\Command.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */