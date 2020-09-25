/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ public class EffectCommand extends VanillaCommand {
/*     */   private static final List<String> effects;
/*     */   
/*     */   public EffectCommand() {
/*  17 */     super("effect");
/*  18 */     this.description = "Adds/Removes effects on players";
/*  19 */     this.usageMessage = "/effect <player> <effect|clear> [seconds] [amplifier]";
/*  20 */     setPermission("bukkit.command.effect");
/*     */   }
/*     */   
/*     */   static {
/*  24 */     ImmutableList.Builder<String> builder = ImmutableList.builder();
/*     */     
/*  26 */     for (PotionEffectType type : PotionEffectType.values()) {
/*  27 */       if (type != null) {
/*  28 */         builder.add(type.getName());
/*     */       }
/*     */     } 
/*     */     
/*  32 */     effects = (List<String>)builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
/*  37 */     if (!testPermission(sender)) {
/*  38 */       return true;
/*     */     }
/*     */     
/*  41 */     if (args.length < 2) {
/*  42 */       sender.sendMessage(getUsage());
/*  43 */       return true;
/*     */     } 
/*     */     
/*  46 */     Player player = sender.getServer().getPlayer(args[0]);
/*     */     
/*  48 */     if (player == null) {
/*  49 */       sender.sendMessage(ChatColor.RED + String.format("Player, %s, not found", new Object[] { args[0] }));
/*  50 */       return true;
/*     */     } 
/*     */     
/*  53 */     if ("clear".equalsIgnoreCase(args[1])) {
/*  54 */       for (PotionEffect potionEffect : player.getActivePotionEffects()) {
/*  55 */         player.removePotionEffect(potionEffect.getType());
/*     */       }
/*  57 */       sender.sendMessage(String.format("Took all effects from %s", new Object[] { args[0] }));
/*  58 */       return true;
/*     */     } 
/*     */     
/*  61 */     PotionEffectType effect = PotionEffectType.getByName(args[1]);
/*     */     
/*  63 */     if (effect == null) {
/*  64 */       effect = PotionEffectType.getById(getInteger(sender, args[1], 0));
/*     */     }
/*     */     
/*  67 */     if (effect == null) {
/*  68 */       sender.sendMessage(ChatColor.RED + String.format("Effect, %s, not found", new Object[] { args[1] }));
/*  69 */       return true;
/*     */     } 
/*     */     
/*  72 */     int duration = 600;
/*  73 */     int duration_temp = 30;
/*  74 */     int amplification = 0;
/*     */     
/*  76 */     if (args.length >= 3) {
/*  77 */       duration_temp = getInteger(sender, args[2], 0, 1000000);
/*  78 */       if (effect.isInstant()) {
/*  79 */         duration = duration_temp;
/*     */       } else {
/*  81 */         duration = duration_temp * 20;
/*     */       } 
/*  83 */     } else if (effect.isInstant()) {
/*  84 */       duration = 1;
/*     */     } 
/*     */     
/*  87 */     if (args.length >= 4) {
/*  88 */       amplification = getInteger(sender, args[3], 0, 255);
/*     */     }
/*     */     
/*  91 */     if (duration_temp == 0) {
/*  92 */       if (!player.hasPotionEffect(effect)) {
/*  93 */         sender.sendMessage(String.format("Couldn't take %s from %s as they do not have the effect", new Object[] { effect.getName(), args[0] }));
/*  94 */         return true;
/*     */       } 
/*     */       
/*  97 */       player.removePotionEffect(effect);
/*  98 */       broadcastCommandMessage(sender, String.format("Took %s from %s", new Object[] { effect.getName(), args[0] }));
/*     */     } else {
/* 100 */       PotionEffect applyEffect = new PotionEffect(effect, duration, amplification);
/*     */       
/* 102 */       player.addPotionEffect(applyEffect, true);
/* 103 */       broadcastCommandMessage(sender, String.format("Given %s (ID %d) * %d to %s for %d seconds", new Object[] { effect.getName(), Integer.valueOf(effect.getId()), Integer.valueOf(amplification), args[0], Integer.valueOf(duration_temp) }));
/*     */     } 
/*     */     
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String commandLabel, String[] args) {
/* 111 */     if (args.length == 1)
/* 112 */       return super.tabComplete(sender, commandLabel, args); 
/* 113 */     if (args.length == 2) {
/* 114 */       return (List<String>)StringUtil.copyPartialMatches(args[1], effects, new ArrayList(effects.size()));
/*     */     }
/*     */     
/* 117 */     return (List<String>)ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\EffectCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */