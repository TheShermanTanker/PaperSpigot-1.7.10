/*     */ package org.bukkit.craftbukkit.libs.jline;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TerminalSupport
/*     */   implements Terminal
/*     */ {
/*     */   public static final String JLINE_SHUTDOWNHOOK = "org.bukkit.craftbukkit.libs.jline.shutdownhook";
/*     */   public static final int DEFAULT_WIDTH = 80;
/*     */   public static final int DEFAULT_HEIGHT = 24;
/*     */   private Thread shutdownHook;
/*     */   private boolean shutdownHookEnabled;
/*     */   private boolean supported;
/*     */   private boolean echoEnabled;
/*     */   private boolean ansiSupported;
/*     */   private Configuration configuration;
/*     */   
/*     */   protected TerminalSupport(boolean supported) {
/*  45 */     this.supported = supported;
/*  46 */     this.shutdownHookEnabled = Configuration.getBoolean("org.bukkit.craftbukkit.libs.jline.shutdownhook", false);
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/*  50 */     installShutdownHook(new RestoreHook());
/*     */   }
/*     */   
/*     */   public void restore() throws Exception {
/*  54 */     TerminalFactory.resetIf(this);
/*  55 */     removeShutdownHook();
/*     */   }
/*     */   
/*     */   public void reset() throws Exception {
/*  59 */     restore();
/*  60 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installShutdownHook(Thread hook) {
/*  66 */     if (!this.shutdownHookEnabled) {
/*  67 */       Log.debug(new Object[] { "Not install shutdown hook " + hook + " because they are disabled." });
/*     */       
/*     */       return;
/*     */     } 
/*  71 */     assert hook != null;
/*     */     
/*  73 */     if (this.shutdownHook != null) {
/*  74 */       throw new IllegalStateException("Shutdown hook already installed");
/*     */     }
/*     */     
/*     */     try {
/*  78 */       Runtime.getRuntime().addShutdownHook(hook);
/*  79 */       this.shutdownHook = hook;
/*     */     }
/*  81 */     catch (AbstractMethodError e) {
/*     */       
/*  83 */       Log.trace(new Object[] { "Failed to register shutdown hook: ", e });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void removeShutdownHook() {
/*  88 */     if (!this.shutdownHookEnabled) {
/*     */       return;
/*     */     }
/*  91 */     if (this.shutdownHook != null) {
/*     */       try {
/*  93 */         Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
/*     */       }
/*  95 */       catch (AbstractMethodError e) {
/*     */         
/*  97 */         Log.trace(new Object[] { "Failed to remove shutdown hook: ", e });
/*     */       }
/*  99 */       catch (IllegalStateException e) {}
/*     */ 
/*     */       
/* 102 */       this.shutdownHook = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final boolean isSupported() {
/* 107 */     return this.supported;
/*     */   }
/*     */   
/*     */   public synchronized boolean isAnsiSupported() {
/* 111 */     return this.ansiSupported;
/*     */   }
/*     */   
/*     */   protected synchronized void setAnsiSupported(boolean supported) {
/* 115 */     this.ansiSupported = supported;
/* 116 */     Log.debug(new Object[] { "Ansi supported: ", Boolean.valueOf(supported) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream wrapOutIfNeeded(OutputStream out) {
/* 124 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasWeirdWrap() {
/* 131 */     return true;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 135 */     return 80;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 139 */     return 24;
/*     */   }
/*     */   
/*     */   public synchronized boolean isEchoEnabled() {
/* 143 */     return this.echoEnabled;
/*     */   }
/*     */   
/*     */   public synchronized void setEchoEnabled(boolean enabled) {
/* 147 */     this.echoEnabled = enabled;
/* 148 */     Log.debug(new Object[] { "Echo enabled: ", Boolean.valueOf(enabled) });
/*     */   }
/*     */   
/*     */   public InputStream wrapInIfNeeded(InputStream in) throws IOException {
/* 152 */     return in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class RestoreHook
/*     */     extends Thread
/*     */   {
/*     */     public void start() {
/*     */       try {
/* 164 */         TerminalSupport.this.restore();
/*     */       }
/* 166 */       catch (Exception e) {
/* 167 */         Log.trace(new Object[] { "Failed to restore: ", e });
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\TerminalSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */