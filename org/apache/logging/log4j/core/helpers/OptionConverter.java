/*     */ package org.apache.logging.log4j.core.helpers;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OptionConverter
/*     */ {
/*  31 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */ 
/*     */   
/*     */   private static final String DELIM_START = "${";
/*     */   
/*     */   private static final char DELIM_STOP = '}';
/*     */   
/*     */   private static final int DELIM_START_LEN = 2;
/*     */   
/*     */   private static final int DELIM_STOP_LEN = 1;
/*     */   
/*     */   private static final int ONE_K = 1024;
/*     */ 
/*     */   
/*     */   public static String[] concatenateArrays(String[] l, String[] r) {
/*  46 */     int len = l.length + r.length;
/*  47 */     String[] a = new String[len];
/*     */     
/*  49 */     System.arraycopy(l, 0, a, 0, l.length);
/*  50 */     System.arraycopy(r, 0, a, l.length, r.length);
/*     */     
/*  52 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String convertSpecialChars(String s) {
/*  57 */     int len = s.length();
/*  58 */     StringBuilder sbuf = new StringBuilder(len);
/*     */     
/*  60 */     int i = 0;
/*  61 */     while (i < len) {
/*  62 */       char c = s.charAt(i++);
/*  63 */       if (c == '\\') {
/*  64 */         c = s.charAt(i++);
/*  65 */         if (c == 'n') {
/*  66 */           c = '\n';
/*  67 */         } else if (c == 'r') {
/*  68 */           c = '\r';
/*  69 */         } else if (c == 't') {
/*  70 */           c = '\t';
/*  71 */         } else if (c == 'f') {
/*  72 */           c = '\f';
/*  73 */         } else if (c == '\b') {
/*  74 */           c = '\b';
/*  75 */         } else if (c == '"') {
/*  76 */           c = '"';
/*  77 */         } else if (c == '\'') {
/*  78 */           c = '\'';
/*  79 */         } else if (c == '\\') {
/*  80 */           c = '\\';
/*     */         } 
/*     */       } 
/*  83 */       sbuf.append(c);
/*     */     } 
/*  85 */     return sbuf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object instantiateByKey(Properties props, String key, Class<?> superClass, Object defaultValue) {
/*  92 */     String className = findAndSubst(key, props);
/*  93 */     if (className == null) {
/*  94 */       LOGGER.error("Could not find value for key " + key);
/*  95 */       return defaultValue;
/*     */     } 
/*     */     
/*  98 */     return instantiateByClassName(className.trim(), superClass, defaultValue);
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
/*     */   public static boolean toBoolean(String value, boolean defaultValue) {
/* 114 */     if (value == null) {
/* 115 */       return defaultValue;
/*     */     }
/* 117 */     String trimmedVal = value.trim();
/* 118 */     if ("true".equalsIgnoreCase(trimmedVal)) {
/* 119 */       return true;
/*     */     }
/* 121 */     if ("false".equalsIgnoreCase(trimmedVal)) {
/* 122 */       return false;
/*     */     }
/* 124 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int toInt(String value, int defaultValue) {
/* 134 */     if (value != null) {
/* 135 */       String s = value.trim();
/*     */       try {
/* 137 */         return Integer.parseInt(s);
/* 138 */       } catch (NumberFormatException e) {
/* 139 */         LOGGER.error("[" + s + "] is not in proper int form.");
/* 140 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 143 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long toFileSize(String value, long defaultValue) {
/* 153 */     if (value == null) {
/* 154 */       return defaultValue;
/*     */     }
/*     */     
/* 157 */     String str = value.trim().toUpperCase(Locale.ENGLISH);
/* 158 */     long multiplier = 1L;
/*     */     
/*     */     int index;
/* 161 */     if ((index = str.indexOf("KB")) != -1) {
/* 162 */       multiplier = 1024L;
/* 163 */       str = str.substring(0, index);
/* 164 */     } else if ((index = str.indexOf("MB")) != -1) {
/* 165 */       multiplier = 1048576L;
/* 166 */       str = str.substring(0, index);
/* 167 */     } else if ((index = str.indexOf("GB")) != -1) {
/* 168 */       multiplier = 1073741824L;
/* 169 */       str = str.substring(0, index);
/*     */     } 
/* 171 */     if (str != null) {
/*     */       try {
/* 173 */         return Long.parseLong(str) * multiplier;
/* 174 */       } catch (NumberFormatException e) {
/* 175 */         LOGGER.error("[" + str + "] is not in proper int form.");
/* 176 */         LOGGER.error("[" + value + "] not in expected format.", e);
/*     */       } 
/*     */     }
/* 179 */     return defaultValue;
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
/*     */   public static String findAndSubst(String key, Properties props) {
/* 191 */     String value = props.getProperty(key);
/* 192 */     if (value == null) {
/* 193 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 197 */       return substVars(value, props);
/* 198 */     } catch (IllegalArgumentException e) {
/* 199 */       LOGGER.error("Bad option value [" + value + "].", e);
/* 200 */       return value;
/*     */     } 
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
/*     */   public static Object instantiateByClassName(String className, Class<?> superClass, Object defaultValue) {
/* 217 */     if (className != null) {
/*     */       try {
/* 219 */         Class<?> classObj = Loader.loadClass(className);
/* 220 */         if (!superClass.isAssignableFrom(classObj)) {
/* 221 */           LOGGER.error("A \"" + className + "\" object is not assignable to a \"" + superClass.getName() + "\" variable.");
/*     */           
/* 223 */           LOGGER.error("The class \"" + superClass.getName() + "\" was loaded by ");
/* 224 */           LOGGER.error("[" + superClass.getClassLoader() + "] whereas object of type ");
/* 225 */           LOGGER.error("\"" + classObj.getName() + "\" was loaded by [" + classObj.getClassLoader() + "].");
/*     */           
/* 227 */           return defaultValue;
/*     */         } 
/* 229 */         return classObj.newInstance();
/* 230 */       } catch (ClassNotFoundException e) {
/* 231 */         LOGGER.error("Could not instantiate class [" + className + "].", e);
/* 232 */       } catch (IllegalAccessException e) {
/* 233 */         LOGGER.error("Could not instantiate class [" + className + "].", e);
/* 234 */       } catch (InstantiationException e) {
/* 235 */         LOGGER.error("Could not instantiate class [" + className + "].", e);
/* 236 */       } catch (RuntimeException e) {
/* 237 */         LOGGER.error("Could not instantiate class [" + className + "].", e);
/*     */       } 
/*     */     }
/* 240 */     return defaultValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String substVars(String val, Properties props) throws IllegalArgumentException {
/* 283 */     StringBuilder sbuf = new StringBuilder();
/*     */     
/* 285 */     int i = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 290 */       int j = val.indexOf("${", i);
/* 291 */       if (j == -1) {
/*     */         
/* 293 */         if (i == 0) {
/* 294 */           return val;
/*     */         }
/*     */         
/* 297 */         sbuf.append(val.substring(i, val.length()));
/* 298 */         return sbuf.toString();
/*     */       } 
/* 300 */       sbuf.append(val.substring(i, j));
/* 301 */       int k = val.indexOf('}', j);
/* 302 */       if (k == -1) {
/* 303 */         throw new IllegalArgumentException('"' + val + "\" has no closing brace. Opening brace at position " + j + '.');
/*     */       }
/*     */ 
/*     */       
/* 307 */       j += 2;
/* 308 */       String key = val.substring(j, k);
/*     */       
/* 310 */       String replacement = PropertiesUtil.getProperties().getStringProperty(key, null);
/*     */       
/* 312 */       if (replacement == null && props != null) {
/* 313 */         replacement = props.getProperty(key);
/*     */       }
/*     */       
/* 316 */       if (replacement != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 322 */         String recursiveReplacement = substVars(replacement, props);
/* 323 */         sbuf.append(recursiveReplacement);
/*     */       } 
/* 325 */       i = k + 1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\OptionConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */