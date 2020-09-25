/*     */ package com.avaje.ebean.bean;
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
/*     */ public class SerializeControl
/*     */ {
/*     */   private static final String BEANS = "com.avaje.ebean.vanillabeans";
/*     */   private static final String COLLECTIONS = "com.avaje.ebean.vanillacollections";
/*     */   
/*     */   private static Boolean getDefault(String key, Boolean dflt) {
/*  58 */     String val = System.getProperty(key);
/*  59 */     if (val != null) {
/*  60 */       return Boolean.valueOf(val.equalsIgnoreCase("true"));
/*     */     }
/*  62 */     return dflt;
/*     */   }
/*     */   
/*  65 */   private static ThreadLocal<Boolean> vanillaBeans = new ThreadLocal<Boolean>() {
/*     */       protected synchronized Boolean initialValue() {
/*  67 */         return SerializeControl.getDefault("com.avaje.ebean.vanillabeans", Boolean.TRUE);
/*     */       }
/*     */     };
/*     */   
/*  71 */   private static ThreadLocal<Boolean> vanillaCollections = new ThreadLocal<Boolean>() {
/*     */       protected synchronized Boolean initialValue() {
/*  73 */         return SerializeControl.getDefault("com.avaje.ebean.vanillacollections", Boolean.TRUE);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultForBeans(boolean vanillaOn) {
/*  81 */     Boolean b = Boolean.valueOf(vanillaOn);
/*  82 */     System.setProperty("com.avaje.ebean.vanillabeans", b.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultForCollections(boolean vanillaOn) {
/*  89 */     Boolean b = Boolean.valueOf(vanillaOn);
/*  90 */     System.setProperty("com.avaje.ebean.vanillacollections", b.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resetToDefault() {
/*  98 */     Boolean beans = getDefault("com.avaje.ebean.vanillabeans", Boolean.FALSE);
/*  99 */     setVanillaBeans(beans.booleanValue());
/*     */     
/* 101 */     Boolean coll = getDefault("com.avaje.ebean.vanillacollections", Boolean.FALSE);
/* 102 */     setVanillaCollections(coll.booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setVanilla(boolean vanillaOn) {
/* 109 */     if (vanillaOn) {
/* 110 */       vanillaBeans.set(Boolean.TRUE);
/* 111 */       vanillaCollections.set(Boolean.TRUE);
/*     */     } else {
/* 113 */       vanillaBeans.set(Boolean.FALSE);
/* 114 */       vanillaCollections.set(Boolean.FALSE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVanillaBeans() {
/* 123 */     return ((Boolean)vanillaBeans.get()).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setVanillaBeans(boolean vanillaOn) {
/* 131 */     vanillaBeans.set(Boolean.valueOf(vanillaOn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVanillaCollections() {
/* 139 */     return ((Boolean)vanillaCollections.get()).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setVanillaCollections(boolean vanillaOn) {
/* 147 */     vanillaCollections.set(Boolean.valueOf(vanillaOn));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\bean\SerializeControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */