/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.Validate;
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
/*  17 */   BLACK('0', 0),
/*     */ 
/*     */ 
/*     */   
/*  21 */   DARK_BLUE('1', 1),
/*     */ 
/*     */ 
/*     */   
/*  25 */   DARK_GREEN('2', 2),
/*     */ 
/*     */ 
/*     */   
/*  29 */   DARK_AQUA('3', 3),
/*     */ 
/*     */ 
/*     */   
/*  33 */   DARK_RED('4', 4),
/*     */ 
/*     */ 
/*     */   
/*  37 */   DARK_PURPLE('5', 5),
/*     */ 
/*     */ 
/*     */   
/*  41 */   GOLD('6', 6),
/*     */ 
/*     */ 
/*     */   
/*  45 */   GRAY('7', 7),
/*     */ 
/*     */ 
/*     */   
/*  49 */   DARK_GRAY('8', 8),
/*     */ 
/*     */ 
/*     */   
/*  53 */   BLUE('9', 9),
/*     */ 
/*     */ 
/*     */   
/*  57 */   GREEN('a', 10),
/*     */ 
/*     */ 
/*     */   
/*  61 */   AQUA('b', 11),
/*     */ 
/*     */ 
/*     */   
/*  65 */   RED('c', 12),
/*     */ 
/*     */ 
/*     */   
/*  69 */   LIGHT_PURPLE('d', 13),
/*     */ 
/*     */ 
/*     */   
/*  73 */   YELLOW('e', 14),
/*     */ 
/*     */ 
/*     */   
/*  77 */   WHITE('f', 15),
/*     */ 
/*     */ 
/*     */   
/*  81 */   MAGIC('k', 16, true),
/*     */ 
/*     */ 
/*     */   
/*  85 */   BOLD('l', 17, true),
/*     */ 
/*     */ 
/*     */   
/*  89 */   STRIKETHROUGH('m', 18, true),
/*     */ 
/*     */ 
/*     */   
/*  93 */   UNDERLINE('n', 19, true),
/*     */ 
/*     */ 
/*     */   
/*  97 */   ITALIC('o', 20, true),
/*     */ 
/*     */ 
/*     */   
/* 101 */   RESET('r', 21);
/*     */   public static final char COLOR_CHAR = '§';
/*     */   private static final Pattern STRIP_COLOR_PATTERN;
/*     */   private final int intCode;
/*     */   private final char code;
/*     */   
/*     */   static {
/* 108 */     STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     BY_ID = Maps.newHashMap();
/* 115 */     BY_CHAR = Maps.newHashMap();
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
/*     */ 
/*     */     
/* 248 */     for (ChatColor color : values()) {
/* 249 */       BY_ID.put(Integer.valueOf(color.intCode), color);
/* 250 */       BY_CHAR.put(Character.valueOf(color.code), color);
/*     */     } 
/*     */   }
/*     */   
/*     */   private final boolean isFormat;
/*     */   private final String toString;
/*     */   private static final Map<Integer, ChatColor> BY_ID;
/*     */   private static final Map<Character, ChatColor> BY_CHAR;
/*     */   
/*     */   ChatColor(char code, int intCode, boolean isFormat) {
/*     */     this.code = code;
/*     */     this.intCode = intCode;
/*     */     this.isFormat = isFormat;
/*     */     this.toString = new String(new char[] { '§', code });
/*     */   }
/*     */   
/*     */   public char getChar() {
/*     */     return this.code;
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     return this.toString;
/*     */   }
/*     */   
/*     */   public boolean isFormat() {
/*     */     return this.isFormat;
/*     */   }
/*     */   
/*     */   public boolean isColor() {
/*     */     return (!this.isFormat && this != RESET);
/*     */   }
/*     */   
/*     */   public static ChatColor getByChar(char code) {
/*     */     return BY_CHAR.get(Character.valueOf(code));
/*     */   }
/*     */   
/*     */   public static ChatColor getByChar(String code) {
/*     */     Validate.notNull(code, "Code cannot be null");
/*     */     Validate.isTrue((code.length() > 0), "Code must have at least one char");
/*     */     return BY_CHAR.get(Character.valueOf(code.charAt(0)));
/*     */   }
/*     */   
/*     */   public static String stripColor(String input) {
/*     */     if (input == null)
/*     */       return null; 
/*     */     return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
/*     */   }
/*     */   
/*     */   public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
/*     */     char[] b = textToTranslate.toCharArray();
/*     */     for (int i = 0; i < b.length - 1; i++) {
/*     */       if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
/*     */         b[i] = '§';
/*     */         b[i + 1] = Character.toLowerCase(b[i + 1]);
/*     */       } 
/*     */     } 
/*     */     return new String(b);
/*     */   }
/*     */   
/*     */   public static String getLastColors(String input) {
/*     */     String result = "";
/*     */     int length = input.length();
/*     */     for (int index = length - 1; index > -1; index--) {
/*     */       char section = input.charAt(index);
/*     */       if (section == '§' && index < length - 1) {
/*     */         char c = input.charAt(index + 1);
/*     */         ChatColor color = getByChar(c);
/*     */         if (color != null) {
/*     */           result = color.toString() + result;
/*     */           if (color.isColor() || color.equals(RESET))
/*     */             break; 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\ChatColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */