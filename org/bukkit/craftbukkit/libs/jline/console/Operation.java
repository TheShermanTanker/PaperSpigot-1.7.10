/*     */ package org.bukkit.craftbukkit.libs.jline.console;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Operation
/*     */ {
/*  26 */   ABORT,
/*  27 */   ACCEPT_LINE,
/*  28 */   ARROW_KEY_PREFIX,
/*  29 */   BACKWARD_BYTE,
/*  30 */   BACKWARD_CHAR,
/*  31 */   BACKWARD_DELETE_CHAR,
/*  32 */   BACKWARD_KILL_LINE,
/*  33 */   BACKWARD_KILL_WORD,
/*  34 */   BACKWARD_WORD,
/*  35 */   BEGINNING_OF_HISTORY,
/*  36 */   BEGINNING_OF_LINE,
/*  37 */   CALL_LAST_KBD_MACRO,
/*  38 */   CAPITALIZE_WORD,
/*  39 */   CHARACTER_SEARCH,
/*  40 */   CHARACTER_SEARCH_BACKWARD,
/*  41 */   CLEAR_SCREEN,
/*  42 */   COMPLETE,
/*  43 */   COPY_BACKWARD_WORD,
/*  44 */   COPY_FORWARD_WORD,
/*  45 */   COPY_REGION_AS_KILL,
/*  46 */   DELETE_CHAR,
/*  47 */   DELETE_CHAR_OR_LIST,
/*  48 */   DELETE_HORIZONTAL_SPACE,
/*  49 */   DIGIT_ARGUMENT,
/*  50 */   DO_LOWERCASE_VERSION,
/*  51 */   DOWNCASE_WORD,
/*  52 */   DUMP_FUNCTIONS,
/*  53 */   DUMP_MACROS,
/*  54 */   DUMP_VARIABLES,
/*  55 */   EMACS_EDITING_MODE,
/*  56 */   END_KBD_MACRO,
/*  57 */   END_OF_HISTORY,
/*  58 */   END_OF_LINE,
/*  59 */   EXCHANGE_POINT_AND_MARK,
/*  60 */   EXIT_OR_DELETE_CHAR,
/*  61 */   FORWARD_BACKWARD_DELETE_CHAR,
/*  62 */   FORWARD_BYTE,
/*  63 */   FORWARD_CHAR,
/*  64 */   FORWARD_SEARCH_HISTORY,
/*  65 */   FORWARD_WORD,
/*  66 */   HISTORY_SEARCH_BACKWARD,
/*  67 */   HISTORY_SEARCH_FORWARD,
/*  68 */   INSERT_COMMENT,
/*  69 */   INSERT_COMPLETIONS,
/*  70 */   KILL_WHOLE_LINE,
/*  71 */   KILL_LINE,
/*  72 */   KILL_REGION,
/*  73 */   KILL_WORD,
/*  74 */   MENU_COMPLETE,
/*  75 */   MENU_COMPLETE_BACKWARD,
/*  76 */   NEXT_HISTORY,
/*  77 */   NON_INCREMENTAL_FORWARD_SEARCH_HISTORY,
/*  78 */   NON_INCREMENTAL_REVERSE_SEARCH_HISTORY,
/*  79 */   NON_INCREMENTAL_FORWARD_SEARCH_HISTORY_AGAIN,
/*  80 */   NON_INCREMENTAL_REVERSE_SEARCH_HISTORY_AGAIN,
/*  81 */   OLD_MENU_COMPLETE,
/*  82 */   OVERWRITE_MODE,
/*  83 */   PASTE_FROM_CLIPBOARD,
/*  84 */   POSSIBLE_COMPLETIONS,
/*  85 */   PREVIOUS_HISTORY,
/*  86 */   QUOTED_INSERT,
/*  87 */   RE_READ_INIT_FILE,
/*  88 */   REDRAW_CURRENT_LINE,
/*  89 */   REVERSE_SEARCH_HISTORY,
/*  90 */   REVERT_LINE,
/*  91 */   SELF_INSERT,
/*  92 */   SET_MARK,
/*  93 */   SKIP_CSI_SEQUENCE,
/*  94 */   START_KBD_MACRO,
/*  95 */   TAB_INSERT,
/*  96 */   TILDE_EXPAND,
/*  97 */   TRANSPOSE_CHARS,
/*  98 */   TRANSPOSE_WORDS,
/*  99 */   TTY_STATUS,
/* 100 */   UNDO,
/* 101 */   UNIVERSAL_ARGUMENT,
/* 102 */   UNIX_FILENAME_RUBOUT,
/* 103 */   UNIX_LINE_DISCARD,
/* 104 */   UNIX_WORD_RUBOUT,
/* 105 */   UPCASE_WORD,
/* 106 */   YANK,
/* 107 */   YANK_LAST_ARG,
/* 108 */   YANK_NTH_ARG,
/* 109 */   YANK_POP,
/* 110 */   VI_APPEND_EOL,
/* 111 */   VI_APPEND_MODE,
/* 112 */   VI_ARG_DIGIT,
/* 113 */   VI_BACK_TO_INDENT,
/* 114 */   VI_BACKWARD_BIGWORD,
/* 115 */   VI_BACKWARD_WORD,
/* 116 */   VI_BWORD,
/* 117 */   VI_CHANGE_CASE,
/* 118 */   VI_CHANGE_CHAR,
/* 119 */   VI_CHANGE_TO,
/* 120 */   VI_CHAR_SEARCH,
/* 121 */   VI_COLUMN,
/* 122 */   VI_COMPLETE,
/* 123 */   VI_DELETE,
/* 124 */   VI_DELETE_TO,
/* 125 */   VI_EDITING_MODE,
/* 126 */   VI_END_BIGWORD,
/* 127 */   VI_END_WORD,
/* 128 */   VI_EOF_MAYBE,
/* 129 */   VI_EWORD,
/* 130 */   VI_FWORD,
/* 131 */   VI_FETCH_HISTORY,
/* 132 */   VI_FIRST_PRINT,
/* 133 */   VI_FORWARD_BIGWORD,
/* 134 */   VI_FORWARD_WORD,
/* 135 */   VI_GOTO_MARK,
/* 136 */   VI_INSERT_BEG,
/* 137 */   VI_INSERTION_MODE,
/* 138 */   VI_MATCH,
/* 139 */   VI_MOVEMENT_MODE,
/* 140 */   VI_NEXT_WORD,
/* 141 */   VI_OVERSTRIKE,
/* 142 */   VI_OVERSTRIKE_DELETE,
/* 143 */   VI_PREV_WORD,
/* 144 */   VI_PUT,
/* 145 */   VI_REDO,
/* 146 */   VI_REPLACE,
/* 147 */   VI_RUBOUT,
/* 148 */   VI_SEARCH,
/* 149 */   VI_SEARCH_AGAIN,
/* 150 */   VI_SET_MARK,
/* 151 */   VI_SUBST,
/* 152 */   VI_TILDE_EXPAND,
/* 153 */   VI_YANK_ARG,
/* 154 */   VI_YANK_TO;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\Operation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */