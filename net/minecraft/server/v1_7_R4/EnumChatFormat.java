/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ public enum EnumChatFormat {
/*     */   private static final Map w;
/*     */   private static final Map x;
/*     */   private static final Pattern y;
/*   7 */   BLACK('0'),
/*   8 */   DARK_BLUE('1'),
/*   9 */   DARK_GREEN('2'),
/*  10 */   DARK_AQUA('3'),
/*  11 */   DARK_RED('4'),
/*  12 */   DARK_PURPLE('5'),
/*  13 */   GOLD('6'),
/*  14 */   GRAY('7'),
/*  15 */   DARK_GRAY('8'),
/*  16 */   BLUE('9'),
/*  17 */   GREEN('a'),
/*  18 */   AQUA('b'),
/*  19 */   RED('c'),
/*  20 */   LIGHT_PURPLE('d'),
/*  21 */   YELLOW('e'),
/*  22 */   WHITE('f'),
/*  23 */   RANDOM('k', true),
/*  24 */   BOLD('l', true),
/*  25 */   STRIKETHROUGH('m', true),
/*  26 */   UNDERLINE('n', true),
/*  27 */   ITALIC('o', true),
/*  28 */   RESET('r'); private final char z; private final boolean A; private final String B;
/*     */   
/*     */   static {
/*  31 */     w = new HashMap<Object, Object>();
/*  32 */     x = new HashMap<Object, Object>();
/*  33 */     y = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
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
/*  51 */     for (EnumChatFormat enumChatFormat : values()) {
/*  52 */       w.put(Character.valueOf(enumChatFormat.getChar()), enumChatFormat);
/*  53 */       x.put(enumChatFormat.d(), enumChatFormat);
/*     */     }  } EnumChatFormat(char paramChar, boolean paramBoolean) { this.z = paramChar;
/*     */     this.A = paramBoolean;
/*     */     this.B = "§" + paramChar; }
/*     */   public char getChar() {
/*  58 */     return this.z;
/*     */   }
/*     */   
/*     */   public boolean isFormat() {
/*  62 */     return this.A;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  66 */     return (!this.A && this != RESET);
/*     */   }
/*     */   
/*     */   public String d() {
/*  70 */     return name().toLowerCase();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  75 */     return this.B;
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
/*     */   public static EnumChatFormat b(String paramString) {
/*  87 */     if (paramString == null) return null; 
/*  88 */     return (EnumChatFormat)x.get(paramString.toLowerCase());
/*     */   }
/*     */   
/*     */   public static Collection a(boolean paramBoolean1, boolean paramBoolean2) {
/*  92 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/*  94 */     for (EnumChatFormat enumChatFormat : values()) {
/*  95 */       if ((!enumChatFormat.c() || paramBoolean1) && (
/*  96 */         !enumChatFormat.isFormat() || paramBoolean2)) {
/*  97 */         arrayList.add(enumChatFormat.d());
/*     */       }
/*     */     } 
/* 100 */     return arrayList;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumChatFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */