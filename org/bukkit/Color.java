/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.SerializableAs;
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
/*     */ @SerializableAs("Color")
/*     */ public final class Color
/*     */   implements ConfigurationSerializable
/*     */ {
/*     */   private static final int BIT_MASK = 255;
/*  23 */   public static final Color WHITE = fromRGB(16777215);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   public static final Color SILVER = fromRGB(12632256);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static final Color GRAY = fromRGB(8421504);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public static final Color BLACK = fromRGB(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public static final Color RED = fromRGB(16711680);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final Color MAROON = fromRGB(8388608);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static final Color YELLOW = fromRGB(16776960);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final Color OLIVE = fromRGB(8421376);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final Color LIME = fromRGB(65280);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final Color GREEN = fromRGB(32768);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final Color AQUA = fromRGB(65535);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public static final Color TEAL = fromRGB(32896);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final Color BLUE = fromRGB(255);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final Color NAVY = fromRGB(128);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static final Color FUCHSIA = fromRGB(16711935);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public static final Color PURPLE = fromRGB(8388736);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static final Color ORANGE = fromRGB(16753920);
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte red;
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte green;
/*     */ 
/*     */   
/*     */   private final byte blue;
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color fromRGB(int red, int green, int blue) throws IllegalArgumentException {
/* 119 */     return new Color(red, green, blue);
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
/*     */   public static Color fromBGR(int blue, int green, int red) throws IllegalArgumentException {
/* 132 */     return new Color(red, green, blue);
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
/*     */   public static Color fromRGB(int rgb) throws IllegalArgumentException {
/* 145 */     Validate.isTrue((rgb >> 24 == 0), "Extrenuous data in: ", rgb);
/* 146 */     return fromRGB(rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb >> 0 & 0xFF);
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
/*     */   public static Color fromBGR(int bgr) throws IllegalArgumentException {
/* 159 */     Validate.isTrue((bgr >> 24 == 0), "Extrenuous data in: ", bgr);
/* 160 */     return fromBGR(bgr >> 16 & 0xFF, bgr >> 8 & 0xFF, bgr >> 0 & 0xFF);
/*     */   }
/*     */   
/*     */   private Color(int red, int green, int blue) {
/* 164 */     Validate.isTrue((red >= 0 && red <= 255), "Red is not between 0-255: ", red);
/* 165 */     Validate.isTrue((green >= 0 && green <= 255), "Green is not between 0-255: ", green);
/* 166 */     Validate.isTrue((blue >= 0 && blue <= 255), "Blue is not between 0-255: ", blue);
/*     */     
/* 168 */     this.red = (byte)red;
/* 169 */     this.green = (byte)green;
/* 170 */     this.blue = (byte)blue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRed() {
/* 179 */     return 0xFF & this.red;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color setRed(int red) {
/* 189 */     return fromRGB(red, getGreen(), getBlue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGreen() {
/* 198 */     return 0xFF & this.green;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color setGreen(int green) {
/* 208 */     return fromRGB(getRed(), green, getBlue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlue() {
/* 217 */     return 0xFF & this.blue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color setBlue(int blue) {
/* 227 */     return fromRGB(getRed(), getGreen(), blue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int asRGB() {
/* 235 */     return getRed() << 16 | getGreen() << 8 | getBlue() << 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int asBGR() {
/* 243 */     return getBlue() << 16 | getGreen() << 8 | getRed() << 0;
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
/*     */   public Color mixDyes(DyeColor... colors) {
/* 255 */     Validate.noNullElements((Object[])colors, "Colors cannot be null");
/*     */     
/* 257 */     Color[] toPass = new Color[colors.length];
/* 258 */     for (int i = 0; i < colors.length; i++) {
/* 259 */       toPass[i] = colors[i].getColor();
/*     */     }
/*     */     
/* 262 */     return mixColors(toPass);
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
/*     */   public Color mixColors(Color... colors) {
/* 274 */     Validate.noNullElements((Object[])colors, "Colors cannot be null");
/*     */     
/* 276 */     int totalRed = getRed();
/* 277 */     int totalGreen = getGreen();
/* 278 */     int totalBlue = getBlue();
/* 279 */     int totalMax = Math.max(Math.max(totalRed, totalGreen), totalBlue);
/* 280 */     for (Color color : colors) {
/* 281 */       totalRed += color.getRed();
/* 282 */       totalGreen += color.getGreen();
/* 283 */       totalBlue += color.getBlue();
/* 284 */       totalMax += Math.max(Math.max(color.getRed(), color.getGreen()), color.getBlue());
/*     */     } 
/*     */     
/* 287 */     float averageRed = (totalRed / (colors.length + 1));
/* 288 */     float averageGreen = (totalGreen / (colors.length + 1));
/* 289 */     float averageBlue = (totalBlue / (colors.length + 1));
/* 290 */     float averageMax = (totalMax / (colors.length + 1));
/*     */     
/* 292 */     float maximumOfAverages = Math.max(Math.max(averageRed, averageGreen), averageBlue);
/* 293 */     float gainFactor = averageMax / maximumOfAverages;
/*     */     
/* 295 */     return fromRGB((int)(averageRed * gainFactor), (int)(averageGreen * gainFactor), (int)(averageBlue * gainFactor));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 300 */     if (!(o instanceof Color)) {
/* 301 */       return false;
/*     */     }
/* 303 */     Color that = (Color)o;
/* 304 */     return (this.blue == that.blue && this.green == that.green && this.red == that.red);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 309 */     return asRGB() ^ Color.class.hashCode();
/*     */   }
/*     */   
/*     */   public Map<String, Object> serialize() {
/* 313 */     return (Map<String, Object>)ImmutableMap.of("RED", Integer.valueOf(getRed()), "BLUE", Integer.valueOf(getBlue()), "GREEN", Integer.valueOf(getGreen()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color deserialize(Map<String, Object> map) {
/* 322 */     return fromRGB(asInt("RED", map), asInt("GREEN", map), asInt("BLUE", map));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int asInt(String string, Map<String, Object> map) {
/* 330 */     Object value = map.get(string);
/* 331 */     if (value == null) {
/* 332 */       throw new IllegalArgumentException(string + " not in map " + map);
/*     */     }
/* 334 */     if (!(value instanceof Number)) {
/* 335 */       throw new IllegalArgumentException(string + '(' + value + ") is not a number");
/*     */     }
/* 337 */     return ((Number)value).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 342 */     return "Color:[rgb0x" + Integer.toHexString(getRed()).toUpperCase() + Integer.toHexString(getGreen()).toUpperCase() + Integer.toHexString(getBlue()).toUpperCase() + "]";
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Color.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */