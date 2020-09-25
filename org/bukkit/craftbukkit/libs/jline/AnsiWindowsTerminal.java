/*    */ package org.bukkit.craftbukkit.libs.jline;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.fusesource.jansi.AnsiConsole;
/*    */ import org.fusesource.jansi.AnsiOutputStream;
/*    */ import org.fusesource.jansi.WindowsAnsiOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnsiWindowsTerminal
/*    */   extends WindowsTerminal
/*    */ {
/* 36 */   private final boolean ansiSupported = detectAnsiSupport();
/*    */ 
/*    */   
/*    */   public OutputStream wrapOutIfNeeded(OutputStream out) {
/* 40 */     return wrapOutputStream(out);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static OutputStream wrapOutputStream(OutputStream stream) {
/* 51 */     String os = System.getProperty("os.name");
/* 52 */     if (os.startsWith("Windows"))
/*    */       
/*    */       try {
/* 55 */         return (OutputStream)new WindowsAnsiOutputStream(stream);
/* 56 */       } catch (Throwable ignore) {
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 61 */         return (OutputStream)new AnsiOutputStream(stream);
/*    */       }  
/* 63 */     return stream;
/*    */   }
/*    */   
/*    */   private static boolean detectAnsiSupport() {
/* 67 */     AnsiConsole.systemInstall();
/* 68 */     OutputStream out = AnsiConsole.wrapOutputStream(new ByteArrayOutputStream());
/*    */     try {
/* 70 */       out.close();
/*    */     }
/* 72 */     catch (Exception e) {}
/*    */ 
/*    */     
/* 75 */     return out instanceof WindowsAnsiOutputStream;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAnsiSupported() {
/* 84 */     return this.ansiSupported;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasWeirdWrap() {
/* 89 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\AnsiWindowsTerminal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */