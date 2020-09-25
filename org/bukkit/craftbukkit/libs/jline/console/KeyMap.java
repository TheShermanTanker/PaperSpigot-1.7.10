/*     */ package org.bukkit.craftbukkit.libs.jline.console;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ public class KeyMap
/*     */ {
/*     */   private static final int KEYMAP_LENGTH = 256;
/*  31 */   private static final Object NULL_FUNCTION = new Object();
/*     */   
/*  33 */   private Object[] mapping = new Object[256];
/*  34 */   private Object anotherKey = null; public static final char CTRL_G = '\007'; public static final char CTRL_H = '\b'; public static final char CTRL_I = '\t'; public static final char CTRL_J = '\n'; public static final char CTRL_M = '\r'; public static final char CTRL_R = '\022';
/*     */   
/*     */   public KeyMap() {
/*  37 */     this(new Object[256]);
/*     */   }
/*     */   public static final char CTRL_U = '\025'; public static final char CTRL_X = '\030'; public static final char CTRL_Y = '\031'; public static final char ESCAPE = '\033'; public static final char CTRL_OB = '\033'; public static final char CTRL_CB = '\035'; public static final int DELETE = 127;
/*     */   protected KeyMap(Object[] mapping) {
/*  41 */     this.mapping = mapping;
/*     */   }
/*     */   
/*     */   public Object getAnotherKey() {
/*  45 */     return this.anotherKey;
/*     */   }
/*     */   
/*     */   public void from(KeyMap other) {
/*  49 */     this.mapping = other.mapping;
/*  50 */     this.anotherKey = other.anotherKey;
/*     */   }
/*     */   
/*     */   public Object getBound(CharSequence keySeq) {
/*  54 */     if (keySeq != null && keySeq.length() > 0) {
/*  55 */       KeyMap map = this;
/*  56 */       for (int i = 0; i < keySeq.length(); i++) {
/*  57 */         char c = keySeq.charAt(i);
/*  58 */         if (c > 'ÿ') {
/*  59 */           return Operation.SELF_INSERT;
/*     */         }
/*  61 */         if (map.mapping[c] instanceof KeyMap) {
/*  62 */           if (i == keySeq.length() - 1) {
/*  63 */             return map.mapping[c];
/*     */           }
/*  65 */           map = (KeyMap)map.mapping[c];
/*     */         } else {
/*     */           
/*  68 */           return map.mapping[c];
/*     */         } 
/*     */       } 
/*     */     } 
/*  72 */     return null;
/*     */   }
/*     */   
/*     */   public void bindIfNotBound(CharSequence keySeq, Object function) {
/*  76 */     if (keySeq != null && keySeq.length() > 0) {
/*  77 */       KeyMap map = this;
/*  78 */       for (int i = 0; i < keySeq.length(); i++) {
/*  79 */         char c = keySeq.charAt(i);
/*  80 */         if (c >= map.mapping.length) {
/*     */           return;
/*     */         }
/*  83 */         if (i < keySeq.length() - 1) {
/*  84 */           if (!(map.mapping[c] instanceof KeyMap)) {
/*  85 */             KeyMap m = new KeyMap();
/*  86 */             if (map.mapping[c] != Operation.DO_LOWERCASE_VERSION) {
/*  87 */               m.anotherKey = map.mapping[c];
/*     */             }
/*  89 */             map.mapping[c] = m;
/*     */           } 
/*  91 */           map = (KeyMap)map.mapping[c];
/*     */         } else {
/*  93 */           if (function == null) {
/*  94 */             function = NULL_FUNCTION;
/*     */           }
/*  96 */           if (map.mapping[c] instanceof KeyMap) {
/*  97 */             map.anotherKey = function;
/*     */           } else {
/*  99 */             Object op = map.mapping[c];
/* 100 */             if (op == null || op == Operation.DO_LOWERCASE_VERSION || op == Operation.VI_MOVEMENT_MODE) {
/* 101 */               map.mapping[c] = function;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void bind(CharSequence keySeq, Object function) {
/* 110 */     if (keySeq != null && keySeq.length() > 0) {
/* 111 */       KeyMap map = this;
/* 112 */       for (int i = 0; i < keySeq.length(); i++) {
/* 113 */         char c = keySeq.charAt(i);
/* 114 */         if (c >= map.mapping.length) {
/*     */           return;
/*     */         }
/* 117 */         if (i < keySeq.length() - 1) {
/* 118 */           if (!(map.mapping[c] instanceof KeyMap)) {
/* 119 */             KeyMap m = new KeyMap();
/* 120 */             if (map.mapping[c] != Operation.DO_LOWERCASE_VERSION) {
/* 121 */               m.anotherKey = map.mapping[c];
/*     */             }
/* 123 */             map.mapping[c] = m;
/*     */           } 
/* 125 */           map = (KeyMap)map.mapping[c];
/*     */         } else {
/* 127 */           if (function == null) {
/* 128 */             function = NULL_FUNCTION;
/*     */           }
/* 130 */           if (map.mapping[c] instanceof KeyMap) {
/* 131 */             map.anotherKey = function;
/*     */           } else {
/* 133 */             map.mapping[c] = function;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindArrowKeys() {
/* 142 */     bindIfNotBound("\033[0A", Operation.PREVIOUS_HISTORY);
/* 143 */     bindIfNotBound("\033[0B", Operation.BACKWARD_CHAR);
/* 144 */     bindIfNotBound("\033[0C", Operation.FORWARD_CHAR);
/* 145 */     bindIfNotBound("\033[0D", Operation.NEXT_HISTORY);
/*     */ 
/*     */     
/* 148 */     bindIfNotBound("à\000", Operation.KILL_WHOLE_LINE);
/* 149 */     bindIfNotBound("àG", Operation.BEGINNING_OF_LINE);
/* 150 */     bindIfNotBound("àH", Operation.PREVIOUS_HISTORY);
/* 151 */     bindIfNotBound("àI", Operation.BEGINNING_OF_HISTORY);
/* 152 */     bindIfNotBound("àK", Operation.BACKWARD_CHAR);
/* 153 */     bindIfNotBound("àM", Operation.FORWARD_CHAR);
/* 154 */     bindIfNotBound("àO", Operation.END_OF_LINE);
/* 155 */     bindIfNotBound("àP", Operation.NEXT_HISTORY);
/* 156 */     bindIfNotBound("àQ", Operation.END_OF_HISTORY);
/* 157 */     bindIfNotBound("àR", Operation.OVERWRITE_MODE);
/* 158 */     bindIfNotBound("àS", Operation.DELETE_CHAR);
/* 159 */     bindIfNotBound("\000H", Operation.PREVIOUS_HISTORY);
/* 160 */     bindIfNotBound("\000K", Operation.BACKWARD_CHAR);
/* 161 */     bindIfNotBound("\000M", Operation.FORWARD_CHAR);
/* 162 */     bindIfNotBound("\000P", Operation.NEXT_HISTORY);
/* 163 */     bindIfNotBound("\000S", Operation.DELETE_CHAR);
/*     */     
/* 165 */     bindIfNotBound("\033[A", Operation.PREVIOUS_HISTORY);
/* 166 */     bindIfNotBound("\033[B", Operation.NEXT_HISTORY);
/* 167 */     bindIfNotBound("\033[C", Operation.FORWARD_CHAR);
/* 168 */     bindIfNotBound("\033[D", Operation.BACKWARD_CHAR);
/* 169 */     bindIfNotBound("\033[H", Operation.BEGINNING_OF_LINE);
/* 170 */     bindIfNotBound("\033[F", Operation.END_OF_LINE);
/*     */     
/* 172 */     bindIfNotBound("\033[OA", Operation.PREVIOUS_HISTORY);
/* 173 */     bindIfNotBound("\033[OB", Operation.NEXT_HISTORY);
/* 174 */     bindIfNotBound("\033[OC", Operation.FORWARD_CHAR);
/* 175 */     bindIfNotBound("\033[OD", Operation.BACKWARD_CHAR);
/* 176 */     bindIfNotBound("\033[OH", Operation.BEGINNING_OF_LINE);
/* 177 */     bindIfNotBound("\033[OF", Operation.END_OF_LINE);
/*     */     
/* 179 */     bindIfNotBound("\033[3~", Operation.DELETE_CHAR);
/*     */ 
/*     */     
/* 182 */     bindIfNotBound("\0340H", Operation.PREVIOUS_HISTORY);
/* 183 */     bindIfNotBound("\0340P", Operation.NEXT_HISTORY);
/* 184 */     bindIfNotBound("\0340M", Operation.FORWARD_CHAR);
/* 185 */     bindIfNotBound("\0340K", Operation.BACKWARD_CHAR);
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
/*     */   public static boolean isMeta(char c) {
/* 197 */     return (c > '' && c <= 'ÿ');
/*     */   }
/*     */   
/*     */   public static char unMeta(char c) {
/* 201 */     return (char)(c & 0x7F);
/*     */   }
/*     */   
/*     */   public static char meta(char c) {
/* 205 */     return (char)(c | 0x80);
/*     */   }
/*     */   
/*     */   public static Map<String, KeyMap> keyMaps() {
/* 209 */     Map<String, KeyMap> keyMaps = new HashMap<String, KeyMap>();
/* 210 */     KeyMap emacs = emacs();
/* 211 */     keyMaps.put("emacs", emacs);
/* 212 */     keyMaps.put("emacs-standard", emacs);
/* 213 */     keyMaps.put("emacs-ctlx", (KeyMap)emacs.getBound("\030"));
/* 214 */     keyMaps.put("emacs-meta", (KeyMap)emacs.getBound("\033"));
/* 215 */     KeyMap viMov = viMovement();
/* 216 */     keyMaps.put("vi", viMov);
/* 217 */     keyMaps.put("vi-move", viMov);
/* 218 */     keyMaps.put("vi-command", viMov);
/* 219 */     KeyMap viIns = viInsertion();
/* 220 */     keyMaps.put("vi-insert", viIns);
/* 221 */     return keyMaps;
/*     */   }
/*     */   
/*     */   public static KeyMap emacs() {
/* 225 */     Object[] map = new Object[256];
/* 226 */     Object[] ctrl = { Operation.SET_MARK, Operation.BEGINNING_OF_LINE, Operation.BACKWARD_CHAR, null, Operation.EXIT_OR_DELETE_CHAR, Operation.END_OF_LINE, Operation.FORWARD_CHAR, Operation.ABORT, Operation.BACKWARD_DELETE_CHAR, Operation.COMPLETE, Operation.ACCEPT_LINE, Operation.KILL_LINE, Operation.CLEAR_SCREEN, Operation.ACCEPT_LINE, Operation.NEXT_HISTORY, null, Operation.PREVIOUS_HISTORY, Operation.QUOTED_INSERT, Operation.REVERSE_SEARCH_HISTORY, Operation.FORWARD_SEARCH_HISTORY, Operation.TRANSPOSE_CHARS, Operation.UNIX_LINE_DISCARD, Operation.QUOTED_INSERT, Operation.UNIX_WORD_RUBOUT, emacsCtrlX(), Operation.YANK, null, emacsMeta(), null, Operation.CHARACTER_SEARCH, null, Operation.UNDO };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     System.arraycopy(ctrl, 0, map, 0, ctrl.length);
/* 262 */     for (int i = 32; i < 256; i++) {
/* 263 */       map[i] = Operation.SELF_INSERT;
/*     */     }
/* 265 */     map[127] = Operation.BACKWARD_DELETE_CHAR;
/* 266 */     return new KeyMap(map);
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
/*     */   public static KeyMap emacsCtrlX() {
/* 285 */     Object[] map = new Object[256];
/* 286 */     map[7] = Operation.ABORT;
/* 287 */     map[18] = Operation.RE_READ_INIT_FILE;
/* 288 */     map[21] = Operation.UNDO;
/* 289 */     map[24] = Operation.EXCHANGE_POINT_AND_MARK;
/* 290 */     map[40] = Operation.START_KBD_MACRO;
/* 291 */     map[41] = Operation.END_KBD_MACRO;
/* 292 */     for (int i = 65; i <= 90; i++) {
/* 293 */       map[i] = Operation.DO_LOWERCASE_VERSION;
/*     */     }
/* 295 */     map[101] = Operation.CALL_LAST_KBD_MACRO;
/* 296 */     map[127] = Operation.KILL_LINE;
/* 297 */     return new KeyMap(map);
/*     */   }
/*     */   
/*     */   public static KeyMap emacsMeta() {
/* 301 */     Object[] map = new Object[256];
/* 302 */     map[7] = Operation.ABORT;
/* 303 */     map[8] = Operation.BACKWARD_KILL_WORD;
/* 304 */     map[9] = Operation.TAB_INSERT;
/* 305 */     map[10] = Operation.VI_EDITING_MODE;
/* 306 */     map[13] = Operation.VI_EDITING_MODE;
/* 307 */     map[18] = Operation.REVERT_LINE;
/* 308 */     map[25] = Operation.YANK_NTH_ARG;
/* 309 */     map[27] = Operation.COMPLETE;
/* 310 */     map[29] = Operation.CHARACTER_SEARCH_BACKWARD;
/* 311 */     map[32] = Operation.SET_MARK;
/* 312 */     map[35] = Operation.INSERT_COMMENT;
/* 313 */     map[38] = Operation.TILDE_EXPAND;
/* 314 */     map[42] = Operation.INSERT_COMPLETIONS;
/* 315 */     map[45] = Operation.DIGIT_ARGUMENT;
/* 316 */     map[46] = Operation.YANK_LAST_ARG;
/* 317 */     map[60] = Operation.BEGINNING_OF_HISTORY;
/* 318 */     map[61] = Operation.POSSIBLE_COMPLETIONS;
/* 319 */     map[62] = Operation.END_OF_HISTORY;
/* 320 */     map[63] = Operation.POSSIBLE_COMPLETIONS;
/* 321 */     for (int i = 65; i <= 90; i++) {
/* 322 */       map[i] = Operation.DO_LOWERCASE_VERSION;
/*     */     }
/* 324 */     map[92] = Operation.DELETE_HORIZONTAL_SPACE;
/* 325 */     map[95] = Operation.YANK_LAST_ARG;
/* 326 */     map[98] = Operation.BACKWARD_WORD;
/* 327 */     map[99] = Operation.CAPITALIZE_WORD;
/* 328 */     map[100] = Operation.KILL_WORD;
/* 329 */     map[102] = Operation.FORWARD_WORD;
/* 330 */     map[108] = Operation.DOWNCASE_WORD;
/* 331 */     map[112] = Operation.NON_INCREMENTAL_REVERSE_SEARCH_HISTORY;
/* 332 */     map[114] = Operation.REVERT_LINE;
/* 333 */     map[116] = Operation.TRANSPOSE_WORDS;
/* 334 */     map[117] = Operation.UPCASE_WORD;
/* 335 */     map[121] = Operation.YANK_POP;
/* 336 */     map[126] = Operation.TILDE_EXPAND;
/* 337 */     map[127] = Operation.BACKWARD_KILL_WORD;
/* 338 */     return new KeyMap(map);
/*     */   }
/*     */   
/*     */   public static KeyMap viInsertion() {
/* 342 */     Object[] map = new Object[256];
/* 343 */     Object[] ctrl = { null, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.VI_EOF_MAYBE, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.BACKWARD_DELETE_CHAR, Operation.COMPLETE, Operation.ACCEPT_LINE, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.ACCEPT_LINE, Operation.MENU_COMPLETE, Operation.SELF_INSERT, Operation.MENU_COMPLETE_BACKWARD, Operation.SELF_INSERT, Operation.REVERSE_SEARCH_HISTORY, Operation.FORWARD_SEARCH_HISTORY, Operation.TRANSPOSE_CHARS, Operation.UNIX_LINE_DISCARD, Operation.QUOTED_INSERT, Operation.UNIX_WORD_RUBOUT, Operation.SELF_INSERT, Operation.YANK, Operation.SELF_INSERT, Operation.VI_MOVEMENT_MODE, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.UNDO };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     System.arraycopy(ctrl, 0, map, 0, ctrl.length);
/* 379 */     for (int i = 32; i < 256; i++) {
/* 380 */       map[i] = Operation.SELF_INSERT;
/*     */     }
/* 382 */     map[127] = Operation.BACKWARD_DELETE_CHAR;
/* 383 */     return new KeyMap(map);
/*     */   }
/*     */   
/*     */   public static KeyMap viMovement() {
/* 387 */     Object[] map = new Object[256];
/* 388 */     Object[] low = { null, null, null, null, Operation.VI_EOF_MAYBE, Operation.EMACS_EDITING_MODE, null, Operation.ABORT, Operation.BACKWARD_CHAR, null, Operation.ACCEPT_LINE, Operation.KILL_LINE, Operation.CLEAR_SCREEN, Operation.ACCEPT_LINE, Operation.NEXT_HISTORY, null, Operation.PREVIOUS_HISTORY, Operation.QUOTED_INSERT, Operation.REVERSE_SEARCH_HISTORY, Operation.FORWARD_SEARCH_HISTORY, Operation.TRANSPOSE_CHARS, Operation.UNIX_LINE_DISCARD, Operation.QUOTED_INSERT, Operation.UNIX_WORD_RUBOUT, null, Operation.YANK, null, emacsMeta(), null, Operation.CHARACTER_SEARCH, null, Operation.UNDO, Operation.FORWARD_CHAR, null, null, Operation.INSERT_COMMENT, Operation.END_OF_LINE, Operation.VI_MATCH, Operation.VI_TILDE_EXPAND, null, null, null, Operation.VI_COMPLETE, Operation.NEXT_HISTORY, Operation.VI_CHAR_SEARCH, Operation.PREVIOUS_HISTORY, Operation.VI_REDO, Operation.VI_SEARCH, Operation.BEGINNING_OF_LINE, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, null, Operation.VI_CHAR_SEARCH, null, Operation.VI_COMPLETE, null, Operation.VI_SEARCH, null, Operation.VI_APPEND_EOL, Operation.VI_PREV_WORD, Operation.VI_CHANGE_TO, Operation.VI_DELETE_TO, Operation.VI_END_WORD, Operation.VI_CHAR_SEARCH, Operation.VI_FETCH_HISTORY, null, Operation.VI_INSERT_BEG, null, null, null, null, Operation.VI_SEARCH_AGAIN, null, Operation.VI_PUT, null, Operation.VI_REPLACE, Operation.VI_SUBST, Operation.VI_CHAR_SEARCH, Operation.REVERT_LINE, null, Operation.VI_NEXT_WORD, Operation.VI_RUBOUT, Operation.VI_YANK_TO, null, null, Operation.VI_COMPLETE, null, Operation.VI_FIRST_PRINT, Operation.VI_YANK_ARG, Operation.VI_GOTO_MARK, Operation.VI_APPEND_MODE, Operation.VI_PREV_WORD, Operation.VI_CHANGE_TO, Operation.VI_DELETE_TO, Operation.VI_END_WORD, Operation.VI_CHAR_SEARCH, null, Operation.BACKWARD_CHAR, Operation.VI_INSERTION_MODE, Operation.NEXT_HISTORY, Operation.PREVIOUS_HISTORY, Operation.FORWARD_CHAR, Operation.VI_SET_MARK, Operation.VI_SEARCH_AGAIN, null, Operation.VI_PUT, null, Operation.VI_CHANGE_CHAR, Operation.VI_SUBST, Operation.VI_CHAR_SEARCH, Operation.UNDO, null, Operation.VI_NEXT_WORD, Operation.VI_RUBOUT, Operation.VI_YANK_TO, null, null, Operation.VI_COLUMN, null, Operation.VI_CHANGE_CASE, null };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 519 */     System.arraycopy(low, 0, map, 0, low.length);
/* 520 */     for (int i = 128; i < 256; i++) {
/* 521 */       map[i] = null;
/*     */     }
/* 523 */     return new KeyMap(map);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\KeyMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */