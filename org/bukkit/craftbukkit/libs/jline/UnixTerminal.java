/*     */ package org.bukkit.craftbukkit.libs.jline;
/*     */ 
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.TerminalLineSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnixTerminal
/*     */   extends TerminalSupport
/*     */ {
/*  30 */   private final TerminalLineSettings settings = new TerminalLineSettings();
/*     */   
/*     */   public UnixTerminal() throws Exception {
/*  33 */     super(true);
/*     */   }
/*     */   
/*     */   protected TerminalLineSettings getSettings() {
/*  37 */     return this.settings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() throws Exception {
/*  46 */     super.init();
/*     */     
/*  48 */     setAnsiSupported(true);
/*     */ 
/*     */     
/*  51 */     this.settings.set("-icanon min 1");
/*     */     
/*  53 */     setEchoEnabled(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restore() throws Exception {
/*  63 */     this.settings.restore();
/*  64 */     super.restore();
/*     */ 
/*     */     
/*  67 */     System.out.println();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  75 */     int w = this.settings.getProperty("columns");
/*  76 */     return (w < 1) ? 80 : w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  84 */     int h = this.settings.getProperty("rows");
/*  85 */     return (h < 1) ? 24 : h;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setEchoEnabled(boolean enabled) {
/*     */     try {
/*  91 */       if (enabled) {
/*  92 */         this.settings.set("echo");
/*     */       } else {
/*     */         
/*  95 */         this.settings.set("-echo");
/*     */       } 
/*  97 */       super.setEchoEnabled(enabled);
/*     */     }
/*  99 */     catch (Exception e) {
/* 100 */       Log.error(new Object[] { "Failed to ", enabled ? "enable" : "disable", " echo: ", e });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\UnixTerminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */