/*     */ package org.bukkit.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NumberConversions
/*     */ {
/*     */   public static int floor(double num) {
/*  10 */     int floor = (int)num;
/*  11 */     return (floor == num) ? floor : (floor - (int)(Double.doubleToRawLongBits(num) >>> 63L));
/*     */   }
/*     */   
/*     */   public static int ceil(double num) {
/*  15 */     int floor = (int)num;
/*  16 */     return (floor == num) ? floor : (floor + (int)((Double.doubleToRawLongBits(num) ^ 0xFFFFFFFFFFFFFFFFL) >>> 63L));
/*     */   }
/*     */   
/*     */   public static int round(double num) {
/*  20 */     return floor(num + 0.5D);
/*     */   }
/*     */   
/*     */   public static double square(double num) {
/*  24 */     return num * num;
/*     */   }
/*     */   
/*     */   public static int toInt(Object object) {
/*  28 */     if (object instanceof Number) {
/*  29 */       return ((Number)object).intValue();
/*     */     }
/*     */ 
/*     */     
/*  33 */     try { return Integer.valueOf(object.toString()).intValue(); }
/*  34 */     catch (NumberFormatException e) {  }
/*  35 */     catch (NullPointerException e) {}
/*     */     
/*  37 */     return 0;
/*     */   }
/*     */   
/*     */   public static float toFloat(Object object) {
/*  41 */     if (object instanceof Number) {
/*  42 */       return ((Number)object).floatValue();
/*     */     }
/*     */ 
/*     */     
/*  46 */     try { return Float.valueOf(object.toString()).floatValue(); }
/*  47 */     catch (NumberFormatException e) {  }
/*  48 */     catch (NullPointerException e) {}
/*     */     
/*  50 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static double toDouble(Object object) {
/*  54 */     if (object instanceof Number) {
/*  55 */       return ((Number)object).doubleValue();
/*     */     }
/*     */ 
/*     */     
/*  59 */     try { return Double.valueOf(object.toString()).doubleValue(); }
/*  60 */     catch (NumberFormatException e) {  }
/*  61 */     catch (NullPointerException e) {}
/*     */     
/*  63 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public static long toLong(Object object) {
/*  67 */     if (object instanceof Number) {
/*  68 */       return ((Number)object).longValue();
/*     */     }
/*     */ 
/*     */     
/*  72 */     try { return Long.valueOf(object.toString()).longValue(); }
/*  73 */     catch (NumberFormatException e) {  }
/*  74 */     catch (NullPointerException e) {}
/*     */     
/*  76 */     return 0L;
/*     */   }
/*     */   
/*     */   public static short toShort(Object object) {
/*  80 */     if (object instanceof Number) {
/*  81 */       return ((Number)object).shortValue();
/*     */     }
/*     */ 
/*     */     
/*  85 */     try { return Short.valueOf(object.toString()).shortValue(); }
/*  86 */     catch (NumberFormatException e) {  }
/*  87 */     catch (NullPointerException e) {}
/*     */     
/*  89 */     return 0;
/*     */   }
/*     */   
/*     */   public static byte toByte(Object object) {
/*  93 */     if (object instanceof Number) {
/*  94 */       return ((Number)object).byteValue();
/*     */     }
/*     */ 
/*     */     
/*  98 */     try { return Byte.valueOf(object.toString()).byteValue(); }
/*  99 */     catch (NumberFormatException e) {  }
/* 100 */     catch (NullPointerException e) {}
/*     */     
/* 102 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\NumberConversions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */