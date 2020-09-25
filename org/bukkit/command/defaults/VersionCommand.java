/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ public class VersionCommand
/*     */   extends BukkitCommand
/*     */ {
/*     */   public VersionCommand(String name) {
/*  19 */     super(name);
/*     */     
/*  21 */     this.description = "Gets the version of this server including any plugins in use";
/*  22 */     this.usageMessage = "/version [plugin name]";
/*  23 */     setPermission("bukkit.command.version");
/*  24 */     setAliases(Arrays.asList(new String[] { "ver", "about" }));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/*  29 */     if (!testPermission(sender)) return true;
/*     */     
/*  31 */     if (args.length == 0) {
/*  32 */       sender.sendMessage("This server is running " + Bukkit.getName() + " version " + Bukkit.getVersion() + " (Implementing API version " + Bukkit.getBukkitVersion() + ")");
/*     */     } else {
/*  34 */       StringBuilder name = new StringBuilder();
/*     */       
/*  36 */       for (String arg : args) {
/*  37 */         if (name.length() > 0) {
/*  38 */           name.append(' ');
/*     */         }
/*     */         
/*  41 */         name.append(arg);
/*     */       } 
/*     */       
/*  44 */       String pluginName = name.toString();
/*  45 */       Plugin exactPlugin = Bukkit.getPluginManager().getPlugin(pluginName);
/*  46 */       if (exactPlugin != null) {
/*  47 */         describeToSender(exactPlugin, sender);
/*  48 */         return true;
/*     */       } 
/*     */       
/*  51 */       boolean found = false;
/*  52 */       pluginName = pluginName.toLowerCase();
/*  53 */       for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
/*  54 */         if (plugin.getName().toLowerCase().contains(pluginName)) {
/*  55 */           describeToSender(plugin, sender);
/*  56 */           found = true;
/*     */         } 
/*     */       } 
/*     */       
/*  60 */       if (!found) {
/*  61 */         sender.sendMessage("This server is not running any plugin by that name.");
/*  62 */         sender.sendMessage("Use /plugins to get a list of plugins.");
/*     */       } 
/*     */     } 
/*  65 */     return true;
/*     */   }
/*     */   
/*     */   private void describeToSender(Plugin plugin, CommandSender sender) {
/*  69 */     PluginDescriptionFile desc = plugin.getDescription();
/*  70 */     sender.sendMessage(ChatColor.GREEN + desc.getName() + ChatColor.WHITE + " version " + ChatColor.GREEN + desc.getVersion());
/*     */     
/*  72 */     if (desc.getDescription() != null) {
/*  73 */       sender.sendMessage(desc.getDescription());
/*     */     }
/*     */     
/*  76 */     if (desc.getWebsite() != null) {
/*  77 */       sender.sendMessage("Website: " + ChatColor.GREEN + desc.getWebsite());
/*     */     }
/*     */     
/*  80 */     if (!desc.getAuthors().isEmpty()) {
/*  81 */       if (desc.getAuthors().size() == 1) {
/*  82 */         sender.sendMessage("Author: " + getAuthors(desc));
/*     */       } else {
/*  84 */         sender.sendMessage("Authors: " + getAuthors(desc));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private String getAuthors(PluginDescriptionFile desc) {
/*  90 */     StringBuilder result = new StringBuilder();
/*  91 */     List<String> authors = desc.getAuthors();
/*     */     
/*  93 */     for (int i = 0; i < authors.size(); i++) {
/*  94 */       if (result.length() > 0) {
/*  95 */         result.append(ChatColor.WHITE);
/*     */         
/*  97 */         if (i < authors.size() - 1) {
/*  98 */           result.append(", ");
/*     */         } else {
/* 100 */           result.append(" and ");
/*     */         } 
/*     */       } 
/*     */       
/* 104 */       result.append(ChatColor.GREEN);
/* 105 */       result.append(authors.get(i));
/*     */     } 
/*     */     
/* 108 */     return result.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 113 */     Validate.notNull(sender, "Sender cannot be null");
/* 114 */     Validate.notNull(args, "Arguments cannot be null");
/* 115 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 117 */     if (args.length == 1) {
/* 118 */       List<String> completions = new ArrayList<String>();
/* 119 */       String toComplete = args[0].toLowerCase();
/* 120 */       for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
/* 121 */         if (StringUtil.startsWithIgnoreCase(plugin.getName(), toComplete)) {
/* 122 */           completions.add(plugin.getName());
/*     */         }
/*     */       } 
/* 125 */       return completions;
/*     */     } 
/* 127 */     return (List<String>)ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\VersionCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */