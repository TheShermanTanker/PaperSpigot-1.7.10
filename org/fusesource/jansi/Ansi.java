/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ansi
/*     */ {
/*     */   private static final char FIRST_ESC_CHAR = '\033';
/*     */   private static final char SECOND_ESC_CHAR = '[';
/*     */   
/*     */   public enum Color
/*     */   {
/*  34 */     BLACK(0, "BLACK"),
/*  35 */     RED(1, "RED"),
/*  36 */     GREEN(2, "GREEN"),
/*  37 */     YELLOW(3, "YELLOW"),
/*  38 */     BLUE(4, "BLUE"),
/*  39 */     MAGENTA(5, "MAGENTA"),
/*  40 */     CYAN(6, "CYAN"),
/*  41 */     WHITE(7, "WHITE"),
/*  42 */     DEFAULT(9, "DEFAULT");
/*     */     
/*     */     private final int value;
/*     */     private final String name;
/*     */     
/*     */     Color(int index, String name) {
/*  48 */       this.value = index;
/*  49 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  54 */       return this.name;
/*     */     }
/*     */     
/*     */     public int value() {
/*  58 */       return this.value;
/*     */     }
/*     */     
/*     */     public int fg() {
/*  62 */       return this.value + 30;
/*     */     }
/*     */     
/*     */     public int bg() {
/*  66 */       return this.value + 40;
/*     */     }
/*     */     
/*     */     public int fgBright() {
/*  70 */       return this.value + 90;
/*     */     }
/*     */     
/*     */     public int bgBright() {
/*  74 */       return this.value + 100;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Attribute {
/*  79 */     RESET(0, "RESET"),
/*  80 */     INTENSITY_BOLD(1, "INTENSITY_BOLD"),
/*  81 */     INTENSITY_FAINT(2, "INTENSITY_FAINT"),
/*  82 */     ITALIC(3, "ITALIC_ON"),
/*  83 */     UNDERLINE(4, "UNDERLINE_ON"),
/*  84 */     BLINK_SLOW(5, "BLINK_SLOW"),
/*  85 */     BLINK_FAST(6, "BLINK_FAST"),
/*  86 */     NEGATIVE_ON(7, "NEGATIVE_ON"),
/*  87 */     CONCEAL_ON(8, "CONCEAL_ON"),
/*  88 */     STRIKETHROUGH_ON(9, "STRIKETHROUGH_ON"),
/*  89 */     UNDERLINE_DOUBLE(21, "UNDERLINE_DOUBLE"),
/*  90 */     INTENSITY_BOLD_OFF(22, "INTENSITY_BOLD_OFF"),
/*  91 */     ITALIC_OFF(23, "ITALIC_OFF"),
/*  92 */     UNDERLINE_OFF(24, "UNDERLINE_OFF"),
/*  93 */     BLINK_OFF(25, "BLINK_OFF"),
/*  94 */     NEGATIVE_OFF(27, "NEGATIVE_OFF"),
/*  95 */     CONCEAL_OFF(28, "CONCEAL_OFF"),
/*  96 */     STRIKETHROUGH_OFF(29, "STRIKETHROUGH_OFF");
/*     */     
/*     */     private final int value;
/*     */     private final String name;
/*     */     
/*     */     Attribute(int index, String name) {
/* 102 */       this.value = index;
/* 103 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 108 */       return this.name;
/*     */     }
/*     */     
/*     */     public int value() {
/* 112 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Erase
/*     */   {
/* 118 */     FORWARD(0, "FORWARD"),
/* 119 */     BACKWARD(1, "BACKWARD"),
/* 120 */     ALL(2, "ALL");
/*     */     
/*     */     private final int value;
/*     */     private final String name;
/*     */     
/*     */     Erase(int index, String name) {
/* 126 */       this.value = index;
/* 127 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 132 */       return this.name;
/*     */     }
/*     */     
/*     */     public int value() {
/* 136 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/* 140 */   public static final String DISABLE = Ansi.class.getName() + ".disable";
/*     */   
/* 142 */   private static Callable<Boolean> detector = new Callable<Boolean>() {
/*     */       public Boolean call() throws Exception {
/* 144 */         return Boolean.valueOf(!Boolean.getBoolean(Ansi.DISABLE));
/*     */       }
/*     */     };
/*     */   
/*     */   public static void setDetector(Callable<Boolean> detector) {
/* 149 */     if (detector == null) throw new IllegalArgumentException(); 
/* 150 */     Ansi.detector = detector;
/*     */   }
/*     */   
/*     */   public static boolean isDetected() {
/*     */     try {
/* 155 */       return ((Boolean)detector.call()).booleanValue();
/*     */     }
/* 157 */     catch (Exception e) {
/* 158 */       return true;
/*     */     } 
/*     */   }
/*     */   
/* 162 */   private static final InheritableThreadLocal<Boolean> holder = new InheritableThreadLocal<Boolean>()
/*     */     {
/*     */       protected Boolean initialValue()
/*     */       {
/* 166 */         return Boolean.valueOf(Ansi.isDetected());
/*     */       }
/*     */     };
/*     */   
/*     */   public static void setEnabled(boolean flag) {
/* 171 */     holder.set(Boolean.valueOf(flag));
/*     */   }
/*     */   private final StringBuilder builder;
/*     */   public static boolean isEnabled() {
/* 175 */     return ((Boolean)holder.get()).booleanValue();
/*     */   }
/*     */   
/*     */   public static Ansi ansi() {
/* 179 */     if (isEnabled()) {
/* 180 */       return new Ansi();
/*     */     }
/*     */     
/* 183 */     return new NoAnsi();
/*     */   }
/*     */   
/*     */   private static class NoAnsi
/*     */     extends Ansi
/*     */   {
/*     */     private NoAnsi() {}
/*     */     
/*     */     public Ansi fg(Ansi.Color color) {
/* 192 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi bg(Ansi.Color color) {
/* 197 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi a(Ansi.Attribute attribute) {
/* 202 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursor(int x, int y) {
/* 207 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorUp(int y) {
/* 212 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorRight(int x) {
/* 217 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorDown(int y) {
/* 222 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi cursorLeft(int x) {
/* 227 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi eraseScreen() {
/* 232 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi eraseScreen(Ansi.Erase kind) {
/* 237 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi eraseLine() {
/* 242 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi eraseLine(Ansi.Erase kind) {
/* 247 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi scrollUp(int rows) {
/* 252 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi scrollDown(int rows) {
/* 257 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi saveCursorPosition() {
/* 262 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi restorCursorPosition() {
/* 267 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ansi reset() {
/* 272 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 277 */   private final ArrayList<Integer> attributeOptions = new ArrayList<Integer>(5);
/*     */   
/*     */   public Ansi() {
/* 280 */     this(new StringBuilder());
/*     */   }
/*     */   
/*     */   public Ansi(Ansi parent) {
/* 284 */     this(new StringBuilder(parent.builder));
/* 285 */     this.attributeOptions.addAll(parent.attributeOptions);
/*     */   }
/*     */   
/*     */   public Ansi(int size) {
/* 289 */     this(new StringBuilder(size));
/*     */   }
/*     */   
/*     */   public Ansi(StringBuilder builder) {
/* 293 */     this.builder = builder;
/*     */   }
/*     */   
/*     */   public static Ansi ansi(StringBuilder builder) {
/* 297 */     return new Ansi(builder);
/*     */   }
/*     */   public static Ansi ansi(int size) {
/* 300 */     return new Ansi(size);
/*     */   }
/*     */   
/*     */   public Ansi fg(Color color) {
/* 304 */     this.attributeOptions.add(Integer.valueOf(color.fg()));
/* 305 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi bg(Color color) {
/* 309 */     this.attributeOptions.add(Integer.valueOf(color.bg()));
/* 310 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(Attribute attribute) {
/* 314 */     this.attributeOptions.add(Integer.valueOf(attribute.value()));
/* 315 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi cursor(int x, int y) {
/* 319 */     return appendEscapeSequence('H', new Object[] { Integer.valueOf(x), Integer.valueOf(y) });
/*     */   }
/*     */   
/*     */   public Ansi cursorUp(int y) {
/* 323 */     return appendEscapeSequence('A', y);
/*     */   }
/*     */   
/*     */   public Ansi cursorDown(int y) {
/* 327 */     return appendEscapeSequence('B', y);
/*     */   }
/*     */   
/*     */   public Ansi cursorRight(int x) {
/* 331 */     return appendEscapeSequence('C', x);
/*     */   }
/*     */   
/*     */   public Ansi cursorLeft(int x) {
/* 335 */     return appendEscapeSequence('D', x);
/*     */   }
/*     */   
/*     */   public Ansi eraseScreen() {
/* 339 */     return appendEscapeSequence('J', Erase.ALL.value());
/*     */   }
/*     */   
/*     */   public Ansi eraseScreen(Erase kind) {
/* 343 */     return appendEscapeSequence('J', kind.value());
/*     */   }
/*     */   
/*     */   public Ansi eraseLine() {
/* 347 */     return appendEscapeSequence('K');
/*     */   }
/*     */   
/*     */   public Ansi eraseLine(Erase kind) {
/* 351 */     return appendEscapeSequence('K', kind.value());
/*     */   }
/*     */   
/*     */   public Ansi scrollUp(int rows) {
/* 355 */     return appendEscapeSequence('S', rows);
/*     */   }
/*     */   
/*     */   public Ansi scrollDown(int rows) {
/* 359 */     return appendEscapeSequence('T', rows);
/*     */   }
/*     */   
/*     */   public Ansi saveCursorPosition() {
/* 363 */     return appendEscapeSequence('s');
/*     */   }
/*     */   
/*     */   public Ansi restorCursorPosition() {
/* 367 */     return appendEscapeSequence('u');
/*     */   }
/*     */   
/*     */   public Ansi reset() {
/* 371 */     return a(Attribute.RESET);
/*     */   }
/*     */   
/*     */   public Ansi bold() {
/* 375 */     return a(Attribute.INTENSITY_BOLD);
/*     */   }
/*     */   
/*     */   public Ansi boldOff() {
/* 379 */     return a(Attribute.INTENSITY_BOLD_OFF);
/*     */   }
/*     */   
/*     */   public Ansi a(String value) {
/* 383 */     flushAtttributes();
/* 384 */     this.builder.append(value);
/* 385 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(boolean value) {
/* 389 */     flushAtttributes();
/* 390 */     this.builder.append(value);
/* 391 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(char value) {
/* 395 */     flushAtttributes();
/* 396 */     this.builder.append(value);
/* 397 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(char[] value, int offset, int len) {
/* 401 */     flushAtttributes();
/* 402 */     this.builder.append(value, offset, len);
/* 403 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(char[] value) {
/* 407 */     flushAtttributes();
/* 408 */     this.builder.append(value);
/* 409 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(CharSequence value, int start, int end) {
/* 413 */     flushAtttributes();
/* 414 */     this.builder.append(value, start, end);
/* 415 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(CharSequence value) {
/* 419 */     flushAtttributes();
/* 420 */     this.builder.append(value);
/* 421 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(double value) {
/* 425 */     flushAtttributes();
/* 426 */     this.builder.append(value);
/* 427 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(float value) {
/* 431 */     flushAtttributes();
/* 432 */     this.builder.append(value);
/* 433 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(int value) {
/* 437 */     flushAtttributes();
/* 438 */     this.builder.append(value);
/* 439 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(long value) {
/* 443 */     flushAtttributes();
/* 444 */     this.builder.append(value);
/* 445 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(Object value) {
/* 449 */     flushAtttributes();
/* 450 */     this.builder.append(value);
/* 451 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi a(StringBuffer value) {
/* 455 */     flushAtttributes();
/* 456 */     this.builder.append(value);
/* 457 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi newline() {
/* 461 */     flushAtttributes();
/* 462 */     this.builder.append(System.getProperty("line.separator"));
/* 463 */     return this;
/*     */   }
/*     */   
/*     */   public Ansi format(String pattern, Object... args) {
/* 467 */     flushAtttributes();
/* 468 */     this.builder.append(String.format(pattern, args));
/* 469 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ansi render(String text) {
/* 480 */     a(AnsiRenderer.render(text));
/* 481 */     return this;
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
/*     */   public Ansi render(String text, Object... args) {
/* 493 */     a(String.format(AnsiRenderer.render(text), args));
/* 494 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 499 */     flushAtttributes();
/* 500 */     return this.builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Ansi appendEscapeSequence(char command) {
/* 508 */     flushAtttributes();
/* 509 */     this.builder.append('\033');
/* 510 */     this.builder.append('[');
/* 511 */     this.builder.append(command);
/* 512 */     return this;
/*     */   }
/*     */   
/*     */   private Ansi appendEscapeSequence(char command, int option) {
/* 516 */     flushAtttributes();
/* 517 */     this.builder.append('\033');
/* 518 */     this.builder.append('[');
/* 519 */     this.builder.append(option);
/* 520 */     this.builder.append(command);
/* 521 */     return this;
/*     */   }
/*     */   
/*     */   private Ansi appendEscapeSequence(char command, Object... options) {
/* 525 */     flushAtttributes();
/* 526 */     return _appendEscapeSequence(command, options);
/*     */   }
/*     */   
/*     */   private void flushAtttributes() {
/* 530 */     if (this.attributeOptions.isEmpty())
/*     */       return; 
/* 532 */     if (this.attributeOptions.size() == 1 && ((Integer)this.attributeOptions.get(0)).intValue() == 0) {
/* 533 */       this.builder.append('\033');
/* 534 */       this.builder.append('[');
/* 535 */       this.builder.append('m');
/*     */     } else {
/* 537 */       _appendEscapeSequence('m', this.attributeOptions.toArray());
/*     */     } 
/* 539 */     this.attributeOptions.clear();
/*     */   }
/*     */   
/*     */   private Ansi _appendEscapeSequence(char command, Object... options) {
/* 543 */     this.builder.append('\033');
/* 544 */     this.builder.append('[');
/* 545 */     int size = options.length;
/* 546 */     for (int i = 0; i < size; i++) {
/* 547 */       if (i != 0) {
/* 548 */         this.builder.append(';');
/*     */       }
/* 550 */       if (options[i] != null) {
/* 551 */         this.builder.append(options[i]);
/*     */       }
/*     */     } 
/* 554 */     this.builder.append(command);
/* 555 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\Ansi.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */