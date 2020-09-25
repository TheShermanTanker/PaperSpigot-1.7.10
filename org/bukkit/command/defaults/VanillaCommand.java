/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ public abstract class VanillaCommand
/*     */   extends Command {
/*     */   static final int MAX_COORD = 30000000;
/*     */   static final int MIN_COORD_MINUS_ONE = -30000001;
/*     */   static final int MIN_COORD = -30000000;
/*     */   
/*     */   protected VanillaCommand(String name) {
/*  14 */     super(name);
/*     */   }
/*     */   
/*     */   protected VanillaCommand(String name, String description, String usageMessage, List<String> aliases) {
/*  18 */     super(name, description, usageMessage, aliases);
/*     */   }
/*     */   
/*     */   public boolean matches(String input) {
/*  22 */     return input.equalsIgnoreCase(getName());
/*     */   }
/*     */   
/*     */   protected int getInteger(CommandSender sender, String value, int min) {
/*  26 */     return getInteger(sender, value, min, 2147483647);
/*     */   }
/*     */   
/*     */   int getInteger(CommandSender sender, String value, int min, int max) {
/*  30 */     return getInteger(sender, value, min, max, false);
/*     */   }
/*     */   
/*     */   int getInteger(CommandSender sender, String value, int min, int max, boolean Throws) {
/*  34 */     int i = min;
/*     */     
/*     */     try {
/*  37 */       i = Integer.valueOf(value).intValue();
/*  38 */     } catch (NumberFormatException ex) {
/*  39 */       if (Throws) {
/*  40 */         throw new NumberFormatException(String.format("%s is not a valid number", new Object[] { value }));
/*     */       }
/*     */     } 
/*     */     
/*  44 */     if (i < min) {
/*  45 */       i = min;
/*  46 */     } else if (i > max) {
/*  47 */       i = max;
/*     */     } 
/*     */     
/*  50 */     return i;
/*     */   }
/*     */   
/*     */   Integer getInteger(String value) {
/*     */     try {
/*  55 */       return Integer.valueOf(value);
/*  56 */     } catch (NumberFormatException ex) {
/*  57 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double getRelativeDouble(double original, CommandSender sender, String input) {
/*  62 */     if (input.startsWith("~")) {
/*  63 */       double value = getDouble(sender, input.substring(1));
/*  64 */       if (value == -3.0000001E7D) {
/*  65 */         return -3.0000001E7D;
/*     */       }
/*  67 */       return original + value;
/*     */     } 
/*  69 */     return getDouble(sender, input);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getDouble(CommandSender sender, String input) {
/*     */     try {
/*  75 */       return Double.parseDouble(input);
/*  76 */     } catch (NumberFormatException ex) {
/*  77 */       return -3.0000001E7D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double getDouble(CommandSender sender, String input, double min, double max) {
/*  82 */     double result = getDouble(sender, input);
/*     */ 
/*     */     
/*  85 */     if (result < min) {
/*  86 */       result = min;
/*  87 */     } else if (result > max) {
/*  88 */       result = max;
/*     */     } 
/*     */     
/*  91 */     return result;
/*     */   }
/*     */   
/*     */   String createString(String[] args, int start) {
/*  95 */     return createString(args, start, " ");
/*     */   }
/*     */   
/*     */   String createString(String[] args, int start, String glue) {
/*  99 */     StringBuilder string = new StringBuilder();
/*     */     
/* 101 */     for (int x = start; x < args.length; x++) {
/* 102 */       string.append(args[x]);
/* 103 */       if (x != args.length - 1) {
/* 104 */         string.append(glue);
/*     */       }
/*     */     } 
/*     */     
/* 108 */     return string.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\VanillaCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */