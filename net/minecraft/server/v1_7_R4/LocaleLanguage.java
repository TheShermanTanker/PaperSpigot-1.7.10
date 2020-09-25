/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.util.com.google.common.base.Splitter;
/*     */ import net.minecraft.util.com.google.common.collect.Iterables;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ import net.minecraft.util.org.apache.commons.io.Charsets;
/*     */ import net.minecraft.util.org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class LocaleLanguage
/*     */ {
/*  16 */   private static final Pattern a = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*  17 */   private static final Splitter b = Splitter.on('=').limit(2);
/*     */   
/*  19 */   private static LocaleLanguage c = new LocaleLanguage();
/*     */   
/*  21 */   private final Map d = Maps.newHashMap();
/*     */   private long e;
/*     */   
/*     */   public LocaleLanguage() {
/*     */     try {
/*  26 */       InputStream inputStream = LocaleLanguage.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");
/*  27 */       for (String str1 : IOUtils.readLines(inputStream, Charsets.UTF_8)) {
/*     */         
/*  29 */         if (str1.isEmpty() || str1.charAt(0) == '#')
/*     */           continue; 
/*  31 */         String[] arrayOfString = (String[])Iterables.toArray(b.split(str1), String.class);
/*     */ 
/*     */         
/*  34 */         if (arrayOfString == null || arrayOfString.length != 2) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/*  39 */         String str2 = arrayOfString[0];
/*  40 */         String str3 = a.matcher(arrayOfString[1]).replaceAll("%$1s");
/*     */         
/*  42 */         this.d.put(str2, str3);
/*     */       } 
/*  44 */       this.e = System.currentTimeMillis();
/*  45 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static LocaleLanguage a() {
/*  52 */     return c;
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
/*     */   public synchronized String a(String paramString) {
/*  88 */     return c(paramString);
/*     */   }
/*     */   
/*     */   public synchronized String a(String paramString, Object... paramVarArgs) {
/*  92 */     String str = c(paramString);
/*     */     try {
/*  94 */       return String.format(str, paramVarArgs);
/*  95 */     } catch (IllegalFormatException illegalFormatException) {
/*  96 */       return "Format error: " + str;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String c(String paramString) {
/* 101 */     String str = (String)this.d.get(paramString);
/* 102 */     return (str == null) ? paramString : str;
/*     */   }
/*     */   
/*     */   public synchronized boolean b(String paramString) {
/* 106 */     return this.d.containsKey(paramString);
/*     */   }
/*     */   
/*     */   public long c() {
/* 110 */     return this.e;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\LocaleLanguage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */