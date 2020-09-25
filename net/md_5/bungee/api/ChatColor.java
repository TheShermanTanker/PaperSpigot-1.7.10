/*     */ package net.md_5.bungee.api;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ChatColor
/*     */ {
/*  17 */   BLACK('0', "black"),
/*     */ 
/*     */ 
/*     */   
/*  21 */   DARK_BLUE('1', "dark_blue"),
/*     */ 
/*     */ 
/*     */   
/*  25 */   DARK_GREEN('2', "dark_green"),
/*     */ 
/*     */ 
/*     */   
/*  29 */   DARK_AQUA('3', "dark_aqua"),
/*     */ 
/*     */ 
/*     */   
/*  33 */   DARK_RED('4', "dark_red"),
/*     */ 
/*     */ 
/*     */   
/*  37 */   DARK_PURPLE('5', "dark_purple"),
/*     */ 
/*     */ 
/*     */   
/*  41 */   GOLD('6', "gold"),
/*     */ 
/*     */ 
/*     */   
/*  45 */   GRAY('7', "gray"),
/*     */ 
/*     */ 
/*     */   
/*  49 */   DARK_GRAY('8', "dark_gray"),
/*     */ 
/*     */ 
/*     */   
/*  53 */   BLUE('9', "blue"),
/*     */ 
/*     */ 
/*     */   
/*  57 */   GREEN('a', "green"),
/*     */ 
/*     */ 
/*     */   
/*  61 */   AQUA('b', "aqua"),
/*     */ 
/*     */ 
/*     */   
/*  65 */   RED('c', "red"),
/*     */ 
/*     */ 
/*     */   
/*  69 */   LIGHT_PURPLE('d', "light_purple"),
/*     */ 
/*     */ 
/*     */   
/*  73 */   YELLOW('e', "yellow"),
/*     */ 
/*     */ 
/*     */   
/*  77 */   WHITE('f', "white"),
/*     */ 
/*     */ 
/*     */   
/*  81 */   MAGIC('k', "obfuscated"),
/*     */ 
/*     */ 
/*     */   
/*  85 */   BOLD('l', "bold"),
/*     */ 
/*     */ 
/*     */   
/*  89 */   STRIKETHROUGH('m', "strikethrough"),
/*     */ 
/*     */ 
/*     */   
/*  93 */   UNDERLINE('n', "underline"),
/*     */ 
/*     */ 
/*     */   
/*  97 */   ITALIC('o', "italic"),
/*     */ 
/*     */ 
/*     */   
/* 101 */   RESET('r', "reset");
/*     */   public static final char COLOR_CHAR = '§';
/*     */   public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";
/*     */   public static final Pattern STRIP_COLOR_PATTERN;
/*     */   private static final Map<Character, ChatColor> BY_CHAR;
/*     */   private final char code;
/*     */   private final String toString;
/*     */   private final String name;
/*     */   
/*     */   static {
/* 111 */     STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
/*     */ 
/*     */ 
/*     */     
/* 115 */     BY_CHAR = new HashMap<Character, ChatColor>();
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
/*     */     
/* 129 */     for (ChatColor colour : values())
/*     */     {
/* 131 */       BY_CHAR.put(Character.valueOf(colour.code), colour);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   ChatColor(char code, String name) {
/* 137 */     this.code = code;
/* 138 */     this.name = name;
/* 139 */     this.toString = new String(new char[] { '§', code });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*     */     return this.name;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 148 */     return this.toString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String stripColor(String input) {
/* 159 */     if (input == null)
/*     */     {
/* 161 */       return null;
/*     */     }
/*     */     
/* 164 */     return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
/*     */   }
/*     */ 
/*     */   
/*     */   public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
/* 169 */     char[] b = textToTranslate.toCharArray();
/* 170 */     for (int i = 0; i < b.length - 1; i++) {
/*     */       
/* 172 */       if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
/*     */         
/* 174 */         b[i] = '§';
/* 175 */         b[i + 1] = Character.toLowerCase(b[i + 1]);
/*     */       } 
/*     */     } 
/* 178 */     return new String(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChatColor getByChar(char code) {
/* 189 */     return BY_CHAR.get(Character.valueOf(code));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\api\ChatColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */