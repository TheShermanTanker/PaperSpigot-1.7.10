/*     */ package org.fusesource.jansi.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsSupport
/*     */ {
/*     */   public static String getLastErrorMessage() {
/*  32 */     int errorCode = Kernel32.GetLastError();
/*  33 */     int bufferSize = 160;
/*  34 */     byte[] data = new byte[bufferSize];
/*  35 */     Kernel32.FormatMessageW(Kernel32.FORMAT_MESSAGE_FROM_SYSTEM, 0L, errorCode, 0, data, bufferSize, null);
/*  36 */     return new String(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int readByte() {
/*  46 */     return Kernel32._getch();
/*     */   }
/*     */   
/*     */   public static int getConsoleMode() {
/*  50 */     long hConsole = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
/*  51 */     if (hConsole == Kernel32.INVALID_HANDLE_VALUE)
/*  52 */       return -1; 
/*  53 */     int[] mode = new int[1];
/*  54 */     if (Kernel32.GetConsoleMode(hConsole, mode) == 0)
/*  55 */       return -1; 
/*  56 */     return mode[0];
/*     */   }
/*     */   
/*     */   public static void setConsoleMode(int mode) {
/*  60 */     long hConsole = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
/*  61 */     if (hConsole == Kernel32.INVALID_HANDLE_VALUE)
/*     */       return; 
/*  63 */     Kernel32.SetConsoleMode(hConsole, mode);
/*     */   }
/*     */   
/*     */   public static int getWindowsTerminalWidth() {
/*  67 */     long outputHandle = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
/*  68 */     Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
/*  69 */     Kernel32.GetConsoleScreenBufferInfo(outputHandle, info);
/*  70 */     return info.windowWidth();
/*     */   }
/*     */   
/*     */   public static int getWindowsTerminalHeight() {
/*  74 */     long outputHandle = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
/*  75 */     Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
/*  76 */     Kernel32.GetConsoleScreenBufferInfo(outputHandle, info);
/*  77 */     return info.windowHeight();
/*     */   }
/*     */   
/*     */   public static int writeConsole(String msg) {
/*  81 */     long hConsole = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
/*  82 */     if (hConsole == Kernel32.INVALID_HANDLE_VALUE)
/*  83 */       return 0; 
/*  84 */     char[] chars = msg.toCharArray();
/*  85 */     int[] written = new int[1];
/*  86 */     if (Kernel32.WriteConsoleW(hConsole, chars, chars.length, written, 0L) != 0) {
/*  87 */       return written[0];
/*     */     }
/*  89 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Kernel32.INPUT_RECORD[] readConsoleInput(int count) throws IOException {
/*  94 */     long hConsole = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
/*  95 */     if (hConsole == Kernel32.INVALID_HANDLE_VALUE)
/*  96 */       return null; 
/*  97 */     return Kernel32.readConsoleKeyInput(hConsole, count, false);
/*     */   }
/*     */   
/*     */   public static Kernel32.INPUT_RECORD[] peekConsoleInput(int count) throws IOException {
/* 101 */     long hConsole = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
/* 102 */     if (hConsole == Kernel32.INVALID_HANDLE_VALUE)
/* 103 */       return null; 
/* 104 */     return Kernel32.readConsoleKeyInput(hConsole, count, true);
/*     */   }
/*     */   
/*     */   public static void flushConsoleInputBuffer() {
/* 108 */     long hConsole = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
/* 109 */     if (hConsole == Kernel32.INVALID_HANDLE_VALUE)
/*     */       return; 
/* 111 */     Kernel32.FlushConsoleInputBuffer(hConsole);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\internal\WindowsSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */