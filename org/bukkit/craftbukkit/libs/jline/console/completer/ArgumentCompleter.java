/*     */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class ArgumentCompleter
/*     */   implements Completer
/*     */ {
/*     */   private final ArgumentDelimiter delimiter;
/*  31 */   private final List<Completer> completers = new ArrayList<Completer>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean strict = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentCompleter(ArgumentDelimiter delimiter, Collection<Completer> completers) {
/*  42 */     assert delimiter != null;
/*  43 */     this.delimiter = delimiter;
/*  44 */     assert completers != null;
/*  45 */     this.completers.addAll(completers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentCompleter(ArgumentDelimiter delimiter, Completer... completers) {
/*  55 */     this(delimiter, Arrays.asList(completers));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentCompleter(Completer... completers) {
/*  64 */     this(new WhitespaceArgumentDelimiter(), completers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentCompleter(List<Completer> completers) {
/*  73 */     this(new WhitespaceArgumentDelimiter(), completers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStrict(boolean strict) {
/*  81 */     this.strict = strict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStrict() {
/*  92 */     return this.strict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentDelimiter getDelimiter() {
/*  99 */     return this.delimiter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Completer> getCompleters() {
/* 106 */     return this.completers;
/*     */   }
/*     */   
/*     */   public int complete(String buffer, int cursor, List<CharSequence> candidates) {
/*     */     Completer completer;
/* 111 */     assert candidates != null;
/*     */     
/* 113 */     ArgumentDelimiter delim = getDelimiter();
/* 114 */     ArgumentList list = delim.delimit(buffer, cursor);
/* 115 */     int argpos = list.getArgumentPosition();
/* 116 */     int argIndex = list.getCursorArgumentIndex();
/*     */     
/* 118 */     if (argIndex < 0) {
/* 119 */       return -1;
/*     */     }
/*     */     
/* 122 */     List<Completer> completers = getCompleters();
/*     */ 
/*     */ 
/*     */     
/* 126 */     if (argIndex >= completers.size()) {
/* 127 */       completer = completers.get(completers.size() - 1);
/*     */     } else {
/*     */       
/* 130 */       completer = completers.get(argIndex);
/*     */     } 
/*     */ 
/*     */     
/* 134 */     for (int i = 0; isStrict() && i < argIndex; i++) {
/* 135 */       Completer sub = completers.get((i >= completers.size()) ? (completers.size() - 1) : i);
/* 136 */       String[] args = list.getArguments();
/* 137 */       String arg = (args == null || i >= args.length) ? "" : args[i];
/*     */       
/* 139 */       List<CharSequence> subCandidates = new LinkedList<CharSequence>();
/*     */       
/* 141 */       if (sub.complete(arg, arg.length(), subCandidates) == -1) {
/* 142 */         return -1;
/*     */       }
/*     */       
/* 145 */       if (subCandidates.size() == 0) {
/* 146 */         return -1;
/*     */       }
/*     */     } 
/*     */     
/* 150 */     int ret = completer.complete(list.getCursorArgument(), argpos, candidates);
/*     */     
/* 152 */     if (ret == -1) {
/* 153 */       return -1;
/*     */     }
/*     */     
/* 156 */     int pos = ret + list.getBufferPosition() - argpos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (cursor != buffer.length() && delim.isDelimiter(buffer, cursor)) {
/* 165 */       for (int j = 0; j < candidates.size(); j++) {
/* 166 */         CharSequence val = candidates.get(j);
/*     */         
/* 168 */         while (val.length() > 0 && delim.isDelimiter(val, val.length() - 1)) {
/* 169 */           val = val.subSequence(0, val.length() - 1);
/*     */         }
/*     */         
/* 172 */         candidates.set(j, val);
/*     */       } 
/*     */     }
/*     */     
/* 176 */     Log.trace(new Object[] { "Completing ", buffer, " (pos=", Integer.valueOf(cursor), ") with: ", candidates, ": offset=", Integer.valueOf(pos) });
/*     */     
/* 178 */     return pos;
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
/*     */   public static interface ArgumentDelimiter
/*     */   {
/*     */     ArgumentCompleter.ArgumentList delimit(CharSequence param1CharSequence, int param1Int);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isDelimiter(CharSequence param1CharSequence, int param1Int);
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
/*     */   public static abstract class AbstractArgumentDelimiter
/*     */     implements ArgumentDelimiter
/*     */   {
/* 219 */     private char[] quoteChars = new char[] { '\'', '"' };
/*     */     
/* 221 */     private char[] escapeChars = new char[] { '\\' };
/*     */     
/*     */     public void setQuoteChars(char[] chars) {
/* 224 */       this.quoteChars = chars;
/*     */     }
/*     */     
/*     */     public char[] getQuoteChars() {
/* 228 */       return this.quoteChars;
/*     */     }
/*     */     
/*     */     public void setEscapeChars(char[] chars) {
/* 232 */       this.escapeChars = chars;
/*     */     }
/*     */     
/*     */     public char[] getEscapeChars() {
/* 236 */       return this.escapeChars;
/*     */     }
/*     */     
/*     */     public ArgumentCompleter.ArgumentList delimit(CharSequence buffer, int cursor) {
/* 240 */       List<String> args = new LinkedList<String>();
/* 241 */       StringBuilder arg = new StringBuilder();
/* 242 */       int argpos = -1;
/* 243 */       int bindex = -1;
/*     */       
/* 245 */       for (int i = 0; buffer != null && i <= buffer.length(); i++) {
/*     */ 
/*     */         
/* 248 */         if (i == cursor) {
/* 249 */           bindex = args.size();
/*     */ 
/*     */           
/* 252 */           argpos = arg.length();
/*     */         } 
/*     */         
/* 255 */         if (i == buffer.length() || isDelimiter(buffer, i)) {
/* 256 */           if (arg.length() > 0) {
/* 257 */             args.add(arg.toString());
/* 258 */             arg.setLength(0);
/*     */           } 
/*     */         } else {
/*     */           
/* 262 */           arg.append(buffer.charAt(i));
/*     */         } 
/*     */       } 
/*     */       
/* 266 */       return new ArgumentCompleter.ArgumentList(args.<String>toArray(new String[args.size()]), bindex, argpos, cursor);
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
/*     */     public boolean isDelimiter(CharSequence buffer, int pos) {
/* 279 */       return (!isQuoted(buffer, pos) && !isEscaped(buffer, pos) && isDelimiterChar(buffer, pos));
/*     */     }
/*     */     
/*     */     public boolean isQuoted(CharSequence buffer, int pos) {
/* 283 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isEscaped(CharSequence buffer, int pos) {
/* 287 */       if (pos <= 0) {
/* 288 */         return false;
/*     */       }
/*     */       
/* 291 */       for (int i = 0; this.escapeChars != null && i < this.escapeChars.length; 
/* 292 */         i++) {
/* 293 */         if (buffer.charAt(pos) == this.escapeChars[i]) {
/* 294 */           return !isEscaped(buffer, pos - 1);
/*     */         }
/*     */       } 
/*     */       
/* 298 */       return false;
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
/*     */     public abstract boolean isDelimiterChar(CharSequence param1CharSequence, int param1Int);
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
/*     */   public static class WhitespaceArgumentDelimiter
/*     */     extends AbstractArgumentDelimiter
/*     */   {
/*     */     public boolean isDelimiterChar(CharSequence buffer, int pos) {
/* 324 */       return Character.isWhitespace(buffer.charAt(pos));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ArgumentList
/*     */   {
/*     */     private String[] arguments;
/*     */ 
/*     */ 
/*     */     
/*     */     private int cursorArgumentIndex;
/*     */ 
/*     */ 
/*     */     
/*     */     private int argumentPosition;
/*     */ 
/*     */ 
/*     */     
/*     */     private int bufferPosition;
/*     */ 
/*     */ 
/*     */     
/*     */     public ArgumentList(String[] arguments, int cursorArgumentIndex, int argumentPosition, int bufferPosition) {
/* 350 */       assert arguments != null;
/*     */       
/* 352 */       this.arguments = arguments;
/* 353 */       this.cursorArgumentIndex = cursorArgumentIndex;
/* 354 */       this.argumentPosition = argumentPosition;
/* 355 */       this.bufferPosition = bufferPosition;
/*     */     }
/*     */     
/*     */     public void setCursorArgumentIndex(int i) {
/* 359 */       this.cursorArgumentIndex = i;
/*     */     }
/*     */     
/*     */     public int getCursorArgumentIndex() {
/* 363 */       return this.cursorArgumentIndex;
/*     */     }
/*     */     
/*     */     public String getCursorArgument() {
/* 367 */       if (this.cursorArgumentIndex < 0 || this.cursorArgumentIndex >= this.arguments.length) {
/* 368 */         return null;
/*     */       }
/*     */       
/* 371 */       return this.arguments[this.cursorArgumentIndex];
/*     */     }
/*     */     
/*     */     public void setArgumentPosition(int pos) {
/* 375 */       this.argumentPosition = pos;
/*     */     }
/*     */     
/*     */     public int getArgumentPosition() {
/* 379 */       return this.argumentPosition;
/*     */     }
/*     */     
/*     */     public void setArguments(String[] arguments) {
/* 383 */       this.arguments = arguments;
/*     */     }
/*     */     
/*     */     public String[] getArguments() {
/* 387 */       return this.arguments;
/*     */     }
/*     */     
/*     */     public void setBufferPosition(int pos) {
/* 391 */       this.bufferPosition = pos;
/*     */     }
/*     */     
/*     */     public int getBufferPosition() {
/* 395 */       return this.bufferPosition;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\ArgumentCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */