/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Stack;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class MojangsonParser
/*     */ {
/*   9 */   private static final Logger a = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTBase parse(String paramString) {
/*  16 */     paramString = paramString.trim();
/*  17 */     int i = b(paramString);
/*  18 */     if (i != 1) {
/*  19 */       throw new MojangsonParseException("Encountered multiple top tags, only one expected");
/*     */     }
/*     */     
/*  22 */     MojangsonTypeParser mojangsonTypeParser = null;
/*  23 */     if (paramString.startsWith("{")) {
/*  24 */       mojangsonTypeParser = a("tag", paramString);
/*     */     } else {
/*  26 */       mojangsonTypeParser = a(b(paramString, false), c(paramString, false));
/*     */     } 
/*     */     
/*  29 */     return mojangsonTypeParser.a();
/*     */   }
/*     */ 
/*     */   
/*     */   static int b(String paramString) {
/*  34 */     byte b1 = 0;
/*  35 */     boolean bool = false;
/*  36 */     Stack<Character> stack = new Stack();
/*     */     
/*  38 */     byte b2 = 0;
/*  39 */     while (b2 < paramString.length()) {
/*  40 */       char c = paramString.charAt(b2);
/*  41 */       if (c == '"') {
/*  42 */         if (b2 > 0 && paramString.charAt(b2 - 1) == '\\') {
/*  43 */           if (!bool) {
/*  44 */             throw new MojangsonParseException("Illegal use of \\\": " + paramString);
/*     */           }
/*     */         } else {
/*  47 */           bool = !bool ? true : false;
/*     */         } 
/*  49 */       } else if (!bool) {
/*  50 */         if (c == '{' || c == '[')
/*  51 */         { if (stack.isEmpty()) {
/*  52 */             b1++;
/*     */           }
/*  54 */           stack.push(Character.valueOf(c)); }
/*  55 */         else { if (c == '}' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '{'))
/*  56 */             throw new MojangsonParseException("Unbalanced curly brackets {}: " + paramString); 
/*  57 */           if (c == ']' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '['))
/*  58 */             throw new MojangsonParseException("Unbalanced square brackets []: " + paramString);  }
/*     */       
/*     */       } 
/*  61 */       b2++;
/*     */     } 
/*  63 */     if (bool) {
/*  64 */       throw new MojangsonParseException("Unbalanced quotation: " + paramString);
/*     */     }
/*  66 */     if (!stack.isEmpty()) {
/*  67 */       throw new MojangsonParseException("Unbalanced brackets: " + paramString);
/*     */     }
/*     */     
/*  70 */     if (b1 == 0 && !paramString.isEmpty()) {
/*  71 */       return 1;
/*     */     }
/*  73 */     return b1;
/*     */   }
/*     */   
/*     */   static MojangsonTypeParser a(String paramString1, String paramString2) {
/*  77 */     paramString2 = paramString2.trim();
/*  78 */     b(paramString2);
/*     */     
/*  80 */     if (paramString2.startsWith("{")) {
/*  81 */       if (!paramString2.endsWith("}")) {
/*  82 */         throw new MojangsonParseException("Unable to locate ending bracket for: " + paramString2);
/*     */       }
/*     */       
/*  85 */       paramString2 = paramString2.substring(1, paramString2.length() - 1);
/*     */       
/*  87 */       MojangsonCompoundParser mojangsonCompoundParser = new MojangsonCompoundParser(paramString1);
/*  88 */       while (paramString2.length() > 0) {
/*  89 */         String str = a(paramString2, false);
/*  90 */         if (str.length() > 0) {
/*  91 */           String str1 = b(str, false);
/*  92 */           String str2 = c(str, false);
/*  93 */           mojangsonCompoundParser.b.add(a(str1, str2));
/*     */           
/*  95 */           if (paramString2.length() >= str.length() + 1) {
/*  96 */             char c = paramString2.charAt(str.length());
/*  97 */             if (c != ',' && c != '{' && c != '}' && c != '[' && c != ']') {
/*  98 */               throw new MojangsonParseException("Unexpected token '" + c + "' at: " + paramString2.substring(str.length()));
/*     */             }
/* 100 */             paramString2 = paramString2.substring(str.length() + 1);
/*     */             
/*     */             continue;
/*     */           } 
/*     */           break;
/*     */         } 
/*     */       } 
/* 107 */       return mojangsonCompoundParser;
/* 108 */     }  if (paramString2.startsWith("[") && !paramString2.matches("\\[[-\\d|,\\s]+\\]")) {
/* 109 */       if (!paramString2.endsWith("]")) {
/* 110 */         throw new MojangsonParseException("Unable to locate ending bracket for: " + paramString2);
/*     */       }
/*     */       
/* 113 */       paramString2 = paramString2.substring(1, paramString2.length() - 1);
/*     */       
/* 115 */       MojangsonListParser mojangsonListParser = new MojangsonListParser(paramString1);
/* 116 */       while (paramString2.length() > 0) {
/* 117 */         String str = a(paramString2, true);
/* 118 */         if (str.length() > 0) {
/* 119 */           String str1 = b(str, true);
/* 120 */           String str2 = c(str, true);
/* 121 */           mojangsonListParser.b.add(a(str1, str2));
/*     */           
/* 123 */           if (paramString2.length() >= str.length() + 1) {
/* 124 */             char c = paramString2.charAt(str.length());
/* 125 */             if (c != ',' && c != '{' && c != '}' && c != '[' && c != ']') {
/* 126 */               throw new MojangsonParseException("Unexpected token '" + c + "' at: " + paramString2.substring(str.length()));
/*     */             }
/* 128 */             paramString2 = paramString2.substring(str.length() + 1);
/*     */             continue;
/*     */           } 
/*     */           break;
/*     */         } 
/* 133 */         a.debug(paramString2);
/*     */       } 
/*     */ 
/*     */       
/* 137 */       return mojangsonListParser;
/*     */     } 
/* 139 */     return new MojangsonPrimitiveParser(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String a(String paramString, boolean paramBoolean) {
/* 144 */     int i = a(paramString, ':');
/* 145 */     if (i < 0 && !paramBoolean) {
/* 146 */       throw new MojangsonParseException("Unable to locate name/value separator for string: " + paramString);
/*     */     }
/* 148 */     int j = a(paramString, ',');
/* 149 */     if (j >= 0 && j < i && !paramBoolean) {
/* 150 */       throw new MojangsonParseException("Name error at: " + paramString);
/*     */     }
/* 152 */     if (paramBoolean && (i < 0 || i > j)) {
/* 153 */       i = -1;
/*     */     }
/*     */     
/* 156 */     Stack<Character> stack = new Stack();
/* 157 */     int k = i + 1;
/* 158 */     boolean bool1 = false;
/* 159 */     boolean bool2 = false;
/* 160 */     boolean bool3 = false;
/* 161 */     int m = 0;
/*     */     
/* 163 */     while (k < paramString.length()) {
/* 164 */       char c = paramString.charAt(k);
/*     */       
/* 166 */       if (c == '"') {
/* 167 */         if (k > 0 && paramString.charAt(k - 1) == '\\') {
/* 168 */           if (!bool1) {
/* 169 */             throw new MojangsonParseException("Illegal use of \\\": " + paramString);
/*     */           }
/*     */         } else {
/* 172 */           bool1 = !bool1 ? true : false;
/* 173 */           if (bool1 && !bool3) {
/* 174 */             bool2 = true;
/*     */           }
/* 176 */           if (!bool1) {
/* 177 */             m = k;
/*     */           }
/*     */         } 
/* 180 */       } else if (!bool1) {
/* 181 */         if (c == '{' || c == '[')
/* 182 */         { stack.push(Character.valueOf(c)); }
/* 183 */         else { if (c == '}' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '{'))
/* 184 */             throw new MojangsonParseException("Unbalanced curly brackets {}: " + paramString); 
/* 185 */           if (c == ']' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '['))
/* 186 */             throw new MojangsonParseException("Unbalanced square brackets []: " + paramString); 
/* 187 */           if (c == ',' && 
/* 188 */             stack.isEmpty()) {
/* 189 */             return paramString.substring(0, k);
/*     */           } }
/*     */       
/*     */       } 
/* 193 */       if (!Character.isWhitespace(c)) {
/* 194 */         if (!bool1 && bool2 && m != k) {
/* 195 */           return paramString.substring(0, m + 1);
/*     */         }
/* 197 */         bool3 = true;
/*     */       } 
/*     */       
/* 200 */       k++;
/*     */     } 
/*     */     
/* 203 */     return paramString.substring(0, k);
/*     */   }
/*     */   
/*     */   private static String b(String paramString, boolean paramBoolean) {
/* 207 */     if (paramBoolean) {
/* 208 */       paramString = paramString.trim();
/* 209 */       if (paramString.startsWith("{") || paramString.startsWith("[")) {
/* 210 */         return "";
/*     */       }
/*     */     } 
/*     */     
/* 214 */     int i = paramString.indexOf(':');
/* 215 */     if (i < 0) {
/* 216 */       if (paramBoolean) {
/* 217 */         return "";
/*     */       }
/* 219 */       throw new MojangsonParseException("Unable to locate name/value separator for string: " + paramString);
/*     */     } 
/* 221 */     return paramString.substring(0, i).trim();
/*     */   }
/*     */   
/*     */   private static String c(String paramString, boolean paramBoolean) {
/* 225 */     if (paramBoolean) {
/* 226 */       paramString = paramString.trim();
/* 227 */       if (paramString.startsWith("{") || paramString.startsWith("[")) {
/* 228 */         return paramString;
/*     */       }
/*     */     } 
/*     */     
/* 232 */     int i = paramString.indexOf(':');
/* 233 */     if (i < 0) {
/* 234 */       if (paramBoolean) {
/* 235 */         return paramString;
/*     */       }
/* 237 */       throw new MojangsonParseException("Unable to locate name/value separator for string: " + paramString);
/*     */     } 
/* 239 */     return paramString.substring(i + 1).trim();
/*     */   }
/*     */   
/*     */   private static int a(String paramString, char paramChar) {
/* 243 */     byte b = 0;
/* 244 */     boolean bool = false;
/* 245 */     while (b < paramString.length()) {
/* 246 */       char c = paramString.charAt(b);
/* 247 */       if (c == '"') {
/* 248 */         if (b <= 0 || paramString.charAt(b - 1) != '\\')
/*     */         {
/* 250 */           bool = !bool ? true : false;
/*     */         }
/* 252 */       } else if (!bool) {
/* 253 */         if (c == paramChar) {
/* 254 */           return b;
/*     */         }
/* 256 */         if (c == '{' || c == '[') {
/* 257 */           return -1;
/*     */         }
/*     */       } 
/* 260 */       b++;
/*     */     } 
/* 262 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MojangsonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */