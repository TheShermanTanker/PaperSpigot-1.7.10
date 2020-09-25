/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ public class GiveCommand
/*     */   extends VanillaCommand {
/*     */   private static List<String> materials;
/*     */   
/*     */   static {
/*  24 */     ArrayList<String> materialList = new ArrayList<String>();
/*  25 */     for (Material material : Material.values()) {
/*  26 */       materialList.add(material.name());
/*     */     }
/*  28 */     Collections.sort(materialList);
/*  29 */     materials = (List<String>)ImmutableList.copyOf(materialList);
/*     */   }
/*     */   
/*     */   public GiveCommand() {
/*  33 */     super("give");
/*  34 */     this.description = "Gives the specified player a certain amount of items";
/*  35 */     this.usageMessage = "/give <player> <item> [amount [data]]";
/*  36 */     setPermission("bukkit.command.give");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/*  41 */     if (!testPermission(sender)) return true; 
/*  42 */     if (args.length < 2) {
/*  43 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  44 */       return false;
/*     */     } 
/*     */     
/*  47 */     Player player = Bukkit.getPlayerExact(args[0]);
/*     */     
/*  49 */     if (player != null) {
/*  50 */       Material material = Material.matchMaterial(args[1]);
/*     */       
/*  52 */       if (material == null) {
/*  53 */         material = Bukkit.getUnsafe().getMaterialFromInternalName(args[1]);
/*     */       }
/*     */       
/*  56 */       if (material != null) {
/*  57 */         int amount = 1;
/*  58 */         short data = 0;
/*     */         
/*  60 */         if (args.length >= 3) {
/*  61 */           amount = getInteger(sender, args[2], 1, 64);
/*     */           
/*  63 */           if (args.length >= 4) {
/*     */             try {
/*  65 */               data = Short.parseShort(args[3]);
/*  66 */             } catch (NumberFormatException ex) {}
/*     */           }
/*     */         } 
/*     */         
/*  70 */         ItemStack stack = new ItemStack(material, amount, data);
/*     */         
/*  72 */         if (args.length >= 5) {
/*     */           try {
/*  74 */             stack = Bukkit.getUnsafe().modifyItemStack(stack, Joiner.on(' ').join(Arrays.<String>asList(args).subList(4, args.length)));
/*  75 */           } catch (Throwable t) {
/*  76 */             player.sendMessage("Not a valid tag");
/*  77 */             return true;
/*     */           } 
/*     */         }
/*     */         
/*  81 */         player.getInventory().addItem(new ItemStack[] { stack });
/*     */         
/*  83 */         Command.broadcastCommandMessage(sender, "Gave " + player.getName() + " some " + material.getId() + " (" + material + ")");
/*     */       } else {
/*  85 */         sender.sendMessage("There's no item called " + args[1]);
/*     */       } 
/*     */     } else {
/*  88 */       sender.sendMessage("Can't find player " + args[0]);
/*     */     } 
/*     */     
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/*  96 */     Validate.notNull(sender, "Sender cannot be null");
/*  97 */     Validate.notNull(args, "Arguments cannot be null");
/*  98 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 100 */     if (args.length == 1) {
/* 101 */       return super.tabComplete(sender, alias, args);
/*     */     }
/* 103 */     if (args.length == 2) {
/* 104 */       String arg = args[1];
/* 105 */       List<String> materials = GiveCommand.materials;
/* 106 */       List<String> completion = new ArrayList<String>();
/*     */       
/* 108 */       int size = materials.size();
/* 109 */       int i = Collections.binarySearch(materials, arg, String.CASE_INSENSITIVE_ORDER);
/*     */       
/* 111 */       if (i < 0)
/*     */       {
/* 113 */         i = -1 - i;
/*     */       }
/*     */       
/* 116 */       while (i < size) {
/* 117 */         String material = materials.get(i);
/* 118 */         if (StringUtil.startsWithIgnoreCase(material, arg)) {
/* 119 */           completion.add(material);
/*     */           
/*     */           i++;
/*     */         } 
/*     */       } 
/*     */       
/* 125 */       return Bukkit.getUnsafe().tabCompleteInternalMaterialName(arg, completion);
/*     */     } 
/* 127 */     return (List<String>)ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\GiveCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */