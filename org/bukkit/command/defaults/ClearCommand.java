/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ public class ClearCommand extends VanillaCommand {
/*     */   private static List<String> materials;
/*     */   
/*     */   static {
/*  20 */     ArrayList<String> materialList = new ArrayList<String>();
/*  21 */     for (Material material : Material.values()) {
/*  22 */       materialList.add(material.name());
/*     */     }
/*  24 */     Collections.sort(materialList);
/*  25 */     materials = (List<String>)ImmutableList.copyOf(materialList);
/*     */   }
/*     */   
/*     */   public ClearCommand() {
/*  29 */     super("clear");
/*  30 */     this.description = "Clears the player's inventory. Can specify item and data filters too.";
/*  31 */     this.usageMessage = "/clear <player> [item] [data]";
/*  32 */     setPermission("bukkit.command.clear");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/*  37 */     if (!testPermission(sender)) return true;
/*     */     
/*  39 */     Player player = null;
/*  40 */     if (args.length > 0) {
/*  41 */       player = Bukkit.getPlayer(args[0]);
/*  42 */     } else if (sender instanceof Player) {
/*  43 */       player = (Player)sender;
/*     */     } 
/*     */     
/*  46 */     if (player != null) {
/*     */       int id;
/*     */       
/*  49 */       if (args.length > 1 && !args[1].equals("-1")) {
/*  50 */         Material material = Material.matchMaterial(args[1]);
/*  51 */         if (material == null) {
/*  52 */           sender.sendMessage(ChatColor.RED + "There's no item called " + args[1]);
/*  53 */           return false;
/*     */         } 
/*     */         
/*  56 */         id = material.getId();
/*     */       } else {
/*  58 */         id = -1;
/*     */       } 
/*     */       
/*  61 */       int data = (args.length >= 3) ? getInteger(sender, args[2], 0) : -1;
/*  62 */       int count = player.getInventory().clear(id, data);
/*     */       
/*  64 */       Command.broadcastCommandMessage(sender, "Cleared the inventory of " + player.getDisplayName() + ", removing " + count + " items");
/*  65 */     } else if (args.length == 0) {
/*  66 */       sender.sendMessage(ChatColor.RED + "Please provide a player!");
/*     */     } else {
/*  68 */       sender.sendMessage(ChatColor.RED + "Can't find player " + args[0]);
/*     */     } 
/*     */     
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/*  76 */     Validate.notNull(sender, "Sender cannot be null");
/*  77 */     Validate.notNull(args, "Arguments cannot be null");
/*  78 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/*  80 */     if (args.length == 1) {
/*  81 */       return super.tabComplete(sender, alias, args);
/*     */     }
/*  83 */     if (args.length == 2) {
/*  84 */       String arg = args[1];
/*  85 */       List<String> materials = ClearCommand.materials;
/*  86 */       List<String> completion = null;
/*     */       
/*  88 */       int size = materials.size();
/*  89 */       int i = Collections.binarySearch(materials, arg, String.CASE_INSENSITIVE_ORDER);
/*     */       
/*  91 */       if (i < 0)
/*     */       {
/*  93 */         i = -1 - i;
/*     */       }
/*     */       
/*  96 */       while (i < size) {
/*  97 */         String material = materials.get(i);
/*  98 */         if (StringUtil.startsWithIgnoreCase(material, arg)) {
/*  99 */           if (completion == null) {
/* 100 */             completion = new ArrayList<String>();
/*     */           }
/* 102 */           completion.add(material);
/*     */           
/*     */           i++;
/*     */         } 
/*     */       } 
/*     */       
/* 108 */       if (completion != null) {
/* 109 */         return completion;
/*     */       }
/*     */     } 
/* 112 */     return (List<String>)ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ClearCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */