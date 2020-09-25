/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum DyeColor
/*     */ {
/*  15 */   WHITE(0, 15, Color.WHITE, Color.fromRGB(15790320)),
/*     */ 
/*     */ 
/*     */   
/*  19 */   ORANGE(1, 14, Color.fromRGB(14188339), Color.fromRGB(15435844)),
/*     */ 
/*     */ 
/*     */   
/*  23 */   MAGENTA(2, 13, Color.fromRGB(11685080), Color.fromRGB(12801229)),
/*     */ 
/*     */ 
/*     */   
/*  27 */   LIGHT_BLUE(3, 12, Color.fromRGB(6724056), Color.fromRGB(6719955)),
/*     */ 
/*     */ 
/*     */   
/*  31 */   YELLOW(4, 11, Color.fromRGB(15066419), Color.fromRGB(14602026)),
/*     */ 
/*     */ 
/*     */   
/*  35 */   LIME(5, 10, Color.fromRGB(8375321), Color.fromRGB(4312372)),
/*     */ 
/*     */ 
/*     */   
/*  39 */   PINK(6, 9, Color.fromRGB(15892389), Color.fromRGB(14188952)),
/*     */ 
/*     */ 
/*     */   
/*  43 */   GRAY(7, 8, Color.fromRGB(5000268), Color.fromRGB(4408131)),
/*     */ 
/*     */ 
/*     */   
/*  47 */   SILVER(8, 7, Color.fromRGB(10066329), Color.fromRGB(11250603)),
/*     */ 
/*     */ 
/*     */   
/*  51 */   CYAN(9, 6, Color.fromRGB(5013401), Color.fromRGB(2651799)),
/*     */ 
/*     */ 
/*     */   
/*  55 */   PURPLE(10, 5, Color.fromRGB(8339378), Color.fromRGB(8073150)),
/*     */ 
/*     */ 
/*     */   
/*  59 */   BLUE(11, 4, Color.fromRGB(3361970), Color.fromRGB(2437522)),
/*     */ 
/*     */ 
/*     */   
/*  63 */   BROWN(12, 3, Color.fromRGB(6704179), Color.fromRGB(5320730)),
/*     */ 
/*     */ 
/*     */   
/*  67 */   GREEN(13, 2, Color.fromRGB(6717235), Color.fromRGB(3887386)),
/*     */ 
/*     */ 
/*     */   
/*  71 */   RED(14, 1, Color.fromRGB(10040115), Color.fromRGB(11743532)),
/*     */ 
/*     */ 
/*     */   
/*  75 */   BLACK(15, 0, Color.fromRGB(1644825), Color.fromRGB(1973019));
/*     */   
/*     */   private final byte woolData;
/*     */   private final byte dyeData;
/*     */   private final Color color;
/*     */   private final Color firework;
/*     */   private static final DyeColor[] BY_WOOL_DATA;
/*     */   private static final DyeColor[] BY_DYE_DATA;
/*     */   private static final Map<Color, DyeColor> BY_COLOR;
/*     */   private static final Map<Color, DyeColor> BY_FIREWORK;
/*     */   
/*     */   DyeColor(int woolData, int dyeData, Color color, Color firework) {
/*  87 */     this.woolData = (byte)woolData;
/*  88 */     this.dyeData = (byte)dyeData;
/*  89 */     this.color = color;
/*  90 */     this.firework = firework;
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
/*     */   
/*     */   @Deprecated
/*     */   public byte getData() {
/* 104 */     return getWoolData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public byte getWoolData() {
/* 116 */     return this.woolData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public byte getDyeData() {
/* 128 */     return this.dyeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 137 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getFireworkColor() {
/* 146 */     return this.firework;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static DyeColor getByData(byte data) {
/* 162 */     return getByWoolData(data);
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
/*     */   
/*     */   @Deprecated
/*     */   public static DyeColor getByWoolData(byte data) {
/* 176 */     int i = 0xFF & data;
/* 177 */     if (i >= BY_WOOL_DATA.length) {
/* 178 */       return null;
/*     */     }
/* 180 */     return BY_WOOL_DATA[i];
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
/*     */   
/*     */   @Deprecated
/*     */   public static DyeColor getByDyeData(byte data) {
/* 194 */     int i = 0xFF & data;
/* 195 */     if (i >= BY_DYE_DATA.length) {
/* 196 */       return null;
/*     */     }
/* 198 */     return BY_DYE_DATA[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DyeColor getByColor(Color color) {
/* 209 */     return BY_COLOR.get(color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DyeColor getByFireworkColor(Color color) {
/* 220 */     return BY_FIREWORK.get(color);
/*     */   }
/*     */   
/*     */   static {
/* 224 */     BY_WOOL_DATA = values();
/* 225 */     BY_DYE_DATA = values();
/* 226 */     ImmutableMap.Builder<Color, DyeColor> byColor = ImmutableMap.builder();
/* 227 */     ImmutableMap.Builder<Color, DyeColor> byFirework = ImmutableMap.builder();
/*     */     
/* 229 */     for (DyeColor color : values()) {
/* 230 */       BY_WOOL_DATA[color.woolData & 0xFF] = color;
/* 231 */       BY_DYE_DATA[color.dyeData & 0xFF] = color;
/* 232 */       byColor.put(color.getColor(), color);
/* 233 */       byFirework.put(color.getFireworkColor(), color);
/*     */     } 
/*     */     
/* 236 */     BY_COLOR = (Map<Color, DyeColor>)byColor.build();
/* 237 */     BY_FIREWORK = (Map<Color, DyeColor>)byFirework.build();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\DyeColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */