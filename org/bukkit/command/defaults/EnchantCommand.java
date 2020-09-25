/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.apache.commons.lang.WordUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ public class EnchantCommand
/*     */   extends VanillaCommand
/*     */ {
/*  23 */   private static final List<String> ENCHANTMENT_NAMES = new ArrayList<String>();
/*     */   
/*     */   public EnchantCommand() {
/*  26 */     super("enchant");
/*  27 */     this.description = "Adds enchantments to the item the player is currently holding. Specify 0 for the level to remove an enchantment. Specify force to ignore normal enchantment restrictions";
/*  28 */     this.usageMessage = "/enchant <player> <enchantment> [level|max|0] [force]";
/*  29 */     setPermission("bukkit.command.enchant");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
/*  34 */     if (!testPermission(sender)) return true; 
/*  35 */     if (args.length < 2) {
/*  36 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  37 */       return false;
/*     */     } 
/*     */     
/*  40 */     boolean force = false;
/*  41 */     if (args.length > 2) {
/*  42 */       force = args[(args.length > 3) ? 3 : 2].equalsIgnoreCase("force");
/*     */     }
/*     */     
/*  45 */     Player player = Bukkit.getPlayerExact(args[0]);
/*  46 */     if (player == null) {
/*  47 */       sender.sendMessage("Can't find player " + args[0]);
/*     */     } else {
/*  49 */       ItemStack item = player.getItemInHand();
/*  50 */       if (item.getType() == Material.AIR) {
/*  51 */         sender.sendMessage("The player isn't holding an item");
/*     */       } else {
/*  53 */         String itemName = item.getType().toString().replaceAll("_", " ");
/*  54 */         itemName = WordUtils.capitalizeFully(itemName);
/*     */         
/*  56 */         Enchantment enchantment = getEnchantment(args[1].toUpperCase());
/*  57 */         if (enchantment == null) {
/*  58 */           sender.sendMessage(String.format("Enchantment does not exist: %s", new Object[] { args[1] }));
/*     */         } else {
/*  60 */           String enchantmentName = enchantment.getName().replaceAll("_", " ");
/*  61 */           enchantmentName = WordUtils.capitalizeFully(enchantmentName);
/*     */           
/*  63 */           if (!force && !enchantment.canEnchantItem(item)) {
/*  64 */             sender.sendMessage(String.format("%s cannot be applied to %s", new Object[] { enchantmentName, itemName }));
/*     */           } else {
/*  66 */             int level = 1;
/*  67 */             if (args.length > 2) {
/*  68 */               Integer integer = getInteger(args[2]);
/*  69 */               int minLevel = enchantment.getStartLevel();
/*  70 */               int maxLevel = force ? 32767 : enchantment.getMaxLevel();
/*     */               
/*  72 */               if (integer != null) {
/*  73 */                 if (integer.intValue() == 0) {
/*  74 */                   item.removeEnchantment(enchantment);
/*  75 */                   Command.broadcastCommandMessage(sender, String.format("Removed %s on %s's %s", new Object[] { enchantmentName, player.getName(), itemName }));
/*  76 */                   return true;
/*     */                 } 
/*     */                 
/*  79 */                 if (integer.intValue() < minLevel || integer.intValue() > maxLevel) {
/*  80 */                   sender.sendMessage(String.format("Level for enchantment %s must be between %d and %d", new Object[] { enchantmentName, Integer.valueOf(minLevel), Integer.valueOf(maxLevel) }));
/*  81 */                   sender.sendMessage("Specify 0 for level to remove an enchantment");
/*  82 */                   return true;
/*     */                 } 
/*     */                 
/*  85 */                 level = integer.intValue();
/*     */               } 
/*     */               
/*  88 */               if ("max".equals(args[2])) {
/*  89 */                 level = maxLevel;
/*     */               }
/*     */             } 
/*     */             
/*  93 */             Map<Enchantment, Integer> enchantments = item.getEnchantments();
/*  94 */             boolean conflicts = false;
/*     */             
/*  96 */             if (!force && !enchantments.isEmpty()) {
/*  97 */               for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
/*  98 */                 Enchantment enchant = entry.getKey();
/*     */                 
/* 100 */                 if (!enchant.equals(enchantment) && 
/* 101 */                   enchant.conflictsWith(enchantment)) {
/* 102 */                   sender.sendMessage(String.format("Can't apply the enchantment %s on an item with the enchantment %s", new Object[] { enchantmentName, WordUtils.capitalizeFully(enchant.getName().replaceAll("_", " ")) }));
/* 103 */                   conflicts = true;
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             }
/* 109 */             if (!conflicts) {
/* 110 */               item.addUnsafeEnchantment(enchantment, level);
/*     */               
/* 112 */               Command.broadcastCommandMessage(sender, String.format("Applied %s (Lvl %d) on %s's %s", new Object[] { enchantmentName, Integer.valueOf(level), player.getName(), itemName }), false);
/* 113 */               sender.sendMessage(String.format("Enchanting succeeded, applied %s (Lvl %d) onto your %s", new Object[] { enchantmentName, Integer.valueOf(level), itemName }));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 124 */     Validate.notNull(sender, "Sender cannot be null");
/* 125 */     Validate.notNull(args, "Arguments cannot be null");
/* 126 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 128 */     if (args.length == 1) {
/* 129 */       return super.tabComplete(sender, alias, args);
/*     */     }
/*     */     
/* 132 */     if (args.length == 2) {
/* 133 */       return (List<String>)StringUtil.copyPartialMatches(args[1], ENCHANTMENT_NAMES, new ArrayList(ENCHANTMENT_NAMES.size()));
/*     */     }
/*     */     
/* 136 */     if ((args.length == 3 || args.length == 4) && 
/* 137 */       !args[args.length - 2].equalsIgnoreCase("force")) {
/* 138 */       return (List<String>)ImmutableList.of("force");
/*     */     }
/*     */ 
/*     */     
/* 142 */     return (List<String>)ImmutableList.of();
/*     */   }
/*     */   
/*     */   private Enchantment getEnchantment(String lookup) {
/* 146 */     Enchantment enchantment = Enchantment.getByName(lookup);
/*     */     
/* 148 */     if (enchantment == null) {
/* 149 */       Integer id = getInteger(lookup);
/* 150 */       if (id != null) {
/* 151 */         enchantment = Enchantment.getById(id.intValue());
/*     */       }
/*     */     } 
/*     */     
/* 155 */     return enchantment;
/*     */   }
/*     */   
/*     */   public static void buildEnchantments() {
/* 159 */     if (!ENCHANTMENT_NAMES.isEmpty()) {
/* 160 */       throw new IllegalStateException("Enchantments have already been built!");
/*     */     }
/*     */     
/* 163 */     for (Enchantment enchantment : Enchantment.values()) {
/* 164 */       ENCHANTMENT_NAMES.add(enchantment.getName());
/*     */     }
/*     */     
/* 167 */     Collections.sort(ENCHANTMENT_NAMES);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\EnchantCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */