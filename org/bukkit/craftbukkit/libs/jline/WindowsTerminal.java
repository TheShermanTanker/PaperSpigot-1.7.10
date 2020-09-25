/*     */ package org.bukkit.craftbukkit.libs.jline;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
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
/*     */ 
/*     */ 
/*     */ public class WindowsTerminal
/*     */   extends TerminalSupport
/*     */ {
/*     */   public static final String JLINE_WINDOWS_TERMINAL_DIRECT_CONSOLE = "org.bukkit.craftbukkit.libs.jline.WindowsTerminal.directConsole";
/*  58 */   public static final String ANSI = WindowsTerminal.class.getName() + ".ansi";
/*     */   
/*     */   private boolean directConsole;
/*     */   
/*     */   private int originalMode;
/*     */   
/*     */   public WindowsTerminal() throws Exception {
/*  65 */     super(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() throws Exception {
/*  70 */     super.init();
/*     */     
/*  72 */     setAnsiSupported(Configuration.getBoolean(ANSI, true));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     setDirectConsole(Configuration.getBoolean("org.bukkit.craftbukkit.libs.jline.WindowsTerminal.directConsole", true));
/*     */     
/*  80 */     this.originalMode = getConsoleMode();
/*  81 */     setConsoleMode(this.originalMode & (ConsoleMode.ENABLE_ECHO_INPUT.code ^ 0xFFFFFFFF));
/*  82 */     setEchoEnabled(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restore() throws Exception {
/*  93 */     setConsoleMode(this.originalMode);
/*  94 */     super.restore();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  99 */     int w = getWindowsTerminalWidth();
/* 100 */     return (w < 1) ? 80 : w;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 105 */     int h = getWindowsTerminalHeight();
/* 106 */     return (h < 1) ? 24 : h;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEchoEnabled(boolean enabled) {
/* 112 */     if (enabled) {
/* 113 */       setConsoleMode(getConsoleMode() | ConsoleMode.ENABLE_ECHO_INPUT.code | ConsoleMode.ENABLE_LINE_INPUT.code | ConsoleMode.ENABLE_PROCESSED_INPUT.code | ConsoleMode.ENABLE_WINDOW_INPUT.code);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 120 */       setConsoleMode(getConsoleMode() & ((ConsoleMode.ENABLE_LINE_INPUT.code | ConsoleMode.ENABLE_ECHO_INPUT.code | ConsoleMode.ENABLE_PROCESSED_INPUT.code | ConsoleMode.ENABLE_WINDOW_INPUT.code) ^ 0xFFFFFFFF));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     super.setEchoEnabled(enabled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirectConsole(boolean flag) {
/* 133 */     this.directConsole = flag;
/* 134 */     Log.debug(new Object[] { "Direct console: ", Boolean.valueOf(flag) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getDirectConsole() {
/* 141 */     return Boolean.valueOf(this.directConsole);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream wrapInIfNeeded(InputStream in) throws IOException {
/* 147 */     if (this.directConsole && isSystemIn(in)) {
/* 148 */       return new InputStream()
/*     */         {
/*     */           public int read() throws IOException {
/* 151 */             return WindowsTerminal.this.readByte();
/*     */           }
/*     */         };
/*     */     }
/* 155 */     return super.wrapInIfNeeded(in);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSystemIn(InputStream in) throws IOException {
/* 160 */     assert in != null;
/*     */     
/* 162 */     if (in == System.in) {
/* 163 */       return true;
/*     */     }
/* 165 */     if (in instanceof FileInputStream && ((FileInputStream)in).getFD() == FileDescriptor.in) {
/* 166 */       return true;
/*     */     }
/*     */     
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getConsoleMode() {
/* 176 */     return WindowsSupport.getConsoleMode();
/*     */   }
/*     */   
/*     */   private void setConsoleMode(int mode) {
/* 180 */     WindowsSupport.setConsoleMode(mode);
/*     */   }
/*     */   
/*     */   private int readByte() {
/* 184 */     return WindowsSupport.readByte();
/*     */   }
/*     */   
/*     */   private int getWindowsTerminalWidth() {
/* 188 */     return WindowsSupport.getWindowsTerminalWidth();
/*     */   }
/*     */   
/*     */   private int getWindowsTerminalHeight() {
/* 192 */     return WindowsSupport.getWindowsTerminalHeight();
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
/*     */   public enum ConsoleMode
/*     */   {
/* 207 */     ENABLE_LINE_INPUT(2),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     ENABLE_ECHO_INPUT(4),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     ENABLE_PROCESSED_INPUT(1),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     ENABLE_WINDOW_INPUT(8),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 241 */     ENABLE_MOUSE_INPUT(16),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     ENABLE_PROCESSED_OUTPUT(1),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     ENABLE_WRAP_AT_EOL_OUTPUT(2);
/*     */     
/*     */     public final int code;
/*     */     
/*     */     ConsoleMode(int code) {
/* 262 */       this.code = code;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\WindowsTerminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */