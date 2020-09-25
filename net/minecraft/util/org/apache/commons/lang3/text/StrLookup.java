/*     */ package net.minecraft.util.org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.util.Map;
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
/*     */ public abstract class StrLookup<V>
/*     */ {
/*  48 */   private static final StrLookup<String> NONE_LOOKUP = new MapStrLookup<String>(null); static {
/*  49 */     StrLookup<String> lookup = null;
/*     */     try {
/*  51 */       Map<?, ?> propMap = System.getProperties();
/*     */       
/*  53 */       Map<?, ?> map1 = propMap;
/*  54 */       lookup = new MapStrLookup((Map)map1);
/*  55 */     } catch (SecurityException ex) {
/*  56 */       lookup = NONE_LOOKUP;
/*     */     } 
/*  58 */     SYSTEM_PROPERTIES_LOOKUP = lookup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final StrLookup<String> SYSTEM_PROPERTIES_LOOKUP;
/*     */ 
/*     */ 
/*     */   
/*     */   public static StrLookup<?> noneLookup() {
/*  68 */     return NONE_LOOKUP;
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
/*     */   public static StrLookup<String> systemPropertiesLookup() {
/*  83 */     return SYSTEM_PROPERTIES_LOOKUP;
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
/*     */   public static <V> StrLookup<V> mapLookup(Map<String, V> map) {
/*  97 */     return new MapStrLookup<V>(map);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String lookup(String paramString);
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
/*     */   static class MapStrLookup<V>
/*     */     extends StrLookup<V>
/*     */   {
/*     */     private final Map<String, V> map;
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
/*     */     MapStrLookup(Map<String, V> map) {
/* 148 */       this.map = map;
/*     */     }
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
/*     */     public String lookup(String key) {
/* 162 */       if (this.map == null) {
/* 163 */         return null;
/*     */       }
/* 165 */       Object obj = this.map.get(key);
/* 166 */       if (obj == null) {
/* 167 */         return null;
/*     */       }
/* 169 */       return obj.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\text\StrLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */