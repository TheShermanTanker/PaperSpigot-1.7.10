/*     */ package org.bukkit.command;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FormattedCommandAlias
/*     */   extends Command
/*     */ {
/*     */   private final String[] formatStrings;
/*     */   
/*     */   public FormattedCommandAlias(String alias, String[] formatStrings) {
/*  16 */     super(alias);
/*  17 */     this.formatStrings = formatStrings;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
/*  22 */     boolean result = false;
/*  23 */     ArrayList<String> commands = new ArrayList<String>();
/*  24 */     for (String formatString : this.formatStrings) {
/*     */       try {
/*  26 */         commands.add(buildCommand(formatString, args));
/*  27 */       } catch (Throwable throwable) {
/*  28 */         if (throwable instanceof IllegalArgumentException) {
/*  29 */           sender.sendMessage(throwable.getMessage());
/*     */         } else {
/*  31 */           sender.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
/*     */         } 
/*  33 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/*  37 */     for (String command : commands) {
/*  38 */       result |= Bukkit.dispatchCommand(sender, command);
/*     */     }
/*     */     
/*  41 */     return result;
/*     */   }
/*     */   
/*     */   private String buildCommand(String formatString, String[] args) {
/*  45 */     int index = formatString.indexOf("$");
/*  46 */     while (index != -1) {
/*  47 */       int start = index;
/*     */       
/*  49 */       if (index > 0 && formatString.charAt(start - 1) == '\\') {
/*  50 */         formatString = formatString.substring(0, start - 1) + formatString.substring(start);
/*  51 */         index = formatString.indexOf("$", index);
/*     */         
/*     */         continue;
/*     */       } 
/*  55 */       boolean required = false;
/*  56 */       if (formatString.charAt(index + 1) == '$') {
/*  57 */         required = true;
/*     */         
/*  59 */         index++;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  64 */       int argStart = ++index;
/*  65 */       while (index < formatString.length() && inRange(formatString.charAt(index) - 48, 0, 9))
/*     */       {
/*  67 */         index++;
/*     */       }
/*     */ 
/*     */       
/*  71 */       if (argStart == index) {
/*  72 */         throw new IllegalArgumentException("Invalid replacement token");
/*     */       }
/*     */       
/*  75 */       int position = Integer.valueOf(formatString.substring(argStart, index)).intValue();
/*     */ 
/*     */       
/*  78 */       if (position == 0) {
/*  79 */         throw new IllegalArgumentException("Invalid replacement token");
/*     */       }
/*     */ 
/*     */       
/*  83 */       position--;
/*     */       
/*  85 */       boolean rest = false;
/*  86 */       if (index < formatString.length() && formatString.charAt(index) == '-') {
/*  87 */         rest = true;
/*     */         
/*  89 */         index++;
/*     */       } 
/*     */       
/*  92 */       int end = index;
/*     */       
/*  94 */       if (required && position >= args.length) {
/*  95 */         throw new IllegalArgumentException("Missing required argument " + (position + 1));
/*     */       }
/*     */       
/*  98 */       StringBuilder replacement = new StringBuilder();
/*  99 */       if (rest && position < args.length) {
/* 100 */         for (int i = position; i < args.length; i++) {
/* 101 */           if (i != position) {
/* 102 */             replacement.append(' ');
/*     */           }
/* 104 */           replacement.append(args[i]);
/*     */         } 
/* 106 */       } else if (position < args.length) {
/* 107 */         replacement.append(args[position]);
/*     */       } 
/*     */       
/* 110 */       formatString = formatString.substring(0, start) + replacement.toString() + formatString.substring(end);
/*     */       
/* 112 */       index = start + replacement.length();
/*     */ 
/*     */       
/* 115 */       index = formatString.indexOf("$", index);
/*     */     } 
/*     */     
/* 118 */     return formatString;
/*     */   }
/*     */   
/*     */   private static boolean inRange(int i, int j, int k) {
/* 122 */     return (i >= j && i <= k);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\FormattedCommandAlias.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */