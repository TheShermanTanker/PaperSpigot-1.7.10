/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.fusesource.jansi.internal.Kernel32;
/*     */ import org.fusesource.jansi.internal.WindowsSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WindowsAnsiOutputStream
/*     */   extends AnsiOutputStream
/*     */ {
/*  52 */   private static final long console = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
/*     */   
/*     */   private static final short FOREGROUND_BLACK = 0;
/*  55 */   private static final short FOREGROUND_YELLOW = (short)(Kernel32.FOREGROUND_RED | Kernel32.FOREGROUND_GREEN);
/*  56 */   private static final short FOREGROUND_MAGENTA = (short)(Kernel32.FOREGROUND_BLUE | Kernel32.FOREGROUND_RED);
/*  57 */   private static final short FOREGROUND_CYAN = (short)(Kernel32.FOREGROUND_BLUE | Kernel32.FOREGROUND_GREEN);
/*  58 */   private static final short FOREGROUND_WHITE = (short)(Kernel32.FOREGROUND_RED | Kernel32.FOREGROUND_GREEN | Kernel32.FOREGROUND_BLUE);
/*     */   
/*     */   private static final short BACKGROUND_BLACK = 0;
/*  61 */   private static final short BACKGROUND_YELLOW = (short)(Kernel32.BACKGROUND_RED | Kernel32.BACKGROUND_GREEN);
/*  62 */   private static final short BACKGROUND_MAGENTA = (short)(Kernel32.BACKGROUND_BLUE | Kernel32.BACKGROUND_RED);
/*  63 */   private static final short BACKGROUND_CYAN = (short)(Kernel32.BACKGROUND_BLUE | Kernel32.BACKGROUND_GREEN);
/*  64 */   private static final short BACKGROUND_WHITE = (short)(Kernel32.BACKGROUND_RED | Kernel32.BACKGROUND_GREEN | Kernel32.BACKGROUND_BLUE);
/*     */   
/*  66 */   private static final short[] ANSI_FOREGROUND_COLOR_MAP = new short[] { 0, Kernel32.FOREGROUND_RED, Kernel32.FOREGROUND_GREEN, FOREGROUND_YELLOW, Kernel32.FOREGROUND_BLUE, FOREGROUND_MAGENTA, FOREGROUND_CYAN, FOREGROUND_WHITE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final short[] ANSI_BACKGROUND_COLOR_MAP = new short[] { 0, Kernel32.BACKGROUND_RED, Kernel32.BACKGROUND_GREEN, BACKGROUND_YELLOW, Kernel32.BACKGROUND_BLUE, BACKGROUND_MAGENTA, BACKGROUND_CYAN, BACKGROUND_WHITE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
/*     */   
/*     */   private final short originalColors;
/*     */   private boolean negative;
/*  92 */   private short savedX = -1;
/*  93 */   private short savedY = -1;
/*     */   
/*     */   public WindowsAnsiOutputStream(OutputStream os) throws IOException {
/*  96 */     super(os);
/*  97 */     getConsoleInfo();
/*  98 */     this.originalColors = this.info.attributes;
/*     */   }
/*     */   
/*     */   private void getConsoleInfo() throws IOException {
/* 102 */     this.out.flush();
/* 103 */     if (Kernel32.GetConsoleScreenBufferInfo(console, this.info) == 0) {
/* 104 */       throw new IOException("Could not get the screen info: " + WindowsSupport.getLastErrorMessage());
/*     */     }
/* 106 */     if (this.negative) {
/* 107 */       this.info.attributes = invertAttributeColors(this.info.attributes);
/*     */     }
/*     */   }
/*     */   
/*     */   private void applyAttribute() throws IOException {
/* 112 */     this.out.flush();
/* 113 */     short attributes = this.info.attributes;
/* 114 */     if (this.negative) {
/* 115 */       attributes = invertAttributeColors(attributes);
/*     */     }
/* 117 */     if (Kernel32.SetConsoleTextAttribute(console, attributes) == 0) {
/* 118 */       throw new IOException(WindowsSupport.getLastErrorMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private short invertAttributeColors(short attibutes) {
/* 124 */     int fg = 0xF & attibutes;
/* 125 */     fg <<= 8;
/* 126 */     int bg = 240 * attibutes;
/* 127 */     bg >>= 8;
/* 128 */     attibutes = (short)(attibutes & 0xFF00 | fg | bg);
/* 129 */     return attibutes;
/*     */   }
/*     */   
/*     */   private void applyCursorPosition() throws IOException {
/* 133 */     if (Kernel32.SetConsoleCursorPosition(console, this.info.cursorPosition.copy()) == 0)
/* 134 */       throw new IOException(WindowsSupport.getLastErrorMessage()); 
/*     */   } protected void processEraseScreen(int eraseOption) throws IOException {
/*     */     Kernel32.COORD topLeft;
/*     */     int screenLength;
/*     */     Kernel32.COORD topLeft2;
/*     */     int lengthToCursor, lengthToEnd;
/* 140 */     getConsoleInfo();
/* 141 */     int[] written = new int[1];
/* 142 */     switch (eraseOption) {
/*     */       case 2:
/* 144 */         topLeft = new Kernel32.COORD();
/* 145 */         topLeft.x = 0;
/* 146 */         topLeft.y = this.info.window.top;
/* 147 */         screenLength = this.info.window.height() * this.info.size.x;
/* 148 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', screenLength, topLeft, written);
/*     */         break;
/*     */       case 1:
/* 151 */         topLeft2 = new Kernel32.COORD();
/* 152 */         topLeft2.x = 0;
/* 153 */         topLeft2.y = this.info.window.top;
/* 154 */         lengthToCursor = (this.info.cursorPosition.y - this.info.window.top) * this.info.size.x + this.info.cursorPosition.x;
/*     */         
/* 156 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', lengthToCursor, topLeft2, written);
/*     */         break;
/*     */       case 0:
/* 159 */         lengthToEnd = (this.info.window.bottom - this.info.cursorPosition.y) * this.info.size.x + this.info.size.x - this.info.cursorPosition.x;
/*     */         
/* 161 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', lengthToEnd, this.info.cursorPosition.copy(), written);
/*     */         break;
/*     */     } 
/*     */   } protected void processEraseLine(int eraseOption) throws IOException {
/*     */     Kernel32.COORD leftColCurrRow, leftColCurrRow2;
/*     */     int lengthToLastCol;
/* 167 */     getConsoleInfo();
/* 168 */     int[] written = new int[1];
/* 169 */     switch (eraseOption) {
/*     */       case 2:
/* 171 */         leftColCurrRow = this.info.cursorPosition.copy();
/* 172 */         leftColCurrRow.x = 0;
/* 173 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', this.info.size.x, leftColCurrRow, written);
/*     */         break;
/*     */       case 1:
/* 176 */         leftColCurrRow2 = this.info.cursorPosition.copy();
/* 177 */         leftColCurrRow2.x = 0;
/* 178 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', this.info.cursorPosition.x, leftColCurrRow2, written);
/*     */         break;
/*     */       case 0:
/* 181 */         lengthToLastCol = this.info.size.x - this.info.cursorPosition.x;
/* 182 */         Kernel32.FillConsoleOutputCharacterW(console, ' ', lengthToLastCol, this.info.cursorPosition.copy(), written);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processCursorLeft(int count) throws IOException {
/* 188 */     getConsoleInfo();
/* 189 */     this.info.cursorPosition.x = (short)Math.max(0, this.info.cursorPosition.x - count);
/* 190 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorRight(int count) throws IOException {
/* 195 */     getConsoleInfo();
/* 196 */     this.info.cursorPosition.x = (short)Math.min(this.info.window.width(), this.info.cursorPosition.x + count);
/* 197 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorDown(int count) throws IOException {
/* 202 */     getConsoleInfo();
/* 203 */     this.info.cursorPosition.y = (short)Math.min(this.info.size.y, this.info.cursorPosition.y + count);
/* 204 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorUp(int count) throws IOException {
/* 209 */     getConsoleInfo();
/* 210 */     this.info.cursorPosition.y = (short)Math.max(this.info.window.top, this.info.cursorPosition.y - count);
/* 211 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorTo(int row, int col) throws IOException {
/* 216 */     getConsoleInfo();
/* 217 */     this.info.cursorPosition.y = (short)Math.max(this.info.window.top, Math.min(this.info.size.y, this.info.window.top + row - 1));
/* 218 */     this.info.cursorPosition.x = (short)Math.max(0, Math.min(this.info.window.width(), col - 1));
/* 219 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processCursorToColumn(int x) throws IOException {
/* 224 */     getConsoleInfo();
/* 225 */     this.info.cursorPosition.x = (short)Math.max(0, Math.min(this.info.window.width(), x - 1));
/* 226 */     applyCursorPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetForegroundColor(int color) throws IOException {
/* 231 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFFF8 | ANSI_FOREGROUND_COLOR_MAP[color]);
/* 232 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetBackgroundColor(int color) throws IOException {
/* 237 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFF8F | ANSI_BACKGROUND_COLOR_MAP[color]);
/* 238 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processAttributeRest() throws IOException {
/* 243 */     this.info.attributes = (short)(this.info.attributes & 0xFFFFFF00 | this.originalColors);
/* 244 */     this.negative = false;
/* 245 */     applyAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSetAttribute(int attribute) throws IOException {
/* 250 */     switch (attribute) {
/*     */       case 1:
/* 252 */         this.info.attributes = (short)(this.info.attributes | Kernel32.FOREGROUND_INTENSITY);
/* 253 */         applyAttribute();
/*     */         break;
/*     */       case 22:
/* 256 */         this.info.attributes = (short)(this.info.attributes & (Kernel32.FOREGROUND_INTENSITY ^ 0xFFFFFFFF));
/* 257 */         applyAttribute();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 263 */         this.info.attributes = (short)(this.info.attributes | Kernel32.BACKGROUND_INTENSITY);
/* 264 */         applyAttribute();
/*     */         break;
/*     */       case 24:
/* 267 */         this.info.attributes = (short)(this.info.attributes & (Kernel32.BACKGROUND_INTENSITY ^ 0xFFFFFFFF));
/* 268 */         applyAttribute();
/*     */         break;
/*     */       
/*     */       case 7:
/* 272 */         this.negative = true;
/* 273 */         applyAttribute();
/*     */         break;
/*     */       case 27:
/* 276 */         this.negative = false;
/* 277 */         applyAttribute();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processSaveCursorPosition() throws IOException {
/* 284 */     getConsoleInfo();
/* 285 */     this.savedX = this.info.cursorPosition.x;
/* 286 */     this.savedY = this.info.cursorPosition.y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processRestoreCursorPosition() throws IOException {
/* 292 */     if (this.savedX != -1 && this.savedY != -1) {
/* 293 */       this.out.flush();
/* 294 */       this.info.cursorPosition.x = this.savedX;
/* 295 */       this.info.cursorPosition.y = this.savedY;
/* 296 */       applyCursorPosition();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processChangeWindowTitle(String label) {
/* 302 */     Kernel32.SetConsoleTitle(label);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\WindowsAnsiOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */