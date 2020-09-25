/*    */ package com.google.common.base;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Defaults
/*    */ {
/*    */   private static final Map<Class<?>, Object> DEFAULTS;
/*    */   
/*    */   static {
/* 34 */     Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
/* 35 */     put(map, boolean.class, Boolean.valueOf(false));
/* 36 */     put(map, char.class, Character.valueOf(false));
/* 37 */     put(map, byte.class, Byte.valueOf((byte)0));
/* 38 */     put(map, short.class, Short.valueOf((short)0));
/* 39 */     put(map, int.class, Integer.valueOf(0));
/* 40 */     put(map, long.class, Long.valueOf(0L));
/* 41 */     put(map, float.class, Float.valueOf(0.0F));
/* 42 */     put(map, double.class, Double.valueOf(0.0D));
/* 43 */     DEFAULTS = Collections.unmodifiableMap(map);
/*    */   }
/*    */   
/*    */   private static <T> void put(Map<Class<?>, Object> map, Class<T> type, T value) {
/* 47 */     map.put(type, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> T defaultValue(Class<T> type) {
/* 57 */     return (T)DEFAULTS.get(type);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Defaults.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */