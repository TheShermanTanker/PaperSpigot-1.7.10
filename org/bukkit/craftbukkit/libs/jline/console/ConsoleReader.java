/*      */ package org.bukkit.craftbukkit.libs.jline.console;
/*      */ 
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.datatransfer.Clipboard;
/*      */ import java.awt.datatransfer.DataFlavor;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.datatransfer.UnsupportedFlavorException;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FilterInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import org.bukkit.craftbukkit.libs.jline.Terminal;
/*      */ import org.bukkit.craftbukkit.libs.jline.TerminalFactory;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.completer.CandidateListCompletionHandler;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.completer.Completer;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.completer.CompletionHandler;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.history.History;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.history.MemoryHistory;
/*      */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
/*      */ import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
/*      */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*      */ import org.fusesource.jansi.AnsiOutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConsoleReader
/*      */ {
/*      */   public static final String JLINE_NOBELL = "org.bukkit.craftbukkit.libs.jline.nobell";
/*      */   public static final char BACKSPACE = '\b';
/*      */   public static final char RESET_LINE = '\r';
/*      */   public static final char KEYBOARD_BELL = '\007';
/*      */   public static final char NULL_MASK = '\000';
/*      */   public static final int TAB_WIDTH = 4;
/*   79 */   private static final ResourceBundle resources = ResourceBundle.getBundle(CandidateListCompletionHandler.class.getName());
/*      */   
/*      */   private final Terminal terminal;
/*      */   
/*      */   private InputStream in;
/*      */   
/*      */   private final Writer out;
/*      */   
/*   87 */   private final CursorBuffer buf = new CursorBuffer();
/*      */   
/*      */   private String prompt;
/*      */   
/*      */   private boolean expandEvents = true;
/*      */   
/*      */   private Character mask;
/*      */   
/*      */   private Character echoCharacter;
/*      */   
/*   97 */   private StringBuffer searchTerm = null;
/*      */   
/*   99 */   private String previousSearchTerm = "";
/*      */   
/*  101 */   private int searchIndex = -1;
/*      */   
/*      */   private Reader reader;
/*      */   
/*      */   private String encoding;
/*      */   
/*      */   private boolean recording;
/*      */   
/*  109 */   private String macro = ""; private String appName; private URL inputrcUrl; private ConsoleKeys consoleKeys;
/*      */   private boolean skipLF = false;
/*      */   public static final String JLINE_COMPLETION_THRESHOLD = "org.bukkit.craftbukkit.libs.jline.completion.threshold";
/*      */   private final List<Completer> completers;
/*      */   private CompletionHandler completionHandler;
/*      */   private int autoprintThreshold;
/*      */   private boolean paginationEnabled;
/*      */   private History history;
/*      */   private boolean historyEnabled;
/*      */   
/*      */   public ConsoleReader() throws IOException {
/*  120 */     this(null, new FileInputStream(FileDescriptor.in), System.out, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ConsoleReader(InputStream in, OutputStream out) throws IOException {
/*  126 */     this(null, in, out, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ConsoleReader(InputStream in, OutputStream out, Terminal term) throws IOException {
/*  132 */     this(null, in, out, term);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyMap getKeys() {
/*  150 */     return this.consoleKeys.getKeys();
/*      */   }
/*      */   
/*      */   void setInput(InputStream in) throws IOException {
/*  154 */     InputStream wrapped = this.terminal.wrapInIfNeeded(in);
/*      */     
/*  156 */     this.in = new FilterInputStream(wrapped)
/*      */       {
/*      */         public int read(byte[] b, int off, int len) throws IOException {
/*  159 */           if (b == null)
/*  160 */             throw new NullPointerException(); 
/*  161 */           if (off < 0 || len < 0 || len > b.length - off)
/*  162 */             throw new IndexOutOfBoundsException(); 
/*  163 */           if (len == 0) {
/*  164 */             return 0;
/*      */           }
/*      */           
/*  167 */           int c = read();
/*  168 */           if (c == -1) {
/*  169 */             return -1;
/*      */           }
/*  171 */           b[off] = (byte)c;
/*  172 */           return 1;
/*      */         }
/*      */       };
/*  175 */     this.reader = (Reader)new InputStreamReader(this.in, this.encoding);
/*      */   }
/*      */   
/*      */   public InputStream getInput() {
/*  179 */     return this.in;
/*      */   }
/*      */   
/*      */   public Writer getOutput() {
/*  183 */     return this.out;
/*      */   }
/*      */   
/*      */   public Terminal getTerminal() {
/*  187 */     return this.terminal;
/*      */   }
/*      */   
/*      */   public CursorBuffer getCursorBuffer() {
/*  191 */     return this.buf;
/*      */   }
/*      */   
/*      */   public void setExpandEvents(boolean expand) {
/*  195 */     this.expandEvents = expand;
/*      */   }
/*      */   
/*      */   public boolean getExpandEvents() {
/*  199 */     return this.expandEvents;
/*      */   }
/*      */   
/*      */   public void setPrompt(String prompt) {
/*  203 */     this.prompt = prompt;
/*      */   }
/*      */   
/*      */   public String getPrompt() {
/*  207 */     return this.prompt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEchoCharacter(Character c) {
/*  234 */     this.echoCharacter = c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Character getEchoCharacter() {
/*  241 */     return this.echoCharacter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean resetLine() throws IOException {
/*  250 */     if (this.buf.cursor == 0) {
/*  251 */       return false;
/*      */     }
/*      */     
/*  254 */     backspaceAll();
/*      */     
/*  256 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   int getCursorPosition() {
/*  261 */     String prompt = getPrompt();
/*  262 */     return ((prompt == null) ? 0 : stripAnsi(lastLine(prompt)).length()) + this.buf.cursor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String lastLine(String str) {
/*  271 */     if (str == null) return ""; 
/*  272 */     int last = str.lastIndexOf("\n");
/*      */     
/*  274 */     if (last >= 0) {
/*  275 */       return str.substring(last + 1, str.length());
/*      */     }
/*      */     
/*  278 */     return str;
/*      */   }
/*      */   
/*      */   private String stripAnsi(String str) {
/*  282 */     if (str == null) return ""; 
/*      */     try {
/*  284 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  285 */       AnsiOutputStream aos = new AnsiOutputStream(baos);
/*  286 */       aos.write(str.getBytes());
/*  287 */       aos.flush();
/*  288 */       return baos.toString();
/*  289 */     } catch (IOException e) {
/*  290 */       return str;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean setCursorPosition(int position) throws IOException {
/*  298 */     return (moveCursor(position - this.buf.cursor) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setBuffer(String buffer) throws IOException {
/*  309 */     if (buffer.equals(this.buf.buffer.toString())) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  314 */     int sameIndex = 0;
/*      */     
/*  316 */     int i = 0, l1 = buffer.length(), l2 = this.buf.buffer.length();
/*  317 */     for (; i < l1 && i < l2 && 
/*  318 */       buffer.charAt(i) == this.buf.buffer.charAt(i); i++) {
/*  319 */       sameIndex++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  326 */     int diff = this.buf.cursor - sameIndex;
/*  327 */     if (diff < 0) {
/*  328 */       moveToEnd();
/*  329 */       diff = this.buf.buffer.length() - sameIndex;
/*      */     } 
/*      */     
/*  332 */     backspace(diff);
/*  333 */     killLine();
/*  334 */     this.buf.buffer.setLength(sameIndex);
/*  335 */     putString(buffer.substring(sameIndex));
/*      */   }
/*      */   
/*      */   private void setBuffer(CharSequence buffer) throws IOException {
/*  339 */     setBuffer(String.valueOf(buffer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawLine() throws IOException {
/*  346 */     String prompt = getPrompt();
/*  347 */     if (prompt != null) {
/*  348 */       print(prompt);
/*      */     }
/*      */     
/*  351 */     print(this.buf.buffer.toString());
/*      */     
/*  353 */     if (this.buf.length() != this.buf.cursor) {
/*  354 */       back(this.buf.length() - this.buf.cursor - 1);
/*      */     }
/*      */     
/*  357 */     drawBuffer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void redrawLine() throws IOException {
/*  364 */     print(13);
/*      */     
/*  366 */     drawLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final String finishBuffer() throws IOException {
/*  375 */     String str = this.buf.buffer.toString();
/*  376 */     String historyLine = str;
/*      */     
/*  378 */     if (this.expandEvents) {
/*  379 */       str = expandEvents(str);
/*  380 */       historyLine = str.replaceAll("\\!", "\\\\!");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  386 */     if (str.length() > 0) {
/*  387 */       if (this.mask == null && isHistoryEnabled()) {
/*  388 */         this.history.add(historyLine);
/*      */       } else {
/*      */         
/*  391 */         this.mask = null;
/*      */       } 
/*      */     }
/*      */     
/*  395 */     this.history.moveToEnd();
/*      */     
/*  397 */     this.buf.buffer.setLength(0);
/*  398 */     this.buf.cursor = 0;
/*      */     
/*  400 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String expandEvents(String str) throws IOException {
/*  411 */     StringBuilder sb = new StringBuilder();
/*  412 */     boolean escaped = false;
/*  413 */     for (int i = 0; i < str.length(); i++) {
/*  414 */       char c = str.charAt(i);
/*  415 */       if (escaped) {
/*  416 */         sb.append(c);
/*  417 */         escaped = false;
/*      */       }
/*  419 */       else if (c == '\\') {
/*  420 */         escaped = true;
/*      */       } else {
/*      */         
/*  423 */         escaped = false;
/*      */         
/*  425 */         switch (c) {
/*      */           case '!':
/*  427 */             if (i + 1 < str.length()) {
/*  428 */               int i1; String sc; int idx; String ss; c = str.charAt(++i);
/*  429 */               boolean neg = false;
/*  430 */               String rep = null;
/*      */               
/*  432 */               switch (c) {
/*      */                 case '!':
/*  434 */                   if (this.history.size() == 0) {
/*  435 */                     throw new IllegalArgumentException("!!: event not found");
/*      */                   }
/*  437 */                   rep = this.history.get(this.history.index() - 1).toString();
/*      */                   break;
/*      */                 case '#':
/*  440 */                   sb.append(sb.toString());
/*      */                   break;
/*      */                 case '?':
/*  443 */                   i1 = str.indexOf('?', i + 1);
/*  444 */                   if (i1 < 0) {
/*  445 */                     i1 = str.length();
/*      */                   }
/*  447 */                   sc = str.substring(i + 1, i1);
/*  448 */                   i = i1;
/*  449 */                   idx = searchBackwards(sc);
/*  450 */                   if (idx < 0) {
/*  451 */                     throw new IllegalArgumentException("!?" + sc + ": event not found");
/*      */                   }
/*  453 */                   rep = this.history.get(idx).toString();
/*      */                   break;
/*      */                 
/*      */                 case '\t':
/*      */                 case ' ':
/*  458 */                   sb.append('!');
/*  459 */                   sb.append(c);
/*      */                   break;
/*      */                 case '-':
/*  462 */                   neg = true;
/*  463 */                   i++;
/*      */                 
/*      */                 case '0':
/*      */                 case '1':
/*      */                 case '2':
/*      */                 case '3':
/*      */                 case '4':
/*      */                 case '5':
/*      */                 case '6':
/*      */                 case '7':
/*      */                 case '8':
/*      */                 case '9':
/*  475 */                   i1 = i;
/*  476 */                   for (; i < str.length(); i++) {
/*  477 */                     c = str.charAt(i);
/*  478 */                     if (c < '0' || c > '9') {
/*      */                       break;
/*      */                     }
/*      */                   } 
/*  482 */                   idx = 0;
/*      */                   try {
/*  484 */                     idx = Integer.parseInt(str.substring(i1, i));
/*  485 */                   } catch (NumberFormatException e) {
/*  486 */                     throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i1, i) + ": event not found");
/*      */                   } 
/*  488 */                   if (neg) {
/*  489 */                     if (idx < this.history.size()) {
/*  490 */                       rep = this.history.get(this.history.index() - idx).toString(); break;
/*      */                     } 
/*  492 */                     throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i1, i) + ": event not found");
/*      */                   } 
/*      */                   
/*  495 */                   if (idx >= this.history.index() - this.history.size() && idx < this.history.index()) {
/*  496 */                     rep = this.history.get(idx).toString(); break;
/*      */                   } 
/*  498 */                   throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i1, i) + ": event not found");
/*      */ 
/*      */ 
/*      */                 
/*      */                 default:
/*  503 */                   ss = str.substring(i);
/*  504 */                   i = str.length();
/*  505 */                   idx = searchBackwards(ss, this.history.index(), true);
/*  506 */                   if (idx < 0) {
/*  507 */                     throw new IllegalArgumentException("!" + ss + ": event not found");
/*      */                   }
/*  509 */                   rep = this.history.get(idx).toString();
/*      */                   break;
/*      */               } 
/*      */               
/*  513 */               if (rep != null)
/*  514 */                 sb.append(rep); 
/*      */               break;
/*      */             } 
/*  517 */             sb.append(c);
/*      */             break;
/*      */           
/*      */           case '^':
/*  521 */             if (i == 0) {
/*  522 */               int i1 = str.indexOf('^', i + 1);
/*  523 */               int i2 = str.indexOf('^', i1 + 1);
/*  524 */               if (i2 < 0) {
/*  525 */                 i2 = str.length();
/*      */               }
/*  527 */               if (i1 > 0 && i2 > 0) {
/*  528 */                 String s1 = str.substring(i + 1, i1);
/*  529 */                 String s2 = str.substring(i1 + 1, i2);
/*  530 */                 String s = this.history.get(this.history.index() - 1).toString().replace(s1, s2);
/*  531 */                 sb.append(s);
/*  532 */                 i = i2 + 1;
/*      */                 break;
/*      */               } 
/*      */             } 
/*  536 */             sb.append(c);
/*      */             break;
/*      */           default:
/*  539 */             sb.append(c); break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  543 */     if (escaped) {
/*  544 */       sb.append('\\');
/*      */     }
/*  546 */     String result = sb.toString();
/*  547 */     if (!str.equals(result)) {
/*  548 */       print(result);
/*  549 */       println();
/*  550 */       flush();
/*      */     } 
/*  552 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void putString(CharSequence str) throws IOException {
/*  560 */     this.buf.write(str);
/*  561 */     if (this.mask == null) {
/*      */       
/*  563 */       print(str);
/*  564 */     } else if (this.mask.charValue() != '\000') {
/*      */ 
/*      */       
/*  567 */       print(this.mask.charValue(), str.length());
/*      */     } 
/*  569 */     drawBuffer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawBuffer(int clear) throws IOException {
/*  580 */     if (this.buf.cursor != this.buf.length() || clear != 0) {
/*      */       
/*  582 */       char[] chars = this.buf.buffer.substring(this.buf.cursor).toCharArray();
/*  583 */       if (this.mask != null) {
/*  584 */         Arrays.fill(chars, this.mask.charValue());
/*      */       }
/*  586 */       if (this.terminal.hasWeirdWrap()) {
/*      */         
/*  588 */         int width = this.terminal.getWidth();
/*  589 */         int pos = getCursorPosition();
/*  590 */         for (int i = 0; i < chars.length; i++) {
/*  591 */           print(chars[i]);
/*  592 */           if ((pos + i + 1) % width == 0) {
/*  593 */             print(32);
/*  594 */             print(13);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  598 */         print(chars);
/*      */       } 
/*  600 */       clearAhead(clear, chars.length);
/*  601 */       if (this.terminal.isAnsiSupported()) {
/*  602 */         if (chars.length > 0) {
/*  603 */           back(chars.length);
/*      */         }
/*      */       } else {
/*  606 */         back(chars.length);
/*      */       } 
/*      */     } 
/*  609 */     if (this.terminal.hasWeirdWrap()) {
/*  610 */       int width = this.terminal.getWidth();
/*      */ 
/*      */ 
/*      */       
/*  614 */       if (getCursorPosition() > 0 && getCursorPosition() % width == 0 && this.buf.cursor == this.buf.length() && clear == 0) {
/*      */ 
/*      */ 
/*      */         
/*  618 */         print(32);
/*  619 */         print(13);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawBuffer() throws IOException {
/*  629 */     drawBuffer(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void clearAhead(int num, int delta) throws IOException {
/*  641 */     if (num == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  645 */     if (this.terminal.isAnsiSupported()) {
/*  646 */       int width = this.terminal.getWidth();
/*  647 */       int screenCursorCol = getCursorPosition() + delta;
/*      */       
/*  649 */       printAnsiSequence("K");
/*      */       
/*  651 */       int curCol = screenCursorCol % width;
/*  652 */       int endCol = (screenCursorCol + num - 1) % width;
/*  653 */       int lines = num / width;
/*  654 */       if (endCol < curCol) lines++;  int i;
/*  655 */       for (i = 0; i < lines; i++) {
/*  656 */         printAnsiSequence("B");
/*  657 */         printAnsiSequence("2K");
/*      */       } 
/*  659 */       for (i = 0; i < lines; i++) {
/*  660 */         printAnsiSequence("A");
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  666 */     print(' ', num);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  673 */     back(num);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void back(int num) throws IOException {
/*  682 */     if (num == 0)
/*  683 */       return;  if (this.terminal.isAnsiSupported()) {
/*  684 */       int width = getTerminal().getWidth();
/*  685 */       int cursor = getCursorPosition();
/*  686 */       int realCursor = cursor + num;
/*  687 */       int realCol = realCursor % width;
/*  688 */       int newCol = cursor % width;
/*  689 */       int moveup = num / width;
/*  690 */       int delta = realCol - newCol;
/*  691 */       if (delta < 0) moveup++; 
/*  692 */       if (moveup > 0) {
/*  693 */         printAnsiSequence(moveup + "A");
/*      */       }
/*  695 */       printAnsiSequence((1 + newCol) + "G");
/*      */       return;
/*      */     } 
/*  698 */     print('\b', num);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() throws IOException {
/*  707 */     this.out.flush();
/*      */   }
/*      */   
/*      */   private int backspaceAll() throws IOException {
/*  711 */     return backspace(2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int backspace(int num) throws IOException {
/*  720 */     if (this.buf.cursor == 0) {
/*  721 */       return 0;
/*      */     }
/*      */     
/*  724 */     int count = 0;
/*      */     
/*  726 */     int termwidth = getTerminal().getWidth();
/*  727 */     int lines = getCursorPosition() / termwidth;
/*  728 */     count = moveCursor(-1 * num) * -1;
/*  729 */     this.buf.buffer.delete(this.buf.cursor, this.buf.cursor + count);
/*  730 */     if (getCursorPosition() / termwidth != lines && 
/*  731 */       this.terminal.isAnsiSupported())
/*      */     {
/*  733 */       printAnsiSequence("K");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  753 */     drawBuffer(count);
/*      */     
/*  755 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean backspace() throws IOException {
/*  764 */     return (backspace(1) == 1);
/*      */   }
/*      */   
/*      */   protected boolean moveToEnd() throws IOException {
/*  768 */     return (moveCursor(this.buf.length() - this.buf.cursor) > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deleteCurrentCharacter() throws IOException {
/*  775 */     if (this.buf.length() == 0 || this.buf.cursor == this.buf.length()) {
/*  776 */       return false;
/*      */     }
/*      */     
/*  779 */     this.buf.buffer.deleteCharAt(this.buf.cursor);
/*  780 */     drawBuffer(1);
/*  781 */     return true;
/*      */   }
/*      */   
/*      */   private boolean previousWord() throws IOException {
/*  785 */     while (isDelimiter(this.buf.current()) && moveCursor(-1) != 0);
/*      */ 
/*      */ 
/*      */     
/*  789 */     while (!isDelimiter(this.buf.current()) && moveCursor(-1) != 0);
/*      */ 
/*      */ 
/*      */     
/*  793 */     return true;
/*      */   }
/*      */   
/*      */   private boolean nextWord() throws IOException {
/*  797 */     while (isDelimiter(this.buf.nextChar()) && moveCursor(1) != 0);
/*      */ 
/*      */ 
/*      */     
/*  801 */     while (!isDelimiter(this.buf.nextChar()) && moveCursor(1) != 0);
/*      */ 
/*      */ 
/*      */     
/*  805 */     return true;
/*      */   }
/*      */   
/*      */   private boolean deletePreviousWord() throws IOException {
/*  809 */     while (isDelimiter(this.buf.current()) && backspace());
/*      */ 
/*      */ 
/*      */     
/*  813 */     while (!isDelimiter(this.buf.current()) && backspace());
/*      */ 
/*      */ 
/*      */     
/*  817 */     return true;
/*      */   }
/*      */   
/*      */   private boolean deleteNextWord() throws IOException {
/*  821 */     while (isDelimiter(this.buf.nextChar()) && delete());
/*      */ 
/*      */ 
/*      */     
/*  825 */     while (!isDelimiter(this.buf.nextChar()) && delete());
/*      */ 
/*      */ 
/*      */     
/*  829 */     return true;
/*      */   }
/*      */   
/*      */   private boolean capitalizeWord() throws IOException {
/*  833 */     boolean first = true;
/*  834 */     int i = 1;
/*      */     char c;
/*  836 */     while (this.buf.cursor + i - 1 < this.buf.length() && !isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1))) {
/*  837 */       this.buf.buffer.setCharAt(this.buf.cursor + i - 1, first ? Character.toUpperCase(c) : Character.toLowerCase(c));
/*  838 */       first = false;
/*  839 */       i++;
/*      */     } 
/*  841 */     drawBuffer();
/*  842 */     moveCursor(i - 1);
/*  843 */     return true;
/*      */   }
/*      */   
/*      */   private boolean upCaseWord() throws IOException {
/*  847 */     int i = 1;
/*      */     char c;
/*  849 */     while (this.buf.cursor + i - 1 < this.buf.length() && !isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1))) {
/*  850 */       this.buf.buffer.setCharAt(this.buf.cursor + i - 1, Character.toUpperCase(c));
/*  851 */       i++;
/*      */     } 
/*  853 */     drawBuffer();
/*  854 */     moveCursor(i - 1);
/*  855 */     return true;
/*      */   }
/*      */   
/*      */   private boolean downCaseWord() throws IOException {
/*  859 */     int i = 1;
/*      */     char c;
/*  861 */     while (this.buf.cursor + i - 1 < this.buf.length() && !isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1))) {
/*  862 */       this.buf.buffer.setCharAt(this.buf.cursor + i - 1, Character.toLowerCase(c));
/*  863 */       i++;
/*      */     } 
/*  865 */     drawBuffer();
/*  866 */     moveCursor(i - 1);
/*  867 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int moveCursor(int num) throws IOException {
/*  877 */     int where = num;
/*      */     
/*  879 */     if (this.buf.cursor == 0 && where <= 0) {
/*  880 */       return 0;
/*      */     }
/*      */     
/*  883 */     if (this.buf.cursor == this.buf.buffer.length() && where >= 0) {
/*  884 */       return 0;
/*      */     }
/*      */     
/*  887 */     if (this.buf.cursor + where < 0) {
/*  888 */       where = -this.buf.cursor;
/*      */     }
/*  890 */     else if (this.buf.cursor + where > this.buf.buffer.length()) {
/*  891 */       where = this.buf.buffer.length() - this.buf.cursor;
/*      */     } 
/*      */     
/*  894 */     moveInternal(where);
/*      */     
/*  896 */     return where;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void moveInternal(int where) throws IOException {
/*      */     char c;
/*  907 */     this.buf.cursor += where;
/*      */     
/*  909 */     if (this.terminal.isAnsiSupported()) {
/*  910 */       if (where < 0) {
/*  911 */         back(Math.abs(where));
/*      */       } else {
/*  913 */         int width = getTerminal().getWidth();
/*  914 */         int cursor = getCursorPosition();
/*  915 */         int oldLine = (cursor - where) / width;
/*  916 */         int newLine = cursor / width;
/*  917 */         if (newLine > oldLine) {
/*  918 */           if (this.terminal.hasWeirdWrap())
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  923 */             if (getCurrentAnsiRow() == this.terminal.getHeight()) {
/*  924 */               printAnsiSequence((newLine - oldLine) + "S");
/*      */             }
/*      */           }
/*  927 */           printAnsiSequence((newLine - oldLine) + "B");
/*      */         } 
/*  929 */         printAnsiSequence((1 + cursor % width) + "G");
/*      */       } 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  937 */     if (where < 0) {
/*  938 */       int len = 0;
/*  939 */       for (int i = this.buf.cursor; i < this.buf.cursor - where; i++) {
/*  940 */         if (this.buf.buffer.charAt(i) == '\t') {
/*  941 */           len += 4;
/*      */         } else {
/*      */           
/*  944 */           len++;
/*      */         } 
/*      */       } 
/*      */       
/*  948 */       char[] chars = new char[len];
/*  949 */       Arrays.fill(chars, '\b');
/*  950 */       this.out.write(chars);
/*      */       
/*      */       return;
/*      */     } 
/*  954 */     if (this.buf.cursor == 0) {
/*      */       return;
/*      */     }
/*  957 */     if (this.mask != null) {
/*  958 */       c = this.mask.charValue();
/*      */     } else {
/*      */       
/*  961 */       print(this.buf.buffer.substring(this.buf.cursor - where, this.buf.cursor).toCharArray());
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  966 */     if (this.mask.charValue() == '\000') {
/*      */       return;
/*      */     }
/*      */     
/*  970 */     print(c, Math.abs(where));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean replace(int num, String replacement) {
/*  976 */     this.buf.buffer.replace(this.buf.cursor - num, this.buf.cursor, replacement);
/*      */     try {
/*  978 */       moveCursor(-num);
/*  979 */       drawBuffer(Math.max(0, num - replacement.length()));
/*  980 */       moveCursor(replacement.length());
/*      */     }
/*  982 */     catch (IOException e) {
/*  983 */       e.printStackTrace();
/*  984 */       return false;
/*      */     } 
/*  986 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int readCharacter() throws IOException {
/*  995 */     int c = this.reader.read();
/*  996 */     if (c >= 0) {
/*  997 */       Log.trace(new Object[] { "Keystroke: ", Integer.valueOf(c) });
/*      */       
/*  999 */       clearEcho(c);
/*      */     } 
/* 1001 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int clearEcho(int c) throws IOException {
/* 1009 */     if (!this.terminal.isEchoEnabled()) {
/* 1010 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 1014 */     int num = countEchoCharacters(c);
/* 1015 */     back(num);
/* 1016 */     drawBuffer(num);
/*      */     
/* 1018 */     return num;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int countEchoCharacters(int c) {
/* 1024 */     if (c == 9) {
/* 1025 */       int tabStop = 8;
/* 1026 */       int position = getCursorPosition();
/*      */       
/* 1028 */       return tabStop - position % tabStop;
/*      */     } 
/*      */     
/* 1031 */     return getPrintableCharacters(c).length();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StringBuilder getPrintableCharacters(int ch) {
/* 1041 */     StringBuilder sbuff = new StringBuilder();
/*      */     
/* 1043 */     if (ch >= 32) {
/* 1044 */       if (ch < 127) {
/* 1045 */         sbuff.append(ch);
/*      */       }
/* 1047 */       else if (ch == 127) {
/* 1048 */         sbuff.append('^');
/* 1049 */         sbuff.append('?');
/*      */       } else {
/*      */         
/* 1052 */         sbuff.append('M');
/* 1053 */         sbuff.append('-');
/*      */         
/* 1055 */         if (ch >= 160) {
/* 1056 */           if (ch < 255) {
/* 1057 */             sbuff.append((char)(ch - 128));
/*      */           } else {
/*      */             
/* 1060 */             sbuff.append('^');
/* 1061 */             sbuff.append('?');
/*      */           } 
/*      */         } else {
/*      */           
/* 1065 */           sbuff.append('^');
/* 1066 */           sbuff.append((char)(ch - 128 + 64));
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1071 */       sbuff.append('^');
/* 1072 */       sbuff.append((char)(ch + 64));
/*      */     } 
/*      */     
/* 1075 */     return sbuff;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int readCharacter(char... allowed) throws IOException {
/* 1082 */     Arrays.sort(allowed);
/*      */     char c;
/* 1084 */     while (Arrays.binarySearch(allowed, c = (char)readCharacter()) < 0);
/*      */ 
/*      */ 
/*      */     
/* 1088 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String readLine() throws IOException {
/* 1105 */     return readLine((String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String readLine(Character mask) throws IOException {
/* 1113 */     return readLine(null, mask);
/*      */   }
/*      */   
/*      */   public String readLine(String prompt) throws IOException {
/* 1117 */     return readLine(prompt, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String readLine(String prompt, Character mask) throws IOException {
/* 1133 */     this.mask = mask;
/* 1134 */     if (prompt != null) {
/* 1135 */       setPrompt(prompt);
/*      */     } else {
/*      */       
/* 1138 */       prompt = getPrompt();
/*      */     } 
/*      */ 
/*      */     
/* 1142 */     try { if (!this.terminal.isSupported()) {
/* 1143 */         beforeReadLine(prompt, mask);
/*      */       }
/*      */       
/* 1146 */       if (prompt != null && prompt.length() > 0) {
/* 1147 */         this.out.write(prompt);
/* 1148 */         this.out.flush();
/*      */       } 
/*      */ 
/*      */       
/* 1152 */       if (!this.terminal.isSupported()) {
/* 1153 */         return readLineSimple();
/*      */       }
/*      */       
/* 1156 */       String originalPrompt = this.prompt;
/*      */       
/* 1158 */       int NORMAL = 1;
/* 1159 */       int SEARCH = 2;
/* 1160 */       int state = 1;
/*      */       
/* 1162 */       boolean success = true;
/*      */       
/* 1164 */       StringBuilder sb = new StringBuilder();
/* 1165 */       List<Character> pushBackChar = new ArrayList<Character>();
/*      */       while (true)
/* 1167 */       { int c = pushBackChar.isEmpty() ? readCharacter() : ((Character)pushBackChar.remove(pushBackChar.size() - 1)).charValue();
/* 1168 */         if (c == -1) {
/* 1169 */           return null;
/*      */         }
/* 1171 */         sb.append((char)c);
/* 1172 */         if (this.recording) {
/* 1173 */           this.macro += (char)c;
/*      */         }
/*      */         
/* 1176 */         Object o = getKeys().getBound(sb);
/* 1177 */         if (o == Operation.DO_LOWERCASE_VERSION) {
/* 1178 */           sb.setLength(sb.length() - 1);
/* 1179 */           sb.append(Character.toLowerCase((char)c));
/* 1180 */           o = getKeys().getBound(sb);
/*      */         } 
/* 1182 */         if (o instanceof KeyMap) {
/*      */           continue;
/*      */         }
/* 1185 */         while (o == null && sb.length() > 0) {
/* 1186 */           c = sb.charAt(sb.length() - 1);
/* 1187 */           sb.setLength(sb.length() - 1);
/* 1188 */           Object o2 = getKeys().getBound(sb);
/* 1189 */           if (o2 instanceof KeyMap) {
/* 1190 */             o = ((KeyMap)o2).getAnotherKey();
/* 1191 */             if (o == null) {
/*      */               continue;
/*      */             }
/* 1194 */             pushBackChar.add(Character.valueOf((char)c));
/*      */           } 
/*      */         } 
/*      */         
/* 1198 */         if (o == null) {
/*      */           continue;
/*      */         }
/* 1201 */         Log.trace(new Object[] { "Binding: ", o });
/*      */ 
/*      */ 
/*      */         
/* 1205 */         if (o instanceof String) {
/* 1206 */           String macro = (String)o;
/* 1207 */           for (int i = 0; i < macro.length(); i++) {
/* 1208 */             pushBackChar.add(Character.valueOf(macro.charAt(macro.length() - 1 - i)));
/*      */           }
/* 1210 */           sb.setLength(0);
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1215 */         if (o instanceof ActionListener) {
/* 1216 */           ((ActionListener)o).actionPerformed(null);
/* 1217 */           sb.setLength(0);
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1226 */         if (state == 2) {
/* 1227 */           int cursorDest = -1;
/* 1228 */           switch ((Operation)o) {
/*      */             case ABORT:
/* 1230 */               state = 1;
/*      */               break;
/*      */             
/*      */             case REVERSE_SEARCH_HISTORY:
/* 1234 */               if (this.searchTerm.length() == 0) {
/* 1235 */                 this.searchTerm.append(this.previousSearchTerm);
/*      */               }
/*      */               
/* 1238 */               if (this.searchIndex == -1) {
/* 1239 */                 this.searchIndex = searchBackwards(this.searchTerm.toString()); break;
/*      */               } 
/* 1241 */               this.searchIndex = searchBackwards(this.searchTerm.toString(), this.searchIndex);
/*      */               break;
/*      */ 
/*      */             
/*      */             case BACKWARD_DELETE_CHAR:
/* 1246 */               if (this.searchTerm.length() > 0) {
/* 1247 */                 this.searchTerm.deleteCharAt(this.searchTerm.length() - 1);
/* 1248 */                 this.searchIndex = searchBackwards(this.searchTerm.toString());
/*      */               } 
/*      */               break;
/*      */             
/*      */             case SELF_INSERT:
/* 1253 */               this.searchTerm.appendCodePoint(c);
/* 1254 */               this.searchIndex = searchBackwards(this.searchTerm.toString());
/*      */               break;
/*      */ 
/*      */             
/*      */             default:
/* 1259 */               if (this.searchIndex != -1) {
/* 1260 */                 this.history.moveTo(this.searchIndex);
/*      */                 
/* 1262 */                 cursorDest = this.history.current().toString().indexOf(this.searchTerm.toString());
/*      */               } 
/* 1264 */               state = 1;
/*      */               break;
/*      */           } 
/*      */ 
/*      */           
/* 1269 */           if (state == 2) {
/* 1270 */             if (this.searchTerm.length() == 0) {
/* 1271 */               printSearchStatus("", "");
/* 1272 */               this.searchIndex = -1;
/*      */             }
/* 1274 */             else if (this.searchIndex == -1) {
/* 1275 */               beep();
/*      */             } else {
/* 1277 */               printSearchStatus(this.searchTerm.toString(), this.history.get(this.searchIndex).toString());
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1283 */             restoreLine(originalPrompt, cursorDest);
/*      */           } 
/*      */         } 
/* 1286 */         if (state == 1 && 
/* 1287 */           o instanceof Operation)
/* 1288 */         { String str; int i; switch ((Operation)o)
/*      */           { case COMPLETE:
/* 1290 */               success = complete();
/*      */               break;
/*      */             
/*      */             case POSSIBLE_COMPLETIONS:
/* 1294 */               printCompletionCandidates();
/* 1295 */               success = true;
/*      */               break;
/*      */             
/*      */             case BEGINNING_OF_LINE:
/* 1299 */               success = setCursorPosition(0);
/*      */               break;
/*      */             
/*      */             case KILL_LINE:
/* 1303 */               success = killLine();
/*      */               break;
/*      */             
/*      */             case KILL_WHOLE_LINE:
/* 1307 */               success = (setCursorPosition(0) && killLine());
/*      */               break;
/*      */             
/*      */             case CLEAR_SCREEN:
/* 1311 */               success = clearScreen();
/*      */               break;
/*      */             
/*      */             case OVERWRITE_MODE:
/* 1315 */               this.buf.setOverTyping(!this.buf.isOverTyping());
/*      */               break;
/*      */             
/*      */             case SELF_INSERT:
/* 1319 */               putString(sb);
/* 1320 */               success = true;
/*      */               break;
/*      */             
/*      */             case ACCEPT_LINE:
/* 1324 */               moveToEnd();
/* 1325 */               println();
/* 1326 */               flush();
/* 1327 */               str = finishBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1480 */               return str;case BACKWARD_WORD: success = previousWord(); break;case FORWARD_WORD: success = nextWord(); break;case PREVIOUS_HISTORY: success = moveHistory(false); break;case NEXT_HISTORY: success = moveHistory(true); break;case BACKWARD_DELETE_CHAR: success = backspace(); break;case EXIT_OR_DELETE_CHAR: if (this.buf.buffer.length() == 0) { str = null; return str; }  success = deleteCurrentCharacter(); break;case DELETE_CHAR: success = deleteCurrentCharacter(); break;case BACKWARD_CHAR: success = (moveCursor(-1) != 0); break;case FORWARD_CHAR: success = (moveCursor(1) != 0); break;case UNIX_LINE_DISCARD: success = resetLine(); break;case UNIX_WORD_RUBOUT: case BACKWARD_KILL_WORD: success = deletePreviousWord(); break;case KILL_WORD: success = deleteNextWord(); break;case BEGINNING_OF_HISTORY: success = this.history.moveToFirst(); if (success) setBuffer(this.history.current());  break;case END_OF_HISTORY: success = this.history.moveToLast(); if (success) setBuffer(this.history.current());  break;case REVERSE_SEARCH_HISTORY: if (this.searchTerm != null) this.previousSearchTerm = this.searchTerm.toString();  this.searchTerm = new StringBuffer(this.buf.buffer); state = 2; if (this.searchTerm.length() > 0) { this.searchIndex = searchBackwards(this.searchTerm.toString()); if (this.searchIndex == -1) beep();  printSearchStatus(this.searchTerm.toString(), (this.searchIndex > -1) ? this.history.get(this.searchIndex).toString() : ""); break; }  this.searchIndex = -1; printSearchStatus("", ""); break;case CAPITALIZE_WORD: success = capitalizeWord(); break;case UPCASE_WORD: success = upCaseWord(); break;case DOWNCASE_WORD: success = downCaseWord(); break;case END_OF_LINE: success = moveToEnd(); break;case TAB_INSERT: putString("\t"); success = true; break;case RE_READ_INIT_FILE: this.consoleKeys.loadKeys(this.appName, this.inputrcUrl); success = true; break;case START_KBD_MACRO: this.recording = true; break;case END_KBD_MACRO: this.recording = false; this.macro = this.macro.substring(0, this.macro.length() - sb.length()); break;case CALL_LAST_KBD_MACRO: for (i = 0; i < this.macro.length(); i++) pushBackChar.add(Character.valueOf(this.macro.charAt(this.macro.length() - 1 - i)));  sb.setLength(0); break;case VI_EDITING_MODE: this.consoleKeys.setViEditMode(true); this.consoleKeys.setKeys(this.consoleKeys.getKeyMaps().get("vi")); break;case EMACS_EDITING_MODE: this.consoleKeys.setViEditMode(false); this.consoleKeys.setKeys(this.consoleKeys.getKeyMaps().get("emacs")); break;default: i = 0; break; }  }  if (!success) beep();  sb.setLength(0); flush(); }  } finally { if (!this.terminal.isSupported()) afterReadLine();
/*      */        }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readLineSimple() throws IOException {
/* 1489 */     StringBuilder buff = new StringBuilder();
/*      */     
/* 1491 */     if (this.skipLF) {
/* 1492 */       this.skipLF = false;
/*      */       
/* 1494 */       int i = readCharacter();
/*      */       
/* 1496 */       if (i == -1 || i == 13)
/* 1497 */         return buff.toString(); 
/* 1498 */       if (i != 10)
/*      */       {
/*      */         
/* 1501 */         buff.append((char)i);
/*      */       }
/*      */     } 
/*      */     
/*      */     while (true) {
/* 1506 */       int i = readCharacter();
/*      */       
/* 1508 */       if (i == -1 || i == 10)
/* 1509 */         return buff.toString(); 
/* 1510 */       if (i == 13) {
/* 1511 */         this.skipLF = true;
/* 1512 */         return buff.toString();
/*      */       } 
/* 1514 */       buff.append((char)i);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConsoleReader(String appName, InputStream in, OutputStream out, Terminal term) throws IOException {
/* 1523 */     this.completers = new LinkedList<Completer>();
/*      */     
/* 1525 */     this.completionHandler = (CompletionHandler)new CandidateListCompletionHandler();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1612 */     this.autoprintThreshold = Integer.getInteger("org.bukkit.craftbukkit.libs.jline.completion.threshold", 100).intValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1648 */     this.history = (History)new MemoryHistory();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1658 */     this.historyEnabled = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1952 */     this.triggeredActions = new HashMap<Character, ActionListener>(); this.appName = (appName != null) ? appName : "JLine"; this.encoding = (this.encoding != null) ? this.encoding : Configuration.getEncoding(); this.terminal = (term != null) ? term : TerminalFactory.get(); this.out = new OutputStreamWriter(this.terminal.wrapOutIfNeeded(out), this.encoding); setInput(in); this.inputrcUrl = Configuration.getUrlFrom(Configuration.getString("org.bukkit.craftbukkit.libs.jline.inputrc", Configuration.getUrlFrom(new File(Configuration.getUserHome(), ".inputrc")).toExternalForm())); this.consoleKeys = new ConsoleKeys(appName, this.inputrcUrl);
/*      */   } public boolean addCompleter(Completer completer) { return this.completers.add(completer); } public boolean removeCompleter(Completer completer) { return this.completers.remove(completer); } public Collection<Completer> getCompleters() { return Collections.unmodifiableList(this.completers); } public void setCompletionHandler(CompletionHandler handler) { assert handler != null; this.completionHandler = handler; } public CompletionHandler getCompletionHandler() { return this.completionHandler; } protected boolean complete() throws IOException { Completer comp; if (this.completers.size() == 0) return false;  List<CharSequence> candidates = new LinkedList<CharSequence>(); String bufstr = this.buf.buffer.toString(); int cursor = this.buf.cursor; int position = -1; Iterator<Completer> i$ = this.completers.iterator(); do { comp = i$.next(); } while (i$.hasNext() && (position = comp.complete(bufstr, cursor, candidates)) == -1); return (candidates.size() != 0 && getCompletionHandler().complete(this, candidates, position)); } protected void printCompletionCandidates() throws IOException { if (this.completers.size() == 0) return;  List<CharSequence> candidates = new LinkedList<CharSequence>(); String bufstr = this.buf.buffer.toString(); int cursor = this.buf.cursor; for (Completer comp : this.completers) { if (comp.complete(bufstr, cursor, candidates) != -1) break;  }  CandidateListCompletionHandler.printCandidates(this, candidates); drawLine(); } public void setAutoprintThreshold(int threshold) { this.autoprintThreshold = threshold; }
/*      */   public int getAutoprintThreshold() { return this.autoprintThreshold; }
/*      */   public void setPaginationEnabled(boolean enabled) { this.paginationEnabled = enabled; }
/*      */   public boolean isPaginationEnabled() { return this.paginationEnabled; }
/*      */   public void setHistory(History history) { this.history = history; }
/*      */   public History getHistory() { return this.history; }
/*      */   public void setHistoryEnabled(boolean enabled) { this.historyEnabled = enabled; }
/*      */   public boolean isHistoryEnabled() { return this.historyEnabled; }
/* 1961 */   public void addTriggeredAction(char c, ActionListener listener) { this.triggeredActions.put(Character.valueOf(c), listener); }
/*      */   private boolean moveHistory(boolean next) throws IOException { if (next && !this.history.next())
/*      */       return false;  if (!next && !this.history.previous())
/*      */       return false;  setBuffer(this.history.current()); return true; }
/*      */   public static final String CR = System.getProperty("line.separator");
/*      */   private final Map<Character, ActionListener> triggeredActions;
/*      */   private Thread maskThread;
/*      */   private void print(int c) throws IOException { if (c == 9) { char[] chars = new char[4]; Arrays.fill(chars, ' '); this.out.write(chars); return; }  this.out.write(c); } private void print(char... buff) throws IOException { char[] chars; int len = 0; for (char c : buff) { if (c == '\t') { len += 4; } else { len++; }  }  if (len == buff.length) { chars = buff; } else { chars = new char[len]; int pos = 0; for (char c : buff) { if (c == '\t') { Arrays.fill(chars, pos, pos + 4, ' '); pos += 4; } else { chars[pos] = c; pos++; }  }
/*      */        }
/*      */      this.out.write(chars); } private void print(char c, int num) throws IOException { if (num == 1) { print(c); }
/*      */     else { char[] chars = new char[num]; Arrays.fill(chars, c); print(chars); }
/* 1972 */      } public final void print(CharSequence s) throws IOException { assert s != null; print(s.toString().toCharArray()); } public void printColumns(Collection<? extends CharSequence> items) throws IOException { int showLines; if (items == null || items.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 1976 */     int width = getTerminal().getWidth();
/* 1977 */     int height = getTerminal().getHeight();
/*      */     
/* 1979 */     int maxWidth = 0;
/* 1980 */     for (CharSequence item : items) {
/* 1981 */       maxWidth = Math.max(maxWidth, item.length());
/*      */     }
/* 1983 */     maxWidth += 3;
/* 1984 */     Log.debug(new Object[] { "Max width: ", Integer.valueOf(maxWidth) });
/*      */ 
/*      */     
/* 1987 */     if (isPaginationEnabled()) {
/* 1988 */       showLines = height - 1;
/*      */     } else {
/*      */       
/* 1991 */       showLines = Integer.MAX_VALUE;
/*      */     } 
/*      */     
/* 1994 */     StringBuilder buff = new StringBuilder();
/* 1995 */     for (CharSequence item : items) {
/* 1996 */       if (buff.length() + maxWidth > width) {
/* 1997 */         println(buff);
/* 1998 */         buff.setLength(0);
/*      */         
/* 2000 */         if (--showLines == 0) {
/*      */           
/* 2002 */           print(resources.getString("DISPLAY_MORE"));
/* 2003 */           flush();
/* 2004 */           int c = readCharacter();
/* 2005 */           if (c == 13 || c == 10) {
/*      */             
/* 2007 */             showLines = 1;
/*      */           }
/* 2009 */           else if (c != 113) {
/*      */             
/* 2011 */             showLines = height - 1;
/*      */           } 
/*      */           
/* 2014 */           back(resources.getString("DISPLAY_MORE").length());
/* 2015 */           if (c == 113) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2023 */       buff.append(item.toString());
/* 2024 */       for (int i = 0; i < maxWidth - item.length(); i++) {
/* 2025 */         buff.append(' ');
/*      */       }
/*      */     } 
/*      */     
/* 2029 */     if (buff.length() > 0)
/* 2030 */       println(buff);  }
/*      */   public final void println(CharSequence s) throws IOException { assert s != null; print(s.toString().toCharArray()); println(); }
/*      */   public final void println() throws IOException { print(CR); }
/*      */   public final boolean delete() throws IOException { return (delete(1) == 1); }
/*      */   private int delete(int num) throws IOException { this.buf.buffer.delete(this.buf.cursor, this.buf.cursor + 1); drawBuffer(1); return 1; }
/*      */   public boolean killLine() throws IOException { int cp = this.buf.cursor; int len = this.buf.buffer.length(); if (cp >= len) return false;  int num = this.buf.buffer.length() - cp; clearAhead(num, 0); for (int i = 0; i < num; i++) this.buf.buffer.deleteCharAt(len - i - 1);  return true; }
/*      */   public boolean clearScreen() throws IOException { if (!this.terminal.isAnsiSupported()) return false;  printAnsiSequence("2J"); printAnsiSequence("1;1H"); redrawLine(); return true; }
/*      */   public void beep() throws IOException { if (!Configuration.getBoolean("org.bukkit.craftbukkit.libs.jline.nobell", true)) { print(7); flush(); }  }
/*      */   public boolean paste() throws IOException { Clipboard clipboard; try { clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); } catch (Exception e) { return false; }  if (clipboard == null) return false;  Transferable transferable = clipboard.getContents(null); if (transferable == null) return false;  try { String value; Object content = transferable.getTransferData(DataFlavor.plainTextFlavor); if (content == null) try { content = (new DataFlavor()).getReaderForText(transferable); } catch (Exception e) {}  if (content == null)
/*      */         return false;  if (content instanceof Reader) { value = ""; BufferedReader read = new BufferedReader((Reader)content); String line; while ((line = read.readLine()) != null) { if (value.length() > 0)
/*      */             value = value + "\n";  value = value + line; }  } else { value = content.toString(); }  if (value == null)
/* 2041 */         return true;  putString(value); return true; } catch (UnsupportedFlavorException e) { Log.error(new Object[] { "Paste failed: ", e }); return false; }  } private void beforeReadLine(String prompt, Character mask) { if (mask != null && this.maskThread == null) {
/* 2042 */       final String fullPrompt = "\r" + prompt + "                 " + "                 " + "                 " + "\r" + prompt;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2048 */       this.maskThread = new Thread()
/*      */         {
/*      */           public void run() {
/* 2051 */             while (!interrupted()) {
/*      */               try {
/* 2053 */                 Writer out = ConsoleReader.this.getOutput();
/* 2054 */                 out.write(fullPrompt);
/* 2055 */                 out.flush();
/* 2056 */                 sleep(3L);
/*      */               }
/* 2058 */               catch (IOException e) {
/*      */                 
/*      */                 return;
/* 2061 */               } catch (InterruptedException e) {
/*      */                 return;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         };
/*      */       
/* 2068 */       this.maskThread.setPriority(10);
/* 2069 */       this.maskThread.setDaemon(true);
/* 2070 */       this.maskThread.start();
/*      */     }  }
/*      */ 
/*      */   
/*      */   private void afterReadLine() {
/* 2075 */     if (this.maskThread != null && this.maskThread.isAlive()) {
/* 2076 */       this.maskThread.interrupt();
/*      */     }
/*      */     
/* 2079 */     this.maskThread = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetPromptLine(String prompt, String buffer, int cursorDest) throws IOException {
/* 2095 */     moveToEnd();
/*      */ 
/*      */     
/* 2098 */     this.buf.buffer.append(this.prompt);
/* 2099 */     this.buf.cursor += this.prompt.length();
/* 2100 */     this.prompt = "";
/* 2101 */     backspaceAll();
/*      */     
/* 2103 */     this.prompt = prompt;
/* 2104 */     redrawLine();
/* 2105 */     setBuffer(buffer);
/*      */ 
/*      */     
/* 2108 */     if (cursorDest < 0) cursorDest = buffer.length(); 
/* 2109 */     setCursorPosition(cursorDest);
/*      */     
/* 2111 */     flush();
/*      */   }
/*      */   
/*      */   public void printSearchStatus(String searchTerm, String match) throws IOException {
/* 2115 */     String prompt = "(reverse-i-search)`" + searchTerm + "': ";
/* 2116 */     String buffer = match;
/* 2117 */     int cursorDest = match.indexOf(searchTerm);
/* 2118 */     resetPromptLine(prompt, buffer, cursorDest);
/*      */   }
/*      */ 
/*      */   
/*      */   public void restoreLine(String originalPrompt, int cursorDest) throws IOException {
/* 2123 */     String prompt = lastLine(originalPrompt);
/* 2124 */     String buffer = this.buf.buffer.toString();
/* 2125 */     resetPromptLine(prompt, buffer, cursorDest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int searchBackwards(String searchTerm, int startIndex) {
/* 2139 */     return searchBackwards(searchTerm, startIndex, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int searchBackwards(String searchTerm) {
/* 2149 */     return searchBackwards(searchTerm, this.history.index());
/*      */   }
/*      */ 
/*      */   
/*      */   public int searchBackwards(String searchTerm, int startIndex, boolean startsWith) {
/* 2154 */     ListIterator<History.Entry> it = this.history.entries(startIndex);
/* 2155 */     while (it.hasPrevious()) {
/* 2156 */       History.Entry e = it.previous();
/* 2157 */       if (startsWith) {
/* 2158 */         if (e.value().toString().startsWith(searchTerm))
/* 2159 */           return e.index(); 
/*      */         continue;
/*      */       } 
/* 2162 */       if (e.value().toString().contains(searchTerm)) {
/* 2163 */         return e.index();
/*      */       }
/*      */     } 
/*      */     
/* 2167 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isDelimiter(char c) {
/* 2182 */     return !Character.isLetterOrDigit(c);
/*      */   }
/*      */   
/*      */   private void printAnsiSequence(String sequence) throws IOException {
/* 2186 */     print(27);
/* 2187 */     print(91);
/* 2188 */     print(sequence);
/* 2189 */     flush();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int getCurrentPosition() {
/* 2195 */     if (this.terminal.isAnsiSupported() && !(this.in instanceof java.io.ByteArrayInputStream)) {
/*      */       try {
/* 2197 */         printAnsiSequence("6n");
/* 2198 */         flush();
/* 2199 */         StringBuffer b = new StringBuffer(8);
/*      */         
/*      */         int r;
/* 2202 */         while ((r = this.in.read()) > -1 && r != 82) {
/* 2203 */           if (r != 27 && r != 91) {
/* 2204 */             b.append((char)r);
/*      */           }
/*      */         } 
/* 2207 */         String[] pos = b.toString().split(";");
/* 2208 */         return Integer.parseInt(pos[1]);
/* 2209 */       } catch (Exception x) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2214 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getCurrentAnsiRow() {
/* 2222 */     if (this.terminal.isAnsiSupported() && !(this.in instanceof java.io.ByteArrayInputStream)) {
/*      */       try {
/* 2224 */         printAnsiSequence("6n");
/* 2225 */         flush();
/* 2226 */         StringBuffer b = new StringBuffer(8);
/*      */         
/*      */         int r;
/* 2229 */         while ((r = this.in.read()) > -1 && r != 82) {
/* 2230 */           if (r != 27 && r != 91) {
/* 2231 */             b.append((char)r);
/*      */           }
/*      */         } 
/* 2234 */         String[] pos = b.toString().split(";");
/* 2235 */         return Integer.parseInt(pos[0]);
/* 2236 */       } catch (Exception x) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2241 */     return -1;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\ConsoleReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */