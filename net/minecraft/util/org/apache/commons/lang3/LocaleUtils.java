/*     */ package net.minecraft.util.org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocaleUtils
/*     */ {
/*  42 */   private static final ConcurrentMap<String, List<Locale>> cLanguagesByCountry = new ConcurrentHashMap<String, List<Locale>>();
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static final ConcurrentMap<String, List<Locale>> cCountriesByLanguage = new ConcurrentHashMap<String, List<Locale>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale toLocale(String str) {
/*  91 */     if (str == null) {
/*  92 */       return null;
/*     */     }
/*  94 */     if (str.isEmpty()) {
/*  95 */       return new Locale("", "");
/*     */     }
/*  97 */     if (str.contains("#")) {
/*  98 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 100 */     int len = str.length();
/* 101 */     if (len < 2) {
/* 102 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 104 */     char ch0 = str.charAt(0);
/* 105 */     if (ch0 == '_') {
/* 106 */       if (len < 3) {
/* 107 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 109 */       char c1 = str.charAt(1);
/* 110 */       char ch2 = str.charAt(2);
/* 111 */       if (!Character.isUpperCase(c1) || !Character.isUpperCase(ch2)) {
/* 112 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 114 */       if (len == 3) {
/* 115 */         return new Locale("", str.substring(1, 3));
/*     */       }
/* 117 */       if (len < 5) {
/* 118 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 120 */       if (str.charAt(3) != '_') {
/* 121 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 123 */       return new Locale("", str.substring(1, 3), str.substring(4));
/*     */     } 
/* 125 */     char ch1 = str.charAt(1);
/* 126 */     if (!Character.isLowerCase(ch0) || !Character.isLowerCase(ch1)) {
/* 127 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 129 */     if (len == 2) {
/* 130 */       return new Locale(str);
/*     */     }
/* 132 */     if (len < 5) {
/* 133 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 135 */     if (str.charAt(2) != '_') {
/* 136 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 138 */     char ch3 = str.charAt(3);
/* 139 */     if (ch3 == '_') {
/* 140 */       return new Locale(str.substring(0, 2), "", str.substring(4));
/*     */     }
/* 142 */     char ch4 = str.charAt(4);
/* 143 */     if (!Character.isUpperCase(ch3) || !Character.isUpperCase(ch4)) {
/* 144 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 146 */     if (len == 5) {
/* 147 */       return new Locale(str.substring(0, 2), str.substring(3, 5));
/*     */     }
/* 149 */     if (len < 7) {
/* 150 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 152 */     if (str.charAt(5) != '_') {
/* 153 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 155 */     return new Locale(str.substring(0, 2), str.substring(3, 5), str.substring(6));
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
/*     */   public static List<Locale> localeLookupList(Locale locale) {
/* 172 */     return localeLookupList(locale, locale);
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
/*     */   public static List<Locale> localeLookupList(Locale locale, Locale defaultLocale) {
/* 194 */     List<Locale> list = new ArrayList<Locale>(4);
/* 195 */     if (locale != null) {
/* 196 */       list.add(locale);
/* 197 */       if (locale.getVariant().length() > 0) {
/* 198 */         list.add(new Locale(locale.getLanguage(), locale.getCountry()));
/*     */       }
/* 200 */       if (locale.getCountry().length() > 0) {
/* 201 */         list.add(new Locale(locale.getLanguage(), ""));
/*     */       }
/* 203 */       if (!list.contains(defaultLocale)) {
/* 204 */         list.add(defaultLocale);
/*     */       }
/*     */     } 
/* 207 */     return Collections.unmodifiableList(list);
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
/*     */   public static List<Locale> availableLocaleList() {
/* 221 */     return SyncAvoid.AVAILABLE_LOCALE_LIST;
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
/*     */   public static Set<Locale> availableLocaleSet() {
/* 235 */     return SyncAvoid.AVAILABLE_LOCALE_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAvailableLocale(Locale locale) {
/* 246 */     return availableLocaleList().contains(locale);
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
/*     */   public static List<Locale> languagesByCountry(String countryCode) {
/* 260 */     if (countryCode == null) {
/* 261 */       return Collections.emptyList();
/*     */     }
/* 263 */     List<Locale> langs = cLanguagesByCountry.get(countryCode);
/* 264 */     if (langs == null) {
/* 265 */       langs = new ArrayList<Locale>();
/* 266 */       List<Locale> locales = availableLocaleList();
/* 267 */       for (int i = 0; i < locales.size(); i++) {
/* 268 */         Locale locale = locales.get(i);
/* 269 */         if (countryCode.equals(locale.getCountry()) && locale.getVariant().isEmpty())
/*     */         {
/* 271 */           langs.add(locale);
/*     */         }
/*     */       } 
/* 274 */       langs = Collections.unmodifiableList(langs);
/* 275 */       cLanguagesByCountry.putIfAbsent(countryCode, langs);
/* 276 */       langs = cLanguagesByCountry.get(countryCode);
/*     */     } 
/* 278 */     return langs;
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
/*     */   public static List<Locale> countriesByLanguage(String languageCode) {
/* 292 */     if (languageCode == null) {
/* 293 */       return Collections.emptyList();
/*     */     }
/* 295 */     List<Locale> countries = cCountriesByLanguage.get(languageCode);
/* 296 */     if (countries == null) {
/* 297 */       countries = new ArrayList<Locale>();
/* 298 */       List<Locale> locales = availableLocaleList();
/* 299 */       for (int i = 0; i < locales.size(); i++) {
/* 300 */         Locale locale = locales.get(i);
/* 301 */         if (languageCode.equals(locale.getLanguage()) && locale.getCountry().length() != 0 && locale.getVariant().isEmpty())
/*     */         {
/*     */           
/* 304 */           countries.add(locale);
/*     */         }
/*     */       } 
/* 307 */       countries = Collections.unmodifiableList(countries);
/* 308 */       cCountriesByLanguage.putIfAbsent(languageCode, countries);
/* 309 */       countries = cCountriesByLanguage.get(languageCode);
/*     */     } 
/* 311 */     return countries;
/*     */   }
/*     */ 
/*     */   
/*     */   static class SyncAvoid
/*     */   {
/*     */     private static final List<Locale> AVAILABLE_LOCALE_LIST;
/*     */     
/*     */     private static final Set<Locale> AVAILABLE_LOCALE_SET;
/*     */ 
/*     */     
/*     */     static {
/* 323 */       List<Locale> list = new ArrayList<Locale>(Arrays.asList(Locale.getAvailableLocales()));
/* 324 */       AVAILABLE_LOCALE_LIST = Collections.unmodifiableList(list);
/* 325 */       AVAILABLE_LOCALE_SET = Collections.unmodifiableSet(new HashSet<Locale>(list));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\LocaleUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */