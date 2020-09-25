/*     */ package org.bukkit.craftbukkit.libs.jline.console;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConsoleKeys
/*     */ {
/*     */   private KeyMap keys;
/*     */   private boolean viEditMode;
/*     */   private Map<String, KeyMap> keyMaps;
/*     */   
/*     */   public ConsoleKeys(String appName, URL inputrcUrl) {
/*  41 */     this.keyMaps = KeyMap.keyMaps();
/*     */     
/*  43 */     loadKeys(appName, inputrcUrl);
/*     */   }
/*     */   
/*     */   protected Map<String, KeyMap> getKeyMaps() {
/*  47 */     return this.keyMaps;
/*     */   }
/*     */   
/*     */   protected KeyMap getKeys() {
/*  51 */     return this.keys;
/*     */   }
/*     */   
/*     */   protected void setKeys(KeyMap keys) {
/*  55 */     this.keys = keys;
/*     */   }
/*     */   
/*     */   protected boolean getViEditMode() {
/*  59 */     return this.viEditMode;
/*     */   }
/*     */   
/*     */   protected void setViEditMode(boolean viEditMode) {
/*  63 */     this.viEditMode = viEditMode;
/*     */   }
/*     */   
/*     */   protected void loadKeys(String appName, URL inputrcUrl) {
/*  67 */     this.keys = this.keyMaps.get("emacs");
/*     */     try {
/*  69 */       InputStream input = inputrcUrl.openStream();
/*     */       try {
/*  71 */         loadKeys(input, appName);
/*  72 */         Log.debug(new Object[] { "Loaded user configuration: ", inputrcUrl });
/*     */       } finally {
/*     */         
/*     */         try {
/*  76 */           input.close();
/*  77 */         } catch (IOException e) {}
/*     */       
/*     */       }
/*     */     
/*     */     }
/*  82 */     catch (IOException e) {
/*  83 */       if (inputrcUrl.getProtocol().equals("file")) {
/*  84 */         File file = new File(inputrcUrl.getPath());
/*  85 */         if (file.exists()) {
/*  86 */           Log.warn(new Object[] { "Unable to read user configuration: ", inputrcUrl, e });
/*     */         }
/*     */       } else {
/*  89 */         Log.warn(new Object[] { "Unable to read user configuration: ", inputrcUrl, e });
/*     */       } 
/*     */     } 
/*  92 */     this.keys.bindArrowKeys();
/*  93 */     this.keys = this.viEditMode ? this.keyMaps.get("vi") : this.keyMaps.get("emacs");
/*     */   }
/*     */   
/*     */   private void loadKeys(InputStream input, String appName) throws IOException {
/*  97 */     BufferedReader reader = new BufferedReader(new InputStreamReader(input));
/*     */     
/*  99 */     boolean parsing = true;
/* 100 */     List<Boolean> ifsStack = new ArrayList<Boolean>(); String line;
/* 101 */     while ((line = reader.readLine()) != null) {
/* 102 */       line = line.trim();
/* 103 */       if (line.length() == 0) {
/*     */         continue;
/*     */       }
/* 106 */       if (line.charAt(0) == '#') {
/*     */         continue;
/*     */       }
/* 109 */       int i = 0;
/* 110 */       if (line.charAt(i) == '$') {
/*     */ 
/*     */         
/* 113 */         for (; ++i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t'); i++);
/* 114 */         int s = i;
/* 115 */         for (; i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t'; i++);
/* 116 */         String cmd = line.substring(s, i);
/* 117 */         for (; i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t'); i++);
/* 118 */         s = i;
/* 119 */         for (; i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t'; i++);
/* 120 */         String args = line.substring(s, i);
/* 121 */         if ("if".equalsIgnoreCase(cmd)) {
/* 122 */           ifsStack.add(Boolean.valueOf(parsing));
/* 123 */           if (!parsing) {
/*     */             continue;
/*     */           }
/* 126 */           if (args.startsWith("term="))
/*     */             continue; 
/* 128 */           if (args.startsWith("mode=")) {
/* 129 */             if (args.equalsIgnoreCase("mode=vi")) {
/* 130 */               parsing = this.viEditMode; continue;
/* 131 */             }  if (args.equals("mode=emacs")) {
/* 132 */               parsing = !this.viEditMode; continue;
/*     */             } 
/* 134 */             parsing = false;
/*     */             continue;
/*     */           } 
/* 137 */           parsing = args.equalsIgnoreCase(appName); continue;
/*     */         } 
/* 139 */         if ("else".equalsIgnoreCase(cmd)) {
/* 140 */           if (ifsStack.isEmpty()) {
/* 141 */             throw new IllegalArgumentException("$else found without matching $if");
/*     */           }
/* 143 */           boolean invert = true;
/* 144 */           for (Iterator<Boolean> i$ = ifsStack.iterator(); i$.hasNext(); ) { boolean b = ((Boolean)i$.next()).booleanValue();
/* 145 */             if (!b) {
/* 146 */               invert = false;
/*     */               break;
/*     */             }  }
/*     */           
/* 150 */           if (invert)
/* 151 */             parsing = !parsing;  continue;
/*     */         } 
/* 153 */         if ("endif".equalsIgnoreCase(cmd)) {
/* 154 */           if (ifsStack.isEmpty()) {
/* 155 */             throw new IllegalArgumentException("endif found without matching $if");
/*     */           }
/* 157 */           parsing = ((Boolean)ifsStack.remove(ifsStack.size() - 1)).booleanValue(); continue;
/* 158 */         }  if ("include".equalsIgnoreCase(cmd));
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 163 */       if (!parsing) {
/*     */         continue;
/*     */       }
/*     */       
/* 167 */       String keySeq = "";
/* 168 */       if (line.charAt(i++) == '"') {
/* 169 */         boolean esc = false;
/* 170 */         for (;; i++) {
/* 171 */           if (i >= line.length()) {
/* 172 */             throw new IllegalArgumentException("Missing closing quote on line '" + line + "'");
/*     */           }
/* 174 */           if (esc) {
/* 175 */             esc = false;
/* 176 */           } else if (line.charAt(i) == '\\') {
/* 177 */             esc = true;
/* 178 */           } else if (line.charAt(i) == '"') {
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 184 */       while (i < line.length() && line.charAt(i) != ':' && line.charAt(i) != ' ' && line.charAt(i) != '\t')
/* 185 */         i++; 
/* 186 */       keySeq = line.substring(0, i);
/* 187 */       boolean equivalency = (i + 1 < line.length() && line.charAt(i) == ':' && line.charAt(i + 1) == '=');
/* 188 */       i++;
/* 189 */       if (equivalency) {
/* 190 */         i++;
/*     */       }
/* 192 */       if (keySeq.equalsIgnoreCase("set")) {
/*     */ 
/*     */         
/* 195 */         for (; i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t'); i++);
/* 196 */         int s = i;
/* 197 */         for (; i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t'; i++);
/* 198 */         String key = line.substring(s, i);
/* 199 */         for (; i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t'); i++);
/* 200 */         s = i;
/* 201 */         for (; i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t'; i++);
/* 202 */         String str1 = line.substring(s, i);
/* 203 */         setVar(key, str1); continue;
/*     */       } 
/* 205 */       for (; i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t'); i++);
/* 206 */       int start = i;
/* 207 */       if (i < line.length() && (line.charAt(i) == '\'' || line.charAt(i) == '"')) {
/* 208 */         char delim = line.charAt(i++);
/* 209 */         boolean esc = false;
/*     */         
/* 211 */         for (; i < line.length(); i++) {
/*     */ 
/*     */           
/* 214 */           if (esc) {
/* 215 */             esc = false;
/* 216 */           } else if (line.charAt(i) == '\\') {
/* 217 */             esc = true;
/* 218 */           } else if (line.charAt(i) == delim) {
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 223 */       for (; i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t'; i++);
/* 224 */       String val = line.substring(Math.min(start, line.length()), Math.min(i, line.length()));
/* 225 */       if (keySeq.charAt(0) == '"') {
/* 226 */         keySeq = translateQuoted(keySeq);
/*     */       } else {
/*     */         
/* 229 */         String keyName = (keySeq.lastIndexOf('-') > 0) ? keySeq.substring(keySeq.lastIndexOf('-') + 1) : keySeq;
/* 230 */         char key = getKeyFromName(keyName);
/* 231 */         keyName = keySeq.toLowerCase();
/* 232 */         keySeq = "";
/* 233 */         if (keyName.contains("meta-") || keyName.contains("m-")) {
/* 234 */           keySeq = keySeq + "\033";
/*     */         }
/* 236 */         if (keyName.contains("control-") || keyName.contains("c-") || keyName.contains("ctrl-")) {
/* 237 */           key = (char)(Character.toUpperCase(key) & 0x1F);
/*     */         }
/* 239 */         keySeq = keySeq + key;
/*     */       } 
/* 241 */       if (val.length() > 0 && (val.charAt(0) == '\'' || val.charAt(0) == '"')) {
/* 242 */         this.keys.bind(keySeq, translateQuoted(val)); continue;
/*     */       } 
/* 244 */       val = val.replace('-', '_').toUpperCase();
/* 245 */       this.keys.bind(keySeq, Operation.valueOf(val));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String translateQuoted(String keySeq) {
/* 254 */     String str = keySeq.substring(1, keySeq.length() - 1);
/* 255 */     keySeq = "";
/* 256 */     for (int i = 0; i < str.length(); i++) {
/* 257 */       char c = str.charAt(i);
/* 258 */       if (c == '\\') {
/* 259 */         boolean ctrl = (str.regionMatches(i, "\\C-", 0, 3) || str.regionMatches(i, "\\M-\\C-", 0, 6));
/* 260 */         boolean meta = (str.regionMatches(i, "\\M-", 0, 3) || str.regionMatches(i, "\\C-\\M-", 0, 6));
/* 261 */         i += (meta ? 3 : 0) + (ctrl ? 3 : 0) + ((!meta && !ctrl) ? 1 : 0);
/* 262 */         if (i >= str.length()) {
/*     */           break;
/*     */         }
/* 265 */         c = str.charAt(i);
/* 266 */         if (meta) {
/* 267 */           keySeq = keySeq + "\033";
/*     */         }
/* 269 */         if (ctrl) {
/* 270 */           c = (c == '?') ? '' : (char)(Character.toUpperCase(c) & 0x1F);
/*     */         }
/* 272 */         if (!meta && !ctrl) {
/* 273 */           int j; switch (c) { case 'a':
/* 274 */               c = '\007'; break;
/* 275 */             case 'b': c = '\b'; break;
/* 276 */             case 'd': c = ''; break;
/* 277 */             case 'e': c = '\033'; break;
/* 278 */             case 'f': c = '\f'; break;
/* 279 */             case 'n': c = '\n'; break;
/* 280 */             case 'r': c = '\r'; break;
/* 281 */             case 't': c = '\t'; break;
/* 282 */             case 'v': c = '\013'; break;
/* 283 */             case '\\': c = '\\'; break;
/*     */             case '0': case '1': case '2': case '3': case '4': case '5': case '6':
/*     */             case '7':
/* 286 */               c = Character.MIN_VALUE;
/* 287 */               for (j = 0; j < 3 && 
/* 288 */                 i < str.length(); j++, i++) {
/*     */ 
/*     */                 
/* 291 */                 int k = Character.digit(str.charAt(i), 8);
/* 292 */                 if (k < 0) {
/*     */                   break;
/*     */                 }
/* 295 */                 c = (char)(c * 8 + k);
/*     */               } 
/* 297 */               c = (char)(c & 0xFF);
/*     */               break;
/*     */             case 'x':
/* 300 */               i++;
/* 301 */               c = Character.MIN_VALUE;
/* 302 */               for (j = 0; j < 2 && 
/* 303 */                 i < str.length(); j++, i++) {
/*     */ 
/*     */                 
/* 306 */                 int k = Character.digit(str.charAt(i), 16);
/* 307 */                 if (k < 0) {
/*     */                   break;
/*     */                 }
/* 310 */                 c = (char)(c * 16 + k);
/*     */               } 
/* 312 */               c = (char)(c & 0xFF);
/*     */               break;
/*     */             case 'u':
/* 315 */               i++;
/* 316 */               c = Character.MIN_VALUE;
/* 317 */               for (j = 0; j < 4 && 
/* 318 */                 i < str.length(); j++, i++) {
/*     */ 
/*     */                 
/* 321 */                 int k = Character.digit(str.charAt(i), 16);
/* 322 */                 if (k < 0) {
/*     */                   break;
/*     */                 }
/* 325 */                 c = (char)(c * 16 + k);
/*     */               } 
/*     */               break; }
/*     */         
/*     */         } 
/* 330 */         keySeq = keySeq + c;
/*     */       } else {
/* 332 */         keySeq = keySeq + c;
/*     */       } 
/*     */     } 
/* 335 */     return keySeq;
/*     */   }
/*     */   
/*     */   private char getKeyFromName(String name) {
/* 339 */     if ("DEL".equalsIgnoreCase(name) || "Rubout".equalsIgnoreCase(name))
/* 340 */       return ''; 
/* 341 */     if ("ESC".equalsIgnoreCase(name) || "Escape".equalsIgnoreCase(name))
/* 342 */       return '\033'; 
/* 343 */     if ("LFD".equalsIgnoreCase(name) || "NewLine".equalsIgnoreCase(name))
/* 344 */       return '\n'; 
/* 345 */     if ("RET".equalsIgnoreCase(name) || "Return".equalsIgnoreCase(name))
/* 346 */       return '\r'; 
/* 347 */     if ("SPC".equalsIgnoreCase(name) || "Space".equalsIgnoreCase(name))
/* 348 */       return ' '; 
/* 349 */     if ("Tab".equalsIgnoreCase(name)) {
/* 350 */       return '\t';
/*     */     }
/* 352 */     return name.charAt(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setVar(String key, String val) {
/* 357 */     if ("keymap".equalsIgnoreCase(key)) {
/* 358 */       if (this.keyMaps.containsKey(val)) {
/* 359 */         this.keys = this.keyMaps.get(val);
/*     */       }
/* 361 */     } else if ("editing-mode".equals(key)) {
/* 362 */       if ("vi".equalsIgnoreCase(val)) {
/* 363 */         this.keys = this.keyMaps.get("vi-insert");
/* 364 */         this.viEditMode = true;
/* 365 */       } else if ("emacs".equalsIgnoreCase(key)) {
/* 366 */         this.keys = this.keyMaps.get("emacs");
/* 367 */         this.viEditMode = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\ConsoleKeys.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */